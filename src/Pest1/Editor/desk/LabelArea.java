package editor.desk;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import absyn.*;
import editor.desk.*;
import gui.*;
import gui.popdialoge.*;
import java.lang.*;
import tesc1.*;


public class LabelArea extends Dialog {

  static int x,y;
  static Frame tf = new Frame("Transitionslabel");
  static TextArea ta = new TextArea();
 static Panel panel = new Panel();
       ActionListener listener = new ActionListener() {
       public void actionPerformed(ActionEvent e){
       // Buttontype = e.getActionCommand();
	   //  LabelArea.hide();
	    tf.dispose();
	}
    };


 public LabelArea(int hx, int hy,String lab)
	{
	super(tf,"Transition",true);
	// tf = this.frame;
	this.setSize(250,250);
	this.setLocation(70,100);
	this.setLayout(new BorderLayout(3,3));
	this.setResizable(false);
	text(lab);
	ta.setEditable(false);
	this.add("Center",ta);
	Button OK = new Button("O.K.");
	this.add("South",OK);

	OK.setActionCommand("O.K.");
	OK.addActionListener(listener);

	//this.isModal();
	this.show();
	//this.setVisible(false);
	}

  public void show(int cx, int cy) {
      tf.setLocation(cx,cy);
       tf.setVisible(true);
	}

  public void hide() {
      tf.setVisible(false);
	}
 public void text(String txt) {
     int lauf;
     String hs1 = txt,ts1="",ts2="",tt1 = "", tt2 = "";
     ts1=ts1.trim();
     lauf = hs1.indexOf("/");
     //System.out.println("Position von / :"+lauf);
     if (lauf == -1) {ts1 = hs1;ts2 = "";}
     if (lauf == 0 & hs1.length()>1) {ts2 = hs1.substring(1);}
     if (lauf > 0 & lauf >= hs1.length()+1) {ts1 = hs1.substring(0,lauf);}
     if (lauf > 0  & lauf < hs1.length()+1) {ts2 = hs1.substring(lauf+1);}
     ts1 = ts1.trim();
     ts2 = ts2.trim();
     ta.setText("");
     //System.out.println("Teilstring1 : -"+ts1+"-");
     //System.out.println("Teilstring2 : -"+ts2+"-");

     // ************************** zu bearbeiten
     ta.append("-- Liste der Guards -- \n");
     if (ts1.length() != 0)
     {
	 
	 if (ts1.indexOf("||") > 0) {lauf = ts1.indexOf("||");}
	 if (ts1.indexOf("&&") < lauf & ts1.indexOf("&&") > -1) {lauf = ts1.indexOf("&&");}
	 while (lauf != -1)
	     {
		 tt1 = ts1.substring(0,lauf+2);
		 ts1 = ts1.substring(lauf+2);
	     //System.out.println("        reported : -"+tt2+"-");
		 ta.append(tt1+"\n");
		 // System.out.println("        Teilstring2 : -"+ts1+"-");
	 
		 {lauf = ts1.indexOf("&&");}
		 if (ts1.indexOf("||") > -1 & ts1.indexOf("||") < lauf) {lauf = ts1.indexOf("||");}
	     }
     ta.append(ts1+"\n");
     } else
	 {
	 ta.append("[leer] \n \n");    
	 }
     // ***********************************
     ta.append("-- Liste der Aktionen -- \n");
     ts2 = ts2.trim();
     if (ts2.length() != 0)
     {
	 
	 lauf = ts2.indexOf(",");
	 while (lauf != -1)
	     {
		 tt2 = ts2.substring(0,lauf);
		 ts2 = ts2.substring(lauf+1);
	     //System.out.println("        reported : -"+tt2+"-");
		 ta.append(tt2+"\n");
		 // System.out.println("        Teilstring2 : -"+ts2+"-");
		 lauf = ts2.indexOf(",");
	     }
     ta.append(ts2+"\n");
     } else
	 {
	 ta.append("[leer] \n");    
	 }
}
} // class
  
