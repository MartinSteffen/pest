package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testBVars.java,v 1.5 1998-12-04 11:11:58 swtech11 Exp $
 */
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

  boolean check() {
    erstelle_Ist();
    erstelle_Soll();
    vergleiche();
    //msg.addWarning(5,"Root");
    return true;
  }

  void erstelle_Ist() {
         
     for(BvarList b=sc.bvars; b!=null; b=b.tail){
       if (Ist.contains(b.head.var)) { msg.addError(100,"BVar: "+b.head.var);}
                                else {Ist.addElement(b.head.var);};
        }
   };

  void erstelle_Soll() {

    if (sc.state instanceof Or_State) {testTransOrState((Or_State)sc.state); }
    if (sc.state instanceof And_State) {testTransAndState((And_State)sc.state); }

  };

  void nextTransInTransList(TrList tl) {
    pruefeGuard(tl.head.label.guard, tl.head);
    pruefeAction(tl.head.label.action, tl.head);
    if (tl.tail != null) { nextTransInTransList(tl.tail); }

  }
    void pruefeGuard(Guard g, Tr t){
    if (g instanceof GuardBVar)  {pruefeBVar  (((GuardBVar )g).bvar, t);};
    if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs, t);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs, t); };
    if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard, t);};
    }

  void pruefeBVar(Bvar b, Tr t){
      if (Ist.contains(b.var)) {if (!Soll.contains(b.var)) { Soll.addElement(b.var);};}
      else {msg.addError(101,"BVar: "+b.var+"/ Statename: "+((Statename)t.source).name+" -> "+((Statename)t.target).name);};
  };

    void pruefeAction(Action a, Tr t){
    if (a instanceof ActionBlock)  { for(Aseq as=((ActionBlock)a).aseq;as.tail!=null;as=as.tail) 
	{ pruefeAction(as.head, t);};}
    if (a instanceof ActionStmt) {pruefeBool (((ActionStmt)a).stmt, t);};
    };

  void pruefeBool(Boolstmt b, Tr t) {
     if (b instanceof BAss)  {pruefeBVar(((BAss)b).ass.blhs, t); pruefeGuard(((BAss)b).ass.brhs, t);};
     if (b instanceof MTrue)  {pruefeBVar(((MTrue)b).var, t);};
     if (b instanceof MFalse) {pruefeBVar(((MFalse)b).var, t);};
    };

    void vergleiche(){
	for( int i=0; i<Soll.size();i++) {
	    Ist.removeElement(Soll.elementAt(i));};
        for(int i=0; i<Ist.size(); i++) { msg.addWarning(102,"BVar: "+(String)Ist.elementAt(i));};

    };
}

