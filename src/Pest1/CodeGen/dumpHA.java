/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.7 1999-01-18 12:33:01 swtech25 Exp $
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
	{
		setStatechart(bla);
		setPathname(fasel);
	}


	/**
	 * Set statechart to bla.
	 */
	public void setStatechart(Statechart bla)
	{
		try {
			S = (Statechart) bla.clone();
		}
		catch (CloneNotSupportedException e) {
			// currently nothing.
		}
	}


	/**
	 * Set path to bla.
	 */
	public void setPathname(String bla)
	{
		path = bla;
	}


	/**
	 * In this section we will generate the new
	 * events.
	 *
	 * @exception CodeGenException ?
	 */
	private void dumpNewEvents(OutputStreamWriter f, Action a)
		 throws CodeGenException, IOException
	{
		// currently nothing, todo
	}


	/**
	 * This method will create the boolean statement for a guard
	 */
	private String dumpGuard(Guard g)
		throws CodeGenException
	{
		if (g instanceof GuardBVar) {
			GuardBVar h = (GuardBVar) g;
			Integer i = (Integer) DT.cond_sym.get(h.bvar.var);
			return "pre_cond[" + i + "]";
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
				throw(new CodeGenException("Unsupported path operation"));
			}
		} else if (g instanceof GuardEmpty) {
			return "true";
		} else if (g instanceof GuardEvent) {
			GuardEvent h = (GuardEvent) g;
			Integer i = (Integer)
				 DT.events_sym.get(h.event.name);
			return "pre_event[" + i + "]";
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
	 * This method will dump the necessary code for
	 * transitions.  s denotes the source.
	 */
	private void dumpTransitions(OutputStreamWriter f, TrList tl,
				     TrAnchor s)
		throws CodeGenException, IOException
	{
		TrList current = tl;

		while(current != null) {
			if (current.head.source == s) {
				f.write("if (" +
					dumpGuard(current.head.label.guard)
					+ ") {");
				dumpNewEvents(f, current.head.label.action);
				// Dump next state.
				f.write("}");
			}
		}
	}


	/**
	 * This section will create the code for a connector.
	 * a connector c must be the source of this code.
	 */
	private void dumpConnector(OutputStreamWriter f, Connector c)
		throws CodeGenException, IOException
	{

	}


	/**
	 * This section will create code for a basic state
	 * @exception CodeGenException If no such state.
	 */
	private void dumpBasicState(OutputStreamWriter f, Basic_State s)
		throws CodeGenException
	{
		// No code for a basic state.  They are already handled
		// in And and Or states.
	}


	/**
	 * This section will create code for an Or-state.
	 * @exception CodeGenException ?
	 * @exception IOException ?
	 */
	private void dumpOrState(OutputStreamWriter f, Or_State s)
		throws CodeGenException, IOException
	{
		StateList current = s.substates;
		TrList currTr = s.trs;

		while (current != null) {
			f.write("if (pre_states[" +
				current.head +
				"]) {");
			dumpTransitions(f, s.trs, current.head.name);
			f.write("}");
			current = current.tail;
		}
		iterateStateList(f, s.substates);
	}


	/**
	 * This section will create the code for an And-State.
	 * @exception CodeGenException If we are not able to infere
	 *   the type of a sub state.
	 * @exception IOException ?
	 */
	private void dumpAndState(OutputStreamWriter f, And_State s)
		throws IOException, CodeGenException
	{
		iterateStateList(f, s.substates);
	}


	/**
	 * This section of code will iterate a list of
	 * states.
	 * @exception CodeGenException If we are not able to infere
	 *   the type of a sub state.
	 * @exception IOException ?
	 */
	private void iterateStateList(OutputStreamWriter f, StateList sl)
		throws CodeGenException, IOException
	{
		StateList current = sl;

		while (current != null) {
			if (current.head instanceof And_State) {
				dumpAndState(f, (And_State) current.head);
			} else if (current.head instanceof Or_State) {
				dumpOrState(f, (Or_State) current.head);
			} else if (current.head instanceof Basic_State) {
				dumpBasicState(f, (Basic_State) current.head);
			} else {
				throw(new CodeGenException(
					"Cannot determine type of state."
				));
			}
			current = current.tail;
		}
	}


	/**
	 * This method will dump the hierarchical automaton
	 *
	 * @exception CodeGenException
	 */
	void dump() throws CodeGenException
	{
		Runtime rt = Runtime.getRuntime(); // Horror, horror, eh?
		FileWriter fw;

		DT = new dumpTables(S);
		try {
			fw = new FileWriter(path + "/SymbolTable.java");
			DT.dump(fw);
			fw.flush();
			fw.close();
			rt.gc();
		}
		catch (IOException e) {
			throw(new CodeGenException(e.toString()));
		}
	}
}



