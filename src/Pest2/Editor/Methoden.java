/*
Sammlung von Methoden fuer den Editor
*/

package editor;

import absyn.*;
import java.awt.*;
import java.awt.event.*;



public class Methoden
{
    private static Point mMoved = new Point(0,0);
    private static CPoint[] pointsTr = new CPoint[50]; //Array fuer addTransitionmouse-Clicked und -Moved



    public static void transitionMouseClicked(MouseEvent e,int x,int y,Editor editor)
    {
        addTransitionMouseClicked(e,x,y,editor);
    }


/*
    Methode: addTransitionMouseClicked(State state,MouseEvent e,int x,int y,Editor editor)
    Funktion: Fuegt Punkt (x,y) zuerst in einem Array ein,
              Anfangspunkt mit der linken Maustaste markiert,
              Weitere Punkte mit der rechten Maustaste,
              Endpunkt mit der linken Maustaste beenden.
    Hilfsmethode: getnullIndex(Point[] p)
                  setnullArray(Point[] p)
*/
    public static void addTransitionMouseClicked(MouseEvent e,int x,int y,Editor editor)
    {
        Graphics g = editor.getGraphics();
        int j=getnullIndex(pointsTr);
/*        if (j>0) //nur parallele oder senkrechte Linien!!
        {
            if ((Betrag(pointsTr[j-1].x,x)<=Betrag(pointsTr[j-1].y,y))) x = pointsTr[j-1].x;
            else y = pointsTr[j-1].y;
        }*/

        State state = editor.statechart.state;
        Rectangle r = new Rectangle();
		if (state instanceof Or_State)
		{
            Or_State orState = (Or_State) state;
            StateList list = orState.substates;
            while (list != null)
            {
                if (list.head.rect.contains(x,y))
                {
                    State s = EditorUtils.getInnermostStateOf(x,y,editor);
                    r = s.rect;
                    if (s instanceof Or_State)
                        if ((Betrag(x,r.x)>20) || (Betrag(x,r.x+r.width) > 20) ||
                            (Betrag(y,r.y)>20) || (Betrag(y,r.y+r.height) > 20)) break;
                    switch (wherePoint(r,x,y)) //in welchem "Bereich" liegt der Punkt??
                    {
                        case 1:{y = r.y;break;}
                        case 2:{x = r.x+r.width;break;}
                        case 3:{y = r.y+r.height;break;}
                        case 4:{x = r.x;}
                    }
                    break;
                }
                list = list.tail;
            }
        }
        pointsTr[j] = new CPoint(x,y);
        if (j==1)
        {
            g.setXORMode(editor.getBackground());
            g.drawOval(pointsTr[0].x-editor.scrollX,pointsTr[0].y-editor.scrollY,5,5);
        }
        if (checkMouseButton(e,1))
        {
            if (j<=0)
            {g.drawOval(x-editor.scrollX,y-editor.scrollY,5,5);} // nur bei zwei Punkten erst zeichnen
            else
            {
                Or_State os = (Or_State) editor.statechart.state;
                CPoint[] P = new CPoint[j+1];
                Point relpoint = new Point(getRelativeCoord(pointsTr[0].x,pointsTr[0].y,0,0,os.substates));
                for (int k=0;k<=j;k++) P[k] = new CPoint(pointsTr[k].x-relpoint.x,pointsTr[k].y-relpoint.y);

                Or_State s;
                if (EditorUtils.getInnermostStateOf(x,y,editor) instanceof Or_State) s = (Or_State)getOpaState(P[0].x,P[0].y,editor);
                else s = (Or_State)getOpaState(P[0].x,P[0].y,editor);
                if (s == null) s = (Or_State)editor.statechart.state;

                Tr dummyTr = new Tr(new Statename(""),new Statename(""),null,P);
                TrList dlist = new TrList(dummyTr,s.trs);
                s.trs = dlist;
                setnullArray(pointsTr);
            }
        }
        else if (checkMouseButton(e,3))
        {
            if ((j<=0)||(j==49)) pointsTr[j]=null;
            return;
        }
        g.dispose();
    }

    public static void labelMouseClicked(MouseEvent e,int x,int y,Editor editor)
    {
        //Fenster oeffnen und label abfragen
        //label einfügen
    }


