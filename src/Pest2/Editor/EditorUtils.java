package editor;

import absyn.*;
import java.awt.*;
import java.awt.event.*;

public class EditorUtils {

    private static int stateCount = 0;

        // **************************************************
        // Hier kommen Methoden fr MouseEvents
        // **************************************************

    private static int getCoordX(MouseEvent e,Editor editor)
    {
	return (e.getX()+editor.scrollX);
    }

    private static int getCoordY(MouseEvent e, Editor editor)
    {
	return (e.getY()+editor.scrollY);
    } 




// **************************************************************************

    public static void createStateMousePressed(MouseEvent e, Editor editor) {

        editor.startPoint = new Point(getCoordX(e,editor),getCoordY(e,editor));
        editor.newRect = new CRectangle(getCoordX(e,editor), getCoordY(e,editor),0,0);
    }

// **************************************************************************

    public static void createStateMouseDragged(MouseEvent e, Editor editor) {
        
        Graphics g = editor.getGraphics();

        // altes Rectangle loeschen

        g.setColor(editor.getBackground());
        g.drawRoundRect(editor.newRect.x-editor.scrollX, editor.newRect.y-editor.scrollY,
                        editor.newRect.width, editor.newRect.height,10,10);

        // neues rectangle

        int x1 = editor.startPoint.x;
        int y1 = editor.startPoint.y;
        int x2 = getCoordX(e,editor);
        int y2 = getCoordY(e,editor);

        // evtl. vertauschen der Koordinaten

        int help;
        if (x1 > x2) {help = x1; x1 = x2; x2 = help; }
        if (y1 > y2) {help = y1; y1 = y2; y2 = help; }      
  
        editor.newRect = new CRectangle(x1,y1,(x2-x1), (y2-y1) );                

        // ueberpruefe, ob sich der neue Zustand mit einem anderen
        // Zustand ueberschneidet.

        g.setColor(Color.red);
        g.drawRoundRect(editor.newRect.x-editor.scrollX, editor.newRect.y-editor.scrollY, editor.newRect.width, editor.newRect.height,10,10);
        editor.actionOk = true;

        showStates(editor);
                
        StateList list = editor.stateList;

        while (list != null) {
            if (!(editor.newRect.intersection(list.head.rect)
                    .equals(list.head.rect))
              & (!(editor.newRect.intersection(list.head.rect)
                    .equals(editor.newRect)))
              & (editor.newRect.intersects(list.head.rect))) {
                 
                g.setColor(Color.gray);
                g.drawRoundRect(editor.newRect.x-editor.scrollX, editor.newRect.y-editor.scrollY, editor.newRect.width, editor.newRect.height,10,10);
                editor.actionOk = false;
            }
            list = list.tail;
        }
    }

// **************************************************************************

    public static void createStateMouseReleased(MouseEvent e, Editor editor) {

        Graphics g = editor.getGraphics();

        if (editor.newRect.width < 10 | editor.newRect.height < 10)
            editor.actionOk = false;

        if (editor.actionOk) { // Zustand darf erzeugt werden

            stateCount++;
            Or_State newState = new Or_State(new Statename("Nr. "+stateCount), null, null, null, null);
            newState.rect = editor.newRect;

            // *** Bestimme den Vater
                        
            State father = getInnermostStateOf(editor.newRect.x,
                                               editor.newRect.y, editor);

            // *** Falls Vater = Or_State, splitte seine Listen
            // *** sonst mache einen OrState aus ihm

            Or_State orFather = null;

            if (father instanceof Or_State) 
                orStateSplit((Or_State) father, newState, editor);

            if (father instanceof Basic_State) {

                orFather = new Or_State(father.name,
                                      null, null, null, null);
                orFather.rect = father.rect;

                // *** Bestimme den Groávater, er ist vorhanden, weil
                // *** Vater ein Basic_State
                
                State grandfather = getFatherOf(father, editor);

                // trage in die groávater.substates den Vater um

                StateList help = null;

                if (grandfather instanceof Or_State) {
                    if (((Or_State) grandfather).substates.head.equals(father))
                        ((Or_State) grandfather).substates = new StateList(orFather,
                                                ((Or_State) grandfather).substates.tail);
                    help = ((Or_State) grandfather).substates;
                }
                else {
                    if (((And_State) grandfather).substates.head.equals(father))
                        ((And_State) grandfather).substates = new StateList(orFather,
                                                ((And_State) grandfather).substates.tail);
                    help = ((And_State) grandfather).substates;
                }
                while (help.tail != null) {
                    if (help.tail.head.equals(father))
                        help.tail = new StateList(orFather, help.tail.tail);
                    help = help.tail;
                }
            }

            // *** und h„nge newState entsprechend als BasicState oder
            // *** ORState in orFather.substates ein
            // *** sowie in editor.stateList

            if (orFather == null) orFather = (Or_State) father;

            if (newState.substates == null & newState.trs == null
              & newState.connectors == null) {
                Basic_State neu = new Basic_State(
                        new Statename("Nr. "+stateCount), newState.rect);
                orFather.substates = new StateList(neu, orFather.substates);
                orFather.defaults = new StatenameList(neu.name, orFather.defaults);
            }
            else {
                orFather.substates = new StateList(newState, orFather.substates);
                orFather.defaults = new StatenameList(newState.name, orFather.defaults);
            }
        }
        else {  // Zustand wieder vom Fenster loeschen
            g.setColor(editor.getBackground());
            g.drawRoundRect(editor.newRect.x, editor.newRect.y, editor.newRect.width, editor.newRect.height,10,10);
        }

        editor.startPoint = null;

        // alle jetzt bestehenden States zeichnen

        editor.stateList = getSubStateList(editor.statechart.state);
        showStates(editor);
    }
  
// **************************************************************************

