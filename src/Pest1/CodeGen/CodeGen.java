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
 * We expect path to be a valid path name, that is, it is
 * a directory, indeed, and the directory is writable.
 *
 * Statechart is a syntactically correct and valid statechart.
 *
 * If an error occures, we will throw a CodeGenException.
 *
 * Prerequisites:  We expect the Statechart to be a complete,
 *     executable and correct state chart.  We will not announce
 *     an error, but instead create incorrect code (as far as it
 *     is possible to talk about incorrect code).  The pathname
 *     is assumed to be a fully qualified path to an existing
 *     directory without trailing "/".
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGen.java,v 1.8 1999-01-04 14:19:54 swtech25 Exp $
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
	 * its work.  If will return after successful completion.
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
         * its work.  If will return after successful completion.
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
	 *
	 * @exception CodeGenException if code generation fails.
         */
        public void Generate(String path, Statechart statechart, int options)
		throws CodeGenException
        {
 
        }
}
