package check;

import absyn.*;
import gui.*;
import editor.*;

/**
 * <h1>Crossreference für Statecharts</h1>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li>Initialisierung:   Crossreference cr = new Crossreference(GUI_Referenz, EDITORReferenz)
 * <li>Aufruf des Checks: cr.report(Statechart)
 * </ol>
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: Crossreference.java,v 1.1 1999-01-11 21:37:03 swtech11 Exp $
 */
public class Crossreference extends ModelCheckBasics {
  private GUIInterface gui = null; // Referenz auf die GUI
  private Editor edit = null;

  public Crossreference(GUIInterface _gui, Editor _edit) {
    gui = _gui;
    edit = _edit;
  }

  public void report(Statechart sc) {

  }

}
