package Absyn;

import java.io.Serializable;

import java.awt.Point;

/**
 * Connector.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Connector.java,v 1.9 1998-12-15 13:38:03 swtech00 Exp $
 */
public class Connector  extends Absyn implements Serializable, Cloneable {
/**
 * Connector name is used for initial bookkeeping only.
 */
    public Conname name;
/**
 * Position of the Connector.
 */
    public CPoint position;
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
    public Connector(Conname n, CPoint p) {
	name = n;
	position = p;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new Connector((Conname)name.clone(),
			     (CPoint)position.clone());
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Connector.java,v 1.9 1998-12-15 13:38:03 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.8  1998/12/15 11:04:35  swtech00
//	Point -> CPoint
//
//	Revision 1.7  1998/12/15 07:11:08  swtech01
//	Added Serialization to all classes
//
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


