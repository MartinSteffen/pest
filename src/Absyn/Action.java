package Absyn;

import java.io.Serializable;


/**
 * Abstract class Action introduces a union type for actions of transition labels.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Action.java,v 1.8 1998-12-15 13:38:00 swtech00 Exp $
 */
public abstract class  Action extends Absyn  implements Serializable, Cloneable {
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public abstract Object clone() throws CloneNotSupportedException;
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Action.java,v 1.8 1998-12-15 13:38:00 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/15 07:11:05  swtech01
//	Added Serialization to all classes
//
//	Revision 1.6  1998/12/11 17:38:51  swtech00
//	cloneable
//
//	Revision 1.5  1998/12/03 17:06:51  swtech00
//	Klasse public gemacht
//
//	Revision 1.4  1998/11/27 16:02:18  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:09  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
