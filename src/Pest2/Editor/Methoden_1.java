
package editor;

import absyn.*;
import gui.*;
import tesc1.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class ObjectList
{
    Absyn head;
    ObjectList tail;
    public ObjectList(Absyn h, ObjectList t)
    {
        head = h;
        tail = t;
    }
}


public class Methoden_1
{
    private static Point points[] = new Point[3];
    private static double factor = 1;
    private static Tr trans = null;
    public static State selectOneState = null;
    public static Tr selectOneTr = null;
    public static Connector selectOneConnector = null;
    public static State copyOneState = null;
    public static Tr copyOneTr = null;
    public static Connector copyOneConnector = null;
    public static Absyn markLast = null; //das zuletzt markierte Objekt

    public static ObjectList selectList = null,copyList = null;


/*
    Methode: addStatenameMouseClicked(int x, int y, Editor editor)
    Funktion: erzeugt ein Dialogfenster fuer die Eingabe von Statename zu dem
              Zustand, der x,y im innersten hat.
*/
    public static void addStatenameMouseClicked(int x, int y, Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s == null) return;
/*
        if (s==editor.statechart.state)
        {
            editor.gui.OkDialog(editor,"Fehler","Zustand kann nicht umbenannt werden!");
            return;
        }
*/
        String name = editor.gui.EingabeDialog(editor,"Zustand benennen", "Name fuer Zustand:", s.name.name);
        if (name != null)
        {
            s.name = new Statename(name);
            changeTransName(name,x,y,editor);
            editor.repaint();
        }
    }

    public static void addAndNameMouseClicked(int x, int y, Editor editor)
    {
        State s = getFirstAndOf(x,y,editor);
        if (s == null) return;
        String name = editor.gui.EingabeDialog(editor,"And_Zustand benennen", "Name fuer And_Zustand:", s.name.name);
        if (name != null)
        {
            s.name = new Statename(name);
            changeTransName(name,x,y,editor);
            editor.repaint();
        }
    }

    public static void addConNameMouseClicked(int x, int y, Editor editor)
    {
        Connector con = Methoden_0.getConEnvOf(x,y,editor);
        if (con == null) return;
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);
        String name = editor.gui.EingabeDialog(editor,"Connector benennen", "Name fuer Connector:", con.name.name);
        if (name != null)
        {
            con.name.name = name;
            editor.repaint();
        }
    }



/*
    Methode: addTransNameMouseClicked(int x, int y, Editor editor)
    Funktion: erzeugt ein Dialogfenster fuer die Eingabe von Transitionsnamen,

*/
    public static void addTransNameMouseClicked(int x, int y, Editor editor)
    {
        if (EditorUtils.getInnermostStateOf(x,y,editor) instanceof Basic_State) return;
        State s1 = Methoden_0.getFirstOrStateOf(x,y,editor);
        Or_State os = (Or_State)s1;
        if (os.trs==null){
            editor.gui.OkDialog(editor,"Fehler","Keine Transition vorhanden!");
        }
        else
        {
            Rectangle r = Methoden_0.abs(editor,s1);
            trans = getNearestTransOf(os.trs,new Point(r.x,r.y),x,y,editor);
            String name = editor.gui.EingabeDialog(editor,"Transition benennen", "Name fuer Transition:",trans.label.caption);
            if (name == null) return;

            TESCLoader tesc = new TESCLoader(editor.gui);
        	TLabel tlabel = null;
            BufferedReader br = new BufferedReader(new StringReader(name));
            try
            {
                tlabel = tesc.getLabel(new BufferedReader(br),editor.statechart);
            }
            catch (IOException e){}
            trans.label = tlabel;
            trans.label.position = new CPoint(Betrag(r.x,x),Betrag(r.y,y));
            editor.repaint();
        }
    }

