package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;
import absyn.*;
import java.io.*;
import stm.*;
import tesc1.*;
import tesc2.*;

class GUIimportML
implements ActionListener
{

GUIMenu myMenu;
pest myWindow;
boolean hil[];

public GUIimportML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
  hil = myWindow.controlWindow.highLight;
  }


public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();

  myWindow.userMessage("GUI   : starte Import "+cmd);

  if(cmd.equals("TESC"))
      {
	  if (myWindow.isSaved())
	      {
		  BufferedReader stImp = myWindow.load("TESC-Datei importieren");
		  if (stImp != null)
		      {
			  try{
			      TESCLoader imp = new TESCLoader(myWindow);
			      Statechart Synb = imp.getStatechart(stImp);
			      if (Synb != null)
				  {
				      myWindow.userMessage("GUI   : TESC1 erfolgreich");
				      GraphOptimizer go = new GraphOptimizer(Synb,myWindow.getGraphics().getFontMetrics());
				      Synb = go.start();
				      myWindow.setStatechart(Synb,"UNBENANNT");
				      myWindow.setDirty(true);		  
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
			      else
				  {
				      myWindow.userMessage("GUI   : TESC1 Fehlgeschlagen !");
				  }
			  }catch(Exception ime){
			      myWindow.OkDialog("Fehler",ime.getMessage());		     
			  }
		      }
	      }
	  //  myWindow.OkDialog("Fehler","TESC sollte BufferedReader erwarten - nicht FileInputStream");
      
      }else if (cmd.equals("Statemate")) {
	  if(myWindow.isSaved())
	      {
		  BufferedReader stImp = myWindow.load("Statemate-Datei importieren");
		  if (stImp != null)
		      {
			  try{
			      HAImport imp = new HAImport(new BufferedReader(stImp),myWindow);
			      Statechart Synb = imp.getStatechart();
			      if (Synb == null)
				  {
				      myWindow.OkDialog("Fehler","Der Import ist fehlgeschlagen !");
				  }
			      else
				  {
				      myWindow.setStatechart(Synb,"UNBENANNT");
				      myWindow.setDirty(true);
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
			      
			      
			  }catch(Exception ime){
			      myWindow.OkDialog("Fehler",ime.getMessage());		     
			  }
 
		      }
	      }
      }else{  
	 myWindow.userMessage("GUI   : IMPORT NOCH NICHT IMPLEMENTIERT"); 
      }	
 
  myWindow.userMessage("GUI   : ende Import "+cmd);
  
}
}
