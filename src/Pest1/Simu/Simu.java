package simu;

import java.util.*;
import java.io.*;
import java.awt.*;
 
import absyn.*;
import editor.*;
import gui.*;
import util.*;

/**Hauptklasse der Simulation<p>
 * Der Start des Simulators erfolgt beim Aufruf des Kontruktors. Dabei wird
 * dann ein UI-Fenster erzeugt.<p>
 * <STRONG> Garantie. </STRONG> Wir garantieren, dass die von unseren
 * Modulen erzeugten Statecharts folgende Eingenschaften haben:
 *
 * <ul>
 * <li> Wir erzeugen keine Statecharts.
 * </ul>
 *
 * Damit ist es nicht notwendig, folgende Checks an unsere Statecharts
 * anzuwenden:
 *
 * <ul>
 * <li> ()
 * </ul>
 *
 * <STRONG> Anforderungen. </STRONG> Wir verlassen uns darauf, dass die
 * Statecharts, die uns uebergeben werden, folgende Eigenschaften haben:
 *
 * <ul>
 * <li> Die Statecharts sind vollstaendig entsprechend der Absyn
 * <li> zusammengesetzt ( insbesondere die Labels ).
 * <li>
 * <li> Transitionen, die mittels mehrerer Connectoren zusammengesetzt
 * <li> sind, muessen ( nicht nur zur Laufzeit ) zykelfrei sein.
 * <li>
 * <li> Statechart muss <strong>dummyfrei</strong> sein
 * <li>  
 * </ul>
 *
 * die mit folgenden Checks ueberprueft werden koennen:
 *
 * <ul>
 * <li> SyntaxCheck-Aufruf:
 * <ul>
 * <li> Initialisierung:   ModelCheck mc = new ModelCheck(GUI_Referenz)
 * <li> Aufruf des Checks: boolean = mc.checkModel(Statechart)
 * </ul>
 * <li> Der Aufruf erfolgt allerdings nicht direkt von uns, sondern wird <b>vor</b> unserem
 * Aufruf von der GUI durchgefuehrt.
 * </ul>
 *
 *
 * <DL COMPACT>
 *
 * <DT><STRONG>
 * STATUS</strong><p>
 * Der Simulator ist noch nicht ablauffaehig, da alle Teilstuecke eng miteinander verzahnt sind und so
 * die fehlenden Features einen Ablauf verhindern.<p>
 *
 * <DT><STRONG>
 * TODO: </strong>
 * <ul>
 * <li> Eingaben in den Fenstern fuer BVars und Events werden nicht ausgewertet (Deadline: 18.01.)
 * </ul>
 * <p> 
 * <DT><STRONG>
 * BEKANNTE FEHLER:</strong>
 * <ul>
 * <li> Die clone()-Funktion der Absyn erzeugt Fehlermeldungen, die einen fehlerfreien Lauf bisher unmoeglich machen.
 * <li> Die Buttons im Simulatorfenster funktionieren noch nicht wie gewuenscht (deswegen disabled)
 * <li> Bei dem Scrollen in der Event- bzw. BvarListe treten Fehler bei den Checkboxen auf.
 * </ul>
 * <p>
 * <DT><STRONG>
 * TEMPORÄRE FEATURES:</strong>
 * <ul>
 * <li> Ueber die GUI:
 * <ul>
 * <li> Statusausgaben ueber Fehler beim Erzeugen diverser Clones
 * <li> Statusausgaben zum Ueberpruefen diverser Features (z.B. welche Objekte gehighlighted werden)
 * <li> Debugmodus: Wird durch Setzen der "debug"-Variablen eingeschaltet
 * </ul>
 * </ul>
 * <p>
 * Ein Abstellen der temporaeren Features ist bisher nur durch Auskommentieren im Sourcecode moeglich, in der
 * naechsten Version wird es eine DEBUG-Einstellung geben, die ein An- und Ausstellen ermoeglichen.
 * Seit dem 11.01.1999 gestattet eine "debug"-Variable das Ausfuehren des Programmes im DEBUG-Modus.
 *
 * @version V3.02 vom 17.01.1999
 * @version V3.05 vom 30.01.1999
 */

public class Simu extends Frame implements java.awt.event.ActionListener, java.awt.event.ItemListener, java.awt.event.MouseListener {
	private ListNonDet2 LND;
	int selectNonDetChoice;
	Trace td;
	Monitor handleMonitor;
	Guard Breakpoint;
	BreakPointHandler hBP;
	boolean gotBreakPoint = false;
	ListFrame LF1;
	ListFrame LF2;
	private Statechart SDaten = null;
	private Vector EventList = new Vector();		// Vektoren, die die Listen darstellen, die wir
	Vector InList = new Vector();			// aufbauen wollen
	Vector EnteredList = new Vector();
	Vector ExitedList = new Vector();
	private Vector possTransList = new Vector();	// Transitionen, die moeglicherweise ausgefuehrt werden
	Vector transList = new Vector();				// die aufgebaute "abstrakte" Transitionsliste
	Vector toDoTransList = new Vector();			// Transitionen, die keinen Nichtdeterminismus aufweisen
	GUIInterface gui = null; 				// Referenz auf die GUI (mit NULL vorbelegen)
	private Editor edit = null;
	Vector bvList = new Vector();
	Vector seList = new Vector();
	int NonDetChoice = 0;					// Modus des Beseitigungsalgorithmus fuer den Nichtdeterminismus
	int simuCount = 0;
	boolean aSyncOn = false;				// Modus Synchroner bzw. Asynchroner Lauf des Simulators
	private Button ivjButton1 = null;
	private Button ivjButton2 = null;
	private Button ivjButton3 = null;
	private Button ivjButton4 = null;
	private Checkbox ivjCheckbox1 = null;
	private Checkbox ivjCheckbox2 = null;
	private Panel ivjContentsPane = null;
	private Frame ivjFrame1 = null;
	private TextField ivjTextField1 = null;
	public boolean debug = true;	// = debug1 !
	public boolean debug1 = true;	// fatal error, nullPointerExeptions
	public boolean debug2 = false;	// Statusmeldungen
	public boolean debug2a = false;	// Statusmeldungen für selten genutze Methoden
	public boolean debug2b = false;	// Statusmeldungen für normal gentzte Methoden
	public boolean debug2c = false;	// Statusmeldungen für häufig gentze Methoden
	public boolean debug3 = false;	// Funktionsnachweise
	public boolean debug4 = false;	// Ausgabe von Countern und Variableninhalte
	public boolean debug5 = false;	// -----
	public boolean debug_makeRealTransList = true;
	public boolean debug_makePossTransList = true;
	public boolean debug_make_action = true;	//
	public boolean debug_get_guard = false;
	private int debug_gg_c = 0;					// Debug-Counter für Methode get_guard()
	private boolean debug_step = true;
	private boolean debug_removePathInList = false;
	private boolean debug_removePathInListLinear = false;
	private boolean debug_killLevelConflicts = true;
	private boolean debug_executeTransList = true;
	private boolean debug_copyEnteredToIn = false;
	private boolean debug_inMethod = false;
	private boolean debug_inMethodInit = false;
	private boolean debug_firstStep = false;
	private boolean debug_resetSimu = false;
	private boolean debug_resetEventList = false;
	private boolean debug_resetBVarList = false;
	private boolean debug_makeTrans = true;
	// redundant
	public int makeTabCounter = 0;
	private CheckboxMenuItem ivjCheckboxMenuItem1 = null;
	private CheckboxMenuItem ivjCheckboxMenuItem2 = null;
	private CheckboxMenuItem ivjCheckboxMenuItem3 = null;
	private CheckboxMenuItem ivjCheckboxMenuItem4 = null;
	private MenuBar ivjFrame1MenuBar = null;
	private Menu ivjMenu1 = null;
	private Menu ivjMenu2 = null;
	private Menu ivjMenu3 = null;
	private MenuItem ivjMenuItem1 = null;
	private MenuItem ivjMenuItem2 = null;
	private MenuItem ivjMenuItem3 = null;
	private MenuItem ivjMenuItem4 = null;
	private MenuItem ivjMenuSeparator2 = null;
	private MenuItem ivjMenuSeparator3 = null;
	private FileDialog ivjFileDialog1 = null;
	private Menu ivjMenu4 = null;
	private CheckboxMenuItem ivjCheckboxMenuItem5 = null;
	private Menu ivjMenu5 = null;
	private MenuItem ivjMenuItem5 = null;
	private MenuItem ivjMenuSeparator1 = null;
	private CheckboxMenuItem ivjCheckboxMenuItem6 = null;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public Simu() {
	super();
	initialize();
}
/**
 * Simulator: 3. gewuenschter, neuer Main-Konstruktor<p>
 * Uebergabe einer Statechart, eines Editorobjektes und einer GUI-
 * schnittstelle, um (erstmal hauptsaechlich zu Debuggingzwecken) Ausgaben
 * im GUI-Fenster machen zu koennen;<p> 
 * Erzeugt auch alle noetigen Fenster um Ein- und Ausgaben des Benutzers
 * registrieren zu koennen;
 *
 * Benutzt globale Objekte		: gui, SDaten
 * Wird aufgerufen von			: GUI
 * Ruft auf:					: makeTab(State, Path), initialize(), groesse()
 *
 * @param Daten absyn.Statechart
 * @param eEdit editor::Editor
 * @param igui gui::GUIInterface
 * @return
 * @version V1.00 vom 14.01.1999
 * @version V4.01 vom 30.01.1999
 */
public Simu(Statechart Daten, Editor eEdit, GUIInterface igui) {
	gui = igui;
	edit = eEdit;
	Path weg;
	int bListSize, eListSize;
	SDaten = Daten;
	highlightObject ho;

	setDebugLevel(gui.isDebug());
	
	if (debug1) {
		System.out.println("Simulator aufgerufen !!");
	}
	
	// SDaten = example();					// Eigenes Beispiel

	bListSize = groesse(SDaten.bvars);
	eListSize = groesse(SDaten.events);
	weg = null;

	//try {
	//	SDaten = ((Statechart)(Daten.clone()));
	//} 
	//catch (CloneNotSupportedException cnse) {
	//}
	if (SDaten != null) {
		guiOutput("Simu: nichtleere Statechart angekommen !");
		initialize();
		getFrame1().show();
		getFrame1().setSize(538, 280);
		getFrame1().repaint();
		getFrame1().setLocation(10, 10);
		getButton1().setSize(211, 26);
		getButton1().repaint();

		bvList = makeTab(SDaten.bvars);				// Initialisiert die BVarListe (bvList)
		seList = makeTab(SDaten.events);			// Initialisiert die EventListe (evList)
													// bvList und seList sind eventuell leere Vektoren.
		firstStep();
			
		simuCount++;

		//guiOutput("Simu: Bis CheckPoint 1 gekommen !");
		//bvList = makeTab(SDaten.bvars); // Initialisiert die BVarListe (bvList) - nur Debugging, sonst in firstStep()
		//guiOutput("Simu: Bis CheckPoint 2 gekommen !");
		//seList = makeTab(SDaten.events); // Initialisiert die EventListe (evList) - nur Debugging, sonst in firstStep()

		makeTab(SDaten.state, weg);			// Hier wird die 'abstrakte' Transitionsliste erstellt.
		
		//try {
		if (debug == true) {
			System.out.println("Listframerahmen Checkpoint 1 erreicht !");
		}
		if (bListSize != 0) {
			LF1 = new ListFrame(bvList, gui.isDebug(), this, bListSize);
			LF1.pack();
			LF1.setSize(538, 399);
			LF1.setLocation(700, 50);
			LF1.setTitle("BVAR-Liste");
			LF1.show();
		}
		if (debug == true) {
			System.out.println("Listframerahmen Checkpoint 2 erreicht !");
		}
		if (eListSize != 0) {
			LF2 = new ListFrame(seList, gui.isDebug(), this, eListSize);
			LF2.pack();
			LF2.setSize(538, 399);
			LF2.setTitle("Event-Liste");
			LF2.setLocation(700, 400);
			LF2.show();
		}

		if (debug == true) {
			System.out.println("Listframerahmen Checkpoint 3 erreicht !");
		}

		if (debug == true) {
			System.out.println("Listframerahmen Checkpoint 4 erreicht !");
		}
		

		if (debug == true) {
			System.out.println("Initialisierung FERTIG !");
		}

		ho = new highlightObject(true);
		selectHighlightObject();
		ho = new highlightObject();	
	}
	else {
		guiOutput("Simu: Keine Statechart angekommen ! Objekt war null");
		System.out.println("Keine Statechart angekommen !");
	}
}
/**
 * Method to handle events for the ActionListener interface.
 * @param e java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void actionPerformed(java.awt.event.ActionEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getMenuItem4()) ) {
		connEtoC7();
	}
	if ((e.getSource() == getMenuItem3()) ) {
		connEtoC8();
	}
	if ((e.getSource() == getMenuItem5()) ) {
		connEtoC12();
	}
	if ((e.getSource() == getMenuItem2()) ) {
		connEtoC15();
	}
	if ((e.getSource() == getMenuItem1()) ) {
		connEtoC16();
	}
	if ((e.getSource() == getMenuItem2()) ) {
		connEtoC17();
	}
	// user code begin {2}
	// user code end
}
/**
 *
 * Haengt an einen uebergebenen ActionBlock die uebergebenen Action als 
 * neuen ActionBlock an  
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: append(), returnNewAction()
 * Ruft auf					: append()
 *
 * @return absyn.Aseq
 * @param arg1 absyn.ActionBlock
 * @param arg2 absyn.Action
 * @version V1 vom 14.01.1999
 */
private Aseq append(ActionBlock arg2, Action arg1) {
	if(arg2.aseq.tail != null) {
		return new Aseq(arg2.aseq.head, append(new ActionBlock(arg2.aseq.tail), arg1));
	    } 
	else 
	    return new Aseq(arg2.aseq.head, new Aseq(arg1, null));

}
/**
 * MouseClick-Listener fuer den Beenden-Button
 */
public void button1_MouseClicked() {
	if (LF1 instanceof ListFrame) {
		LF1.dispose();
	}
	if (LF2 instanceof ListFrame) {
		LF2.dispose();
	}
	if (handleMonitor instanceof Monitor){
		handleMonitor.getFrame1().dispose();
	}
	if (td instanceof Trace) {
		td.getFrame1().dispose();
	}
	this.getFrame1().dispose();
	gui.simuExit();
	return;
}
/**
 * Comment
 */
public void button2_MouseClicked() {
	//if (simuCount < 1) {
	//	this.firstStep();
	//	simuCount++;
	//}
	//else {
		this.makeStep();
	//}	
	return;
}
/**
 * Button "makeNStep" wurde gedrückt 
 */
public void button3_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	int i;
	
	i = Integer.parseInt(((getTextField1()).getText()));
	// System.out.println("button3_mouseClicked(): Schritte zu machen --------------------------------------: "+i);
	makeNStep(i);
	return;
}
/**
 * Comment
 */
public void button4_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	guiOutput("SIMU: Reset-Button gedrueckt");
	this.resetSimu();
	return;
}
/**
 *
 * Dient zum simulierten veraendern von Vektorwahrheitwerten.
 *
 *
 * Benutzt globale Objekte	 		: bvList,seList
 * Aufgerufen von					: simu(,,)
 * Ruft auf							: ----
 * @param arg1 java.util.Vector
 */
private void change(Vector arg1) {
	Object helpObject;
	BvarTab d1;
	SEventTab d3;	
	int i = arg1.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts
	Random test = new Random();
	float zufall = 0;
	
	if (debug==true){
		System.out.println("change wurde aufgerufen !");
	}
	
	i--;
	for (int j = 0; j <= i; j++) {			// Schleife durchlaeuft Vektor und fuellt die Liste
		helpObject = arg1.elementAt(j);
		
											// Da diese Methode sowohl fuer bvList als auch fuer seList
											// verwendet wird, ist eine Fallunterscheidung noetigt.
		
		if (helpObject instanceof BvarTab){	// Fall 1: bvList
			d1 = (BvarTab)helpObject;
			if (d1.bWert==true){
				d1.bWert=true;
			}
			else {
				zufall = test.nextFloat();
				if ( zufall > 0.75){
				d1.bWert=true;
				}
			}
			
			arg1.removeElementAt(j);
			arg1.insertElementAt(d1, j);	
			
		}
		
		if (helpObject instanceof SEventTab){ // Fall 2: seList
			d3 = (SEventTab)helpObject;
			if (d3.bWert==true){
				d3.bWert=true;
			}
			else {
				zufall = test.nextFloat();
				if ( zufall > 0.75){
				d3.bWert=true;
				}
			}
			arg1.removeElementAt(j);
			arg1.insertElementAt(d3, j);	
		}
	}
}
/**
 *
 * Veraendert den Status der Checkbox 2 in Abhaengigkeit davon, wie der Status von Checkbox 1 ist
 *
 * Benutzt globale Objekte	: aSyncOn<p>
 * Wird aufgerufen von		: <p>
 * Ruft auf					: getCheckbox2(), getCheckbox1<p>
 *
 * @param java.awt.event.ItemEvent
 * @return 
 * @version V1 vom 14.01.1999
 */
public void checkbox1_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	getCheckbox2().setState(!(getCheckbox1().getState()));
	if ((getCheckbox2().getState()) == true) {
		aSyncOn = true;
		getButton3().setEnabled(false);
		getTextField1().setEnabled(false);
		getCheckboxMenuItem3().setState(false);
	}
	else {
		aSyncOn = false;
		getButton3().setEnabled(true);
		getTextField1().setEnabled(true);
		getCheckboxMenuItem3().setState(true);
	}
	return;
}
/**
 *
 * Veraendert den Status in Checkbox 1 in Abhaengigkeit des Status von Checkbox 2
 *
 * Benutzt globale Objekte	: aSyncOn<p>
 * Wird aufgerufen von		: <p>
 * Ruft auf					: getCheckbox1(), getCheckbox2()<p>
 *
 * @param
 * @return 
 * @version V1 vom 14.01.1999
 */
public void checkbox2_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	getCheckbox1().setState(!(getCheckbox2().getState()));
	if ((getCheckbox2().getState()) == true) {
		aSyncOn = true;
		getButton3().setEnabled(false);
		getTextField1().setEnabled(false);
		getCheckboxMenuItem3().setState(false);

	}
	else {
		aSyncOn = false;
		getButton3().setEnabled(true);
		getTextField1().setEnabled(true);
		getCheckboxMenuItem3().setState(false);
	}

	return;
}
/**
 * Comment
 */
public void checkboxMenuItem2_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {

	if (getCheckboxMenuItem2().getState()){
		NonDetChoice = 0;
		getCheckboxMenuItem4().setState(false);
	}
	else{
		NonDetChoice = 1;
		getCheckboxMenuItem4().setState(true);
	}
	
	return;
}
/**
 * Comment
 */
public void checkboxMenuItem3_ActionEvents() {
	System.out.println("CB-MenuItem3: Action-Event erhoert !");
	//getCheckbox2().setState(!(getCheckbox1().getState()));
	// next lines by FTA :
	if (getCheckboxMenuItem3().getState()){
		getCheckbox1().setState(true);
		getCheckbox2().setState(false);
	}
	else{
		getCheckbox1().setState(false);
		getCheckbox2().setState(true);
	}
	
	if ((getCheckbox2().getState()) == true) {
		aSyncOn = true;
		getButton3().setEnabled(false);
		getTextField1().setEnabled(false);
		getCheckboxMenuItem3().setState(false);
	}
	else {
		aSyncOn = false;
		getButton3().setEnabled(true);
		getTextField1().setEnabled(true);
		getCheckboxMenuItem3().setState(true);
	}
	return;
}
/**
 * Comment
 */
public void checkboxMenuItem4_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	if (getCheckboxMenuItem4().getState()){
		NonDetChoice = 1;
		getCheckboxMenuItem2().setState(false);
	}
	else{
		NonDetChoice = 0;
		getCheckboxMenuItem2().setState(true);
	}
	return;
}
/**
 * Comment
 */
public void checkboxMenuItem5_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	if (getCheckboxMenuItem5().getState()) {
		hBP = new BreakPointHandler(this, SDaten);
		Breakpoint = hBP.returnBreakPoint();	
	}
	return;
}
/**
 * Comment
 */
public void checkboxMenuItem6_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	if (handleMonitor instanceof Monitor) {
		if (getCheckboxMenuItem6().getState()) {
			handleMonitor.getFrame1().setVisible(true);
		}
		else {
			handleMonitor.getFrame1().setVisible(false);
		}
	}
	return;
}
/**
 * Benutzt globale Objekte			:
 * Aufgerufen von					:
 * Ruft auf							:
 * 
 * @param
 * @version V1.00 vom
 * @return boolean
 */
boolean checkBreakpoint() {
	return false;
}
/**
 * New method, created by HH on INTREPID
 * @param arg1 absyn.Path
 * @param arg2 absyn.Path
 * Diese Methode vergleich zwei Paths auf Stringebene miteinander und liefert bei Gleichheit
 * "true" zurück ( sonst "false" ). NPExep sollten nicht vorkommen duerfen.
 *
 * Benutzte globale Objekte	: ----
 * Aufruf von 				: removePathInList
 * Ruft auf   				: ----
 *
 * @param arg1 absyn.Path
 * @param arg2 absyn.Path
 * @return result boolean
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 24.01.1999 NPPExep, .tail-Fehler
 *
 */
private boolean comparePaths(Path arg1, Path arg2)
{
	boolean loop = true;
	boolean step1 = false;
	boolean step2 = false;
	boolean result = true; 		// Setze Default
	Path argcopy1, argcopy2; 	// "Kopien" der Argumente anlegen, weil sie
								// verändert werden.
	argcopy1 = arg1;
	argcopy2 = arg2;

	
	// Sicherheitsabfragen : 
	// Wir gehen davon aus, das es keinen Path mit fuehrender "null" gibt,
	// der im "tail" etwas anderes als eine "null" stehen hat.
	
	if ((argcopy1.head == null) && (argcopy2.head == null)){
		return (true);
	}
	
	if ((argcopy1.head != null) && (argcopy2.head == null)){
		return (false);
	}
	
	if ((argcopy1.head == null) && (argcopy2.head != null)){
		return (false);
	}

	
	if ((argcopy1 != null) && (argcopy2 != null))	// 1.) Wenn denn beide ungleich "null" sind ...
	{
	
		while (loop == true){
			if (((String)(argcopy1.head)).equals((String)(argcopy2.head))) {
				// Bisher ist alles gleich gewesen !
				
				if (argcopy1.tail != null){
					argcopy1 = argcopy1.tail;
					step1 = true;
				}
				else{
					step1 = false;
				}
								
				if (argcopy2.tail != null){
					argcopy2 = argcopy2.tail;
					step2 = true;
				}
				else{
					step2 = false;
				}

				if ((step1 == true) && (step2 == true)){
									// Nichts passiert, weiter mit dem naechsten Vergleich !
				}
				
				if ((step1 == false) && (step2 == false)){
										// Die beiden Paths sind gleichlang ...
										
					if (((String)(argcopy1.head)).equals((String)(argcopy2.head))){
										// ... und auch im letzten Glied identisch =>
						loop = false;	// Schleife verlassen,
						result = true;	// Ergebnis ist positiv !
					}
					else{
										// ... aber im letzten Glied verschieden =>
						loop = false;	// Schleife verlassen,
						result = false;	// Ergebnis ist negativ !
					}
						

				}
				
				if ((step1 == true) && (step2 == false)){
									// Path arg2 ist enthalten in arg1, aber arg1 ist laenger =>
					loop = false;	// Schleife verlassen,
					result = false;	// Ergebnis ist negativ ! 
				
				}
				
				if ((step1 == false) && (step2 == true)){
									// Path arg1 ist enthalten in Path arg2, aber arg2 ist laenger =>
					loop = false;	// Schleife verlassen,
					result = false; // Ergebnis ist negativ !

				}
				
			}
			else{	
								// Unterschied festgestellt.
				loop = false;	// Schleife beenden
				result = false;	// Ergebnis festhalten 
			}
		}

	}

												// Bei den folgenden Vergleichen muss natuerlich
												// mit den Originalen verglichen werden, da die Kopien
												// eventuell schon verändert sind.
	
	if ((arg1 == null) && (arg2 != null)){		// 2.) Wenn nur einer ungleich "null" ist :
		result = false;
	}

	if ((arg1 != null) && (arg2 == null)){		// 3.) Wenn nur einer ungleich "null" ist:
		result = false;
	}

	if ((arg1 == null) && (arg2 == null)){		// 4.) Wenn beide gleich "null" sind :
		result = true;
	}
	
	
	return result;
}
/**
 * connEtoC1:  (Button1.mouse.mouseClicked(java.awt.event.MouseEvent) --> Simu.button1_MouseClicked()V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button1_MouseClicked();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC10:  (CheckboxMenuItem3.item.itemStateChanged(java.awt.event.ItemEvent) --> Simu.checkboxMenuItem3_ActionEvents()V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC10(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkboxMenuItem3_ActionEvents();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC11:  (CheckboxMenuItem4.item.itemStateChanged(java.awt.event.ItemEvent) --> Simu.checkboxMenuItem4_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC11(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkboxMenuItem4_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC12:  (MenuItem5.action. --> Simu.menuItem5_ActionEvents()V)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC12() {
	try {
		// user code begin {1}
		// user code end
		this.menuItem5_ActionEvents();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC13:  (CheckboxMenuItem5.item.itemStateChanged(java.awt.event.ItemEvent) --> Simu.checkboxMenuItem5_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC13(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkboxMenuItem5_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC14:  (CheckboxMenuItem6.item.itemStateChanged(java.awt.event.ItemEvent) --> Simu.checkboxMenuItem6_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC14(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkboxMenuItem6_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC15:  (MenuItem2.action. --> Simu.menuItem2_ActionEvents()V)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC15() {
	try {
		// user code begin {1}
		// user code end
		this.menuItem2_ActionEvents();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC16:  (MenuItem1.action. --> Simu.menuItem1_ActionEvents()V)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC16() {
	try {
		// user code begin {1}
		// user code end
		this.menuItem1_ActionEvents();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC17:  (MenuItem2.action. --> Simu.menuItem3_ActionEvents()V)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC17() {
	try {
		// user code begin {1}
		// user code end
		// this.menuItem3_ActionEvents();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (Button4.mouse.mouseClicked(java.awt.event.MouseEvent) --> Simu.button4_MouseClicked(Ljava.awt.event.MouseEvent;)V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button4_MouseClicked(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC3:  (Button2.mouse.mouseClicked(java.awt.event.MouseEvent) --> Simu.button2_MouseClicked()V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC3(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button2_MouseClicked();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC4:  (Button3.mouse.mouseClicked(java.awt.event.MouseEvent) --> Simu.button3_ActionEvents()V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC4(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button3_MouseClicked(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC5:  (Checkbox1.item.itemStateChanged(java.awt.event.ItemEvent) --> Simu.checkbox1_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC5(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox1_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC6:  (Checkbox2.item.itemStateChanged(java.awt.event.ItemEvent) --> Simu.checkbox2_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC6(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox2_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC7:  (MenuItem4.action. --> Simu.button1_MouseClicked()V)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC7() {
	try {
		// user code begin {1}
		// user code end
		this.button1_MouseClicked();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC8:  (MenuItem3.action. --> Simu.menuItem3_ActionEvents()V)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC8() {
	try {
		// user code begin {1}
		// user code end
		this.menuItem3_ActionEvents();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC9:  (CheckboxMenuItem2.item.itemStateChanged(java.awt.event.ItemEvent) --> Simu.checkboxMenuItem2_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC9(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkboxMenuItem2_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 *
 * Fuegt den Inhalt der EnteredListe an die InListe an unter Beibehaltung der EnteredListe.
 *
 * Benutzt globale Objekte			: InList, EnteredList
 * Aufgerufen von					: step()
 * Ruft auf							: ----
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999 debug-Level
 * @version V4.00 vom 07.02.1999 debug-Methoden
 */
