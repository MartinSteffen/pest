package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import absyn.*;
import editor.*;
import gui.*;
import util.*;

class Communicator extends Frame implements ActionListener, ItemListener{
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
  Vector brkpts=null;

  boolean record_trace=false;

  Monitor monitor=null;
  Tracer tracer=null;


  BreakpointDialog brdialog=null;



  List list=null;
  Vector listvector=null;
  Tr listresult=null;

  public Communicator(GUIInterface g,Statechart s){
    super("PEST: Simulation");
    gui=g;
    akt_status=new Status();
    prev_status=new Status();
    running=true;
    chart=s;
    monitor=new Monitor(chart,this);
    tracer=new Tracer(this,gui,chart);
    brdialog=new BreakpointDialog(this,chart,gui,new Vector());
    brkpts=new Vector();
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
    return prev_status;
  }

  void trace_record(boolean recording){
    record_trace=recording;
  }

  void trace_set(Status status){
    prev_status=akt_status;
    akt_status=status;
    unhighlightPrevStates();
    unhighlightPrevTrs();
    highlightAktStates();
    highlightAktTrs();
    monitor.setStatus(akt_status);      
    brkpts=brdialog.getAnswer();
    TLabel label=null;
    Guard gres=null;
    if (brkpts!=null){
      if (brkpts.size()>0){
	for (int i=0; i<brkpts.size(); i++){
	  label=(TLabel)brkpts.elementAt(i);
	  gres=label.guard;
	  if (maschine.isSatisfied(gres)){
	    int j=gui.OkDialog("Breakpoint erfuellt!",""+label.caption+"");
	  }
	}
      }
    }
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
      if (record_trace){
	tracer.add(akt_status);
      }
      monitor.setStatus(akt_status);      
      brkpts=brdialog.getAnswer();
      TLabel label=null;
      Guard gres=null;
      if (brkpts!=null){
	if (brkpts.size()>0){
	  for (i=0; i<brkpts.size(); i++){
	    label=(TLabel)brkpts.elementAt(i);
	    gres=label.guard;
	    if (maschine.isSatisfied(gres)){
	      int j=gui.OkDialog("Breakpoint erfuellt!",""+label.caption+"");
	    }
	  }
	}
      }
    }
  }

  void setzeEvent(String name){
    akt_status.events.set(name,new SEvent(name));
    monitor.setStatus(akt_status);
  }

  void aendereAutodet(){
    autodet=!autodet;
  }

  void brpo(){
    brdialog=new BreakpointDialog(this,chart,gui,brkpts);
    brdialog.show();
  }

  void highlightAktStates(){
    highlightObject highlight=null;
    StateTabelle states=akt_status.states;
    Enumeration actives=states.data.elements();
    State element=null;
    while (actives.hasMoreElements()){
      element=(State)actives.nextElement();
      if (element!=null){
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
      else{
	if (gui.isDebug()){
	  System.out.println("State ist null");
	}
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
      if (element!=null){
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
      else{
	if (gui.isDebug()){
	  System.out.println("State ist null");
	}
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
      if (element!=null){
	highlight=new highlightObject(element,Color.black);
      }
      else{
	if (gui.isDebug()){
	  System.out.println("Tr ist null");
	}
      }
    }
  }
  
  void unhighlightPrevTrs(){
    highlightObject highlight=null;
    TransitionTabelle transitions=prev_status.transitions;
    Enumeration actives=transitions.data.elements();
    Tr element=null;
    while (actives.hasMoreElements()){
      element=(Tr)actives.nextElement();
      if (element!=null){
	highlight=new highlightObject(element);
      }
      else{
	if (gui.isDebug()){
	  System.out.println("Tr ist null");
	}
      }
    }
  } 


  

  void buildFrame(){
    setSize(200,200);
    setLayout(new FlowLayout());
    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
	unhighlightPrevTrs();
	unhighlightPrevStates();
	setVisible(false);
	monitor.setVisible(false);
	tracer.setVisible(false);
	gui.simuExit();
      }
    });
    Button b1=new Button("naechster Simulationsschritt");
    b1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	vor();
      }
    });  
  
    Button b5=new Button("Monitor");
    b5.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	monitor.show();
      }
    });  

    Button b6=new Button("Tracer");
    b6.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	tracer.show();
      }
    });  
    
    MenuBar bar=new MenuBar();
    setMenuBar(bar);
    Menu einstellungen=new Menu("Einstellungen");
    bar.add(einstellungen);
    CheckboxMenuItem ndet=new CheckboxMenuItem("automatische Aufloesung von Nichtdeterminismus",false);
    einstellungen.add(ndet);
    ndet.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e){
	aendereAutodet();
      }
    });
    MenuItem breakpoints=new MenuItem("Breakpoints");
    einstellungen.add(breakpoints);
    breakpoints.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	brpo();
      }
    });
    MenuItem schrittweite=new MenuItem("Schrittweite");
    einstellungen.add(schrittweite);
    schrittweite.addActionListener(this);
    add(b1);
    add(b5);
    add(b6);
    pack();
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e){
    SchrittweitenDialog dialog=new SchrittweitenDialog(this,Schrittanzahl);
    dialog.show();
    int result=dialog.getAnswer();
    Schrittanzahl=result;
  }

  public void itemStateChanged(ItemEvent e){
    Checkbox box=(Checkbox)e.getItemSelectable();
    int change=e.getStateChange();
    if (box instanceof EventView){
      EventView eview=(EventView)box;
      SEvent event=eview.event;
      String name=event.name;
      if (change==ItemEvent.SELECTED){
	akt_status.events.set(name,new SEvent(name));
	//monitor.setStatus(akt_status);
      }
      if (change==ItemEvent.DESELECTED){
	akt_status.events.remove(name);
	//monitor.setStatus(akt_status);
      }
    }
    if (box instanceof BooleanView){
      BooleanView bview=(BooleanView)box;
      Bvar bvar=bview.bvar;
      String name=bvar.var;
      if (change==ItemEvent.SELECTED){
	akt_status.booleans.setTrue(name);
	//monitor.setStatus(akt_status);
      }
      if (change==ItemEvent.DESELECTED){
	akt_status.booleans.setFalse(name);
	//monitor.setStatus(akt_status);
      }
    }
   
  }


}







