package check;

import absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestEvents.java,v 1.7 1999-01-06 15:58:41 swtech11 Exp $
 */
/** Diese Testklasse testet, ob alle Events deklariert worden sind, 
    <br>ob die deklarierten eindeutig sind und ob sie alle verwendet werden.*/

class TestEvents extends ModelCheckBasics{
  private Vector Ist;
  private Vector Soll;

  TestEvents() {
    super();
    Ist=new Vector();
    Soll=new Vector();
  }

  TestEvents(Statechart s, ModelCheckMsg m) {
    super(s,m);
    Ist=new Vector();
    Soll=new Vector();
  }

 /** Die Methode check ueberprueft die Statechart auf Fehler bzgl. Events.*/

  boolean check() {
    int m=msg.getErrorNumber();
    erstelle_Ist();
    erstelle_Soll();
    vergleiche();
    return ((msg.getErrorNumber()-m)==0);
  }
   /** Die Methode erstellt einen Vector Ist der deklarierten Events, 
	<br>dabei wird ueberprueft, ob alle Namen eindeutig sind.*/

  void erstelle_Ist() {
     for(SEventList e=sc.events; e!=null;e=e.tail){
        if (Ist.contains(e.head.name)) {msg.addError(200,"Event: "+e.head.name);}
          else {Ist.addElement(e.head.name);};}
        };

/** Die Methode ueberprueft saemtliche Transitionen auf eine Verwendung von Events.
	<br>Es werden alle Eventnamen in einen Vector Soll geschrieben.*/

  void erstelle_Soll() {
    if (sc.state instanceof Or_State) {navOrState((Or_State)sc.state, null, ""); }
    if (sc.state instanceof And_State) {navAndState((And_State)sc.state, null, ""); }

  };


 void navTransInTransList(TrList tl, State _s, String p) {
    pruefeGuard(tl.head.label.guard, tl.head, p);
    pruefeAction(tl.head.label.action, tl.head, p);
    if (tl.tail != null) { navTransInTransList(tl.tail, _s, p); }

  }  

 /** Ueberprueft den Guard auf Verwendung von Events.*/
 
    void pruefeGuard(Guard g, Tr t, String p){
    if (g instanceof GuardEvent) {pruefeEvent (((GuardEvent)g).event, t, p);};
    if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs, t, p);
                                  pruefeGuard (((GuardCompg)g).cguard.erhs, t, p); };
    if (g instanceof GuardCompp) {pruefePath  (((GuardCompp)g).cpath.path, t, p);}
    if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard, t, p);};
    }

/** Ueberprueft, ob ein Event deklariert worden ist.*/

  void pruefeEvent(SEvent e, Tr t, String p){
      if (Ist.contains(e.name)) {if (!Soll.contains(e.name)) { Soll.addElement(e.name);};}
      else {msg.addError(201,"Event: "+e.name, t, p);};
  };

  /** Ueberprueft den Action auf Verwendung von Events.*/

     void pruefeAction(Action a, Tr t, String p){
    if (a instanceof ActionBlock)  { if ((((ActionBlock)a).aseq)!=null)
for(Aseq as=((ActionBlock)a).aseq; (as.tail!=null); as=as.tail) 
	{ pruefeAction(as.head, t, p);}

    else {msg.addError(24, t , p);};
    }
    if (a instanceof ActionEvt) {pruefeEvent (((ActionEvt)a).event, t, p);};
    }

  /** Ueberprueft einen Pfad auf Existenz.*/
 
  void pruefePath(Path p, Tr t, String s){
      PathList pl=sc.cnames;
      
      
      boolean b=false;
 
      for(; ((pl != null) && (!b)) ; pl=pl.tail){ 
          if (PathtoString(pl.head).equals(PathtoString(p))) {b=true;};}              
          
      
      if (b==false) {msg.addError(203,"Pfad: "+PathtoString(p), t, s);}
     
  };

 /** Die Vectoren Ist und soll werden verglichen. 
<br>Wenn Events aus dem Vector Ist nicht verwendet werden , wird gewarnt.*/

    void vergleiche(){
	for( int i=0; i<Soll.size();i++) {
	    Ist.removeElement(Soll.elementAt(i));};
        for(int i=0; i<Ist.size(); i++) { msg.addWarning(202,(String)Ist.elementAt(i));};

    };}


