package Absyn;

/**
 * Transition label.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Label.java,v 1.4 1998-11-27 16:10:43 swtech00 Exp $
 */
public class Label extends Absyn {
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
	guard = g;
	action = a;
    }
}


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Label.java,v 1.4 1998-11-27 16:10:43 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/26 16:32:18  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
