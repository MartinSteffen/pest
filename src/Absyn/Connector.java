package Absyn;

import java.awt.Point;

/**
 * Connector.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Connector.java,v 1.5 1998-12-02 12:35:40 swtech19 Exp $
 */
public class Connector  extends Absyn {
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
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Connector.java,v 1.5 1998-12-02 12:35:40 swtech19 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/11/27 16:08:16  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:15  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
