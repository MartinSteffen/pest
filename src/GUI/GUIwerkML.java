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
	  myWindow.startEditor();
      }else if (cmd.equals("SyntaxCheck")) {

	  myWindow.checkSB(true);
	  
      }else if (cmd.equals("Simulator")) {
	  if (myWindow.checkSB(false))
	      {
		  if (myWindow.PEditor != null)
		      {
			  myWindow.PEditor.work(false);   // �nderungen verbieten
			  simu.Simu Sim = new simu.Simu(myWindow.SyntaxBaum,myWindow.PEditor,myWindow);
// 			  if (Sim instanceof Thread)
// 			      {
// 				  Sim.start();
// 				  while(Sim.isAlive())
// 				      {
// 					  // warten auf Ende !
// 				      }
// 			      }

			  myWindow.PEditor.work(true);    // �nderungen zulassen
		      }
		  else
		      {
			  myWindow.OkDialog("Fehler","Zur Simulation mu� ein Editor ge�ffnet sein");
		      }
		//  myWindow.OkDialog("FEHLER","Nicht compilierbar");

	      }
      }else if (cmd.equals("Codegenerator")) {
	  if (myWindow.checkSB(false))
	      {
		  //myWindow.OkDialog("FEHLER","Wegen nicht compilierbarem Quelltext nicht implementiert");
		  try{
		    
		    new codegen.CodeGen(myWindow.SBPfad,myWindow.SyntaxBaum);
      		}catch(codegen.CodeGenException cge)
		    {
		      myWindow.OkDialog("Fehler","Fehler bei der Code-Generierung");
		  }
	      }

      }else if (cmd.equals("Crossreferenz")) {
	  check.Crossreference cr = new check.Crossreference(myWindow,  myWindow.PEditor);
	  cr.report(myWindow.SyntaxBaum);
      }else if (cmd.equals("PrettyPrinter")) {
	  (new PrettyPrint()).start( myWindow.SyntaxBaum );
      }else{  
	 myWindow.userMessage("GUI   : NOCH NICHT IMPLEMENTIERT"); 
      }	 
  myWindow.userMessage("GUI   : ende "+cmd);
  
}
}
