/*
 * CodeGenException
 *
 */
package CodeGen;

/**
 * This is the exception caused by CodeGen, if we fail to generate code
 * for some reasons.
 * @author Marcel Kyas
 * @version $Id: CodeGenException.java,v 1.1 1998-11-30 17:46:51 swtech25 Exp $
 */
public class CodeGenException extends Exception
(
	private int error;
	private final String[] messages = {
		"CodeGen failed for unknown reasons.\n"
	}
	private String notice;

	/**
	 * Constructor
	 */
	public CodeGenException()
	{
		error = 0;
	}

        /** 
         * Constructor
         */
	public CodeGenException(String s)
	{
		notice = s.clone();
	}

        /** 
         * Constructor
         */
	public CodeGenException(int e)
	{
		error = e;
	}

        /** 
         * Constructor
         */
	public CodeGenException(String s, int e)
	{
		notice = s.clone();
		error = e;
	}

	/**
	 * Overides Throwable
	 */
	public String getMessage()
	{
		return "CodeGenException "+
			error +
			": " +
			messages[error] +
			notice;
	}

        /**
         * Overides Throwable
         */
	public String toString()
	{
		return this.getMessage();
	}
)
