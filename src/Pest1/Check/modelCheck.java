package Check;

import java.util.*;
import Absyn.*;

/**
 * <b>Syntax Check für Statecharts</b>
 * <br><a href="../Check/Docu/ErrorAndWarningCodes.html">Codes von Fehlern und Warnungen beim Syntax Check</a> 
 * @author   Daniel Wendorff und Magnus Stiller
 * @version  $Id: modelCheck.java,v 1.7 1998-12-03 21:58:06 swtech11 Exp $
 */
public class modelCheck {
  private modelCheckMsg mcm;
  private boolean warning = true;
  private boolean outputGUI = true;

/**
 * Constructor des Syntax Checkers
 */
  public modelCheck() {
    mcm = new modelCheckMsg();
  }

/**
 * Führt den gesamten Syntax Check bei der Statechart sc durch, und gibt <b>true</b> zurück, falls keine Fehler oder Warnungen aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkModel(Statechart sc) {
    return (checkEvents(sc) & checkStates(sc) &
            checkTransitions(sc) & checkBVars(sc));
  };

/**
 * Checkt die Events der Statechart sc, und gibt <b>true</b> zurück, falls keine Fehler oder Warnungen aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkEvents(Statechart sc) {
    testEvents te = new testEvents(sc,mcm);
    return te.check();
  }

/**
 * Checkt die States der Statechart sc, und gibt <b>true</b> zurück, falls keine Fehler oder Warnungen aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkStates(Statechart sc) {
    testStates ts = new testStates();
    return ts.check(sc,mcm);
  }

/**
 * Checkt die Transitionen der Statechart sc, und gibt <b>true</b> zurück, falls keine Fehler oder Warnungen aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkTransitions(Statechart sc) {
    testTransitions tt = new testTransitions(sc,mcm);
    return tt.check();
  }

/**
 * Checkt die booleschen Variablen der Statechart sc, und gibt <b>true</b> zurück, falls keine Fehler oder Warnungen aufgetreten sind, sonst <b>false</b>.
 */
  public boolean checkBVars(Statechart sc) {
    testBVars tb = new testBVars(sc, mcm);
    return tb.check();
  }

/**
 *
 */
  public void setWarning(boolean _w) { warning = _w;}

/**
 * Legt fest, ob die Fehler und Warnungen auf der GUI erscheinen sollen (Voreinstellung: <b>true</b>).
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
