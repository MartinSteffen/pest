package simu;

/**
 * Hauptklasse Simulation
 * @author: SWTECH 26
 * @version:
 */

/**
 * <STRONG> Garantie. </STRONG> Wir garantieren, dass die von unseren
 * Module erzeigten Statecharts folgende Eingenschaften haben:
 *
 * <ul>
 * <li> Wir erzeugen keine Statecharts.
 * </ul>
 *
 * Damit ist es nicht notwendig, folgender Checks an unsere Statecharts
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
 */

import java.util.*;
import java.io.*;
 
import absyn.*;
import editor.*;
import gui.*;

public class Simu extends Object{
	private Vector EventList;		// Vektoren, die die Listen darstellen, die wir
	private Vector InList;			// aufbauen wollen
	private Vector EnteredList;
	private Vector ExitedList;
	private GUIInterface gui = null; // Referenz auf die GUI (mit NULL vorbelegen)
	
/**
 * Simulator Main-Klasse
 */
public Simu(Statechart Daten) {
	Statechart SDaten = Daten;
	if (SDaten != null) {
		System.out.println("Simu: Statechart angekommen !");
	}
	else {
		System.out.println("Simu: Keine Statechart angekommen ! Objekt war null");
	}
}
/**
 * Simulator Main-Klasse
 */
public Simu(Statechart Daten, Editor eEdit) {
	Statechart SDaten = Daten;
	if (SDaten != null) {
		System.out.println("Simu: Statechart angekommen !");
	}
	else {
		System.out.println("Simu: Keine Statechart angekommen ! Objekt war null");
	}
}
/**
 * Simulator Main-Klasse
 */
public Simu(Statechart Daten, Editor eEdit, GUIInterface igui) {
	gui = igui;
	Statechart SDaten = Daten;
	if (SDaten != null) {
		guiOutput("Simu: Statechart angekommen !");
	}
	else {
		guiOutput("Simu: Keine Statechart angekommen ! Objekt war null");
	}
}
/**
 * Simulator Main-Klasse
 */
public Simu(Statechart Daten, GUIInterface igui) {
	gui = igui;
	Statechart SDaten = Daten;
	if (SDaten != null) {
		guiOutput("Simu: Statechart angekommen !");
	}
	else {
		guiOutput("Simu: Keine Statechart angekommen ! Objekt war null");
	}
}
/**
 * Bestimmt den Wert des Guards und liefert
 * entsprechend true oder false zurueck
 */
private boolean get_guard(Guard waechter) {
	boolean goal = false;
	
	return goal;
}
/**
 *
 */
private void getNext() {
}
/**
 * Interface-Methode zur GUI.
 * Eigentlich mehr zu Debugging-Zwecken. Wird der GUI als Schnittstelle zur Verfuegung
 * gestellt, um Ausgaben im GUI-Fenster zu machen.
 * @param SimuMessage java.lang.String
 */
private void guiOutput(String SimuMessage) {
	gui.userMessage(SimuMessage);
}
/**
 * Fuehrt eine Action aus
 * @param aktion Absyn.Action
 */
private boolean make_action(Action aktion) {
	return false;
}
/**
 * Erstellt die noetigen Tabellen
 */
private void makeTab(Statechart Daten) {
}
}
