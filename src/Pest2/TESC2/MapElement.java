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
import java.awt.Point;

abstract class MapElement  {

    Vector loops = new Vector();
    Vector upper = new Vector();
    Vector lower = new Vector();

    abstract TrAnchor getAnchor();
    abstract boolean equalAnchor(TrAnchor ta);
    abstract CPoint getPosition();
    abstract CPoint getTransPosition(MapTransition mt);
    abstract CPoint getUpperTransPosition(int i);
    abstract CPoint getLowerTransPosition(int i);
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

    int countLower() {
	return lower.size();
    }

    Point getEndElementLower(int i) {
	MapTransition mt = (MapTransition) lower.elementAt(i);
	if (mt.startRow > mt.endRow) {
	    return new Point(mt.startRow, mt.startColumn);
	}
	else {
	    return new Point(mt.endRow, mt.endColumn);
	}
    }

    int numberLower(int j, int turn) {
	if (lower.size() > 0) {
	    int i;
	    for (i = lower.size()-1; i>turn; i--) {
		((MapTransition) lower.elementAt(i)).number = j;
		j++;
	    }
	    for (i = 0; i<=turn; i++) {
		((MapTransition) lower.elementAt(i)).number = j;
		j++;
	    }
	}
	return j;
    }

    int getHeightLower() {
	if (lower.size() > 0) {
	    return ((MapTransition) lower.firstElement()).midheight;
	}
	else {
	    return 0;
	}
    }

    void setHeightLower(int height, int bonus) {
	int i;
	for (i = lower.size()-1; i>=0; i--) {
	    ((MapTransition) lower.elementAt(i)).setHeight(height, bonus);
	}
    }

    void setHeightUpper(int height) {
	int i;
	for (i = upper.size()-1; i>=0; i--) {
	    ((MapTransition) upper.elementAt(i)).setHeight(height, 0);
	}
    }

    MapTransition findOpposite(MapTransition mt) {
	return null;
    }

} // MapElement
