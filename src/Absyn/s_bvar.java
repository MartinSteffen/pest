package Absyn;

/**
 * One kind of guard: boolean variable.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_bvar.java,v 1.3 1998-11-27 16:15:08 swtech00 Exp $
 */
public class s_bvar extends Guard {
/**
 * Contents of variable.
 */
    public Bvar bvar;
/**
 * Constructor.
 */
    public s_bvar(Bvar v) {
	bvar = v;
    };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_bvar.java,v 1.3 1998-11-27 16:15:08 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:30  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
