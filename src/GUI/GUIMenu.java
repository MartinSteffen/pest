import java.awt.*;
import java.awt.event.*;


class GUIMenu
extends MenuBar
{
pest myWindow;

public GUIMenu(pest myWindow)
  {
  this.myWindow = myWindow;

  GUIMenuListener ml= new GUIMenuListener(this);

  Menu m = new Menu("Datei");

  addMenuItem(m,"&Neu",ml);
  addMenuItem(m,"&Oeffnen",ml);
  addMenuItem(m,"&Speichern",ml);
  addMenuItem(m,"Speichern &unter",ml);
  m.addSeparator();
  addMenuItem(m,"&Beenden",ml);
  add(m);

  m = new Menu("Einstellungen");
  addMenuItem(m,"Allgemein",ml);
  m.addSeparator();
  addMenuItem(m,"-Editor",ml);
  addMenuItem(m,"-Simulator",ml);
  addMenuItem(m,"-SyntaxCheck",ml);
  addMenuItem(m,"-Codegenerator",ml);
  addMenuItem(m,"-TESC",ml);
  addMenuItem(m,"-Statemate",ml);
  add(m);

  m = new Menu("Werkzeuge");
  addMenuItem(m,"Editor",ml);
  addMenuItem(m,"Simulator",ml);
  addMenuItem(m,"Codegenerator",ml);
  addMenuItem(m,"SyntaxCheck",ml);
  add(m);

  m = new Menu("Hilfe");
  addMenuItem(m,"Allgemein",ml);
  addMenuItem(m,"Info",ml);
  add(m);
  setHelpMenu(m);
  }

private void addMenuItem(Menu menu,String name,ActionListener lis)
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
  } 

class GUIMenuListener
implements ActionListener
{
GUIMenu myMenu;

public GUIMenuListener(GUIMenu myMenu)
  {
  this.myMenu = myMenu;
  }

public void actionPerformed( ActionEvent e ) {
  String cmd = e.getActionCommand();

  if(cmd.equals("Beenden"))
  {
	System.exit(0);
  }else if (cmd.equals("Oeffnen")) {
      myWindow.load();
  }else if (cmd.equals("Speichern")) {
      myWindow.save();
  }else if (cmd.equals("Neu")) {
      myWindow.Initialize();
  }else if (cmd.equals("Editor")) {
      new testClass(myWindow);
  }

  }
}
}
  
