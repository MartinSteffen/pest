package check;

import gui.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenster zur Eingabe der gewünschten Warnungen des Syntax Checks, die im Browser ausgegeben werden sollen
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: CheckOptionMeldungen.java,v 1.1 1999-02-11 22:29:12 swtech11 Exp $
 *  @see CheckConfig, CheckOption
 */
class CheckOptionMeldungen extends Dialog implements ActionListener {
  pest parent;

  ErrorAndWarningCodes cod = new ErrorAndWarningCodes();
  Vector vw = cod.codeWarnings();
  Vector ve = cod.codeErrors();

  Panel panel;
  Button button1, button2;
  List lst;

  /**
   * Konstruktor für das Eingabefenster
   *
   * @param parent notwendige Referenz auf das aufrufende Fenster
   * @param _cf die Klasse zum Speichern der Optionen des Checks
  */
  public CheckOptionMeldungen(pest parent)  {
    super(parent,"Optionen beim Check - Meldungen des Syntax Check",true);
    this.parent = parent;

    Point p = parent.getLocation();
    setLocation(p.x + 30 , p.y + 30);
    setLayout(new BorderLayout());

/**
    // Ueberschrift Warnungen
  	panel = new Panel(new FlowLayout());
	  panel.setBackground(Color.gray);
  	panel.setForeground(Color.white);
  	panel.add(new Label("Meldungen des Syntax Check:"));
	  add("North",panel);
*/

    // Meldungsausgabe

    lst = new List(30);
    lst.setMultipleMode(true);
    lst.setFont(new Font("Serif",Font.PLAIN,14));
    lst.setBackground(Color.lightGray);
    lst.setForeground(Color.black);

    Integer n, na;

    lst.add("Fehlermeldungen ( "+ ve.size() + " Stueck ):");
    lst.add("");
    lst.add("allgemeine Fehler:");
    na = new Integer(0);
    for (int i=0; i<ve.size(); i++) {
      n = (Integer) ve.elementAt(i);
      if (na.intValue()<100 && n.intValue()>=100) {lst.add("Fehler bei booleschen Variablen:"); };
      if (na.intValue()<200 && n.intValue()>=200) {lst.add("Fehler bei Events:"); };
      if (na.intValue()<300 && n.intValue()>=300) {lst.add("Fehler bei States:"); };
      if (na.intValue()<400 && n.intValue()>=400) {lst.add("Fehler bei Transitionen:"); };
      lst.add("- "+cod.codeToString(n.intValue())+" ("+n.toString()+")");
      na = n;
    }
    lst.add("");
    lst.add("Warnmeldungen ( "+ vw.size() + " Stueck ):");
    lst.add("");
    lst.add("allgemeine Warnungen:");
    na = new Integer(0);
    for (int i=0; i<vw.size(); i++) {
      n = (Integer) vw.elementAt(i);
      if (na.intValue()<100 && n.intValue()>=100) {lst.add("Warnungen bei booleschen Variablen:"); };
      if (na.intValue()<200 && n.intValue()>=200) {lst.add("Warnungen bei Events:"); };
      if (na.intValue()<300 && n.intValue()>=300) {lst.add("Warnungen bei States:"); };
      if (na.intValue()<400 && n.intValue()>=400) {lst.add("Warnungen bei Transitionen:"); };
      lst.add("- "+cod.codeToString(n.intValue())+" ("+n.toString()+")");
      na = n;
    }

    add("Center",lst);

    // Steuerungsbuttons
  	panel = new Panel(new FlowLayout());
  	panel.setBackground(Color.gray);
	  panel.setForeground(Color.white);
  	button1 = new Button("OK");
	  button1.setActionCommand("OK");
  	button1.addActionListener(this);
	  panel.add(button1);

  	add("South",panel);

  	pack();
	  setResizable(true);
    Dimension d = getSize();
    if (d.width<400) { // Weite manual anpassen, da UNIX es nicht schafft
      d.width=450;     // im Gegesatz zur Windows Version
      setSize(d);
    }
  	setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
  	String cmd = e.getActionCommand();
  	if (cmd.equals(button1.getActionCommand())) {
  		setVisible(false);
	  	dispose();
	  }
  }


}
