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
	  myWindow.load("Statechart laden");
      }else if (cmd.equals("Speichern")) {
	  myWindow.save("Statechart speichern");
      }else if (cmd.equals("Neu")) {
	  myWindow.setStatechart(new absyn.Statechart(myWindow.SBDateiname),"UNBENANNT");
      }else{
	  myWindow.userMessage("GUI   : cmd:"+cmd);
	  new testClass(myWindow);
      }	 
  
}
}
