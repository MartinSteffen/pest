package Check;

import java.util.*;
import Absyn.*;

/**
 * <b>Syntax Check für Statecharts</b>
 * <br><a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a>
 * @author   Daniel Wendorff und Magnus Stiller<br><a href="mailto:swtech11@informatik.uni-kiel.de">eMail an uns</a>
 * @version  $Id: modelCheck.java,v 1.11 1998-12-10 10:45:02 swtech11 Exp $
 */
public class modelCheck {
  private modelCheckMsg mcm;
  private boolean warning = true;
  private boolean outputGUI = true;
  private boolean donePI = false;
  private boolean NoFatalError = true;

/**
 * Constructor des Syntax Checkers,
 * der keine Ausgabe auf die GUI ermöglicht
 */
  public modelCheck() {
    mcm = new modelCheckMsg();
    setOutputGUI(false);
  }

/**
 * Constructor des Syntax Checkers,
 * der, wenn man die Einstellungen nicht ändert, Meldungen auf der GUI ausgibt
 */
  public modelCheck(String EigentlichPlatzFuerReferenzAufGUI) {
    mcm = new modelCheckMsg();
    setOutputGUI(true);
  }

/**
 * Führt den gesamten Syntax Check bei der Statechart sc durch
 * und gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkModel(Statechart sc) {
    return (checkEvents(sc) & checkStates(sc) &
            checkTransitions(sc) & checkBVars(sc));
  };

/**
 * Checkt die Events der Statechart sc
 * und gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkEvents(Statechart sc) {
    boolean pi = false;
    if (donePI == false ) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      testEvents te = new testEvents(sc, mcm);
      pi = te.check();
    }
    return pi;
  }

/**
 * Checkt die States der Statechart sc
 * und gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkStates(Statechart sc) {
    boolean pi = false;
    if (donePI == false) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      testStates ts = new testStates(sc, mcm);
      pi = ts.check();
    }  
    return pi;
  }

/**
 * Checkt die Transitionen der Statechart sc
 * und gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkTransitions(Statechart sc) {
    boolean pi = false;
    if (donePI == false) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      testTransitions tt = new testTransitions(sc,mcm);
      pi = tt.check();
    }
    return pi;
  }

/**
 * Checkt die booleschen Variablen der Statechart sc
 * und gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkBVars(Statechart sc) {
    boolean pi = false;
    if (donePI == false) { NoFatalError = checkPI(sc); }
    if (NoFatalError == true) {
      testBVars tb = new testBVars(sc, mcm);
      pi = tb.check();
    }
    return pi;
  }

/**
 * Checkt die Statechart sc auf schwerwiegende Fehler der Programmierer (Kreise)
 * und gibt <b>true</b> zurück, falls keine Fehler aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkPI(Statechart sc) {
    testPI tpi = new testPI(sc, mcm);
    NoFatalError=tpi.check();
    donePI = true;
    return NoFatalError;
  }

/**
 *
 */
  public void setWarning(boolean _w) { warning = _w;}

/**
 * Legt fest, ob die Fehler und Warnungen auf der GUI erscheinen sollen
 * (Voreinstellung: <b>true</b>).
 */
  public void setOutputGUI(boolean _w) { outputGUI = _w;}

/**
 * Gibt die Anzahl der aufgetretenen Fehler zurück.
 */
  public int getErrorNumber() { return mcm.getErrorNumber(); }

/**
 * Liefert alle vorhandenen Fakten über den Fehler mit der entsprechenden Nummer als <b>String</b> zurück.
 */
  public String getError(int _number) { return mcm.getError(_number); }

/**
 * Liefert den Code des Fehlers mit der entsprechenden Nummer als <b>int</b> zurück.
 * <br><a href="#Codes">Codes von Fehlern und Warnungen beim Syntax Check</a>
 */
  public int getErrorCode(int _number) { return mcm.getErrorCode(_number); }

/**
 * Liefert die Beschreibung des Fehlers mit der entsprechenden Nummer als <b>String</b> zurück.
 */
  public String getErrorMsg(int _number) { return mcm.getErrorMsg(_number); }

/**
 * Liefert den Pfad des Fehlers mit der entsprechenden Nummer als <b>String</b> zurück.
 */
  public String getErrorPath(int _number) { return mcm.getErrorPath(_number); }

/**
 * Gibt die Anzahl der aufgetretenen Warnungen zurück.
 */
  public int getWarningNumber() { return mcm.getWarningNumber(); }

/**
 * Liefert alle vorhandenen Fakten über die Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 */
  public String getWarning(int _number) { return mcm.getWarning(_number); }

/**
 * Liefert den Code der Warnung mit der entsprechenden Nummer als <b>int</b> zurück.
 *
 *<a name="Codes"><h1>Codes von Fehlern und Warnungen beim Syntax Check</h1></a>
 *<b>Grobe Fehlerklassifizierung:</b><br>
 * 000 - 099 allgemeine Fehler und Warnungen<br>
 * 100 - 199 BVars Fehler und Warnungen<br>
 * 200 - 299 Events Fehler und Warnungen<br>
 * 300 - 399 State Fehler und Warnungen<br>
 * 400 - 499 Transitions Fehler und Warnungen<br>
 *<br>
 *<TABLE BORDER CELLSPACING=3 BORDERCOLOR="#000000" CELLPADDING=3>
 *<TR><TH><B>Code</B></TH><TH><P ALIGN="CENTER"><B>Fehler</B></TH><TH><P ALIGN="CENTER">Warnung</B></TH></TR>
 *<TR><TD><P ALIGN="RIGHT">1</td><td></td><td>Der Syntax Check funktioniert noch nicht einwandfrei.</td></TR>
 *<tr><TD><P ALIGN="RIGHT">2</td><td></td><td>Der Event Check funktioniert noch nicht einwandfrei.</td></TR>
 *<tr><TD><P ALIGN="RIGHT">3</td><td></td><td>Der State Check funktioniert noch nicht einwandfrei.</td></TR>
 *<tr><TD><P ALIGN="RIGHT">4</td><td></td><td>Der Transition Check funktioniert noch nicht einwandfrei.</td></TR>
 *<tr><TD><P ALIGN="RIGHT">5</td><td></td><td>Der BVars Check funktioniert noch nicht einwandfrei.</td></TR>
 *<tr><TD><P ALIGN="RIGHT">100</td><td>Doppelte Definition von BVar</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">101</td><td>Keine Deklaration von BVar</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">102</td><td></td><td>Deklarierte BVar wurde nicht verwendet</td></TR>
 *<tr><TD><P ALIGN="RIGHT">200</td><td>Doppelte Definition von Events</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">201</td><td>Keine Deklaration von Event</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">202</td><td></td><td>Deklarierter Event wurde nicht verwendet</td></TR>
 *<tr><TD><P ALIGN="RIGHT">203</td><td>Der Pathname in einem GuardComppath ist nicht vorhanden.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">204</td><td></td><td>Der Pathname in einem GuardComppath ist mehrfach vorhanden.</td></TR>
 *<tr><TD><P ALIGN="RIGHT">400</td><td>Der Start-State der Transition ist nicht definiert.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">401</td><td>Der Ziel-State der Transition ist nicht definiert.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">402</td><td>Beide States der Transition sind nicht definiert.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">403</td><td>Der Start-State der Transition ist unbekannt.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">404</td><td>Der Ziel-State der Transition ist unbekannt.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">405</td><td>Beide States der Transition sind unbekannt.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">406</td><td>Interlevel-Transition: Der Start-State liegt falsch.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">407</td><td>Interlevel-Transition: Der Ziel-State liegt falsch.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">408</td><td>Interlevel-Transition: Beide States liegt falsch.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">409</td><td>Der Start-Connenctor der Transition ist unbekannt.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">410</td><td>Der Ziel-Connenctor der Transition ist unbekannt.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">411</td><td>Beide Connenctoren der Transition sind unbekannt.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">412</td><td>Interlevel-Transition: Der Start-Connenctor liegt falsch.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">413</td><td>Interlevel-Transition: Der Ziel-Connenctor liegt falsch.</td><td></td></TR>
 *<tr><TD><P ALIGN="RIGHT">414</td><td>Interlevel-Transition: Beide Connenctoren liegten falsch.</td><td></td></TR>
 *<TR><TD><P ALIGN="RIGHT">X</td><td>unbekannter Fehler</td><td>unbekannte Warnung</td></TR>
 *</TABLE>
 */
  public int getWarningCode(int _number) { return mcm.getWarningCode(_number); }

/**
 * Liefert die Beschreibung der Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 */
  public String getWarningMsg(int _number) { return mcm.getWarningMsg(_number); }

/**
 * Liefert den Pfad der Warnung mit der entsprechenden Nummer als <b>String</b> zurück.
 */
  public String getWarningPath(int _number) { return mcm.getWarningPath(_number); }

}
