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

public GUIdateiML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
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

	      }
      }else if (cmd.equals("Speichern")) {
	  myWindow.save_sc();
      }else if (cmd.equals("Neu")) {
	  if (myWindow.isSaved())
	      {
		  myWindow.newStatechart();
	      }
      }else{
	  myWindow.userMessage("GUI   : "+cmd+" nicht vorhanden !");

      }	 
  
}
}
