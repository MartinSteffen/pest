package Absyn;



/**
 * One kind of transition action.
 * <br> Collects a sequence of actions into one block.
 * @author Initially provided by Martin Steffen.
 * @version $Id: ActionBlock.java,v 1.1 1998-12-01 17:52:52 swtech00 Exp $
 */
public class ActionBlock extends Action {
/**
 * Contents of the block.
 */
    public Aseq aseq;
    ActionBlock (Aseq a) {
	aseq = a;
    };
    
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ActionBlock.java,v 1.1 1998-12-01 17:52:52 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/27 16:13:55  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:28  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
