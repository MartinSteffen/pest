/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.6 1999-01-11 15:10:59 swtech25 Exp $
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
	 * This method will create code for a compound guard.
	 */
	private String dumpCompguard(Compguard g)
	{
		return ""; // todo
	}


	/**
	 * This method will create the boolean statement for a guard
	 */
	private String dumpGuard(Guard g)
		throws CodeGenException
	{
		if (g instanceof GuardBVar) {
			// todo
		} else if (g instanceof GuardCompg) {
			// todo
		} else if (g instanceof GuardCompp) {
			// todo
			// check what this is
		} else if (g instanceof GuardEmpty) {
			return "true";
		} else if (g instanceof GuardEvent) {
			// todo
		} else if (g instanceof GuardNeg) {
			GuardNeg h = (GuardNeg) g;
			return "!(" + dumpGuard(h.guard) +")";
		} else if (g instanceof GuardUndet) {
			throw(new CodeGenException(
				"Undetermined Guards not supported"));
		} else {
			throw(new CodeGenException(
				"Cannot determine type of guard."
			));
		}
		return ""; // not reached.
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



