package check;

import absyn.*;
import java.util.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestTransitions.java,v 1.12 1999-01-18 12:14:21 swtech11 Exp $
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

  // übergebenen Statenamen in der Pfadliste der Statechart suchen; TRUE wenn gefunden
  boolean StatenameInPathList(Vector pl, String s) {
    boolean in = false;
    String sn;
    int pos;
    for (int i=0; (i<pl.size()) ; i++) {
      sn  = (String)pl.elementAt(i);
//      pos = sn.lastIndexOf(".");
//      if (pos>0) {sn = sn.substring(pos+1); }
//      if (sn.equals(s)){ in = true; }
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

  // Name in der übergebenen ConnectorList finden
  boolean ConnectorNameInThisStateConnectorList(ConnectorList _cl, String _s) {
    boolean in = false;
    if (_cl.head.name.name.equals(_s)) { in = true; }
    else if (_cl.tail != null) { in = ConnectorNameInThisStateConnectorList(_cl.tail, _s);}
    return in;
  }

  void navTransInTransList(TrList tl, State _s, String p) {
    boolean u1 = false; boolean u2 = false;
    boolean v1 = false; boolean v2 = false; boolean w1 = false; boolean w2 = false;
    boolean i1 = false; boolean i2 = false; boolean j1 = false; boolean j2 = false;
    boolean con1 = false;
    String z1 = new String();
    String z2 = new String();
    TransE mtr = new TransE();
    ConE co2 = new ConE();

    // Startanker der Transition bearbeiten
    if (tl.head.source instanceof UNDEFINED) { // undefiniert ?
      mtr.s = 0;
      z1 = new String("UNDEFINED");
      u1 = true;
    }
    else if (tl.head.source instanceof Statename) { // State ?
      mtr.s = 1;
      mtr.sz = 1;
      z1 = new String(((Statename)tl.head.source).name);
      v1 = !StatenameInPathList(newPLV, z1); // State -> nicht vorhanden ?
      if (v1==false) { i1 = !NameInThisStateSubstates(((Or_State)_s).substates, z1); } // State -> Interlevel ?
    }
    else if (tl.head.source instanceof Conname) { // Connector ?
      mtr.s = 2;
      con1 = true;
      z1 = new String(((Conname)tl.head.source).name);
      co2 = new ConE();
      co2.name = z1;
      co2.d = "Connector: " +z1 + " in State: " + p;
      newLCL.addElement(co2);
      w1 = !StatenameInPathList(newCLV, z1); // Connector -> nicht vorhanden ?
      if (w1==false) { j1 = !ConnectorNameInThisStateConnectorList(((Or_State)_s).connectors, z1); } // Connector -> Interlevel ?
    }
    else {System.out.println("unbekannter StartAnker");};

    // Zielanker der Transition bearbeiten
    if (tl.head.target instanceof UNDEFINED) { // undefiniert ?
      mtr.z = 0;
      z2 = new String("UNDEFINED");
      u2 = true;
    }
    else if (tl.head.target instanceof Statename) { // State ?
      mtr.z = 1;
      mtr.zz = 1;
      z2 = new String(((Statename)tl.head.target).name);
      v2 = !StatenameInPathList(newPLV, z2); // State -> nicht vorhanden ?
      if (v2==false) { i2 = !NameInThisStateSubstates(((Or_State)_s).substates, z2); } // State -> Interleven ?
    }
    else if (tl.head.target instanceof Conname) { // Connector ?
      mtr.z = 2;
      z2 = new String(((Conname)tl.head.target).name);
      co2 = new ConE();
      co2.name = z2;
      co2.d = "Connector: " +z2 + " in State: " + p;
      newLCL.addElement(co2);
      if (con1==true & z1.equals(z2)) { msg.addError(415,"Trans: "+z1+" -> "+z2+" in State: " + p); } // gleicher Connecntor bei Start und Ziel
      w2 = !StatenameInPathList(newCLV, z2); // Connector -> nicht vorhanden ?
      if (w2==false) { j2 = !ConnectorNameInThisStateConnectorList(((Or_State)_s).connectors, z2); } // Connector -> Interlevel ?
    }
    else {System.out.println("unbekannter ZielAnker");};

    String t = new String("Trans: "+z1+" -> "+z2+" in State: " + p);
    mtr.d=t;
    mtr.sn=z1;
    mtr.zn=z2;
    newLTL.addElement(mtr);

    // Auswertung auf undefiniert
    if (u1==true & u2==false) { msg.addError(400,t); }
    else if (u1==false & u2==true) { msg.addError(401,t); }
    else if (u1==true & u2==true) { msg.addError(402,t); }
    // Auswertung auf Nicht-Vorhandenheit von State
    if (v1==true & v2==false) { msg.addError(403,t); }
    else if (v1==false & v2==true) { msg.addError(404,t); }
    else if (v1==true & v2==true)  { msg.addError(405,t); }
    // Auswertung auf Interlevel von State
    if (i1==true & i2==false) { msg.addError(406,t); }
    else if (i1==false & i2==true) { msg.addError(407,t); }
    else if (i1==true & i2==true)  { msg.addError(408,t); }
    // Auswertung auf Nicht-Vorhandenheit von Connectoren
    if (w1==true & w2==false) { msg.addError(409,t); }
    else if (w1==false & w2==true) { msg.addError(410,t); }
    else if (w1==true & w2==true)  { msg.addError(411,t); }
    // Auswertung auf Interlevel von Connectoren
    if (j1==true & j2==false) { msg.addError(412,t); }
    else if (j1==false & j2==true) { msg.addError(413,t); }
    else if (j1==true & j2==true)  { msg.addError(414,t); }

    if (tl.head.label.guard!=null)  {Guardlist.addElement(new GuardE(tl.head.label.guard, tl.head));} // DW
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
	       {msg.addWarning(416,text);};

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
      if ( m.sz==0 & m.zz==0 ) { msg.addError(419,m.d); }
      else if ( m.sz==0 ) { msg.addError(422,m.d); }
      else if ( m.zz==0 ) { msg.addError(423,m.d); }
    }
    // Connectorenzyklus
    for (int i=0; i<newLCL.size(); i++) {
      co = (ConE)newLCL.elementAt(i);
      co.count=1;
      testZyklusCon(co,tlv);
      if (co.count>1) { msg.addError(421,co.d); } // Zyklus
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
}

class GuardE {
   Guard g;
   Tr    t;

GuardE(Guard _g, Tr _t){
     g=_g;
     t=_t;};}
