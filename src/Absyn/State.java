package Absyn;

abstract public class State extends Absyn {
    public Statename name;
}



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: State.java,v 1.8 1998-12-01 09:47:59 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/11/30 08:24:06  swtech00
//	Das Feld 'name' wieder aufgenommen.
//
//	Revision 1.6  1998/11/27 16:12:35  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.5  1998/11/27 09:56:04  swtech20
//	Mergekonflikte aufgeloest
//
//	Revision 1.4  1998/11/27 08:54:08  swtech24
//	Zu State :  Erweiterung um : public Statename name
//		    Zweck          : Schnellerer Zugriff auf Statenamen.
//
//	Zu Statename		   : Einfuegen des fehlenden public
//
//	Zu Absyn		   : keine Aenderungen, aber noch Probleme mit
//				     Location und Coord.
//				     Veraenderungen erst nach Besprechung.
//
//	Revision 1.3  1998/11/26 16:32:23  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
