// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                CheckTransitions
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          10.02.1999
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
String path;

// ****************************************************************************
// Konstruktoren
// ****************************************************************************

  CheckTransitions(State s, String _path) {
    this.state = s;
    this.path = path;
  }

  CheckTransitions(State s, SyntaxError error, SyntaxWarning warning, String path) {
    this.state = s;
    this.error = error;
    this.warning = warning;
    this.path = path;
  }


// ****************************************************************************
// Methoden, die nur innerhalb des "packages" zu sehen sind
// ****************************************************************************

  boolean check() {

    boolean ok = true;
    String source;
    String target;

    // Zustand nur dann ueberpruefen, wenn Zustand ein OR-Zustand ist
    if (state instanceof Or_State) {

      os = (Or_State)state;

      trl = os.trs;

      while (trl != null) {

        Tr trans = trl.head; // Transition

        // zu ueberpruefen ist,
        //    a) Existenz der "Source"
        //    b) Existenz des "Targets"
        //    c) keine "Source", aber ein "Target" -> Warnung
        //    d) kein "Target aber eine "Source" -> Fehler
        //    e) Interleveltransistionen

        source="";
        target="";

        // Fall a)
        if (!(trans.source instanceof UNDEFINED)) {
          // suche nach dem "statename" im "state"
          if (trans.source instanceof Statename) {
            ok = Contains((Statename)trans.source) && ok;
            source = ((Statename)trans.source).name;
	    }

          if (!ok) { // Fehler
            error.addError(new ItemError(100, "Transitions Source "+source+" nicht gefunden",path));
          }
        }

        // Fall b)
        if (!(trans.target instanceof UNDEFINED)) {
          // suche nach dem "statename" im "state"
          if (trans.source instanceof Statename) {
             ok = Contains((Statename)trans.target) && ok;
             target = ((Statename)trans.target).name;
	     }

          if (!ok) { // Fehler!
            error.addError(new ItemError(100, "Transitions Target "+target+" nicht gefunden",path));
          }
        }

        // Fall c)
        if ((trans.source instanceof UNDEFINED) && (!(trans.target instanceof UNDEFINED))) {
          // warnung ausgeben
          warning.addWarning(new ItemWarning(101, "Transition "+source+"-> hat keine Source, aber ein Target",path));
          ok = false;
        }

        // Fall d)
        if ((!(trans.source instanceof UNDEFINED)) && (trans.target instanceof UNDEFINED)) {
          // fehler ausgeben
          ok = false;
          error.addError(new ItemError(100, "Transition ->"+target+" hat kein Target aber eine Source",path));
        }

        // Fall e): Ist die Transition eine Interlevel Transition?
        //          nur dann testen, wenn auch Koordinaten vorhanden sind!
        if (trans.points != null) {
          // Fall e1) : Anchor
          if (IsInterlevelTransition(state, trans, true)) {
            // fehler ausgeben
            ok = false;
            error.addError(new ItemError(103, "Quelle: Interleveltransition gefunden",path));
          }

          // Fall e2) : Dest
          if (IsInterlevelTransition(state, trans, false)) {
            // fehler ausgeben
            ok = false;
            error.addError(new ItemError(104, "Ziel: Interleveltransition gefunden",path));
          }
        }

        trl = trl.tail; // nächste Transition
      } // while

    } // if

    return ok;
  }


  // such in dem definierten "state" nach dem "statename"
  boolean Contains(Statename statename) {

    StateList stl = os.substates;
    boolean found = false;
    while ((!found) && (stl != null))  {
      State s = stl.head;
      // im Augenblick wird case-sensitive verglichen - ggf. auf
      // case insentiv Vergleich "equalsIgnoreCase" aendern
      if (s.name != null)
        if (statename != null)
          if (s.name.name.compareTo(statename.name)==0) {
            found = true;
          }
      // Iteration
      stl = stl.tail;
    } // while

    return found;
  }

  boolean IsInterlevelTransition(State s, Tr t, boolean anchor) {
    int xt,yt;

    if (!anchor) {
      int n=t.points.length-1;
      xt=t.points[n].x;
      yt=t.points[n].y;
    }else {
      xt=t.points[0].x;
      yt=t.points[0].y;
    }

    int xs=0; //s.rect.x;     relative Koordinaten, Rechteck immer von (0,0) bis (ws,hs)
    int ys=0; //s.rect.y;
    int hs=s.rect.height;
    int ws=s.rect.width;

    boolean OK=false;  // ist keine Interlevel Transition per default

    // 1. Fall: Transition zeigt auf den Rand der eigenen State
    if (umgebung(xs,xt) || umgebung(xs+ws,xt) ||
        umgebung(ys,yt) || umgebung(ys+hs,yt)) OK = true;

    // 2. Fall: Transition zeigt nach aussen
    if (xt<=xs || xt>=(xs+ws) || yt<=ys || yt>=(ys+hs)) OK=false;

    /*    
    if (!umgebung(xs,xt)    && yt>=ys && yt<=(ys+hs)) OK=false;
    if (!umgebung(ys,yt)    && xt>=xs && xt<=(xs+ws)) OK=false;
    if (!umgebung(xs+ws,xt) && yt>=ys && yt<=(ys+ws)) OK=false;
    if (!umgebung(ys+hs,yt) && xt>=xs && xt<=(xs+ws)) OK=false;
    */
    return OK; }

  boolean umgebung(int a, int s) {
    int e=2;
    boolean b=false;
    if (((int)Math.abs(a-s))<e) b=true;
    return b;
  }

}
