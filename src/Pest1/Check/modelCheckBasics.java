package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: modelCheckBasics.java,v 1.2 1998-12-06 23:03:54 swtech11 Exp $
 */
class modelCheckBasics {
  modelCheckMsg msg = new modelCheckMsg();
  Statechart sc = new Statechart(null,null,null,null);
  boolean printOut; // falls true, dann schrittweise Ausgabe der Chart
  static String ts = new String("."); // Trennsymbol im Pfad

  modelCheckBasics() { };

  modelCheckBasics(Statechart s, modelCheckMsg m) {
    sc = s;  
    msg = m;
    printOut = false;
  }


  // gibt den kompletten Pfad des States _n zurueck, d.h. _p + "." +_n
  String getAddPathPart(String _p, String _n) {   // _p Pfad, _n Name des States
    String _np = new String();
    if (_p.equals("")) { _np = _n; } else { _np = _p + ts + _n; }
    return _np;
  }


// ******* Methoden, um sich durch den Baum zu hangeln ********************

  // Transition bearbeiten und evtl. naechste aufrufen
  void nextTransInTransList(TrList tl) {
    if (printOut == true) {
      String z1,z2;
      if (tl.head.source instanceof UNDEFINED) {z1 = new String("UNDEFINED");} else {z1 = new String(((Statename)tl.head.source).name);}
      if (tl.head.target instanceof UNDEFINED) {z2 = new String("UNDEFINED");} else {z2 = new String(((Statename)tl.head.target).name);}
      System.out.println("Trans: "+z1+" -> "+z2);
    }
    if (tl.tail != null) { nextTransInTransList(tl.tail); }
  }

  // Transition bearbeiten und evtl. naechste aufrufen
  void nextTransInTransList(TrList tl, State _s, String p) {
           // _s enthält den State, indem die Transition liegt
           //  p gibt den Pfad des States _s zurück, indem die Transition liegt
    if (printOut == true) {
      String z1,z2;
      if (tl.head.source instanceof UNDEFINED) {z1 = new String("UNDEFINED");} else {z1 = new String(((Statename)tl.head.source).name);}
      if (tl.head.target instanceof UNDEFINED) {z2 = new String("UNDEFINED");} else {z2 = new String(((Statename)tl.head.target).name);}
      System.out.println("Trans: "+z1+" -> "+z2);
    }
    if (tl.tail != null) { nextTransInTransList(tl.tail, _s, p); }
  }

  // Art des States festellen und evtl. naechsten aufrufen
  void nextStateInStateList(StateList sl) {
    if (sl.head instanceof Or_State) {testTransOrState ((Or_State)sl.head); }
    if (sl.head instanceof And_State) {testTransAndState ((And_State)sl.head); }
    if (sl.head instanceof Basic_State) {testTransBasicState ((Basic_State)sl.head); }
    if (sl.tail != null) { nextStateInStateList(sl.tail); }
  }

  // Art des States festellen und evtl. naechsten aufrufen
  void nextStateInStateList(StateList sl, State _s, String p) {
          // _s enthält den State, indem der State sl.head liegt
          //  p gibt den Pfad des States _s zurück, indem der State sl.head liegt
    if (sl.head instanceof Or_State) {testTransOrState ((Or_State)sl.head, _s, p); }
    if (sl.head instanceof And_State) {testTransAndState ((And_State)sl.head, _s, p); }
    if (sl.head instanceof Basic_State) {testTransBasicState ((Basic_State)sl.head, _s, p); }
    if (sl.tail != null) { nextStateInStateList(sl.tail, _s, p); }
  }

  // Or-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void testTransOrState(Or_State os) {
    if (printOut == true) { System.out.println("OR-State: "+os.name.name); }
    if (os.trs != null) { nextTransInTransList(os.trs); }
    if (os.substates != null) { nextStateInStateList(os.substates); }
  }

  // Or-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void testTransOrState(Or_State os, State _s, String p) {
            // _s enthält den State, indem der Or-State os liegt
            //  p enthält den Pfad des States _s zurück, indem  der Or-State os liegt
    String np = getAddPathPart(p, os.name.name);
    if (printOut == true) { System.out.println("OR-State: "+os.name.name+" ("+np+")"); }
    if (os.trs != null) { nextTransInTransList(os.trs, os, np); }
    if (os.substates != null) { nextStateInStateList(os.substates, os, np); }
  }

  // And-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void testTransAndState(And_State as) {
    if (printOut == true) { System.out.println("AND-State: "+as.name.name); }
    if (as.substates != null) { nextStateInStateList(as.substates); }
  }

  // And-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void testTransAndState(And_State as, State _s, String p) {
           // _s enthält den State, indem der And-State as liegt
           //  p enthält den Pfad des States _s zurück, indem  der And-State as liegt
    String np = getAddPathPart(p, as.name.name);
    if (printOut == true) { System.out.println("AND-State: "+as.name.name+" ("+np+")"); }
    if (as.substates != null) { nextStateInStateList(as.substates, as, np); }
  }

  // Basic-State bearbeiten
  void testTransBasicState(Basic_State bs) {
    if (printOut == true) { System.out.println("BASIC-State: "+bs.name.name); }
  }

  // Basic-State bearbeiten
  void testTransBasicState(Basic_State bs, State _s, String p) {
          // _s enthält den State, indem der Basic-State bs liegt
          //  p enthält den Pfad des States _s zurück, indem  der Basic-State os liegt
    String np = getAddPathPart(p, bs.name.name);
    if (printOut == true) { System.out.println("BASIC-State: "+bs.name.name+" ("+np+")"); }
  }


// ******* Methoden, um eine Pfadliste aus den bestehenden States einer Statechart zu extrahieren

  Vector getPathListFromState(State _s) {
    Vector plv = new Vector();

    if (_s instanceof Or_State) {toOrState ((Or_State)_s, "", plv); }
    if (_s instanceof And_State) {toAndState ((And_State)_s, "", plv); }
    if (_s instanceof Basic_State) {
      String np = getAddPathPart("", _s.name.name);
      plv.addElement(np);
    }
    return plv;
  }

  // notwendige Methoden für getPathListFromState
  void toOrState(Or_State os, String p, Vector plv) {
    String np = getAddPathPart(p, os.name.name);
    plv.addElement(np);
    if (os.substates != null) { toNextStateInStateList(os.substates, np, plv); }
  }
  void toAndState(And_State as, String p, Vector plv) {
    String np = getAddPathPart(p, as.name.name);
    plv.addElement(np);
    if (as.substates != null) { toNextStateInStateList(as.substates, np, plv); }
  }
  void toNextStateInStateList(StateList sl, String p, Vector plv) {
    if (sl.head instanceof Or_State) {toOrState ((Or_State)sl.head, p, plv); }
    if (sl.head instanceof And_State) {toAndState ((And_State)sl.head, p, plv); }
    if (sl.head instanceof Basic_State) {
      String np = getAddPathPart(p, sl.head.name.name);
      plv.addElement(np);
    }
    if (sl.tail != null) { toNextStateInStateList(sl.tail, p, plv); }
  }


}
