package absyn;

import java.io.Serializable;


public class Conname extends TrAnchor implements Serializable, Cloneable{
    public String name;
    public CPoint position;

    public Conname(String n) {
	name  = n;
	position = null;
    };

    public Conname(String n, CPoint p) {
	name  = n;
	position = p;
    };


    public Conname(String n, CPoint p, Location l) {
      name  = n;
      position = p;
      location = l;
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
    
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new Conname(name, positionclone, locationclone);
  };
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Conname.java,v 1.8 1999-01-11 17:23:48 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1999/01/08 23:03:07  swtech14
//	Conname um CPoint erweitert.
//
//	Revision 1.6  1998/12/15 16:33:26  swtech00
//	Towards new package names.
//
//	Revision 1.5  1998/12/15 13:38:03  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.4  1998/12/15 07:11:08  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:42:57  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:14  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
