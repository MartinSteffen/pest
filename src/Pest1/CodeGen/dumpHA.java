/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.27 1999-03-11 15:36:43 swtech25 Exp $
 * @author Marcel Kyas
 */
package codegen;

import absyn.*;
import java.io.*;
import java.util.*;

public class dumpHA
{
  private static final String endEventName =
    dumpTables.generateSymEvent(new SEvent(dumpTables.endEvent));
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
   * This method is doing the actual look up for lookupState.
   * @see lookupState.
   * @exception CodeGenException Raised if we cannot find the target.
   */
  private final State lookup(Statename t, StateList c) throws CodeGenException
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
  private final State lookupState(Statename t, State s) throws CodeGenException
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


  private String dumpDefaultStateCode(int lvl, Path p, State s)
       throws CodeGenException
  {
    if ((s instanceof Or_State) || (s instanceof And_State)) {
      return util.printlnPP(lvl, "a.post_states[a." +
			    dumpTables.generateSymState(p) + "] = true;") +
	dumpDefaultStates(lvl, p, s);
    } else if (s instanceof Basic_State) {
      return util.printlnPP(lvl, "a.post_states[a." +
			    dumpTables.generateSymState(p) + "] = true;");
    } else {
      return "";
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

      ret += util.printlnPP(lvl, "// Default of or state " + os.name.name);
      ret += dumpDefaultStateCode(lvl, p.append(os.defaults.head.name),
				  lookup(os.defaults.head, os.substates));
    } else if (s instanceof And_State) {
      And_State as = (And_State) s;
      StateList current = as.substates;

      ret += util.printlnPP(lvl, "// Defaults of and state " + as.name.name);
      while (current != null) {
	ret += dumpDefaultStateCode(lvl, p.append(current.head.name.name),
				    current.head);
	current = current.tail;
      }
    }
    return ret;
  }


