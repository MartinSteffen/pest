
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

public class MapTransition  {
    
    int startRow,startColumn,endRow,endColumn;

    public MapTransition(int srow,int scolumn,int erow,int ecolumn) {
	startRow = srow;
	startColumn = scolumn;
	endRow = erow;
	endColumn = ecolumn;
    }

    public String toString() {
	return "MapTransition("+startRow+","+startColumn+","+endRow+","+endColumn+")";
    }

} // MapTransition
