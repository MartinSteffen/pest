package absyn;

import java.io.Serializable;


/**
 * Boolean variable.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Bvar.java,v 1.10 1999-02-09 11:32:55 swtech00 Exp $
 */
public class Bvar  extends Absyn  implements Serializable, Cloneable {
/**
 * Contents of the variable.
 */
    public String var;
/**
 * Constructor.
 */
    public Bvar (String v) {
	var = v;
    };


    public Bvar (String v, Location l) {
	var = v;
	location = l;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
      Location  locationclone  = (location == null) ? null : (Location)location.clone();
      return new Bvar(var,locationclone);
      // Beachte: var vom Typ String braucht nicht geklont zu werden!
    };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Bvar.java,v 1.10 1999-02-09 11:32:55 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.9  1999/01/11 17:23:47  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.8  1998/12/15 16:33:25  swtech00
//	Towards new package names.
//
//	Revision 1.7  1998/12/15 13:38:02  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.6  1998/12/15 07:11:07  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:42:56  swtech00
//	Cloneable
//
//	Revision 1.4  1998/11/27 16:05:31  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:12  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
