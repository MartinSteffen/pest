package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;


class GUIoptionML
implements ActionListener
{

GUIMenu myMenu;
pest myWindow;
EditorOption option;


public GUIoptionML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
  }


public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();
  
  if(cmd.equals("Editor"))
      {
          option = new EditorOption(myWindow,this);
      }
     else if (cmd.equals(option.button1.getActionCommand())){
	     myWindow.stateColor = option.getStateColor();
	     myWindow.transitionColor = option.getTransColor();
	     myWindow.connectorColor = option.getConColor();
	     option.setVisible(false);
	     option.dispose();	     
    }else if (cmd.equals(option.button2.getActionCommand())) {
	     option.setVisible(false);
	     option.dispose();
	     }


      else if (cmd.equals("")) {
      }else if (cmd.equals("")) {
      }else{  
	 myWindow.userMessage("GUI   : NOCH NICHT IMPLEMENTIERT"); 
      }	
  
}
}
