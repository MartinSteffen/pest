package Absyn;

import java.io.Serializable;

/**
 * Composition of two guards.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Compguard.java,v 1.8 1998-12-15 13:38:03 swtech00 Exp $
 */
public class Compguard extends Absyn implements Serializable, Cloneable {
/**
 * Biary operation.
 */
    public int   eop;
/**
 * Left-hand-side.
 */
    public Guard elhs;
/**
 * Right-hand-side.
 */
    public Guard erhs;
/**
 * Constants.
 */
    public final static int AND     = 0;
    public final static int OR      = 1;
    public final static int IMPLIES = 2;
    public final static int EQUIV   = 3;
/**
 * Constructor.
 */
    public Compguard (int op, Guard g1, Guard g2) {
	eop = op;
	elhs = g1;
	erhs = g2;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new Compguard(eop, (Guard)elhs.clone(), (Guard)erhs.clone());
    };

};


//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Compguard.java,v 1.8 1998-12-15 13:38:03 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/15 07:11:07  swtech01
//	Added Serialization to all classes
//
//	Revision 1.6  1998/12/11 17:42:56  swtech00
//	Cloneable
//
//	Revision 1.5  1998/12/01 17:42:30  swtech00
//	Die Operatoren als konstante Felder mit aufgenommen
//
//	Revision 1.4  1998/11/27 16:07:02  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:13  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
