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

public class Simu extends Frame implements java.awt.event.ItemListener, java.awt.event.MouseListener {
	private ListFrame LF1;
	private ListFrame LF2;
	private Statechart SDaten = null;
	private Vector EventList = new Vector();		// Vektoren, die die Listen darstellen, die wir
	private Vector InList = new Vector();			// aufbauen wollen
	private Vector EnteredList = new Vector();
	private Vector ExitedList = new Vector();
	private Vector possTransList = new Vector();	// Transitionen, die moeglicherweise ausgefuehrt werden
	Vector transList = new Vector();				// die aufgebaute "abstrakte" Transitionsliste
	Vector toDoTransList = new Vector();			// Transitionen, die keinen Nichtdeterminismus aufweisen
	private GUIInterface gui = null; 				// Referenz auf die GUI (mit NULL vorbelegen)
	private Editor edit = null;
	Vector bvList = new Vector();
	Vector seList = new Vector();
	int NonDetChoice = 1;					// Modus des Beseitigungsalgorithmus fuer den Nichtdeterminismus
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
	// redundant
	public int makeTabCounter = 0;
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
		guiOutput("Simu: nichtleere Beispiel - Statechart angekommen !");
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
		ho = new highlightObject(true);
		selectHighlightObject();
		ho = new highlightObject();		
		simuCount++;

		//guiOutput("Simu: Bis CheckPoint 1 gekommen !");
		//bvList = makeTab(SDaten.bvars); // Initialisiert die BVarListe (bvList) - nur Debugging, sonst in firstStep()
		//guiOutput("Simu: Bis CheckPoint 2 gekommen !");
		//seList = makeTab(SDaten.events); // Initialisiert die EventListe (evList) - nur Debugging, sonst in firstStep()

		makeTab(SDaten.state, weg);
		
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
		
		/*long tempo = 0;
		while (tempo < 25000000){
		tempo++;
		}
		change(bvList);
		if (LF1 instanceof ListFrame) {
		LF1.update(bvList);
		}
		int help = 0;
		while ( help!=2){
		tempo = 0;
		while (tempo < 25000000){
		tempo++;
		}
		change(bvList);
		change(seList);
		if (LF1 instanceof ListFrame) {
		LF1.update(bvList);
		}
		if (LF2 instanceof ListFrame) {
		LF2.update(seList);
		}
		help++;
		}
		*/
		if (debug == true) {
			System.out.println("Initialisierung FERTIG !");
		}

