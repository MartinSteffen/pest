package simu;

import java.util.*;
import java.io.*;
 
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
 * <li> syntaxcheck
 * <li> 
 * </ul>
 *
 *
 * <DL COMPACT>
 *
 * <DT><STRONG>
 * STATUS
 * Der Simulator ist noch nicht ablauffaehig, da alle Teilstuecke eng miteinander verzahnt sind und so
 * die fehlenden Features einen Ablauf verhindern.
 * </STRONG>
 *
 * <DT><STRONG>
 * TODO.
 * <ul>
 * <li> Eingaben in den Fenstern fuer BVars und Events werden nicht ausgewertet (Deadline: 11.01.)
 * <li> Interlevel-Konflikte werden noch nicht aufgeloest (Deadline: < 11.01.)
 * <li> Ablaufsteuerung ( 1 Schritt und erster Schritt wegen vorherigem Punkt nicht korrekt) (Deadline: 11.01.)
 * </ul>
 * 
 * </STRONG>
 * <DT><STRONG>
 * BEKANNTE FEHLER.
 * <ul>
 * <li> Die clone()-Funktion der Absyn erzeugt Fehlermeldungen, die einen fehlerfreien Lauf bisher unmoeglich machen.
 * <li> Fehler bei den Fenstern
 * </ul>
 * </STRONG>
 *
 * <DT><STRONG>
 * TEMPORÄRE FEATURES.
 * <ul>
 * <li> Ueber die GUI:
 * <ul>
 * <li> Statusausgaben ueber Fehler beim Erzeugen diverser Clones
 * <li> Statusausgaben zum Ueberpruefen diverser Features (z.B. welche Objekte gehighlighted werden)
 * </ul>
 * </ul>
 * Ein Abstellen der temporaeren Features ist bisher nur durch Auskommentieren im Sourcecode moeglich, in der
 * naechsten Version wird es eine DEBUG-Einstellung geben, die ein An- und Ausstellen ermoeglichen.
 * </STRONG>
 */

