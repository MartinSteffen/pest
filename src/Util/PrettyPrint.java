// The PrettyPrinter for Absyn-objects.
// Written by Eike Schulz, 01-12-1998.

package Util;


import Absyn.*;

public class PrettyPrint {
  private int column; // Offset-Spalte
  private int tab;    // Einrueck-Tabulator

  /**
   * Standardoffset-Spalte.
   */

  public static final int NORM_COLUMN = 0;

  /**
   * Standardtabulator.
   */

  public static final int NORM_TAB = 4;


  /**
   * Konstruktoren fuer PrettyPrint.
   * Uebergabewerte:
   * - Offset-Spalte c, in der die Ausgabe beginnen soll
   * - Einrueck-Tabulator t
   * Dabei muss c ein Vielfaches von t sein, anderenfalls
   * wird c entsprechend abgerundet.
   */

  public PrettyPrint (int c, int t) {

    // Fange Exception ab, falls Division durch 0 auftritt.

    try {
      column = (c < 0) ? NORM_COLUMN : (c - (c % t));
      tab = (t < 0) ? NORM_TAB : t;
    } catch (ArithmeticException e) {
      column = (c - (c % NORM_TAB));
      tab = NORM_TAB;
    }
  } // constructor PrettyPrint


  public PrettyPrint () { this (NORM_COLUMN, NORM_TAB); }


  /**
   * Starte Ausgabe auf Bildschirm (Absyn).
   */

  public void start (Absyn ab) {
    if (ab instanceof Action)
      output ((Action)ab);
    if (ab instanceof Aseq)
      output ((Aseq)ab);
    if (ab instanceof Bassign)
      output ((Bassign)ab);
    if (ab instanceof Boolstmt)
      output ((Boolstmt)ab);
    if (ab instanceof Bvar)
      output ((Bvar)ab);
    if (ab instanceof BvarList)
      output ((BvarList)ab);
    if (ab instanceof Compguard)
      output ((Compguard)ab);
    if (ab instanceof Comppath)
      output ((Comppath)ab);
    if (ab instanceof Connector)
      output ((Connector)ab);
    if (ab instanceof ConnectorList)
      output ((ConnectorList)ab);
    if (ab instanceof Dummy)
      output ((Dummy)ab);
    if (ab instanceof Guard)
      output ((Guard)ab);
    if (ab instanceof TLabel)
      output ((TLabel)ab);
    if (ab instanceof State)
      output ((State)ab);
    if (ab instanceof Statechart)
      output ((Statechart)ab);
    if (ab instanceof StatenameList)
      output ((StatenameList)ab);
    if (ab instanceof TrAnchor)
      output ((TrAnchor)ab);
  } // method start (Absyn)


  // Starte Ausgabe auf Bildschirm (Statechart).

  private void output (Statechart sChart) {
    if (sChart != null) {
      System.out.println (whiteSpace (column) + "[Statechart] ");
      PrettyPrint ppSChart = new PrettyPrint (column + tab, tab);

      // Gib SEventList aus.
      ppSChart.start (sChart.events);

      // Gib BvarList aus.
      ppSChart.start (sChart.bvars);

      // Gib PathList aus.
      ppSChart.start (sChart.cnames);

      // Gib State aus.
      ppSChart.start (sChart.state);
    }
  } // method output (Statechart)


  /**
   * Starte Ausgabe auf Bildschirm (SEventList).
   */

  public void start (SEventList eListIterator) {
    if (eListIterator != null) {
      System.out.println (whiteSpace (column) + "[SEventList] ");
      PrettyPrint ppEList = new PrettyPrint (column + tab, tab);
      while (eListIterator != null) {
        ppEList.start (eListIterator.head);
	eListIterator = eListIterator.tail;
      }
    }
  } // method start (SEventList)


  /**
   * Starte Ausgabe auf Bildschirm (SEvent).
   */

  public void start (SEvent ev) {
    if (ev != null)
      System.out.println (whiteSpace (column) + "[SEvent] " +
			  ev.name);
  } // method start (SEvent)


  // Starte Ausgabe auf Bildschirm (BvarList).

  private void output (BvarList bListIterator) {
    if (bListIterator != null) {
      System.out.println (whiteSpace (column) + "[BvarList] ");
      PrettyPrint ppBList = new PrettyPrint (column + tab, tab);
      while (bListIterator != null) {
        ppBList.start (bListIterator.head);
	bListIterator = bListIterator.tail;
      }
    }
  } // method output (BvarList)


  // Starte Ausgabe auf Bildschirm (Bvar).

