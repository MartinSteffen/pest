package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testBVars.java,v 1.8 1998-12-10 11:40:12 swtech11 Exp $
 */

/** Diese Testklasse testet, ob alle BVars deklariert worden sind, 
    <br>ob die Deklarierten eindeutig sind und ob sie alle verwendet werden.*/

class testBVars extends modelCheckBasics{
  private Vector Ist;
  private Vector Soll;

  testBVars() {
    super();
    Ist=new Vector();
    Soll=new Vector();
  }
  testBVars(Statechart s, modelCheckMsg m) {
    super(s,m);
    Ist=new Vector();
    Soll=new Vector();
  }

    /** Die Methode check ueberprueft die Statechart auf Fehler bzgl. BVars.*/

  boolean check() {
    int m=msg.getErrorNumber();
    erstelle_Ist();
    erstelle_Soll();
    vergleiche();
    return ((msg.getErrorNumber()-m)==0);
  }

    /** Die Methode erstellt einen Vector Ist der deklarierten BVars, 
	<br>dabei wird ueberprueft, ob alle Namen eindeutig sind.*/

  void erstelle_Ist() {
         
     for(BvarList b=sc.bvars; b!=null; b=b.tail){
       if (Ist.contains(b.head.var)) { msg.addError(100,"BVar: "+b.head.var);}
                                else {Ist.addElement(b.head.var);};
        }
   };
    /** Die Methode ueberprueft saemtliche Transitionen auf eine Verwendung von BVars.
	<br>Es werden alle BVarnamen in einen Vector Soll geschrieben.*/

  void erstelle_Soll() {

    if (sc.state instanceof Or_State) {testTransOrState((Or_State)sc.state, null, ""); }
    if (sc.state instanceof And_State) {testTransAndState((And_State)sc.state, null, ""); }

  };

  void nextTransInTransList(TrList tl, State _s, String p) {
    pruefeGuard(tl.head.label.guard, tl.head, p);
    pruefeAction(tl.head.label.action, tl.head, p);
    if (tl.tail != null) { nextTransInTransList(tl.tail, _s, p); }

  }

    /** Ueberprueft den Guard auf Verwendung von BVars.*/

    void pruefeGuard(Guard g, Tr t, String p){
    if (g instanceof GuardBVar)  {pruefeBVar  (((GuardBVar )g).bvar, t, p);};
    if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs, t, p);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs, t, p); };
    if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard, t, p);};
    }

    /** Ueberprueft, ob ein BVar, der in einem Guard oder einem Action verwendet wird, 
	<br>deklariert worden ist.*/

  void pruefeBVar(Bvar b, Tr t, String p){
      if (Ist.contains(b.var)) {if (!Soll.contains(b.var)) { Soll.addElement(b.var);};}
      else {msg.addError(101,"BVar: "+b.var+
                  "/ Transition: "+((Statename)t.source).name+" -> "+((Statename)t.target).name+" in State: "+p);};
  };
   /** Ueberprueft den Action auf Verwendung von BVars.*/

    void pruefeAction(Action a, Tr t, String p){
    if (a instanceof ActionBlock)  { for(Aseq as=((ActionBlock)a).aseq;as.tail!=null;as=as.tail) 
	{ pruefeAction(as.head, t, p);};}
    if (a instanceof ActionStmt) {pruefeBool (((ActionStmt)a).stmt, t, p);};
    };

  /** Ueberprueft einen boolschen Block auf Verwendung von BVars.*/

  void pruefeBool(Boolstmt b, Tr t, String p) {
     if (b instanceof BAss)  {pruefeBVar(((BAss)b).ass.blhs, t, p); pruefeGuard(((BAss)b).ass.brhs, t, p);};
     if (b instanceof MTrue)  {pruefeBVar(((MTrue)b).var, t, p);};
     if (b instanceof MFalse) {pruefeBVar(((MFalse)b).var, t, p);};
    };

    /** Die Vectoren Ist und soll werden verglichen. 
<br>Wenn BVars aus dem Vector Ist nicht verwendet werden , wird gewarnt.*/

    void vergleiche(){
	for( int i=0; i<Soll.size();i++) {
	    Ist.removeElement(Soll.elementAt(i));};
        for(int i=0; i<Ist.size(); i++) { msg.addWarning(102,"BVar: "+(String)Ist.elementAt(i));};

    };
}

