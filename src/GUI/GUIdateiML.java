package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;
import java.io.*;

class GUIdateiML
implements ActionListener
{

GUIMenu myMenu;
pest myWindow;
boolean hil[];

public GUIdateiML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
  hil = myWindow.controlWindow.highLight;      
  }


public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();

  if(cmd.equals("Beenden"))
      {
	  if(myWindow.isSaved())
	      {
		  myWindow.rememberConfig();
		  System.exit(0);
	      }
      }else if (cmd.equals("Oeffnen")) {
	  if(myWindow.isSaved())
	      {
		  myWindow.load_sc();

		  hil[4] = true;
		  if (myWindow.PEditor == null)
		      {
			  hil[5] = true;
		      }
		  hil[6] = false;
		  hil[7] = true;
		  hil[8] = false;
		  hil[9] = false;
		  hil[10] = false;
		  myWindow.controlWindow.repaint();
	      }
      }else if (cmd.equals("Speichern")) {
	  myWindow.save_sc();
      }else if (cmd.equals("Neu")) {
	  if (myWindow.isSaved())
	      {
		  myWindow.setStatechart(new absyn.Statechart(myWindow.SBDateiname),"UNBENANNT");
		  hil[4] = true;
		  if (myWindow.PEditor == null)
		      {
			  hil[5] = true;
		      }
		  hil[6] = false;
		  hil[7] = true;
		  hil[8] = false;
		  hil[9] = false;
		  hil[10] = false;
		  myWindow.controlWindow.repaint();
	      }
      }else{
	  myWindow.userMessage("GUI   : "+cmd+" nicht vorhanden !");

      }	 
  
}
}
