
/**
 * GeneralLayoutAlgorithm.java
 *
 *
 * Created: Fri Jan 22 10:35:28 1999
 *
 * @author Achim Abeling
 * @version
 */

package tesc2;

import absyn.*;

abstract class GeneralLayoutAlgorithm implements LayoutAlgorithm {
    
    /**
     * Klassenkonstanten
     */
    static final int WIDTH_BONUS = 15;
    static final int HEIGHT_BONUS = 15;

    /**
     * Instanzvariablen
     */
    GraphOptimizer go;

    /**
     * Konstruktor
     */
    GeneralLayoutAlgorithm(GraphOptimizer _go) {
	go = _go;
    }
  
    /**
     * Führt das Layout für einen Basic_State durch
     */
    public void layoutBasicState(Basic_State s) {
	
	/* $Testausgabe */
	//System.out.println("layoutBasicState:"+s.name.name);
	
	String text = s.name.name;

	int textWidth;
	int textHeight;

	if (go.fm != null) {	
	    textWidth = go.fm.stringWidth(text);
	    textHeight = go.fm.getHeight();
	} else {
	    textWidth=30;
	    textHeight=20;
	}

	s.rect.width = textWidth + WIDTH_BONUS;
	s.rect.height = textHeight + HEIGHT_BONUS;

	/* Hier wird die Position des Namens des state relativ zu seiner
	   eigenen linken oberen Ecke angegeben. Später (beim Layout des
	   umgebenden state) wird diese Position
	   dann umgewandelt in eine relative bzgl. des umgebenden
	   state */

	s.name.position = new CPoint((s.rect.width-textWidth)/2,
				     (s.rect.height+textHeight)/2);

	/* $Testausgabe */
	//System.out.println("width="+s.rect.width+" height="+s.rect.height);
	
	/* x- und y-Wert werden beim Layout des Superstate gesetzt */
    };  
    
    /**
     * Führt das Layout für einen And_State durch
     */

