//   Projekt:               PEST2
//   Klasse:                CheckConnectors
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          11.01.1999
//
// ****************************************************************************

package check;

import absyn.*;            // abstrakte Syntax
import java.util.*;        // f�r Vector-Klasse ben�tigt

/**
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
class CheckConnectors {
  Statechart statechart;
  SyntaxWarning warning;
  SyntaxError error;

  // Konstruktor
  CheckConnectors(Statechart st, SyntaxError error, SyntaxWarning warning) {
    statechart = st;
    this.warning = warning;
    this.error = error;
    }

  public boolean check() {
    return check(statechart.state);
    }

  boolean check(State state) {
    StateList substates = null;
    Or_State os;
    boolean ok = true;

    if (state instanceof Or_State) {
      checkConnectors((Or_State)state, new Conname(""), new Vector());
      }

    // diese Methode an allen Substates aufrufen
    while (substates != null) {
      ok = ok && check(substates.head);
      substates = substates.tail;
      }

  return ok;

    }

  // sucht in einem State alle Connectoren zusammen und sucht �ber
  // Tiefensuche Kreise
  boolean checkConnectors(Or_State state, Conname con, Vector pathlist) {
    boolean ok = true;
    boolean found = false;
    int i = 0;
    String s = "";
    TrList tl = state.trs;
    Conname source;

    if (con.name != "") {
      // da die Connectoren nur �ber ihren Namen identifiziert werden,
      // kann nicht mit contains gepr�ft werden
      while (i<pathlist.size() && !found) {
        s = (String)pathlist.elementAt(i);
        if (con.name==s) { found = true; }
        i = i+1;
        }

      if (found == true) {
        error.addError(new ItemError(300,s+" im Kreis", ""));
        //System.out.println(s+" im Kreis");
        return false;
      }
    }

    // aktuellen Connector zur Liste hinzuf�gen
    Vector next = new Vector();
    next = pathlist;
    next.addElement(con);

    // diese Methode an allen Nachfolgern von con aufrufen
    // dazu erstmal die Nachfolger finden
    while (tl != null) {
      if (tl.head.source instanceof Conname) {
        source = (Conname)tl.head.source;

        if (source.name==con.name &&
            tl.head.target instanceof Conname) { // Connector con in tl gefunden

          ok = ok && checkConnectors(state,(Conname)tl.head.target,next);
          }

        }

      tl = tl.tail;
      }

    return false;
    }

  }
