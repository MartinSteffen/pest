package Absyn;

/**
 * One kind of guard: guard negation.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_negguard.java,v 1.3 1998-11-27 16:20:09 swtech00 Exp $
 */
public class s_negguard extends Guard {
/**
 * Guard to negate.
 */
    public Guard guard;
/**
 * Constructor.
 */
    public s_negguard (Guard g) {
	guard = g;
    };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_negguard.java,v 1.3 1998-11-27 16:20:09 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:34  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
