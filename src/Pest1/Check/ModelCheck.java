package check;

import java.util.*;
import java.io.*;

import absyn.*;
import gui.*;

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
 * <br>
 * <a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a><br>
 * <br>
 * <br>
 * <DL COMPACT>
 * <DT><STRONG>STATUS: </STRONG>
 * Unserer Syntax Check ist fertig, abgesehen davon,
 * da� wir unseren Test nicht in der Praxis testen k�nnen, da die Module ( z.B. Editor )
 * nicht fertig sind, und da� es noch keine genauen Informationen der anderen Module
 * �ber die Konsequenzen der <b>NULL</b> gab. Ein Test des Syntax Checkers ist �ber
 * das selbsterkl�rende Programm t im Directory check/test m�glich.
 * <DT><STRONG>To Do: </STRONG>
 * Testen, Testen, Testen.<br>Wir m�ssen den Umgang mit der NULL, nachdem wir
 * die Informationen der anderen Module bekommen haben, entweder kompletieren
 * oder wieder entfernen.
 * <DT><STRONG>Bekannte Fehler: </STRONG>
 * <DT><STRONG>Tempor�re Features: </STRONG>
 * Ausgabe der Dauer der einzelnen Tests in Sekunden.
 * </DL COMPACT>
 *
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: ModelCheck.java,v 1.10 1998-12-29 20:56:24 swtech11 Exp $
 */
