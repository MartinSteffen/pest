/**
 * MapConnector.java
 *
 *
 * Created: Tue Dec  8 12:28:26 1998
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

import absyn.*;

class MapConnector extends MapElement {

    private Connector con = null;

    // Konstruktor
    MapConnector(Connector _con) {
	con = _con;
    }

    TrAnchor getAnchor() {
	return con.name;
    }

    boolean equalAnchor(TrAnchor ta) {
	if (ta.getClass().getName().equals("absyn.Conname")) {
	    return ((Conname) ta).name.equals(con.name.name);
	} else {
	    return false;
	}
    }

    CPoint getPosition() {
	return con.position;
    }

    CPoint getTransPosition(MapTransition mt) {
	return this.getPosition();
    }

    CPoint getUpperTransPosition(int i) {
	return this.getPosition();
    }

    CPoint getLowerTransPosition(int i) {
	return this.getPosition();
    }
	
    void setPosition(CPoint p) {
	con.position = p;
    }

    void setNamePosition(CPoint p) {
	con.name.position = p;
    }

    CRectangle getRect() {
	return new CRectangle(con.position);
    }

    public String toString() {
	return con.name.name+" "+con.position;
    }

} // MapConnector




