package Absyn;

public class PathList implements Cloneable {
    public Path head;
    public PathList tail;
    public PathList (Path  h, PathList tl) {
	head = h;
	tail = tl;
    };


    public Object clone() throws CloneNotSupportedException {
	PathList tailclone;
	if (tail != null)
	    tailclone = (PathList)tail.clone();
	else tailclone = null;
	return new PathList ((Path)head.clone(), (PathList)tailclone);
    };

};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: PathList.java,v 1.3 1998-12-11 17:43:00 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:21  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
