package Absyn;

import java.io.Serializable;



/**
 * One kind of transition action.
 * <br> Collects a sequence of actions into one block.
 * @author Initially provided by Martin Steffen.
 * @version $Id: ActionBlock.java,v 1.4 1998-12-15 07:11:06 swtech01 Exp $
 */
public class ActionBlock extends Action implements Serializable, Cloneable {
/**
 * Contents of the block.
 */
    public Aseq aseq;
    public ActionBlock (Aseq a) {
	aseq = a;
    };

    public Object clone() throws CloneNotSupportedException {
	return new ActionBlock((Aseq)aseq.clone());
    };
    
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ActionBlock.java,v 1.4 1998-12-15 07:11:06 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/12/11 17:38:58  swtech00
//	Cloneable
//
//	Revision 1.2  1998/12/07 14:58:31  swtech18
//	Konstruktor public gesetzt.
//
//	Revision 1.1  1998/12/01 17:52:52  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.3  1998/11/27 16:13:55  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:28  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
