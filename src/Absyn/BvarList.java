package Absyn;

/**
 * List of boolean variables.
 * @author Initially provided by Martin Steffen.
 * @version $Id: BvarList.java,v 1.5 1998-12-11 17:42:56 swtech00 Exp $
 */
public class BvarList extends Absyn implements Cloneable {
/**
 * Head of the list.
 */
    public Bvar head;
/**
 * Tail of the list.
 */
    public BvarList tail;
/**
 * Constructor.
 */
    public BvarList (Bvar h, BvarList tl) {
	head = h;
	tail = tl;
    };

    public Object clone() throws CloneNotSupportedException {
	BvarList tailclone;
	if (tail != null) {tailclone = (BvarList) tail.clone();} else {tailclone = null;};
	return new BvarList((Bvar)head.clone(), tailclone);
    };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: BvarList.java,v 1.5 1998-12-11 17:42:56 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/11/27 16:06:18  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:13  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
