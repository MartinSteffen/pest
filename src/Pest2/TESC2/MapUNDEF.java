/**
 * MapUNDEF.java
 *
 *
 * Created: Tue Dec  8 12:28:26 1998
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

import absyn.*;

class MapUNDEF extends MapElement {

    private UNDEFINED anchor = null;
    private CPoint Position = null;

    // Konstruktor
    MapUNDEF(UNDEFINED _undef) {
	anchor = _undef;
	Position = new CPoint();
    }

    TrAnchor getAnchor() {
	return anchor;
    }

    boolean equalAnchor(TrAnchor ta) {
	return (anchor == ta);
    }

    CPoint getPosition() {
	return Position;
    }

    CPoint getTransPosition(MapTransition mt) {
	return Position;
    }

    CPoint getUpperTransPosition(int i) {
	return this.getPosition();
    }

    CPoint getLowerTransPosition(int i) {
	return this.getPosition();
    }

    void setPosition(CPoint p) {
	Position = p;
    }

    void setNamePosition(CPoint p) {}

    CRectangle getRect() {
	return new CRectangle(Position);
    }

    MapTransition findOpposite(MapTransition mt) {
	if (upper.contains(mt)) {
	    if (lower.size()>0) {
		return (MapTransition) lower.firstElement();
	    }
	    else {
		return null;
	    }
	}
	else {
	    if (upper.size()>0) {
		return (MapTransition) upper.firstElement();
	    }
	    else {
		return null;
	    }
	}
    }

    public String toString() {
	return "UNDEFINED "+Position;
    }

} // MapUNDEF




