package Absyn;

/**
 * One kind of guard: empty guard.
 * @author Initially provided by Martin Steffen.
 * @version $Id: empty_expr.java,v 1.3 1998-11-27 16:13:13 swtech00 Exp $
 */
public class empty_expr extends Guard {
/**
 * Contents of empty guard.
 */
    public Dummy dummy;
/**
 * Constructor.
 */
    public empty_expr (Dummy d) {
	dummy = d;
    };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: empty_expr.java,v 1.3 1998-11-27 16:13:13 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:28  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
