package gui;
// Aufgrund doppelter Klassen in "java.awt.event" und "Absyn"
// empfiehlt es sich hier NICHT "Absyn" zu importieren !

import java.awt.event.*;
import java.awt.*;
import util.PrettyPrint;
import absyn.Example;
import absyn.*;
import tesc2.*;

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
	  myWindow.startSimulator();



      }else if (cmd.equals("Codegenerator")) {
	  GUIcodegenML cml = new GUIcodegenML(myWindow,this);
	  String label = cml.getLabel();
	  if (label.equals("Aktuelles Statechart"))
	      {
		  if (myWindow.SyntaxBaum != null)
		      {
			  if (myWindow.checkSB(false))
			      {
				  try{
				      // 			      myWindow.fDialog.setMode(FileDialog.SAVE);
				      // 			      myWindow.fDialog.setTitle("Generierten Code speichern");
				      // 			      myWindow.fDialog.setFile("<OUTFILES>");			
				      // 			      myWindow.fDialog.show();
				      // 			      String path = myWindow.fDialog.getDirectory();
				      // 			      //		        String path = myWindow.EingabeDialog("","Geben Sie bitte einen Pfad an",myWindow.SBPfad);
				      // 			      System.out.println(path);
				      // 			      if (path != null)
				      // 				  {
				      new codegen.CodeGen(myWindow.SyntaxBaum,myWindow.codeGenConfig);
				      // 				  }
				      // 			      myWindow.fDialog.dispose();
				      
				  }catch(codegen.CodeGenException cge)
				      {
					  myWindow.OkDialog("Fehler",cge.getMessage());
				      }	      
			      }
		      }
		  else
		      {
			  myWindow.OkDialog("Fehler","Es ist noch kein Statechart geladen");
		      }
	      }else
		  {
		      //		      Statechart sc_save = myWindow.SyntaxBaum;
		      Statechart sc1 = null;
		      Statechart sc2 = null;
		      if (myWindow.isSaved())
			  {
			      myWindow.OkDialog("Codegenerator","Bitte Laden Sie das erste Statechart");
			      myWindow.load_sc();
			      myWindow.checkSB(true);		  
			      sc1 = myWindow.SyntaxBaum;
			      int cont = 1;
			      while ((cont == 1) && (myWindow.ResultSC == false))
				  {
				      cont = myWindow.YesNoDialog("Codegenerator","Das ausgewählte Statechart ist fehlerhaft. Wollen Sie es erneut versuchen ?");
				      if (cont == 1)
					  {
					      myWindow.load_sc();
					      myWindow.checkSB(true);		  
					      sc1 = myWindow.SyntaxBaum;
					  }
				      else
					  {
					      cont = myWindow.OkDialog("Codegenerator","Sie haben die Codegenerierung abgebrochen.");
					  }
				  }
			      
			      if (cont != 0)
				  {
				      myWindow.OkDialog("Codegenerator","Bitte Laden Sie das zweite Statechart");
				      myWindow.load_sc();
				      myWindow.checkSB(true);		  
				      sc2 = myWindow.SyntaxBaum;
				      cont = 1;
				      while ((cont == 1) && (myWindow.ResultSC == false))
					  {
					      cont = myWindow.YesNoDialog("Codegenerator","Das ausgewählte Statechart ist fehlerhaft. Wollen Sie es erneut versuchen ?");
					      if (cont == 1)
						  {
						      myWindow.load_sc();
						      myWindow.checkSB(true);		  
						      sc2 = myWindow.SyntaxBaum;
						  }
					      else
						  {
						      cont = myWindow.OkDialog("Codegenerator","Sie haben die Codegenerierung abgebrochen.");
						      
						  }
					  }
				      
				  }
			      
			      if ((cont != 0) && (sc2 != null) && (sc1 != null))
				  {
				      try{
					  // 			      myWindow.fDialog.setMode(FileDialog.SAVE);
					  // 			      myWindow.fDialog.setTitle("Generierten Code speichern");
					  // 			      myWindow.fDialog.setFile("<OUTFILES>");			
					  // 			      myWindow.fDialog.show();
					  // 			      String path = myWindow.fDialog.getDirectory();
					  // 			      //		        String path = myWindow.EingabeDialog("","Geben Sie bitte einen Pfad an",myWindow.SBPfad);
					  // 			      System.out.println(path);
					  // 			      if (path != null)
					  // 				  {
					  new codegen.CodeGen(sc1,sc2,myWindow.codeGenConfig);
					  // 				  }
					  // 			      myWindow.fDialog.dispose();
					  
				      }catch(codegen.CodeGenException cge)
					  {
					      myWindow.OkDialog("Fehler",cge.getMessage());
					  }
				  }
			      			      
			  }
		  }
		      

	  }else if (cmd.equals("Crossreferenz")) {
	      check.Crossreference cr = new check.Crossreference(myWindow,  myWindow.PEditor , myWindow.checkConfig);
	      cr.report(myWindow.SyntaxBaum);
      } else if (cmd.equals("Neue Koordinaten")){
	  if (myWindow.isSaved())
	      {
		  try
		      {
			  GraphOptimizer go = new GraphOptimizer(myWindow.SyntaxBaum,myWindow.getGraphics().getFontMetrics());
			  myWindow.SyntaxBaum = go.start(myWindow.layoutAlgorithm);
			  if (myWindow.PEditor != null)
			      {
				  myWindow.exlis.windowClosing(new WindowEvent(myWindow,WindowEvent.WINDOW_CLOSING));
				  myWindow.startEditor();
			      }
			  myWindow.setDirty(true);
		      }
		  catch (Exception ex)
		      {
			  myWindow.OkDialog("Fehler","Es konnten keine neuen Koordinaten berechnet werden");
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
