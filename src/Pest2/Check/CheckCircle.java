//   Projekt:               PEST2
//   Klasse:                CheckCircle
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          19.12.1998
//
// ****************************************************************************

package check;

import absyn.*;            // abstrakte Syntax
import java.util.*;        // für Vector-Klasse ben”tigt

/**
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
class CheckCircle {
  Statechart statechart;

  // Konstruktor
  CheckCircle(Statechart st) {
    statechart = st;
    }

  boolean check() {
    return check(statechart.state, new Vector());
    }

  // In pathlist wird immer der bis hierhin aktuelle Pfad als Vektor
  // übergeben. Falls der aktuelle State dort enthalten ist, so liegt
  // ein Kreis im Graphen vor.
  boolean check(State state, Vector pathlist) {
    boolean ok = true;
    And_State as;
    Or_State os;
    StateList substates = null;

    if (pathlist.contains(state))
     { return false; }
    else
     { if (state instanceof And_State) {
         as = (And_State)state;
         substates = as.substates;
         }
       if (state instanceof Or_State) {
         os = (Or_State)state;
         substates = os.substates;
         }

       // aktuellen State zur Liste hinzufügen
       Vector next = new Vector();
       next = pathlist;
       next.addElement(state);

       // diese Methode an allen Substates aufrufen
       while (substates != null) {
         ok = ok && check(substates.head, next);
         substates = substates.tail;
         }

       return ok;
       }
    }
  }
