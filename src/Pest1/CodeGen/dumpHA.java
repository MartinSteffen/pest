/**
 * dumpHA
 *
 * This class is responsible for generating our hierarchical
 * automaton.
 *
 * @version $Id: dumpHA.java,v 1.2 1999-01-04 12:08:10 swtech25 Exp $
 * @author Marcel Kyas
 */
package codegen;

import absyn.*;

public class dumpHA
{
	private Statechart S;
	private String path;

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
	 * In this section we will generate a symbol table.
	 *
	 * @exception CodeGenException
	 */
	private void dumpSymbolTable() throws CodeGenException
	{
		
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

	}
}
