package Absyn;

import java.io.Serializable;


/**
 * One kind of boolean statement: assigns true to the variable.
 * @author Initially provided by Martin Steffen.
 * @version $Id: MTrue.java,v 1.4 1998-12-15 07:11:09 swtech01 Exp $
 */
public class MTrue extends Boolstmt implements Serializable, Cloneable {
/**
 * Variable to change.
 */
  public Bvar var;
  public MTrue(Bvar v){
    var = v;
  };

    public Object clone () throws CloneNotSupportedException {
	return new MTrue((Bvar)var.clone());
    };

    
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: MTrue.java,v 1.4 1998-12-15 07:11:09 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/12/11 17:42:59  swtech00
//	Cloneable
//
//	Revision 1.2  1998/12/03 17:10:49  swtech00
//	Felder public gemacht
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
//	Revision 1.3  1998/11/27 16:19:32  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:34  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