/*
    Funktion: aendert Transition-> source,target, wenn Zustandsname veraendert wird
*/
    private static void changeTransName(String name,int x, int y,Editor editor)
    {
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);//noch And_State beachten!!!
        Rectangle r = Methoden_0.abs(editor,s);
        TrList list = ((Or_State)s).trs;
        while (list != null)
        {
            changeTrAnchorOf(list.head,new Point(r.x,r.y),editor);
            list = list.tail;
        }
    } //END changeTransName()

    private static State getFirstAndOf(int x, int y, Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s.rect.x == 0 & s.rect.y == 0)
        {
            StateList list = editor.stateList;
            StateList dlist = null;
            while (list != null)
            {
                if (list.head instanceof And_State)
                {
                    dlist = ((And_State)list.head).substates;
                    while (dlist != null)
                    {
                        if (dlist.head.equals(s)) return list.head;
                        dlist = dlist.tail;
                    }
                }
                list = list.tail;
            }
        }
        if (s instanceof And_State) return s;
        return null;
    }

/*
    Methode: getNearestTransOf
    Funktion: liefert die Transition am naehersten (x,y)
    Parameter: tlist: Transliste, die durchsucht wird
               p: absolute Koordinate des Or-Zustandes, der tlist enthaelt
               (x,y) : MouseClicked-Position
    -> tlist darf nicht leer sein!
*/

    private static Tr getNearestTransOf(TrList tlist,Point p, int x, int y, Editor editor)
    {
        TrList list = tlist;
        Tr nearest = list.head;
        while (list != null)
        {
             if ((Betrag(x,nearest.points[0].x+p.x)+Betrag(y,nearest.points[0].y+p.y)) >
                 (Betrag(x,list.head.points[0].x+p.x)+Betrag(y,list.head.points[0].y+p.y)))
             {
                  nearest = list.head;
             }
             list = list.tail;
        }
        return nearest;
    }

    private static int Betrag(int x, int y)
    {
        if (x < y) return (y-x);
        else return (x-y);
    }

