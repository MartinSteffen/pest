package Absyn;

import java.awt.Rectangle;

public class And_State extends State implements Cloneable {
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
		     Rectangle r) 
    {
	name = n;
	rect = r;
	substates = sl;
    };	

    public Object clone() throws CloneNotSupportedException {
	return new And_State(name, 
			     (StateList)substates.clone(), 
			     (Rectangle)rect);
    };
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: And_State.java,v 1.5 1998-12-11 17:39:18 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
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
