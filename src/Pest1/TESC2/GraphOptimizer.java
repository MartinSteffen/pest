/**
 * GraphOptimizer.java
 *
 * Created: Mon Dec 07 16:43:30 1998
 * 
 * @author swtech14 : Eike Schulz & Martin Poerksen
 * @version $Id: GraphOptimizer.java,v 1.13 1999-02-15 10:40:47 swtech14 Exp $
 *
 *
 * <br>Der GraphOptimizer stellt Algorithmen zur Verfuegung, die die Zuordnung
 * von Koordinaten fuer einen Statechart uebernehmen. Das String-Konstanten-
 * Array 'LIST_OF_ALGORITHMS' beinhaltet die Namen der implementierten Algo-
 * rithmen. Die Groesse dieses Arrays gibt also die Anzahl der verwendbaren Al-
 * gorithmen an. Mit Hilfe der Methode 'start' wird der Layoutprozess gestar-
 * tet, Koordinaten werden unabhaengig von evtl. vorher vorhandenen Koordinaten
 * bestimmt und in eine Kopie des Uebergabe-Statechart-Objektes entsprechend
 * abgelegt. Als Rueckgabewert dient die mit Koordinaten ausgestattete Kopie,
 * das Original-Statechart-Objekt bleibt unveraendert.
 *
 * <p>Erlaeuterungen zum eigentlichen Algorithmus siehe Datei
 * 'docu/SpreadAlgorithm.txt'
 *
 * <p>Vorbedingungen:
 * <br> Das dem Konstruktor uebergebene Statechart-Objekt muss folgende Eigen-
 * schaften besitzen:
 * <br> - keine Interlevel-Transitionen,
 * <br> - keine Schleifen im State-Baum,
 * <br> - bis auf die Instanzvariablen fuer Koordinaten (und Listen usw.) 
 *        duerfen keine null-Pointer vorkommen
 *
 * <p>Nachbedingungen:
 * <br> Es wird eine vollstaendig mit Koordinaten ausgestattete Kopie des
 * Uebergabe-Statechart-Objektes zurueckgegeben; das Uebergabe-Objekt bleibt
 * unveraendert.
 *
 * <p>
 * <dl compact>
 *
 * <dt><strong>
 * STATUS
 * </strong>
 * Der 'SpreadAlgorithm' ist weitestgehend fertig; die Anordnung der States
 * und der Transitionen funktioniert bei den vorgegebenen Testobjekten einwand-
 * frei. Nach Uebereinstimmung mit PEST1/editor werden Transitionenlabels nur
 * bis zu 20 Characters angezeigt. Laengere TLabels werden im Editor gespei-
 * chert und auf Wunsch eingeblendet.
 * Noch nicht getestet sind die "SE-SYSTEMS"-Beispiele, da diese noch Interle-
 * vel-Transitionen enthalten und somit nicht von stm berechnet werden (Aus-
 * wurf einer Exception).
 * <p> Im Verzeichnis tesc2/Test befinden sich die Programme 'Test1' und
 * 'Test2', durch die einige unserer Testobjekte mit dem PrettyPrinter, bzw.
 * in einem selbsterzeugten Frame (schematisch) angesehen werden koennen. Bei
 * den Testobjekten kommt es uns dabei nicht auf die syntaktische Korrektheit
 * an, sondern auf markante Eigenschaften bzgl. Schachtelungstiefe, Statename-
 * laengen, etc.
 *
 * <dt><strong>
 * TODO
 * </strong>
 * Testen, evtl. Layout-Verbesserung.
 *
 * <dt><strong>
 * BEKANNTE FEHLER
 * </strong>
 * ---
 *
 * <dt><strong>
 * TEMPORAERE FEATURES
 * </strong>
 * Zur Zeit keine vorhanden.
 * 
 * </dl compact>
 *
 */

package tesc2;


import absyn.*;
import java.awt.*;

public class GraphOptimizer {

  /**
   * Mindestlaenge fuer darzustellende Transitionenlabels.
   */
  public static final int TLABELLENGTH = 20;


  /**
   * Abstandskonstanten.
   */

  static final int STEPSIZE_SMALL  = 6;
  static final int STEPSIZE_MEDIUM = 8;
  static final int STEPSIZE_LARGE  = 12;

  static final int BASICSTATE_MINWIDTH  = 9;
  static final int BASICSTATE_MINHEIGHT = 6;


