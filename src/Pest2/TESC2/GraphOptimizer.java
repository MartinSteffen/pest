package TESC2;

import java.awt.FontMetrics;
import Absyn.*;

/**
 *
 * <br>Der GraphOptimizer stellt Algorithmen zur Verfuegung, die das Layout
 * eines Statecharts &uuml;bernehmen. Die Variable LIST_OF_ALGORITHMS 
 * beinhaltet die Namen der implementierten Algorithmen. &Uuml;ber die
 * Gr&ouml;&szlig;e dieses Arrays ermittelt man die Anzahl der verwendbaren
 * Algorithmen. &Uuml;ber die Methode start wird der Layoutproze&szlig;
 * gestartet. Dabei wird eine Kopie des &uuml;bergebenen Statecharts
 * erzeugt.
 *
 * <p>Erkl&auml;rung der Algorithmen: 
 *  <br>Das Layout erfolgt rekursiv &uuml;ber den Aufbau der abstrakten Syntax.
 *  Die Gr&ouml;&szlig;e von Basic-States wird entsprechend ihres Namens 
 *  durchgef&uuml;hrt. Bei AND-States wird zuerst das Layout der
 *  Unterzust&auml;nde bestimmt und diese dann so angeordnet, da&szlig;
 *  der Raum m&ouml;glichst optimal ausgenutzt wird. Bei OR-States wird
 *  das Layout der Unterzust&auml;nde bestimmt und diese dann nach
 *  einem bestimmten Verfahren im Zustand angeordnet. Diese Verfahren
 *  unterscheiden sich f&uuml;r die verschiedenen Algorithmen, der Rest ist
 *  gleich. 
 *  
 *  <p>SugiyamaBCM: Die Knoten (Unterzust&auml;nde) werden so in Ebenen
 *   angeordnet, da&szlig; in einer Ebene keine Transitionen verlaufen.
 *   Transitionen verlaufen nur von einer Ebene zur n&auml;chsten, wobei
 *   f&uuml;r Transitionen, die eigentlich &uuml;ber mehrere Ebenen verlaufen,
 *   Dummy-Knoten in den Zwischenebenen eingef&uuml;gt werden. Dann werden
 *   die Knoten in den Ebenen so permutiert, da&szlig; m&ouml;glichst wenige
 *   Kanten&uuml;berschneidungen vorkommen (heuristisch). Ausgehend von
 *   dieser Darstellung, die die Gr&ouml;&szlig;e der Unterzust&auml;nde nicht
 *   ber&uuml;cksichtigt, werden die Unterzust&auml;nde dann endg&uuml;ltig 
 *   plaziert.
 *  <p>Weitere Algorithmen (einer) in Planung.<p>
 *
 * Created: Fri Nov 27 11:26:25 1998
 *
 * @author Software Technologie 19, Klaus H&ouml;ppner, Achim Abeling
 * @version $Id: GraphOptimizer.java,v 1.4 1998-12-14 14:36:10 swtech19 Exp $
 *
 */

public class GraphOptimizer {
    
    Statechart sc;
    FontMetrics fm;
    int algorithm;

    /**
     * Die Eintr&auml;ge des Arrays sind Namen f&uuml;r die verwendbaren
     * Algorithmen. Die Anzahl der Algorithmen erh&auml;lt man &uuml;ber die
     * L&auml;nge des Array.
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
     * Erzeugt Optimierer f&uuml;r Statechart _sc mit FontMetrics _fm
     */
    public GraphOptimizer(Statechart _sc,FontMetrics _fm) {
	sc = _sc;
	fm = _fm;
	algorithm = 0;
    }
	
    /**
     * Optimiert das aktuelle Statechart mit Algorithmus _algorithm
     * @exception AlgorithmException Falls ein Fehler auftreten sollte
     * (was wissen wir auch noch nicht)
     */
    public Statechart start(int _algorithm) throws AlgorithmException {
	algorithm = _algorithm;
	return start();
    };
	
    /**
     * Optimiert aktuelles Statechart mit aktuellem Algorithmus
     * @exception AlgorithmException Falls ein Fehler auftreten sollte
     * (was wissen wir auch noch nicht)
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


