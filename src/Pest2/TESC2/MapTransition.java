
/**
 * MapTransition.java
 *
 *
 * Created: Tue Dec  8 13:06:59 1998
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

class MapTransition  {
    
    int startRow,startColumn,endRow,endColumn;
    int number = 0;
    int midheight = 0;

    MapTransition(int srow,int scolumn,int erow,int ecolumn) {
	startRow = srow;
	startColumn = scolumn;
	endRow = erow;
	endColumn = ecolumn;
    }

    void setHeight(int height, int bonus) {
	midheight = height + (number * bonus);
    }

    public String toString() {
	return "MapTransition("+startRow+","+startColumn+","+endRow+","+endColumn+")";
    }

} // MapTransition
