package codegen;

/**
 * This class defines an auxilluary class for creating
 * code for transitions.
 *
 * @author Marcel Kyas
 * @version $Id: CodeGenTrans.java,v 1.1 1999-02-03 20:03:23 swtech25 Exp $
 */
public class CodeGenTrans {
	/**
	 * Number of transition.
	 */
	public int number;

	/**
	 * Name of the source state.
	 */
	public String source;

	/**
	 * Code of the guard.
	 */
	public String guard;

	/**
	 * Code for the action.
	 */
	public String action;

	/**
	 * Name of the target.
	 */
	public String target;

	/**
	 * Next transition
	 */
	public CodeGenTrans next;

}

