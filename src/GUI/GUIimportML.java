package GUI;
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

  if(cmd.equals(""))
      {
	  
      }else if (cmd.equals("")) {
      }else{  
	 myWindow.userMessage("GUI   : IMPORT NOCH NICHT IMPLEMENTIERT"); 
      }	 
  
}
}
