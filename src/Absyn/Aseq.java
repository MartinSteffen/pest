package Absyn;

import java.io.Serializable;

/**
 * Sequence of actions, needed as the contense of a s_block.
 * <br>
 * @author Initially provided by Martin Steffen.
 * @version $Id: Aseq.java,v 1.6 1998-12-15 07:11:06 swtech01 Exp $
 */
public class Aseq extends Absyn implements Serializable, Cloneable {
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
    
    
    public Object clone() throws CloneNotSupportedException {
	return new Aseq ((Action)head.clone(),
			 (Aseq)tail.clone());
    };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Aseq.java,v 1.6 1998-12-15 07:11:06 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  1998/12/11 17:39:28  swtech00
//	Cloneable
//
//	Revision 1.4  1998/11/27 16:03:06  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:10  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------



