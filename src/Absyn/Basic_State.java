package absyn;

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

  public Basic_State (Statename n, CRectangle r, Location l) {
    name = n;
    rect = r;
    location = l;
  };
    
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new Basic_State ((Statename)name.clone(),rect, locationclone);
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Basic_State.java,v 1.10 1999-01-11 17:23:47 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.9  1998/12/15 16:33:25  swtech00
//	Towards new package names.
//
//	Revision 1.8  1998/12/15 13:38:01  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.7  1998/12/15 11:30:38  swtech00
//	Rectangle durch CRectangle ersetzt
//
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
