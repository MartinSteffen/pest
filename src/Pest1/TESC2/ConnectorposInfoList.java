/**
 * ConnectorposInfoList
 *
 * Created: Wed Dec 30 1998, 01:55:50
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: ConnectorposInfoList.java,v 1.1 1999-01-08 23:08:50 swtech14 Exp $
 */

package tesc2;


class ConnectorposInfoList {
  ConnectorposInfo head;
  ConnectorposInfoList tail;

  /**
   * Konstruktor fuer Liste mit ConnectorposInfo-Objekten.
   */

  ConnectorposInfoList (ConnectorposInfo cpi, ConnectorposInfoList cpiL) {
    head = cpi;
    tail = cpiL;
  } // Constructor ConnectorposInfoList


  /**
   * Gib ConnectorposInfoList-Objekt auf Bildschirm aus.
   */

  void print () {
    if (head != null) {
      System.out.println ();
      head.print();
    }
    if (tail != null)
      tail.print();
  } // method print

} // class ConnectorposInfoList
