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
	private Vector EventList;		// Vektoren, die die Listen darstellen, die wir
	private Vector InList;			// aufbauen wollen
	private Vector EnteredList;
	private Vector ExitedList;
	private Vector possTransList;	// Transitionen, die moeglicherweise ausgefuehrt werden
	Vector transList;
	Vector toDoTransList;			// Transitionen, die keinen Nichtdeterminismus aufweisen
	private GUIInterface gui = null; 		// Referenz auf die GUI (mit NULL vorbelegen)
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
	public boolean debug = false;
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
 * Ruft auf:					: makeTab(State, Path)
 *
 * @param Daten absyn.Statechart
 * @param eEdit editor::Editor
 * @param igui gui::GUIInterface
 * @return
 * @version V1 vom 14.01.1999
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
public void button1_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	LF1.dispose();
	LF2.dispose();
	getFrame1().dispose();
	System.out.println("Simu.Button 1 gedrueckt !");
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
 * "true" zurck ( sonst "false" ).
 *
 * Benutzte globale Objekte	: ----
 * Aufruf von 				: removePathInList
 * Ruft auf   				: ----
 *
 * @param arg1 absyn.Path
 * @param arg2 absyn.Path
 * @return result boolean
 * @version V1 vom 14.01.1999
 *
 */
private boolean comparePaths(Path arg1, Path arg2) {

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
	if (argcopy2 != null) {					// argcopy2 war einfach nur l„nger.
		result = false;
	}
	return result;
}
/**
 * connEtoC1:  (Button1.mouse.mouseClicked(java.awt.event.MouseEvent) --> Simu.button1_MouseClicked(Ljava.awt.event.MouseEvent;)V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button1_MouseClicked(null);
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
		this.button3_ActionEvents();
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

 *  @version  $Id: Simu.java,v 1.16 1999-01-22 14:04:52 swtech26 Exp $

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
 * @version V1 vom 14.01.1999
 */
