// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                ModelCheck
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          21.12.1998
//
// ****************************************************************************

package check;

import absyn.*;         // abstrakte Syntax
import gui.*;           // GUI Interface


/**
 * <h1>Syntax Check f�r Statecharts</h1>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li>Initialisierung:   ModelCheck mc = new ModelCheck(GUI_Referenz)
 * <li>Aufruf des Checks: boolean = mc.checkModel(Statechart)
 * </ol>
 * <h2>Forderungen an die an den Check �bergebene Statechart:</h2>
 * <ul>
 * <li>KEINE
 * </ul>
 * <h2>Garantien nach der Beendigung des Checks:</h2>
 * <ul>
 * <li>Der Check ver�ndert die an ihn �bergebene Statechart <b>nicht</b>.
 * <li>Der Check liefert nur dann <b>true</b> zur�ck, wenn die Statechart
 * keine Fehler mehr enth�lt, die den Simulator oder den CodeGenerator zu
 * zu falschen Ergebnissen f�hren w�rde.
 * </ul>
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
 */
public class ModelCheck {

private SyntaxWarning warnings;
private SyntaxError   errors;
private GUIInterface  gui;
private boolean       OutputToGUI = false;

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  // Konstruktor
  public ModelCheck() {
    // initialisieren
    warnings = new SyntaxWarning();
    errors   = new SyntaxError();
    OutputToGUI = false;
	}

  // Konstruktor
  public ModelCheck(GUIInterface gui) {
     this();
     this.gui = gui;
     OutputToGUI = true;
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

    // auf Kreisfreiheit, d.h. Programmierfehler pruefen.
    CheckCircle checkCircle = new CheckCircle(statechart);
    ok = checkCircle.check();

    if (!ok) {
      // Fataler Fehler! => Ausgabe!
      if (gui != null) {
        int result = gui.OkDialog("Fataler Fehler","Statechart enthaelt einen Kreis");
      } else System.out.println("Fataler Fehler: Statechart enthaelt einen Kreis");

    } else {
      CheckEvents checkEvents = new CheckEvents(statechart);
      CheckBVars  checkBVars  = new CheckBVars(statechart);
      CheckConnectors checkConnectors = new CheckConnectors(statechart);

      ok = ok && checkEvents.check();
      ok = ok && checkBVars.check();
      ok = ok && checkConnectors.check();

      // ueberpruefen eine Zustandes, darunter fallen
      //   a) Transitionen
      //   b) Connectoren
      //   c) der State selber

      // Fehlerausgabe, falls "gui" zugewiesen ist
      if (gui != null) {
        for (int i = 0; i < warnings.count(); i++) {
          ItemWarning w = warnings.warningAt(i);
          gui.userMessage(w.toString());
        }
        for (int i = 0; i < errors.count(); i++) {
          ItemError e = errors.errorAt(i);
          gui.userMessage(e.toString());
        }
      } else { // Ausgabe auf Standard-Out
        for (int i = 0; i < warnings.count(); i++) {
          ItemWarning w = warnings.warningAt(i);
          System.out.println(w.toString());
        }
        for (int i = 0; i < errors.count(); i++) {
          ItemError e = errors.errorAt(i);
          System.out.println(e.toString());
        }
      }
    }
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
	public boolean checkConnectors(Statechart statechart, State s) {
    boolean ok = true;

    CheckConnectors checkConn = new CheckConnectors(statechart);
    ok = checkConn.check(s);

    return ok;
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

