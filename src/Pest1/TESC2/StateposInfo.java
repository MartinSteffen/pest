/**
 * StateposInfo
 *
 * Created: Thu Dec 24 1998, 00:00:00
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: StateposInfo.java,v 1.1 1999-01-08 23:13:52 swtech14 Exp $
 */

package tesc2;


import absyn.*;

class StateposInfo {

  // Mit Positions-Informationen auszustattender State.
  State state;

  // Zaehler fuer x-Position.
  int counter_x;

  // Zaehler fuer y-Position.
  int counter_y;

  // Anzahl der Transitionen, die mit ´state´ und einem anderen State verbun-
  // den sind.
  int directTrs;

  // Anzahl der Transitionen, die mit ´state´ und einem Connector verbunden
  // sind.
  int indirectTrs;

  // y-Maximum (Notwendig zum Zeichnen der And-/Or-States, um die Hoehe indi-
  // viduell pro State zu bestimmen).
  int max_y;


  /**
   * StateposInfo-Konstruktor.
   *
   * Uebergabeobjekt:
   *   Referenz auf State-Objekt.
   */

  StateposInfo (State s) {
    state = s;
    counter_x = 0;
    counter_y = 0;
    max_y = 0;
    directTrs = 0;
    indirectTrs = 0;
  } // constructor StateposInfo


  /**
   * Gib StateposInfo-Objekt auf Bildschirm aus.
   */

  void print () {
    if (state != null)
      if (state.name != null)
	if (state.name.name != null)
	  System.out.println ("Statename: " + state.name.name);
    System.out.println ("Counter x: " + counter_x);
    System.out.println ("Counter y: " + counter_y);
    System.out.println ("Maximum y: " + max_y);
    System.out.println ("direct Transitions: " + directTrs);
    System.out.println ("indirect Transitions: " + indirectTrs);
  } // method print

} // class StateposInfo
