/**
 * Container for Options to codegen.
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenOpt.java,v 1.1 1999-01-25 15:10:16 swtech25 Exp $
 */
package codegen;

public class CodeGenOpt {
	/**
	 * Flavor for nondeterminism.
	 */
	public final static int takeFirst = 0;
	public final static int roundRobin = 1;
	public final static int random = 2;

	/**
	 * Flavor for environment.
	 */
	public final static int none = 0;
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
 