/**
 * GraphOptimizer.java
 *
 *
 * Created: Fri Nov 27 11:26:25 1998
 *
 * @author Software Technologie 19
 * @version
 */

package TESC2;

import java.awt.FontMetrics;
import Absyn.*;

public class GraphOptimizer {
    
    Statechart sc;
    FontMetrics fm;
    int algorithm;

    /**
     * Die Eintraege des Arrays sind Namen fuer die verwendbaren
     * Algorithmen. DIe Anzahl der Algorithmen erhaelt man ueber die
     * Laenge des Array.
     */

    public static final String[] LIST_OF_ALGORITHMS = {
	"Dummy-Algorithmus"
    };


    /**
     * Default-Konstruktor
     */
    public GraphOptimizer() {
	sc = null;
	fm = null;
	algorithm = 0;
    }
    
    /**
     * erzeugt Optimierer fuer Statechart _sc mit FontMetrics _fm
     */
    public GraphOptimizer(Statechart _sc,FontMetrics _fm) {
	sc = _sc;
	fm = _fm;
	algorithm = 0;
    }
	
    /**
     * optimiert das aktuelle Statechart mit Algorithmus _algorithm
     */
    public Statechart start(int _algorithm) throws AlgorithmException {
	algorithm = _algorithm;
	return start();
    };
	
    /**
     * optimiert aktuelles Statechart mit aktuellem Algorithmus
     */
    public Statechart start() throws AlgorithmException {
	// return sc.clone();
	return sc;
    }

    // plaziert beliebiges Statechart (auch ohne Koordinaten)
    Statechart place() {
	// return sc.clone();
	return sc;
    }

    /**
     * setzt das aktuelle Statechart
     */
    public void setStatechart(Statechart _sc) {
	sc = _sc;
    }

    /**
     * setzt die aktuelle FontMetrics
     */
    public void setFontMetrics(FontMetrics _fm) {
	fm = _fm;
    }

    /**
     * setzt den aktuellen Algorithmus
     */
    public void setAlgorithm(int _algorithm) {
	algorithm = _algorithm;
    }

} // GraphOptimizer