  private void output (Bvar bv) {
    if (bv != null)
      System.out.println (whiteSpace (column) + "[Bvar] " +
			  bv.var);
  } // method output (Bvar)


  /**
   * Starte Ausgabe auf Bildschirm (PathList).
   */

  public void start (PathList pListIterator) {
    if (pListIterator != null) {
      System.out.println (whiteSpace (column) + "[PathList] ");
      PrettyPrint ppPList = new PrettyPrint (column + tab, tab);
      while (pListIterator != null) {
	  System.out.print (whiteSpace (column+tab) + "[Path:] ");
	  ppPList.start (pListIterator.head);
	  System.out.println();
	  pListIterator = pListIterator.tail;
      }
    }
  } // method start (PathList)


  /**
   * Starte Ausgabe auf Bildschirm (Path) = Liste von Namen.
   */

  public void start (Path pa) {
      if (pa != null)        
	  // das hilft hier nicht viel, denn
	  // wg. Overloading kommt man hier garnicht her
      System.out.print (pa.head);
      if (pa.tail != null) {
	  System.out.print(".");             // Punkt als Separator
	  start(pa.tail);
      }
  } // method start (Path)


  /**
   * Starte Ausgabe auf Bildschirm (StateList)
   */

  public void start (StateList sListIterator) {
    if (sListIterator != null) {
      System.out.println (whiteSpace (column) + "[StateList] ");
      PrettyPrint ppSList = new PrettyPrint (column + tab, tab);
      while (sListIterator != null) {
        ppSList.start (sListIterator.head);
	sListIterator = sListIterator.tail;
      }
    }
  } // method start (StateList)


  // Starte Ausgabe auf Bildschirm (State).

  private void output (State state) {
    if (state instanceof Basic_State)
      output ((Basic_State)state);
    if (state instanceof And_State)
      output ((And_State)state);
    if (state instanceof Or_State)
      output ((Or_State)state);
  } // method output (State)


  // Starte Ausgabe auf Bildschirm (Basic_State).

  private void output (Basic_State bState) {
    if (bState != null) {
      System.out.println (whiteSpace (column) + "[Basic_State] ");
      PrettyPrint ppBasicSt = new PrettyPrint (column + tab, tab);

      // Gib Statename aus.
      ppBasicSt.start (bState.name);

      // Gib java.awt.Rectangle aus.
      ppBasicSt.start (bState.rect);
    }
  } // method output (Basic_State)


  // Starte Ausgabe auf Bildschirm (And_State).

  private void output (And_State aState) {
    if (aState != null) {
      System.out.println (whiteSpace (column) + "[And_State] ");
      PrettyPrint ppAndSt = new PrettyPrint (column + tab, tab);

      // Gib Statename aus.
      ppAndSt.start (aState.name);

      // Gib java.awt.Rectangle aus.
      ppAndSt.start (aState.rect);

      // Gib StateList aus.
      ppAndSt.start (aState.substates);
    }
  } // method output (And_State)


  // Starte Ausgabe auf Bildschirm (Or_State)

  private void output (Or_State oState) {
    if (oState != null) {
      System.out.println (whiteSpace (column) + "[Or_State] ");
      PrettyPrint ppOrSt = new PrettyPrint (column + tab, tab);

      // Gib Statename aus.
      ppOrSt.start (oState.name);

      // Gib java.awt.Rectangle aus.
      ppOrSt.start (oState.rect);

      // Gib StateList aus.
      ppOrSt.start (oState.substates);

      // Gib TrList aus.
      ppOrSt.start (oState.trs);

      // Gib StatenameList aus.
      ppOrSt.start (oState.defaults);

      // Gib ConnectorList aus.
      ppOrSt.start (oState.connectors);
    }
  } // method output (Or_State)


  // Starte Ausgabe auf Bildschirm (StatenameList)

  private void output (StatenameList snListIterator) {
    if (snListIterator != null) {
      System.out.println (whiteSpace (column) + "[StatenameList] ");
      PrettyPrint ppSnList = new PrettyPrint (column + tab, tab);
      while (snListIterator != null) {
        ppSnList.start (snListIterator.head);
	snListIterator = snListIterator.tail;
      }
    }
  } // method output (StatenameList)


  // Starte Ausgabe auf Bildschirm (Statename).

  private void output (Statename sn) {
    if (sn != null)
      System.out.println (whiteSpace (column) + "[Statename] " +
			  sn.name);
  } // method output (Statename)


  /**
   * Starte Ausgabe auf Bildschirm (java.awt.Rectangle).
   */

  public void start (java.awt.Rectangle re) {
    if (re != null)
      System.out.println (whiteSpace (column) + "[Rectangle] " +
			  re.toString());
  } // method start (java.awt.Rectangle)


