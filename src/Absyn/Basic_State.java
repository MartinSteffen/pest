package Absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public class Basic_State extends State implements Serializable, Cloneable {
    public Basic_State (Statename n) {
	name = n;
	rect = null;
    }
    public Basic_State (Statename n, CRectangle r) {
	name = n;
	rect = r;
    };
    
    public Object clone() throws CloneNotSupportedException {
	return new Basic_State ((Statename)name.clone(),rect);
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Basic_State.java,v 1.7 1998-12-15 11:30:38 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.6  1998/12/15 07:11:07  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:42:55  swtech00
//	Cloneable
//
//	Revision 1.4  1998/12/02 12:39:32  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.3  1998/11/30 17:08:55  swtech00
//	Namens-Feld entfernt, da es nun in der Oberklasse enthalten ist
//
//	(Steffen)
//
//	Revision 1.2  1998/11/26 16:32:11  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
