//   Projekt:               PEST2
//   Klasse:                CheckBVars
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          10.02.1999
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
  String path = "";
  SyntaxWarning warning;
  SyntaxError error;

  CheckBVars(Statechart _st, SyntaxError error, SyntaxWarning warning) {
    statechart = _st;
    s = statechart.state;
    if ((s != null) && (s.name != null)) path = s.name.name;
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
	boolean check() {
    BvarList bvl = statechart.bvars;
    BvarList bv;
    BAss ba = null;
    StateList substates=null;
    StateList sl;
    CheckDupes checkDupes = new CheckDupes(statechart, error, warning);
    Or_State os;
    And_State as;
    TrList trl = null;
    boolean ok = true;
    boolean found;

    if (s instanceof Or_State) {
      os = (Or_State)s;
      substates=os.substates;
      // in dieser Routine, weil es gerade so gut passt, noch einiges mehr
      // testen: Duplikate, Defaultstates
      if (!checkDupes.check(substates,path)) { ok = false; }
      if (substates==null || substates.head==null) {
          error.addError(new ItemError(100,"Substates in or-State nicht vorhanden oder null", path));
          ok = false;
          } else
      if (substates.tail==null && os.defaults==null) {
          os.defaults=new StatenameList(substates.head.name,null);
          warning.addWarning(new ItemWarning(100,"Defaultstate "+os.defaults.head.name+" gesetzt", path));
          }

      if (os.defaults==null ||
          os.defaults.head==null ||
          os.defaults.head.name==null ||
          os.defaults.head.name.compareTo("")==0) {
          error.addError(new ItemError(100,"Kein Defaultstate gesetzt", path));
          ok = false;
          }

      if (os.defaults != null && os.defaults.tail != null) {
          error.addError(new ItemError(100,"Zuviele Defaultstates gesetzt", path));
          ok = false;
          }

      // Versuche, den Defaultstate zu finden
      if (ok) {
        found=false;
        sl=substates;
        while (sl != null) {
          if (sl.head.name.name.compareTo(os.defaults.head.name)==0) { found=true; }
          sl=sl.tail;
	  }

        if (!found) {
          error.addError(new ItemError(100,"Defaultstate ist kein State in diesem Or-State", path));
          ok = false;
          }
        }

      trl = os.trs;
      while (trl != null) {
        // Testen, ob die BVars in den Guards auch deklariert sind
        if (trl.head.label.guard instanceof GuardBVar) {
          GuardBVar g = (GuardBVar)trl.head.label.guard;
          bv = bvl;
          found = false;
          while (bv != null && found==false) {
            if (g.bvar.var.compareTo(bv.head.var)==0) { found = true; }
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
          found = true;
          if (a.stmt instanceof BAss) {
            found = false;
            ba = (BAss)a.stmt;
            while (bv != null && found==false) {
              if (ba.ass.blhs.var.compareTo(bv.head.var)==0) { found = true; }
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
                              path+"."+substates.head.name.name,error,warning).check();
    substates = substates.tail;
    }

    return ok;
	}

}
