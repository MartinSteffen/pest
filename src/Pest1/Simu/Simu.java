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
 * <li> Auswahl bei Nichtdeterminismus
 * <li> Traces
 * <li> Asynchroner Modus wird (NOCH) nicht aktiviert, ist im Sourcecode aber schon vorhanden.
 * <li> Multiple Steps (NOCH) nicht aktiviert, im Sourcecode vorhanden.
 * <li> Reset des Simulators (NOCH) nicht aktiviert.
 * </ul>
 * <p> 
 * <DT><STRONG>
 * BEKANNTE FEHLER:</strong>
 * <ul>
 * <li> Bei einem Step werden die Transitionen nicht angezeigt, nur der dann erreichte State (in Arbeit)
 * <li> Keine Auswahl bei Nichtdeterminismus, Auswahl wird (noch) per Zufallszahl getroffen (in Arbeit)
 * </ul>
 * <p>
 * <DT><STRONG>
 * TEMPORÄRE FEATURES:</strong>
 * <ul>
 * <li> Ueber die GUI:
 * <ul>
 * <li> Statusausgaben ueber Fehler beim Erzeugen diverser Clones
 * <li> Statusausgaben zum Ueberpruefen diverser Features (z.B. welche Objekte gehighlighted werden)
 * <li> Debugmodus: Wird durch Setzen der "debugX"-Variablen eingeschaltet (wird auf die GUI-Var umgehaengt
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

public class Simu extends Frame implements java.awt.event.MouseListener {
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
	public boolean debug2 = true;	// Statusmeldungen
	public boolean debug2a = true;	// Statusmeldungen für selten genutze Methoden
	public boolean debug2b = true;	// Statusmeldungen für normal gentzte Methoden
	public boolean debug2c = true;	// Statusmeldungen für häufig gentze Methoden
	public boolean debug3 = true;	// Funktionsnachweise
	public boolean debug4 = true;	// Ausgabe von Countern und Variableninhalte
	public boolean debug5 = false;	// -----

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
	
	boolean DBStatus = false;            // Keine DebugInformationen ausgeben, temporaer, da
	//boolean DBStatus = gui.isDebug();  // die Abfrage des Debugstatus der GUI (hier) nicht funktioniert

	setDebugLevel(DBStatus);
	
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


		firstStep();
		selectHighlightObject();	
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
			LF1 = new ListFrame(bvList, DBStatus, this, bListSize);
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
			LF2 = new ListFrame(seList, DBStatus, this, eListSize);
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
 * Comment
 */
public void button3_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	String tempString;
	int i;
	tempString = getTextField1().getText();
	i = Integer.parseInt(((getTextField1()).getText()));
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
	}
	else {
		aSyncOn = false;
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
	}
	else {
		aSyncOn = false;
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
 */
private void copyEnteredToIn() {
	
	if (debug2a==true){
		System.out.println("copyEnteredToIn()  : Start");
	}
	
	int i,j;

	i = EnteredList.size();
	
	if (debug4==true){
		System.out.println("copyEnteredToIn() : "+i+" Eintraege sind zu 'kopieren'.");
	}
	
	i--;

	for (j=i; j >= 0; j--) {
		InList.addElement(EnteredList.elementAt(j)); // ****** moegliche Fehlerquelle ***************
	}


	if (debug2a == true) {
		System.out.println("copyEnteredToIn(): Feuer fuer High-Lightning :)");
	}

	

	if (debug2a==true){
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

 *  @version  $Id: Simu.java,v 1.19 1999-02-01 12:29:04 swtech26 Exp $

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
 *
 */
 
private void executeTransList() {

	if (debug2b==true){
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

			if (debug4==true){
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
				if (debug4==true){
					System.out.println("executeTransList : Habe alle zugehoerigen Actions abgearbeitet !");
				}

			}
		}	

		// Nun müssen die Transitionen und Connectoren noch gehighlightet werden :

		// Zuerst die Transitionen (**) :
		
		tempTL = tt1.tListe;						// Transitionsliste der 'abstrakten' Transition
		loop = true;
		while (loop == true) {			
			ho = new highlightObject(tempTL.head);	// HIER HIGHLIGHTEN WIR !!!
			
			if (debug4==true){
				System.out.println("executeTransList : Habe Action gehighlightet !");
			}
			
			// Dann pruefen, ob noch eine weitere Action gefunden werden kann
			if ( tempTL.tail != null){
				// Es ist noch mindestens eine weitere vorhanden ...
				tempTL = tempTL.tail;
			}
			else{
				// Es sind alle abgearbeitet => Schleife verlassen !
				loop = false;

				if (debug4==true){
					System.out.println("executeTransList : Habe alle zugehörigen Actions gehighlightet !");
				}

				
			}
		}


		// Dann die Connectoren (*) :
		
		tempCL = tt1.conn;							// Connectorenliste der 'abstrakten' Transition


		while (loop == true) {
			
			 ho = new highlightObject(tempCL.head);		// HIER HIGHLIGHTEN WIR !!!
			
			if (debug4==true){
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

				if (debug4==true){
					System.out.println("executeTransList : Habe alle zugehörigen Connectoren gehighlightet !");
				}

				
			}
		}
	
			
	}
	
	if (debug2b==true){
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
 */
private void firstStep() {
	String rootName;
	State rootState;
	Path  rootPath;
	int i;					//debug-Variable
	
	rootName = SDaten.state.name.name;
	rootState = SDaten.state;
	rootPath = new Path(rootName,null);
	
	if (debug1==true){
		System.out.println("firstStep erfolgreich aufgerufen !");
	}
		
	InList.removeAllElements();					// Alle bisherigen Eintraege aus der InList loeschen,
												// da es sich um einen Neustart handelt.

	InList.addElement(rootPath);				// Defaultmaessig den aeussersten Pfad eintragen
												
	i = InList.size();
	if (debug2 == true){
		System.out.println("firstStep : InList zurueckgesetzt auf : "+i);
	}
	initInList(rootState, rootPath);			// Initialisiert die InListe (InList)

	i = InList.size();
	
	if (debug2 == true){
		System.out.println("firstStep : InList durch initInList gesetzt auf : "+i);
	}
	
	bvList = makeTab(SDaten.bvars);				// Initialisiert die BVarListe (bvList)
	seList = makeTab(SDaten.events);			// Initialisiert die EventListe (evList)
												// bvList und seList sind eventuell leere Vektoren.
	i = bvList.size();
	if (debug2 == true){
		System.out.println("firstStep : bvList gesetzt auf : "+i);
	}
	
	i = seList.size();
	if (debug2 == true){
		System.out.println("firstStep : seList gesetzt auf : "+i);
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
 *
 */
private boolean get_guard(Guard waechter) {
	boolean goal = false;				// Rueckgabewert, default: false
	boolean temp1, temp2;				// Temporaere Variablen zum Speichern der Zwischenergebnisse

	if (debug2c == true) {
		System.out.println("get_guard : Start !");
	}

	
	// Suche Bvar in BvarListe und liefere den Wahrheitswert zurueck	
	if (waechter instanceof GuardBVar) {
		goal = returnBvarStatus(((GuardBVar)waechter).bvar);
		if (debug4 == true) {
			System.out.println("get_guard: Habe eine GuardBVar gefunden");
		}
	}

	// Zerlege Compguard in seine einzelnen Guards, rufe get_guard mit den einzelnen Guards auf
	// liefere den Wahrheitswert entsprechend des gewuenschten Auswertungsoperators zurueck
	if (waechter instanceof GuardCompg) {
		temp1 = get_guard(((GuardCompg)waechter).cguard.elhs);
		temp2 = get_guard(((GuardCompg)waechter).cguard.erhs);

		if (debug4 == true) {
			System.out.println("get_guard: Habe einen GuardCompg gefunden");
		}

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
	}
	
	//	Ueberpruefe, ob die durch Paths angegebenen States in der entsprechenden Liste 
	// 	vorhanden sind ( NEU : Jetzt mit : returnPathInList etc.).
	if (waechter instanceof GuardCompp) {

		if (debug4 == true) {
			System.out.println("get_guard: Habe einen GuardCompp gefunden");
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
	}

	// Leerer Guard ist defaultmaessig wahr 
	if (waechter instanceof GuardEmpty) {

		if (debug4 == true) {
			System.out.println("get_guard: Habe einen GuardEmpty gefunden");
		}

		
		goal = true;
	}

	// Suche SEvent in SEventListe und liefere Wahrheitswert zurueck
	if (waechter instanceof GuardEvent) {
		if (debug4 == true) {
			System.out.println("get_guard: Habe einen GuardEvent gefunden");
		}

		goal = returnSEventStatus(((GuardEvent)waechter).event);
	}

	// Negiere den durch get_guard bestimmten Wahrheitswert und liefere diesen zurueck
	if (waechter instanceof GuardNeg) {
		if (debug4 == true) {
			System.out.println("get_guard: Habe einen GuardNeg gefunden");
		}
		goal = !(get_guard(((GuardNeg)waechter).guard));
	}

	// Dieser Fall darf nicht vorkommen => Abbruch der Simulator-Instanz mit Fehlermeldung (wuenschenswert :-) )
	if (waechter instanceof GuardUndet) {
		guiOutput("Simulator: FEHLER !!! Ein Guard ist eine Instanz von GuardUndet !");
		if (debug1 == true) {
			System.out.println("get_guard : Ein Guard ist eine Instanz von GuardUndet !");
		}
		
	}	

	if (debug2c == true) {
		System.out.println("get_guard : Ende !");
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
	D0CB838494G88G88G05GBBA6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E13CDB8FD4145715B70959248DE7C352A4EBDB56522CBD71B46654B61C2D1E5A469EB9DB32313BEEE3D3B7E13BEC1613189C1B10BD34B5DB1BBE84C6D4147FB84200B37C9114E106E18684C4850144A829ECA44AE64D0601790411E1E6B2732118385D3D6F3D6F3E7966639B949357F32E5F3C5F7BF36F3B6F3E7B6E3B5F87A9BD10181B353D14102CDC227CBB12C5C8E29FA1EB3E52D103D89E319075BFDA
	AAA2662E9CF89A213461240594120606BCA8DB21FC8A4A7B60F78931B6941E049FB178EC6EA5642E401FAC7373F97FB07259414A45C579705C8B548258B0D082217C8F979995FC964AFB155F143236935218C55B14359BD5F81A52EE9B6D8F75C92C4DC01136CD907045C089C0BB19E4FB0ABB147A8D0EC74848DB1506BD140F2A9F5236AC4E433C1DBF89F9A317AAA11BBC7ED181BC3B0726A60E073D53B3DDDD6760FF923F1D4AF034BD0F35A1E4844A26EC0ABD59CE673D914ADB00F232D1161F54F4AB3C623E
	7AA8A84A94D31651ECAE43626C08AC330D29D91C67D49172CC4EC61EA70C1467B678DD1AE3A1569C44BFEA25F8BF14AF8249B9A84BCB87D116D501C749456A58324465F099D666C4E4D9DF1E1A351F2D71AF1B689AEC013A42DC8B2983BA85B489F4AFD0A5332D2F9EBE8C4F5EB139AFB0BD9D70778603A7FD1E50FB810F64035FDDDDD023E06FCB63011007A8765353EE551187A9B157B28763F33BC0F04D7B639348C33253102BB337523D3506A4E50C145C485C728DD4EFCDC7285EB683CE7BD15DF03C0B61C3
	0688BE3803622B591A53328CF5E9865473624FD016614F9EA0E9672C069DFA32BC61B0446F60B2AC5F9111056BF8D5B35571C640B3007272AC448C5483B41A07BA7E57434E9B6978DD3FAC057CEE5F39193120BBA41F1C717ABCD2C851F530A8162E2F674D5573DE210FA7195D4567F1FD648A7CC78CDF2642391E8698CE4B2B204E345322347F87E6270BF67276BB45B83FE4F8028A67630CB31C16D7C11DE91705F0DDD55B292E96C1B981A889E8BD5096203C5D28ABDB137DA66DF1C825A75E776526F2F8CA33
	F2487D2EBFAC3B7D6352F9F7486B9E73C9E1223449E822BA9D0B6A77EB36CF460147BB12DF8A39E5EF408F233B27A5D814B0EF73D6D391ED9312D86D30F7DA828653C102639A2CA5707439437287C10FDB16A2ADF87DDEAB55479577F9989186005FA33B292EAEB09F55883F7BF7231E86DAA816G65A42117AFD90B61F9860CC472F5F575FB4729986E50B55C0F0EA3F48EA950FEB09F7A811976D8C8A25036BDE86F2F0D203DAFB2FD1D2CF938CE575E13FED0E6706C65F6F5FDEF445E15FD4F78AC83BC99A89D
	A827GFC8BD0FF811EADCF350F6A58A44AC6E522E5AA9F15B3168F5123EDFBA26DB92F67F5F9E5ABF63C1BC4FC4F0E85702134C7751B6BEF39D36D3BC6B38F6A6AEF4539DA0327C051DF0156DF3C6AE0FB8670D5C01B8AF0FD132DE6EE77A75DE189AD7DEC880CD472C4D61B377DF1B3BD7324AB12DF6E1702125F83BF424AB8A94DBBA3FB60D2C058675FB11C7A16BEB0523040B7B697F19C46D06FA82CCFE575EED98EF947E6E4691CA44BDE7FC424FFBA13A59C6536420AED5F434E428C18E3DEA15A46EAA30B
	EF205CA330E4068D952A5B1DB10E44D8975A17BEFB8A97E6BBEA62BCBCB1F9E4AB4F5CDB9CBF4263972D05AAFB5C21680D572533BA996611G148C34C54C5F5BBA2212857B5E8C5F4A1B166D4EDB663173A665A8A18D765836EA336B5B2A4B2EEF2BDD7679ED7124DD4F96F95D3BF66ABF4783FE3F44B61328A6BC0EFCED184A6C75FB658BDE4F04A41F73FEAAC1DCD9C46D584FFCF4A97CEEACC2DFFA3596FDC16340DF1016DF156B46B17927DC06C5C55CCE9FAA0A7882AE5369966A8BA41FB48DCA698B4C78E5
	0E8FB17F2F6061EC06E50C4F40535CF8F2C61683FE17F5BAB09316EC6C7FBE1FF7FCCA72B4DEF6DFF15B5CD7E59B53366DBDDAB7C0FF9EB2B7B79FB7534058BA463BFF7C1E3A63E04C1EC78F1DBF64D06C230454CFFD281221757349509AAD83B0EAC5BE1681B59F676763637C3817ADB82B8DD3BECE64F3577619AF124F82F46A94BA9D39F4D6A503436AE6425A3423BB96B23ACB99FB097CDFD1E3FF2E79B9662C17432944AB35CEEA47C9E05FA9C0E9C5F8262DEF21759833A807D9DF406763DB906AC60A697E
	3AF224D435E73696717BE8BF54717A5F6A56A70A7A598E3D7AA4D17F26EEFD0A287F34D32FBED5547FCE371E7E66733738687CB320EDDE090598CBF07E390E92557C878301291960B00DC6B196F5D07F9F16BE1911A0D42595E724EBCA3C79DBC731266E3C5BB79309E37F6020310DCFB5A677E3AB5D948F063C5390AE5083DD3933AF0D3A0FDBC1C6FA3F9F0467A8903717BB4F9A6079B68DFC9D6897578D533C813583BE4EEF0CFA634C42B38EE8241436B1BACDCA7DB24056G25AA6D0C25DCEF3C5D8B11F6D0
	DE8314DA2A34ABD337DB6A42F65D008F17C55A49D16D5EEF43F6C9002F875AD2465BB51673F60365345D5DA2AFE097D882431E8585FCBF87633D778A557989F2CF0B2E0F27942F0C3F2CDC9B6F8D3751F113815FD80EF671E92B5AAEFA4361403817DDAB147AC3CE357FD74ECA67AEF8DE720642F0D774C833622CF4D2D9693DC30115F5FB3C699819FB0F9A70F865C088434B3E2BEC2B8F877A606AF38ACC29CF9C0F673D52D5BAAF1653F862886AEBF3B91EFFAFFE284E17485BCD065DB1F4E35E2744EB7BA23A
	09AF47FC092D8347DE84759C7B0E40D6892C2593318C01FDD7E00DC654ED03CB7FCCEDF6D1BB1E927B4B612236FEC559D3DCE72BCB68BEE739A4740BDDDDECBBF0FE4F31F816369DB5E28EE7E9A96537844AAB01D20CA81BEDBFEDCB6329B451F6FDA5EDEB5CEFA1ADC0270078FD395709776ED140166CC73F6334297D4E8640B3010C9538DF17BB1B6139A02FAE6C63FC8B9C37E46161G6E0D9D3BFA9439961184325651EEA850D97F783B2A8C568A3E6E43951175EF3C101A558D44671F634279A7C21BCD95B8
	7F23E63D7997D6627C27DC37BEFFD23ACF4CFFCEA8222C4BCF04DCF54073E2A54A952C51136BA12867E37BD53ADDC8D80079134F5A106FF298B3CD707D38C5579ECC280F975BEEDD9F5FD069E34ED10F393BD635BF5CFC79C0CCFFE8B5A9FB5E2475079D4C17DC84BCE842BD7B382BC8658F5F878F6476CF400906BE6EBF5B32795ED54A9F3565625EA5F817795E11797211DADAEC42DC4B8C0B8D16C0B989E8BDD0BAD0CE954A165EF6435C5D406CBC39BBE747C2642A2B427BDF52027AF508FEED1D8B69B7A87A
	7D75027A0D0AFEFB0A357D642A483D7C5E2A39F7F41EBBACAEE3F910AA1EBB4C96380961ABD4B85F934B181F2565B5D0E72EC67E4E7DDA7E0B2BA3BC17D4C773DFD10DB94D9E939DEFED35167FE906EF2856727FD9A572CF033A7E83487F9D27167F728351BC13B5E55E6F03B97D2AB5654D1AFEFC1F7E2C2B0C4CB923F800CF707D4C9DA6F50CC64C9616DBB14353E5467369328D4F27C1B36E05E7BAE97C34984A09C02B00B682ED85AA35D8C80785770433DDAF36085E9331820BC8FE096BE1176B5674E71C53
	4F26A90FD8227B71F9074CB86F208557702737A8438BF37AE957F0392E8C0F55208CEB058CF7345D1A8C774F69275543D6DD999EE941D826308657FF77D10A1D0272C88D2EF5FCBB4D0322EF9E8AF87DB2C13FFC6C880D2182A16F0457AFF2B08C9B8FC07B9066381FE6BEFF9246DCAC467D1F0E58F921459D7AF921AFF768670562BB664FE1AE690815B762721D6EC079165039B36C99012D9058E0A7E2EB8576D301ED94D8DDB145B6C3F91B407614D04CD48BFB3096575CD8C6E35291D12EE16520A8FF3BB4D6
	FEF221C4F9E8B14EE35D9746A3DB0FAF57AF0B059F8299165722BE1C95280F94018DD7D2EC8B14C99D6A60ED9355C93EA88F31B20DFBF8F9D36597A5BF65212F23B73EB09EFABA2AE2785365854C0643E1A94C1CE73FDBF6ABF5AF4B54674F49DD7E91AEA68473C8FFE277C05AE6C08E745178131F977F5828F32799F243EE66FEE035897DC0DD9D5ED3F22A69D9F7914AB2D01C50FD40B6DF0C308C5A2DD2F438DEF9727D77734E58FBBD35D3FF2F3F58C97DCEB8B0939A17347B7915CE23280B1CAC6F4A52B4D5
	89EF73EF2C3F6C8ECDC8F37C411B2CBF2F0B55FFFBD7AC1F41755D6BD26B5BA9EC652AF9BE9D2501DEB2955D6455DB04CD14101CB675F8AEB15E0A1A7946B34198AD4AB87DD1632D0A9A4FA1467BFA4BFC630D54C776002FDE3D0605C7E9BCB4AB4A252CFCCD5819543BD768F4406FD18E8A1EDBC9E37E3F84BA3491445C4C3F7EFABBA18382FB11E16330411D829BE77EF5B5E07FA130F4065D835859EC1C6F51E2FA6E785C5782B340F7EDBD4B87952B6FCB0B1479AC2F47FB5AF72F521C45A5386043521E8BCA
	63A461A0735FA598BF65C15984548DF491687AC13CEBFD65825DA3E0BDB2B5E63A21F86CF52ECC9BFB51F7DA6950EF2B686B5EBFD756597DFA3296B6D89419CED760380D00F583DD843ACE6B9B44FD38124ECB764A6C6E342601FE79F3E50C881B18ABB337099F1934B6C19AA3F6D05A98AB06FF6DG9D2F3111474A8582FF0B61C3AA1C47503B18DF2165D9284BECC43F7FE7B6BC8B020790FB4C4E4E8CA8AF975896062D017286017D39997B268A6CBB966CCB8EA3365502FDCB85362E867B1A8516DE0BFD7B85
	F6288E7B0E886CFBAD5857A330E79D943B974A89075146DE32517D220D5783B497BE488D007BDCDFB1554DF2683796E853E14C33FDE98673EC5DC73FC136DC2C524D332D78365310F1D8311926481DBBCD6459CE337803769B047A45CD4C66CA54B6C7ED64846171A32F4FABD557D3FF78C2363A3EAB2A1E4AFBF7D4FFDFD4BD5547BBCABD1F5B2B65E2EE3F7FA631BE907B7B2F754A1CB214E77A5BCF640EA6625C6EAB43B32C510AE758F365D41F23D01E850A9778037B191E213C96E813956FC3418A54CF0695
	753334C22B1FFF49D24FEFC305D6BFFF95B57F6D15DA7D3465286BDD15DA7D3C90251FFD9F2A73ADBD6BEA8CD9B17453584C7562EA0E2CBD37491F1BB0761AECC6BBDDDD0536FBEFB31E6D1987685DAF894AA9C029C019C079B68B29836A30E13B9CB3EDC7F375B48FBF88388FA84E46737CB4674E5B95D6871571E8FFAAD307AD62C7D21BEF7C6EBEC5E913F993ED731531B9EFBB63CD578B3F61A8D0353D19F75C785E7FE65EF363B7A65CCF7F258D7D748A10E9ADD09A9031030F31231F7E9B8B15C1725357B6
	9EB2ECE736E4C77B6B916D8AEA289CBE771864A3684F93BDC4707A1699F9AD01BEAB005683ED814AEF31A8637E1385F364D64078987FACC546F83E864768067AA120A0D0BC50CAB1461EBA14ED039823FAA2B24653ADB8C6BA54EF85B2BA40E600C69CB817D79DF367F267A49D031F99E94C7F4DBA781911A570D7983ED8057333A444F0DA0E073ACC875EE1BDDE3A27C6DAA1F6850A83DA86145C0A774E9F0E35109B64AE068341C188D6FD2A2BA4372B33A58B4911217D90D2E1C1BFB7B979B592D7D59D73DDC9
	DA2B32FF1D4AB7D246A7F269134F6D0D4BD44FADD0B78894843A97A881A849A95EB90EDB797C682B1EDED9F60FCF3237C8B8B97AE2A7825320074FAD3ECCBB379427EA3FBAE345G04FDB31861542E5BCE7E4D2BD33BEE296538EEF9AE709DAE4C4799AAD85E4D259D27006129AE6DB8CB7663B81B21EE308D7D4ECC45C257086B36141A252243B3637570BC6F3E2C7B566CB49C63E747AE4325E158CDB7810F5F5EB229FD794AB6651C02E7E1BB5B5FD566F9E45856260E5D6F5FC56722FCE521E00B9AA218D341
	4E6C226F840761727436D772F99C587F97D5546E7AA62571293140EC647B0D9FD62A71D6017F60C2E4EC7CF6A02DA802B914778F89F56AB90E3473778F575B717D447A036A7AF825BEC9546F306A55273463993C4E06FEA6359D4F60B7EC5AB318FEAF91B9C3EB6D5AB338A92A7E833B768C0EBE236F31EB4FE00BF246F3BD3CEC2758A537AFAC915C4F4BA65467722627EBE23E87A9BD425735D1F9CE16BC114B677A0C9967BAFA84677A26D9BB578BD132B6D934F3BD94D57F11C5BB57EFC5555FEF514E95F545
	671AE9213995B9B4233C6FA435A86392A1232DD6AB6387D1BCC6EB35B2BE9CD57FE02DD646EFC655A757EAE534C749D8D727DE8FAE676BAD62FC92F2D6B934F25A23781CF5E865EC0B2A3F432115733128586FC907D64E126D3C1E7B211768679B4A6F57D53FE70B3F28DC129EF19E8F5CD69E5CC77A3C31F3BAE12FFECE671AD7BFFF7B3F5E793F6B3B472B77DD9F775B1353F49D72BAA04E84BA85F491A8580167613E4B748FF068D763F34F42814F844D9F51AF45E564756F17E34FABEB6A465F176F1A22BEF9
	ACGFEF31A0EAF72A7D3F4ED64C010019CAB1B227A88F9A7A6E55ED441AB2728BD7B24CB8CD66E43E518D30E6B40B87E3EF20C63D7F46079E7B0C6EC2DC0757B45725BEFF73F7BFF6011DF9119D3196A77527D371F7777EFAB8F1EAF18282131874B970C8726ED5E31E95B4C15E91BFBCC36C99006F4F57DF14667D3B287C439539A2BE8AC3489ECA083A82F93E36EDFD551BCD31FC47F36844F2E7B6AB0964DEC7B9A59780A43C0771E6EF7BB1D4AB7D31D11BBE5C44EBF30BC17A62FA1C2916E171F2D4377DC0B
	BAB19F7206FD5F5CFE9812A87EBC1B7D8D9C7B7B11CE0C8D0EDBF12C0CCE347514F97351FA44FD465F7A595FDCF4D9C8FF977A09C7FC74AC540D79G9C8A8502D2C83E46D176A51EA24306F9733D31EDA93776567AE54E1B3F55A3BE3F60F4EC3FF6F57A46FEED51B47BA68D7C5170352012A33FFB1A3D7F3BF4A9AC897F796569C2013D9B9E8A043DB4374EE3648DE5FAB132278BE364C74B4D734C63FA97EF4F6F85F14C66633B34770507991E50253DAFD4D761FDE1B954659F457B626F8314E7904AF1C0AB01
	D200D201B201723A615E5E0DB6E272B74EE3A3BC29474E13105B6B171577D3F5FA6B555D4D67B284CFCF375EFB5D3C067956F9967A44A97D926119540DFB224FBADF3F94E8172A744B68D667777F5ECFFF0BB3181B38D2771C1F2E9B7DB65E6B76F9BFE51FAA1E03067E896EFFDEE55FEC649C339093106B987A1F274A557E074B77A0FD25056FA2D43F6B2AEE3F7FBEF6DBF9F0DD6585E36F3302600D7719B1289FBFD48767468F35C17D7861D0D09DBFA85F0FAA6306440BC3657B51A04D1171F7916F397D6E89
	761E0C47972FB37FBD886BF97118F8C7DB0FB1472440E68EE24EBCCEE0879A30DD22408CCD58AED9E0E71AB0373EC9E0AB98168E652D827B500A63659FC76C33E69C2FC5E0EB1BF13C7E6318DB78EF16F398BA2E7595938C3FF8DC6BABB24A50D7CCC25D7A63E84F06FAF6BF028564792B9FFFC26D176740266AB18756F282F883F9009683ADBB01F9342587711E12FC825F6B34B7E03FCD00ED814A6F3190AB50E08F7633B7E1BFDF8F665F888D0F953956B55133958CCA26B6407BBC5FA43E2B6DC1DE1FD811D7
	BCE04B0012015201F2FA115732E664D5570B3CCE4811FD6AA90F7C1ED67D7ECD596D5F3F8537B53E64F1468375B87F0EDE3CCBBED52F3DCB2A6F0A7F27898D05CB517B12287D6E454B507B1246A872D7172177A517220CC118AE436C735ED98EF3678738BB3916636F735AAC4798BFC0695366ADC7F7E7BB0A7C0295686E5C59006C4EE32B115D8991ABDC0DBA26A03E8666EE8D383BDD5760F7F7799A0CB19E94F72DDC0B6E6E09A8723757E2383B89595D56EB115D8DC9EBE2CB49EB1FBD44DBEF6356A703375E
	4606A1E7C3B86CG4D416B64B634FEE918FE7D651BB72F5F3F85F6F2973CFC8B5E3C7366A130057225217552D3E0FB6CCBC34759072F1FDE7E7274431706CE90739C045DF579710BB7AF1FFE7E8C2C2C9A9AB7FEE918F479673B47AFBE3DFF77745302C3CF01D57D53C7DF9AA69F7C0CA15AG6BD7F0ACC59E933BF2E9AE6EB1314350B1314368EB032703571FBD810AB39C0635391E6F822DE946B596FB796D43B7EF3EBCFC737A603BA720F159C3BF3FBCC50C9B4C598DB27FB3CC8DD492DAF76E86397B95129E
	40GD0CB87882A1B3F970096GG48C3GGD0CB818294G94G88G88G05GBBA62A1B3F970096GG48C3GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG3A96GGGG
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
			ivjButton3.setEnabled(false);
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
			ivjButton4.setEnabled(false);
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
			ivjCheckbox1.setBounds(28, 148, 131, 23);
			ivjCheckbox1.setEnabled(false);
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
			ivjCheckbox2.setBounds(284, 149, 136, 23);
			ivjCheckbox2.setEnabled(false);
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
			ivjTextField1.setEnabled(false);
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
					result = true;	// Ergebnis ist positiv ! 
				
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

	if (debug2c == true){	
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

		Path copyPath = handClonePath(pfad);
		EnteredList.addElement(copyPath);	// Natuerlich nicht das Original benutzen !!
		if (debug2c == true){	
			System.out.println("inMethod : Trage Basic-State "+returnDottedPath(copyPath)+" ein.");
		}

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
		if (debug2c == true){	
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

	if (debug2c == true){	
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
		if (debug2c == true){	
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
			if (debug2c == true){	
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
			if (debug2c == true){	
				System.out.println("inMethodInit : Trage OR-State "+returnDottedPath(copyPath)+" ein.");
			}		
		}	
		// Der default muss natürlich auch in die EnteredList.
		pfadclone = handClonePath(pfad);		
		pfadclone = pfadclone.append(((Or_State)tempState).defaults.head.name);
		EnteredList.addElement(pfadclone);
		if (debug2c == true){	
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
 */
private void killLevelConflicts() {
	int i,j;
	Object t1;						// Temporaere Hilfsobjekte fuer die aeussere Schleife
	Object u1;						// Temporaere Hilfsobjekte fuer die innere Schleife
	Path p1 = null;					// Pfadreferenzen aeussere Schleife
	Path q1 = null;					// Pfadreferenzen innere Schleife
	boolean kill = false;

	if (debug2b == true) {
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
			if (kill == true) {				
				if (debug4){
					System.out.println("killLevelConflicts : Will Transition von IN in ENTERED-List verschieben !");
				}
												// Enthaelt Objekt j das Objekt i vollstaendig
				removePathInList(q1);			// und ist groesser, wird Objekt j von der IN- in die
												// EXITED-Liste verschoben (mit removePathInList() )
				if (j < i) {					
					i--;						// ggf. Zaehler i anpassen.
				}
			}
				
		}
		i--;
	}

	if (debug2b == true) {
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
		if (((ActionStmt)aktion).stmt instanceof MTrue) {
			MTrue temp1 = (MTrue)(((ActionStmt)aktion).stmt);

			isInbvList = returnBvarStatus(temp1.var);
			
			if (isInbvList) {
				// Bvar ist drin -> Mache sie wahr !	
				position = returnPosInbvList(temp1.var);
				if (position > -1){
					// Position der Bvar ist gefunden worden
					bvList.removeElementAt(position);
					bvList.insertElementAt(new BvarTab((temp1.var), true),position);
					if (debug4 == true){
						System.out.println("make_action(MTrue) : BVar "+temp1.var.var+" in bvList auf 'true' gesetzt.");
					}
				}
				else{
					// NPExep abgefangen
					System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
				}

			}
			else {
				// Bvar war nicht zu finden -> ERROR !
				System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
			}
				
		}
		
		if (((ActionStmt)aktion).stmt instanceof MFalse) {
			MFalse temp1 = (MFalse)(((ActionStmt)aktion).stmt);
			isInbvList = returnBvarStatus(temp1.var);
			
			if (isInbvList) {
				// Bvar ist drin -> Mache sie wahr !	
				position = returnPosInbvList(temp1.var);
				if (position > -1){
					// Position der Bvar ist gefunden worden
					bvList.removeElementAt(position);
					bvList.insertElementAt(new BvarTab((temp1.var), false),position);
					if (debug4 == true){
						System.out.println("make_action(MFalse) : BVar "+temp1.var.var+" in bvList auf 'false' gesetzt.");
					}
				}
				else{
					// NPExep abgefangen
					System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
				}

			}
			else {
				// Bvar war nicht zu finden -> ERROR !
				System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
			}

		}
		
		// 3. Wenn ActionStmt eine Instanz von BAss ist, dann bestimme den
		// Wahrheitswert des Guards und weise ihn zu.  
		if (((ActionStmt)aktion).stmt instanceof BAss) {
			BAss temp1 = (BAss)(((ActionStmt)aktion).stmt);
			Bvar temp2 = ((Bassign)temp1.ass).blhs;
			Guard temp3 = ((Bassign)temp1.ass).brhs;
			boolean temp4 = get_guard(temp3);

			isInbvList = returnBvarStatus(temp2);
			
			if (isInbvList) {
				// Bvar ist drin -> Mache sie wahr !	
				position = returnPosInbvList(temp2);
				if (position > -1){
					// Position der Bvar ist gefunden worden
					bvList.removeElementAt(position);
					bvList.insertElementAt(new BvarTab((temp2), temp4),position);
					if (debug4 == true){
						System.out.println("make_action(BAss) : BVar "+temp2.var+" in bvList auf "+temp4+" gesetzt.");
					}
				}
				else{
					// NPExep abgefangen
					System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
				}

			}
			else {
				// Bvar war nicht zu finden -> ERROR !
				System.out.println("make_action() : BVar nicht wiedergefunden => ERROR !");
			}
		}		
	}

	// Wenn aktion ein ActionEvt ist, wird in der EventListe der Event
	// auf true gesetzt.
	if (aktion instanceof ActionEvt) {
			
			SEvent temp1 = ((ActionEvt)aktion).event;
			isInseList = returnSEventStatus(temp1);
			
			if (isInseList) {
				// Bvar ist drin -> Mache sie wahr !	
				position = returnPosInseList(temp1);
				if (position > -1){
					// Position der Bvar ist gefunden worden
					seList.removeElementAt(position);
					seList.insertElementAt(new SEventTab((temp1), true),position);

					if (debug4 == true){
						System.out.println("make_action() : SEvent "+temp1.name+" in seList gesetzt.");
					}
				}
				else{
					// NPExep abgefangen
					System.out.println("make_action() : SEvent nicht wiedergefunden => ERROR !");
				}

			}
			else {
				// Bvar war nicht zu finden -> ERROR !
				System.out.println("make_action() : SEvent nicht wiedergefunden => ERROR !");
			}
	}

	// Leere Action: Daher passiert nichts.
	if (aktion instanceof ActionEmpty) {
	}

	// Wenn aktion ein ActionBlock ist, werden die Elemente, die Actions sind, mittels
	// make_action() ausgefuehrt. Zur Sicherheit wird dazu ein Clone benutzt (temp2).	
	if (aktion instanceof ActionBlock) {
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
 * @version V1 vom 14.01.1999
 */
protected void makeNStep(int n) {
	
	int counter = 0;
	
	if (aSyncOn == false){
		while (counter < n) {
			makeStep();
			counter++;
		}
	}
	else {
		makeStep();
	}
}
/**
 * makePossTransList, created by HH on INTREPID
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
 */
private void makePossTransList() {
	int i, j;
	int x, y;
	int counter = 0;		// debug - Variable (zaehlt die eingefügten "abstrakten" Transitionen)
	i = InList.size();
	j = transList.size();
	Path temp1;
	TransTab temp2;
	Path temp3;
	
	possTransList.removeAllElements();		// Loeschen der alten possTransList

	if (debug2 == true) {
		System.out.println("makePossTransList: Zaehler fuer InList meldet "+i+" Elemente");
		System.out.println("makePossTransList: Zaehler fuer transList meldet "+j+" Elemente");
	}

	i--;
	j--;
	
	// Wir vergleichen jeden Pfad der InListe mit jedem Sourcepfad der Transitionen der 
	// TransTabEntries aus der 'abstrakten' Transitionsliste (transList).

	
	if ((i > -1) && (j > -1)){	// NPExep abfangen
		 
		for (x = 0; x < i; x++) {	// InList - Schleife
	
			temp1 = (Path)InList.elementAt(x);
			
			for (y = 0; y < j; y++) {	// transList - Schleife
				temp2 = (TransTab)transList.elementAt(y);
				temp3 = temp2.transition.tteSource;
				if (comparePaths(temp1,temp3)) {						// Vergleich mit comparePaths-Methode					
					possTransList.addElement(transList.elementAt(y)); //*************moegliche Fehlerquelle******************
					counter++;
				}
			}
		}
	}
	else {
		if (debug2 == true){
			System.out.println("makePossTransList : Unwahrscheinlicher Abfang einer NPExep aufgetreten !");
		}
	}
	
	if (true){
		System.out.println("makePossTransList : "+counter+" 'abstrakte' Transitionen eingefuegt. !");
	}
}
/**
 * New method, created by HH on INTREPID
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
 */
private void makeRealTransList() {
	int i = 0;
	int counter = 0; 	//debug - Variable
	TransTab temp1;
	boolean bguard;
	Guard tguard;
	
	i = possTransList.size();

	if (debug1 == true){
		System.out.println("makeRealTransList : Start ( überprüfe "+i+" Transitionen ).");
	}
	
	i--;
	if (i >= 0){		// Vermeide NPExep
		
		// Gehe Vektor mit den moeglichen Transitionen durch und eleminiere alle
		// Transitionen, bei denen der Guard nicht erfuellt ist 
		// => erhalte Liste mit real moeglichen Transitionen ( in possTransList ).

		for (int x=i; x>-1; x--) {
				temp1 = (TransTab)transList.elementAt(x);
				tguard = temp1.transition.tteLabel.guard;
				bguard = get_guard(tguard);
				if (bguard != true) {
					counter++;
					possTransList.removeElementAt(x);
					if (debug4 == true){
						System.out.println("makeRealTransList : "+counter+" Transition(en) geloescht.");
					}
				}
		}
	}
	
	i = possTransList.size();

	if (debug1 == true){
		System.out.println("makeRealTransList : Ende ( "+i+" moegliche Transitionen identifiziert");
		System.out.println("                  :         und "+counter+" Transitionen geloescht).");
	}
	 
}
/**
 * Diese Methode macht genau einen Simulationsschritt, der entweden synchron oder asynchron ist.
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
						Tr newTrans = new Tr(null,null,null); 	// Neue Transition instantiieren
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
						
						found = false;
						while (found == false) 										// Standartabbauvariante
						{
							compare1 = ((Connector)(connClone1.head)).name.name;
							compare2 = ((Conname)(newTrans.target)).name;
							
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
 */
private void removePathInList(Path KillPath) {
	int i;
	boolean found = false;
	boolean test = false;
	Path temp1 = null;
	
	if (debug2a==true){
		System.out.println("removePathInList : Start");
	}
	
	i = InList.size();
	i--;						// Vector beginnt mit Element "0"
	
	System.out.println("removePathInList: Groesse der InList ist: "+i);

	if (i > -1) {
	
		while (found == false) {
			temp1 = (Path) InList.elementAt(i);
			test = comparePaths(KillPath, temp1);
			if (test == true) {
				InList.removeElementAt(i);
				ExitedList.addElement(temp1);
				found = true;
				if (debug3==true){
					System.out.println("removePathInList : Zu entfernenden Path gefunden");
				}
				}
		
			i--;
		
			if (i < 0){
				found = true;
				if (debug1==true){
					System.out.println("removePathInList : Der zu entfernende Pfad konnte nicht gefunden werden !");
					System.out.println("				 : FATALER FEHLER !!!");
				}
			}
		}
	}
	else{
		if (debug1==true){
			System.out.println("removePathInList : NPExep abgefangen !");
			System.out.println("	|InList| = 0 : FATALER FEHLER !!!");
		}
	}

		
	
	if (debug2a==true){
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
 *
 */
private void removePathInListLinear(Path toKill) {
	
	if (debug2c==true){
		System.out.println("removePathInListLinear : Start");
	}
	
	boolean kill = false;
	int i,j;
	Path toCompare = null;

	removePathInList(toKill);							// Entfernt den Vater	
	i = InList.size();
	
	if (i < 1){
		if (debug1==true){
			System.out.println("removePathInListLinear : |InList| = 0 aufgetreten !");
		}
	}
	
	i--;
	for (j=i; j >= 0; j--) {							// Schleife durchlaeuft die InList
		toCompare = (Path)InList.elementAt(j);			// Bestimmt Element j der InList 
		kill = greaterAndContains(toKill, toCompare);	// Bestimmt, ob Element j Sohn von toKill ist
		if (kill == true) {								// Wenn ja, 
			removePathInList(toCompare);				// aus der InList entfernen und in die ExitedListe 
														// eintragen.
			if (debug4==true){
				System.out.println("removePathInListLinear : Habe einen Sohn gekillt !");
			}
		}
	}
	
	if (debug2c==true){
		System.out.println("removePathInListLinear : Ende");
	}
}
/**
 * Setzt die Enentliste zurück. Genauer :
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
 */
private void resetEventList() {

	if (debug2b == true){
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

	if (debug2b == true){
		System.out.println("resetEventList() : Start");
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
 * @version V1 vom 14.01.1999
 */
protected void resetSimu() {
	simuCount = 0;
	bvList = makeTab(SDaten.bvars);				// Versetzt die BVarListe (bvList) in den Anfangszustand
	seList = makeTab(SDaten.events);			// Versetzt die EventListe (evList) in den Anfangszustand
	LF1.update(bvList);
	LF2.update(seList);
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

	ho = new highlightObject(true);
			
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
	ho = new highlightObject();
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
	debug = status;	// = debug1 !
	debug1 = status;	// fatal error, nullPointerExeptions
	debug2 = status;	// Statusmeldungen
	debug2a = status;	// Statusmeldungen für selten genutze Methoden
	debug2b = status;	// Statusmeldungen für normal gentzte Methoden
	debug2c = status;	// Statusmeldungen für häufig gentze Methoden
	debug3 = status;	// Funktionsnachweise
	debug4 = status;	// Ausgabe von Countern und Variableninhalte
	debug5 = status;	// -----

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
	if (debug2a==true){
		System.out.println("step : Start");
	}

	highlightObject ho = null;		// Fuer HIGHLIGHT
	
	EnteredList.removeAllElements();
	
	if (debug3==true){
		System.out.println("step : resetEventList() wird aufgerufen");
	}
	
	resetEventList();

	if (debug3==true){
		System.out.println("step : executeTransList() wird aufgerufen");
	}
	
	executeTransList();

	if (debug2a==true){
		System.out.println("step : highlightObject() wird aufgerufen (FEUER) !");
	}
	
	ho = new highlightObject();			// Nachdem alle Objekte an den Editor uebergeben wurden, 
										// wird die highlight-Funktion gestartet.
	
	if (debug2a==true){
		System.out.println("step : copyEnteredToIn() wird aufgerufen");
	}
	
	copyEnteredToIn();
	

	if (debug2a==true){
		System.out.println("step : Ende");
	}
}
}
