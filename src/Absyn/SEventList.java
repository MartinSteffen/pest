package absyn;

import java.io.Serializable;

public class SEventList implements Serializable, Cloneable {
    public SEvent  head;
    public SEventList  tail;
    public SEventList(SEvent h, SEventList tl) {
	head = h;
	tail = tl;
    };
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	SEventList tailclone;
	if (tail != null) {tailclone = (SEventList)tail.clone();} else {tailclone = null;};
	return new SEventList(
			      (SEvent)head.clone(),
			      tailclone
			      );
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: SEventList.java,v 1.7 1998-12-15 16:33:30 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.6  1998/12/15 13:38:07  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.5  1998/12/15 07:11:10  swtech01
//	Added Serialization to all classes
//
//	Revision 1.4  1998/12/11 17:43:01  swtech00
//	Cloneable
//
//	Revision 1.3  1998/12/01 17:44:03  swtech00
//	Fehler ausgebessert: head und tail m"ussen public sein
//
//	Revision 1.2  1998/11/26 16:32:23  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
