package absyn;

import java.io.Serializable;

import java.awt.Point;

/**
 * Connector.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Connector.java,v 1.14 1999-02-09 13:17:11 swtech00 Exp $
 */
public class Connector  extends Absyn implements Serializable, Cloneable {
/**
 * Connector name is used for initial bookkeeping only.
 */
    public Conname name;
/**
 * Position of the Connector.
 */
    public CPoint position;
/**
 * Constructor.
 */
    public Connector(Conname n) {
	name = n;
	position = null;
    };
/**
 * Constructor with Position.
 */
    public Connector(Conname n, CPoint p) {
	name = n;
	position = p;
    };

/**
 * Constructor with Position and Location.
 */
  public Connector(Conname n, CPoint p, Location l) {
    name = n;
    position = p;
    location = l;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    CPoint positionclone     = (position == null) ? null : (CPoint)position.clone();
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    Conname nameclone        = (name == null)     ? null : (Conname)name.clone();
    return new Connector(nameclone, positionclone, locationclone);
  };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Connector.java,v 1.14 1999-02-09 13:17:11 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.13  1999/02/09 10:45:51  swtech00
//	Null-name abgefangen
//
//	Revision 1.12  1999/01/11 17:23:48  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.11  1998/12/17 15:47:17  swtech00
//	Null-Pointer-Exception nei clone() abgefangen
//
//	Revision 1.10  1998/12/15 16:33:26  swtech00
//	Towards new package names.
//
//	Revision 1.9  1998/12/15 13:38:03  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.8  1998/12/15 11:04:35  swtech00
//	Point -> CPoint
//
//	Revision 1.7  1998/12/15 07:11:08  swtech01
//	Added Serialization to all classes
//
//	Revision 1.6  1998/12/11 17:42:57  swtech00
//	Cloneable
//
//	Revision 1.5  1998/12/02 12:35:40  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.4  1998/11/27 16:08:16  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:15  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------


