package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;
import java.awt.*;


class GUIoptionML
implements ActionListener,ItemListener
{

GUIMenu myMenu;
pest myWindow;
EditorOption EdColor;
GUIFarbOption GUIColor;
boolean isEdOpOpen = false;
boolean isGUIOPOpen = false;


public GUIoptionML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
  }


public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();
  
    if(cmd.equals("Editor"))
	{
	    EdColor = new EditorOption(myWindow,this);
     }else if(cmd.equals("Graphische Benutzerfuehrung"))
	 {
	     GUIColor = new GUIFarbOption(myWindow,this);
      }else if (cmd.equals("SyntaxCheck")) { 
	  //new check.CheckOption(myWindow,myWindow.checkConfig);
      }else if (cmd.equals("Statemate")) {
	  new stmOption(myWindow);
      }else{  
	 myWindow.userMessage("GUI   : NOCH NICHT IMPLEMENTIERT"); 
      }	
}


public void itemStateChanged(ItemEvent e)
    {
	myWindow.ctrlWin =((CheckboxMenuItem)(e.getSource())).getState();
	if (myWindow.ctrlWin)
	    {
		myWindow.MsgWindow.setRows(4);
		myWindow.add("South",myWindow.MsgWindow);
		myWindow.add("Center",myWindow.controlWindow);
	    }
	else
	    {
		myWindow.MsgWindow.setRows(50);
		myWindow.remove(myWindow.controlWindow);
		myWindow.add("Center",myWindow.MsgWindow);
		//	myWindow.add("South",myWindow.controlWindow);
	    }
	myWindow.setVisible(true);
	//myWindow.repaint();
    }
  
}
