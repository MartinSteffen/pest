package codegen;

import java.io.*;

/**
 * This is a simple container for CodeGens options.  It is maintained
 * by the class CodeGenConfig.  You may only create an object of this
 * class and store it.
 *
 * @see CodeGenConfig
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenOpt.java,v 1.5 1999-02-17 11:59:13 swtech25 Exp $
 */
public class CodeGenOpt implements Serializable {

  /**
   * Take the first Transition enabled.
   */
  final static int takeFirst = 0;

  /**
   * Try to cycle through enabled transitions.
   */
  final static int roundRobin = 1;

  /**
   * Choose an enabled transition randomly.
   */
  final static int random = 2;

  /**
   * Do not generate code for an environment.
   */
  final static int none = 0;

  /**
   * Generate the code for a simple environment.
   * This is an environment without user interaction.  It will
   * treat the statechart as a closed system.
   */
  final static int simple = 1;

  /**
   * Current flavor of non-determinism handling
   */
  int nondetFlavor;

  /**
   * Current flavor for environment.
   */
  int envFlavor;

  /**
   * Do we want to generate a generation trace?
   */
  boolean traceCodeGen;

  /**
   * Do we want to generate verbose output.
   */
  boolean verbose;

  /**
   * Two statecharts?
   */
  boolean twoStatecharts;

  /**
   * Path.
   */
  String path;

  /**
   * The options are initialized with the following settings:
   * nondetFlavor is takeFirst, envFlavor is none; traceCodeGen,
   * verbose, and twoStatecharts are false.  Path will be set to
   * "/tmp".
   */
  public CodeGenOpt() {
    nondetFlavor = takeFirst;
    envFlavor = none;
    traceCodeGen = false;
    verbose = false;
    twoStatecharts = false;
    path = new String("/tmp");
  }
}
 
