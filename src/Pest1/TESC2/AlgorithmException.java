// Written by Eike Schulz, 04-12-1998.

package TESC2;


public class AlgorithmException extends Exception {

  // In diesem String wird der uebergebene Fehlertext gespeichert.
  private String Errortext;


  /**
   * Konstuktor fuer Algorithmusfehler-Exception.
   */

  public AlgorithmException (String msg) {
    super (msg);
    Errortext = msg;
  } // constructor AlgorithmException

} // class AlgorithmException
