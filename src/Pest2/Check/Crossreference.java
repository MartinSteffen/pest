// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                Crossreference
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          30.01.1999
//
// ****************************************************************************


package check;

import absyn.*;     // Objekte
import gui.*;       // In- und Output
import editor.*;    // zwecks Highlighten
import java.util.*; // Vector Klasse
import java.awt.*;  // Farben

/**
*<h1 align="center">Crossreference</h1>
*
*<h2>Aufruf</h2>
*
*<table border="0">
*    <tr>
*        <td>Initialisierung:</td>
*        <td><font face="Courier">Crossreference cref = new
*        Crossreference(GUIInterface, Editor, CheckConfig);</font></td>
*    </tr>
*   <tr>
*        <td>Ausführung:</td>
*        <td><font face="Courier">cref.report(Statechart);</font></td>
*    </tr>
*</table>
*
*<h2>Anforderungen</h2>
*
*<p>Es werden keinerlei Anforderungen an die übergebene
*Statechart gestellt</p>
*
*<h2>Status (30.01.1999)</h2>
*
*<ul>
*    <li>voll funktionstuechtig (beta)</li>
*</ul>
*
*<h2>Todo</h2>
*
*<p>bis dato nichts...</p>
*
*<h2>Kontakt</h2>
*
*<table border="0">
*    <tr>
*        <td valign="top">Autoren:</td>
*        <td><a href="mailto:u1579@bwl.uni-kiel.de">Tobias Kunz</a><br>
*        <a href="mailto:tc@bwl.uni-kiel.de">Mario Thies</a> </td>
*    </tr>
*</table>
*<h2>Version</h2>
* @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
* @version $id:$
*/
public class Crossreference extends General {

// ****************************************************************************
// private Variablen der Klasse
// ****************************************************************************

private Editor       editor    = null;           // Referenz des Editors
private String       searchstr = new String(""); // Suchstring
private Vector       items     = new Vector();   // Vector zum Speichern
                                                 // der Einträge vom Typ
                                                 // "ItemCrossrefernce"
private GUIInterface gui       = null;           // Referenz auf die GUI
private Statechart   statechart= null;
private Color        COLOR     = Color.red;       // Farbe fuer das Highlighten

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  public Crossreference() {
  }

  public Crossreference(GUIInterface gui, Editor editor) {
    this();

    this.gui = gui;
    this.editor = editor;
  }

  public Crossreference(GUIInterface gui, Editor editor, CheckConfig checkconfig) {
    this(gui, editor);
    // CheckConfig wird nicht benoetigt - Kompatibilitaet zu PEST1/Check...
  }

// ****************************************************************************
// ”ffentliche Instanzmethoden
// ****************************************************************************

  public void report(Statechart statechart) {
    this.statechart = statechart;

    // ### nur zum Testen!
    // Example e = new Example();
    // this.statechart = e.getExample();
    // ###

    // Benutzer zur Eingabe auffordern
    searchstr = gui.EingabeDialog("Crossreference","Objektname:",searchstr);
    // Start der Suche
    if (searchstr!=null) {

      // Suche beginnen
      StartReport();
      if (this.statechart.state instanceof Or_State)
        nextOrState ((Or_State)this.statechart.state, null,"");
      if (this.statechart.state instanceof And_State)
        nextAndState ((And_State)this.statechart.state, null,"");
      if (this.statechart.state instanceof Basic_State)
        handleBasicState ((Basic_State)this.statechart.state, null);
      // Ausgabe

      if (items.size()>0) {
        for (int i=0; i<items.size(); i++) {
          ItemCrossreference item = (ItemCrossreference)items.elementAt(i);
          gui.userMessage(item.path);
          if (item.highlighted_object !=null) {
            // ### EDITOR: Brauchen Methode um irgendein Objekt der Absyn zu
            // highlighten ###
            /*
            highlightObject o = new highlightObject(
                                    (Absyn)item.highlighted_object,
                                    COLOR);
            */
          }
        }
      }
    }
    // Zusammenfassung
    gui.userMessage(Integer.toString(items.size())+" Objekte gefunden");
  }

// ****************************************************************************
// private Methoden
// ****************************************************************************

