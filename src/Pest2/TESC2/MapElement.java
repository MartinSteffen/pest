
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

class MapElement  {
    
    protected TrAnchor anchor = null;

    // Default-Konstruktor, Dummy-Element
    MapElement() {
	anchor = null;
    }

    // Konstruktor: falls _anchor == null, dann Dummy-Element
    MapElement(TrAnchor _anchor) {
	anchor = _anchor;
    }

    TrAnchor getAnchor() {
	return anchor;
    }

    public String toString() {
	return anchor.toString();
    }

} // MapElement

class MapState extends MapElement {

    private State state = null;

    // Konstruktor
    MapState(State _state) {
	state = _state;
    }

    TrAnchor getAnchor() {
	return state.name;
    }

    public String toString() {
	return this.getAnchor().toString();
    }

} // MapState

class MapConnector extends MapElement {

    private Connector con = null;

    // Konstruktor
    MapConnector(Connector _con) {
	con = _con;
    }

    TrAnchor getAnchor() {
	return con.name;
    }

    public String toString() {
	return this.getAnchor().toString();
    }

} // MapConnector

class MapUNDEF extends MapElement {

    Point Position = null;

    // Konstruktor
    MapUNDEF(TrAnchor _anchor) {
	anchor = _anchor;
    }

    TrAnchor getAnchor() {
	return state.name;
    }

    public String toString() {
	return this.getAnchor().toString();
    }

} // MapUNDEF
