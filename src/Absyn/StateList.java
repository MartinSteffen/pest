package Absyn;

public class StateList {
    public State head;
    public StateList tail;
    public StateList(State h, StateList tl) {
	head = h;
	tail = tl;
    };
};


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: StateList.java,v 1.2 1998-11-26 16:32:24 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
