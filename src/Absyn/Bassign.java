package Absyn;

import java.io.Serializable;

/**
 * Boolean assignment.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Bassign.java,v 1.8 1998-12-15 13:38:02 swtech00 Exp $
 */
public class Bassign extends Absyn implements Serializable, Cloneable {
/**
 * Left-hand-side of a boolean assignment.
 */
    public Bvar blhs;
/**
 * Right-hand-side of a boolean assignment.
 */
    public Guard brhs;
/**
 * Constructs a boolean assignment.
 */
    public Bassign (Bvar l, Guard r) {
	blhs = l;
	brhs = r;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new Bassign((Bvar)blhs.clone(),(Guard)brhs.clone());
    };

};
    



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Bassign.java,v 1.8 1998-12-15 13:38:02 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/15 07:11:07  swtech01
//	Added Serialization to all classes
//
//	Revision 1.6  1998/12/11 17:42:55  swtech00
//	Cloneable
//
//	Revision 1.5  1998/12/01 10:35:41  swtech00
//	Die Selektoren geaendert ("s_") entfernt
//
//	Revision 1.4  1998/11/27 16:03:53  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:11  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------

