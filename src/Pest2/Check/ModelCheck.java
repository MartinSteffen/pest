// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                ModelCheck
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          20.02.1999
//
// ****************************************************************************

package check;

import absyn.*;         // abstrakte Syntax
import gui.*;           // GUI Interface
import editor.*;        // Editor zwecks Highlight Funktionalitaet


/**
*<h1 align="center">Syntax Check für Statecharts, PEST2</h1>
*
*<h2>Aufruf</h2>
*
*<table border="0">
*    <tr>
*       <td>Initialisierung:</td>
*        <td><font face="Courier">ModelCheck check = new
*        ModelCheck(GUIInterface, Editor);</font></td>
*    </tr>
*    <tr>
*        <td>Ausführung:</td>
*        <td><font face="Courier">boolean ok =
*        check.checkModel(Statechart);</font></td>
*    </tr>
*</table>
*
*<h2>Anforderungen</h2>
*
*<p>Es werden keinerlei Anforderungen an die übergebene
*Statechart gestellt; sobald alle Projektteilnehmer die
*programmiertechnische Kreisfreiheit einer Statechart
*sicherstellen k÷nnen, werden wir unseren eigenen Test auf eben
*diese Kreisfreiheit entfernen.</p>
*
*<p>Was wir unbedingt brauchen sind ein brauchbarer funktionaler
*Editor, um Statecharts zum Testen zu erzeugen.</p>
*
*<h2>Status (20.02.1999)</h2>
*
*<p>Implementierte Feature</p>
*
*<ul>
*    <li>Bool'scher Variablen Test (beta)</li>
*    <li>Event Test (beta)</li>
*    <li>Transistions Test (beta)</li>
*    <li>Connectoren Test (beta)</li>
*    <li>Test eines einzelnen States (beta)</li>
*</ul>
*
*<h2>Todo</h2>
*
*<p>Im Vordergrund diese Woche stehen bei uns die Tests und die
*Implementierung der Klasse Crossreference</p>
*
*<h2>Kontakt</h2>
*
*<table border="0">
*    <tr>
*        <td valign="top">Autoren:</td>
*        <td><a href="mailto:u1579@bwl.uni-kiel.de">Tobias Kunz</a><br>
*        <a href="mailto:tc@bwl.uni-kiel.de">Mario Thies</a> </td>
*    </tr>
*</table>
*
*<h2>Version</h2>
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

  public ModelCheck(GUIInterface gui, Editor edit) {
    this(gui);
  }

  public ModelCheck(GUIInterface gui, Editor edit, CheckConfig config) {
    this(gui);
  }

// ****************************************************************************
// private Methoden
// ****************************************************************************

// ****************************************************************************
// ÷ffentliche Instanzmethoden
// ****************************************************************************

	/** Ueberprueft die komplette "Statechart"
  *   Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  *   sind, FALSE sonst.
  */
	public boolean checkModel(Statechart statechart) {

    boolean ok = true;

    // fuer Testzwecke!
    // Example example = new Example();
    // statechart = example.getExample();

    if ((statechart == null) && (statechart.state == null))
      errors.addError(new ItemError(0, "Uebergebene Statechart ist leer",""));

    // auf Kreisfreiheit, d.h. Programmierfehler pruefen.
    CheckCircle checkCircle = new CheckCircle(statechart);
    ok = checkCircle.check();

    if (!ok) {
      // Fataler Fehler! => Ausgabe!
      if (gui != null) {
        int result = gui.OkDialog("Fataler Fehler","Statechart enthaelt einen Kreis");
      } else System.out.println("Fataler Fehler: Statechart enthaelt einen Kreis");

    } else {
      CheckEvents checkEvents = new CheckEvents(statechart, errors, warnings);
      CheckBVars  checkBVars  = new CheckBVars(statechart, errors, warnings);
      CheckConnectors checkConnectors = new CheckConnectors(statechart, errors, warnings);
      CheckDupes checkDupes = new CheckDupes(statechart, errors, warnings);

      // zu Debugzwecken
      if (!ok) gui.userMessage("Test auf inkonsistente Statenamen ist fehlgeschlagen");

      CheckStates cs = new CheckStates(statechart, errors, warnings);

      ok = cs.check() && ok;
      ok = checkEvents.check() && ok;
      ok = checkBVars.check() && ok;
      ok = checkConnectors.check() && ok;

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
    // Total
    gui.userMessage("Fehler: "+Integer.toString(errors.count()) +
                    ", Warnungen: "+Integer.toString(warnings.count()));
    return ok;
	}

	/** ueberprüft alle Events, die innerhalb der "Statechart" definiert sind
	*   ueberflüssige Events koennten "Warnungen" sein.
  *   Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  *   sind, FALSE sonst.
  */
	public boolean checkEvents(Statechart statechart) {
    boolean ok = true;

    CheckEvents checkEvents = new CheckEvents(statechart, errors, warnings);
    ok = checkEvents.check();

    return ok;
  }

  public boolean checkBVars(Statechart statechart) {
    boolean ok = true;

    CheckBVars  checkBVars  = new CheckBVars(statechart, errors, warnings);
    ok = checkBVars.check();

    return ok;
  }

	/** ueberprüft den Zustand, sowie alle Subzustaende, damit ist es moeglich
  *   nur Teile der Statechart zu ueberprüfen
  *   Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  *   sind, FALSE sonst.
  */
  /*
	public boolean checkStates(State s) {
    return true;
	}
  */


	/** ueberprüft innerhalb eines OR-Zustandes die Transitionen auf Korrektheit
  *   Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  *   sind, FALSE sonst.
  */
/*
	public boolean checkTransitions(State s) {
    boolean ok = true;

    CheckTransitions checkTrans = new CheckTransitions(s);
    ok = checkTrans.check();

    return ok;
	}
*/

	/** ueberprüft innerhalb eines OR-Zustandes die Connectors auf Korrektheit
  *   Rueckgabe der Methode ist TRUE, wenn keine Fehler oder Warnungen vorhanden
  *   sind, FALSE sonst.
  */
/*
	public boolean checkConnectors(Statechart statechart, State s) {
    boolean ok = true;

    CheckConnectors checkConn = new CheckConnectors(statechart, errors, warnings);
    // ok = checkConn.check(s);
    ok = checkConn.check(); // geaendert, Tobias 10.02.1999

    return ok;
	}
*/

  /** Rueckgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Fehlern
  *   von Typ itemError
  */
	public SyntaxError getErrors() {
    return errors;
	}

  /** Rückgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Warnungen
  *   von Typ itemWarning
  */
	public SyntaxWarning getWarnings() {
    return warnings;
	}

}

