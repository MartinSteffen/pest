/*
 * CodeGenException
 *
 */
package codegen;

/**
 * This is the exception caused by CodeGen, if we fail to generate code
 * for some reasons.
 * @author Marcel Kyas
 * @version $Id: CodeGenException.java,v 1.3 1998-12-15 17:51:43 swtech00 Exp $
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