private void copyEnteredToIn() {
	
	if (debug_copyEnteredToIn==true){
		System.out.println("copyEnteredToIn()  : Start");
	}
	
	int i,j;

	i = EnteredList.size();
	
	if (debug_copyEnteredToIn==true){
		System.out.println("copyEnteredToIn() : "+i+" Eintraege sind zu 'kopieren'.");
	}
	
	i--;

	for (j=i; j >= 0; j--) {
		InList.addElement(EnteredList.elementAt(j)); // ****** moegliche Fehlerquelle ***************
	}


	if (debug_copyEnteredToIn==true){
		System.out.println("copyEnteredToIn()  : Ende");
	}
}
/**
 *
 * Liefert Teststatechart
 *
 * Benutzt globale Objekte			:
 * Aufgerufen von					:
 * Ruft auf							:
 */
public Statechart example() {

/**

 *  @author   Daniel Wendorff und Magnus Stiller

 *  @version  $Id: Simu.java,v 1.22 1999-02-28 18:11:16 swtech26 Exp $

 */

 Bvar a3 = new Bvar ("A");
 Bvar a4 = new Bvar ("C");
 
 Bvar a10 = new Bvar ("M1");
 Bvar a11 = new Bvar ("M2");
 Bvar a12 = new Bvar ("M3");
 Bvar a13 = new Bvar ("M4");
 Bvar a14 = new Bvar ("M5");
 Bvar a15 = new Bvar ("M6");
 Bvar a16 = new Bvar ("M7");
 Bvar a17 = new Bvar ("M8"); 
 Bvar a18 = new Bvar ("M9");
 Bvar a19 = new Bvar ("M10");
 Bvar a20 = new Bvar ("M11");
 Bvar a21 = new Bvar ("M12");

 Bvar a5 = new Bvar ("B");

 Bvar a6 = new Bvar ("D");

 Bvar a7 = new Bvar ("F");

   

 /*BvarList blist=new BvarList(a3,null);

   blist=new BvarList(a4,blist);*/



	 BvarList blist =

 new BvarList (a10,
 new BvarList (a11,	 
 new BvarList (a12,	 
 new BvarList (a13,	 
 new BvarList (a14,
 new BvarList (a15,	 
 new BvarList (a16,
 new BvarList (a17,
 new BvarList (a18,
 new BvarList (a19,
 new BvarList (a20,
 new BvarList (a21,	 

 new BvarList (a3,

 new BvarList (a4,

 new BvarList (a6,

 new BvarList (a5,null))))))))))))))));



	SEvent A = new SEvent ("A");

	SEvent B = new SEvent ("B");

	SEvent C = new SEvent ("o");

	SEvent D = new SEvent ("D");

	SEvent G = new SEvent ("G");



	SEventList statelist =

	  new SEventList (A,

		new SEventList (B,

		

			new SEventList (D,

			  new SEventList (G,null))));



	Path sudp = new Path ("SUD", null);

	Path p1p  = sudp.append("P1");

	Path p2p  = sudp.append("P2");

	Path p3p  = sudp.append("P3");

	Path q1p  = p3p.append("Q1");

	Path q2p  = p3p.append("Q2");

	Path r1p = q2p.append("R1");

	Path r2p = q2p.append("R2");

	Path s1p = r1p.append("S1");

	Path s2p = r1p.append("S2");

	Path t1p = r2p.append("T1");

	Path t2p = r2p.append("T2");





	PathList pathlist =

	new PathList (sudp,

	  new PathList (p1p,

	   new PathList (p2p,

		 new PathList (p3p,

		   new PathList (q1p,

			 new PathList (q2p,

			   new PathList (r1p,

				 new PathList (r2p,

				   new PathList (s1p,

	             new PathList (s2p,

					   new PathList (t1p,

						 new PathList (t2p,null))))))))))));



  Basic_State S1 = new Basic_State (new Statename("S1"));

  Basic_State S2 = new Basic_State (new Statename("S2"));

  Basic_State T1 = new Basic_State (new Statename("Tp"));

  Basic_State T2 = new Basic_State (new Statename("T2"));

  

  Tr tr1 = new Tr (new Statename ("S1"), 

		   new Statename ("S2"),

		   new TLabel (new GuardEvent(C),null));



  Tr tr2 = new Tr (new Statename ("S9"), 

		   new Statename ("S1"),

		   new TLabel (new GuardCompg (new Compguard (Compguard.AND,

							  new GuardEvent(B),

							  new GuardCompp (new Comppath (Comppath.IN,(new Path("SUD", null)).append("P4"))))),

					   new ActionEmpty (new Dummy())));





  Or_State R1 = new Or_State (new Statename ("R1"),

			      new StateList (S1,new StateList (S2,null)),

			      new TrList (tr1, new TrList (tr2, null)),

			      new StatenameList (new Statename("T1"), null),	

			      null);

  

  Or_State R2 = new Or_State (

			      new Statename ("R2"),

			      new StateList (T1,new StateList (T2,null)),

			      new TrList  (new Tr (new Statename ("T1"), 

						   new Statename ("T2"),

						   new TLabel (new GuardEvent(D),null)),

					   new TrList (new Tr (new Statename ("T2"), 

							       new Statename ("T1"),

							       new TLabel (new GuardEvent(B),null)),null)),

			      new StatenameList (new Statename("T1"), null),	

			      null);

  

  Basic_State Q1 = new Basic_State (new Statename("Q1"));

	

  And_State Q2 = new And_State (

				new Statename ("Q2"),null);

  

  Basic_State P1 = new Basic_State (new Statename("P1"));



  Basic_State P2 = new Basic_State (new Statename("P2"));



  Or_State P3 = new Or_State (

			      new Statename ("P3"),

			      new StateList (Q1,new StateList (Q2,null)),

			      new TrList (new Tr (new Statename ("Q1"), 

						  new Statename ("Q2"),

						  new TLabel (new GuardEvent(A),new ActionEvt (C))),

					  null),

			      new StatenameList( new Statename("Q1"), null),	

			      null);

  

  Or_State SUD = new Or_State (

			       new Statename ("SUD"),

			       new StateList (P1,new StateList (P2,new StateList (P3,null))),

			       new TrList (new Tr (new Statename ("P1"), 

						   new Statename ("P2"), 

						   new TLabel (new GuardEvent(C),null)),

					   new TrList (new Tr (new Statename ("P1"), 

							       new Statename ("P3"), 

							       new TLabel (new GuardEvent(A),null)),

						       new TrList (new Tr (new Statename ("P3"), 

									   new Statename ("P1"), 

									   new TLabel (null,null)),null))),

			       new StatenameList (new Statename("P1"), null),

			       null);  

  

  return new Statechart (statelist,

			 blist,

			 pathlist,

			 SUD);
}
/**
 *
 * Fuehrt fuer alle Elemente in der toDoTransList folgende Aktionen aus :
 * 		- toDoTransList.transition.tteSource von InList in ExitedList verschieben (mit removePathInListLinear())
 *		- inMethodInit(toDoTransList.transition.tteTarget) aufrufen
 *		- make_action() fuer alle Aktions, die in einem ´TransTab stecken, aufrufen
 *		- editor::highlightObject() fuer Connectorenliste (*) und Transitionsliste (**) aufrufen
 *
 * Danach wird die toDoTransListe vollstaendig geloescht.
 *
 *
 * Benutzt globale Objekte			: toDoTransList
 * Aufgerufen von					: step()
 * Ruft auf							: inMethod()
 *									  make_action()
 *									  removePathInListLinear()
 * 							          Editor::highlightObject()
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 31.01.1999 debug-Level, tail-Error, returnNewAction()- Error beseitigt
 * @version V4.01 vom 31.01.1999 Kommentare angepasst
 * @version V4.02 vom 07.02.1999 debug-Methoden
 *
 */
 
private void executeTransList() {

	if (debug_executeTransList==true){
		System.out.println("executeTransList : Start");
	}
	
	int i, j;
	Object temp1;
	TransTab tt1 = null;

	TransTabEntry tte1 = null;
	Path source, target;
	TLabel executeLabel;
	Action executeAction;

	boolean loop = true;

	highlightObject ho = null;
		
	TrList tempTL = null;			// fuer Highlight
	TrList tempTLwork = null;		// zum abarbeiten (executeAction)

	ConnectorList tempCL = null;	// fuer Highlight
	
	i = toDoTransList.size();
	i--;
	
	for (j = i; j >= 0; j--) {						// Schleife durchlaeuft die toDoTransList von 
		temp1 = toDoTransList.elementAt(j);			// hinten nach vorne
		tt1 = (TransTab) temp1;
		tte1 = tt1.transition;
		target = tte1.tteTarget;					// Targetpath der 'abstrakten' Transition
		source = tte1.tteSource;					// Sourcepath dazu
		tempTLwork = tt1.tListe;					// Transitionsliste der 'abstrakten' Transition
	
			
		removePathInListLinear(source);
		inMethodInit(target, null);
				
		// Schleife zum abarbeiten der Transitionsliste der 'abstrakten' Transition
		loop = true;
		while (loop == true){
			// Zuerst auszuführende Action bestimmen und ausfuehren
			executeLabel = tempTLwork.head.label;
			executeAction = executeLabel.action;

			if (debug_executeTransList==true){
				System.out.println("executeTransList : Will Action ausfuehren !");
			}
			
			make_action(executeAction);
			// Dann pruefen, ob noch eine weitere Action gefunden werden kann
			if (tempTLwork.tail != null){
				// Es ist noch mindestens eine weitere vorhanden ...
				tempTLwork = tempTLwork.tail;
			}
			else{
				// Es sind alle abgearbeitet => Schleife verlassen !
				loop = false;
				if (debug_executeTransList==true){
					System.out.println("executeTransList : Habe alle zugehoerigen Actions abgearbeitet !");
				}

			}
		}	

		// Nun müssen die Transitionen und Connectoren noch gehighlightet werden :

		// Zuerst die Transitionen (**) :
		
		tempTL = tt1.tListe;						// Transitionsliste der 'abstrakten' Transition
		loop = true;
		while (loop == true) {			
			ho = new highlightObject(tempTL.head,Color.blue);	// HIER HIGHLIGHTEN WIR !!!
			
			if (debug_executeTransList==true){
				System.out.println("executeTransList : Habe Transition gehighlightet !");
			}
			
			// Dann pruefen, ob noch eine weitere Action gefunden werden kann
			if ( tempTL.tail != null){
				// Es ist noch mindestens eine weitere vorhanden ...
				tempTL = tempTL.tail;
			}
			else{
				// Es sind alle abgearbeitet => Schleife verlassen !
				loop = false;

				if (debug_executeTransList==true){
					System.out.println("executeTransList : Habe alle zugehörigen Transitionen gehighlightet !");
				}

				
			}
		}


		// Dann die Connectoren (*) :
		
		tempCL = tt1.conn;							// Connectorenliste der 'abstrakten' Transition


		while (loop == true) {
			
			 ho = new highlightObject(tempCL.head,Color.blue);		// HIER HIGHLIGHTEN WIR !!!
			
			if (debug_executeTransList==true){
				System.out.println("executeTransList : Habe Connector gehighlightet !");
			}
			
			// Dann pruefen, ob noch eine weiterer Connector gefunden werden kann
			if ( tempCL.tail != null){
				// Es ist noch mindestens eine weitere vorhanden ...
				tempCL = tempCL.tail;
			}
			else{
				// Es sind alle abgearbeitet => Schleife verlassen !
				loop = false;

				if (debug_executeTransList==true){
					System.out.println("executeTransList : Habe alle zugehörigen Connectoren gehighlightet !");
				}
			}
		}
			
	}
	
	if (debug_executeTransList==true){
		System.out.println("executeTransList : Habe toDoTransList komplett abgearbeitet und setze sie zurueck");
	}

	toDoTransList.removeAllElements();				// Alles abgearbeitet, Liste kann geloescht werden

	
}
/**
 * Bestimmt zum angegebenen Pfad rekursiv den passenden State in der Statechart.
 * ACHTUNG : Der uebergebene Pfad wird abgebaut !!!!
 * ACHTUNG : find_state muss beim ersten Aufruf den auessersten State und den vollstaendigen Pfadnamen
 *			 OHNE den ersten HEAD bekommen !!!!!!!!!!!!!
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: find_state(), inMethod()
 * Ruft auf					: find_state(), guiOutput()
 *
 * @param x absyn.State
 * @param y absyn.Path
 * @return z absyn.State (z. Zt. nicht unterstuetzt)
 * @version V1.00 vom 14.01.1999
 * @version V4.06 vom 30.01.1999
 */
private State find_state(State x, Path y) {
	State z = null;
	And_State z1 = null;
	Or_State z2 = null;
	StateList liste;
	boolean found = false;
	
	if (debug2c == true){
			System.out.println("find_state(State, Path) : Start");
	}
	
	z = x;

	if (z instanceof And_State) {
		if (debug2c == true){
			System.out.println("find_state (&) : Pfad y ist "+returnDottedPath(y));
			System.out.println("find_state (&) : Suche derzeit in AND-State nach "+y.head);
		}
		found = false;
		z1 = (And_State)z;

		liste = z1.substates;	// 'Kopie' anlegen, weil die Liste abgebaut wird

		if (liste.head.name.equals(y.head)) {
			if (debug1 == true){
				System.out.println("find_state (&) : Habe State am Anfang gefunden");
			}
			found = true;		// Suchschleife darf deswegen nicht mehr durchlaufen werden.
		}

		
		// Die SubstateListe durchgehen, bis der passende Name gefunden wird.	
		while (found == false) {
			if (liste.head.name.name.equals(y.head)) {
				if (debug4 == true){
					System.out.println("find_state (&) : Habe TeilStateNameGleichheit festgestellt");
				}
				found = true;
			}
			else { 
				// Suche hatte bisher keinen Erfolg
				if (liste.tail != null){
					liste = liste.tail;
					if (debug4 == true) {
						System.out.println("find_state (&) : Muss noch weitersuchen ...");
					}
				}
				else{
					// NPExep abfangen !!
					if (debug1 == true){
						System.out.println("find_state (&) : [NPExep] Habe State nicht gefunden");
					}
					found = true;
				}
			}
		}
		
		// Sicherheitsabfrage
		if (!(liste.head.name.name.equals(y.head))) {
			if (debug1 == true){
				System.out.println("find_state (&) : Habe State nicht gefunden");
			}
			
		}
		

		if ( y.tail != null){
			// Wenn der Path noch nicht vollstaendig abgebaut ist, dann suche rekursiv weiter :
			y = y.tail;
			return find_state(liste.head, y);
		}
		else{
			// Wenn der Path vollstaendig abgebaut ist, gebe den gefundenen State zurueck :
			if (debug4 == true){
				System.out.println("find_state (&) : Habe State identifiziert und liefere ihn aus");
			}
			return (liste.head);
		}			
	}


	// Kommentare: siehe oben
	if (z instanceof Or_State) {
		if (debug4 == true) {
			System.out.println("find_state: Checkpoint 3");
			System.out.println("find_state (|) : Pfad y ist "+returnDottedPath(y));
		}
		
		if (debug2c==true){
			System.out.println("find_state (|) : Suche derzeit in OR-State nach "+y.head);
		}
		found = false;

		
		z2 = (Or_State)z;
		liste = z2.substates;	// 'Kopie' anlegen, weil die Liste abgebaut wird


		if (liste.head.name.equals(y.head)) {
			if (debug1==true){
				System.out.println("find_state (|) : Habe State am Anfang gefunden");
			}
			found = true;		// Suchschleife darf deswegen nicht mehr durchlaufen werden.
		}

		// Die SubstateListe durchgehen, bis der passende Name gefunden wird.	
		while (found == false) {
			if (liste.head.name.name.equals(y.head)) {
				if (debug4==true){
					System.out.println("find_state (|) : Habe TeilStateNameGleichheit festgestellt");
				}
				found = true;
			}
			else { 
				// Suche hatte bisher keinen Erfolg
				if (liste.tail != null){
					liste = liste.tail;
					if (debug4) {
						System.out.println("find_state (|) : Muss noch weitersuchen ...");
					}
				}
				else{
					// NPExep abfangen !!
					if (debug1==true){
						System.out.println("find_state (|) : [NPExep]Habe State nicht gefunden");
					}
					found = true;
				}
			}
		}
		
		// Sicherheitsabfrage
		if (!(liste.head.name.name.equals(y.head))) {
			if (debug1==true){
				System.out.println("find_state (|) : Habe State nicht gefunden");
			}
			
		}
		

		if ( y.tail != null){
			// Wenn der Path noch nicht vollstaendig abgebaut ist, dann suche rekursiv weiter :
			y = y.tail;
			return find_state(liste.head, y);
		}
		else{
			// Wenn der Path vollstaendig abgebaut ist, gebe den gefundenen State zurueck :
			if (debug4==true){
				System.out.println("find_state (|) : Habe State identifiziert und liefere ihn aus");
			}
			return (liste.head);
		}			
	}
	
	
	// Defaultrueckgabe, wegen Javacompiler
	if (debug1==true){
		System.out.println("find_state() : Allgemeiner IdentifizierungsError !");
	}
	return null;	
}
/**
 *
 * Diese Methode kuemmert sich um die Initialisierung der InListe. Dabei wird die
 * Hauptarbeit von der Methode initInList uebernommen.
 * Weiterhin werden die BVarListe und die EventListe angelegt.
 *
 * Benutzt globale Objekte			: SDaten, evList, bvList, makeTraceStep()
 * Aufgerufen von					: makeStep() *!*!*
 * Ruft auf							: initInList
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.12 vom 27.01.1999
 * @version V5.01 vom 29.01.1999
 * @version V5.02 vom 07.02.1999 debug-Methoden, Entered- und ExitedList werden jetzt auch zurückgesetzt
 * @version V6.00 vom 23.02.1999 BreakPoints und Traces
 */
private void firstStep() {
	String rootName;
	State rootState;
	Path  rootPath;
	int i;					//debug-Variable
	
	rootName = SDaten.state.name.name;
	rootState = SDaten.state;
	rootPath = new Path(rootName,null);
	
	if (debug_firstStep==true){
		System.out.println("firstStep erfolgreich aufgerufen !");
	}
	
	/********************************************************************/ 
	if (getCheckboxMenuItem5().getState()) {
		// System.out.println("firstStep: Checke Breakpoint");
		gotBreakPoint = get_guard(Breakpoint);
		if (gotBreakPoint) {
			gui.OkDialog("Breakpoint found", "Letzter Schritt loeste Breakpoint aus !!");
			gotBreakPoint = false;
		}
	}
	/********************************************************************/
	
	InList.removeAllElements();					// Alle bisherigen Eintraege aus der InList loeschen,
												// da es sich um einen Neustart handelt.
	EnteredList.removeAllElements();			// Genauso bei Entered- und
	ExitedList.removeAllElements();				// ExitedList !!!

	InList.addElement(rootPath);				// Defaultmaessig den aeussersten Pfad eintragen
												
	i = InList.size();
	if (debug_firstStep == true){
		System.out.println("firstStep : InList zurueckgesetzt auf : "+i);
	}
	initInList(rootState, rootPath);			// Initialisiert die InListe (InList)

	i = InList.size();
	
	if (debug_firstStep == true){
		System.out.println("firstStep : InList durch initInList gesetzt auf : "+i);
	}
	
	resetBVarList();
	resetEventList();
	
	i = bvList.size();
	if (debug_firstStep == true){
		System.out.println("firstStep : bvList gesetzt auf : "+i);
	}
	
	i = seList.size();
	if (debug_firstStep == true){
		System.out.println("firstStep : seList gesetzt auf : "+i);
	}

	if (LF1 instanceof ListFrame){
		bvList = LF1.inVector;
	}
	if (LF2 instanceof ListFrame){
		seList = LF2.inVector;
	}
	
	if (td instanceof Trace) {
		if (getCheckboxMenuItem1().getState()){
			td.makeTraceStep(InList, seList, bvList, true);
		}
		else {
			System.out.print(" FIRSTSTEP: FATALER FEHLER !! PROGRAMM INSTABIL !!");
		}
	}

	/********************************************************************/ 
	if (getCheckboxMenuItem5().getState()) {
		gotBreakPoint = get_guard(Breakpoint);
		if (gotBreakPoint) {
			gui.OkDialog("Breakpoint found", "Letzter Schritt loeste Breakpoint aus !!");
			gotBreakPoint = false;
		}
	}
	/********************************************************************/

	if (debug_firstStep==true){
		System.out.println("firstStep(): Ende !");
	}
}
/**
 * Bestimmt den Wert des Guards und liefert
 * entsprechend true oder false zurueck.
 * @param absyn.Guard
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: get_guard(), make_action(), makeRealTransList()
 * Ruft auf					: get_guard(), guiOutput(), returnPathInList(), returnPathExitedList(),
 *							  returnPathEnteredList(), returnBvarStatus(), returnSEventStatus()
 *
 * @param waechter absyn.Guard 
 * @return goal boolean
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999 returnPathInList etc., debug-Level
 * @version V4.01 vom 06.02.1999 debug-Methoden, debug-Ausgabenoptimierung
 *
 */
