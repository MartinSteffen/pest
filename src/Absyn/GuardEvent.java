package Absyn;

import java.io.Serializable;

/**
 * One kind of guard: event.
 * @author Initially provided by Martin Steffen.
 * @version $Id: GuardEvent.java,v 1.3 1998-12-15 07:11:09 swtech01 Exp $
 */
public class GuardEvent extends Guard implements Serializable, Cloneable {
/**
 * Contents of event.
 */
    public SEvent event;
/**
 * Constructor.
 */
    public GuardEvent(SEvent e) {
	event = e;
    }

    public Object clone() throws CloneNotSupportedException {
	return new GuardEvent((SEvent)event.clone());
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: GuardEvent.java,v 1.3 1998-12-15 07:11:09 swtech01 Exp $
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
//	Revision 1.3  1998/11/27 16:17:39  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:32  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
