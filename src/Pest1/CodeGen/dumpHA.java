/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.24 1999-03-01 14:36:06 swtech25 Exp $
 * @author Marcel Kyas
 */
package codegen;

import absyn.*;
import java.io.*;
import java.util.*;

public class dumpHA
{
  private static final float loadFactor = 0.7f;
  private static final int hashTabSize = 1024;
  private Statechart S1;
  private Statechart S2;
  CodeGenOpt opt;
  private dumpTables DT;


  /**
   * This will construct a dumb instance of dumpHA.  You will
   * need to set the state chart seperately.
   */
  public dumpHA(Statechart s1, Statechart s2, CodeGenOpt o)
  {
    S1 = s1;
    S2 = s2;
    opt = o;
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
   */
  private String dumpAction(int lvl, Action a)
       throws CodeGenException
  {
    String ret = new String();
    
    if (a instanceof ActionBlock) {
      ActionBlock b = (ActionBlock) a;
      Aseq current = b.aseq;

      while (current != null) {
	ret += dumpAction(lvl, current.head);
	current = current.tail;
      }
    } else if (a instanceof ActionEmpty) {
      ret += util.printlnPP(lvl, "// Empty action");
    } else if (a instanceof ActionEvt) {
      ActionEvt b = (ActionEvt) a;

      ret += util.printlnPP(lvl, "a.post_events[a." +
			    dumpTables.generateSymEvent(b.event)
			    + "] = true;");
    } else if (a instanceof ActionStmt) {
      ActionStmt b = (ActionStmt) a;

      ret += util.printlnPP(lvl, dumpStatement(b.stmt));
    } else {
      throw(new CodeGenException("Unknown Action."));
    }
    return ret;
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
   * This method will handle or-state targets.  Pass a target state as s.
   * @exception CodeGenException If chart is inconsistent.
   */
  private String dumpDefaultStates(int lvl, Path p, State s)
       throws CodeGenException
  {
    String ret = new String();

    if (s instanceof Or_State) {
      Or_State os = (Or_State) s;
      Path q = p.append(os.defaults.head.name);
      StateList current = os.substates;

      ret += util.printlnPP(lvl, "// Default of " + os.name.name);
      while (current != null) {
	Path r = p.append(current.head.name.name);

	if (current.head instanceof Or_State ||
	    current.head instanceof And_State) {
	  ret += util.printlnPP(lvl, "a.post_states[a." +
				dumpTables.generateSymState(r) + "] = true;");
	  ret += dumpDefaultStates(lvl, r, current.head);
	}
	current = current.tail;
      }
    } else if (s instanceof And_State) {
      And_State as = (And_State) s;
      StateList current = as.substates;

      ret += util.printlnPP(lvl, "// Defaults of " + as.name.name);
      while (current != null) {
	Path q = p.append(current.head.name.name);

	if (current.head instanceof Or_State ||
	    current.head instanceof And_State) {
	  ret += util.printlnPP(lvl, "a.post_states[a." +
				dumpTables.generateSymState(q) + "] = true;");
	  ret += dumpDefaultStates(lvl, q, current.head);
	}
	current = current.tail;
      }
    }
    return ret;
  }


  /**
   * This method is doing the actual look up for lookupState.
   * @see lookupState.
   * @exception CodeGenException Raised if we cannot find the target.
   */
  private State lookup(Statename t, StateList c) throws CodeGenException
  {
    StateList current = c;

    while (current != null) {
      if (t.name.equals(current.head.name.name)) {
	return current.head;
      }
      current = current.tail;
    }
    throw(new CodeGenException("lookup(" + t + ", " + c +
			       "): Cannot find the desired state in list."));
  }


  /**
   * This method will look up a state from a transition name.
   * Yuck.  Java does not short circuit boolean expressions like C does.
   * @exception CodeGenException Raised if we cannot find the target.
   */
  private State lookupState(Statename t, State s) throws CodeGenException
  {
    if (s instanceof Or_State) {
      Or_State o = (Or_State) s;

      return lookup(t, o.substates);
    }
    else if (s instanceof And_State) {
      And_State o = (And_State) s;

      return lookup(t, o.substates);
    }
    throw(new CodeGenException("lookupState(" + t + "," + s +
			       "): Cannot search this state."));
  }


  /**
   * This method will dump code for a new target.
   *
   * @exception CodeGenException Raised if we cannot find the target.
   */
  private String dumpTarget(int lvl, Path p, Or_State o, TrList tl,
			    TrAnchor t)
       throws CodeGenException
  {
    Path q;

    if (t instanceof Conname) {
      return dumpTransitions(lvl, p, o, tl, t);
    } else if (t instanceof Statename) {
      String ret;
      Statename s = (Statename) t;

      q = p.append(s.name);
      ret = util.printlnPP(lvl, "a.post_states[a." + dumpTables.generateSymState(q)
			   + "] = true;");
      ret += dumpDefaultStates(lvl, q, lookupState(s, o));
      return ret;
    } else if (t instanceof UNDEFINED) {
      throw(new CodeGenException("Undefined target."));
    } else {
      throw(new CodeGenException("Unknown target."));
    }
  }


  /**
   * This method will build a list of all transitions from state s.
   * @exception CodeGenException If we fail to determine the transition
   * for various reasons.
   */
  private CodeGenTrList collectTransitions(int lvl, Path p, Or_State o,
					  TrList tl, TrAnchor s)
       throws CodeGenException
  {
    TrList current = tl;
    CodeGenTrList tr = null;
    int trs = 0;

    while(current != null) {
      if (tr != null) {
	tr = tr.append(new CodeGenTrans(trs++, null,
					dumpTarget(lvl + 1, p, o, tl,
						   current.head.target),
					dumpGuard(current.head.label.guard),
					dumpAction(lvl + 1,
						   current.head.label.action)
					)
		       );
      } else {
	tr = new CodeGenTrList(new CodeGenTrans(trs++, null,
						dumpTarget(lvl + 1, p, o, tl,
							   current.head.target),
						dumpGuard(current.head.label.guard),
						dumpAction(lvl + 1,
							   current.head.label.action)
						)
			       );
	}
      current = current.tail;
    }
    return tr;
  }


  /**
   * This method generates code for the dynamic evaluation of
   * transitions.
   */
  private String dumpNonDetEval(int lvl, CodeGenTrList tr)
  {
    String ret = new String();
    CodeGenTrList current = tr;
    
    ret += util.printlnPP(lvl, "boolean[] enabled = {");
    while (current != null) {
      ret += util.printlnPP(lvl + 1, tr.head.guard + ",");
      current = current.tail;
    }
    ret += util.printlnPP(lvl, "};");
    return ret;
  }


  /**
   * This method puts code for non determinism out.
   */
  private String dumpNonDet(int lvl)
  {
    String ret = new String();
    ret += util.printlnPP(lvl, "trans = 0") +
      util.printlnPP(lvl, "for (i = 0; i < enabled.length; ++i)") +
      util.printlnPP(lvl + 1, "if (enabled[i])") +
      util.printlnPP(lvl + 2, "++trans;") +
      util.printlnPP(lvl, "if (trans == 0) {") +
      util.printlnPP(lvl + 1, "selected = -1; // Force default") +
      util.printlnPP(lvl, "} else if (trans == 1) {") +
      util.printlnPP(lvl + 1, "for (selected = 0; !enabled[selected]; " +
		     "++selected)") +
      util.printlnPP(lvl + 2, ";") +
      util.printlnPP(lvl, "} else {");
    if (opt.nondetFlavor == opt.takeFirst) {
      ret += util.printlnPP(lvl + 1, "for (selected = 0; " +
			    "!enabled[selected]; " +
			    "++selected)") +
	util.printlnPP(lvl + 2, ";");
    } else if (opt.nondetFlavor == opt.random) {
      // Other cases go here ...
    }
    return ret;
  }


  /**
   * This will generate code for stuttering steps.
   */
  private String dumpStutter(int lvl, Path p)
  {
    return util.printlnPP(lvl, "// no outgoing transitions found") +
      util.printlnPP(lvl,"// Need to stutter here.") +
      util.printlnPP(lvl, "a.post_states[a." + dumpTables.generateSymState(p) +
		     "] = true;");
  }


  /**
   * We will create code for the actual selection of a transition.
   */
  private String dumpTrSelect(int lvl, Path p, CodeGenTrList tr)
  {
    String ret = new String();
    CodeGenTrList current = tr;

    ret += util.printlnPP(lvl, "switch (trans) {");
    while (current != null) {
      ret += util.printlnPP(lvl, "case " + tr.head.number + ":") +
	tr.head.action +
	tr.head.target +
	util.printlnPP(lvl + 1, "break;");
      current = current.tail;
    }
    ret += util.printlnPP(lvl, "default:") +
      dumpStutter(lvl + 1, p) +
      util.printlnPP(lvl + 1, "break;") +
      util.printlnPP(lvl, "}");
    return ret;
  }


  /**
   * This method will dump the necessary code for
   * transitions.  s denotes the source.
   *
   * @exception CodeGenException If we fail to determine the transition
   * for various reasons.
   */
  private String dumpTransitions(int lvl, Path p, Or_State o, TrList tl,
				 TrAnchor s)
       throws CodeGenException
  {
    String ret = new String();
    CodeGenTrList tr;

    tr = collectTransitions(lvl, p, o, tl, s);
    if (tr == null) {
      ret += dumpStutter(lvl, p);
    } else {
      ret += dumpNonDetEval(lvl, tr);
      ret += dumpNonDet(lvl);
      ret += dumpTrSelect(lvl, p, tr);
    }
    return ret;
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
    f.write(util.printlnPP(lvl, "// Basic State " + s.name.name));
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

    f.write(util.printlnPP(lvl, "// Or State " + s.name.name));
    while (current != null) {
      q = p.append(current.head.name.name);
      f.write(util.printlnPP(lvl, "if (a.pre_states[a." +
			     dumpTables.generateSymState(q) +
			     "]) {"));
      f.write(dumpTransitions(lvl + 1, p, s, s.trs, current.head.name));
      f.write(util.printlnPP(lvl, "} else"));
      current = current.tail;
    }
    f.write(util.printlnPP(lvl, "{"));
    iterateStateList(f, lvl + 1, p, s.substates);
    f.write(util.printlnPP(lvl, "}"));
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
    f.write(util.printlnPP(lvl, "// And State " + s.name.name));
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
  private void dumpAutomaton(OutputStreamWriter f, String name, Statechart s)
       throws CodeGenException, IOException
  {
    Path p = new Path(S1.state.name.name, null);

    f.write(
	    util.printlnPP(0, "/**") +
	    util.printlnPP(0, " * This code was automatically generated by codegen") +
	    util.printlnPP(0, " */") +
	    util.printlnPP(0, "public class " + name + " {") +
	    util.printlnPP(0, "") +
	    util.printlnPP(1, "/**") +
	    util.printlnPP(1, " * Init method for state chart") +
	    util.printlnPP(1, " */") +
	    util.printlnPP(1, "public void init(SymbolTable a) {") +
	    util.printlnPP(2, "a.post_states[ a." +
			   dumpTables.generateSymState(p) + " ] = true;") +
	    dumpDefaultStates(2, p, s.state) +
	    util.printlnPP(1, "}") +
	    util.printlnPP(1, "") +
	    util.printlnPP(1, "/**") +
	    util.printlnPP(1, " * This method will simulate a single step") +
	    util.printlnPP(1, " * of the hierarchical automaton") +
	    util.printlnPP(1, " */") +
	    util.printlnPP(1, "public void step(SymbolTable a) {") +
	    util.printlnPP(1, "// Handle endEvent first") +
	    util.printlnPP(2, "if ( a.pre_events[a." +
			   dumpTables.generateSymEvent(new SEvent(
								  dumpTables.endEvent
								  )) +
			   "]) {") +
	    util.printlnPP(3, "// Stutter in this state") +
	    util.printlnPP(3, "a.post_events[a." + dumpTables.generateSymEvent(
									       new SEvent(dumpTables.endEvent)) + "] = true;") +
	    util.printlnPP(3, "return;") +
	    util.printlnPP(2, "}"));
    if (s.state instanceof And_State) {
      dumpAndState(f, 2, p, (And_State) s.state);
    } else if (s.state instanceof Or_State) {
      dumpOrState(f, 2, p, (Or_State) s.state);
    } else if (s.state instanceof Basic_State) {
      dumpBasicState(f, 2, p, (Basic_State) s.state);
    } else {
      throw(new CodeGenException(
				 "Cannot determine type of state."
				 ));
    }
    f.write(
	    util.printlnPP(1, "}") +
	    util.printlnPP(0, "}"));
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
    if (S2 != null && opt.twoStatecharts) {
      DT.generate(S2);
    }
    try {
      fw = new FileWriter(opt.path + "/SymbolTable.java");
      DT.dump(fw);
      fw.flush();
      fw.close();
      fw = new FileWriter(opt.path + "/" + opt.name1);
      dumpAutomaton(fw, opt.name1, S1);
      fw.flush();
      fw.close();
      if (opt.twoStatecharts && S2 != null) {
	fw = new FileWriter(opt.path + "/" + opt.name2);
	dumpAutomaton(fw, opt.name2, S2);
	fw.flush();
	fw.close();
      }
    }
    catch (IOException e) {
      throw(new CodeGenException(e.toString()));
    }
  }
}
