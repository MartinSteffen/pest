package Absyn;


public class Path  implements Cloneable {
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

    public Object clone() throws CloneNotSupportedException {
	Path tailclone;
	if (tail != null) {tailclone = (Path)tail.clone();} else tailclone = null;
	return new Path (head, (Path)tailclone);
    };
			 
			 
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Path.java,v 1.5 1998-12-11 17:43:00 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/08 09:37:03  swtech00
//	Path corrected
//
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
