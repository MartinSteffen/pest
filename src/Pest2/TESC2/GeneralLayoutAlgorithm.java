
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
    static final int WIDTH_BONUS = 10;
    static final int HEIGHT_BONUS = 10;

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

	/* arrayOfSubstates ordnen nach Größe der substates */

	for (int i=0;i<substateCount-1;i++) {
	    for (int j=i+1;j<substateCount;j++) {
		if ((arrayOfSubstates[i].rect.width*
		     arrayOfSubstates[i].rect.height) <
		    (arrayOfSubstates[i].rect.width*
		     arrayOfSubstates[i].rect.height)) {
		    State help = arrayOfSubstates[i];
		    arrayOfSubstates[i] = arrayOfSubstates[j];
		    arrayOfSubstates[j] = help;
		}
	    }
	}
	    
	/* Substates so anordnen, daß ein Rechteck vollständig 
	   ausgefüllt wird */

	CRectangle[] rect = new CRectangle[substateCount];
	for (int i=0;i<substateCount;i++) {
	    rect[i] = arrayOfSubstates[i].rect;
	}

	int[] widthHeightArray = placeRectangles(rect);

	/**
	 * An dieser Stelle könnten auch noch die evtl. substates von
	 * substate verändert werden, sodaß deren Elemente zentriert
	 * im neuen Rechteck werden
	 */

	for (int i=0;i<substateCount;i++) {
	    substate = arrayOfSubstates[i];
	    int startx = substate.rect.x;
	    int starty = substate.rect.y;

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
	}

	/* Hier wird die Position des Namens des state relativ zu seiner
	   eigenen linken oberen Ecke angegeben. Später (beim Layout des
	   umgebenden state) wird diese Position
	   dann umgewandelt in eine relative bzgl. des umgebenden
	   state */

	s.name.position = new CPoint(5,-5);

	s.rect.width = widthHeightArray[0];
	s.rect.height = widthHeightArray[1];

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
     * Plazierung von Rechtecken 
     */
    int[] placeRectangles(CRectangle[] s) {
	
	CRectangle[] freeRect = new CRectangle[3*s.length];
	int countFreeRect = 0;

	int Width = s[0].width;
	int Height = s[0].height;
	
	s[0].x = 0;
	s[0].y = 0;

	for (int i=1;i<s.length;i++) {
	    	    
	    /* suche kleinstes freies Rechteck, in das s[i] passt */
	    int index = -1;
	    int minArea = -1;
	    for (int j=0;j<freeRect.length;j++) {
		if (freeRect[j] != null) {
		    if ((freeRect[j].width >= s[i].width)&&
			(freeRect[j].height >= s[i].height)) {
			if (minArea == -1) {
			    index = j;
			    minArea = freeRect[j].width*freeRect[j].height;
			} else {
			    if (freeRect[j].width*freeRect[j].height<
				minArea) {
				index = j;
				minArea = freeRect[j].width*freeRect[j].height;
			    }
			}
		    }
		}
	    }

	    if (index == -1) {
		/* füge Rechteck rechts oder unten ein */

		
		if (Width < Height) {
		    /* rechts anfügen */
		    
		    s[i].x = Width;
		    s[i].y = 0;
		    if (Height > s[i].height) {
			freeRect[countFreeRect] = 
			    new CRectangle(s[i].x,s[i].height,
					   s[i].width,Height-s[i].height);
			countFreeRect++;
		    } else if (Height < s[i].height) {
			freeRect[countFreeRect] = 
			    new CRectangle(0,Height,Width,s[i].height-Height);
			countFreeRect++;
		    }
		    Width = Width + s[i].width;
		    Height = Math.max(Height,s[i].height);
		} else {
		    /* unten anfügen */
 
		    s[i].x = 0;
		    s[i].y = Height;
		    if (Width > s[i].width) {
			freeRect[countFreeRect] = 
			    new CRectangle(s[i].width,Height,
					   Width-s[i].width,s[i].height);
			countFreeRect++;
		    } else if (Width < s[i].width) {
			freeRect[countFreeRect] = 
			    new CRectangle(Width,0,s[i].width-Width,Height);
			countFreeRect++;
		    }
		    Width = Math.max(Width,s[i].width);
		    Height = Height + s[i].height;
		}
	    } else {
		/* gefundenes freies Rechteck füllen */
		
		CRectangle r = freeRect[index];
		s[i].x = r.x;
		s[i].y = r.y;
		if (s[i].width < r.width) {
		    freeRect[countFreeRect] = 
			new CRectangle(r.x+s[i].width,r.y,
				       r.width-s[i].width,r.height);
		    countFreeRect++;
		    r.width = s[i].width;
		}

		if (s[i].height < r.height) {
		    freeRect[countFreeRect] = 
			new CRectangle(r.x,r.y+s[i].height,
				       r.width,r.height-s[i].height);
		    countFreeRect++;
		}
		freeRect[index] = null; // Rechteck löschen
	    }
	}


	
	/* freie Rechtecke löschen durch Vergrößern der anderen Rechtecke */

	/* zähle die freien Rechtecke */

	countFreeRect = 0;
	for (int i=0;i<freeRect.length;i++) 
	    if (freeRect[i] != null) countFreeRect++;

	/* lösche die freien Rechtecke */

	while (countFreeRect > 0) {	    
	    for (int i=0;i<freeRect.length;i++) 
		if (freeRect[i] != null) {
		    boolean notFound = true;
		    for (int j=0;(j<s.length)&&notFound;j++) {
			if ((freeRect[i].width>=s[j].width)&&
			    (freeRect[i].x==s[j].x)&&
			    (freeRect[i].y==s[j].y+s[j].height)) {
			    s[j].height += freeRect[i].height;
			    freeRect[i].x += s[j].width;
			    freeRect[i].width -= s[j].width;
			    if (freeRect[i].width == 0) {
				freeRect[i] = null;
				countFreeRect--;
			    }
			    notFound = false;
			} else if ((freeRect[i].height>=s[j].height)&&
				   (freeRect[i].x==s[j].x+s[j].width)&&
				   (freeRect[i].y==s[j].y)) {
			    s[j].width += freeRect[i].width;
			    freeRect[i].y += s[j].height;
			    freeRect[i].height -= s[j].height;
			    if (freeRect[i].height == 0) {
				freeRect[i] = null;
				countFreeRect--;
			    }
			    notFound = false;
			}
		    }
		    for (int j=0;(j<freeRect.length)&&notFound;j++) {
			if (freeRect[j] != null) {
			    if ((freeRect[i].width>=freeRect[j].width)&&
				(freeRect[i].x==freeRect[j].x)&&
				(freeRect[i].y==freeRect[j].y+
				 freeRect[j].height)) {
				freeRect[j].height += freeRect[i].height;
				freeRect[i].x += freeRect[j].width;
				freeRect[i].width -= freeRect[j].width;
				if (freeRect[i].width == 0) {
				    freeRect[i] = null;
				    countFreeRect--;
				}
				notFound = false;
			    } else if ((freeRect[i].height>=
					freeRect[j].height)&&
				       (freeRect[i].x==
					freeRect[j].x+freeRect[j].width)&&
				       (freeRect[i].y==freeRect[j].y)) {
				freeRect[j].width += freeRect[i].width;
				freeRect[i].y += freeRect[j].height;
				freeRect[i].height -= freeRect[j].height;
				if (freeRect[i].height == 0) {
				    freeRect[i] = null;
				    countFreeRect--;
				}
				notFound = false;
			    }
			}
		    }
		}
	}
	int[] res = {Width,Height};
	return res;
    }

} // GeneralLayoutAlgorithm
