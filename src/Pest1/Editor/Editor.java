/**
 * editor.java
 *
 *
 * Created: Fri Nov 27 09:58:01 1998
 *
 */

package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import absyn.*;
import editor.desk.*;
import gui.*;
import gui.popdialoge.*;
import java.lang.*;
import tesc1.*;

/**
 * @author Michael Mai / Martin Bestaendig
 * @version 1.38 </dl>
 * <a HREF="../editor/Test/tutorial.html">Der Editor</a>
 *
 * <H3>Wir erwarten :</H3>
 *<ul>
 * <li> eine syntaktisch korrekte Statechart  
 * <li> diese Statechart soll mindestens so aussehen : Statechart(null,null,null,null)  
 * <li> die Koordinaten der Statechart-Objekt muessen korrekt und relativ sein
 * <li> alle Listen muessen vorschriftsmaessig gefuehrt sein
 * </ul>
 * </dl>
 *<H3> Wir bieten :</H3>
 *<ul>
 * <li> ein grafisches Tool zum veraendern von Statecharts  
 * <li> ein separates Menuefenster  
 * <li> die Moeglichkeit alle Menues zu deaktivieren
 * <li> eine highlight-Funktion zur Simulation
 * </ul>
 * </dl>
 *<H3> STATUS:</H3>
 * <ul>
 * <li> Zeichnen von States funktioniert
 * <li> Zeichnen von Connectoren funktioniert
 * <li> Zeichnen von Transitionen funktioniert
 * <li> Kennzeichnen von Anfangszustaenden funktioniert
 * <li> Labeln von States funktioniert
 * <li> Labeln von Transitionen funktioniert
 * <li> Patternmatcher : uebernimmt TESC 1
 * <li> Loeschen von Komponenten funktioniert
 * <li> Selektieren von Transitionen funktioniert
 * <li> highlighten funktioniert
 * <li> Zoomen funktioniert
 * <li> Kopieren von Objekten beschraenkt sich derzeit auf States (andere scheinen wenig sinnvoll)
 * <li> Verschieben von Komponenten wurde eingebunden, kolliediert aber noch mit der AbSyn.
 * <li> Ein Info-Fenster fuer die Transitionslabel wurde erstellt
 * <li> Ein Syntaxcheck fuer die Transitionsanchor wurde eingefuegt
 * <li> Undo funktioniert (In Abhaengigkeit zum AbSyn-Status)
 * </ul>
 */
public class Editor extends Frame {
    public static String Buttontype = "";
    private static boolean update = false;
    private static Editor menufeld = null;
    private static Editor drawfeld = null;
    private static boolean workable = true;
    private static boolean drawstatus = false;
    public static double ZoomFaktor = 1;
    public static double zoomfk = 1;
    public static final String LongLabel = "<LongLabel>";
    public static boolean init = true;
    public static boolean Bez = false;
    private static GUIInterface gui = null; // Referenz auf die GUI (mit NULL vorbelegen)
    
    static PESTDrawDesk scribble;
    ScrollPane pane = new ScrollPane();      // Create a ScrollPane.
    Panel panel = new Panel();
    static Frame th;
    
    static Panel xpanel;
    static Graphics h;

    static Statechart nroot = new Statechart(null,null,null,null);
    
    ActionListener listener = new ActionListener() {
	public void actionPerformed(ActionEvent e){
	    Buttontype = e.getActionCommand();
	}
    };


    /**
     * Generiert einen Menu-Frame 
     * <ul>
     * <li>name   : windowname 
     * </ul>
     */
    public Editor(Statechart root,String name) {
	nroot = root; 
	new Editor(nroot,name,100,100,500,400,null);
    }

    /**
     * Generiert einen Editorfenster ohne GUI-Unterstuetzung 
     * <ul>
     * <li>Hinweis: nur als Stub eingfuegt
     * </ul>
     */
    public Editor(Statechart root,String name,int top,int left,int width,int height) {
	nroot = root;
	new Editor(nroot,name,top,left,width,height,null); 
    }

