package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import absyn.*;
import editor.*;
import gui.*;

public class Communicator extends Frame{
  Statechart chart=null;
  Editor editor=null;
  GUIInterface gui=null;
  Nachfolgermaschine maschine=null;
  Status akt_status=null;
  Status prev_status=null;
  boolean running=false;
  TextField tf=null;

  List list=null;
  Vector listvector=null;
  Tr listresult=null;

  public Communicator(GUIInterface g){
    super("PEST: Simulation");
    gui=g;
    akt_status=new Status();
    prev_status=new Status();
    running=true;
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

  /* to be done... */
  public Vector solveNonDeterminism(Vector v){
    Vector result=null;
    System.err.println("Nicht-Determinismus");
    NonDeterminismDialog dialog=new NonDeterminismDialog(this,v);
    dialog.show();
    result=dialog.getAnswer();
    return result;
  }

  /* to be done */
  public Status solveBVarRacing(RacingException e){
    System.err.println("Racing....");
    Status result=new Status();
    BooleanTabelle racing_status=e.status;
    Bvar racing_var=e.var;
    BVarRacingDialog dialog=new BVarRacingDialog(this,racing_status,racing_var);
    dialog.show();
    racing_status=dialog.getAnswer();
    return result;
  }


  /* loeseNDeterm bekommt einen Vector mit mehreren moeglichen Transitionen */
  /* und liefert einen Vector mit genau einer Transition zurueck.           */
  /* Funktioniert noch nicht, da keine Rueckgabe bei knopfdruck....         */
  public Vector loeseNDeterm(Vector v){
    Vector result=new Vector(1);
    Tr transition=null;
    TrAnchor von=null;
    TrAnchor nach=null;
    String vontext=null;
    String nachtext=null;
    listvector=v;
    int count=listvector.size();
    Frame f=new Frame("Nichtdeterminismus aufgetreten");
    int listsize=0;
    if (count>10){
      listsize=10;
    }
    else{
      listsize=count;
    }
    list=new List(listsize,false);
    for (int i=0; i<count; i++){
      transition=(Tr)listvector.elementAt(i);
      von=transition.source;
      nach=transition.target;
      if (von instanceof absyn.Statename){
	vontext=((Statename)von).name;
      }
      else{
	vontext=((Conname)von).name;
      }
      if (nach instanceof absyn.Statename){
	nachtext=((Statename)nach).name;
      }
      else{
	nachtext=((Conname)nach).name;
      }
      list.addItem(vontext+"->"+nachtext);
    }
    list.select(1);
    Button b=new Button("Auswählen");
    b.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	int index=list.getSelectedIndex();
	Tr t=(Tr)listvector.elementAt(index);
	/* result.addElement(t); kann nicht funktionieren, nur zur Erinnerung da... */
      }
    });  
    return result;
  }
    
    

  void vor(){
    Status temp=maschine.liefereNachfolger(prev_status,akt_status);
    prev_status=akt_status;
    akt_status=temp;
    System.err.println("-----------------------");
    System.err.println("Ergebnis des Schrittes:");
    akt_status.debug();
    System.err.println("-----------------------");
  }

  void setzeEvent(String name){
    akt_status.events.set(name,new SEvent(name));
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
    Button b1=new Button("1 Schritt weiter");
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
    add(b1);
    add(tf);
    add(b4);
    pack();
    setVisible(true);
  }

}

