package Absyn;

import java.awt.Point;

/**
 * Transition label.
 * @author Initially provided by Martin Steffen.
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