  void StartReport() {
    // Eventliste durchsuchen
    for(SEventList e=statechart.events; e!=null; e=e.tail)
      if (compareStrings(e.head.name,searchstr)) {addObject(e.head,null,"Event");}

    // Boolsche Variablen
    for(BvarList b=statechart.bvars; b!=null;b=b.tail)
	    if (compareStrings(b.head.var,searchstr)) {addObject(b.head,null,"Bool'sche Variable");}

    // State
    for(PathList p=statechart.cnames; p!=null;p=p.tail)
	    if (compareStrings(PathtoString(p.head),searchstr)) {addObject(p.head,null,"kommt in der State-Liste vor");}
  }

  void checkGuard(Guard g, Tr t, String p){

    if (g instanceof GuardBVar)
      checkBVar(((GuardBVar )g).bvar, t, p, 1);
    else {
      if (g instanceof GuardCompg) {
        checkGuard (((GuardCompg)g).cguard.elhs, t, p);
        checkGuard (((GuardCompg)g).cguard.erhs, t, p);
      } else {
        if (g instanceof GuardNeg)
          checkGuard (((GuardNeg)g).guard, t, p);
        else {
	        if (g instanceof GuardEvent)
            checkEvent (((GuardEvent)g).event, t, p,1);
	        else {
		        if (g instanceof GuardCompp)
              checkPath  (((GuardCompp)g).cpath.path, t, p);
		        else {
			        if (g instanceof GuardUndet)
                pruefeString(g, t, p);
            }
          }
        }
      }
    }
  }

  void checkAction(Action a, Tr t, String p){
    if (a instanceof ActionBlock) {
      if ((((ActionBlock)a).aseq)!=null) {
            Aseq as=((ActionBlock)a).aseq;
            for(; (as.tail!=null); as=as.tail) checkAction(as.head, t, p);
	          checkAction(as.head, t, p);}
    } else {
      if (a instanceof ActionStmt) checkBool (((ActionStmt)a).stmt, t, p);
      else {
        if (a instanceof ActionEvt) checkEvent (((ActionEvt)a).event, t, p, 2);
      }
    }
  }


  void checkBVar(Bvar b, Tr t, String p, int i){
      if ((compareStrings(b.var,searchstr)) && (i==1)) addObject(b,t,"Bool'sche Variable im Guard",t,p);
      if ((compareStrings(b.var,searchstr)) && (i==2)) addObject(b,t,"Bool'sche Variable im Action",t,p);
  };


  void checkBool(Boolstmt b, Tr t, String p) {
     if (b instanceof BAss)  {
       checkBVar(((BAss)b).ass.blhs, t, p, 2);
       checkGuard(((BAss)b).ass.brhs, t, p);}
     else {
       if (b instanceof MTrue) checkBVar(((MTrue)b).var, t, p, 2);
       else {
         if (b instanceof MFalse) checkBVar(((MFalse)b).var, t, p, 2);
       }
     }
  }

  void checkEvent(SEvent e, Tr t, String p, int i){
      if ((compareStrings(e.name,searchstr)) && (i==1))
        addObject(e,t,"Ereignis im Guard",t,p);
      if ((compareStrings(e.name,searchstr)) && (i==2))
        addObject(e,t,"Ereignis im Action",t,p);
  };

  void checkPath(Path p, Tr t, String s){
      if (compareStrings(PathtoString(p),searchstr))
        addObject(p,t,"Pfad im Guard",t,s);

      for (;p.tail!=null;p=p.tail){};

      if (searchstr.equals(p.head))
        addObject(p,t,"State im Guard",t,s);
  }

  void pruefeString(Absyn a, Tr t, String p){
      if ((a instanceof TLabel) && (compareStrings(((TLabel)a).caption, searchstr)))
        addObject(a,t,"Text in Caption",t,p);
      if ((a instanceof GuardUndet) && (compareStrings(((GuardUndet)a).undet, searchstr)))
        addObject(a,t,"Text im GuardUndet",t,p);
  };


