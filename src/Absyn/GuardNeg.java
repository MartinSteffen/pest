package Absyn;

/**
 * One kind of guard: guard negation.
 * @author Initially provided by Martin Steffen.
 * @version $Id: GuardNeg.java,v 1.2 1998-12-11 17:42:59 swtech00 Exp $
 */
public class GuardNeg extends Guard implements Cloneable {
/**
 * Guard to negate.
 */
    public Guard guard;
/**
 * Constructor.
 */
    public GuardNeg (Guard g) {
	guard = g;
    };


    public Object clone() throws CloneNotSupportedException {
	return new GuardNeg ((Guard)guard.clone());
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: GuardNeg.java,v 1.2 1998-12-11 17:42:59 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  1998/12/01 17:52:54  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.3  1998/11/27 16:20:09  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:34  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