    public static void andStateMouseMoved(MouseEvent e, Editor editor) {
    
        // *** loesche eine evtl. schon gezeigte Linie

        if (editor.endPoint != null) 
            showAndLine(editor.startPoint, editor.endPoint, editor.getBackground(), editor);

        // *** In welchem Zustand liegt der Mauszeiger gerade?

        State inState = getInnermostStateOf(getCoordX(e,editor), getCoordY(e,editor), editor);
        if (inState.equals(editor.statechart.state)) inState = null;

        // *** Weiter nur, wenn Maus in einem Zustand ist

        if (inState != null) {

            // *** Bestimme Start und Endpunkt der Linie
            // *** Darf die Linie gezogen werden? Spalte in 2 H„lften

            CRectangle a = null;
            CRectangle b = null;

            if (getSideOf(inState, getCoordX(e,editor), getCoordY(e,editor)) == 0) {
     
                // *** senkrechte TrennLinie
           
                editor.startPoint = new Point(getCoordX(e,editor), inState.rect.y);
                editor.endPoint   = new Point(getCoordX(e,editor), inState.rect.y+ inState.rect.height);

                a = new CRectangle(inState.rect.x, inState.rect.y,
                                  editor.endPoint.x-inState.rect.x,
                                  inState.rect.height);
                b = new CRectangle(editor.startPoint.x, editor.startPoint.y,
                                  inState.rect.x+inState.rect.width-editor.startPoint.x,
                                  inState.rect.height);
            }
            else {

                // *** waagerechte TrennLinie

                editor.startPoint = new Point(inState.rect.x, getCoordY(e,editor));
                editor.endPoint   = new Point(inState.rect.x+inState.rect.width, getCoordY(e,editor));

                a = new CRectangle(inState.rect.x, inState.rect.y,
                                  inState.rect.width,
                                  editor.endPoint.y-inState.rect.y);
                b = new CRectangle(editor.startPoint.x, editor.startPoint.y,
                                  inState.rect.width,
                                  inState.rect.y+inState.rect.height
                                  -editor.endPoint.y);
            }

            editor.actionOk = true;

            if (inState instanceof Or_State) {

                // *** prfe, ob jeder Substate ganz in a oder b liegt

                StateList list = ((Or_State) inState).substates;

                while (list != null) {
                    if (! a.union(list.head.rect).equals(a)
                     &  ! b.union(list.head.rect).equals(b)) 
                        editor.actionOk = false;
                    list = list.tail;
                }
            }
            if (editor.actionOk)
                 showAndLine(editor.startPoint, editor.endPoint, Color.red, editor);
            else showAndLine(editor.startPoint, editor.endPoint, Color.gray, editor);
        }
        else 
            // *** Maus ist in keinem Zustand

            if (editor.endPoint != null) {
                showAndLine(editor.startPoint, editor.endPoint, editor.getBackground(), editor);
                editor.startPoint = null;
                editor.endPoint = null;
                editor.actionOk = false;
            }
        showStates(editor);
    }
    
// **************************************************************************

