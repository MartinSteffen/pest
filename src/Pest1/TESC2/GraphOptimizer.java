/**
 * GraphAlg.java
 *
 * Created: Mon Dec 07 16:43:30 1998
 * 
 * @author swtech14 : Eike Schulz & Martin Poerksen
 * @version $Id: GraphOptimizer.java,v 1.2 1998-12-10 17:10:14 swtech14 Exp $
 */

package TESC2;


import Absyn.*;
import java.awt.FontMetrics;

public class GraphOptimizer {


  /**
   * Stringkonstantenfeld fuer verschiedene Algorithmusarten 
   */

  public static final String[] LIST_OF_ALGORITHMS =
  { "Algorithmus 1" };


  // Ergebniswert fuer Graphplazierungsalgorithmus :
  // 0         : Algorithmus korrekt gelaufen
  // Zahl <> 0 : Fehlercode

  private int errorcode;


  // Referenzvariable fuer uebergebenes Statechart-Objekt.
  private Statechart sChart;

  // Referenzvariable fuer 'sChart'-Kopie -> Objekt, das mit
  // Koordinaten ausgestattet und zurueckgegeben wird.
  private Statechart cChart;

  // Referenzvariable fuer uebergebenes FontMetrics-Objekt.
  private FontMetrics fMetrics;

  // Speicherwert fuer Algorithmus-Art.
  private int algorithm;


  /**
   * Konstruktor zur Erzeugung eines Algorithmusobjektes ()
   */

  public GraphOptimizer () {
    sChart = null;
    fMetrics = null;
    algorithm = 0;
  } // constructor GraphOptimizer ()


  /**
   * Konstruktor zur Erzeugung eines Algorithmusobjektes (mit Parametern).
   *
   * Uebergabeobjekte:
   * - Statechart-Objekt,
   * - FontMetrics-Objekt.
   */

  public GraphOptimizer (Statechart sc, FontMetrics fm) {
    sChart = sc;
    fMetrics = fm;
  } // constructor GraphOptimizer (Statechart sc, FontMetrics fm)



  /**
   * Start des Graphplazierungsalgorithmus ().
   */

  public Statechart start () throws AlgorithmException {
    errorcode = 0;           // "Reset" des Fehlercodes
    //    cChart = (Statechart)sChart.clone(); // Kopiere 'sChart'-Objekt.
    cChart = sChart;

    // Pruefe, of 'algorithm' einen "sinnvollen" Wert besitzt.
    // Falls nicht, fuehre Default-Algorithmus aus.

    if ((algorithm != 0) && (algorithm != 1))
      algorithm = 0;

    // (Aufruf des entsprechenden Algorithmus erfolgt hier...)

    switch (algorithm) {
      case (0) : // Aufruf des Default-Algorithmus.
	break;
      case (1) : // Aufruf des Alternativ-Algorithmus.
	break;
    }

    // Falls Fehler aufgetreten ist, wirf Exception, sonst gib
    // Referenz auf 'cChart' zurueck.

    if (errorOccured())
      throwException();

    return (cChart);
  } // method start ()



  /**
   * Start des Graphplazierungsalgorithmus (int-Parameter).
   * Uebergabewert:
   * - int-Wert fuer Algorithmus-Art
   */

  public Statechart start (int alg) throws AlgorithmException {
    algorithm = alg;
    return start();
  } // method start (int)



  /**
   * Setze Statechart-Objekt.
   */

  public void setStatechart (Statechart sc) {
    sChart = sc;
  } // method setStatechart



  /**
   * Setze Algorithmus-Art.
   */

  public void setAlgorithm (int alg) {
    algorithm = alg;
  } // method setAlgorithm



  /**
   * Setze FontMetrics.
   */

  public void setFontMetrics (FontMetrics fm) {
    fMetrics = fm;
  } // method setFontMetrics



  // Fehlerueberpruefung 
  //
  // Rueckgabewert:
  // - true, falls Fehler aufgetreten ist
  // - false, falls kein Fehler aufgetreten ist

  private boolean errorOccured () {
    return (errorcode != 0);
  } // method errorOccured



  // Erzeuge ´AlgorithmException´:
  //
  // Rueckgabewert:
  // - Exception-Objekt

  private void throwException () throws AlgorithmException {
    switch (errorcode)
      {
      case 1 :
	throw
	  new AlgorithmException
	  ("Algorithmus aufgrund von Speicherplatzmangel abgebrochen.   ");
      case 2 :
	throw
	  new AlgorithmException
	  ("Kein Statechart-Objekt zur Optimierung vorhanden.           ");

      // (...)

      default:
	throw
	  new AlgorithmException
	  ("Unbekannter Fehler aufgetreten.                             ");
      } // switch
  } // method throwException

} // class GraphOptimizer
