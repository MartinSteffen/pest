package check;

import absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestBVars.java,v 1.13 1999-01-20 15:06:54 swtech11 Exp $
 */

/** Diese Testklasse testet, ob alle BVars deklariert worden sind, 
    <br>ob die Deklarierten eindeutig sind und ob sie alle verwendet werden.*/

class TestBVars extends ModelCheckBasics{
  private Vector Ist;
  private Vector GSoll;
  private Vector ASoll;

  TestBVars() {
    super();
    Ist=new Vector();
    GSoll=new Vector();
    ASoll=new Vector();
  }
  TestBVars(Statechart s, ModelCheckMsg m) {
    super(s,m);
    Ist=new Vector();
    GSoll=new Vector();
    ASoll=new Vector();
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
       if (equalString(Ist, b.head.var)) { msg.addError(100,"BVar: "+b.head.var);}
                                else {
				    if (b.head.var.equals("")) {msg.addError(106,"");};
                             Ist.addElement(new BVarC(b.head, "Def.Liste"));};
        }
   };
    /** Die Methode ueberprueft saemtliche Transitionen auf eine Verwendung von BVars.
	<br>Es werden alle BVarnamen in einen Vector Soll geschrieben.*/

  void erstelle_Soll() {

    if (sc.state instanceof Or_State) {navOrState((Or_State)sc.state, null, ""); }
    if (sc.state instanceof And_State) {navAndState((And_State)sc.state, null, ""); }

  };

  void navTransInTransList(TrList tl, State _s, String p) {
    pruefeGuard(tl.head.label.guard, tl.head, p);
    pruefeAction(tl.head.label.action, tl.head, p);
    if (tl.tail != null) { navTransInTransList(tl.tail, _s, p); }

  }

    /** Ueberprueft den Guard auf Verwendung von BVars und ob der nur Guardelemente verwendet worden sind.*/

    void pruefeGuard(Guard g, Tr t, String p){
    if (g instanceof GuardBVar)  {pruefeBVar  (((GuardBVar )g).bvar, t, p, 1);}
      else {
      if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs, t, p);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs, t, p); }
        else {
        if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard, t, p);}
          else {
          if ((g instanceof GuardEmpty) || (g instanceof GuardEvent) ||
             (g instanceof GuardCompp)) {}
	  else {
	      if (g instanceof GuardUndet) {msg.addError(424, t, p);}
               else {msg.addError(417, t, p); };
	  };};};};};



    /** Ueberprueft, ob ein BVar, der in einem Guard oder einem Action verwendet wird, 
	<br>deklariert worden ist.*/

  void pruefeBVar(Bvar b, Tr t, String p, int i){

      if (equalString(Ist, b.var)) {
          if ((!equalString(GSoll,b.var)) && (i==1))  {GSoll.addElement(new BVarC(b, t, p));
};
           if ((!equalString(ASoll, b.var)) && (i==2)) {ASoll.addElement(new BVarC(b, t, p));
	  };}
      else {msg.addError(101,"BVar: "+b.var, t, p);};
  };



   /** Ueberprueft den Action auf Verwendung von BVars und ob nur Actionelemente verwendet werden.*/

    void pruefeAction(Action a, Tr t, String p){
    if (a instanceof ActionBlock)  { if ((((ActionBlock)a).aseq)!=null) {
            Aseq as=((ActionBlock)a).aseq;
            for(; (as.tail!=null); as=as.tail) 
	      { pruefeAction(as.head, t, p);}
	    pruefeAction(as.head, t, p);}
      else {msg.addError(24, t , p);};
      }
      else {
      if (a instanceof ActionStmt) {pruefeBool (((ActionStmt)a).stmt, t, p);}
        else {
          if ((a instanceof ActionEvt) || (a instanceof ActionEmpty)) {}
            else {msg.addError(418, t, p);};
    }; }; };

  /** Ueberprueft einen boolschen Block auf Verwendung von BVars.*/

  void pruefeBool(Boolstmt b, Tr t, String p) {
     if (b instanceof BAss)  {pruefeBVar(((BAss)b).ass.blhs, t, p, 2); pruefeGuard(((BAss)b).ass.brhs, t, p);}
       else {
       if (b instanceof MTrue)  {pruefeBVar(((MTrue)b).var, t, p, 2);}
         else {
         if (b instanceof MFalse) {pruefeBVar(((MFalse)b).var, t, p, 2);}
           else { msg.addError(103, t, p);};
     };};};

    /** Die Vectoren Ist und soll werden verglichen. 
<br>Wenn BVars aus dem Vector Ist nicht verwendet werden , wird gewarnt.*/

    void vergleiche(){
	for( int i=0; i<ASoll.size();i++) { 
            if (!equalString_r(GSoll, ((BVarC)ASoll.elementAt(i)).b.var)) {
                 msg.addWarning(105,"BVar: "+((BVarC)ASoll.elementAt(i)).b.var,
                                             ((BVarC)ASoll.elementAt(i)).t,
                                             ((BVarC)ASoll.elementAt(i)).ort );};
};

        for(int i=0; i<GSoll.size(); i++) {
                 equalString_r(Ist, ((BVarC)GSoll.elementAt(i)).b.var); 
                 msg.addWarning(104,"BVar: "+((BVarC)GSoll.elementAt(i)).b.var,
                                             ((BVarC)GSoll.elementAt(i)).t,
                                             ((BVarC)GSoll.elementAt(i)).ort );};
	for (int i=0; i<ASoll.size(); i++){
	    equalString_r(Ist, ((BVarC)ASoll.elementAt(i)).b.var);}
            for(int i=0; i<Ist.size(); i++) {
                 msg.addWarning(102,"BVar: "+((BVarC)Ist.elementAt(i)).b.var+" : "+((BVarC)Ist.elementAt(i)).ort);};
    };
}