  // Vergleicht zwei Strings miteinander - Wildcards "*" erlaubt
  boolean compareStrings(String a, String b) {

    boolean infix    = false;
    boolean praefix  = false;
    boolean postfix  = false;
    int     blength  = b.length();
    int     alength  = a.length();
    boolean OK       = false;

    // Infix ?
    if ((b.substring(blength-1,blength).equals("*")) &&
        (b.substring(0,1).equals("*"))) infix = true;
    // postfix ?
    if ((!b.substring(blength-1,blength).equals("*")) &&
        (b.substring(0,1).equals("*"))) postfix = true;
    // praefix ?
    if ((b.substring(blength-1,blength).equals("*")) &&
        (!b.substring(0,1).equals("*"))) praefix = true;

    String _in = b;
    String in  = "";
    String m   = "";

    // Infix?
    if ((infix) && (_in.length()>2)) {
      in=_in.substring(1,_in.length()-1);
      int ilength = in.length();
      if ((ilength<=alength) && (alength>0) && (ilength>0))
        for (int j=0; ((j <= (alength - ilength)) && (!OK));j++) {
          m=a.substring(j,ilength+j);
          if (m.equals(in)) OK = true;
        }
    }

    // postfix
    if (postfix) {
      in =_in.substring(1,_in.length());
      int ilength=in.length();
      if ((ilength <= alength) && (alength > 0) && (ilength > 0)) {
        m=a.substring(alength - ilength, alength);
	      if (m.equals(in)) OK=true;
      }
    }

    // praefix
    if (praefix) {
      in=_in.substring(0,_in.length()-1);
      int ilength = in.length();
      if ((ilength <= alength) && (alength > 0) && (ilength > 0)) {
        m=a.substring(0,ilength);
	      if (m.equals(in)) OK=true;
      }
    }

    // ohne wildcards
    if (!praefix && !postfix && !infix) {
      if (a.equals(b)) OK = true;
    }

    return OK;
  }

// ****************************************************************************
// hinzufuegen zur Liste der gefundenen Objekte
// ****************************************************************************

  void addObject(Object object,
                 Object highlighted_object,
                 String path) {
    ItemCrossreference item = new ItemCrossreference(object, highlighted_object, path);
    items.addElement(item);
  }

  void addObject(Object object,
                Object highlighted_object,
                String path1,
                Tr transition,
                String path2) {

    String s1 = getTrSourceName(transition);
    String s2 = getTrTargetName(transition);

    ItemCrossreference item = new ItemCrossreference (object,
       highlighted_object, path1 +" Trans: "+s1+" -> "+s2+" in State: "+path2);
    items.addElement(item);
  }

// ****************************************************************************
// Navigation durch den Baum der Statechart
// ****************************************************************************

  void nextOrState(Or_State os, State s, String p) {

    if (compareStrings(os.name.name,searchstr)) addObject(os,os,"Or-State");

    String np = getAddPathPart(p, os.name.name);
    if (os.trs != null)        nextTransInTransList(os.trs, os, np);
    if (os.connectors != null) nextConInConList(os.connectors, np);
    if (os.substates != null)  nextStateInStateList(os.substates, os, np);
  }

  void nextAndState(And_State as, State s, String p) {

    if (compareStrings(as.name.name,searchstr))
      addObject(as,as,"And-State");
    String np = getAddPathPart(p, as.name.name);

    if (as.substates != null) nextStateInStateList(as.substates, as, np);
  }

  void handleBasicState(Basic_State bs, State s) {
    if (compareStrings(bs.name.name,searchstr))
      addObject(bs,bs,"Basic-State");
  }

