package check;

import absyn.*;
import gui.*;
import editor.*;
import java.util.*;
import java.awt.*;

/**
 * <h1>Crossreference für Statecharts</h1>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li>Initialisierung:    Crossreference cr = new Crossreference(GUI_Referenz, EDITOR_Referenz)
 * <li>Aufruf des Reports: cr.report(Statechart)
 * </ol>
 * <h2>Forderungen an die an den Report übergebene Statechart:</h2>
 * <ul>
 * <li>Es darf keine <b>null</b> an Stellen stehen, die dafür nicht vorgesehen
 * sind (z.B. in TrList.head).
 * <li>In der Datenstruktur der Statechart darf kein Zyklus sein.
 * </ul>
 * <h2>Garantien nach der Beendigung des Reports:</h2>
 * <ul>
 * <li>Der Report verändert die an ihn übergebene Statechart <b>nicht</b>.
 * </ul><br>
 * <br>
 * <DL COMPACT>
 * <DT><STRONG>STATUS: </STRONG><br>
 * Unserere Crossreference ist soweit fertig.
 * <DT><STRONG>To Do: </STRONG><br>
 * Testen, Testen, Testen.
 * <DT><STRONG>Bekannte Fehler: </STRONG><br>
 * keine
 * <DT><STRONG>Temporäre Features: </STRONG><br>
 * keine
 * </DL COMPACT>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: Crossreference.java,v 1.15 1999-01-25 08:00:27 swtech11 Exp $
 */
public class Crossreference extends ModelCheckBasics {
  private GUIInterface gui = null; // Referenz auf die GUI
  private Editor edit = null;
  String such = new String("");
  private Vector items = new Vector();
  private boolean high = false; // highlighten ?

  /**
  * Der Konstruktor der Crossreference.
  * @param _gui Referenz auf die GUI
  * @param _edit Referenz auf den Editor
  */
  public Crossreference(GUIInterface _gui, Editor _edit) {
    gui = _gui;
    edit = _edit;
  }

  /**
  * Führt die Crossreference aus.
  * @param sc  die zu checkende Statechart
  */
  public void report(Statechart _sc) {
    sc=_sc;
    // Eingabe
    such = gui.EingabeDialog("Crossreference","Zu suchendes Element eingeben:",such);
    if (such!=null) {
      // Start der Auswertung
      report_list();
      if (sc.state instanceof Or_State) {navOrState ((Or_State)sc.state, null,""); }
      if (sc.state instanceof And_State) {navAndState ((And_State)sc.state, null,""); }
      if (sc.state instanceof Basic_State) {navBasicState ((Basic_State)sc.state, null,""); }
      // Ausgabe
      if (edit != null) { high = true; } else { high = false; } // muß noch um Config erweiter werden
      highlightObject ho;
      if (items.size()>0) {
        if (high==true) { ho = new highlightObject(true); }// Highlighten vorbereiten
        gui.userMessage("Check: "+such+" ist ein:");
        for (int i=0; i<items.size(); i++) {
          ReportItem rp = (ReportItem)items.elementAt(i);
          gui.userMessage("Check:   - "+rp.Pth);
          if (high==true & rp.HiObj!=null) {
            ho = new highlightObject((Absyn)rp.HiObj,Color.black); // Object highlighten
  	      }
        }
        if (high==true) {ho = new highlightObject(); }// Highlighten aktivieren
      }
      else { gui.userMessage("Check: "+such+" wurde nicht gefunden."); }
    }
  }

  // Listen durchsuchen
  void report_list() {
    for(SEventList e=sc.events; e!=null; e=e.tail){
	     if (equalString(e.head.name,such)) {itemInput(e.head,null,"Def.Liste Events");}
    }
    for(BvarList b=sc.bvars; b!=null;b=b.tail){
	    if (equalString(b.head.var,such)) {itemInput(b.head,null,"Def.Liste BVars");}
    }
    for(PathList p=sc.cnames; p!=null;p=p.tail) {
	    if (equalString(PathtoString(p.head),such)) {itemInput(p.head,null,"Pfadliste States");}
    }
  }

  void navBasicState(Basic_State bs, State _s, String p) {
    if (equalString(bs.name.name,such)) { itemInput(bs,bs,"Basic-State in "+p); }
  }

