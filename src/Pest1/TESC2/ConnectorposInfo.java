/**
 * ConnectorposInfo
 *
 * Created: Wed Dec 30 1998, 01:48:30
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: ConnectorposInfo.java,v 1.1 1999-01-08 23:07:59 swtech14 Exp $
 */

package tesc2;


import absyn.Connector;

class ConnectorposInfo {

  // Mit Positions-Informationen auszustattender Connector.
  Connector con;

  // Zaehler fuer x-Positionen.
  int counter_xa, counter_xb;

  // Zaehler fuer y-Positionen.
  int counter_ya, counter_yb;


  /**
   * ConnectorposInfo-Konstruktor.
   *
   * Uebergabeobjekt:
   *   Referenz auf Connector-Objekt.
   */

  ConnectorposInfo (Connector c) {
    con = c;
    counter_xa = 0;
    counter_xb = 0;
    counter_ya = 0;
    counter_yb = 0;
  } // constructor ConnectorposInfo


  /**
   * Gib ConnectorposInfo-Objekt auf Bildschirm aus.
   */

  void print () {
    if (con != null)
      if (con.name != null)
	if (con.name.name != null)
	  System.out.println ("Conname: " + con.name.name);
    System.out.println ("Counter xa: " + counter_xa);
    System.out.println ("Counter xb: " + counter_xb);
    System.out.println ("Counter ya: " + counter_ya);
    System.out.println ("Counter yb: " + counter_yb);
  } // method print

} // class ConnectorposInfo
