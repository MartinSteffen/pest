

package absyn;

import java.io.Serializable;
import java.awt.Point;

/**
 * CPoint.
 * @author Initially provided by Martin Steffen.
 * @version $Id: CPoint.java,v 1.3 1998-12-15 16:33:26 swtech00 Exp $
 */
public class CPoint extends Point implements Cloneable {
    /**
     * Constructor.
     */
    public CPoint(int x, int y) {super(x,y);};

    /**
     * Constructor.
     */
    public CPoint(Point p) {super(p);};
    
    /**
     * Constructor.
     */
    public CPoint(){super();};


/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return super.clone();
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: CPoint.java,v 1.3 1998-12-15 16:33:26 swtech00 Exp $
//      $Log: not supported by cvs2svn $
//      Revision 1.2  1998/12/15 13:38:02  swtech00
//      exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//      Revision 1.1  1998/12/15 10:01:55  swtech00
//      Klasse
//
//      	          CPoint.java
//
//      hinzugefuegt => Cloneable Version von Point.java.
//      Entsprechend werden in der Abstrakten Syntax alle Vorkommen von Point
//      durch CPoint ersetzt.
//
//
//
//
//----------------------------------------------------------------------


