package Check;

import Absyn.*;

public class modelCheck {

// öffentliche Konstruktoren
	
	public modelCheck() {
		// denkbar ist eine Überladung des Konstruktors mit Schaltern vom Typ
		// "boolean" - beipielsweise: Test auf Kreisfreiheit oder Unterdrückung
		// der Warnungen oder ((Optimierung))

		// Properties (Schalter) werden über die JAVA konforme Methode des Aufrufs
		//   public boolean getXXX() {}		für den lesenden Zugriff und
		//   public void    setXXX() {}     für den schreibenden Zugriff
		// zur Verfügung gestellt.
	}


// öffentliche Instanzmethoden

	// überprüft die komplette "Statechart"
	public boolean modelCheck(Statechart statechart) { 
		// Rückgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}
	

	// überprüft alle Events, die innerhalb der "Statechart" definiert sind
	// überflüssige Events könnten "Warnungen" sein.
	public boolean checkEvents(Statechart statechart) { 
		// Rückgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}


	// überprüft den Zustand, sowie alle Subzustände, damit ist es möglich nur Teile
	// der Statechart zu überprüfen
	public boolean checkStates(State s) {
		// Rückgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}


	// überprüft innerhalb eines OR-Zustandes die Transitionen auf Korrektheit
	public boolean checkTransitions(State s) {
		// Rückgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
           return true;
	}


	// überprüft innerhalb eines OR-Zustandes die Connectors auf Korrektheit
	public boolean checkConnectors(State s) { 
		// Rückgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
		// sind, FALSE sonst.
          return true;
	}

	public SyntaxError getErrors() {
		// Rückgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Fehlern
		// von Typ itemError
          return null;
	}

	public SyntaxWarning getWarnings() {
		// Rückgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Warnungen
		// von Typ itemWarning
           return null;
	}

}

