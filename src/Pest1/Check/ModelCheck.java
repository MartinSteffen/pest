package check;

import java.util.*;
import java.io.*;
import java.awt.*;

import absyn.*;
import gui.*;
import editor.*;

/**
 * <h1>Syntax Check für Statecharts</h1>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li>Initialisierung:   ModelCheck mc = new ModelCheck(GUI_Referenz,EDITOR_Referenz, CheckConfig)
 * <li>Aufruf des Checks: boolean = mc.checkModel(Statechart)
 * </ol>
 * <h2>Forderungen an die an den Check übergebene Statechart:</h2>
 * <ul>
 * <li>Es darf keine <b>null</b> an Stellen stehen, die dafür nicht vorgesehen
 * sind (z.B. in TrList.head).
 * <li>In der Datenstruktur der Statechart darf kein Zyklus sein.
 * </ul>
 * <h2>Garantien nach der Beendigung des Checks:</h2>
 * <ul>
 * <li>Der Check verändert die an ihn übergebene Statechart <b>nicht</b>.
 * <li>Der Check liefert nur dann <b>true</b> zurück, wenn die Statechart
 * keine Fehler mehr enthält, die den Simulator oder den CodeGenerator zu
 * zu falschen Ergebnissen führen würde.
 * </ul>
 * <br>
 * <DL COMPACT>
 * <DT><STRONG>STATUS: </STRONG><br>
 * Unserer Syntax Check ist soweit fertig, <br>
 * aber die Optionen hadern noch mit der GUI.
 * <DT><STRONG>To Do: </STRONG><br>
 * Testen, Testen, Testen.
 * <DT><STRONG>Bekannte Fehler: </STRONG><br>
 * keine
 * <DT><STRONG>Temporäre Features: </STRONG><br>
 * keine
 * </DL COMPACT>
 * <br>
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
 *<TR><TD ALIGN="right">1</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Syntax Check funktioniert nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">2</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Event Check funktioniert nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">3</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der State Check funktioniert nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">4</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Transition Check funktioniert nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">5</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der BVars Check funktioniert nicht einwandfrei.</TD></TR>
 *<TR><TD ALIGN="right">6</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Check auf Fehler der Programmierer funktioniert nicht einwandfrei.</TD></TR>
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
 *<TR><TD ALIGN="right">21</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Typs Label wird in Transitionen mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">22</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Typs Guard wird in Transitionen mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">23</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Typs Action wird in Transitionen mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">24</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Typs Aseq ist ein Nullpointer.</TD></TR>
 *<TR><TD ALIGN="right">99</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Aufgrund eines fatalen Fehlers wird der Syntax Check abgebrochen.</TD></TR>
 *<TR><TD ALIGN="right">100</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Doppelte Definition von BVar</TD></TR>
 *<TR><TD ALIGN="right">101</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Definition von BVar</TD></TR>
 *<TR><TD ALIGN="right">102</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Definierte BVar wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">103</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Boolsche Ausdruck im Guard der Transition laesst sich nicht spezifizieren. </TD></TR>
 *<TR><TD ALIGN="right">104</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Die Boolsche Variable wird in keinem Action veraendert. </TD></TR>
 *<TR><TD ALIGN="right">105</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Kein Guard fragt den Wert der boolschen Variable ab. </TD></TR>
 *<TR><TD ALIGN="right">106</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Name des BVars ist ein leerer String.</TD></TR>
 *<TR><TD ALIGN="right">110</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Die Statechart enthält keine Liste der booleschen Variablen.</TD></TR>
 *<TR><TD ALIGN="right">200</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Name von Event nicht eindeutig</TD></TR>
 *<TR><TD ALIGN="right">201</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von Event</TD></TR>
 *<TR><TD ALIGN="right">202</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Deklarierter Event wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">203</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Pathname in einem GuardComppath ist nicht vorhanden.</TD></TR>
 *<TR><TD ALIGN="right">204</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Das Guardevent wird nie von einem Actionevent ausgeloest.</TD></TR>
 *<TR><TD ALIGN="right">205</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Das Actionevent loest kein  Guardevent aus.</TD></TR>
 *<TR><TD ALIGN="right">206</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Name des Events ist ein leerer String.</TD></TR>
 *<TR><TD ALIGN="right">210</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Die Statechart enthält keine Eventliste.</TD></TR>
 *<TR><TD ALIGN="right">300</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Mehrfache Definition von Statename</TD></TR>
 *<TR><TD ALIGN="right">301</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Bezeichnung von Statename nicht eindeutig</TD></TR>
 *<TR><TD ALIGN="right">302</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Deklarierter State wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">303</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von Statename</TD></TR>
 *<TR><TD ALIGN="right">304</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Ein Or-State enthaelt nur einen inneren State</TD></TR>
 *<TR><TD ALIGN="right">305</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: Ein Or-State enthaelt keinen inneren State.</TD></TR>
 *<TR><TD ALIGN="right">306</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: Ein And-State enthaelt keinen inneren State.</TD></TR>
 *<TR><TD ALIGN="right">307</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Ein And-State enthaelt nur einen inneren State</TD></TR>
 *<TR><TD ALIGN="right">308</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Statename ist ein leerer String</TD></TR>
 *<TR><TD ALIGN="right">310</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Statechart enthält keine Pfadliste.</TD></TR>
 *<TR><TD ALIGN="right">311</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Statechart enthält keine States.</TD></TR>
 *<TR><TD ALIGN="right">312</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Zu diesem Defaultconnector gibt es keinen State.</TD></TR>
 *<TR><TD ALIGN="right">313</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Defaultconnector ist mehrfach eingetragen.</TD></TR>
 *<TR><TD ALIGN="right">314</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>In diesem State gibt es keinen Defaultconnector.</TD></TR>
 *<TR><TD ALIGN="right">315</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>IN diesem State gibt es mehr als einen Defaultconnector.</TD></TR>
 *<TR><TD ALIGN="right">316</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Statename ist ein leerer String.</TD></TR>
 *<TR><TD ALIGN="right">317</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: Unbekannter Statetyp.</TD></TR>
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
 *<TR><TD ALIGN="right">416</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Guard der Transition beinhaltet einen Nichtdetermininsmus.</TD></TR>
 *<TR><TD ALIGN="right">417</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Guard der Transition laesst sich nicht spezifizieren.</TD></TR>
 *<TR><TD ALIGN="right">418</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Action der Transition laesst sich nicht spezifizieren.</TD></TR>
 *<TR><TD ALIGN="right">419</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Transition ist auf keinem Weg mit einem State verbunden.</TD></TR>
 *<TR><TD ALIGN="right">420</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Statechart enthält keine Transitionen.</TD></TR>
 *<TR><TD ALIGN="right">421</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Dieser Connector ist Teil eines Zyklus.</TD></TR>
 *<TR><TD ALIGN="right">422</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Transition ist an ihrem Startpunkt nicht durch andere Transitionen mit einem Startstate verbunden.</TD></TR>
 *<TR><TD ALIGN="right">423</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Transition ist an ihrem Zielpunkt nicht durch andere Transitionen mit einem Zielstate verbunden.</TD></TR>
 *<TR><TD ALIGN="right">423</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Guard hat den Typ GuardUndet.</TD></TR>
 *<TR><TD ALIGN="right">X</TD><TD ALIGN="center">X</TD><TD ALIGN="center">X</TD><TD>unbekannter Code</TD></TR>
 *</Table>
 * <br>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: ModelCheck.java,v 1.38 1999-02-09 13:46:26 swtech11 Exp $
 * @see CheckConfig
 */
