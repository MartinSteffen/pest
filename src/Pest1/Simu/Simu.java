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
 * TEMPORŽRE FEATURES:</strong>
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
	Vector bvList;
	Vector seList;
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
	public boolean debug = true;
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
 * @version V2.07 vom 24.01.1999
 */
public Simu(Statechart Daten, Editor eEdit, GUIInterface igui) {
	gui = igui;
	edit = eEdit;
	Path weg;
	int bListSize, eListSize;
	SDaten = Daten;
	System.out.println("Simulator aufgerufen !!");
	// SDaten = example();					// Eigenes Beispiel

	bListSize = groesse(SDaten.bvars);
	eListSize = groesse(SDaten.events);
		
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
		getFrame1().setLocation(10,10);
		getButton1().setSize(211,26);
		getButton1().repaint();
		
		
		//firstStep();

		
		guiOutput("Simu: Bis CheckPoint 1 gekommen !");
		bvList = makeTab(SDaten.bvars);		// Initialisiert die BVarListe (bvList) - nur Debugging, sonst in firstStep()
		guiOutput("Simu: Bis CheckPoint 2 gekommen !");
		seList = makeTab(SDaten.events);	// Initialisiert die EventListe (evList) - nur Debugging, sonst in firstStep()
		
		//try {
			if (debug==true){
				System.out.println("Listframerahmen Checkpoint 1 erreicht !");
			}
			if (bListSize != 0) {
				LF1 = new ListFrame(bvList, true, this, bListSize);
				LF1.pack();
				LF1.setSize(538,320);
				LF1.setLocation(700,50);
				LF1.setTitle("BVAR-Liste");
				LF1.show();
			}
			if (debug==true){
				System.out.println("Listframerahmen Checkpoint 2 erreicht !");
			}
			
			if (eListSize !=0) {
				LF2 = new ListFrame(seList, false, this, eListSize);
				LF2.pack();
				LF2.setSize(538,320);
				LF2.setTitle("Event-Liste");
				LF2.setLocation(700,400);
				LF2.show();
			}

				
			if (debug==true){
				System.out.println("Listframerahmen Checkpoint 3 erreicht !");
			}

			if (debug==true){
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
			System.out.println("FERTIG !");
		}
		
		//} catch (Throwable exception) {
		//	System.err.println("Exception occurred in main() of java.lang.Object");
		//	exception.printStackTrace(System.out);
		//}
	} else {
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
 * Comment
 */
public void button1_MouseClicked() {
	if (LF1 instanceof ListFrame) {
		this.LF1.dispose();
		System.out.println("LF1 vernichtet !");
	}
	if (LF2 instanceof ListFrame) {
		this.LF2.dispose();
		System.out.println("LF2 vernichtet !");
	}
	this.getFrame1().dispose();
	System.out.println("MainWindow vernichtet !");
	return;
}
/**
 * ActionEvent zum Button 2 (makeStep) auf dem UI.
 *
 * Benutzt globale Objekte	: ----
 * Wird aufgerufen von		:
 * Ruft auf					: makeStep()
 */
public void button2_ActionEvents() {
	makeStep();
	return;
}
/**
 * Comment
 */
public void button2_MouseClicked() {
	this.makeStep();
	return;
}
/**
 * ActionEvent zum Button 3 (makeNStep) in dem UI
 *
 * Benutzt globale Objekte	: ----
 * Wird aufgerufen von		: 
 * Ruft auf					: makeNStep(int)
 * @param
 * @return void
 * @version V1 vom 14.01.1999
 */
public void button3_ActionEvents() {
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
public void button3_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	String tempString;
	int i;
	tempString = getTextField1().getText();
	i = Integer.parseInt(((getTextField1()).getText()));
	makeNStep(i);
	return;
}
/**
 * ActionEvent zum Button 4 (Reset) in der UI<p>
 *
 * Benutzt globale Objekte	: ----<p>
 * Wird aufgerufen von		:<p>
 * Ruft auf					: resetSimu()<p>
 *
 * @param
 * @return void
 * @version V1 vom 14.01.1999
 */
public void button4_ActionEvents() {
	int auswahl;

	auswahl = gui.YesNoCancelDialog("Warnung !", "Soll die Simulation wirklich zurueckgesetzt werden ?");

	switch (auswahl) {
		case 1:						// YES-Button ausgewaehlt
			resetSimu();
			break;
		case 2:						// NO-Button ausgewaehlt
		case 3:						// CANCEL-Button ausgewaehlt
		default:					// Alle anderen Faelle
	return;
	}
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
 * "true" zurck ( sonst "false" ). NPExep sollten nicht vorkommen duerfen.
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
	boolean result = true; 		// Setze Default
	Path argcopy1, argcopy2; 	// "Kopien" der Argumente anlegen, weil sie
								// ver„ndert werden.
	argcopy1 = arg1;
	argcopy2 = arg2;
	
	if ((argcopy1 != null) && (argcopy2 != null))	// 1.) Wenn denn beide ungleich "null" sind ...
	{
		while (argcopy1.tail != null)		// Standartabbauweise		
			{
				
			if (((String) (argcopy1.head)).equals((String) (argcopy2.head)))
			{
				argcopy1 = argcopy1.tail; 	// Bisher alles gleich, weiter mit tail.
				argcopy2 = argcopy2.tail;
				if (argcopy2.tail == null)
				{ 							// argcopy2 war einfach kuerzer => Schleife beenden
					result = false;			// und Ergebnis merken.
					argcopy1.tail = null;
				}
			}
			else
			{
				argcopy1.tail = null; 		// Ungleiche Stelle gefunden => Schleife beenden
				result = false; 			// und Ergebnis merken.
			}
		}
		if (((String) (argcopy1.head)).equals((String) (argcopy2.head)))	// Rest der Standartabbauweise
		{		// nichts machen, da sonst bisheriges Ergebnis eventuell vernichtet wird.
		}
		else
		{
			result = false;		// Ungleiche Stelle gefunden 
		}

		
		if (argcopy2.tail != null)
		{ 
			result = false;		// argcopy2 war einfach nur laenger.
		}
	}

												// Bei den folgenden Vergleichen muss natuerlich
												// mit den Originalen verglichen werden, da die Kopien
												// eventuell schon ver„ndert sind.
	
	if ((arg1 == null) && (arg2 != null)){		// 2.) Wenn nur einer ungleich "null" ist :
		result = false;
	}

	if ((arg1 != null) && (arg2 == null)){		// 3.) Wenn nur einer ungleich "null" ist:
		result = false;
	}

	if ((arg1 == null) && (arg2 == null)){		// 4.) Wenn beide gleich "null" sind :
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
 * @version V1 vom 14.01.1999
 */
private void copyEnteredToIn() {
	int i,j;

	i = EnteredList.size();
	i--;

	for (j=i; j >= 0; j--) {
		InList.addElement(EnteredList.elementAt(j));
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

 *  @version  $Id: Simu.java,v 1.17 1999-01-24 23:27:19 swtech26 Exp $

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
 * Fuehrt fuer alle Elemente in der toDoTransList folgende Aktionen aus
 * 		- toDoTransList.transition.tteSource von InList in ExitedList verschieben (mit removePathInListLinear())
 *		- inMethod(toDoTransList.transition.tteTarget) aufrufen
 *		- make_action(toDoTransList.transition.tteLabel.action) aufrufen
 *		- editor::highlightObject() fuer Connectoren (*) und Transitionsliste (**) aufrufen
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
 * @version V1 vom 14.01.1999
 */
 
private void executeTransList() {
	int i, j;
	Object temp1;
	TransTab tt1 = null;

	TransTabEntry tte1 = null;
	Path source, target;
	TLabel executeLabel;
	Action executeAction;

	TrList tempTL = null;

	ConnectorList tempCL = null;
	
	i = toDoTransList.size();
	i--;
	
	for (j = i; j >= 0; j--) {						// Schleife durchlaeuft die toDoTransList von hinten nach vorne
		temp1 = toDoTransList.elementAt(j);			
		tt1 = (TransTab) temp1;
		tte1 = tt1.transition;
		target = tte1.tteTarget;					// Targetpath der 'abstrakten' Transition
		source = tte1.tteSource;					// Sourcepath dazu
		
		removePathInListLinear(source);
		inMethod(target, null);
		
		executeLabel = tte1.tteLabel;
		executeAction = executeLabel.action;		// Action der 'abstrakten' Transition
		make_action(executeAction);
		
		tempTL = tt1.tListe;						// Transitionsliste der 'abstrakten' Transition
		while (tempTL.head != null) {				// (**)
			// highlightObject(tempTL.head);
			tempTL = tempTL.tail;
		}
		tempCL = tt1.conn;							// Connectorenliste der 'abstrakten' Transition
		while (tempCL.head != null) {				// (*)
			// hightlightObject(tempCL.head);
			tempCL = tempCL.tail;
		}	
	}
	toDoTransList.removeAllElements();				// Alles abgearbeitet, Liste kann geloescht werden
	
}
/**
 * New method, created by HH on INTREPID
 * @param absyn.State
 * @param absyn.Path
 * @return absyn.State
 *
 * Bestimmt zum angegebenen Pfad rekursiv den passenden State in der Statechart
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: find_state(), inMethod()
 * Ruft auf					: find_state(), guiOutput()
 *
 * @param x absyn.State
 * @param y absyn.Path
 * @return z absyn.State (z. Zt. nicht unterstuetzt)
 * @version V1 vom 14.01.1999
 */
private State find_state(State x, Path y) {
	State z = null;
	And_State z1 = null;
	Or_State z2 = null;

	z = x;
	
	/*try {
		z = (State)x.clone();
	}
	catch (CloneNotSupportedException cnse) {
		guiOutput("CLONE-Fehler in find_state()");
	}*/
	
	if (z instanceof And_State) {
		z1 = (And_State)z;
		// Die SubstateListe durchgehen, bis der passende Name gefunden wird.
		while (z1.substates.tail != null) {
			if (z1.substates.head.name.name.equals(y.head)) {
				z1.substates.tail = null;
			}
			else {
				z1.substates = z1.substates.tail;
			}
		}
		
		// Sicherheitsabfrage
		if (!(z1.substates.head.name.equals(y.head))) {
			guiOutput("Allgemeiner Suchfehler in find_State");
		}
		
		// Wenn der Pfad nicht zuende ist, wird er rekursiv weiter abgebaut.
		y = y.tail;
		if (y == null) {
			return (z1.substates.head);
		}
		else {
			return find_state(z1.substates.head, y);
		}	
	}
	
	// Kommentare: siehe oben
	if (z instanceof Or_State) {
		z2 = (Or_State)z;
		while (z2.substates.tail != null) {
			if (z2.substates.head.name.name.equals(y.head)) {
				z2.substates.tail = null;
			}
			else {
				z2.substates = z2.substates.tail;
			}	
		}
		if (!(z2.substates.head.name.equals(y.head))) {
			guiOutput("Allgemeiner Suchfehler in find_State()");
		}
		y = y.tail;
		if (y == null) {
			return (z2.substates.head);
		}
		else {
			return find_state(z2.substates.head, y);
		}	
	}
	
	// Defaultrueckgabe, wegen Javacompiler
	guiOutput("Rueckgabefehler in find_State()");
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
 * @version V4.00 vom 23.01.1999
 */
private void firstStep() {
	String rootName;
	State rootState;
	Path  rootPath;
	int i;					//debug-Variable
	
	rootName = SDaten.state.name.name;
	rootState = SDaten.state;
	rootPath = new Path(rootName,null);
	
	if (debug==true){
		System.out.println("firstStep erfolgreich aufgerufen !");
	}
		
	InList.removeAllElements();					// Alle bisherigen Eintraege aus der InList loeschen,
												// da es sich um einen Neustart handelt.
	i = InList.size();
	if (debug == true){
		System.out.println("firstStep : InList zurueckgesetzt auf : "+i);
	}
	initInList(rootState, rootPath);			// Initialisiert die InListe (InList)
	
	if (debug == true){
		System.out.println("firstStep : InList durch initInList gesetzt auf : "+i);
	}
	
	bvList = makeTab(SDaten.bvars);				// Initialisiert die BVarListe (bvList)
	seList = makeTab(SDaten.events);			// Initialisiert die EventListe (evList)
												// bvList und seList sind eventuell leere Vektoren.
	i = bvList.size();
	if (debug == true){
		System.out.println("firstStep : bvList gesetzt auf : "+i);
	}
	
	i = seList.size();
	if (debug == true){
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
 * Ruft auf					: get_guard(), guiOutput()
 *
 * @param waechter absyn.Guard 
 * @return goal boolean
 * @version V1 vom 14.01.1999
 *
 */
private boolean get_guard(Guard waechter) {
	boolean goal = false;				// Rueckgabewert, default: false
	boolean temp1, temp2;				// Temporaere Variablen zum Speichern der Zwischenergebnisse

	
	// Suche Bvar in BvarListe und liefere den Wahrheitswert zurueck	
	if (waechter instanceof GuardBVar) {
		BvarTab newElement = new BvarTab(((GuardBVar)waechter).bvar, false);
		if (bvList.contains(newElement)){ goal = false;}
		else { goal = true;}
	}

	// Zerlege Compguard in seine einzelnen Guards, rufe get_guard mit den einzelnen Guards auf
	// liefere den Wahrheitswert entsprechend des gewuenschten Auswertungsoperators zurueck
	if (waechter instanceof GuardCompg) {
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
	}
	
	//	Ueberpruefe, ob die durch Paths angegebenen States in der entsprechenden Liste 
	// 	vorhanden sind 
	if (waechter instanceof GuardCompp) {
		switch(((GuardCompp)waechter).cpath.pathop) {
			case 0:
				goal = (InList.contains(((GuardCompp)waechter).cpath.path));
				break;
			case 1:
				goal = (EnteredList.contains(((GuardCompp)waechter).cpath.path));
				break;
			case 2:
				goal = (ExitedList.contains(((GuardCompp)waechter).cpath.path));
				break;
		}
	}

	// Leerer Guard ist defaultmaessig wahr 
	if (waechter instanceof GuardEmpty) {
		goal = true;
	}

	// Suche SEvent in SEventListe und liefere Wahrheitswert zurueck
	if (waechter instanceof GuardEvent) {
		SEventTab newElement = new SEventTab(((GuardEvent)waechter).event, false);
		if (seList.contains(newElement)){ goal = false;}
		else { goal = true;}	
	}

	// Negiere den durch get_guard bestimmten Wahrheitswert und liefere diesen zurueck
	if (waechter instanceof GuardNeg) {
		goal = !(get_guard(((GuardNeg)waechter).guard));
	}

	// Dieser Fall darf nicht vorkommen => Abbruch der Simulator-Instanz mit Fehlermeldung (wuenschenswert :-) )
	if (waechter instanceof GuardUndet) {
		guiOutput("Simulator: FEHLER !!! Ein Guard ist eine Instanz von GuardUndet !");
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
	D0CB838494G88G88G9BF9B8A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E13CBB8DD415D5364EC8E946ABACACD48CB41CA8FF422244441112F951DB4E9A579B67BD1BF8B33C495550AC27D5EB18B7CE4F1A430FD7ACD294C492A82ED9C3AFA290FA3908A694ADFC6148D3C6F90A49655E8F38F07F3A773BF079915EBE67FC7B5C6FFEFC9745F2DCEB7B5D3377B9FB6F334FBE7B6C333F8FD278F674764C2CC2C2B23793655FA7190444B491927A2B4B9108EB1EEEA46AFF343507FCD3
	11834FF620957E2311641341FD395036C1BB74C7C6329FFE971051FD054760C790B9DB0E92F262593B6E19DC4E7786D04E8E560E3E10874FDDGAE40058324CE277293AE94AB7884E86FD2FED3484CA224B70476F9EE3CD805AF8F617D9A60598A10417A7C740236CF9CC0B2C0AA40CE26599BFB0FA8748DB5F71176D7CB8CEFD0B92AF1888DB7F1991D4A13101736D3A3E413A517F6D3FAEBFF4FDFBCD63B373E7EAC7CCFB65CC4F548A3392C8FA191506E381962C2881DF7A17C2E84B8FEB36A72644186C5D668
	5CC50494178655A57AE62EC3724DFEDDFC952919DC669FAF204C1C99A8F313094AEC00F69B00CD602BC6A9BE845A11G71B3D017675EC3DD921C0BC847DB41F5D9BF036B70628C3FAE6B0BD2B35FE4EB7C060F2EC165CCD897G8B40CC00D80094009266DBA9837F854F23DDF21353EEF7BA0E3ADC47EC9677A927C532416F7AFA20A838B3125969369045FF7610CA9DFD9052E69AA760785C4E93DC7363E111A4DC2EB5EC5719DB612EF78C998A0F5CDB7CF3EB39055AED9BDB438A78CD47D15BF07C6B19949F26
	42074E2278D92CBFED47832DFC965AB9E484F5E99B19CD524ED69AF66869322C4630E19657A1FD16DF97EE63DF8FD19B975EEAA455G4DG1DG53G22EFC59B6F9D2C3D1A0DCFBAE4496DB0594EFA3BDCA637FC4CEB35D9A437E26BD7AF8433F5622D936D3CCB5823250B4EFB432D5C9E5B857EBC43EFD661391DFE417C143637822DA3542874DF447CB4A914775FA978BC487029AABC67D34770343D95E89D7FE0A45CD627C6282D12201D8A108110F79B6CC300665B50D61786BF3AC6FFECD559090FAD729541
	D37249EE53C907C7B6B94C52B9135BEA6A32C99E2274F95BC7ED5A95B06E905BA7E610712564105CA6596AF4GF713DD02C57170BE9F7A7650BEEE09D15B2CF6898458DD8479FE6CAB00274D64117F663218E4495F0353BF73D1FB8C184E81C7E0G3FA3EE23362AE0B12A97FE075C06F612C7A9AE955A9942AE893E3D707C8A1CC43255579F371A299AA677906E472CCCBA075C5B615CG48G6888B312B4000659686FAF34233FCFAB1DCF92679C52757778557B8CB13339DFA54E767B3B326F191C4D004F3943
	C88E819C8730G045C01E7CB98695471C9540D6AC45BD4BF2AE7309822878D337D7D392CA53A323295BFFE1D78E34F0EA9482170066AB737DFEC2DBAF6F5E63C27EB3F25E75FB1445C21586F8EED3CF8F30C6D9940EF85A83E935777F73EF26E7747CC9E89BD7D349B1CD43278D71B77FDF1081EF9520064100FCBAE49E1019F9E054F9F06F67A77C037D37867AB8CCFE3CB93B829C760330777F0BC70D06FA8243F4E68A6D9F6DB3B3C32F4D612E52B2347BF3EE0186A628988DB9E453777DD24FEDC8DF3EC3E93
	FD63CC974BEF20BDCD60AEB1DCD8C03F4D2676A06BC247B29F3EF3EA3E2386AE631520B2321567766B646F1791BB1A2F72479D0A5DB86D91C60B07F924826404C3AC8C4779570F362BF4413157A237641AF5BBFB5DB2F6DE239E85645CC8F0DF3DB4226F2B6691FDDF350ECC6E0B5F0C6879A22779C6E87CB4BB9D8E09EDA6C1A6BC0FFC210D6AECF5D86573D6CB0FA41F358ECB040C06D3BF7E1C4568B6785D9B0E31F4709D0C8595A76EA6553FAD524DE3721A6B8CC9615CCFB7067BE38157E9081D1312CD3203
	D11A1CDE074C7163AC7EABF8CFB6433DE87642333C6218D7161D0E3ACA3B536B112A587FCDB62B39DF32D4741986CCD526C1390AD93B6A94251D20BF5FAF7F70433F14534438320B8F3FFCCABD30A56848CF5FBF77FE0D629F85645D7E8BAA9D8EFFBB9D8E876AG028E239C6389351C5AEFA7277672D136600C6A21F2EAD14E74AC6FF7A9E78AB62D95B6ED6FBE2D5221265244142D52F22F198A77BA05F704FCB10077371ADF4D04752A29D57215B363540FB3403FF367C0FEBD874F347F9C25F44CD914432C49
	E93371AD883408B9F4FFFD19D9285AB3C766707BE888BCB9BDC5171EA668A733746899027ECF3A745C3B107ED5369E3DD450576B5269EFBE7FD6F63F28067E4DG6DF7617C6B2E9428665F62F476FBDDEDB49B45DCF40C46FF0F740DD702D415923E120614FC733D313D9A5AB9134D6B4FE32B46E8EEE3D37164F1AC4EC471AE37558E6982BD5015BBFB65C575FD5C86BA527BFDA8BC2381383FFC7F34811EDF5044378663623AB6DAB720FE40795C5E2547A7811E6B293D6E26FD1E9FADD5681B81378D2074EE5E
	2F7DEEEEB75EEF6995518F5A5BGCAA314FE916AFE4EB16CB78A787088FF3F7800FE6F0CE33F8C40674D0573F4AE6F57BB17778B1DCB7B1D93F5010F844E4EF0BF85832C4C41FC6FD43E3ABEC1E6D46B46F8C6D778EF1E2B4D7726E7D23EB9730C24F09E7A457DE3EA3FB86A71B84DD6F62DD0686D636A782F1C15632F41335B6A7640DD51A27944D9B9CEF525778E852D2C5B128FBEA3936FD1A7ACD65969467432E910ED75B6E793DCFD1A41151A4471F84EAA8D52F9311A46D8A65A2BE29E1EFF3F3A202E1748
	D9250657035826F31E122F4D735BA62C886BA5FF923C13004EF153329037D560B23391D7BD9FF15F97385E79E85BF3C47FCC6DA0540F7B457E6AA454578714BD45ED362A006EF3D6CB4238D8DF4F368317E7E179AC6D9BBD9FEBB81FFCCD65ED02760BG3911285B13AC272579D4F9A47615A45A379D5AAE0019G7C3E3C778A5E3B238137A99263CE0ECF9DF7729798C99DC07B825C2FC3D7BE046714223A700FF355F05C1229278338B7F63C562868B50DC412241A7FB66C56D97F3069E5865B823E6E618B7C6B
	DFF1BEB53381004F7FE7E27EE95027789E1C7FAF2DFA73EF3D87675FB0F67D73A7057B457CA724A24A3AC40C21DE961099A974AA3769693591681C37C3E55B292485D8BFE999C739694033BC8A65D68F6B7AC3945A6361716B37C7144A9E930EFA2C5D9DD64743743E370346C3DB14326723347130076D59C84047C5611E7DB959230A07E7A082199CBDF022E10C1B19194D772E528EE585F036F7895EE526E7CED60F54C2F29456DAE2F228CDB7C1BB83A0AF5AC88E819C0FC65D8A322EDA3BBB611B24F61713B5
	953DAC51F87F3BE9CA63FCE21CA1FBAA63C2453899D39A97AD464D3F289D979F6D3F1727C4CF3C23735A619296B7D323F96DB0DB609FE178ADAABC5F931FB1BCEDEF83DA67C2143F3ECBABBFF921DF662605017237AC441A6653AC9613C5DA794FB2FC7EA22D7CFE89651783ADE4914AAF3E22151F3EA8D0E64E3D01EDBE6E3D89635E5234ABB46378BE7D677AFDE442994593FC026FE78EF42BF334C660C3EBAB1D70146F457369D39F1ECF217762DEF8F9BB4D1F12211D86309520B046C80EG348178E2F0CFF4
	E569659601FBA2D8E2612FAFF1BBBCF31D76FBFE4238AACDBBA2A6F09C1FF7D19F4EBBAE8657701E316B53E16904F15AB5CC5755613C8DF5A00BC5DDEC7C7AF4681CB0CEEB07A30B75F4689946DC26F5B12EFF7D8E0A1B896D08453856EB33E89D94E3F32B536A108946E5DBB64D211CEEEB0F55A1EAB08CE7F6C2FFB7563867319CAF96F8A68B3ECBF30257059E4E512F8B2D4A512F8B2D4D193C0679E4CE303A91572FA48B755BC4674EF0118237C5604A3291C7FE0038FB842ED0609EE2F9E285349B84EEFE87
	45F5C0DB7E812E790A2FE9CE9AA15A2BD9BBCE341B3E8ED61F1CAAD099DA9C1751711D494856134B667EAC4B05B7028E69429E6B4DE80F5C7B9077DB8B45D5C23B71BE3441AFA5EA139651FE1E35E95E435B5FC85F157ED4063E0DE6FDE7B274ECD44670CF956DE6BE6C71C89E96BC0F1BE413C27B35CCE37E045A65DF61E2C2300EF47A4ACE254F895959C473CFFEDEBCDD21F327E9B541EE66F160CCB746814BFDF8CFD950C34F3AC8E847832C9736EF74CD16A3EC06FEDB959B664572A75FFFB90660FB7DF503
	7EDE5F435EF9FB1CDE37D9526E67E2C3312079CF161332E427A661FD0CEC3CECF277C89362C1859B4FE94146579802458CEE6F3DE3EAFB578ADFA9691B4CC665E017BA45B64D4A13737BD9G3FBA412F43B6993F8E6061D2780484703BE5DC4D2FC670338DCF46AFA2563F8716862C6167BBE8BE64936DA6569E92FEA69D5DA5ECFA42E1D18E8ADEDBD94162FF818C7818A06EB00B2F3F4FA2640440BD4CF0E658603582D747626BAA407D2F40BD42F0B3GF7BA9B677B54C5FA6E58CCC3CEAFBD206E277D52AE2A
	6FCBC94AFC52E3711E36E21056AC3A61028FCBFB56A519C9AA9B77D08766CF4D506EG98850884C83C9F6FDAF71E27FB843CC7264ECCB7944F3D36FC2D4D3D68BB2DC38FC09E75G0EFD39EB222EFB4CFA3A3642982E5333E6645B8B38D100C800C4CA97FCF3A4BAAF59AA333B5336876817BFCE3A75026A4CFD62FC3756A79A17787D20EDC9309C7E8EAB65573B04674A3B85FE9143072970BC073E1745955ACEGDA5DD20C7B67FDF89644891CEF081D9950CE9738B58C378D5A794B90F7388F47168A5C59FE9C
	5BA8F08B7AF1EC1B40F9ECB836D360426DB8B6C4601E36635888011BBE0CE3E384AEE60462D2201D3A8CFD6C39AA3ADF34793A135642DB388370187B72C5EA1BF498C7169BC971F22C335D6245BADB4327D1E4CBC719EE1DED698335066A650A4FAC775F3953C41DED934BBF6838D020A7AF27B843A5354FD19F791C707C11536F8A2053F878CC361A1E92C0277A5E94B07E65GBA3547178A1D4F6DB7C5E2EE2FACA4153303FF7F1577A01FD335725C7C45326D1D51B837C317718C6BFD904FB0E7A735E7B43493
	GB6887C87E6E6E7E813B830F39C5E07F6DA50BE55F1E81FCFACDA7B7CFB26FAFED60B56BE77864CFF3EC5EB1F0FF354749FDB3476F9A640BE7BAF286BAD0D6B8E9AB2035827B70E5BC50E732FBD77C9C7B766DE31F16827E7FA50F7D36270EC0F30523BDF8634F3D740DE822883E88130G78D6E03FD8560F566AE99DBE9470CB81562F60F5FEDAF3677D6E6FF1A97C68F82A13EF05BF0E142E1898D334102B74293B063EADCA9FAEBB0549266B055FF06CD6753D16F75C785E7FDA5EF363B7A6BCCEBBFC982737
	00CE64A1A3A987E88468FC8863F4DFBF55C1F25057B696927E909D37F0887DEF1A68975FCF7530193AA49B41F8BE173A19A26BE49F4A5A84E336G64BDECA415GADG1C6F777A112F8DF01C47263DFE9E33EC48E39A9D83908F3091A0C370D8E3C79E057148E3FF211F473CE164D1817486008E00D10048F81C4B6A1109F39921E92DF2E6B8BA683ECC0C67E7C626408FB2FC0A8A4F4F924270343D91E8C79E41BB6C25A22AD8A43493GB6G248364BE0A774EC7F7D7132B54AE5A1C2E96C8D6ED2A2BA4772BFF
	6B18CA0D8C7D071083C60CF33D3D73C8E859DBC15F159CFA147B71F165D9DA3CECBBFD72397DE6BF4BDB019686908F309EA08DE02B185F2B3BAB797C682B1E2332ECB2773237C8B8B97AE2470F26C98F1F5B7B1735F34BDB695FF765AB0365GF359B703F5AB356B3608619BD7EA574D5B096B5682343815D80F0BE2F139B0C14BA716614B9334FCEA4C482792E8E18998F7F6D9263AC64A373274215870F87E3B70BCE76A53FDEB76BFF00C1F6E6A03CBC31B29078EE2FB606EEEF5AC4FC860762AD8857946AAF6
	D67414CF22C363AAF56EFE7BEBF4AE4AD7968AEE5A1F7D38DA857755EB741DE09BDC1E3E30CAB6CB8D0E7F57B26AF7CD3D12393F4B6973FF3F3126C40DBFAC702B4F7BF9633783E9FB7C389A657DC30BC3BD4748D57C7DC352AAFCBFB1432526EFD468DB857D47DEBDFA5EE3F886077BB04E14BF06E77093BE6D99CC3F17701F2199BE6D997CC1G7D3DA16D999CF8C6378FE94FE023F246F3BBA48CD1DC37496611886E67189E75393C692903C15F0334BF46575526BCFB8B16ED67F3F57661DCE344DC9774E967
	FABEC0571F74E9677AFEGBD33DFBB574581745AFE6DDC51D64A77A27D6A39F2BD63EC28E72A507397B62D1EFF8B1013EF576AB9A700FE4C2E55F3E1GDD36EB757CA8C04F0D7609FAA68D0BB3EAB56A794230D64F0F8264140FE8757CB800FEE6C42B67FD8179DF4808D64F02ACCE6731C86EA062779555EFC347F7D5CF5283AE636D9BAA0347C929A8F8DD47D624DF57F1976957F0FDC513FF5BB72DC86F5BBE9E3BCD8768BA34403A77820482C483442F46B3B1FF7FC1424EC41D73700425075610685762B24A
	7A5D7E6073FAF97F553FB14FAE26F1394B893153CE79A3FEE7B1DD9B5969E2C80E5BD5CC6D613676744A3C2B02AFACE65F49CA5D8C4D7376464BD8D75E309AF379C31D184B3F389A4F40A8135F57E2D53FC7BAEF3C5F057F9DE4ACB67B6734DC757B93730D175DFAC3E5701AC14FC11AFF301A0159E92F32F6592B3C8376AAD317DCA5C1AAD2DF7FD72F4D560265A3FE2FDD51CD732152C4B8D381DA92B16F3E3907561A1AA47A77A5F8FE5DFA8873510C0F67110D4F73DA275E79E5C96467D6D8227FDE695773CF
	7B0A49045A06C8C7F8DC1E6E40F7DD4909D893F969237D9347E1DA2244739F3A68B82A6F36C44C8FF28610D75D9A7475391356247500470CF5A5ECFF811F19EBB0CE5C7EA6BD8FF573BEC0363A1DAE49AD8FF1AC7B9A8F637C24B55F603E744824355D6063FEB4E98DD78F146F278F840FEB2E83D70FEBC38758F7E9900F5A06DCCA1DFC6CGFB87585D6D11C47C1CFEA0DF60CEFADA1D9EAB7B380D6549564BFAF9F26C9A4C13BF6DAC1FE49EC9EBF8FFFEB7F8175593B72E515E992A98BEED0D764E30329B6F8C
	DB00567AC33CB37EC7A915998F6D8DG99GF9EB615E8EF0842085403296FDE4DFC945A4BE428BFB6CBCF11B2C8E192F57CC075EFACDDB2BFC97854F5835FA6FF65739A6DB67C49833C19937991EDB57621E58B9B059383CA418DB12D2DBC9D25778D3CA68EFF186F397D7E8BFA9216BC63F0F371AEC56E1763962D96868686171E77AE5EA5BE660578960CB4278535E290EBFDC3F8F5835097F2ED37DDE53FD6363773F5CD0995CD619E54177D9EE595577591EB27D7CE1FF59447C2134CCBFFFB8D8264E9FB86E
	4F8ADF37F8F9287CEDDB992D1371779127CC8ED38FFBD746730BA8963F43E0BD2313B06748F1E24E31D4602ADDD8B75FA0F0BFF7E13F4D8237DE46FE390FA3EE0F976B6B258267E6388AE8B788DC79G72EB953816C164A78B5C6CC164B773F12CAF7C0179E778635AD8C1F81D78F1ED2C3865B2460A25C04BFF827D79D187FD7FAC4382729A56E374138F258ED66244BA188C7DE782448224G24BF0135347FF361BDA5F79D3E5BF9490563CA81D78D508A608288DB0763FEAF633838F5D8037B635BD436723E4F
	CB4FD6F0A8197AG9F936D456FE313052CCF86D056C640258364A69BC985C0F3B24A728E222C5EE414F544685F272FDC767FB6F47AFF5FFE79466F5F589BAA036799C71DB87F51E43CCBBA1D5A3BA45E957FDF90F9457768FDC9543EF75CFB743E24B10A7C048F68FD4925A863909FBEE076F927FF043953846E4E239F7177F93FFF4498C3C06953EAFEC4F7E7BB0AFC74C7F4F76EECC0F6E74FD7E4F7C2444C3F02734C272F50F554F73F415C1D83F7B777B77C6E367D06B14E03622E7CEF686E1E08A23F66BB06
	3B1B105D4D739D595D1034F66CBDF96D33B99F49537788EF3D0D8DC34E0718389CB48F2F135B507A25E17A7517EF5E3CFE7F965849DD7072ADF8834F1B0740964A170656CBCF016D31AF8D9DE79F3EFEFA794B538FDF9ABAC14CF390F6576547AF5E3CFC7A79B33032EAE85C7825E152651F6F9E3F78747E5D53CF8B8EBD85D675CF9FFDE918FC70B306E8832CDF413194F9DC6C4A25393847455C2063E2EE686B03DF2C842F27820A3B384177BE2FG2DEB46B59EFB796D43B7EF3EBCFC737A603BA7203159C3BF
	3FBCC50C9B3CDD89B27FB3CC8DD492A277E5A5485D2F1074G86GD0CB8788DA65FE981996GG4CC3GGD0CB818294G94G88G88G9BF9B8A6DA65FE981996GG4CC3GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG5396GGGG
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
			ivjButton2.setEnabled(false);
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
 */
private boolean greaterAndContains(Path arg1, Path arg2) {
	boolean result = true;					// Setze Default
	Path argcopy1, argcopy2;				// Kopien der Argumente anlegen, weil sie
											// ver„ndert werden.
	argcopy1 = arg1;
	argcopy2 = arg2;

	while (argcopy1 != null) {
		if (((String)(argcopy1.head)).equals((String)(argcopy2.head))) {
			argcopy1 = argcopy1.tail;		// Bisher alles gleich, weiter mit tail.
			argcopy2 = argcopy2.tail;
			}
		else {
			argcopy1 = null;				// Ungleiche Stelle gefunden => Schleife beenden
			result = false;					// und Ergebnis merken.
		}
	}
	
	if (argcopy2 == null) {					// Dann koennte arg1 = arg2 sein.
		result = false;
	}
	
	return result;
}
/**
 *
 * Liefert die Anzahl der von "null" ungleichen Elemente der uebergebenen BvarList zurck.
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

	if (debug==true){
		System.out.println("groesse vermeldet eine Groesse von : "+result+" fuer die BvarList : bvars");
	}
	return result;
}
/**
 *
 * Liefert die Anzahl der von "null" ungleichen Elemente der uebergebenen BvarList zurck.
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

	if (debug==true){
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
 * Der Code ist zudem gegen jede m÷glich Form von NullPoiterExeptions gefeit.
 *
 * Benutzt globale Objekte			: ------
 * Aufgerufen von					: makeTrans()
 * Ruft auf							: ------
 * 
 * @params
 * @returns
 * @version V4.00 vom 24.01.1999
 * @return absyn.Path
 * @param arg1 absyn.Path
 */
private Path handClonePath(Path arg1)
{
	Path copy;
	Path back = null;
	copy = arg1;
	if (copy != null) 				// 1. Es soll sich nicht um einen null-Path handeln.
	{
		if (copy.head != null)			// 2. Es soll sich nicht um einen (null,null)-Path handeln.
		{
			back = new Path(copy.head, null);
			copy = copy.tail;
			while (copy.tail != null)		// Standartabbauweise
			{
				back.append(copy.head);
				copy = copy.tail;
			}
			back.append(copy.head);			// Rest der Standartabbauweise
		}
		else
		{
			back = new Path(null, null);	// Rest von 2.
		}
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
 * Beim Grundaufruf aus firstStep() heraus werden demzufolge der Name des „u˜ersten
 * States und dieser „u˜erste State selbst als Parameter an initInList() bergeben.
 * 
 * Benutzt globale Objekte			: InList
 * Aufgerufen von					: firstStep(), initInList()
 * Ruft auf							: initInList(), killLastElement()
 *
 * @param x absyn.State
 * @param y absyn.Path
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V4.00 vom 23.01.1999
 */
private void initInList(State x, Path y) {
	Path y_help = null;
	StateList liste;
	String compareName;
	StatenameList nameList;
	State targetState = null;
	boolean found = false;
	
	if (debug==true){
		System.out.println("initInList() aufgerufen !");
	}

	if (x instanceof Basic_State) {		// Wenn es sich um einen Basic_State handelt, wird
		InList.addElement(y);			// der Pfad von x in die InListe eingetragen. Rekursion
	}									// ist dann nicht notwendig.
	
	if (x instanceof And_State) {				// Handelt es sich um einen And_State, muss zuerst der
		InList.addElement(y);					// And_State selbst und dann alle Substates aus der
		liste = ((And_State)x).substates;		// Substateliste in die InList eingefgt werden.
		while (liste.tail != null) {            
			y = y.append(liste.head.name.name);
			InList.addElement(y);
			initInList(liste.head, y);			// Ausserdem muss fr diese (Substates) ein rekursiver 
			y_help = killLastElement(y);		// Aufruf von initInList erfolgen.
			y = y_help;
			liste = liste.tail;
		}
		
		y = y.append(liste.head.name.name);		// Weil in der Schleife nur auf (list.tail ==null) geprueft
		InList.addElement(y);					// werden kann, muss das letzte Elt. separat extrahiert werden.
		initInList(liste.head, y);				 
		y_help = killLastElement(y);		
		y = y_help;
		
	}
	
	if (x instanceof Or_State) {				// Handelt es sich um einen Or_State, muss zuerst der
		InList.addElement(y);					// Or_State selbst und dann der dazugeh÷rige Default-State
		nameList = ((Or_State)x).defaults;		// in die InListe eingefgt werden.
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
		y = y.append(compareName);
		InList.addElement(y);					// Hier wird der Pfad des Default_States eingefgt.
		initInList(targetState, y);				// Fr den Default_State muss ebenso ein rekursiver 
		y_help = killLastElement(y);			// Aufruf von InitInList erfolgen.
		y = y_help;
	} 
}
/**
 * New method, created by HH on INTREPID
 * @param pfad absyn.Path
 *
 * Nimmt die Eintraege in die EnteredListe rekursiv vor.
 *
 * Benutzte globale Objekte : EnteredList
 * Aufgerufen von			: inMethod()
 * Ruft auf					: find_state(), inMethod(), 
 							  absyn.Path::append()
 *
 * @param pfad absyn.Path
 * @param state absyn.State
 * @return 
 * @version V1 vom 14.01.1999
 */
private void inMethod(Path pfad, State state) {
	Path tempPfad = null;
	State tempState = null;
	Statechart tempStatechart = null;

	tempPfad = pfad;
	tempStatechart = SDaten;
	
	/*try {
		tempPfad = (Path)pfad.clone();
		tempStatechart = (Statechart)statechart.clone();
	}
	catch (CloneNotSupportedException cnse) {
		guiOutput("CLONE-ERROR in inMethod()");
	}*/

	// Rekursion: Sollte ein State uebergeben worden sein, weise ihn zu, sonst
	// bestimme ihn	
	if (state == null) {
		tempState = find_state(tempStatechart.state, tempPfad);
	}
	else { tempState = state; }

	// Wenn der bestimmte State ein Basic_State ist, trage ihn in die Liste ein 
	if (tempState instanceof Basic_State) {
		EnteredList.addElement(pfad);
	}

	// Wenn der bestimmte State ein And_State ist, rufe inMethod() mit den Substates und
	// einem erweiterten Pfad (Clone) wieder auf.
	if (tempState instanceof And_State) {
		StateList temp1 = null;
		temp1 = ((And_State)tempState).substates;
		/*try {
			
			temp1 = (StateList)((And_State)tempState).substates.clone();
		}
		catch (CloneNotSupportedException cnse) {
			guiOutput("CLONE-ERROR in inMethod(), And_State");
		}*/
		
		while (temp1 != null) {
			Path pfadclone = pfad;
			
			/*try {
				pfadclone = (Path)pfad.clone();
			}
			catch (CloneNotSupportedException cnse) {
				guiOutput("CLONE-ERROR in inMethod(), And_State, pfadclone");
			}*/
			
			pfadclone = pfadclone.append(temp1.head.name.name);
			inMethod(pfadclone, temp1.head);
			temp1 = temp1.tail;		
		}
	}
	
	// Wenn der bestimmte State ein Or_State ist, rufe inMethod() mit dem erweiterten Pfad
	// und dem bestimmten State wieder auf. 
	if (tempState instanceof Or_State) {
		pfad = pfad.append(((Or_State)tempState).defaults.head.name);
		inMethod(pfad, tempState);
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
 * @version V4.00 vom 23.01.1999
 */
private Path killLastElement(Path arg1) {

	Path help = null;

	if (arg1.head != null) {					
		if (arg1.tail != null) {
			help.head = arg1.head;				// Mindestens ein Teilpfad-Element muss uebernommen werden,
			help.tail = null;					// weil zumindest der Rootstatename immer uebernommen werden muss.
			arg1 = arg1.tail;
			while (arg1.tail != null) {			// Sollten noch weitere Teilpfad-Elemente vorhanden sein,
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
		}										// darf eigenlich NIE passieren !
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
 * @version V1 vom 14.01.1999
 */
private void killLevelConflicts() {
	int i,j;
	Object t1;						// Temporaere Hilfsobjekte fuer die aeussere Schleife
	Object u1;						// Temporaere Hilfsobjekte fuer die innere Schleife
	Path p1 = null;					// Pfadreferenzen aeussere Schleife
	Path q1 = null;					// Pfadreferenzen innere Schleife
	boolean kill = false;
	
	i = toDoTransList.size();
	i--;							// Vektor beginnt bei 0
	while (i > 0) {
		t1 = toDoTransList.elementAt(i);
		p1 = ((TransTab)t1).transition.tteSource;
		for (j=((toDoTransList.size())-1); j == 0; j--) {
			if (i != j) {
				u1 = toDoTransList.elementAt(j);
				q1 = ((TransTab)u1).transition.tteSource;
				kill = greaterAndContains(p1, q1);
			}
			if (kill == true) {					// Enthaelt Objekt j das Objekt i vollstaendig
				removePathInList(q1);			// und ist groesser, wird Objekt j von der IN- in die
												// EXITED-Liste verschoben (mit removePathInList() )
				if (j < i) {					
					i--;						// ggf. Zaehler i anpassen.
				}
			}
				
		}
		i--;
	}
}
/**
 * New method, created by HH on INTREPID
 *
 * Methode beseitigt Nichtdeterminismus und baut in toDoTransList einen Vector auf, 
 * der frei von Nichtdeterminismus ist.
 *
 * Benutzte globale Objekte : transList, toDoTransList
 * Aufgerufen von			: makeStep()
 * Ruft auf					: selectNonDet()
 *
 * @param
 * @return 
 * @version V1 vom 14.01.1999
 */
private void killNonDet() {
	Vector NonDetWahl = null;
	TransTab temp1, temp3, temp5;
	Path temp2, temp4;
	int LastIndex;
	
	while (transList.size() > 0) {
		LastIndex = transList.size();
		--LastIndex;										// Weil Vector von 0 an gezaehlt wird.
		temp1 = (TransTab)transList.elementAt(LastIndex);
		temp2 = temp1.transition.tteSource;
		NonDetWahl.removeAllElements();							// Auswahl-Vector auf null setzen
		NonDetWahl.addElement(transList.elementAt(LastIndex));	// und erste Auswahl einfuegen
		transList.removeElementAt(LastIndex);					// dann aus altem Vector loeschen 
		--LastIndex;											// und Ende-Index um 1 erniedrigen
		
		for (int j=(LastIndex); j > -1; j--) {					// Vergleich mit den restlichen Transitionen
			temp3 = (TransTab)transList.elementAt(j);			// der Transliste durchfuehren
			temp4 = temp3.transition.tteSource;					// und ggf. speichern
			if (temp2 == temp4) {
				NonDetWahl.addElement(temp3);
				NonDetWahl.removeElementAt(j);
			}
		}
		if (NonDetWahl.size() > 1) {							// Wenn mehr als eine Auswahl vorliegt, muss mit
			temp5 = selectNonDet(NonDetWahl);					// selectNonDet() eine ausgewaehlt werden.
			toDoTransList.addElement(temp5);					// Die Auswahl wird dann eingefuegt.
		}
		else {													// Die einzige Auswahl wird eingefuegt.
			toDoTransList.addElement(temp1);
		}	
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
 * @version V1 vom 14.01.1999
 */
private boolean make_action(Action aktion) {
	
	// Wenn Aktion eine Instanz von ActionStmt ist, dann teste, ob
	// 1. eine Instanz von MTrue vorliegt: Dann ueberpruefe den Wahrheitswert in der BvarListe; wenn er nicht stimmt,
	// aendere die BvarListe.
	// 2. eine Instanz von MFalse vorliegt: Vorgehensweise wie in 1.
	// 3. siehe unten
	
	if (aktion instanceof ActionStmt) {
		if (((ActionStmt)aktion).stmt instanceof MTrue) {
			MTrue temp1 = (MTrue)(((ActionStmt)aktion).stmt);
			if (bvList.contains(new BvarTab((temp1.var), true))) {	
			}
			else {
				bvList.removeElement(new BvarTab((temp1.var), false));
				bvList.addElement(new BvarTab((temp1.var), true));
			}
		}
		if (((ActionStmt)aktion).stmt instanceof MFalse) {
			MFalse temp1 = (MFalse)(((ActionStmt)aktion).stmt);
			if (bvList.contains(new BvarTab((temp1.var), false))) {	
			}
			else {
				bvList.removeElement(new BvarTab((temp1.var), true));
				bvList.addElement(new BvarTab((temp1.var), false));
			}
		}
		
		// 3. Wenn ActionStmt eine Instanz von BAss ist, dann bestimme den
		// Wahrheitswert des Guards und weise ihn zu.  
		if (((ActionStmt)aktion).stmt instanceof BAss) {
			BAss temp1 = (BAss)(((ActionStmt)aktion).stmt);
			Bvar temp2 = ((Bassign)temp1.ass).blhs;
			Guard temp3 = ((Bassign)temp1.ass).brhs;
			boolean temp4 = get_guard(temp3);
			if (temp4) {
				if (bvList.contains(new BvarTab(temp2, true))) {	
				}
				else {
					bvList.removeElement(new BvarTab(temp2, false));
					bvList.addElement(new BvarTab(temp2, true));
				}
			}
			else {
				if (bvList.contains(new BvarTab(temp2, false))) {	
				}
				else {
					bvList.removeElement(new BvarTab(temp2, true));
					bvList.addElement(new BvarTab(temp2, false));
				}
			}	
			
		}		
	}

	// Wenn aktion ein ActionEvt ist, wird in der EventListe der Event
	// auf true gesetzt.
	if (aktion instanceof ActionEvt) {
			
			SEvent temp1 = ((ActionEvt)aktion).event;
			if (seList.contains(new SEventTab(temp1, true))) {	
			}
			else {
				seList.removeElement(new SEventTab(temp1, false));
				seList.addElement(new SEventTab(temp1, true));
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
		/*try {
			temp2 = (Aseq)(temp1.clone());
		}
		catch (CloneNotSupportedException cnse) {
		}*/
		while (temp2 != null) {
			make_action(temp2.head);
			temp2 = temp2.tail;
		}
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
	int counter = 0;		// debug - Variable (zaehlt die eingefgten "abstrakten" Transitionen)
	i = InList.size();
	j = transList.size();
	Path temp1;
	TransTab temp2;
	Path temp3;
	
	possTransList.removeAllElements();		// Loeschen der alten possTransList
	
	// Wir vergleichen jeden Pfad der InListe mit jedem Sourcepfad der Transitionen der 
	// TransTabEntries aus der 'abstrakten' Transitionsliste (transList).
	
	if ((i != 0) && (j != 0)){	// NPExep abfangen
	 
		for (x = 0; x < i; x++) {	// InList - Schleife
		
			temp1 = (Path)InList.elementAt(i);
			for (y = 0; y < j; y++) {	// transList - Schleife
				temp2 = (TransTab)transList.elementAt(j);
				temp3 = temp2.transition.tteSource;
				if (comparePaths(temp1,temp3)) {						// Vergleich mit comparePaths-Methode
					possTransList.addElement(transList.elementAt(j));
					counter++;
				}
			}
		}
	}
	else {
		if (debug == true){
			System.out.println("makePossTransList : Unwahrscheinlicher Abfang einer NPExep aufgetreten !");
		}
	}
	
	if (debug == true){
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
 */
private void makeRealTransList() {
	int i = 0;
	TransTab temp1;
	boolean bguard;
	Guard tguard;
	
	i = possTransList.size();
	i--;

	if (i > 0){		// Vermeide NPExep
		
		// Gehe Vektor mit den moeglichen Transitionen durch und eleminiere alle
		// Transitionen, bei denen der Guard nicht erfuellt ist 
		// => erhalte Liste mit real moeglichen Transitionen ( in possTransList ).

		if (debug == true){
			System.out.println("makeRealTransList : Ueberpruefe "+i+" abstrakte	Transitionen !");
		}
		
		for (int x=i; x>-1; x--) {
				temp1 = (TransTab)transList.elementAt(x);
				tguard = temp1.transition.tteLabel.guard;
				bguard = get_guard(tguard);
				if (bguard != true) {
					possTransList.removeElementAt(x);
				}
		}
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
 */
private Vector makeTab(BvarList daten) {
	Vector result = new Vector();
	BvarList data;
	data = daten;
	int i = 0;			// Debug - Zaehlvariable
	
	// Durchlauf nur, wenn data != null, sonst Ergebnis auf null setzten und zurueckgeben
	
	if (data != null) {
		 System.out.println("Die ursprngliche BvarList ist nicht leer");
	
			
		while (data.tail != null) {									// "data.tail" statt "data"
				BvarTab newElement = new BvarTab(data.head, false);
				result.addElement(newElement);
				data = data.tail;
				i++;
		}
		// Weil nur auf (data.tail != null) geprueft werden kann, mu˜ das letzte Element
		// au˜erhalb der Schleife exrahiert werden.
	
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
 */

private Vector makeTab(SEventList daten)
{
	Vector result = new Vector();
	SEventList data;
	data = daten;
	int i = 0;			// debug - Counter
	
	if (data != null)
	{
		System.out.println("Die ursprngliche EventList ist nicht leer");
		while (data.tail != null)
		{
			SEventTab newElement = new SEventTab(data.head, false);
			result.addElement(newElement);
			data = data.tail;
			i++;
		}

		// Weil nur auf (data.tail != null) geprueft werden kann, mu˜ das letzte Element
		// au˜erhalb der Schleife separat exrahiert werden.
		SEventTab newElement = new SEventTab(data.head, false);
		result.addElement(newElement);
		i++;
		
	}
	
	if (debug == true){
			System.out.println(i + " Events gez„hlt und in seList eingefuegt !");
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
 * Aufgerufen von			: Simu(Statechart, Editor, GUIInterface
 *							  makeTab(State, Path)
 * Ruft auf					: makeTab(State, Path), makeTrans()
 *
 * @param daten absyn.State
 * @param weg absyn.Path
 * @return 
 * @version V1.00 vom 14.01.1999
 * @version V2.00 vom 23.01.1999
 * @version V4.00 vom 24.01.1999 - Namensabbau
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
	
	data = daten;						// Verweis kopieren, da wir Liste abbauen
	
	if (debug == true ){
		System.out.println("makeTab(State, Path");
	}
		
	if (data != null) {
		
		// Wenn der "data"-State ein Basic_State ist, dann mache gar nichts, denn ein Basic-State kann keine
		// Transition haben.
		if (data instanceof Basic_State) {
		};

		// Wenn der "data"-State ein And_State ist, gehe die Substate-Liste von vorne nach hinten durch
		// und rufe makeTab() rekursiv auf. 
		if (data instanceof And_State) {
			weg = weg.append(data.name.name);		// "weg" um den Namen des aktuellen AND-States verlaengern
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
			weg = weg.append(data.name.name);		// "weg" um den Namen des aktuellen OR-States verlaengern
			workList =(((Or_State)data).substates);			// Lege abbaubare "Kopie" an.
			
			while (workList.tail != null) {					// Standartabbauvariante
				makeTab(workList.head, weg);
				workList = workList.tail;
			};
			makeTab(workList.head, weg);					// Rest der Standartabbbauvariante
			workList = workList.tail;

			// Dann lege zwei "Kopien" der trs an, die spaeter gebraucht werden.

			trclone1 = ((Or_State)data).trs;
			trclone2 = ((Or_State)data).trs;

			connList1 = ((Or_State)data).connectors;

			
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
			
			while (trclone2.tail != null) {
				makeTrans((trclone2.head), weg, trclone1, connList1, new TrList(trclone2.head,null), new ConnectorList(null,null));
				trclone2 = trclone2.tail;
			};
			makeTrans((trclone2.head), weg, trclone1, connList1, new TrList(trclone2.head,null), new ConnectorList(null,null));
			// Rest der Standartabbauvariante

			killLastElement(weg);	// Korrektur des bisherigen Wegs ('down'date)			
		};
	};
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
		
	// siehe (1)
	if (trans.source instanceof Conname)
	{
	}
	
	
	 
	else // hier ist also davon auszugehen, dass (trans.source instanceof Stanename) = true ist
	
	{	// siehe (2)
		if (trans.target instanceof Statename)
		{
			Path source = handClonePath(weg);			// Hier werden Clones erstellt, weil die Eintr„ge in die 
			Path target	= handClonePath(weg);			// transList nicht mehr veraendert werden duerfen.
			source = source.append(((Statename) trans.source).name);
			target = target.append(((Statename) trans.target).name);

			// Instanz der TransTabEntry-Klasse fuer Transitionsliste erzeugen, Transition eintragen
			// und in Transitionsliste uebernehmen ( prevTrans und prevConn mitiefern ).
			
			// *************************CLONING *****************************************************
			try {
				prevTransTemp = (TrList)(prevTrans.clone());
				prevConnTemp = (ConnectorList)prevConn.clone();
			}
			catch(CloneNotSupportedException cnse) {;
				cnse.printStackTrace();
			}
			// ****************** ge-clont - theoretisch muesste es funktionieren *******************
			
			TransTabEntry tteDummy = new TransTabEntry(source, target, trans.label);
			transList.addElement(new TransTab(tteDummy, prevTrans, prevConn));
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
					// Problem: Sind die Objekte wirklich gleich ? **** M÷gliche Fehlerquelle *****************
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
 * aus der InList und fgt ihn in die ExitedList ein. Dabei wird die InList von hinten nach
 * vorne durchlaufen.
 *
 * Benutzte globale Objekte : InList, ExitedList
 * Aufruf von 				: killLevelConflicts()
 *							  removePathInListLinear()
 * ruft auf   				: comparePath()
 *
 * @param KillPath absyn.Path
 * @return 
 * @version V1 vom 14.01.1999
 */
private void removePathInList(Path KillPath) {
	int i;
	boolean found = false;
	boolean test = false;
	Path temp1 = null;
	
	i = InList.size();
	i--;						// Vector beginnt mit Element "0"
	
	while (found == false) {
		temp1 = (Path) InList.elementAt(i);
		test = comparePaths(KillPath, temp1);
		if (test == true) {
			InList.removeElementAt(i);
			ExitedList.addElement(temp1);
			found = true;
			}
		
		i--;	
		
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
 * @version V1 vom 14.01.1999
 */
private void removePathInListLinear(Path toKill) {
	boolean kill = false;
	int i,j;
	Path toCompare = null;

	removePathInList(toKill);							// Entfernt den Vater	
	i = InList.size();
	i--;
	for (j=i; j >= 0; j--) {							// Schleife durchlaeuft die InList
		toCompare = (Path)InList.elementAt(j);			// Bestimmt Element j der InList 
		kill = greaterAndContains(toKill, toCompare);	// Bestimmt, ob Element j Sohn von toKill ist
		if (kill == true) {								// Wenn ja, 
			removePathInList(toCompare);				// aus der InList entfernen und in die ExitedListe eintragen.
		}
	}
}
/**
 *
 * Traegt fuer jedes Event der Eventliste den Wahrheitswert "false" in die Eventliste ein.
 *
 * Benutzt globale Objekte	: seList (EventListe)
 * Aufgerufen von			: step()
 * Ruft auf					: ----
 *
 * @param
 * @return 
 * @version V1 vom 14.01.1999
 */
private void resetEventList() {
	int i;
	Object temp1;
	SEventTab temp2;
	Vector tempSeList = null;
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
 * Waehlt die Art, wie der Nichtdeterminismus beseitigt wird (Benutzerwahl oder Zufall)
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: killNonDet()
 * Ruft auf					: utils::RandomGenerator
 *
 * @return simu.TransTab
 * @param arg1 java.util.Vector
 * @version V1 vom 14.01.1999
 */
private TransTab selectNonDet(Vector arg1) {
	switch (NonDetChoice) {
		case 0: break;								// Auswahl per Benutzerwahl :-]
		
		case 1:										// Auswahl per Zufallszahl
			RandomIntGenerator rig = new RandomIntGenerator(0,(arg1.size()-1));
			int x = rig.nextInt();
			return ((TransTab)(arg1.elementAt(x)));
		default:
			return null;
	}
	return null;
}
/**
 * Macht einen Schritt
 *
 * Benutzte globale Objekte : ----
 * Aufgerufen von			: makeStep()
 * Ruft auf					: resetEventList(), executeTransList(), copyEnteredToIn()
 *
 * @param 
 * @return 
 * @version V1 vom 14.01.1999
 */

private void step() {
	EnteredList.removeAllElements();
	resetEventList();
	executeTransList();
	copyEnteredToIn();
	//highlightObject();			// Nachdem alle Objekte an den Editor uebergeben wurden, 
									// wird die highlight-Funktion gestartet.	
}
}
