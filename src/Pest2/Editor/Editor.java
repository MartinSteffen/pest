/**
 * Editor.java
 */

package Editor;

import java.awt.*;
import java.awt.event.*;
import Absyn.*;

public class Editor extends Frame
{
    private Statechart statechart;
    private String label;

    /**
     * public Editor(Statechart st, String l)
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
     * public static boolean highlight(Path obj)
     */

    public static boolean highlight(Path obj)
    {
	return true;
    }

}




