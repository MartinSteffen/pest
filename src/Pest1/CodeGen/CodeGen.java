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
 *   <LI>Need to fix code for non-determinism.
 *   </UL>
 * <DT><STRONG>Known Bugs</STRONG>
 *   <UL>
 *     <LI>Code emission for transition hangs PEST.
 *     <LI>Perhaps we need to check the identifiers and think of
 *       a way to convert identifiers into valid Java identifiers.
 *       Question is:  Do we want to restrict the choice of valid
 *       Identifiers?  This would be simpler, since it would avoid
 *       name clashes during conversion.
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
 *   <LI>All identifiers should not contain ".".  Perhaps we might fix
 *     this problem someday.  Currently we expect all identifiers to be
 *     valid Java identifiers.
 *   <LI>The pathname is assumed to be a fully qualified path to an
 *     existing directory without trailing "/".
 *   </UL>
 * <DT><STRONG>Assertions:</STRONG>
 *   <UL>
 *   <LI>none.
 *   </UL>
 * </DL>
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGen.java,v 1.14 1999-01-21 13:46:24 swtech25 Exp $
 */
public class CodeGen
{
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
