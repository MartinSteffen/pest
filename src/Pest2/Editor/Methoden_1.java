
package editor;

import absyn.*;
import gui.*;
import tesc1.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Methoden_1
{
    private static Point points[] = new Point[3];
    private static double factor = 1;
    private static Tr trans = null;
    public static State selectOneState = null;
    public static Tr selectOneTr = null;
    public static Connector selectOneConnector = null;
    public static State copyOneState = null;
    public static Tr copyOneTrans = null;
    public static Connector copyOneConnector = null;


/*
    Methode: addStatenameMouseClicked(int x, int y, Editor editor)
    Funktion: erzeugt ein Dialogfenster fuer die Eingabe von Statename zu dem
              Zustand, der x,y im innersten hat.
*/

    public static void addStatenameMouseClicked(int x, int y, Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s==editor.statechart.state)
        {
            editor.gui.OkDialog(editor,"Fehler","Zustand kann nicht benannt werden!");
            return;
        }
        String name = editor.gui.EingabeDialog(editor,"Zustand benennen", "Name fuer Zustand:", s.name.name);
        if (name != null)
        {
            s.name = new Statename(name);
            changeTransName(name,x,y,editor);
            Methoden_0.updateAll(editor);
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
            Methoden_0.updateAll(editor);
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
            Methoden_0.updateAll(editor);
        }
    }



/*
    Methode: addTransNameMouseClicked(int x, int y, Editor editor)
    Funktion: erzeugt ein Dialogfenster fuer die Eingabe von Transitionsnamen,

*/
    public static void addTransNameMouseClicked(int x, int y, Editor editor)
    {
        int i = getNullIndex(points);
        if (i == 0) {points[0] = new CPoint(x,y); return;}
        else
        if (i == 1)
        {
            State s1 = Methoden_0.getFirstOrStateOf(points[0].x,points[0].y,editor);
            Or_State os = (Or_State)s1;
            if (os.trs==null)
            {
                editor.gui.OkDialog(editor,"Fehler","Keine Transition vorhanden!");
                setNullArray(points);
            }
            else
            {
                Rectangle r = Methoden_0.abs(editor,s1);
                points[1] = new Point(r.x,r.y);
                trans = getNearestTransOf(os.trs,points[1],points[0].x,points[0].y,editor);
                String name = editor.gui.EingabeDialog(editor,"Transition benennen", "Name fuer Transition:",trans.label.caption);
                if (name == null)
                {
                    setNullArray(points);
                    return;
                }
                TESCLoader tesc = new TESCLoader(editor.gui);
		TLabel tlabel = null;
                BufferedReader br = new BufferedReader(new StringReader(name));
                try
                {
                    tlabel = tesc.getLabel(new BufferedReader(br),editor.statechart);
                }
                catch (IOException e){}
                trans.label = tlabel;
            }
        }
        else
        if (i == 2)
        {
            trans.label = new TLabel(trans.label.guard,trans.label.action,
                                     new CPoint(x+points[1].x,y+points[1].y),trans.label.location,
                                     trans.label.caption);
            setNullArray(points);
            Methoden_0.updateAll(editor);
        }
    }

    private static int getNullIndex(Point[] p)
    {
        int i=0;
        try {while (p[i] !=null) i++;}
        catch(ArrayIndexOutOfBoundsException a) {return -1;}
        catch(NullPointerException a) {}
        return i;
    }

    private static void setNullArray(Point[] p)
    {
        for(int i=0;i<3;i++) p[i] = null;
    }

