//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          11.02.1999
//
// ****************************************************************************

package check;

import absyn.*;

class CheckStates {
  Statechart statechart;
  SyntaxWarning warning;
  SyntaxError error;

  CheckStates(Statechart statechart, SyntaxError error, SyntaxWarning warning) {
    this.statechart = statechart;
    this.error = error;
    this.warning = warning;
  }

  boolean check() {
    //System.out.println("Check State!");
    boolean ok = checkState(statechart.state, statechart.state.name.name);
    return ok;
  }

  boolean checkState(State state, String path) {
    boolean ok = true;
    And_State as;
    Or_State os;
    StateList substates = null;

    if (state == null) {
    error.addError(new ItemError(100, "Zustand ist leer",""));
    ok = false;
    }

    if (state instanceof And_State) {
      as = (And_State)state;
      substates = as.substates;
    }

    if (state instanceof Or_State) {
      os = (Or_State)state;
      substates = os.substates;
    }

    // diese Methode an allen Substates aufrufen
    while (substates != null) {
      //System.out.println(substates.head.name.name);
      ok = checkSingleState(substates.head, path) && ok;
      ok = checkState(substates.head, path+"."+substates.head.name.name) && ok;
      substates = substates.tail;
    }

  return ok;
  }


  /**
  * ueberprueft einen einzelnen "State"
  */
  boolean checkSingleState(State s, String path) {
    boolean ok = true;

    // Gueltiger Bezeichner
    if (s.name == null)
      error.addError(new ItemError(503, "Zustand hat NULL Bezeichner",""));
    if (s.name.name == "")
      warning.addWarning(new ItemWarning(504,"Zustand hat leeren Bezeichner",""));

    // OR State
    if (s instanceof Or_State) {
      // OR Zustand enthaelt keine States
      if (((Or_State)s).substates==null) {
        error.addError(new ItemError(500,"OR-Zustand enthaelt keine untergeordneten Zustaende",s.name.name));
        ok = false;
      }
    }
    // AND State
    if (s instanceof And_State) {
      // AND Zustand enthaelt keine States
      if (((And_State)s).substates==null) {
        error.addError(new ItemError(501,"AND-Zustand enthaelt keine untergeordneten Zustaende",s.name.name));
        ok = false;
      }
    }
    // enthaltene Transitionen pruefen
    CheckTransitions trans = new CheckTransitions(s, error, warning, path);
    ok = trans.check() && ok;

    return ok;
  }

  /*
  void pruefeState(State s, State _s, String p){
	//System.out.println("pS "+p);
    if (s instanceof Or_State) {
	   if (((Or_State)s).substates==null) {msg.addError(305,"State: "+s.name.name);}
	   else {
                p=getAddPathPart(p, s.name.name);
	        if (Anzahl_States(((Or_State)s).substates)==1) {msg.addWarning(304,"State: "+p);};
                Vector dc=defaultcon((Or_State)s,p);
                navStateInStateList(((Or_State)s).substates, s, p, dc); };};
    if (s instanceof And_State) {
           if (((And_State)s).substates==null) {msg.addError(306,"State: "+s.name.name);}
	   else {
            p=getAddPathPart(p, s.name.name);
            if (Anzahl_States(((And_State)s).substates)==1) {msg.addWarning(307,"State: "+p);};
            navStateInStateList(((And_State)s).substates, s, p, new Vector()); };};
    }
  */
}