    public void layoutANDState(And_State s) {

	/* $Testausgabe */
	//System.out.println("layoutANDState:"+s.name.name);

	/* Layout für Substates durchführen */
	StateList stateList = s.substates;
	State substate;
	int substateCount = 0;

	while (stateList != null) {
	    substateCount++;
	    substate = stateList.head;
	    layoutState(substate);
	    stateList = stateList.tail;
	}
		
	/* Substates in Array einfügen */
	State[] arrayOfSubstates = new State[substateCount];
	stateList = s.substates;
	substateCount = 0;
	while (stateList != null) {
	    substate = stateList.head;
	    arrayOfSubstates[substateCount] = substate;
	    substateCount++;
	    stateList = stateList.tail;
	}

	/* Substates so anordnen, daß ein Rechteck vollständig 
	   ausgefüllt wird */
	
	/* Erzeugen der Plazierungsgraphen Gx und Gy */
	PlacingGraphEdge[] edgesGx = new PlacingGraphEdge[substateCount];
	PlacingGraphEdge[] edgesGy = new PlacingGraphEdge[substateCount];
	edgesGx[0] = new PlacingGraphEdge(-1,-2,
					  arrayOfSubstates[0].rect.width);
	edgesGy[0] = new PlacingGraphEdge(-1,-2,
					  arrayOfSubstates[0].rect.height);
	int interVertexCountGx = 0;
	int interVertexCountGy = 0;
	int H = arrayOfSubstates[0].rect.height;
	int W = arrayOfSubstates[0].rect.width;

	for (int i=1;i<substateCount;i++) {
	    
	    if (H<=W) {
		edgesGy[i] = 
		    new PlacingGraphEdge(interVertexCountGy,-2,
					 arrayOfSubstates[i].rect.height);
		for (int j=0;j<i;j++) {
		    if (edgesGy[j].getEnd()==-2) 
			edgesGy[j].setEnd(interVertexCountGy);
		}
		interVertexCountGy++;
		edgesGx[i] =
		    new PlacingGraphEdge(-1,-2,
					 arrayOfSubstates[i].rect.width);
		H = H + arrayOfSubstates[i].rect.height;
		W = Math.max(W,arrayOfSubstates[i].rect.width);
	    } else {
		edgesGx[i] = 
		    new PlacingGraphEdge(interVertexCountGx,-2,
					 arrayOfSubstates[i].rect.width);
		for (int j=0;j<i;j++) {
		    if (edgesGx[j].getEnd()==-2) 
			edgesGx[j].setEnd(interVertexCountGx);
		}
		interVertexCountGx++;
		edgesGy[i] =
		    new PlacingGraphEdge(-1,-2,
					 arrayOfSubstates[i].rect.height);
		H = Math.max(H,arrayOfSubstates[i].rect.height);
		W = W + arrayOfSubstates[i].rect.width;
	    }
	}

	/* Ausgabe der Plazierungsgraphen */
	
	/*
	System.out.println("Ausgabe der Plazierungsgraphen für den And_State '"+s.name.name+"'");
	System.out.println("Gx=");
	for (int i=0;i<substateCount;i++)
	    System.out.println(i+".: ("+edgesGx[i].getStart()+","+edgesGx[i].getEnd()+","+edgesGx[i].getLength()+")");
	System.out.println("Gy=");
	for (int i=0;i<substateCount;i++)
	    System.out.println(i+".: ("+edgesGy[i].getStart()+","+edgesGy[i].getEnd()+","+edgesGy[i].getLength()+")");
	*/

	/**
	 * An dieser Stelle können die Plazierungsgraphen optimiert werden
	 */

	/* Berechnung der Koordinaten und Größen der Substates aus den
	   Plazierungsgraphen */

	int[] posOfVertexGx = new int[interVertexCountGx+2];
	int[] posOfVertexGy = new int[interVertexCountGy+2];
	posOfVertexGx[0] = 0; // entspricht der linkesten Kante in Gx
	posOfVertexGy[0] = 0; // entspricht der linkesten Kante in Gy

	for (int i=1;i<interVertexCountGx+1;i++) {
	    posOfVertexGx[i] = 0;
	    
	    for (int j=0;j<substateCount;j++) {
		if (edgesGx[j].getEnd()==i-1) {
		    posOfVertexGx[i] = 
			Math.max(posOfVertexGx[i],
				 posOfVertexGx[edgesGx[j].getStart()+1]+
				 edgesGx[j].getLength());
		}
		    
	    }
	}
	int k = interVertexCountGx+1;
	posOfVertexGx[k] = 0;	    
	for (int j=0;j<substateCount;j++) {
	    if (edgesGx[j].getEnd()==-2) {
		posOfVertexGx[k] = 
		    Math.max(posOfVertexGx[k],
			     posOfVertexGx[edgesGx[j].getStart()+1]+
			     edgesGx[j].getLength());
	    }
	    
	}
	for (int i=1;i<interVertexCountGy+1;i++) {
	    posOfVertexGy[i] = 0;

	    for (int j=0;j<substateCount;j++) {
		if (edgesGy[j].getEnd()==i-1) {
		    posOfVertexGy[i] = 
			Math.max(posOfVertexGy[i],
				 posOfVertexGy[edgesGy[j].getStart()+1]+
				 edgesGy[j].getLength());
		}
	    }
	}
	k = interVertexCountGy+1;
	posOfVertexGy[k] = 0;	    
	for (int j=0;j<substateCount;j++) {
	    if (edgesGy[j].getEnd()==-2) {
		posOfVertexGy[k] = 
		    Math.max(posOfVertexGy[k],
			     posOfVertexGy[edgesGy[j].getStart()+1]+
			     edgesGy[j].getLength());
	    }
	    
	}
		
	stateList = s.substates;
	substateCount = 0;
	while (stateList != null) {
	    substate = stateList.head;
	    int startx = edgesGx[substateCount].getStart();
	    int endx   = edgesGx[substateCount].getEnd();
	    int starty = edgesGy[substateCount].getStart();
	    int endy   = edgesGy[substateCount].getEnd();
	    substateCount++;
	    if (startx == -1) {
		startx = posOfVertexGx[0];
	    } else {
		startx = posOfVertexGx[startx+1];
	    }
	    if (endx == -2) {
		endx = posOfVertexGx[interVertexCountGx+1];
	    } else {
		endx = posOfVertexGx[endx+1];
	    }
	    if (starty == -1) {
		starty = posOfVertexGy[0];
	    } else {
		starty = posOfVertexGy[starty+1];
	    }
	    if (endy == -2) {
		endy = posOfVertexGy[interVertexCountGy+1];
	    } else {
		endy = posOfVertexGy[endy+1];
	    }

	    /**
	     * An dieser Stelle könnten auch noch die evtl. substates von
	     * substate verändert werden, sodaß deren Elemente zentriert
	     * im neuen Rechteck werden
	     */

	    substate.rect.x = startx;
	    substate.rect.y = starty;
	    substate.rect.width = endx - startx;
	    substate.rect.height = endy - starty;

	    if (substate instanceof And_State)
		resizeAnd_State((And_State) substate);

	    /* Substate-Namen plazieren */
	    if (substate instanceof Basic_State) {
		substate.name.position.x = startx+5;
		substate.name.position.y = starty+10;
	    } else if (substate instanceof And_State) {
		substate.name.position.x = startx+5;
		substate.name.position.y = starty-5;
	    } else {
		substate.name.position.x = startx+5;
		substate.name.position.y = starty+10;
	    }
	    stateList = stateList.tail;

	    /* $Testausgabe */
	    //System.out.println("Position des States "+substate.name.name+":"+substate.rect);
	    
	}
	
	s.rect.width = posOfVertexGx[interVertexCountGx+1];
	s.rect.height = posOfVertexGy[interVertexCountGy+1];

	/* Hier wird die Position des Namens des state relativ zu seiner
	   eigenen linken oberen Ecke angegeben. Später (beim Layout des
	   umgebenden state) wird diese Position
	   dann umgewandelt in eine relative bzgl. des umgebenden
	   state */

	s.name.position = new CPoint(5,-5);
	
	/* x- und y-Wert werden beim Layout des Superstate gesetzt */

	
    };
    