private boolean get_guard(Guard waechter) {
	boolean goal = false;				// Rueckgabewert, default: false
	boolean temp1, temp2;				// Temporaere Variablen zum Speichern der Zwischenergebnisse
	
	debug_gg_c++;
	
	if (debug_get_guard == true) {
		System.out.println("get_guard("+debug_gg_c+"): Start !");
	}

	
	// Suche Bvar in BvarListe und liefere den Wahrheitswert zurueck	
	if (waechter instanceof GuardBVar) {
		goal = returnBvarStatus(((GuardBVar)waechter).bvar);
		if (debug_get_guard == true) {
			System.out.println("get_guard("+debug_gg_c+"): GuardBVar: "+(((GuardBVar)waechter).bvar).var);
			System.out.println("get_guard("+debug_gg_c+"): GuardBVar: Liefere "+goal+" zurück !");
		}
	}

	// Zerlege Compguard in seine einzelnen Guards, rufe get_guard mit den einzelnen Guards auf
	// liefere den Wahrheitswert entsprechend des gewuenschten Auswertungsoperators zurueck
	if (waechter instanceof GuardCompg) {
		if (debug_get_guard == true) {
			System.out.print("get_guard("+debug_gg_c+"): GuardCompg: ");
		}
		switch(((GuardCompg)waechter).cguard.eop) {
			case 0: 
				if (debug_get_guard == true) {
					System.out.println("(&)");
				}
				break;
				
			case 1:
				if (debug_get_guard == true) {
					System.out.println("(|)");
				}
				break;
			case 2:
				if (debug_get_guard == true) {
					System.out.println("(=>)");
				}
				break;
			case 3:
				if (debug_get_guard == true) {
					System.out.println("(=)");
				}
		}
		
		temp1 = get_guard(((GuardCompg)waechter).cguard.elhs);
		temp2 = get_guard(((GuardCompg)waechter).cguard.erhs);


		switch(((GuardCompg)waechter).cguard.eop) {
			case 0: 
				goal = (temp1 && temp2);
				break;
				
			case 1:
				goal = (temp1 || temp2);
				break;
				
			case 2:
				goal = (!(temp1) || temp2);
				break;
				
			case 3:
				goal = (temp1 == temp2);
		}		
		if (debug_get_guard == true) {
			System.out.println("get_guard("+debug_gg_c+"): GuardCompg: returns: "+goal);
		}	
		
	}
	
	//	Ueberpruefe, ob die durch Paths angegebenen States in der entsprechenden Liste 
	// 	vorhanden sind ( NEU : Jetzt mit : returnPathInList etc.).
	if (waechter instanceof GuardCompp) {

		if (debug_get_guard == true) {
			System.out.print("get_guard("+debug_gg_c+"): GuardCompp: ");
		}
		if (debug_get_guard == true){
			switch(((GuardCompp)waechter).cpath.pathop) {
				case 0:
					System.out.println("InList");
					break;
				case 1:
					System.out.println("EnteredList");
					break;
				case 2:
					System.out.println("ExitedList");
			}
		}
			
		switch(((GuardCompp)waechter).cpath.pathop) {
			case 0:
				goal = (returnPathInList(((GuardCompp)waechter).cpath.path));
				break;
			case 1:
				goal = (returnPathEnteredList(((GuardCompp)waechter).cpath.path));
				break;
			case 2:
				goal = (returnPathExitedList(((GuardCompp)waechter).cpath.path));
				break;
		}
		if (debug_get_guard == true) {
			System.out.println("get_guard("+debug_gg_c+"): GuardCompp: returns: "+goal);
		}
	}

	// Leerer Guard ist defaultmaessig wahr 
	if (waechter instanceof GuardEmpty) {

		if (debug_get_guard == true) {
			System.out.println("get_guard("+debug_gg_c+"): GuardEmpty");
		}

		
		goal = true;
	}

	// Suche SEvent in SEventListe und liefere Wahrheitswert zurueck
	if (waechter instanceof GuardEvent) {

		goal = returnSEventStatus(((GuardEvent)waechter).event);
		if (debug_get_guard == true) {
			System.out.println("get_guard("+debug_gg_c+"): GuardEvent: "+(((GuardEvent)waechter).event).name);
			System.out.println("get_guard("+debug_gg_c+"): GuardEvent: Liefere "+goal+" zurück !");
		}		
	}

	// Negiere den durch get_guard bestimmten Wahrheitswert und liefere diesen zurueck
	if (waechter instanceof GuardNeg) {
		if (debug_get_guard == true) {
			System.out.println("get_guard("+debug_gg_c+"): GuardNeg");
		}
		goal = !(get_guard(((GuardNeg)waechter).guard));
	}

	// Dieser Fall darf nicht vorkommen => Abbruch der Simulator-Instanz mit Fehlermeldung (wuenschenswert :-) )
	if (waechter instanceof GuardUndet) {
		guiOutput("Simulator: FEHLER !!! Ein Guard ist eine Instanz von GuardUndet !");
		if (debug_get_guard == true) {
			System.out.println("get_guard("+debug_gg_c+"): Ein Guard ist eine Instanz von GuardUndet !");
		}
		
	}	

	if (debug_get_guard == true) {
		System.out.println("get_guard("+debug_gg_c+"): Ende ");
	}
	debug_gg_c--;
	if (debug_gg_c == 0){
		System.out.println(" ");
	}
	
	return goal;
}
/**
 * ACHTUNG ! VISUAL AGE interne Daten; nicht veraendern !
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G02F3DCA6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E14CDBFFF09457FD57E0E22B0DBA21296260114625A9FF28E355D19B1C5206C94918CE1418F628A1B3B0D5BCCA2A340A87FB18D413E10226339CC61CB1BF8448B68EB2BFEC5988CB401DF4620790B26887BA0104A4F47A85279FFBF7AB5D41696E385BC3C212215F377B3E6F76765EC9BAC15AB273E1EF3FBFBF6F7B5E3EFD3B7BA4149CDB36F33B21C490366F94683F735B85A17002A0FC6BE20F99E5C6D3
	1AC1507CBB81B8A07C29799DB89E865D326CB4C33170EB23914EAB613C7247E9068F6177A161EDE3417370A3CE1E35207B5BD7135E1CBDCF079D736CD24E33AB7640F19F4084B29BA05DC47267D69C2672C9B85FC7FF93EC072865E6E2F3266F30C61ECB6D8A09BF68D7A8B65BAA74B68110AF8624835ED5186DBDF8016A5719B78A7D7FBDD83417645178A18A4DEA0E63D56AD1902A0BC891F688AF8D6E07E3DD673D317A10E7BCDCDB5B8D7F8BCB2A8887DB1FD1319104FEB8AF2DA63235FD245D9B60FCB3601D
	EA647263CF6AE82E14977220D0C3F1390CD42B9C96D7C738ECF96BE3031A73C4A566DCD30DB98F1AC84EC278DDE201FE35203C2A15481B605C8E10AD4865BFBEC7AEAB7DF9027DE8FCAEA99615C316A54225A967E343C7CA9F7F5BA069034D20AB2EC9B314812CGB7A099F0C499DB4F5B3F0063D5075C689F9F777B2E86828DDED7305D6F123C703B3696B4D4F6CBF27A03AEC1D0474FC0DFA5078FE2D9CD7458A1D05B56A7E01FB7ADF9C3C81543CABF6A5BD632CFAADAC1E3E457C45A16FE0E542DE00054AD9F
	64442F87204ACF5EA6725CF3917966BAA2FFDD69F3F21EFABE4DF025966B3CF89A395826578B395D3E22DDBCAEFF9DAAB2DDD6B9D8AFC73828B5FEE51854D8867992C086E0B5A097A054E30DF36D55F3553855A7CBC11F686D8EBB82E2D0EE88FB3CAEA9C8EB7DF9E53CDA17564756F99F2B47334A38B3552B755845645FD464D60DDC2D53E1C5CE4EC7C03786205A1F39CE6465DF2A761F32B8D6C5BE29112BF1AEA8631A1CE703AE6FCA9A9D0FC7C4D2AB218146BF4082E883F881498DD82B0A4133739C0F1D1A
	BA293EF9039F40D11A1403E22BAFA40BBE2754AB86BD2243AB0584EA7356A0E91FA34A6FB74AF562049CAD12CF8A0A32476F03686238841D92D2ED368D9EA0B6C1C9515ABC6392A4988F8898F74750A1B8FA45105C93F009329431D0756F8D11B177C06C050890GFEEFECA0358AA8F3D4867CDE5300F5EAF090D9E9A35428916B726C50C1B85E00C1A2F9EBEB1BBCCEC2C38CBE446B71F7839924CF41FEAD71EB023E839CEFCEB34CG72G01EBE906C2C0F28B68GD900ECC0AEE0AB20441AE6B081BA818140E2
	40F2409A0031957A8CE0813481F24971FA1AE18410844886B45DGB9E026AD4D30941085588AC8ED07FB822073A6488139B7717AFB3B9F2F3F24521F8B2B3EB54A3D7ED67C202F283CC39D67D7BAA2571F5A6E5337C93B5DA0CF86E4G328179GE3E71A219CE085388149008CC0B6A09FE06C82BD408AF08312819900ECC0BE40F88B74GAB408DC886E4G328179GE3B76881D6009B108C48GE4837281C69B6881D6009B10EC43FB6F463EE50658EB165413541D74994AC8BF6A6DC8FD15F931A5DAC77AF326
	19BF9FE8C17A1A74F1BAA7B66943183924E56E1884E45CA977C5EB2CBD99A7FA9999DBE4AC4DA7B699D7E49C654631A7E33AD427A3E3CD99BF6D6A38556ACC9C0E645AA15782994BC6EA1FFBB35AAE09530E187A36C55B106BEEAE1FAC0DCF89079B39EE49F5C82E2B3CF97471C25029EB2BBAD61F69670D550B00130077F476FA3BE6FABC221313F9EE6FBCB89D83FCA628EB873D6EAAC77661BC5B3A139ECFF1F417B8328AC04DBCE3B7825A816763685F8718F532EEFA6CGD46BF41FF2E21075654700AF81E7
	292CC7E7F3F99EDCEF684E8F4C4327D5737B84C7CF6E032781AD007D732CD92268521D2B77130CEA6D5AF224404D3D1FE4F6CBC599B6F52C2F366957F37F69D45610A0AF841476607D77C7C34785E5DD52A006A4DC09F484E191A139A2F7E355760743245D528349A7B7C9814967029FA13A8E7827E142152ED1C67D822E9FDED364E46D57880B0890137F6B7081D58EB134AB9E54EFD27422AC87BD0E30ACF5CB326C710DC57CFF21F089C5ADABC3F46DF1D9F9D631C09B5BFA705E14222C8183F03E98104A64CB
	95F9A635DD4764C7CD7DF17AA8C07DC91C0576331A63FC5C9CBB284D4E85460F6430DCEF2271F6D25991BA2E489CC7662A4F6858A6739E390649DCC3468C397E4835C8662A93AC5ECDA5DE81343D349756C6GAFA095308A108758538777A5009D3098108958GA8841475C31D81F2BF56393E35F8FB244D48D14BCFFB7DE9799D51715BC75BF2D2D707334242EB78FF8535B6DF6356866B221D038E696A722E26AEB84F9E2075536679787FC1DB9305FA7D17097167228A11BF9719C57EDCF4DE1CFD2E79D2644D
	B52A6E1AC81EDF1CFE1FCFD2A6CB26965477B8EF5B88E70F4FA377F9DCE3125C6D191284E1CDBF19271ED31E115261F7D6BFBEBBCCFC0AF3FDF973A64174D6BF77BD429E6B03A26300BA7F148EC466FA15D30B034CE912D79A0722B47A43BED91537AB4FDFD49E5A214836B843F0BCDE5E9016E53F4FD2B96E8F0724B34A7F0DDE0F731E64AA3FABBE904F08937299255AE75A092E197CBCF57C74697A636445D425C3F59FEE57BA3645753CF42A7714190E6BC342E777EEEBB8D4BD99072AE88E10288A731CE856
	6629FE32BC5543D7158ED734A112279A73BCE388BF4DBC8954341A5534FF34C34341DCA9AAE44F68231B9309EE2131174AF722E2BFD17B4CB17DE52E26F9CC879FABF99A5D12731E43BFE92E744852789972DF37AC4AD223DB740D45A6EB853DBE1793C38C376A3C4DB10DE67E5AB6CF6DFE9445256A4939D445F0191DCA8D76ED2779519F3EAEF31505F2E914487C54AE7942116B79F5874A2A186CCF4E3CCBE5A4CE44F6422715C76CFFB72315C746750BF777D1F937845331A87B036C7A5E5B9423E357E4300D
	4C71D831D89ECBBF6269A3F9FFFE895B94118D1772FCA2ED787AD51E5E42749D3FBF2774A3534C73AA057A1F4398BF3B033CA2F11796E9EB97116F6FA7EB07EB9E2F5464913D7EB1B315DFBA7DEC54F8E22D7F5F9FD797106B6E7DC3F1B845546809F39D6D479A5630DAFD371F57F7917D162E0BD1FD4761B50E2A18BB0579820B73399878C3553EBDEE7E0BEA6ECD1EEFF0F3BF05390D05CA30CF4C97E9DDFEF1154F4D727438D99265E6225C52BDB1730C997B73F117765A0B0C1746226871D293FF3C54440E17
	48BC3572B43FAEB5CF2FAEB50956258A2F896EFCF80EF19F2C67F3BF7774381FCB10FB952EB5BEB0C577CFD57C7E290A6D1F489C6B952263F0D6DE98479C9BA7B2DE7E6113F973614CAF24666AFB1F8A09BC8B5441B3C2A740BD006FFDFE62A0FA7C6EC2DF78B47A3DDE75B186F439834AB34A6D924DF3C752007ACDFDAD9CD57D21BBBCFD79ED5427F275F5CC7F3E1D276FE47A6739FA9753FFB848536FE37C9B6750CFF17513AC7E36B97CCF8EE975A937D57DEA66AF8D73749B187E65912DBE1F6A0F5FC13DD9
	6469ADCC7FD32E5E4A74579DDA3D7D0E1E7F03B9745FF17062AFE5716B1DBCFDA653EF74F16B43749F85F87E9B187EBBDCFDBE53D75C6775CFA153E75E6765AF36237E37C1DE7CB2261F8CE975976C2A5E4D746B9F70724FB03DEDE2767113B441633704797F63A40F5FF2263F76F07678C12EFEA5737F2FB97C0FCD4D2E3FB1B57B7819186271DF4B72EF1DA3FE59746C7A4EE9DE7CDC96FF674C6C7E66B97403F3687FA82EDE1DFFAD23E47EADGBEFB86538C2503B87F9A1C641D9D4E3FEDFE7F3DF040C63E68
	6377FCE7B19CC3527D3064F3CAC4F1C3FAC83F5997B88F6AF43D22B79C598BD068A46BFC2FA6267AAEAACBA472C050B3AE869F12176EF45F132DD23B2749849C499E29CEB83AG6A7DE8D1C7919C2F114D83E6FC37752A0D6C3DA27722F84F97EA7C57CC3C78B3F0DC82D0EDCCADDA1B8CEA338E0EF9002CA1E2730D6B25D4DF88329210D906D43B40905DAF24585D3A416C603CE4986C06295D30566E7DB634CB85F92646EEED145DCF185D1E91189BGCDA32ADD520856AE259D6D1603FC95A0075A9D9635F69D
	B7514E8EF2D90C5825C6596D68C03B0DA05F82A8F670722EE2F6E5202F835829DD4021358BF7225DD210E78156D13BBDCE2D5D05AE342B83398DA0BBD53B2CA83BED4C2E9064A5AE281F0B66F5C5556F965A2D827986C08135B3CADA3BC55DE8E78239956016D43B65D1F64DB6345B82F263A80C21D1554EB52A35B37620DD9648338179542EB04A6EC74C2EEC8C6A87300F51F60CE96D26FAD97D4654F84C8E6BBCC66CDEE47B734EB259AB0AECBDDCC40DC578DD2F3DD83BCFD0F8AE48FD57CBD0C0F9985D7A6F
	FADF0D113865A03F604639E542251DDB2E06C2FE27C7595E036F41A56DFBE0D5F6CE5A8D47D1CFB0A4377ADC52A44EA5F59261CA767FD0B13D76DF2AA05FE1757B191ADD9EF898424F080D934A125466EF743BA4ABCCC70D6CB5F92FC71AGFFF5EF618FEFE33D5A5C789E7C1737357B96E543D051FBF1EA331856E43926B6469C5C37F87D8E464E833DAAFB0D49CABDA8EB33234C46E47F4CE4A9CC76B703EC9FA44854DAFCA1FCBEE7AD4899D94BE31B07060C341605ACF67AB04AACF7B1763177F97B98793149
	43G467F558872745E4578EE9163AFE7326D8E344BE1B9D3A55EDEB5FECE7684CC73EEF2E00E12FB18A36CC4D99B136D7723ECB113F586D0360A49B6B1D98113B96EA367CAAF4A7E7DBE5A39186CF4886D523D5836DFFF70491CEDEBF51287A0B546F71598643B5B8696F77784465DB30E323F1B407CCDCC765BC9144DB059DFBDC45F95CC7672944AB6B359F353C4C62E05B29FDE5735237C6FBAD7C649FD789EDB9FB40D12FB75833AA6D05B7D770748BAC559CF0C4F4335354A6DDC4D17D209DC6DBE5C47BBFC
	1664CB0573CC409A9FF25BE1C6EEB94C7628E2DB62CFB3D4821A7CD86F2C0B9FF17232A5821B835CADD8833B9FE3BE36AA7961BC933086206E43DC296C4FA471F3C0D69240355E2EC66DDA2C8D6481C0EAG57B047EE107D9B89FD2DE073DD2F093C57CD7CB3974E753BF6DF213C1204B704556649227D1C79EC49B343C59981F59EDB97084CE74BCA0F9AD6G5476CF5F4076175E077B73FDEC7F49AADE7BD36EE37BFF79846D97CABEE46D0F7944C677475637A12FE510F3A363E53770F89587F10C78B435CD64
	F3976E4B7D8D4BDB86B16DC14CDB554C9D8FC12C47C07B426B71671AFA443C1E273CDEE9C7DE99049363B5D82F65F5B824722A8BA12F521BE47ECD701D986535A8C476E7AD7015B865BDF993F9F782276490724E3942633DBA047D783D285C893E7A2539ABBBB07786083BC7465C83D1E3288964EEC00A0CB57B338EB2C753F7E4B4562FD82CE5E0371145B21C5747AA8E435AB50C315ABA353154360D3949BC9677151B00E37145CE4CE905F8C9E14CF927C21BF3A54873G468718F3E757427B7C0563A7B4F50F
	7B5A1FF1943B587C8179ED8F5875D22E6718823AAC46B17B89B83EE47B1243F1E1E3646CAD643F863895B27EFFFC0A475FB2017C1F3F35F07EBF6072CF7093824ED70C3F9538A5CDA07FC517F963F2A563BF509DBBAE3F089A17B11F8ED81F3F540DB957C23CF7A6B16723E37A1A5501EEE492F39E34AD3CE63D5C1AA578F901727759103F8C5CB2997F7567757CC94D72987F1FBD817FAEAE7F84BFB1D07E669E643F9538199EA27FEF57727A5C7D9079AF6D096D73EFC775F94C67885667EF321CB390EF854B19
	F90917F3B34B59559B1B539315B36653854B79173D18D31802E752A9FC1E6B2B52BE6B665F9563BE6B862668FE7BA97D332E68A1EB44E5A0DFB1453EE30C12EFCE782CFB8B1EAE21CB3C12004F2F0B46C81BE0EDCC4F1F9DA3F9153531007B6CDE3DBD5B5FFC6911BD05FF4F7262DDD2479CB85F8AA81ECEB314832C5348ED2FFB4E3F0FEA1E1C656F23BA6EA4424BBD0DFBAA57A564B7437C3A6C0978ADE1FE2FA764371C79FDFFB0913F154C4FB9B41B5F5A6968FD39394C6FAF06E773AB507999E7506F4D11C4
	F816B23FF1F1B6BF53CCF43EA666D764C8A4DFBF73FB0379F9F531F9C8E2FEDF7721DF6ABC7CB218DF09FFB61E2BF43156B13F1EG7A654DA35F56C87B185F1E2F6676BB7E957A0D5DCF241E966617BF2B1FD547414E7C2AC209648BB03F95B1FE0BB5B916EAFEE7525F2A5F7E09C4722DE679D64D6A37C1573EFC6637ED927D8A67518F450F506F65070970ACE3FE5F1BCA446F8273CB19A6FEEDF0BE72A816272C13A5B33FFF09793B54F40DED96A7167AF7236B15F7A0EB9E29FFB73A1D49F39479BA0DDC3DD7
	CA0A1C1C67006E4AE34CEFB4137C4B9FC7722C547CDE7BD8B73FBD463FE77D26922F60311AE7874B1F2E485FC90A48557CE50A1C1C1700EEB2094DDBADA47F7F50F6ADD0D1DD679A9595D3D4D40CA8C40941C714C694927111B469B50936B4D79B5B1A045EE59AF2637562155C6A0A35A6999104283D452A51FB5508A6860CBC65618B8585F5CCD02922F12513BAA30CB0BA4C0C4300A8345EFFBF7EFF4EB9B30CC892565A6B303F3D777FFF7B5F7B6C335F2354916F673B1C051E5F73791AA4BFED7ED7F1FC151F
	B67F3D75187F749E2149569E287F39AF357A6776D06BCF5478C53AB80FF41FE86BE2CFF5BA513779659136AFDEB3C7A5B6376A70EE00D773156BD359A0071D99514353509367450ED762DC212BA77691165DE2EB8611601F8DEEA138D560B2FBC1BF9B1C319776958C0D5E665355FD054EA6535DE7E7049DFAFC55BD7B8D74C81727717B75D22713F35987B05FA13D308C2B2A3B476122C7BAED994E754AE1E4B1F2D8C49CD6F413435AF38F32C3263FB78E16D3B807FA489FB94C701055B58EF3BC52E96D607077
	D6175F4D5F6AD917F143156499FC9C67EF830867BF4FF70FE720C7BAAD4F673D5AEA5389AC2F85446140056EF12870C827AD2F0C5E5EEC75DF173C580A96D244FB3F67006A3D87B9FAF00D60BAFA63FB9FDA0CF357413DB1AF7F594D3C7C6101F9095364C5704CAF7146B3815C8A62F978A0724C6803BCA3EA3A47F30AC7BAADCFFD9FEFB6FF69482E4E6B274676D5195E72E484392EBE1827417B319E85D21E9A7E5A3DBC35F824535665D9FD3C59BE66F3ADCFB637978FF1130827A59BF92677C51EA5973B4733
	52A31D16E7C5DFEF6F5C8B7B30FD3ACA9CD6F413435ACB8FAA7FA02F9C269DC38E630943E48FD9DD6370930FF4DABBAC6C6B2D3C9EBB6E2DDE2D00389B82303C1E5E056F4AE7814853D55BBD1EBDBD52E9F99A823C59EA5DA16ACF900703173B476114C7BAEDF9C5FA65704A91AC2FD944A1291B9CBEF8A007D5DEB93CF51456147BA107A72FF40F43D40FF4DA8E557D546964BE2EA646219E42DC7D304FB62D111D434439279A3B456652613C538D8BDB0335BBAD8D969BE237B8D6E70778CE3CA765F53E77419F
	E406135C31B7BBBF5B78631B5E4FB6C65F74FE36F15ACD5F672CDF38595959C7416F465F10DF8CB8013DC358EC429A8D084593769F04A591760BEF91CBF98431FBFCAF44B6709790F6106F0528853F153058EB984F0FB0BFA3460BA0EC358931D8427CB9B67F91B147A930D00A3708626D3C0E186EC708AD67D886783389DBC971AA889BED730CB74E0671EA895BEF47FC3888DBE447F8A104D99D1E72EEBBA8EFEEFEDE622D24F88B887B67ED347D8A42BE3C0DDCB684A2763293539690F63699B1BDE1BF671891
	7CFE043D530272A2880BE8E558EC70479376799D64320C30D76FE05A747E088535215E9C427EF257B33FDB6FE23CAA0A7762DD54E1A06CE777B01E0330A778DE8B7F7E4A3AB13D5DD347CF5A318E2D26F83FEE27F2A3F9EF733D94D1601FC558AB391837047E58063D4616A6E43B74BB457F3772BB3B97607BF0C2077F8F2AC364F9975FA712BEC0FCDF859646ED33833002810875A65B5490B6B60FE1AE70878C203B9A7259BE17E0720F66FE3686AD7CDFFCEFF9E2BA3C5B2D72FB533126D33B79733DB0114061
	F94A6B9F746E7A11FC567D7F0F74BFFCDD0978C1F56C64361873B17B66B96053E5EE66939DD5A617C9063D63E273B39EE76C4F1B1A4CBABCAF337C4B0FE41CD3AEFBA55BCFA2469B2FE5FB5977D4E302AF3668DB2CAF403E454281386F68CAA11B175AB6906AA8B87DC0AC034ACADF7310C608679AA86ACD00FC0AEF6C8737BA7F1E27DA3DFF4F57DBD95F22595E622CB3EB3F59FF31EE23B077AC909BB8B11308B85BF9FA174959E07678666F6169C5D8E76977DBBB6B9748F3E8DFA96DDDC4F5256E00AF9B0501
	DD2224EDE649271057DE2D14D7CC721EA876A5EF8148C814F2F4C1CAF9796714728AC91E6D14AFF91B035C6FFD36CA5EFF2B6415103C6147FD49AB8399B5D2CEBDBC5B02F00D756973CAF925A4EF5B89DF7282A1FD3814972362E7392014F7106A7F1B1BB6F956FF3E91B5E5902B6B678A15EB4133254C44A09C87E594515E5CC178FEC4943177439F7CE160E286617B916E332C14AEEE10127B1E9AA5F736CEACF4B714503BC93A4BCB793BB9985ECDF07A41F40F4C412E6AB68ED66AFE23E61D321E94206E0ECC
	4C37EBB06ADE3F1B5F95847ED96062C977307DBE5795873B6BD47A9010B5846B40483FAA73FD18721D78B96AAE98023A5B33186EC6707B018B99023AED593EF4C78DF16B1E857FAFA05D560B2AFC97E27DFBE9DFD76D188832F441A8AF7F12CA5E8114775CB11F6FDB3062FD8BD6164BA2353CA2344D2F0E23ED4A0251B616F2E69B97788341C58423ED266F6AEADEE62AF4CF2CD569AE26F536C33E644DD564A5817EDFC13631DDD649ABC1F92F9E71A5EF43D0373C4C21CAFED9D7D472CAD15E22233E6495AB64
	69D572962A659D446F4461A6564FE5FB7E057F98775F55E15BE2BE41E4086F48A9DB3D9CG0B738E6F72F16D50E41D6E35B5087D030F9B17A70B7B73847681476AD2447DF0823B4F470D53D244FDE8825371F1E3DF40AE103C840E3D8F7EB1EB105364BB6C1C408D272989BED7179C66BADD54D096F7505FF14E47A0736E104F202138FFD13F1D1F1BB2DDB7DBAF41F74F56A06DF31E0FBBAFB858D752EC93727E759A4ADBA165ECF8D4BCF39E45F29FE6E4637DDA27290E6D439658B6937BAE83B1CBDD2359E94B
	4C4B4B23F2B8F608152359460EEC55CBEC8A3F33285A84FD86D55C626B6CF95E5CA2737D77BCD6068ECBDB1349A1BAC6F2CF66377C5D30B7B4D84DB5BCD456213AD464C6B21F3331F3E5176E5A6A9A1D10D3E766C9B31841FCDDA7EDF8520EF92E16F9B54A279F6579C3BB13F19EF2672AE7574CC2B1D81A1BDAA87F817CBC67C978G396E195924C4B35841D25FE2EB90619B79F94D9A273D810A30C999AE4AFCA89F2FCE044803F4486BE3A7728A17FCE264F3BE717AB7A73363A51739456C84E114E7E3B3335B
	253AC62B458CFDCF9B5A4DEEE479G920EC6CBDDF31539394EE9F130E38E3A40E1CC676F1A194EE5A0FF9B7875607C06A95608B92F8FDA1057FC9936C8BE53C3304ECDAACA45BAC71CE634EA4F51093C9F636363A5707EECCCC19D0B6F200E6C90A13BCCBE8DA11877F1EDAC0FA71DE653AD07F8C7851EF7D70B8BBD71FC5C5C82AF7B4EB598774DFBE93C5CF86FD5E2170EB06E4DECA79CF1DF524EE41E335B58010ACC138D71D5B9EBF9BE6F5ADBC00F68FBEC4AD576BDBAA46F88F80A3C3D1D0BF91B8958DCF0
	C960B2064337FFB8662D341071E27B52CE1A1CD2D73497DDBF4B71262BE6B86A8AA3DD0D00F5008B83B793DCBC69FAB60F6FBF3438787E32D54359FD747759D5CFD26F90FE9FB77428DD2C5E32C10640C34FF87219F2C64927E20460639A015F2E0C82EDDB9EB842DD3F42957F2393EB6D2F7331CB4C88B1C771E74297F3BCDE010BB5F8834719BFA5946AF0A80E3F3F2944B179AA425A2B981699961ADC9CC6F3A29C53035FC0583D831836113050A2CC9BC8D8C291268DA7ECFAB1260DA46C0D92CCBB1730DD25
	18B601305E27B0ED92E1E3CFB3EC73E32149590FA196F59C639593B62F9CF5DCA5ECCB856A3092564A31B670879216FF82650593D6F392654DA2AC78A44ADB70983E7F0FD4333B3D3D744FD9F556097ECD83AF2384C8338A5C6611382732DF8B1E11ABBB7A26AE51F04D6B99394889ADEBF3C60A7AD2BD523D2772EA7CCED9573694F5351F94A0530BBA352D18718A9B295DEF32076311A3357BCDA69461FE13199016B98A6DB2151739BF78C3C6E17D3716786294B1CA31B7E6141253D4BEBE089BA5F47F097017
	B81E24408527D39CE77E4D61D07F431153650348A9B19CB975486C2A1DD604ABB95D6763068D0F8B5D6B896F4F719C85AEB8ED64B873D7C3D854634869A93E5639F9B4546551083D379FF91691F6154F316941EF980D5C5F7D3C2B5C9D23155C1772F1077FE841B11570159C8FD16002FB0FAC5C7F9B89E199CF503B4957BFDD608FA06C15EC649EC6D87EBE64BE1E305F6E43F8B389BB7E85461BCD582F785AE2BC7813887B023F6BA991701D0EC0DB3C6EF34C2EF41FC5A8ED71DBBE4EB894212D6F6FF0DC9F21
	2D6F76F22C6FD7A1ECE68435B5FCEDB1F38C34BFE3E8BE7C904EADD790766B43FCEE997C0D04159D4139EA97E10BB996G7EB0428E9FC51BC5917661D10CB713302AB20CB7F78C1DE5646BB1FCDF9AE01B4772EF316A1E93B6B77464EA31F6A642372A6EB9E1654F6E16E661222DDA7CB135D56F3F254B995479EF2298464ABE25FCA61CDE1A3A407C0964367C9AF20B9D0B5CBEB5EA397553A939990DDAEE1F4AF0F976500EB213C86623F62D4C1757A8E54E36EBE5FE38C6A9334C09B23347214C1BCE2D4CFDAA
	1EE1CE2D4CF95246229F71FEB31B872533860A8EA21BBFAA9B4F5BE457E7BAF16F6BEEE97B0F24AEA370C88447E4EC980FFD1AD9AD3C1C8CD64BF5C31D3D49E0395EE4E8E9EDB2008243F53349E6315D309BEED82C7596D3E1E9DE0365066412407BBD22EC3757D359867EC697BC6FDA27E56BB7DE7671463B3FC3D15BDEDCADEC76D9AB553B71E8330B2DDA1BE5294A413FD5EB33752AF248E8C3191B1FC419475B34B2DFC8D64AF434E9E5AE15B245B77B5F7358F8D83BDF504E6ED02A961DBC715D4E4DE56FFF
	B5682D875771A43EDF6B7310D3B0F1BA1C275449ECDC2E2A9BF372341C5FD059610F2AF0D6AE3DD569F37335F9BAA3438557049C7E5D84BE8960D691571F66127DA6A05724DCAD57E8D5DB3047036BE49517DA8F2EA5A94A70BEF9DA2E16554A36E46BB74A33A4957FD23F967715EA6BDB7584D14F2ECEF057B7F91EEF4FDEACC32876F9D06D73205A67192E3B724C7596A84F73ADD62BACCD34D15C99E623C0109581EE6684ECC3338AF0CDEBBEE16B8B31CF38EC82FEF79696323D201993215D872787E7845786
	AE88DC44C45ADF4163ED16F71244CE94F70930FBC2581DA76C7E91916FFA21C34AE36979FBB5D1B147B351F30C20F545B20E318BF15BA6AA771C77603AD959639D5D4A7B28539EA08B4B954B7FC1F171779844D86DF6A50E5596G27C4F09BA2C313F340D5C7E23BB625C8B917529149672D2BF06E50CA717E289AFF0BB15DDAB6650573C6347F6FF9C8B39F5CB2F01BFF8CE588CE481DC3F36435008999B5F75DB26695230CFA88EF839782AE965C821211D20AB2D6100C01775CB242CE210C7427C213F700AB83
	E7G5771940DCFCFFB6665776DEE99AF533E27B0C89385EEAE38A4F0991310474AF21AFB1E04BC8E7CC32743EFD07F8A6518DF2419D401E9AAA661F87A748954D583D8BDB8FFF09160E6112E3BA7B1DD82693A4D2E36157D2AB77838E759A4512F7A0870C59C4F08F263223F5544F1664F013020A85C03FD69BEEBCF1601BFE3B2741D41D500B3CE467DB673DA8AF48F58FBDDEBF7D41BDB4DD645969A711E6770326FGD9C1136937830A593AF4987933391F5D5FA37CD1C55FF5BD15496EACCC6E9F7E4EBA3C2D
	CFAB43B669DEA9762CEFFAD6A4F29D3BD78976B7E3A6E33B98D7027D4D59E41FE7CBD9DD19BFD97C96CEA2BCD326C09D87F7E88A460963F1585DC66972AE9FF6CFCF4D94F11F100873D529D14313491B6F43D6CA477431E722A6FE0A6421366F8D8FDD298F30193776CC5BCE323A2B7C4D83E1533E49EE1B06E4227D9CE41B5E3BD97D6EGFF9838D8F0F16096D178909E1E9E8DF5BFDA1C0956C70B7B1FC478565DD8D796C999DA3E9DDE30B8AF983AD88FFB95100E75BA6F3FEB614DF63E3E8DE91A341BF4AF6E
	77EC736377E39B53980DED4CBC6615E9CE6F73EC4FFA50EF9D36C5E33DCD4FC23B87C4235D16F3AC987CB10469781836D65E07A5303ED98F73AEB2F9DD0997932D7C9E07BF140E2E5653D4C57CCD3A30ECCF7BC6E7232D6613FDB37871DF917E6DBEEA63A97C1B356E7006AF30FF39AC9A3FE30947BC752CB90E7152E3B05E6EE31E6D4FF016D43EAB6F9E47F1760E98AC0B374AD9DB1CCD7EA55C4F6E9A13FB404B3FEB3B49E47B2A339BF5833E330E87374D69651E36F9AB5DED775AC3E8CFBD59F349E14FB439
	6B5CE9EE9E4132B4D01A91A51EE57549D16AE745E01973E5B9991EDD063ABBA87CA8EB7624FDE67ABC5F6B4D09F1417F6D46F1E8E3E33CAEF0273153BB4302E499C549670EEDCBD359D374973E7E9F762DD94838014BFC9A5AFFF055606A1F46BE437F36B20E72A22C93AE17292E112FD6E10701DDF0660659C224485BCA1FE710BB2FB3CDBE4F5AF61E6E0473E153095F66ADF57941730E783B675C437F326E8EE7FFE753DCD57CAF2DFD867FCBEB7F7440E578CBEBEFEF5FAB1ED9DB2E187642F3466E0D81AD6C
	5996104D1FD97A1EDB525A336E6C4D4A02704B7CAF2D75867FCBF372F254E50D13FAF7BC436A4BAD970E875B407EECDEA3659939564567BCD2DA14F39EEBF46EF98FA3440BFC467DBE0AFA326BCF4A716306DD5F2E5D54C9BD0913E9D749277E7C52D4D1C7967F1955110229501784D78B4E8A4E8FDC58D42CA346D6B63FF342F16322D9EC56BAE732199A786DDDD0C79CB74EB601CF3934A84BECDFEB774AFACB5B4326937D694779BCC56CD46D394D899C1FB5D5FBEE33A8974FED4E053062D8DA334A6B1E7E2B
	DCDEC82CD63F09639131DA7D0BF3D0FF9404ED1E067ADF4EFDD87D22ACBFE2D394F20F4364D6B6AFF045F4536B4DF0DF1ADACD972E5FB45739EACD8D4433021FE3ED1B2665FF16637E53347CFFF9867987C158B2F0E2CD3026405B1A605569B0F61A4E67D58AB3FD6445B1DD3927E360BA3EDEAB6E6B1218DF169B43FB4F3FDA479A78DAF31B6B3445EC2D27FB6CFF33133DAB748BA3A477596DCA5C7DDB97B33EF64B465FCCD938510D954A7BF28717AA7398B7DD5CA115B49D671B06D56237A1E5864EB73DD829
	1DEFE23FED132743792277AA3573CD7BD5617B2A3473CDCF28427FD6251DEF5A2D1AF71DD1457A88AEE70BD9349F918D4A76E3411C7ACE6FB2A91BA157196533F15352D4B947FEG7399C079FC7B00B61FDF2BF8EEAD72BDFFFC36C81B4FB12A7076A2EDBE51CE323C0AB01F0263FB454871F9623837C44B7132CA4757A5DA0EC3D5617DCA349C9FD7054FA851F24CD7F15CDE2A667853D348F185F1CCBA2565182F5231673416E309AA3C76341663B8557CE61F53DA0E1B1215F34ECB282FDAB093B9EDAF774D69
	DC39C79DD6693CDF2E6514268A1FDC2E65F4C2E5370D95E8B761FF29C2EDC76B89645CA8B97F7FGD0CB87886C8962C1C6A6GGG00GGD0CB818294G94G88G88G02F3DCA66C8962C1C6A6GGG00GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG00A6GGGG
**end of data**/
}
/**
 * Return the Button1 property value.
 *
 * @param
 * @return java.awt.Button
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton1() {
	if (ivjButton1 == null) {
		try {
			ivjButton1 = new java.awt.Button();
			ivjButton1.setName("Button1");
			ivjButton1.setBounds(265, 26, 211, 23);
			ivjButton1.setLabel("Schliessen");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton1;
}
/**
 * Return the Button2 property value.
 *
 * @param
 * @return java.awt.Button
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton2() {
	if (ivjButton2 == null) {
		try {
			ivjButton2 = new java.awt.Button();
			ivjButton2.setName("Button2");
			ivjButton2.setBounds(21, 20, 82, 43);
			ivjButton2.setEnabled(true);
			ivjButton2.setLabel("MakeStep");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton2;
}
/**
 * Return the Button3 property value.
 *
 * @param
 * @return java.awt.Button
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton3() {
	if (ivjButton3 == null) {
		try {
			ivjButton3 = new java.awt.Button();
			ivjButton3.setName("Button3");
			ivjButton3.setBounds(23, 74, 80, 42);
			ivjButton3.setEnabled(true);
			ivjButton3.setLabel("MakeNStep");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton3;
}
/**
 * Return the Button4 property value.
 *
 * @param
 * @return java.awt.Button
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton4() {
	if (ivjButton4 == null) {
		try {
			ivjButton4 = new java.awt.Button();
			ivjButton4.setName("Button4");
			ivjButton4.setBounds(144, 21, 81, 39);
			ivjButton4.setEnabled(true);
			ivjButton4.setLabel("Reset");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton4;
}
/**
 * Benutzt globale Objekte			:
 * Aufgerufen von					:
 * Ruft auf							:
 * 
 * @param
 * @return
 * @version V1.00 vom
 */
