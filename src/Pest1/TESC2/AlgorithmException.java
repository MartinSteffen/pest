// Written by Eike Schulz, 04-12-1998.

package tesc2;


public class AlgorithmException extends Exception {

  // In diesem String wird der uebergebene Fehlertext gespeichert.
  private String Errortext;

  /**
   * Konstuktor fuer Algorithmusfehler-Exception (default).
   */

  public AlgorithmException () {
    super ();
  } // constructor AlgorithmException ()


  /**
   * Konstuktor fuer Algorithmusfehler-Exception (String).
   */

  public AlgorithmException (String msg) {
    super (msg);
    Errortext = msg;
  } // constructor AlgorithmException (String)


  /**
   * Gib Fehlermeldung zurueck.
   */

  public String getMessage() {
    return Errortext;
  } // method getMessage

} // class AlgorithmException
