/*
 * CodeGenerator
 */
package codegen;

import absyn.*;

/**
 * This class provides a simple interface to other parts
 * of codegen.
 *
 * If an error occures, we will throw a CodeGenException.
 *
 * <DL COMPACT>
 * <DT><STRONG>Status</STRONG>
 * <UL>
 * <LI>First beta release.  Have fun testing.
 * <LI>Code for connectors is not generated.
 * </UL>
 * <DT><STRONG>To Do</STRONG>
 *   <UL>
 *   <LI> More testing.
 *   <LI> Fix a problem with connectors.
 *   </UL>
 * <DT><STRONG>Known Bugs</STRONG>
 *   <UL>
 *   <LI>No Bugs known.  But see Prerequisites.
 *   </UL>
 * <A HREF="mailto:swtech25@informatik.uni-kiel.de">swtech25</A>
 * <DT><STRONG>Prerequisites:</STRONG>
 *   <UL>
 *   <LI>We expect the Statechart to be a complete,  executable and
 *     correct state chart.  We will not announce an error, but instead create
 *     incorrect code (as far as it is possible to talk about incorrect code).
 *   <LI>All identifiers should not contain ".". We expect all identifiers
 *      to be valid Java identifiers.  It is wise to use no underscores "_"
 *      in your state chart, because this may lead to name clashes, which
 *      will make the compiler barf.
 *   </UL>
 * <DT><STRONG>Assertions:</STRONG>
 *   <UL>
 *   <LI>none.
 *   </UL>
 * <DT><STRONG>Changes:</STRONG>
 * <UL>
 * <LI> The interface was changed to cope with the composition of two
 *   statecharts.  We will allow two filenames for the automata and
 *   one path name, where codegen will store its result.
 * </UL>
 * </DL>
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGen.java,v 1.20 1999-03-11 15:41:54 swtech25 Exp $
 */
public class CodeGen
{
  /**
   * This constructor will cause codegen to produce code for
   * statechart s with options o.
   *
   * @exception CodeGenException if code generation fails.
   */
  public CodeGen(Statechart s, CodeGenOpt o)
       throws CodeGenException
  {
    this(s, null, o);
  }


  /**
   * This constructor will generate code for statecharts s1
   * and s2 with the default options.  In this scenario s1 and
   * s2 will be composed into a single, closed system.
   *
   * @exception CodeGenException if code generation fails.
   */
  public CodeGen(Statechart s1, Statechart s2)
       throws CodeGenException
  {
    this(s1, s2, new CodeGenOpt());
  }


  /**
   * This simple constructor will generate code for a statechart
   * s using default options.
   *
   * @exception CodeGenException if code generation fails.
   */
  public CodeGen(Statechart s)
       throws CodeGenException
  {
    this(s, null, new CodeGenOpt());
  }


  /**
   * This constructor will generate code for statecharts s1
   * and s2 with options o.  In this scenario s1 and
   * s2 will be composed into a single, closed system.
   *
   * @exception CodeGenException if code generation fails.
   */
  public CodeGen(Statechart s1, Statechart s2, CodeGenOpt o)
       throws CodeGenException
  {
    dumpHA h = new dumpHA(s1, s2, o);
    h.dump();
  }
}
