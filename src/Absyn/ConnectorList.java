package absyn;

import java.io.Serializable;

/**
 * Connector list.
 * @author Initially provided by Martin Steffen.
 * @version $Id: ConnectorList.java,v 1.8 1998-12-15 16:33:27 swtech00 Exp $
 */
public class ConnectorList extends Absyn implements Serializable, Cloneable {
/**
 * Head of the list.
 */
    public Connector head;
/**
 * Tail of the list.
 */
    public ConnectorList  tail;
/**
 * Constructor.
 */
    public ConnectorList(Connector h, ConnectorList tl) {
	head = h;
	tail = tl;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	ConnectorList tailclone;
	if (tail != null) {tailclone = (ConnectorList) tail.clone();} else {tailclone = null;};
	return new ConnectorList((Connector)head.clone(), (ConnectorList)tailclone);
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ConnectorList.java,v 1.8 1998-12-15 16:33:27 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/15 13:38:04  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.6  1998/12/15 07:11:08  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:42:57  swtech00
//	Cloneable
//
//	Revision 1.4  1998/11/27 16:08:51  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:15  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
