//   Projekt:               PEST2
//   Klasse:                CheckConnectors
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Mario Thies
//                          24.01.1999
//
// ****************************************************************************

package check;

import absyn.*;            // abstrakte Syntax
import java.util.*;        // fÅr Vector-Klasse benˆtigt

/**
 * @author Java Praktikum: <a href="mailto:swtech23@informatik.uni-kiel.de">Gruppe 23</a><br>Mario Thies und Tobias Kunz
 * @version $id:$
*/
class CheckConnectors {
  Statechart statechart;
  SyntaxWarning warning;
  SyntaxError error;
  String path = "";

  // Konstruktoren
  CheckConnectors(Statechart st, SyntaxError error, SyntaxWarning warning) {
    statechart = st;
    this.warning = warning;
    this.error = error;
    }


  boolean check() {
    return check(statechart.state, statechart.state.name.name);
    }

  boolean check(State state, String _path) {
    StateList substates = null;
    Or_State os;
    And_State as;
    boolean ok = true;
    path = _path;

    if (state instanceof Or_State) {
      os = (Or_State)state;
      ok = checkCircle(os);
      substates=os.substates;
      }

    if (state instanceof And_State) {
      as = (And_State)state;
      substates=as.substates;
      }

    // diese Methode an allen Substates aufrufen
    while (substates != null) {
      ok = ok && new CheckConnectors(statechart,error,warning).check(substates.head,path+"."+substates.head.name.name);
      substates = substates.tail;
      }

    return ok;

    }

  boolean checkCircle(Or_State state) {
    ConnectorList cl = state.connectors;
    boolean ok = true;

    // System.out.println("checking for circles in '"+path+"'");
    while (cl != null && ok) {
      // System.out.println("entering dfs at '"+cl.head.name.name+"'");
      ok = dfsConnector(state,cl.head.name.name,new Vector());
      cl = cl.tail;
      }

    // System.out.println("finishing checkCircle");
    return ok;
    }

  boolean dfsConnector(Or_State state,String con, Vector conList) {
    boolean ok = true;
    boolean found = false;
    TrList trl = state.trs;
    Conname cn;
    // Vector v=new Vector(conList.size()+1);
    Vector v=new Vector();
    v=conList;

    // System.out.println("dfs:"+con+", "+v);

    // Testen, ob Connector bereits besucht wurde
    if (conList.indexOf(con) != -1) {
      error.addError(new ItemError(100,"Kreis in Connectoren:"+v, path));
      ok = false;
      }

    v.addElement(con);    

    // Transitionen durchgehen und eine suchen, die von con ausgeht
    while (trl != null) {
      if (trl.head.source instanceof Conname) {
        cn = (Conname)trl.head.source;
        if (cn.name.compareTo(con)==0) { found=true; }
  
        if (trl.head.target instanceof Conname) {
          cn = (Conname)trl.head.source;
          if (cn.name.compareTo(con)==0 && ok) {
            cn = (Conname)trl.head.target;
            // System.out.println("found next connector '"+cn.name+"'");
            ok = ok && dfsConnector(state,cn.name,v);
            }
          }
        }
      trl = trl.tail;
      }

    if (!found) { warning.addWarning(new ItemWarning(100,"zu Connector "+con+" fuehrt keine Transition", path)); }

    return ok;
    }

  }
