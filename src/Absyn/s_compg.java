package Absyn;


/**
 * One kind of guard: composition of guards.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_compg.java,v 1.3 1998-11-27 16:15:45 swtech00 Exp $
 */
public class s_compg extends Guard {
/**
 * Contents of compound guard.
 */
    public Compguard cguard;
/**
 * Constructor.
 */
    public s_compg(Compguard g) {
	cguard = g;
    };
};


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_compg.java,v 1.3 1998-11-27 16:15:45 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:30  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
