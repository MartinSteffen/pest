/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.20 1999-02-17 11:00:02 swtech25 Exp $
 * @author Marcel Kyas
 */
package codegen;

import absyn.*;
import java.io.*;
import java.util.*;

public class dumpHA
{
  private static final String indentLevel = "    ";
  private static final float loadFactor = (float) 0.7;
  private static final int hashTabSize = 1024;
  private Statechart S1;
  private Statechart S2;
  CodeGenOpt opt;
  private String path;
  private dumpTables DT;
  private Hashtable transitions = new Hashtable(hashTabSize, loadFactor);


  /**
   * This will construct a dumb instance of dumpHA.  You will
   * need to set the state chart seperately.
   */
  public dumpHA(Statechart s1, Statechart s2, CodeGenOpt o)
  {
    S1 = s1;
    S2 = s2;
    opt = o;
    path = o.path;
  }


  /**
   * This will construct some instance of dumpHA and will initialize
   * its state chart with bla and the path name to fasel.
   */
  public dumpHA(Statechart s1, Statechart s2, String p)
  {
    S1 = s1;
    S2 = s2;
    path = p;
  }


  /**
   * This method will write a line to a file and pretty-print
   * it.
   */
  private String printlnPP(int lvl, String s)
  {
    int i;
    String t = new String();

    for (i = lvl; i > 0; --i) {
      t += indentLevel;
    }
    return (t + s + "\n");
  }


  /**
   * This will generate the code for an ActionStatement.
   *
   * @exception CodeGenException if and only if we cannot determine
   *   the type of statement.
   */
  private String dumpStatement(Boolstmt b)
       throws CodeGenException
  {
    if (b instanceof BAss) {
      BAss c = (BAss) b;
      return "a.post_cond[a." +
	dumpTables.generateSymBVar(c.ass.blhs) +
	"] = " + dumpGuard(c.ass.brhs) + ";";
    } else if (b instanceof MFalse) {
      MFalse c = (MFalse) b;
      return "a.post_cond[a." + 
	dumpTables.generateSymBVar(c.var) +
	"] = false;";
    } else if (b instanceof MTrue) {
      MTrue c = (MTrue) b;
      return "a.post_cond[a." +
	dumpTables.generateSymBVar(c.var) +
	"] = true;";
    } else {
      throw(new CodeGenException(
				 "Unknown boolean assignment"
				 ));
    }
  }


  /**
   * In this section we will generate the new
   * events.
   *
   * @exception CodeGenException iff we cannot determine the
   *   action type.
   * @exception IOException .
   */
  private void dumpNewEvents(OutputStreamWriter f, int lvl, Action a)
       throws CodeGenException, IOException
  {
    if (a instanceof ActionBlock) {
      ActionBlock b = (ActionBlock) a;
      Aseq current = b.aseq;

      while (current != null) {
	dumpNewEvents(f, lvl, current.head);
	current = current.tail;
      }
    } else if (a instanceof ActionEmpty) {
      f.write(printlnPP(lvl, "// Empty action"));
      return;
    } else if (a instanceof ActionEvt) {
      ActionEvt b = (ActionEvt) a;

      f.write(printlnPP(lvl, "a.post_events[a." +
			dumpTables.generateSymEvent(b.event)
			+ "] = true;"));
    } else if (a instanceof ActionStmt) {
      ActionStmt b = (ActionStmt) a;

      f.write(printlnPP(lvl, dumpStatement(b.stmt)));
    } else {
      throw(new CodeGenException("Unknown Action."));
    }
  }


  /**
   * This method will create the boolean statement for a guard
   *
   * @exception CodeGenException .
   */
  private String dumpGuard(Guard g)
       throws CodeGenException
  {
    if (g instanceof GuardBVar) {
      GuardBVar h = (GuardBVar) g;
      return "pre_cond[a." +
	dumpTables.generateSymBVar(h.bvar) +
	"]";
    } else if (g instanceof GuardCompg) {
      GuardCompg h = (GuardCompg) g;
      String s = dumpGuard(h.cguard.elhs);
      String t = dumpGuard(h.cguard.erhs);
      switch (h.cguard.eop) {
      case Compguard.AND:
	return "( " + s + " && " + t + " )";
      case Compguard.OR:
	return "( " + s + " || " + t + " )";
      case Compguard.IMPLIES:
	return "( !" + s + " || " + t + " )";
      case Compguard.EQUIV:
	return "(( " + s + " && " + t + ") || ( !" +
	  s + " && !" + t + "))";
      default:
	throw(new CodeGenException("Unsupported binary operation"));
      }
    } else if (g instanceof GuardCompp) {
      GuardCompp h = (GuardCompp) g;
      Path p = h.cpath.path;
      String s = "a." + dumpTables.generateSymState(p);

      switch (h.cpath.pathop) {
      case Comppath.IN:
	return "a.pre_states[" + s + "]";
      case Comppath.ENTERED:
	return "( !a.his_states[" + s + "] && a.pre_states[" + s + "])";
      case Comppath.EXITED:
	return "( a.his_states[" + s + "] && !a.pre_states[" + s + "])";
      default:
	throw(new CodeGenException("Unsupported path operation"));
      }
    } else if (g instanceof GuardEmpty) {
      return "true";
    } else if (g instanceof GuardEvent) {
      GuardEvent h = (GuardEvent) g;

      return "a.pre_events[a." +dumpTables.generateSymEvent(h.event) + "]";
    } else if (g instanceof GuardNeg) {
      GuardNeg h = (GuardNeg) g;
      return "!(" + dumpGuard(h.guard) +")";
    } else if (g instanceof GuardUndet) {
      return "true" ;
      // We should warn about this situation.
    } else {
      throw(new CodeGenException("Cannot determine type of guard."));
    }
  }