public class ModelCheck {
  private ModelCheckMsg mcm; // Object, um die Fehler und Warnungen zu speichern
  private boolean outputGUI; // Meldungen auf die GUI ausgeben
  private GUIInterface gui = null; // Referenz auf die GUI

/**
 * Der Constructor des Syntax Checkers.
 * Es wird keine Ausgabe auf die GUI erm�glicht.
 */
  public ModelCheck() {
    mcm = new ModelCheckMsg();
    outputGUI = false;
  }

/** 
 * Der Constructor des Syntax Checkers.
 * Es werden, wenn man die Einstellungen nicht �ndert, Meldungen von Fehlern und Warnungen auf der GUI ausgegeben.
 * @param _gui Referenz auf die GUI
 * @see        #setOutputGUI(boolean)
 */
  public ModelCheck(GUIInterface _gui) {
    this();
    gui = _gui;
    outputGUI = true;
  }

/**
 * F�hrt den gesamten Syntax Check durch.
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

    long s0=0; long e0=0; long s1=0; long e1=0; long s2=0; long e2=0;
    long s3=0; long e3=0; long s4=0; long e4=0; long s5=0; long e5=0;

    // Test auf Kreisfreiheit und doppelte Referenzierung


    s0 = getT();
    s1 = getT();
    TestPI tpi = new TestPI(sc, mcm);
    NoFatalError=tpi.check();
    e1 = getT();

    if ( NoFatalError == false ) {
      if ( outputGUI == true ) {
        int j = gui.OkDialog("Fataler Fehler","Der beschriebene Fehler f�hrt zum Abbruch des Syntax Checks !");
      }
    }
    else {
      // Checkt die States.
      if (sc.state == null) {
        mcm.addError(311,"uebergebene Statechart");
        if ( outputGUI == true ) { gui.userMessage("Check: Der Check f�r die �berpr�fung der States wurde nicht ausgef�hrt."); }
      }
      else if (sc.cnames == null) {
        mcm.addError(310,"uebergebene Statechart");
        if ( outputGUI == true ) { gui.userMessage("Check: Der Check f�r die �berpr�fung der States wurde nicht ausgef�hrt."); }
      }
      else {
        s2 = getT();
        TestStates ts = new TestStates(sc, mcm);
        NoStateError = ts.check();
        e2 = getT();
        if ( outputGUI == true ) { gui.userMessage("Check: Keine Fehler w�hrend der �berpr�fung der States gefunden."); }
      }

      // Checkt die Transitionen.
      if (sc.state == null) {
        mcm.addError(420,"uebergebene Statechart");
        if ( outputGUI == true ) { gui.userMessage("Check: Der Check f�r die �berpr�fung der Transitionen wurde nicht ausgef�hrt."); }
      }
      else {
        s3 = getT();
        TestTransitions tt = new TestTransitions(sc,mcm);
        NoTransError = tt.check();
        e3 = getT();
        if ( outputGUI == true & NoTransError == true ) {
          gui.userMessage("Check: Keine Fehler w�hrend der �berpr�fung der Transitions gefunden.");
        }
      }

      // Checkt die Events.
      if (sc.events == null) {
        mcm.addWarning(210,"uebergebene Statechart");
        if ( outputGUI == true ) { gui.userMessage("Check: Der Check f�r die �berpr�fung der Events wurde nicht ausgef�hrt."); }
      }
      else {
        s4 = getT();
        TestEvents te = new TestEvents(sc, mcm);
        NoEventError = te.check();
        e4 = getT();
        if ( outputGUI == true & NoEventError == true ) { gui.userMessage("Check: Keine Fehler w�hrend der �berpr�fung der Events gefunden."); }
      }

      // Checkt die booleschen Variablen.
      if (sc.bvars == null) {
        mcm.addWarning(110,"uebergebene Statechart");
        if ( outputGUI == true ) { gui.userMessage("Check: Der Check f�r die �berpr�fung der Booleschen Variablen wurde nicht ausgef�hrt."); }
      }
      else {
        s5 = getT();
        TestBVars tb = new TestBVars(sc, mcm);
        NoBooleanError = tb.check();
        e5 = getT();
        if ( outputGUI == true & NoBooleanError == true) { gui.userMessage("Check: Keine Fehler w�hrend der �berpr�fung der Booleschen Variablen gefunden."); }
      }

      // Gesamtergebnis erstellen
      result = ( NoEventError & NoBooleanError & NoTransError & NoStateError );
    }

    e0 = getT();
    
    System.out.println("");
    System.out.println("Dauer in Sekunden fuer Check auf:");
    System.out.println("- fatale Fehler: " + getS(s1,e1));
    if ( NoFatalError == true ) {
      System.out.println("- States       : " + getS(s2,e2));
      System.out.println("- Transitionen : " + getS(s3,e3));
      System.out.println("- Events       : " + getS(s4,e4));
      System.out.println("- BVars        : " + getS(s5,e5));
    }
    System.out.println("=================================");
    System.out.println("- alles        : " + getS(s0,e0));
    System.out.println("");

    if ( outputGUI == true ) { outputToGUI(); }
    return result;
  };


  private long getT() {
    Date now = new Date();
    return now.getTime();
  }

  private float getS(long s, long e) {
    float f=0;
    // System.out.println(s + " " + e);
    if ( e != s ) { f = ((float)(e-s))/100; }      //  
    return f;
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
      gui.userMessage( "Check: Fehlermeldungen ( Anzahl: " + getErrorNumber() +  " ):");
      for (int i=1;(i<=getErrorNumber());i++) {gui.userMessage("Check: "+getError(i) ); } }
    if (getWarningNumber()>0) {
      gui.userMessage( "Check: Warnmeldungen ( Anzahl: " + getWarningNumber() +  " ):");
      for (int i=1;(i<=getWarningNumber());i++) {gui.userMessage("Check: "+getWarning(i) ); } }
  }

/**
 * Legt fest, ob die Fehler und Warnungen auf der GUI erscheinen sollen.
 * Diese Methode funktioniert nur, wenn im Constructor-Aufruf eine Referenz auf die GUI �bergeben wurde.
 * (Voreinstellung: <b>true</b>).
 */
  public void setOutputGUI(boolean _w) {
    if (gui != null & _w==true) { outputGUI = true; }
    else { outputGUI = false; }
  }

/**
 * Gibt die Anzahl der aufgetretenen Fehler zur�ck.
 */
  public int getErrorNumber() { return mcm.getErrorNumber(); }

/**
 * Liefert alle vorhandenen Fakten �ber den entsprechenden Fehler.
 * Die Methode gibt alle vorhandenen Fakten �ber den Fehler mit der entsprechenden Nummer als <b>String</b> zur�ck.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public String getError(int _number) { return mcm.getError(_number); }

/**
 * Liefert den Code des entsprechenden Fehlers.
 * Die Methode gibt den Code des Fehlers mit der entsprechenden Nummer als <b>int</b> zur�ck.
 * <br><a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a>
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public int getErrorCode(int _number) { return mcm.getErrorCode(_number); }

/**
 * Liefert die Beschreibung des entsprechenden Fehlers.
 * Die Methode gibt die Beschreibung des Fehlers mit der entsprechenden Nummer als <b>String</b> zur�ck.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public String getErrorMsg(int _number) { return mcm.getErrorMsg(_number); }

/**
 * Liefert den Pfad des entsprechenden Fehlers.
 * Die Methode gibt den Pfad des Fehlers mit der entsprechenden Nummer als <b>String</b> zur�ck.
 * @param _number Index des Fehlers (1 bis getErrorNumber() )
 * @see           #getErrorNumber()
 */
  public String getErrorPath(int _number) { return mcm.getErrorPath(_number); }

/**
 * Gibt die Anzahl der aufgetretenen Warnungen zur�ck.
 */
  public int getWarningNumber() { return mcm.getWarningNumber(); }

/**
 * Liefert alle vorhandenen Fakten �ber die entsprechende Warnung.
 * Die Methode gibt alle vorhandenen Fakten �ber die Warnung mit der entsprechenden Nummer als <b>String</b> zur�ck.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public String getWarning(int _number) { return mcm.getWarning(_number); }

/**
 * Liefert den Code der entsprechenden Warnung.
 * Die Methode gibt den Code der Warnung mit der entsprechenden Nummer als <b>int</b> zur�ck.
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
 *<TR><TD ALIGN="right">21</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Typs Label wird in Transitionen mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">22</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Typs Guard wird in Transitionen mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">23</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Fataler Fehler: EIN Objekt des Typs Action wird in Transitionen mehrfach referenziert.</TD></TR>
 *<TR><TD ALIGN="right">99</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Aufgrund eines fatalen Fehlers wird der Syntax Check abgebrochen.</TD></TR>
 *<TR><TD ALIGN="right">100</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Doppelte Definition von BVar</TD></TR>
 *<TR><TD ALIGN="right">101</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von BVar</TD></TR>
 *<TR><TD ALIGN="right">102</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Deklarierte BVar wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">110</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Die Statechart enth�lt keine Liste der booleschen Variablen.</TD></TR>
 *<TR><TD ALIGN="right">200</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Doppelte Definition von Events</TD></TR>
 *<TR><TD ALIGN="right">201</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von Event</TD></TR>
 *<TR><TD ALIGN="right">202</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Deklarierter Event wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">203</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Der Pathname in einem GuardComppath ist nicht vorhanden.</TD></TR>
 *<TR><TD ALIGN="right">204</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Der Pathname in einem GuardComppath ist mehrfach vorhanden.</TD></TR>
 *<TR><TD ALIGN="right">210</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Die Statechart enth�lt keine Eventliste.</TD></TR>
 *<TR><TD ALIGN="right">300</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Mehrfache Definition von Statename</TD></TR>
 *<TR><TD ALIGN="right">301</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Bezeichnung von Statename nicht eindeutig</TD></TR>
 *<TR><TD ALIGN="right">302</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Deklarierter State wurde nicht verwendet</TD></TR>
 *<TR><TD ALIGN="right">303</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Keine Deklaration von Statename</TD></TR>
 *<TR><TD ALIGN="right">304</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Ein Or-State enthaelt nur einen inneren State</TD></TR>
 *<TR><TD ALIGN="right">305</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Ein Or-State enthaelt keinen inneren State</TD></TR>
 *<TR><TD ALIGN="right">306</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Ein And-State enthaelt nur einen inneren State</TD></TR>
 *<TR><TD ALIGN="right">307</TD><TD ALIGN="center">&nbsp;</TD><TD ALIGN="center">X</TD><TD>Ein And-State enthaelt keinen inneren State</TD></TR>
 *<TR><TD ALIGN="right">310</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Statechart enth�lt keine Pfadliste.</TD></TR>
 *<TR><TD ALIGN="right">311</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Statechart enth�lt keine States.</TD></TR>
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
 *<TR><TD ALIGN="right">420</TD><TD ALIGN="center">X</TD><TD ALIGN="center">&nbsp;</TD><TD>Die Statechart enth�lt keine Transitionen.</TD></TR> 
 *<TR><TD ALIGN="right">X</TD><TD ALIGN="center">X</TD><TD ALIGN="center">X</TD><TD>unbekannter Code</TD></TR>
 *</Table>
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public int getWarningCode(int _number) { return mcm.getWarningCode(_number); }

/**
 * Liefert die Beschreibung der entsprechenden Warnung.
 * Die Methode gibt die Beschreibung der Warnung mit der entsprechenden Nummer als <b>String</b> zur�ck.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public String getWarningMsg(int _number) { return mcm.getWarningMsg(_number); }

/**
 * Liefert den Pfad der entsprechenden Warnung.
 * Die Methode gibt den Pfad der Warnung mit der entsprechenden Nummer als <b>String</b> zur�ck.
 * @param _number Index der Warnung (1 bis getWarningNumber() )
 * @see           #getWarningNumber()
 */
  public String getWarningPath(int _number) { return mcm.getWarningPath(_number); }

}
