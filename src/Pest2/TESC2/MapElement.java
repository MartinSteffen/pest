
/**
 * MapElement.java
 *
 *
 * Created: Tue Dec  8 12:28:26 1998
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

import absyn.*;

abstract class MapElement  {

    abstract TrAnchor getAnchor();
    abstract CPoint getPosition();
    abstract void setPosition(CPoint p);
    abstract CRectangle getRect();

    public String toString() {
	return this.getAnchor().toString();
    }

} // MapElement
