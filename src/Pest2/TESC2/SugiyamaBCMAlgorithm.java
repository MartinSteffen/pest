/**
 * SugiyamaBCMAlgorithm.java
 *
 *
 * Created: Mon Dec 14 12:59:50 1998
 *
 * @author Software Technologie 19
 * @version
 */

package tesc2;

import absyn.*;
import java.util.Vector;
import java.awt.*;

class SugiyamaBCMAlgorithm implements LayoutAlgorithm {
    
/**
     * Klassenkonstanten
     */
    static final int WIDTH_BONUS = 10;
    static final int HEIGHT_BONUS = 10;

    /**
     * Instanzvariablen
     */
    GraphOptimizer go;

    /**
     * Konstruktor
     */
    SugiyamaBCMAlgorithm(GraphOptimizer _go) {
	go = _go;
    }

  
    /**
     * Führt das Layout für einen Basic_State durch
     */
    public void layoutBasicState(Basic_State s) {
	
	/* $Testausgabe */
	System.out.println("layoutBasicState:"+s.name.name);
	
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
	
	/* $Testausgabe */
	System.out.println("width="+s.rect.width+" height="+s.rect.height);
	
	/* x- und y-Wert werden beim Layout des Superstate gesetzt */
    };  
    
    /**
     * F&uuml;hrt das Layout f&uuml;r einen Or_State durch
     */
    public void layoutORState(Or_State s) {
	Vector anchors = new Vector();
	StateList sl = s.substates;

	/* $Testausgabe */
	System.out.println("layoutORState:"+s.name.name);

	while (sl != null) {
	    anchors.addElement(new MapState(sl.head));
	    sl = sl.tail;
	}
	ConnectorList cl = s.connectors;
	while (cl != null) {
	    anchors.addElement(new MapConnector(cl.head));
	    cl = cl.tail;
	}
	TrList tl = s.trs;
	Vector transitions = new Vector();
	while (tl != null) {
	    /* feststellen aller UNDEFINED in tl und anfu"gen an anchors */
	    transitions.addElement(tl.head);
	    tl = tl.tail;
	}
	MapElement[] al = new MapElement[anchors.size()];
	for (int i = 0; i<anchors.size(); i++) {
	    al[i] = (MapElement) anchors.elementAt(i);
	}
	Tr[] ntl = new Tr[transitions.size()];
	for (int i = 0; i<transitions.size(); i++) {
	    ntl[i] = (Tr) transitions.elementAt(i);
	}
	ProperMap pm = new ProperMap(al, ntl);
    };

    /**
     * Führt das Layout für einen And_State durch
     */
    public void layoutANDState(And_State s) {

	/* $Testausgabe */
	System.out.println("layoutANDState:"+s.name.name);

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
	    stateList = stateList.tail;

	    /* Ausgabe */
	    System.out.println("Position des States "+substate.name.name+":"+substate.rect);
	    
	}
	
	s.rect.width = posOfVertexGx[interVertexCountGx+1];
	s.rect.height = posOfVertexGy[interVertexCountGy+1];

	/* x- und y-Wert werden beim Layout des Superstate gesetzt */
    };
    
    /**
     * Führt das Layout für einen beliebigen State durch
     */
    void layoutState(State s) {
		
	s.rect = new CRectangle();

	Class classOfState = s.getClass();
	
	if (classOfState.getName().compareTo("absyn.Basic_State")==0) {
	    layoutBasicState((Basic_State) s);
	} else if (classOfState.getName().compareTo("absyn.Or_State")==0) {
	    layoutORState((Or_State) s);
	} else if (classOfState.getName().compareTo("absyn.And_State")==0) {
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
} // SugiyamaBCMAlgorithm