public class ModelCheck {
  private ModelCheckMsg mcm; // Object, um die Fehler und Warnungen zu speichern
  private boolean outputGUI; // Meldungen auf die GUI ausgeben
  private GUIInterface gui = null; // Referenz auf die GUI
  private Editor edit = null;
  private CheckConfig cf = new CheckConfig();

/**
 * Der Constructor des Syntax Checkers.
 * @param _gui Referenz auf die GUI
 */
  public ModelCheck(GUIInterface _gui) {
    mcm = new ModelCheckMsg();
    gui = _gui;
    outputGUI = true;
  }

/**
 * Der Constructor des Syntax Checkers.
 * @param _gui Referenz auf die GUI
 * @param _edit Referenz auf den Editor
 */
  public ModelCheck(GUIInterface _gui, Editor _edit) {
    mcm = new ModelCheckMsg();
    gui = _gui;
    outputGUI = true;
    edit = _edit;
  }


/**
 * Der Constructor des Syntax Checkers.
 * @param _gui Referenz auf die GUI
 * @param _edit Referenz auf den Editor
 * @param _cf Referenz auf das Konfigurationsobjekt des Syntax Checks
 */
  public ModelCheck(GUIInterface _gui, Editor _edit, CheckConfig _cf) {
    mcm = new ModelCheckMsg();
    gui = _gui;
    outputGUI = true;
    edit = _edit;
    if ( _cf==null ) { cf = new CheckConfig(); } else { cf = _cf; }

  }

/**
 * Führt den gesamten Syntax Check durch.
 * @return true, falls keine Fehler aufgetreten sind, sonst false
 * @param sc  die zu checkende Statechart
 */
  public boolean checkModel(Statechart sc) {
    boolean NoEventError = false;
    boolean NoBooleanError = false;
    boolean NoTransError = false;
    boolean NoStateError = false;
    boolean NoFatalError = false;
    boolean result = false;

    Dialog V;
    Panel pan;
    Label l2;

    // CheckOption co = new CheckOption((pest)gui,cf); // zu Testzwecken

    // Fortschrittsanzeige
    V = new Dialog((pest)gui,"Fortschrittanzeige");
    pan = new Panel(new GridLayout(1,1));
    l2 = new Label("");
    l2.setAlignment(Label.CENTER);
    pan.add(l2);
    V.add(pan);
    Point p = ((pest)gui).getLocation();
    V.setLocation(p.x + 30 , p.y + 30);
    V.pack();
    Dimension d = V.getSize();
    d.width=270;
    V.setSize(d);
	  V.setResizable(false);
  	V.setVisible(true);

    // Test auf Kreisfreiheit und doppelte Referenzierung, rerauskommentiert dank Garantie
    // TestPI tpi = new TestPI(sc, mcm);             der anderen
    // NoFatalError=tpi.check();
    //NoFatalError=true;
    //if ( NoFatalError == false ) {
    //  if ( outputGUI == true ) {
    //    int j = gui.OkDialog("Fataler Fehler","Der Fehler führt zum Abbruch des Syntax Checks !"); } }
    //else

    {
      // Checkt die States.
      l2.setText("Check: States");
      if (sc.state == null) {
        mcm.addError(311,"uebergebene Statechart");
        if ( outputGUI == true ) {
          gui.userMessage("Check: Der Check für die Überprüfung der States wurde nicht ausgeführt."); }
      }
      else {
        if (sc.cnames == null) { mcm.addError(310,"uebergebene Statechart"); }
        TestStates ts = new TestStates(sc, mcm);
        NoStateError = ts.check();
        if ( outputGUI == true & NoStateError == true) { gui.userMessage("Check: Keine Fehler während der Überprüfung der States gefunden."); }
      }
      // Checkt die Transitionen.
      l2.setText("Check: Transitionen");
      if (sc.state == null) {
        // mcm.addError(420,"uebergebene Statechart");
        if ( outputGUI == true ) {
          gui.userMessage("Check: Der Check für die Überprüfung der Transitionen wurde nicht ausgeführt."); }
      }
      else {
        TestTransitions tt = new TestTransitions(sc,mcm);
        NoTransError = tt.check();
        if ( outputGUI == true & NoTransError == true ) { gui.userMessage("Check: Keine Fehler während der Überprüfung der Transitions gefunden."); }
      }
      // Checkt die Events.
      l2.setText("Check: Events");
      if (sc.events == null) { mcm.addWarning(210,"uebergebene Statechart"); }
      TestEvents te = new TestEvents(sc, mcm);
      NoEventError = te.check();
      if ( outputGUI == true & NoEventError == true ) { gui.userMessage("Check: Keine Fehler während der Überprüfung der Events gefunden."); }
      // Checkt die booleschen Variablen.
      l2.setText("Check: boolesche Variablen");
      if (sc.bvars == null) { mcm.addWarning(110,"uebergebene Statechart"); }
      TestBVars tb = new TestBVars(sc, mcm);
      NoBooleanError = tb.check();
      if ( outputGUI == true & NoBooleanError == true) {
        gui.userMessage("Check: Keine Fehler während der Überprüfung der Booleschen Variablen gefunden.");
      }
      // Gesamtergebnis erstellen
      result = ( NoEventError & NoBooleanError & NoTransError & NoStateError );
    }

    l2.setText("Check: Meldungen sortieren");
    mcm.sort(); // Meldungen sortieren
    V.dispose();

    if ( outputGUI == true ) {
      if (cf.sc_browser==true) { // Browser-Ausgabe
        Browser b = new Browser((pest)gui,edit,mcm,cf);
      }
      else { outputToGUI(); } // Ausgabe an die GUI
    }
      //outputToFile("test.txt");
    return result;
  };

/**
 * Gibt alle Meldungen des Syntax Checkers in eine Datei aus.
 * Die Methode gibt alle Fehler- und Warnungmeldungen in einer Textdatei im ASCII-Format aus.
 * @param _name  Name der Datei, in die gespeichert werden soll
 */
  void outputToFile(String _name) {
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

  // Ausgabe an die GUI
  void outputToGUI() {
    if (getErrorNumber()>0) {
      gui.userMessage("Check:");
      gui.userMessage("Check: Fehlermeldungen ( Anzahl: " + getErrorNumber() +  " ):");
      for (int i=1; (i<=getErrorNumber()); i++) {
        gui.userMessage("Check: - "+getErrorMsg(i)+" ("+getErrorCode(i)+")" );
        gui.userMessage("Check:   ["+getErrorPath(i)+"]"); }}
    if (getWarningNumber()>0 & cf.sc_warning==true) {
      gui.userMessage("Check:");
      gui.userMessage("Check: Warnmeldungen ( Anzahl: " + getWarningNumber() +  " ):");
      for (int i=1; (i<=getWarningNumber()); i++) {
        gui.userMessage("Check: - "+getWarningMsg(i)+" ("+getWarningCode(i)+")" );
        gui.userMessage("Check:   ["+getWarningPath(i)+"]"); }}
  }

/**
 * Legt fest, ob die Fehler und Warnungen auf der GUI erscheinen sollen.
 * Diese Methode funktioniert nur, wenn im Constructor-Aufruf eine Referenz auf die GUI übergeben wurde.
 * (Voreinstellung: <b>true</b>).
 */
  void setOutputGUI(boolean _w) {
    if (gui != null & _w==true) { outputGUI = true; }
    else { outputGUI = false; }
  }

/**
 * Gibt die Anzahl der aufgetretenen Fehler zurück.
 */
  int getErrorNumber() { return mcm.getErrorNumber(); }

/**
 * Liefert alle vorhandenen Fakten über den entsprechenden Fehler.
 * Die Methode gibt alle vorhandenen Fakten über den Fehler mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  String getError(int _number) { return mcm.getError(_number); }

/**
 * Liefert den Code des entsprechenden Fehlers.
 * Die Methode gibt den Code des Fehlers mit der entsprechenden Nummer als <b>int</b> zurück.
 * <br><a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a>
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  int getErrorCode(int _number) { return mcm.getErrorCode(_number); }

/**
 * Liefert die Beschreibung des entsprechenden Fehlers.
 * Die Methode gibt die Beschreibung des Fehlers mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  String getErrorMsg(int _number) { return mcm.getErrorMsg(_number); }

/**
 * Liefert den Pfad des entsprechenden Fehlers.
 * Die Methode gibt den Pfad des Fehlers mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  String getErrorPath(int _number) { return mcm.getErrorPath(_number); }

/**
 * Gibt die Anzahl der aufgetretenen Warnungen zurück.
 */
  int getWarningNumber() { return mcm.getWarningNumber(); }

/**
 * Liefert alle vorhandenen Fakten über die entsprechende Warnung.
 * Die Methode gibt alle vorhandenen Fakten über die Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  String getWarning(int _number) { return mcm.getWarning(_number); }

/**
 * Liefert den Code der entsprechenden Warnung.
 * Die Methode gibt den Code der Warnung mit der entsprechenden Nummer als <b>int</b> zurück.
 * <a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a><br>
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  int getWarningCode(int _number) { return mcm.getWarningCode(_number); }

/**
 * Liefert die Beschreibung der entsprechenden Warnung.
 * Die Methode gibt die Beschreibung der Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  String getWarningMsg(int _number) { return mcm.getWarningMsg(_number); }

/**
 * Liefert den Pfad der entsprechenden Warnung.
 * Die Methode gibt den Pfad der Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  String getWarningPath(int _number) { return mcm.getWarningPath(_number); }

}


/** hier folgenden die Methoden zur Zeitnahmen, die nicht mehr gebraucht werden

    long s0=0; long e0=0; long s1=0; long e1=0; long s2=0; long e2=0;
    long s3=0; long e3=0; long s4=0; long e4=0; long s5=0; long e5=0;

    { // Dauer ausgeben
      System.out.println("");
      System.out.println("Dauer in Sekunden fuer Check auf:");
      System.out.println("- fatale Fehler: " + getTimeDif(s1,e1));
      if ( NoFatalError == true ) {
        System.out.println("- States       : " + getTimeDif(s2,e2));
        System.out.println("- Transitionen : " + getTimeDif(s3,e3));
        System.out.println("- Events       : " + getTimeDif(s4,e4));
        System.out.println("- BVars        : " + getTimeDif(s5,e5));
      }
      System.out.println("=================================");
      System.out.println("- alles        : " + getTimeDif(s0,e0));
      System.out.println("");
    }

  // Zeit auslesen
  private long getTimeC() {
    Date now = new Date();
    return now.getTime();
  }

  // Dauer berechnen
  private float getTimeDif(long s, long e) {
    float f=0;
    // System.out.println(s + " " + e);
    if ( e != s ) { f = ((float)(e-s))/100; }      //  
    return f;
  }

*/