  /**
   * This method will dump code for a new target.
   *
   * @exception CodeGenException Raised if we cannot find the target.
   */
  private String dumpTarget(int lvl, int tag, Path p, Or_State o, TrList tl,
			    TrAnchor t)
       throws CodeGenException
  {
    Path q;

    if (t instanceof Conname) {
      System.out.println("Cannot handle Connames... not implemented yet.");
      return ""; // dumpTransitions(lvl, tag, p, o, tl, t);
    } else if (t instanceof Statename) {
      String ret;
      Statename s = (Statename) t;

      q = p.append(s.name);
      ret = util.printlnPP(lvl, "a.post_states[a." +
			   dumpTables.generateSymState(q)
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
  private CodeGenTrList collectTransitions(int lvl, int tag, Path p,
					   Or_State o, TrList tl, TrAnchor s)
       throws CodeGenException
  {
    TrList current = tl;
    CodeGenTrList tr = null;
    int trs = 0;

    while(current != null) {
      if (tr != null) {
	tr = tr.append(new CodeGenTrans(trs, null,
					dumpTarget(lvl + 1, tag + 1, p, o, tl,
						   current.head.target),
					dumpGuard(current.head.label.guard),
					dumpAction(lvl + 1,
						   current.head.label.action)));
	if (opt.traceCodeGen)
	  System.out.println("Collected transition " + trs);
	++trs;
      } else {
	tr = new CodeGenTrList(new CodeGenTrans(trs, null,
						dumpTarget(lvl + 1, tag + 1, p, o, tl,
							   current.head.target),
						dumpGuard(current.head.label.guard),
						dumpAction(lvl + 1,
							   current.head.label.action)));
	if (opt.traceCodeGen)
	  System.out.println("Collected transition " + trs);
	++trs;
      }
      current = current.tail;
    }
    return tr;
  }


  /**
   * This method generates code for the dynamic evaluation of
   * transitions.
   */
  private String dumpNonDetEval(int lvl, int tag, CodeGenTrList tr)
  {
    String ret = new String();
    CodeGenTrList current = tr;
    
    ret += util.printlnPP(lvl, "boolean[] enabled_" + tag + " = {");
    while (current != null) {
      ret += util.printlnPP(lvl + 1, current.head.guard + ",");
      current = current.tail;
    }
    ret += util.printlnPP(lvl, "};");
    return ret;
  }


  /**
   * This method puts code for non determinism out.
   */
  private String dumpNonDet(int lvl, int tag)
  {
    String ret = new String();
    ret += util.printlnPP(lvl, "trans = 0;") +
      util.printlnPP(lvl, "for (i = 0; i < enabled_" + tag +
		     ".length; ++i)") +
      util.printlnPP(lvl + 1, "if (enabled_"+ tag + "[i])") +
      util.printlnPP(lvl + 2, "++trans;") +
      util.printlnPP(lvl, "if (trans == 0) {") +
      util.printlnPP(lvl + 1, "selected = -1; // Force default") +
      util.printlnPP(lvl, "} else {");
    if (opt.nondetFlavor == opt.takeFirst) {
      ret += util.printlnPP(lvl + 1, "for (selected = 0; " +
			    "!enabled_" + tag + "[selected]; " +
			    "++selected)") +
	util.printlnPP(lvl + 2, ";");
    } else if (opt.nondetFlavor == opt.random) {
      ret += util.printlnPP(lvl + 1, "for (selected = " +
			    "Math.abs(rg.nextInt()) % enabled_" + tag +
			    ".length; !enabled_" + tag + "[selected]; " +
                            "selected = (selected + 1) % enabled_" +
			    tag + ".length)") +
	util.printlnPP(lvl + 2, ";");
    }
    ret += util.printlnPP(lvl, "}");
    return ret;
  }


  /**
   * This section of code will iterate a list of
   * states.
   * @exception CodeGenException If we are not able to infere
   *   the type of a sub state.
   */
  private final String iterateStateList(int lvl, int tag, Path p, StateList sl)
       throws CodeGenException
  {
    StateList current = sl;
    String ret = new String();
    Path q;

    while (current != null) {
      q = p.append(current.head.name.name);
      if (current.head instanceof And_State) {
	ret += dumpAndState(lvl, tag, q, (And_State) current.head);
      } else if (current.head instanceof Or_State) {
	ret += dumpOrState(lvl, tag, q, (Or_State) current.head);
      } else if (current.head instanceof Basic_State) {
	ret += dumpBasicState(lvl, tag, q, (Basic_State) current.head);
      } else {
	throw(new CodeGenException("Cannot determine type of state."));
      }
      current = current.tail;
    }
    return ret;
  }


  /**
   * This will generate code for stuttering steps.
   * @exception CodeGenException Whatever
   */
  private String dumpStutter(int lvl, int tag, Path p, StateList l)
       throws CodeGenException
  {
    return util.printlnPP(lvl, "a.post_states[a." +
			  dumpTables.generateSymState(p) + "] = true;") +
      iterateStateList(lvl, tag, p, l);
  }


  /**
   * We will create code for the actual selection of a transition.
   * @exception CodeGenException Choose your favourite error.
   */
  private String dumpTrSelect(int lvl, int tag, Path p, StateList l,
			      CodeGenTrList t)
       throws CodeGenException
  {
    String ret = new String();
    CodeGenTrList current = t;

    ret += util.printlnPP(lvl, "switch (selected) {");
    while (current != null) {
      ret += util.printlnPP(lvl, "case " + current.head.number + ":") +
	current.head.action +
	current.head.target +
	util.printlnPP(lvl + 1, "break;");
      current = current.tail;
    }
    ret += util.printlnPP(lvl, "default:") +
      dumpStutter(lvl + 1, tag + 1, p, l) +
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
  private String dumpTransitions(int lvl, int tag, Path p, Or_State o,
				 TrList tl, TrAnchor s)
       throws CodeGenException
  {
    String ret = new String();
    CodeGenTrList tr;

    tr = collectTransitions(lvl, tag, p, o, tl, s);
    if (tr == null) {
      ret += dumpStutter(lvl, tag, p, o.substates);
    } else {
      ret += dumpNonDetEval(lvl, tag, tr);
      ret += dumpNonDet(lvl, tag);
      ret += dumpTrSelect(lvl, tag, p, o.substates, tr);
    }
    return ret;
  }


  /**
   * This section will create code for a basic state
   */
  private String dumpBasicState(int lvl, int tag, Path p, Basic_State s)
  {
    return util.printlnPP(lvl, "// Basic State " + s.name.name) +
      util.printlnPP(lvl, "a.post_states[ a." +
		     dumpTables.generateSymState(p) + " ] = true;");
  }


  /**
   * This section will create code for an Or-state.
   * @exception CodeGenException ?
   */
  private String dumpOrState(int lvl, int tag, Path p, Or_State s)
       throws CodeGenException
  {
    return util.printlnPP(lvl, "// Or State " + s.name.name) +
      util.printlnPP(lvl, "if (a.pre_states[ a." +
		     dumpTables.generateSymState(p) + "]) {") +
      util.printlnPP(lvl + 1, "a.post_states[ a." +
		     dumpTables.generateSymState(p) + " ] = true;") +
      dumpTransitions(lvl + 1, tag, p, s, s.trs, s.name) +
      util.printlnPP(lvl, "}");
  }


  /**
   * This section will create the code for an And-State.
   * @exception CodeGenException If we are not able to infere
   *   the type of a sub state.
   */
  private String dumpAndState(int lvl, int tag, Path p, And_State s)
       throws CodeGenException
  {
    return util.printlnPP(lvl, "// And State " + s.name.name) +
      util.printlnPP(lvl, "a.post_states[ a." +
		     dumpTables.generateSymState(p) + " ] = true;") +
      iterateStateList(lvl, tag, p, s.substates);
  }


  /**
   * The workhorse.
   * @exception CodeGenException Passed from the guts of this code.
   * @exception IOException see above.
   */
  private void dumpAutomaton(OutputStreamWriter f, String name, Statechart s)
       throws CodeGenException, IOException
  {
    Path p = new Path(S1.state.name.name, null);

    f.write(
	    util.printlnPP(0, "/**") +
	    util.printlnPP(0, " * This code was automatically generated by codegen") +
	    util.printlnPP(0, " */") + "\n" +
	    util.printlnPP(0, "import java.util.*;") + "\n" +
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
	    util.printlnPP(2, "int trans = 0;") +
	    util.printlnPP(2, "int selected = 0;") +
	    util.printlnPP(2, "int i = 0;") +
	    util.printlnPP(2, "Random rg = new Random();") + "\n" +
	    util.printlnPP(2, "if ( a.pre_events[a." + endEventName + "]) {") +
	    util.printlnPP(3, "// Stutter in this state") +
	    util.printlnPP(3, "a.post_events[a." + endEventName + "] = true;")+
	    util.printlnPP(3, "return;") +
	    util.printlnPP(2, "}"));
    if (s.state instanceof And_State) {
      f.write(dumpAndState(2, 0, p, (And_State) s.state));
    } else if (s.state instanceof Or_State) {
      f.write(dumpOrState(2, 0, p, (Or_State) s.state));
    } else if (s.state instanceof Basic_State) {
      f.write(dumpBasicState(2, 0, p, (Basic_State) s.state));
    } else {
      throw(new CodeGenException("dumpAutomaton(): " +
				 "Cannot determine type of state."));
    }
    f.write(
	    util.printlnPP(1, "}") +
	    util.printlnPP(0, "}"));
  }


  /**
   * This method will dump the hierarchical automaton
   *
   * @exception CodeGenException Passed from the guts of this code.
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
      fw = new FileWriter(opt.path + "/" + opt.name1 + ".java");
      dumpAutomaton(fw, opt.name1, S1);
      fw.flush();
      fw.close();
      if (opt.twoStatecharts && S2 != null) {
	fw = new FileWriter(opt.path + "/" + opt.name2 + ".java");
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
