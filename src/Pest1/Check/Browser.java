package check;

import gui.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Browser
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: Browser.java,v 1.4 1999-01-19 22:57:33 swtech11 Exp $
 */
public class Browser extends Dialog implements ActionListener {

  pest parent;
  ModelCheckMsg mcm;

  Button button1;

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
    
    Panel output = new Panel(new BorderLayout());

    List lst = new List(mcm.getErrorNumber()+mcm.getWarningNumber()+2);
    lst.setMultipleMode(true);

    if (mcm.getErrorNumber()>0) {
      lst.add("Fehlermeldungen ( Anzahl " + mcm.getErrorNumber() +  " ):");
      for (int i=1; (i<=mcm.getErrorNumber()); i++) {
        lst.add(mcm.getErrorMsg(i)+" ("+mcm.getErrorCode(i)+")");
        // gui.userMessage("Check:   ["+mcm.etErrorPath(i)+"]");
      }
    }
    if (mcm.getWarningNumber()>0) {
      lst.add("Warnmeldungen ( Anzahl " + mcm.getWarningNumber() +  " ):");
      for (int i=1; (i<=mcm.getWarningNumber()); i++) {
        lst.add(mcm.getWarningMsg(i)+" ("+mcm.getWarningCode(i)+")");
	    //gui.userMessage("Check:   ["+getWarningPath(i)+"]");
      }
    }

    if (mcm.getWarningNumber()>0 | mcm.getErrorNumber()>0) {
      output.add(lst);
     }

    add(output,"Center");

    // Steuerungsbuttons
  	panel = new Panel(new FlowLayout());
  	panel.setBackground(Color.gray);
	  panel.setForeground(Color.white);
  	button1 = new Button("OK");
	  button1.setActionCommand("OK");
  	button1.addActionListener(this);
	  panel.add(button1);
  	add(panel,"South");

  	pack();
	  setResizable(true);
//setSize(d);
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
