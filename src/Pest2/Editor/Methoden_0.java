/*
Sammlung von Methoden fuer den Editor
*/
package editor;

import absyn.*;
import gui.*;
import java.awt.*;
import java.awt.event.*;


public class Methoden_0
{
    private static Point mMoved = new Point(0,0); //enthaehlt absolute Koordinate
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
    Hilfsmethode: getNullIndex(Point[] p)
                  setNullArray(CPoint[] p)
*/
    public static void addTransitionMouseClicked(MouseEvent e,int x,int y,Editor editor)
    {
        State s1=null,s2=null;
        Graphics g = editor.getGraphics();
        int j=getNullIndex(pointsTr);
/*        if (j>0) //nur parallele oder senkrechte Linien!!
        {
            if ((Betrag(pointsTr[j-1].x,x)<=Betrag(pointsTr[j-1].y,y))) x = pointsTr[j-1].x;
            else y = pointsTr[j-1].y;
        }*/
        pointsTr[j] = new CPoint(x,y);
        CRectangle r = new CRectangle(0,0,0,0);
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s != editor.statechart.state)
        {
            r = abs(editor,s);
            if (s instanceof Or_State)
            {
                if ((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                    (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20))
                    switch (wherePoint(r,x,y)) //in welchem "Bereich" liegt der Punkt??
                    {
                        case 1:{y = r.y;break;}
                        case 2:{x = r.x+r.width;break;}
                        case 3:{y = r.y+r.height;break;}
                        case 4:{x = r.x;}
                    }
            }
            if (s instanceof Basic_State)
                switch (wherePoint(r,x,y)) //in welchem "Bereich" liegt der Punkt??
                {
                    case 1:{y = r.y;break;}
                    case 2:{x = r.x+r.width-1;break;}
                    case 3:{y = r.y+r.height-1;break;}
                    case 4:{x = r.x;}
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
                CPoint[] P = new CPoint[j+1];
                s = EditorUtils.getInnermostStateOf(pointsTr[0].x,pointsTr[0].y,editor);
                r = abs(editor,s);
                if (s instanceof Basic_State)
                {
                    s = EditorUtils.getInnermostStateOf(r.x-1,r.y-1,editor);
                    r = abs(editor,s);
                }

                for (int k=0;k<=j;k++) P[k] = new CPoint(pointsTr[k].x-r.x,pointsTr[k].y-r.y);

                s1 = EditorUtils.getInnermostStateOf(pointsTr[0].x,pointsTr[0].y,editor);
                s2 = EditorUtils.getInnermostStateOf(pointsTr[j].x,pointsTr[j].y,editor);
                Or_State os = (Or_State)s;
                Tr dummyTr = new Tr(s1.name,s2.name,null,P);
                TrList dlist = new TrList(dummyTr,os.trs);
                os.trs = dlist;
                setNullArray(pointsTr);
                EditorUtils.showStates(editor);
                showAllTrans(editor);
            }
        }
        else if (checkMouseButton(e,3))
        {
            if ((j<=0)||(j==49)) pointsTr[j]=null;
            return;
        }
        g.dispose();
    }

    //transitionmouseMoved(..)
    //Methode soll aufgerufen werden, wenn Maus bewegt wird.
    //Funktion: ruft weitere Methoden auf, die bearbeitet werden sollen,
    //          wenn Transition gewaehlt ist
    //Parametern: State, x- und y-Koordinaten des Mauszeigers

