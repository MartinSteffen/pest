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
import gui.*;
import gui.popdialoge.*;

public class Editor extends Frame {
    private static String Buttontype = "";
    private static boolean update = false;
    private static Editor menufeld = null;
    private static Editor drawfeld = null;
    private static boolean workable = true;
    private static boolean drawstatus = false;
    public static double ZoomFaktor = 1;
    public static double zoomfk = 1;
    private static GUIInterface gui = null; // Referenz auf die GUI (mit NULL vorbelegen)

    static PESTDrawDesk scribble;
    ScrollPane pane = new ScrollPane();      // Create a ScrollPane.
    Panel panel = new Panel();


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
    
    new Editor(nroot,name,100,100,500,400,null);}


public Editor(Statechart root,String name,int top,int left,int width,int height) {
	nroot = root;
    new Editor(nroot,name,top,left,width,height,null); }

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
	Button Defconn = new Button("CONNECTOR");
	this.add(Defconn);
	Defconn.setActionCommand("Draw_Conn");
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

	Button setdef = new Button("Set Default");
	this.add(setdef);
	setdef.setActionCommand("Set_Default");
	setdef.addActionListener(listener);

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
 *
 *
 *<DL COMPACT>
 * 
 *<DT><STRONG>
 * STATUS.
 *</STRONG>
 * <li> Zeichnen von States funktioniert
 * <li> Zeichnen von Connectoren funktioniert
 * <li> Zeichnen von Transitionen 90 %
 * <li> Kennzeichnen von Anfangszustaenden 90 %
 * <li> Labeln von States 75 % (benoetigen nur noch das Fenster von der gui und 5 Zeilen Code)
 * <li> Labeln von Transitionen ??? (zweiter Teil kommt erst im Laufe des Tages)
 * <li> Patternmatcher : ??? (siehe oben)
 * <li> Loeschen von Komponenten ??? (s.o.)
 * <li> Selektieren von Objektgruppen ??? (s.o.)
 * <li> Selektieren von Objekten 75 % (Das Selektieren von Transitionen funktioniert noch nicht)
 * <li> highlighten 99 % (nur die Tests stehen noch aus)
 * <li> Zoomen 100 %
 * <li> Undo 100 % (50 %) (UNDO steht (100 %), funktioniert mit dem derzeitigen Status der Absyn noch nicht korrekt (50 %).
 *
 *<DT><STRONG>
 * TODO.
 * </STRONG>
 * <li> Zusammenfassen der beiden Programmteile (bis 08.01)
 * <li> Speichern von Substatecharts (Termin : Abhaengig von der GUI
 * <DT><STRONG>
 *
 *<DT><STRONG>
 * BEKANNTE FEHLER.
 * </STRONG> 
 * Fehler treten zur Zeit auf, wenn wir keine gui-Referenz bekommen, dann laeuft das Programm, gibt aber Fehlermeldungen aus. 
 * Das Beispiel der STM laesst sich nicht fehlerfrei laden.
 * Wird ein Startzustand in ein anderes Objekt umgewandelt, so bleibt es trotzdem Startzustand. 
 *
 * <DT><STRONG>
 * TEMPORÄREN FEATURES.
 * </STRONG>
 *  Zeichnen von Transitionen nur vom Anfangs- zum Endpunkt
 *  Statenames noch nicht variabel 
 * </DL COMPACT>
 */

  public Editor(Statechart root,String name,int top,int left,int width,int height,GUIInterface ngui) {
  super(name);                  // Create the window.
gui = ngui;
// Statechart root = new Statechart(null,null,null,null);
nroot = root;  

    drawfeld = this;
    drawstatus = true;
    menufeld = new Editor("menue");
    this.setLocation(top,left);                // Koordinaten des Zeichenfensters setzen.
    // ScrollPane pane = new ScrollPane();      // Create a ScrollPane.
    pane.setSize(width,height);                  // Specify its size.
    this.add(pane, "Center");                // Add it to the frame.
    
   // Panel panel = new Panel();
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
    tools.add(s1 = new MenuItem("Dialog"));
    tools.add(s2 = new MenuItem("T2"));
    tools.add(s3 = new MenuItem("T3 .."));
    // Set the window size and pop it up.

s1.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { fehlermeldung1();}
    });

   Menu zoom = new Menu("Zoom");            // Create a File menu.
    menubar.add(tools);                       // Add to menubar.

    // Tool-Menue erzeugen
    MenuItem z1, z2, z3, z4, z5, z6, z7,z10,z12;
    zoom.add(z10 = new MenuItem("Zoom In"));
    zoom.add(z12 = new MenuItem("Zoom Out"));
    zoom.addSeparator();
    zoom.add(z1 = new MenuItem("1/8 x"));
    zoom.add(z2 = new MenuItem("1/4 x"));
    zoom.add(z3 = new MenuItem("1/2 x "));
    zoom.add(z4 = new MenuItem("1 x"));
    zoom.add(z5 = new MenuItem("2 x"));
    zoom.add(z6 = new MenuItem("4 x "));
    zoom.add(z7 = new MenuItem("8 x "));

    menubar.add(zoom);                       // Add to menubar.


z1.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ZoomFaktor = 0.125; zoomfk = -8; scribble.repaint();}
    });
z2.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ZoomFaktor = 0.25; zoomfk = -4;scribble.repaint();}
    });
z3.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ZoomFaktor = 0.5; zoomfk = -2;scribble.repaint();}
    });
z4.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ZoomFaktor = 1; zoomfk = 1;	scribble.repaint();}
    });
z5.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ZoomFaktor = 2; zoomfk = 2;scribble.repaint();}
    });
z6.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ZoomFaktor = 4; zoomfk = 4; scribble.repaint();}
    });
z7.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { ZoomFaktor = 8; zoomfk = 8; scribble.repaint();}
    });

z10.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { zoomfk = zoomfk + 1; if (zoomfk == 0) {zoomfk = 2;}
			if (zoomfk > -1) {ZoomFaktor = zoomfk;} else
					{ZoomFaktor = 1/(Math.abs(zoomfk));}
					 	scribble.repaint();}

    });
z12.addActionListener(new ActionListener() {    
      public void actionPerformed(ActionEvent e) { zoomfk = zoomfk - 1; if (zoomfk == 0) {zoomfk = -2;}
			if (zoomfk > -1) {ZoomFaktor = zoomfk;} else
					{ZoomFaktor = 1/(Math.abs(zoomfk));}
						scribble.repaint();}
    });



      
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

public static void SetListen() {
// *****************************
	Relist rl = new Relist(); rl.start(nroot,null,null,nroot);  
	update = true;
//  *****************************
	gui.userMessage("Editor : Update durchgefuehrt");
 	PESTDrawDesk.addundo(nroot);
	}
 
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
    drawstatus = false;
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

public static boolean active() { return drawstatus;}

private static void Freeit(Statechart xroot) { 	xroot.state = null;
					xroot.events = null;
					xroot.bvars = null;
   					xroot.cnames = null;
					scribble.repaint();
					}

public static void newdraw() { 
			scribble.repaint();
			}



    // Vorsicht : die folgende Methode wurde nur zu Testzwecken eingebaut
    // und verursacht inkonsistente Zustaende !!!!!!!!!!!!!!!!!!!!!!!!!!!
public Editor(boolean i) {System.out.println("loesche alles im Editor"); scribble.repaint(); }

public static void fehlermeldung1()
{
  gui.OkDialog("Editor","Fehler beim Zeichnen aufgetreten");
}


}
// Editor 





