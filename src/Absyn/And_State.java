package Absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public class And_State extends State implements Serializable, Cloneable {
    public StateList substates;
    public And_State(Statename n,
		     StateList sl) 
    {
	name = n;
	rect = null;
	substates = sl;
    };
    public And_State(Statename n,
		     StateList sl,
		     CRectangle r) 
    {
	name = n;
	rect = r;
	substates = sl;
    };	

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
   public Object clone() throws CloneNotSupportedException {
	return new And_State(name, 
			     (StateList)substates.clone(), 
			     (CRectangle)rect.clone());
    };
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: And_State.java,v 1.8 1998-12-15 13:38:01 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1998/12/15 11:30:38  swtech00
//	Rectangle durch CRectangle ersetzt
//
//	Revision 1.6  1998/12/15 07:11:06  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:39:18  swtech00
//	*** empty log message ***
//
//	Revision 1.4  1998/12/02 12:40:35  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.3  1998/11/30 17:07:40  swtech00
//	-  Namens-feld entfernt (da es nun in der Oberklasse ist)
//	-  Konventionen vereinfacht ("_s" und "o"-Praefix entfernt)
//
//	(Steffen)
//
//	Revision 1.2  1998/11/26 16:32:10  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
