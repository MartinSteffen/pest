package absyn;

import java.io.Serializable;

/**
 * Sequence of actions, needed as the contense of a s_block.
 * <br>
 * @author Initially provided by Martin Steffen.
 * @version $Id: Aseq.java,v 1.10 1999-01-11 17:23:47 swtech00 Exp $
 */
public class Aseq extends Absyn implements Serializable, Cloneable {
/**
 * Head of the list.
 */
    public Action head;
/**
 * Tail of the list.
 */
    public Aseq tail;
/**
 * Constructor.
 */
  
  public Aseq (Action h, Aseq tl) {head = h;tail = tl; };
  public Aseq (Action h, Aseq tl, Location l) {head = h;tail = tl; location = l;};
    
    
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	Action headclone;
	if (head != null) {
	    headclone = (Action)head.clone();
	}
	else headclone = null;
	
	Aseq tailclone;
	if (tail != null) {
	    tailclone = (Aseq)tail.clone();
	}
	else tailclone = null;
	
	Location  locationclone  = (location == null) ? null : (Location)location.clone();
	return new Aseq ((Action)headclone,
			 (Aseq)tailclone,
			 locationclone);
    };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Aseq.java,v 1.10 1999-01-11 17:23:47 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.9  1999/01/09 15:47:53  swtech13
//	clone() methoden korrigiert (weitere nullpointerabfragen)
//
//	Revision 1.8  1998/12/15 16:33:24  swtech00
//	Towards new package names.
//
//	Revision 1.7  1998/12/15 13:38:01  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.6  1998/12/15 07:11:06  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:39:28  swtech00
//	Cloneable
//
//	Revision 1.4  1998/11/27 16:03:06  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:10  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------



