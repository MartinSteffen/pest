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
	super(tf,"test",true);
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
     //ta.setText(txt+"\n");
     
     // ta.append(txt);
	}

} // class
  
