package Absyn;

import java.awt.Point;

/**
 * Transition label.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Label.java,v 1.5 1998-12-02 12:34:32 swtech19 Exp $
 */
public class Label extends Absyn {
/**
 * Position of the label.
 */
    public Point position;
/**
 * Left-hand-side of the label: guard.
 */
    public Guard guard;
/**
 * Righ-hand-side of the label: action
 */
    public Action action;
/**
 * Constructor.
 */
    public Label (Guard g, Action a) {
	position = null;
	guard = g;
	action = a;
    };
/**
 * Constructor with Position.
 */
    public Label (Guard g, Action a, Point p) {
	position = p;
	guard = g;
	action = a;
    };
}


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Label.java,v 1.5 1998-12-02 12:34:32 swtech19 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/11/27 16:10:43  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:18  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