  /**
   * Starte Ausgabe auf Bildschirm (TrList).
   */

  public void start (TrList tListIterator) {
    if (tListIterator != null) {
      System.out.println (whiteSpace (column) + "[TrList] ");
      PrettyPrint ppTrList = new PrettyPrint (column + tab, tab);
      while (tListIterator != null) {
        ppTrList.start (tListIterator.head);
	tListIterator = tListIterator.tail;
      }
    }
  } // method start (TrList)


  /**
   * Starte Ausgabe auf Bildschirm (Tr).
   */

  public void start (Tr t) {
    if (t != null) {
      System.out.println (whiteSpace (column) + "[Tr] ");
      PrettyPrint ppTr = new PrettyPrint (column + tab, tab);

      // Gib java.awt.Point[] aus.
      ppTr.start (t.points);

      // Gib TrAnchor aus.
      ppTr.start (t.source);
      ppTr.start (t.target);

      // Gib Label aus.
      ppTr.start (t.label);
    }
  } // method start (Tr)


  /**
   * Starte Ausgabe auf Bildschirm (java.awt.Point[]).
   */

  public void start (java.awt.Point[] pArray) {
    if (pArray != null) {
      System.out.println (whiteSpace (column) + "[Point[]] ");
      PrettyPrint ppPArray = new PrettyPrint (column + tab, tab);
      for (int i=0; i < pArray.length; i++)
	ppPArray.start (pArray[i]);
    }
  } // method start (java.awt.Point[])


  /**
   * Starte Ausgabe auf Bildschirm (java.awt.Point).
   */

  public void start (java.awt.Point p) {
    if (p != null)
      System.out.println (whiteSpace (column) + "[Point] " +
			  p.toString());
  } // method start (java.awt.Point)


  // Starte Ausgabe auf Bildschirm (TrAnchor).

  private void output (TrAnchor ta) {
    if (ta instanceof Statename)
      output ((Statename)ta);
    if (ta instanceof Conname)
      output ((Conname)ta);
    if (ta instanceof UNDEFINED)
      output ((UNDEFINED)ta);
  } // method output (TrAnchor)


  // Starte Ausgabe auf Bildschirm (Conname).

  private void output (Conname cn) {
    if (cn != null)
      System.out.println (whiteSpace (column) + "[Conname] " +
			  cn.name);
  } // method output (Conname)


  // Starte Ausgabe auf Bildschirm (UNDEFINED).

  private void output (UNDEFINED un) {
    if (un != null)
      System.out.println (whiteSpace (column) + "[UNDEFINED] ");
  } // method output (UNDEFINED)


  // Starte Ausgabe auf Bildschirm (Label).

  private void output (TLabel l) {
    if (l != null) {
      System.out.println (whiteSpace (column) + "[TLabel] ");
      PrettyPrint ppLab = new PrettyPrint (column + tab, tab);

      // Gib Point aus.
      ppLab.start (l.position);

      // Gib Guard aus.
      ppLab.start (l.guard);

      // Gib Action aus.
      ppLab.start (l.action);
    }
  } // method output (TLabel)


  // Starte Ausgabe auf Bildschirm (Guard).

  private void output (Guard g) {
    if (g instanceof GuardBVar)
      output ((GuardBVar)g);
    if (g instanceof GuardCompg)
      output ((GuardCompg)g);
    if (g instanceof GuardCompp)
      output ((GuardCompp)g);
    if (g instanceof GuardEmpty)
      output ((GuardEmpty)g);
    if (g instanceof GuardEvent)
      output ((GuardEvent)g);
    if (g instanceof GuardNeg)
      output ((GuardNeg)g);
    if (g instanceof GuardUndet)
      output ((GuardUndet)g);
  } // method output (Guard)


  // Starte Ausgabe auf Bildschirm (GuardBVar).

  private void output (GuardBVar gbv) {
    if (gbv != null) {
      System.out.println (whiteSpace (column) + "[GuardBVar] ");
      PrettyPrint ppGbv = new PrettyPrint (column + tab, tab);

      // Gib Bvar aus.
      ppGbv.start (gbv.bvar);
    }
  } // method output (GuardBVar)


  // Starte Ausgabe auf Bildschirm (GuardCompg).

  private void output (GuardCompg gcg) {
    if (gcg != null) {
      System.out.println (whiteSpace (column) + "[GuardCompg] ");
      PrettyPrint ppGcg = new PrettyPrint (column + tab, tab);

      // Gib Compguard aus.
      ppGcg.start (gcg.cguard);
    }
  } // method output (GuardCompg)


  // Starte Ausgabe auf Bildschirm (GuardCompp).

