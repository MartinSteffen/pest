package check;

import gui.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenster zur Eingabe der Optionen des Syntax Checks, die in der Klasse CheckConfig gespeichert werden
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: CheckOption.java,v 1.6 1999-02-08 23:38:48 swtech11 Exp $
 *  @see CheckConfig
 */
public class CheckOption extends Dialog implements ActionListener {
  pest parent;
  CheckConfig cf;
  Button button1;
  Button button2;
  Checkbox sc_warn;
  Checkbox cr_high;
  Checkbox sc_brow;
  Choice ch;
  String[] farbe;

  /**
   * Konstruktor für das Eingabefenster
   *
   * @param parent notwendige Referenz auf das aufrufende Fenster
   * @param _cf die Klasse zum Speichern der Optionen des Checks
  */
  public CheckOption(pest parent,CheckConfig _cf)  {
    super(parent,"Optionen beim Check",true);
    this.parent = parent;
    if ( _cf==null ) { cf = new CheckConfig(); } else { cf = _cf; }

  /**System.out.println(cf.sc_browser);
  System.out.println(cf.sc_warning);
  System.out.println(cf.cr_highlight);
  System.out.println(cf.high_color);*/

    Point p = parent.getLocation();
	  setLocation(p.x + 30 , p.y + 30);
    setLayout(new GridLayout(8,1));
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
    // Highlight Color auswählen
  	panel = new Panel(new GridLayout(1,1));
	  panel.setBackground(Color.gray);
  	panel.setForeground(Color.white);
  	panel.add(new Label("  Highlight Color:"));
    add(panel);
    // Highlight Color auswählen
  	panel = new Panel(new FlowLayout(FlowLayout.LEFT));
    panel.setBackground(Color.lightGray);
    panel.setForeground(Color.black);
  	farbe = new String[13];
  	farbe[0] = "Rot";
	  farbe[1] = "Blau";
  	farbe[2] = "Gelb";
	  farbe[3] = "Grün";
	  farbe[4] = "Orange";
	  farbe[5] = "Pink";
	  farbe[6] = "Magenta";
	  farbe[7] = "Cyan";
    farbe[8] = "Schwarz";
  	farbe[9] = "Weiß";
	  farbe[10]= "Hell-Grau";
	  farbe[11]= "Dunkel-Grau";
	  farbe[12]= "Grau";
    ch = new Choice();
    ch.setForeground(Color.black);
    for (int i=0;i<=12; i++) {
      ch.addItem(farbe[i]);
    }
    ch.select(cf.high_color);
    panel.add(ch);
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
	  	cf.sc_warning   = sc_warn.getState();
		  cf.cr_highlight = cr_high.getState();
  		cf.sc_browser   = sc_brow.getState();
      cf.high_color   = ch.getSelectedIndex();
  		setVisible(false);
	  	dispose();
	  }
  	else if (cmd.equals(button2.getActionCommand())) {
  		setVisible(false);
	  	dispose();
	  }
  }


}
