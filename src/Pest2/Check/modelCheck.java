// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                itemWarning
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          13.12.1998
//
// ****************************************************************************

package Check;

import Absyn.*;         // abstrakte Syntax

public class modelCheck {

private SyntaxWarning warnings;
private SyntaxError   errors;

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  // Konstruktor
	public modelCheck() {
		// denkbar ist eine Überladung des Konstruktors mit Schaltern vom Typ
		// "boolean" - beipielsweise: Test auf Kreisfreiheit oder Unterdrückung
		// der Warnungen oder ((Optimierung))

		// Properties (Schalter) werden über die JAVA konforme Methode des Aufrufs
		//   public boolean getXXX() {}		für den lesenden Zugriff und
		//   public void    setXXX() {}     für den schreibenden Zugriff
		// zur Verfügung gestellt.

    // initialisieren
    warnings = new SyntaxWarning();
    errors   = new SyntaxError();
	}

  // Konstruktor
	public modelCheck(Statechart statechart) {
		// denkbar ist eine Überladung des Konstruktors mit Schaltern vom Typ
		// "boolean" - beipielsweise: Test auf Kreisfreiheit oder Unterdrückung
		// der Warnungen oder ((Optimierung))

		// Properties (Schalter) werden über die JAVA konforme Methode des Aufrufs
		//   public boolean getXXX() {}		für den lesenden Zugriff und
		//   public void    setXXX() {}     für den schreibenden Zugriff
		// zur Verfügung gestellt.

    // initialisieren
    warnings = new SyntaxWarning();
    errors   = new SyntaxError();

    // Ueberpruefung starten
    checkModel(statechart);
	}

// ****************************************************************************
// private Methoden
// ****************************************************************************

// ****************************************************************************
// öffentliche Instanzmethoden
// ****************************************************************************

	// Ueberprueft die komplette "Statechart"
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkModel(Statechart statechart) {
    boolean ok = true;
    CheckEvents checkEvents = new CheckEvents(statechart);
    CheckBVars  checkBVars  = new CheckBVars(statechart);

    ok = ok && checkEvents.check();
    ok = ok && checkBVars.check();

    // ueberpruefen aller Transitionen



    return ok;
	}

	// ueberprüft alle Events, die innerhalb der "Statechart" definiert sind
	// ueberflüssige Events koennten "Warnungen" sein.
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkEvents(Statechart statechart) {
    boolean ok = true;

    CheckEvents checkEvents = new CheckEvents(statechart);
    ok = checkEvents.check();

    return ok;
  }

  public boolean checkBVars(Statechart statechart) {
    boolean ok = true;

    CheckBVars  checkBVars  = new CheckBVars(statechart);
    ok = checkBVars.check();

    return ok;
  }

	// ueberprüft den Zustand, sowie alle Subzustaende, damit ist es moeglich
  // nur Teile der Statechart zu ueberprüfen
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkStates(State s) {
    return true;
	}


	// ueberprüft innerhalb eines OR-Zustandes die Transitionen auf Korrektheit
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkTransitions(State s) {
    boolean ok = true;

    CheckTransitions checkTrans = new CheckTransitions(s);
    ok = checkTrans.check();

    return ok;
	}


	// ueberprüft innerhalb eines OR-Zustandes die Connectors auf Korrektheit
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkConnectors(State s) {
    return true;
	}

  // Rueckgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Fehlern
  // von Typ itemError
	public SyntaxError getErrors() {
    return errors;
	}

  // Rückgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Warnungen
  // von Typ itemWarning
	public SyntaxWarning getWarnings() {
    return warnings;
	}

}