  /**
   * This method will handle or-state targets.
   * @exception CodeGenException If chart is inconsistent.
   */
  private String dumpDefaultStates(int lvl, Path p, Or_State o, TrAnchor t)
       throws CodeGenException
  {
    String ret = new String();
    
    StateList current = o.substates;
    if (!current.head.name.equals(t)) {
      current = current.tail;
      if (current == null) {
	throw(new CodeGenException("Cannot determine sub-state"));
      }
    }
    if (current.head instanceof Or_State) {
      Or_State u = (Or_State) current.head;
      ret += printlnPP(lvl, "a.post_states[a." +
		       dumpTables.generateSymState(p.append(u.defaults.head.name)) 
		       + "] = true;");
    }
    return ret;
  }


  /**
   * This method will dump code for a new target.
   *
   * @exception CodeGenException .
   * @exception IOException .
   */
  private void dumpNextState(OutputStreamWriter f,
			     int lvl,
			     Path p,
			     Or_State o,
			     TrList tl,
			     TrAnchor t)
       throws CodeGenException, IOException
  {
    Path q;

    if (t instanceof Conname) {
      dumpTransitions(f, lvl, p, o, tl, t);
    } else if (t instanceof Statename) {
      String ret;
      Statename s = (Statename) t;

      q = p.append(s.name);
      ret = printlnPP(lvl, "a.post_states[a." + dumpTables.generateSymState(q) +
		      "] = true;");
      ret += dumpDefaultStates(lvl, q, o, t);
      f.write(ret);
    } else if (t instanceof UNDEFINED) {
      throw(new CodeGenException("Undefined target"));
    } else {
      throw(new CodeGenException("Unknown target"));
    }
  }


  /**
   * This method will dump the necessary code for
   * transitions.  s denotes the source.
   */
  private void dumpTransitions(OutputStreamWriter f,
			       int lvl,
			       Path p,
			       Or_State o,
			       TrList tl,
			       TrAnchor s)
       throws CodeGenException, IOException
  {
    TrList current = tl;
    int trs = 0;

    while(current != null) {
      if ((s instanceof Statename) &&
	  (current.head.source instanceof Statename)) {
	Statename t = (Statename) current.head.source;
	Statename u = (Statename) s;

	if (t.name.equals(u.name)) {
	  TLabel l = current.head.label;

	  trs++;
	  f.write(printlnPP(lvl, "if (" +
			    dumpGuard(l.guard)
			    + ") {"));
	  dumpNewEvents(f, lvl + 1, l.action);
	  dumpNextState(f, lvl + 1, p, o, tl,
			current.head.target);
	  f.write(printlnPP(lvl, "}"));
	}
      }
      current = current.tail;
    }
    if (trs == 0) {
      f.write(printlnPP(lvl,
			"// no outgoing transitions found") +
	      printlnPP(lvl,
			"// Need to stutter here.") +
	      printlnPP(lvl, "a.post_states[a." +
			dumpTables.generateSymState(p) +
			"] = true;")
	      );
    }
  }


  /**
   * This section will create code for a basic state
   * @exception CodeGenException If no such state.
   * @exception IOException self-explanatory.
   */
  private void dumpBasicState(OutputStreamWriter f,
			      int lvl,
			      Path p,
			      Basic_State s)
       throws IOException, CodeGenException
  {
    f.write(printlnPP(lvl, "// Basic State " + s.name.name));
  }


