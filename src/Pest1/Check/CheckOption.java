package check;

import gui.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenster zur Eingabe der Optionen des Syntax Checks
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: CheckOption.java,v 1.2 1999-01-19 10:18:37 swtech11 Exp $
 */
public class CheckOption extends Dialog implements ActionListener {

  pest parent;
  CheckConfig cf;

  Button button1;
  Button button2;

  Checkbox sc_high;
  Checkbox sc_warn;
  Checkbox cr_high;
  Checkbox sc_brow;
  

  /**
   * Konstruktor für das Eingabefenster
   */
  public CheckOption(pest parent,CheckConfig _cf)  {
    super(parent,"Syntax Check Optionen",true);
    this.parent = parent;
    cf = _cf;

    Point p = parent.getLocation();
	  setLocation(p.x + 30 , p.y + 30);
    setLayout(new GridLayout(7,1));

    // Ueberschrift Syntax Check
  	Panel panel = new Panel(new GridLayout(1,1));
	  panel.setBackground(Color.gray);
  	panel.setForeground(Color.white);
  	panel.add(new Label("  Syntax Check:"));
	  add(panel);

    // Browser benutzen
  	panel = new Panel(new GridLayout(1,1));
    panel.setBackground(Color.lightGray);
    panel.setForeground(Color.black);
    sc_brow = new Checkbox("Message Browser benutzen",cf.sc_browser );
    panel.add(sc_brow);
    add(panel);

    // Check highlighten
  	panel = new Panel(new GridLayout(1,1));
    panel.setBackground(Color.lightGray);
    panel.setForeground(Color.black);
    sc_high = new Checkbox("Ergebnisse highlighten",cf.sc_highlight);
    panel.add(sc_high);
    add(panel);

    // Check Warnungen
  	panel = new Panel(new GridLayout(1,1));
    panel.setBackground(Color.lightGray);
    panel.setForeground(Color.black);
    sc_warn = new Checkbox("Warnungen ausgeben",cf.sc_warning);
    panel.add(sc_warn);
    add(panel);

    // Ueberschrift Crossreference
  	panel = new Panel(new GridLayout(1,1));
	  panel.setBackground(Color.gray);
  	panel.setForeground(Color.white);
  	panel.add(new Label("  Crossreference:"));
	  add(panel);

    // Crossreference highlighten
  	panel = new Panel(new GridLayout(1,1));
    panel.setBackground(Color.lightGray);
    panel.setForeground(Color.black);
    cr_high = new Checkbox("Ergebnisse highlighten",cf.cr_highlight);
    panel.add(cr_high);
    add(panel);

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
  	add(panel);

  	pack();
	  setResizable(true);
  	setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
  	String cmd = e.getActionCommand();
  	if (cmd.equals(button1.getActionCommand())) {
  		cf.sc_highlight = sc_high.getState();
	  	cf.sc_warning   = sc_warn.getState();
		cf.cr_highlight = cr_high.getState();
  		cf.sc_browser   = sc_brow.getState();
  		setVisible(false);
	  	dispose();
	  }
  	else if (cmd.equals(button2.getActionCommand())) {
  		setVisible(false);
	  	dispose();
	  }
  }


}