    //transitionmouseMoved(..)
    //Methode soll aufgerufen werden, wenn Maus bewegt wird.
    //Funktion: ruft weitere Methoden auf, die bearbeitet werden sollen,
    //          wenn Transition gewaehlt ist
    //Parametern: State, x- und y-Koordinaten des Mauszeigers

    public static void transitionMouseMoved(State state,int x, int y, Editor editor)
    {
		if (state instanceof Or_State)
		{
            Or_State orState = (Or_State) state;
            StateList list = orState.substates;
            while (list != null)
            {
                if (list.head.rect.contains(x,y))
                {

                    markTransitionMouseMoved(EditorUtils.getInnermostStateOf(x,y,editor),x,y,editor);
                }
                list = list.tail;
            }
        }
        drawAllTransition(editor);
    }

    //markTransitionMouseMoved(..)
    //Funktion: Prueft, ob Mauszeiger im Rechtecht, wenn ja:
    //          zeichnet an der Seite eines Rechtecks einen Kreis, um
    //          das Hinzufuegen von Transition zu erleichtern
    //          sonst: nichts
    //Hilfsmethode: wherePoint
    //Parametern: Rechteck und x-y-Koordinate des Mauszeigers

    public static void markTransitionMouseMoved(State state,int x, int y,Editor editor)
    {
        int rad = 5;
        Rectangle r = state.rect;
        Graphics g = editor.getGraphics();
        g.setColor(editor.getBackground());
        g.drawOval(mMoved.x-editor.scrollX,mMoved.y-editor.scrollY,rad,rad);
        if (!r.contains(x,y))
        {
            g.dispose();
            return;
        }
        if ((x == mMoved.x) && (y == mMoved.y))
        {
            g.dispose();
            return;
        }
        g.setColor(Color.blue);
        switch (wherePoint(r,x,y)) //in welchem "Bereich" liegt der Punkt??
        {
            case 1:{g.drawOval(x-editor.scrollX,r.y-editor.scrollY-2-rad,rad,rad);mMoved.x=x;mMoved.y=r.y-2-rad;break;}
            case 2:{g.drawOval(r.x-editor.scrollX+r.width+2,y-editor.scrollY,rad,rad);mMoved.x=r.x+r.width+2;mMoved.y=y;break;}
            case 3:{g.drawOval(x-editor.scrollX,r.y-editor.scrollY+r.height+2,rad,rad);mMoved.x=x;mMoved.y=r.y+r.height+2;break;}
            case 4:{g.drawOval(r.x-editor.scrollX-2-rad,y-editor.scrollY,rad,rad);mMoved.x=r.x-2-rad;mMoved.y=y;}
        }
        g.dispose();
    }


/*
    Methode: drawAllTransition(Editor editor)
    Funktion: zeichnet die root-Transitionen und ruft dfsStateTransition auf
*/
    public static void drawAllTransition(Editor editor)
    {
        Or_State os = (Or_State)editor.statechart.state;
        drawTransition(os.trs,0,0,editor);
        dfsStateTransition(os.substates,editor);
    }

/*
    Methode: dfsStateTransition(StateList sl,Editor editor)
    Funktion: durchlaeuft den Statechart und ruft fuer jeden Or_State die
              Methode drawTransition auf
*/

    public static void dfsStateTransition(StateList sl,Editor editor)
    {
        StateList list = sl;
        while (list != null)
        {
            if (list.head instanceof Or_State)
            {
                Or_State os = (Or_State)list.head;
                drawTransition(os.trs,os.rect.x,os.rect.y,editor);
                if (os.substates != null) dfsStateTransition(os.substates,editor);
            }
            list = list.tail;
        }
    }

/*
    Methode: drawTransition(TrList trlist,Editor editor)
    Funktion: zeichnet Transitionen aus der uebergebenen Liste
*/