/*
    Zoom-Faktor
*/
    public static double getFactor()
    {
        return factor;
    }
    public static void setFactor(double f)
    {
        factor = f/100;
    }

    public static void markObjects(Rectangle rect, Editor editor)
    {
        selectList = getObjects(rect,editor);
    }

    public static ObjectList getObjects(Rectangle rect, Editor editor)
    {
        ObjectList objList = null;
        Or_State os = (Or_State)(editor.statechart.state);
        Rectangle r = Methoden_0.abs(editor,os);
        ConnectorList clist = os.connectors;
        TrList tlist = os.trs;
        while (clist != null) //fuer root-State
        {
            if (rect.contains(clist.head.position.x+r.x,clist.head.position.y+r.y))
            {
                objList = new ObjectList(clist.head, objList);
                markSelectedCon(clist.head,new Point(r.x,r.y),editor);
            }
            clist = clist.tail;
        }
        while (tlist != null)  //fuer root-State
        {
            if (rect.contains(tlist.head.points[0].x+r.x,tlist.head.points[0].y+r.y))
            {
                objList = new ObjectList(tlist.head,objList);
                markSelectedTr(tlist.head,new Point(r.x,r.y),editor);
            }
            tlist = tlist.tail;
        }
        StateList list = editor.stateList;
        while (list != null)
        {
            r = Methoden_0.abs(editor,list.head);
            if (list.head instanceof Or_State)  //noch fuer And_State
                clist = ((Or_State)(list.head)).connectors;
            while (clist != null)
            {
                if (rect.contains(clist.head.position.x+r.x,clist.head.position.y+r.y))
                {
                    objList = new ObjectList(clist.head, objList);
                    markSelectedCon(clist.head,new Point(r.x,r.y),editor);
                }
                clist = clist.tail;
            }
            if(list.head instanceof Or_State)  //noch fuer And_State
                tlist = ((Or_State)(list.head)).trs;
            while (tlist != null)
            {
                if (rect.contains(tlist.head.points[0].x+r.x,tlist.head.points[0].y+r.y))
                {
                    objList = new ObjectList(tlist.head,objList);
                    markSelectedTr(tlist.head,new Point(r.x,r.y),editor);
                }
                tlist = tlist.tail;
            }
            list = list.tail;
        }
        return objList;
    }

    public static void copyObjects(ObjectList objlist, Editor editor)
    {
        while (objlist != null)
        {
            if (objlist.head instanceof Connector){
                try
                {
                    Connector con = (Connector)(objlist.head.clone());
                    copyList = new ObjectList(con,copyList);
                }
                catch(CloneNotSupportedException e)
                {
                    editor.gui.OkDialog(editor,"Fehler","Kopieren nicht moeglich!");
                }
            }
            if (objlist.head instanceof Tr){
                Tr neu = (Tr)objlist.head;
                Tr tr = new Tr(neu.source,neu.target,neu.label,neu.points);
                copyList = new ObjectList(tr,copyList);
            }
            objlist = objlist.tail;
        }
    }

    public static void removeObjects(Editor editor)
    {
        ObjectList list = selectList;
        while (list != null)
        {
            if (list.head instanceof Connector)
            {
                markLast = (Connector)list.head;
                removeOneCon(editor);
            }
            if (list.head instanceof Tr)
            {
                markLast = (Tr)list.head;
                removeOneTr(editor);
            }
            list = list.tail;
        }
        selectList = null;
    }

    public static void selectOne(int x, int y, Editor editor)
    {
        if (Methoden_0.getConEnvOf(x,y,editor) != null)
            selectOneConnector(x,y,editor);
        else
            selectOneTr(x,y,editor);
    }

    public static void insertOne(int x, int y, Editor editor)
    {
        if (markLast instanceof Connector)
            insertOneConnector(x,y,editor);
        if (markLast instanceof Tr)
            insertOneTr(x,y,editor);
    }

    private static void selectOneTr(int x, int y, Editor editor) //p2 ist nicht erforderlich fuer die berechnung
    {
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);//noch And_State beachten!!!
        if (s.equals(editor.statechart.state)) selectOneTr = null;
        if (s instanceof Or_State)
        {
            Or_State os = (Or_State)s;
            if (os.trs==null) return;
            Rectangle r = Methoden_0.abs(editor,os);
            selectOneTr = getNearestTransOf(os.trs,new Point(r.x,r.y),x,y,editor);
            if (selectOneTr != null)
            {
                markSelectedTr(selectOneTr,new Point(r.x,r.y),editor);
                markLast = selectOneTr;
                selectList = new ObjectList(selectOneTr,selectList);
            }
        }
        if (s instanceof And_State)
        {
            //noch zu machen!!
        }
    }

    public static void copyOneTr(Editor editor)
    {
        try
        {
            if (selectOneTr != null)
                copyOneTr = (Tr)(selectOneTr.clone());
            CPoint p[] = new CPoint[copyOneTr.points.length];
            for (int i=0;i<copyOneTr.points.length;i++)
                p[i] = copyOneTr.points[i];
            copyOneTr.points = p;
        }
        catch(CloneNotSupportedException e)
        {
            editor.gui.OkDialog(editor,"Fehler","Kopieren nicht moeglich!");
        }
    }
    private static void insertOneTr(int x, int y, Editor editor)
    {
        if (copyOneTr == null) return;
        try
        {
            copyOneTr = (Tr)(copyOneTr.clone());
        }
        catch(CloneNotSupportedException e)
        {
            editor.gui.OkDialog(editor,"Fehler","Kopieren nicht moeglich!");
        }
        if (copyOneTr != null)
            addTransition(x,y,editor);
        Methoden_0.updateAll(editor);
    }

    private static void addTransition(int x, int y, Editor editor)
    {
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);
        Rectangle r = Methoden_0.abs(editor,s);

        State s1 = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s1 != editor.statechart.state)
        {
            Rectangle r1 = Methoden_0.abs(editor,s1);
            if (s1 instanceof Or_State)
            {
                if ((Betrag(x,r1.x)<20) || (Betrag(x,r1.x+r1.width) < 20) ||
                    (Betrag(y,r1.y)<20) || (Betrag(y,r1.y+r1.height) < 20))
                    switch (Methoden_0.wherePoint(r1,x,y)) //in welchem "Bereich" liegt der Punkt??
                    {
                        case 1:{y = r1.y+1;break;}
                        case 2:{x = r1.x+r1.width-1;break;}
                        case 3:{y = r1.y+r1.height-1;break;}
                        case 4:{x = r1.x+1;}
                    }
            }
            if (s1 instanceof Basic_State)
                switch (Methoden_0.wherePoint(r1,x,y)) //in welchem "Bereich" liegt der Punkt??
                {
                    case 1:{y = r1.y+1;break;}
                    case 2:{x = r1.x+r1.width-1;break;}
                    case 3:{y = r1.y+r1.height-1;break;}
                    case 4:{x = r1.x+1;}
                }
        }

        Tr tr = getNewPosTrans(copyOneTr,new Point(r.x,r.y),x,y);
        Or_State os = (Or_State)s;
        r = Methoden_0.abs(editor,os); //neue Berechnung fuer changeTrAnchor
        changeTrAnchorOf(tr,new Point(r.x,r.y),editor); //veraendert die TrAnchor-Felder
        TrList list = new TrList(tr,os.trs);
        os.trs = list;
    }
    private static Tr getNewPosTrans(Tr tr, Point p, int x, int y)
    {
        Tr copy = null;
        if (tr == null) return tr;
        CPoint[] q = new CPoint[tr.points.length];
        if (selectOneTr != null)
/*        {
            copy = new Tr(copyOneTr.source,copyOneTr.target,copyOneTr.label,q);
        }
*/
        try
        {
            copy = (Tr)(tr.clone());
        }
        catch(CloneNotSupportedException e){}

        int dx,dy;
        dx = x-(p.x+selectOneTr.points[0].x);
        dy = y-(p.y+selectOneTr.points[0].y);
        copy.points[0] = new CPoint(Betrag(p.x,x),Betrag(p.y,y));
        for (int i=1;i<copy.points.length;i++)
        {
            copy.points[i] = new CPoint(selectOneTr.points[i].x+dx,selectOneTr.points[i].y+dy);
        }
        //punkt fuer caption
        if (copy.label.position != null){
            copy.label.position = new CPoint(selectOneTr.label.position.x+dx,selectOneTr.label.position.y+dy);
        }
        return copy;
    }

