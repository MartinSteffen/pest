package absyn;

import java.io.Serializable;


public class Path  implements Serializable, Cloneable {
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

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
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
//	$Id: Path.java,v 1.8 1998-12-15 16:33:30 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/15 13:38:06  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.6  1998/12/15 07:11:10  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:43:00  swtech00
//	Cloneable
//
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
