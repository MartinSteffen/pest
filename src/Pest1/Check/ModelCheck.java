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
 * <a name="Codes"><h2>Codes von Fehlern und Warnungen beim Syntax Check:</h2></a>
 * Die genaue Spezifizierung kann man im Programm in der Menuezeile unter Einstellungen -> Syntax Check -> Meldungen nachschauen.
 * <h2>Testmöglichkeiten:</h2>
 * Das Testprogramm t.java im Directory test erzeugt fehlerhafte Statecharts,
 * deren Resultate man gezielt analysieren kann (näheres siehe README).
 * <br>
 * <br>
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
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 * @version  $Id: ModelCheck.java,v 1.44 1999-02-14 20:56:27 swtech11 Exp $
 * @see CheckConfig
 */
public class ModelCheck {
  private ModelCheckMsg mcm; // Object, um die Fehler und Warnungen zu speichern
  private boolean outputGUI; // Meldungen auf die GUI ausgeben
  protected GUIInterface gui = null; // Referenz auf die GUI
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
        Browser b = new Browser(gui,edit,mcm,cf,new String(""));
      }
      else { outputToGUI(); } // Ausgabe an die GUI
    }

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
    catch (Exception e) { 

     gui.OkDialog("Errormeldung","Die Speicherung ist fehlgeschlagen!");
     //System.out.println(e);
 }
  }

  // Ausgabe an die GUI
  void outputToGUI() {
    if (getErrorNumber()>0) {
      gui.userMessage("Check:");
      gui.userMessage("Check: Fehlermeldungen ( Anzahl: " + getErrorNumber() +  " ):");
      for (int i=1; (i<=getErrorNumber()); i++) {
        gui.userMessage("Check: - "+getErrorMsg(i)+" ("+getErrorCode(i)+")" );
        gui.userMessage("Check:   ["+getErrorPath(i)+"]"); }}
    if (getWarningNumber()>0 & cf.sc_warning==1) {
      gui.userMessage("Check:");
      gui.userMessage("Check: Warnmeldungen ( Anzahl: " + getWarningNumber() +  " ):");
      for (int i=1; (i<=getWarningNumber()); i++) {
        gui.userMessage("Check: - "+getWarningMsg(i)+" ("+getWarningCode(i)+")" );
        gui.userMessage("Check:   ["+getWarningPath(i)+"]"); }}
    else if (getWarningNumber()>0 & cf.sc_warning==2) {
      gui.userMessage("Check:");
      gui.userMessage("Check: Warnmeldungen ( Anzahl: " + getWarningNumber() +  " ):");
      for (int i=1; (i<=getWarningNumber()); i++) {
        int wci = mcm.getWarningCode(i);
        String wc = new String();
        if ( cf.sc_warnStr.indexOf(";"+ wc.valueOf(wci) +";" ) ==-1  ) {
          gui.userMessage("Check: - "+getWarningMsg(i)+" ("+getWarningCode(i)+")" );
          gui.userMessage("Check:   ["+getWarningPath(i)+"]"); }} }
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