CheckboxMenuItem getCBMI1() {
	CheckboxMenuItem wrapper = getCheckboxMenuItem1();
	return wrapper;
	
}
/**
 * Return the Checkbox1 property value.
 *
 * @param
 * @return java.awt.Checkbox
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox1() {
	if (ivjCheckbox1 == null) {
		try {
			ivjCheckbox1 = new java.awt.Checkbox();
			ivjCheckbox1.setName("Checkbox1");
			ivjCheckbox1.setBounds(32, 149, 131, 23);
			ivjCheckbox1.setEnabled(true);
			ivjCheckbox1.setLabel("Synchroner Betrieb");
			ivjCheckbox1.setState(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox1;
}
/**
 * Return the Checkbox2 property value.
 *
 * @param
 * @return java.awt.Checkbox
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox2() {
	if (ivjCheckbox2 == null) {
		try {
			ivjCheckbox2 = new java.awt.Checkbox();
			ivjCheckbox2.setName("Checkbox2");
			ivjCheckbox2.setBounds(288, 149, 136, 23);
			ivjCheckbox2.setEnabled(true);
			ivjCheckbox2.setLabel("Asynchroner Betrieb");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox2;
}
/**
 * Return the CheckboxMenuItem1 property value.
 * @return java.awt.CheckboxMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */

