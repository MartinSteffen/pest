
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

    CPoint getPosition() {
	return con.position;
    }

    void setPosition(CPoint p) {
	con.position = p;
    }

    CRectangle getRect() {
	return new CRectangle(con.position);
    }

} // MapConnector