private void firstStep() {
	String rootName;
	State rootState;
	Path  rootPath;
	
	rootName = SDaten.state.name.name;
	rootState = SDaten.state;
	rootPath = new Path(rootName,null);

	System.out.println("firstStep erfolgreich aufgerufen !");
	
	initInList(rootState, rootPath);			// Initialisiert die InListe (InList)
	bvList = makeTab(SDaten.bvars);				// Initialisiert die BVarListe (bvList)
	seList = makeTab(SDaten.events);			// Initialisiert die EventListe (evList)
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
	D0CB838494G88G88G86DAB2A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E13CBB8BF0D4D51651E19CE64D2CE10D4AA86E2203B3A945AD18CD15EC09DB38C30D380B3559119D9807590A2365C64DA06E32B5EC956A2373A1AA1FA0D1F1CCA01FCE52A11DCEFF7269F4BAC907A498A011043469008152C9BF1206CEF7EF778B840D631EFB6FBB2FDF3FFE5D9214256A6475BD675EF34EBD775CF34FBD6F41959DDE1A37D3D344F1BB73B871DF73CE0E53F6F25CE0CA57F308ABA8F5E9B8
	593FB2007D5C03DCAEBC8F824DB9665294F23D7E82E86B213D7E02CB73A17CBE40757BD3BB60C79CB9BE2085DFF0FD1ED84E43A614330B367FDC3C9B1EFB810CC4B64012D2A27F6E6203A2FE965AFB455F84F682D7DD996913DFF5D0064F947B6DA063011ECE7B2CACD6768982FE814092003729E6FB5E330A748C535D5C481B9F676FA1F2E46390F614B19925656C49F12F6791A364F00F1C5B87CF7B4065497630F7FA46E69B023FDCCAB951C1DFD5C07BF05C88340BAB88EED995197786E8E78364D620AECF15
	5BC5D949BFFE908CD59CD717318A2643020A08AEBA3BA307495CD80CB257D6204CA7AB084C9D703BC86B52683508FF350D603B21BD8AA0E8D117172AD017D501873951C371F5C956B29DD6E8A33A4C9DF164FCC4573849C256A09BE80515AE0D962097E08AE0A140475437DEB79D01E767385095181E8E78BB034123BECF28BF6061FD705BE6830A08BB4DCF84C29E0EE37EE32A522B68033034B25AF788303939B8DC736E14A5DC2AE0494FD319DB515E0A7CF411473A4A485CF22B085D8ED6933BE581FE274817
	61ABA93E37AA0257D7937C43F44DC95B8334F5D5E86785DF22AE4EAF97F119C37A7CDDEA3ABCEA4A8FD6B39D96D6C7F4E1B6AEECA4B6CE87FC86C096G27F3E9CA819CBA34F12B5972CDB6BE6E9778105F6D9B1A998F3AC34251992F4F4307C4DBEFAE0EE76BD1DD2C1D77CA76E85412F987F54C9EF9925EC1718BE4F8E62727281F1276E2206DGE07DAF35937CD80D525ED7A9FE2EC6E96F575B505E0B01368300596ACA933155589117E68EA095E0B940FA002CA3E82B5B4C7557690F83B2BB31319D168F6049
	4F8AA177F1FFD8F07BA778E1F7486B9E7771E1CE6C53EBA1B69D0F9A57CF7749844878047773A137608D78013BFB1A07C58933BE43167D24CF0827D427F71A878153C18E791E379C00274F9D96BE8BFA5C829F69416897AD449ED75C4340919840EFEBAD0D77B4C69540EFEDAD5A697EE6021B03F6EAAD5A65F94BFB70BC89CE427BEC36EE6F84D1439D3A067B71D0F5A1BC53203FDE6F522CGB8D807FE3EE5847DBC2978BEEE75DDDAD5BFCFFF7C7D7C24BA66CF0B6BA2FE4E7857D27EEBG1F89409940478DF8
	162C289A91F99079A8BBDEECA07A45232981137134A4A3C7443D1DE0CC5EBC78C7E2D31AC59E1B4636D5295AE979D0C53E434066B0E2D0460323AD4477E7811F8A10EE4075ABEEA8E5FEFD549D665113CF054089F9CFE4B5D95F0F9A491946DF617DC2B79F647D9E7891967D2834716D080FDF8CC87ED7C571A4F6F4019306A53C3EF1BF43838F790EC1FA83253B85A1649D1F9178A1DE903C7E4948F8BB55A59C951642226F7E97BD6B56439CDF14FC21D5CB7D399E4E38FA449D23B8FD3D3C5FB395381ECAA0E3
	493338FEFEFE926BB3AF451511F303FE92ABA358DEA87AC80E48EF17C47B0A5286E09E33GCB8156C9B679E35B084A389B55C5DD37427691857FC4FBE63E407C636B26783EFADB133A2FA6B7297B6ADFB5A57645FB1B54FC11519EE8A271F1A2607773F4B3C9E40E6509DB1CC4E72F5FAB383C1EC9DE9872FE41C31AE7A4FEFC0146602D30B639C60C19D7ABB09668FA6E610CFF78C0B5CF595D5B10BFEAE4FE9AB4C6E281536941E6928BF89FBF8DC6698A4C788506CF2371DD4407F3D85F3DB370AC559D1D9104
	005F241F8E4C04F9837D5B6573CEDC66BD3ACB6EABEE037B2AE02056B67493DA8F79D9D3DAD757DECA92DF7DB89BFE21DFBE30AF6E48561A619A2368A387382D93F29D9A3E1D8E8D735141A469D0F9792CCC87733753419C2D8388B2231C329E399C4B3713E33950C91D0ED243C48EA4FC6CBC7852C6FCA085FCE39940EAA31E87DAAB2163F9AE9E84DD811F0F39AF509ACCB4772AAE12795B86A33B2B15820D51DFD5254FC9F41FCE0D3ED7623FB8AE1D693F20156A8FFD53GD619D07FF35683B27D7B82014BB3
	C1A74934B04FEAA131AF4C7F6F8C8FE998A11C642F0939542916779434E137EFA6122339DA5E0527CF46136D6195EE028F063C53F0D41243CC3C0F3ED3A23FEB2E879D495DB59B1E1C5925E16BFD6B29FCF89EA3C91D89E342AF1C64CECC561171791ED60D4FC160218758EE26775A76E2115E8D6DD100B9B36B374A4C6C467A899DD2BF8B74831833087DAC72FEF7E8315FF64097B638B44686564F57A06FF73A927B25GBECD56EF1D58AF3B01743BA25DF96BA55C0FA86ED9B040E339186B74974A6F5E5C8F0C
	2A710D003111716FEED466BA7FE8A7FCBD00979A51AFFE55A2770B4EF0B8B06125A933383637586431CFBCA7EC6F407322B79406FB10071F154E899B51156454A2DADC37C7EA5B3858BBC20F47AB84C218DAF5DD251B51996802343E97DC29CBBA9A063D7CD5E2C7FADFFF209A6D353091E37F6F4F4AEB810226B87F5DB836D9A35AA4D3E69B47912C85146B10F7E9935A67AFDA554F0414D6620B1725BD125ACA7C750A38AF583C7F7EG592B3456013149E623AE4D6475179408B69AE9429AC3E9A95D3F50CE83
	D883407ABECEF3B21293EC127A1E21776742E617C68760GE07739675A71DEB8823814E60CADFFE815470EF5004F86A834621E6B6C2803673CA22B34467798A1D4F373BF4E503FF73D639075CA6216F0EBCCCD797BD456B065FBA579DAABDB3BBEEBE48DB7DDF264E48330795F5FA15D0B21CF0695677FE40B5A7C8BDAF07E035A9B1F7F30EC7E73B9CA718E3ECA0BBA9703BE4E9654F9DD2D1A4E49124E07ABEFDCE72E68C3C9671823D7546B4BCA54EBA9485CA46975E61D2AAF59DC9A467BE2D904F74CF10B35
	A533BC26E5DDBA9CB726E9EDEC2D2DB6E5CC7BE9BB5997CE40CF59F04FFEE45FAF0BE927A10A387D13F0AAE11CFA4A1E43762E587EE7FB9E6EDD8EF3711FD6A72A17A9E14E06350097BA08DDD2203D9CE0AD40E6009D12EE275B3E313654B31B2036D42D1B0FDE452DF8FFD9D0229CE7EC0D5C573ADBE36FEE2CE614CA6351C8AB2B9965C8783F2678A9991E79CB95451376AC50B6CB7257E815729D7608CC27BDDA7E149DEBD94F5098B7E7D74A5FC87149F6257C8B55A8FF895038B6141F592E146FE90B161924
	E833F1DBE24699946DE50AF14C7FFFE5FB1F0B097DACF96530AE3FD7A74FDFD6839FF2675E8C4F2DED987735F6BCA38A5A5147DABAC9EE610076A800G1082108E1081302D9DFD6D01B635F3B75A57629D3A113A833343CFBAEE4CFEABE3469DD2340BBA224731F9EFEF4173CE5F01EB788FB1BEF4FDBA2C0F9927DCC30F2A8EEF55228EF3128E37D45E188EF7440CD36A102E2AC32381F5D85F016B3F60A84165BADC1AA2872E751BED641D83463C010057AFF0986F9EE9A739C9A0641D747A91370262A68250BF
	04354DA7E8EE25851E8E096F2B9D716B857F5921DEAF585E21DEAFF833A3F1EDAB5F912F1E4074EB5721FECEG063B0F66D0D3501E13F08B8E915C92E827BBF05D6EADA5795AA6293D0C3637CAEDFDE93C5A53FC0148D062180C1E6FCCC60E8A0E4DFDA34D930BBAA1A76CC4FB2C29C4FB74CA3867699D43876D24CE3441335544A649D27BF734CD4EF556662B3FAB7D098CF59B7D79BB1321E623920AFF7A03FD548F43E1BECC83E037DBF00B347F905E67D46AD21FC2524EE1AD62C36A5F24CF0F9068A2359096
	73FF23D34977875C30A359DEBE2643D83E2C93F378C5B5643C5A8C6DEDG3B0F222DEB6C094EF96D51087D2C62EF36073ABA636F57931D6A7BF52013440EF0E0A6B441AB77242B7320C40B1C8E4785FE1A18047599236385F7E8120F595313F4BC23459B9F6C0C376F193D1F0B3237D97215B7DA9259288F6CB2A65AC6901F0C5FFFEB657CAC923FDCFDA2FE8B014792114F0AA8FE2BAA657C9AA4FEB6C3A2FEEB0147A611CFF6549A7E70A849E9E625760FE87B1A64E7FC67DE4926BDFE0F986CD96DE0830D614F
	EBB86EE7B9087B0C7A1D851834CABA7F3C0444FF1F7BDAE08646BA3AC83F27CB64B9BD5745F452F5E1CE3F72AA39D7DF048BACAC4FD0101F600A3BE98CBE04F94CC2683B84E0B540E6009DDDF8CF3953C57C9CBCC0A08EC9B6854B017E2DD4198311F78E9E60BB5B0DE337E8E3F54D2BD453B58546B01DB6D6A25FB4E82F86588C300350FB106F76AAB2AF41AB503B013D07FC07D1D14BC9B60A55192D6B49189CBC29A7329FD3FB626552DF5692FEE9BDACE75DA761EF27788C991E6532775058C05A5B01A69C43
	586D30E3BC5FAA615CEDB46EFF82F17F9344955930DF0304EBECA53891E87BA45C979417846DD48977319E47AE15F076BA9C3BD6425DD607E3B7C938C1830EFDD142056AF1ECC1AF629E2B4731453D68E3AF99084FAB7366G293776B187E0F1730592E29B9D0CF3G0C74E2AD6807B3D88B32377E8D17BDDA22DA8BDA7E30A5BF58AB7ACCEF64CE19A255021E24B98499379E683947896E35C3F21FA3BEC2BE73B110F524742F8F4B69A4267DB6C7CEDF99C5A77AFEBFEA7C16A8BA3147A7A21D4D6D158F24393D
	71G27DF947F7B1B3E6362BB8E71796231C7738A53F1EEFB4A709CCABB0E6750C8B931E7A6343793DDCEA07EB03D7F6B206DG98B9017712B7AA51BE4193E81F5AAA25FD1E5FA91F5F39AA25FDFE92B57FBB2A1476E94C15531F2CD25A6737D17679702C3C1E6078C5F97E4EB876C9BB416C12FEA232764CA747F518BFE51DC0BFBDD6037EDCF0D23A875412BBD88334FB81C6818400E400E5G2B24FE0BE83FC231863C9E7059G3B7BD80D195423D93F7BEB02A2BFB21E68346AE4A40E0C1E0C0DA9CA68957B88
	5751B7D96C43E42F263249FA6D91697BE4FD2F67BDA73E173D1E77156F0ACF9627DDF60C53FA300983E08CA089E0E99F466959B6425F679E67FD9C466991D27692F9545A10479A983381E09BC0D1BF443DFE64B1D4C766417B496B898FB755CF461DEFC59FF6CA7D6E2F0B15F50FA1A24B2CC7D94BE14C9A00CCG6ED317269400713DD407FC9D00E3BCFEDD9F61B1E5C09ECEB286E0A1C09A40BA0947FD7548A3CB62E1A32926F8E6388E51EF54BEE5E746CE89BFC6718527A2F8F6163CCC63A5E99781ED76945E
	A50B4DE4CFED0676F6004281B8BFGFA87707EF729163CEFCCD8C3F08602FD10F07AE4D7BA66D73FBCB41FDA957A8F477D298C635C54543DDCF249213875FC4F0078ED1F78ACBE78E89EF9323925536F0452011681308DE077A02C9300F5906777C42D1E4D0F3C0E689484774494FD530113A3AF9FA2E8127430393DD7261CDB5FE0E45F0D8D464B81EE255FEC89034AF53B1D621386156BF63E9C57AD99E8DB8731AEF6A70D4B4E53CABECBA8FE6C3412CFE5A5727181AD63B4469DCD426F82550059F6A3C9F345
	7CB4C7DF894FE177A555B7BBA76098BFB5FE8992FF27FB12437C6D762877188DC34CDED370CC9D22E7C5CDE9829D12066479779D6F103908EF20C5DCD2F584E791F1A75FA16F2D1CF081BA6665FD9E930E5FD8C27C2EEB0A1F38BC9E180D3C5BFE62E3B95EAC619FF7C5F8B708384C7D911CC9FCD7FB44A81F6366A1D6A3671CD8C3BFEB16530B1C0CEE1568F7B52B517B1CF886FFE54798B16644B378913B728CA601A7F2067E4E2EBC039B23687B5A14E7704F224E704EB66599FCC04368CC27C19B6A34D85269
	EB1BD227D3D1B2D75A14BA55C651DF30A9F50A4E9B5EEFD56AD4A66A4C56E6261560AE3AFDE11E439833ECD21EABECFA3ABC6E3B078C2718C3094F29830F6631390E68F12E451F61DCEF53AB676A0A52F515DEB9571AA87AABFA65DC9F0A229757A9670A36E2F33D25CEBED72667DF9A24F3CE52F335C12967E7D1F2369814FA5E95C5AF2DD76A79C0947DF43DD24F7AA8BDFFDEAF57134522F324B4AC7E3EA87B7DDA49F7D593D283A62360504D14A1FEC3E20EDF1B29B52B57E6CCE675DAEA33B97137D79DE635
	EF2FD86C2EEDA2EB138C3E1086309EA093E05BE7F8A67E0B391C23E7224AF9586319A4F5A072352E00321E30441F575A6B784677998B094B6381081D5304BF621F255F7E8A01A0C5B25CA67ADDEE48BBB9A5302EA27E5FAD341E49DF24E816375714E1ED38F098F379BD65184B9B07718CDCD8917135C5325F1E721B6FF7DF7DBF4878F1E5E4CE8F4AFE57D65EFC595DB7D5862B99CC1613C9511A41C4E05A609D1FB64CDC19B6384785838F29084D76690C4FA7D68FB831FE70100E64C323608382C072B0667FDF
	5410DAD397CF3E6F473362F6AD66235B9A6F65B63CEC4AA7FBCF6D2CD8B64C4E080C61483DB22267DB6F9F64E2EA9BD2BA42E2355B086FFBF2DDD893F93D7E4358F1181608B1FE11190CA37A5ADD189F2CEFC6DE028BFD7D5604F565786BB8EC3B31BCF1B155E1B948DF674278F226111CD92A79A2A087C201A09F922EB1AC7D528C4F07047559783A5F19308E2B86CCCFCB02EFE5EDCD5F9C573A1A6837D3900F1C5702E22D3B37093E0B3BF8B14CCB71333F29D0429D8F8F8442DED2A3E7F97269B235BCB94B05
	F932363CB441BC3891561F5D8DBE2275442291651D211C62CBC714F706C7F4F8E75083ADE5846F0C3F3692195B20DDF886F2D500BE00B100D900E400E5E7F02DDFE952A5D8EBD6582367C9486D758B6259EFD4DB2FB5E758DCB640B36B0C5ABB563B4D0956F9870CA9BC4B46E961E9BD0BFB62DF1B933EC700FEE362B8612C3CCEFFFFB379AD1D414CD5C55AB21A3B136F173DEE1F778B7AC95D90F474CF327863ABA336DD887C16822CBAABFD03D5AE0FBFCC3F43A4C594FF6BE43FD76AEEFE7C7E271BAAC37C3F
	B256787BAC4B7A4D7B6CF52BFA7E70C7EBEC7E30432A1EBFE8DA647983436DEAE1FCC3528BC0065F53C26AE46CFDC43F5B6F1E246F3BD8FE71950D5F99301E19E7B167783D91F30EEC8917E346FAF8616708FB540C7D3492AE35897B75CA385FB4E15DFCD44259E81C1D02761C044BEAC6FEA9926E6D966417AE61BEEDC1FE6BBE477AC2A6754F8D1FABE3454B941F7939B2D6044AB0D6E483EDE0947DB955C85EA18B30002C0675100D78AF2B037D0F916BE06950FF9DC096C041B917C6F78E6BE0FAB35EDDFA4F
	613B1D35E69CB78A38A0C08A408A008CE95C93CDB8EE6BB92C1F3DC6DEFF09770F41E6F23602C3894487581829E67C7EB377BC4AFA2B99E59581CE87508BB0853070BC4AB237202C3473A84B50965927AF15C5FE3FA67BBDDBF673776F221BAA03659907CDB87F556771FE59EFD25EAF236B8CDF1B14774B68FB6ECA1372FE79F7D17497CC4A7BE5E5541D3F4B9CFB8FFEC57225ED121E87AD096BA5A7ACB1777528FA481CC529672EA87A5FDA14FA36F0F2BDB7B44975E4382F1B691E894D085F89D7DAD1EF7D85
	54FB481AD86F6FDBE3EAAAD1FABDE6D56A3DBF0A7E32B5C6EF0DDC6F63AD3177F72D6946723357921E5B71479D314EF79C49237FCF139B8E7D874D256B64B634FEE918FE7D651BB72F5F3F85F6F2973CFC8B5EA47266A130057225216B726397EFDEBE7D7C99D89BB534DEFA8AEC1CFDE968B87B7075534BDF1EFE78525089E21E03309BB7FEE918F479673B47AFBE3DFF77745302C3CF01D57D53C7DF9AA69F7C0CA15AG6BD7F0ACC59E033AF2E9AE4EB1A8098710F6074E8374752FAA0D2034118194EFF988EB
	F3E5B600A6C7F00D47DEFE7B704D1BAF8F5F3CBE786E89E8EC76504FAFCF9163861A0DA073BF4354C0A521EB469AC16EFE052487A040GD0CB8788782F4BB16D95GGCCC1GGD0CB818294G94G88G88G86DAB2A6782F4BB16D95GGCCC1GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGGA796GGGG
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
	getButton1().addMouseListener(this);
	getButton4().addMouseListener(this);
	getButton2().addMouseListener(this);
	getButton3().addMouseListener(this);
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
 * @version V1 vom 14.01.1999
 */
private void initInList(State x, Path y) {
	StateList liste;
	String compareName;
	StatenameList nameList;
	State targetState = null;
	boolean found = false;

	System.out.println("initInList() aufgerufen !");
	
	InList.removeAllElements();			// Alle bisherigen Eintraege aus der InList loeschen,
										// da es sich um einen Neustart handelt.
	
	if (x instanceof Basic_State) {		// Wenn es sich um einen Basic_State handelt, wird
		InList.addElement(y);			// der Pfad von x in die InListe eingetragen. Rekursion
	}									// ist dann nicht notwendig.
	
	if (x instanceof And_State) {				// Handelt es sich um einen And_State, muss zuerst der
		InList.addElement(y);					// And_State selbst und dann alle Substates aus der
		liste = ((And_State)x).substates;		// Substateliste in die InList eingefgt werden.
		while (liste.head != null) {
			y = y.append(liste.head.name.name);
			InList.addElement(y);
			initInList(liste.head, y);			// Ausserdem muss fr diese (Substates) ein rekursiver 
			killLastElement(y);					// Aufruf von initInList erfolgen.
			liste = liste.tail;
		}
	}
	if (x instanceof Or_State) {				// Handelt es sich um einen Or_State, muss zuerst der
		InList.addElement(y);					// Or_State selbst und dann der dazugeh÷rige Default-State
		nameList = ((Or_State)x).defaults;		// in die InListe eingefgt werden.
		compareName = nameList.head.name;
		liste = ((Or_State)x).substates;
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
		killLastElement(y);						// Aufruf von InitInList erfolgen.
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
 * @version V1 vom 14.01.1999
 */
private void killLastElement(Path arg1) {

	Path help = null;

	if (arg1.head != null) {					
		if (arg1.tail != null) {
			help.head = arg1.head;				// Mindestens ein Teilpfad-Element muss uebernommen werden
			help.tail = null;
			arg1 = arg1.tail;
			while (arg1.tail != null) {			// Sollten noch weitere Teilpfad-Elemente vorhanden sein,
				help = help.append(arg1.head);	// werden sie hier bis auf das Letzte (.tail == null !)
				arg1 = arg1.tail;				// uebernommen.
			}
		}
		else {
			help = null;						// Nur zur Sicherheit (bei dem Fall arg1.tail == null)
												// Ausgangspfad bestand nur aus (head, null).
		}
	}
	arg1 = help;								// Uebernahme des Ergebnisses
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
 * Aufgerufen von			: make_action()
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
 * Aufgerufen von			: button3_ActionEvents()
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
 * Benutzte globale Objekte : transList, possTransList
 * Aufgerufen von			: makeStep();
 * Ruft auf					: ----
 *
 * @param
 * @return 
 * @version V1 vom 14.01.1999
 */
private void makePossTransList() {
	int i, j;
	int x, y;
	i = InList.size();
	j = transList.size();
	Path temp1;
	TransTab temp2;
	Path temp3;
	possTransList.removeAllElements();
	
	// Wir vergleichen jeden Pfad der InListe mit jedem Sourcepfad der Transitionen der 
	// TransTabEntries aus der 'abstrakten' Transitionsliste. 
	for (x = 0; x < i; x++) {
		temp1 = (Path)InList.elementAt(i);
		for (y = 0; y < j; y++) {
			temp2 = (TransTab)transList.elementAt(j);
			temp3 = temp2.transition.tteSource;
			if (temp1 == temp3 ) {
				possTransList.addElement(transList.elementAt(j));
			}
		}
	}
}
/**
 * New method, created by HH on INTREPID
 *
 * Benutzte globale Objekte : possTransList
 * Aufgerufen von			: makeStep()
 * Ruft auf					: get_guard()
 *
 * @param
 * @return 
 * @version V1 vom 14.01.1999
 */
private void makeRealTransList() {
	int i = 0;
	TransTab temp1;
	boolean bguard;
	i = possTransList.size();
	i--;
	Guard tguard;
	// Gehe Vektor mit den moeglichen Transitionen durch und eleminiere alle Transitionen, bei denen der
	// Guard nicht erfuellt ist => erhalte Liste mit real moeglichen Transitionen.
	for (int x=i; x>-1; x--) {
			temp1 = (TransTab)transList.elementAt(x);
			tguard = temp1.transition.tteLabel.guard;
			bguard = get_guard(tguard);
			if (bguard != true) {
				possTransList.removeElementAt(x);
			}
	} 
}
/**
 * Diese Methode macht genau einen Simulationsschritt, der NICHT der erste ist
 *
 * Benutzt globale Objekte 	: simuCount, aSyncOn
 * Aufgerufen von			: makeNStep()
 *							  button2_ActionEvents()
 * Ruft auf					: makePossTransList(), makeRealTransList(), killNonDet()
 *							  killLevelConflicts(), step()
 *
 * @param
 * @return 
 * @version V1 vom 14.01.1999
 */
  
protected void makeStep() {

	boolean aSyncOnDo;
	
	if (aSyncOn == false) {					// Handelt es sich um einen synchronen Step ? Wenn ja, dann ....
		if (simuCount == 0) {
			firstStep();					// Mache den ersten Schritt
			simuCount++;
		}
		else {
			makePossTransList();			// Berechnet alle theoretisch moeglichen Transitionen (possTransList)
			makeRealTransList();			// Berechnet alle praktisch moeglichen Transitionen (toDoTransList)
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
				ExitedList.removeAllElements();	// Exited-Liste zuruecksetzen, denn in den folgenden Methoden wird sie gefuellt
				killLevelConflicts();			// Beseitigt Level-Konflikte (in toDoTransList)
				if (toDoTransList.size()>0){
					step();						// Macht einen Schritt 
					simuCount++;
				}
				else {
					aSyncOnDo = false;
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
 * @version V3.0 vom 16.01.1999
 */
private Vector makeTab(BvarList daten) {
	Vector result = new Vector();
	BvarList data;
	data = daten;
	int i = 0;			// Debug - Zaehlvariable
	
	if (data != null) {
	    System.out.println("Die ursprngliche BvarList ist nicht leer");
	    
	    
	    while (data.tail != null) {		   // "data.tail" statt "data"
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
	    if (debug==true){
		System.out.println(i+" Bvars gezaehlt und in bvList eingefuegt!");
	    }
	}
	else {
	    result = null;
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
 * @version V3.0 vom 16.01.1999
 */
 
private Vector makeTab(SEventList daten) {
	Vector result= new Vector();
	SEventList data;
	data = daten;
	int i=0;

	if (data != null) {
	    System.out.println("Die urspruengliche EventList ist nicht leer");

	    
	    while (data.tail != null) {
		SEventTab newElement = new SEventTab(data.head, false); 
		result.addElement(newElement);
		data = data.tail;
		i++;
	    }
	    
	    // Weil nur auf (data.tail != null) geprueft werden kann, mu˜ das letzte Element
	    // au˜erhalb der Schleife exrahiert werden.
	    SEventTab newElement = new SEventTab(data.head, false); 
	    result.addElement(newElement);
	    i++;
	    
	    if (debug==true){
		System.out.println(i+" Events gez„hlt und in seList eingefuegt !");
	    } 
	}
	else {
	    result = null;
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
 							  makeTab(State, Path)
 * Ruft auf					: makeTab(State, Path), makeTrans()
 *
 * @param daten absyn.State
 * @param weg absyn.Path
 * @return 
 * @version V1 vom 14.01.1999
 */
private void makeTab(State daten, Path weg) {
	State data = null;
	Vector result=null;
	TrList trclone1 = null;
	TrList trclone2 = null;
	ConnectorList connList1 = null;
	
	data = daten;						// Verweis kopieren, da wir Liste abbauen

	System.out.println("makeTab(State, Path");
		
	if (data != null) {
		
		// Wenn state ein Basic_State, dann mache gar nichts.
		if (data instanceof Basic_State) {
		};

		// Wenn state ein And_State, gehe die Substate-Liste von vorne nach hinten durch
		// und rufe makeTab() rekursiv auf. 
		if (data instanceof And_State) {
			weg = weg.append(data.name.name);
			while ((((And_State)data).substates) != null) {
				makeTab((((And_State)data).substates.head), weg);
				((And_State)data).substates = (((And_State)data).substates.tail);
			};
		};

		// Wenn state ein Or_State, dann gehe zuerst die Substate-Liste von vorne nach hinten durch
		// und rufe makeTab() rekursv auf.	
		if (data instanceof Or_State) {
			weg = weg.append(data.name.name);
			while ((((Or_State)data).substates) != null) {
				makeTab((((Or_State)data).substates.head), weg);
				((Or_State)data).substates = (((Or_State)data).substates.tail);
			};

			// Dann lege 2 Kopien der trs an, die spaeter gebraucht werden.
			// Bemerkung: Rueckgabewert einer clone()-Operation ist immer ein Objekt, daher ist ein Cast
			// notwendig.

			trclone1 = ((Or_State)data).trs;
			trclone2 = ((Or_State)data).trs;

			
			/*			try {
				trclone1 = (TrList)(((Or_State)data).trs.clone());
				trclone2 = (TrList)(((Or_State)data).trs.clone());
			}
			catch (CloneNotSupportedException cnse) {
				guiOutput("CLONE-ERROR in makeTab(State, Path), Kopien der Tansitionslisten");

			}*/

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
			while (trclone2 != null) {
				makeTrans((trclone2.head), weg, trclone1, connList1, new TrList(trclone2.head,null), new ConnectorList(null,null));
				trclone2 = trclone2.tail;
			};

			
				
			/*while ((((Or_State)data).trs) != null) {
				makeTrans((((Or_State)data).trs.head), weg, trclone1);
				((Or_State)data).trs = (((Or_State)data).trs.tail);
			}; */

		};
	};
}
/**
 * Erzeugt einen Eintrag in der global definierten Transitionstabelle
 * Parameter: siehe makeTab(State, Path)
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
 * @version V1 vom 14.01.1999
 */


/* Problem: Wird newTrans korrekt weiter- und zurueckgegeben ? **************************/


private void makeTrans(Tr trans, Path weg, TrList trclone, ConnectorList connList, TrList prevTrans, ConnectorList prevConn)
{
	TrList trclone3 = null;
	ConnectorList connClone1 = null;
	// siehe (1)
	if (trans.source instanceof Conname)
	{
	}
	// siehe (2) 
	else
	{
		if (trans.target instanceof Statename)
		{
			Path source = weg.append(((Statename) trans.source).name);
			Path target = weg.append(((Statename) trans.target).name);

			// Instanz der TransTabEntry-Klasse fuer Transitionsliste erzeugen, Transition eintragen
			// und in Transitionsliste uebernehmen.
			TransTabEntry tteDummy = new TransTabEntry(source, target, trans.label);
			// Keine Eintraege in TrList und ConnectorList ? Dann neu erzeugen
			if ((prevConn == null))
			{
				transList.addElement(new TransTab(tteDummy, prevTrans, new ConnectorList(null, null)));
			}
			// Sonst uebergebene uebernehmen
			else
			{
				transList.addElement(new TransTab(tteDummy, prevTrans, prevConn));
			}
		}
		// siehe (3)
		else
		{

			// Erstelle weitere Kopie der Transitionsliste
			trclone3 = trclone;
			/*try {
			trclone3 = (TrList)trclone.clone();
			}
			catch (CloneNotSupportedException cnse) {
			guiOutput("CLONE-ERROR in makeTrans(), Part 3, TransitionListClone");
			
			}*/
			while (trclone3 != null)
			{
				if (trclone3.head.source instanceof Conname)
				{
					// Vergleiche source und target-Objekte
					// Problem: Sind die Objekte wirklich gleich ????
					if (trans.target == trclone3.head.source)
					{
						Tr newTrans = null; // Neue Transition
						newTrans.source = trans.source; // die als Anfangspunkt den Startpunkt der
														// Anfangstransition besitzt
						newTrans.target = trclone3.head.target; // und als Endpunkt den Endpunkt der gewaehlten
																// Transition aus der Transitionsliste

						// Bestimme mit returnNewGuard und returnNewAction das zusammengefasste Label.
						Guard arg1 = returnNewGuard(trans.label.guard, trclone3.head.label.guard);
						Action arg2 = returnNewAction(trans.label.action, trclone3.head.label.action);
						newTrans.label.guard = arg1;
						newTrans.label.action = arg2;

						// Haenge dann die abgearbeitete Transition in die prevTrans-Liste (vorne ran)
						prevTrans = new TrList(trclone3.head, prevTrans);

						// Erstelle einen Clone der uebergebenen Connectorliste
						connClone1 = connList;
						/*try {
						connClone1 = (ConnectorList)connList.clone();
						}
						catch (CloneNotSupportedException cnse) {
						guiOutput("CLONE-ERROR in makeTrans(), Part 3, ConnectorListClone");
						
						}*/

						// Suche das passende Connectorobjekt zum bekannten Connectornamen aus
						// der geklonten Liste heraus und haenge das Objekt in die prevConn-Liste (vorne ran)
						while (connClone1 != null)
						{
							if (connClone1.head.name == newTrans.target)
							{
								prevConn = new ConnectorList(connClone1.head, prevConn);
								connClone1 = null;
							}
							connClone1 = connClone1.tail;
						}
						// Rufe dann makeTrans mit der neuen Transition und den angepassten prevTrans - und
						// prevConn - Listen auf
						makeTrans(newTrans, weg, trclone, connList, prevTrans, prevConn);
					}
					trclone3 = trclone3.tail;
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
	if ((e.getSource() == getButton1()) ) {
		connEtoC1(e);
	}
	if ((e.getSource() == getButton4()) ) {
		connEtoC2(e);
	}
	if ((e.getSource() == getButton2()) ) {
		connEtoC3(e);
	}
	if ((e.getSource() == getButton3()) ) {
		connEtoC4(e);
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
