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

    public static final String[] LIST_OF_ALGORITHMS = {
	"Dummy-Algorithmus"
    };
    public static final int DUMMY_AR = 0;

    // Default-Konstruktor
    public GraphOptimizer() {
	sc = null;
	fm = null;
	algorithm = DUMMY_AR;
    }
    
    // erzeugt Optimierer fuer Statechart _sc mit FontMetrics _fm
    public GraphOptimizer(Statechart _sc,FontMetrics _fm) {
	sc = _sc;
	fm = _fm;
	algorithm = DUMMY_AR;
    }

    // optimiert das aktuelle Statechart mit Algorithmus _algorithm
    public Statechart optimize(int _algorithm) {
	algorithm = _algorithm;
	return optimize();
    };

    // optimiert aktuelles Statechart mit aktuellem Algorithmus
    public Statechart optimize() {
	// return sc.clone();
	return sc;
    }

    // plaziert beliebiges Statechart (auch ohne Koordinaten)
    Statechart place() {
	// return sc.clone();
	return sc;
    }

    public void setStatechart(Statechart _sc) {
	sc = _sc;
    }

    public void setFontMetrics(FontMetrics _fm) {
	fm = _fm;
    }

    public void setAlgorithm(int _algorithm) {
	algorithm = _algorithm;
    }

} // GraphOptimizer
