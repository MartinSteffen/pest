
/**
 * MapEndTr.java
 *
 *
 * Created: Tue Dec 22 13:06:59 1998
 *
 * @author
 * @version
 */

package tesc2;

import absyn.Tr;

class MapEndTr extends MapTransition {

    Tr transition = null;

    MapEndTr(int srow,int scolumn,int erow,int ecolumn,Tr trans) {
	super(srow, scolumn, erow, ecolumn);
	transition = trans;
    }

    public String toString() {
	return "MapEndTr("+startRow+","+startColumn+","+endRow+","+endColumn+")";
    }
} // MapEndTr