/*
    Methode: markSelectedTr
    Funktion: markiert die ausgewaehlte Transition
    Parameter: tr:Transition
               p : absoluter Punkt fuer tr
*/
    private static void markSelectedTr(Tr tr, Point p, Editor editor)
    {
        if (tr == null | (tr.points.length <2)) return;
        Graphics g = editor.getGraphics();
        if (editor.gui.getTransitioncolor() != Color.blue)
            g.setColor(Color.blue);
        else g.setColor(Color.red);
        int i=0;
        try
        {
            for (i=0;i<=tr.points.length-1;i++)
            {
                if (i==0) g.fillOval((int)((double)(tr.points[0].x+p.x)*Methoden_1.getFactor())-2-editor.scrollX,
                           (int)((double)(tr.points[0].y+p.y)*Methoden_1.getFactor())-editor.scrollY,4,4);
                g.drawLine((int)((double)(tr.points[i].x+p.x)*Methoden_1.getFactor())-editor.scrollX,
                           (int)((double)(tr.points[i].y+p.y)*Methoden_1.getFactor())-editor.scrollY,
                           (int)((double)(tr.points[i+1].x+p.x)*Methoden_1.getFactor())-editor.scrollX,
                           (int)((double)(tr.points[i+1].y+p.y)*Methoden_1.getFactor())-editor.scrollY);
            }
         }
         catch(NullPointerException e)
         {
             if (i>=1){
             Pfeil pfeil = new Pfeil(g,(int)((double)(tr.points[i-1].x+p.x)*Methoden_1.getFactor())-editor.scrollX,
                                       (int)((double)(tr.points[i-1].y+p.y)*Methoden_1.getFactor())-editor.scrollY,
                                       (int)((double)(tr.points[i].x+p.x)*Methoden_1.getFactor())-editor.scrollX,
                                       (int)((double)(tr.points[i].y+p.y)*Methoden_1.getFactor())-editor.scrollY);}
         }
         catch(ArrayIndexOutOfBoundsException e)
         {
             if (i>=1){
             Pfeil pfeil = new Pfeil(g,(int)((double)(tr.points[i-1].x+p.x)*Methoden_1.getFactor())-editor.scrollX,
                                       (int)((double)(tr.points[i-1].y+p.y)*Methoden_1.getFactor())-editor.scrollY,
                                       (int)((double)(tr.points[i].x+p.x)*Methoden_1.getFactor())-editor.scrollX,
                                       (int)((double)(tr.points[i].y+p.y)*Methoden_1.getFactor())-editor.scrollY);}
         }
         g.dispose();
    }

    private static void removeOneTr(Editor editor)
    {
        StateList list = editor.stateList;
        TrList tlist = null,copy=null;
        tlist = ((Or_State)(editor.statechart.state)).trs;
        while (tlist != null)
        {
            if (tlist.head.equals(markLast))
            {
                    tlist.head = null;
                    TrList neu = tlist.tail;
                    while (neu != null)
                    {
                        copy = new TrList(neu.head,copy);
                        neu = neu.tail;
                    }
                    ((Or_State)(editor.statechart.state)).trs = copy;
                    selectOneTr = null;
                    markLast = null;
                    editor.repaint();
                    return;
            }
                copy = new TrList(tlist.head,copy);
                tlist = tlist.tail;
        }
        while (list != null)
        {
            if (list.head instanceof Or_State){
                tlist = ((Or_State)(list.head)).trs;
                while(tlist != null)
                {
                    if (tlist.head.equals(markLast))
                    {
                        tlist.head = null;
                        TrList neu = tlist.tail;
                        while (neu != null)
                        {
                            copy = new TrList(neu.head,copy);
                            neu = neu.tail;
                        }
                        ((Or_State)(list.head)).trs = copy;
                        selectOneTr = null;
                        markLast = null;
                        editor.repaint();
                        return;
                    }
                    copy = new TrList(tlist.head,copy);
                    tlist = tlist.tail;
                }
            }
            list = list.tail;
        }
    }

