package Absyn;

import java.io.Serializable;

import java.awt.Point;

/**
 * Connector.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Connector.java,v 1.7 1998-12-15 07:11:08 swtech01 Exp $
 */
public class Connector  extends Absyn implements Serializable, Cloneable {
/**
 * Connector name is used for initial bookkeeping only.
 */
    public Conname name;
/**
 * Position of the Connector.
 */
    public Point position;
/**
 * Constructor.
 */
    public Connector(Conname n) {
	name = n;
	position = null;
    };
/**
 * Constructor with Position.
 */
    public Connector(Conname n, Point p) {
	name = n;
	position = p;
    };

    public Object clone() throws CloneNotSupportedException {
	return new Connector((Conname)name.clone(),
			     position);
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Connector.java,v 1.7 1998-12-15 07:11:08 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.6  1998/12/11 17:42:57  swtech00
//	Cloneable
//
//	Revision 1.5  1998/12/02 12:35:40  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.4  1998/11/27 16:08:16  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:15  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------