    private static void drawTransition(TrList trlist,int absX, int absY,Editor editor)
    {
        Graphics g = editor.getGraphics();
        TrList dlist = trlist;
        int i=0;
        while (dlist != null)
        {
            i=0;
            try
            {
                for (i=0;i<50;i++)
                {
                    g.drawLine(dlist.head.points[i].x-editor.scrollX+absX,dlist.head.points[i].y-editor.scrollY+absY,
                               dlist.head.points[i+1].x-editor.scrollX,dlist.head.points[i+1].y-editor.scrollY);
                }
                dlist = dlist.tail;
            }
            catch(NullPointerException e)
            {
                if (i>=1){
                Pfeil pfeil = new Pfeil(g,dlist.head.points[i-1].x-editor.scrollX+absX,dlist.head.points[i-1].y-editor.scrollY+absY,
                                          dlist.head.points[i].x-editor.scrollX,dlist.head.points[i].y-editor.scrollY);}
                dlist=dlist.tail;

            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                if (i>=1){
                Pfeil pfeil = new Pfeil(g,dlist.head.points[i-1].x-editor.scrollX+absX,dlist.head.points[i-1].y-editor.scrollY+absY,
                                          dlist.head.points[i].x-editor.scrollX,dlist.head.points[i].y-editor.scrollY);}
                dlist=dlist.tail;
            }
        }
        try
        {
            for (i=0;i<50;i++)
            {
                g.drawLine(pointsTr[i].x-editor.scrollX,pointsTr[i].y-editor.scrollY,
                           pointsTr[i+1].x-editor.scrollX,pointsTr[i+1].y-editor.scrollY);
            }
        }
        catch(NullPointerException e){return;}
        catch(ArrayIndexOutOfBoundsException e){return;}
    }

    //wherePoint(..)
    //Hilfsmethode fuer markTransitionMouseMoved
    //Funktion: Prueft, in welchem "Bereich" der Mauszeiger sich im Rechteck
    //          befindet.
    //Rueckgabewerte: 1 fuer obere Seite
    //                2 fuer rechte Seite
    //                3 fuer untere Seite
    //                4 fuer linke Seite

    private static int wherePoint(Rectangle r, int x, int y)
    {
        Rectangle r1 = new Rectangle(r.x,r.y,r.width/2,r.height/2);
        Rectangle r2 = new Rectangle(r.x+r.width/2,r.y,r.width/2,r.height/2);
        Rectangle r3 = new Rectangle(r.x+r.width/2,r.y+r.height/2,r.width/2,r.height/2);
        Rectangle r4 = new Rectangle(r.x,r.y+r.height/2,r.width/2,r.height/2);
        double m1 = (double)r.width/(double)r.height;// (x/y)
        double m2 = (double)r.height/(double)r.width;// (y/x)
        //Abfrage: wo liegt Punkt genau??
        if (((r1.contains(x,y)) &&
            (double)Betrag(r.y,y)/(double)Betrag(r.x,x)<=m2) ||
            (r2.contains(x,y)) &&
            ((double)Betrag(r.x+r.width/2,x)/(double)Betrag(r.y+r.height/2,y)<=m1))
            return 1; //Punkt liegt im "oberen" Bereich
        else
        if ((r2.contains(x,y)) &&
           ((double)Betrag(r.x+r.width/2,x)/(double)Betrag(r.y+r.height/2,y)>m1) ||
           ((r3.contains(x,y)) &&
           ((double)Betrag(r.y+r.height/2,y)/(double)Betrag(r.x+r.width/2,x))<=m2))
            return 2; //Punkt liegt im "rechten" Bereich
        else
        if (((r3.contains(x,y)) &&
             ((double)Betrag(r.y+r.height/2,y)/(double)Betrag(r.x+r.width/2,x)>m2)) ||
            (r4.contains(x,y)) &&
            ((double)Betrag(r.x,x)/(double)Betrag(r.y+r.height,y)>m1))
            return 3; //Punkt liegt im "unteren" Bereich
        else return 4; //Punkt liegt im "linken" Bereich
    }

/*
    Methode: getnullIndex(Point[] p)
    Funktion: liefert den ersten Index, der den Wert null hat.
    Benutzt von: addTransitionMouseMoved
*/
    private static int getnullIndex(Point[] p)
    {
        int i=0;
        try {while (p[i] !=null) i++;}
        catch(ArrayIndexOutOfBoundsException a) {return -1;}
        catch(NullPointerException a) {}
        return i;
    }

/*
    Methode: setnullArray(Point[] p)
    Funktion: setzt das Point[]-Array auf null
    Benutzt von: addTransitionMouseMoved
*/
    private static void setnullArray(Point[] p)
    {
        for(int i=0;i<50;i++) pointsTr[i] = null;
    }

/*
    Methode: checkMouseButton(MouseEvent e,int button)
    Funktion: Ueberprueft, ob MausTaste (button) gedrueckt wurde
    Werte fuer button: 1: linke Maustaste
                       2: mittlere Maustaste
                       3: rechte Maustaste
    Rueckgabe: true, wenn die abgefragte Taste gedrueckt wurde, sonst false
*/

