package absyn;

import java.io.Serializable;

public class SEvent extends Absyn implements Serializable, Cloneable {  
  public String name;
  public SEvent (String n) {
    name = n;
    
  }

  public SEvent (String n, Location l) {
    name     = n;
    location = l;
  }

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new SEvent(name, locationclone);
  }
};


    
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: SEvent.java,v 1.9 1999-01-11 17:23:51 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.8  1999/01/04 15:35:36  swtech00
//	SEvent als Unterklasse von Absyn (und damit mit Location)
//
//	[Martin]
//
//	Revision 1.7  1998/12/15 16:33:30  swtech00
//	Towards new package names.
//
//	Revision 1.6  1998/12/15 13:38:07  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.5  1998/12/15 07:11:10  swtech01
//	Added Serialization to all classes
//
//	Revision 1.4  1998/12/11 17:43:00  swtech00
//	Cloneable
//
//	Revision 1.3  1998/11/27 09:59:04  swtech20
//	name public
//
//	Revision 1.2  1998/11/26 16:32:22  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
