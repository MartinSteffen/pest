/**
 * GraphOptimizer.java
 *
 * Created: Mon Dec 07 16:43:30 1998
 * 
 * @author swtech14 : Eike Schulz & Martin Poerksen
 * @version $Id: GraphOptimizer.java,v 1.6 1998-12-21 10:17:58 swtech14 Exp $
 */

package tesc2;

import absyn.*;
import java.awt.FontMetrics;

/**
 * Wir erwarten:
 * -------------
 *   Syntaktisch korrektes Statechart-Objekt
 *   (d.h. keine Interlevel-Transitionen; fuer alle Statechart-
 *    komponenten muss ein EINDEUTIGES Objekt vorhanden sein
 *    -> keine kopierten/geclonten Objekte!);
 *   Das Statechart-Objekt wird direkt dem Konstruktor oder der
 *   Methode 'setStatechart' uebergeben.
 *
 *   Wir erwarten keine Fehlermeldung aus dem Syntaxcheck!
 *
 *
 * Wir erzeugen:
 * -------------
 *   Syntaktisch korrektes Statechart-Objekt (sofern uebergeben)
 *   mit Koordinaten fuer alle Komponenten;
 *
 *   Fehler-Exception, falls Fehler auftreten (z.B. zu viele
 *   States vorhanden, Zeichenflaechenbegrenzung wird durch
 *   Koordinaten ueberschritten)
 *   Die Fehler-Exception wird von der Gruppe abgefangen, die
 *   unseren Algorithmus aufruft, daher sollte diese Gruppe
 *   evtl. aufgetretene Fehler entsprechend behandeln.
 */

public class GraphOptimizer {


  /**
   * Stringkonstantenfeld zur Bezeichnung der verschiedenen
   * Algorithmusarten.
   */

  public static final String[] LIST_OF_ALGORITHMS =
  { "LeftRightAlgorithm" };


  // 1.  Ergebniswert fuer Graphplazierungsalgorithmus :
  //     errorcode == 0 : Algorithmus korrekt gelaufen,
  //     errorcode != 0 : Fehler aufgetreten -> Fehlercode.
  // 2.  Referenzvariable fuer uebergebenes Statechart-Objekt.
  // 3.  Referenzvariable fuer 'sChart'-Kopie -> Objekt, das mit
  //     Koordinaten ausgestattet und zurueckgegeben wird.
  // 4.  Referenzvariable fuer uebergebenes FontMetrics-Objekt.
  // 5.  Speicherwert fuer Algorithmus-Art.


  private int         errorcode;
  private Statechart  sChart;
  private Statechart  cChart;
  private FontMetrics fMetrics;
  private int         algorithm;


  /**
   * Default-Konstruktor zur Erzeugung eines Algorithmusobjektes.
   */

  public GraphOptimizer () {
    sChart    = null;
    fMetrics  = null;
    algorithm = 0;
  } // constructor GraphOptimizer ()


  /**
   * Konstruktor zur Erzeugung eines Algorithmusobjektes.
   *
   * Uebergabeobjekte:
   *   Statechart-Objekt,
   *   FontMetrics-Objekt.
   */

  public GraphOptimizer (Statechart sc, FontMetrics fm) {
    sChart = sc;
    fMetrics = fm;
  } // constructor GraphOptimizer (Statechart sc, FontMetrics fm)



  /**
   * Start des Graphplazierungsalgorithmus.
   */

  public Statechart start () throws AlgorithmException {
    errorcode = 0;           // "Reset" des Fehlercodes
    //    cChart = (Statechart)sChart.clone(); // Kopiere 'sChart'-Objekt.
    cChart = sChart;

    // Pruefe, of 'algorithm' einen "sinnvollen" Wert besitzt.
    // Falls nicht, fuehre Default-Algorithmus aus.

    if ((algorithm != 0) && (algorithm != 1))
      algorithm = 0;

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
   * Start des Graphplazierungsalgorithmus.
   * Uebergabewert:
   *   int-Wert fuer Algorithmus-Art.
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