/*
    Methode: markSelectedCon
    Funktion: markiert den ausgewaehlte Connector
    Parameter: con: Connector
               p : absoluter Punkt fuer con
*/

    private static void markSelectedCon(Connector con, Point p, Editor editor)
    {
        Graphics g = editor.getGraphics();
        if (editor.gui.getConnectorcolor() != Color.blue)
            g.setColor(Color.blue);
        else g.setColor(Color.red);
        int big = 10;
        if (Methoden_1.getFactor()*100 < 25) big = 5;
        else if (Methoden_1.getFactor()*100 > 400) big = 15;
        else big = 10;
        if(con != null)
        {
            g.fillOval((int)((double)(con.position.x+p.x)*Methoden_1.getFactor())-editor.scrollX,
                       (int)((double)(con.position.y+p.y)*Methoden_1.getFactor())-editor.scrollY,big,big);
        }
        g.dispose();
    }

    private static void selectOneConnector(int x, int y, Editor editor)
    {
        selectOneConnector =  Methoden_0.getConEnvOf(x,y,editor);
        if (selectOneConnector == null) return;
        markLast = selectOneConnector;
        selectList = new ObjectList(selectOneConnector,selectList);
    }

    public static void copyOneConnector(Editor editor)
    {
        try
        {
            if (selectOneConnector != null)
                copyOneConnector = (Connector)(selectOneConnector.clone());
        }
        catch(CloneNotSupportedException e)
        {
            editor.gui.OkDialog(editor,"Fehler","Kopieren nicht moeglich!");
        }
    }

    public static void insertOneConnector(int x, int y,Editor editor)
    {
        try
        {
            if (copyOneConnector != null)
                copyOneConnector = (Connector)(copyOneConnector.clone());
        }
        catch(CloneNotSupportedException e){}
        if (copyOneConnector != null)
            addConnector(copyOneConnector,x,y,editor);
        Methoden_0.updateAll(editor);
    }

    private static void addConnector(Connector con, int x, int y, Editor editor)
    {
        if (Methoden_0.getConEnvOf(x,y,editor) != null) return;
        if (EditorUtils.getInnermostStateOf(x,y,editor) instanceof Basic_State) return;
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);
        Rectangle r = Methoden_0.abs(editor,s);
        Or_State os = (Or_State)s;
        con.position = new CPoint(x-r.x,y-r.y);
        ConnectorList clist = new ConnectorList(con,os.connectors);
        os.connectors = clist;
        Methoden_0.updateAll(editor);
    }
