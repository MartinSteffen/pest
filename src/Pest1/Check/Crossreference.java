package check;

import absyn.*;
import gui.*;
import editor.*;

/**
 * <h1>Crossreference für Statecharts</h1>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li>Initialisierung:   Crossreference cr = new Crossreference(GUI_Referenz, EDITOR_Referenz)
 * <li>Aufruf des Checks: cr.report(Statechart)
 * </ol>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: Crossreference.java,v 1.2 1999-01-11 22:42:04 swtech11 Exp $
 */
public class Crossreference extends ModelCheckBasics {
  private GUIInterface gui = null; // Referenz auf die GUI
  private Editor edit = null;
  String such = new String("?");

  public Crossreference(GUIInterface _gui, Editor _edit) {
    gui = _gui;
    edit = _edit;
  }

  public void report(Statechart _sc) {
    sc = _sc;
    gui.EingabeDialog("Crossreference","Zu suchendes Element eingeben;",such);
  }

}
