
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

    CPoint getPosition() {
	return Position;
    }

    void setPosition(CPoint p) {
	Position = p;
    }

    CRectangle getRect() {
	return new CRectangle(Position);
    }

} // MapUNDEF