    public static void andStateMouseReleased(MouseEvent e, Editor editor) {

        if (editor.actionOk) {

            State inState = getInnermostStateOf(getCoordX(e,editor), getCoordY(e,editor), editor);

            And_State inStateAnd = new And_State(new Statename("andState"), null);
                      inStateAnd.rect = inState.rect;

            // *** Bestimme den Vater, dies ist ein Or oder AndState

            State father = getFatherOf(inState, editor);

            // *** 1. l”sche in Vater.substates inState.
            // *** 2. Falls Vater ein Or_State, fge inStateAnd in seine
            // ***    substateList ein

            StateList help = null;

            if (father instanceof Or_State) {
                if (((Or_State) father).substates.head.equals(inState)) {
                    ((Or_State) father).substates = ((Or_State) father).substates.tail;
                }
                help = ((Or_State) father).substates;
            }
            else {
                if (((And_State) father).substates.head.equals(inState)) {
                    ((And_State) father).substates = ((And_State) father).substates.tail;
                }
                help = ((And_State) father).substates;
            }

            if (help != null)
                while (help.tail != null) {
                    if (help.tail.head.equals(inState)) {
                        help.tail = help.tail.tail;
                    }
                    else help = help.tail;
                }
            if (father instanceof Or_State) {
                ((Or_State) father).substates = new StateList(inStateAnd, ((Or_State) father).substates);
            }    

            // *** erzeuge 2 Or_States aState und bState und

            stateCount++;
            Or_State aState = new Or_State(new Statename("Nr. "+stateCount),
                              null, null, null, null, null);
            stateCount++;
            Or_State bState = new Or_State(new Statename("Nr. "+stateCount),
                              null, null, null, null);

            // *** Bestimme Start und Endpunkt der Linie

            if (getSideOf(inState, getCoordX(e,editor), getCoordY(e,editor)) == 0) {
          
                // *** senkrechte TrennLinie
           
                editor.startPoint = new Point(getCoordX(e,editor), inState.rect.y);
                editor.endPoint   = new Point(getCoordX(e,editor), inState.rect.y+ inState.rect.height);

                aState.rect = new CRectangle(inState.rect.x, inState.rect.y,
                                            editor.endPoint.x-inState.rect.x,
                                            inState.rect.height);
                bState.rect = new CRectangle(editor.startPoint.x, editor.startPoint.y,
                                            inState.rect.x+inState.rect.width-editor.startPoint.x,
                                            inState.rect.height);
            }
            else {

                // *** waagerechte TrennLinie

                editor.startPoint = new Point(inState.rect.x, getCoordY(e,editor));
                editor.endPoint   = new Point(inState.rect.x+inState.rect.width, getCoordY(e,editor));

                aState.rect = new CRectangle(inState.rect.x, inState.rect.y,
                                            inState.rect.width,
                                            editor.endPoint.y-inState.rect.y);
                bState.rect = new CRectangle(editor.startPoint.x, editor.startPoint.y,
                                            inState.rect.width,
                                            inState.rect.y+inState.rect.height
                                            -editor.endPoint.y);
            }

            // *** Weiter nur, wenn inState ein Or_State

            if (inState instanceof Or_State) {

                // *** verteile inState auf aState und bState
                // *** orStateSplit(inState, aState) liefert korrektes
                // *** aState
                // *** bState := inState; bState.rect bleibt!


                orStateSplit((Or_State) inState, aState, editor);
                CRectangle h = bState.rect;
                String n = bState.name.name;

                bState = (Or_State) inState;

                bState.rect = h;
                bState.name.name = n;
            }

            // *** h„nge aState und bState in die inStateAnd.substates
            // *** ein als Basic oder orState oder, falls father ein AndState,
            // *** direkt in die father.substates

            if (aState.substates == null & aState.trs == null
              & aState.connectors == null) {
                Basic_State neu1 = new Basic_State(aState.name, aState.rect);
                if (father instanceof Or_State)
                     inStateAnd.substates = new StateList(neu1, inStateAnd.substates);
                else ((And_State) father).substates = new StateList(neu1,
                     ((And_State) father).substates);
            }
            else {
                if (father instanceof Or_State)
                    inStateAnd.substates = new StateList(aState, inStateAnd.substates);
                else ((And_State) father).substates = new StateList(aState,
                     ((And_State) father).substates);
            }

            if (bState.substates == null & bState.trs == null
              & bState.connectors == null) {
                Basic_State neu2 = new Basic_State(bState.name, bState.rect);
                if (father instanceof Or_State)
                    inStateAnd.substates = new StateList(neu2, inStateAnd.substates);
                else ((And_State) father).substates = new StateList(neu2,
                     ((And_State) father).substates);
            }
            else {
                if (father instanceof Or_State)
                    inStateAnd.substates = new StateList(bState, inStateAnd.substates);
                else ((And_State) father).substates = new StateList(bState,
                     ((And_State) father).substates);
            }

            editor.stateList = getSubStateList(editor.statechart.state);
        }
        else { // action nicht ok
            // loesche eine evtl. schon gezeigte Linie
            if (editor.endPoint != null) showAndLine(editor.startPoint, editor.endPoint, editor.getBackground(), editor);
        }                
        editor.startPoint = null;
        editor.endPoint = null;
        editor.actionOk = false;
    }

