package Absyn;

/**
 * One kind of guard: empty guard.
 * @author Initially provided by Martin Steffen.
 * @version $Id: GuardEmpty.java,v 1.1 1998-12-01 17:52:54 swtech00 Exp $
 */
public class GuardEmpty extends Guard {
/**
 * Contents of empty guard.
 */
    public Dummy dummy;
/**
 * Constructor.
 */
    public GuardEmpty (Dummy d) {
	dummy = d;
    };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: GuardEmpty.java,v 1.1 1998-12-01 17:52:54 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/27 16:13:13  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:28  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
