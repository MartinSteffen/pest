//   Projekt:               PEST2
//   Klasse:                CheckEvents
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          12.12.1998
//
// ****************************************************************************

package check;

import absyn.*;         // abstrakte Syntax

public class CheckEvents {
  Statechart statechart;
  State s;
  String path;
  SyntaxWarning warnings = new SyntaxWarning();
  SyntaxError errors   = new SyntaxError();

  CheckEvents(Statechart _st) {
    statechart = _st;
    s = statechart.state;
    path = "";
    }

  CheckEvents(Statechart _st, State _s, String _path) {
    statechart = _st;
    s = _s;
    path = _path;
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
            if (g.event.name == se.head.name) { found = true; }
            se = se.tail;
            }

          // Event aus der Transitionsliste wurde nicht gefunden
          if (found == false) {
            System.out.println(path+"Event("+g.event.name+") nicht gefunden");
            ok = false;
            }
          }

        // Testen, ob die Events in den Actions auch deklariert sind
        if (trl.head.label.action instanceof ActionEvt) {
          ActionEvt a = (ActionEvt)trl.head.label.action;
          se = sel;
          found = false;
          while (se != null && found==false) {
            if (a.event.name == se.head.name) { found = true; }
            se = se.tail;
            }

          // Event aus der Transitionsliste wurde nicht gefunden
          if (found == false) {
            System.out.println(path+"Event("+a.event.name+") nicht gefunden");
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
                              path+"."+substates.head.name.name).check();
    substates = substates.tail;
    }

  return ok;
	}

  // Rueckgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Fehlern
  // von Typ itemError
	public SyntaxError getErrors() {
    return errors;
	}

  // Rückgabe der Methode ist eine Liste (JAVA Klasse Vector) mit Warnungen
  // von Typ itemWarning
	public SyntaxWarning getWarnings() {
    return warnings;
	}

}