package tesc2;

/**
 * Diese Exception wurde von uns am Anfang der Entwicklung in unser
 * Interface aufgenommen. Sp&auml;ter stellte sich heraus, da&szlig;
 * wir diese Exception nicht ben&ouml;tigen. 
 * Um die Konsistenz zur anderen tesc2-Gruppe aber zu behalten,
 * lie&szlig;en wir die Exception drin.
 *
 *
 * Created: Tue Dec  8 11:20:46 1998
 *
 * @author Software Technologie 19, Hoeppner, Abeling
 * @version $Id: AlgorithmException.java,v 1.4 1999-02-11 14:55:54 swtech19 Exp $
 */


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
