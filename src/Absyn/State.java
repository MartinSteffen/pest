package Absyn;

import java.io.Serializable;

import java.awt.Rectangle;

abstract public class State extends Absyn implements Serializable, Cloneable {
    public Statename name;
    public CRectangle rect;

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public abstract Object clone() throws  CloneNotSupportedException;
}



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: State.java,v 1.13 1998-12-15 13:38:07 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.12  1998/12/15 11:30:38  swtech00
//	Rectangle durch CRectangle ersetzt
//
//	Revision 1.11  1998/12/15 07:11:10  swtech01
//	Added Serialization to all classes
//
//	Revision 1.10  1998/12/11 17:43:01  swtech00
//	Cloneable
//
//	Revision 1.9  1998/12/02 12:37:16  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.8  1998/12/01 09:47:59  swtech00
//	*** empty log message ***
//
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
