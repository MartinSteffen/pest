package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testEvents.java,v 1.4 1998-12-03 21:58:10 swtech11 Exp $
 */
class testEvents extends modelCheckBasics{
  private Vector Ist;
  private Vector Soll;

  testEvents() {
    super();
    Ist=new Vector();
    Soll=new Vector();
  }

  testEvents(Statechart s, modelCheckMsg m) {
    super(s,m);
    Ist=new Vector();
    Soll=new Vector();
  }

  boolean check() {
    erstelle_Ist();
    erstelle_Soll();
    vergleiche();
    msg.addWarning(2,"Root");
    return true;
  }
  void erstelle_Ist() {
     for(SEventList e=sc.events; e!=null;e=e.tail){
        if (Ist.contains(e.head.name)) {msg.addError(200,e.head.name);}
          else {Ist.addElement(e.head.name);};}
     
   };

  void erstelle_Soll() {
    if (sc.state instanceof Or_State) {testTransOrState((Or_State)sc.state); }
    if (sc.state instanceof And_State) {testTransAndState((And_State)sc.state); }

  };


 void nextTransInTransList(TrList tl) {
    pruefeGuard(tl.head.label.guard, tl.head);
    // pruefeAction(tl.head.label.action, tl.head);
    if (tl.tail != null) { nextTransInTransList(tl.tail); }

  }
    void pruefeGuard(Guard g, Tr t){
    if (g instanceof GuardEvent) {pruefeEvent (((GuardEvent)g).event,t);};
    if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs,t);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs,t); };
    if (g instanceof GuardCompp) {pruefePath  (((GuardCompp)g).cpath.path,t);}
    if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard,t);};
    }

  void pruefeEvent(SEvent e,Tr t){
      if (Ist.contains(e.name)) {if (!Soll.contains(e.name)) { Soll.addElement(e.name);};}
      else {msg.addError(201,e.name+":"+((Statename)t.source).name+" -> "+((Statename)t.target).name);};
  };

    /*     void pruefeAction(Action a,Tr t){
    if (a instanceof ActionBlock)  { for(Aseq as=((ActionBlock)a).aseq;as.tail!=null;as=as.tail) 
	{ pruefeAction(as.head,t);};}
    if (a instanceof ActionEvent) {pruefeEvent (((ActionEvent)a).event,t);};
    }*/

 
  void pruefePath(Path p, Tr t){
      PathList pl=sc.cnames;
      int i=0;
      for(;pl != null;pl=pl.tail){ 
	  if (p.name==pl.head.name) {i++;};};
      
      if (i==0) {msg.addError(203,p.name+":"+((Statename)t.source).name+" -> "+((Statename)t.target).name);}
      if (i>1)  {msg.addWarning(204,p.name+":"+((Statename)t.source).name+" -> "+((Statename)t.target).name);}
  };

    void vergleiche(){
	for( int i=0; i<Soll.size();i++) {
	    Ist.removeElement(Soll.elementAt(i));};
        for(int i=0; i<Ist.size(); i++) { msg.addWarning(202,(String)Ist.elementAt(i));};

    };


 
}
