package Absyn;

import java.io.Serializable;

public class Statename extends TrAnchor implements Serializable, Cloneable {
    public String name;
    public Statename (String s) {
	name = s;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new Statename(name);
    };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Statename.java,v 1.6 1998-12-15 13:38:08 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
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
