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


public class SelectArea extends Dialog {

    static int x,y;
    static Frame tf = new Frame("Referenzspezifikation");
    FileDialog fDialog = new FileDialog(tf);
    TextField TXT = new java.awt.TextField("",31);
    Choice sel = new Choice();


    public String FileName;
    public Syntax_Type filetype;

    ActionListener listener = new ActionListener() {
    public void actionPerformed(ActionEvent e){
	    
     if (e.getActionCommand() == "Datei")
      {
		fDialog.setMode(FileDialog.LOAD);
		fDialog.setTitle("Statechart speichern");
		fDialog.setVisible(true);
		FileName = fDialog.getFile();
		TXT.setText(FileName);
        }

     if (e.getActionCommand() == "OK")
      {
	if (sel.getSelectedIndex()==0) {filetype = new Pest_CoordSyntax();}
	if (sel.getSelectedIndex()==1) {filetype = new Pest_NocoordSyntax();}
	if (sel.getSelectedIndex()==2) {filetype = new Tesc_Syntax();}
	tf.dispose();
        }

     if (e.getActionCommand() == "ABB")
      {
		FileName = "";
		tf.dispose();
        }


     //tf.dispose();
    }
    };


 public SelectArea(int hx, int hy)
	{
	super(tf,"Transition",true);
	this.setSize(260,175);
	this.setLocation(70,100);
	this.setLayout(new BorderLayout(3,3));
	this.setResizable(false);
	
	Panel p1 = new Panel();
	p1.setLayout(new BorderLayout(10,10));
	this.add(p1);

	Button Datei = new java.awt.Button("Datei");
	Datei.setBounds(0, 0, 240, 30);
	Datei.setLocation(8,35);
	Datei.setActionCommand("Datei");
	Datei.addActionListener(listener);

	sel.setName("C1");
	sel.setBounds(0, 0, 240, 25);
	sel.setLocation(10,75);
	sel.addItem("PEST ");
	sel.addItem("PEST (ohne Koordinaten)");
	sel.addItem("TESC");	
	
	Button OK = new java.awt.Button("OK");
	OK.setBounds(0, 0, 100, 35);
	OK.setLocation(150,110);
	OK.setActionCommand("OK");
	OK.addActionListener(listener);

	Button Close = new java.awt.Button("Abbrechen");
	Close.setBounds(0, 0, 100, 35);
	Close.setLocation(10,110);
	Close.setActionCommand("ABB");
	Close.addActionListener(listener);

	Panel p2 = new java.awt.Panel();
	p2.setLocation(10,40);
	p2.setSize(240,80);
	//p2.setBackground(Color.white);

	TXT.setLocation(100,200);	
	TXT.setEditable(false);
	//p1.add(L1);

	p1.add(Datei);
	p1.add(sel);
	p1.add(OK);
	p1.add(Close);
	p1.add(p2);
	p2.add(TXT);

	p1.setVisible(true);
	this.show();
	this.isModal();
	}

  public void show(int cx, int cy) {
      tf.setLocation(cx,cy);
       tf.setVisible(true);
	}

 
} // class
  
