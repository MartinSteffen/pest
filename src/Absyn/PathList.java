package Absyn;

public class PathList {
    public Path head;
    public PathList tail;
    public PathList (Path  h, PathList tl) {
	head = h;
	tail = tl;
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: PathList.java,v 1.2 1998-11-26 16:32:21 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
