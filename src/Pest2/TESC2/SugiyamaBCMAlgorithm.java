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
     * F�hrt das Layout f�r einen Basic_State durch
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
	
	/* $Testausgabe */
	//System.out.println("width="+s.rect.width+" height="+s.rect.height);
	
	/* x- und y-Wert werden beim Layout des Superstate gesetzt */
    };  
    
    /**
     * F&uuml;hrt das Layout f&uuml;r einen Or_State durch
     */
    public void layoutORState(Or_State s) {

	/* $Testausgabe */
	//System.out.println("layoutORState:"+s.name.name);

	/* Sammeln und layout der Substates */
	Vector anchors = new Vector();
	StateList sl = s.substates;
	while (sl != null) {
	    anchors.addElement(new MapState(sl.head));
	    layoutState(sl.head);
	    sl = sl.tail;
	}

	/* Sammeln der Connectors */
	ConnectorList cl = s.connectors;
	while (cl != null) {
	    cl.head.position = new CPoint();
	    anchors.addElement(new MapConnector(cl.head));
	    cl = cl.tail;
	}

	/* Sammeln der Transitionen und der UNDEFINED-Objekten */
	TrList tl = s.trs;
	Vector transitions = new Vector();
	while (tl != null) {
	    if (tl.head.source instanceof UNDEFINED) {
		UNDEFINED newAnchor = new UNDEFINED();
		anchors.addElement(new MapUNDEF(newAnchor));
		tl.head.source = newAnchor;
	    }
	    if (tl.head.target instanceof UNDEFINED) {
		UNDEFINED newAnchor = new UNDEFINED();
		anchors.addElement(new MapUNDEF(newAnchor));
		tl.head.target = newAnchor;
	    }
	    transitions.addElement(tl.head);
	    tl = tl.tail;
	}

	/* Erzeugen von Arrays und berechnen der ProperMap */
	int i;
	MapElement[] al = new MapElement[anchors.size()];
	for (i = anchors.size()-1; i>=0; i--) {
	    al[i] = (MapElement) anchors.elementAt(i);
	}
	Tr[] ntl = new Tr[transitions.size()];
	for (i = transitions.size()-1; i>=0; i--) {
	    ntl[i] = (Tr) transitions.elementAt(i);
	}
	ProperMap pm = new ProperMap(al, ntl);
	
	/* Optimierung der Kantenueberschneidungen */

	//System.out.println("vor down_up()");
	//System.out.println(pm);
	
	pm.down_up();
	
	//System.out.println("nach down_up()");	
	//System.out.println(pm);
	
	/* Zuordnen der Transitionen zu den MapElements */
	MapElement me;
	MapTransition[] mt = pm.getMapTransitions();
	for (i = 0; i<mt.length; i++) {
	    if (mt[i].startRow == mt[i].endRow) {
		me = pm.getElement(mt[i].startRow, mt[i].startColumn);
		me.addLoop(mt[i]);
	    }
	    else {
		if (mt[i].startRow > mt[i].endRow) {
		    me = pm.getElement(mt[i].startRow, mt[i].startColumn);
		    me.addUpper(mt[i]);
		    me = pm.getElement(mt[i].endRow, mt[i].endColumn);
		    me.addLower(mt[i]);
		}
		else {
		    me = pm.getElement(mt[i].startRow, mt[i].startColumn);
		    me.addLower(mt[i]);
		    me = pm.getElement(mt[i].endRow, mt[i].endColumn);
		    me.addUpper(mt[i]);
		}
	    }
	}

	/* ermitteln der Maximalen Zeilenbreite */
	int j, xpos, maxWidth = 0;
	int[] RowWidth = new int[pm.getHeight()];
	for (i = pm.getHeight()-1; i>=0; i--) {
	    xpos = WIDTH_BONUS;
	    for (j = pm.getWidthOfRow(i)-1; j>=0; j--) {
		xpos = xpos + pm.getElement(i, j).getRect().width + WIDTH_BONUS;
		xpos = xpos + (pm.getElement(i, j).countLoops() * WIDTH_BONUS);
	    }
	    if (maxWidth<xpos) {
		maxWidth = xpos;
	    }
	    RowWidth[i] = xpos;
	}

	/* plazieren der SubStates */
	CPoint p;
	int maxHeight = 0;
	int actHeight = 0;
	int ypos = -1;
	for (i = 0; i<pm.getHeight(); i++) {
	    xpos = WIDTH_BONUS + ((maxWidth - RowWidth[i]) / 2);
	    maxHeight = 0;
	    for (j = pm.getWidthOfRow(i)-1; j>=0; j--) {
		actHeight = pm.getElement(i, j).getRect().height;
		if (actHeight>maxHeight) {
		    maxHeight = actHeight;
		}
	    }
	    if (i == 0) {
		ypos = HEIGHT_BONUS;
	    }
	    else {
		if (((RowWidth[i-1]+RowWidth[i]) /8) < HEIGHT_BONUS) {
		    ypos = ypos + HEIGHT_BONUS;
		}
		else {
		    ypos = ypos + ((RowWidth[i-1]+RowWidth[i]) / 8);
		}
	    }
	    for (j = 0; j<pm.getWidthOfRow(i); j++) {
		actHeight = pm.getElement(i, j).getRect().height;
		p = new CPoint(xpos, ypos + ((maxHeight-actHeight) / 2));
		pm.getElement(i, j).setPosition(p);

		/* Plazieren des Substate-Namens */
		p = new CPoint(p);
		p.translate(5,10);
		pm.getElement(i, j).setNamePosition(p);

		xpos = xpos + pm.getElement(i, j).getRect().width + WIDTH_BONUS;
		xpos = xpos + (pm.getElement(i, j).countLoops() * WIDTH_BONUS);

		/* $Testausgabe */
		//System.out.println("Position des States "+pm.getElement(i, j));
	    }
	    ypos = ypos + maxHeight;
	}
	s.rect.height = ypos + HEIGHT_BONUS;
	s.rect.width = maxWidth;
	
	/* $Testausgabe */
	//System.out.println("width="+s.rect.width+" height="+s.rect.height);

	/**
	 * Plazieren der Transitionen 
	 */

	for (i = 0; i<mt.length; i++) {
	    //System.out.println("i="+i);
	    //System.out.println(pm);
	    
	    if (mt[i] != null) {
		transitions = new Vector();
		me = pm.getElement(mt[i].startRow, mt[i].startColumn);
		p = me.getTransPosition(mt[i]);
		transitions.addElement(p);
		MapTransition amt = mt[i];
		if (me.isLoop(amt)) {
		    CPoint h = new CPoint(p);
		    h.translate(WIDTH_BONUS*(me.countLoops()-me.loops.indexOf(amt)), 0);
		    transitions.addElement(h);
		    h = new CPoint(h);
		    h.y = 2*me.getPosition().y+me.getRect().height-p.y;
		    transitions.addElement(h);
		    h = new CPoint(h);
		    h.x = p.x;
		    transitions.addElement(h);
		}
		else {
		    /* Vorgng&auml;ertransitionen sammeln */
		    boolean search = true;
		    while (search) {
			MapTransition omt = me.findOpposite(amt);
			if (omt != null) {
			    for (j = i; (j+1<mt.length) && search; j++) {
				search = (mt[j+1] != omt);
			    }
			    amt = mt[j];
			    mt[j] = null;
			    me = pm.getElement(amt.startRow, amt.startColumn);
			    transitions.insertElementAt(me.getTransPosition(amt), 0);
			    search = true;
			}
			else {
			    search = false;
			}
		    }
		    me = pm.getElement(mt[i].endRow, mt[i].endColumn);
		    transitions.addElement(me.getTransPosition(mt[i]));
		    amt = mt[i];
		    /* Nachfolgertransitionen sammeln */
		    search = true;
		    while (search) {
			MapTransition omt = me.findOpposite(amt);
			if (omt != null) {
			    for (j = i; (j+1<mt.length) && search; j++) {
				search = (mt[j+1] != omt);
			    }
			    amt = mt[j];
			    mt[j] = null;
			    me = pm.getElement(amt.endRow, amt.endColumn);
			    transitions.addElement(me.getTransPosition(amt));
			    search = true;
			}
			else {
			    search = false;
			}
		    }
		}
		/* Punkteliste in Transition eintragen */
		CPoint[] tpos = new CPoint[transitions.size()];
		/* $Testausgabe */
	      	//System.out.print("Transition: ");
		
		for (j = transitions.size()-1; j >= 0; j--) {
		    tpos[j] = (CPoint) transitions.elementAt(j);
		}
		
		/* $ */
		//System.out.println("amt instanceof MapEndTr="+(amt instanceof MapEndTr));		
		
		((MapEndTr) amt).transition.points = tpos;		
		/* Label platzieren */
		p = new CPoint(tpos[0]);
		if (amt.endColumn < amt.startColumn) {
		    p.translate(0, 10);
		}
		else{
		    p.translate(0, -10);
		}
		((MapEndTr) amt).transition.label.position = p;
		/* $Testausgabe */
		//System.out.println("Label:"+p.x+","+p.y);
				
		mt[i] = null;
	    }
	}
	/* $Testausgabe */
	//System.out.println("layoutORStateExited:"+s.name.name);
    };





    /**
     * F�hrt das Layout f�r einen And_State durch
     */

    public void layoutANDState(And_State s) {

	/* $Testausgabe */
	//System.out.println("layoutANDState:"+s.name.name);

	/* Layout f�r Substates durchf�hren */
	StateList stateList = s.substates;
	State substate;
	int substateCount = 0;

	while (stateList != null) {
	    substateCount++;
	    substate = stateList.head;
	    layoutState(substate);
	    stateList = stateList.tail;
	}
		
	/* Substates in Array einf�gen */
	State[] arrayOfSubstates = new State[substateCount];
	stateList = s.substates;
	substateCount = 0;
	while (stateList != null) {
	    substate = stateList.head;
	    arrayOfSubstates[substateCount] = substate;
	    substateCount++;
	    stateList = stateList.tail;
	}

	/* Substates so anordnen, da� ein Rechteck vollst�ndig 
	   ausgef�llt wird */
	
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
	System.out.println("Ausgabe der Plazierungsgraphen f�r den And_State '"+s.name.name+"'");
	System.out.println("Gx=");
	for (int i=0;i<substateCount;i++)
	    System.out.println(i+".: ("+edgesGx[i].getStart()+","+edgesGx[i].getEnd()+","+edgesGx[i].getLength()+")");
	System.out.println("Gy=");
	for (int i=0;i<substateCount;i++)
	    System.out.println(i+".: ("+edgesGy[i].getStart()+","+edgesGy[i].getEnd()+","+edgesGy[i].getLength()+")");
	*/

	/**
	 * An dieser Stelle k�nnen die Plazierungsgraphen optimiert werden
	 */

	/* Berechnung der Koordinaten und Gr��en der Substates aus den
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
	     * An dieser Stelle k�nnten auch noch die evtl. substates von
	     * substate ver�ndert werden, soda� deren Elemente zentriert
	     * im neuen Rechteck werden
	     */

	    substate.rect.x = startx;
	    substate.rect.y = starty;
	    substate.rect.width = endx - startx;
	    substate.rect.height = endy - starty;
	    /* Substate-Namen plazieren */
	    substate.name.position.x = startx+5;
	    substate.name.position.y = starty-5;
	    stateList = stateList.tail;

	    /* $Testausgabe */
	    //System.out.println("Position des States "+substate.name.name+":"+substate.rect);
	    
	}
	
	s.rect.width = posOfVertexGx[interVertexCountGx+1];
	s.rect.height = posOfVertexGy[interVertexCountGy+1];

	/* x- und y-Wert werden beim Layout des Superstate gesetzt */
    };
    
    /**
     * F�hrt das Layout f�r einen beliebigen State durch
     */
    void layoutState(State s) {
		
	s.rect = new CRectangle();
	s.name.position = new CPoint();

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
     * Es folgen Klassen, die f�r das Plazieren der Substates in einem
     * And_State ben�tigt werden
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