  void navOrState(Or_State os, State _s, String p) {
    if (equalString(os.name.name,such)) { itemInput(os,os,"Or-State in "+p); }
    String np = getAddPathPart(p, os.name.name);
    if (os.trs != null) { navTransInTransList(os.trs, os, np); }
    if (os.connectors != null) { navConInConList(os.connectors, np); }
    if (os.substates != null) { navStateInStateList(os.substates, os, np); }
  }

  void navAndState(And_State as, State _s, String p) {
    if (equalString(as.name.name,such)) { itemInput(as,as,"And-State in "+p); }
    String np = getAddPathPart(p, as.name.name);
    if (as.substates != null) { navStateInStateList(as.substates, as, np); }
  }

  void navConInConList(ConnectorList cl, String p) {
    if (equalString(cl.head.name.name,such)) { itemInput(cl.head,cl.head,"Connector in "+p); }
    if (cl.tail != null) {navConInConList(cl.tail, p);}
  }

  void navTransInTransList(TrList tl, State _s, String p) {
    String z1 = msg.getTrSourceName(tl.head);
    String z2 = msg.getTrTargetName(tl.head);
    // Ueberpruefe Guards und Actions
   
    pruefeGuard(tl.head.label.guard, tl.head, p);
    pruefeAction(tl.head.label.action, tl.head, p);
    // Auswertung der Anker
    if (tl.head.source instanceof Statename) {
      if ( equalString(z1,such) )
        { itemInput(tl.head.source, tl.head,"Statename des Startankers der Transition "+z1+" -> "+z2+" in "+p); }
    }
    else if (tl.head.source instanceof Conname) {
      if ( equalString(z1,such) )
        { itemInput(tl.head.source,tl.head,"Connectorname des Startankers der Transition "+z1+" -> "+z2+" in "+p); }
    }
    if (tl.head.target instanceof Statename) {
      if ( equalString(z2,such) )
        { itemInput(tl.head.target,tl.head,"Statename des Zielankers der Transition "+z1+" -> "+z2+" in "+p); }
    }
    else if (tl.head.target instanceof Conname) {
      if ( equalString(z2,such) )
        { itemInput(tl.head.target,tl.head,"Connectorname des Zielankers der Transition "+z1+" -> "+z2+" in "+p); }
    }
    if (tl.tail != null) { navTransInTransList(tl.tail, _s, p); }
  }

  //
  void pruefeGuard(Guard g, Tr t, String p){
    if (g instanceof GuardBVar)  {pruefeBVar  (((GuardBVar )g).bvar, t, p, 1);}
      else {
      if (g instanceof GuardCompg) {pruefeGuard (((GuardCompg)g).cguard.elhs, t, p);
                                    pruefeGuard (((GuardCompg)g).cguard.erhs, t, p); }
        else {
        if (g instanceof GuardNeg)   {pruefeGuard (((GuardNeg)g).guard, t, p);}
          else {
	      if (g instanceof GuardEvent) {pruefeEvent (((GuardEvent)g).event, t, p,1);} 
	      else {
		   
		       if (g instanceof GuardCompp) {pruefePath  (((GuardCompp)g).cpath.path, t, p);}
		       else{
			   if (g instanceof GuardUndet) {pruefeString(g, t, p);};
			   
		       };};};};};};

    void pruefeAction(Action a, Tr t, String p){
    if (a instanceof ActionBlock)  { if ((((ActionBlock)a).aseq)!=null) {
            Aseq as=((ActionBlock)a).aseq;
            for(; (as.tail!=null); as=as.tail) 
	      { pruefeAction(as.head, t, p);}
	    pruefeAction(as.head, t, p);}
      
    }
      else {
      if (a instanceof ActionStmt) {pruefeBool (((ActionStmt)a).stmt, t, p);}
        else {
          if (a instanceof ActionEvt) {pruefeEvent (((ActionEvt)a).event, t, p, 2);}
    }; }; };

  void pruefeBVar(Bvar b, Tr t, String p, int i){

      if ((equalString(b.var,such)) && (i==1)) {itemInput(b,t,"BVar im Guard",t,p);}
      if ((equalString(b.var,such)) && (i==2)) {itemInput(b,t,"BVar im Action",t,p);}
  };

