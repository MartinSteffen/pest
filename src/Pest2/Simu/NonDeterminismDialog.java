package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import absyn.*;

public class NonDeterminismDialog extends Dialog implements ActionListener{

  Vector result=null;
  Vector listvector=null;
  List list=null;
  Button b=null;

  NonDeterminismDialog(Frame parent,Vector v){
    super(parent,"Nichtdeterminismus",true);
    setLayout(new FlowLayout());
    result=new Vector(1);
    Tr transition;
    TrAnchor von=null;
    TrAnchor nach=null;
    String vontext=null;
    String nachtext=null;
    listvector=v;
    int count=listvector.size();
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
    list.select(0);
    list.setSize(150,150);
    Button b=new Button("Auswählen");
    b.addActionListener(this);
    add(list);
    add(b);
    setSize(200,200);
  }

  public void actionPerformed(ActionEvent e){
    int index=list.getSelectedIndex();
    System.err.println("User hat angeklickt: "+index);
    Tr t=(Tr)listvector.elementAt(index);
    result.insertElementAt(t,0);
    setVisible(false);
  }

  public Vector getAnswer(){
    return result;
  }

}
    
  
  