public class Simu extends Object{
	private Statechart statechart = null;
	private Vector EventList;		// Vektoren, die die Listen darstellen, die wir
	private Vector InList;			// aufbauen wollen
	private Vector EnteredList; 
	private Vector ExitedList;
	private Vector possTransList;	// Transitionen, die moeglicherweise ausgefuehrt werden
	Vector transList;
	Vector toDoTransList;			// Transitionen, die keinen Nichtdeterminismus aufweisen
	private GUIInterface gui = null; 		// Referenz auf die GUI (mit NULL vorbelegen)
	Vector bvList;
	Vector seList;
	int NonDetChoice = 1;					// Modus des Beseitigungsalgorithmus fuer den Nichtdeterminismus

	
/**
 * Default - Constructor
 * Noetig, damit die Klasse UserInterface nicht herummeckert (Implizit
 * aufgerufener Constructor) 
 */
protected Simu() {
}
/**
 * Simulator: 3) gewuenschter, neuer Main-Konstruktor<p>
 * Uebergabe einer Statechart, eines Editorobjektes und einer GUI-
 * schnittstelle, um (erstmal hauptsaechlich zu Debuggingzwecken) Ausgaben
 * im GUI-Fenster machen zu koennen; 
 * Erzeugt auch alle noetigen Fenster um Ein- und Ausgaben des Benutzers
 * registrieren zu koennen; 
 */
public Simu(Statechart Daten, Editor eEdit, GUIInterface igui) {
	gui = igui;
	Statechart SDaten = null;
	Path weg;
	statechart = Daten;
	System.out.println("Simulator aufgerufen !!");
	try {
		SDaten = ((Statechart)(Daten.clone()));
	} 
	catch (CloneNotSupportedException cnse) {
	}
	if (SDaten != null) {
		guiOutput("Simu: Statechart angekommen !");
		try {
			UserInterface aUserInterface;
			aUserInterface = new UserInterface();
		} catch (Throwable exception) {
			System.err.println("Exception occurred in main() of java.lang.Object");
			exception.printStackTrace(System.out);
		}
		bvList = makeTab(SDaten.bvars);
		seList = makeTab(SDaten.events);
		//transList = makeTab(SDaten.state);
		weg = new Path(null,null);
		makeTab(SDaten.state, weg);
		try {
			ListFrame LF1 = new ListFrame(bvList, true);
			ListFrame LF2 = new ListFrame(seList, false);
		} catch (Throwable exception) {
			System.err.println("Exception occurred in main() of java.lang.Object");
			exception.printStackTrace(System.out);
		}
	} else {
		guiOutput("Simu: Keine Statechart angekommen ! Objekt war null");
		System.out.println("Keine Statechart angekommen !");
	}
}
/**
 * New method, created by HH on INTREPID
 * @return absyn.Aseq
 * @param arg1 absyn.Action
 * @param arg2 absyn.Aseq
 */
private Aseq append(ActionBlock arg2, Action arg1) {
	if(arg2.aseq.tail != null)
	    {
		return new Aseq(arg2.aseq.head, append(new ActionBlock(arg2.aseq.tail), arg1));
	    } 
	else 
	    return new Aseq(arg2.aseq.head, new Aseq(arg1, null));

}
/**
 * New method, created by HH on INTREPID
 * @param absyn.State
 * @param absyn.Path
 * @return absyn.State
 *
 * Bestimmt zum angegebenen Pfad rekursiv den passenden State in der Statechart
 */
private State find_state(State x, Path y) {
	State z = null;
	And_State z1 = null;
	Or_State z2 = null;
	
	try {
		z = (State)x.clone();
	}
	catch (CloneNotSupportedException cnse) {
		guiOutput("CLONE-Fehler in find_state()");
	}
	
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
 * Bestimmt den Wert des Guards und liefert
 * entsprechend true oder false zurueck.
 * @param absyn.Guard
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
 * Interface-Methode zur GUI.
 * Eigentlich mehr zu Debugging-Zwecken. Wird von der GUI als Schnittstelle zur Verfuegung
 * gestellt, um Ausgaben im GUI-Fenster zu machen.
 * @param SimuMessage java.lang.String
 */
protected void guiOutput(String SimuMessage) {
	gui.userMessage(SimuMessage);
}
/**
 * New method, created by HH on INTREPID
 * @param pfad absyn.Path
 *
 * Nimmt die Eintraege in die EnteredListe rekursiv vor.
 */
private void inMethod(Path pfad, State state) {
	Path tempPfad = null;
	State tempState = null;
	Statechart tempStatechart = null;
	
	try {
		tempPfad = (Path)pfad.clone();
		tempStatechart = (Statechart)statechart.clone();
	}
	catch (CloneNotSupportedException cnse) {
		guiOutput("CLONE-ERROR in inMethod()");
	}

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
		try {
			
			temp1 = (StateList)((And_State)tempState).substates.clone();
		}
		catch (CloneNotSupportedException cnse) {
			guiOutput("CLONE-ERROR in inMethod(), And_State");
		}
		
		while (temp1 != null) {
			Path pfadclone = null;
			try {
				pfadclone = (Path)pfad.clone();
			}
			catch (CloneNotSupportedException cnse) {
				guiOutput("CLONE-ERROR in inMethod(), And_State, pfadclone");
			}
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
 * New method, created by HH on INTREPID
 */
private void killLevelConflicts() {
	
}
/**
 * New method, created by HH on INTREPID
 *
 * Methode beseitigt Nichtdeterminismus und baut in toDoTransList einen Vector auf, 
 * der frei von Nichtdeterminismus ist.
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
 * Fuehrt eine Action aus
 * @param aktion Absyn.Action
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
		Aseq temp2 = null;
		try {
			temp2 = (Aseq)(temp1.clone());
		}
		catch (CloneNotSupportedException cnse) {
		}
		while (temp2 != null) {
			make_action(temp2.head);
			temp2 = temp2.tail;
		}
	}
	
	return false;
}
/**
 * This method was created in VisualAge.
 * @param n int
 */
protected boolean makeNStep(int n) {
	int counter = 0;
	while (counter < n) {
		makeStep();
	}
	return true;
}
/**
 * New method, created by HH on INTREPID
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
 * Diese Methode macht genau einen Simulationsschritt
 */
  
protected boolean makeStep() {
	makePossTransList();
	makeRealTransList();
	killNonDet();
	ExitedList.removeAllElements();	// Exited-Liste zuruecksetzen, denn in der folgenden Methode wird sie gefuellt
	killLevelConflicts();
	step();
	return true;
}
/**
 * Erstellt die noetigen Tabellen
 * hier: Bvar-Liste
 */
private Vector makeTab(BvarList data) {
	Vector result=null;
	if (data != null) {
		while (data != null) {
			BvarTab newElement = new BvarTab(data.head, false); 
			result.addElement(newElement);
			data = data.tail;
		}
	};
	return result;
}
/**
 * Erstellt die noetigen Tabellen
 * hier: Event-Liste
 */
private Vector makeTab(SEventList data) {
	Vector result=null;
	if (data != null) {
		while (data != null) {
			SEventTab newElement = new SEventTab(data.head, false); 
			result.addElement(newElement);
			data = data.tail;
		}
	};
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
 */
private void makeTab(State data, Path weg) {
	Vector result=null;
	TrList trclone1 = null;
	TrList trclone2 = null;
	ConnectorList connList1 = null;
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
			try {
				trclone1 = (TrList)(((Or_State)data).trs.clone());
				trclone2 = (TrList)(((Or_State)data).trs.clone());
			}
			catch (CloneNotSupportedException cnse) {
				guiOutput("CLONE-ERROR in makeTab(State, Path), Kopien der Tansitionslisten");

			}

			try {
				connList1 = (ConnectorList)(((Or_State)data).connectors.clone());
			}
			catch (CloneNotSupportedException cnse) {
				guiOutput("CLONE-ERROR in makeTab(State, Path), Kopie der ConnectorList");

			}

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
 */
private void makeTrans(Tr trans, Path weg, TrList trclone, 
	ConnectorList connList, TrList prevTrans, ConnectorList prevConn) {
	
	TrList trclone3 = null;		
	ConnectorList connClone1 = null;
	// siehe (1)
	if (trans.source instanceof Conname) {
	}
	// siehe (2) 
	else {
		if (trans.target instanceof Statename) {
			Path source = weg.append(((Statename)trans.source).name);
			Path target = weg.append(((Statename)trans.target).name);
			
		// Instanz der TransTabEntry-Klasse fuer Transitionsliste erzeugen, Transition eintragen
		// und in Transitionsliste uebernehmen.
			TransTabEntry tteDummy = new TransTabEntry(source,target,trans.label);
			// Keine Eintraege in TrList und ConnectorList ? Dann neu erzeugen
			if ((prevConn == null)) {
				transList.addElement(new TransTab(tteDummy, prevTrans, new ConnectorList(null, null)));
			}
			// Sonst uebergebene uebernehmen
			else {
				transList.addElement(new TransTab(tteDummy, prevTrans, prevConn));
			}

			
		}
		// siehe (3)
		else {
			
			// Erstelle weitere Kopie der Transitionsliste
			try {
				trclone3 = (TrList)trclone.clone();
			}
			catch (CloneNotSupportedException cnse) {
				guiOutput("CLONE-ERROR in makeTrans(), Part 3, TransitionListClone");

			}
				while (trclone3 != null) {
					if (trclone3.head.source instanceof Conname) {
						// Vergleiche source und target-Objekte
						// Problem: Sind die Objekte wirklich gleich ????
						if (trans.target == trclone3.head.source) {
							Tr newTrans = null;								// Neue Transition
							newTrans.source = trans.source;					// die als Anfangspunkt den Startpunkt der
																			// Anfangstransition besitzt
							newTrans.target = trclone3.head.target;			// und als Endpunkt den Endpunkt der gewaehlten
																			// Transition aus der Transitionsliste
							
							// Bestimme mit returnNewGuard und returnNewAction das zusammengefasste Label.
							Guard arg1 = returnNewGuard(trans.label.guard, trclone3.head.label.guard);
							Action arg2 = returnNewAction(trans.label.action, trclone3.head.label.action);
							newTrans.label.guard = arg1;
							newTrans.label.action = arg2;

							// Haenge dann die abgearbeitete Transition in die prevTrans-Liste (vorne ran)
							prevTrans = new TrList(trclone3.head, prevTrans);

							// Erstelle einen Clone der uebergebenen Connectorliste 
							try {
								connClone1 = (ConnectorList)connList.clone();
							}
							catch (CloneNotSupportedException cnse) {
								guiOutput("CLONE-ERROR in makeTrans(), Part 3, ConnectorListClone");

							}

							// Suche das passende Connectorobjekt zum bekannten Connectornamen aus
							// der geklonten Liste heraus und haenge das Objekt in die prevConn-Liste (vorne ran)
							while (connClone1 != null) {
								if (connClone1.head.name == newTrans.target){
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
 * New method, created by HH on INTREPID
 * @return absyn.Action
 * @param arg1 absyn.Action
 * @param arg2 absyn.Action
 */
private ActionBlock returnNewAction(Action arg1, Action arg2) {
	ActionBlock result = null;
	if (arg1 instanceof ActionEmpty) {
		result = new ActionBlock(new Aseq(arg2, null));   //default
	}
	else {
		if (arg2 instanceof ActionEmpty) {
			result = new ActionBlock(new Aseq(arg1, null));
		}
		else {
			if (arg1 instanceof ActionBlock) {
				result = new ActionBlock(append((ActionBlock)arg1, arg2));
			}
			else {
			}	
		}
	} 
	return result;
}
/**
 * New method, created by HH on INTREPID
 * @return absyn.Guard
 * @param arg1 absyn.Guard
 * @param arg2 absyn.Guard
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
			result = (new GuardCompg(new Compguard(0, arg1, arg2)));
		}
	} 
	return result;
}
/**
 * New method, created by HH on INTREPID
 * @return simu.TransTab
 * @param arg1 java.util.Vector
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
 * New method, created by HH on INTREPID
 */
private void step() {
}
}