package Absyn;

/**
 * Composition of two guards.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Compguard.java,v 1.4 1998-11-27 16:07:02 swtech00 Exp $
 */
public class Compguard extends Absyn {
/**
 * Biary operation.
 */
    public Op    eop;
/**
 * Left-hand-side.
 */
    public Guard elhs;
/**
 * Right-hand-side.
 */
    public Guard erhs;
/**
 * Constructor.
 */
    public Compguard (Op op, Guard g1, Guard g2) {
	eop = op;
	elhs = g1;
	erhs = g2;
    };
};


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Compguard.java,v 1.4 1998-11-27 16:07:02 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/26 16:32:13  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
