package Check;

import Absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testEvents.java,v 1.7 1998-12-09 14:28:52 swtech11 Exp $
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
    //msg.addWarning(2,"Root");
    return (msg.getErrorNumber()==0);
  }
  void erstelle_Ist() {
     for(SEventList e=sc.events; e!=null;e=e.tail){
        if (Ist.contains(e.head.name)) {msg.addError(200,"Event: "+e.head.name);}
          else {Ist.addElement(e.head.name);};}
     
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
    if (g instanceof GuardEvent) {pruefeEvent (((GuardEvent)g).event, t, p);};
    if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs, t, p);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs, t, p); };
    if (g instanceof GuardCompp) {pruefePath  (((GuardCompp)g).cpath.path, t, p);}
    if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard, t, p);};
    }

  void pruefeEvent(SEvent e, Tr t, String p){
      if (Ist.contains(e.name)) {if (!Soll.contains(e.name)) { Soll.addElement(e.name);};}
      else {msg.addError(201,"Event: "+e.name+
                           "/ Transition: "+((Statename)t.source).name+" -> "+((Statename)t.target).name+
                           " in State: "+p);};
  };

     void pruefeAction(Action a, Tr t, String p){
    if (a instanceof ActionBlock)  { for(Aseq as=((ActionBlock)a).aseq;as.tail!=null;as=as.tail) 
	{ pruefeAction(as.head, t, p);};}
    if (a instanceof ActionEvt) {pruefeEvent (((ActionEvt)a).event, t, p);};
    }

 
  void pruefePath(Path p, Tr t, String s){
      PathList pl=sc.cnames;
      int i=0;
      for(;pl != null;pl=pl.tail){ 
	  if (p==pl.head) {i++;};};
      
      if (i==0) {msg.addError(203,"Statename: "+p+"/ Transition: "
                               +((Statename)t.source).name+" -> "+((Statename)t.target).name+
                           " in State: "+s);}
      if (i>1)  {msg.addWarning(204,"Statename: "+p+"/ Transition: "
                               +((Statename)t.source).name+" -> "+((Statename)t.target).name+
                           " in State: "+s);}
  };

    void vergleiche(){
	for( int i=0; i<Soll.size();i++) {
	    Ist.removeElement(Soll.elementAt(i));};
        for(int i=0; i<Ist.size(); i++) { msg.addWarning(202,(String)Ist.elementAt(i));};

    };}






