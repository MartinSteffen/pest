/**
 * AlgorithmException.java
 *
 *
 * Created: Tue Dec  8 11:20:46 1998
 *
 * @author Software Technologie 19
 * @version
 */

package TESC2;

public class AlgorithmException extends Exception {
    
    String errMsg;

    /**
     * Default-Konstruktor ohne Text
     */
    public AlgorithmException() {
	errMsg = null;
    }

    /**
     * Konstruktor mit Fehlermeldung
     */
    public AlgorithmException(String msg) {
	errMsg = new String(msg);
    }

    /**
     * Gibt den Fehlertext zurueck (ueberschreibt die Methode
     * von Throwable)
     */
    public String getMessage() {
	return errMsg;
    }
    
} // AlgorithmException
