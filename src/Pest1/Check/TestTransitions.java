package check;

import absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestTransitions.java,v 1.17 1999-01-25 23:21:43 swtech11 Exp $
 */
class TestTransitions extends ModelCheckBasics {
  Vector newPLV = new Vector(); // Vector fuer die selbst angelegte PathList der States
  Vector newCLV = new Vector(); // Vector fuer die selbst angelegte PathList der Connectoren
  Vector Guardlist = new Vector();
  Vector newLTL; // lokale Transitionsliste (d.h. Transitionen in EINEM State)
  Vector newLCL; // lokale Connectorenliste (d.h. Connectoren in EINEM State)

  TestTransitions(Statechart _s, ModelCheckMsg _m) {
    super(_s,_m);
  }

  // Check-Methode für den Transitionen
  boolean check() {
    int m = msg.getErrorNumber();
    newPLV = getPathListFromState(sc.state);
    newCLV = getConnectorPathListFromState(sc.state);
    if (sc.state instanceof Or_State) {navOrState ((Or_State)sc.state, null,""); }  // null, da es keinen übergeordneten State gibt
    if (sc.state instanceof And_State) {navAndState ((And_State)sc.state, null,""); }
    return ((msg.getErrorNumber()-m)==0);
  }

  // übergebenen Statenamen in der Pfadliste der Statechart suchen und die Anzahl zurückgeben
  int AnzStatenameInPathList(Vector pl, String s) {
    int anz = 0;
    String sn;
    for (int i=0; (i<pl.size()) ; i++) {
      sn  = (String)pl.elementAt(i);
      if (sn.endsWith(s)){ anz++; }
    }
    return anz;
  }

  // übergebenen Statenamen in der Pfadliste der Statechart suchen; TRUE wenn gefunden
  boolean StatenameInPathList(Vector pl, String s) {
    boolean in = false;
    String sn;
    int pos;
    for (int i=0; (i<pl.size()) ; i++) {
      sn  = (String)pl.elementAt(i);
      if (sn.endsWith(s)){ in = true; }
    }
    return in;
  }

  // Name in der übergebenen StateList finden
  boolean NameInThisStateSubstates(StateList _sl, String _s) {
    boolean in = false;
    if (_sl.head.name.name.equals(_s)) { in = true; }
    else if (_sl.tail != null) { in = NameInThisStateSubstates(_sl.tail, _s);}
    return in;
  }

  // Name in der übergebenen StateList finden
  int AnzNameInThisStateSubstates(StateList _sl, String _s, int az) {
    if (_sl.head.name.name.equals(_s)) { az++; }
    if (_sl.tail != null) { az = AnzNameInThisStateSubstates(_sl.tail, _s, az);}
    return az;
  }

  // State in der übergebenen StateList laut Statename finden
  State StateFromStatenameInThisStateSubstates(StateList _sl, String _s) {
    State st = null;
    if (_sl.head.name.name.equals(_s)) {
      st = _sl.head;
    }
    else if (_sl.tail != null) { st = StateFromStatenameInThisStateSubstates(_sl.tail, _s);}
    return st;
  }

  // Name in der übergebenen ConnectorList finden
  boolean ConnectorNameInThisStateConnectorList(ConnectorList _cl, String _s) {
    boolean in = false;
    if (_cl.head.name.name.equals(_s)) { in = true; }
    else if (_cl.tail != null) { in = ConnectorNameInThisStateConnectorList(_cl.tail, _s);}
    return in;
  }

  // Name in der übergebenen ConnectorList finden und Connector zurückgeben
  Connector getConnectorFromNameInThisStateConnectorList(ConnectorList _cl, String _s) {
    Connector in = null;
    if (_cl.head.name.name.equals(_s)) { in = _cl.head; }
    else if (_cl.tail != null) { in = getConnectorFromNameInThisStateConnectorList(_cl.tail, _s);}
    return in;
  }

