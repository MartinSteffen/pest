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
     }else if(cmd.equals("Graphische Benutzerfuehrung")) {
	 GUIColor = new GUIFarbOption(myWindow,this);
      }else if (cmd.equals("SyntaxCheck")) { 
	  new check.CheckOption(myWindow,myWindow.checkConfig);
      }else if (cmd.equals("Statemate")) {
	  new stmOption(myWindow);
      }else if (cmd.equals("TESC2")) {
	  new tesc2Option(myWindow);
      }else{  
	 myWindow.userMessage("GUI   : NOCH NICHT IMPLEMENTIERT"); 
      }	
}


public void itemStateChanged(ItemEvent e)
    {
	boolean state =((CheckboxMenuItem)(e.getSource())).getState();
	String  name  = e.paramString();
	if(name.indexOf("Graphische Benutzerfuehrung")!=-1) {
	    myWindow.ctrlWin = state;
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
	}else if(name.indexOf("Debugging") != -1){
	    myWindow.debugMode = state;
// 	    if (myWindow.debugMode){
// 		myWindow.userMessage("GUI:   debugging on");
// 	    }else{
// 		myWindow.userMessage("GUI:   debugging off");
// 	    }
	}
	    
	myWindow.setVisible(true);
	//myWindow.repaint();
    }
  
}
