package Absyn;

/**
 * Sequence of actions, needed as the contense of a s_block.
 * <br>
 * @author Initially provided by Martin Steffen.
 * @version $Id: Aseq.java,v 1.4 1998-11-27 16:03:06 swtech00 Exp $
 */
public class Aseq extends Absyn {
/**
 * Head of the list.
 */
    public Action head;
/**
 * Tail of the list.
 */
    public Aseq tail;
/**
 * Constructor.
 */
    public Aseq (Action h, Aseq tl) {head = h;tail = tl; };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Aseq.java,v 1.4 1998-11-27 16:03:06 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/26 16:32:10  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
