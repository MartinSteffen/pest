package Check;

import Absyn.*;
import java.util.*;

class testBVars extends test{
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
    //vergleiche();
    msg.addWarning(5,"Root");
    return true;
  }

  void erstelle_Ist() {
     BvarList b=sc.bvars;
     int i;
   
     
     for(i=0;(b!=null) && (i<(2*Ist.size()+1));i++){
       if (Ist.contains(b.head.var)) { msg.addError(100,b.head.var);}
                                else {Ist.addElement(b.head.var);};
        b=b.tail;
        System.out.println(i);
       }
     System.out.println(Ist.size());
     if (Ist.size()<i) {/* Prüfe auf Kreis in BVarList */}
   };

  void erstelle_Soll() {

    if (sc.state instanceof Or_State) {testTransOrState((Or_State)sc.state); }
    if (sc.state instanceof And_State) {testTransAndState((And_State)sc.state); }

  // erstellen einer Liste von Events, die in Guard oder Action vorkommen

  };
  void nextTransInTransList(TrList tl) {
    pruefeGuard(tl.head.label.guard);
    pruefeAction(tl.head.label.action);
    if (tl.tail != null) { nextTransInTransList(tl.tail); }

  }
    void pruefeGuard(Guard g){
    if (g instanceof GuardBVar)  {pruefeBvar  (((GuardBVar )g).bvar);};
    if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs); };
    //  if (g instanceof GuardCompp) {pruefePath  (((GuardCompp)g).cpath.path);}
    if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard);};
    }

    void pruefeAction(Action a){
    if (a instanceof ActionBlock)  { for(Aseq as=((ActionBlock)a).aseq;as.tail!=null;as=as.tail) 
	                                { pruefeAction(as.head);};}
    if (a instanceof ActionStmt) {pruefeBool (((ActionStmt)a).stmt);};
    }


  void pruefeBvar(Bvar b){
      if (Ist.contains(b.var)) {if (!Soll.contains(b.var)) { Soll.addElement(b.var);};}
      else {msg.addError(101,(b.var));};
  };

  void pruefeBool(Boolstmt b) {
    if (b instanceof BAss)  {};
    if (b instanceof MTrue) {};
    if (b instanceof MFalse) {};
    };



  void pruefePath(Path p){
      PathList pl=sc.cnames;
      int i=0;
      for(;pl != null;pl=pl.tail){ 
	  if (p.name==pl.head.name) {i++;};};
      
      if (i==0) {msg.addError(102,(p.name));}
      if (i>1)  {msg.addWarning(103,(p.name));}

      

  };

}

