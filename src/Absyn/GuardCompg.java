package Absyn;

import java.io.Serializable;


/**
 * One kind of guard: composition of guards.
 * @author Initially provided by Martin Steffen.
 * @version $Id: GuardCompg.java,v 1.3 1998-12-15 07:11:08 swtech01 Exp $
 */
public class GuardCompg extends Guard implements Serializable, Cloneable {
/**
 * Contents of compound guard.
 */
    public Compguard cguard;
/**
 * Constructor.
 */
    public GuardCompg(Compguard g) {
	cguard = g;
    };

    public Object clone() throws CloneNotSupportedException {
	return new GuardCompg ((Compguard)cguard.clone());
    };

};


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: GuardCompg.java,v 1.3 1998-12-15 07:11:08 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/12/11 17:42:58  swtech00
//	Cloneable
//
//	Revision 1.1  1998/12/01 17:52:54  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.3  1998/11/27 16:15:45  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:30  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
