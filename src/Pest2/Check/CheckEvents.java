//   Projekt:               PEST2
//   Klasse:                CheckEvents
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          11.01.1999
//
// ****************************************************************************

package check;

import absyn.*;         // abstrakte Syntax

/**
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
class CheckEvents {
  Statechart statechart;
  State s;
  String path;
  SyntaxWarning warning;
  SyntaxError error;

  CheckEvents(Statechart _st, SyntaxError error, SyntaxWarning warning) {
    statechart = _st;
    s = statechart.state;
    path = s.name.name;
    this.error = error;
    this.warning = warning;
    }

  CheckEvents(Statechart _st, State _s, String _path, SyntaxError error, SyntaxWarning warning) {
    statechart = _st;
    s = _s;
    path = _path;
    this.error = error;
    this.warning = warning;
    }

  // überprüft, ob alle Events im State s auch in der Statechart
  // definiert sind. Zurückgegeben wird TRUE, wenn dies der Fall ist.
	public boolean check() {
    SEventList sel = statechart.events;
    SEventList se;
    StateList substates = null;
    Or_State os;
    And_State as;
    TrList trl;
    boolean ok=true;
    boolean found;

    // nur im Falle eines OrStates überprüfen
    if (s instanceof Or_State) {
      os = (Or_State)s;
      substates=os.substates;
      trl = os.trs;
      while (trl != null) {
        // Testen, ob die Events in den Guards auch deklariert sind
        if (trl.head.label.guard instanceof GuardEvent) {
          GuardEvent g = (GuardEvent)trl.head.label.guard;
          se = sel;
          found = false;
          while (se != null && found==false) {
            if (g.event.name.compareTo(se.head.name)==0) { found = true; }
            se = se.tail;
            }

          // Event aus der Transitionsliste wurde nicht gefunden
          if (found == false) {
            error.addError(new ItemError(100,"Event ("+g.event.name+") nicht gefunden", path));
            //System.out.println(path+"Event("+g.event.name+") nicht gefunden");
            ok = false;
            }
          }

        // Testen, ob die Events in den Actions auch deklariert sind
        if (trl.head.label.action instanceof ActionEvt) {
          ActionEvt a = (ActionEvt)trl.head.label.action;
          se = sel;
          found = false;
          while (se != null && found==false) {
            if (a.event.name.compareTo(se.head.name)==0) { found = true; }
            se = se.tail;
            }

          // Event aus der Transitionsliste wurde nicht gefunden
          if (found == false) {
            error.addError(new ItemError(100,"Event ("+a.event.name+") nicht gefunden", path));
            ok = false;
            }
          }

        trl = trl.tail;
        }

      } else
      if (s instanceof And_State) {
        as = (And_State)s;
        substates = as.substates;
        }


  // diese Methode an allen Substates aufrufen
  while (substates != null) {
    ok = ok && new CheckEvents(statechart,
                              substates.head,
                              path+"."+substates.head.name.name, error, warning).check();
    substates = substates.tail;
    }

  return ok;
	}


}
