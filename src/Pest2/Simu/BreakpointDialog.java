package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import absyn.*;
import tesc1.*;
import java.io.*;
import gui.*;

public class BreakpointDialog extends Dialog implements ActionListener{

  Button b=null;
  TextField tf=null;
  List list=null;
  GUIInterface gui=null;
  Statechart chart=null;
  
  BreakpointDialog(Frame parent, Statechart s, GUIInterface g){
    super(parent,"Breakpoints",false);
    setLayout(new FlowLayout());
    tf=new TextField(10);
    b=new Button("Breakpoint setzen");
    b.addActionListener(this);
    add(tf);
    add(b);

    list=new List(10,false);
    list.setSize(150,150);
    add(list);

    setSize(200,300);

    gui=g;
    try{
      chart=(Statechart)s.clone();
    }
    catch (CloneNotSupportedException e){
    }
  }
      

  public void actionPerformed(ActionEvent e){
    String text=tf.getText();
    if ((text!="")&&(text!=null)){
      StringReader txt=new StringReader(text);
      BufferedReader buff=new BufferedReader(txt);
      TESCLoader loader=new TESCLoader(gui);
      try{
	TLabel label=loader.getLabel(buff,chart);
      }
      catch (IOException f){
      }
      tf.setText("");
      setVisible(false);
    }
  }
  
}
    
  
  
 
