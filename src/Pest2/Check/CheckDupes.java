//   Projekt:               PEST2
//   Klasse:                CheckDupes
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          31.01.1999
//
// ****************************************************************************

package check;

import absyn.*;            // abstrakte Syntax
import java.util.*;        // fÅr Vector-Klasse benˆtigt

/**
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
class CheckDupes {
  Statechart statechart;
  SyntaxWarning warning;
  SyntaxError error;

  // Konstruktor
  CheckDupes(Statechart st, SyntaxError error, SyntaxWarning warning) {
    statechart = st;
    this.warning = warning;
    this.error = error;
    }

  CheckDupes() {
    warning = null;
    error = null;
    }

  // public boolean check() {
  // return check(statechart.cnames, "");
  //  }

  boolean check(StateList _sl, String path) {
    if (_sl == null) {
      return true; }
    else {
      StateList sl = _sl.tail;
      State s = _sl.head;
      boolean ok = true;

      while (sl != null && ok) {
        if (sl.head.name.name.compareTo(s.name.name)==0) {
          ok = false;
          error.addError(new ItemError(100,"Doppelter Bezeichner gefunden:"+s.name.name, path));
          }
        sl = sl.tail;
        }
      return ok && check(_sl.tail,path);
      }
    }

  }