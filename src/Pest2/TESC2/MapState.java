
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

    CPoint getPosition() {
	return new CPoint(state.rect.getLocation());
    }

    void setPosition(CPoint p) {
	state.rect.setLocation(p);
    }

    CRectangle getRect() {
	return state.rect;
    }

} // MapState
