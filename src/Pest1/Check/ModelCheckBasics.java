package check;

import absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: ModelCheckBasics.java,v 1.12 1999-01-22 11:07:16 swtech11 Exp $
 */
class ModelCheckBasics {
  ModelCheckMsg msg = new ModelCheckMsg();
  Statechart sc = new Statechart(null,null,null,null);
  static String ts = new String("."); // Trennsymbol im Pfad

  ModelCheckBasics() { };

  ModelCheckBasics(Statechart s, ModelCheckMsg m) {
    sc = s;  
    msg = m;
  }


// ******* Methoden, um sich durch den Baum zu hangeln ********************

  // Transition bearbeiten und evtl. naechste aufrufen
  void navTransInTransList(TrList tl) {
    if (tl.tail != null) { navTransInTransList(tl.tail); }
  }

  // Transition bearbeiten und evtl. naechste aufrufen
  void navTransInTransList(TrList tl, State _s, String p) {
           // _s enthält den State, indem die Transition liegt
           //  p gibt den Pfad des States _s zurück, indem die Transition liegt
    if (tl.tail != null) { navTransInTransList(tl.tail, _s, p); }
  }

  // Art des States festellen und evtl. naechsten aufrufen
  void navStateInStateList(StateList sl) {
    if (sl.head instanceof Or_State) {navOrState ((Or_State)sl.head); }
    if (sl.head instanceof And_State) {navAndState ((And_State)sl.head); }
    if (sl.head instanceof Basic_State) {navBasicState ((Basic_State)sl.head); }
    if (sl.tail != null) { navStateInStateList(sl.tail); }
  }


  // Art des States festellen und evtl. naechsten aufrufen
  void navStateInStateList(StateList sl, State _s, String p) {
          // _s enthält den State, indem der State sl.head liegt
          //  p gibt den Pfad des States _s zurück, indem der State sl.head liegt
    if (sl.head instanceof Or_State) {navOrState ((Or_State)sl.head, _s, p); }
    if (sl.head instanceof And_State) {navAndState ((And_State)sl.head, _s, p); }
    if (sl.head instanceof Basic_State) {navBasicState ((Basic_State)sl.head, _s, p); }
    if (sl.tail != null) { navStateInStateList(sl.tail, _s, p); }
  }

  // Or-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void navOrState(Or_State os) {
    if (os.trs != null) { navTransInTransList(os.trs); }
    if (os.substates != null) { navStateInStateList(os.substates); }
  }

  // Or-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void navOrState(Or_State os, State _s, String p) {
            // _s enthält den State, indem der Or-State os liegt
            //  p enthält den Pfad des States _s zurück, indem  der Or-State os liegt
    String np = getAddPathPart(p, os.name.name);
    if (os.trs != null) { navTransInTransList(os.trs, os, np); }
    if (os.substates != null) { navStateInStateList(os.substates, os, np); }
  }

  // And-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void navAndState(And_State as) {
    if (as.substates != null) { navStateInStateList(as.substates); }
  }

  // And-State bearbeiten und evtl. Transition und untergeordnete States aufrufen
  void navAndState(And_State as, State _s, String p) {
           // _s enthält den State, indem der And-State as liegt
           //  p enthält den Pfad des States _s zurück, indem  der And-State as liegt
    String np = getAddPathPart(p, as.name.name);
    if (as.substates != null) { navStateInStateList(as.substates, as, np); }
  }

  // Basic-State bearbeiten
  void navBasicState(Basic_State bs) {
  }

  // Basic-State bearbeiten
  void navBasicState(Basic_State bs, State _s, String p) {
          // _s enthält den State, indem der Basic-State bs liegt
          //  p enthält den Pfad des States _s zurück, indem  der Basic-State os liegt
    String np = getAddPathPart(p, bs.name.name);
  }


