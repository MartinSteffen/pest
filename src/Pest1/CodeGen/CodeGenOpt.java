/**
 * Container for Options to codegen.
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenOpt.java,v 1.4 1999-02-12 15:13:07 swtech25 Exp $
 */
package codegen;

import java.io.*;

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
 