    /**
     * Generiert einen Menu-Frame 
     * <ul>
     * <li>name   : windowname 
     * </ul>
     */
    public Editor (String name) {
	super(name);
	this.setLayout(new GridLayout(0,1,5,5));
	this.setSize(105,300);
	this.setLocation(50,100);
	this.setResizable(false);

	Button Select = new Button("Select");
	this.add(Select);
	Select.setActionCommand("Select");
	Select.addActionListener(listener);
	
	Button Info = new Button("Info");
	this.add(Info);
	Info.setActionCommand("Info");
	Info.addActionListener(listener);

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

	Button refstate = new Button("Referenz");
	this.add(refstate);
	refstate.setActionCommand("Referenz");
	refstate.addActionListener(listener);

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
    public Editor(Statechart root,String name,int top,int left,int width,int height,GUIInterface ngui) {
	super(name);                  // Create the window.
	gui = ngui;
	nroot = root;  
	SetListen(); 
	drawfeld = this;
	drawstatus = true;
	th = this;
	menufeld = new Editor("menue");
	init = true;
	this.setLocation(top,left);                // Koordinaten des Zeichenfensters setzen.
	pane.setSize(width,height);                  // Specify its size.
	xpanel = panel;
	scribble = new PESTDrawDesk(panel, 4000, 4000,nroot); // Create a bigger scribble area.
	pane.add(scribble);                      // Add it to the ScrollPane.

	this.add(pane);                // Add it to the frame.

	MenuBar menubar = new MenuBar();         // Create a menubar.
	this.setMenuBar(menubar);                // Add it to the frame.
   
	Menu win = new Menu("Fenster");            // Create a File menu.
	menubar.add(win);                       // Add to menubar.

	MenuItem w1, w2;
	win.add(w1 = new MenuItem("Loeschen"));
	win.addSeparator();                    
	win.add(w2 = new MenuItem("Beenden"));

	w1.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { Freeit(nroot); }
	});
	w2.addActionListener(new ActionListener() {     
	    public void actionPerformed(ActionEvent e) { gui.editorClosing();  }
	});

	Menu bez = new Menu("Modus");            // Create a File menu.

	// Tool-Menue erzeugen
	MenuItem m1,m2;
	bez.add(m1 = new MenuItem("Normal"));
	bez.add(m2 = new MenuItem("Bezier"));
	menubar.add(bez);                       // Add to menubar.

