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

class SugiyamaBCMAlgorithm extends GeneralLayoutAlgorithm {
    

    /**
     * Konstruktor
     */
    SugiyamaBCMAlgorithm(GraphOptimizer _go) {
	super(_go);
    }

  
 
    
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
	int ypos = 0;
	int counter = 1;
	for (i = 0; i<pm.getHeight(); i++) {
	    xpos = WIDTH_BONUS + ((maxWidth - RowWidth[i]) / 2);
	    maxHeight = 0;
	    for (j = pm.getWidthOfRow(i)-1; j>=0; j--) {
		pm.getElement(i,j).setHeightUpper(ypos, HEIGHT_BONUS);
		actHeight = pm.getElement(i, j).getRect().height;
		if (actHeight>maxHeight) {
		    maxHeight = actHeight;
		}
	    }
	    ypos = ypos + (counter * HEIGHT_BONUS);
	    counter = 1;
	    for (j = 0; j<pm.getWidthOfRow(i); j++) {
		counter = pm.getElement(i, j).numberLower(counter);
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
		CPoint h;
		transitions = new Vector();
		me = pm.getElement(mt[i].startRow, mt[i].startColumn);
		p = me.getTransPosition(mt[i]);
		transitions.addElement(p);
		MapTransition amt = mt[i];
		if (me.isLoop(amt)) {
		    h = new CPoint(p);
		    h.translate(WIDTH_BONUS*(me.countLoops()-me.loops.indexOf(amt)), 0);
		    transitions.addElement(h);
		    h = new CPoint(h);
		    h.y = 2*me.getPosition().y+me.getRect().height-p.y;
		    transitions.addElement(h);
		    h = new CPoint(h);
		    h.x = p.x;
		    transitions.addElement(h);
		    p = new CPoint(p);
		    ((MapEndTr) amt).transition.label.position = p;
		}
		else {
		    p = new CPoint(p);
		    p.y = amt.midheight;
		    transitions.addElement(p);
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
			    p = (CPoint) transitions.firstElement();
			    p.y = amt.midheight;
			    p = me.getTransPosition(amt);
			    h = new CPoint(p);
			    h.y = amt.midheight;
			    transitions.insertElementAt(h, 0);
			    transitions.insertElementAt(p, 0);
			    search = true;
			}
			else {
			    search = false;
			}
		    }
		    me = pm.getElement(mt[i].endRow, mt[i].endColumn);
		    amt = mt[i];
		    p = me.getTransPosition(amt);
		    h = new CPoint(p);
		    h.y = amt.midheight;
		    transitions.addElement(h);
		    transitions.addElement(p);
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
			    p = (CPoint) transitions.lastElement();
			    p.y = amt.midheight;
			    p = me.getTransPosition(amt);
			    h = new CPoint(p);
			    h.y = amt.midheight;
			    transitions.addElement(h);
			    transitions.addElement(p);
			    search = true;
			}
			else {
			    search = false;
			}
		    }
		    /* Label platzieren */
		    p = (CPoint) transitions.firstElement();
		    h = (CPoint) transitions.lastElement();
		    if (p.x < h.x) {
			p = new CPoint(p);
		    }
		    else{
			p = new CPoint(h);
		    }
		    p.y = amt.midheight;
		    ((MapEndTr) amt).transition.label.position = p;

		    /* $Testausgabe */
		    //System.out.println("Label:"+p.x+","+p.y);
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
		mt[i] = null;
	    }
	}
	/* $Testausgabe */
	//System.out.println("layoutORStateExited:"+s.name.name);
    };

} // SugiyamaBCMAlgorithm