  private void output (GuardCompp gcp) {
    if (gcp != null) {
      System.out.println (whiteSpace (column) + "[GuardCompp] ");
      PrettyPrint ppGcp = new PrettyPrint (column + tab, tab);

      // Gib Comppath aus.
      ppGcp.start (gcp.cpath);
    }
  } // method output (GuardCompp)


  // Starte Ausgabe auf Bildschirm (GuardEmpty).

  private void output (GuardEmpty ge) {
    if (ge != null) {
      System.out.println (whiteSpace (column) + "[GuardEmpty] ");
      PrettyPrint ppGe = new PrettyPrint (column + tab, tab);

      // Gib Dummy aus.
      ppGe.start (ge.dummy);
    }
  } // method output (GuardEmpty)


  // Starte Ausgabe auf Bildschirm (GuardEvent).

  private void output (GuardEvent gev) {
    if (gev != null) {
      System.out.println (whiteSpace (column) + "[GuardEvent] ");
      PrettyPrint ppGev = new PrettyPrint (column + tab, tab);

      // Gib SEvent aus.
      ppGev.start (gev.event);
    }
  } // method output (GuardEvent)


  // Starte Ausgabe auf Bildschirm (GuardNeg).

  private void output (GuardNeg gn) {
    if (gn != null) {
      System.out.println (whiteSpace (column) + "[GuardNeg] ");
      PrettyPrint ppGn = new PrettyPrint (column + tab, tab);

      // Gib Guard aus.
      ppGn.start (gn.guard);
    }
  } // method output (GuardNeg)


  // Starte Ausgabe auf Bildschirm (GuardUndet).

  private void output (GuardUndet gu) {
    if (gu != null)
      System.out.println (whiteSpace (column) + "[GuardUndet] " +
			  gu.undet);
  } // method output (GuardUndet)


  // Starte Ausgabe auf Bildschirm (Compguard).

  private void output (Compguard cg) {
    if (cg != null) {

      // Wandle int-Wert in Klassenkonstanten um:
      String op;
      switch (cg.eop)
	{
	case 0 : op = "<AND> "; break;
	case 1 : op = "<OR> "; break;
	case 2 : op = "<IMPLIES> "; break;
	case 3 : op = "<EQUIV> "; break;
	default : op = " "; break;
	} // switch

      System.out.println (whiteSpace (column) + "[Compguard] " + op);
      PrettyPrint ppCg = new PrettyPrint (column + tab, tab);

      // Gib Guard aus.
      ppCg.start (cg.elhs);
      ppCg.start (cg.erhs);
    }
  } // method output (Compguard)


  // Starte Ausgabe auf Bildschirm (Comppath).

  private void output (Comppath cp) {
    if (cp != null) {

      // Wandle int-Wert in Klassenkonstanten um:
      String op;
      switch (cp.pathop)
	{
	case 0 : op = "<IN> "; break;
	case 1 : op = "<ENTERED> "; break;
	case 2 : op = "<EXITED> "; break;
	default : op = " "; break;
	} // switch

      System.out.println (whiteSpace (column) + "[Comppath] " + op);
      PrettyPrint ppCp = new PrettyPrint (column + tab, tab);

      // Gib Path aus.
      ppCp.start (cp.path);
    }
  } // method output (Comppath)


  // Starte Ausgabe auf Bildschirm (Action).

  private void output (Action a) {
    if (a instanceof ActionBlock)
      output ((ActionBlock)a);
    if (a instanceof ActionEmpty)
      output ((ActionEmpty)a);
    if (a instanceof ActionEvt)
      output ((ActionEvt)a);
    if (a instanceof ActionStmt)
      output ((ActionStmt)a);
  } // method output (Action)


  // Starte Ausgabe auf Bildschirm (ActionBlock).

  private void output (ActionBlock ab) {
    if (ab != null) {
      System.out.println (whiteSpace (column) + "[ActionBlock] ");
      PrettyPrint ppAb = new PrettyPrint (column + tab, tab);

      // Gib Aseq aus.
      ppAb.start (ab.aseq);
    }
  } // method output (ActionBlock)


  // Starte Ausgabe auf Bildschirm (ActionEmpty).

  private void output (ActionEmpty ae) {
    if (ae != null) {
      System.out.println (whiteSpace (column) + "[ActionEmpty] ");
      PrettyPrint ppAe = new PrettyPrint (column + tab, tab);

      // Gib Dummy aus.
      ppAe.start (ae.dummy);
    }
  } // method output (ActionEmpty)


  // Starte Ausgabe auf Bildschirm (ActionEvt).

