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
import GUI.*;           // GUI Interface

public class modelCheck {

private SyntaxWarning warnings;
private SyntaxError   errors;
private GUIInterface  gui;

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  // Konstruktor
	public modelCheck() {
		// denkbar ist eine �berladung des Konstruktors mit Schaltern vom Typ
		// "boolean" - beipielsweise: Test auf Kreisfreiheit oder Unterdr�ckung
		// der Warnungen oder ((Optimierung))

		// Properties (Schalter) werden �ber die JAVA konforme Methode des Aufrufs
		//   public boolean getXXX() {}		f�r den lesenden Zugriff und
		//   public void    setXXX() {}     f�r den schreibenden Zugriff
		// zur Verf�gung gestellt.

    // initialisieren
    warnings = new SyntaxWarning();
    errors   = new SyntaxError();
	}

  // Konstruktor
  public modelCheck(GUIInterface gui) {
     warnings = new SyntaxWarning();
     errors   = new SyntaxError();
     this.gui = gui;
  }

// ****************************************************************************
// private Methoden
// ****************************************************************************

// ****************************************************************************
// �ffentliche Instanzmethoden
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

	// ueberpr�ft alle Events, die innerhalb der "Statechart" definiert sind
	// ueberfl�ssige Events koennten "Warnungen" sein.
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

	// ueberpr�ft den Zustand, sowie alle Subzustaende, damit ist es moeglich
  // nur Teile der Statechart zu ueberpr�fen
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkStates(State s) {
    return true;
	}


	// ueberpr�ft innerhalb eines OR-Zustandes die Transitionen auf Korrektheit
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkTransitions(State s) {
    boolean ok = true;

    CheckTransitions checkTrans = new CheckTransitions(s);
    ok = checkTrans.check();

    return ok;
	}


	// ueberpr�ft innerhalb eines OR-Zustandes die Connectors auf Korrektheit
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

  // R�ckgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Warnungen
  // von Typ itemWarning
	public SyntaxWarning getWarnings() {
    return warnings;
	}

}

