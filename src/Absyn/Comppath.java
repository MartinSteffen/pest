package absyn;

import java.io.Serializable;

/**
 * Operation on path of statenames.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Comppath.java,v 1.12 1999-02-09 10:40:12 swtech00 Exp $
 */
public class Comppath extends Absyn implements Serializable, Cloneable {
/**
 * Path operation.
 */
    public int  pathop;
/**
 * Path.
 */
    public Path   path;
/**
 * Constants
 */
    public final static int IN = 0;
    public final static int ENTERED  = 1;
    public final static int EXITED   = 2;
/**
 * Constructor.
 */
  public Comppath(int op, Path p) {
    pathop  = op;
    path    = p;
  };


  public Comppath(int op, Path p, Location l) {
    pathop  = op;
    path    = p;
    location = l;
  };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();  
    Path      pathclone      = (path == null)     ? null : (Path)path.clone();
    return new Comppath (pathop, pathclone, locationclone);
  };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Comppath.java,v 1.12 1999-02-09 10:40:12 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.11  1999/01/11 17:23:48  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.10  1998/12/15 16:33:26  swtech00
//	Towards new package names.
//
//	Revision 1.9  1998/12/15 13:38:03  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.8  1998/12/15 07:11:07  swtech01
//	Added Serialization to all classes
//
//	Revision 1.7  1998/12/11 17:42:56  swtech00
//	Cloneable
//
//	Revision 1.6  1998/12/01 17:43:07  swtech00
//	Die operatoren als Konstante Felder mit aufgenommen
//
//	Revision 1.5  1998/12/01 10:48:04  swtech00
//	Die Selektor-Felder umbenannt ("s_" entfernt)
//
//	Revision 1.4  1998/11/27 16:07:39  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.3  1998/11/26 16:32:14  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
