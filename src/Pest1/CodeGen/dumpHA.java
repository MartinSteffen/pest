/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.5 1999-01-08 18:49:39 swtech25 Exp $
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
	 * @exception CodeGenException
	 */
	private void dumpNewEvents() throws CodeGenException
	{

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



