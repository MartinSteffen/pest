package check;

import gui.*;
import java.awt.*;
import java.awt.event.*;
import editor.*;
import absyn.*;
import java.util.*;

/**
 * Browser
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: Browser.java,v 1.5 1999-01-21 22:39:14 swtech11 Exp $
 */
public class Browser extends Dialog implements ActionListener {
  pest parent = null;
  Editor edit = null;
  ModelCheckMsg mcm;
  List lst; // Liste für Meldungen
  Button button1; // OK Knopf
  Panel panel;
  boolean SE = false;
  boolean SW = false;

  /**
   * Konstruktor für das Eingabefenster
   */
  public Browser(pest parent,Editor _edit,ModelCheckMsg _mcm)  {
    super(parent,"Browser",false);
    edit = _edit;
    mcm = _mcm;

    this.parent = parent;

    Point p = parent.getLocation();
    setLocation(p.x + 30 , p.y + 30);
    setLayout(new BorderLayout());
    
    Panel output = new Panel(new BorderLayout());

    lst = new List(mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+2);
    lst.setMultipleMode(true);
    lst.setFont(new Font("Serif",Font.BOLD,14));
    // lst.setForeground(Color.white);

    // Fehler ausgeben
    lst.setFont(new Font("Serif",Font.BOLD,14));
    lst.add("Fehlermeldungen ( Anzahl " + mcm.getErrorNumber() +  " ):");
    if (mcm.getErrorNumber()>0) {
      for (int i=1; (i<=mcm.getErrorNumber()); i++) {
        lst.setFont(new Font("Serif",Font.PLAIN,14));
        lst.add("- "+mcm.getErrorMsg(i)+" ("+mcm.getErrorCode(i)+")");
        lst.add("     "+mcm.getErrorPath(i));
      }
    }
    // Warnungen ausgeben
    lst.setFont(new Font("Serif",Font.BOLD,14));
    lst.add("Warnmeldungen ( Anzahl " + mcm.getWarningNumber() +  " ):");
    if (mcm.getWarningNumber()>0) {
      for (int i=1; (i<=mcm.getWarningNumber()); i++) {
        lst.setFont(new Font("Serif",Font.PLAIN,14));
        lst.add("- "+mcm.getWarningMsg(i)+" ("+mcm.getWarningCode(i)+")");
        lst.add("     "+mcm.getWarningPath(i));
      }
    }

    // Markierungen auswerten
    if (mcm.getWarningNumber()>0 | mcm.getErrorNumber()>0) {
      lst.addItemListener( new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          selectConfirm();
          selectObject();
        }
      });
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

  // Selection anpassen
  void selectConfirm() {
    int ix[] = lst.getSelectedIndexes();
    for (int i=0; i < ix.length; i++) {
      for (int c=2; c < (1+mcm.getErrorNumber()*2)+1; c=c+2) { // Fehler umschalten
        if (c == ix[i]) {
          lst.deselect(ix[i]);
          if (lst.isIndexSelected(ix[i]-1)) { lst.deselect(ix[i]-1); } else { lst.select(ix[i]-1); }
        }
      }
      for (int c=mcm.getErrorNumber()*2+3;
           c < (mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+2); c=c+2) { // Warnungen umschalten
        if (c == ix[i]) {
          lst.deselect(ix[i]);
          if (lst.isIndexSelected(ix[i]-1)) { lst.deselect(ix[i]-1); } else { lst.select(ix[i]-1); }
        }
      }
    }
    // Error global behandeln
    if (mcm.getErrorNumber()==0 & lst.isIndexSelected(0)) {lst.deselect(0);}
    else if (lst.isIndexSelected(0) & SE==false) {
      SE = true;
      for (int c=1; (c<mcm.getErrorNumber()*2+1); c=c+2) {
        lst.select(c);
      }
    }
    else if (lst.isIndexSelected(0)==false & SE==true) {
      SE = false;
      for (int c=1; (c<mcm.getErrorNumber()*2+1); c=c+2) {
        lst.deselect(c);
      }
    }
    // Warning global behandeln
    if (mcm.getWarningNumber()==0 & lst.isIndexSelected(mcm.getErrorNumber()*2+1)) {lst.deselect(mcm.getErrorNumber()*2+1);}
    else if (lst.isIndexSelected(mcm.getErrorNumber()*2+1) & SW==false) {
      SW = true;
      for (int c=mcm.getErrorNumber()*2+2; (c<mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+1); c=c+2) {
        lst.select(c);
      }
    }
    else if (lst.isIndexSelected(mcm.getErrorNumber()*2+1)==false & SW==true) {
      SW = false;
      for (int c=mcm.getErrorNumber()*2+2; (c<mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+1); c=c+2) {
        lst.deselect(c);
      }
    }
  }

  // Error und Warnings highlighten
  void selectObject() {
    highlightObject ho;
    if (edit!=null) { ho = new highlightObject(true); }
    int ix[] = lst.getSelectedIndexes();
    for (int i=0; i < ix.length; i++) {
      if (ix[i]>0 & ix[i]<mcm.getErrorNumber()*2+1) {
        int c = (ix[i]+1) / 2;
        Object o = mcm.getErrorHiObj(c);
        if ( o instanceof Absyn & edit!=null) { ho = new highlightObject((Absyn)o,Color.black); }
        else if ( o instanceof Vector & edit!=null) {
          Vector v = (Vector)o;
          for (int j=0; j<v.size(); j++) {
            Absyn ao = (Absyn)v.elementAt(j);
            ho = new highlightObject(ao,Color.black);
          }
        }
      }
      if (ix[i]>mcm.getErrorNumber()*2+1 & ix[i]<mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+2) {
        int c = ix[i]/2 - mcm.getErrorNumber();
        Object o = mcm.getWarningHiObj(c);
        if ( o instanceof Absyn & edit!=null) { ho = new highlightObject((Absyn)o,Color.black); }
        else if ( o instanceof Vector & edit!=null) {
          Vector v = (Vector)o;
          for (int j=0; j<v.size(); j++) {
            Absyn ao = (Absyn)v.elementAt(j);
            ho = new highlightObject(ao,Color.black);
          }
        }
      }
    }
    if (edit!=null) { ho = new highlightObject(); }
  }

  public void actionPerformed(ActionEvent e) {
  	String cmd = e.getActionCommand();
  	if (cmd.equals(button1.getActionCommand())) {
      if (edit!=null) {
        highlightObject ho = new highlightObject(true);
        ho = new highlightObject();
      }
  		setVisible(false);
	  	dispose();
	  }
  }


}
