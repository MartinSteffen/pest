package check;

import absyn.*;
import gui.*;
import editor.*;
import java.util.*;

/**
 * <h1>Crossreference f�r Statecharts</h1>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li>Initialisierung:    Crossreference cr = new Crossreference(GUI_Referenz, EDITOR_Referenz)
 * <li>Aufruf des Reports: cr.report(Statechart)
 * </ol>
 * <h2>Forderungen an die an den Report �bergebene Statechart:</h2>
 * <ul>
 * <li>Es darf keine <b>null</b> an Stellen stehen, die daf�r nicht vorgesehen
 * sind (z.B. in TrList.head).
 * <li>In der Datenstruktur der Statechart darf kein Zyklus sein.
 * </ul>
 * <h2>Garantien nach der Beendigung des Reports:</h2>
 * <ul>
 * <li>Der Report ver�ndert die an ihn �bergebene Statechart <b>nicht</b>.
 * </ul><br>
 * <br>
 * <DL COMPACT>
 * <DT><STRONG>STATUS: </STRONG><br>
 * noch gar nichts
 * <DT><STRONG>To Do: </STRONG><br>
 * alles
 * <DT><STRONG>Bekannte Fehler: </STRONG><br>
 * keine
 * <DT><STRONG>Tempor�re Features: </STRONG><br>
 * keine
 * </DL COMPACT>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: Crossreference.java,v 1.3 1999-01-12 17:25:40 swtech11 Exp $
 */
public class Crossreference extends ModelCheckBasics {
  private GUIInterface gui = null; // Referenz auf die GUI
  private Editor edit = null;
  private String such = new String("?");

  private Vector rArt = new Vector(); // die Art des Elementes, evtl. �berfl�ssig
             //   1: OrState
             //   2: AndState
             //   3: BasicState

  // das Objeckt selbst, z.B. zum Highlighten, evtl. �berfl�ssig
  private Vector rObj = new Vector();
  // die texttuelle Art des Elementes plus seiner Beschreibung der Lage
  private Vector rPth = new Vector();


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
    if (rPth.size()>0) {
      gui.userMessage("Check: "+such+" ist ein:");
      for (int i=0; i<rPth.size(); i++) {
        gui.userMessage("Check: - "+(String)rPth.elementAt(i));
      }
    }
    else {
      gui.userMessage("Check: "+such+" wurde nicht gefunden.");
    }
  }


  void navBasicState(Basic_State bs, State _s, String p) {
    if (bs.name.name.equals(such)) {
      rArt.addElement(new Integer(3));
      rObj.addElement(bs);
      rPth.addElement(new String("Basic-State in "+p));
    }
  }

  void navOrState(Or_State os, State _s, String p) {
    if (os.name.name.equals(such)) {
      rArt.addElement(new Integer(1));
      rObj.addElement(os);
      rPth.addElement(new String("Or-State in "+p));
    }
    String np = getAddPathPart(p, os.name.name);
    if (os.trs != null) { navTransInTransList(os.trs, os, np); }
    if (os.substates != null) { navStateInStateList(os.substates, os, np); }
  }

  void navAndState(And_State as, State _s, String p) {
    if (as.name.name.equals(such)) {
      rArt.addElement(new Integer(2));
      rObj.addElement(as);
      rPth.addElement(new String("And-State in "+p));
    }
    String np = getAddPathPart(p, as.name.name);
    if (as.substates != null) { navStateInStateList(as.substates, as, np); }
  }



}
