package absyn;
import java.io.Serializable;

public class TrList extends Absyn implements Cloneable, Serializable { // List of transitions
    public Tr     head;
    public TrList tail;
    public TrList (Tr h, TrList tl) {
	head = h;
	tail = tl;
    };

    public TrList (Tr h, TrList tl, Location l) {
	head = h;
	tail = tl;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone () throws CloneNotSupportedException {
	TrList tailclone = (tail == null) ? null :  tailclone = (TrList)tail.clone();
	Tr     headclone = (head == null) ? null :  headclone = (Tr)head.clone();
	return new TrList(headclone, tailclone);
    }
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TrList.java,v 1.12 1999-02-09 11:33:00 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.11  1999/01/12 08:57:29  swtech00
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
//	Revision 1.10  1999/01/11 17:23:52  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.9  1998/12/15 16:33:32  swtech00
//	Towards new package names.
//
//	Revision 1.8  1998/12/15 13:38:09  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.7  1998/12/15 08:01:24  swtech00
//	Cloneable zu Dummy + TrList hinzugefuegt
//
//	Revision 1.6  1998/12/15 07:11:11  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:43:02  swtech00
//	Cloneable
//
//	Revision 1.4  1998/12/01 09:37:17  swtech00
//	Small error corrected
//
//	Revision 1.3  1998/11/27 10:07:18  swtech20
//	Klasse TrList public und Kontruktor public
//
//	Revision 1.2  1998/11/26 16:32:27  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