  // durch TrList durchgehen
  void navTransInTransList(TrList tl, State _s, String p) {
    boolean u1 = false; boolean u2 = false;
    boolean v1 = false; boolean v2 = false; boolean w1 = false; boolean w2 = false;
    boolean i1 = false; boolean i2 = false; boolean j1 = false; boolean j2 = false;
    boolean con1 = false;
    TransE mtr = new TransE();
    ConE co2 = new ConE();

    String z1 = msg.getTrSourceName(tl.head);
    String z2 = msg.getTrTargetName(tl.head);    
    mtr.t = tl.head;
    // Startanker der Transition bearbeiten
    if (tl.head.source instanceof UNDEFINED) { // undefiniert ?
      mtr.s = 0;
      u1 = true;
    }
    else if (tl.head.source instanceof Statename) { // State ?
      mtr.s = 1;
      mtr.sz = 1;
      v1 = !StatenameInPathList(newPLV, z1); // State -> nicht vorhanden ?
      if (v1==false) { i1 = !NameInThisStateSubstates(((Or_State)_s).substates, z1); } // State -> Interlevel ?
    }
    else if (tl.head.source instanceof Conname) { // Connector ?
      mtr.s = 2;
      con1 = true;
      co2 = new ConE();
      co2.name = z1;
      co2.d = "Connector: " +z1 + " in State: " + p;
      newLCL.addElement(co2);
      w1 = !StatenameInPathList(newCLV, z1); // Connector -> nicht vorhanden ?
      // Connector -> Interlevel ?
      if (w1==false) {
        co2.c = getConnectorFromNameInThisStateConnectorList(((Or_State)_s).connectors, z1);
        if (co2.c == null) {j1 = true; }
      }
    }
    else {System.out.println("unbekannter StartAnker");};

    // Zielanker der Transition bearbeiten
    if (tl.head.target instanceof UNDEFINED) { // undefiniert ?
      mtr.z = 0;
      u2 = true;
    }
    else if (tl.head.target instanceof Statename) { // State ?
      mtr.z = 1;
      mtr.zz = 1;
      //v2 = !StatenameInPathList(newPLV, z2);
      int aS = AnzStatenameInPathList(newPLV, z2); // State -> nicht vorhanden ?
      if (aS == 0) { v2 = true; }
      else { // State -> Interlevel ?
        //i2 = !NameInThisStateSubstates(((Or_State)_s).substates, z2); }
        int anz = AnzNameInThisStateSubstates(((Or_State)_s).substates, z2, 0);
        // System.out.println(anz);
        if (anz > 1) { // 2 uneindeutige States auf einem Level
          i2 = false;
          msg.addError(425,"Trans: "+z1+" -> "+z2+" in State: " + p,tl.head);
        }
        else if (anz == 1 & aS ==1) { i2 = false; } //  1 State am richtigen Ort => kein Interlevel
        else if (anz == 0) { i2 = true; } //  1 oder mehr States nur am falschen Ort => Interlevel eindeutig
        else if (anz == 1 & aS >1) { // 1 State richtig und 1 oder mehr States falsch => Interlevel ?
          State st =  StateFromStatenameInThisStateSubstates(((Or_State)_s).substates, z2);
          // System.out.println("Graphischer Test notwendig"+st);
          i2 = !pruefe_coord_IT( st,tl.head);
        }
      }        
    }
    else if (tl.head.target instanceof Conname) { // Connector ?
      mtr.z = 2;
      co2 = new ConE();
      co2.name = z2;
      co2.d = "Connector: " +z2 + " in State: " + p;
      newLCL.addElement(co2);
      w2 = !StatenameInPathList(newCLV, z2); // Connector -> nicht vorhanden ?
      // Connector -> Interlevel ?
      if (w2==false) {
        co2.c = getConnectorFromNameInThisStateConnectorList(((Or_State)_s).connectors, z2);
        if (co2.c == null) {j2 = true; }
      }
      // gleicher Connecntor bei Start und Ziel
      if (con1==true & z1.equals(z2)) { msg.addError(415,"Trans: "+z1+" -> "+z2+" in State: " + p,co2.c); }
    }
    else {System.out.println("unbekannter ZielAnker");};

    String t = new String("Trans: "+z1+" -> "+z2+" in State: " + p);
    mtr.d=t;
    mtr.sn=z1;
    mtr.zn=z2;
    newLTL.addElement(mtr);

    // Auswertung auf undefiniert
    if (u1==true & u2==false) { msg.addError(400,t,tl.head); }
    else if (u1==false & u2==true) { msg.addError(401,t,tl.head); }
    else if (u1==true & u2==true) { msg.addError(402,t,tl.head); }
    // Auswertung auf Nicht-Vorhandenheit von State
    if (v1==true & v2==false) { msg.addError(403,t,tl.head); }
    else if (v1==false & v2==true) { msg.addError(404,t,tl.head); }
    else if (v1==true & v2==true)  { msg.addError(405,t,tl.head); }
    // Auswertung auf Interlevel von State
    if (i1==true & i2==false) { msg.addError(406,t,tl.head); }
    else if (i1==false & i2==true) { msg.addError(407,t,tl.head); }
    else if (i1==true & i2==true)  { msg.addError(408,t,tl.head); }
    // Auswertung auf Nicht-Vorhandenheit von Connectoren
    if (w1==true & w2==false) { msg.addError(409,t,tl.head); }
    else if (w1==false & w2==true) { msg.addError(410,t,tl.head); }
    else if (w1==true & w2==true)  { msg.addError(411,t,tl.head); }
    // Auswertung auf Interlevel von Connectoren
    if (j1==true & j2==false) { msg.addError(412,t,tl.head); }
    else if (j1==false & j2==true) { msg.addError(413,t,tl.head); }
    else if (j1==true & j2==true)  { msg.addError(414,t,tl.head); }

    if (tl.head.label.guard!=null)  {Guardlist.addElement(new GuardE(tl.head.label.guard, tl.head, p));} // DW
    if (tl.tail != null) { navTransInTransList(tl.tail, _s,p); }
  }


