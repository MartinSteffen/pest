/**
 * Nullbrowser
 *
 * Created: Fri Feb 12 1999, 17:24:30
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: Nullbrowser.java,v 1.1 1999-02-12 16:29:15 swtech14 Exp $
 */

package tesc2;


import absyn.*;

class Nullbrowser {
  private Statechart sChart;

  /**
   * Konstruktor.
   * Uebergabeobjekt:
   *   Statechart, dessen Koordinaten alle auf 'null' gesetzt werden sollen.
   */

  Nullbrowser (Statechart sc) {
    sChart = sc;
  }


  /**
   * Start-Methode.
   */

  void start () {
    if (sChart != null)
      browse (sChart.state);
  }


  private void browse (State s) {
    if (s instanceof Or_State)
      browse ((Or_State)s);
    if (s instanceof And_State)
      browse ((And_State)s);
    if (s instanceof Basic_State)
      browse ((Basic_State)s);
  } // method browse



  private void browse (Or_State os) {
    StateList sL;
    ConnectorList cL;
    TrList tL;

    if (os != null) {
      sL = os.substates;
      cL = os.connectors;
      tL = os.trs;

      while (sL != null) {
	browse (sL.head);
	sL = sL.tail;
      }

      while (cL != null) {
	browse (cL.head);
	cL = cL.tail;
      }

      while (tL != null) {
	browse (tL.head);
	tL = tL.tail;
      }

      if (os.name != null)
	os.name.position = null;

      os.rect = null;

    } // if os != null

  } // method browse (Or_State)



  private void browse (And_State as) {
    StateList sL;

    if (as != null) {
      sL = as.substates;

      while (sL != null) {
	browse (sL.head);
	sL = sL.tail;
      }

      if (as.name != null)
	as.name.position = null;

      as.rect = null;

    } // if as != null

  } // method browse (And_State)



  private void browse (Basic_State bs) {
    if (bs != null) {
      if (bs.name != null)
	bs.name.position = null;

      bs.rect = null;

    } // if bs != null

  } // method browse (Basic_State)



  private void browse (Tr t) {
    if (t != null) {
      t.points = null;
      if (t.label != null)
	t.label.position = null;
    }
  } // method browse (Tr)



  private void browse (Connector c) {
    if (c != null) {
      c.position = null;
      if (c.name != null)
	c.name.position = null;
    }
  } // method browse (Tr)

} // class Nullbrowser
