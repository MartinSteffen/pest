/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.10 1999-01-21 13:44:53 swtech25 Exp $
 * @author Marcel Kyas
 */
package codegen;

import absyn.*;
import java.io.*;
import java.util.*;

public class dumpHA
{
	private Statechart S;
	private String path;
	private dumpTables DT;

	/**
	 * This will construct a dumb instance of dumpHA.  You will
	 * need to set the state chart seperately.
	 */
	public dumpHA()
	{
		S = null;
		path = null;
	}


	/**
	 * This will construct some instance of dumpHA and will initialize
	 * its state chart with bla.
	 */
	public dumpHA(Statechart bla)
		throws CodeGenException
	{
		path = null;
		setStatechart(bla);
	}


	/**
	 * This will construct some instance of dumpHA and will initialize
	 * its path name with bla.
	 */
	public dumpHA(String bla)
	{
		S = null;
		setPathname(bla);
	}


	/**
	 * This will construct some instance of dumpHA and will initialize
	 * its state chart with bla and the path name to fasel.
	 */
	public dumpHA(Statechart bla, String fasel)
		throws CodeGenException
	{
		setStatechart(bla);
		setPathname(fasel);
	}


	/**
	 * Set statechart to bla.
	 */
	public void setStatechart(Statechart bla)
	{
			S = bla;
	}


	/**
	 * Set path to bla.
	 */
	public void setPathname(String bla)
	{
		path = bla;
	}


