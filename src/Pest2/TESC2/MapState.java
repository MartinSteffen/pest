/**
 * MapState.java
 *
 *
 * Created: Tue Dec  8 12:28:26 1998
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

import absyn.*;

class MapState extends MapElement {

    private State state = null;

    // Konstruktor
    MapState(State _state) {
	state = _state;
    }

    TrAnchor getAnchor() {
	return state.name;
    }

    boolean equalAnchor(TrAnchor ta) {
	if (ta.getClass().getName().equals("absyn.Statename")) {
	    return ((Statename) ta).name.equals(state.name.name);
	} else {
	    return false;
	}
    }

    CPoint getPosition() {
	return new CPoint(state.rect.getLocation());
    }

    CPoint getTransPosition(MapTransition mt) {
	CPoint p = this.getPosition();
	int pos = loops.indexOf(mt);
	if (pos <0) {
	    int count;
	    pos = upper.indexOf(mt);
	    if (pos<0) {
		p.y = p.y + state.rect.height;
		pos = lower.indexOf(mt);
		count = lower.size();
	    }
	    else {
		count = upper.size();
	    }
	    p.x = p.x + (pos+1) * ((state.rect.width) / (count+1));
	}
	else {
	    p.x = p.x + state.rect.width;
	    p.y = p.y + (pos+1) * ((state.rect.height) / (1+loops.size()*2));
	}
	return p;
    }

    void setPosition(CPoint p) {
	state.rect.setLocation(p);
    }

    void setNamePosition(CPoint p) {
	state.name.position = p;
    }

    CRectangle getRect() {
	return state.rect;
    }

    public String toString() {
	return state.name.name+" "+state.rect;
    }

    void addUpper(MapTransition mt) {
	int column;
	if (mt.startRow > mt.endRow) {
	    column = mt.endColumn;
	}
	else {
	    column = mt.startColumn;
	}
	MapTransition h;
	int i = 0;
	boolean notfound = true;
	while ((i<upper.size()) && notfound) {
	    h = (MapTransition) upper.elementAt(i);
	    if (h.startRow > h.endRow) {
		if (column <= h.endColumn) {
		    notfound = false;
		}
		else {
		    i++;
		}
	    }
	    else {
		if (column <= h.startColumn) {
		    notfound = false;
		}
		else {
		    i++;
		}
	    }
	}
	upper.insertElementAt(mt, i);
    }

    void addLower(MapTransition mt) {
	int column;
	if (mt.startRow < mt.endRow) {
	    column = mt.endColumn;
	}
	else {
	    column = mt.startColumn;
	}
	MapTransition h;
	int i = 0;
	boolean notfound = true;
	while ((i<lower.size()) && notfound) {
	    h = (MapTransition) lower.elementAt(i);
	    if (h.startRow < h.endRow) {
		if (column <= h.endColumn) {
		    notfound = false;
		}
		else {
		    i++;
		}
	    }
	    else {
		if (column <= h.startColumn) {
		    notfound = false;
		}
		else {
		    i++;
		}
	    }
	}
	lower.insertElementAt(mt, i);
    }

} // MapState
