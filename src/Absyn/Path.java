package Absyn;


public class Path  {
    public String head;
    public Path   tail;
    public Path (String h, Path tl) {
	head       = h;
	tail       = tl; 
    };
    public Path append (String s) {
	if(tail != null)
	    {
		return new Path (head,tail.append(s));
	    } 
	else 
	    return new Path(head, new Path (s, null));
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Path.java,v 1.4 1998-12-08 09:37:03 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/12/07 15:08:00  swtech00
//	- TLabel anstelle Label
//
//	- Path verbessert: path ist nun eine Liste von Strings
//
//	- Example.java angepasst
//
//	Revision 1.2  1998/11/26 16:32:21  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
