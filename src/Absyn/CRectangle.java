package absyn;

import java.io.Serializable;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;


/**
 * CRectangle.
 * @author Initially provided by Martin Steffen.
 * @version $Id: CRectangle.java,v 1.3 1998-12-15 16:33:26 swtech00 Exp $
 */
public class CRectangle extends Rectangle implements Cloneable, Serializable {
    /**
     * Constructor.
     */
    public CRectangle(int x, int y, int width, int height) {super(x,y, width, height);};
    /**
     * Constructor.
     */
    public CRectangle(int width, int height) {super(width, height);};

    /**
     * Constructor.
     */
    public CRectangle(CPoint p, Dimension d) {super(p,d);};

    /**
     * Constructor.
     */
    public CRectangle(CPoint p) {super(p);};

    /**
     * Constructor.
     */
    public CRectangle(Dimension d) {super(d);};

    /**
     * Constructor.
     */
    public CRectangle(CRectangle r) {super(r);};
    
    /**
     * Constructor.
     */
    public CRectangle(){super();};


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
//	$Id: CRectangle.java,v 1.3 1998-12-15 16:33:26 swtech00 Exp $
//      $Log: not supported by cvs2svn $
//      Revision 1.2  1998/12/15 13:38:03  swtech00
//      exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//      Revision 1.1  1998/12/15 11:23:26  swtech00
//      kloniebare Rectangles hinzugefuegt, alos
//
//      	 CRectangle.
//
//      CRectangle besitzt die selben Konstuktoren wie Rectangle auch. Zusatzlich
//      unterst"utzt es die Methode clone.
//
//
//
//
//----------------------------------------------------------------------


