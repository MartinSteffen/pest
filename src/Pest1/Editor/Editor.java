/**
 * editor.java
 *
 *
 * Created: Fri Nov 27 09:58:01 1998
 *
 * @author Software Technologie 24
 * @author Mai / Bestaendig
 * @version
 *
 * Wir erwarten :
 *<ul>
 * <li> eine syntaktisch korrekte Statechart  
 * <li> diese Statechart soll mindestens so aussehen : Statechart(null,null,null,null)  
 * <li> die Koordinaten der Statechart-Objekt muessen korrekt und relativ sein
 * </ul>
 *
 * Wir bieten :
 *<ul>
 * <li> ein grafisches Tool zum veraendern von Statecharts  
 * <li> ein separates Menuefenster  
 * <li> die Moeglichkeit alle Menues zu deaktivieren
 * <li> eine highlight-Funktion zur Simulation
 * </ul>
 */

package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import absyn.*;
import editor.desk.*;

public class Editor extends Frame {
    private static String Buttontype = "";
    private static boolean update = false;
    private static Editor menufeld = null;
    private static Editor drawfeld = null;
    private static boolean workable = true;

static Panel xpanel;
static Graphics h;

static Statechart nroot = new Statechart(null,null,null,null);

       ActionListener listener = new ActionListener() {
       public void actionPerformed(ActionEvent e){
       Buttontype = e.getActionCommand();}
    };

/**
 * Generiert einen Menu-Frame 
 * <ul>
 * <li>name   : windowname 
 * </ul>
 */

    public Editor(Statechart root,String name) {
 

    nroot = root; 
    
    new Editor(nroot,name,100,100,500,400);}


/**
 * Generiert einen Menu-Frame 
 * <ul>
 * <li>name   : windowname 
 * </ul>
 */


    public Editor (String name) {
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
 * Generiert einen drawframe
 *<ul>
 * <li>root   : Handle auf einen Statechart  
 * <li>name   : Window-Name 
 * <li>top    : top Window-Position
 * <li>left   : left Window-Position
 * <li>width  : Hoehe des Windows
 * <li>height : Breite des Windows
 * </ul>
 */

  public Editor(Statechart root,String name,int top,int left,int width,int height) {
  super(name);                  // Create the window.

// Statechart root = new Statechart(null,null,null,null);
nroot = root;  

    drawfeld = this;
    menufeld = new Editor("menue");
    this.setLocation(top,left);                // Koordinaten des Zeichenfensters setzen.
    ScrollPane pane = new ScrollPane();      // Create a ScrollPane.
    pane.setSize(width,height);                  // Specify its size.
    this.add(pane, "Center");                // Add it to the frame.
    PESTDrawDesk scribble;
    Panel panel = new Panel();
   xpanel = panel;
    scribble = new PESTDrawDesk(panel, 2400, 2400,nroot); // Create a bigger scribble area.
    pane.add(scribble);                      // Add it to the ScrollPane.

    MenuBar menubar = new MenuBar();         // Create a menubar.
    this.setMenuBar(menubar);                // Add it to the frame.
   

Menu win = new Menu("Window");            // Create a File menu.
    menubar.add(win);                       // Add to menubar.

    MenuItem w1, w2;
    win.add(w1 = new MenuItem("Loeschen"));
    win.addSeparator();                    
    win.add(w2 = new MenuItem("Schliessen"));

    w1.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { Freeit(nroot); }
    });
    w2.addActionListener(new ActionListener() {     
      public void actionPerformed(ActionEvent e) { Dispose();  }
    });




    Menu tools = new Menu("Tools");            // Create a File menu.
    menubar.add(tools);                       // Add to menubar.

    // Tool-Menue erzeugen
    MenuItem s1, s2, s3;
    tools.add(s1 = new MenuItem("T1"));
    tools.add(s2 = new MenuItem("T2"));
    tools.add(s3 = new MenuItem("T3 .."));
    // Set the window size and pop it up.

      
 s1.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ; }
    });



    this.pack();
    this.show();

  }

/**
 * Diese Methode gibt den derzeitigen Zeichenmodus zurueck 
 */

public static String Editor () {return Buttontype ;}

/**
 * Setzt den update-Listener auf true 
 * (nur fuer Editor-Events)
 */

public static void SetListen() {  update = true;
 PESTDrawDesk.addundo(nroot);}
 
private static void RemoveListen() {update = false;}

/**
 * Gibt den Status des  update-Listener zurueck
 */

public static boolean listenEditor() {
    boolean Listentemp = update;
    RemoveListen();
    return Listentemp;
    }

/**
 * loescht das Zeichenfeld und das Menuefeld 
 * <ul>
 * </ul>
 */

public static void Dispose() {
    menufeld.dispose();
    drawfeld.dispose();
    }

/**
 * Setzt den Menuestatus auf den uebergebenen Wert 
 * <ul>
 * <li>arbeit   : Menuestatus 
 * </ul>
 */

public static void work(boolean arbeit) { 
    if (arbeit == false) {Buttontype = "";}; if (menufeld != null) {menufeld.setVisible(arbeit);}
    workable = arbeit;}


/**
 * Gibt den derzeitigen Menustatus zurueck 
 * <ul>
 * </ul>
 */

public static boolean work() { return workable;}

private static void Freeit(Statechart xroot) { 	xroot.state = null;
					xroot.events = null;
					xroot.bvars = null;
   					xroot.cnames = null;
					new PESTDrawDesk();   

					}


}
// Editor 





