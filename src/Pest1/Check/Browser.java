package check;

import gui.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Browser
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: Browser.java,v 1.1 1999-01-18 23:27:25 swtech11 Exp $
 */
public class Browser extends Dialog implements ActionListener {

  pest parent;
  ModelCheckMsg mcm;

  Button button1;
  Button button2;

  Panel panel;

  

  /**
   * Konstruktor für das Eingabefenster
   */
  public Browser(pest parent,ModelCheckMsg _mcm)  {

    super(parent,"Browser",true);
    mcm = _mcm;
        
    this.parent = parent;

    Point p = parent.getLocation();
	  setLocation(p.x + 30 , p.y + 30);
    setLayout(new BorderLayout());


    List lst = new List();

    if (mcm.getErrorNumber()>0) {

      for (int i=1; (i<=mcm.getErrorNumber()); i++) {
        String s = new String( mcm.getErrorMsg(i)+" ("+mcm.getErrorCode(i)+")" );
        lst.add(s);
        // gui.userMessage("Check:   ["+mcm.etErrorPath(i)+"]");
      }

      add(lst,"Center");
    }
/**    if (mcm.getWarningNumber()>0) {
      gui.userMessage("Check:");
      gui.userMessage("Check: Warnmeldungen ( Anzahl: " + getWarningNumber() +  " ):");
      for (int i=1; (i<=getWarningNumber()); i++) {
        gui.userMessage("Check: - "+getWarningMsg(i)+" ("+getWarningCode(i)+")" );
        gui.userMessage("Check:   ["+getWarningPath(i)+"]");
      }
    }*/





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
  	add(panel,"South");

  	pack();
	  setResizable(true);
  	setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
  	String cmd = e.getActionCommand();
  	if (cmd.equals(button1.getActionCommand())) {

  		setVisible(false);
	  	dispose();
	  }
  	else if (cmd.equals(button2.getActionCommand())) {
  		setVisible(false);
	  	dispose();
	  }
  }


}
