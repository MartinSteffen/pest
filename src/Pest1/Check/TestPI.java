package Check;

import Absyn.*;
import java.util.*;

/**
 *  Test auf doppelte Referenzierung:
 *  es fehlt auf jeden Fall noch der Check
 *  auf mehrfach referenzierte Koordinatenangaben
 *  und Label der Transitionen
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestPI.java,v 1.1 1998-12-15 11:41:42 swtech11 Exp $
 */
class TestPI  {
  ModelCheckMsg msg = new ModelCheckMsg();
  Statechart sc = new Statechart(null,null,null,null);
  static String ts = new String("."); // Trennsymbol im Pfad

  String FatalMsg = new String();
  Vector sv  = new Vector(); // Vektor um States zu speichern
  Vector svn = new Vector(); // Vektor um den Pfad der States zu speichern
  Vector tv  = new Vector(); // Vektor um Transitions zu speichern
  Vector tvn = new Vector(); // Vektor um den Pfad der Transitions zu speichern
  Vector tlv = new Vector(); // Vektor um Transitions zu speichern
  Vector tlvn= new Vector(); // Vektor um den Pfad der Transitions zu speichern
  Vector plv = new Vector(); // Vektor um PathList zu speichern
  Vector pv  = new Vector(); // Vektor um Path zu speichern
  Vector elv = new Vector(); // Vektor um EventList zu speichern
  Vector ev  = new Vector(); // Vektor um Event zu speichern
  Vector blv = new Vector(); // Vektor um BVarList zu speichern
  Vector bv  = new Vector(); // Vektor um BVar zu speichern




  TestPI(Statechart _s, ModelCheckMsg _m) {
    sc = _s;
    msg = _m;
  }

  boolean check() {
    try {
      // BVars testen
      if (sc.bvars != null) { controlBVarList(sc.bvars); }
      // Event testen
      if (sc.events != null) { controlEventList(sc.events); }
      // Pfade testen
      if (sc.cnames != null) { controlPathList(sc.cnames); }
      // States und Transitions auf doppelte Referenzierung testen
      if (sc.state instanceof Or_State) {controlOrState ((Or_State)sc.state, null, ""); }
      if (sc.state instanceof And_State) {controlAndState ((And_State)sc.state, null, ""); }
    }
    catch (PIException e) {
      msg.addError(e.get_wert(),FatalMsg);
      msg.addError(99,"uebergebene Statechart");
    }
    msg.addWarning(6,"uebergebene Statechart");
    return (msg.getErrorNumber()==0);
  }

  // BVars testen
  void controlBVarList(BvarList bl) throws PIException {
    if (blv.size()>0) {
      if (blv.contains(bl)) {
        FatalMsg = "in den BVarList der Statechart";
        throw (new PIException(19));
      }
      else {
        blv.addElement(bl);
      }
    }
    else { blv.addElement(bl); }
    if (bl.head != null) {
      if (bv.size()>0) {
        if (bv.contains(bl.head)) {
          FatalMsg = "in den BVars der Statechart";
          throw (new PIException(20));
        }
        else {
          bv.addElement(bl.head);
        }
      }
      else { bv.addElement(bl.head); }
    }
    if (bl.tail != null) { controlBVarList(bl.tail); }
  }

  // Event testen
  void controlEventList(SEventList el) throws PIException {
    if (elv.size()>0) {
      if (elv.contains(el)) {
        FatalMsg = "in den EventList der Statechart";
        throw (new PIException(17));
      }
      else {
        elv.addElement(el);
      }
    }
    else { elv.addElement(el); }
    if (el.head != null) {
      if (ev.size()>0) {
        if (ev.contains(el.head)) {
          FatalMsg = "in den Events der Statechart";
          throw (new PIException(18));
        }
        else {
          ev.addElement(el.head);
        }
      }
      else { ev.addElement(el.head); }
    }
    if (el.tail != null) { controlEventList(el.tail); }
  }

