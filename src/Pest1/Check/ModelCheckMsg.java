package check;

import java.util.*;
import absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: ModelCheckMsg.java,v 1.9 1999-01-21 22:39:18 swtech11 Exp $
 */
class ModelCheckMsg {
  private Vector ErrorCode;
  private Vector ErrorMsg;
  private Vector ErrorPath;
  private Vector WarningCode;
  private Vector ErrorHiObj;
  private Vector WarningMsg;
  private Vector WarningPath;
  private Vector WarningHiObj;
  private ErrorAndWarningCodes ewc = new ErrorAndWarningCodes();

  ModelCheckMsg() {
    ErrorCode = new Vector();
    ErrorMsg = new Vector();
    ErrorPath = new Vector();
    ErrorHiObj = new Vector();
    WarningCode = new Vector();
    WarningMsg = new Vector();
    WarningPath = new Vector();
    WarningHiObj = new Vector();
  };

  // liefert die Anzahl der Fehler zurueck
  int getErrorNumber() { return ErrorCode.size(); }

  // Error hinzufuegen
  void addError(int _code, String _path) {
    Integer ic = new Integer(_code);
    ErrorCode.addElement(ic);
    ErrorMsg.addElement(ewc.codeToString(_code));
    ErrorPath.addElement(_path);
    ErrorHiObj.addElement(null);
  }

  void addError(int _code, String _path, Object _ho) {
    Integer ic = new Integer(_code);
    ErrorCode.addElement(ic);
    ErrorMsg.addElement(ewc.codeToString(_code));
    ErrorPath.addElement(_path);
    ErrorHiObj.addElement(_ho);
  }



  void addError(int n, Tr t, String p) {
    this.addError(n,"", t, p);
  }

    /** Error hinzufuegen*/
 void addError(int n, String p1, Tr t, String p) {
  String s1="";
  String s2="";
  if (t.source instanceof Statename) { s1=((Statename)t.source).name;};
  if (t.target instanceof Statename) { s2=((Statename)t.target).name;};
  if (t.source instanceof Conname)  { s1=((Conname)t.source).name;};
  if (t.target instanceof Conname)  { s2=((Conname)t.target).name;};
  if (t.source instanceof UNDEFINED)  { s1="UNDEFINED";};
  if (t.target instanceof UNDEFINED)  { s2="UNDEFINED";};
  this.addError(n,p1 +" Trans: "+s1+" -> "+s2+" in State: "+p);
  }

