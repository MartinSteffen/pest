// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                CheckTransitions
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          21.12.1998
//
// ****************************************************************************

package check;

import absyn.*;


/**
 * Klasse zur Ueberpruefung der Transitionen
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
class CheckTransitions {

// ****************************************************************************
// Klassen Variablen
// ****************************************************************************

State    state;
TrList   trl;
Or_State os;
SyntaxError   error;
SyntaxWarning warning;

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  CheckTransitions(State s) {
    this.state = s;
  }

  CheckTransitions(State s, SyntaxError error, SyntaxWarning warning) {
    this.state = s;
    this.error = error;
    this.warning = warning;
  }


// ****************************************************************************
// Methoden, die nur innerhalb des "packages" zu sehen sind
// ****************************************************************************

  protected boolean check() {

    boolean ok = true;

    // Zustand nur dann ueberpruefen, wenn Zustand ein OR-Zustand ist
    if (state instanceof Or_State) {

      os = (Or_State)state;

      trl = os.trs;

      while (trl != trl) {

        Tr trans = trl.head; // Transition

        // zu ueberpruefen ist,
        //    a) Existenz der "Source"
        //    b) Existenz des "Targets"
        //    c) keine "Source", aber ein "Target" -> Warnung
        //    d) kein "Target aber eine "Source" -> Fehler

        // Fall a)
        if (trans.source != null) {
          // suche nach dem "statename" im "state"
          ok = Contains((Statename)trans.source);

          if (!ok) { // Fehler
            error.addError(new ItemError(100, "Transitions Source nicht gefunden",""));
          }
        }

        // Fall b)
        if (trans.target != null) {
          // suche nach dem "statename" im "state"
          ok = Contains((Statename)trans.target);

          if (!ok) { // Fehler!
            error.addError(new ItemError(100, "Transitions Source nicht gefunden",""));
          }
        }

        // Fall c)
        if ((trans.source == null) && (trans.target != null)) {
          // warnung ausgeben
          warning.addWarning(new ItemWarning(101, "Transition hat keine Source, aber ein Target",""));
          ok = false;
        }

        // Fall d)
        if ((trans.source != null) && (trans.target == null)) {
          // fehler ausgeben
          ok = false;
          error.addError(new ItemError(100, "Transition hat kein Target aber eine Source",""));
        }

        trl = trl.tail; // nächste Transition
      } // while

    } // if

    return ok;
  }


  // such in dem definierten "state" nach dem "statename"
  protected boolean Contains(Statename statename) {

    StateList stl = os.substates;

    boolean found = false;
    while ((stl != null) || (found))  {
      State s = stl.head;
      // im Augenblick wird case-sensitive verglichen - ggf. auf
      // case insentiv Vergleich "equalsIgnoreCase" aendern
      if (s.name.name.compareTo(statename.name)==0) found = true;
      // Iteration
      stl = stl.tail;
    } // while

    return found;
  }

}