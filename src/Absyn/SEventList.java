package absyn;

import java.io.Serializable;

public class SEventList extends Absyn implements Serializable, Cloneable {
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
	SEventList tailclone = (tail == null) ? null : (SEventList)tail.clone();
	SEvent     headclone = (head == null) ? null : (SEvent)head.clone();
	return new SEventList(headclone, tailclone);
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: SEventList.java,v 1.10 1999-02-09 13:17:12 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.9  1999/02/09 11:32:58  swtech00
//	Abfangen von weiteren Null-Pointern beim Klonen
//
//	Revision 1.8  1999/01/12 08:57:29  swtech00
//	Die "Anderungen von gestern in den ``Listen'' r"uckg"angig gemacht.
//
//	Grund: Uniformit"at bestimmter Funktionen, insbesondere des PrettyPrinters.
//	       Dieser nimmt als Argument einen Term der abstrakten Syntax (eine
//	       instanz der Klasse Absyn) und verzweigt dann mittels instance_of.
//
//	       Der Code f"ur derartige Funktionen w"are ansonsten etwas komplexer.
//	       Allerdings haben nun (wieder) die Listen ein location-Feld durch
//	       Vererbung aus Absyn, was im Grunde "uberfl"ussig ist.
//
//
//	[Steffen]
//
//	Revision 1.7  1998/12/15 16:33:30  swtech00
//	Towards new package names.
//
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
