package check;

import absyn.*;
import java.util.*;

/**
 * Diese Testklasse testet, ob alle Statenamen deklariert worden sind, 
 * ob die Deklarierten eindeutig sind und ob sie alle verwendet werden.
 *
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 *  @version  $Id: TestStates.java,v 1.18 1999-02-14 20:56:34 swtech11 Exp $
 */
class TestStates extends ModelCheckBasics{
  private Vector Pfad1=new Vector();
  private Vector Pfad2=new Vector();

  // Konstruktor
  TestStates(Statechart _s, ModelCheckMsg _m) {
    super(_s,_m);
  }

  /** Die Methode check ueberprueft die Statechart auf Fehler bzgl. States.*/
  boolean check() {
    int m=msg.getErrorNumber();
    erstelle_Pfad2();
    erstelle_Pfad1();
    vergleiche_Pfade();
    pruefeState(sc.state, null, "");
    return ((msg.getErrorNumber()-m)==0);;
  }

   /** Die Methode erstellt einen Vector Pfad1 der verwendeten States, 
    * dabei wird ueberprueft, ob alle Namen eindeutig sind und
    * ob die States deklariert worden sind.*/
  void erstelle_Pfad1(){
    Pfad1=getPathListFromState(sc.state);
    Vector p1=(Vector)Pfad1.clone();
    Vector p2=(Vector)Pfad1.clone();
	  for (int i=0; i<p1.size(); i++){
      int j=0;
      if (!equalString(Pfad2, ((StateC)p1.elementAt(i)).Pfad))
        {msg.addError(303,"State: "+((StateC)p1.elementAt(i)).Pfad,((StateC)p1.elementAt(i)).s);};
      for (;equalString_r(p2, ((StateC)p1.elementAt(i)).Pfad) ;j++){};
        if (j>1) {
            msg.addError(301,"State: "+((StateC)p1.elementAt(i)).Pfad+" Anzahl: "+j,((StateC)p1.elementAt(i)).s);}
	  }
  }

  // PathListe erstellen
  Vector getPathListFromState(State _s) {
    Vector plv = new Vector();
    if (_s instanceof Or_State) {toOrState ((Or_State)_s, "", plv); }
    else {
       if (_s instanceof And_State) {toAndState ((And_State)_s, "", plv); }
       else {
         if (_s instanceof Basic_State) {
         String np = getAddPathPart("", _s.name.name);
         plv.addElement(new StateC(_s,np,np));
         }
         else {msg.addError(317,"",_s);}
       }}
    return plv;
  }
  // notwendige Methoden für getPathListFromState
  void toOrState(Or_State os, String p, Vector plv) {
    String np = getAddPathPart(p, os.name.name);
    plv.addElement(new StateC(os,p,np));
    if (os.substates != null) { tonavStateInStateList(os.substates, np, plv); }
  }
  void toAndState(And_State as, String p, Vector plv) {
    String np = getAddPathPart(p, as.name.name);
    plv.addElement(new StateC(as,p,np));
    if (as.substates != null) { tonavStateInStateList(as.substates, np, plv); }
  }
  void tonavStateInStateList(StateList sl, String p, Vector plv) {
    if (sl.head instanceof Or_State) {toOrState ((Or_State)sl.head, p, plv); }
    else {
         if (sl.head instanceof And_State) {toAndState ((And_State)sl.head, p, plv); }
	 else {
             if (sl.head instanceof Basic_State) {
             String np = getAddPathPart(p, sl.head.name.name);
             plv.addElement(new StateC(sl.head,p,np));
             }
             else {msg.addError(317,"State: "+p, sl.head);
                   String np = getAddPathPart(p, sl.head.name.name);
                   plv.addElement(new StateC(sl.head,p,np)); }
	 }}
    if (sl.tail != null) { tonavStateInStateList(sl.tail, p, plv); }
  }


/** Die Methode erstellt einen Vector Pfad2 der deklarierten States, 
	<br>dabei wird ueberprueft, ob alle Namen eindeutig sind.*/

    void erstelle_Pfad2(){
    String s;
    for(PathList p=sc.cnames; p!=null; p=p.tail){
       Path p_=p.head;
       s=p_.head;
       String sn="";

       p_=p_.tail;
       for(;p_!=null; p_=p_.tail) {
	   if (p_.head.equals("")) {msg.addError(316,"Pfadliste");};
	   s=s+"."+p_.head;
           if (p_.tail==null) {
               sn=p_.head;
               };
           };
       StateC sc=new StateC(new Statename(sn),"Pfadliste",s);
       if (equalString(Pfad2, sc.Pfad)) { msg.addError(300,"State: "+sc.Pfad, sc.s);}
                                else {Pfad2.addElement(sc);};
        };
    };