  // eine Connectorliste aus den bestehenden States einer Statechart zu extrahieren
  Vector getConnectorPathListFromState(State _s) {
    Vector clv = new Vector();
    if (_s instanceof Or_State) {gC_OrState ((Or_State)_s, "", clv); }
    if (_s instanceof And_State) {gC_AndState ((And_State)_s, "", clv); }
    return clv;
  }
  
  // notwendige Methoden für getConnectorPathListFromState
  void gC_ConnectorInConnectorList(ConnectorList cl, String p, Vector clv) {
    String np = getAddPathPart(p, cl.head.name.name);
    clv.addElement(np);
    if (cl.tail != null) { gC_ConnectorInConnectorList(cl.tail, p, clv); }
  }
  void gC_OrState(Or_State os, String p, Vector clv) {
    String np = getAddPathPart(p, os.name.name);
    if (os.connectors != null) { gC_ConnectorInConnectorList(os.connectors, np, clv); }
    if (os.substates != null) { gC_StateInStateList(os.substates, np, clv); }
  }
  void gC_AndState(And_State as, String p, Vector clv) {
    String np = getAddPathPart(p, as.name.name);
    if (as.substates != null) { gC_StateInStateList(as.substates, np, clv); }
  }
  void gC_StateInStateList(StateList sl, String p, Vector clv) {
    if (sl.head instanceof Or_State) {gC_OrState ((Or_State)sl.head, p, clv); }
    if (sl.head instanceof And_State) {gC_AndState ((And_State)sl.head, p, clv); }
    if (sl.tail != null) { gC_StateInStateList(sl.tail, p, clv); }
  }

