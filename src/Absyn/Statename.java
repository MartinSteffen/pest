package absyn;

import java.io.Serializable;

public class Statename extends TrAnchor implements Serializable, Cloneable {
    public String name;
    public CPoint position;

    public Statename (String s) {
	name = s;
	position = null;
    };

    public Statename (String s, CPoint p) {
	name = s;
	position = p;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
      CPoint positionclone;
      if (position != null) {
	positionclone = (CPoint)position.clone();
      } else
	positionclone = null;

	return new Statename(name, positionclone);
    };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Statename.java,v 1.8 1999-01-08 23:03:32 swtech14 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/15 16:33:31  swtech00
//	Towards new package names.
//
//	Revision 1.6  1998/12/15 13:38:08  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.5  1998/12/15 07:11:11  swtech01
//	Added Serialization to all classes
//
//	Revision 1.4  1998/12/11 17:43:01  swtech00
//	Cloneable
//
//	Revision 1.3  1998/11/27 08:55:15  swtech24
//	Zu State :  Erweiterung um : public Statename name
//		    Zweck          : Schnellerer Zugriff auf Statenamen.
//
//	Zu Statename		   : Einfuegen des fehlenden public
//
//	Zu Absyn		   : keine Aenderungen, aber noch Probleme mit
//				     Location und Coord.
//				     Veraenderungen erst nach Besprechung.
//
//	Revision 1.2  1998/11/26 16:32:25  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