	m1.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { Bez = false; scribble.repaint();}
	});
	m2.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { Bez = true; scribble.repaint();}
	});
	
	// Set the window size and pop it up.

	Menu zoom = new Menu("Zoom");            // Create a File menu.

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
	    public void actionPerformed(ActionEvent e) { 
	      ZoomFaktor = 0.125; 
	      zoomfk = -8;
	      pane.remove(scribble);
	      th.remove(pane);
	      scribble = new PESTDrawDesk(panel, 500, 500,nroot); // Create a bigger scribble area.
	      pane.add(scribble);
	      th.add(pane);
	      th.pack();
	      th.show();
	      scribble.repaint();}
	});
	z2.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { 
	      ZoomFaktor = 0.25; 
	      zoomfk = -4;
	      pane.remove(scribble);
	      th.remove(pane);
	      scribble = new PESTDrawDesk(panel, 1000, 1000,nroot); // Create a bigger scribble area.
	      pane.add(scribble);
	      th.add(pane);
	      th.pack();
	      th.show();
	      scribble.repaint();}
	});
	z3.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { 
	      ZoomFaktor = 0.5;
	      zoomfk = -2;
	      pane.remove(scribble);
	      th.remove(pane);
	      scribble = new PESTDrawDesk(panel, 2000, 2000,nroot); // Create a bigger scribble area.
	      pane.add(scribble);
	      th.add(pane);
	      th.pack();
	      th.show();
	      scribble.repaint();}
	});
	z4.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { 
	      ZoomFaktor = 1; 
	      zoomfk = 1;
	      pane.remove(scribble);
	      th.remove(pane);
	      scribble = new PESTDrawDesk(panel, 4000, 4000,nroot); // Create a bigger scribble area.
	      pane.add(scribble);
	      th.add(pane);
	      th.pack();
	      th.show();
	      scribble.repaint();}
	});
	z5.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { 
	      ZoomFaktor = 2; 
	      zoomfk = 2;
	      pane.remove(scribble);
	      th.remove(pane);
	      scribble = new PESTDrawDesk(panel, 8000, 8000,nroot); // Create a bigger scribble area.
	      pane.add(scribble);
	      th.add(pane);
	      th.pack();
	      th.show();
	      scribble.repaint();}
	});
	z6.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { 
	      ZoomFaktor = 4; 
	      pane.remove(scribble);
	      th.remove(pane);
	      scribble = new PESTDrawDesk(panel, 16000, 16000,nroot); // Create a bigger scribble area.
	      pane.add(scribble);
	      th.add(pane);
	      th.pack();
	      th.show();
	      zoomfk = 4; 
	      scribble.repaint();}
	});
	z7.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) {
	      ZoomFaktor = 8; 
	      zoomfk = 8;
	      pane.remove(scribble);
	      th.remove(pane);
	      scribble = new PESTDrawDesk(panel, 32000, 32000,nroot); // Create a bigger scribble area.
	      pane.add(scribble);
	      th.add(pane);
	      th.pack();
	      th.show();
	      scribble.repaint();}
	});

	z10.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { zoomfk = zoomfk + 1; if (zoomfk == 0) {zoomfk = 2;}
	    if (zoomfk > 8) {zoomfk = 8;}
	    if (zoomfk > -1) {ZoomFaktor = zoomfk;} else
		{ZoomFaktor = 1/(Math.abs(zoomfk));}
	    pane.remove(scribble);
	    th.remove(pane);
	    scribble = new PESTDrawDesk(panel,(int) (4000*ZoomFaktor),(int) (4000*ZoomFaktor),nroot); // Create a bigger scribble area.
	    pane.add(scribble);
	    th.add(pane);
	    th.pack();
	    th.show();
	    scribble.repaint();}
	});

	z12.addActionListener(new ActionListener() {    
	    public void actionPerformed(ActionEvent e) { zoomfk = zoomfk - 1; if (zoomfk == 0) {zoomfk = -2;}
	    if (zoomfk > -1) {ZoomFaktor = zoomfk;} else
		{ZoomFaktor = 1/(Math.abs(zoomfk));}
th.remove(pane);
	    scribble = new PESTDrawDesk(panel,(int) (4000*ZoomFaktor),(int) (4000*ZoomFaktor),nroot); // Create a bigger scribble area.
	    pane.add(scribble);
	    th.add(pane);
	    th.pack();
	    th.show();
	    scribble.repaint();}
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
	gui.StateChartHasChanged();
 	scribble.addundo(nroot);
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
	if (arbeit == false) {Buttontype = ""; if (menufeld != null) {menufeld.setVisible(arbeit);}}
	else {if (menufeld != null) {menufeld.setVisible(arbeit);};}
	workable = arbeit;}

    /**
     * Gibt den derzeitigen Menustatus zurueck 
     */

    public static boolean work() { return workable;}
    
    /**
     * Gibt den drzeitigen Zeichenstatus zurueck 
     */
    protected static boolean active() { return drawstatus;}

    /**
     * loescht den aktuellen Statechart
     * <ul>
     * <li>xroot   : aktueller Statechart 
     * </ul>
     */
    private static void Freeit(Statechart xroot) { 	
	Statechart nroot = xroot;
	nroot.state = null;
	nroot.events = null;
	nroot.bvars = null;
	nroot.cnames = null;
	SetListen();
	newdraw();
    }

    /**
     * Aufruf der Drawdesk-Repaintmethode
     */
    public static void newdraw() { 
	scribble.repaint();}

    /**
     * nichtstatischer Aufruf der Drawdesk-Repaintmethode 
     */
    public Editor(boolean i) { scribble.repaint(); }

    /**
     * Aufruf der Standardzeichenfehlerbehandlung 
     */
    public static void fehlermeldung1(){
	gui.OkDialog(th,"Editor","Fehler beim Zeichnen aufgetreten");
    }
  public static void fehlermeldung2(){
    gui.OkDialog(th,"Editor","Zeichnen ausserhalb der Zeichenflaeche");
  }

    /**
     * Aufruf des Eingabefensters der GUI
     * <ul>
     * <li>a    : Standardausgabe 1 
     * <li>b    : Standardausgabe 2  
     * <li>a    : uebergebener String 
     * <li>type : zu benennendes Objekt aus der AbSyn 
     * </ul>
 
     */
    protected static String Stringeingabe(String a, String b, String c,Absyn type)
    {
	String tempname=c,tempstring;
	if (type instanceof State)
	    {
	        if (c.length() >= 3) {
		    tempstring = c.substring(0,3);
		    if (tempstring.compareTo("___")==0) {tempname = "";} else {tempname = c;};}
	    }
	if (type instanceof Tr)
	    {
		tempstring = c;
		if (tempstring.compareTo("")==0) {tempname = "";} else {tempname = c;}
	    }
	tempstring = gui.EingabeDialog(th,a,b,tempname);
	return tempstring;
    }

    /**
     * initialisierung der Namenseingabe
     * <ul>
     * <li>obj    : zu benennendes Objekt 
     * </ul>
     */
    public static String labelObject (Absyn obj)
    {
	String name,ausgabe = "",ausgabe2 = "";
	Action act = null;
	Guard gua = null;
	State stateobj;
	Tr transobj;
	TLabel tlab ;
	
	if (obj instanceof State)
	    {
		stateobj = (State) obj;
		name = stateobj.name.name;
		ausgabe = Stringeingabe("Zustand umbenennen","Neuer Zustandsname :",name,obj);
		if (ausgabe != null) {
		    if (ausgabe.length() < 1 ) {ausgabe2 = name;} else {ausgabe2 = ausgabe;}
		} else {ausgabe2 = name;}
	    }
	if (obj instanceof Tr)
	    {
		transobj = (Tr) obj;
		name = transobj.label.caption;
		ausgabe2 = Stringeingabe("Transition umbenennen","Neues Transitionslabel :",name,obj);
		if (ausgabe2 == null) {ausgabe2 = name;}
		transobj.label.caption = ausgabe2;
		TESCLoader tl = new TESCLoader(gui);
		tlab = tl.getLabel(transobj.label,nroot);
		if (tlab == null)
		    {tlab = new TLabel(new GuardEmpty(new Dummy()),new ActionEmpty(new Dummy()));}
		transobj.label = tlab;
	    }
	return ausgabe2;
    }

    public static void relabeltrans(Tr akttr)
    {
	TLabel tlab;
	TESCLoader tl = new TESCLoader(gui);
	tlab = tl.getLabel(akttr.label,nroot);
	if (tlab == null)
	    {tlab = new TLabel(new GuardEmpty(new Dummy()),new ActionEmpty(new Dummy()));
	    }
	akttr.label = tlab;
    }


    /**
     * Uebergabe der Connectorfarbe 
     */
    public static Color con_color()
    {return gui.getConnectorcolor();}

    /**
     * Uebergabe der Transitionsfarbe 
     */
    public static Color tr_color()
    {return gui.getTransitioncolor();}

    /**
     * Uebergabe der Statefarbe 
     */
    public static Color st_color()
    {return gui.getStatecolor();}

    /**
     * Standardfehlermeldung fuer Koordinatenprobleme 
     */
    public static void dislocation() {
	if (init == true)
	    {
		init = false;
		gui.OkDialog("Editor","Koordinatenproblem aufgetreten");
		gui.editorClosing();
	    }
    }
}
// Editor 