/*
    Funktion: aendert Transition-> source,target, wenn Zustandsname veraendert wird
*/
    private static void changeTransName(String name,int x, int y,Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        Rectangle r = Methoden_0.abs(editor,s);
        if (s instanceof Basic_State)
        {
            s = EditorUtils.getInnermostStateOf(r.x-1,r.y-1,editor);
            r = Methoden_0.abs(editor,s);
        }
        if (s instanceof Or_State & s != editor.statechart.state)
        {
            Or_State os = (Or_State)s;
            TrList list = os.trs;
            while (list != null)
            {
                if (EditorUtils.getInnermostStateOf(x,y,editor) == EditorUtils.getInnermostStateOf(r.x+list.head.points[0].x,r.y+list.head.points[0].y,editor))
                {
                    list.head.source = new Statename(name);
                }
                if (EditorUtils.getInnermostStateOf(x,y,editor) == EditorUtils.getInnermostStateOf(r.x+list.head.points[1].x,r.y+list.head.points[1].y,editor))
                {
                    list.head.target = new Statename(name);
                }
                list = list.tail;
            }
        }
    } //END changeTransName()

    private static State getFirstAndOf(int x, int y, Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(x,y,editor);
        if (s.rect.x == 0 & s.rect.y == 0)
        {
            StateList list = ((Or_State)(editor.statechart.state)).substates;
            StateList dlist = null;
            while (list != null)
            {
                if (list.head instanceof And_State)
                {
                    dlist = ((And_State)list.head).substates;
                    while (dlist != null)
                    {
                        if (dlist.head == s) return list.head;
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

    public static void selectMouseDragged(Editor editor)
    {
//        Rectangle r = editor.newRect;
//        if (!contained(r,editor)) return;
    }

    public static void selectOneStateMouseClicked(int x, int y,Editor editor)
    {
        selectOneState = EditorUtils.getInnermostStateOf(x,y,editor);
    }
    public static void copyOneState(Editor editor)
    {
        try
        {
            if (selectOneState == null) return;
            if (selectOneState instanceof Basic_State){
                copyOneState = (Basic_State)(selectOneState.clone());
            }
            else
            if (selectOneState instanceof Or_State){
                copyOneState = (Or_State)(selectOneState.clone());
            }
            else copyOneState = (And_State)(selectOneState.clone());
        }
        catch(CloneNotSupportedException e)
        {
            editor.gui.OkDialog(editor,"Fehler","Kopieren nicht moeglich!");
        }
    }
    public static void insertOneState(State s, int x, int y, Editor editor)
    {
//        Rectangle r = Methoden_0.abs(editor.s);
    }



    public static void selectOneTr(int x, int y, Editor editor) //p2 ist nicht erforderlich fuer die berechnung
    {
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);//noch And_State beachten!!!
        if (s.equals(editor.statechart.state)) selectOneTr = null;
        if (s instanceof Or_State)
        {
            Or_State os = (Or_State)s;
            if (os.trs==null) return;
            Rectangle r = Methoden_0.abs(editor,os);
            selectOneTr = getNearestTransOf(os.trs,new Point(r.x,r.y),x,y,editor);
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
                copyOneTrans = (Tr)(selectOneTr.clone());
        }
        catch(CloneNotSupportedException e)
        {
            editor.gui.OkDialog(editor,"Fehler","Kopieren nicht moeglich!");
        }
    }
    public static void insertOneTr(int x, int y, Editor editor)
    {
        selectOneTr = null;
        if (copyOneTrans == null) return;
        try
        {
            copyOneTrans = (Tr)(copyOneTrans.clone());
        }
        catch(CloneNotSupportedException e)
        {
            editor.gui.OkDialog(editor,"Fehler","Kopieren nicht moeglich!");
        }
        if (copyOneTrans != null)
            addTransition(x,y,editor);
    }

    private static void addTransition(int x, int y, Editor editor)
    {
        State s = Methoden_0.getFirstOrStateOf(x,y,editor);
        Rectangle r = Methoden_0.abs(editor,s);
        Tr tr = getNewPosTrans(copyOneTrans,new Point(r.x,r.y),x,y);
        Or_State os = (Or_State)s;
        TrList list = new TrList(tr,os.trs);
        os.trs = list;
    }
    private static Tr getNewPosTrans(Tr tr, Point p, int x, int y)
    {
        Tr copy = null;
        if (tr == null) return tr;
        try
        {
            copy = (Tr)(tr.clone());
        }
        catch(CloneNotSupportedException e){}
        int dx,dy;
        dx = x-(p.x+copy.points[0].x);
        dy = y-(p.y+copy.points[0].y);
        copy.points[0].x = Betrag(p.x,x);
        copy.points[0].y = Betrag(p.y,y);
        for (int i=1;i<copy.points.length;i++)
        {
            copy.points[i].x += dx;
            copy.points[i].y += dy;
        }
        System.out.println(dx);
        return copy;
    }

    public static void selectOneConnector(int x, int y, Editor editor)
    {
        selectOneConnector =  Methoden_0.getConEnvOf(x,y,editor);
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
    }

    public static void addConnector(Connector con, int x, int y, Editor editor)
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


    private static boolean contained(Rectangle rect, Editor editor)
    {
        State s = EditorUtils.getInnermostStateOf(rect.x,rect.y,editor);
        Rectangle r = Methoden_0.abs(editor,s);

        if (s instanceof Basic_State) return false;

        if (s instanceof Or_State)
        {
            StateList list = ((Or_State)(s)).substates;
            while (list != null) //noch zu machen!!!
            {
            }
        }
        return false;
    }
}