		//} catch (Throwable exception) {
		//	System.err.println("Exception occurred in main() of java.lang.Object");
		//	exception.printStackTrace(System.out);
		//}
	}
	else {
		guiOutput("Simu: Keine Statechart angekommen ! Objekt war null");
		System.out.println("Keine Statechart angekommen !");
	}
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
	this.getFrame1().dispose();
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
	System.out.println("button3_mouseClicked(): Schritte zu machen --------------------------------------: "+i);
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
	}
	else {
		aSyncOn = false;
		getButton3().setEnabled(true);
		getTextField1().setEnabled(true);
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
	}
	else {
		aSyncOn = false;
		getButton3().setEnabled(true);
		getTextField1().setEnabled(true);
	}

	return;
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

 *  @version  $Id: Simu.java,v 1.20 1999-02-07 23:05:12 swtech26 Exp $

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
 * Benutzt globale Objekte			: SDaten, evList, bvList
 * Aufgerufen von					: makeStep() *!*!*
 * Ruft auf							: initInList
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.12 vom 27.01.1999
 * @version V5.01 vom 29.01.1999
 * @version V5.02 vom 07.02.1999 debug-Methoden, Entered- und ExitedList werden jetzt auch zurückgesetzt
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
	D0CB838494G88G88GE9F9C7A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E13CDB8FD8D5D536679B69E5935F9B6A5964B4FEC5E68DD3B411D251088D23D4E80E030A26C98E9A9A96B2DA4C9B5E7B2C5908A01A7917124A8C9AAD489BFF8495C90D3F491F2BB072D7446442BD424D4B3DF46F2130FF73565E7B2CFD4FBD77DC8C2B6777AD4F5D3F3577DAEB2F33765EEB6FFDA0196F8424252C4FA4A4A50DA87F0E249072CDADA10F35F52FC3ACA3B807287F51528E727A5E8D704C82DE
	505FF348F632F2D386148DD05E87655D70FB97F9FED342A770430B1E8860BDBF7978F7A36BC9FC9F75ECE4653BCB3740F3ABD0A1E0ADC09302297E6925D98ABE8C652D4AEFCAA96B89493D1F56E93E102542E315FA6BE8FB600730BACF17EA6B8C826E8BB481E893336C551D258ABF2A68172463653753DF25FAD46D10565D4FF5E4CF66CFC2DECC23CEC8A5771C5F864FE3CD177ACEB8AD83C3E5E54D70BF711FCCED38FCA10355A124834AFB26D0EC5D855A6FE8A8A7GED180236BC7E6EB1C5175F2FA6022376
	FA3525FB8A3741F70A4B166483CBD3384E542328B3E28A6ADC7DB155398EFEE7066490C38862BBBAA9DE8D65AEA0B984EDD9799E5A92EA1FC83A76FA3745AF045B909C62324578665214B758BB3EA60F3E038460EDFFG62892896289FE8AC50DBAC368E65FEG4F0A9E394ABEB0E037D58C8ED6DA4D0ED3F633E4055FE5E540D130B3D22F5DE1A6CA7CFCF74120E38FD24083B99E986FDBBB41F7DE6DBF010C138F25276974ADF36B7E7490C5C664832E3EE5BDC87DB63E1B7AAD9E70945687F8DD8C3F1B61DD8F
	3A700807A83E1E61346C873C5C07504F3EDF23ADADDF5FC0E21B7353B76A59F2EFF1FAC62892FF21AEDB380FAF9C20BEB6825E8F6483B49EA894E8D1A87A785F3945D772F11DCD169CB61335F928E7506410AB07ACD63364D0FC1DF1541B2F13C2BD7D3CD57843D4CE7B1D71B077C71A402D8C5F2342391FDEE4F1CA4B05409B87446BDF4762B4D9297F2A10B31E61FBFE6F42391C5D8C2765F2608581F1DFBDEA203E4A1A1AC3CA000CC0E6A0DF200029682BD939855FB39E1BD4FE62ED7FB36FF5F8CA433243D4
	E7F34AA6DB2F54E6F2D8CCBDD649C914BAB767D11F7638351B4046C9AF68B8A959A407C93658EDA05DB4A041CBF172BAF7666D20F59C926336D886A4D0B0B0C8D06E3DF9B43E2CA6275CBAE8B649122B8667BF10C7E36E8BD39BC8848170FB4DD46A2B1B589C958E3FE326221F92BE24D8C99878A88C7DD27841CEF8B6C010C8563232EACBAFB54364380C6371566EFD706C067AF1345DB4180701CA9F017788348EE86C9FE07E894758CF6C4058775973EB92F653FB3A319FB2F5F7FAC0B80F3150F0D76CF31D41
	CCE7AC60640FB9A49B289C289B4887A8GA89CA8760F3866E4F47B0FB0E728095A2F45287DDA0C760776937B28A78BFDD26A054FEDAB785E36ED5441F668E0B49EB6A963E01B97D9294AF8FB458B1F3F2B40E2751C591D142B7B2E021A772713693C0FD9533573548D85F4CC16825E82A4CF47383A74C1B69FEF15A62704A32C518103C3B23B220C576DBFC07BA1FDA15964EAE9D032196107D311E3BF3049B576AE5A453898E2B81D532AE0F0B8857E75019D9C87996A110C7CB10CEF12E50725E7C8161AA5D936
	587ADC6D2FE736B85D26CB27B2265EE1EB309F74F152F40C3D3EF20AC5C2B9C6E08E06A538557B1F0FBB3C3C0BC84527B1537563687B464E8E32335C1B0ED46519F61572DDBAFE5119250A2F0D0A5FB6A931264C992C4E7699B0B6003A0146828583AD1A017EF83B33DB932B9BD5F2C632632DD15BDC2A2B0B6AF9758A6DB70DC2970F1FB9866F313C40209F4BCB8C7A313C4CB0F22C2EB26845AA672DB150F93D57EE33C9EC3089B66179EDE28B3559E233486D96F31FA4B7DB3E12A08F0F20F19E44568E823FB3
	A3F0DE7F721FB8D764565CCC8AD7E76966D7DBEA8F27DBA3F89C7BC438668AEE535A8F69DCA1D92581F0CA15FD48A6F37C6FECDDD2F0E7AA373FF7881E593915C332EC371598866CC3CEA91F7DDFE53574DE124C391F193EB0651B3E1473193773CFD1DE8D7D19177D6107A732E942EE68614DAF1CD2B7B4FAED79D1DEDBDE1192A73B487ECB1DAA9B8E7EB09B8E3A5BG0A8E221E1C9A351E629F262778C285FB610C6B24FA0AD14F1875C3BF261ED17834D8783463E2234A06A20309991B2F15DEB49A69A50A6C
	7172B9B759BF28FFC59E6F2B28D851D338735FCCCFD53F54FB2947BEDCE4304852C0BE7D2FD9069C2D2A5FE46B73D4D687FC2D2EDE94F121CE533A482353223D3A4FDFED7E4E4D1603BF5C16039E360CEC4AA1FC37CDC55F7D74FE3912DB14DCF7C1A11DEB82E08E8A811A9D01F909B31F72B15FD59212AA3B554A27C960D5BFCA6740D55D192AF9ADAE021FE594820F73BFB369717D847FF9DDFE0060DBFB7478A1023FDA179FA97817FB7578DB057DF7DE01BF778A7C173C7239FF6797D17F46C05DA4208D0F21
	FF0372F7297CEB345BAF8D8D36501D926E138A3653B5CB7AFCC802ED94E5B4C817153D502D85BBB53CB613F54835473A2B002E59D615CC3E16851BA8BE68308CC0CAC913BE65BC6970D175D9D19658C84F1ECA61E98462B17B3346F4F8FEC2B7E5C538B64EE821E7DAB48E391CC965FAF24C7489D47DB82D935D39C7617B8396889429545B703862D7D66F66F3229E148301A29FD76AC52A6BF5F6E13DDC404BA3DD753A5D6AEDBD0F7582G8F85DA94496BE54ED4570B9175CA81EF829A1C496B8539553B6DD32C
	3786700CD92EFA3933F8BD6BACDAEF06B883AB9058D38C1B8F8EFDE8836EB1CEEDD71F4511EB8F6A668D14029579913334FB0C74E2AAB78E7024D998E706C2F51CD5B81D76DE8B5BC2E3CED5284EA9B896DB38991E97AD8E27DC870959B046554AC2EAAB5DE3AB309287779CB8CEBC4F8CEA4C96596E40ADCD5517EC6EE931D741B63F96C233CA24DCED9669CB7A1E5979DD36897D15718466D44BBB55E703727A3D692FF871CD79935CA75DCF38FC337DCDBC9B3CDD488EGBE475E6DC1EC11406E90587659086D
	6BC52CF6B67AF6F111FE1E7617A2BAAEAE09717ACC919DBBDFA8E3147B6C61DDF45EE06726B8CF17153161457559589E0A5675190D671546E3D4DFA81423006205EDB1EC9FC5F3F572A72C7BD5955BEFC23989289F081F8D3D5409E7CCBE00057E89674978D675BC3686704CB9B0874C4171EFBE77A1BCC715A90878E8AB04940E0CBE45443131F1F339E2178F19C0420BCA5337693CFF7FB16F249B677077AE4FF13D7F404E25A9E1C03C7F6167307F61D0A7F98E76FF55C93D7E977E997B7FFE5755771FE46E96
	7D77C8EF1577B2268B6D2A861D5F7C996D5ADB2FE7D7887039EC1B4A3723C9B5712C7048F9549B89B2C9946A5D55219B8FD1680F5F1E3FFAFF5C267207C77A28581576A95AE5841D3E422E6153EA3B422238DD3142AE6B27F41E9DE5BE255875B36791F8DEE5CA25589DA96CCE821B8CF3516E494DFAF61B6762FB1C6226FB1429A51E6D9FD42FA1711F65F8DDC30667727161B7D73B067C2D044EA50100074D45F96E7CC13A3F46B5648C4C5AE092E49538AE3CD21C4A67BB253C2DB88D67BB02E78B7BCDA35DD7
	E8A9EAAE1E459EBFCC7D9687E5B2AF07E4819582B54DC35BFED9FC45337D1A61914E766F9A15DD037370DC667D1E513473956D82C755EE3CE81757BB1AF6C1225D1D9EF7976173DC67E75173BC4F52785DC2B8DBEB6267713B05D4014FE2F8328A67F1FF0661EC5D1E1FC32C73D17F32F22D7E2879AE1DF1735D75A74F47BB0FC436FEED09566ACFE6781EE82DFE1FAA54EFG1EFFB46ACFBD27551F946D2EF37B8277B2EF3743235DDECD39C4530E0F63F9E52F910FF51DEF34895E5F96D42B73E4A34821E720D6
	F87AAC40B57D1DB6DC5347AF4031F06B911AF3C6517AC09B966610EC20EA20EEA05F05B8A6D6946B65E36EE342DBB266BA8766FEF86CAA7D976D51AEDFD31E34503D1D127F55E23F439662BB3C2E6B6AEC3859231D769DA6695A50DA0FB6ECF992EDB8FE7E6AECE870E82775C37513FAB654F7E07E57A5ECF865532B33618D0FF6DABF0CFBD2AF1657966C760CE5BC34D06C3C6FB435132EBFC1424EB9D7E9E74C956D0C5375552AE6FCDF6B1E44313234145D61AD0271318847452992FA0E0D6BD8135DE213892E
	E17FDBC2F3F43B4352E731A1768F06755A213E836F8B0F31BDC48B489C94F2EB8EFBBF4BB69E56BF4BBEF3D87FAC3B63704877B25D073D1DF5F37B26897BFC01B8F622873171827B03400284D65E0BD838404258BEA49A4A71828BAE23D846E218FF9663BBFF6C985D73D40BF294ABF709F247B1EFF7AE23A52AC30BF19D1F7FE8BAD2F5B05E77E4365772819B46ADC6FFAC2BC0FF0488ECFD954596C1B9C178A0290A7AA4D914DFE6E51A877272FFD47DD876A7FB7551EDBF1A8EBD9F3D4D70192FEFE3B16CF4CA
	CE3650D41BE41342FBD6266B2347FD4BE9587892BC9FADBB37C929D3A35B2B687E064FC70BF3F5764CCDA6984DFC9E682B46B9F37BD3388F1EDCC37302DAA8F7838DBF053E5E57B6D2BE658F75A6BD255C3BABCFBE7E820FF89F6B779D519F6B8F9C21730E53BE6468153463B96CC8166039D6E11A5CD31770BA0F3276324951A7F94C8733D9FB4E7356FE4191EFF3867777CBEE7EAE9631323FF6A49F4D863F44AA3EC9D21EDCDEF817DADE091057D9BF123C0CA5704E16F0B905CB54726473EAF9C5C2DEE347C8
	722A173846C0071B3C4ACF55728E89F9A127C712E78599BE0A1CF1708CDC02FB4E348BEAF907053C4466116405C17BA8C5DE5C92F50CBDD3CAF35BE1D1FE1615AF0BF1A0D5EC956F3C46E6D696B2FE36584C667FDD5000760BE3518C7B5BFAC2EA84564A667FDE18000A853610E18F8376AF013533B561DA409AD351FF730F52F551EA3AEC9F823D25B1345EF3C7557B5BE425BF59B1383F3D7FCBFAE6F751E19A0050EB9E14FAC96653345D63E5188B7BC05DF1C0A1C051C0C9B1685F7FEA27E39822DB26030D8E
	F81EC7279C57665174BB02AE10BB78B4365DD661E96BFB95FA360E05B65C26648A149BG659020E820A44ADF0AF25F28227D12ADB25B8717AC25DFF96E2DA0A246BCED66B1E12D56464470D2D71C7AAD751C37793EE742C9F6CF33146FFB3689BC086111AA1C6F07C2593CC74BEB00E77D8B2ECB9F3561DA95AF302EF60AED090575BC9631G0615C039D6E047EA31ED0B404E57E15BE1015DD507ED7D8476F9BD369DAF309B9B30ED184096B6E05BA801DD6C4036B1823B76AC3BFBDF866FE499E2CF1C96F90940
	A61FC19D55827B6FB32823C3E06DAC07B4C3F9D8E0E9ADA84FCFE06F35223CE0018D35223C08E5986FAB736958556E836D749E4D4803112FCF5B4A68FB0A02F671C0E4B91EF9DFB704E75E47BE3A0DA4F43D23FB669DF47721742C65BCE6CA173B4EF23A451977EA162B51F6B2700316D36C2D0F5471CF6375E34277A51CFF0F9B1F2E9DCBD2557CF8B7BE35779A3776EF9ED37329BFCEAAFC5E3767DE97FDFB69F6E238417BF747C9CA1F321ED16649CF6ECB0B3D837BF674B82E77CD4F60FA6FFB027AF3AC1483
	00C285DEDE417C8C65F8A092077B6C3D15681F2CB8740F31D26B1FE5A96A7EFDD729754F9DEE7D8F2E547A675086B5FFF92556BFCB5C7C333BD3FD0ED7BEE3DFFA0A977FB445F13FF445395EBD0F49B15518277A47E11C7655E0BC8747E19EB471A4BDD30821F500B62CGFF8115G99013AD7E03D492C9E3D0723F7EC728AFEB7C66F3D689D9E3DCF63759E2999D464517654266E952EB9ED438A4F79CDCBEB143E6C799EF5CB14BADC7753CCB7FDDF783D127A3BBF3D6F8635045FFDFD1F6F19707BAB3EE61CE9
	43B5E39C5894889489948F143192578C390E5AA05968152C19142E246D2EE947FB25DCD1EFDA9D3543EA6A112C8457165AAAA2F4B557222EEF200DBFD0B0D094509AA0AE777ABAF10775EC0EA26346EA170C3175A8A31B7201DA00E420F14F220C298DA8A3C44818DB631271ED874A08G7EA220E4202CF8E89F0FFD39752CE7DF4ACF3AE4ACBF0DB2BA200D95488FA888E8F6BC5A31648C5A11900FF67C3C1690DCEBED4DD407131E1643D371EBB336D9970F6B7A1E965435E5952C91C065C05DC0BE2BD0D7E32B
	38A3DC053AE656D1DDFC3D9D43666B60D5FC3DCD91389F4343D4B8DF07DFE3B8ADC7824F709C1ECFBC486EF57C219C8494819483346EB9BCD310AA8A49954E70DA6C03C65808D8D54784FC9C3CD0B61A33E20CF7C25E4C41F9393F7F966277F63657FB562C6715B5C7F96E493AAF0DBEF95F4E9A291F7B01678B9488948E948D1470BC766F5E8A836F9F3DA62E10E5D3EFBF3B01464E51CBE197CC93C65E37934735FDA3892EF9A2B341DB7E74BB76EDFDEE02763DBD447012846DFB7B7989FCEF65401B14006752
	A1EC9D49D82D15B30D61192B35F2BE29C0B9594073DD0D7364BFABC77B0E38EF3FB9C53B4CFD78E245FEF83619BE533DF12F0734233167B3589036187A88663EC1556A35A7FE35727E92A14FCBE4EBDBCD7688B614A42A77D13F58CC7B22FC7527E0BE6F3BB07C8E2CE1B37D1E2085B6461FD8A42B39885BBF79B60DBB71951410BB6DADB5FED060D35BDD32713B2E589DAE2CC839EB2BB32A7B681F285C19A6621ABF5C06EBFEE4A22E79F735E957FC7A7D1DEB4DDE5426DD738F3871B737EB57FC771C60D83BF6
	4D4FD1F28A5E0F896D74ACC8F68CC9FCACCE6AD367G0BE66E73FA17C77E4A5FC926726C5FF5DF1A7277D73558CF63DF311F3F295576335D4D4E45355AFE663971DF29537673CEB77E71BAEDBF51CF3C1F936B301F5C465F55230D3E424618FA2D0D2DEEBA36B6E8ED3C490DDF5E203571F6B73E23C1EBE3011B0D739A5CED3C2383ED8C93B64E6D505AD86026A3752C5646C3EE7CC3E735B67E46AD476CBB2B35F157FA4E67B6E5B422CDC94226430DA35B545F689143EEBAEFE9545A34510DBF3BD1EBD3051B5F
	DEB803FED3E23A595D0F15ADE833E18D5ABC58B2324D93DBC636B93AC5EB738EB7FEFA2B074D6B55B6FF5E0AB6AAF7CDE504E0BEF9236A77DBE5BF5639299EF19DA5BF298E3EE6BCD26F7D7C7251FA7D734BA76A756FAA66560F7C5D7D62FA3D6F6E79BA96453E63B0C39C78GCD828A851A3D8673833961DD4272831D5C20465CC74FCA69DF1849282B47683DDF9663157FAE4DE124EBD40F9D561181AA9F71E1A3FDB732FD10019C7B56C87D613074754B3CAA7E4D0C114637D53A48E03E67EABDAE6ECF5660BE
	6C68895C07E52C45FCA064E3D72CCDD37D76BF7153475DEF7F9FF47C3E425527992A5F460A1FDE3774136A6067BDFD6F52DC0C1D77745A8772ADBD8379C3DF8C641BFA64FC895232323253C3D62BF272C314B309472AD96E89B1E084B22F45BD532FEB6819E515C47FA695F3736B77E1EE1EF46896923D2AB81D0EBD3DFC601B35BC8F88DC6BBA93F059790F573208473914C84D78BC6DE3447B5498911F5F0EF8DF22C7FC1C9FEC2232B2DF48A105AF6058FE79944D89F473D6G1B9C76C149A1DF66A87B32997F
	66D977BE627FE8391A50A602A86CC12560C96841C3694903075EA21E745E038771644D9E85AD483A09B9CE4502CA84290A81FF120805DE05C2211825FFA0456EF6379B70524B361DB6EBB61B109D50C8905F4C6CECF7575D048AB9BCB24C5F6E4C3CF9EF3E6F4DE6707AE77A469D525BDD6A9BDFC8EF77FC7B7F5AFD2D1D349D1F4FDBDA3A6D3B2D8D36FDF798EF0A41E6553AEDBFE6B42D31F878612107839BABE9AF023CCDCFEFF9B60DB5F1DC71271A04ABF4C9608ADDA97799C7DD72F9BE06251699F75D1362
	98EB1D650FC8F10CF5FDC3E02CD1A84BCB82E3E303BEF377C1857D829983398CB289B285A20394E421132737977A68A4A7EE194F69D836CB78FA1D59CADA2F85190F658B7C6AF2A5E13D16F41A8E7CA6D7F13FCC5169BC526F4DEC4B31FF332B5AA6D4F40F384D38D025E3BD00FEFBA023323099CDA5ECB3787B2DB02E0627B7C3691B9B433739F7077A8CBED7EDA3DD6FFF9A03757E14116C734F9A7F7A7CF3C6324FBFEF04FD3EFFA7516F379384367D6F5C8D4ACB72D814EA3956910B6372B34198333993301E
	D7E5F1CE18D145B9E1B2487B28097843BDD9F0928546D520ECFC3F3CE67905ECFC3F0CD745FEA9C2D9AFABF4A833454E42B0E91C77DAB4E9371CBBAB2A02BB1B027AB2482BDC85AD0254F202BB7B26896E0C64045F18D30E756EEDA8FD31BAFC9D3CB654E7F0DF292881FE4D89CC6328F1CC9345FE63EA9C53C4B1748DB50EE9B21172C7DA9C534CC73804BDAD0AE95655137A957E3FB7D75E51187344B41AA5684DF75BF19A2DA69C444BF613FDFC66D8E0A1891A5D41608F88FA22DA8DEC925CA668A9CDEABC7D
	4C5C2FBBB676BC581CE84E6C3A7B75CE4B451D729A8633067788FA396AA5644EFCC6626CB3B29F66C2EABB25D4AE647EC3EEEF9F6763771A0BA53ACEAB103F1B8F6EB5FF20F7585378B333E9B98E311C462AFBC0393435AE41475866FD09766F09BAFEA1AF7BD4226F7DA354666FGD0CB878819E058553597GG24C8GGD0CB818294G94G88G88GE9F9C7A619E058553597GG24C8GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGG
	G6F97GGGG
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
 * Return the Frame1 property value.
 *
 * @param 
 * @return java.awt.Frame
 * @version V1 vom 14.01.1999
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Frame getFrame1() {
	if (ivjFrame1 == null) {
		try {
			ivjFrame1 = new java.awt.Frame();
			ivjFrame1.setName("Frame1");
			ivjFrame1.setLayout(new java.awt.BorderLayout());
			ivjFrame1.setBounds(109, 81, 538, 240);
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
	add(getContentsPane(), "Center");
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
		aSimu = new Simu(null, null, null);
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
			try{
				edit.repaint();
				Thread.yield();
			
				Thread.sleep(2000);
				
			}
			catch(InterruptedException e){}
			
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
					//System.out.println("makePossTransList: Vergleichspfad eins ist:"+returnDottedPath(temp1)+" nein");
					//System.out.println("makePossTransList: Vergleichspfad zwei ist:"+returnDottedPath(temp3)+" nein");
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
  
protected void makeStep() {

	boolean aSyncOnDo;
	
	if (aSyncOn == false) {					// Handelt es sich um einen synchronen Step ? Wenn ja, dann ....
		if (simuCount == 0) {
			firstStep();					// Mache den ersten Schritt
			simuCount++;
		}
		else {
			makePossTransList();			// Berechnet alle theoretisch moeglichen Transitionen (in possTransList)
			makeRealTransList();			// Berechnet alle praktisch moeglichen Transitionen (auf possTransList)
			killNonDet();					// Beseitigt Nicht-Determinismen (in toDoTransList)
			ExitedList.removeAllElements();	// Exited-Liste zuruecksetzen, denn in den folgenden Methoden wird sie gefuellt
			killLevelConflicts();			// Beseitigt Level-Konflikte (in toDoTransList)
			step();							// Macht einen Schritt 
			simuCount++;
		}
	
	}
	
	else {										// Es soll ein asynchroner Step gemacht werden
		aSyncOnDo = true;
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
				}
				else {
					aSyncOnDo = false; 			// muss hier eventuell noch mehr gemacht werden ? ******************************
												// ( Sachen, die sonst von step() erledigt werden )*****************************
				}
			}
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
private Vector makeTab(BvarList daten) {
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

private Vector makeTab(SEventList daten)
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

			workList = workList.tail;						// Verursacht NPE

			// Dann lege zwei "Kopien" der trs an, die spaeter gebraucht werden.

			trclone1 = ((Or_State)data).trs;
			trclone2 = ((Or_State)data).trs;

			connList1 = ((Or_State)data).connectors;

			//System.out.println("makeTab(S,P): connList = "+connList1);

			/*try {
				connList1 = (ConnectorList)(((Or_State)data).connectors.clone());
			}
			catch (CloneNotSupportedException cnse) {
				guiOutput("CLONE-ERROR in makeTab(State, Path), Kopie der ConnectorList");

			}*/

			// Fuer jeden Eintrag in der trs-Liste rufe makeTrans() auf, mit den Parametern:
			// 1. Eintrag (Transition)
			// 2. Der bisherige Weg (Pfad) in der Statechart (Bis zu DIESEM Or-State!)
			// 3. Geklonte Transitionsliste zu DIESEM Or-State.
			// 4. Geklonte Connectorenliste zu diesem Or-State.
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
 *			 3. Geklonte Transitionsliste zu DIESEM Or-State.
 *			 4. Geklonte Connectorenliste zu diesem Or-State.
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
		
	if (debug2) {
		System.out.println("makeTrans: 'weg' hat den Wert: "+returnDottedPath(weg));
	}
	
	// siehe (1)
	if (trans.source instanceof Conname)
	{
	}
	
	
	 
	else // hier ist also davon auszugehen, dass (trans.source instanceof Stanename) = true ist
	
	{	// siehe (2)
		if (trans.target instanceof Statename)
		{
			if (debug4) {
				System.out.println("makeTrans: 'weg' (2) hat den Wert: "+returnDottedPath(weg));
			}
			Path source = handClonePath(weg);			// Hier werden Clones erstellt, weil die Einträge in die
			if (debug4) {
				System.out.println("makeTrans: 'weg' (3) hat den Wert: "+returnDottedPath(weg));
			}
			Path target	= handClonePath(weg);			// transList nicht mehr veraendert werden duerfen.
			
			if (debug4) {
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
					if (debug2) {
						//System.out.println("makeTrans(): Versuche clone() von prevTrans");
					}
					prevTransTemp = (TrList)(prevTrans.clone());
				}
				else {
					prevTransTemp = null;
				}
				if (prevConn.head != null) {
					if (debug2) {
						//System.out.println("makeTrans(). Versuche clone() von prevConn");
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
			if (debug2) {
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
				System.out.println("makeTrans: WARNING ! trans-Liste hat NULLPOINTER !!!");
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
					if (compare1.equals(compare2)){
						if (debug==true){
							System.out.println("makeTrans hat eine Aequivalenz des letzten Typs erkannt !");
						}
						Tr newTrans = new Tr(null,null,new TLabel(null,null)); 	// Neue Transition instantiieren
						System.out.println("Checkpoint 1");
						newTrans.source = trans.source; 		// die als Anfangspunkt den Startpunkt der
																// Anfangstransition besitzt
						System.out.println("Checkpoint 2");
						newTrans.target = trclone3.head.target; // und als Endpunkt den Endpunkt der gewaehlten
																// Transition aus der Transitionsliste bekommt.
						System.out.println("Checkpoint 3");

						// Bestimme mit returnNewGuard und returnNewAction das zusammengefasste Label.
						Guard arg1 = returnNewGuard(trans.label.guard, trclone3.head.label.guard);
						System.out.println("Checkpoint 4");

						/*	 Das ist die alte "Spielweise". Wir haben uns dagenen entschieden, weil man
							 bei returnNewAction nicht ohne cloning auskommt und cloning bisher nicht
							 korrekt funktioniert.
							 
						Action arg2 = returnNewAction(trans.label.action, trclone3.head.label.action);

							statt dessen :
						*/
						Action arg2 = null;
						System.out.println("Checkpoint 5");
						newTrans.label.guard = arg1;
						System.out.println("Checkpoint 6");
						newTrans.label.action = arg2;
						System.out.println("Checkpoint 7");

						// Haenge dann die abgearbeitete Transition in die prevTrans-Liste (vorne ran)
						// unkritisch !
						prevTrans = new TrList(trclone3.head, prevTrans); //**************************************
						System.out.println("Checkpoint 8");						
						// Erstelle eine "Kopie" der uebergebenen Connectorliste
						connClone1 = connList;
						System.out.println("Checkpoint 9");
						
						// Suche das passende Connectorobjekt zum bekannten Connectornamen aus
						// der geklonten Liste heraus und haenge das Objekt in die prevConn-Liste (vorne ran).
						
						found = false;
						while (found == false) 										// Standartabbauvariante
						{	System.out.println("Checkpoint 10");
							compare1 = ((Connector)(connClone1.head)).name.name;
							System.out.println("Checkpoint 11");
							compare2 = ((Conname)(newTrans.target)).name;
							System.out.println("Checkpoint 12");
							
							if ( compare1.equals(compare2)) 
							{
								prevConn = new ConnectorList(connClone1.head, prevConn); //********************
								connClone1.tail = null;
								found=true;
							}
							if (connClone1.tail == null){
								found=true;
								System.out.println("makeTrans meldet: Fataler Fehler an Checkpoint Zwo");
							}
							else{
								connClone1 = connClone1.tail;
							}
							
						}
						// Rufe dann makeTrans mit der neuen Transition und den angepassten prevTrans - und
						// prevConn - Listen auf
						makeTrans(newTrans, weg, trclone, connList, prevTrans, prevConn);
						prevConn = prevConn.tail;
						prevTrans = prevTrans.tail;
					}
					if (trclone3.tail != null){
						trclone3 = trclone3.tail;
					}
					else{
						doLoop=false;
					}
				}
			}
		}
	}
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
 * Ruft auf							: makeTab(BvarList), makeTab(SEventList)
 *
 * @param
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 07.02.1999 :	firstStep() wrd jetzt auch aufgerufen; damit werden 
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
private void selectHighlightObject() {
	
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
	if (debug2b == true) {
		System.out.println("selectNonDet : Start !");
	}
	switch (NonDetChoice) {
		case 0:
			
			if (debug2b == true) {
				System.out.println("selectNonDet : Ende (Benutzerwahl) !");
			}
			break;								// Auswahl per Benutzerwahl :-]
		
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
	if (debug1 == true) {
		System.out.println("SelectNonDet : Ende mit Java-Ausgang -> Es liegt ein Fehler vor !");
	}
	return null;
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
	/*debug = status;	// = debug1 !
	debug1 = status;	// fatal error, nullPointerExeptions
	debug2 = status;	// Statusmeldungen
	debug2a = status;	// Statusmeldungen für selten genutze Methoden
	debug2b = status;	// Statusmeldungen für normal gentzte Methoden
	debug2c = status;	// Statusmeldungen für häufig gentze Methoden
	debug3 = status;	// Funktionsnachweise
	debug4 = status;	// Ausgabe von Countern und Variableninhalte
	debug5 = status;	// ----- */
	debug_get_guard = status;
	debug_make_action = status;
	debug_killLevelConflicts = status;

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
	
	printInList();
	if (debug_step==true){
		System.out.println("step : copyEnteredToIn() wird aufgerufen");
	}
	
	copyEnteredToIn();

	if (debug_step==true){
		System.out.println("step : Jetzt ist Inhalt der InList der folgende:");
	}
	
	printInList();
	

	
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