//   Projekt:               PEST2
//   Klasse:                CheckEvents
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          11.02.1999
//
// ****************************************************************************

package check;

import absyn.*;         // abstrakte Syntax

/**
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
class CheckLabels {
  Statechart statechart;
  State s;
  String source;
  String target;
  String path = "";
  SyntaxWarning warning;
  SyntaxError error;

  CheckLabels(Statechart _st, SyntaxError error, SyntaxWarning warning) {
    statechart = _st;
    s = statechart.state;
    if ((s != null) && (s.name != null)) path = s.name.name;
    this.error = error;
    this.warning = warning;
    }

  CheckLabels(Statechart _st, State _s, String _path, SyntaxError error, SyntaxWarning warning) {
    statechart = _st;
    s = _s;
    path = _path;
    this.error = error;
    this.warning = warning;
    }

  // überprüft, ob alle Events im State s auch in der Statechart
  // definiert sind. Zurückgegeben wird TRUE, wenn dies der Fall ist.
	boolean check() {
    SEventList sel = statechart.events;
    SEventList se;
    StateList substates = null;
    StateList sl;
    Or_State os;
    And_State as;
    TrList trl;
    boolean ok=true;
    boolean found;
    ActionEvt a;
    CheckDupes checkDupes = new CheckDupes(statechart, error, warning); 

    // nur im Falle eines OrStates überprüfen
    if (s instanceof Or_State) {
      os = (Or_State)s;
      substates=os.substates;

      // Teste auf Defaultstates und diverse Anomalitaeten
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
        source="";
        target="";
        if (trl.head.source instanceof Statename) source=((Statename)trl.head.source).name;
        if (trl.head.source instanceof Conname) source=((Conname)trl.head.source).name;
        if (trl.head.target instanceof Statename) target=((Statename)trl.head.target).name;
        if (trl.head.target instanceof Conname) target=((Conname)trl.head.target).name;

        // Testen, ob die Events und BVars in den Guards auch deklariert sind
        ok = checkGuard(trl.head.label.guard) && ok;

        // Testen, ob die Events und BVars in den Actions auch deklariert sind
        ok = checkAction(trl.head.label.action) && ok;

        trl = trl.tail;
        }

      } else
    if (s instanceof And_State) {
      as = (And_State)s;
      substates = as.substates;
      } else
    substates = null;

  // diese Methode an allen Substates aufrufen
  while (substates != null) {
    ok = checkDupes.check(substates, path) && ok;
    ok = new CheckLabels(statechart,
                         substates.head,
                         path+"."+substates.head.name.name, error, warning).check() && ok;
    substates = substates.tail;
    }

  return ok;
	}

  boolean checkAction(Action a) {
    boolean ok = true;
    Aseq asq;
    ActionBlock ablock;
    SEventList sel = statechart.events;
    SEventList se;
    BvarList bvl = statechart.bvars;
    BvarList bv;
    BAss ba = null;

    // eine Action kann sein:
    // - ActionStmt
    // - ActionEvt
    // - ActionBlock
    // - ActionEmpty (leer)

    if (a == null) {
      ok=false;
      error.addError(new ItemError(100,"Action aus "+source+"->"+target+" ist Null-Pointer", path));
      } else

    if (a instanceof ActionBlock) {
      asq = ((ActionBlock)a).aseq;
      while (asq != null) {
        ok = checkAction(asq.head) && ok;
        asq = asq.tail;
        }
      } else

    if (a instanceof ActionStmt) {
      bv = bvl;
      if (((ActionStmt)a).stmt instanceof BAss) {
        ok = false;
        ba = (BAss)((ActionStmt)a).stmt;
        while (bv != null && ok==false) {
          if (ba.ass.blhs.var.compareTo(bv.head.var)==0) { ok = true; }
            bv = bv.tail;
          }
        ok = checkGuard(ba.ass.brhs) && ok;
        }
      } else

    if (a instanceof ActionEvt) {
      se = sel;
      ok=false;
      while (se != null && !ok) {
        if (((ActionEvt)a).event.name.compareTo(se.head.name)==0) ok=true;
        se = se.tail;
        }
      if (!ok) error.addError(new ItemError(100,"Event ("+((ActionEvt)a).event.name+") aus "+source+"->"+target+" nicht gefunden", path));
      }

    return ok;
    }

  boolean checkGuard(Guard g) {
    boolean ok = true;
    SEventList sel = statechart.events;
    SEventList se;
    BvarList bvl = statechart.bvars;
    BvarList bv;
    Path cPath;
    PathList pl;

    // ein Guard kann sein:
    // GuardCompg
    // GuardCompp
    // GuardEvent
    // GuardNeg
    // GuardUndet
    // GuardEmpty (leer)

    if (g == null) {
      ok=false;
      error.addError(new ItemError(100,"Guard aus "+source+"->"+target+" ist Null-Pointer", path));
      } else

    if (g instanceof GuardCompg) {
      ok = checkGuard(((GuardCompg)g).cguard.elhs) &&
           checkGuard(((GuardCompg)g).cguard.erhs) && ok;
      } else

    if (g instanceof GuardCompp) {
      cPath=((GuardCompp)g).cpath.path;
      // Suchen, ob cPath als Pfad überhaupt existiert
      pl=statechart.cnames;
      ok = false;
      while (pl != null && !ok) {
        if (pathEqual(cPath,pl.head)) { ok = true; }
        pl=pl.tail;
        }
      if (!ok) {
        error.addError(new ItemError(100,"Pfad in Bedingung im Guard in "+source+"->"+target+" nicht gefunden", path));
        }
      } else

    if (g instanceof GuardEvent) {
      se = sel;
      ok = false;
      while (se != null && ok==false) {
        if (((GuardEvent)g).event.name.compareTo(se.head.name)==0) { ok = true; }
        se = se.tail;
        }

      if (!ok) {
        error.addError(new ItemError(100,"Event aus Guard ("+((GuardEvent)g).event.name+") aus "+source+"->"+target+" nicht gefunden", path));
        }

      } else

    if (g instanceof GuardNeg) {
      ok = checkGuard(((GuardNeg)g).guard) && ok;
      } else

    if (g instanceof GuardUndet) {
        error.addError(new ItemError(100,"Event ("+((GuardEvent)g).event.name+") aus "+source+"->"+target+" nicht gefunden", path));
      }

    return ok;
    }

  // Vergleicht zwei Pfade auf Gleichheit
  boolean pathEqual(Path p1, Path p2) {
    boolean ok=false;
    Path a=p1;
    Path b=p2;

    while (a!=null && b!=null && a.head.compareTo(b.head)==0) {
      a=a.tail;
      b=b.tail;
      }

    ok=(a==null) && (b==null);
    return ok;
    }
}
