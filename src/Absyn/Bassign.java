package Absyn;

/**
 * Boolean assignment.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Bassign.java,v 1.5 1998-12-01 10:35:41 swtech00 Exp $
 */
public class Bassign extends Absyn {
/**
 * Left-hand-side of a boolean assignment.
 */
    public Bvar blhs;
/**
 * Right-hand-side of a boolean assignment.
 */
    public Guard brhs;
/**
 * Constructs a boolean assignment.
 */
    public Bassign (Bvar l, Guard r) {
	blhs = l;
	brhs = r;
    };
};
    



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Bassign.java,v 1.5 1998-12-01 10:35:41 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/11/27 16:03:53  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:11  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
