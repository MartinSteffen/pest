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

