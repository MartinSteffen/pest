package Absyn;

/**
 * Abstract class to provide coordinates and locations.
 * <br> Graphical objects may have coordinates.
 * <br> TESC input language may have locations.
 * <p>
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Absyn.java,v 1.8 1998-12-02 12:29:02 swtech19 Exp $
 */
abstract public class Absyn {
 Location location;
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Absyn.java,v 1.8 1998-12-02 12:29:02 swtech19 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/11/27 16:01:32  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.6  1998/11/27 09:54:11  swtech20
//	Mergekonflikte aufgeloest.
//
//	Revision 1.5  1998/11/27 08:53:03  swtech24
//	Zu State :  Erweiterung um : public Statename name
//		    Zweck          : Schnellerer Zugriff auf Statenamen.
//
//	Zu Statename		   : Einfuegen des fehlenden public
//
//	Zu Absyn		   : keine Aenderungen, aber noch Probleme mit
//				     Location und Coord.
//				     Veraenderungen erst nach Besprechung.
//
//	Revision 1.4  1998/11/26 16:32:08  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
