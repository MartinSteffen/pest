package check;

import absyn.*;
import java.util.*;


/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: TestTransitions.java,v 1.5 1998-12-30 18:20:34 swtech11 Exp $
 */
class TestTransitions extends ModelCheckBasics {
  Vector newPLV = new Vector(); // Vector fuer die selbst angelegte PathList der States
  Vector newCLV = new Vector(); // Vector fuer die selbst angelegte PathList der Connectoren  Vector Guardlist =new Vector();

  TestTransitions(Statechart _s, ModelCheckMsg _m) {
    super(_s,_m);
    // printOut = true;
  }

  boolean check() {
    int m=msg.getErrorNumber();
    newPLV = getPathListFromState(sc.state);
    newCLV = getConnectorPathListFromState(sc.state);
    if (sc.state instanceof Or_State) {testTransOrState ((Or_State)sc.state, null,""); }  // null, da es keinen �bergeordneten State gibt
    if (sc.state instanceof And_State) {testTransAndState ((And_State)sc.state, null,""); }
    if (sc.state instanceof Basic_State) {testTransBasicState ((Basic_State)sc.state, null,""); }    ND();

    msg.addWarning(4,"bei allen Transitionen");
    return ((msg.getErrorNumber()-m)==0);
  }


  // �bergebenen Statenamen in der Pfadliste der Statechart suchen; TRUE wenn gefunden
  boolean StatenameInPathList(Vector pl, String s) {
    boolean in = false;
    String sn;
    int pos;
    for (int i=0; (i<pl.size()) ; i++) {
      sn  = (String)pl.elementAt(i);
      pos = sn.lastIndexOf(".");
      if (pos>0) {sn = sn.substring(pos+1); }
      if (sn.equals(s)){ in = true; }
    }
    return in;
  }

  boolean NameInThisStateSubstates(StateList _sl, String _s) {
    boolean in = false;
    if (_sl.head.name.name.equals(_s)) { in = true; }
    else if (_sl.tail != null) { in = NameInThisStateSubstates(_sl.tail, _s);}
    return in;
  }

  boolean ConnectorNameInThisStateConnectorList(ConnectorList _cl, String _s) {
    boolean in = false;
    if (_cl.head.name.name.equals(_s)) { in = true; }
    else if (_cl.tail != null) { in = ConnectorNameInThisStateConnectorList(_cl.tail, _s);}
    return in;
  }

