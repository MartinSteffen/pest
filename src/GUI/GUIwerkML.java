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
	myWindow.PEditor = new editor.Editor(myWindow.SyntaxBaum,myWindow.SBDateiname,100,100,200,200,myWindow);

      }else if (cmd.equals("SyntaxCheck")) {

	  myWindow.CheckedSC = false;
	  myWindow.checkSB();
	  
      }else if (cmd.equals("Simulator")) {
	  if (myWindow.checkSB())
	      {
		  new simu.Simu(myWindow.SyntaxBaum,myWindow.PEditor,myWindow);
		//  myWindow.OkDialog("FEHLER","Nicht compilierbar");

	      }
      }else if (cmd.equals("Codegenerator")) {
	  if (myWindow.checkSB())
	      {
		  //myWindow.OkDialog("FEHLER","Wegen nicht compilierbarem Quelltext nicht implementiert");
	      	try{

		  new codegen.CodeGen(".",myWindow.SyntaxBaum);
      		}catch(codegen.CodeGenException cge)
		  {
		    myWindow.OkDialog("Fehler","Fehler bei der Code-Generierung");
		  }
	      }

      }else if (cmd.equals("PrettyPrinter")) {
	 	  (new PrettyPrint()).start( myWindow.SyntaxBaum );
      }else{  
	 myWindow.userMessage("GUI   : NOCH NICHT IMPLEMENTIERT"); 
      }	 
  myWindow.userMessage("GUI   : ende "+cmd);
  
}
}
