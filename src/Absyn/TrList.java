package Absyn;

class TrList { // List of transitions
    public Tr     head;
    public TrList tail;
    TrList (Tr h, TrList tl) {
	head = h;
	tail = tail;
    };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TrList.java,v 1.2 1998-11-26 16:32:27 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
