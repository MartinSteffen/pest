/**
 * GraphAlg.java
 *
 * Created: Mon Dec 07 16:43:30 1998
 * 
 * @author swtech14 : Eike Schulz & Martin Poerksen
 * @version $Id: GraphOptimizer.java,v 1.1 1998-12-07 16:44:37 swtech14 Exp $
 */

package TESC2;


import Absyn.*;
import java.awt.FontMetrics;

public class GraphOptimizer {


  /**
   * Stringkonstantenfeld fuer verschiedene Algorithmusarten 
   */

  public static final String[] LIST_OF_ALGORITHMS =
  { "Algorithmus Eins", "Algorithmus Zwei" };


  // Ergebniswert fuer Graphplazierungsalgorithmus :
  // 0         : Algorithmus korrekt gelaufen
  // Zahl <> 0 : Fehlercode

  private int errorcode;


  // Referenzvariable fuer uebergebenes Statechart-Objekt
  private Statechart sChart;

  // Referenzvariable fuer uebergebenes FontMetrics-Objekt
  private FontMetrics fMetrics;

  // Speicherwert fuer Algorithmus-Art
  private int algorithm;


  /**
   * Konstruktor fuer Erzeugung eines Algorithmusobjektes (mit Parametern).
   *
   * Uebergabeobjekte:
   * - Statechart-Objekt
   * - FontMetrics-Objekt
   */

  public GraphOptimizer (Statechart sc, FontMetrics fm) {
    sChart = sc;
    fMetrics = fm;
  } // constructor GraphOptimizer (Statechart sc, FontMetrics fm)


  /**
   * Konstruktor fuer Erzeugung eines Algorithmusobjektes ()
   */

  public GraphOptimizer () {
    sChart = null;
    fMetrics = null;
    algorithm = 0;
  } // constructor GraphOptimizer ()



  /**
   * Start des Graphplazierungsalgorithmus ().
   */

  public void start () throws AlgorithmException {
    errorcode = 0;
    if ((algorithm != 0) && (algorithm != 1))
      algorithm = 0;

    // (Aufruf des entsprechenden Algorithmus erfolgt hier...)

    if (algorithm == 0) {
       // Aufruf des Default-Algorithmus
    }
     
    if (algorithm == 1) {
       // Aufruf des Alternativ-Algorithmus 
    }

    if (errorOccured())
      throwException();

  } // method start ()



  /**
   * Start des Graphplazierungsalgorithmus (int-Parameter).
   * Uebergabewert:
   * - int-Wert fuer Algorithmus-Art
   */

  public void start (int alg) throws AlgorithmException {
    algorithm = alg;
    start();
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
	  ("Algorithmus-Art nicht verfuegbar.                           ");

      // (...)

      default:
	throw
	  new AlgorithmException
	  ("Unbekannter Fehler aufgetreten.                             ");
      } // switch
  } // method throwException

} // class GraphOptimizer
