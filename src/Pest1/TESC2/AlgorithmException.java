/**
 * AlgorithmException
 *
 * Created: Fri Dec 04 1998, 00:00:00
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: AlgorithmException.java,v 1.6 1999-02-11 14:04:20 swtech14 Exp $
 *
 *
 * Exception-Klasse fuer die Fehler-/Exception-Behandlung bzgl. GraphOptimizer.
 */

package tesc2;


class AlgorithmException extends Exception {

  // In diesem String wird der uebergebene Fehlertext gespeichert.
  private String Errortext;

  /**
   * Konstuktor fuer Algorithmusfehler-Exception (default).
   */

  AlgorithmException () {
    super ();
  } // constructor AlgorithmException ()


  /**
   * Konstuktor fuer Algorithmusfehler-Exception (String).
   */

  AlgorithmException (String msg) {
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
