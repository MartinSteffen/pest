/*
 * CodeGenerator
 *
 *
 */
package codegen;

import absyn.*;

/**
 *
 * This class provides a simple interface to other parts
 * of pest.
 * path is a path name to a directory, where CodeGen will
 * store its files.
 *
 * If an error occures, we will throw a CodeGenException.
 *
 * <DL COMPACT>
 * <DT><STRONG>Status</STRONG>
 * Most of the code is implemented.  Non-determinism is not handled
 * correctly yet.
 * <DT><STRONG>To Do</STRONG>
 *   <UL>
 *   <LI>Need to fix code for non-determinism.
 *   <LI>Default connectors currently do not work.
 *   </UL>
 * <DT><STRONG>Known Bugs</STRONG>
 *   <UL>
 *   <LI>No Bugs nown.  But see Prerequisites.
 *   </UL>
 * <A HREF="mailto:swtech25@informatik.uni-kiel.de">swtech25</A>
 * </DL>
 *
 * <DL COMPACT>
 * <DT><STRONG>Prerequisites:</STRONG>
 *   <UL>
 *   <LI>We expect the Statechart to be a complete,  executable and
 *     correct state chart.  We will not announce an error, but instead create
 *     incorrect code (as far as it is possible to talk about incorrect code).
 *   <LI>All identifiers should not contain ".".  Perhaps we might fix
 *     this problem someday.  Currently we expect all identifiers to be
 *     valid Java identifiers.
 *   <LI>The pathname is assumed to be a fully qualified path to an
 *     existing directory without trailing "/".
 *   </UL>
 * <DT><STRONG>Assertions:</STRONG>
 *   <UL>
 *   <LI>none.
 *   </UL>
 * </DL>
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGen.java,v 1.17 1999-02-12 15:13:06 swtech25 Exp $
 */
public class CodeGen
{
	/**
	 * This method will cause the CodeGenerator to do
	 * its work.  It will return after successful completion.
	 *
	 * @exception CodeGenException if code generation fails.
	 */
	public CodeGen(Statechart s, CodeGenOpt o)
		throws CodeGenException
	{
		this(s, null, o);
	}

        /**
         * This method will cause the CodeGenerator to do
         * its work.  It will return after successful completion.
	 * This will activate some options. 
	 *
	 * @exception CodeGenException if code generation fails.
         */
        public CodeGen(Statechart s1, Statechart s2)
		throws CodeGenException
        {
                this(s1, s2, new CodeGenOpt());
        }
 
	/**
	 * This method is an alternative to produce code,
	 * if you do not want to create a new instance of
	 * the code generator.
	 *
	 * @exception CodeGenException if code generation fails.
	 */
	public CodeGen(Statechart s)
		throws CodeGenException
	{
		this(s, null, new CodeGenOpt());
	}

        /**
         * This method is an alternative to produce code,
         * if you do not want to create a new instance of
         * the code generator.  This time with options.
	 * Options are currently ignored.
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
