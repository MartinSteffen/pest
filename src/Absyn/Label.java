package Absyn;

public class Label extends Absyn {
    public Guard guard;
    public Action action;
    public Label (Guard g, Action a) {
	guard = g;
	action = a;
    }
}


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Label.java,v 1.3 1998-11-26 16:32:18 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
