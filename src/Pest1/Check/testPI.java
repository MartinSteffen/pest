package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testPI.java,v 1.1 1998-12-08 00:09:24 swtech11 Exp $
 */
class testPI  {
  modelCheckMsg msg = new modelCheckMsg();
  Statechart sc = new Statechart(null,null,null,null);
  static String ts = new String("."); // Trennsymbol im Pfad
  Vector nV = new Vector();

  testPI(Statechart _s, modelCheckMsg _m) {
    sc = _s;
    msg = _m;
  }

  boolean check() {
    try {
      if (sc.state instanceof Or_State) {ttOrState ((Or_State)sc.state, null, ""); }
      if (sc.state instanceof And_State) {ttAndState ((And_State)sc.state, null, ""); }
    }
    catch (PIException e) { msg.addError(e.get_wert(),(String)nV.lastElement()); }
    msg.addWarning(6,"ueberall");
    return (msg.getErrorNumber()==0);
  }

  void ttOrState(Or_State os, State _s, String p) throws PIException {
    String np = getAddPathPart(p, os.name.name);

    if (nV.size()>0) {
      if (nV.contains(os)) {
        nV.addElement(np);  // unsauberer Trick
        throw (new PIException(10)); }
      else { nV.addElement(os); }
    }
    else { nV.addElement(os); }

    if (os.trs != null) { nTransInTransList(os.trs, os, np); }
    if (os.substates != null) { nStateInStateList(os.substates, os, np); }
  }

  void ttAndState(And_State as, State _s, String p) throws PIException {
    String np = getAddPathPart(p, as.name.name);

    if (nV.size()>0) {
      if (nV.contains(as)) {
        nV.addElement(np);  // unsauberer Trick
        throw (new PIException(10)); }
      else { nV.addElement(as); }
    }
    else { nV.addElement(as); }

    if (as.substates != null) { nStateInStateList(as.substates, as, np); }
  }

  void nStateInStateList(StateList sl, State _s, String p) throws PIException {
    if (sl.head instanceof Or_State) { ttOrState ((Or_State)sl.head, _s, p); }
    if (sl.head instanceof And_State) {ttAndState ((And_State)sl.head, _s, p); }
    if (sl.tail != null) { nStateInStateList(sl.tail, _s, p); }
  }

  void nTransInTransList(TrList tl, State _s, String p) throws PIException {
    if (tl.tail != null) { nTransInTransList(tl.tail, _s, p); }
  }

  String getAddPathPart(String _p, String _n) {
    String _np = new String();
    if (_p.equals("")) { _np = _n; } else { _np = _p + ts + _n; }
    return _np;
  }

}


class PIException extends Exception {
  int wert;
  PIException(int i) {
    wert = i;
  }
  public int get_wert() {
    return wert;
  }
}

