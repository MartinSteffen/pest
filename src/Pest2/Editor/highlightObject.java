/**
 * highlightObject.java
 *
 *
 *
 *
 * @author
 * @version
 */

package editor;

import java.awt.*;
import java.awt.event.*;
import absyn.*;

public class highlightObject {

    static Editor editor = Editor.getRef();
    static HltList list = null;

    public highlightObject(State obj)
    {
         deleteObject(obj);
    }

    public highlightObject(State obj,Color col)
    {
        if (obj instanceof Basic_State)
            new highlightObject((Basic_State)obj,col);
        if (obj instanceof Or_State)
            new highlightObject((Or_State)obj,col);
        if (obj instanceof And_State)
            new highlightObject((And_State)obj,col);
    }
    public highlightObject(Basic_State obj)
    {
        deleteObject(obj);
    }
    public highlightObject(Basic_State obj, Color col)
    {
        if (obj != null)
            list = new HltList(new Hlt(obj,col),list);
        showHlt();
    }

    public highlightObject(And_State obj)
    {
        deleteObject(obj);
    }
    public highlightObject(And_State obj, Color col)
    {
        if (obj != null)
            list = new HltList(new Hlt(obj,col),list);
        showHlt();
    }

    public highlightObject(Or_State obj)
    {
        deleteObject(obj);
    }
    public highlightObject(Or_State obj, Color col)
    {
        if (obj != null)
            list = new HltList(new Hlt(obj,col),list);
        showHlt();
    }

    public highlightObject(Tr obj)
    {
        deleteObject(obj);
    }
    public highlightObject(Tr obj, Color col)
    {
        if (obj != null)
            list = new HltList(new Hlt(obj,col),list);
        showHlt();
    }

    public highlightObject(Connector obj)
    {
        deleteObject(obj);
    }
    public highlightObject(Connector obj, Color col)
    {
        if (obj != null)
            list = new HltList(new Hlt(obj,col),list);
        showHlt();
    }
    public highlightObject(boolean clear)
    {
        if(clear) list = null;
    }

    public highlightObject()
    {
        showHlt();
    }

    private void deleteObject(Absyn obj)
    {
        HltList hlist = list;
        while (hlist != null)
        {
            if (hlist.head.obj.equals(obj))
            {
                HltList copy = null;
                hlist = list;
                while (hlist != null)
                {
                    if (!hlist.head.obj.equals(obj))
                    {
                        copy = new HltList(hlist.head,copy);
                    }
                    hlist = hlist.tail;
                }
                list = copy;
                return;
            }
            hlist = hlist.tail;
        }
        showHlt();
    }
    private void showHlt()
    {
        HltList hlist = list;
        while (hlist != null)
        {
            Methoden_0.showHltObject(hlist.head.obj,hlist.head.color,editor);
            hlist = hlist.tail;
        }
    }
}

class Hlt
{
    Absyn obj;
    Color color;
    public Hlt(Absyn o, Color c)
    {
        obj = o;
        color = c;
    }
}
class HltList
{
    Hlt head;
    HltList tail;
    public HltList(Hlt h, HltList t)
    {
        head = h;
        tail = t;
    }
}
