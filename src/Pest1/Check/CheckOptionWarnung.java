package check;

import gui.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenster zur Eingabe der gewünschten Warnungen des Syntax Checks, die im Browser ausgegeben werden sollen
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: CheckOptionWarnung.java,v 1.1 1999-02-11 01:06:23 swtech11 Exp $
 *  @see CheckConfig, CheckOption
 */
class CheckOptionWarnung extends Dialog implements ActionListener {
  pest parent;
  CheckConfig cf;

  ErrorAndWarningCodes cod = new ErrorAndWarningCodes();
  Vector v = cod.codeWarnings();

  Panel panel;
  Button button1, button2;
  List lst;

  /**
   * Konstruktor für das Eingabefenster
   *
   * @param parent notwendige Referenz auf das aufrufende Fenster
   * @param _cf die Klasse zum Speichern der Optionen des Checks
  */
  public CheckOptionWarnung(pest parent,CheckConfig _cf)  {
    super(parent,"Optionen beim Check - Warnungen selektieren",true);
    this.parent = parent;
    if ( _cf==null ) { cf = new CheckConfig(); } else { cf = _cf; }
    
    Point p = parent.getLocation();
    setLocation(p.x + 30 , p.y + 30);
    setLayout(new BorderLayout());

    // Ueberschrift Warnungen
  	panel = new Panel(new FlowLayout());
	  panel.setBackground(Color.gray);
  	panel.setForeground(Color.white);
  	panel.add(new Label("Warnungen, die angezeigt werden sollen, markieren:"));
	  add("North",panel);


    // Warnungs Liste

    lst = new List(v.size());
    lst.setMultipleMode(true);
    lst.setFont(new Font("Serif",Font.PLAIN,14));
    lst.setBackground(Color.lightGray);
    lst.setForeground(Color.black);

    for (int i=0; i<v.size(); i++) {
      Integer n = (Integer) v.elementAt(i);
      lst.add(cod.codeToString(n.intValue()));
      if (cf.sc_warnStr.indexOf(";"+n.toString()+";")==-1) {
        lst.select(i);
      }
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
  	button2 = new Button("Abbrechen");
	  button2.setActionCommand("Ab");
  	button2.addActionListener(this);
	  panel.add(button2);
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
      cf.sc_warnStr = "";
      for (int i=0; i<v.size(); i++) {
        if (lst.isIndexSelected(i)==false) {
          Integer n = (Integer) v.elementAt(i);
          cf.sc_warnStr = cf.sc_warnStr + ";" + n.toString() + ";";
        }
      }
  		setVisible(false);
	  	dispose();
	  }
  	else if (cmd.equals(button2.getActionCommand())) {
  		setVisible(false);
	  	dispose();
	  }
  }


}
