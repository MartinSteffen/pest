//   Projekt:               PEST2
//   Klasse:                CheckBVars
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          12.12.1998
//
// ****************************************************************************

package check;

import absyn.*;         // abstrakte Syntax

public class CheckBVars {
  Statechart statechart;
  State s;
  String path;
  SyntaxWarning warnings = new SyntaxWarning();
  SyntaxError errors   = new SyntaxError();

  CheckBVars(Statechart _st) {
    statechart = _st;
    s = statechart.state;
    path = "";
    }

  CheckBVars(Statechart _st, State _s, String _path) {
    statechart = _st;
    s = _s;
    path = _path;
    }

//
	public boolean check() {
    BvarList bvl = statechart.bvars;
    BvarList bv;
    BAss ba = null;
    StateList substates = null;
    Or_State os;
    And_State as;
    TrList trl = null;
    boolean ok = true;
    boolean found;

    if (s instanceof Or_State) {
      os = (Or_State)s;
      substates=os.substates;
      trl = os.trs;
      while (trl != null) {
        // Testen, ob die BVars in den Guards auch deklariert sind
        if (trl.head.label.guard instanceof GuardBVar) {
          GuardBVar g = (GuardBVar)trl.head.label.guard;
          bv = bvl;
          found = false;
          while (bv != null && found==false) {
            if (g.bvar.var == bv.head.var) { found = true; }
            bv = bv.tail;
            }

          // BVar aus der Transitionsliste wurde nicht gefunden
          if (found == false) {
            System.out.println(path+"BVar("+g.bvar.var+") nicht gefunden");
            ok = false;
            }
          }

        // Testen, ob die BVars in den Actions auch deklariert sind
        if (trl.head.label.action instanceof ActionStmt) {
          ActionStmt a = (ActionStmt)trl.head.label.action;
          bv = bvl;
          found = false;
          while (bv != null && found==false) {
            if (a.stmt instanceof BAss) {
              ba = (BAss)a.stmt;
              if (ba.ass.blhs.var == bv.head.var) { found = true; }
              bv = bv.tail;
              }
            }

          // Event aus der Transitionsliste wurde nicht gefunden
          if (found == false) {
            System.out.println(path+"BVar("+ba.ass.blhs.var+") nicht gefunden");
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
    ok = ok && new CheckBVars(statechart,
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