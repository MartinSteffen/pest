/**
 * GraphAlg.java
 *
 * Created: Sun Dec 06 17:16:58 1998
 * 
 * @author Eike Schulz & Martin Poerksen
 * @version $Id: GraphAlg.java,v 1.5 1998-12-06 16:50:00 swtech14 Exp $
*/

package TESC2;
import Absyn.*;


public class GraphAlg {


/**
 * Konstanten fuer verschiedene Algorithmusarten 
 *
 * Vorerst werden zwei Algorithmen implementiert :
 * Der Algorithmus Typ 0  ( dient als Default-Algorithmus )
 * und Algorithmus Typ 1  ( dient als Alternativ-Algorithmus )
 *
 * Ergebniswert fuer Graphplazierungsalgorithmus :
 * 0         : Algorithmus korrekt gelaufen
 * Zahl <> 0 : Fehlercode
 */

  public static final int algorithm_0 = 0;
  public static final int algorithm_1 = 1;

  private int errorcode;
  private Statechart sChart;


/**
 * Konstruktor fuer Erzeugung eines Algorithmusobjektes 
 *
 * Uebergabeobjekt:
 * - Statechart-Objekt
 */
  public GraphAlg (Statechart s) {
    sChart = s;
    errorcode = 0;
  } // constructor GraphAlg


/**
 * Start des Graphplazierungsalgorithmus 
 *
 * Uebergabewert:
 * - Integer-Wert fuer Algorithmus-Art (siehe Konstanten oben!)
 */
  public void StartAlg (int algorithm) {
    if ((algorithm != algorithm_0) && (algorithm != algorithm_1))
      algorithm = algorithm_0;

    if (algorithm == algorithm_0) {
       // Aufruf des Default-Algorithmus
    }
     
    if (algorithm == algorithm_1) {
       // Aufruf des Alternativ-Algorithmus 
    }

  } // method start


/**
 * Fehlerueberpruefung 
 *
 * Rueckgabewert:
 * - true, falls Fehler aufgetreten ist
 * - false, falls kein Fehler aufgetreten ist
 */
  public boolean errorOccured () {
    return (errorcode != 0);
  } // method errorOccured


/**
 * Rueckgabe der Fehlercode-Interpretation 
 *
 * Rueckgabewert:
 * - String mit verbalisierter Fehlermeldung
 */
  public String getErrorMsg () {
    switch (errorcode) {
    case 0 : return "Graphplazierungsalgorithmus erfolgreich " +
                    "ausgefuehrt.";
    case 1 : return "FEHLER: Algorithmus-Art nicht verfuegbar.";

      // (...)

    default : return "FEHLER: Unbekannter Fehler aufgetreten";
    } // switch

  } // method getErrorMsg

} // class GraphAlg
