package absyn;

import java.io.Serializable;

public class StateList implements Serializable, Cloneable {
    public State head;
    public StateList tail;
    public StateList(State h, StateList tl) {
	head = h;
	tail = tl;
    };
    
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	StateList tailclone;
	if (tail != null) {tailclone = (StateList)tail.clone();} else {tailclone = null;};

	return new StateList((State)head.clone(),
			     (StateList)tailclone);
    };
    
};



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: StateList.java,v 1.6 1998-12-15 16:33:31 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  1998/12/15 13:38:07  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.4  1998/12/15 07:11:11  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:43:01  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:24  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
