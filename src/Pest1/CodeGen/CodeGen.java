/*
 * CodeGenerator
 *
 *
 */
package codegen;

import absyn.*;

/**
 *
 * This class provides a simple interface to other parts
 * of pest.
 * path is a path name to a directory, where CodeGen will
 * store its files.
 *
 * If an error occures, we will throw a CodeGenException.
 *
 * <DL COMPACT>
 * <DT><STRONG>Status</STRONG>
 * Most of the code is implemented.  Non-determinism is not handled
 * correctly yet.
 * <DT><STRONG>To Do</STRONG>
 *   <UL>
 *   <LI>Need to debug the code properly.
 *   <LI>Need to fix code for non-determinism.
 *   <LI>Symbolic names for conditions and events.
 *   <LI>We currently do not produce traces.
 * <DT><STRONG>Known Bugs</STRONG>
 *   <UL>
 *     <LI>Code emission for transition hangs PEST.
 *     <LI>Lookup for symbols does not work correctly.
 *   </UL>
 * <A HREF="mailto:swtech25@informatik.uni-kiel.de">swtech25</A>
 * </DL>
 *
 * <DL COMPACT>
 * <DT><STRONG>Prerequisites:</STRONG>
 *   <UL>
 *   <LI>We expect the Statechart to be a complete,  executable and
 *     correct state chart.  We will not announce an error, but instead create
 *     incorrect code (as far as it is possible to talk about incorrect code).
 *   <LI>  The pathname is assumed to be a fully qualified path to an
 *     existing directory without trailing "/".
 *   </UL>
 * <DT><STRONG>Assertions:</STRONG>
 *   <UL>
 *   <LI>none.
 *   </UL>
 * </DL>
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGen.java,v 1.13 1999-01-20 18:47:25 swtech25 Exp $
 */
public class CodeGen
{
	/**
	 * This is our reserved event keyword.  If it occures,
	 * the generated code will clean up and exit.
	 */
	final String endEvent = "$$stop$$";

	/**
	 * This is the default option setting
	 */
	final int defaultOptions = 0;

	/**
	 * This method will cause the CodeGenerator to do
	 * its work.  It will return after successful completion.
	 *
	 * @exception CodeGenException if code generation fails.
	 */
	public CodeGen(String path, Statechart statechart)
		throws CodeGenException
	{
		Generate(path, statechart);
	}

        /**
         * This method will cause the CodeGenerator to do
         * its work.  It will return after successful completion.
	 * This will activate some options. 
	 *
	 * @exception CodeGenException if code generation fails.
         */
        public CodeGen(String path, Statechart statechart, int options)
		throws CodeGenException
        {
                Generate(path, statechart);
        }
 
	/**
	 * This method is an alternative to produce code,
	 * if you do not want to create a new instance of
	 * the code generator.
	 *
	 * @exception CodeGenException if code generation fails.
	 */
	public void Generate(String path, Statechart statechart)
		throws CodeGenException
	{
		dumpHA h = new dumpHA(statechart, path);
		h.dump();
	}

        /**
         * This method is an alternative to produce code,
         * if you do not want to create a new instance of
         * the code generator.  This time with options.
	 * Options are currently ignored.
	 *
	 * @exception CodeGenException if code generation fails.
         */
        public void Generate(String path, Statechart statechart, int options)
		throws CodeGenException
        {
		Generate(path, statechart);
        }
}
