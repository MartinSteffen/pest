
/**
 * MapElement.java
 *
 *
 * Created: Tue Dec  8 12:28:26 1998
 *
 * @author Achim Abeling
 * @version
 */

package TESC2;

import Absyn.TrAnchor;

class MapElement  {
    
    TrAnchor anchor = null;

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
