package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;
import java.io.*;
import tesc1.*;

class GUIexportML
implements ActionListener
{

GUIMenu myMenu;
pest myWindow;

public GUIexportML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
  }


public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();

  if(cmd.equals(""))
      {
      }else if (cmd.equals("TESC")) {
	  BufferedWriter stExp = myWindow.save("TESC-Datei exportieren");
	  if (stExp != null)
	      {
		  try{
		      TESCSaver ts = new TESCSaver(myWindow);
		      if (ts.saveStatechart(stExp,myWindow.SyntaxBaum))
			  {
			      myWindow.userMessage("GUI   : TESC-Export erfolgreich");
			  }
		      else
			  {
			      myWindow.userMessage("GUI   : TESC-Export fehlgeschlagen !");
			  }
		  }catch(IOException ime){
		      myWindow.OkDialog("Fehler",ime.getMessage());		     
		  }
	      }
      }else{  
	  myWindow.userMessage("GUI   : EXPORT NOCH NICHT IMPLEMENTIERT"); 
      }	 
  
}
}
