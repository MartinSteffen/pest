package Absyn;

import java.io.Serializable;


/**
 * Abstract class Guard introduces a union type for guards of transition labels.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Guard.java,v 1.6 1998-12-15 07:11:08 swtech01 Exp $
 */
public abstract class Guard extends Absyn implements Serializable, Cloneable {
    public abstract Object clone() throws CloneNotSupportedException;
};









//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Guard.java,v 1.6 1998-12-15 07:11:08 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  1998/12/11 17:42:57  swtech00
//	Cloneable
//
//	Revision 1.4  1998/11/27 16:10:06  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:17  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