    /* Passt die substate-Groessen von s an */

    private void resizeAnd_State(And_State s) {

	int newWidth = s.rect.width;
	int newHeight = s.rect.height;
	
	/* es werden diejenigen substates von s vergroessert, die ganz
	   rechts und ganz unten liegen. Das sind diejenigen, deren
	   Werte x+width bzw. y+height maximal sind */

	/* Bestimmung der Maximalwerte */
	int xPlusWidthMax = 0;
	int yPlusHeightMax = 0;

	StateList substateList = s.substates;
	State substate = null;
	int actX;
	int actY;

	while (substateList != null) {
	    substate = substateList.head;
	    
	    actX = substate.rect.x+substate.rect.width;
	    actY = substate.rect.y+substate.rect.height;
	    if (actX > xPlusWidthMax) {
		xPlusWidthMax = actX;
	    }
	    if (actY > yPlusHeightMax) {
		yPlusHeightMax = actY;
	    }
	    substateList = substateList.tail;
	}
	
	/* Vergroessern der in Frage kommenden substates */
	boolean stateChanged;
	substateList = s.substates;
	while (substateList != null) {
	    stateChanged = false;
	    substate = substateList.head;
	    actX = substate.rect.x+substate.rect.width;
	    actY = substate.rect.y+substate.rect.height;
	    if (actX == xPlusWidthMax) {		
		substate.rect.width = newWidth - substate.rect.x;
		stateChanged = true;
	    }
	    if (actY == yPlusHeightMax) {
		substate.rect.height = newHeight - substate.rect.y;
		stateChanged = true;
	    }
	    /* falls es sich bei diesem substate um einen And_State handelt,
	       muss dieser genauso behandelt werden */
	    if ((substate instanceof And_State) && (stateChanged)) {
		resizeAnd_State((And_State) substate);
	    }
	    substateList = substateList.tail;
	}
    };

    /**
     * Führt das Layout für einen beliebigen State durch
     */
    void layoutState(State s) {
		
	s.rect = new CRectangle();
	s.name.position = new CPoint();

	Class classOfState = s.getClass();
	
	if (s instanceof Basic_State) {
	    layoutBasicState((Basic_State) s);
	} else if (s instanceof Or_State) {
	    layoutORState((Or_State) s);
	} else if (s instanceof And_State) {
	    layoutANDState((And_State) s);
	} else {
	    System.out.println("layoutState: classnameOfState="+classOfState.getName());
	}
    }

    /**
     * Fuehrt das Layout fuer ein Statechart durch
     */
    public void layoutStatechart(Statechart sc) {
	
	layoutState(sc.state);

	sc.state.rect.x = 0;
	sc.state.rect.y = 0;
    };

    /**
     * Es folgen Klassen, die für das Plazieren der Substates in einem
     * And_State benötigt werden
     */

    private class PlacingGraphEdge {
	private int start,end;
	private int length;

	PlacingGraphEdge(int _s,int _e,int _l) {
	    start = _s;
	    end = _e;
	    length = _l;
	}
	void setStart(int _s) {start = _s;}
	void setEnd(int _e) {end = _e;}
	void setLength(int _l) {length = _l;}
	int getStart() {return start;}
	int getEnd() {return end;}
	int getLength() {return length;}
    }   
    
} // GeneralLayoutAlgorithm
