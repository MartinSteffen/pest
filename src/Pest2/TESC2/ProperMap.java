
/**
 * ProperMap.java
 *
 *
 * Created: Tue Dec  8 12:19:26 1998
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

import java.util.Vector;
import absyn.*;

class ProperMap {

    private int height = 0;
    private int width = 0;
    private MapElement[][] map = null;
    private MapTransition[] mapTransition = null;

    // Konstruktor: erzeugt eine ProperMap mit den angegebenen Knoten
    // (Statename,Conname,UNDEFINED) und den angegebenen Transitionen
    ProperMap(MapElement[] vertex,Tr[] transition) {

	// Phase I: Verteilen der Knoten auf Ebenen
	// Initialisierung
	Vector vlevel = new Vector(vertex.length); // Knoten auf den Ebenen
	for (int i=0;i<vertex.length;i++) 
	    vlevel.insertElementAt(new Vector(),i);
	MapElement[] W = new MapElement[vertex.length]; // Hilfskopie der Knoten
	for (int i=0;i<vertex.length;i++) W[i] = vertex[i];
	int row = 0; // Zaehler fuer die Ebenen
	MapElement[] X = new MapElement[vertex.length]; // Hilfskopie der Knoten
	for (int i=0;i<vertex.length;i++) X[i] = vertex[i];

	// Schleife, bis alle Knoten aus W abgearbeitet sind
	boolean done = false;
	int indexOfX;
	MapElement w;
	int countW=W.length;
	int countX=X.length;

	while (!done) {
	    /*System.out.println("row="+row);
	    
	    System.out.print("W=");
	    for (int i=0;i<W.length;i++) System.out.print(W[i]+" ");
	    System.out.println();*/
	    
	    if (countW == 0) { // W ist leer
		done = true;
	    } else {
		// neuen Knoten aus X waehlen
		for (indexOfX=0;X[indexOfX]==null;indexOfX++) {};
		w = X[indexOfX]; // neuen Knoten waehlen
		X[indexOfX] = null; // Knoten aus X loeschen
		countX--;
		//System.out.println("w="+w);
		
		// Knoten zu Ebene hinzufuegen
		((Vector) vlevel.elementAt(row)).addElement(w); 
		W[indexOfX] = null; // Knoten aus W loeschen
		countW--;
		// aus X alle zu w inzidenten Knoten loeschen
		for (int i=0;i<X.length;i++) {
		    // pruefen, ob X[i] inzident zu w ist
		    boolean isIncident = false;
		    for (int j=0;(j<transition.length)&&(!isIncident);j++) {
			
			// Achtung: Vergleich von Objekten, kein Vergleich
			// von Zeichenketten fuer Statename und Conname
			if (((transition[j].source == w.getAnchor())&&
			     (transition[j].target == X[i].getAnchor()))||
			    ((transition[j].source == X[i].getAnchor())&&
			     (transition[j].target == w.getAnchor()))) {
			    isIncident = true;
			}
		    }
		    // wenn ja, X[i] streichen
		    if (isIncident) {
			X[i] = null;
			countX--;
		    }
		}
		/*System.out.print("X=");
		for (int i=0;i<W.length;i++) System.out.print(X[i]+" ");
		System.out.println();*/

		if (countX == 0) {
		    row++; // naechste Ebene beginnen
		    // X neu setzen
		    for (int i=0;i<W.length;i++) X[i] = W[i];
		    countX = countW;
		}
	    }
	}

	// Ausgabe der Ebenen
	
	int rows = row; // Anzahl der Ebenen
	Vector level;

	/*
	for (int i=0;i<rows;i++) {
	    level = (Vector) vlevel.elementAt(i);
	    System.out.print(i+" : ");
	    
	    for (int j=0;j<level.size();j++) {
		System.out.print(level.elementAt(j)+" ");
	    }
	    System.out.println();
	}
	*/

	// Phase II: Einfuegen der Transitionen

	Vector dummyMapTransition = new Vector();
	TrAnchor source,target;
	int sourceRow=0,targetRow=0,sourceColumn=0,targetColumn=0;
	for (int i=0;i<transition.length;i++) {
	    source = transition[i].source;
	    target = transition[i].target;

	    //System.out.println("source="+source+" target="+target);
	    
	    // Berechnung der Position von source und target
	    sourceRow = -1;
	    targetRow = -1;
	    for (int j=0;(j<rows)&&((sourceRow==-1)||(targetRow==-1));j++) {
		level = (Vector) vlevel.elementAt(j);
		for (int col = 0; (i<level.size())&&((sourceRow==-1)||(targetRow==-1)); col++) {
		    MapElement el = (MapElement) level.elementAt(col);
		    if (el.getAnchor() == source) {
			sourceRow = j;
			sourceColumn = col;
		    }
		    if (el.getAnchor() == target) {
			targetRow = j;
			targetColumn = level.indexOf(target);
		    }
		}
	    }
	    
	    //System.out.println("sourcePos=("+sourceRow+","+sourceColumn+") targetPos=("+targetRow+","+targetColumn+")");
	    
	    // Unterscheidung nach Ebenenabstand
	    int diffRow = Math.abs(sourceRow-targetRow);
	    if (diffRow <= 1) { // aufeinanderfolgende oder gleiche Ebenen
		
		dummyMapTransition.addElement(new MapEndTr(sourceRow,
							   sourceColumn,
							   targetRow,
							   targetColumn,
							   transition[i]));
	    } else if (diffRow > 1) { // entfernte Ebenen (Dummy-Knoten noetig)
		
		int deltaRow;
		if (sourceRow<targetRow) {
		    deltaRow = 1;
		} else {
		    deltaRow = -1;
		}
		int fRow = sourceRow;
		int fColumn = sourceColumn;
		for (int j=sourceRow+deltaRow;j!=targetRow;j=j+deltaRow) {
		    
		    level = (Vector) vlevel.elementAt(j);
		    int levelSize = level.size();
		    level.addElement(new MapUNDEF(new UNDEFINED()));
		    dummyMapTransition.addElement(new MapTransition(fRow,
								    fColumn,
								    j,
								    levelSize));
		    fRow = j;
		    fColumn = levelSize;
		}
		dummyMapTransition.addElement(new MapEndTr(fRow,
							   fColumn,
							   targetRow,
							   targetColumn,
							   transition[i]));
	    }
	}

	// Ausgabe der Ebenen
	//System.out.println("nach Einfuegen der Transitionen");
	
	/*
	for (int i=0;i<rows;i++) {
	    level = (Vector) vlevel.elementAt(i);
	    System.out.print(i+" : ");
	    
	    for (int j=0;j<level.size();j++) {
		System.out.print(level.elementAt(j)+" ");
	    }
	    System.out.println();
	}
	for (int i=0;i<dummyMapTransition.size();i++) {
	    System.out.println((MapTransition) dummyMapTransition.elementAt(i));
	}
	*/

	// Uebertragen von vlevel und dummyMapTransition 
	// in map und mapTransition

	mapTransition = new MapTransition[dummyMapTransition.size()];
	for (int i=0;i<mapTransition.length;i++) {
	    mapTransition[i] = (MapTransition) dummyMapTransition.elementAt(i);
	}

	int maxLevelSize = 0;
	for (int i=0;i<rows;i++) {
	    maxLevelSize=Math.max(maxLevelSize,
				  ((Vector) vlevel.elementAt(i)).size());
	}


	height = rows;
	width = maxLevelSize;
	map = new MapElement[rows][maxLevelSize];
	for (int i=0;i<rows;i++) {
	    level = (Vector) vlevel.elementAt(i);
	    for (int j=0;j<level.size();j++) {
		map[i][j] = (MapElement) level.elementAt(j);
	    }
	}
    }

    void setElement(MapElement me,int row,int column) {
	map[row][column] = me;
    }

    MapElement getElement(int row,int column) {
	return map[row][column];
    }
    
    void setMapTransitions(MapTransition[] mp) {
	mapTransition = mp;
    }

    MapTransition[] getMapTransitions() {
	return mapTransition;
    }

    int getHeight() {
	return height;
    }

    int getWidth() {
	return width;
    }

    int getWidthOfRow(int row) {
	int c,res=-1;
	for (c=0;(c<width)&&(res==-1);c++) {
	    if (map[row][c] == null) res = c;
	}
	if (c==width) {
	    return width;
	} else {
	    return res;
	}
    }

} // ProperMap
