package absyn;

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

    public And_State(Statename n,
		     StateList sl,
		     CRectangle r,
		     Location l) 
    {
	name      = n;
	rect      = r;
	substates = sl;
	location  = l;
    };	

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
   public Object clone() throws CloneNotSupportedException {
       CRectangle rectclone;
       if (rect != null) 
	   rectclone = (CRectangle)rect.clone();
       else
	   rectclone = null;

       StateList substatesclone;
       if (substates != null) 
	   substatesclone = (StateList)substates.clone();
       else
	   substatesclone = null;

       Location  locationclone  = (location == null) ? null : (Location)location.clone();

       return new And_State((Statename)name.clone(), 
			    (StateList)substatesclone, 
			    rectclone,
			    locationclone);
    };
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: And_State.java,v 1.13 1999-01-28 10:40:30 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.12  1999/01/11 17:23:46  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.11  1999/01/09 15:47:52  swtech13
//	clone() methoden korrigiert (weitere nullpointerabfragen)
//
//	Revision 1.10  1998/12/17 15:47:16  swtech00
//	Null-Pointer-Exception nei clone() abgefangen
//
//	Revision 1.9  1998/12/15 16:33:24  swtech00
//	Towards new package names.
//
//	Revision 1.8  1998/12/15 13:38:01  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
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