private CheckboxMenuItem getCheckboxMenuItem1() {
	if (ivjCheckboxMenuItem1 == null) {
		try {
			ivjCheckboxMenuItem1 = new java.awt.CheckboxMenuItem();
			ivjCheckboxMenuItem1.setEnabled(false);
			ivjCheckboxMenuItem1.setLabel("Trace");
			ivjCheckboxMenuItem1.setState(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckboxMenuItem1;
}
/**
 * Return the CheckboxMenuItem2 property value.
 * @return java.awt.CheckboxMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private CheckboxMenuItem getCheckboxMenuItem2() {
	if (ivjCheckboxMenuItem2 == null) {
		try {
			ivjCheckboxMenuItem2 = new java.awt.CheckboxMenuItem();
			ivjCheckboxMenuItem2.setEnabled(true);
			ivjCheckboxMenuItem2.setLabel("Benutzerentscheidung");
			ivjCheckboxMenuItem2.setState(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckboxMenuItem2;
}
/**
 * Return the CheckboxMenuItem3 property value.
 * @return java.awt.CheckboxMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private CheckboxMenuItem getCheckboxMenuItem3() {
	if (ivjCheckboxMenuItem3 == null) {
		try {
			ivjCheckboxMenuItem3 = new java.awt.CheckboxMenuItem();
			ivjCheckboxMenuItem3.setEnabled(true);
			ivjCheckboxMenuItem3.setLabel("Synchroner Betrieb");
			ivjCheckboxMenuItem3.setState(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckboxMenuItem3;
}
/**
 * Return the CheckboxMenuItem4 property value.
 * @return java.awt.CheckboxMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private CheckboxMenuItem getCheckboxMenuItem4() {
	if (ivjCheckboxMenuItem4 == null) {
		try {
			ivjCheckboxMenuItem4 = new java.awt.CheckboxMenuItem();
			ivjCheckboxMenuItem4.setLabel("Programmentscheidung");
			ivjCheckboxMenuItem4.setState(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckboxMenuItem4;
}
/**
 * Return the CheckboxMenuItem5 property value.
 * @return java.awt.CheckboxMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private CheckboxMenuItem getCheckboxMenuItem5() {
	if (ivjCheckboxMenuItem5 == null) {
		try {
			ivjCheckboxMenuItem5 = new java.awt.CheckboxMenuItem();
			ivjCheckboxMenuItem5.setLabel("Breakpoint");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckboxMenuItem5;
}
/**
 * Return the CheckboxMenuItem6 property value.
 * @return java.awt.CheckboxMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private CheckboxMenuItem getCheckboxMenuItem6() {
	if (ivjCheckboxMenuItem6 == null) {
		try {
			ivjCheckboxMenuItem6 = new java.awt.CheckboxMenuItem();
			ivjCheckboxMenuItem6.setEnabled(false);
			ivjCheckboxMenuItem6.setLabel("Monitor an");
			ivjCheckboxMenuItem6.setState(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckboxMenuItem6;
}
/**
 * Return the ContentsPane property value.
 *
 * @param
 * @return java.awt.Panel
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Panel getContentsPane() {
	if (ivjContentsPane == null) {
		try {
			ivjContentsPane = new java.awt.Panel();
			ivjContentsPane.setName("ContentsPane");
			ivjContentsPane.setLayout(null);
			getContentsPane().add(getButton2(), getButton2().getName());
			getContentsPane().add(getButton3(), getButton3().getName());
			getContentsPane().add(getTextField1(), getTextField1().getName());
			getContentsPane().add(getCheckbox1(), getCheckbox1().getName());
			getContentsPane().add(getCheckbox2(), getCheckbox2().getName());
			getContentsPane().add(getButton4(), getButton4().getName());
			getContentsPane().add(getButton1(), getButton1().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjContentsPane;
}
/**
 * Return the FileDialog1 property value.
 * @return java.awt.FileDialog
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private FileDialog getFileDialog1() {
	if (ivjFileDialog1 == null) {
		try {
			ivjFileDialog1 = new java.awt.FileDialog(this);
			ivjFileDialog1.setName("FileDialog1");
			ivjFileDialog1.setLayout(null);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjFileDialog1;
}
/**
 * Return the Frame1 property value.
 *
 * @param 
 * @return java.awt.Frame
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
Frame getFrame1() {
	if (ivjFrame1 == null) {
		try {
			ivjFrame1 = new java.awt.Frame();
			ivjFrame1.setName("Frame1");
			ivjFrame1.setMenuBar(getFrame1MenuBar());
			ivjFrame1.setLayout(new java.awt.BorderLayout());
			ivjFrame1.setBounds(180, 88, 538, 217);
			ivjFrame1.setTitle("Willkommen im Simulator");
			getFrame1().add(getContentsPane(), "Center");
			// user code begin {1}
			ivjFrame1.setSize(538,240);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjFrame1;
}
/**
 * Return the Frame1MenuBar property value.
 * @return java.awt.MenuBar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuBar getFrame1MenuBar() {
	if (ivjFrame1MenuBar == null) {
		try {
			ivjFrame1MenuBar = new java.awt.MenuBar();
			ivjFrame1MenuBar.add(getMenu4());
			ivjFrame1MenuBar.add(getMenu2());
			ivjFrame1MenuBar.add(getMenu5());
			ivjFrame1MenuBar.add(getMenu1());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjFrame1MenuBar;
}
/**
 * Return the Menu1 property value.
 * @return java.awt.Menu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Menu getMenu1() {
	if (ivjMenu1 == null) {
		try {
			ivjMenu1 = new java.awt.Menu();
			ivjMenu1.setLabel("Optionen");
			ivjMenu1.add(getCheckboxMenuItem1());
			ivjMenu1.add(getMenuSeparator2());
			ivjMenu1.add(getMenu3());
			ivjMenu1.add(getMenuSeparator3());
			ivjMenu1.add(getCheckboxMenuItem3());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenu1;
}
/**
 * Return the Menu2 property value.
 * @return java.awt.Menu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Menu getMenu2() {
	if (ivjMenu2 == null) {
		try {
			ivjMenu2 = new java.awt.Menu();
			ivjMenu2.setEnabled(true);
			ivjMenu2.setLabel("Traces");
			ivjMenu2.add(getMenuItem3());
			ivjMenu2.add(getCheckboxMenuItem6());
			ivjMenu2.add(getMenuItem2());
			ivjMenu2.add(getMenuItem1());
			ivjMenu2.add(getMenuSeparator1());
			ivjMenu2.add(getMenuItem5());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenu2;
}
/**
 * Return the Menu3 property value.
 * @return java.awt.Menu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Menu getMenu3() {
	if (ivjMenu3 == null) {
		try {
			ivjMenu3 = new java.awt.Menu();
			ivjMenu3.setLabel("Nichtdeterminismus");
			ivjMenu3.add(getCheckboxMenuItem2());
			ivjMenu3.add(getCheckboxMenuItem4());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenu3;
}
/**
 * Return the Menu4 property value.
 * @return java.awt.Menu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Menu getMenu4() {
	if (ivjMenu4 == null) {
		try {
			ivjMenu4 = new java.awt.Menu();
			ivjMenu4.setLabel("Steuerung");
			ivjMenu4.add(getMenuItem4());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenu4;
}
/**
 * Return the Menu5 property value.
 * @return java.awt.Menu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Menu getMenu5() {
	if (ivjMenu5 == null) {
		try {
			ivjMenu5 = new java.awt.Menu();
			ivjMenu5.setLabel("Breakpoint");
			ivjMenu5.add(getCheckboxMenuItem5());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenu5;
}
/**
 * Return the MenuItem1 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuItem1() {
	if (ivjMenuItem1 == null) {
		try {
			ivjMenuItem1 = new java.awt.MenuItem();
			ivjMenuItem1.setEnabled(false);
			ivjMenuItem1.setLabel("Speichern ...");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem1;
}
/**
 * Return the MenuItem2 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuItem2() {
	if (ivjMenuItem2 == null) {
		try {
			ivjMenuItem2 = new java.awt.MenuItem();
			ivjMenuItem2.setEnabled(true);
			ivjMenuItem2.setLabel("Laden ...");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem2;
}
/**
 * Return the MenuItem3 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. *
*/
private MenuItem getMenuItem3() {
	if (ivjMenuItem3 == null) {
		try {
			ivjMenuItem3 = new java.awt.MenuItem();
			ivjMenuItem3.setLabel("Neu");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem3;
}
/**
 * Return the MenuItem4 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuItem4() {
	if (ivjMenuItem4 == null) {
		try {
			ivjMenuItem4 = new java.awt.MenuItem();
			ivjMenuItem4.setLabel("Schliessen");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem4;
}
/**
 * Return the MenuItem5 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuItem5() {
	if (ivjMenuItem5 == null) {
		try {
			ivjMenuItem5 = new java.awt.MenuItem();
			ivjMenuItem5.setEnabled(false);
			ivjMenuItem5.setLabel("Beenden");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem5;
}
/**
 * Return the MenuSeparator1 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuSeparator1() {
	if (ivjMenuSeparator1 == null) {
		try {
			ivjMenuSeparator1 = new java.awt.MenuItem();
			ivjMenuSeparator1.setLabel("-");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuSeparator1;
}
/**
 * Return the MenuSeparator2 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuSeparator2() {
	if (ivjMenuSeparator2 == null) {
		try {
			ivjMenuSeparator2 = new java.awt.MenuItem();
			ivjMenuSeparator2.setLabel("-");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuSeparator2;
}
/**
 * Return the MenuSeparator3 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuSeparator3() {
	if (ivjMenuSeparator3 == null) {
		try {
			ivjMenuSeparator3 = new java.awt.MenuItem();
			ivjMenuSeparator3.setLabel("-");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuSeparator3;
}
/**
 * Benutzt globale Objekte			:
 * Aufgerufen von					:
 * Ruft auf							:
 * 
 * @param
 * @return
 * @version V1.00 vom
 */
MenuItem getMI3() {
	MenuItem wrapper = getMenuItem3();
	return wrapper;
}
/**
 * Return the TextField1 property value.
 *
 * @param
 * @return java.awt.TextField
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField1() {
	if (ivjTextField1 == null) {
		try {
			ivjTextField1 = new java.awt.TextField();
			ivjTextField1.setName("TextField1");
			ivjTextField1.setText("1");
			ivjTextField1.setBounds(146, 82, 89, 26);
			ivjTextField1.setEnabled(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField1;
}
/**
 * New method, created by HH on INTREPID
 * @param arg1 absyn.Path
 * @param arg2 absyn.Path
 *
 * Vergleicht zwei Pfade darauf, ob arg1 in arg2 enthalten und arg2 groesser als arg1 ist
 *
 * Benutzte globale Objekte :
 * Aufgerufen von			: killLevelConflicts()
 *							  removePathInListLinear()
 * ruft auf					: -----
 *
 * @param arg1 absyn.Path
 * @param arg2 absyn.Path
 * @return result boolean
 * @version V1 vom 14.01.1999
 * @version V4.00 vom 30.01.1999 komplette Umstrukturierung !
 * @version V4.01 vom 07.02.1999 von 'smaller' nach 'greaterAndContains' geaendert (Logikdreher) 
 */
 
private boolean greaterAndContains(Path arg1, Path arg2)
{
	boolean step1 = false;
	boolean step2 = false;
	
	boolean result = false;		// Setze Default
	boolean loop = true;
	Path argcopy1, argcopy2; 	// "Kopien" der Argumente anlegen, weil sie
								// verändert werden.
								
	argcopy1 = arg1;
	argcopy2 = arg2;

	// Sicherheitsabfragen : 
	// Wir gehen davon aus, das es keinen Path mit fuehrender "null" gibt,
	// der im "tail" etwas anderes als eine "null" stehen hat.
	
	if ((argcopy1.head == null) && (argcopy2.head == null)){
		return(false);
	}
	
	if ((argcopy1.head != null) && (argcopy2.head == null)){
		return(false);
	}
	
	if ((argcopy1.head == null) && (argcopy2.head != null)){
		return(false);
	}

	
	if ((argcopy1 != null) && (argcopy2 != null))	// 1.) Wenn denn beide ungleich "null" sind ...
	{
		while (loop == true){
			if (((String)(argcopy1.head)).equals((String)(argcopy2.head))) {
				// Bisher ist alles gleich gewesen !
				
				if (argcopy1.tail != null){
					argcopy1 = argcopy1.tail;
					step1 = true;
				}
				else{
					step1 = false;
				}
								
				if (argcopy2.tail != null){
					argcopy2 = argcopy2.tail;
					step2 = true;
				}
				else{
					step2 = false;
				}

				if ((step1 == true) && (step2 == true)){
									// Nichts passiert, weiter mit dem naechsten Vergleich !
				}
				
				if ((step1 == false) && (step2 == false)){
									// Die beiden Paths sind gleichlang =>
					loop = false;	// Schleife verlassen,
					result = false;	// Ergebnis ist negativ !

				}
				
				if ((step1 == true) && (step2 == false)){
									// Path arg2 ist enthalten in arg1, aber arg1 ist laenger =>
					loop = false;	// Schleife verlassen,
					result = false;	// Ergebnis ist negativ ! 
				
				}
				
				if ((step1 == false) && (step2 == true)){
									// Path arg1 ist enthalten in Path arg2, aber arg2 ist laenger =>
					loop = false;	// Schleife verlassen,
					result = true; // Ergebnis ist positiv !

				}
				
			}
			else{	
								// Unterschied festgestellt.
				loop = false;	// Schleife beenden
				result = false;	// Ergebnis festhalten 
			}
		}

	}

												// Bei den folgenden Vergleichen muss natuerlich
												// mit den Originalen verglichen werden, da die Kopien
												// eventuell schon verändert sind.
	
	if ((arg1 == null) && (arg2 != null)){		// 2.) Wenn nur einer ungleich "null" ist :
		result = false;
	}

	if ((arg1 != null) && (arg2 == null)){		// 3.) Wenn nur einer ungleich "null" ist:
		result = false;
	}

	if ((arg1 == null) && (arg2 == null)){		// 4.) Wenn beide gleich "null" sind :
		result = false;
	}
	
	
	return result;
}
/**
 *
 * Liefert die Anzahl der von "null" ungleichen Elemente der uebergebenen BvarList zurück.
 *
 * Benutzt globale Objekte			: SDaten.bvars
 * Aufgerufen von					: simu(,,)
 * Ruft auf							: --------
 * @return int
 * @param arg1 absyn.BvarList
 * @version 3.00 vom 17.01.1999
 */
private int groesse(BvarList arg1)
{
	int result = 0;
	BvarList copy;					// Wir arbeiten auf der Kopie !
	copy = arg1;
	if (copy != null)				// Die Liste koennte ja auch leer sein ...
	{
		while (copy.tail != null)	// Standardzaehlweise
		{
			result++;
			copy = copy.tail;
		}
		if (copy.head != null){		// Die Liste koennte dummerweise (null,null) sein, wer weiss ?!
			result++;
		}
	}

	if (debug == true){
		System.out.println("groesse vermeldet eine Groesse von : "+result+" fuer die BvarList : bvars");
	}
	return result;
}
/**
 *
 * Liefert die Anzahl der von "null" ungleichen Elemente der uebergebenen BvarList zurück.
 *
 * Benutzt globale Objekte	 		: SDaten.events
 * Aufgerufen von					: simu();
 * Ruft auf							: ----
 * @return int
 * @param arg1 absyn.SEventList
 * @version V3.00 vom 17.01.1999
 */
private int groesse(SEventList arg1) {
	int result = 0;
	SEventList copy;				// Wir arbeiten auf der Kopie !
	copy = arg1;
	if (copy != null)				// Die Liste koennte ja auch leer sein ...
	{
		while (copy.tail != null)	// Standardzaehlweise
		{
			result++;
			copy = copy.tail;
		}
		if (copy.head != null){		// Die Liste koennte dummerweise (null,null) sein, wer weiss ?!
			result++;
		}
	}

	if (debug == true){
		System.out.println("groesse vermeldet eine Groesse von : "+result+" fuer die SEventList : events");
	}
	return result;
}
/**
 * Interface-Methode zur GUI.
 * Eigentlich mehr zu Debugging-Zwecken. Wird von der GUI als Schnittstelle zur Verfuegung
 * gestellt, um Ausgaben im GUI-Fenster zu machen.
 * @param SimuMessage java.lang.String
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: UserInterface::button1_ActionEvents()
 							  UserInterface::button2_ActionEvents()
 							  find_state(), get_guard(), 
 							  Simu(Statechart, Editor, GUIInterface
 * Ruft auf					: GUI::userMessage
 *
 * @param simuMessage String
 * @return 
 * @version V1 vom 14.01.1999
 */
protected void guiOutput(String simuMessage) {
	gui.userMessage(simuMessage);
}
/**
 * Erstellt einen Handclone eines Vectors in dem eine bvList erwartet wird.
 * @return java.util.Vector
 * @param arg1 java.util.Vector
 */
Vector handclonebvList(Vector arg1) {
	int max = 0;
	Vector main = new Vector();
	Vector neu = new Vector();
	BvarTab bvTab;
	
	main = arg1;		// übergebenen Vektor übernehmen
	max = main.size();
	
	// nun den Handclone füllen :
	for (int i = 0; i < max; i++){
		bvTab = (BvarTab)(main.elementAt(i));
		boolean neuBool = bvTab.bWert;
		BvarTab dazu = new BvarTab(bvTab.bName, neuBool);
		neu.addElement(dazu);
	}
		
	return neu;
}
/**
 *
 * Diese Methode bekommt einen Path (arg1) uebergeben und erstellt einen Clone von arg1
 * per Hand. Der zurueckgelieferte Path hat zwar die gleichen String-Objekte zum Inhalt
 * ist aber ein von arg1 verschiedenes Objekt.
 * Der Code ist zudem gegen jede möglich Form von NullPoiterExeptions gefeit.
 *
 * Benutzt globale Objekte			: ------
 * Aufgerufen von					: makeTrans()
 * Ruft auf							: ------
 * 
 * @params
 * @returns
 * @version V4.00 vom 24.01.1999
 * @version V4.01 vom 30.01.1999 debug-Levels, Path.append liefert Path ZURUECK !
 *
 * @return absyn.Path
 * @param arg1 absyn.Path
 */
private Path handClonePath(Path arg1)
{
	Path copy;
	Path back = null;
	copy = arg1;

	if (debug2c == true) {
		System.out.println("handClone: Start !");
	}
	
	if (copy != null) 				// 1. Es soll sich nicht um einen null-Path handeln.
	{
		if (copy.head != null)			// 2. Es soll sich nicht um einen (null,null)-Path handeln.
		{
			back = new Path(copy.head, null);
			if (copy.tail != null) {	// Aenderung fuer Fehlermeldung in ERRORLIST ***********************************
				copy = copy.tail;
			
				if (debug4 == true) {
					System.out.println("handClone: Init mit: "+copy.head);
				}
	
				while (copy.tail != null)		// Standartabbauweise
				{
					if (debug4 == true) {
						System.out.println("handClone: Will etwas anhaengen : "+copy.head);
					}
					back = back.append(copy.head);
					copy = copy.tail;				
				}
				if (debug4 == true) {
						System.out.println("handClone: Will Rest anhaengen : "+copy.head);
				}
				back = back.append(copy.head);			// Rest der Standartabbauweise
			}
		}
		else
		{
			back = new Path(null, null);	// Rest von 2.
		}
	}
	if (debug4 == true) {
		System.out.println("handClone: Schliesslich : "+returnDottedPath(back));
	}
	if (debug2c == true) {
		System.out.println("handClone: Ende !");
	}
	return back;
}
/**
 * Erstellt einen Clone eines Vectors, der eine seList enthält.
 * @return java.util.Vector
 * @param arg1 java.util.Vector
 */
Vector handcloneseList(Vector arg1) {
	int max = 0;
	Vector main = new Vector();
	Vector neu = new Vector();
	SEventTab seTab;
	
	main = arg1;		// übergebenen Vektor übernehmen
	max = main.size();
	
	// nun den Handclone füllen :
	for (int i = 0; i < max; i++){
		seTab = (SEventTab)(main.elementAt(i));
		boolean neuBool = seTab.bWert;
		SEventTab dazu = new SEventTab(seTab.bName, neuBool);
		neu.addElement(dazu);
	}
		
	return neu;
}
/**
 * Called whenever the part throws an exception.
 *
 * @param exception java.lang.Throwable
 * @return
 * @version V1 vom 14.01.1999
 */
private void handleException(Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
/**
 * Initializes connections
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() {
	// user code begin {1}
	// user code end
	getButton4().addMouseListener(this);
	getButton2().addMouseListener(this);
	getButton3().addMouseListener(this);
	getButton1().addMouseListener(this);
	getCheckbox1().addItemListener(this);
	getCheckbox2().addItemListener(this);
	getMenuItem4().addActionListener(this);
	getMenuItem3().addActionListener(this);
	getCheckboxMenuItem3().addItemListener(this);
	getCheckboxMenuItem2().addItemListener(this);
	getCheckboxMenuItem4().addItemListener(this);
	getMenuItem5().addActionListener(this);
	getCheckboxMenuItem5().addItemListener(this);
	getCheckboxMenuItem6().addItemListener(this);
	getMenuItem2().addActionListener(this);
	getMenuItem1().addActionListener(this);
}
/**
 * Initialize the class.
 *
 * @param
 * @return 
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	// user code begin {1}
	// user code end
	setName("Simu");
	setLayout(new java.awt.BorderLayout());
	setSize(66, 31);
	initConnections();
	// user code begin {2}
	// user code end
}
/**
 *
 * Diese Methode initialisiert die InListe. Sie bekommt als Parameter den bisher 
 * ( rekursiv ) erreichten Pfad und den bisher ( rekursiv ) erreichten State.
 * Beim Grundaufruf aus firstStep() heraus werden demzufolge der Name des äußersten
 * States und dieser äußerste State selbst als Parameter an initInList() übergeben.
 * 
 * Benutzt globale Objekte			: InList
 * Aufgerufen von					: firstStep(), initInList()
 * Ruft auf							: initInList(), killLastElement()
 *
 * @param x absyn.State
 * @param y absyn.Path
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.37ß vom 28.01.1999
 * @version V4.50 vom 30.01.1999
 *
 *
 * Changes from previous versions: 
 *	- 	'erste' Substates von And-States nicht in die InListe mit uebernehmen. 
 *		Diese koennen nicht gehighlighted werden
 *	- 	es werden nur noch die Substates in die Liste eingefuehrt, die aktuellen 
 *		States aber nicht, da dies einen doppelten Eintrag zur Folge haette.
 *	-	Debug-Infos hinzugefuegt.
 *	-	Der erste "main"State wird nicht mehr in die Liste mit eingefuegt (Logisch
 *		da dieser erste State nicht gehighlighted werden muss).
 */
private void initInList(State x, Path y) {
	Path y_help = null;
	Path y_h1 = null;
	Path y_h2 = null;
	StateList liste;
	Statename outName;
	String compareName;
	StatenameList nameList;
	State targetState = null;
	boolean found = false;

	y_h2 = y;
	
	if (debug==true){
		System.out.println("initInList() aufgerufen !");
	}

	if (x instanceof Basic_State) {	// Wenn es sich um einen Basic_State handelt, wird
									// der Pfad von x in die InListe eingetragen. Rekursion
									// ist dann nicht notwendig.
		outName = ((Basic_State)x).name;
		if (debug1 == true) {
			System.out.println("InitInList: BASIC-State "+outName.name+" erkannt !");
		}
		/*InList.addElement(y);
		if (debug2 == true) {
			System.out.println("initInList: InListe um "+outName.name+" ergaenzt.");
		}*/
	}									
	
	if (x instanceof And_State) {		// Handelt es sich um einen And_State, muss zuerst der
										// And_State selbst und dann alle Substates aus der
										// Substateliste in die InList eingefügt werden.
		if (debug1 == true) {
 			outName = ((And_State)x).name;
			System.out.println("InitInList: AND-State "+outName.name+" erkannt !");
		}
		//InList.addElement(y_h2);					
		liste = ((And_State)x).substates;		
		while (liste.tail != null) {            
			y_h1 = y_h2.append(liste.head.name.name);
			y_h2 = y_h1;
			/*InList.addElement(y_h2);
			if (debug2 == true) {
				System.out.println("initInList: InListe um "+liste.head.name.name+" ergaenzt.");
			}*/
	
			initInList(liste.head, y_h2);		// Ausserdem muss für diese (Substates) ein rekursiver 
			y_help = killLastElement(y_h2);		// Aufruf von initInList erfolgen.
			y_h2 = y_help;
			liste = liste.tail;
		}
		
		y_h1 = y_h2.append(liste.head.name.name); // Weil in der Schleife nur auf (list.tail ==null) geprueft
		y_h2 = y_h1;
		/*InList.addElement(y_h2);			// werden kann, muss das letzte Elt. separat extrahiert werden.
		if (debug2 == true) {
			System.out.println("initInList: InListe um "+liste.head.name.name+" ergaenzt.");
		}*/

		initInList(liste.head, y_h2);				 
		y_help = killLastElement(y_h2);		
		y_h2 = y_help;
		
	}
	
	if (x instanceof Or_State) {				// Handelt es sich um einen Or_State, muss zuerst der
		outName = ((Or_State)x).name;
		if (debug1 == true) {
			System.out.println("InitInList: OR-State "+outName.name+" erkannt !");
		}

		/*InList.addElement(y_h2);			// Or_State selbst und dann der dazugehörige Default-State
		if (debug2 == true) {
			System.out.println("initInList: InListe um "+outName.name+" ergaenzt.");
		}
		*/
		
		nameList = ((Or_State)x).defaults;		// in die InListe eingefügt werden.
		compareName = nameList.head.name;
		liste = ((Or_State)x).substates;
		found = false;										// zur Sicherheit
		while (found == false) {							// Die Schleife dient zum identifizieren
			if (liste.head.name.name.equals(compareName)) {	// des Default-States.
				targetState = liste.head;
				found = true;
			}
			else {
				liste = liste.tail;
			}
		}
		y_h1 = y_h2.append(compareName);
		y_h2 = y_h1;

		InList.addElement(y_h2);			// Hier wird der Pfad des Default_States eingefügt.
		if (debug2 == true) {
			System.out.println("initInList: InListe um "+liste.head.name.name+" ergaenzt.");
		}

		initInList(targetState, y_h2);		// Für den Default_State muss ebenso ein rekursiver 
		y_help = killLastElement(y_h2);		// Aufruf von InitInList erfolgen.
		y_h2 = y_help;
	} 
}
/**
 * Nimmt die Eintraege in die EnteredListe rekursiv vor.
 * ACHTUNG : Der an find_state() uebergebene Path wird zerhackstueckt
 *
 * Benutzte globale Objekte : EnteredList
 * Aufgerufen von			: inMethod(), inMethodeInit(),
 * Ruft auf					: find_state(), inMethod(), 
 							  absyn.Path::append()
 *
 * @param pfad absyn.Path
 * @param state absyn.State
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999
 * @version V4.01 vom 31.01.1999 debug-Level Aufrufe korrigiert
 * @version V4.01 vom 07.02.1999 debug-Methoden, Basic-State darf sich NICHT selbst eintragen !
 */
 

private void inMethod(Path arg1, State state) {
	Path tempPfad = null;
	State tempState = null;
	Statechart tempStatechart = null;
	boolean alreadyIn = false;
	Path pfad = handClonePath(arg1);
	Path pfadclone = null;

	// Variablen fuer den OR-Teil :
	StatenameList nameList = null;
	String compareName = null;
	boolean found = false;
	StateList liste;
	
	tempPfad = handClonePath(pfad);
	tempStatechart = SDaten;

	if (debug_inMethod == true){	
		System.out.println("inMethod : Start mit "+returnDottedPath(pfad));
	}

	// Rekursion: Sollte ein State uebergeben worden sein, weise ihn zu, sonst
	// bestimme ihn	mit find_state(State, Path).
	if (state == null) {
		if (tempPfad.tail != null){
			// Der State muss tatsaechlich gesucht werden =>
			tempPfad = tempPfad.tail;	// Vorbereitung fuer find_state.
			tempState = find_state(tempStatechart.state, tempPfad);
		}
		else{
			// Der aeusserste State wurde gesucht =>
			tempState = tempStatechart.state;
		}
	}
	else {
		// Es wurde ein State mituebergeben, also ist keine Suche vonnoeten.
		tempState = state; 
	}

	// Wenn der bestimmte State ein Basic_State ist, trage ihn in die Liste ein 
	if (tempState instanceof Basic_State) {

		/*Path copyPath = handClonePath(pfad);
		EnteredList.addElement(copyPath);	// Natuerlich nicht das Original benutzen !!
		if (debug_inMethod == true){	
			System.out.println("inMethod : Trage Basic-State "+returnDottedPath(copyPath)+" ein.");
		}*/

	}

	// Wenn der bestimmte State ein And_State ist, rufe inMethod() mit den Substates und
	// einem erweiterten Pfad (Clone) wieder auf.
	if (tempState instanceof And_State) {
		
		StateList temp1 = null;
		temp1 = ((And_State)tempState).substates;
		
		// rufe inMethod() mit den Substates und einem erweiterten Pfad (Clone) wieder auf.
		while (temp1.tail != null) {				// Standartabbauweise
			pfadclone = handClonePath(pfad);
			pfadclone = pfadclone.append(temp1.head.name.name);
			inMethod(pfadclone, temp1.head);
			temp1 = temp1.tail;		
		}
		pfadclone = handClonePath(pfad);			// Rest der Standartabbauweise
		pfadclone = pfadclone.append(temp1.head.name.name);
		inMethod(pfadclone, temp1.head);
	}
	
	// Wenn der bestimmte State ein Or_State ist, rufe inMethod() mit dem erweiterten Pfad
	// und dem bestimmten State wieder auf. 
	if (tempState instanceof Or_State) {

		// Der default-State muss natürlich in die EnteredList.
		pfadclone = handClonePath(pfad);		
		pfadclone = pfadclone.append(((Or_State)tempState).defaults.head.name);
		EnteredList.addElement(pfadclone);
		if (debug_inMethod == true){	
			System.out.println("inMethod : Trage OR-State-default "+returnDottedPath(pfadclone)+" ein.");
		}
		
		// Zunaechst muss der passende State zum default - Substate identifiziert werden :
		
		nameList = ((Or_State)tempState).defaults;		
		compareName = nameList.head.name;
		liste = ((Or_State)tempState).substates;
		found = false;										// zur Sicherheit
		while (found == false) {							// Die Schleife dient zum identifizieren
			if (liste.head.name.name.equals(compareName)) {	// des Default-States.
				tempState = liste.head;
				found = true;
			}
			else {
				if (liste.tail != null){
					liste = liste.tail;
				}
				else{
					// Die Liste ist durchsucht und der State wurde nicht gefunden.
					found = true;
					tempState=null;			// Dann wird 'null' an die uebergeben und komplett
					if (debug1 == true){	// neu gesucht.
						System.out.println("inMethod(|) : NPExep abgefangen !");
					}
				}
			}
		}
		
		// Dann erfolgt der Aufruf der inMethod mit dem default - Substate

		inMethod(pfadclone, tempState); 
	}
}
/**
 * Nimmt die Eintraege in die EnteredListe rekursiv vor.
 * ACHTUNG : Der an find_state() uebergebene Path wird abgebaut
 *
 * Benutzte globale Objekte : EnteredList
 * Aufgerufen von			: inMethod(), inMethodeInit(),
 * Ruft auf					: find_state(), inMethod(), 
 							  absyn.Path::append()
 *
 * @param pfad absyn.Path
 * @param state absyn.State
 * @return 
 * @version V4.00 vom 31.01.1999
 * @version V4.01 vom 31.01.1999 debug-Level Aufrufe korrigiert
 * @version V4.02 vom 07.02.1999 debug-Methoden
 */
 

private void inMethodInit(Path arg1, State state) {
	Path tempPfad = null;
	State tempState = null;
	Statechart tempStatechart = null;
	boolean alreadyIn = false;
	Path pfad = handClonePath(arg1);
	Path pfadclone = null;

	// Variablen fuer den OR-Teil :
	StatenameList nameList = null;
	String compareName = null;
	boolean found = false;
	StateList liste;
	
	tempPfad = handClonePath(pfad);
	tempStatechart = SDaten;

	if (debug_inMethodInit == true){	
		System.out.println("inMethodInit : Start mit "+returnDottedPath(pfad));
	}

	// Rekursion: Sollte ein State uebergeben worden sein, weise ihn zu, sonst
	// bestimme ihn	mit find_state(State, Path).
	if (state == null) {
		if (tempPfad.tail != null){
			// Der State muss tatsaechlich gesucht werden =>
			tempPfad = tempPfad.tail;	// Vorbereitung fuer find_state.
			tempState = find_state(tempStatechart.state, tempPfad);
		}
		else{
			// Der aeusserste State wurde gesucht =>
			tempState = tempStatechart.state;
		}
	}
	else {
		// Es wurde ein State mituebergeben, also ist keine Suche vonnoeten.
		tempState = state; 
	}

	// Wenn der bestimmte State ein Basic_State ist, trage ihn in die Liste ein 
	if (tempState instanceof Basic_State) {

		Path copyPath = handClonePath(pfad);
		EnteredList.addElement(copyPath);	// Natuerlich nicht das Original benutzen !!
		if (debug_inMethodInit == true){	
			System.out.println("inMethodInit : Trage Basic-State "+returnDottedPath(copyPath)+" ein.");
		}

	}

	// Wenn der bestimmte State ein And_State ist, rufe inMethod() mit den Substates und
	// einem erweiterten Pfad (Clone) wieder auf.
	if (tempState instanceof And_State) {

		// Der AND-State selbst muss in die EnteredList, wenn er nicht schon drin ist.
		Path copyPath = handClonePath(pfad);
		alreadyIn = returnPathEnteredList(copyPath);
		if (alreadyIn == false){
			EnteredList.addElement(copyPath);
			if (debug_inMethodInit == true){	
				System.out.println("inMethodInit : Trage AND-State "+returnDottedPath(copyPath)+" ein.");
			}	
		}	
		StateList temp1 = null;
		temp1 = ((And_State)tempState).substates;
		
		// rufe inMethod() mit den Substates und einem erweiterten Pfad (Clone) wieder auf.
		while (temp1.tail != null) {				// Standartabbauweise
			pfadclone = handClonePath(pfad);
			pfadclone = pfadclone.append(temp1.head.name.name);
			inMethod(pfadclone, temp1.head);
			temp1 = temp1.tail;		
		}
		pfadclone = handClonePath(pfad);			// Rest der Standartabbauweise
		pfadclone = pfadclone.append(temp1.head.name.name);
		inMethod(pfadclone, temp1.head);
	}
	
	// Wenn der bestimmte State ein Or_State ist, rufe inMethod() mit dem erweiterten Pfad
	// und dem bestimmten State wieder auf. 
	if (tempState instanceof Or_State) {

		// Der OR-State selbst muss in die EnteredList, wenn er nicht schon drin ist.
		Path copyPath = handClonePath(pfad);
		alreadyIn = returnPathEnteredList(copyPath);
		if (alreadyIn == false){
			EnteredList.addElement(copyPath);
			if (debug_inMethodInit == true){	
				System.out.println("inMethodInit : Trage OR-State "+returnDottedPath(copyPath)+" ein.");
			}		
		}	
		// Der default muss natürlich auch in die EnteredList.
		pfadclone = handClonePath(pfad);		
		pfadclone = pfadclone.append(((Or_State)tempState).defaults.head.name);
		EnteredList.addElement(pfadclone);
		if (debug_inMethodInit == true){	
			System.out.println("inMethodInit : Trage OR-State-default "+returnDottedPath(pfadclone)+" ein.");
		}
		
		// Zunaechst muss der passende State zum default - Substate identifiziert werden :
		
		nameList = ((Or_State)tempState).defaults;		
		compareName = nameList.head.name;
		liste = ((Or_State)tempState).substates;
		found = false;										// zur Sicherheit
		while (found == false) {							// Die Schleife dient zum identifizieren
			if (liste.head.name.name.equals(compareName)) {	// des Default-States.
				tempState = liste.head;
				found = true;
			}
			else {
				if (liste.tail != null){
					liste = liste.tail;
				}
				else{
					// Die Liste ist durchsucht und der State wurde nicht gefunden.
					found = true;
					tempState=null;			// Dann wird 'null' an die uebergeben und komplett
					if (debug1 == true){	// neu gesucht.
						System.out.println("inMethodInit(|) : NPExep abgefangen !");
					}
				}
			}
		}
		
		// Dann erfolgt der Aufruf der inMethod mit dem default - Substate

		inMethod(pfadclone, tempState); 
	}
}
/**
 * Method to handle events for the ItemListener interface.
 * @param e java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void itemStateChanged(java.awt.event.ItemEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getCheckbox1()) ) {
		connEtoC5(e);
	}
	if ((e.getSource() == getCheckbox2()) ) {
		connEtoC6(e);
	}
	if ((e.getSource() == getCheckboxMenuItem3()) ) {
		connEtoC10(e);
	}
	if ((e.getSource() == getCheckboxMenuItem2()) ) {
		connEtoC9(e);
	}
	if ((e.getSource() == getCheckboxMenuItem4()) ) {
		connEtoC11(e);
	}
	if ((e.getSource() == getCheckboxMenuItem5()) ) {
		connEtoC13(e);
	}
	if ((e.getSource() == getCheckboxMenuItem6()) ) {
		connEtoC14(e);
	}
	// user code begin {2}
	// user code end
}
/**
 *
 * Schneidet beim uebergebenen Pfad das letzte Element ab. 
 *
 * Benutzt globale Objekte			: ----
 * Aufgerufen von					: initInList()
 * Ruft auf							: ----
 *
 * @param arg1 absyn.Path
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.03 vom 24.01.1999
 */
private Path killLastElement(Path arg1) {

	Path help = new Path(null, null);
	int i = 0;
	
	if (debug1 == true) {
		System.out.println("KillLastElement: Aufruf erfolgt !");
	}

	if (arg1.head != null) {
		if (debug2 == true) {
			System.out.println("KillLastElement: Head != null !");
		}
		if (arg1.tail != null) {
			if (debug2 == true) {
				System.out.println("KillLastElement: Tail != null !");
			}
			help.head = arg1.head;				// Mindestens ein Teilpfad-Element muss uebernommen werden,
			help.tail = null;					// weil zumindest der Rootstatename immer uebernommen werden muss.
			arg1 = arg1.tail;
			while (arg1.tail != null) {			// Sollten noch weitere Teilpfad-Elemente vorhanden sein,
				if (debug2 == true) {
					System.out.println("KillLastElement: Schleifendurchlauf: "+i);
					i++;
				}
				help = help.append(arg1.head);	// werden sie hier bis auf das Letzte (.tail == null !)
				arg1 = arg1.tail;				// uebernommen.
			}
		}
		else {
			help = null;						// Nur zur Sicherheit (bei dem Fall arg1.tail == null)
												// Ausgangspfad bestand nur aus (head, null).
			if (debug==true){
				System.out.println("killLastElement : WARNING (help = null) !");
			}	
		}										// darf eigentlich NIE passieren !
	}
	return help;								// Rueckgabe des Ergebnisses
}
/**
 * Diese Methode beseitigt Levelkonflikte in der toDoTransList.
 *
 * In der aeusseren while-Schleife durchlaeuft die Methode die toDoTransList von hinten nach vorne 
 * mit Zaehler i, greift sich das Objekt i der toDoTransList und fuehrt in der inneren FOR-Schleife
 * greaterAndContains()-Vergleiche von Objekt i und j durch.
 * 
 *
 * Benutzt globale Objekte	: toDoTransList 
 * Aufgerufen von			: makeStep()
 * ruft auf					: greaterAndContains()
 *							  removePathInList()
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999	Korrektur (i>=0) statt (i<0), ;j>-1; statt ;j==0;
 *								  	debug-Level
 * @version V4.01 vom 06.02.1999	debug-Methoden
 */
private void killLevelConflicts() {
	int i,j;
	Object t1;						// Temporaere Hilfsobjekte fuer die aeussere Schleife
	Object u1;						// Temporaere Hilfsobjekte fuer die innere Schleife
	Path p1 = null;					// Pfadreferenzen aeussere Schleife
	Path q1 = null;					// Pfadreferenzen innere Schleife
	boolean kill = false;

	if (debug_killLevelConflicts == true) {
		System.out.println("killLevelConflicts: Start !");
	}

	
	i = toDoTransList.size();
	i--;							// Vektor beginnt bei 0
	while (i >= 0) {				// Korrektur V4.00
		t1 = toDoTransList.elementAt(i);
		p1 = ((TransTab)t1).transition.tteSource;
		for (j=((toDoTransList.size())-1); j > -1; j--) {	// Korrektur V4.00	
			if (i != j) {									// Was passiert, wenn gleich am Anfang j=-1 gilt ??
				u1 = toDoTransList.elementAt(j);			// Antwort : Er durchläuft die Schleife nicht !!!
				q1 = ((TransTab)u1).transition.tteSource;
				kill = greaterAndContains(p1, q1);
			} 
			else {
				kill = false;
			}
			
			if (kill == true) {				
				if (debug_killLevelConflicts){
					System.out.println("killLevelConflicts : Will Transition von IN in ENTERED-List verschieben !");
				}
												// Enthaelt Objekt j das Objekt i vollstaendig
				removePathInList(q1);			// und ist groesser, wird Objekt j von der IN- in die
												// EXITED-Liste verschoben (mit removePathInList() )
				toDoTransList.removeElementAt(j);
				if (j < i) {					
					i--;						// ggf. Zaehler i anpassen.
				}
			}
				
		}
		i--;
	}

	if (debug_killLevelConflicts == true) {
		System.out.println("Am Ende von killLevelConflicts sind noch : "+toDoTransList.size()+" Transitionen uebrig");
		System.out.println("killLevelConflicts: Ende !");
	}
}
/**
 * New method, created by HH on INTREPID
 *
 * Methode beseitigt Nichtdeterminismus und baut in toDoTransList einen Vector auf, 
 * der frei von Nichtdeterminismus ist.
 * Dabei wird jeweils das letzte Element der possTransList ausgewählt und dann der Source-Pfad
 * der restlichen Elemente der possTransList mit dem Source-Pfad der ausgewählten Transition
 * verglichen. Bei Gleichheit werden diese Transitionen dann zusammen mit der ausgewählten
 * Transition im Vector NonDetWahl abgelegt.
 * Alsdann wird eine Transition aus dem Vector NonDetWahl ausgewählt und in die toDoTransList
 * eingefügt.
 *
 * Benutzte globale Objekte : possTransList, toDoTransList
 * Aufgerufen von			: makeStep()
 * Ruft auf					: selectNonDet()
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999
 */
private void killNonDet() {
	Vector NonDetWahl = new Vector();
	TransTab temp1, temp3, temp5;
	Path temp2, temp4;
	boolean compare;
	int LastIndex;

	if (debug2c == true) {
		System.out.println("killNonDet: Start !");
	}
	
	while (possTransList.size() > 0) {					// Solange possTransList noch nicht abgearbeitet ist
		LastIndex = possTransList.size();
		--LastIndex;										// Weil Vector von 0 an gezaehlt wird.
		temp1 = (TransTab)possTransList.elementAt(LastIndex);
		temp2 = temp1.transition.tteSource;
		NonDetWahl.removeAllElements();							// Auswahl-Vector auf Null setzen
		NonDetWahl.addElement(temp1);							// und erste Auswahl einfuegen
		possTransList.removeElementAt(LastIndex);				// dann aus altem Vector loeschen 
		--LastIndex;											// und Ende-Index um 1 erniedrigen
		
		for (int j=(LastIndex); j > -1; j--) {					// Vergleich mit den restlichen Transitionen
			temp3 = (TransTab)possTransList.elementAt(j);		// der possTransList durchfuehren
			temp4 = temp3.transition.tteSource;					// und ggf. speichern
			compare = comparePaths(temp2, temp4);
			if (compare == true) {
				NonDetWahl.addElement(temp3);
				possTransList.removeElementAt(j);
			}
		}
		if (NonDetWahl.size() > 1) {							// Wenn mehr als eine Auswahl vorliegt, muss 
			temp5 = selectNonDet(NonDetWahl);					// mit selectNonDet() eine ausgewaehlt werden.
			toDoTransList.addElement(temp5);					// Die Auswahl wird dann eingefuegt.
			if (debug4 == true) {
				System.out.println("killNonDet : Habe Auswahl treffen muessen !");
			}
		}
		else {													// Die einzige Auswahl wird eingefuegt.
			toDoTransList.addElement(temp1);
		}	
	}
	if (debug2c == true) {
		System.out.println("Nach killNonDet sind noch : "+(toDoTransList.size())+" uebrig !");
		System.out.println("killNonDet: Ende !");
	}
	
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		Simu aSimu;
		aSimu = new Simu();
		try {
			Class aCloserClass = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
			Class parmTypes[] = { java.awt.Window.class };
			Object parms[] = { aSimu };
			java.lang.reflect.Constructor aCtor = aCloserClass.getConstructor(parmTypes);
			aCtor.newInstance(parms);
		} catch (java.lang.Throwable exc) {};
		aSimu.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of java.awt.Frame");
		exception.printStackTrace(System.out);
	}
}
/**
 * Fuehrt eine Action aus
 * @param aktion Absyn.Action
 *
 * Benutzte globale Objekte : bvList, seList
 * Aufgerufen von			: executeTransList(), make_action()
 * Ruft auf					: get_guard(), make_action()
 *
 * @param aktion absyn.Action
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 31.01.1999 debug-Level, Search-In-xxList ERROR
 * @version V4.01 vom 06.02.1999 debug-Methode
 * @version V4.02 vom 06.02.1999 isInseList-Error( Eine Abfrage zuviel )
 */
private boolean make_action(Action aktion) {

	boolean isInbvList = false;
	boolean isInseList = false;
	int position = 0;	
	
	// Wenn Aktion eine Instanz von ActionStmt ist, dann teste, ob
	// 1. eine Instanz von MTrue vorliegt: Dann ueberpruefe den Wahrheitswert in der BvarListe; wenn er nicht stimmt,
	// aendere die BvarListe.
	// 2. eine Instanz von MFalse vorliegt: Vorgehensweise wie in 1.
	// 3. siehe unten
	
	if (aktion instanceof ActionStmt) {
		if (debug_make_action == true){
				System.out.println("make_action: (ActionStmt) identifiziert !");
		}
		if (((ActionStmt)aktion).stmt instanceof MTrue) {
			if (debug_make_action == true){
				System.out.println("make_action: (MTrue) identifiziert !");
			}
			MTrue temp1 = (MTrue)(((ActionStmt)aktion).stmt);

			isInbvList = returnBvarStatus(temp1.var);
			position = returnPosInbvList(temp1.var);
			if (position > -1){
				// Position der Bvar ist gefunden worden
				bvList.removeElementAt(position);
				bvList.insertElementAt(new BvarTab((temp1.var), true),position);
				if (debug_make_action == true){
					System.out.println("make_action(MTrue) : BVar "+temp1.var.var+" in bvList auf 'true' gesetzt.");
				}
			}
			else{
				// NPExep abgefangen
				System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
			}
		}
		
		if (((ActionStmt)aktion).stmt instanceof MFalse) {
			if (debug_make_action == true){
				System.out.println("make_action: (MFalse) identifiziert !");
			}
			MFalse temp1 = (MFalse)(((ActionStmt)aktion).stmt);
			position = returnPosInbvList(temp1.var);
			if (position > -1){
				// Position der Bvar ist gefunden worden
				bvList.removeElementAt(position);
				bvList.insertElementAt(new BvarTab((temp1.var), false),position);
				if (debug_make_action == true){
					System.out.println("make_action(MFalse) : BVar "+temp1.var.var+" in bvList auf 'false' gesetzt.");
				}
			}
			else{
				// NPExep abgefangen
				System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
			}
		}
		
		// 3. Wenn ActionStmt eine Instanz von BAss ist, dann bestimme den
		// Wahrheitswert des Guards und weise ihn zu.  
		if (((ActionStmt)aktion).stmt instanceof BAss) {
			if (debug_make_action == true){
				System.out.println("make_action: (BAss) identifiziert !");
			}
			BAss temp1 = (BAss)(((ActionStmt)aktion).stmt);
			Bvar temp2 = ((Bassign)temp1.ass).blhs;
			Guard temp3 = ((Bassign)temp1.ass).brhs;
			boolean temp4 = get_guard(temp3);
			// Bvar ist drin -> Mache sie wahr !	
			position = returnPosInbvList(temp2);
			if (position > -1){
				// Position der Bvar ist gefunden worden
				bvList.removeElementAt(position);
				bvList.insertElementAt(new BvarTab((temp2), temp4),position);
				if (debug_make_action == true){
					System.out.println("make_action(BAss) : BVar "+temp2.var+" in bvList auf "+temp4+" gesetzt.");
				}
			}
			else{
				// NPExep abgefangen
				System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
			}
		}		
	}

	// Wenn aktion ein ActionEvt ist, wird in der EventListe der Event
	// auf true gesetzt.
	if (aktion instanceof ActionEvt) {
			if (debug_make_action == true){
				System.out.println("make_action: (ActionEvt) identifiziert !");
			}
			SEvent temp1 = ((ActionEvt)aktion).event;
			if (debug_make_action == true){
				System.out.println("make_action: Suche SEvent: "+temp1.name);
			}
			position = returnPosInseList(temp1);
			if (position > -1){
				// Position der Bvar ist gefunden worden
				seList.removeElementAt(position);
				seList.insertElementAt(new SEventTab((temp1), true),position);
				if (debug_make_action == true){
					System.out.println("make_action() : SEvent "+temp1.name+" in seList gesetzt.");
				}
			}
			else{
				// NPExep abgefangen
				System.out.println("make_action() : SEvent nicht wiedergefunden => ERROR !");
			}
			
	}

	// Leere Action: Daher passiert nichts.
	if (aktion instanceof ActionEmpty) {
		if (debug_make_action == true){
				System.out.println("make_action: (ActionEmpty) identifiziert !");
		}
	}

	// Wenn aktion ein ActionBlock ist, werden die Elemente, die Actions sind, mittels
	// make_action() ausgefuehrt. Zur Sicherheit wird dazu ein Clone benutzt (temp2).	
	if (aktion instanceof ActionBlock) {
		if (debug_make_action == true){
				System.out.println("make_action: (ActonBlock) identifiziert !");
		}
		Aseq temp1 = ((ActionBlock)aktion).aseq;
		Aseq temp2 = temp1;
		
		while (temp2.tail != null) {		// 31.01.99: Standardabbauweise, da auf .tail = null getestet
			make_action(temp2.head);		// 31.01.99: wird, muss noch ein Aufruf nachfolgen. 
			temp2 = temp2.tail;
		}
		make_action(temp2.head);			// 31.01.99: Wegen (temp2.tail != null)
	}
	
	return false;
}
/**
 * Diese Methode fuehrt n Simulationsschritte aus ( im synchronen Betrieb ).
 * Im asynchronen Betrieb wird nur ein Schritt ausgefuehrt.
 *
 * @param n int
 *
 * Benutzte globale Objekte : aSyncOn
 * Aufgerufen von			: button3_MouseClicked()
 * Ruft auf					: makeStep()
 *
 * @param n int
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 07.02.1999 Warteschleife(statisch)
 */
protected void makeNStep(int n) {
	
	int counter = 0;
	
	if (aSyncOn == false){
		while (counter < n) {
			makeStep();
			counter++;
			/*try{
				edit.repaint();
				Thread.yield();
			
				Thread.sleep(2000);
				
			}
			catch(InterruptedException e){}
			*/
			
		}
	}
	else {
		makeStep();
	}
}
/**
 *
 * Berechnet alle theoretisch moeglichen Transitionen (possTransList).
 * NPExep sollten nicht auftreten. 
 *
 * Benutzte globale Objekte : transList, possTransList
 * Aufgerufen von			: makeStep();
 * Ruft auf					: ----
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 24.01.1999 - NPExep, comparePaths() zum Vergleich, debug
 * @version V4.01 vom 06.02.1999 - debug-Methoden, ersetzen in for-SchleifeN '<' durch '<='
 */
private void makePossTransList() {
	int i, j;
	int x, y;
	int counter = 0;		// debug - Variable (zaehlt die eingefügten "abstrakten" Transitionen)
	int debugger =0;
	i = InList.size();
	j = transList.size();
	Path temp1;
	TransTab temp2;
	Path temp3;
	Path temp4;
	
	possTransList.removeAllElements();		// Loeschen der alten possTransList

	if (debug_makePossTransList == true) {
		printInList();
		System.out.println("makePossTransList: Zaehler fuer InList meldet "+i+" Elemente");
		System.out.println("makePossTransList: Zaehler fuer transList meldet "+j+" Elemente");
		printInList();
	}

	i--;
	j--;
	
	// Wir vergleichen jeden Pfad der InListe mit jedem Sourcepfad der Transitionen der 
	// TransTabEntries aus der 'abstrakten' Transitionsliste (transList).

	
	if ((i > -1) && (j > -1)){	// NPExep abfangen
		 
		for (x = 0; x <= i; x++) {	// InList - Schleife
	
			temp1 = (Path)InList.elementAt(x);
			
			for (y = 0; y <= j; y++) {	// transList - Schleife
				temp2 = (TransTab)transList.elementAt(y);
				temp3 = temp2.transition.tteSource;
				temp4 = temp2.transition.tteTarget;
				
				if (comparePaths(temp1,temp3)) {						// Vergleich mit comparePaths-Methode					
					possTransList.addElement(transList.elementAt(y)); //*************moegliche Fehlerquelle******************
					counter++;
				}
				else{
					if (debug_makePossTransList) {
						System.out.println("makePossTransList: Vergleichspfad eins ist:"+returnDottedPath(temp1)+" nein");
						System.out.println("makePossTransList: Vergleichspfad zwei ist:"+returnDottedPath(temp3)+" nein");
					}
				}
			}
		}
	}
	else {
		if (debug_makePossTransList == true){
			System.out.println("makePossTransList : Unwahrscheinlicher Abfang einer NPExep aufgetreten !");
		}
	}
	
	if (debug_makePossTransList){
		System.out.println("makePossTransList : "+counter+" 'abstrakte' Transitionen eingefuegt. !");
	}
}
/**
 *
 * Berechnet alle praktisch moeglichen Transitionen (auf possTransList)
 *
 * Benutzte globale Objekte : possTransList
 * Aufgerufen von			: makeStep()
 * Ruft auf					: get_guard()
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 24.01.1999 - NPExep, debug
 * @version V4.01 vom 30.01.1999 - debugLevel eingefuehrt, NPExep - Abfang korrigert (auf i >= 0).
 * @version V4.02 com 06.02.1999 - debug-Methoden
 */
private void makeRealTransList() {
	int i = 0;
	int counter = 0; 	//debug - Variable
	TransTab temp1;
	boolean bguard;
	Guard tguard;
	
	i = possTransList.size();

	if (debug_makeRealTransList == true){
		System.out.println("makeRealTransList : Start ( überprüfe "+i+" Transitionen ).");
	}
	
	i--;
	if (i >= 0){		// Vermeide NPExep
		
		// Gehe Vektor mit den moeglichen Transitionen durch und eleminiere alle
		// Transitionen, bei denen der Guard nicht erfuellt ist 
		// => erhalte Liste mit real moeglichen Transitionen ( in possTransList ).

		for (int x=i; x>-1; x--) {
			temp1 = (TransTab)possTransList.elementAt(x);
			tguard = temp1.transition.tteLabel.guard;
			
			if (debug_makeRealTransList == true){
				System.out.println("makeRealTransList : Prüfe Transition Nr.: "+x);
				System.out.println("makeRealTransList : Source: "+returnDottedPath(temp1.transition.tteSource));
				System.out.println("makeRealTransList : Target: "+returnDottedPath(temp1.transition.tteTarget));	
			}
			
			bguard = get_guard(tguard);

			if (bguard != true) {
				counter++;
				possTransList.removeElementAt(x);
				if (debug_makeRealTransList == true){
					System.out.println("makeRealTransList : "+counter+" Transition(en) geloescht.");
				}
			}
		}
	}
	
	i = possTransList.size();

	if (debug_makeRealTransList == true){
		System.out.println("makeRealTransList : Ende ( "+i+" moegliche Transitionen identifiziert");
		System.out.println("                  :         und "+counter+" Transitionen geloescht).");
	}
	 
}
/**
 * Diese Methode macht genau einen Simulationsschritt, der entweder synchron oder asynchron ist.
 *
 * Benutzt globale Objekte 	: simuCount, aSyncOn
 * Aufgerufen von			: makeNStep()
 *							  button2_ActionEvents()
 * Ruft auf					: makePossTransList(), makeRealTransList(), killNonDet()
 *							  killLevelConflicts(), step(). firstStep()
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V2.00 vom 23.01.1999
 */

void makeStep() {

	boolean aSyncOnDo;
	
	if (aSyncOn == false) {					// Handelt es sich um einen synchronen Step ? Wenn ja, dann ....
		if (simuCount == 0) {
			firstStep();					// Mache den ersten Schritt
			simuCount++;
		}
		else {
			if (LF1 instanceof ListFrame){
				bvList = LF1.inVector;
			}
			if (LF2 instanceof ListFrame){
				seList = LF2.inVector;
			}
			if (td instanceof Trace) {
				if (getCheckboxMenuItem1().getState()){
					td.makeTraceStep(InList, seList, bvList, true);
				}
			
				//else {
				//	gui.OkDialog("ERROR !!", "MAKESTEP: FATALER FEHLER !! PROGRAMM INSTABIL");
				//}
			}

			makePossTransList();			// Berechnet alle theoretisch moeglichen Transitionen (in possTransList)
			makeRealTransList();			// Berechnet alle praktisch moeglichen Transitionen (auf possTransList)
			killNonDet();					// Beseitigt Nicht-Determinismen (in toDoTransList)
			ExitedList.removeAllElements();	// Exited-Liste zuruecksetzen, denn in den folgenden Methoden wird sie gefuellt
			killLevelConflicts();			// Beseitigt Level-Konflikte (in toDoTransList)
			step();							// Macht einen Schritt

			/********************************************************************/ 
			if (getCheckboxMenuItem5().getState()) {
				gotBreakPoint = get_guard(Breakpoint);
				if (gotBreakPoint) {
					gui.OkDialog("Breakpoint found", "Letzter Schritt loeste Breakpoint aus !!");
					gotBreakPoint = false;
				}
			}
			/********************************************************************/

			if (td instanceof Trace) {
				if (getCheckboxMenuItem1().getState()){
					td.makeTraceStep(InList, seList, bvList, false);
				}
				//else {
				//	gui.OkDialog("ERROR !!", "MAKESTEP: FATALER FEHLER !! PROGRAMM INSTABIL");
				//}
			}

			simuCount++;
		}
	
	}
	
	else {										// Es soll ein asynchroner Step gemacht werden
		aSyncOnDo = true;
		
		if (LF1 instanceof ListFrame){
			bvList = LF1.inVector;
		}
		if (LF2 instanceof ListFrame){
			seList = LF2.inVector;
		}

		if (td instanceof Trace) {
			if (getCheckboxMenuItem1().getState()){
				td.makeTraceStep(InList, seList, bvList, true);
			}
		}
		else {
			gui.OkDialog("ERROR !!", "MAKESTEP: FATALER FEHLER !! PROGRAMM INSTABIL");
		}

		while ( aSyncOnDo == true ){			// 
			
			if (simuCount == 0) {				// Mache ersten Schritt
				firstStep();
				simuCount++;
			}
			else {
				
				makePossTransList();			// Berechnet alle theoretisch moeglichen Transitionen (possTransList)
				makeRealTransList();			// Berechnet alle praktisch moeglichen Transitionen (toDoTransList)
				killNonDet();					// Beseitigt Nicht-Determinismen (in toDoTransList)
				if (toDoTransList.size()>0){
					ExitedList.removeAllElements();	// Exited-Liste zuruecksetzen, denn in den folgenden Methoden 
													// wird sie gefuellt
					killLevelConflicts();			// Beseitigt Level-Konflikte (in toDoTransList)
					step();							// Macht einen Schritt 
					simuCount++;

					/********************************************************************/ 
					if (getCheckboxMenuItem5().getState()) {
						gotBreakPoint = get_guard(Breakpoint);
						if (gotBreakPoint) {
							gui.OkDialog("Breakpoint found", "Letzter Schritt loeste Breakpoint aus !!");
							gotBreakPoint = false;
							toDoTransList.removeAllElements();
						}
					}
					/********************************************************************/

				}
				else {
					aSyncOnDo = false; 			// muss hier eventuell noch mehr gemacht werden ? ******************************
												// ( Sachen, die sonst von step() erledigt werden )*****************************
				}
			}
		}
		if (td instanceof Trace) {
			if (getCheckboxMenuItem1().getState()){
				td.makeTraceStep(InList, seList, bvList, false);
			}
		}
		else {
			gui.OkDialog("ERROR !!", "MAKESTEP: FATALER FEHLER !! PROGRAMM INSTABIL");

		}

	}
}
/**
 * Erstellt die noetigen Tabellen
 * hier: Bvar-Liste
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: firstStep()
 * Ruft auf					: ----
 *
 * @param daten absyn.BvarList
 * @return result Vector
 * @version V3.07 vom 20.01.1999
 * @version V3.08 vom 21.01.1999
 * @version V4.00 vom 23.01.1999
 * @version V4.05 vom 30.01.1999 - DebugLevel
 */
Vector makeTab(BvarList daten) {
	Vector result = new Vector();
	BvarList data;
	data = daten;
	int i = 0;			// Debug - Zaehlvariable
	
	// Durchlauf nur, wenn data != null, sonst Ergebnis auf null setzten und zurueckgeben
	
	if (data != null) {
		if (debug1) {
			System.out.println("Die ursprüngliche BvarList ist nicht leer");
		}
	
			
		while (data.tail != null) {									// "data.tail" statt "data"
				BvarTab newElement = new BvarTab(data.head, false);
				result.addElement(newElement);
				data = data.tail;
				i++;
		}
		// Weil nur auf (data.tail != null) geprueft werden kann, muß das letzte Element
		// außerhalb der Schleife exrahiert werden.
	
		BvarTab newElement = new BvarTab(data.head, false);
		result.addElement(newElement);
		i++;

		//	};	FTA

	}
	if (debug==true){
		System.out.println(i+" Bvars gezaehlt und in bvList eingefuegt!");
	}
	
	return result;
}
/**
 * Erstellt die noetigen Tabellen
 * hier: Event-Liste
 *
 * Benutzt globale Objekte	: ----
 * Aufgerufen von 			: firstStep()
 * ruft auf					: ----
 *
 * @param daten absyn.SEventList
 * @return result Vector
 * @version V3.02 vom 21.01.1999
 * @version V4.00 vom 23.01.1999
 * @version V4.05 vom 30.01.1999 - DebugLevel
 */

Vector makeTab(SEventList daten)
{
	Vector result = new Vector();
	SEventList data;
	data = daten;
	int i = 0;			// debug - Counter
	
	if (data != null)
	{
		if (debug1 == true) {
			System.out.println("makeTab(SEventList): Die ursprüngliche EventList ist nicht leer");
		}
		
		while (data.tail != null)
		{
			SEventTab newElement = new SEventTab(data.head, false);
			result.addElement(newElement);
			data = data.tail;
			i++;
		}

		// Weil nur auf (data.tail != null) geprueft werden kann, muß das letzte Element
		// außerhalb der Schleife separat exrahiert werden.
		SEventTab newElement = new SEventTab(data.head, false);
		result.addElement(newElement);
		i++;
		
	}
	
	if (debug == true){
			System.out.println(i + " Events gezählt und in seList eingefuegt !");
	}
	
	return result;
}
/**
 * Erstellt die noetigen Tabellen
 * hier: Transitions-Liste
 * Parameter: State, Path
 * Ablauf: 	In Abhaengigkeit des Typs des uebergebenen States wird der Teil der Statechart, der sich in diesem
 * 			State befindet, rekursiv durchlaufen.
 *			Handelt es sich um einen Or_State, wird mit den Elementen der Transitionsliste trs, bei denen es sich
 *			um Transitionen handelt, die Methode makeTrans() aufgerufen, die fuer die Erstellung der 'abstrakten'
 *			Transitionsliste zustaendig ist.
 *			Handelt es sich um einen Or_ oder And_State erfolgt in der Variablen "weg" ein Pfad-Update.
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: Simu(Statechart, Editor, GUIInterface)
 *							  makeTab(State, Path)
 * Ruft auf					: makeTab(State, Path), makeTrans()
 *
 * @param daten absyn.State
 * @param weg absyn.Path
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V2.00 vom 23.01.1999
 * @version V4.00 vom 24.01.1999 - Namensabbau
 * @version V4.11 vom 28.01.1999 - Debug - Informationen, Fehlerbereinigungen
 *
 *
 */
private void makeTab(State daten, Path weg) {
	State data = null;
	Vector result= new Vector();
	TrList trclone1 = null;
	TrList trclone2 = null;
	ConnectorList connList1 = null;
	StateList workList = null;
	
	Path weg_temp = weg;
	
	data = daten;						// Verweis kopieren, da wir Liste abbauen
	makeTabCounter++;
	
	if (debug2){
		System.out.println("Neue Instanz von makeTab(S,P), Nr. "+makeTabCounter);
	}
		
	if (data != null) {
		
		// Wenn der "data"-State ein Basic_State ist, dann mache gar nichts, denn ein Basic-State kann keine
		// Transition haben.
		if (data instanceof Basic_State) {
			if (debug2 == true) {
				System.out.println("makeTab(S,P): Basic_State "+data.name.name+" erkannt !");
			}
		};

		// Wenn der "data"-State ein And_State ist, gehe die Substate-Liste von vorne nach hinten durch
		// und rufe makeTab() rekursiv auf. 
		if (data instanceof And_State) {

			if (debug2 == true) {
				System.out.println("makeTab(S,P): And_State "+data.name.name+" erkannt !");
			}

			if (weg == null) {
				weg = new Path(data.name.name, null);
			}
			else {
				weg = weg.append(data.name.name);		// "weg" um den Namen des aktuellen And-States verlaengern
			}

			workList = (((And_State)data).substates);		// Lege abbaubare "Kopie" an.
			while (workList.tail != null) {					// Standartabbauvariante
				makeTab(workList.head, weg);
				workList = workList.tail;
			};
			makeTab(workList.head, weg);					// Rest von Standartabbauvariante
			killLastElement(weg);					// Korrektur des bisherigen Wegs ('down'date)
			
		};

		// Wenn state ein Or_State, dann gehe zuerst die Substate-Liste von vorne nach hinten durch
		// und rufe makeTab() rekursv auf.	
		if (data instanceof Or_State) {

			if (debug2 == true) {
				System.out.println("makeTab(S,P): Or_State "+data.name.name+" erkannt !");
			}

			if (weg == null) {
				weg = new Path(data.name.name, null);
			}
			else {
				weg = weg.append(data.name.name);		// "weg" um den Namen des aktuellen OR-States verlaengern
			}
			
			workList =(((Or_State)data).substates);			// Lege abbaubare "Kopie" an.
			
			while (workList.tail != null) {					// Standartabbauvariante
				makeTab(workList.head, weg);
				workList = workList.tail;
			};
			makeTab(workList.head, weg);					// Rest der Standartabbbauvariante

			//workList = workList.tail;						// Verursacht NPE

			// Dann lege zwei "Kopien" der trs an, die spaeter gebraucht werden.

			trclone1 = ((Or_State)data).trs;
			trclone2 = ((Or_State)data).trs;

			connList1 = ((Or_State)data).connectors;

			//System.out.println("makeTab(S,P): connList = "+connList1);


			// Fuer jeden Eintrag in der trs-Liste rufe makeTrans() auf, mit den Parametern:
			// 1. Eintrag (Transition)
			// 2. Der bisherige Weg (Pfad) in der Statechart (Bis zu DIESEM Or-State!)
			// 3. Kopierte Transitionsliste zu DIESEM Or-State.
			// 4. Kopierte Connectorenliste zu diesem Or-State.
			// 5. Zugehoerige Transitionen zur zu generierenden 'abstrakten' Transition (fuer unsere Transitionsliste)
			// 6. Zugehoerige Connectoren zur zu generierenden 'abstrakten' Transition. 
			
			// Abbau erfolgt mit Standartabbauvariante :
			if (trclone2 != null) {
				while (trclone2.tail != null) {
					makeTrans((trclone2.head), weg, trclone1, connList1, new TrList(trclone2.head,null), new ConnectorList(null,null));
					if (debug2) {
						System.out.println("makeTab: Groesse von transList = "+transList.size());
					}
					trclone2 = trclone2.tail;
				};
				makeTrans((trclone2.head), weg, trclone1, connList1, new TrList(trclone2.head,null), new ConnectorList(null,null));
			}
			
			if (debug2) {
				System.out.println("makeTab: Groesse von transList = "+transList.size());
			}

			
			// Rest der Standartabbauvariante

			killLastElement(weg);	// Korrektur des bisherigen Wegs ('down'date)			
		};
	}
	if (debug2) {
		System.out.println("makeTab: Instanz "+makeTabCounter+" beendet");
	}
	makeTabCounter--;
}
/**
 * Erzeugt einen Eintrag in der global definierten Transitionstabelle
 *
 * Parameter:
 *			 1. Eintrag (Transition)
 *			 2. Der bisherige Weg (Pfad) in der Statechart (Bis zu DIESEM Or-State!)
 *			 3. Kopierte Transitionsliste zu DIESEM Or-State.
 *			 4. Kopierte Connectorenliste zu diesem Or-State.
 *			 5. Zugehoerige Transitionen zur zu generierenden 'abstrakten' Transition (fuer unsere Transitionsliste)
 *			 6. Zugehoerige Connectoren zur zu generierenden 'abstrakten' Transition.
 *
 * Ablauf:	(1) Handelt es sich um eine Transition, die an einem Connector beginnt,
 *			wird sie ignoriert.
 *			(2) Beginnt die Transition in einem State und endet auch in einem solchen, 
 *			haengen wir an den uebergebenen Pfad des vaeterlichen Or_States die Namen des
 *			Anfangs- und Endstates der Transition an, um die 'abstrakte' Transition unter 
 *			Benutzung des alten Labels zu bilden.
 *			(3) Beginnt die Transition in einem State, endet aber an einem Connector, muss man
 *			alle von dem Connector ausgehenden Transitionen bestimmen und diese jeweils mit der 
 *			urspruenglichen Transition zu einer 'abstrakten' Transition zusammenfassen, wobei auch
 *			die Listen, die die vorher abgearbeiteten Transitionen und Connectoren enthalten, angepasst
 *			werden. Alsdann erfolgt mit diesen Parametern ein erneuter Aufruf von makeTrans() -(rekursiv)- .
 *
 * Benutzte globale Objekte : transList
 * Aufgerufen von			: makeTab(State, Path), makeTrans()
 * Ruft auf					: makeTrans(), returnNewAction(), returnNewGuard()
 * 							  absyn.Path::append()
 *
 * @param trans absyn.Tr
 * @param weg absyn.Path
 * @param trclone TrList
 * @param connList absyn.ConnectorList
 * @param prevTrans absyn.TrList
 * @param prevConn absyn.ConnectorList
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.03 vom 23.01.1999 - Cloning, NPExep
 * @version V4.04 vom 24.01.1999 - handClonePath
 * @version V4.11 vom 08-10.02.1999 - Bughunting
 */


/* Problem: Wird newTrans korrekt weiter- und zurueckgegeben ? **************************/


private void makeTrans(Tr trans, Path weg, TrList trclone, ConnectorList connList, TrList prevTrans, ConnectorList prevConn)
{
	TrList trclone3 = null;
	ConnectorList connClone1 = null;
	String compare1;
	String compare2;
	TrList prevTransTemp;
	ConnectorList prevConnTemp; 
	boolean found = false;
	boolean doLoop = true;
		
	if (debug_makeTrans) {
		System.out.println("makeTrans: 'weg' hat den Wert: "+returnDottedPath(weg));
	}
	
	// siehe (1)
	if (trans.source instanceof Conname)
	{
	}
	
	
	 
	else // hier ist also davon auszugehen, dass (trans.source instanceof Statename) = true ist
	
	{	// siehe (2)
		if (trans.target instanceof Statename)
		{
			Path source = handClonePath(weg);			// Hier werden Clones erstellt, weil die Einträge in die

			Path target	= handClonePath(weg);			// transList nicht mehr veraendert werden duerfen.
			
			if (debug_makeTrans) {
				System.out.println("makeTrans: 'source' hat den Wert: "+returnDottedPath(source));
				System.out.println("makeTrans: 'target' hat den Wert: "+returnDottedPath(target));
			}
			
			source = source.append(((Statename) trans.source).name);
			target = target.append(((Statename) trans.target).name);

			// Instanz der TransTabEntry-Klasse fuer Transitionsliste erzeugen, Transition eintragen
			// und in Transitionsliste uebernehmen ( prevTrans und prevConn mitiefern ).
			
			// *************************CLONING *****************************************************
			try {
				if (prevTrans != null) {
					if (debug_makeTrans) {
						System.out.println("makeTrans(): Versuche clone() von prevTrans");
					}
					prevTransTemp = (TrList)(prevTrans.clone());
				}
				else {
					prevTransTemp = new TrList(null, null);
				}
				if (prevConn.head != null) {
					if (debug_makeTrans) {
						System.out.println("makeTrans(). Versuche clone() von prevConn");
					}
					prevConnTemp = (ConnectorList)prevConn.clone();
				}
				else {
					prevConnTemp = new ConnectorList(null,null);
				}
			}
			catch(CloneNotSupportedException cnse) {;
				cnse.printStackTrace();
			}
			// ****************** ge-clont - theoretisch muesste es funktionieren *******************
			
			TransTabEntry tteDummy = new TransTabEntry(source, target, trans.label);
			transList.addElement(new TransTab(tteDummy, prevTrans, prevConn));
			if (debug_makeTrans) {
				System.out.println("makeTrans: Erweitere die transList ");
				System.out.println("makeTrans: um source = "+returnDottedPath(tteDummy.tteSource));
				System.out.println("makeTrans: und target= "+returnDottedPath(tteDummy.tteTarget));
			}
			// prevTrans kann nicht "leer" sein, prevConn ist eventuell eine (null,null)-Liste.
		}
		// siehe (3)
		else // (trans.source instanceof Statename) = true  & (trans.target instance of Conname) = true
		{
			// Erstelle weitere Kopie der Transitionsliste
			trclone3 = trclone;

			doLoop = true;

			// Sicherheitsabfrage: Die folgende Schleife nicht ausfuehren, wenn einer der beiden
			// benutzten Variablen einen Nullpointer aufweisen !!!
			if (trclone3 == null) {
				doLoop = false;
				System.out.println("makeTrans: WARNING ! trclone hat NULLPOINTER !!!");
			}

			if (trans == null) {
				doLoop = false;
				System.out.println("makeTrans: WARNING ! Transition 'trans' hat NULLPOINTER !!!");
			}
			
			
			while (doLoop == true) 
			{
				if (trclone3.head.source instanceof Conname)
				{
					// Vergleiche source und target-Objekte
					// Problem: Sind die Objekte wirklich gleich ? **** Mögliche Fehlerquelle *****************
					// Nach reichlicher Ueberlegung am 23.01.1999 gehen wir von "ja" aus.
					// Spaeter haben wir das dann aber doch wieder geaendert. :-)
					
					compare1 = ((Conname)(trans.target)).name;
					compare2 = ((Conname)(trclone3.head.source)).name;
					//System.out.println("Vergleiche : XXX"+compare1+"xxx mit XXX"+compare2+"xxx");
					
					if (compare1.equals(compare2)){
						if (debug_makeTrans==true){
							System.out.println("makeTrans: Aequivalenz des letzten Typs erkannt !");
							System.out.println("Aeqivalenz zwischen : XXX"+compare1+"xxx und XXX"+compare2+"xxx gefunden !");
						}
						Tr newTrans = new Tr(null,null,new TLabel(null,null)); 	// Neue Transition instantiieren
						newTrans.source = trans.source; 		// die als Anfangspunkt den Startpunkt der
																// Anfangstransition besitzt
						newTrans.target = trclone3.head.target; // und als Endpunkt den Endpunkt der gewaehlten
						 										// Transition aus der Transitionsliste bekommt.
						
						// Bestimme mit returnNewGuard und returnNewAction das zusammengefasste Label.
						Guard arg1 = returnNewGuard(trans.label.guard, trclone3.head.label.guard);
						
						/*	 Das ist die alte "Spielweise". Wir haben uns dagenen entschieden, weil man
							 bei returnNewAction nicht ohne cloning auskommt und cloning bisher nicht
							 korrekt funktioniert.
							 
						Action arg2 = returnNewAction(trans.label.action, trclone3.head.label.action);

							statt dessen :
						*/
						Action arg2 = null;
						newTrans.label.guard = arg1;
						newTrans.label.action = arg2;
						

						// Haenge dann die abgearbeitete Transition in die prevTrans-Liste (vorne ran)
						// unkritisch !
						prevTrans = new TrList(trclone3.head, prevTrans); //**************************************
						// Erstelle eine "Kopie" der uebergebenen Connectorliste
						connClone1 = connList;
						
						// Suche das passende Connectorobjekt zum bekannten Connectornamen aus
						// der geklonten Liste heraus und haenge das Objekt in die prevConn-Liste (vorne ran).
						//System.out.println("Suche jetzt nach dem Connectorobjekt");						
						found = false;
						while (found == false) 										// Standartabbauvariante
						{
							//System.out.println("makeTrans: searching .....");
							
							compare1 = ((Connector)(connClone1.head)).name.name;
						
							compare2 = ((Conname)(trans.target)).name;

														
							if ( compare1.equals(compare2)) 
							{
								//System.out.println("makeTrans: Habe etwas passendes gefunden !");
								prevConn = new ConnectorList(connClone1.head, prevConn); //********************
								found=true;
							}
							//System.out.println("makeTrans: Checkpoint 1");
							if ((connClone1.tail == null) && (found == false)){
								found=true;
								System.out.println("makeTrans meldet: Fataler Fehler !!");
							}
							else{
								if (found == false) {
									//System.out.println("makeTrans: Checkpoint 2");
									connClone1 = connClone1.tail;
								}
							}
							//System.out.println("makeTrans: Checkpoint 3");
						}
						// Rufe dann makeTrans mit der neuen Transition und den angepassten prevTrans - und
						// prevConn - Listen auf
						//System.out.println("makeTrans: Checkpoint 4");
						makeTrans(newTrans, weg, trclone, connList, prevTrans, prevConn);
						//System.out.println("makeTrans: Checkpoint 5");
						prevConn = prevConn.tail;			// Abbau des angefuegten Connectors
						prevTrans = prevTrans.tail;			// Abbau der angefuegten Transition
						//System.out.println("makeTrans: Checkpoint 6");
					}
					if (trclone3.tail != null){
						trclone3 = trclone3.tail;
						//System.out.println("makeTrans: Checkpoint 7");
					}
					else{
						doLoop=false;
						//System.out.println("makeTrans: Checkpoint 8");
					}
				}
				else {
					if (trclone3.tail != null){
						trclone3 = trclone3.tail;
						//System.out.println("makeTrans: Checkpoint 9");
					}
					else {
						doLoop=false;
						//System.out.println("makeTrans: Checkpoint 10");
					}

				}
					
			}
		}
	}
}
/**
 * Comment
 */
void menuItem1_ActionEvents() {
	//Speichern eines Traces

	boolean IOError = false;	
	
	Vector tmpVec = new Vector();
	tmpVec.addElement(td.cStates);
	tmpVec.addElement(td.cEvents);
	tmpVec.addElement(td.cBvars);
	tmpVec.addElement(new Vector());
	tmpVec.addElement(new Vector());

	IOError = td.saveTrace(tmpVec);
		
	if (IOError) {
		gui.OkDialog("I/O Error", "Fehler beim Speichern des Traces");
	}
	
	return;
}
/**
 * Comment
 */
void menuItem2_ActionEvents() {
	// Laden von Traces
	boolean IOError = false;
	td = new Trace(this);									// neuen Trace anlegen
	td.getFrame1().setVisible(false);

	Vector tmpVec = td.loadTrace();

	if (tmpVec != null) {
		td.cStates = (Vector) tmpVec.elementAt(0);
		td.cEvents = (Vector) tmpVec.elementAt(1);
		td.cBvars = (Vector) tmpVec.elementAt(2);	
	}
	else {
		IOError = true;
	}	

	if (!(IOError)) {
	
		getCheckboxMenuItem6().setEnabled(true);
		getMenuItem1().setEnabled(true);
		getMenuItem5().setEnabled(true);
		getMenuItem2().setEnabled(false);
		getMenuItem3().setEnabled(false);
	
		if (LF1 instanceof ListFrame){
			bvList = LF1.inVector;
		}
		if (LF2 instanceof ListFrame){
			seList = LF2.inVector;
		}
		td.traceCounter = 0;
		handleMonitor = new Monitor(this, td);		// Neuen Monitor erzeugen	
		handleMonitor.getFrame1().setVisible(true);
		handleMonitor.fillTextFields();									// die Textfelder grundfüllen
		handleMonitor.fillDrawFrameData();						// das Grafikarray grundfüllen
		handleMonitor.getDrawFrame1().repaint();			// die Grafik auch anzeigen
		handleMonitor.getTextField0().setText(String.valueOf(td.traceCounter));
		getCheckboxMenuItem1().setEnabled(true);
		getCheckboxMenuItem1().setState(true);
		
	}
	else {
		gui.OkDialog("I/O Error !","Fehler beim Laden des Traces !");
	}
	
	return;
}
/**
 *	Diese Methode wird von Trace - 'neu' aufgerufen !
 */
void menuItem3_ActionEvents() {
	td = new Trace(this);									// neuen Trace anlegen
	td.getFrame1().setVisible(false);
	handleMonitor = new Monitor(this, td);					// neuen Monitor anlegen

	getCheckboxMenuItem6().setEnabled(true);
	getMenuItem1().setEnabled(true);
	getMenuItem5().setEnabled(true);
	getMenuItem2().setEnabled(false);
	getMenuItem3().setEnabled(false);
	getCheckboxMenuItem1().setEnabled(true);
	getCheckboxMenuItem1().setState(true);

	
	if (LF1 instanceof ListFrame){
		bvList = LF1.inVector;
	}
	if (LF2 instanceof ListFrame){
		seList = LF2.inVector;
	}
	
	td.makeTraceStep(InList, seList, bvList, true);		// Trace erhält Grundfüllung
	handleMonitor.getFrame1().setVisible(true);
	handleMonitor.fillTextFields();									// die Textfelder grundfüllen
	handleMonitor.fillDrawFrameData();						// das Grafikarray grundfüllen
	handleMonitor.getDrawFrame1().repaint();			// die Grafik auch anzeigen
	handleMonitor.getTextField0().setText(String.valueOf(td.traceCounter));
	getCheckboxMenuItem1().setEnabled(true);
	getCheckboxMenuItem1().setState(true);
	getMenuItem3().setEnabled(false);

	return;
}
/**
 * Comment
 */
public void menuItem5_ActionEvents() {
	getCheckboxMenuItem1().setState(false);
	getCheckboxMenuItem1().setEnabled(false);
	getMenuItem3().setEnabled(true);
	getMenuItem2().setEnabled(true);
	getMenuItem5().setEnabled(false);
	getMenuItem1().setEnabled(false);
	getCheckboxMenuItem6().setEnabled(false);

	getCheckboxMenuItem1().setEnabled(false);
	getCheckboxMenuItem1().setState(false);

	
	td.getFrame1().dispose();
	handleMonitor.getFrame1().dispose();

	return;
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseClicked(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getButton4()) ) {
		connEtoC2(e);
	}
	if ((e.getSource() == getButton2()) ) {
		connEtoC3(e);
	}
	if ((e.getSource() == getButton3()) ) {
		connEtoC4(e);
	}
	if ((e.getSource() == getButton1()) ) {
		connEtoC1(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseEntered(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseExited(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mousePressed(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseReleased(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Benutzt globale Objekte			:InList
 * Aufgerufen von					:
 * Ruft auf							:---
 * 
 * @params
 * @returns
 * @version V1.00 vom 06.02.1999
 */
private void printInList() {
	int i;

	i = InList.size();

	i--;
	System.out.println("\nAktuelle InList:");
	for (int j=i; j>0; j--){
		System.out.println(returnDottedPath((Path)InList.elementAt(j)));
	}
}
/**
 * Benutzt globale Objekte			:
 * Aufgerufen von					:
 * Ruft auf							:
 * 
 * @return
 * @version V1.00 vom
 * @param arg1 java.util.Vector
 */
void printseList(Vector arg1) {
	Vector copy = new Vector();
	int counter = -1;
	SEventTab seTab;
	
	copy = arg1;
	counter = copy.size();
	
	for (int i = 0; i < counter; i++){
		seTab = ((SEventTab)(copy.elementAt(i)));
		System.out.println(seTab.bName.name+"  "+seTab.bWert);
	}
}
/**
 * Diese Methode sucht einen zu KillPath identischen Path in der InList, entfernt selbigen
 * aus der InList und fügt ihn in die ExitedList ein. Dabei wird die InList von hinten nach
 * vorne durchlaufen.
 *
 * Benutzte globale Objekte : InList, ExitedList
 * Aufruf von 				: killLevelConflicts()
 *							  removePathInListLinear()
 * ruft auf   				: comparePath()
 *
 * @param KillPath absyn.Path
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999 debug-Level, NPExep
 * @version V4.01 vom 06.02.1999 debug-Methoden 
 */
private void removePathInList(Path KillPath) {
	int i;
	boolean found = false;
	boolean test = false;
	Path temp1 = null;
	
	if (debug_removePathInList==true){
		System.out.println("removePathInList : Start: Will loeschen: "+returnDottedPath(KillPath));
	}
	
	i = InList.size();
	i--;						// Vector beginnt mit Element "0"
	if (debug_removePathInList) {
		System.out.println("removePathInList: Groesse der InList ist: "+i);
	}

	if (i > -1) {
	
		while (found == false) {
			temp1 = (Path) InList.elementAt(i);
			test = comparePaths(KillPath, temp1);
			if (test == true) {
				InList.removeElementAt(i);
				ExitedList.addElement(temp1);
				found = true;
				if (debug_removePathInList==true){
					System.out.println("removePathInList : Zu entfernenden Path gefunden");
				}
				}
		
			i--;
		
			if (i < 0){
				found = true;
				if (debug_removePathInList==true){
					System.out.println("removePathInList : Der zu entfernende Pfad konnte nicht gefunden werden !");
					System.out.println("				 : FATALER FEHLER !!!");
				}
			}
		}
	}
	else{
		if (debug_removePathInList==true){
			System.out.println("removePathInList : NPExep abgefangen !");
			System.out.println("	|InList| = 0 : FATALER FEHLER !!!");
		}
	}

		
	
	if (debug_removePathInList==true){
		System.out.println("removePathInList : Ende");
	}

}
/**
 * 
 * Entfernt einen Pfad und alle seine Soehne aus der InListe
 *
 * Benutzt globale Objekte			: InList
 * Aufgerufen von					: executeTransList()
 * Ruft auf							: removePathInList()
 *									  greaterAndContains()
 *
 * @param toKill absyn.Path
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999 debug-Level
 * @version V4.01 vom 06.02.1999 debug-Methoden
 *
 */
private void removePathInListLinear(Path toKill) {
	
	if (debug_removePathInListLinear==true){
		System.out.println("removePathInListLinear: Start: Loesche: "+returnDottedPath(toKill));
	}
	
	boolean kill = false;
	int i,j;
	Path toCompare = null;

	removePathInList(toKill);							// Entfernt den Vater	
	i = InList.size();
	
	if (i < 1){
		if (debug_removePathInListLinear==true){
			System.out.println("removePathInListLinear: |InList| = 0 aufgetreten !");
		}
	}
	
	i--;
	for (j=i; j >= 0; j--) {							// Schleife durchlaeuft die InList
		toCompare = (Path)InList.elementAt(j);			// Bestimmt Element j der InList 
		kill = greaterAndContains(toKill, toCompare);	// Bestimmt, ob Element j Sohn von toKill ist
		if (kill == true) {								// Wenn ja, 
			removePathInList(toCompare);				// aus der InList entfernen und in die ExitedListe 
														// eintragen.
			if (debug_removePathInListLinear==true){
				System.out.println("removePathInListLinear:  Sohn: "+returnDottedPath(toCompare)+" gekillt !");
			}
		}
	}
	
	if (debug_removePathInListLinear==true){
		System.out.println("removePathInListLinear: Ende");
	}
}
/**
 * Setzt die BVarliste zurück. Genauer :
 * Traegt fuer jede BVar der BVarliste den Wahrheitswert "false" in die BVarliste ein.
 * Dabei wird die Reihenfolge innerhalb der bvList NICHT (!) veraendert, obwohl es vielleicht
 * zuerst den Anschein macht.
 *
 * Benutzt globale Objekte	: bvList (BVarListe)
 * Aufgerufen von			: resetSimu() 
 * Ruft auf					: ----
 *
 * @param
 * @return 
 * @version V4.00 vom 07.02.1999 
 */
private void resetBVarList() {

	if (debug_resetBVarList == true){
		System.out.println("resetBVarList() : Start");
	}

	int i;
	Object temp1;
	BvarTab temp2;
	Vector tempBvList = new Vector();
	i = bvList.size();
	i--;
	for (int j = i; j >= 0; j--) {			// Liest jedes Element aus der seList aus
		temp1 =  bvList.elementAt(j);		// und setzt den Wahrheitswert auf "false",
		temp2 = (BvarTab) temp1;			// traegt das veraenderte Element in einen
		temp2.bWert = false;				// temporaeren Vektor tempSeList ein.
		tempBvList.addElement(temp2);
		bvList.removeElementAt(j);
	}
	bvList.removeAllElements(); 		// Nur vorsichtshalber
	i = tempBvList.size();
	i--;
	for (int j = i; j >= 0; j--) {			// Kopieraktion, da bei einem seList = tempSeList
		temp1 =  tempBvList.elementAt(j);	// die Gefahr besteht, dass der GC die tempSeList loescht
		temp2 = (BvarTab) temp1;			// nachdem wir die Methode verlassen haben und damit
		bvList.addElement(temp2);			// seList leer waere.
		tempBvList.removeElementAt(j);
	}

	if (debug_resetBVarList == true){
		System.out.println("resetBVarList() : Ende");
	}
}
/**
 * Setzt die Eventliste zurück. Genauer :
 * Traegt fuer jedes Event der Eventliste den Wahrheitswert "false" in die Eventliste ein.
 * Dabei wird dir Reihenfolge innerhalb der SeList NICHT (!) veraendert, obwohl es vielleicht
 * zuerst den Anschein macht.
 *
 * Benutzt globale Objekte	: seList (EventListe)
 * Aufgerufen von			: step()
 * Ruft auf					: ----
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 von 30.01.1999 debug-Level, Vector-Init-Error
 * @version V4.02 vom 31.01.1999
 * @version V4.05 vom 07.02.1999 debug-Methoden
 */
private void resetEventList() {

	if (debug_resetEventList == true){
		System.out.println("resetEventList() : Start");
	}

	int i;
	Object temp1;
	SEventTab temp2;
	Vector tempSeList = new Vector();
	i = seList.size();
	i--;
	for (int j = i; j >= 0; j--) {			// Liest jedes Element aus der seList aus
		temp1 =  seList.elementAt(j);		// und setzt den Wahrheitswert auf "false",
		temp2 = (SEventTab) temp1;			// traegt das veraenderte Element in einen
		temp2.bWert = false;				// temporaeren Vektor tempSeList ein.
		tempSeList.addElement(temp2);
		seList.removeElementAt(j);
	}
	seList.removeAllElements(); 		// Nur vorsichtshalber
	i = tempSeList.size();
	i--;
	for (int j = i; j >= 0; j--) {			// Kopieraktion, da bei einem seList = tempSeList
		temp1 =  tempSeList.elementAt(j);	// die Gefahr besteht, dass der GC die tempSeList loescht
		temp2 = (SEventTab) temp1;			// nachdem wir die Methode verlassen haben und damit
		seList.addElement(temp2);			// seList leer waere.
		tempSeList.removeElementAt(j);
	}

	if (debug_resetEventList == true){
		System.out.println("resetEventList() : Ende");
	}
}
/**
 * Versetzt die Simulation in ihren Ausgangszustand zurueck
 *
 * Benutzt globale Objekte			: SDaten, bvList, seList, simuCount
 * Aufgerufen von					: Actionhandler zu Button 4
 * Ruft auf							: 
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 07.02.1999 :	firstStep() wird jetzt auch aufgerufen; damit werden 
 *								 	Statelisten zurückgesetzt; debug-Methoden
 */
protected void resetSimu() {
	if (debug_resetSimu){
		System.out.println("resetSimu: Start !");
	}
	
	highlightObject ho;
	
	simuCount = 0;

	firstStep();
	if (LF1 instanceof ListFrame){
		LF1.update(bvList);
	}
	
	if (LF2 instanceof ListFrame){
		LF2.update(seList);
	}


/*	if (td instanceof Trace) {
		td.getFrame1().dispose();
		td = new Trace(this);
	}*/
	
	if (debug_resetSimu){
		System.out.println("resetSimu: InList besteht aus :");
		printInList();
	}
	ho = new highlightObject(true);
	selectHighlightObject();
	ho = new highlightObject();		
	simuCount++;
	
	if (debug_resetSimu){
		System.out.println("resetSimu: Ende !");
	}
}
/**
 *
 * Bekommt eine Bvar uebergeben, extrahiert den entsprechenden Wahrheitswert aus der bvList
 * und liefert diesen dann auch zurueck.
 *
 * Benutzt globale Objekte			: bvList
 * Aufgerufen von					: get_guard(Guard)
 * Ruft auf							: 
 * 
 * @params
 * @returns
 * @version V4.00 vom 24.01.1999 - NPExep, debugLevel
 * @return boolean
 * @param arg1 absyn.Bvar
 */
private boolean returnBvarStatus(Bvar arg1) {
	
	boolean back = false;
	boolean found = false;
	int laenge;
	String compareStr1;
	String compareStr2;
	BvarTab bvT;
	Bvar bv;

	if (debug2 == true){
			System.out.println("returnBvarStatus : Start");
	}
	
	compareStr1 = arg1.var;
	laenge = bvList.size(); 		// Groesse der bvList bestimmen, da sie nachher durchlaufen
									// werden soll.
	laenge--;						// "Vector" beginnt bei 0

	if (laenge > -1){ 				// Fange NPExep ab
		
		while (laenge > -1){		// Durchlaufe die bvList, bis die entsprechende Bvar gefunden ist.
			
			bvT = (BvarTab)(bvList.elementAt(laenge));
			bv = bvT.bName;
			compareStr2 = bv.var;
			found = compareStr1.equals(compareStr2);
			
			if(found == true){		// Die Bvar wurde gefunden,
				back = bvT.bWert;	// der Wahrheitswert in "back" uebernommen und
				laenge = -1;		// die Schleife wird verlassen.
				
				if (debug3 == true){
					System.out.println("returnBvarStatus : Gesuchte Bvar in bvList gefunden !");
				}
				
			}
			else{
				laenge--;			// Die Bvar wurde noch nicht gefunden, also weiter in der Schleife.
			}	
		}			
	} 
	else{
		if (debug1 == true){
			System.out.println("returnBvarStatus : NPExep abgefangen, |bvList|=0  !");
		}
	}

	if (debug2 == true){
			System.out.println("returnBvarStatus : Ende");
	}
	
	return back;
	}
/**
 * Benutzt globale Objekte			: ----
 * Aufgerufen von					: makeTrans()
 * Ruft auf							: ----
 * 
 * @return java.lang.String
 * @param arg1 absyn.Path
 * @version V1.00 vom 28.01.1999
 * @version V2.01 vom 29.01.1999 - Namensabbau
 * @version V4.00 vom 29.01.1999
 */
private String returnDottedPath(Path arg1) {
	String result = new String();
	Path tArg1 = arg1;
	while (tArg1.tail != null) {
		result = result.concat(tArg1.head);
		result = result.concat(".");
		tArg1 = tArg1.tail;
	}
	result = result.concat(tArg1.head);
	return result;
}
/**
 * Gibt die neu erzeugte, aus arg1 und arg2 'zusammengesetzte' Action zurueck.
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: makeTrans()
 * Ruft auf					: append()
 *
 * @param arg1 absyn.Action
 * @param arg2 absyn.Action
 * @return absyn.ActionBlock
 * @version V1 vom 14.01.1999 (deprecated)
 */
private ActionBlock returnNewAction(Action arg1, Action arg2) {
	
	ActionBlock result = null;
	
	if (arg1 instanceof ActionEmpty) {
		if (arg2 instanceof ActionBlock) {
			result = (ActionBlock)arg2;
		}
		else {
			result = new ActionBlock(new Aseq(arg2, null));   //default
		}
	}
	else {
		if (arg2 instanceof ActionEmpty) {
			if (arg1 instanceof ActionBlock) {
				result = (ActionBlock)arg1;
			}
			else {
				result = new ActionBlock(new Aseq(arg1, null));
			}
		}
		else {
			if (arg1 instanceof ActionBlock) {
				if (arg2 instanceof ActionBlock) {
					
				}
				else {
					result = new ActionBlock(append((ActionBlock)arg1, arg2));
				}
			}
			else {
			}	
		}
	} 
	return result;
}
/**
 *
 * Verbindet zwei Guards zu einem konkatenierten Guard
 *
 * Benutzte globale Objekte : ----<p>
 * Aufgerufen von			: makeTrans()<p>
 * Ruft auf					: ----<p>
 *
 * @param arg1 absyn.Guard
 * @param arg2 absyn.Guard
 * @return absyn.Guard
 * @version 2.0 vom 14.01.1999
 * @version 4.0 vom	24.01.1999
 */
 
private Guard returnNewGuard(Guard arg1, Guard arg2) {
	Guard result = null;
	if (arg1 instanceof GuardEmpty) {
		result = arg2;   //default
	}
	else {
		if (arg2 instanceof GuardEmpty) {
			result = arg1;
		}
		else {
			result = (new GuardCompg(new Compguard(0, arg1, arg2)));	// Argumente Compguard: Op, Guard1, Guard2
		}
	} 
	return result;
}
/**
 * 
 * Diese Methode untersucht, ob sich der uebergebene Path (arg1) in der EnteredList befindet und
 * gibt das Ergebnis als boolean-Wert zurück.
 * 
 * Benutzt globale Objekte			: EnteredList
 * Aufgerufen von					: get_guard(Guard)
 * Ruft auf							:
 * 
 * @params
 * @returns
 * @version V4.00 vom 30.01.1999 
 * @return boolean
 * @param arg1 absyn.Path
 */
private boolean returnPathEnteredList(Path arg1) {

	int laenge;
	boolean found = false;
	Path comparePath1;
	Path comparePath2;

	if (debug2c == true){
		System.out.println("returnPathEnteredList : Start");
	}

	comparePath1 = arg1;
	
	laenge = EnteredList.size();	//  Groesse der EnteredList bestimmen
	laenge--;						// 	"Vector" beginnt bei 0

	if (laenge > -1){	// NPExep abfangen
		while (laenge > -1){
			if (found == false){	// Wenn er noch nicht gefunden wurde, dann suche weiter
				comparePath2 = (Path)(EnteredList.elementAt(laenge));
				found = comparePaths(comparePath1,comparePath2);
			}
			laenge--;	
		}
	}
	else{
		if (debug2b == true){
			System.out.println("returnPathEnteredList : |EnteredList| = 0 ist aufgetreten !");
		}
	}
	
	if (debug2c == true){
		System.out.println("returnPathEnteredList : Ende");
	}
	
	return found;
}
/**
 * 
 * Diese Methode untersucht, ob sich der uebergebene Path (arg1) in der ExitedList befindet und
 * gibt das Ergebnis als boolean-Wert zurück.
 * 
 * Benutzt globale Objekte			: ExitedList
 * Aufgerufen von					: get_guard(Guard)
 * Ruft auf							:
 * 
 * @params
 * @returns
 * @version V4.00 vom 30.01.1999 
 * @return boolean
 * @param arg1 absyn.Path
 */
private boolean returnPathExitedList(Path arg1) {

	int laenge;
	boolean found = false;
	Path comparePath1;
	Path comparePath2;

	if (debug2c == true){
		System.out.println("returnPathExitedList : Start");
	}

	comparePath1 = arg1;
	
	laenge = ExitedList.size();		//  Groesse der ExitedList bestimmen
	laenge--;						// 	"Vector" beginnt bei 0

	if (laenge > -1){	// NPExep abfangen
		while (laenge > -1){
			if (found == false){	// Wenn er noch nicht gefunden wurde, dann suche weiter
				comparePath2 = (Path)(ExitedList.elementAt(laenge));
				found = comparePaths(comparePath1,comparePath2);
			}
			laenge--;	
		}
	}
	else{
		if (debug2b == true){
			System.out.println("returnPathExitedList : |ExitedList| = 0 ist aufgetreten !");
		}
	}
	
	if (debug2c == true){
		System.out.println("returnPathExitedList : Ende");
	}
	
	return found;
}
/**
 * 
 * Diese Methode untersucht, ob sich der uebergebene Path (arg1) in der InList besindet und
 * gibt das Ergebnis als boolean-Wert zurück.
 * 
 * Benutzt globale Objekte			: InList
 * Aufgerufen von					: get_guard(Guard)
 * Ruft auf							:
 * 
 * @params
 * @returns
 * @version V4.00 vom 24.01.1999
 * @version V4.01 vom 30.01.1999 Kommentar, debug-Levels
 * @return boolean
 * @param arg1 absyn.Path
 */
private boolean returnPathInList(Path arg1) {

	int laenge;
	boolean found = false;
	Path comparePath1;
	Path comparePath2;

	if (debug2c == true){
		System.out.println("returnPathInList : Start");
	}

	comparePath1 = arg1;
	
	laenge = InList.size();		//  Groesse der InList bestimmen
	laenge--;					// 	"Vector" beginnt bei 0

	if (laenge > -1){	// NPExep abfangen
		while (laenge > -1){
			if (found == false){	// Wenn er noch nicht gefunden wurde, dann suche weiter
				comparePath2 = (Path)(InList.elementAt(laenge));
				found = comparePaths(comparePath1,comparePath2);
			}
			laenge--;	
		}
	}
	else{
		if (debug1 == true){
			System.out.println("returnPathInList : |InList| = 0 ist aufgetreten !");
		}
	}
	
	if (debug2c == true){
		System.out.println("returnPathInList : Ende");
	}
	
	return found;
}
/**
 *
 * Bekommt eine Bvar uebergeben, stellt die Position in der bvList fest
 * und liefert diese dann ( als int ) auch zurueck.
 *
 * Benutzt globale Objekte			: bvList
 * Aufgerufen von					: 
 * Ruft auf							: 
 *
 * @version V4.00 vom 31.01.1999 - NPExep, debugLevel
 * @return int
 * @param arg1 absyn.Bvar
 */
private int returnPosInbvList(Bvar arg1) {
	
	int back = -1;
	boolean found = false;
	int laenge;
	String compareStr1;
	String compareStr2;
	BvarTab bvT;
	Bvar bv;

	if (debug2 == true){
			System.out.println("returnPosInbvList : Start");
	}
	
	compareStr1 = arg1.var;
	laenge = bvList.size(); 		// Groesse der bvList bestimmen, da sie nachher durchlaufen
									// werden soll.
	laenge--;						// "Vector" beginnt bei 0

	if (laenge > -1){ 				// Fange NPExep ab
		
		while (laenge > -1){		// Durchlaufe die bvList, bis die entsprechende Bvar gefunden ist.
			
			bvT = (BvarTab)(bvList.elementAt(laenge));
			bv = bvT.bName;
			compareStr2 = bv.var;
			found = compareStr1.equals(compareStr2);
			
			if(found == true){		// Die Bvar wurde gefunden,
				back = laenge;		// die Position wird in "back" uebernommen und
				laenge = -1;		// die Schleife wird verlassen.
				
				if (debug3 == true){
					System.out.println("returnPosInbvList : Gesuchte Bvar in bvList gefunden !");
				}
				
			}
			else{
				laenge--;			// Die Bvar wurde noch nicht gefunden, also weiter in der Schleife.
			}	
		}			
	} 
	else{
		if (debug1 == true){
			System.out.println("returnPosInbvList : NPExep abgefangen, |bvList|=0  !");
		}
	}

	if (debug2 == true){
			System.out.println("returnPosInbvList : Ende");
	}
	
	return back;
	}
/**
 *
 * Bekommt einen SEvent uebergeben, bestimmt die Position in der seList
 * und liefert diese ( als int ) dann auch zurueck.
 *
 * Benutzt globale Objekte			: seList
 * Aufgerufen von					: make_action()
 * Ruft auf							: --------
 *
 * @version V4.00 vom 31.01.1999 - NPExep, debugLevel
 * @return boolean
 * @param arg1 absyn.SEvent
 */

 private int returnPosInseList(SEvent arg1) {
	
	int back = -1;
	boolean found = false;
	int laenge;
	String compareStr1;
	String compareStr2;
	SEventTab svT;
	SEvent se;

	if (debug2 == true){
			System.out.println("returnPosInseList : Start");
	}
	
	compareStr1 = arg1.name;
	laenge = seList.size(); 		// Groesse der seList bestimmen, da sie nachher durchlaufen
									// werden soll.
	laenge--;						// "Vector" beginnt bei 0

	if (laenge > -1){ 				// Fange NPExep ab
		
		while (laenge > -1){		// Durchlaufe die seList, bis der entsprechende SEvent gefunden ist.
			
			svT = (SEventTab)(seList.elementAt(laenge));
			se = svT.bName;
			compareStr2 = se.name;
			found = compareStr1.equals(compareStr2);
			
			if(found == true){		// Die Bvar wurde gefunden,
				back = laenge;		// die Position wird in "back" uebernommen und
				laenge = -1;		// die Schleife wird verlassen.
				
				if (debug3 == true){
					System.out.println("returnPosInseList : Gesuchter SEvent in seList gefunden !");
				}
				
			}
			else{
				laenge--;			// Der SEvent wurde noch nicht gefunden, also weiter in der Schleife.
			}	
		}			
	} 
	else{
		if (debug1 == true){
			System.out.println("returnPosInseList : NPExep abgefangen, |seList|=0  !");
		}
	}

	if (debug2 == true){
			System.out.println("returnPosInseList : Ende");
	}
	
	return back;
 } 
/**
 *
 * Bekommt eine SEvent uebergeben, extrahiert den entsprechenden Wahrheitswert aus der seList
 * und liefert diesen dann auch zurueck.
 *
 * Benutzt globale Objekte			: seList
 * Aufgerufen von					: get_guard(Guard)
 * Ruft auf							: 
 * 
 * @params
 * @returns
 * @version V4.00 vom 24.01.1999 - NPExep, debugLevel
 * @return boolean
 * @param arg1 absyn.SEvent
 */
private boolean returnSEventStatus(SEvent arg1) {
	
	boolean back = false;
	boolean found = false;
	int laenge;
	String compareStr1;
	String compareStr2;
	SEventTab svT;
	SEvent se;

	if (debug2 == true){
			System.out.println("returnSEventStatus : Start");
	}
	
	compareStr1 = arg1.name;
	laenge = seList.size(); 		// Groesse der seList bestimmen, da sie nachher durchlaufen
									// werden soll.
	laenge--;						// "Vector" beginnt bei 0

	if (laenge > -1){ 				// Fange NPExep ab
		
		while (laenge > -1){		// Durchlaufe die seList, bis der entsprechende SEvent gefunden ist.
			
			svT = (SEventTab)(seList.elementAt(laenge));
			se = svT.bName;
			compareStr2 = se.name;
			found = compareStr1.equals(compareStr2);
			
			if(found == true){		// Die Bvar wurde gefunden,
				back = svT.bWert;	// der Wahrheitswert in "back" uebernommen und
				laenge = -1;		// die Schleife wird verlassen.
				
				if (debug3 == true){
					System.out.println("returnSEventStatus : Gesuchter SEvent in seList gefunden !");
				}
				
			}
			else{
				laenge--;			// Der SEvent wurde noch nicht gefunden, also weiter in der Schleife.
			}	
		}			
	} 
	else{
		if (debug1 == true){
			System.out.println("returnSEventStatus : NPExep abgefangen, |seList|=0  !");
		}
	}

	if (debug2 == true){
			System.out.println("returnSEventStatus : Ende");
	}
	
	return back;
	}
/**
 * Übergibt den Inhalt der InList als zu highlightende Objekte an den Editor.
 *
 * Benutzt globale Objekte			: InList
 * Aufgerufen von					: 
 * Ruft auf							: find_state, editor::highlightObject()
 * 
 * @params
 * @returns
 * @version V1.02 vom 30.01.1999
 * @version V4.01 vom 30.01.1999
 */
void selectHighlightObject() {
	
	Path workPath = null;
	int sizeInList = 0;	
	Vector workInList = InList;
	State rootState = SDaten.state;
	State foundState = null;
	highlightObject ho;
	

	sizeInList = workInList.size();
	sizeInList--;

//ho = new highlightObject(true);
			
	while (sizeInList > -1) {
		workPath = (Path)workInList.elementAt(sizeInList);
		if (debug3) {
			System.out.println("selectHighlightobject: workPath = "+returnDottedPath(workPath));
			System.out.println("selectHighlightObject: rootState = "+rootState);
		}
		if (workPath != null) {
			if (workPath.tail != null) {
				workPath = workPath.tail;
				foundState = find_state(rootState, workPath);
			}
			else {
				foundState = rootState;
			}	
			ho = new highlightObject(foundState, Color.black);
				
		}
		else {
			System.out.println("selectHighlightObject: Unerwartete NPException aufgetreten !!!!");
		}
		sizeInList--;
	}
//	ho = new highlightObject();
}
/**
 * Waehlt aus dem uebergebenen Vector mit TransTab's  per Benutzer- oder Zufallswahl einen
 * TransTab aus und liefert diesen zurück.
 *
 * Benutzte globale Objekte : NonDetChoice
 * Aufgerufen von			: killNonDet()
 * Ruft auf					: utils::RandomGenerator
 *
 * @return simu.TransTab
 * @param arg1 java.util.Vector
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999 debug-Level
 */
private TransTab selectNonDet(Vector arg1) {
	int laenge;
	
	if (debug2b == true) {
		System.out.println("selectNonDet : Start !");
	}
	switch (NonDetChoice) {
		case 0:
			laenge = arg1.size();
			selectNonDetChoice = 0;
			System.out.println("selectNonDet: Vor Erzeugung !");
			LND = new ListNonDet2(arg1, gui.isDebug(), this, laenge);
			System.out.println("selectNonDet: Nach Erzeugung ist choice : "+selectNonDetChoice);
			// derzeitiger default-Rückgabewert:
		/*	LND.getDialog1().setModal(true);
			LND.getDialog1().pack();
			LND.setSize(538, 399);
			LND.setLocation(700, 50);
			LND.setTitle("BVAR-Liste"); */
			LND.getDialog1().dispose();
			
			
			if (debug2b == true) {
				System.out.println("selectNonDet : Ende (Benutzerwahl) !");
			}
		
			System.out.println("selectNonDet: Vor Return; die Wahl fällt auf : "+selectNonDetChoice);
			return ((TransTab)(arg1.elementAt(selectNonDetChoice)));
			
			//break;								// Auswahl per Benutzerwahl :-]
		
		case 1:										// Auswahl per Zufallszahl
			RandomIntGenerator rig = new RandomIntGenerator(0,(arg1.size()-1));
			int x = rig.nextInt();
			if (debug2b == true) {
				System.out.println("SelectNonDet : Ende (Zufallswahl) !");
			}
			return ((TransTab)(arg1.elementAt(x)));
		default:
			if (debug1 == true) {
				System.out.println("SelectNonDet: Ende mit default -> Es liegt ein Fehler vor !");
			}
			return null;
	}
}

/**
 * Benutzt globale Objekte			: alle DebugLevel
 * Aufgerufen von					: Simu()
 * Ruft auf							: ----
 * 
 * @returns
 * @version V1.00 vom 30.01.1999
 * @param status boolean
 */
public void setDebugLevel(boolean status) {
	debug = status;	// = debug1 !
	debug1 = status;	// fatal error, nullPointerExeptions
	debug2 = status;	// Statusmeldungen
	debug2a = status;	// Statusmeldungen für selten genutze Methoden
	debug2b = status;	// Statusmeldungen für normal gentzte Methoden
	debug2c = status;	// Statusmeldungen für häufig gentze Methoden
	debug3 = status;	// Funktionsnachweise
	debug4 = status;	// Ausgabe von Countern und Variableninhalte
	debug5 = status;	// ----- 
	debug_get_guard = status;
	debug_make_action = status;
	debug_killLevelConflicts = status;
	debug_makeRealTransList = status;
	debug_makePossTransList = status;
	debug_step = status;
	debug_removePathInList = status;
	debug_removePathInListLinear = status;
	debug_executeTransList = status;
	debug_copyEnteredToIn = status;
	debug_inMethod = status;
	debug_inMethodInit = status;
	debug_firstStep = status;
	debug_resetSimu = status;
	debug_resetEventList = status;
	debug_resetBVarList = status;
	debug_makeTrans = status;


}
/**
 * Macht einen Schritt indem folgende Aktionen ausgeloest werden:
 * 		- die EnteredList wird geloescht
 *		- die EventList wird zurueckgesetzt
 *		- executeTransList() wird aufgerufen
 *		- highlightObject wird gefeuert
 *		- der Inhalt der EnteredListe wird zur InListe hinzugefuegt
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: makeStep()
 * Ruft auf					: resetEventList(), executeTransList(), copyEnteredToIn()
 *
 * @param 
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 30.01.1999 debug-Level, Kommentare
 * @version V4.01 vom 31.01.1999 Highlightfire aktiviert
 */

private void step() {
	if (debug_step==true){
		System.out.println("step : Start");
	}

	highlightObject ho = null;		// Fuer HIGHLIGHT

	ho = new highlightObject(true);
	
	EnteredList.removeAllElements();
	
	if (debug_step==true){
		System.out.println("step : resetEventList() wird aufgerufen");
	}
	
	resetEventList();


	if (debug_step==true){
		System.out.println("step : executeTransList() wird aufgerufen");
	}
	
	executeTransList();

	if (LF1 instanceof ListFrame) {
 		LF1.update(bvList);
	}
	if (LF2 instanceof ListFrame) {
		LF2.update(seList);
	}
	
	if (debug_step==true){
		System.out.println("step : Inhalt der InList ist :");
	}
	
	if (debug_step==true){
		printInList();
		System.out.println("step : copyEnteredToIn() wird aufgerufen");
	}
	
	copyEnteredToIn();

	if (debug_step==true){
		System.out.println("step : Jetzt ist Inhalt der InList der folgende:");
	}

	if (debug_step==true){
		printInList();
	}
	

	
	if (debug_step==true){
		System.out.println("step : highlightObject() wird aufgerufen (FEUER) !");
	}
	
	selectHighlightObject();
		
	
	ho = new highlightObject();			// Nachdem alle Objekte an den Editor uebergeben wurden, 
										// wird die highlight-Funktion gestartet.
	
	if (debug_step==true){
		System.out.println("step : Ende+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
}
}