 void addError(int n, String p1, Tr t, Tr t2, String p) {
  String s1="";
  String s2="";
  String s3="";
  String s4="";
  if (t.source instanceof Statename) { s1=((Statename)t.source).name;};
  if (t.target instanceof Statename) { s2=((Statename)t.target).name;};
  if (t.source instanceof Conname)  { s1=((Conname)t.source).name;};
  if (t.target instanceof Conname)  { s2=((Conname)t.target).name;};
  if (t.source instanceof UNDEFINED)  { s1="UNDEFINED";};
  if (t.target instanceof UNDEFINED)  { s2="UNDEFINED";};
  if (t2.source instanceof Statename) { s3=((Statename)t2.source).name;};
  if (t2.target instanceof Statename) { s4=((Statename)t2.target).name;};
  if (t2.source instanceof Conname)  { s3=((Conname)t2.source).name;};
  if (t2.target instanceof Conname)  { s4=((Conname)t2.target).name;};
  if (t2.source instanceof UNDEFINED)  { s3="UNDEFINED";};
  if (t2.target instanceof UNDEFINED)  { s4="UNDEFINED";};
  this.addError(n,p1 +" Trans: "+s1+" -> "+s2+" Trans: "+s3+" -> "+s4+" in State: "+p);
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

  // Object des Fehlers zurückgeben
  Object getErrorHiObj(int _number) {
    return (Object)ErrorHiObj.elementAt(_number-1); }

  // liefert die Anzahl der Warnings zurueck
  int getWarningNumber() { return WarningCode.size(); }

  // Warning hinzufuegen
  void addWarning(int _code, String _path) {
    Integer ic = new Integer(_code);
    WarningCode.addElement(ic);
    WarningMsg.addElement(ewc.codeToString(_code));
    WarningPath.addElement(_path);
    WarningHiObj.addElement(null);
  }

  void addWarning(int _code, String _path, Object _ho) {
    Integer ic = new Integer(_code);
    WarningCode.addElement(ic);
    WarningMsg.addElement(ewc.codeToString(_code));
    WarningPath.addElement(_path);
    WarningHiObj.addElement(_ho);
  }

    /** Warnung hinzufuegen.*/
 void addWarning(int n, Tr t, String p) {
  this.addWarning(n,"", t, p);
  }

    /** Warnung hinzufuegen.*/
 void addWarning(int n, String p1, Tr t, String p) {
  String s1="";
  String s2="";
  if (t.source instanceof Statename) { s1=((Statename)t.source).name;};
  if (t.target instanceof Statename) { s2=((Statename)t.target).name;};
  if (t.source instanceof Conname)  { s1=((Conname)t.source).name;};
  if (t.target instanceof Conname)  { s2=((Conname)t.target).name;};
  if (t.source instanceof UNDEFINED)  { s1="UNDEFINED";};
  if (t.target instanceof UNDEFINED)  { s2="UNDEFINED";};
  this.addWarning(n,p1 +" Trans: "+s1+" -> "+s2+" in State: "+p);
  }

 void addWarning(int n, String p1, Tr t, Tr t2, String p) {
  String s1="";
  String s2="";
  String s3="";
  String s4="";
  if (t.source instanceof Statename) { s1=((Statename)t.source).name;};
  if (t.target instanceof Statename) { s2=((Statename)t.target).name;};
  if (t.source instanceof Conname)  { s1=((Conname)t.source).name;};
  if (t.target instanceof Conname)  { s2=((Conname)t.target).name;};
  if (t.source instanceof UNDEFINED)  { s1="UNDEFINED";};
  if (t.target instanceof UNDEFINED)  { s2="UNDEFINED";};
  if (t2.source instanceof Statename) { s3=((Statename)t2.source).name;};
  if (t2.target instanceof Statename) { s4=((Statename)t2.target).name;};
  if (t2.source instanceof Conname)  { s3=((Conname)t2.source).name;};
  if (t2.target instanceof Conname)  { s4=((Conname)t2.target).name;};
  if (t2.source instanceof UNDEFINED)  { s3="UNDEFINED";};
  if (t2.target instanceof UNDEFINED)  { s4="UNDEFINED";};

  this.addWarning(n,p1 +" Trans: "+s1+" -> "+s2+" Trans: "+s3+" -> "+s4+" in State: "+p);
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

  // Object des Warnings zurückgeben
  Object getWarningHiObj(int _number) {
    return (Object)WarningHiObj.elementAt(_number-1); }

  void sort() {
    Vector tc,tm,tp,to;
    // Fehler sortieren
    if (ErrorCode.size()>1) {
      boolean ok = false;
      while (ok == false) {
        ok = true;
        tc = new Vector();
        tm = new Vector();
        tp = new Vector();
        to = new Vector();
        int i=0;
        while (i<ErrorCode.size()) {
          Integer er1 =  new Integer(  ((Integer)ErrorCode.elementAt(i)).intValue()   );
          if (i==(ErrorCode.size()-1)) {
            tc.addElement(er1);
            tm.addElement(ErrorMsg.elementAt(i));
            tp.addElement(ErrorPath.elementAt(i));
            to.addElement(ErrorHiObj.elementAt(i));
          }
          else {
            Integer er2 = new Integer(  ((Integer)ErrorCode.elementAt(i+1)).intValue()   ) ;
            if ( er1.intValue()>er2.intValue() ) {
              tc.addElement(er2);
              tc.addElement(er1);
              tm.addElement(ErrorMsg.elementAt(i+1));
              tm.addElement(ErrorMsg.elementAt(i));
              tp.addElement(ErrorPath.elementAt(i+1));
              tp.addElement(ErrorPath.elementAt(i));
              to.addElement(ErrorHiObj.elementAt(i+1));
              to.addElement(ErrorHiObj.elementAt(i));

              ok = false;
              i++;
            }
            else {
              tc.addElement(er1);
              tm.addElement(ErrorMsg.elementAt(i));
              tp.addElement(ErrorPath.elementAt(i));
              to.addElement(ErrorHiObj.elementAt(i));
            }
          }
          i++;
        }
         ErrorCode = tc;
         ErrorMsg  = tm;
         ErrorPath = tp;
         ErrorHiObj= to;
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
        to = new Vector();
        int i=0;
        while (i<WarningCode.size()) {
          Integer er1 =  new Integer(  ((Integer)WarningCode.elementAt(i)).intValue()   );
          if (i==(WarningCode.size()-1)) {
            tc.addElement(er1);
            tm.addElement(WarningMsg.elementAt(i));
            tp.addElement(WarningPath.elementAt(i));
            to.addElement(WarningHiObj.elementAt(i));
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
              to.addElement(WarningHiObj.elementAt(i+1));
              to.addElement(WarningHiObj.elementAt(i));
              ok = false;
              i++;
            }
            else {
              tc.addElement(er1);
              tm.addElement(WarningMsg.elementAt(i));
              tp.addElement(WarningPath.elementAt(i));
              to.addElement(WarningHiObj.elementAt(i));
            }
          }
          i++;
        }
         WarningCode = tc;
         WarningMsg  = tm;
         WarningPath = tp;
         WarningHiObj= to;
      }
    }
  }

  
}



