package check;

import java.util.*;
import java.io.*;

import absyn.*;
import gui.*;

/**
 * <h1>Syntax Check für Statecharts</h1>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li>Initialisierung:   ModelCheck mc = new ModelCheck(GUI_Referenz)
 * <li>Aufruf des Checks: boolean = mc.checkModel(Statechart)
 * </ol>
 * <h2>Forderungen an die an den Check übergebene Statechart:</h2>
 * <ul>
 * <li>KEINE
 * </ul>
 * <h2>Garantien nach der Beendigung des Checks:</h2>
 * <ul>
 * <li>Der Check verändert die an ihn übergebene Statechart <b>nicht</b>.
 * <li>Der Check liefert nur dann <b>true</b> zurück, wenn die Statechart
 * keine Fehler mehr enthält, die den Simulator oder den CodeGenerator zu
 * zu falschen Ergebnissen führen würde.
 * </ul>
 * <br>
 * <a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a><br>
 * <br>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: ModelCheck.java,v 1.5 1998-12-18 07:23:29 swtech11 Exp $
 */
public class ModelCheck {
  private ModelCheckMsg mcm; // Object, um die Fehler und Warnungen zu speichern
  private boolean warning;   // auch Warnungen ausgeben
  private boolean outputGUI; // Meldungen auf die GUI ausgeben
  private boolean donePI;    // Test auf doppelte Referenzierung ausgeführt ?
  private boolean b_ce,b_cb,b_ct, b_cs;
  private boolean NoFatalError;  // existiert kein fataler Fehhler (doppelte Referenzierung)
  private GUIInterface gui = null; // Referenz auf die GUI

/**
 * Der Constructor des Syntax Checkers.
 * Es wird keine Ausgabe auf die GUI ermöglicht.
 */
  public ModelCheck() {
    mcm = new ModelCheckMsg();
    outputGUI = false;
    warning = true;
    donePI = false;
    b_ce = false;
    b_cb = false;
    b_ct = false;
    b_cs = false;
    NoFatalError = true;
  }

/** 
 * Der Constructor des Syntax Checkers.
 * Es werden, wenn man die Einstellungen nicht ändert, Meldungen von Fehlern und Warnungen auf der GUI ausgegeben.
 * @param _gui Referenz auf die GUI
 * @see        #setOutputGUI(boolean)
 */
  public ModelCheck(GUIInterface _gui) {
    this();
    gui = _gui;
    outputGUI = true;
  }

/**
 * Führt den gesamten Syntax Check durch.
 * Die Methode gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 * @param sc  die zu checkende Statechart
 */
  public boolean checkModel(Statechart sc) {
    boolean m = (checkEvents(sc) & checkStates(sc) &
                   checkTransitions(sc) & checkBVars(sc));
    if (outputGUI==true) { outputToGUI(); }
    return m;
  };

/**
 * Checkt die Events.
 * Die Methode gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 * @param sc  die zu checkende Statechart
 */
  public boolean checkEvents(Statechart sc) {
    boolean pi = false;
    if (donePI == false ) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      TestEvents te = new TestEvents(sc, mcm);
      b_ce = te.check();
      if (outputGUI==true & b_ce==true) {
        gui.userMessage("Check: Keine Fehler während der Überprüfung der Events gefunden.");
      }
    }
    return b_ce;
  }

/**
 * Checkt die States.
 * Die Methode gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 * @param sc  die zu checkende Statechart
 */
  public boolean checkStates(Statechart sc) {
    boolean pi = false;
    if (donePI == false) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      TestStates ts = new TestStates(sc, mcm);
      b_cs = ts.check();
      if (outputGUI==true & b_cs==true) {
        gui.userMessage("Check: Keine Fehler während der Überprüfung der States gefunden.");
      }
    }  
    return b_cs;
  }

/**
 * Checkt die Transitionen.
 * Die Methode gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 * @param sc  die zu checkende Statechart
 */
  public boolean checkTransitions(Statechart sc) {
    boolean pi = false;
    if (donePI == false) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      TestTransitions tt = new TestTransitions(sc,mcm);
      b_ct = tt.check();
      if (outputGUI==true & b_ct==true) {
        gui.userMessage("Check: Keine Fehler während der Überprüfung der Transitions gefunden.");
      }
    }
    return b_ct;
  }

/**
 * Checkt die booleschen Variablen.
 * Die Methode gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 * @param sc  die zu checkende Statechart
 */
  public boolean checkBVars(Statechart sc) {
    boolean pi = false;
    if (donePI == false) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      TestBVars tb = new TestBVars(sc, mcm);
      b_cb = tb.check();
      if (outputGUI==true & b_cb==true) {
        gui.userMessage("Check: Keine Fehler während der Überprüfung der Booleschen Variablen gefunden.");
      }
    }
    return b_cb;
  }