/*
    Methode: removeOne()
    Funktion: entfernt das markierte Objekt
*/
    public static void removeOne(Editor editor)
    {
        if (markLast instanceof Connector)
            removeOneCon(editor);
        if (markLast instanceof Tr)
            removeOneTr(editor);
    }
/*
    Methode: removeOneCon()
    Funktion; entfernt das markierte Objekt:Connector
*/
    private static void removeOneCon(Editor editor)
    {
        StateList list = editor.stateList;
        ConnectorList clist = null,copy=null;
        clist = ((Or_State)(editor.statechart.state)).connectors;
        while(clist != null)
        {
            if (clist.head.equals(markLast))
            {
                ConnectorList neu = clist.tail;
                while (neu != null)
                {
                    copy = new ConnectorList(neu.head,copy);
                    neu = neu.tail;
                }
                ((Or_State)(editor.statechart.state)).connectors = copy;
                selectOneConnector = null;
                markLast = null;
                editor.repaint();
                return;
            }
            copy = new ConnectorList(clist.head,copy);
            clist = clist.tail;
        }
        while (list != null)
        {
            if (list.head instanceof Or_State){
                clist = ((Or_State)(list.head)).connectors;
                while(clist != null)
                {
                    if (clist.head.equals(markLast))
                    {
                        clist.head = null;
                        ConnectorList neu = clist.tail;
                        while (neu != null)
                        {
                            copy = new ConnectorList(neu.head,copy);
                            neu = neu.tail;
                        }
                        ((Or_State)(list.head)).connectors = copy;
                        selectOneConnector = null;
                        markLast = null;
                        editor.repaint();
                        return;
                    }
                    copy = new ConnectorList(clist.head,copy);
                    clist = clist.tail;
                }
            }
            list = list.tail;
        }
    }

    public static void moveOne(int x, int y, Editor editor)
    {
        if (markLast instanceof Connector){
            if (markLast == null) return;
            addConnector((Connector)markLast,x,y,editor);
            removeOneCon(editor);
            editor.repaint();
        }
        if (markLast instanceof Tr)
        {
            if (markLast == null) return;
            copyOneTr = (Tr)markLast;
            addTransition(x,y,editor);
            removeOneTr(editor);
            editor.repaint();
        }
    }