   /** Die Vectoren Pfad1 und Pfad2 werden verglichen.
<br>Wenn States aus dem Vector Pfad2 nicht verwendet werden, wird ein Fehler ausgegeben.*/
    void vergleiche_Pfade(){
    Vector p2=(Vector)Pfad2.clone();
    for (int i=0; i<Pfad1.size(); i++){
      equalString_r(p2, ((StateC)Pfad1.elementAt(i)).Pfad); };
  
    for(int i=0; i<p2.size(); i++) { msg.addError(302,"State: "+((StateC)p2.elementAt(i)).Pfad,((StateC)p2.elementAt(i)).s );};
    };
 
    
    /** Es wird ueberprueft, ob ein Or- bzw. And-State andere States enthaelt.
   <br> Wenn kein innere State vorhanden ist, wird ein Fehler ausgegeben. Bei einem inneren State wird gewarnt.
    */
    void pruefeState(State s, State _s, String p){
    if (s instanceof Or_State) {
	   if (((Or_State)s).substates==null) {msg.addError(305,"State: "+s.name.name,s);}
	   else {
                p=getAddPathPart(p, s.name.name);
	        if (Anzahl_States(((Or_State)s).substates)==1) {msg.addWarning(304,"State: "+p,s);};
                Vector dc=defaultcon((Or_State)s,p);
                navStateInStateList(((Or_State)s).substates, s, p, dc); };};
    if (s instanceof And_State) {
           if (((And_State)s).substates==null) {msg.addError(306,"State: "+s.name.name,s);}
	   else {
            p=getAddPathPart(p, s.name.name);
            if (Anzahl_States(((And_State)s).substates)==1) {msg.addWarning(307,"State: "+p,s);};
            navStateInStateList(((And_State)s).substates, s, p, new Vector()); };};
    }

  void navStateInStateList(StateList sl, State _s, String p, Vector dc) {
    boolean b=true;
    for (int i=0; ((i<dc.size()) && b); i++){
	    if (((String)dc.elementAt(i)).equals(sl.head.name.name)) {
        dc.removeElementAt(i);};};
    if (sl.head instanceof Or_State) {pruefeState ((Or_State)sl.head, _s, p); }
    if (sl.head instanceof And_State) {pruefeState ((And_State)sl.head, _s, p); }
    if (sl.tail != null) { navStateInStateList(sl.tail, _s, p, dc); }
    if (sl.tail ==null) {if (dc.size()>0) {
      for (;dc.size()!=0; dc.removeElementAt(0)) {
        msg.addError(312,"Defaultconnector "+(String)dc.elementAt(0)+" in State "+p, _s);};};
      }
  }

  /** Berechnet die Anzahl der States die in StateList sl vorhandne sind. */
  int Anzahl_States(StateList sl){
    int i=1;
	  for (; sl.tail!=null; sl=sl.tail) {i++;}
    return i;
  }

  // Default Connector
  Vector defaultcon(Or_State _s, String p) {
    Vector v=new Vector();
    StatenameList sl=_s.defaults;
    if (sl!=null) {
      for (;sl.tail!=null; sl=sl.tail ) {
        for(int i=0; i<v.size();i++){
          if (((String)v.elementAt(i)).equals(sl.head.name)) {msg.addError(313,"Defaultcon: "+sl.head.name+" im State "+p, _s);}
	        else {v.addElement(sl.head.name);}
        }
	      if (v.size()==0) {v.addElement(sl.head.name);}
      }
      int s=v.size();
      for(int i=0; i<s; i++){
        if (((String)v.elementAt(i)).equals(sl.head.name)) {msg.addError(313,"Defaultcon: "+sl.head.name+" im State "+p, _s);}
	      else {v.addElement(sl.head.name); }
      }
      if (s==0) {v.addElement(sl.head.name);}
    }
    if (sl == null) { msg.addError(314,"State "+p, _s); } // kein DefaultConnector
    if (v.size()>1) { msg.addError(315,"State "+p, _s); } // mehr als ein DefaultConnector
    return v;
  }



    /**   gibt den kompletten Pfad des States _n zurueck, d.h. _p + "." +_n
	  <br> falls eine Statename aus "" besteht wird gewarnt.*/
  String getAddPathPart(String _p, String _n) {   // _p Pfad, _n Name des States
    String _np = new String();
    if (_p.equals("")) { _np = _n; } else { _np = _p + ts + _n; }
    if (_n=="") {msg.addError(308,"State: "+_p);};
    return _np;
  }
}



