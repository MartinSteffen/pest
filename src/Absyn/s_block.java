package Absyn;



/**
 * One kind of transition action.
 * <br> Collects a sequence of actions into one block.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_block.java,v 1.3 1998-11-27 16:13:55 swtech00 Exp $
 */
public class s_block extends Action {
/**
 * Contents of the block.
 */
    public Aseq aseq;
    s_block (Aseq a) {
	aseq = a;
    };
    
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_block.java,v 1.3 1998-11-27 16:13:55 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:28  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