  /** Vergleicht die Liste der Guards mit sich selber und sucht nach doppelten Eintraegen.
	<br> Dadurch moechte man den Nichtdeterminismus finden.*/
  void ND(){
   GuardE g;
   for (;Guardlist.size()>0;){
     g=(GuardE)Guardlist.elementAt(0);
     Guardlist.removeElement(g);
     for (int i=0; i<Guardlist.size(); i++){
       String text=vergleicheGuards(g,(GuardE)Guardlist.elementAt(i),"");
       //System.out.println("Text "+text+" S1 "+((Statename)g.t.source).name+" S2 "+((Statename)((GuardE)Guardlist.elementAt(i)).t.source).name);
       if (text!="") {
           if ((g.t.source instanceof Conname) && (((GuardE)Guardlist.elementAt(i)).t.source instanceof Conname) &&
                 (((Conname)g.t.source).name.equals(((Conname)((GuardE)Guardlist.elementAt(i)).t.source).name )))  
                                    {msg.addWarning(416,text);};
           if ((g.t.source instanceof Statename) && (((GuardE)Guardlist.elementAt(i)).t.source instanceof Statename) &&
                 ((((Statename)g.t.source).name).equals(((Statename)((GuardE)Guardlist.elementAt(i)).t.source).name ))) 
	       {String s="Trans1: "+((Statename)g.t.source).name;
                msg.addWarning(416,text, g.t, ((GuardE)Guardlist.elementAt(i)).t, g.ort);};

};

   };
   
   };};

  /** Vergleiche zwei Guards mit sich.*/
  String vergleicheGuards(GuardE _g1, GuardE _g2, String text){
   Guard g1=_g1.g;
   Guard g2=_g2.g;
    if ((g1 instanceof GuardEmpty) &&  (g2 instanceof GuardEmpty)) {
                  text=text+"GuardDummy ";
                   };

   if ((g1 instanceof GuardEvent) &&  (g2 instanceof GuardEvent)){
     if (((GuardEvent)g1).event.name.equals(((GuardEvent)g2).event.name)) {
                  text=text+"GuardEvent: "+((GuardEvent)g1).event.name;
                   };};

   if ((g1 instanceof GuardBVar) && (g2 instanceof GuardBVar)){
     if (((GuardBVar)g1).bvar.var.equals(((GuardBVar)g2).bvar.var)) {
                  text=text+"GuardBVar: "+((GuardBVar)g1).bvar.var;
                   };};

   if ((g1 instanceof GuardCompp) && (g2 instanceof GuardCompp)){
     if ((((GuardCompp)g1).cpath.pathop==((GuardCompp)g2).cpath.pathop)
                  &&  Pathequal(((GuardCompp)g1).cpath.path,((GuardCompp)g2).cpath.path))
                  {text=text+"GuardCompp: "+((GuardCompp)g1).cpath.pathop+" "+((GuardCompp)g1).cpath.path;
                   };};

   if ((g1 instanceof GuardNeg) && (g2 instanceof GuardNeg)){
        String s=vergleicheGuards( new GuardE(((GuardNeg)g1).guard, _g1.t), 
                                   new GuardE(((GuardNeg)g2).guard, _g2.t), "");
               if (s!="") {text="GuardNeg1: "+s;};
                         };

   if ((g1 instanceof GuardCompg) && (g2 instanceof GuardCompg)){
     if (((GuardCompg)g1).cguard.eop ==((GuardCompg)g2).cguard.eop) {
                   String s1=vergleicheGuards( new GuardE(((GuardCompg)g1).cguard.elhs, _g1.t),
                                               new GuardE(((GuardCompg)g2).cguard.elhs, _g2.t), "");
                   String s2=vergleicheGuards( new GuardE(((GuardCompg)g1).cguard.erhs, _g1.t),
                                               new GuardE(((GuardCompg)g2).cguard.erhs, _g2.t), "");
                   if ((s1!="") && (s2!="")) {text=text+"GuardCompg: "+((GuardCompg)g1).cguard.eop+" "+ s1+" " + s2;}
                     else { if ((((GuardCompg)g1).cguard.eop == Compguard.OR) ||
                                (((GuardCompg)g1).cguard.eop == Compguard.EQUIV) ||
                                (((GuardCompg)g1).cguard.eop == Compguard.AND)) {
                       s1=vergleicheGuards( new GuardE(((GuardCompg)g1).cguard.elhs, _g1.t),
                                            new GuardE(((GuardCompg)g2).cguard.erhs, _g2.t), "");
                       s2=vergleicheGuards( new GuardE(((GuardCompg)g1).cguard.erhs, _g1.t),
                                            new GuardE(((GuardCompg)g2).cguard.elhs, _g2.t), "");
                       if ((s1!="") && (s2!="")) {text=text+"GuardCompg: "+((GuardCompg)g1).cguard.eop+" "+ s1+" " + s2;};


                         };};

                   };};

   return text;

  };

