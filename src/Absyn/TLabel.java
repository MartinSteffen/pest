package Absyn;

import java.io.Serializable;

import java.awt.Point;

/**
 * Transition label.
 * @author Initially provided by Martin Steffen.
 */
public class TLabel extends Absyn implements Serializable, Cloneable {
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
    public TLabel (Guard g, Action a) {
	position = null;
	guard = g;
	action = a;
    };
/**
 * Constructor with Position.
 */
    public TLabel (Guard g, Action a, Point p) {
	position = p;
	guard = g;
	action = a;
    };

    public Object clone() throws CloneNotSupportedException {
	return new TLabel (
			   (Guard)guard.clone(),
			   (Action)action.clone(),
			   (Point)position
			   );
    };
}



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TLabel.java,v 1.4 1998-12-15 07:11:11 swtech01 Exp $
//----------------------------------------------------------------------