    public static void transitionMouseMoved(State state,int x, int y, Editor editor)
    {
        boolean clear = true;
        CRectangle r = null;
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s != editor.statechart.state)
        {
            r = abs(editor,s);
            markTransitionMouseMoved(s,x,y,editor);
            clear = false;
        }
        if (clear)
        {
            Graphics g = editor.getGraphics();
            g.setColor(editor.getBackground());
            g.drawOval(mMoved.x-editor.scrollX,mMoved.y-editor.scrollY,5,5);
            g.dispose();
        }
        showAllTrans(editor);
    }

    //markTransitionMouseMoved(..)
    //Funktion: Prueft, ob Mauszeiger im Rechtecht, wenn ja:
    //          zeichnet an der Seite eines Rechtecks einen Kreis, um
    //          das Hinzufuegen von Transition zu erleichtern
    //          sonst: nichts
    //Hilfsmethode: wherePoint
    //Parametern: Rechteck und x-y-Koordinate des Mauszeigers


    private static void markTransitionMouseMoved(State state,int x, int y,Editor editor)
    {
        int rad = 5;
        CRectangle r = abs(editor,state);
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
            case 1:{
                    if (state instanceof Or_State)
                        {if (!((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                            (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20)))
                            break;}

                    g.drawOval(x-editor.scrollX,r.y-editor.scrollY-2-rad,rad,rad);
                    mMoved.x=x;mMoved.y=r.y-2-rad;break;}
            case 2:{
                    if (state instanceof Or_State)
                        {if (!((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                            (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20)))
                            break;}

                    g.drawOval(r.x-editor.scrollX+r.width+2,y-editor.scrollY,rad,rad);
                    mMoved.x=r.x+r.width+2;mMoved.y=y;break;}
            case 3:{
                    if (state instanceof Or_State)
                        {if (!((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                            (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20)))
                            break;}

                    g.drawOval(x-editor.scrollX,r.y-editor.scrollY+r.height+2,rad,rad);
                    mMoved.x=x;mMoved.y=r.y+r.height+2;break;}
            case 4:{
                    if (state instanceof Or_State)
                        {if (!((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                            (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20)))
                            break;}

                    g.drawOval(r.x-editor.scrollX-2-rad,y-editor.scrollY,rad,rad);
                    mMoved.x=r.x-2-rad;mMoved.y=y;}
        }
        g.dispose();
    }

    private static void showAllTrans(Editor editor)
    {
        StateList list = null;
        Rectangle r = null;
        Graphics g = null;
        try {
            list = editor.stateList;
            g = editor.getGraphics();
            g.setColor(Color.red);
            StateList help = list;
            while (help != null)
            {
                if (help.head instanceof Or_State)
                {
                    Or_State os = (Or_State)help.head;
                    if (os.trs == null) {help = help.tail;continue;}
                    r = abs(editor,help.head);
                    drawTransition(os.trs,r.x,r.y,editor);
                }
                help = help.tail;
            }
        }
        catch (Exception e) {}
    }

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
                for (i=0;i<=dlist.head.points.length-1;i++)
                {
                    g.drawLine(dlist.head.points[i].x-editor.scrollX+absX,dlist.head.points[i].y-editor.scrollY+absY,
                               dlist.head.points[i+1].x+absX-editor.scrollX,dlist.head.points[i+1].y+absY-editor.scrollY);
                }
                dlist = dlist.tail;
            }
            catch(NullPointerException e)
            {
                if (i>=1){
                Pfeil pfeil = new Pfeil(g,dlist.head.points[i-1].x-editor.scrollX+absX,dlist.head.points[i-1].y-editor.scrollY+absY,
                                          dlist.head.points[i].x+absX-editor.scrollX,dlist.head.points[i].y+absY-editor.scrollY);}
                dlist=dlist.tail;

            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                if (i>=1){
                Pfeil pfeil = new Pfeil(g,dlist.head.points[i-1].x+absX-editor.scrollX,dlist.head.points[i-1].y+absY-editor.scrollY,
                                          dlist.head.points[i].x+absX-editor.scrollX,dlist.head.points[i].y+absY-editor.scrollY);}
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

    //wherePoint(..) (x,y): relative Koordinate
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
    Methode: getNullIndex(Point[] p)
    Funktion: liefert den ersten Index, der den Wert null hat.
    Benutzt von: addTransitionMouseMoved
*/
    private static int getNullIndex(Point[] p)
    {
        int i=0;
        try {while (p[i] !=null) i++;}
        catch(ArrayIndexOutOfBoundsException a) {return -1;}
        catch(NullPointerException a) {}
        return i;
    }

/*
    Methode: setNullArray(CPoint[] p)
    Funktion: setzt das Point[]-Array auf null
    Benutzt von: addTransitionMouseMoved
*/
    private static void setNullArray(CPoint[] p)
    {
        for(int i=0;i<50;i++) p[i] = null;
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
                x1 += list.head.rect.x;
                y1 += list.head.rect.y;
                if (list.head instanceof Or_State)
                {
                    Or_State os = (Or_State)list.head;
                    if (os.substates != null) return getRelativeCoord(x-list.head.rect.x,y-list.head.rect.y,x1,y1,os.substates);
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

/*
    Methode: showNames(Editor editor)
    Funktion: gibt die Zustandsnamen aus
*/

    private static void showNames(Editor editor)
    {
        StateList list = null;
        Rectangle r = null;
        Graphics g = null;
        try {
            list = editor.stateList;
            g = editor.getGraphics();
            g.setColor(Color.red);
            StateList help = list;
            while (help != null) {
                r = abs(editor,help.head);
                if (help.head.name.name.length() > 2)
                {
                    if((help.head.name.name.charAt(0)=='.') & (help.head.name.name.charAt(1)=='.') &
                       (help.head.name.name.charAt(2)=='.'))
                    {
                        help = help.tail;
                        continue;
                    }
                }
                if (help.head instanceof Or_State)
                    {g.drawString(help.head.name.name,r.x+10-editor.scrollX,r.y+15-editor.scrollY);}
                else
                if (help.head instanceof Basic_State)
                    {g.drawString(help.head.name.name,r.x+(20)-editor.scrollX,r.y+15-editor.scrollY);}
                help = help.tail;
            }
        }
        catch (Exception e) {}
    }

    private static CRectangle abs(Editor editor, State state) {

        // liefert das Rectangle von state mit absoluten Koordinaten
        // Achtung: state muá im Baum h„ngen. Ist dies nicht der Fall
        // so ist die andere abs-Methode zu benutzen!

        if (state.equals(editor.statechart.state))
            return state.rect;
        State father = EditorUtils.getFatherOf(state, editor);

        if (father.equals(editor.statechart.state))
            return state.rect;
        else {
            CRectangle fatherAbsRect = abs(editor, father);

            return new CRectangle(fatherAbsRect.x + state.rect.x,
                                  fatherAbsRect.y + state.rect.y,
                                  state.rect.width, state.rect.height);
        }
    }

    public static void updateAll(Editor editor)
    {
        showAllTrans(editor);
        showNames(editor);
    }

}
