package Check;

import java.util.*;
import Absyn.*;

public class modelCheck {
  private modelCheckMsg mcm;
  private boolean warning = true;
  private boolean outputGUI = true;

  public modelCheck() {
    mcm = new modelCheckMsg();
  }

  public boolean checkModel(Statechart sc) {
    return (checkEvents(sc) | checkStates(sc) | 
            checkTransitions(sc) | checkBVars(sc));
  }
  public boolean checkEvents(Statechart sc) {
    testEvents te = new testEvents();
    return te.check(sc,mcm);
  }
  public boolean checkStates(Statechart sc) {
    testStates ts = new testStates();
    return ts.check(sc,mcm);
  }
  public boolean checkTransitions(Statechart sc) {
    testTransitions tt = new testTransitions();
    return tt.check(sc,mcm);
  }
  public boolean checkBVars(Statechart sc) {
    testBVars tb = new testBVars();
    return tb.check(sc,mcm);
  }

    /** nach Warnings suchen ? */
  public void setWarning(boolean _w) { warning = _w;}

    /** Ausgabe an die GUI festlegen */
  public void setOutputGUI(boolean _w) { outputGUI = _w;}

  public int getErrorNumber() { return mcm.getErrorNumber(); }
  public String getError(int _number) { return mcm.getError(_number); }
  public int getErrorCode(int _number) { return mcm.getErrorCode(_number); }
  public String getErrorMsg(int _number) { return mcm.getErrorMsg(_number); }
  public String getErrorPath(int _number) { return mcm.getErrorPath(_number); }

  public int getWarningNumber() { return mcm.getWarningNumber(); }
  public String getWarning(int _number) { return mcm.getWarning(_number); }
  public int getWarningCode(int _number) { return mcm.getWarningCode(_number); }
  public String getWarningMsg(int _number) { return mcm.getWarningMsg(_number); }
  public String getWarningPath(int _number) { return mcm.getWarningPath(_number); }

}
