/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.15 1999-01-25 14:53:24 swtech25 Exp $
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
	private Statechart S;
	private String path;
	private dumpTables DT;
	private Hashtable transitions = new Hashtable(hashTabSize, loadFactor);

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
	 * This method will write a line to a file and pretty-print
	 * it.
	 *
	 * @exception IOException RTFM
	 */
	private void printlnPP(OutputStreamWriter f, int lvl, String s)
		throws IOException
	{
		int i;

		for (i = lvl; i > 0; --i) {
			f.write(indentLevel);
		}
		f.write(s);
		f.write("\n");
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
			return "post_cond[" +
				dumpTables.generateSymBVar(c.ass.blhs) +
				 "] = " + dumpGuard(c.ass.brhs) + ";";
		} else if (b instanceof MFalse) {
			MFalse c = (MFalse) b;
			return "post_cond[" + 
				dumpTables.generateSymBVar(c.var) +
				"] = false;";
		} else if (b instanceof MTrue) {
			MTrue c = (MTrue) b;
			return "post_cond[" +
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
			printlnPP(f, lvl, "// Empty action");
			return;
		} else if (a instanceof ActionEvt) {
			ActionEvt b = (ActionEvt) a;

			printlnPP(f, lvl, "post_events[" +
				dumpTables.generateSymEvent(b.event)
				+ "] = true;");
		} else if (a instanceof ActionStmt) {
			ActionStmt b = (ActionStmt) a;

			printlnPP(f, lvl, dumpStatement(b.stmt));
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

			return "pre_events[" +
				dumpTables.generateSymEvent(h.event) +
				"]";
		} else if (g instanceof GuardNeg) {
			GuardNeg h = (GuardNeg) g;
			return "!(" + dumpGuard(h.guard) +")";
		} else if (g instanceof GuardUndet) {
			return "true" ;
			/* Emmit a warning here.  Talk with the gui people.
			throw(new CodeGenException(
				"Undetermined Guards are not supported"));
			*/
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
				   int lvl,
				   Path p,
				   TrList tl,
				   TrAnchor t)
		throws CodeGenException, IOException
	{
		Path q;

		if (t instanceof Conname) {
			dumpTransitions(f, lvl, p, tl, t);
		} else if (t instanceof Statename) {
			Statename s = (Statename) t;

			q = p.append(s.name);
			printlnPP(f, lvl, "post_states[" +
				dumpTables.generateSymState(q) +
				"] = true;");
			// TODO
			State v = null;
			if (v instanceof Or_State) {
				Or_State u = (Or_State) v;
				printlnPP(f, lvl, "post_states[" +
					dumpTables.generateSymState(
						q.append(u.defaults.head.name)
					) + "] = true;");
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
	private void dumpTransitions(OutputStreamWriter f,
				     int lvl,
				     Path p,
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
					// TODO: Handle non-determinism here
					// with care.
					// let the generated code
					// count the eligible transitions
					// first and choose one?
					printlnPP(f, lvl, "if (" +
						dumpGuard(l.guard)
						+ ") {");
					dumpNewEvents(f, lvl + 1, l.action);
					dumpNextState(f, lvl + 1, p, tl,
						      current.head.target);
					printlnPP(f, lvl, "}");
				}
			}
			current = current.tail;
		}
		if (trs == 0) {
			printlnPP(f, lvl, "// no outgoing transitions found");
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
		printlnPP(f, lvl, "// Basic State " + s.name.name);
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

		printlnPP(f, lvl, "// Or State " + s.name.name);
		while (current != null) {
			q = p.append(current.head.name.name);
			printlnPP(f, lvl, "if (pre_states[" +
				dumpTables.generateSymState(q) +
				"]) {");
			dumpTransitions(f, lvl + 1, p, s.trs,
					current.head.name);
			printlnPP(f, lvl, "} else");
			current = current.tail;
		}
		printlnPP(f, lvl, "{");
		iterateStateList(f, lvl + 1, p, s.substates);
		printlnPP(f, lvl, "}");
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
		printlnPP(f, lvl, "// And State " + s.name.name);
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
		Path p = new Path(S.state.name.name, null);

		printlnPP(f, 0, "/**");
		printlnPP(f, 0, " * This code was automatically generated by codegen");
		printlnPP(f, 0, " */");
		printlnPP(f, 0, "public class Automaton extends SymbolTable {");
		printlnPP(f, 0, "");
		printlnPP(f, 1, "/**");
		printlnPP(f, 1, " * This method will simulate a single step");
		printlnPP(f, 1, " * of the hierarchical automaton");
		printlnPP(f, 1, " */");
		printlnPP(f, 1, "public void step() {");
		printlnPP(f, 1, "// Handle endEvent first");
		printlnPP(f, 2, "if ( pre_events[" +
			dumpTables.generateSymEvent(new SEvent(
				dumpTables.endEvent
			)) +
			"]) {");
		printlnPP(f, 3, "// Stutter in this state");
		printlnPP(f, 3, "post_events[" + dumpTables.generateSymEvent(
			  new SEvent(dumpTables.endEvent)) + "] = true;");
		printlnPP(f, 3, "return;");
		printlnPP(f, 2, "}");
		if (S.state instanceof And_State) {
			dumpAndState(f, 2, p, (And_State) S.state);
		} else if (S.state instanceof Or_State) {
			dumpOrState(f, 2, p, (Or_State) S.state);
		} else if (S.state instanceof Basic_State) {
			dumpBasicState(f, 2, p, (Basic_State) S.state);
		} else {
			throw(new CodeGenException(
				"Cannot determine type of state."
			));
		}
		printlnPP(f, 1, "}");
		printlnPP(f, 0, "}");
	}


	/**
	 * This method will dump the hierarchical automaton
	 *
	 * @exception CodeGenException
	 */
	void dump() throws CodeGenException
	{
		FileWriter fw;

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