/*
    Methode: changeTrAnchorOf(..)
    Funktion: beim Verschieben oder Einfuegen muessen die TrAnchor-Felder
              dieser Transition auch veraendert werden
    Parameter: tr: die zuveraendende Transition
               p: absolute Koordinate desjenigen Or_States
*/
    private static void changeTrAnchorOf(Tr tr, Point p,Editor editor)
    {
        Absyn source = null, target = null;
        TrAnchor s=null,t=null;
        source = Methoden_0.getConEnvOf(p.x+tr.points[0].x,p.y+tr.points[0].y,editor);
        if (source == null)
            source = EditorUtils.getInnermostStateOf(p.x+tr.points[0].x,p.y+tr.points[0].y,editor);

        target = Methoden_0.getConEnvOf(p.x+tr.points[tr.points.length-1].x,p.y+tr.points[tr.points.length-1].y,editor);
        if (target == null)
            target = EditorUtils.getInnermostStateOf(p.x+tr.points[tr.points.length-1].x,p.y+tr.points[tr.points.length-1].y,editor);

        if (source instanceof Connector) s = (TrAnchor)((Conname)((Connector)source).name);
        else s = (TrAnchor)(((State)source).name);
        if (target instanceof Connector) t = (TrAnchor)((Conname)((Connector)target).name);
        else t = (TrAnchor)(((State)target).name);

        tr.source = s;
        tr.target = t;
    }

    public static void moveTransName(int x, int y, Editor editor)
    {
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);
        Rectangle r = Methoden_0.abs(editor,s);
        selectOneTr.label.position = new CPoint(Betrag(r.x,x),Betrag(r.y,y));
        editor.repaint();
    }

    public static void showFullTransName(int x, int y, Editor editor)
    {
        StateList list = editor.stateList;
        TrList tlist = null,copy=null;
        tlist = ((Or_State)(editor.statechart.state)).trs;
        Rectangle r = editor.statechart.state.rect;
        Rectangle rect = new Rectangle();
        Graphics g = editor.getGraphics();
        g.setColor(editor.gui.getTransitioncolor());
        while(tlist != null)
        {
            if (tlist.head.label.position != null){
                rect = new Rectangle(tlist.head.label.position.x+r.x,tlist.head.label.position.y+r.y-10,5*tlist.head.label.caption.length(),10);
                if (rect.contains(x,y))
                {
                    g.drawString(tlist.head.label.caption,(int)((double)(r.x+tlist.head.label.position.x)*Methoden_1.getFactor())-editor.scrollX,
                                (int)((double)r.y+tlist.head.label.position.y*Methoden_1.getFactor())-editor.scrollY);
                    return;
                }
            }
            tlist = tlist.tail;
        }
        while (list != null)
        {
            if (list.head instanceof Or_State){
                tlist = ((Or_State)(list.head)).trs;
                r = Methoden_0.abs(editor,list.head);
                while(tlist != null)
                {
                    if (tlist.head.label.position != null){
                        rect = new Rectangle(tlist.head.label.position.x+r.x,tlist.head.label.position.y+r.y-10,5*tlist.head.label.caption.length(),10);
                        if (rect.contains(x,y))
                        {
                            g.drawString(tlist.head.label.caption,(int)((double)(r.x+tlist.head.label.position.x)*Methoden_1.getFactor())-editor.scrollX,
                                        (int)((double)r.y+tlist.head.label.position.y*Methoden_1.getFactor())-editor.scrollY);
                            return;
                        }
                    }
                    tlist = tlist.tail;
                }
            }
            list = list.tail;
        }
    }



/*
    Methode: chechSEventList()
    Funktion: prueft, ob durch das Loeschen die events-List noch richtig ist
*/
/*
    private static void checkSEventList(Editor editor)
    {
        SEventList sEventList = editor.events;
        StateList list = null;
        TrList tlist = null;
        boolean isIn = false;
        while(sEventList != null)
        {
            while(list != null)
            {
                if (list.head instanceof Or_State)
                {
                    tlist = ((Or_State)list.head).trs;
                    while (tlist != null)
                    {
                        if (tlist.label.caption.equals(sEventList.head.name))
                            isIn = true;
                        tlist = tlist.tail;
                    }
                }
                list = list.tail;
            }
            if (!isIn)
*/
}