  void nextTransInTransList(TrList tl, State _s, String p) {
    boolean u1 = false; boolean u2 = false;
    boolean v1 = false; boolean v2 = false; boolean w1 = false; boolean w2 = false;
    boolean i1 = false; boolean i2 = false; boolean j1 = false; boolean j2 = false;
    boolean con1 = false;
    String z1 = new String();
    String z2 = new String();
    // Startanker der Transition bearbeiten
    if (tl.head.source instanceof UNDEFINED) { // undefiniert ?
      z1 = new String("UNDEFINED");
      u1 = true;
    }
    else if (tl.head.source instanceof Statename) { // State ?
      z1 = new String(((Statename)tl.head.source).name);
      v1 = !StatenameInPathList(newPLV, z1); // State -> nicht vorhanden ?
      if (v1==false) { i1 = !NameInThisStateSubstates(((Or_State)_s).substates, z1); } // State -> Interlevel ?
    }
    else if (tl.head.source instanceof Conname) { // Connector ?
      con1 = true;
      z1 = new String(((Conname)tl.head.source).name);
      w1 = !StatenameInPathList(newCLV, z1); // Connector -> nicht vorhanden ?
      if (w1==false) { j1 = !ConnectorNameInThisStateConnectorList(((Or_State)_s).connectors, z1); } // Connector -> Interlevel ?
    }
    else {System.out.println("unbekannter StartAnker");};
    

    // Zielanker der Transition bearbeiten
    if (tl.head.target instanceof UNDEFINED) { // undefiniert ?
      z2 = new String("UNDEFINED");
      u2 = true;
    }
    else if (tl.head.target instanceof Statename) { // State ?
      z2 = new String(((Statename)tl.head.target).name);
      v2 = !StatenameInPathList(newPLV, z2); // State -> nicht vorhanden ?
      if (v2==false) { i2 = !NameInThisStateSubstates(((Or_State)_s).substates, z2); } // State -> Interleven ?
    }
    else if (tl.head.target instanceof Conname) { // Connector ?
      z2 = new String(((Conname)tl.head.target).name);
      if (con1==true & z1.equals(z2)) { msg.addError(415,"Trans: ("+z1+") -> ("+z2+") in State: " + p); } // gleicher Connecntor bei Start und Ziel
      w2 = !StatenameInPathList(newCLV, z2); // Connector -> nicht vorhanden ?
      if (w2==false) { j2 = !ConnectorNameInThisStateConnectorList(((Or_State)_s).connectors, z2); } // Connector -> Interlevel ?
    }
    else {System.out.println("unbekannter ZielAnker");};


    // Auswertung auf undefiniert
    if (u1==true & u2==false) { msg.addError(400,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    else if (u1==false & u2==true) { msg.addError(401,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    else if (u1==true & u2==true) { msg.addError(402,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    // Auswertung auf Nicht-Vorhandenheit von State
    if (v1==true & v2==false) { msg.addError(403,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    else if (v1==false & v2==true) { msg.addError(404,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    else if (v1==true & v2==true)  { msg.addError(405,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    // Auswertung auf Interlevel von State
    if (i1==true & i2==false) { msg.addError(406,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    else if (i1==false & i2==true) { msg.addError(407,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    else if (i1==true & i2==true)  { msg.addError(408,"Trans: "+z1+" -> "+z2+" in State: " + p); }
    // Auswertung auf Nicht-Vorhandenheit von Connectoren
    if (w1==true & w2==false) { msg.addError(409,"Trans: ("+z1+") -> "+z2+" in State: " + p); }
    else if (w1==false & w2==true) { msg.addError(410,"Trans: "+z1+" -> ("+z2+") in State: " + p); }
    else if (w1==true & w2==true)  { msg.addError(411,"Trans: ("+z1+") -> ("+z2+") in State: " + p); }
    // Auswertung auf Interlevel von Connectoren
    if (j1==true & j2==false) { msg.addError(412,"Trans: ("+z1+") -> "+z2+" in State: " + p); }
    else if (j1==false & j2==true) { msg.addError(413,"Trans: "+z1+" -> ("+z2+") in State: " + p); }
    else if (j1==true & j2==true)  { msg.addError(414,"Trans: ("+z1+") -> ("+z2+") in State: " + p); }


    if (printOut == true) { System.out.println("Trans: "+z1+" -> "+z2); }    if (tl.head.label.guard!=null)  {Guardlist.addElement(tl.head.label.guard);} // DW
    if (tl.tail != null) { nextTransInTransList(tl.tail, _s,p); }
  }


  // eine Connectorliste aus den bestehenden States einer Statechart zu extrahieren
  Vector getConnectorPathListFromState(State _s) {
    Vector clv = new Vector();
    if (_s instanceof Or_State) {coOrState ((Or_State)_s, "", clv); }
    if (_s instanceof And_State) {coAndState ((And_State)_s, "", clv); }

    return clv;
  }

  // notwendige Methoden f�r getConnectorPathListFromState
  void coConnectorInConnectorList(ConnectorList cl, String p, Vector clv) {
    String np = getAddPathPart(p, cl.head.name.name);
    clv.addElement(np);
    if (cl.tail != null) { coConnectorInConnectorList(cl.tail, p, clv); }
  }
  void coOrState(Or_State os, String p, Vector clv) {
    String np = getAddPathPart(p, os.name.name);
    if (os.connectors != null) { coConnectorInConnectorList(os.connectors, np, clv); }
    if (os.substates != null) { coNextStateInStateList(os.substates, np, clv); }
  }
  void coAndState(And_State as, String p, Vector clv) {
    String np = getAddPathPart(p, as.name.name);
    if (as.substates != null) { coNextStateInStateList(as.substates, np, clv); }
  }
  void coNextStateInStateList(StateList sl, String p, Vector clv) {
    if (sl.head instanceof Or_State) {coOrState ((Or_State)sl.head, p, clv); }
    if (sl.head instanceof And_State) {coAndState ((And_State)sl.head, p, clv); }
    if (sl.tail != null) { coNextStateInStateList(sl.tail, p, clv); }
  }
  // DW
  void ND(){
   Guard g;
   for (;Guardlist.size()>0;){
     g=(Guard)Guardlist.elementAt(0);
     Guardlist.removeElement(g);
     for (int i=0; i<Guardlist.size(); i++){
       String text=vergleicheGuards(g,(Guard)Guardlist.elementAt(i),"");
       if (text!="") {msg.addWarning(416,text);};};

   };
  };

  // DW

  String vergleicheGuards(Guard g1, Guard g2, String text){
   if ((g1 instanceof GuardEvent) &&  (g2 instanceof GuardEvent)){
     if (((GuardEvent)g1).event.name==((GuardEvent)g2).event.name) {
                  text=text+"GuardEvent: "+((GuardEvent)g1).event.name;
                   };};

   if ((g1 instanceof GuardBVar) && (g2 instanceof GuardBVar)){
     if (((GuardBVar)g1).bvar.var==((GuardBVar)g2).bvar.var) {
                  text=text+"GuardBVar: "+((GuardBVar)g1).bvar.var;
                   };};

   if ((g1 instanceof GuardCompp) && (g2 instanceof GuardCompp)){
     if ((((GuardCompp)g1).cpath.pathop==((GuardCompp)g2).cpath.pathop)
                  &&  Pathequal(((GuardCompp)g1).cpath.path,((GuardCompp)g2).cpath.path))
                  {text=text+"GuardCompp: "+((GuardCompp)g1).cpath.pathop+" "+((GuardCompp)g1).cpath.path;
                   };};

   if ((g1 instanceof GuardNeg) && (g2 instanceof GuardNeg)){
        String s=vergleicheGuards(((GuardNeg)g1).guard,((GuardNeg)g2).guard,"");
               if (s!="") {text="GuardNeg1: "+s;};
                         };

   if ((g1 instanceof GuardCompg) && (g2 instanceof GuardCompg)){
     if (((GuardCompg)g1).cguard.eop ==((GuardCompg)g2).cguard.eop) {
                   String s1=vergleicheGuards(((GuardCompg)g1).cguard.elhs,((GuardCompg)g2).cguard.elhs,"");
                   String s2=vergleicheGuards(((GuardCompg)g1).cguard.erhs,((GuardCompg)g2).cguard.erhs,"");
                   if ((s1!="") && (s2!="")) {text=text+"GuardCompg: "+((GuardCompg)g1).cguard.eop+" "+ s1+" " + s2;}
                     else { if ((((GuardCompg)g1).cguard.eop == Compguard.OR) || (((GuardCompg)g1).cguard.eop == Compguard.AND)) {
                       s1=vergleicheGuards(((GuardCompg)g1).cguard.elhs,((GuardCompg)g2).cguard.erhs,"");
                       s2=vergleicheGuards(((GuardCompg)g1).cguard.erhs,((GuardCompg)g2).cguard.elhs,"");
                       if ((s1!="") && (s2!="")) {text=text+"GuardCompg: "+((GuardCompg)g1).cguard.eop+" "+ s1+" " + s2;};


                         };};

                   };};

   return text;

  };

}
