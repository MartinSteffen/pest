//   Projekt:               PEST2
//   Klasse:                CheckBVars
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          11.01.1999
//
// ****************************************************************************

/**
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
package check;

import absyn.*;         // abstrakte Syntax

class CheckBVars {
  Statechart statechart;
  State s;
  String path;
  SyntaxWarning warning;
  SyntaxError error;

  CheckBVars(Statechart _st, SyntaxError error, SyntaxWarning warning) {
    statechart = _st;
    s = statechart.state;
    path = "";
    this.warning = warning;
    this.error = error;
    }

  CheckBVars(Statechart _st, State _s, String _path) {
    statechart = _st;
    s = _s;
    path = _path;
    }

  CheckBVars(Statechart _st, State _s, String _path, SyntaxError error, SyntaxWarning warning) {
    statechart = _st;
    s = _s;
    path = _path;
    this.error = error;
    this.warning = warning;
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
            //System.out.println(path+"BVar("+g.bvar.var+") nicht gefunden");
            error.addError(new ItemError(100,"BVar ("+g.bvar.var+") nicht gefunden", path));
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
            //System.out.println(path+"BVar("+ba.ass.blhs.var+") nicht gefunden");
            error.addError(new ItemError(100,"BVar ("+ba.ass.blhs.var+") nicht gefunden", path));
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

}
