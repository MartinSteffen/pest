package Absyn;

public class TrList { // List of transitions
    public Tr     head;
    public TrList tail;
    public TrList (Tr h, TrList tl) {
	head = h;
	tail = tail;
    };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TrList.java,v 1.3 1998-11-27 10:07:18 swtech20 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:27  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
