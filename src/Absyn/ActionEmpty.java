package absyn;

import java.io.Serializable;


/**
 * One kind of transition action: empty action.
 * <br>
 * <br> Initially provided by Martin Steffen.
 * <br> Version $Id: ActionEmpty.java,v 1.8 1999-02-09 10:09:29 swtech00 Exp $
 */
public class ActionEmpty extends Action implements Serializable, Cloneable {
  public Dummy dummy;
  public ActionEmpty (Dummy d) {
    dummy = d;
  }

  public ActionEmpty (Dummy d, Location l) {
    dummy = d;
    location = l;
  }

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    Dummy dummyclone = (dummy == null) ? null : (Dummy)dummy.clone();
    return new ActionEmpty(dummyclone,locationclone);
  };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ActionEmpty.java,v 1.8 1999-02-09 10:09:29 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1999/01/11 17:23:46  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.6  1998/12/15 16:33:23  swtech00
//	Towards new package names.
//
//	Revision 1.5  1998/12/15 13:38:00  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.4  1998/12/15 07:11:06  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:39:06  swtech00
//	Cloneable
//
//	Revision 1.2  1998/12/07 11:12:01  swtech00
//	public vergessen gehabt
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
//	Revision 1.3  1998/11/27 16:17:00  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:32  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
