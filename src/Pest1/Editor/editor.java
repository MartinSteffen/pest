/**
 * editor.java
 *
 *
 * Created: Fri Nov 27 09:58:01 1998
 *
 * @author Software Technologie 24
 * @author Mai / Bestaendig
 * @version
 */

package Editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import Absyn.*;
import Editor.desk.*;

public class editor extends Frame {
    private static String Buttontype = "";
    private static boolean update = false;


    static Statechart nroot = new Statechart(null,null,null,null);


    ActionListener listener = new ActionListener() {
       public void actionPerformed(ActionEvent e){
       Buttontype = e.getActionCommand();}
    };

/**
 * Generates a menuframe 
 * <ul>
 * <li>name   : windowname 
 * </ul>
 */

    public editor(Statechart root,String name) {
    nroot = root; new editor(nroot,name,100,100,500,400);}

    public editor (String name) {
	super(name);
	this.setLayout(new GridLayout(0,1,5,5));
	this.setSize(105,260);
	this.setLocation(50,100);
	this.setResizable(false);
	Button Select = new Button("Select");
	this.add(Select);
	Select.setActionCommand("Select");
	Select.addActionListener(listener);
	Button State = new Button("STATE");
	this.add(State);
	State.setActionCommand("Draw_State");
	State.addActionListener(listener);
	Button Trans = new Button("TRANSITION");
	this.add(Trans);
	Trans.setActionCommand("Draw_Trans");
	Trans.addActionListener(listener);
	Button Defconn = new Button("DEF CONN");
	this.add(Defconn);
	Defconn.setActionCommand("Draw_DefConn");
	Defconn.addActionListener(listener);
	Button Par = new Button("PARALLEL");
	this.add(Par);
	Par.setActionCommand("Draw_Par");
	Par.addActionListener(listener);
	Button TLabelS = new Button("LABEL State");
	this.add(TLabelS);
	TLabelS.setActionCommand("Draw_StateLabel");
	TLabelS.addActionListener(listener);
	Button TLabelT = new Button("LABEL Trans");
	this.add(TLabelT);
	TLabelT.setActionCommand("Draw_TransLabel");
	TLabelT.addActionListener(listener);
	this.show();
    } 


/**
 * Generates a drawframe
 *<ul>
 * <li>root   : handle of Statechart  
 * <li>name   : windowname 
 * <li>top    : top windowposition
 * <li>left   : left windowposition
 * <li>width  : window width
 * <li>height : window height
 * </ul>
 */

  public editor(Statechart root,String name,int top,int left,int width,int height) {
    super(name);                  // Create the window.
    new editor("menue");
    this.setLocation(top,left);                // Koordinaten des Zeichenfensters setzen.
    ScrollPane pane = new ScrollPane();      // Create a ScrollPane.
    pane.setSize(width,height);                  // Specify its size.
    this.add(pane, "Center");                // Add it to the frame.
    PESTDrawDesk scribble;
    Panel panel = new Panel();
    scribble = new PESTDrawDesk(panel, 500, 500,root); // Create a bigger scribble area.
    pane.add(scribble);                      // Add it to the ScrollPane.

    MenuBar menubar = new MenuBar();         // Create a menubar.
    this.setMenuBar(menubar);                // Add it to the frame.
   
    Menu tools = new Menu("Tools");            // Create a File menu.
    menubar.add(tools);                       // Add to menubar.

    // Tool-Menue erzeugen
    MenuItem s1, s2, s3;
    tools.add(s1 = new MenuItem("T1"));
    tools.add(s2 = new MenuItem("T2"));
    tools.add(s3 = new MenuItem("T3 .."));
    // Set the window size and pop it up.
    this.pack();
    this.show();
  }

/**
 * This Method returns the current Drawmode 
 * of the WindowMenu
 */

public static String editor () {return Buttontype ;}

/**
 * This Method turns the update-Listener to true 
 * (only for Editor-Events)
 */

public static void SetListen() {  update = true;
 PESTDrawDesk.addundo(nroot);}
 

/**
 * This Method returns the status of the update-Listener 
 */

public static boolean ListenEditor() {
    boolean Listentemp = update;
    update = false;
    return Listentemp;
    }
}
// Editor 





