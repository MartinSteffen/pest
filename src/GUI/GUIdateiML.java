package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;


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
	  System.exit(0);
      }else if (cmd.equals("Oeffnen")) {
	  myWindow.load();
      }else if (cmd.equals("Speichern")) {
	  myWindow.save();
      }else if (cmd.equals("Neu")) {
	  myWindow.SBDateiname="UNBENANNT";
	  myWindow.SyntaxBaum = new absyn.Statechart(myWindow.SBDateiname);
	  myWindow.userMessage("GUI   : Neues Statechart erzeugt");
	  myMenu.updateMenu();
      }else{
	  myWindow.userMessage("GUI   : cmd:"+cmd);
	  new testClass(myWindow);
      }	 
  
}
}
