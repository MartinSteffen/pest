package GUI;
import java.awt.*;
import java.awt.event.*;


class GUIMenu
extends MenuBar
{

pest myWindow;

    Menu M_Export;
MenuItem M_Speichern, M_Speichernu, M_Editor;
MenuItem M_Simulator, M_Codegen, M_SyntaxCheck,M_PrettyPrint;


public GUIMenu(pest myWindow)
  {
  this.myWindow = myWindow;

  GUIdateiML   ml  = new GUIdateiML(this,myWindow);
  GUIwerkML    mlw = new GUIwerkML(this,myWindow);
  GUIoptionML  mle = new GUIoptionML(this,myWindow);
  GUIhilfeML   mlh = new GUIhilfeML(this,myWindow);
  GUIimportML  mli = new GUIimportML(this,myWindow);
  GUIexportML  mlex= new GUIexportML(this,myWindow);


  Menu m = new Menu("Datei");

  addMenuItem(m,"&Neu",ml);
  addMenuItem(m,"&Oeffnen",ml);
  M_Speichern  = addMenuItem(m,"&Speichern",ml);
  M_Speichernu = addMenuItem(m,"Speichern &unter",ml);
  m.addSeparator();

  Menu mm = new Menu("Import");
  addMenuItem(mm,"TESC",mli);
  addMenuItem(mm,"Statemate",mli);
  m.add(mm);

  M_Export = new Menu("Export");
  addMenuItem(M_Export,"TESC",mlex);
  addMenuItem(M_Export,"Statemate",mlex);
  m.add(M_Export);

  m.addSeparator();
  addMenuItem(m,"&Beenden",ml);
  add(m);

  m = new Menu("Einstellungen");
  addMenuItem(m,"Allgemein",mle);
  m.addSeparator();
  addMenuItem(m,"Editor",mle);
  addMenuItem(m,"Simulator",mle);
  addMenuItem(m,"SyntaxCheck",mle);
  addMenuItem(m,"Codegenerator",mle);
  addMenuItem(m,"TESC",mle);
  addMenuItem(m,"Statemate",mle);
  add(m);

  m = new Menu("Werkzeuge");
  M_Editor      = addMenuItem(m,"Editor",mlw);
  M_Simulator   = addMenuItem(m,"Simulator",mlw);
  M_Codegen     = addMenuItem(m,"Codegenerator",mlw);
  M_SyntaxCheck = addMenuItem(m,"SyntaxCheck",mlw);
  m.addSeparator();
  M_PrettyPrint = addMenuItem(m,"PrettyPrinter",mlw);
  add(m);

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
		M_Speichernu.setEnabled(false);
		M_Export.setEnabled(false);
		M_Editor.setEnabled(false);
		M_Codegen.setEnabled(false);
		M_Simulator.setEnabled(false);
		M_SyntaxCheck.setEnabled(false);
	    }
	else
	    {
		
		M_Speichern.setEnabled(true);
		M_Speichernu.setEnabled(true);
		M_Editor.setEnabled(true);
		M_Export.setEnabled(true);
		M_Codegen.setEnabled(true);
		M_Simulator.setEnabled(true);
		M_SyntaxCheck.setEnabled(true);
	    }
    }
}