  void navOrState(Or_State os, State _s, String p) {
    String np = getAddPathPart(p, os.name.name);
    if (os.trs != null) {
      newLTL = new Vector();
      newLCL = new Vector();      
      navTransInTransList(os.trs, os, np);
      TransAnalyse();
    }
    //System.out.println(np+" vorher: "+Guardlist.size());
    ND();
    //System.out.println(np+" nachher: "+Guardlist.size());
    if (os.substates != null) { navStateInStateList(os.substates, os, np); }
  }

  // weitere Analye der Transitionen im Zusammenspiel mit Connectoren
  void TransAnalyse() {
    Vector tlv = new Vector();
    Vector clv = new Vector();
    TransE m;
    ConE co,co2;
    // lokale TransitionsList passend umgestelten
    for (int i=0; i<newLTL.size(); i++) {
      m = (TransE)newLTL.elementAt(i);
      // nur folgende Transitionen lohnen einer Überprüfung
      if ( (m.s==1 & m.z==2) | (m.s==2 & m.z==1) | (m.s==2 & m.z==2) ) { tlv.addElement(m); }
    }
    // doppelt eingetragene Connectoren entfernen
    for (int j=0; j<newLCL.size(); j++) {
      co = (ConE)newLCL.elementAt(j);
      if (clv.size()==0) { clv.addElement(co); }
      else {
        int c = 0;
        for (int i=0; i<clv.size(); i++) {
          co2 = (ConE)clv.elementAt(i);
          if ( co2.name.equals(co.name) ) { c++;}
        }
        if ( c==0 ) {clv.addElement(co);}
      }
    }
    newLCL = clv;
    // nach Verbindung zu einem State in beide Richtungen suchen
    for (int ii=0; ii<tlv.size(); ii++) {
      workOnCon(tlv);
      for (int i=0; i<tlv.size(); i++) {
        m = (TransE)tlv.elementAt(i);
        if (m.sz == 0) {
          for (int j=0; j<newLCL.size(); j++) {
            co = (ConE)newLCL.elementAt(j);
            if (co.name.equals(m.sn)) { if (co.sz != 0)  { m.sz = co.sz; } }
          }
        }
        if (m.zz == 0) {
          for (int j=0; j<newLCL.size(); j++) {
            co = (ConE)newLCL.elementAt(j);
            if (co.name.equals(m.zn)) { if (co.zz != 0)  { m.zz = co.zz; } }
          }
        }
      }
    }
    // Auswertung der Verbundenheit
    for (int i=0; i<tlv.size(); i++) {
      m = (TransE)tlv.elementAt(i);
      if ( m.sz==0 & m.zz==0 ) { msg.addError(419,m.d,m.t); }
      else if ( m.sz==0 ) { msg.addError(422,m.d,m.t); }
      else if ( m.zz==0 ) { msg.addError(423,m.d,m.t); }
    }
    // Connectorenzyklus
    for (int i=0; i<newLCL.size(); i++) {
      co = (ConE)newLCL.elementAt(i);
      co.count=1;
      testZyklusCon(co,tlv);
      if (co.count>1) { msg.addError(421,co.d,co.c); } // Zyklus
      for (int j=0; j<tlv.size(); j++) { // Zaehler wieder auf Null setzen
        m = (TransE)tlv.elementAt(j);
        m.count=0;
      }
    }
  }