  // **************************************************************************

    public static void showStates(Editor editor) {
	StateList list = editor.stateList;
        Graphics g = editor.getGraphics();
	g.setColor(Color.red);
        StateList help = list;
        while (help != null) {
            g.drawRoundRect(help.head.rect.x-editor.scrollX, help.head.rect.y-editor.scrollY, help.head.rect.width, help.head.rect.height,10,10);
            help = help.tail;
        }
    }

// **************************************************************************

    public static StateList getSubStateList(State state) {

        StateList returnList = null;
        StateList help       = null;
        StateList substates  = null;

        if (state instanceof Basic_State) 
            returnList = new StateList(state, null);
            
        else {
            if (state instanceof Or_State) 
                 help = ((Or_State) state).substates;
            else help = ((And_State) state).substates;

            returnList = new StateList(state, null);

            while (help != null) { // fuer alle Substates:

                // returnList = returnList + help.head + subStateList(help.head)

                if (help.head != null) substates = getSubStateList(help.head);

                while (substates != null) {
                    if (substates.head != null) {
                        returnList = new StateList(substates.head, returnList);
                    }
                    substates = substates.tail;
                }
                help = help.tail;
            }
        }
        return returnList;

    }

// **************************************************************************
                                        
    public static void showAndLine(Point start, Point end, Color color, Editor editor) {
        Graphics g = editor.getGraphics();
        g.setColor(color);
        g.drawLine(start.x-editor.scrollX, start.y-editor.scrollY, end.x-editor.scrollX, end.y-editor.scrollY);
    }

// **************************************************************************

    public static int getSideOf(State state, int x, int y) {                

        // ziehe durch den State beide Diagonalen und berechne dann, in welchem
        // der 4 Teile sich die Maus gerade befindet.

        // bestimme die rel. Koordinaten der Maus bzgl des States

        x = x - state.rect.x;
        y = y - state.rect.y;

        double schranke = (1-
                          (Math.abs(x-(state.rect.width / 2))
                            * (2.0 / (float) state.rect.width)))
                            * (state.rect.height / 2);

        if ((y < schranke) | (state.rect.height-y < schranke))
             return 0;
        else return 1;
    }
                
// **************************************************************************

    public static State getFatherOf(State state, Editor editor) {

        StateList list = editor.stateList;
        State father = null;
        StateList substates = null;

        while (list != null) {
            if (list.head instanceof Or_State)
                substates = ((Or_State) list.head).substates;
            if (list.head instanceof And_State)
                substates = ((And_State) list.head).substates;
            while (substates != null) {
                if (substates.head.equals(state)) return list.head;
                substates = substates.tail;
            }
            list = list.tail;
        }
        return null;
    }

// **************************************************************************

    public static State getInnermostStateOf(int x, int y, Editor editor) {
                
        StateList list = editor.stateList;
        State smallest = null;

        // *** Bestimme Liste der Zustaende, die x,y enthalten

        StateList inStates = null;

        while (list != null) {
            if (list.head.rect.contains(x-1,y-1)
              & (!(list.head instanceof And_State)))
                inStates = new StateList(list.head, inStates);
            if (list.head.rect.contains(x,y)
               & (list.head instanceof And_State))
                inStates = new StateList(list.head, inStates);

            list = list.tail;
        }

        // *** Bestimme aus dieser Liste den kleinsten Zustand smallest

        if (inStates != null) smallest = inStates.head;

        while (inStates != null) {
            if (!(inStates.head.rect.contains(smallest.rect.x, smallest.rect.y)))
                smallest = inStates.head;
            inStates = inStates.tail;
        }
        return smallest;
    }
  
// **************************************************************************
  
