package editor;

import absyn.*;
import java.awt.*;
import java.awt.event.*;

public class EditorUtils {

    private static int stateCount = 0;

    private static int getCoordX(MouseEvent e,Editor editor) {
        return (e.getX()+editor.scrollX);
    }
    private static int getCoordY(MouseEvent e, Editor editor) {
        return (e.getY()+editor.scrollY);
    }


        // **************************************************
        // Hier kommen Methoden fÅr MouseEvents
        // **************************************************


// **************************************************************************

    public static void createStateMousePressed(MouseEvent e, Editor editor) {

        editor.startPoint = new Point(getCoordX(e,editor),getCoordY(e,editor));
        editor.newRect = new CRectangle(getCoordX(e,editor), getCoordY(e,editor),0,0);
    }

// **************************************************************************

    public static void createStateMouseDragged(MouseEvent e, Editor editor) {

        Graphics g = editor.getGraphics();

        // altes Rectangle loeschen

        show(editor.newRect, editor.getBackground(), editor, g);

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

        show(editor.newRect, Color.red, editor, g);
        editor.actionOk = true;

        showStates(editor);

        StateList list = editor.stateList;

        while (list != null) {
            if (!(editor.newRect.intersection(abs(editor,list.head))
                    .equals(abs(editor,list.head)))
              & (!(editor.newRect.intersection(abs(editor,list.head))
                    .equals(editor.newRect)))
              & (editor.newRect.intersects(abs(editor,list.head)))) {

                show(editor.newRect, Color.gray, editor, g);
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
            Or_State newState      = new Or_State(new Statename("...defaultName"+stateCount), null, null, null, null);
                     newState.rect = rel(editor,editor.newRect);

            // *** Bestimme den Vater

            State father = getInnermostStateOf(editor.newRect.x,
                                               editor.newRect.y, editor);

            // *** Falls Vater = Or_State, splitte seine Listen
            // *** sonst mache einen OrState aus ihm

            Or_State orFather = null;

            if (father instanceof Or_State) {
                orStateSplit((Or_State) father, newState, editor);
                fitComponentsOf(newState, newState.rect.x, newState.rect.y);
            }

            if (father instanceof Basic_State) {

                orFather = new Or_State(father.name,
                                      null, null, null, null);
                orFather.rect = father.rect;

                // *** Bestimme den Gro·vater, er ist vorhanden, weil
                // *** Vater ein Basic_State

                State grandfather = getFatherOf(father, editor);

                // trage in die gro·vater.substates den Vater um

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

            // *** und hÑnge newState entsprechend als BasicState oder
            // *** ORState in orFather.substates ein
            // *** sowie in editor.stateList

            if (orFather == null) orFather = (Or_State) father;

            if (newState.substates == null & newState.trs == null
              & newState.connectors == null) {
                Basic_State neu = new Basic_State(
                        new Statename("...defaultName"+stateCount), newState.rect);
                orFather.substates = new StateList(neu, orFather.substates);
                orFather.defaults = new StatenameList(neu.name, orFather.defaults);
            }
            else {
                orFather.substates = new StateList(newState, orFather.substates);
                orFather.defaults = new StatenameList(newState.name, orFather.defaults);
            }
        }
        else {  // Zustand wieder vom Fenster loeschen
                show(editor.newRect, editor.getBackground(), editor, g);
        }

        editor.startPoint = null;

        // alle jetzt bestehenden States zeichnen

        editor.stateList = getSubStateList(editor.statechart.state);
        setPathList(editor.statechart, editor);
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
            // *** Darf die Linie gezogen werden? Spalte in 2 HÑlften

            CRectangle a = null;
            CRectangle b = null;

            if (getSideOf(editor,inState, getCoordX(e,editor),
                getCoordY(e,editor)) == 0) {

                // *** senkrechte TrennLinie

                editor.startPoint = new Point(getCoordX(e,editor),
                                              abs(editor,inState).y);
                editor.endPoint   = new Point(getCoordX(e,editor),
                                              abs(editor,inState).y
                                              + inState.rect.height);

                a = new CRectangle(abs(editor,inState).x,
                                   abs(editor,inState).y,
                                   editor.endPoint.x-abs(editor,inState).x,
                                   inState.rect.height);
                b = new CRectangle(editor.startPoint.x, editor.startPoint.y,
                                   abs(editor,inState).x
                                   +inState.rect.width-editor.startPoint.x,
                                   inState.rect.height);
            }
            else {

                // *** waagerechte TrennLinie

                editor.startPoint = new Point(abs(editor,inState).x,
                                              getCoordY(e,editor));
                editor.endPoint   = new Point(abs(editor,inState).x
                                              +inState.rect.width,
                                              getCoordY(e,editor));

                a = new CRectangle(abs(editor,inState).x,
                                   abs(editor,inState).y,
                                   inState.rect.width,
                                   editor.endPoint.y - abs(editor,inState).y);
                b = new CRectangle(editor.startPoint.x, editor.startPoint.y,
                                   inState.rect.width,
                                   abs(editor,inState).y + inState.rect.height
                                   -editor.endPoint.y);
            }

            editor.actionOk = true;

            if (inState instanceof Or_State) {

                // *** prÅfe, ob jeder Substate ganz in a oder b liegt

                StateList list = ((Or_State) inState).substates;

                while (list != null) {
                    if (! a.union(abs(editor,list.head)).equals(a)
                     &  ! b.union(abs(editor,list.head)).equals(b))
                        editor.actionOk = false;
                    list = list.tail;
                }
            }
            if (editor.actionOk)
                 showAndLine(editor.startPoint, editor.endPoint,
                             Color.red, editor);
            else showAndLine(editor.startPoint, editor.endPoint,
                             Color.gray, editor);
        }
        else
            // *** Maus ist in keinem Zustand

            if (editor.endPoint != null) {
                showAndLine(editor.startPoint, editor.endPoint,
                            editor.getBackground(), editor);
                editor.startPoint = null;
                editor.endPoint = null;
                editor.actionOk = false;
            }
        showStates(editor);
    }

// **************************************************************************

    public static void andStateMouseReleased(MouseEvent e, Editor editor) {


        if (editor.actionOk) {

            State inState = getInnermostStateOf(getCoordX(e,editor),
                                                getCoordY(e,editor), editor);

            stateCount++;
            And_State inStateAnd = new And_State(new Statename("...defaultNameAndState"+stateCount), null);
                      inStateAnd.rect = inState.rect;

            int getSideOfVar = getSideOf(editor,inState, getCoordX(e,editor),
                                         getCoordY(e,editor));
            CRectangle absInStateVar = abs(editor, inState);

            // *** Bestimme den Vater, dies ist ein Or oder AndState

            State father = getFatherOf(inState, editor);

            // ***    lîsche den Namen von inState auch aus der StatenameList,
            // ***    falls der Vater ein Or_State
            // ***    und fÅge den Namen in die StatenameList ein, falls der
            // ***    Vater ein Or_State
            // ***    Falls Vater ein Or_State, fÅge inStateAnd in seine
            // ***    substateList ein

            StateList help = null;

            if (father instanceof Or_State) {
                ((Or_State) father).substates = new StateList(inStateAnd, ((Or_State) father).substates);
            }

            // *** Lîsche den Namen von inState aus father.defaults

            StatenameList helpnames = null;

            if (father instanceof Or_State) {
                if ( ((Or_State) father).defaults.head.equals(inState.name) )
                    ((Or_State) father).defaults = ((Or_State) father).defaults.tail;
                else
                    helpnames = ((Or_State) father).defaults;

                if (helpnames != null)
                    while (helpnames.tail != null) {
                        if (helpnames.tail.head.equals(inState.name)) {
                            helpnames.tail = helpnames.tail.tail;
                        }
                        else helpnames = helpnames.tail;
                    }
            }

            // *** fÅge inStateAnd.name in father.defaults ein, falls father
            // *** Or_State

            if (father instanceof Or_State)
                ((Or_State) father).defaults = new StatenameList(inStateAnd.name,
                                                ((Or_State) father).defaults);

            // *** erzeuge 2 Or_States aState und bState und

            stateCount++;
            Or_State aState = new Or_State(new Statename("...defaultName"+stateCount),
                              null, null, null, null, null);
            stateCount++;
            Or_State bState = new Or_State(new Statename("...defaultName"+stateCount),
                              null, null, null, null);

            // *** Hilfspunkte

            Point point    = null;
            Point absPoint = null;
            if (father instanceof And_State) {
                point = new Point(inState.rect.x, inState.rect.y);
                absPoint = abs(editor, point, father);
            }
            else {
                point = new Point(0,0);
                absPoint = abs(editor, point, inState);
            }

            // *** Bestimme Start und Endpunkt der Linie

            if (getSideOfVar == 0) {

                // *** senkrechte TrennLinie (abs(inState) darf nicht mehr
                // *** angewendet werden, deshalb wurde der Wert vorher
                // *** schon gespeichhert in absInStateVar
                // *** getSideOf == 0 ==> senkrechteLinie

                editor.startPoint = new Point(getCoordX(e,editor),
                                              absInStateVar.y);
                editor.endPoint   = new Point(getCoordX(e,editor),
                                              absInStateVar.y
                                              + inState.rect.height);

                aState.rect = new CRectangle(point.x, point.y,
                                             editor.startPoint.x - absPoint.x,
                                             inState.rect.height);

                bState.rect = new CRectangle(point.x + aState.rect.width,
                                             point.y,
                                             inState.rect.width
                                             -aState.rect.width,
                                             inState.rect.height);
            }
            else {

                // *** waagerechte TrennLinie

                editor.startPoint = new Point(absInStateVar.x,
                                              getCoordY(e,editor));
                editor.endPoint   = new Point(absInStateVar.x
                                              + inState.rect.width,
                                              getCoordY(e,editor));

                aState.rect = new CRectangle(point.x, point.y,
                                             inState.rect.width,
                                             editor.startPoint.y
                                             - absPoint.y);
                bState.rect = new CRectangle(point.x, point.y+aState.rect.height,
                                             inState.rect.width,
                                             inState.rect.height
                                             -aState.rect.height);
            }

            // *** Weiter nur, wenn inState ein Or_State

            if (inState instanceof Or_State) {

                // *** verteile inState auf aState und bState
                // *** orStateSplit(inState, aState) liefert korrektes
                // *** aState
                // *** bState := inState; bState.rect bleibt!

                orStateSplit((Or_State) inState, aState, editor);

                bState.substates  = ((Or_State) inState).substates;
                bState.defaults   = ((Or_State) inState).defaults;
                bState.trs        = ((Or_State) inState).trs;
                bState.connectors = ((Or_State) inState).connectors;

                if (getSideOfVar == 0)
                     fitComponentsOf(bState, aState.rect.width, 0);
                else fitComponentsOf(bState, 0, aState.rect.height);
            }

            // *** hÑnge aState und bState in die inStateAnd.substates
            // *** ein als Basic oder orState oder, falls father ein AndState,
            // *** direkt in die father.substates

            if (aState.substates == null & aState.trs == null
              & aState.connectors == null) {
                Basic_State neu1 = new Basic_State(aState.name, aState.rect);
                if (father instanceof Or_State) {
                     inStateAnd.substates = new StateList(neu1, inStateAnd.substates);
                }
                else {
                    ((And_State) father).substates = new StateList(neu1,
                         ((And_State) father).substates);
                }
            }
            else {
                if (father instanceof Or_State) {
                    inStateAnd.substates = new StateList(aState, inStateAnd.substates);
                }
                else {
                    ((And_State) father).substates = new StateList(aState,
                         ((And_State) father).substates);
                }
            }

            if (bState.substates == null & bState.trs == null
              & bState.connectors == null) {
                Basic_State neu2 = new Basic_State(bState.name, bState.rect);
                if (father instanceof Or_State) {
                    inStateAnd.substates = new StateList(neu2, inStateAnd.substates);
                }
                else {
                    ((And_State) father).substates = new StateList(neu2,
                         ((And_State) father).substates);
                }
            }
            else {
                if (father instanceof Or_State) {
                    inStateAnd.substates = new StateList(bState, inStateAnd.substates);
                }
                else {
                    ((And_State) father).substates = new StateList(bState,
                         ((And_State) father).substates);
                }
            }

            // ***    lîsche in Vater.substates inState.

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

            editor.stateList = getSubStateList(editor.statechart.state);
            setPathList(editor.statechart, editor);
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
        try {
            StateList list = editor.stateList;
            Graphics g = editor.getGraphics();

            g.setColor(Color.red);
            StateList help = list;
            while (help != null) {
                show(abs(editor, help.head), Color.red, editor, g);
                help = help.tail;
            }
        }
        catch (Exception e) {}
    }

// **************************************************************************

    public static void show(Rectangle absRect, Color color, Editor editor,
                            Graphics g) {
        g.setColor(color);
        g.drawRoundRect((int)((double)(absRect.x*Methoden_1.getFactor()))
                            -editor.scrollX,
                        (int)((double)(absRect.y*Methoden_1.getFactor()))
                            -editor.scrollY,
                        (int)((double)(absRect.width)*Methoden_1.getFactor()),
                        (int)((double)(absRect.height)*Methoden_1.getFactor()),10,10);
    }        

// **************************************************************************

    public static StateList getSubStateList(State state) {

        // erzeugt eine Liste, die state beinhaltet und alle States, die
        // innerhalb von state liegen, also auch die substates der substates
        // usw.

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

    public static int getSideOf(Editor editor, State state, int x, int y) {

        // ziehe durch den State beide Diagonalen und berechne dann, in welchem
        // der 4 Dreiecke sich x,y befindet.
        // Befindet sich x,y im oberen oder unteren Dreieck, so wird 0 zurÅck-
        // gegeben, sonst 1;

        // bestimme die rel. Koordinaten der Maus bzgl des States

        x = x - abs(editor,state).x;
        y = y - abs(editor,state).y;

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

        // liefert den Vater von state, aber Vorsicht:
        // Liefert nur dann ein Ergebnis, wenn der State im Baum hÑngt

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

        // Liefert den kleinsten (= innersten) Zustand, der x,y IM INNEREN
        // enthÑlt. Im Falle, da· x,y auf der Kante eines And_States liegt,
        // wird der And_State zurÅckgeliefert. Liegt x,y auf der Kantes eines
        // Or_States, wird der Vater dieses Or_States zurÅckgeliefert.

        StateList list = editor.stateList;
        State smallest = null;

        // *** Bestimme Liste der Zustaende, die x,y enthalten

        StateList inStates = null;
        while (list != null) {
            if (abs(editor,list.head).contains(x-1,y-1)
              & (!(list.head instanceof And_State)))
                inStates = new StateList(list.head, inStates);
            if (abs(editor,list.head).contains(x,y)
               & (list.head instanceof And_State))
                inStates = new StateList(list.head, inStates);

            list = list.tail;
        }

        // *** Bestimme aus dieser Liste den kleinsten Zustand smallest

        if (inStates != null) smallest = inStates.head;

        while (inStates != null) {
            if (!(abs(editor,inStates.head)
                    .contains(abs(editor,smallest).x, abs(editor,smallest).y)))
                smallest = inStates.head;
            inStates = inStates.tail;
        }
        return smallest;
    }

// **************************************************************************

    public static void orStateSplit(Or_State alt, Or_State neu, Editor editor) {

        // alt ist der werdende Vater des in ihm enthaltenen States neu.
        // die Substates, Defaults, Connectors und Trs werden, sofern sie
        // in neu liegen, auf neu umgetragen.

        StateList     substateList         = alt.substates;
        StateList     altSubstates = null;
        StatenameList altDefaults  = null;

        while (substateList != null) {
            if (abs(editor,neu,alt).contains(abs(editor,substateList.head).x,
                                         abs(editor,substateList.head).y)) {
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

/*
        TrList trList   = alt.trs;
        TrList altTrs = null;
        while (trList != null) {

            if (abs(editor,neu).contains(abs(editor,trList.head.points[0],alt)))
                neu.trs = new TrList(trList.head, neu.trs);
            else altTrs = new TrList(trList.head, altTrs);
            trList = trList.tail;
        }
        alt.trs = altTrs;
*/

        ConnectorList connectorList          = alt.connectors;
        ConnectorList altConnectors = null;

        while (connectorList != null) {
            if (abs(editor,neu, alt).contains(abs(editor,connectorList.head.position, alt)))
                neu.connectors = new ConnectorList(connectorList.head, neu.connectors);
            else altConnectors = new ConnectorList(connectorList.head, altConnectors);
            connectorList = connectorList.tail;
        }
        alt.connectors = altConnectors;
    }

// **************************************************************************

    private static void fitComponentsOf(Or_State orstate, int x, int y) {

        // von allen Komponenten von orstate (substates, connectors, trs)
        // werden bei deren relativen Koordinaten x,y abgezogen.

        // 1. die Substates:

        StateList substates = orstate.substates;

        while (substates != null) {
            substates.head.rect.x = substates.head.rect.x - x;
            substates.head.rect.y = substates.head.rect.y - y;
            substates = substates.tail;
        }

        // 2. die Connectors

        ConnectorList connectors = orstate.connectors;

        while (connectors != null) {
            connectors.head.position.x = connectors.head.position.x - x;
            connectors.head.position.y = connectors.head.position.y - y;
            connectors = connectors.tail;
        }

        // 3. die Trs
        // TrAnchors noch nicht implementiert !!!!!!!!!!!!!!!!!!!

        TrList trs = orstate.trs;

        while (trs != null) {
            for (int i=0; i < trs.head.points.length; i++) {
                trs.head.points[i].x = trs.head.points[i].x - x;
                trs.head.points[i].y = trs.head.points[i].y - y;
            }
            trs = trs.tail;
        }
    }

// **************************************************************************

    private static CRectangle abs(Editor editor, State state) {

        // liefert das Rectangle von state mit absoluten Koordinaten
        // Achtung: state mu· im Baum hÑngen. Ist dies nicht der Fall
        // so ist die andere abs-Methode zu benutzen!

        if (state.equals(editor.statechart.state))
            return state.rect;
        State father = getFatherOf(state, editor);

        if (father.equals(editor.statechart.state))
            return state.rect;
        else {
            CRectangle fatherAbsRect = abs(editor, father);

            return new CRectangle(fatherAbsRect.x + state.rect.x,
                                  fatherAbsRect.y + state.rect.y,
                                  state.rect.width, state.rect.height);
        }
    }

// **************************************************************************

    private static CRectangle abs(Editor editor, State state, State father) {

        // liefert das Rectangle von state mit absoluten Koordinaten
        // Achtung: father mu· im Baum hÑngen.


        if (state.equals(editor.statechart.state))
            return state.rect;

        if (father.equals(editor.statechart.state))
            return state.rect;
        else {
            CRectangle fatherAbsRect = abs(editor, father);

            return new CRectangle(fatherAbsRect.x + state.rect.x,
                                  fatherAbsRect.y + state.rect.y,
                                  state.rect.width, state.rect.height);
        }
    }

// **************************************************************************

    private static Point abs(Editor editor, Point point, State state) {

        // liefert die absoluten Koordinaten eines beliebigen Punktes, der
        // innerhalb von state liegt. Achtung: state mu· im Baum hÑngen.

        CRectangle fatherAbsRect = abs(editor, state);

        return new Point(fatherAbsRect.x + point.x,
                         fatherAbsRect.y + point.y);
    }

// **************************************************************************

    private static CRectangle rel(Editor editor, CRectangle rect) {

        // rechnet die absoluten Koordinaten von rect in relative Koordinaten
        // um.

        State father = getInnermostStateOf(rect.x, rect.y, editor);

        CRectangle fatherAbsRect = abs(editor, father);

        return new CRectangle(rect.x - fatherAbsRect.x,
                              rect.y - fatherAbsRect.y,
                              rect.width, rect.height);
    }

// **************************************************************************

    public static void setPathList(Statechart statechart, Editor editor) {

        statechart.cnames = null;        
        StateList list    = editor.stateList;
        Path path         = null;
        Path help         = null;
        State state       = null;
        PathList pathList = null;
        PathList pathListHelp = null;

        while (list != null) {
            path = new Path(list.head.name.name, null);
            state = list.head;
            while (!state.equals(statechart.state)) {
                state = getFatherOf(state,editor);
                path = new Path(state.name.name, path);
            }
            pathList = new PathList(path, pathList);
            list = list.tail;
        }
        statechart.cnames = pathList;
    }

// **************************************************************************

    private static void highlight(State state, Editor editor) {
        show(abs(editor,state), Color.gray, editor, editor.getGraphics());
    }

// **************************************************************************

    public static void deleteStateMouseMoved(MouseEvent e, Editor editor) {

        showStates(editor);

        // falls fatherOf(inState) = And_State, highlighte den father

        State inState = getInnermostStateOf(getCoordX(e,editor),
                                            getCoordY(e,editor), editor);

        StateList list = null;

        if (!inState.equals(editor.statechart.state)) {
            if (getFatherOf(inState,editor) instanceof And_State) 
                 list = getSubStateList(getFatherOf(inState, editor));
            else list = getSubStateList(inState);
            while (list != null) {
                highlight(list.head, editor);
                list = list.tail;
            }
        }
    }

// **************************************************************************

    public static void deleteStateMouseReleased(MouseEvent e, Editor editor) {

        State inState = getInnermostStateOf(getCoordX(e,editor),
                                            getCoordY(e,editor), editor);

        if (!inState.equals(editor.statechart.state)) {

            State father = getFatherOf(inState,editor);

            if (father instanceof Or_State)

                // *** 1. Fall: Vater wird leer und somit BasicState

                if  (((Or_State) father).substates.tail == null
                   & ((Or_State) father).trs == null
                   & ((Or_State) father).connectors == null
                   & !father.equals(editor.statechart.state)) {

                    Basic_State newFather = new Basic_State(father.name,
                                                            father.rect);
                    State grandFather = getFatherOf(father,editor);

                    if (grandFather instanceof And_State) {
                        deleteStateIn((And_State) grandFather, father, editor);
                        ((And_State) grandFather).substates
                                      = new StateList(newFather,
                                        ((And_State) grandFather).substates);
                    }
                    if (grandFather instanceof Or_State) {

                        deleteStateIn(grandFather, father, editor);
                        ((Or_State) grandFather).substates
                                      = new StateList(newFather,
                                        ((Or_State) grandFather).substates);
                        ((Or_State) grandFather).defaults
                                      = new StatenameList(newFather.name,
                                        ((Or_State) grandFather).defaults);
                    }
                }
                else {
                    // *** 2. Fall: Vater wird nicht leer oder ist root

                    deleteStateIn(father, inState, editor);
                }

            if (father instanceof And_State) {
                Or_State grandFather = (Or_State) getFatherOf(father,editor);
                deleteStateIn(grandFather, father, editor);
                if (!grandFather.equals(editor.statechart.state) &
                     grandFather.substates == null &
                     grandFather.trs == null &
                     grandFather.connectors == null) {

                     Or_State grandGrandFather = (Or_State) getFatherOf(grandFather,editor);
                     deleteStateIn(grandGrandFather,grandFather, editor);
                     grandGrandFather.substates
                        = new StateList(new Basic_State(grandFather.name, grandFather.rect), grandGrandFather.substates);
                }
            }
        }
        editor.stateList = getSubStateList(editor.statechart.state);
        setPathList(editor.statechart, editor);
        showStates(editor);        
    }

// **************************************************************************

    private static void deleteStateIn(State in, State delState, Editor editor) {

        // *** entferne zu lîschenden State von der ZeichenflÑche

        StateList list = getSubStateList(delState);
        while (list != null) {
            show(abs(editor, list.head), editor.getBackground(), editor, editor.getGraphics());
            list = list.tail;
        }

        // *** lîsche ihn aus dem Baum

        StateList help = null;
        StatenameList help2 = null;

        if (in instanceof Or_State) {
            if (((Or_State) in).substates.head.equals(delState)) 
                ((Or_State) in).substates = ((Or_State) in).substates.tail;
            else {
                help = ((Or_State) in).substates;
                while (help.tail != null) {
                    if (help.tail.head.equals(delState)) 
                         help.tail = help.tail.tail;
                    else help = help.tail;
                }
            }
            if (((Or_State) in).defaults.head.equals(delState.name))
                ((Or_State) in).defaults = ((Or_State) in).defaults.tail;
            else {
                help2 = ((Or_State) in).defaults;
                while (help2.tail != null) {
                    if (help2.tail.head.equals(delState.name)) 
                         help2.tail = help2.tail.tail;
                    else help2 = help2.tail;
                }
            }
        }
        if (in instanceof And_State) 
            if (((And_State) in).substates.head.equals(delState))
                ((And_State) in).substates = ((And_State) in).substates.tail;
            else {
                help = ((And_State) in).substates;
                while (help.tail != null) {
                    if (help.tail.head.equals(delState))
                         help.tail = help.tail.tail;
                    else help = help.tail;
                }
            }
        editor.stateList = getSubStateList(editor.statechart.state);
    }    

// **************************************************************************
}
