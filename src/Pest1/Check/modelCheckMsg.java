package Check;

import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: modelCheckMsg.java,v 1.4 1998-12-04 07:36:09 swtech11 Exp $
 */
class modelCheckMsg {
  private Vector ErrorCode;
  private Vector ErrorMsg;
  private Vector ErrorPath;
  private Vector WarningCode;
  private Vector WarningMsg;
  private Vector WarningPath;
  private ErrorAndWarningCodes ewc = new ErrorAndWarningCodes();

  modelCheckMsg() {
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
    s = "Path: "+getErrorPath(_number)+" Fehlercode: "
        +t.valueOf(getErrorCode(_number))
        +" Fehlermeldung: "+getErrorMsg(_number);
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
    s = "Path: "+getWarningPath(_number)+" Warningcode: "
        +t.valueOf(getWarningCode(_number))+" Warning-Meldung: "
        +getWarningMsg(_number);
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

}
