package tesc2;

import java.awt.FontMetrics;
import absyn.*;

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
 * <p>Vorbedingungen:
 * <br> Das beim Konstruktor &uuml;bergebene Statechart mu&szlig; folgende
 * Korrektheitsbedingungen erf&uuml;llen:
 * <br> -keine Interlevel-Transitionen
 * <br> -keine Schleifen im State-Baum
 * <br> -bis auf die Instanzvariablen f&uuml;r Koordinaten (und Listen usw.) 
 * d&uuml;rfen keine null-Pointer vorkommen
 *
 * <p>Nachbedingungen:
 * Es wird ein Clone des Statecharts mit Koordinaten zur&uuml;ckgegeben.
 * 
 *
 * <p>
 * <dl compact>
 *
 * <dt><strong>
 * STATUS
 * </strong>
 * <p>Wir haben die Beta-Version erreicht.
 * 
 * <br>
 * Im Verzeichnis
 * tesc2/Test findet sich ein Programm TestFrame, das in diesem
 * Verzeichnis mit 'make frame' gestartet werden kann. Es wird dann
 * ein Zufallsstatechart erzeugt und 'behelfsm&auml;&szlig;ig 
 * angezeigt.
 *
 * <dt><strong>
 * TODO 
 * </strong>
 * Verbesserung der optischen Erscheinung, weiterer Algorithmus.
 * Aktivierung der Optimierung.
 *
 * <dt><strong>
 * BEKANNTE FEHLER
 * </strong>
 * Das Erscheinungsbild ist noch nicht ,,optimal''
 *
 * <dt><strong>
 * TEMPOR&Auml;RE FEATURES
 * </strong>
 * 
 * </dl compact>
 *
 * Created: Fri Nov 27 11:26:25 1998
 *
 * @author Software Technologie 19, Klaus H&ouml;ppner, Achim Abeling
 * @version $Id: GraphOptimizer.java,v 1.9 1999-01-12 10:26:05 swtech19 Exp $
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

       
	Statechart scclone = null;
	LayoutAlgorithm layoutAR;

	try {
	    scclone = (Statechart) sc.clone();

	    switch (algorithm) {
	    default: 
		layoutAR = new SugiyamaBCMAlgorithm(this);
	    }
	    
	    layoutAR.layoutStatechart(scclone);

	} catch (CloneNotSupportedException e) {

	}
	
	
	return scclone;
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


