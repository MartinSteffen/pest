package codegen;

import java.io.*;

/**
 * This is a simple container for CodeGens options.  It is maintained
 * by the class CodeGenConfig.  You may only create an object of this
 * class and store it.
 *
 * @see CodeGenConfig
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenOpt.java,v 1.10 1999-03-11 16:57:59 swtech25 Exp $
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
   * This is public due to a compiler bug in java.
   */
  boolean PrettyTraces;

  /**
   * Path, where we will store the generated code.  This must be
   * a fully qualified path name, without trailing "/".
   */
  String path;

  /**
   * Class name of automaton 1.
   */
  String name1;

  /**
   * Class name of automaton 2.
   */
  String name2;

  /**
   * The options are initialized with the following settings:
   * nondetFlavor is takeFirst, envFlavor is none; traceCodeGen,
   * verbose, and twoStatecharts are false.  Path will be set to
   * "/tmp", name[12] will be set to "Automaton[12]"
   */
  public CodeGenOpt() {
    nondetFlavor = random;
    envFlavor = none;
    traceCodeGen = true;
    verbose = false;
    twoStatecharts = false;
    PrettyTraces = true;
    path = new String(".");
    name1 = new String("Automaton1");
    name2 = new String("Automaton2");
  }
}
 
