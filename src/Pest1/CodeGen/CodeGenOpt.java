/**
 * Container for Options to codegen.
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenOpt.java,v 1.2 1999-02-03 20:03:22 swtech25 Exp $
 */
package codegen;

public class CodeGenOpt {
	/**
	 * Take the first Transition enabled.
	 */
	public final static int takeFirst = 0;

	/**
	 * Try to cycle through enabled transitions.
	 */
	public final static int roundRobin = 1;

	/**
	 * Choose an enabled transition randomly.
	 */
	public final static int random = 2;

	/**
	 * Do not generate code for an environment.
	 */
	public final static int none = 0;

	/**
	 * Generate the code for a simple environment.
	 * This is an environment without user interaction.  It will
	 * treat the statechart as a closed system.
	 */
	public final static int simple = 1;

	/**
	 * Current flavor of non-determinism handling
	 */
	public int nondetFlavor;

	/**
	 * Current flavor for environment.
	 */
	public int envFlavor;

	/**
	 * Do we want to generate a generation trace?
	 */
	public boolean traceCodeGen;

	/**
	 * Do we want to generate verbose output.
	 */
	public boolean verbose;

	/**
	 * Standard constructer.  We use the following defaults:
	 * nondetFlavor = takeFirst,
	 * envFlavor = none,
	 * traceCodeGen = false,
	 * verbose = false.
	 */
	public CodeGenOpt() {
		nondetFlavor = takeFirst;
		envFlavor = none;
		traceCodeGen = false;
		verbose = false;
	}
}
 
