package check;

import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: ModelCheckMsg.java,v 1.4 1999-01-04 22:36:39 swtech11 Exp $
 */
class ModelCheckMsg {
  private Vector ErrorCode;
  private Vector ErrorMsg;
  private Vector ErrorPath;
  private Vector WarningCode;
  private Vector WarningMsg;
  private Vector WarningPath;
  private ErrorAndWarningCodes ewc = new ErrorAndWarningCodes();

  ModelCheckMsg() {
    ErrorCode = new Vector();
    ErrorMsg = new Vector();
    ErrorPath = new Vector();
    WarningCode = new Vector();
    WarningMsg = new Vector();
    WarningPath = new Vector();
  };

  // liefert die Anzahl der Fehler zurueck
  int getErrorNumber() { return ErrorCode.size(); }

  // Error hinzufuegen
  void addError(int _code, String _path) {
    Integer ic = new Integer(_code);
    ErrorCode.addElement(ic);
    ErrorMsg.addElement(ewc.codeToString(_code));
    ErrorPath.addElement(_path);
  }

  // gesamte Error-Meldung des _number. Errors
  // zurueckmelden, der erste Index ist 1 nicht 0
  String getError(int _number) {
    String t = new String();
    String s = new String();
    s = "["+getErrorPath(_number)+"] " + getErrorMsg(_number)
        + " ("+t.valueOf(getErrorCode(_number))+")";
    return s;
  }

  // Error Code zurueckgeben
  int getErrorCode(int _number) {
    return ((Integer)ErrorCode.elementAt(_number-1)).intValue(); }

  // Error Msg zurueckgeben
  String getErrorMsg(int _number) {
    return (String)ErrorMsg.elementAt(_number-1); }

  // Ort des Fehlers zurückgeben
  String getErrorPath(int _number) {
    return (String)ErrorPath.elementAt(_number-1); }

  // liefert die Anzahl der Warnings zurueck
  int getWarningNumber() { return WarningCode.size(); }

  // Warning hinzufuegen
  void addWarning(int _code, String _path) {
    Integer ic = new Integer(_code);
    WarningCode.addElement(ic);
    WarningMsg.addElement(ewc.codeToString(_code));
    WarningPath.addElement(_path);
  }

  // gesamte Warning-Meldung des _number. Warnings zurueckmelden,
  // der erste Index ist 1 nicht 0
  String getWarning(int _number) {
    String t = new String();
    String s = new String();
    s = "["+getWarningPath(_number)+"] " + getWarningMsg(_number)
        + " ("+t.valueOf(getWarningCode(_number))+")";
    return s;
  }
  
  // Warning Code zurückgeben
  int getWarningCode(int _number) {
    return ((Integer)WarningCode.elementAt(_number-1)).intValue(); }

  // Warning Msg zurückgeben
  String getWarningMsg(int _number) {
    return (String)WarningMsg.elementAt(_number-1); }

  // Ort des Warnings zurückgeben
  String getWarningPath(int _number) {
    return (String)WarningPath.elementAt(_number-1); }


  void sort() {
    Vector tc,tm,tp;
    // Fehler sortieren
    if (ErrorCode.size()>1) {
      boolean ok = false;
      while (ok == false) {
        ok = true;
        tc = new Vector();
        tm = new Vector();
        tp = new Vector();
        int i=0;
        while (i<ErrorCode.size()) {
          Integer er1 =  new Integer(  ((Integer)ErrorCode.elementAt(i)).intValue()   );
          if (i==(ErrorCode.size()-1)) {
            tc.addElement(er1);
            tm.addElement(ErrorMsg.elementAt(i));
            tp.addElement(ErrorPath.elementAt(i));
          }
          else {
            Integer er2 = new Integer(  ((Integer)ErrorCode.elementAt(i+1)).intValue()   ) ;
            if ( er1.intValue()>er2.intValue() ) {
              tc.addElement(er2);
              tc.addElement(er1);
              tm.addElement(ErrorMsg.elementAt(i+1));
              tp.addElement(ErrorPath.elementAt(i+1));
              tm.addElement(ErrorMsg.elementAt(i));
              tp.addElement(ErrorPath.elementAt(i));
              ok = false;
              i++;
            }
            else {
              tc.addElement(er1);
              tm.addElement(ErrorMsg.elementAt(i));
              tp.addElement(ErrorPath.elementAt(i));
            }
          }
          i++;
        }
         ErrorCode = tc;
         ErrorMsg  = tm;
         ErrorPath = tp;
      }
    }
    // Warnungen sortieren
    if (WarningCode.size()>1) {
      boolean ok = false;
      while (ok == false) {
        ok = true;
        tc = new Vector();
        tm = new Vector();
        tp = new Vector();
        int i=0;
        while (i<WarningCode.size()) {
          Integer er1 =  new Integer(  ((Integer)WarningCode.elementAt(i)).intValue()   );
          if (i==(WarningCode.size()-1)) {
            tc.addElement(er1);
            tm.addElement(WarningMsg.elementAt(i));
            tp.addElement(WarningPath.elementAt(i));
          }
          else {
            Integer er2 = new Integer(  ((Integer)WarningCode.elementAt(i+1)).intValue()   ) ;
            if ( er1.intValue()>er2.intValue() ) {
              tc.addElement(er2);
              tc.addElement(er1);
              tm.addElement(WarningMsg.elementAt(i+1));
              tp.addElement(WarningPath.elementAt(i+1));
              tm.addElement(WarningMsg.elementAt(i));
              tp.addElement(WarningPath.elementAt(i));
              ok = false;
              i++;
            }
            else {
              tc.addElement(er1);
              tm.addElement(WarningMsg.elementAt(i));
              tp.addElement(WarningPath.elementAt(i));
            }
          }
          i++;
        }
         WarningCode = tc;
         WarningMsg  = tm;
         WarningPath = tp;
      }
    }
  }

  
}