	/**
	 * This will generate the code for an ActionStatement.
	 *
	 * @exception CodeGenException if and only if we cannot determine
	 *   the type of statement.
	 * @exception IOException .
	 */
	private void dumpStatement(OutputStreamWriter f, Boolstmt b)
		throws CodeGenException, IOException
	{
		if (b instanceof BAss) {
			BAss c = (BAss) b;
			f.write("post_cond[" +
				dumpTables.generateSymBVar(c.ass.blhs) +
				 "] = " + dumpGuard(c.ass.brhs) + ";\n");
		} else if (b instanceof MFalse) {
			MFalse c = (MFalse) b;
			f.write("post_cond[" + 
				dumpTables.generateSymBVar(c.var) +
				"] = false;\n");
		} else if (b instanceof MTrue) {
			MTrue c = (MTrue) b;
			f.write("post_cond[" +
				dumpTables.generateSymBVar(c.var) +
				"] = true;\n");
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
	private void dumpNewEvents(OutputStreamWriter f, Action a)
		 throws CodeGenException, IOException
	{
		System.out.println("dumpNewEvent");
		if (a instanceof ActionBlock) {
			ActionBlock b = (ActionBlock) a;
			Aseq current = b.aseq;

			f.write("// Action sequence\n");
			while (current != null) {
				dumpNewEvents(f, current.head);
				current = current.tail;
			}
		} else if (a instanceof ActionEmpty) {
			f.write("// Empty action\n");
			return;
		} else if (a instanceof ActionEvt) {
			ActionEvt b = (ActionEvt) a;

			f.write("post_events[" +
				dumpTables.generateSymEvent(b.event)
				+ "] = true;\n");
		} else if (a instanceof ActionStmt) {
			ActionStmt b = (ActionStmt) a;

			dumpStatement(f, b.stmt);
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
		System.out.println("dumpGuard");
		if (g instanceof GuardBVar) {
			GuardBVar h = (GuardBVar) g;
			return "pre_cond[" +
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
			String s = dumpTables.generateSymState(p);

			switch (h.cpath.pathop) {
			case Comppath.IN:
				return "pre_states[" + s + "]";
			case Comppath.ENTERED:
				return "( !his_states[" + s +
					"] && pre_states[" + s + "])";
			case Comppath.EXITED:
				return "( his_states[" + s +
					"] && !pre_states[" + s + "])";
			default:
				throw(new CodeGenException(
				      "Unsupported path operation"
				));
			}
		} else if (g instanceof GuardEmpty) {
			return "true";
		} else if (g instanceof GuardEvent) {
			GuardEvent h = (GuardEvent) g;

			return "pre_event[" +
				dumpTables.generateSymEvent(h.event) +
				"]";
		} else if (g instanceof GuardNeg) {
			GuardNeg h = (GuardNeg) g;
			return "!(" + dumpGuard(h.guard) +")";
		} else if (g instanceof GuardUndet) {
			throw(new CodeGenException(
				"Undetermined Guards are not supported"));
		} else {
			throw(new CodeGenException(
				"Cannot determine type of guard."
			));
		}
	}


	/**
	 * This method will dump code for a new target.
	 *
	 * @exception CodeGenException .
	 * @exception IOException .
	 */
	private void dumpNextState(OutputStreamWriter f,
				   Path p,
				   TrList tl,
				   TrAnchor t)
		throws CodeGenException, IOException
	{
		Path q;

		System.out.println("dumpNextState");
		if (t instanceof Conname) {
			//dumpTransitions(f, tl, t);
		} else if (t instanceof Statename) {
			Statename s = (Statename) t;

			q = p.append(s.name);
			f.write("post_states[" +
				dumpTables.generateSymState(q) +
				"] = true;\n");
			//This needs a fix.
			State v = null;
			if (v instanceof Or_State) {
				Or_State u = (Or_State) v;
				f.write("post_states[" +
					dumpTables.generateSymState(
						q.append(u.defaults.head.name)
					) + "] = true;\n");
			}
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
	private void dumpTransitions(OutputStreamWriter f, Path p, TrList tl,
				     TrAnchor s)
		throws CodeGenException, IOException
	{
		TrList current = tl;

		System.out.println("dumpTransition");
		while(current != null) {
			if (current.head.source == s) {
				f.write("if (" +
					dumpGuard(current.head.label.guard)
					+ ") {\n");
				dumpNewEvents(f, current.head.label.action);
				dumpNextState(f, p, tl, current.head.target);
				f.write("}\n");
			}
		}
	}


	/**
	 * This section will create code for a basic state
	 * @exception CodeGenException If no such state.
	 * @exception IOException self-explanatory.
	 */
	private void dumpBasicState(OutputStreamWriter f,
				    Path p,
				    Basic_State s)
		throws IOException, CodeGenException
	{
		f.write("// Basic State " + s.name.name + "\n");
		// No code for a basic state.  They are already handled
		// in And and Or states.
	}


	/**
	 * This section will create code for an Or-state.
	 * @exception CodeGenException ?
	 * @exception IOException ?
	 */
	private void dumpOrState(OutputStreamWriter f, Path p, Or_State s)
		throws CodeGenException, IOException
	{
		StateList current = s.substates;
		TrList currTr = s.trs;
		Path q;

		System.out.println("dumpOrState");
		f.write("// Or State " + s.name.name + "\n");
		while (current != null) {
			q = p.append(current.head.name.name);
			f.write("if (pre_states[" +
				dumpTables.generateSymState(q) +
				"]) {\n");
			//dumpTransitions(f, s.trs, current.head.name);
			f.write("}\n");
			current = current.tail;
		}
		iterateStateList(f, p, s.substates);
	}


	/**
	 * This section will create the code for an And-State.
	 * @exception CodeGenException If we are not able to infere
	 *   the type of a sub state.
	 * @exception IOException ?
	 */
	private void dumpAndState(OutputStreamWriter f, Path p, And_State s)
		throws IOException, CodeGenException
	{
		System.out.println("dumpAndState");
		f.write("// And State " + s.name.name + "\n");
		iterateStateList(f, p, s.substates);
	}


	/**
	 * This section of code will iterate a list of
	 * states.
	 * @exception CodeGenException If we are not able to infere
	 *   the type of a sub state.
	 * @exception IOException ?
	 */
	private void iterateStateList(OutputStreamWriter f,
				      Path p,
				      StateList sl)
		throws CodeGenException, IOException
	{
		StateList current = sl;
		Path q;

		System.out.println("Iterating state list");
		while (current != null) {
			q = p.append(current.head.name.name);
			if (current.head instanceof And_State) {
				dumpAndState(f, q, (And_State) current.head);
			} else if (current.head instanceof Or_State) {
				dumpOrState(f, q, (Or_State) current.head);
			} else if (current.head instanceof Basic_State) {
				dumpBasicState(f, q,
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
		Path p = new Path(S.state.name.name, null);

		System.out.println("Dumping Automaton");
		f.write("/**\n * This code was automatically generated " +
			"by codegen\n */");
		f.write("\npublic class Automaton extends SymbolTable {\n");
		f.write("\npublic void step() {\n");
		if (S.state instanceof And_State) {
			dumpAndState(f, p, (And_State) S.state);
		} else if (S.state instanceof Or_State) {
			dumpOrState(f, p, (Or_State) S.state);
		} else if (S.state instanceof Basic_State) {
			dumpBasicState(f, p, (Basic_State) S.state);
		} else {
			throw(new CodeGenException(
				"Cannot determine type of state."
			));
		}
		f.write("\n}\n}\n");
	}


	/**
	 * This method will dump the hierarchical automaton
	 *
	 * @exception CodeGenException
	 */
	void dump() throws CodeGenException
	{
		FileWriter fw;

		System.out.println("Dumping chart to " + path);
		DT = new dumpTables(S);
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
