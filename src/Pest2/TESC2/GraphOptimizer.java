/**
 * GraphOptimizer.java
 *
 *
 * Created: Fri Nov 27 11:26:25 1998
 *
 * @author Software Technologie 19
 * @version
 *
 * Der GraphOptimizer stellt Algorithmen zur Verfuegung, die das Layout
 * eines Statecharts uebernehmen. Die Variable LIST_OF_ALGORITHMS 
 * beinhaltet die Namen der implementierten Algorithmen. Ueber die
 * Groesse dieses Arrays ermittelt man die Anzahl der verwendbaren
 * Algorithmen. Ueber die Methode start wird der Layoutprozess
 * gestartet. Dabei wird eine Kopie des uebergebenen Statecharts
 * erzeugt.
 *
 * Erklaerung der Algorithmen: 
 *  Das Layout erfolgt rekursiv ueber den Aufbau der abstrakten Syntax.
 *  Die Groesse von Basic-States wird entsprechend ihres Namens 
 *  durchgefuehrt. Bei AND-States wird zuerst das Layout der
 *  Unterzustaende bestimmt und diese dann so angeordnet, dass
 *  der Raum moeglichst optimal ausgenutzt wird. Bei OR-States wird
 *  das Layout der Unterzustaende bestimmt und diese dann nach
 *  einem bestimmten Verfahren im Zustand angeordnet. Diese Verfahren
 *  unterscheiden sich fuer die verschiedenen Algorithmen, der Rest ist
 *  gleich. 
 *  
 *  SugiyamaBCM: Die Knoten (Unterzustaende) werden so in Ebenen
 *   angeordnet, dass in einer Ebene keine Transitionen verlaufen.
 *   Transitionen verlaufen nur von einer Ebene zur naechsten, wobei
 *   fuer Transitionen, die eigentlich ueber mehrere Ebenen verlaufen,
 *   Dummy-Knoten in den Zwischenebenen eingefuegt werden. Dann werden
 *   die Knoten in den Ebenen so permutiert, dass moeglichst wenige
 *   Kantenueberschneidungen vorkommen (heuristisch). Ausgehend von
 *   dieser Darstellung, die die Groesse der Unterzustaende nicht
 *   beruecksichtigt, werden die Unterzustaende dann endgueltig plaziert.
 *   
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
     * Algorithmen. Die Anzahl der Algorithmen erhaelt man ueber die
     * Laenge des Array.
     */

    public static final String[] LIST_OF_ALGORITHMS = {
	"SugiyamaBCM-Algorithmus"
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
     * Erzeugt Optimierer fuer Statechart _sc mit FontMetrics _fm
     */
    public GraphOptimizer(Statechart _sc,FontMetrics _fm) {
	sc = _sc;
	fm = _fm;
	algorithm = 0;
    }
	
    /**
     * Optimiert das aktuelle Statechart mit Algorithmus _algorithm
     */
    public Statechart start(int _algorithm) throws AlgorithmException {
	algorithm = _algorithm;
	return start();
    };
	
    /**
     * Optimiert aktuelles Statechart mit aktuellem Algorithmus
     */
    public Statechart start() throws AlgorithmException {
	// return sc.clone();
	return sc;
    }

    // Plaziert beliebiges Statechart (auch ohne Koordinaten)
    Statechart place() {
	// return sc.clone();
	return sc;
    }

    /**
     * Setzt das aktuelle Statechart
     */
    public void setStatechart(Statechart _sc) {
	sc = _sc;
    }

    /**
     * Setzt die aktuelle FontMetrics
     */
    public void setFontMetrics(FontMetrics _fm) {
	fm = _fm;
    }

    /**
     * Setzt den aktuellen Algorithmus
     */
    public void setAlgorithm(int _algorithm) {
	algorithm = _algorithm;
    }

} // GraphOptimizer


