/*
 * CodeGenerator
 *
 *
 */
package CodeGen;

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
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGen.java,v 1.1 1998-11-30 17:46:50 swtech25 Exp $
 */
public class CodeGen throws CodeGenException
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
	 */
	public CodeGenerator(String path, Statechart statechart)
	{
		Generate(path, statechart);
	}

        /**
         * This method will cause the CodeGenerator to do
         * its work.  If will return after successful completion.
	 * This will activate some options. 
         */
        public CodeGenerator(String path, Statechart statechart, int options)
        {
                Generate(path, statechart);
        }
 
	/**
	 * This method is an alternative to produce code,
	 * if you do not want to create a new instance of
	 * the code generator.
	 */
	public void Generate(String path, Statechart statechart)
	{

	}

        /**
         * This method is an alternative to produce code,
         * if you do not want to create a new instance of
         * the code generator.  This time with options.
         */
        public void Generate(String path, Statechart statechart, int options)
        {
 
        }
}
