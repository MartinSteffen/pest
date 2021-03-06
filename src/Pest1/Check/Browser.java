package check;

import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import editor.*;
import absyn.*;
import java.util.*;

/**
 *  Browser
 *
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 *  @version  $Id: Browser.java,v 1.16 1999-02-14 20:56:20 swtech11 Exp $
 */
class Browser extends Dialog implements ActionListener {
  pest parent = null;
  GUIInterface gui;
  Editor edit = null;
  ModelCheckMsg mcm;
    //ModelCheck mc;
  CheckConfig cf;
  String art;
  List lst; // Liste f�r Meldungen
  Button button1; // OK Knopf
  Button button2; // SAVE All Knopf
  Button button3; // SAVE Selected Knopf

  Panel panel;
  boolean high = false; // highlighten ?
  boolean SE = false;
  boolean SW = false;

  /**
   * Konstruktor f�r das Eingabefenster
   */
  public Browser(GUIInterface _gui, Editor _edit,ModelCheckMsg _mcm, CheckConfig _cf, String _art)  {
    super((pest)_gui,"Message-Browser",false);
    gui=_gui;
    parent=(pest)gui;
    edit = _edit;
    mcm  = _mcm;
    cf   = _cf;
    art  = _art;
    this.parent = parent;
    Point p = parent.getLocation();
    setLocation(p.x + 30 , p.y + 30);
    setLayout(new BorderLayout());

    // Liste f�r Messages anlegen
    int ml = mcm.getErrorNumber()*2+2;
    if (cf.sc_warning!=0) { ml = ml + mcm.getWarningNumber()*2+1; }
    if (ml>30) { ml = 30; }
    lst = new List(ml);
    lst.setMultipleMode(true);
    lst.setFont(new Font("Serif",Font.PLAIN,14));

    if ( art.equals("") ) { // Syntax Check
      // Fehler ausgeben
      lst.add("Fehlermeldungen ( Anzahl " + mcm.getErrorNumber() +  " ):");
      if (mcm.getErrorNumber()>0) {
        for (int i=1; (i<=mcm.getErrorNumber()); i++) {
          lst.add("- "+mcm.getErrorMsg(i)+" ("+mcm.getErrorCode(i)+")");
          lst.add("     "+mcm.getErrorPath(i)); }}
      // Warnungen ausgeben
      if (cf.sc_warning==1) { // alle
        lst.add("Warnmeldungen ( Anzahl " + mcm.getWarningNumber() +  " ):");
        if (mcm.getWarningNumber()>0) {
          for (int i=1; (i<=mcm.getWarningNumber()); i++) {
            lst.add("- "+mcm.getWarningMsg(i)+" ("+mcm.getWarningCode(i)+")");
            lst.add("     "+mcm.getWarningPath(i)); }}}
      else if (cf.sc_warning==2){ // nur bestimmte
        lst.add("Warnmeldungen ( Anzahl " + mcm.getWarningNumber() +  " ):");
        if (mcm.getWarningNumber()>0) {
          for (int i=1; (i<=mcm.getWarningNumber()); i++) {
            int wci = mcm.getWarningCode(i);
            String wc = new String();
            if ( cf.sc_warnStr.indexOf(";"+ wc.valueOf(wci) +";" ) ==-1  ) {
              lst.add("- "+mcm.getWarningMsg(i)+" ("+mcm.getWarningCode(i)+")");
              lst.add("     "+mcm.getWarningPath(i));
            }
          }
        }
      }
    }
    else { // Browser
      lst.add(art+" ist ein:");
      for (int i=1; (i<=mcm.getErrorNumber()); i++) {
        lst.add("- "+mcm.getErrorPath(i));
        lst.add("");
      }
    }



    // Methode: Markierungen auswerten
    lst.addItemListener( new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        selectConfirm();
        selectObject();
      }
    });
    add(lst,"Center");


    // Steuerungsbuttons
  	panel = new Panel(new FlowLayout());
  	panel.setBackground(Color.gray);
	  panel.setForeground(Color.white);
  	button1 = new Button("OK");
	  button1.setActionCommand("OK");
  	button1.addActionListener(this);
	  panel.add(button1);
    panel.add(new Label(" "));
  	button2 = new Button("Alles Speichern");
	  button2.setActionCommand("SAVE");
  	button2.addActionListener(this);
	  panel.add(button2);
    panel.add(new Label(" "));    
  	button3 = new Button("Selektiertes Speichern");
	  button3.setActionCommand("SAVE Sel");
  	button3.addActionListener(this);
	  panel.add(button3);
  	add(panel,"South");
    pack();
	  setResizable(true);
    Dimension d = getSize();
    if (d.width<400) { // Weite manual anpassen, da UNIX es nicht schafft
      d.width=450;     // im Gegesatz zur Windows Version
      setSize(d);
    }  
  	setVisible(true);
  }

  // Selection anpassen
  void selectConfirm() {
    int ix[] = lst.getSelectedIndexes();
    for (int i=0; i < ix.length; i++) {
      for (int c=2; c < (1+mcm.getErrorNumber()*2)+1; c=c+2) { // Fehler umschalten
        if (c == ix[i]) {
          lst.deselect(ix[i]);
          if (lst.isIndexSelected(ix[i]-1)) { lst.deselect(ix[i]-1); } else { lst.select(ix[i]-1); }}}
      for (int c=mcm.getErrorNumber()*2+3;
           c < (mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+2); c=c+2) { // Warnungen umschalten
        if (c == ix[i]) {
          lst.deselect(ix[i]);
          if (lst.isIndexSelected(ix[i]-1)) { lst.deselect(ix[i]-1); } else { lst.select(ix[i]-1); }}}
    }
    // Error global behandeln
    if (mcm.getErrorNumber()==0 & lst.isIndexSelected(0)) {lst.deselect(0);}
    else if (lst.isIndexSelected(0) & SE==false) {
      SE = true;
      for (int c=1; (c<mcm.getErrorNumber()*2+1); c=c+2) {
        lst.select(c); }}
    else if (lst.isIndexSelected(0)==false & SE==true) {
      SE = false;
      for (int c=1; (c<mcm.getErrorNumber()*2+1); c=c+2) {
        lst.deselect(c); }}
    // Warning global behandeln
    if (mcm.getWarningNumber()==0 & lst.isIndexSelected(mcm.getErrorNumber()*2+1)) {lst.deselect(mcm.getErrorNumber()*2+1);}
    else if (lst.isIndexSelected(mcm.getErrorNumber()*2+1) & SW==false) {
      SW = true;
      for (int c=mcm.getErrorNumber()*2+2; (c<mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+1); c=c+2) {
        lst.select(c); }}
    else if (lst.isIndexSelected(mcm.getErrorNumber()*2+1)==false & SW==true) {
      SW = false;
      for (int c=mcm.getErrorNumber()*2+2; (c<mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+1); c=c+2) {
        lst.deselect(c); }}
  }

  // Error und Warnings highlighten
  void selectObject() {
    if (edit != null) { high = true; } else { high = false; } // mu� noch um Config erweiter werden
    highlightObject ho;
    if (high==true) { ho = new highlightObject(true); }
    int ix[] = lst.getSelectedIndexes();
    for (int i=0; i < ix.length; i++) {
      if (ix[i]>0 & ix[i]<mcm.getErrorNumber()*2+1) { // Fehler bearbeiten
        int c = (ix[i]+1) / 2;
        Object o = mcm.getErrorHiObj(c);
        if ( o instanceof Absyn & high==true) { ho = new highlightObject((Absyn)o,cf.color[cf.high_color]); }
        else if ( o instanceof Vector & high==true) {
          Vector v = (Vector)o;
          for (int j=0; j<v.size(); j++) {
            Absyn ao = (Absyn)v.elementAt(j);
            ho = new highlightObject(ao,cf.color[cf.high_color]); }}}
      if (ix[i]>mcm.getErrorNumber()*2+1 & ix[i]<mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+2) { // Warnungen bearbeiten
        int c = ix[i]/2 - mcm.getErrorNumber();
        Object o = mcm.getWarningHiObj(c);
        if ( o instanceof Absyn & high==true) { ho = new highlightObject((Absyn)o,cf.color[cf.high_color]); }
        else if ( o instanceof Vector & high==true) {
          Vector v = (Vector)o;
          for (int j=0; j<v.size(); j++) {
            Absyn ao = (Absyn)v.elementAt(j);
            ho = new highlightObject(ao,cf.color[cf.high_color]); }}}
    }
    if (high==true) { ho = new highlightObject(); }
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
    else if (cmd.equals(button2.getActionCommand())) {
      if (art.equals("")) {
        FileDialog fd=new FileDialog(parent,new String("Ergebnis des Syntax Checks"),FileDialog.SAVE);
        fd.show();
        String fn=fd.getFile();
        String dn=fd.getDirectory();
        if (fn!=null) { try {
          FileOutputStream fos = new FileOutputStream(dn+fn);
          PrintWriter out = new PrintWriter(fos);
          out.println("Meldungen des Syntax Check:");
          out.println();
          out.println("Fehlermeldungen ( Anzahl: " + mcm.getErrorNumber() +  " ):");
          if (mcm.getErrorNumber()>0) {
            for (int i=1;(i<=mcm.getErrorNumber());i++) {
              out.println( mcm.getError(i) ); } }
          out.println();
          out.println("Warnmeldungen ( Anzahl: " + mcm.getWarningNumber() +  " ):");
          if (mcm.getWarningNumber()>0) {
            for (int i=1;(i<=mcm.getWarningNumber());i++) {
              out.println( mcm.getWarning(i) ); } }
          out.flush();
          out.close();
        }
        catch (Exception ex) { gui.OkDialog("Fehlermeldung","Die Speicherung ist fehlgeschlagen!"); }
      }}
      else  {
        FileDialog fd=new FileDialog(parent,new String("Ergebnis von Crossreference"),FileDialog.SAVE);
        fd.show();
        String fn=fd.getFile();
        String dn=fd.getDirectory();
        if (fn!=null) { try {
          FileOutputStream fos = new FileOutputStream(dn+fn);
          PrintWriter out = new PrintWriter(fos);
          out.println("Meldungen von Crossreference:");
          out.println();
          out.println(art+ " ist ein:");
          for (int i=1;(i<=mcm.getErrorNumber());i++) {
            out.println( "- "+mcm.getErrorPath(i) ); }
          out.flush();
          out.close();
        }
        catch (Exception ex) { gui.OkDialog("Fehlermeldung","Die Speicherung ist fehlgeschlagen!"); }
      }}
    }
    else if (cmd.equals(button3.getActionCommand())) {
      if (art.equals("")) {
        FileDialog fd=new FileDialog(parent,new String("Selektiertes Ergebnis des Syntax Checks"),FileDialog.SAVE);
        fd.show();
        String fn=fd.getFile();
        String dn=fd.getDirectory();
        if (fn!=null) {try {
          FileOutputStream fos = new FileOutputStream(dn+fn);
          PrintWriter out = new PrintWriter(fos);
          out.println("Selektierte Meldungen des Syntax Check:");
          out.println();
          int ix[] = lst.getSelectedIndexes();
          out.println("Fehlermeldungen ( Anzahl: " + mcm.getErrorNumber() +  " ):");
          for (int i=0; i < ix.length; i++) {
            if (ix[i]>0 & ix[i]<mcm.getErrorNumber()*2+1) { // Fehler bearbeiten
              int c = (ix[i]+1) / 2;
              out.println( mcm.getError(c) );
            }
          }
          out.println();
          out.println("Warnmeldungen ( Anzahl: " + mcm.getWarningNumber() +  " ):");
          for (int i=0; i < ix.length; i++) {
            if (ix[i]>mcm.getErrorNumber()*2+1 & ix[i]<mcm.getErrorNumber()*2+mcm.getWarningNumber()*2+2) { // Warnungen bearbeiten
              int c = ix[i]/2 - mcm.getErrorNumber();
              out.println( mcm.getWarning(c) );
            }
          }
          out.flush();
          out.close();
        }
        catch (Exception ex) { gui.OkDialog("Fehlermeldung","Die Speicherung ist fehlgeschlagen!");  }
      } }
      else {
        FileDialog fd=new FileDialog(parent,new String("Selektiertes Ergebnis von Crossreference"),FileDialog.SAVE);
        fd.show();
        String fn=fd.getFile();
        String dn=fd.getDirectory();
        if (fn!=null) {try {
          FileOutputStream fos = new FileOutputStream(dn+fn);
          PrintWriter out = new PrintWriter(fos);
          out.println("Selektierte Meldungen von Crossreference:");
          out.println();
          int ix[] = lst.getSelectedIndexes();
          out.println(art+ " ist ein:");
          for (int i=0; i < ix.length; i++) {
            if (ix[i]>0 & ix[i]<mcm.getErrorNumber()*2+1) { // Fehler bearbeiten
              int c = (ix[i]+1) / 2;
              out.println("- "+mcm.getErrorPath(c) );
            }
          }
          out.flush();
          out.close();
        }
        catch (Exception ex) { gui.OkDialog("Fehlermeldung","Die Speicherung ist fehlgeschlagen!");  }
      } }
    }
  }


}


