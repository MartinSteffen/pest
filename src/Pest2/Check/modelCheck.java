package Check;

import Absyn.*;

public class modelCheck {

// �ffentliche Konstruktoren
	
	public modelCheck() {
		// denkbar ist eine �berladung des Konstruktors mit Schaltern vom Typ
		// "boolean" - beipielsweise: Test auf Kreisfreiheit oder Unterdr�ckung
		// der Warnungen oder ((Optimierung))

		// Properties (Schalter) werden �ber die JAVA konforme Methode des Aufrufs
		//   public boolean getXXX() {}		f�r den lesenden Zugriff und
		//   public void    setXXX() {}     f�r den schreibenden Zugriff
		// zur Verf�gung gestellt.
	}


// �ffentliche Instanzmethoden

	// �berpr�ft die komplette "Statechart"
	public boolean modelCheck(Statechart statechart) { 
		// R�ckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}
	

	// �berpr�ft alle Events, die innerhalb der "Statechart" definiert sind
	// �berfl�ssige Events k�nnten "Warnungen" sein.
	public boolean checkEvents(Statechart statechart) { 
		// R�ckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}


	// �berpr�ft den Zustand, sowie alle Subzust�nde, damit ist es m�glich nur Teile
	// der Statechart zu �berpr�fen
	public boolean checkStates(State s) {
		// R�ckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}


	// �berpr�ft innerhalb eines OR-Zustandes die Transitionen auf Korrektheit
	public boolean checkTransitions(State s) {
		// R�ckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}


	// �berpr�ft innerhalb eines OR-Zustandes die Connectors auf Korrektheit
	public boolean checkConnectors(State s) { 
		// R�ckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
          return true;
	}

	public SyntaxError getErrors() {
		// R�ckgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Fehlern
		// von Typ itemError
          return null;
	}

	public SyntaxWarning getWarnings() {
		// R�ckgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Warnungen
		// von Typ itemWarning
           return null;
	}

}

