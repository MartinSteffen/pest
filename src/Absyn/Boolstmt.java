
package Absyn;


/**
 * Abstract class Boolstmt introduces a union type for boolean statements.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Boolstmt.java,v 1.5 1998-12-11 17:42:56 swtech00 Exp $
 */
public abstract class Boolstmt extends Absyn implements Cloneable {
    public abstract Object clone() throws  CloneNotSupportedException;
};


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Boolstmt.java,v 1.5 1998-12-11 17:42:56 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/11/27 16:04:42  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:12  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
