// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                itemWarning
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          07.12.1998
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
	public modelCheck(Statechart statechart) {
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

    // Ueberpruefung starten
    checkModel(statechart);
	}

// ****************************************************************************
// �ffentliche Instanzmethoden
// ****************************************************************************

	// Ueberprueft die komplette "Statechart"
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkModel(Statechart statechart) {
    return true;
	}


	// ueberpr�ft alle Events, die innerhalb der "Statechart" definiert sind
	// ueberfl�ssige Events koennten "Warnungen" sein.
  // Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  // sind, FALSE sonst.
	public boolean checkEvents(Statechart statechart) {
    return true;
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
    return true;
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

