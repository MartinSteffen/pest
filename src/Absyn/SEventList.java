package Absyn;

public class SEventList {
    SEvent  head;
    SEventList  tail;
    public SEventList(SEvent h, SEventList tl) {
	head = h;
	tail = tl;
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: SEventList.java,v 1.2 1998-11-26 16:32:23 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
