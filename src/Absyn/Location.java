package absyn;

import java.io.Serializable;

/**
 * Location.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Location.java,v 1.9 1999-01-11 16:28:34 swtech00 Exp $
 */
public class Location implements Serializable, Cloneable {

    int line;
    public Location(int l) { line = l;}

    public String toString() {
        return new Integer(line).toString();
    }

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    return new Location (line);
  };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Location.java,v 1.9 1999-01-11 16:28:34 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.8  1999/01/03 21:42:09  swtech20
//	Adding Location facilities to Absyn
//
//	Revision 1.7  1998/12/15 16:33:29  swtech00
//	Towards new package names.
//
//	Revision 1.6  1998/12/15 13:38:06  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.5  1998/12/15 11:05:18  swtech00
//	Log-Information zugefuegt
//
//	Revision 1.4  1998/12/15 07:11:09  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:42:59  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:18  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
