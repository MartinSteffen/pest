/**
 * StateposInfoList
 *
 * Created: Thu Dec 24 1998, 00:00:00
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: StateposInfoList.java,v 1.1 1999-01-08 23:14:24 swtech14 Exp $
 */

package tesc2;


import absyn.State;

class StateposInfoList {
  StateposInfo head;
  StateposInfoList tail;

  /**
   * Konstruktor fuer Liste mit StateposInfo-Objekten.
   */

  StateposInfoList (StateposInfo spi, StateposInfoList spiL) {
    head = spi;
    tail = spiL;
  } // Constructor StateposInfoList


  /**
   * Gib StateposInfoList-Objekt auf Bildschirm aus.
   */

  void print () {
    if (head != null) {
      System.out.println ();
      head.print();
    }
    if (tail != null)
      tail.print();
  } // method print

} // class StateposInfoList
