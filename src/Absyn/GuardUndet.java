package Absyn;


/**
 * One kind of guard: untyped identifier.
 * If the type of the identifier can not be determined, this class is used.
 * @author Initially provided by Martin Steffen.
 * @version $Id: GuardUndet.java,v 1.1 1998-12-01 17:52:55 swtech00 Exp $
 */
public class GuardUndet extends Guard {
/**
 * Identifier name.
 */
    public String undet;
/**
 * Constructor.
 */
    public GuardUndet (String u) {
	undet = u;
    };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: GuardUndet.java,v 1.1 1998-12-01 17:52:55 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/27 16:20:49  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:35  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