// ******* Methoden, um eine Pfadliste aus den bestehenden States einer Statechart zu extrahieren
// *******       und um auf den Pfaden zu arbeiten

  // ertstellt eine Pathliste
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
    if (os.substates != null) { tonavStateInStateList(os.substates, np, plv); }
  }
  void toAndState(And_State as, String p, Vector plv) {
    String np = getAddPathPart(p, as.name.name);
    plv.addElement(np);
    if (as.substates != null) { tonavStateInStateList(as.substates, np, plv); }
  }
  void tonavStateInStateList(StateList sl, String p, Vector plv) {
    if (sl.head instanceof Or_State) {toOrState ((Or_State)sl.head, p, plv); }
    if (sl.head instanceof And_State) {toAndState ((And_State)sl.head, p, plv); }
    if (sl.head instanceof Basic_State) {
      String np = getAddPathPart(p, sl.head.name.name);
      plv.addElement(np);
    }
    if (sl.tail != null) { tonavStateInStateList(sl.tail, p, plv); }
  }

  /** prueft ob zwei Pfade gleich sind.*/
  boolean Pathequal(Path p1, Path p2){
    boolean b=(p1.head == p2.head);
    if ((b==true) && (p1.tail!=null) && (p2.tail!=null)) {b=Pathequal(p1.tail, p2.tail);}
    if ((b==true) &&
        (((p1.tail!=null) && (p2.tail==null)) ||
         ((p1.tail==null) && (p2.tail!=null))))  {b=false;}
    return b;
  };

  /** Wandelt einen Pfad in einen String um.*/
  String PathtoString(Path p) {
    String s=p.head;
    p=p.tail;
    for(;p!=null; p=p.tail) {s=s+"."+p.head;};
    return s;
  }

  // gibt den kompletten Pfad des States _n zurueck, d.h. _p + "." +_n
  String getAddPathPart(String _p, String _n) {   // _p Pfad, _n Name des States
    String _np = new String();
    if (_p.equals("")) { _np = _n; } else { _np = _p + ts + _n; }
    return _np;
  }

    boolean equalString(Vector v, String s) {
    boolean b=true;
    for (int j=0; ((j<v.size()) && b); j++) {
              if ((v.elementAt(j) instanceof String) && (((String)v.elementAt(j)).equals(s))) {
		            b=false;
			    
}
	      else {
           if ((v.elementAt(j) instanceof BVarC) && ((((BVarC)v.elementAt(j)).b.var).equals(s))) {
		            b=false; 
                            
}


           else {
           if ((v.elementAt(j) instanceof EventC) && ((((EventC)v.elementAt(j)).e.name).equals(s))) {
		            b=false; 
                           
};};};};       


    
    return !b;}

    boolean equalString_r(Vector v, String s) {
    boolean b=true;
    for (int j=0; ((j<v.size()) && b); j++) {

           if ((v.elementAt(j) instanceof String) && (((String)v.elementAt(j)).equals(s))) {
		            b=false;
			     
                            v.removeElementAt(j);}
           else { 
           if ((v.elementAt(j) instanceof BVarC) && ((((BVarC)v.elementAt(j)).b.var).equals(s))) {
		            b=false; 
                            v.removeElementAt(j);}


           else {
           if ((v.elementAt(j) instanceof EventC) && ((((EventC)v.elementAt(j)).e.name).equals(s))) {
		            b=false; 
                            v.removeElementAt(j);};};};};

    return !b;}


  String getTrSourceName(Tr t) {
    String s=new String();
    if (t.source instanceof Statename)      { s=((Statename)t.source).name;}
    else if (t.source instanceof Conname)   { s=((Conname)t.source).name;}
    else if (t.source instanceof UNDEFINED) { s="UNDEFINED";}
    return s;
  }

  String getTrTargetName(Tr t) {
    String s=new String();
    if (t.target instanceof Statename)      { s=((Statename)t.target).name;}
    else if (t.target instanceof Conname)   { s=((Conname)t.target).name;}
    else if (t.target instanceof UNDEFINED) { s="UNDEFINED";}
    return s;
  }
}