    private static boolean checkMouseButton(MouseEvent e,int button)
    {
        switch(button)
        {
            case 1: {if ((e.getModifiers() & InputEvent.BUTTON1_MASK)!=0) return true;break;}
            case 2: {if ((e.getModifiers() & InputEvent.BUTTON2_MASK)!=0) return true;break;}
            case 3: {if ((e.getModifiers() & InputEvent.BUTTON3_MASK)!=0) return true;}
        }
        return false;
    }


    public static Point getScrollPosition(Editor editor)
    {
        return new Point(editor.scrollX,editor.scrollY);
    }

/*
    Methode: getStateList(..)
    Funktion: durchlaeuft den Statechart und gibt die Zustaende als eine
              Liste zurueck (mit absoluten Koordinaten)
*/
    public static StateList dfs(StateList sl)//getStateList(..)
    {
        StateList list = sl;
        while (list != null)
        {
            if (list.head instanceof Or_State)
            {
                Or_State os = (Or_State)list.head;
                if (os.substates != null) dfs(os.substates);
            }
            else if (list.head instanceof And_State)
            {
                And_State as = (And_State)list.head;
                if (as.substates != null) dfs(as.substates);
            }
            list = list.tail;
        }
        return null;
    }

/*
    Methode: getOpaState(..)
    Funktion: liefert den uebergeordneten Zustand, der den Punkt (x,y)
              enthaelt
    Zweck: wird fuer Einfuegen von Transitionen benutzt
*/

    public static State getOpaState(int x, int y,Editor editor)
    {
        Or_State os = (Or_State)editor.statechart.state;
        if (os == null) return null;
        StateList list = os.substates;
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s == null) return null;
        else
        s = EditorUtils.getInnermostStateOf(s.rect.x-1,s.rect.y-1,editor);
        if (s==null) return null;
        else return s;
    }

    public static void getTest(int x, int y, Editor editor)
    {
        if (editor.statechart.state instanceof Or_State)
        {
            Or_State os = (Or_State) editor.statechart.state;
            Point p = getRelativeCoord(x,y,0,0,os.substates);
            System.out.println(p.x);
            System.out.println(p.y);
            System.out.println(x);
            System.out.println(y);
            System.out.println("---------");
        }
    }

/*
    Methode: getRelativeCoord(int x, int y,int relX, int relY, StateList sl)
    Funktion: berechnet die relative Koordinate aus (x,y) zum "innersten Zustand"
    Parameter: (x,y) Mausklick
               relX,relY : (0,0)-Punkt (Ursprung)
               sl : statelist
    Rueckgabe: Punkt, der absolute Koordinate zum (x,y) darstellt.
    Bsp: Aufruf: getRelativeCoord(200,100,0,0,editor.statechart.state.substates)
*/

    private static Point getRelativeCoord(int x, int y,int relX, int relY, StateList sl)
    {
        StateList list = sl;
        int x1=relX, y1=relY;
        while (list != null)
        {
            if (list.head.rect.contains(x,y))
            {
                System.out.println(list.head.rect.x);
                System.out.println(list.head.rect.y);
                x1 += list.head.rect.x;
                y1 += list.head.rect.y;
                if (list.head instanceof Or_State)
                {
                    System.out.println("ja");
                    Or_State os = (Or_State)list.head;
                    if (os.substates != null) return getRelativeCoord(x,y,x1,y1,os.substates);
                }
            }
            list = list.tail;
        }
        return new Point(x1,y1);
    }


    //Funktion: liefert den Betrag einer Differenz zweier Zahlen

    private static int Betrag(int x, int y)
    {
        if (x < y) return (y-x);
        else return (x-y);
    }
}
