package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import absyn.*;
import tesc1.*;
import java.io.*;
import gui.*;

public class BreakpointDialog extends Dialog implements ActionListener{

  Button b1=null;
  Button b2=null;
  TextField tf=null;
  List list=null;
  GUIInterface gui=null;
  Statechart chart=null;
  Vector result=null;
  
  BreakpointDialog(Frame parent, Statechart s, GUIInterface g, Vector res){
    super(parent,"Breakpoints",false);
    setLayout(new FlowLayout());
    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
	setVisible(false);
      }
    });
    result=res;
    gui=g;
    try{
      chart=(Statechart)s.clone();
    }
    catch (CloneNotSupportedException e){
    }
    tf=new TextField(10);
    b1=new Button("Breakpoint setzen");
    b1.addActionListener(this);
    add(tf);
    add(b1);
    list=new List(10,false);
    list.setSize(150,150);
    add(list);
    if (result.size()>0){
      for (int i=0; i<result.size(); i++){
	String stringres=(String)result.elementAt(i);
	list.add(stringres);
      }
    }
    b2=new Button("ausgewaehlten Breakpoint loeschen");
    b2.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	int index=list.getSelectedIndex();
	if (index>=0){
	  list.remove(index);
	  result.removeElementAt(index);
	}
      }
    });
    add(b2);
    setSize(300,300);
  }
      

  public void actionPerformed(ActionEvent e){
    String text=tf.getText();
    if ((text!="")&&(text!=null)){
      StringReader txt=new StringReader(text);
      BufferedReader buff=new BufferedReader(txt);
      TESCLoader loader=new TESCLoader(gui);
      TLabel label=null;
      try{
	label=loader.getLabel(buff,chart);
      }
      catch (IOException f){
      }
      tf.setText("");
      if (label!=null){
	String labelstring=label.caption;
	list.add(labelstring);
	result.insertElementAt(labelstring,result.size());
      }
    }
  }

  public Vector getAnswer(){
    return result;
  }
  
}
    
  
  
 
