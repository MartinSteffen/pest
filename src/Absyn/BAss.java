package  Absyn;

import java.io.Serializable;

/**
 * One kind of boolean statement: assignment.
 * @author Initially provided by Martin Steffen.
 * @version $Id: BAss.java,v 1.5 1998-12-15 13:38:01 swtech00 Exp $
 */
public class BAss extends Boolstmt implements Serializable, Cloneable {
/**
 * Contents of the assignment.
 */
  public Bassign ass;
/**
 * Constructor.
 */
  public BAss (Bassign a) {
    ass = a;
  };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new BAss ((Bassign)ass.clone());
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: BAss.java,v 1.5 1998-12-15 13:38:01 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/15 07:11:06  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:42:55  swtech00
//	Cloneable
//
//	Revision 1.2  1998/12/03 17:10:48  swtech00
//	Felder public gemacht
//
//	Revision 1.1  1998/12/01 17:52:53  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.3  1998/11/27 16:18:16  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:33  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------