  // Connectorenzyklus suchen
  void testZyklusCon(ConE co, Vector tlv) {
    ConE co2;
    TransE m;
    for (int i=0; i<tlv.size(); i++) {
      m = (TransE)tlv.elementAt(i);
      if (co.name.equals(m.sn) & m.count<=1 & m.z==2) {
        m.count++;
        for (int j=0; j<newLCL.size(); j++) {
          co2 = (ConE)newLCL.elementAt(j);
          if (m.zn.equals(co2.name)) {
            co2.count++;
            testZyklusCon(co2,tlv);
          }
        }  
      }
    }
  }

  // Connectoren die Verbundenheit durch Transitionen zuweisen
  void workOnCon(Vector tlv) {
    ConE co;
    TransE m;
    for (int i=0; i<newLCL.size(); i++) {
      co = (ConE)newLCL.elementAt(i);
      if (co.sz == 0 | co.zz == 0) {
        for (int j=0; j<tlv.size(); j++) {
          m = (TransE)tlv.elementAt(j);
          if (m.zn.equals(co.name)) { if (m.sz != 0) { co.sz = m.sz; } }
          if (m.sn.equals(co.name)) { if (m.zz != 0) { co.zz = m.zz; } }
        }
      }
    }
  }

  boolean pruefe_coord_IT(State s, Tr t) {
    int n=t.points.length-1;  // :-)
    int xt=t.points[n].x;
    int yt=t.points[n].y;
    int xs=s.rect.x;
    int ys=s.rect.y;
    int hs=s.rect.height;
    int ws=s.rect.width;
/*
System.out.println();
System.out.println("xt"+xt);
System.out.println("yt"+yt);
System.out.println("xs"+xs);
System.out.println("ys"+ys);
System.out.println("hs"+hs);
System.out.println("ws"+ws);
System.out.println();
*/

    boolean b=false;
    if (umgebung(xs,xt) && yt>=ys && yt<=(ys+hs)) b=true;
    if (umgebung(ys,yt) && xt>=xs && xt<=(xs+ws)) b=true;
    if (umgebung(xs+ws,xt) && yt>=ys && yt<=(ys+ws)) b=true;
    if (umgebung(ys+hs,yt) && xt>=xs && xt<=(xs+ws)) b=true;
    return b;

  }

  boolean umgebung(int a, int s) {
    int e=2;
    boolean b=false;
    if (((int)Math.abs(a-s))<e) b=true;
//System.out.println(b);
    return b;
  }

}

// Transitionen zur besseren Nutzung speichern
class TransE {
  // Art der Anker
  int s=0;  // 0:UNDEFINED, 1:STATE, 2:CONNECTOR
  int z=0;
  // indirekte Verbundenheit der Anker
  String sn = null;
  String zn = null;
  // Beschreibung: Trans plus Path
  String d = new String();
  int count=0;
  // ist ein State hier oder in der Transition vorher (nachher) vorhanden
  int sz = 0; // 0: noch nicht getestet, 1: JA, 2:NEIN
  int zz = 0;
  // Transition
  Tr t = null;
}

// Connenctoren zur besseren Nutzung speichern
class ConE {
  // indirekte Verbundenheit der Anker
  int sz = 0; // 0: noch nicht getestet, 1: JA, 2:NEIN
  int zz = 0;
  int count = 0;
  // Beschreibung: Connector plus Path
  String d = new String();
  String name = null;
  // Connector
  Connector c;
}

class GuardE {
   Guard g;
   Tr    t;
   String ort="";

GuardE(Guard _g, Tr _t){
     g=_g;
     t=_t;};

GuardE(Guard _g, Tr _t, String _ort){
     g=_g;
     t=_t;
     ort=_ort;};


}
