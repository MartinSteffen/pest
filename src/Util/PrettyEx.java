// Example for using PrettyPrint.
// Written by Eike Schulz, 03-12-1998.

package Util;


import Absyn.Example;
import Util.PrettyPrint;

public class PrettyEx {

  /**
   * main-Methode -> Ausgabe Statechart-Example.
   */
  public static void main (String[] args) {

    // Erzeuge PP-Objekt.
    PrettyPrint pp = new PrettyPrint ();
    pp.start (Example.getExample());

  } // method main

} // class PrettyEx
