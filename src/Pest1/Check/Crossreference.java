package check;

import absyn.*;
import gui.*;
import editor.*;
import java.util.*;

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
 * noch gar nichts
 * <DT><STRONG>To Do: </STRONG><br>
 * alles
 * <DT><STRONG>Bekannte Fehler: </STRONG><br>
 * keine
 * <DT><STRONG>Temporäre Features: </STRONG><br>
 * keine
 * </DL COMPACT>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: Crossreference.java,v 1.4 1999-01-17 20:21:45 swtech11 Exp $
 */
public class Crossreference extends ModelCheckBasics {
  private GUIInterface gui = null; // Referenz auf die GUI
  private Editor edit = null;
  private String such = new String("?");
  private Vector items = new Vector();

  public Crossreference(GUIInterface _gui, Editor _edit) {
    gui = _gui;
    edit = _edit;
  }


  public void report(Statechart sc) {

    // Eingabe
    gui.EingabeDialog("Crossreference","Zu suchendes Element eingeben;",such);

    // Start der Auswertung
    if (sc.state instanceof Or_State) {navOrState ((Or_State)sc.state, null,""); }
    if (sc.state instanceof And_State) {navAndState ((And_State)sc.state, null,""); }
    if (sc.state instanceof Basic_State) {navBasicState ((Basic_State)sc.state, null,""); }

    // Ausgabe
    if (items.size()>0) {
      gui.userMessage("Check: "+such+" ist ein:");
      for (int i=0; i<items.size(); i++) {
        ReportItem rp = (ReportItem)items.elementAt(i);
        gui.userMessage("Check:   - "+rp.Pth);
      }
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
    if (os.substates != null) { navStateInStateList(os.substates, os, np); }
  }

  void navAndState(And_State as, State _s, String p) {
    if (as.name.name.equals(such)) { itemInput(2,as,"And-State in "+p); }
    String np = getAddPathPart(p, as.name.name);
    if (as.substates != null) { navStateInStateList(as.substates, as, np); }
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
