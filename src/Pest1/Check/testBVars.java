package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testBVars.java,v 1.7 1998-12-09 14:28:51 swtech11 Exp $
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
    return (msg.getErrorNumber()==0);
  }

  void erstelle_Ist() {
         
     for(BvarList b=sc.bvars; b!=null; b=b.tail){
       if (Ist.contains(b.head.var)) { msg.addError(100,"BVar: "+b.head.var);}
                                else {Ist.addElement(b.head.var);};
        }
   };

  void erstelle_Soll() {

    if (sc.state instanceof Or_State) {testTransOrState((Or_State)sc.state, null, ""); }
    if (sc.state instanceof And_State) {testTransAndState((And_State)sc.state, null, ""); }

  };

  void nextTransInTransList(TrList tl, State _s, String p) {
    pruefeGuard(tl.head.label.guard, tl.head, p);
    pruefeAction(tl.head.label.action, tl.head, p);
    if (tl.tail != null) { nextTransInTransList(tl.tail, _s, p); }

  }
    void pruefeGuard(Guard g, Tr t, String p){
    if (g instanceof GuardBVar)  {pruefeBVar  (((GuardBVar )g).bvar, t, p);};
    if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs, t, p);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs, t, p); };
    if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard, t, p);};
    }

  void pruefeBVar(Bvar b, Tr t, String p){
      if (Ist.contains(b.var)) {if (!Soll.contains(b.var)) { Soll.addElement(b.var);};}
      else {msg.addError(101,"BVar: "+b.var+
                  "/ Transition: "+((Statename)t.source).name+" -> "+((Statename)t.target).name+" in State: "+p);};
  };

    void pruefeAction(Action a, Tr t, String p){
    if (a instanceof ActionBlock)  { for(Aseq as=((ActionBlock)a).aseq;as.tail!=null;as=as.tail) 
	{ pruefeAction(as.head, t, p);};}
    if (a instanceof ActionStmt) {pruefeBool (((ActionStmt)a).stmt, t, p);};
    };

  void pruefeBool(Boolstmt b, Tr t, String p) {
     if (b instanceof BAss)  {pruefeBVar(((BAss)b).ass.blhs, t, p); pruefeGuard(((BAss)b).ass.brhs, t, p);};
     if (b instanceof MTrue)  {pruefeBVar(((MTrue)b).var, t, p);};
     if (b instanceof MFalse) {pruefeBVar(((MFalse)b).var, t, p);};
    };

    void vergleiche(){
	for( int i=0; i<Soll.size();i++) {
	    Ist.removeElement(Soll.elementAt(i));};
        for(int i=0; i<Ist.size(); i++) { msg.addWarning(102,"BVar: "+(String)Ist.elementAt(i));};

    };
}

