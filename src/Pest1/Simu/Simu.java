package simu;

/**
 * Hauptklasse Simulation
 * @author: SWTECH 26
 * @version:
 */

import java.util.*;
 
import absyn.*;
import editor.*;

public class Simu extends Object{
	Vector EventList;
	Vector InList;
	Vector EnteredList;
	Vector ExitedList;	
/**
 * Simulator Main-Klasse
 */
public Simu(Statechart Daten) {
	Statechart SDaten = Daten;
	if (SDaten != null) {
		System.out.println("Statechart angekommen !");
	}
	else {
		System.out.println("Keine Statechart angekommen ! Objekt war null");
	}
}
/**
 * Bestimmt den Wert des Guards und liefert
 * entsprechend true oder false zurueck
 */
private boolean get_guard(Guard waechter) {
	return true;
}
/**
 * This method was created in VisualAge.
 */
private void getNext() {
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