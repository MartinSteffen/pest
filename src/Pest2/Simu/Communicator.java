package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import absyn.*;
import editor.*;
import gui.*;
import util.*;

public class Communicator extends Frame implements ActionListener{
  Statechart chart=null;
  Editor editor=null;
  GUIInterface gui=null;
  Nachfolgermaschine maschine=null;
  Status akt_status=null;
  Status prev_status=null;
  boolean running=false;
  TextField tf=null;
  int Schrittanzahl=1;
  boolean autodet=false;

  Monitor monitor=null;

  List list=null;
  Vector listvector=null;
  Tr listresult=null;

  public Communicator(GUIInterface g){
    super("PEST: Simulation");
    gui=g;
    akt_status=new Status();
    prev_status=new Status();
    running=true;
    monitor=new Monitor();
    buildFrame();
  }

  public void setMachine(Nachfolgermaschine m){
    maschine=m;
  }

  public void setEditor(Editor e){
    editor=e;
  }

  public boolean isRunning(){
    return true;
  }

  public Vector solveNonDeterminism(Vector v){
    Vector result=null;
    if (gui.isDebug()){
      System.out.println("Nicht-Determinismus");
    }
    if (!autodet){
      NonDeterminismDialog dialog=new NonDeterminismDialog(this,v);
      dialog.show();
      if (gui.isDebug()){
	System.out.println("Und weiter gehts...");
      }   
      result=dialog.getAnswer();
      return result;
    }
    else{
      RandomIntGenerator rig=new RandomIntGenerator(0,(v.size()-1));
      int i=rig.nextInt();
      Tr t=(Tr)v.elementAt(i);
      result=new Vector();
      result.insertElementAt(t,0);
      return result;
    }
  }

  public Status solveBVarRacing(RacingException e){
    if (gui.isDebug()){
      System.out.println("Racing....");
    }
    Status result=new Status();
    BooleanTabelle racing_status=e.status;
    Bvar racing_var=e.var;
    BVarRacingDialog dialog=new BVarRacingDialog(this,racing_status,racing_var);
    dialog.show();
    racing_status=dialog.getAnswer();
    return result;
  }


  void vor(){
    for (int i=1; i<=Schrittanzahl; i++){
      Status temp=maschine.liefereNachfolger(prev_status,akt_status);
      prev_status=akt_status;
      akt_status=temp;
      if (gui.isDebug()){
	System.out.println("-----------------------");
	System.out.println("Ergebnis des Schrittes:");
	akt_status.debug();
	System.out.println("-----------------------");
      }    
      unhighlightPrevStates();
      unhighlightPrevTrs();
      highlightAktStates();
      highlightAktTrs();
    }
  }

  void setzeEvent(String name){
    akt_status.events.set(name,new SEvent(name));
  }

  void aendereAutodet(){
    autodet=!autodet;
  }

  void highlightAktStates(){
    highlightObject highlight=null;
    StateTabelle states=akt_status.states;
    Enumeration actives=states.data.elements();
    State element=null;
    while (actives.hasMoreElements()){
      element=(State)actives.nextElement();
      if (element instanceof Or_State){
	highlight=new highlightObject((Or_State)element,Color.white);
      }
      if (element instanceof Basic_State){
	highlight=new highlightObject((Basic_State)element,Color.white);
      }
        if (element instanceof And_State){
	highlight=new highlightObject((And_State)element,Color.white);
      }
    }
  }

  void unhighlightPrevStates(){
    highlightObject highlight=null;
    StateTabelle states=prev_status.states;
    Enumeration actives=states.data.elements();
    State element=null;
    while (actives.hasMoreElements()){
      element=(State)actives.nextElement();
        if (element instanceof Or_State){
	highlight=new highlightObject((Or_State)element);
      }
      if (element instanceof Basic_State){
	highlight=new highlightObject((Basic_State)element);
      }
        if (element instanceof And_State){
	highlight=new highlightObject((And_State)element);
      }
    }
  }
    
  void highlightAktTrs(){
    highlightObject highlight=null;
    StateTabelle states=prev_status.states;
    TransitionTabelle transitions=akt_status.transitions;
    Enumeration actives=transitions.data.elements();
    Tr element=null;
    while (actives.hasMoreElements()){
      element=(Tr)actives.nextElement();
      highlight=new highlightObject(element,Color.black);
    }
  }
  
  void unhighlightPrevTrs(){
    highlightObject highlight=null;
    TransitionTabelle transitions=akt_status.transitions;
    Enumeration actives=transitions.data.elements();
    Tr element=null;
    while (actives.hasMoreElements()){
      element=(Tr)actives.nextElement();
      highlight=new highlightObject(element);
    }
  } 


  

  void buildFrame(){
    setSize(200,200);
    setLayout(new FlowLayout());
    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
	setVisible(false);
	running=false;
      }
    });
    Button b1=new Button("naechster Simulationsschritt");
    b1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	vor();
      }
    });  
    tf=new TextField(10);
    Button b4=new Button("Event setzen");
    b4.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	String text=tf.getText();
	if ((text!="")&&(text!=null)){
	  setzeEvent(text);
	}
	tf.setText("");
      }
    });  
    Button b5=new Button("Monitor");
    b5.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	monitor.show();
      }
    });  
    
    MenuBar bar=new MenuBar();
    setMenuBar(bar);
    Menu einstellungen=new Menu("Einstellungen");
    bar.add(einstellungen);
    CheckboxMenuItem ndet=new CheckboxMenuItem("automatische Aufloesung von Nichtdeteminismus",false);
    einstellungen.add(ndet);
    ndet.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e){
	aendereAutodet();
      }
    });
    MenuItem schrittweite=new MenuItem("Schrittweite");
    einstellungen.add(schrittweite);
    schrittweite.addActionListener(this);
    add(b1);
    add(tf);
    add(b4);
    add(b5);
    pack();
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e){
    SchrittweitenDialog dialog=new SchrittweitenDialog(this,Schrittanzahl);
    dialog.show();
    int result=dialog.getAnswer();
    Schrittanzahl=result;
  }


}







