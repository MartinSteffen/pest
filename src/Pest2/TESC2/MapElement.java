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
import java.util.Vector;

abstract class MapElement  {

    Vector loops = new Vector();
    Vector upper = new Vector();
    Vector lower = new Vector();

    abstract TrAnchor getAnchor();
    abstract boolean equalAnchor(TrAnchor ta);
    abstract CPoint getPosition();
    abstract CPoint getTransPosition(MapTransition mt);
    abstract void setPosition(CPoint p);
    abstract void setNamePosition(CPoint p); 
    abstract CRectangle getRect();

    void addLoop(MapTransition mt) {
	loops.addElement(mt);
    }

    boolean isLoop(MapTransition mt) {
	return loops.contains(mt);
    }

    int countLoops() {
	return loops.size();
    }

    void addUpper(MapTransition mt) {
	upper.addElement(mt);
    }

    void addLower(MapTransition mt) {
	lower.addElement(mt);
    }

    MapTransition findOpposite(MapTransition mt) {
	return null;
    }

} // MapElement
