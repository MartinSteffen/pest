package absyn;

import java.io.Serializable;

/**
 * List of boolean variables.
 * @author Initially provided by Martin Steffen.
 * @version $Id: BvarList.java,v 1.12 1999-02-09 13:17:11 swtech00 Exp $
 */
public class BvarList extends Absyn implements Serializable, Cloneable {
/**
 * Head of the list.
 */
    public Bvar head;
/**
 * Tail of the list.
 */
    public BvarList tail;
/**
 * Constructor.
 */
    public BvarList (Bvar h, BvarList tl) {
	head = h;
	tail = tl;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	BvarList tailclone = (tail == null) ? null : (BvarList) tail.clone();
        Bvar     headclone = (head == null) ? null : (Bvar)head.clone();
	return new BvarList(headclone, tailclone);
    };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: BvarList.java,v 1.12 1999-02-09 13:17:11 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.11  1999/02/09 10:35:03  swtech00
//	null-head f"urs Clonen abgefangen
//
//	Revision 1.10  1999/01/12 08:57:27  swtech00
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
//	Revision 1.9  1999/01/11 17:15:36  swtech00
//	BvarList erweitert _nicht_ Absyn, da es keine Locations braucht.
//
//	Revision 1.8  1998/12/15 16:33:25  swtech00
//	Towards new package names.
//
//	Revision 1.7  1998/12/15 13:38:02  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.6  1998/12/15 07:11:07  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:42:56  swtech00
//	Cloneable
//
//	Revision 1.4  1998/11/27 16:06:18  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:13  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
