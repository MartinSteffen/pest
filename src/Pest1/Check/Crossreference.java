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
 * Die Auswertung erfolgt bis jetzt für: States (alle Arten), Connectoren, Anker der Transitionen
 * <DT><STRONG>To Do: </STRONG><br>
 * alles
 * <DT><STRONG>Bekannte Fehler: </STRONG><br>
 * keine
 * <DT><STRONG>Temporäre Features: </STRONG><br>
 * keine
 * </DL COMPACT>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: Crossreference.java,v 1.5 1999-01-18 20:56:31 swtech11 Exp $
 */
public class Crossreference extends ModelCheckBasics {
  private GUIInterface gui = null; // Referenz auf die GUI
  public String such = new String("");
  private Vector items = new Vector();

  public Crossreference(GUIInterface _gui, Editor _edit) {
    gui = _gui;
  }

  public Crossreference(GUIInterface _gui) {
    gui = _gui;
  }


  public void report(Statechart sc) {

    // Eingabe
    such = gui.EingabeDialog("Crossreference","Zu suchendes Element eingeben;",such);
    System.out.println(such);

    // Start der Auswertung
    if (sc.state instanceof Or_State) {navOrState ((Or_State)sc.state, null,""); }
    if (sc.state instanceof And_State) {navAndState ((And_State)sc.state, null,""); }
    if (sc.state instanceof Basic_State) {navBasicState ((Basic_State)sc.state, null,""); }

    // Ausgabe
    if (items.size()>0) {
      highlightObject ho = new highlightObject(true); // Highlighten vorbereiten
      gui.userMessage("Check: "+such+" ist ein:");
      for (int i=0; i<items.size(); i++) {
        ReportItem rp = (ReportItem)items.elementAt(i);
        gui.userMessage("Check:   - "+rp.Pth);
        ho = new highlightObject((Absyn)rp.Obj,Color.black); // Object highlighten
      }
      ho = new highlightObject(); // Highlighten aktivieren
    }
    else {
      gui.userMessage("Check: "+such+" wurde nicht gefunden.");
    }
  }


  void navBasicState(Basic_State bs, State _s, String p) {
    if (bs.name.name.equals(such)) { itemInput(3,bs,"Basic-State in "+p); }
  }

  void navOrState(Or_State os, State _s, String p) {
    if (os.name.name.equals(such)) { itemInput(1,os,"Or-State in "+p); }
    String np = getAddPathPart(p, os.name.name);
    if (os.trs != null) { navTransInTransList(os.trs, os, np); }
    if (os.connectors != null) { navConInConList(os.connectors, np); }
    if (os.substates != null) { navStateInStateList(os.substates, os, np); }
  }

  void navAndState(And_State as, State _s, String p) {
    if (as.name.name.equals(such)) { itemInput(2,as,"And-State in "+p); }
    String np = getAddPathPart(p, as.name.name);
    if (as.substates != null) { navStateInStateList(as.substates, as, np); }
  }

  void navConInConList(ConnectorList cl, String p) {
    if (cl.head.name.name.equals(such)) { itemInput(4,cl.head,"Connector in "+p); }
    if (cl.tail != null) {navConInConList(cl.tail, p);}
  }

  void navTransInTransList(TrList tl, State _s, String p) {
    String z1 = new String();
    String z2 = new String();
    
    // Namen der Anker rausfinden
    if (tl.head.source instanceof UNDEFINED)
      { z1 = new String("UNDEFINED"); }
    else if (tl.head.source instanceof Statename)
      { z1 = new String(((Statename)tl.head.source).name); }
    else if (tl.head.source instanceof Conname)
      { z1 = new String(((Conname)tl.head.source).name); }
    if (tl.head.target instanceof UNDEFINED)
      { z2 = new String("UNDEFINED"); }
    else if (tl.head.target instanceof Statename)
      { z2 = new String(((Statename)tl.head.target).name); }
    else if (tl.head.target instanceof Conname)
      { z2 = new String(((Conname)tl.head.target).name); }

    // Auswertung der Anker  
    if (tl.head.source instanceof Statename) {
      if ( z1.equals(such) )
        { itemInput(5,tl.head,"Statename des Startankers der Transition "+z1+" -> "+z2+" in "+p); }
    }
    else if (tl.head.source instanceof Conname) {
      if ( z1.equals(such) )
        { itemInput(7,tl.head,"Connectorname des Startankers der Transition "+z1+" -> "+z2+" in "+p); }
    }
    if (tl.head.target instanceof Statename) {
      if ( z2.equals(such) )
        { itemInput(6,tl.head,"Statename des Zielankers der Transition "+z1+" -> "+z2+" in "+p); }
    }
    else if (tl.head.target instanceof Conname) {
      if ( z2.equals(such) )
        { itemInput(8,tl.head,"Connectorname des Zielankers der Transition "+z1+" -> "+z2+" in "+p); }
    }

    if (tl.tail != null) { navTransInTransList(tl.tail, _s, p); }
  }

  // Eingabe eines Report Ergebnises
  void itemInput(int a, Object o, String p) {
    ReportItem ri = new ReportItem(a, o, p);
    items.addElement(ri);
  }

}

class ReportItem {
  // die Art des Elementes, evtl. überflüssig
  int Art = 0;
       //   0: unbekannt oder egal
       //   1: OrState
       //   2: AndState
       //   3: BasicState
       //   4: Connectoren
       //   5: Statename des Startanker
       //   6: Statename des Zielanker
       //   7: Connectorname des Startanker
       //   8: Connectorname des Zielanker

  // das Objekt selbst, z.B. zum Highlighten, evtl. überflüssig
  Object Obj = null;
  // die texttuelle Art des Elementes plus seiner Beschreibung der Lage
  String Pth = null;

  public ReportItem(int a, Object o, String p) {
    Art = a;
    Obj = o;
    Pth = p;
  }
}