  void nextTransInTransList(TrList tl, State s, String p) {

    String z1 = getTrSourceName(tl.head);
    String z2 = getTrTargetName(tl.head);
    // Ueberpruefe Guards und Actions
    checkGuard(tl.head.label.guard, tl.head, p);
    checkAction(tl.head.label.action, tl.head, p);

    // Auswertung der Anker
    if (tl.head.source instanceof Statename) {
      if ( compareStrings(z1,searchstr) )
        addObject(tl.head.source,
                  tl.head,
                  "Statename des Startankers der Transition "+z1+" -> "+z2+" in "+p);

    } else if (tl.head.source instanceof Conname) {
      if ( compareStrings(z1,searchstr) )
        addObject(tl.head.source,
                  tl.head,
                  "Connectorname des Startankers der Transition "+z1+" -> "+z2+" in "+p);
    }

    if (tl.head.target instanceof Statename) {
      if ( compareStrings(z2,searchstr) )
        addObject(tl.head.target,
                  tl.head,
                  "Statename des Zielankers der Transition "+z1+" -> "+z2+" in "+p);
    } else if (tl.head.target instanceof Conname) {
      if ( compareStrings(z2,searchstr) )
        addObject(tl.head.target,
                  tl.head,
                  "Connectorname des Zielankers der Transition "+z1+" -> "+z2+" in "+p);
    }
    if (tl.tail != null) { nextTransInTransList(tl.tail, s, p); }
  }

  void nextStateInStateList(StateList sl, State _s, String p) {

    if (sl.head instanceof Or_State) {nextOrState ((Or_State)sl.head, _s, p); }
    if (sl.head instanceof And_State) {nextAndState ((And_State)sl.head, _s, p); }
    if (sl.head instanceof Basic_State) {handleBasicState ((Basic_State)sl.head, _s); }
    if (sl.tail != null) { nextStateInStateList(sl.tail, _s, p); }

  }

  void nextConInConList(ConnectorList cl, String p) {

    if (compareStrings(cl.head.name.name,searchstr))
      addObject(cl.head,cl.head,"Connector in "+p);
    if (cl.tail != null) {nextConInConList(cl.tail, p);}
  }
}

/*
* Basisklasse, die eine gewisse Basis-Funktionialitaet zur Verfuegung
* stellt.
*/
class General {

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  General() {
  }

// ****************************************************************************
// Methoden
// ****************************************************************************

   // liefert den vollstaendigen Pfad zurueck
   String PathtoString(Path p) {

     String s=p.head;

     p=p.tail;

     for(;p!=null; p=p.tail)
       s=s+"."+p.head;

     return s;
   }


  String getAddPathPart(String p, String n) {
    String s = new String();

    if (p.equals("")) s = n;
                 else s = p + "." + n;

    return s;
  }

  void nextStateInStateList(StateList sl) {
    if (sl.head instanceof Or_State)
      nextOrState ((Or_State)sl.head);
    if (sl.head instanceof And_State)
      nextAndState ((And_State)sl.head);
    if (sl.tail != null)
      nextStateInStateList(sl.tail);
  }

  // liefert Quelle einer Transisition zurueck
  String getTrSourceName(Tr t) {
    String s = new String();

    if (t.source instanceof Statename)      { s=((Statename)t.source).name;}
    else if (t.source instanceof Conname)   { s=((Conname)t.source).name;}
    else if (t.source instanceof UNDEFINED) { s="UNDEFINED";}
    return s;
  }


  // liefert Ziel einer Transition zurueck
  String getTrTargetName(Tr t) {

    String s=new String();
    if (t.target instanceof Statename)
      s=((Statename)t.target).name;
    else if (t.target instanceof Conname)
      s=((Conname)t.target).name;
    else if (t.target instanceof UNDEFINED)
      s="UNDEFINED";
    return s;
  }

  void nextOrState(Or_State os) {
    if (os.trs != null)
      nextTransInTransList(os.trs);

    if (os.substates != null)
      nextStateInStateList(os.substates);
  }


  // liefert den naechsten AND state zurueck
  void nextAndState(And_State as) {
    if (as.substates != null) { nextStateInStateList(as.substates); }
  }

  // liefert die naechste Transisation zurueck
  void nextTransInTransList(TrList tl) {
    if (tl.tail != null) { nextTransInTransList(tl.tail); }
  }

}
