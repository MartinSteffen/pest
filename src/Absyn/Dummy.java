package Absyn;



/**
 * Dummy.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Dummy.java,v 1.6 1998-12-13 17:45:32 swtech20 Exp $
 */
public class Dummy extends Absyn {
/**
 * Constructor.
 */
    public Dummy () {
    };

    
    public Object clone() throws CloneNotSupportedException {
	return new Dummy();
    };



};



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Dummy.java,v 1.6 1998-12-13 17:45:32 swtech20 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  1998/12/11 17:42:57  swtech00
//	Cloneable
//
//	Revision 1.4  1998/11/27 16:09:29  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:17  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