  // Pfade testen
  void controlPathList(PathList pl) throws PIException {
    if (plv.size()>0) {
      if (plv.contains(pl)) {
        FatalMsg = "in den PathList der Statechart";
        throw (new PIException(15));
      }
      else {
        plv.addElement(pl);
      }
    }
    else { plv.addElement(pl); }
    if (pl.head != null) {
      pv = new Vector();
      controlPath(pl.head);
    }
    if (pl.tail != null) { controlPathList(pl.tail); }
  }

  void controlPath(Path p) throws PIException {
    if (pv.size()>0) {
      if (pv.contains(p.head)) {
        FatalMsg = "in den Path der Statechart";
        throw (new PIException(16));
      }
      else {
        pv.addElement(p.head);
      }
    }
    else { pv.addElement(p.head); }
    if (p.tail != null) { controlPath(p.tail); }
  }

  // States tsten
  void inputState(State _s, String _np, int _ex) throws PIException {
    if (sv.size()>0) {
      int i = sv.indexOf(_s);
      if (i!=-1) {
        FatalMsg=(String)svn.elementAt(i)+" und "+ _np;
        throw (new PIException(_ex)); }
      else {
        sv.addElement(_s);
        svn.addElement(_np);
      }
    }
    else {
      sv.addElement(_s);
      svn.addElement(_np);
    }
  }

  void controlOrState(Or_State os, State _s, String p) throws PIException {
    String np = getAddPathPart(p, os.name.name);
    inputState(os,np,10);
    if (os.trs != null) { controlTransInTransList(os.trs, os, np); }
    if (os.substates != null) { controlStateInStateList(os.substates, os, np); }
  }

  void controlAndState(And_State as, State _s, String p) throws PIException {
    String np = getAddPathPart(p, as.name.name);
    inputState(as,np,11);
    if (as.substates != null) { controlStateInStateList(as.substates, as, np); }
  }

  void controlBasicState(Basic_State bs, State _s, String p) throws PIException {
    String np = getAddPathPart(p, bs.name.name);
    inputState(bs,np,12);
  }


  void controlStateInStateList(StateList sl, State _s, String p) throws PIException {
    if (sl.head instanceof Or_State) { controlOrState ((Or_State)sl.head, _s, p); }
    if (sl.head instanceof And_State) {controlAndState ((And_State)sl.head, _s, p); }
    if (sl.head instanceof Basic_State) {controlBasicState ((Basic_State)sl.head, _s, p); }
    if (sl.tail != null) { controlStateInStateList(sl.tail, _s, p); }
  }

  void controlTransInTransList(TrList tl, State _s, String p) throws PIException {
    String z1 = new String();
    String z2 = new String();
    // TransitionList testen
    if (tlv.size()>0) {
      int i = tlv.indexOf(tl);
      if (i!=-1) {
        FatalMsg=(String)tlvn.elementAt(i)+" und "+ p;
        throw (new PIException(14)); }
      else {
        tlv.addElement(tl);
        tlvn.addElement(p);
      }
    }
    else {
      tlv.addElement(tl);
      tlvn.addElement(p);
    }
    // Transition testen
    if (tv.size()>0) {
      int i = tv.indexOf(tl.head);
      if (i!=-1) {
        // Name des Startanker der Transition herausfinden
        if (tl.head.source instanceof UNDEFINED) { z1 = new String("UNDEFINED"); }
        else if (tl.head.source instanceof Statename) { z1 = new String(((Statename)tl.head.source).name); }
        else if (tl.head.source instanceof Conname) { z1 = new String(((Conname)tl.head.source).name); }
        // Name des Zielanker der Transition herausfinden
        if (tl.head.target instanceof UNDEFINED) { z2 = new String("UNDEFINED"); }
        else if (tl.head.target instanceof Statename) { z2 = new String(((Statename)tl.head.target).name); }
        else if (tl.head.target instanceof Conname) { z2 = new String(((Conname)tl.head.target).name); }
        FatalMsg="Trans: "+z1+" -> "+z2+" in State "+(String)tvn.elementAt(i)+" und " + p;
        throw (new PIException(13)); }
      else {
        tv.addElement(tl.head);
        tvn.addElement(p);
      }
    }
    else {
      tv.addElement(tl.head);
      tvn.addElement(p);
    }
    if (tl.tail != null) { controlTransInTransList(tl.tail, _s, p); }
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