    public static void orStateSplit(Or_State alt, Or_State neu, Editor editor) {


        StateList     substateList         = alt.substates;
        StateList     altSubstates = null;
        StatenameList altDefaults  = null;
        while (substateList != null) {
            if (neu.rect.contains(substateList.head.rect.x,
                                  substateList.head.rect.y)) {
                neu.substates = new StateList(substateList.head, neu.substates);
                neu.defaults  = new StatenameList(substateList.head.name, neu.defaults);
            }
            else {
                altSubstates = new StateList(substateList.head, altSubstates);
                altDefaults = new StatenameList(substateList.head.name, altDefaults);
            }
            substateList = substateList.tail;
        }
        alt.substates = altSubstates;
        alt.defaults  = altDefaults;


        TrList trList   = alt.trs;
        TrList altTrs = null;
        while (trList != null) {
            if (neu.rect.contains(trList.head.points[0]))
                neu.trs = new TrList(trList.head, neu.trs);
            else altTrs = new TrList(trList.head, altTrs); 
            trList = trList.tail;
        }
        alt.trs = altTrs;


        ConnectorList connectorList          = alt.connectors;
        ConnectorList altConnectors = null;
                                
        while (connectorList != null) {
            if (neu.rect.contains(connectorList.head.position))
                neu.connectors = new ConnectorList(connectorList.head, neu.connectors);
            else altConnectors = new ConnectorList(connectorList.head, altConnectors);
            connectorList = connectorList.tail;
        }
        alt.connectors = altConnectors;
    }

// **************************************************************************
// **************************************************************************

// *** nachfolgende Methode wird noch nicht eingesetzt (printStatechart)

// **************************************************************************

    public static void printStatechart(State state, Point relativeTo, Editor editor) {
		
        Graphics g = editor.getGraphics();

        if (state instanceof Basic_State) printState(state, relativeTo, editor, g);         
        if (state instanceof And_State) printAnd_State((And_State) state, relativeTo, editor, g);    
		if (state instanceof Or_State) {

            Or_State orState = (Or_State) state;
            StateList help = orState.substates;

			// zeichne den Or-Zustand

            if (! state.equals(editor.statechart.state))
                printState(state, relativeTo, editor, g);

			// zeichne die untergeordneten Zustaende

            while (help != null) {
                printStatechart(help.head, new Point(help.head.rect.x, help.head.rect.y), editor);
                help = help.tail;
			}

			// Zeichne die Transitionen

                        TrList trList = orState.trs;

                        while (trList.head != null) {
                                printTr(trList.head, relativeTo, editor, g);
                                trList = trList.tail;
			}

			// zeichne die Connectoren

                        ConnectorList connList = orState.connectors;

                        while (connList.head != null) {
                                printConnector(connList.head, relativeTo, g);
                                connList = connList.tail;
			}

		}					
	}

        public static void printState(State state, Point relativeTo, Editor editor, 
					    Graphics g) { 

		g.drawRoundRect(relativeTo.x+state.rect.x,
			        relativeTo.y+state.rect.y,
  			        state.rect.width,
			        state.rect.height,						
			        10, 10);
                TextField t = new TextField(state.name.name);
		t.setBounds(    relativeTo.x+state.rect.x+3, 
			        relativeTo.y+state.rect.y+3,
  			        state.rect.width-6,
			        20);
                editor.add(t);
	}
	  
        public static void printAnd_State(And_State state, Point relativeTo, Editor editor,
					  Graphics g) {
	
		g.drawRoundRect(relativeTo.x+state.rect.x, 
			        relativeTo.y+state.rect.y,
  			        state.rect.width,
			        state.rect.height,						
			        10, 10);
                TextField t = new TextField(state.name.name);
		t.setBounds(    relativeTo.x+state.rect.x+3, 
			        relativeTo.y+state.rect.y-22,
  			        state.rect.width-6,
			        20);
                editor.add(t);

		// Ermittle Anzahl paralleler Ebenen

		int i=0;

                while (state.substates.head != null) {
			i++;
                        state.substates = state.substates.tail;
		}

		// damit wird noch nichts gemacht...

                while (state.substates.head != null) {
                        printStatechart(state.substates.head, new Point(state.rect.x, state.rect.y), editor);
			state.substates = state.substates.tail;
		}
	}

        public static void printTr(Tr tr, Point relativeTo, Editor editor, Graphics g) { 
	
		for (int i=0; i < tr.points.length-1; i++) {
	
			g.drawLine(tr.points[i].x,   tr.points[i].y,
			           tr.points[i+1].x, tr.points[i+1].y);
                }
                TextField t = new TextField();
		t.setBounds(    tr.points[0].x, tr.points[0].y,
  			        50, 20);
                editor.add(t);
	}
		
	public static void printConnector(Connector conn, Point relativeTo, Graphics g) {
			
		g.drawOval(conn.position.x,conn.position.y,10,10);	
	}

// **************************************************************************

}
