package Absyn;

import java.io.Serializable;

public class PathList implements Serializable, Cloneable {
    public Path head;
    public PathList tail;
    public PathList (Path  h, PathList tl) {
	head = h;
	tail = tl;
    };


/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
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
//	$Id: PathList.java,v 1.5 1998-12-15 13:38:07 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/15 07:11:10  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:43:00  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:21  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
