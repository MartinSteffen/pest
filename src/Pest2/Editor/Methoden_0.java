package editor;

import absyn.*;
import gui.*;
import java.awt.*;
import java.awt.event.*;


public class Methoden_0
{
    private static Point mMoved = new Point(0,0); //enthaehlt absolute Koordinate
    private static Point conpos = new Point(0,0);
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
                        case 1:{y = r.y+1;break;}
                        case 2:{x = r.x+r.width-1;break;}
                        case 3:{y = r.y+r.height-1;break;}
                        case 4:{x = r.x+1;}
                    }
            }
            if (s instanceof Basic_State)
                switch (wherePoint(r,x,y)) //in welchem "Bereich" liegt der Punkt??
                {
                    case 1:{y = r.y+1;break;}
                    case 2:{x = r.x+r.width-1;break;}
                    case 3:{y = r.y+r.height-1;break;}
                    case 4:{x = r.x+1;}
                }
        }
        pointsTr[j] = new CPoint(x,y);
        if (j==1)
        {
            g.setXORMode(editor.getBackground());
            g.drawOval((int)((double)pointsTr[0].x*Methoden_1.getFactor())-editor.scrollX,
                       (int)((double)pointsTr[0].y*Methoden_1.getFactor())-editor.scrollY,5,5);
        }
        if (checkMouseButton(e,1))
        {
            if (j<=0)
            {
                g.drawOval((int)((double)x*Methoden_1.getFactor())-editor.scrollX,
                           (int)((double)y*Methoden_1.getFactor())-editor.scrollY,5,5);} // nur bei zwei Punkten erst zeichnen
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

                Connector con1 = getConEnvOf(pointsTr[0].x,pointsTr[0].y,editor);
                Connector con2 = getConEnvOf(pointsTr[j].x,pointsTr[j].y,editor);

                s1 = EditorUtils.getInnermostStateOf(pointsTr[0].x,pointsTr[0].y,editor);
                s2 = EditorUtils.getInnermostStateOf(pointsTr[j].x,pointsTr[j].y,editor);

                for (int k=0;k<=j;k++) P[k] = new CPoint(pointsTr[k].x-r.x,pointsTr[k].y-r.y);

                Or_State os = (Or_State)s;

                Tr dummyTr = null;
                TrList dlist = null;
                TLabel tlabeldummy = new TLabel(new GuardEmpty(new Dummy(new Location(1))),new ActionEmpty(new Dummy(new Location(1))));

                if (con1 != null & con2 != null)
                {
                    dummyTr = new Tr(con1.name,con2.name,tlabeldummy,P);
                }
                else
                if (con1 != null & con2 == null)
                {
                    dummyTr = new Tr(con1.name,s2.name,tlabeldummy,P);
                }
                else
                if (con1 == null & con2 != null)
                {
                    dummyTr = new Tr(s1.name,con2.name,tlabeldummy,P);
                }
                else
                if (con1 == null & con2 == null)
                {
                    dummyTr = new Tr(s1.name,s2.name,tlabeldummy,P);
                }
                dlist = new TrList(dummyTr,os.trs);
                os.trs = dlist;
                setNullArray(pointsTr);
                EditorUtils.showStates(editor);
                showAllTrans(editor);
                editor.setChangedStatechart(true);
            }
        }
        else if (checkMouseButton(e,3))
        {
            if ((j<=0)||(j==49)) pointsTr[j]=null;
            return;
        }
        g.dispose();
    }

    public static void addConnectorMouseClicked(int x, int y, Editor editor)
    {
        if (getConEnvOf(x,y,editor) != null) return;
        if (EditorUtils.getInnermostStateOf(x,y,editor) instanceof Basic_State) return;
        State s = getFirstOrStateOf(x,y,editor);
        Rectangle r = abs(editor,s);
        Or_State os = (Or_State)s;
        Connector dummy = new Connector(new Conname("...Connector"),new CPoint(x-r.x,y-r.y));
        ConnectorList clist = new ConnectorList(dummy,os.connectors);
        os.connectors = clist;
        editor.setChangedStatechart(true);
        updateAll(editor);
    }


    //transitionmouseMoved(..)
    //Methode soll aufgerufen werden, wenn Maus bewegt wird.
    //Funktion: ruft weitere Methoden auf, die bearbeitet werden sollen,
    //          wenn Transition gewaehlt ist
    //Parametern: State, x- und y-Koordinaten des Mauszeigers

    public static void transitionMouseMoved(State state,int x, int y, Editor editor)
    {
        State st = editor.statechart.state;
        if (st == null) return;
        if (!st.rect.contains(x,y)) return;
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
            g.drawOval((int)((double)mMoved.x*Methoden_1.getFactor())-editor.scrollX,
                       (int)((double)mMoved.y*Methoden_1.getFactor())-editor.scrollY,5,5);
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
        g.drawOval((int)((double)mMoved.x*Methoden_1.getFactor())-editor.scrollX,
                   (int)((double)mMoved.y*Methoden_1.getFactor())-editor.scrollY,rad,rad);

        if (!r.contains(x,y))
        {
            mMoved = new Point(0,0);
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

                    g.drawOval((int)((double)x*Methoden_1.getFactor())-editor.scrollX,
                               (int)((double)r.y*Methoden_1.getFactor())-editor.scrollY-2-rad,rad,rad);
                    mMoved.x=x;mMoved.y=r.y-2-rad;break;}
            case 2:{
                    if (state instanceof Or_State)
                        {if (!((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                            (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20)))
                            break;}

                    g.drawOval((int)((double)r.x*Methoden_1.getFactor())-editor.scrollX+r.width+2,
                               (int)((double)y*Methoden_1.getFactor())-editor.scrollY,rad,rad);
                    mMoved.x=r.x+r.width+2;mMoved.y=y;break;}
            case 3:{
                    if (state instanceof Or_State)
                        {if (!((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                            (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20)))
                            break;}

                    g.drawOval((int)((double)x*Methoden_1.getFactor())-editor.scrollX,
                               (int)((double)r.y*Methoden_1.getFactor())-editor.scrollY+r.height+2,rad,rad);
                    mMoved.x=x;mMoved.y=r.y+r.height+2;break;}
            case 4:{
                    if (state instanceof Or_State)
                        {if (!((Betrag(x,r.x)<20) || (Betrag(x,r.x+r.width) < 20) ||
                            (Betrag(y,r.y)<20) || (Betrag(y,r.y+r.height) < 20)))
                            break;}

                    g.drawOval((int)((double)r.x*Methoden_1.getFactor())-editor.scrollX-2-rad,
                               (int)((double)y*Methoden_1.getFactor())-editor.scrollY,rad,rad);
                    mMoved.x=r.x-2-rad;mMoved.y=y;}
        }
        EditorUtils.showStates(editor);
        g.dispose();
    }

    public static void markConMouseMoved(int x, int y, Editor editor)
    {
        State state = editor.statechart.state;
        if (state == null) return;
        if (!state.rect.contains(x,y)) return;
        Connector con = getConEnvOf(x,y,editor);
        Graphics g = editor.getGraphics();
        int big = 10;
        switch((int)(Methoden_1.getFactor()*100))
        {
            case 400 : {big = 15; break;}
            case 25  : {big = 5;break;}
        }
        if (con != null)
        {
            State s = getFirstOrStateOf(x,y,editor);
            Rectangle r = abs(editor,s);
            if (editor.gui.getConnectorcolor() != Color.blue)
                g.setColor(Color.blue);
            else g.setColor(Color.red);
            g.fillOval((int)((double)(con.position.x+r.x)*Methoden_1.getFactor())-editor.scrollX,
                       (int)((double)(con.position.y+r.y)*Methoden_1.getFactor())-editor.scrollY,big,big);
            conpos = new Point((int)((double)(con.position.x+r.x)*Methoden_1.getFactor())-editor.scrollX,
                                (int)((double)(con.position.y+r.y)*Methoden_1.getFactor())-editor.scrollY);
            g.dispose();
            return;
        }
        g.setColor(editor.gui.getConnectorcolor());
        g.fillOval(conpos.x,conpos.y,big,big);
        conpos = new Point(0,0);
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

    public static void drawTransition(TrList trlist,int absX, int absY,Editor editor)
    {
        Graphics g = editor.getGraphics();
        g.setColor(editor.gui.getTransitioncolor());
        TrList dlist = trlist;
        int i=0;
        if (editor.cbbezier.getState())
        {
            while (dlist != null)
            {
                bezier(dlist.head.points,absX,absY,editor.gui.getTransitioncolor(),editor);
                dlist = dlist.tail;
            }
            return;
        }
        while (dlist != null)
        {
            i=0;
            try
            {
                for (i=0;i<=dlist.head.points.length-1;i++)
                {
                    if (i==0) g.fillOval((int)((double)(dlist.head.points[0].x+absX)*Methoden_1.getFactor())-2-editor.scrollX,
                               (int)((double)(dlist.head.points[0].y+absY)*Methoden_1.getFactor())-editor.scrollY,4,4);

                    g.drawLine((int)((double)(dlist.head.points[i].x+absX)*Methoden_1.getFactor())-editor.scrollX,
                               (int)((double)(dlist.head.points[i].y+absY)*Methoden_1.getFactor())-editor.scrollY,
                               (int)((double)(dlist.head.points[i+1].x+absX)*Methoden_1.getFactor())-editor.scrollX,
                               (int)((double)(dlist.head.points[i+1].y+absY)*Methoden_1.getFactor())-editor.scrollY);

                }
                dlist = dlist.tail;
            }
            catch(NullPointerException e)
            {
                if (i>=1){
                    Pfeil pfeil = new Pfeil(g,(int)((double)(dlist.head.points[i-1].x+absX)*Methoden_1.getFactor())-editor.scrollX,
                                          (int)((double)(dlist.head.points[i-1].y+absY)*Methoden_1.getFactor())-editor.scrollY,
                                          (int)((double)(dlist.head.points[i].x+absX)*Methoden_1.getFactor())-editor.scrollX,
                                          (int)((double)(dlist.head.points[i].y+absY)*Methoden_1.getFactor())-editor.scrollY);
                }
                dlist=dlist.tail;

            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                if (i>=1){
                Pfeil pfeil = new Pfeil(g,(int)((double)(dlist.head.points[i-1].x+absX)*Methoden_1.getFactor())-editor.scrollX,
                                          (int)((double)(dlist.head.points[i-1].y+absY)*Methoden_1.getFactor())-editor.scrollY,
                                          (int)((double)(dlist.head.points[i].x+absX)*Methoden_1.getFactor())-editor.scrollX,
                                          (int)((double)(dlist.head.points[i].y+absY)*Methoden_1.getFactor())-editor.scrollY);}
                dlist=dlist.tail;
            }
        }
        try
        {
            for (i=0;i<50;i++)
            {
                g.drawLine((int)((double)pointsTr[i].x*Methoden_1.getFactor())-editor.scrollX,
                           (int)((double)pointsTr[i].y*Methoden_1.getFactor())-editor.scrollY,
                           (int)((double)pointsTr[i+1].x*Methoden_1.getFactor())-editor.scrollX,
                           (int)((double)pointsTr[i+1].y*Methoden_1.getFactor())-editor.scrollY);
            }
        }
        catch(NullPointerException e){return;}
        catch(ArrayIndexOutOfBoundsException e){return;}

    }


// x,y absolute Koordinate des innersten zustandes
    public static void bezier(Point[] p1,int x, int y,Color col, Editor editor)
    {
        Graphics g = editor.getGraphics();
        g.setColor(col);
	    double[] px = new double[p1.length+1];
	    double[] py = new double[p1.length+1];
	    int m=p1.length,i=0,count = 0,k=0;
	    if (m==2) m = 3;
        double t;
        Point[] points = new Point[p1.length*50];
	    for (t = 0; t <= 1; t+=0.1)
        {
            for(i=0;i<=p1.length-1;i++){
                if ( i == 1 & p1.length == 2)
                {
                    if (Betrag(p1[0].x,p1[1].x) < 5 | Betrag(p1[0].y,p1[1].y) < 5)
                    {
                        px[1] = (double)((p1[0].x+p1[1].x)/2+x);
                        py[1] = (double)((p1[0].y+p1[1].y)/2+y);
                        px[2] = (double)(p1[1].x+x);
                        py[2] = (double)(p1[1].y+y);
                    }
                    else
                    {
                        px[1] = (double)((p1[0].x+p1[1].x)/2+x);
                        if (p1[0].x < p1[1].x)
                            py[1] = (double)(p1[1].y+y);//0].y+neu+y);
                        else py[1] = (double)(p1[0].y+y);
                        px[2] = (double)(p1[1].x+x);
                        py[2] = (double)(p1[1].y+y);
                    }
                    break;

                }
      	        px[i] = (double)p1[i].x+x;
  	            py[i] = (double)p1[i].y+y;
  	        }

            m = p1.length;
            if (m == 2) m = 3;
            while (m >= 0)
            {
                for (int j=0;j<(m-1);j++)
                {
                    px[j] = px[j]+(t*(double)(px[j+1]-px[j]));
                    py[j] = py[j]+(t*(double)(py[j+1]-py[j]));
	            }
                m--;
       	    }
       	    count++;
            points[k++] = new Point((int)(px[0]*Methoden_1.getFactor())-editor.scrollX,
                                    (int)(py[0]*Methoden_1.getFactor())-editor.scrollY);
        }
        for (i=0;i<count-1;i++){
            if (i==0) g.fillOval(points[0].x,points[0].y,4,4);
            g.drawLine(points[i].x,points[i].y,points[i+1].x,points[i+1].y);
        }
        Pfeil pf = new Pfeil(g,points[count-2].x,points[count-2].y,points[count-1].x,points[count-1].y);
    }

    private static void showAllConnectors(Editor editor)
    {
        StateList list = null;
        Rectangle r = null;
        try {
            list = editor.stateList;
            StateList help = list;
            while (help != null)
            {
                if (help.head instanceof Or_State)
                {
                    Or_State os = (Or_State)help.head;
                    if (os.connectors == null) {help = help.tail;continue;}
                    r = abs(editor,help.head);
                    drawConnectors(os.connectors,r.x,r.y,editor);
                }
                help = help.tail;
            }
        }
        catch (Exception e) {}
    }

    private static void drawConnectors(ConnectorList clist, int absX, int absY, Editor editor)
    {
        ConnectorList list = clist;
        Graphics g = editor.getGraphics();
        g.setColor(editor.gui.getConnectorcolor());
        int big = 10;
        switch((int)(Methoden_1.getFactor()*100))
        {
            case 400 : {big = 15; break;}
            case 25  : {big = 5;break;}
        }
        while (list != null)
        {
            g.fillOval((int)((double)(list.head.position.x+absX)*Methoden_1.getFactor())-editor.scrollX,
                       (int)((double)(list.head.position.y+absY)*Methoden_1.getFactor())-editor.scrollY,big,big);
            list = list.tail;
        }
        g.dispose();
    }


    //wherePoint(..) (x,y): relative Koordinate
    //Hilfsmethode fuer markTransitionMouseMoved
    //Funktion: Prueft, in welchem "Bereich" der Mauszeiger sich im Rechteck
    //          befindet.
    //Rueckgabewerte: 1 fuer obere Seite
    //                2 fuer rechte Seite
    //                3 fuer untere Seite
    //                4 fuer linke Seite

    public static int wherePoint(Rectangle r, int x, int y)
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

    //Funktion: liefert den Betrag einer Differenz zweier Zahlen

    private static int Betrag(int x, int y)
    {
        if (x < y) return (y-x);
        else return (x-y);
    }

/*
    Methode: showStateNames(Editor editor)
    Funktion: gibt die Zustandsnamen aus
*/

    private static void showStateNames(Editor editor)
    {
        StateList list = null;
        Rectangle r = null;
        Graphics g = null;
        try {
            list = editor.stateList;
            g = editor.getGraphics();
            g.setColor(editor.gui.getStatecolor());
            int groesse = (int)(Methoden_1.getFactor()*100);
            Font font = new Font("Serif",Font.PLAIN,14);
            if (editor.fontsize != 0) g.setFont(new Font("Serif",Font.PLAIN,editor.fontsize));
            else{
            if (groesse < 25) g.setFont(font = new Font("Serif",Font.PLAIN,8));
            else if (groesse < 50) g.setFont(font = new Font("Serif",Font.PLAIN,10));
            else if (groesse < 100) g.setFont(font = new Font("Serif",Font.PLAIN,14));
            else if (groesse < 200) g.setFont(font = new Font("Serif",Font.PLAIN,16));
            else if (groesse < 300) g.setFont(font = new Font("Serif",Font.PLAIN,18));
            else if (groesse <= 400) g.setFont(font = new Font("Serif",Font.PLAIN,20));
            else if (groesse > 400) g.setFont(font = new Font("Serif",Font.PLAIN,22));
            }
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
                    {g.drawString(help.head.name.name,(int)((double)r.x*Methoden_1.getFactor())+10-editor.scrollX,
                                  (int)((double)r.y*Methoden_1.getFactor())+15-editor.scrollY);}
                else
                if (help.head instanceof And_State){
                    int hoehe = font.getSize();
                    int breite = font.getSize()*help.head.name.name.length();
                    g.drawRect((int)((double)r.x*Methoden_1.getFactor())+10-editor.scrollX-5,
                               (int)((double)r.y*Methoden_1.getFactor())-editor.scrollY-hoehe,breite,hoehe);
                    g.drawString(help.head.name.name,(int)((double)r.x*Methoden_1.getFactor())+10-editor.scrollX,
                                  (int)((double)r.y*Methoden_1.getFactor())-3-editor.scrollY);
                }
                help = help.tail;
            }
        }
        catch (Exception e) {}
    }


    private static void showConNames(Editor editor)
    {
        StateList list = null;
        Rectangle r = null;
        Graphics g = null;
        ConnectorList clist = null;
        try {
            list = editor.stateList;
            g = editor.getGraphics();
            g.setColor(editor.gui.getConnectorcolor());
            int groesse = (int)(Methoden_1.getFactor()*100);
            if (editor.fontsize != 0) g.setFont(new Font("Serif",Font.PLAIN,editor.fontsize));
            else{
            if (groesse < 25) g.setFont(new Font("Serif",Font.PLAIN,8));
            else if (groesse < 50) g.setFont(new Font("Serif",Font.PLAIN,10));
            else if (groesse < 100) g.setFont(new Font("Serif",Font.PLAIN,14));
            else if (groesse < 200) g.setFont(new Font("Serif",Font.PLAIN,16));
            else if (groesse < 300) g.setFont(new Font("Serif",Font.PLAIN,18));
            else if (groesse <= 400) g.setFont(new Font("Serif",Font.PLAIN,20));
            else if (groesse > 400) g.setFont(new Font("Serif",Font.PLAIN,22));
            }
            StateList help = list;
            while (help != null)
            {
                if (help.head instanceof Or_State)
                {
                    r = abs(editor,help.head);
                    clist = ((Or_State)(help.head)).connectors;
                    while (clist != null)
                    {
                        if (clist.head.name.name.length() > 2)
                        {
                            if((clist.head.name.name.charAt(0)=='.') & (clist.head.name.name.charAt(1)=='.') &
                               (clist.head.name.name.charAt(2)=='.'))
                            {
                                help = help.tail;
                                continue;
                            }
                        }
                        g.drawString(clist.head.name.name,(int)((double)(r.x+clist.head.position.x)*Methoden_1.getFactor())-editor.scrollX,
                                     (int)((double)r.y+clist.head.position.y*Methoden_1.getFactor())-2-editor.scrollY);
                        clist = clist.tail;
                    }
                }
                help = help.tail;
            }
            g.dispose();
        }
        catch (Exception e) {}
    }

    private static void showTransNames(Editor editor)
    {
        StateList list = null;
        Rectangle r = null;
        Graphics g = null;
        TrList tlist = null;
        try {
            list = editor.stateList;
            g = editor.getGraphics();
            g.setColor(editor.gui.getTransitioncolor());

            int groesse = (int)(Methoden_1.getFactor()*100);
            if (editor.fontsize != 0) g.setFont(new Font("Serif",Font.PLAIN,editor.fontsize));
            else{
            if (groesse < 25) g.setFont(new Font("Serif",Font.PLAIN,8));
            else if (groesse < 50) g.setFont(new Font("Serif",Font.PLAIN,10));
            else if (groesse < 100) g.setFont(new Font("Serif",Font.PLAIN,14));
            else if (groesse < 200) g.setFont(new Font("Serif",Font.PLAIN,16));
            else if (groesse < 300) g.setFont(new Font("Serif",Font.PLAIN,18));
            else if (groesse <= 400) g.setFont(new Font("Serif",Font.PLAIN,20));
            else if (groesse > 400) g.setFont(new Font("Serif",Font.PLAIN,22));
            }

            StateList help = list;
            while (help != null)
            {
                if (help.head instanceof Or_State)
                {
                    r = abs(editor,help.head);
                    tlist = ((Or_State)(help.head)).trs;
                    while (tlist != null)
                    {
                        if (tlist.head.label.caption.length() > 2)
                        {
                            if((tlist.head.label.caption.charAt(0)=='.') & (tlist.head.label.caption.charAt(1)=='.') &
                               (tlist.head.label.caption.charAt(2)=='.'))
                            {
                                help = help.tail;
                                continue;
                            }
                        }
                        String trName = tlist.head.label.caption;
                        String neuString = trName;
                        if (trName.length() > 20) neuString = trName.substring(0,20);
                        g.drawString(neuString,(int)((double)(r.x+tlist.head.label.position.x)*Methoden_1.getFactor())-editor.scrollX,
                                     (int)((double)(r.y+tlist.head.label.position.y)*Methoden_1.getFactor())-editor.scrollY);
                        tlist = tlist.tail;
                    }
                }
                help = help.tail;
            }
            g.dispose();
        }
        catch (Exception e) {}
    }


    public static CRectangle abs(Editor editor, State state) {

        // liefert das Rectangle von state mit absoluten Koordinaten
        // Achtung: state muá im Baum h„ngen. Ist dies nicht der Fall
        // so ist die andere abs-Methode zu benutzen!

	if (state == null) return null;
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

//State "root" kann zurueck gegeben werden
    public static State getFirstOrStateOf(int x, int y, Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
	if (s.equals(editor.statechart.state) & !(editor.statechart.state instanceof Or_State)) return null;
        Rectangle r = abs(editor,s);
	if (r == null) return null;
        while (!(s instanceof Or_State))
        {
            s = EditorUtils.getInnermostStateOf(r.x-1,r.y-1,editor);
            r = abs(editor,s);
        }
        return s;
    }

/*
    Methode: getConEnvOf
    Funktion: liefert den Connector, der um den absoluten Punkt(x,y) mit 10 Pixel Umgebung enthaelt
*/
    public static Connector getConEnvOf(int x, int y, Editor editor)
    {
        State s = getFirstOrStateOf(x,y,editor);
        Rectangle r = abs(editor,s), rectcon = new Rectangle(x-5,y-5,10,10); //Connector hat durchmesser 10
        Or_State os = (Or_State)s;
        ConnectorList list = os.connectors;
        while (list != null)
        {
            if (rectcon.contains(r.x+list.head.position.x+5,r.y+list.head.position.y+5))
                return list.head;
            list = list.tail;
        }
        return null;
    }

    public static void showDefaultState(Editor editor)
    {
        StateList help = editor.stateList;
        Or_State os = null;
        StatenameList list = null;
        StateList slist = null;
        while (help != null)
        {
            if (help.head instanceof Or_State)
            {
                os = (Or_State)help.head;
                list = os.defaults;
                while (list != null)
                {
                    if (help.head.name == list.head)
                        drawDefaultState(help.head,editor);
                    slist = os.substates;
                    while (slist != null)
                    {
                        if (list.head == slist.head.name)
                            drawDefaultState(slist.head,editor);
                        slist = slist.tail;
                    }
                    list = list.tail;
                }
            }
            help = help.tail;
        }
    }

    private static void drawDefaultState(State state, Editor editor)
    {
        Rectangle r = abs(editor,state);
        Point[] p = new Point[2];
        p[0] = new Point(-15,0);
        p[1] = new Point(0,15);
        bezier(p,r.x,r.y,editor.gui.getStatecolor(),editor);
    }


    public static void addDefaultMouseClicked(int x, int y, Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        State s1 = getFirstOrStateOf(x,y,editor);
        Or_State os = (Or_State)s1;
        if (os == null) return;
        StatenameList list = os.defaults;
        if (list != null)
        {
            if (list.head == s.name){
                StatenameList neu = os.defaults;
                StatenameList copy = null;
                while (neu != null){
                    if (neu.head != s.name)
                        copy = new StatenameList(neu.head,copy);
                    neu = neu.tail;
                }
                os.defaults = copy;
                editor.setChangedStatechart(true);
                return;
            }
            while (list.tail != null)
            {
                if (list.tail.head == s.name){
                    StatenameList neu = os.defaults;
                    StatenameList copy = null;
                while (neu != null){
                    if (neu.head != s.name)
                        copy = new StatenameList(neu.head,copy);
                    neu = neu.tail;
                }
                os.defaults = copy;
                editor.setChangedStatechart(true);
                return;
                }
                list = list.tail.tail;
            }
        }
        os.defaults = new StatenameList(s.name,os.defaults);
    }

    public static void updateAll(Editor editor)
    {
        showAllTrans(editor);
        showAllConnectors(editor);
        showStateNames(editor);
        showConNames(editor);
        showTransNames(editor);
        showDefaultState(editor);
    }

}
