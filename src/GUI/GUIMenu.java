package gui;
import java.awt.*;
import java.awt.event.*;


class GUIMenu
extends MenuBar
{

pest myWindow;

    Menu M_Export,M_Werkzeuge;
MenuItem M_Speichern, M_Editor;
    //MenuItem M_Speichernu;
MenuItem M_Simulator, M_Codegen, M_SyntaxCheck,M_PrettyPrint,M_CrossRef,M_NeueKoordinaten;
GUIdateiML   ml;  
GUIwerkML    mlw; 
GUIoptionML  mle; 
GUIhilfeML   mlh; 
GUIimportML  mli; 
GUIexportML  mlex;

CheckboxMenuItem cbItem1;
CheckboxMenuItem cbItem2;

public GUIMenu(pest myWindow)
  {
  this.myWindow = myWindow;

  ml  = new GUIdateiML(this,myWindow);
  mlw = new GUIwerkML(this,myWindow);
  mle = new GUIoptionML(this,myWindow);
  mlh = new GUIhilfeML(this,myWindow);
  mli = new GUIimportML(this,myWindow);
  mlex= new GUIexportML(this,myWindow);

  Menu m = new Menu("Datei");

  addMenuItem(m,"&Neu",ml);
  addMenuItem(m,"&Oeffnen",ml);
  M_Speichern  = addMenuItem(m,"&Speichern",ml);
  //  M_Speichernu = addMenuItem(m,"Speichern &unter",ml);
  m.addSeparator();

  Menu mm = new Menu("Import");
  addMenuItem(mm,"TESC",mli);
  addMenuItem(mm,"Statemate",mli);
  m.add(mm);

  M_Export = new Menu("Export");
  addMenuItem(M_Export,"TESC",mlex);
  m.add(M_Export);

  m.addSeparator();
  addMenuItem(m,"&Beenden",ml);
  add(m);

  m = new Menu("Einstellungen");
  //addMenuItem(m,"Allgemein",mle);
  //m.addSeparator();
  Menu mf = new Menu("Farben");
  addMenuItem(mf,"Graphische Benutzerfuehrung",mle);
  addMenuItem(mf,"Editor",mle);
  m.add(mf);
  //addMenuItem(m,"Simulator",mle);
  //addMenuItem(m,"SyntaxCheck",mle);
  //addMenuItem(m,"Codegenerator",mle);
  //  addMenuItem(m,"TESC1",mle);

  addMenuItem(m,"TESC2",mle);

  addMenuItem(m,"SyntaxCheck",mle);
  addMenuItem(m,"Statemate",mle);
  addMenuItem(m,"Codegenerator",mle);
  m.addSeparator();
  cbItem1 = new CheckboxMenuItem("Graphische Benutzerfuehrung",true);
  cbItem1.addItemListener(mle);
  m.add(cbItem1);
  cbItem2 = new CheckboxMenuItem("Debugging",myWindow.debugMode);
  cbItem2.addItemListener(mle);
  m.add(cbItem2);
  //m.add(new MenuItem("Benutzerfuehrung"));
  //addMenuItem(m,"Statemate",mle);
  add(m);

  M_Werkzeuge = new Menu("Werkzeuge");
  M_Editor      = addMenuItem(M_Werkzeuge,"Editor",mlw);
  M_Simulator   = addMenuItem(M_Werkzeuge,"Simulator",mlw);
  M_Codegen     = addMenuItem(M_Werkzeuge,"Codegenerator",mlw);
  M_SyntaxCheck = addMenuItem(M_Werkzeuge,"SyntaxCheck",mlw);
  M_CrossRef    = addMenuItem(M_Werkzeuge,"Crossreferenz",mlw);
  M_NeueKoordinaten = addMenuItem(M_Werkzeuge, "Neue Koordinaten" ,mlw);
  m.addSeparator();
  M_PrettyPrint = addMenuItem(M_Werkzeuge,"PrettyPrinter",mlw);
  add(M_Werkzeuge);
  
  m = new Menu("Hilfe");
  addMenuItem(m,"Allgemein",mlh);
  addMenuItem(m,"Info",mlh);
  add(m);
  setHelpMenu(m);
  }

private MenuItem addMenuItem(Menu menu,String name,ActionListener lis)
  {
  int pos = name.indexOf('&');
  MenuShortcut shortcut = null;
  MenuItem mi;

  if(pos != -1)
	{
	if (pos < name.length() - 1)
		{
		char c = name.charAt(pos+1);
		shortcut = new MenuShortcut(Character.toLowerCase(c));
		name = name.substring(0,pos)+name.substring(pos+1);
		}
	}

  if(shortcut != null)
	{
	mi = new MenuItem(name,shortcut);
        }
	else
	{
	mi = new MenuItem(name);
	}

  mi.setActionCommand(name);
  mi.addActionListener(lis);
  menu.add(mi);
  return mi;
  } 



void updateMenu()
    {
	if (myWindow.SyntaxBaum == null)
	    {
 		M_Speichern.setEnabled(false);
		//M_Speichernu.setEnabled(false);
 		M_Export.setEnabled(false);
		M_Werkzeuge.setEnabled(false);
		M_Editor.setEnabled(false);
 		M_Codegen.setEnabled(false);
 		M_Simulator.setEnabled(false);
 		M_SyntaxCheck.setEnabled(false);
		M_CrossRef.setEnabled(false);
	    }
	else
	    {
		M_Werkzeuge.setEnabled(true);
		M_Speichern.setEnabled(true);
		M_CrossRef.setEnabled(true);
		M_PrettyPrint.setEnabled(true);
		if (myWindow.PEditor == null)
		    {
			M_Editor.setEnabled(true);
			M_Simulator.setEnabled(false);
		    }
		else 
		    {
			M_Editor.setEnabled(false);
			if (myWindow.ResultSC)
			    {
				M_Simulator.setEnabled(true);
			    }
		    }
		if (myWindow.CheckedSC)
		    {
			M_SyntaxCheck.setEnabled(false);
		    }
		else
		    {
			M_SyntaxCheck.setEnabled(true);
		    }
		if (myWindow.ResultSC)
		    {
			M_Export.setEnabled(true);
			M_Codegen.setEnabled(true);
		    }
		else
		    {
			M_Export.setEnabled(false);
			M_Codegen.setEnabled(false);
		    }
		

	    }
    }
}
