/*
 * CodeGenException
 *
 */
package CodeGen;

/**
 * This is the exception caused by CodeGen, if we fail to generate code
 * for some reasons.
 * @author Marcel Kyas
 * @version $Id: CodeGenException.java,v 1.2 1998-12-07 12:12:15 swtech25 Exp $
 */
public class CodeGenException extends Exception
{
	private String notice;

	/**
	 * Constructor
	 */
	public CodeGenException()
	{
		notice = "CodeGen failed.";
	}

        /** 
         * Constructor
         */
	public CodeGenException(String s)
	{
		notice = s;
	}

	/**
	 * Overides Throwable
	 */
	public String getMessage()
	{
		return notice;
	}

        /**
         * Overides Throwable
         */
	public String toString()
	{
		return this.getMessage();
	}
}