  void pruefeBool(Boolstmt b, Tr t, String p) {
     if (b instanceof BAss)  {pruefeBVar(((BAss)b).ass.blhs, t, p, 2); pruefeGuard(((BAss)b).ass.brhs, t, p);}
       else {
       if (b instanceof MTrue)  {pruefeBVar(((MTrue)b).var, t, p, 2);}
         else {
         if (b instanceof MFalse) {pruefeBVar(((MFalse)b).var, t, p, 2);}
          
     };};};

  void pruefeEvent(SEvent e, Tr t, String p, int i){

      if ((equalString(e.name,such)) && (i==1)) {itemInput(e,t,"Event im Guard",t,p);}
      if ((equalString(e.name,such)) && (i==2)) {itemInput(e,t,"Event im Action",t,p);}
  };

  void pruefePath(Path p, Tr t, String s){
      if (equalString(PathtoString(p),such)) {itemInput(p,t,"Pfad im Guard",t,s);}
      for (;p.tail!=null;p=p.tail){}; if (such.equals(p.head)) {itemInput(p,t,"State im Guard",t,s);}
     
  };

  void pruefeString(Absyn a, Tr t, String p){

      if ((a instanceof TLabel) && (equalString(((TLabel)a).caption, such))) {itemInput(a,t,"Text in Caption",t,p);}
      if ((a instanceof GuardUndet) && (equalString(((GuardUndet)a).undet, such))) {itemInput(a,t,"Text im GuardUndet",t,p);}
     
  };

  boolean equalString(String a, String such) {
    boolean b =false;
    boolean infix=false;
    boolean praefix=false;
    boolean postfix=false;
    int suchl=such.length();
    if ((such.substring(suchl-1,suchl).equals("*")) && (such.substring(0,1).equals("*"))) {infix=true; praefix=false; postfix=false;};
    if ((!such.substring(suchl-1,suchl).equals("*")) && (such.substring(0,1).equals("*"))) {infix=false; praefix=false; postfix=true;};
    if ((such.substring(suchl-1,suchl).equals("*")) && (!such.substring(0,1).equals("*"))) {infix=false; praefix=true; postfix=false;};
    String _in=such;

    int al=a.length();
    String in="";
    String m="";
    if ((infix) && (_in.length()>2)) {
        in=_in.substring(1,_in.length()-1);
        int il=in.length();
        if ((il<=al) && (al>0) && (il>0)) {
	  for (int j=0; ((j<=(al-il)) && (!b));j++) {
	    m=a.substring(j,il+j);
	    //System.out.println("in: "+in+" sub: "+a);

	    if (m.equals(in)) {b=true;};
	};};};
  
   if (postfix) {
        in=_in.substring(1,_in.length());
        int il=in.length();
        if ((il<=al) && (al>0) && (il>0)) {
                  m=a.substring(al-il,al);
		  //System.out.println("in: "+in+" sub: "+m+" a "+a);

	    if (m.equals(in)) {b=true;};
	};};

  if (praefix) {
        in=_in.substring(0,_in.length()-1);
        int il=in.length();
        if ((il<=al) && (al>0) && (il>0)) {
                  m=a.substring(0,il);
		  //System.out.println("in: "+in+" sub: "+a);

	    if (m.equals(in)) {b=true;};
	};};
  if (!praefix && !postfix && !infix) {
        in=_in;
        if (a.equals(in)) {b=true;};
  }
   return b;
  }

  // Eingabe eines Report Ergebnises
  void itemInput(Object o, Object ho, String p) {
    ReportItem ri = new ReportItem(o, ho, p);
    items.addElement(ri);
  }

  void itemInput(Object o, Object ho, String p1, Tr t, String p) {
    String s1=msg.getTrSourceName(t);
    String s2=msg.getTrTargetName(t);
    ReportItem ri = new ReportItem(o, ho, p1 +" Trans: "+s1+" -> "+s2+" in State: "+p);
    items.addElement(ri);
  }
}

class ReportItem {
  // die Art des Elementes, evtl. überflüssig
  Object Obj = null;
  // das Objekt selbst, z.B. zum Highlighten, evtl. überflüssig
  Object HiObj = null;
  // die texttuelle Art des Elementes plus seiner Beschreibung der Lage
  String Pth = null;

  public ReportItem(Object o, Object ho, String p) {
    Obj = o;
    HiObj = ho;
    Pth = p;
  }
}
