package absyn;

import java.io.Serializable;


/**
 * One kind of guard: untyped identifier.
 * If the type of the identifier can not be determined, this class is used.
 * @author Initially provided by Martin Steffen.
 * @version $Id: GuardUndet.java,v 1.5 1998-12-15 16:33:29 swtech00 Exp $
 */
public class GuardUndet extends Guard implements Serializable, Cloneable {
/**
 * Identifier name.
 */
    public String undet;
/**
 * Constructor.
 */
    public GuardUndet (String u) {
	undet = u;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new GuardUndet (undet);
    };

};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: GuardUndet.java,v 1.5 1998-12-15 16:33:29 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/15 13:38:05  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.3  1998/12/15 07:11:09  swtech01
//	Added Serialization to all classes
//
//	Revision 1.2  1998/12/11 17:42:59  swtech00
//	Cloneable
//
//	Revision 1.1  1998/12/01 17:52:55  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.3  1998/11/27 16:20:49  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:35  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
