package absyn;

import java.io.Serializable;

public class UNDEFINED extends TrAnchor implements Cloneable, Serializable{

  public UNDEFINED () {
  }


  public UNDEFINED (Location l) {
    location = l;
  }

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new UNDEFINED(locationclone);
  };
  
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: UNDEFINED.java,v 1.9 1999-01-12 06:08:19 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.8  1999/01/11 20:22:33  swtech13
//	Default K-Tor hinzugefuegt. (Sonst gibt es UNDEFINED() ohne Params nicht
//	mehr :-( )
//
//	Revision 1.7  1999/01/11 17:23:52  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.6  1998/12/15 16:33:32  swtech00
//	Towards new package names.
//
//	Revision 1.5  1998/12/15 13:38:09  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.4  1998/12/15 07:11:12  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:43:03  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:27  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
