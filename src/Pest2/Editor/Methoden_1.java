
package editor;

import absyn.*;
import gui.*;
import java.awt.*;
import java.awt.event.*;


public class Methoden_1
{
    private static Point points[] = new Point[2];


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
        String name = editor.gui.EingabeDialog("Zustand benennen", "Name fuer Zustand:", s.name.name);
        if (name != null) s.name = new Statename(name);
        editor.paint(editor.getGraphics());
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
        {
            points[1] = new CPoint(x,y);
            State s1 = EditorUtils.getInnermostStateOf(points[0].x,points[0].y,editor);
            if (!(s1 instanceof Or_State)) s1 = EditorUtils.getInnermostStateOf(s1.rect.x-1,s1.rect.y-1,editor);
            State s2 = EditorUtils.getInnermostStateOf(points[1].x,points[1].y,editor);
            if (!(s2 instanceof Or_State)) s2 = EditorUtils.getInnermostStateOf(s2.rect.x-1,s2.rect.y-1,editor);
            Or_State os1 = (Or_State)s1;
            Or_State os2 = (Or_State)s2;
            if (os1.trs==null || os2.trs==null)
                editor.gui.OkDialog(editor,"Fehler","Keine Transition vorhanden!");
            else
            {
                String name = editor.gui.EingabeDialog("Transition benennen", "Name fuer Transition:","TEST");
            }

            setNullArray(points);
            editor.paint(editor.getGraphics());
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
        for(int i=0;i<2;i++) p[i] = null;
    }


}