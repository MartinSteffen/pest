/**
 * Editor.java
 */

package editor;

import java.awt.*;
import java.awt.event.*;
import absyn.*;

public class Editor extends Frame
{
    private Statechart statechart;
    private String label;

    /**
     * Ruft den Editor auf, uebergibt Statechart und Name 
     */

    public Editor(Statechart st, String l)
    {
	statechart = st;
	label = l;
	mainproc();
    }

    private void mainproc()
    {
	MenuBar mb = new MenuBar();
	Menu File = new Menu("File");
	mb.add(File);
        setMenuBar(mb);
	setSize(400,300);
	show();
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                Window window = e.getWindow();
                window.dispose();
                System.exit(0);
            }
        });

    }

    /** 
     * highlight-Funktion: gibt true zurueck, falls erfolgreich
     */

    public static boolean highlight(Path obj, Color c)
    {
	return true;
    }
    public static boolean highlight(Basic_State obj, Color c)
    {
	return true;
    }
    public static boolean highlight(And_State obj, Color c)
    {
	return true;
    }
    public static boolean highlight(Or_State obj,Color c)
    {
	return true;
    }
    public static boolean highlight(Tr obj,Color c)
    {
	return true;
    }
    public static boolean highlight(Connector obj,Color c)
    {
	return true;
    }

    /**
     *listenEditor: gibt true zurueck, falls Aenderung an dem Statechart vorgenommen(seit der letzten Abfrage) 
     */

    public static boolean listenEditor()
    {
	return true;
    }

}