/**
 * Checkt auf schwerwiegende Fehler der Programmierer.
 * Bei diesem Check geht es darum, Fehler zu finden, die den Syntax Checker zu falschen Ergebnissen führen würde
 * (z.B. Zyklen in den Statelisten oder States).
 * <br>Diese Methode wird <b>einmalig</b> aufgerufen, wenn ein oder mehrere Tests ausgeführt werden.
 * Die Methode gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 * @param sc  die zu checkende Statechart
 */
  public boolean checkPI(Statechart sc) {
    TestPI tpi = new TestPI(sc, mcm);
    NoFatalError=tpi.check();
    donePI = true;
    if (outputGUI==true & NoFatalError==false) {
      int j = gui.OkDialog("Fataler Fehler","Der beschriebene Fehler führt zum Abbruch des Syntax Checks !");
    }
    return NoFatalError;
  }


/**
 * Gibt alle Meldungen des Syntax Checkers in eine Datei aus.
 * Die Methode gibt alle Fehler- und Warnungmeldungen in einer Textdatei im ASCII-Format aus.
 * @param _name  Name der Datei, in die gespeichert werden soll
 */
  public void outputToFile(String _name) {
    try {
      FileOutputStream fos = new FileOutputStream(_name);
      PrintWriter out = new PrintWriter(fos);
      out.println("Meldungen des Syntax Check:");
      out.println();
      out.println("Fehlermeldungen ( Anzahl: " + getErrorNumber() +  " ):");
      if (getErrorNumber()>0) {
        for (int i=1;(i<=getErrorNumber());i++) {
          out.println( getError(i) ); } }
      out.println();
      out.println("Warnmeldungen ( Anzahl: " + getWarningNumber() +  " ):");
      if (getWarningNumber()>0) {
        for (int i=1;(i<=getWarningNumber());i++) {
          out.println( getWarning(i) ); } }
      out.flush();
      out.close();
    }
    catch (Exception e) { System.out.println(e); }
  }


  void outputToGUI() {
    if (getErrorNumber()>0) {
      gui.userMessage("Check	: Fehlermeldungen ( Anzahl: " + getErrorNumber() +  " ):");
      for (int i=1;(i<=getErrorNumber());i++) {
            gui.userMessage("Check	: "+getError(i) ); } }
    if (getWarningNumber()>0) {
      gui.userMessage("Check	: Warnmeldungen ( Anzahl: " + getWarningNumber() +  " ):");
      for (int i=1;(i<=getWarningNumber());i++) {
        gui.userMessage("Check	: "+getWarning(i) ); } }
  }

/**
 *
 */
  public void setWarning(boolean _w) { warning = _w;}


/**
 * Legt fest, ob die Fehler und Warnungen auf der GUI erscheinen sollen.
 * Diese Methode funktioniert nur, wenn im Constructor-Aufruf eine Referenz auf die GUI übergeben wurde.
 * (Voreinstellung: <b>true</b>).
 */
  public void setOutputGUI(boolean _w) {
    if (gui != null & _w==true) { outputGUI = true; }
    else { outputGUI = false; }
  }

/**
 * Gibt die Anzahl der aufgetretenen Fehler zurück.
 */
  public int getErrorNumber() { return mcm.getErrorNumber(); }

/**
 * Liefert alle vorhandenen Fakten über den entsprechenden Fehler.
 * Die Methode gibt alle vorhandenen Fakten über den Fehler mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public String getError(int _number) { return mcm.getError(_number); }

/**
 * Liefert den Code des entsprechenden Fehlers.
 * Die Methode gibt den Code des Fehlers mit der entsprechenden Nummer als <b>int</b> zurück.
 * <br><a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a>
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public int getErrorCode(int _number) { return mcm.getErrorCode(_number); }

/**
 * Liefert die Beschreibung des entsprechenden Fehlers.
 * Die Methode gibt die Beschreibung des Fehlers mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public String getErrorMsg(int _number) { return mcm.getErrorMsg(_number); }

/**
 * Liefert den Pfad des entsprechenden Fehlers.
 * Die Methode gibt den Pfad des Fehlers mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public String getErrorPath(int _number) { return mcm.getErrorPath(_number); }

/**
 * Gibt die Anzahl der aufgetretenen Warnungen zurück.
 */
  public int getWarningNumber() { return mcm.getWarningNumber(); }