  private void output (ActionEvt aev) {
    if (aev != null) {
      System.out.println (whiteSpace (column) + "[ActionEvt] ");
      PrettyPrint ppAev = new PrettyPrint (column + tab, tab);

      // Gib SEvent aus.
      ppAev.start (aev.event);
    }
  } // method output (ActionEvt)


  // Starte Ausgabe auf Bildschirm (ActionStmt).

  private void output (ActionStmt ast) {
    if (ast != null) {
      System.out.println (whiteSpace (column) + "[ActionStmt] ");
      PrettyPrint ppAst = new PrettyPrint (column + tab, tab);

      // Gib Boolstmt aus.
      ppAst.start (ast.stmt);
    }
  } // method output (ActionStmt)


  // Starte Ausgabe auf Bildschirm (Aseq).

  private void output (Aseq aseqIterator) {
    if (aseqIterator != null) {
      System.out.println (whiteSpace (column) + "[Aseq] ");
      PrettyPrint ppAseq = new PrettyPrint (column + tab, tab);
      while (aseqIterator != null) {
        ppAseq.start (aseqIterator.head);
	aseqIterator = aseqIterator.tail;
      }
    }
  } // method output (Aseq)


  // Starte Ausgabe auf Bildschirm (Boolstmt).

  private void output (Boolstmt b) {
    if (b instanceof BAss)
      output ((BAss)b);
    if (b instanceof MFalse)
      output ((MFalse)b);
    if (b instanceof MTrue)
      output ((MTrue)b);
  } // method output (Boolstmt)


  // Starte Ausgabe auf Bildschirm (BAss).

  private void output (BAss bass) {
    if (bass != null) {
      System.out.println (whiteSpace (column) + "[BAss] ");
      PrettyPrint ppBass = new PrettyPrint (column + tab, tab);

      // Gib Bassign aus.
      ppBass.start (bass.ass);
    }
  } // method output (BAss)


  // Starte Ausgabe auf Bildschirm (MFalse).

  private void output (MFalse mf) {
    if (mf != null) {
      System.out.println (whiteSpace (column) + "[MFalse] ");
      PrettyPrint ppMf = new PrettyPrint (column + tab, tab);

      // Gib Bvar aus.
      ppMf.start (mf.var);
    }
  } // method output (MFalse)


  // Starte Ausgabe auf Bildschirm (MTrue).

  private void output (MTrue mt) {
    if (mt != null) {
      System.out.println (whiteSpace (column) + "[MTrue] ");
      PrettyPrint ppMt = new PrettyPrint (column + tab, tab);

      // Gib Bvar aus.
      ppMt.start (mt.var);
    }
  } // method output (Mtrue)


  // Starte Ausgabe auf Bildschirm (Bassign).

  private void output (Bassign ba) {
    if (ba != null) {
      System.out.println (whiteSpace (column) + "[Bassign] ");
      PrettyPrint ppBa = new PrettyPrint (column + tab, tab);

      // Gib Bvar aus.
      ppBa.start (ba.blhs);

      // Gib Guard aus.
      ppBa.start (ba.brhs);
    }
  } // method output (Bassign)


  // Starte Ausgabe auf Bildschirm (ConnectorList).

  private void output (ConnectorList cListIterator) {
    if (cListIterator != null) {
      System.out.println (whiteSpace (column) + "[ConnectorList] ");
      PrettyPrint ppCList = new PrettyPrint (column + tab, tab);
      while (cListIterator != null) {
        ppCList.start (cListIterator.head);
	cListIterator = cListIterator.tail;
      }
    }
  } // method output (ConnectorList)


  // Starte Ausgabe auf Bildschirm (Connector).

  private void output (Connector co) {
    if (co != null) {
      System.out.println (whiteSpace (column) + "[Connector] ");
      PrettyPrint ppCon = new PrettyPrint (column + tab, tab);

      // Gib Conname aus.
      ppCon.start (co.name);

      // Gib Point aus.
      ppCon.start (co.position);
    }
  } // method output (Connector)


  // Starte Ausgabe auf Bildschirm (Dummy).

  private void output (Dummy d) {
    if (d != null)
      System.out.println (whiteSpace (column) + "[Dummy] ");
  } // method output (Dummy)


  // Gib Fuellzeichen-String der Laenge n zurueck.
  // Uebergabewert:
  // - Laenge n des Strings
  // Rueckgabeobjekt:
  // - String mit uebergebener Laenge n

  private String whiteSpace (int n) {
    String str = "";
    for (int i=0; i<n; i++)
      str = (i % tab == 0) ? (str + "|") : (str + " ");
    return str;
  } // method whiteSpace

} // class PrettyPrint