  /**
   * This section will create code for an Or-state.
   * @exception CodeGenException ?
   * @exception IOException ?
   */
  private void dumpOrState(OutputStreamWriter f,
			   int lvl,
			   Path p,
			   Or_State s)
       throws CodeGenException, IOException
  {
    StateList current = s.substates;
    TrList currTr = s.trs;
    Path q;

    f.write(printlnPP(lvl, "// Or State " + s.name.name));
    while (current != null) {
      q = p.append(current.head.name.name);
      f.write(printlnPP(lvl, "if (a.pre_states[a." +
			dumpTables.generateSymState(q) +
			"]) {"));
      dumpTransitions(f, lvl + 1, p, s, s.trs,
		      current.head.name);
      f.write(printlnPP(lvl, "} else"));
      current = current.tail;
    }
    f.write(printlnPP(lvl, "{"));
    iterateStateList(f, lvl + 1, p, s.substates);
    f.write(printlnPP(lvl, "}"));
  }


  /**
   * This section will create the code for an And-State.
   * @exception CodeGenException If we are not able to infere
   *   the type of a sub state.
   * @exception IOException ?
   */
  private void dumpAndState(OutputStreamWriter f,
			    int lvl,
			    Path p,
			    And_State s)
       throws IOException, CodeGenException
  {
    f.write(printlnPP(lvl, "// And State " + s.name.name));
    iterateStateList(f, lvl, p, s.substates);
  }


  /**
   * This section of code will iterate a list of
   * states.
   * @exception CodeGenException If we are not able to infere
   *   the type of a sub state.
   * @exception IOException ?
   */
  private void iterateStateList(OutputStreamWriter f,
				int lvl,
				Path p,
				StateList sl)
       throws CodeGenException, IOException
  {
    StateList current = sl;
    Path q;

    while (current != null) {
      q = p.append(current.head.name.name);
      if (current.head instanceof And_State) {
	dumpAndState(f, lvl, q,
		     (And_State) current.head);
      } else if (current.head instanceof Or_State) {
	dumpOrState(f, lvl, q,
		    (Or_State) current.head);
      } else if (current.head instanceof Basic_State) {
	dumpBasicState(f, lvl, q,
		       (Basic_State) current.head);
      } else {
	throw(new CodeGenException(
				   "Cannot determine type of state."
				   ));
      }
      current = current.tail;
    }
  }


  /**
   *
   */
  private void dumpAutomaton(OutputStreamWriter f)
       throws CodeGenException, IOException
  {
    Path p = new Path(S1.state.name.name, null);

    f.write(
	    printlnPP(0, "/**") +
	    printlnPP(0, " * This code was automatically generated by codegen") +
	    printlnPP(0, " */") +
	    printlnPP(0, "public class Automaton {") +
	    printlnPP(0, "") +
	    printlnPP(1, "/**") +
	    printlnPP(1, " * Init method for state chart") +
	    printlnPP(1, " */") +
	    printlnPP(1, "public void init(SymbolTable a) {") +
	    printlnPP(2, "pre_states[ " +
		      dumpTables.generateSymState(p) + " ] = true;") +
	    dumpDefaultState(2, p, S1.state, null) +
	    printlnPP(1, "}") +
	    printlnPP(1, "") +
	    printlnPP(1, "/**") +
	    printlnPP(1, " * This method will simulate a single step") +
	    printlnPP(1, " * of the hierarchical automaton") +
	    printlnPP(1, " */") +
	    printlnPP(1, "public void step(SymbolTable a) {") +
	    printlnPP(1, "// Handle endEvent first") +
	    printlnPP(2, "if ( a.pre_events[a." +
		      dumpTables.generateSymEvent(new SEvent(
							     dumpTables.endEvent
							     )) +
		      "]) {") +
	    printlnPP(3, "// Stutter in this state") +
	    printlnPP(3, "a.post_events[a." + dumpTables.generateSymEvent(
								      new SEvent(dumpTables.endEvent)) + "] = true;") +
	    printlnPP(3, "return;") +
	    printlnPP(2, "}"));
    if (S1.state instanceof And_State) {
      dumpAndState(f, 2, p, (And_State) S1.state);
    } else if (S1.state instanceof Or_State) {
      dumpOrState(f, 2, p, (Or_State) S1.state);
    } else if (S1.state instanceof Basic_State) {
      dumpBasicState(f, 2, p, (Basic_State) S1.state);
    } else {
      throw(new CodeGenException(
				 "Cannot determine type of state."
				 ));
    }
    f.write(
	    printlnPP(1, "}") +
	    printlnPP(0, "}"));
  }


  /**
   * This method will dump the hierarchical automaton
   *
   * @exception CodeGenException
   */
  void dump() throws CodeGenException
  {
    FileWriter fw;

    DT = new dumpTables(S1);
    if (S2 != null) {
      DT.generate(S2);
    }
    try {
      fw = new FileWriter(path + "/SymbolTable.java");
      DT.dump(fw);
      fw.flush();
      fw.close();
      fw = new FileWriter(path + "/Automaton.java");
      dumpAutomaton(fw);
      fw.flush();
      fw.close();
    }
    catch (IOException e) {
      throw(new CodeGenException(e.toString()));
    }
  }
}
