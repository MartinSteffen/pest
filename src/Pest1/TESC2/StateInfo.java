/**
 * StateInfo
 *
 * Created: Thu Dec 24 1998, 00:00:00
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: StateInfo.java,v 1.1 1999-01-08 23:12:34 swtech14 Exp $
 */

package tesc2;


import absyn.*;

class StateInfo {
  State surrounding_state;
  boolean marked;
  int depth;
  StateList substates;

  /**
   * StateInfo-Konstruktor.
   *
   * Uebergabeobjekte/-werte:
   *   Referenz auf State-Objekt (NUR And-/Or-States!),
   *   Schachtelungstiefe des State-Objekts,
   *   Substates des State-Objekts
   */

  StateInfo (State s, int d, StateList sL) {
    surrounding_state = s;
    marked = false;
    depth = d;
    substates = sL;
  } // constructor StateInfo


  /**
   * Gib StateInfo-Objekt auf Bildschirm aus.
   */

  void print () {
    if (surrounding_state != null)
      if (surrounding_state.name != null)
        if (surrounding_state.name.name != null)
	  System.out.println ("Statename: " + surrounding_state.name.name);
    String m = (marked==true) ? "yes" : "no";
    System.out.println ("Marked: " + m);
    System.out.println ("Depth: " + depth);
    System.out.println ("Substates: ");
    StateList sL = substates;
    while (sL != null) {
      if (sL.head != null)
	if (sL.head.name != null)
	  if (sL.head.name.name != null)
	    System.out.println ("  " + sL.head.name.name);
      sL = sL.tail;
    }
  } // method print

} // class StateInfo
