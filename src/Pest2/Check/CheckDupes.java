//   Projekt:               PEST2
//   Klasse:                CheckDupes
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          16.01.1999
//
// ****************************************************************************

package check;

import absyn.*;            // abstrakte Syntax
import java.util.*;        // für Vector-Klasse benötigt

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

  public boolean check() {
    return check(statechart.cnames, "");
    }

  private boolean check(PathList p, String path) {
    if (p == null) {
      return true; }
      //return false; } /* geaendert (18.01.1999) */
    else {
      PathList i = p.tail;
      while (i != null) {
        if (p.head.head == i.head.head) {
          error.addError(new ItemError(100,"Doppelter Bezeichner "+i.head, path));
          // geaendert (18.01.1999)
          return false
          /*return true*/; } // Falls ein Dupe gefunden wird, true liefern
                             // ### sollte er dann nicht FALSE liefern?
        else {
          i = i.tail;
          }
        }
      }

    return check(p.tail, p.head.head+"."+path);
    }

  }