/**
 * Liefert alle vorhandenen Fakten über die entsprechende Warnung.
 * Die Methode gibt alle vorhandenen Fakten über die Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public String getWarning(int _number) { return mcm.getWarning(_number); }

/**
 * Liefert den Code der entsprechenden Warnung.
 * Die Methode gibt den Code der Warnung mit der entsprechenden Nummer als <b>int</b> zurück.
 *
 *<a name="Codes"><h1>Codes von Fehlern und Warnungen beim Syntax Check</h1></a>
 *<b>Grobe Fehlerklassifizierung:</b><br>
 * 000 - 099 allgemeine Fehler und Warnungen<br>
 * 100 - 199 BVars Fehler und Warnungen<br>
 * 200 - 299 Events Fehler und Warnungen<br>
 * 300 - 399 State Fehler und Warnungen<br>
 * 400 - 499 Transitions Fehler und Warnungen<br>
 *<br>
 *<Table border CELLSPACING=3 BORDERCOLOR="#000000" CELLPADDING=3>
 *<TR ALIGN="center" VALIGN="middle"><TH><B>Code</B></TH><TH><B>F</B></TH><TH><B>W</B></TH><TH><B>Beschreibung</B></TH></TR>
 *<TR><TD ALIGN="right">1</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Syntax Check funktioniert noch nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">2</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Event Check funktioniert noch nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">3</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der State Check funktioniert noch nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">4</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Transition Check funktioniert noch nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">5</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der BVars Check funktioniert noch nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">6</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Check auf Fehler der Programmierer funktioniert noch nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">10</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types Or-State wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">11</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types And-State wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">12</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types Basic-State wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">13</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types Transition wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">14</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types TransitionList wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">15</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types PathList wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">16</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types Path wird in EINER PathList mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">17</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types EventList wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">18</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types Event wird in der EventList mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">19</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types BVarList wird mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">20</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Types BVar wird in der BVarList mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">99</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Aufgrund eines fatalen Fehlers wird der Syntax Check abgebrochen.</TD></TR>
 *<TR><TD ALIGN="right">100</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Doppelte Definition von BVar</TD></TR>
 *<TR><TD ALIGN="right">101</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von BVar</TD></TR>
 *<TR><TD ALIGN="right">102</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Deklarierte BVar wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">200</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Doppelte Definition von Events</TD></TR>
 *<TR><TD ALIGN="right">201</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von Event</TD></TR>
 *<TR><TD ALIGN="right">202</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Deklarierter Event wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">203</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Pathname in einem GuardComppath ist nicht vorhanden.</TD></TR>
 *<TR><TD ALIGN="right">204</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Pathname in einem GuardComppath ist mehrfach vorhanden.</TD></TR>
 *<TR><TD ALIGN="right">300</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Mehrfache Definition von Statename</TD></TR>
 *<TR><TD ALIGN="right">301</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Bezeichnung von Statename nicht eindeutig</TD></TR>
 *<TR><TD ALIGN="right">302</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Deklarierter State wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">303</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von Statename</TD></TR>
 *<TR><TD ALIGN="right">304</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Ein Or-State enthaelt nur einen inneren State</TD></TR>
 *<TR><TD ALIGN="right">305</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Ein Or-State enthaelt keinen inneren State</TD></TR>
 *<TR><TD ALIGN="right">306</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Ein And-State enthaelt nur einen inneren State</TD></TR>
 *<TR><TD ALIGN="right">307</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Ein And-State enthaelt keinen inneren State</TD></TR>
 *<TR><TD ALIGN="right">400</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Start-State der Transition ist nicht definiert.</TD></TR>
 *<TR><TD ALIGN="right">401</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Ziel-State der Transition ist nicht definiert.</TD></TR>
 *<TR><TD ALIGN="right">402</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Beide States der Transition sind nicht definiert.</TD></TR>
 *<TR><TD ALIGN="right">403</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Start-State der Transition ist unbekannt.</TD></TR>
 *<TR><TD ALIGN="right">404</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Ziel-State der Transition ist unbekannt.</TD></TR>
 *<TR><TD ALIGN="right">405</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Beide States der Transition sind unbekannt.</TD></TR>
 *<TR><TD ALIGN="right">406</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Interlevel-Transition: Der Start-State liegt falsch.</TD></TR>
 *<TR><TD ALIGN="right">407</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Interlevel-Transition: Der Ziel-State liegt falsch.</TD></TR>
 *<TR><TD ALIGN="right">408</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Interlevel-Transition: Beide States liegt falsch.</TD></TR>
 *<TR><TD ALIGN="right">409</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Start-Connenctor der Transition ist unbekannt.</TD></TR>
 *<TR><TD ALIGN="right">410</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Ziel-Connenctor der Transition ist unbekannt.</TD></TR>
 *<TR><TD ALIGN="right">411</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Beide Connenctoren der Transition sind unbekannt.</TD></TR>
 *<TR><TD ALIGN="right">412</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Interlevel-Transition: Der Start-Connenctor liegt falsch.</TD></TR>
 *<TR><TD ALIGN="right">413</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Interlevel-Transition: Der Ziel-Connenctor liegt falsch.</TD></TR>
 *<TR><TD ALIGN="right">414</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Interlevel-Transition: Beide Connenctoren liegten falsch.</TD></TR>
 *<TR><TD ALIGN="right">415</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Transition hat den gleichen Start- und Ziel-Connector.</TD></TR>
 *<TR><TD ALIGN="right">X</TD><TD ALIGN="center">X</TD><TD ALIGN="center">X</TD><TD>unbekannter Code</TD></TR>
 *</Table>
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public int getWarningCode(int _number) { return mcm.getWarningCode(_number); }

/**
 * Liefert die Beschreibung der entsprechenden Warnung.
 * Die Methode gibt die Beschreibung der Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public String getWarningMsg(int _number) { return mcm.getWarningMsg(_number); }

/**
 * Liefert den Pfad der entsprechenden Warnung.
 * Die Methode gibt den Pfad der Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public String getWarningPath(int _number) { return mcm.getWarningPath(_number); }

}
