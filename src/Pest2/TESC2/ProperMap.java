
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
		    if (X[i] != null) {
			// pruefen, ob X[i] inzident zu w ist
			boolean isIncident = false;
			for (int j=0;(j<transition.length)&&(!isIncident);j++) {
			    
			    // Achtung: Vergleich von Objekten, kein Vergleich
			    // von Zeichenketten fuer Statename und Conname
			    if ((w.equalAnchor(transition[j].source)&&
				 X[i].equalAnchor(transition[j].target))||
				(X[i].equalAnchor(transition[j].source)&&
				 w.equalAnchor(transition[j].target))) {
				isIncident = true;
			    }
			}
			// wenn ja, X[i] streichen
			if (isIncident) {
			    X[i] = null;
			    countX--;
			}
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
		for (int col = 0; (col<level.size())&&((sourceRow==-1)||(targetRow==-1)); col++) {
		    MapElement el = (MapElement) level.elementAt(col);
		    if (el.equalAnchor(source)) {
			sourceRow = j;
			sourceColumn = col;
		    }
		    if (el.equalAnchor(target)) {
			targetRow = j;
			targetColumn = col;
		    }
		}
	    }
	    	    
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
	if (res==-1) {
	    return width;
	} else {
	    return res;
	}
    }


    /* Die down_up-Methode optimiert je zwei Schichten der ProperMap, wobei
       eine festgehalten und die andere verändert wird. Und zwar von der
       obersten Schicht nach unten und dann wieder hoch. */
    void down_up() {
	
	int[][] sigma = new int[height][width]; // Umsortierungsmatrix
	for (int i=0;i<height;i++)
	    for (int j=0;j<width;j++) sigma[i][j] = j;

	/* Aufstellung der Inzidenzmatrizen fuer die Transitionen zwischen
	   zwei aufeinanderfolgenden Ebenen */

	int[][][] M = new int[height-1][width][width];
	for (int i=0;i<height-1;i++) 
	    for (int j=0;j<width;j++)
		for (int k=0;k<width;k++) M[i][j][k] = 0;
	MapTransition h;
	int rowL,rowG,colL,colG;
	for (int i=0;i<mapTransition.length;i++) {
	    h = mapTransition[i];
	    if (h.startRow>h.endRow) {
		rowL=h.endRow;rowG=h.startRow;
		colL=h.endColumn;colG=h.startColumn;
		M[rowL][colL][colG] = 1;
	    } else if (h.startRow<h.endRow) {
		rowL=h.startRow;rowG=h.endRow;
		colL=h.startColumn;colG=h.endColumn;
		M[rowL][colL][colG] = 1;
	    }
	    
	}


	/*
	System.out.println("Ausgabe der Inzidenzmatrizen:");
	for (int i=0;i<height-1;i++) {
	    System.out.println("Inzidenzmatrix zwischen "+i+" und "+(i+1));
	    for (int j=0;j<width;j++) {
		for (int k=0;k<width;k++) 
		    System.out.print(M[i][j][k]+" ");
		System.out.println();
	    }
	}
	*/
	

	/* down-Schleife */
	for (int i=0;i<height-1;i++) {
	    barycentricMethod(i,i+1,sigma[i],sigma[i+1],M);
	}
	
	/* up-Schleife */
	for (int i=height-1;i>0;i--) {
	    barycentricMethod(i,i-1,sigma[i],sigma[i-1],M);
	}

	/*
	System.out.println("Umsortierungsmatrix:");
	for (int i=0;i<height;i++) {
	    for (int j=0;j<width;j++)
		System.out.print(sigma[i][j]+" ");
	    System.out.println();
	}
	*/
  
	/* Umordnen der Knoten und Transitionen */
       
	MapElement dummy;
	for (int i=0;i<height;i++) {
	    for (int j=0;j<width;j++) {
		if (j<sigma[i][j]) {
		    dummy = map[i][j];
		    map[i][j] = map[i][sigma[i][j]];
		    map[i][sigma[i][j]] = dummy;
		}
	    }
	}

	for (int i=0;i<mapTransition.length;i++) {
	    h = mapTransition[i];
	    int j;
	    for (j=0;sigma[h.startRow][j]!=h.startColumn;j++) ;
	    h.startColumn = j;
	    for (j=0;sigma[h.endRow][j]!=h.endColumn;j++) ;
	    h.endColumn   = j;
	}
	
    }

    /* Barycentric-Method */
    private void barycentricMethod(int fixedRow,int variableRow,
			      int[] sigmafR,
			      int[] sigmavR,
			      int[][][] M) {

	// Testausgabe
	/*
	System.out.println("barycentricMethod for fixedRow="+fixedRow+" variableRow="+variableRow);
	System.out.print("sigmafR=");
	for (int i=0;i<width;i++) 
	    System.out.print(sigmafR[i]+" ");
	System.out.println();
	System.out.print("sigmavR=");
	for (int i=0;i<width;i++) 
	    System.out.print(sigmavR[i]+" ");
	System.out.println();
	*/

	/* Minimum von fixedRow und variableRow fuer die Indizierung von M */
	int minRow = Math.min(fixedRow,variableRow);
	boolean down = (fixedRow<variableRow);

	/* Testpermutation */
	int[] sigmavRtest = new int[width];
	for (int i=0;i<width;i++) sigmavRtest[i] = sigmavR[i];

	/* Berechnung der Kantenueberschneidungen */

	int KUE;
	if (down) {
	    KUE = kantenueberschneidungen(fixedRow,sigmafR,sigmavR,M);
	} else {
	    KUE = kantenueberschneidungen(variableRow,sigmavR,sigmafR,M);
	}

	/*
	System.out.println("Kantenueberschneidungen zwischen "+fixedRow+" und "+variableRow+" = "+KUE);
	*/

	/* Schleife, bis ,,optimale'' Loesung gefunden wurde oder maximale
	   Anzahl von Schritten durchgefuehrt wurde */

	int iterCount = 4;
	boolean changed;
	int testKUE = KUE;

	do {
	    
	    /* Verbesserung nur versuchen, falls Kantenueberschneidungen
	       existieren */
	    changed = false;
	    if (testKUE>0) {
		
		/* Berechnung der Barycenter */
		float[] B = new float[width];
		int s1,s2;
		int m;
		
		for (int z=0;z<width;z++) {
		    s1 = 0;
		    s2 = 0;
		    for (int y=0;y<width;y++) {
			if (down) {
			    m = M[minRow][sigmafR[y]][z];
			} else {
			    m = M[minRow][z][sigmafR[y]];
			}
			s1 = s1 + (y+1) * m;
			s2 = s2 + m;
		    }
		    if (s2!=0) {
			B[z] = (float) s1 / (float) s2;
		    } else {
			B[z] = (float) (width*width);
		    }
		    /*
		    System.out.println("B["+z+"]="+B[z]);
		    */
		    
		}

		/* Umordnen von sigmavRtest nach den barycenter-Werten */
		int help;
		changed = false;
		for (int i=0;i<width-1;i++)
		    for (int j=i+1;j<width;j++) {
			if (B[sigmavRtest[i]]>B[sigmavRtest[j]]) {
			    changed = true;
			    help = sigmavRtest[i];
			    sigmavRtest[i] = sigmavRtest[j];
			    sigmavRtest[j] = help;
			}
		    }

		/* Berechnung der Kantenueberschneidungen nach dem
		   Umordnen */
		
		if (down) {
		    testKUE = kantenueberschneidungen(minRow,
						      sigmafR,
						      sigmavRtest,M);
		} else {
		    testKUE = kantenueberschneidungen(minRow,
						      sigmavRtest,
						      sigmafR,M);
		}

		/* falls die Testloesung besser ist, dann uebernehme sie */
		if (testKUE<KUE) {
		    KUE = testKUE;
		    /*
		    System.out.println("bessere Loesung mit "+KUE+" Ueberschneidungen");
		    System.out.print("sigmavR=");
		    */

		    for (int i=0;i<width;i++) {
			sigmavR[i] = sigmavRtest[i];
			/*
			System.out.print(sigmavR[i]+" ");
			*/
		    }
		    /*
		    System.out.println();
		    */
		    
		}
	    }
	    
	    iterCount--;
	} while ((iterCount>0)&&(!changed));

    }

    /* Berechnung der Kantenueberschneidungen zwischen Ebene fixedRow
       und fixedRow+1 */
    private int kantenueberschneidungen(int minRow,
					int[] sigmaUpperR,
					int[] sigmaLowerR,
					int[][][] M) {
	int KM = 0;
      
	for (int j=0;j<width-1;j++)
	    for (int k=j+1;k<width;k++)
		for (int alpha=0;alpha<width-1;alpha++)
		    for (int beta=alpha+1;beta<width;beta++)
			KM = KM + 
			    M[minRow][sigmaUpperR[j]]
			    [sigmaLowerR[beta]]*
			    M[minRow][sigmaUpperR[k]]
			    [sigmaLowerR[alpha]];
	return KM;
    }

} // ProperMap
