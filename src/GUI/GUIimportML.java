package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;


class GUIimportML
implements ActionListener
{

GUIMenu myMenu;
pest myWindow;

public GUIimportML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
  }


public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();

  if(cmd.equals("TESC"))
      {
	  myWindow.OkDialog("Fehler","TESC sollte BufferedReader erwarten - nicht FileInputStream");
      
      }else if (cmd.equals("Statemate")) {
	  myWindow.OkDialog("Fehler","Statemate hat falsche Packageanweisung");
      }else{  
	 myWindow.userMessage("GUI   : IMPORT NOCH NICHT IMPLEMENTIERT"); 
      }	 
  
}
}
