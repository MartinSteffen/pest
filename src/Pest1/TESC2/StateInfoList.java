/**
 * StateInfoList
 *
 * Created: Thu Dec 24 1998, 00:00:00
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: StateInfoList.java,v 1.1 1999-01-08 23:13:16 swtech14 Exp $
 */

package tesc2;


class StateInfoList {
  StateInfo head;
  StateInfoList tail;

  /**
   * Konstruktor fuer Liste mit StateInfo-Objekten.
   */

  StateInfoList (StateInfo si, StateInfoList siL) {
    head = si;
    tail = siL;
  } // Constructor StateInfoList


  /**
   * Gib StateInfoList-Objekt auf Bildschirm aus.
   */

  void print () {
    if (head != null) {
      System.out.println ();
      head.print();
      if (tail != null)
	tail.print();
    }
  } // method print

} // class StateInfoList