  /**
   * Width-Konstante fuer Connectoren (nach Absprache mit der Editor-Gruppe
   * auf "12" gesetzt).
   */

  public static final int CONNECTOR_SIZE = 12;


  /**
   * Stringkonstantenfeld zur Bezeichnung der verschiedenen Algorithmusarten.
   */

  public static final String[] LIST_OF_ALGORITHMS = { "SpreadAlgorithm" };


  // 1.  Ergebniswert fuer Graphplazierungsalgorithmus :
  //     errorcode == 0 : Algorithmus korrekt gelaufen,
  //     errorcode != 0 : Fehler aufgetreten -> Fehlercode.
  // 2.  Referenzvariable fuer uebergebenes Statechart-Objekt.
  // 3.  Referenzvariable fuer uebergebenes FontMetrics-Objekt.
  // 4.  Speicherwert fuer Algorithmus-Art.

  private int         errorcode;
  private Statechart  sChart, sChartCopy;
  private FontMetrics fMetrics;
  private int         algorithm = 0;


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
   * Default-Konstruktor zur Erzeugung eines Algorithmusobjektes.
   */

  public GraphOptimizer () { this (null, null); }



  /**
   * Start des Graphplazierungsalgorithmus.
   * @exception AlgorithmException Test, ob Parameteruebergabe richtig
   */
  public Statechart start () throws AlgorithmException {
    errorcode = 0;           // "Reset" des Fehlercodes

    // Teste, ob Parameter vollstaendig uebergeben wurden.

    if (sChart == null)
      errorcode = 2;
    else if (fMetrics == null)
      errorcode = 3;
    else if ((sChart == null) && (fMetrics == null))
      errorcode = 4;
    else {

      // Erzeuge Kopie von 'sChart', rufe Algorithmus auf.

      try {
	sChartCopy = (Statechart)sChart.clone();

	// Verzweige zu ´algorithm´ entsprechendem Algorithmus.

	switch (algorithm)
	  {
	  default : // Aufruf des Default-Algorithmus.

	  case (0) : // Algorithmus_1.
	    SpreadAlgorithm sa =
	      new SpreadAlgorithm (sChartCopy, fMetrics, true);
	    sa.calculate_coordinates();
	    break;

	  case (1) : // Algorithmus_2.

	    ShuffleAlgorithm sha =
	      new ShuffleAlgorithm (sChartCopy, fMetrics);
	    sha.calculate_coordinates();

	    break;
	  }
      } catch (CloneNotSupportedException e) {
	errorcode = 1;
      }
    } // else

    // Falls ein Fehler aufgetreten ist, wirf Exception, sonst gib Referenz
    // auf ´sChartCopy´ zurueck.

    if (errorOccured())
      throwException();

    return (sChartCopy);
  } // method start ()



  /**
   * Start des Graphplazierungsalgorithmus.
   * @exception AlgorithmException Test, ob Parameteruebergabe richtig
   * Uebergabewert:
   *   int-Wert fuer Algorithmus-Art.
   */

  public Statechart start (int alg) throws AlgorithmException {
    setAlgorithm (alg);
    start();
    return (sChartCopy);
  } // method start (int alg)



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
  //   true, falls Fehler aufgetreten ist
  //   false, falls kein Fehler aufgetreten ist

  private boolean errorOccured () {
    return (errorcode != 0);
  } // method errorOccured



  // Erzeuge ´AlgorithmException´:
  //
  // Rueckgabewert:
  //   Exception-Objekt

  private void throwException () throws AlgorithmException {
    switch (errorcode)
      {
      case 1:
	throw
	  new AlgorithmException
	  ("FEHLER: CloneNotSupportedException bei der Optimierung aufgetret" +
	   "en!        ");
      case 2:
	throw
	  new AlgorithmException
	  ("FEHLER: Kein Absyn.Statechart-Objekt zur Optimierung vorhanden. " +
	   "           ");
      case 3:
	throw
	  new AlgorithmException
	  ("FEHLER: Kein java.awt.FontMetrics-Objekt vorhanden.             " +
	   "           ");
      case 4:
	throw
	  new AlgorithmException
	  ("FEHLER: Keine Absyn.Statechart- und java.awt.FontMetrics-Objekte" +
	   " vorhanden.");

      // (...)

      default:
	throw
	  new AlgorithmException
	  ("FEHLER: Unbekannter Fehler aufgetreten.                         " +
	   "           ");
      } // switch

  } // method throwException


} // class GraphOptimizer
