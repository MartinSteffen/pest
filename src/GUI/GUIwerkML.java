package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;
import util.PrettyPrint;
import absyn.Example;

class GUIwerkML
implements ActionListener
{

GUIMenu myMenu;
pest myWindow;

public GUIwerkML(GUIMenu myMenu,pest myWindow)
  {
  this.myMenu = myMenu;
  this.myWindow = myWindow;
  }


public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();

  myWindow.userMessage("GUI   : starte "+cmd);
  if(cmd.equals("Editor"))
      {
	  new editor.Editor(myWindow.SyntaxBaum,myWindow.SBDateiname,100,100,200,200);

      }else if (cmd.equals("SyntaxCheck")) {

	  myWindow.CheckedSC = false;
	  myWindow.checkSB();
	  
      }else if (cmd.equals("Simulator")) {
	  if (myWindow.checkSB())
	      {
		  new simu.Simu(myWindow.SyntaxBaum);
		  //myWindow.OkDialog("FEHLER","Wegen fehlender PACKAGE Anweisung in der Klasse Simu nicht implementiert");

	      }
      }else if (cmd.equals("Codegenerator")) {
	  if (myWindow.checkSB())
	      {
		  //myWindow.OkDialog("FEHLER","Wegen nicht compilierbarem Quelltext nicht implementiert");
	      	try{

		  new codegen.CodeGen(".",myWindow.SyntaxBaum);
      		}catch(codegen.CodeGenException cge)
		  {
		    myWindow.OkDialog("Fehler","Fehler bei Code-Generierung");
		  }
	      }

      }else if (cmd.equals("PrettyPrinter")) {
	 	  (new PrettyPrint()).start( Example.getExample() );
      }else{  
	 myWindow.userMessage("GUI   : NOCH NICHT IMPLEMENTIERT"); 
      }	 
  myWindow.userMessage("GUI   : ende "+cmd);
  
}
}
