package Absyn;

public class BvarList extends Absyn {
    public Bvar head;
    public BvarList tail;
    public BvarList (Bvar h, BvarList tl) {
	head = h;
	tail = tl;
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: BvarList.java,v 1.3 1998-11-26 16:32:13 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
