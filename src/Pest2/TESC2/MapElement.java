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

    int numberLower(int start) {
	int i = 0;
	boolean search = true;
	while (search && (i<lower.size())) {
	    MapTransition mt = (MapTransition) lower.elementAt(i);
	    if (mt.startRow < mt.endRow) {
		search = mt.startColumn <= mt.endColumn;
		i++;
	    }
	    else {
		search = mt.endColumn <= mt.startColumn;
		i++;
	    }
	}
	if (!search) {
	    i = i-1;
	}
	int j;
	for (j = 0; j<i; j++) {
	    ((MapTransition) lower.elementAt(j)).number = j + start;
	}
	for (j = 0; j+i<lower.size();j++) {
	    ((MapTransition) lower.elementAt(lower.size()-j-1)).number=j+i+start;
	}
	return j + i + start;
    }

    void setHeightUpper(int height, int bonus) {
	int i;
	for (i = upper.size()-1; i>=0; i--) {
	    ((MapTransition) upper.elementAt(i)).setHeight(height, bonus);
	}
    }

    MapTransition findOpposite(MapTransition mt) {
	return null;
    }

} // MapElement
