

package Absyn;

import java.io.Serializable;
import java.awt.Point;

/**
 * CPoint.
 * @author Initially provided by Martin Steffen.
 * @version $Id: CPoint.java,v 1.1 1998-12-15 10:01:55 swtech00 Exp $
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


    public Object clone() throws CloneNotSupportedException {
	return super.clone();
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: CPoint.java,v 1.1 1998-12-15 10:01:55 swtech00 Exp $
//      $Log: not supported by cvs2svn $
//
//
//
//----------------------------------------------------------------------


