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
import java.io.*;
import absyn.*;

public class highlightObject {

    Color defaultcolor = Color.red;
    static Graphics h;
    public highlightObject(Basic_State obj, Color col)
    {
        if (obj != null)
            highlight(obj,col);
    }
    public highlightObject(And_State obj, Color col)
    {
        if (obj != null)
            highlight(obj,col);
    }
    public highlightObject(Or_State obj, Color col)
    {
        if (obj != null)
            highlight(obj,col);
    }

    public highlightObject(Tr obj, Color col)
    {
        if (obj != null)
        highlight(obj,col);
    }

    public highlightObject(Connector obj, Color col)
    {
        if (obj != null)
            highlight(obj,col);
    }

    private void highlight(State obj, Color col)
    {
        writeData(obj,col);
    }

    private void highlight(Tr obj, Color col)
    {
        writeData(obj,col);
    }

    private void highlight(Connector obj, Color col)
    {
        writeData(obj,col);
    }

    private void writeData(State obj, Color col)
    {
        File fi = new File("highlight.dat");
        try
        {
            RandomAccessFile file = new RandomAccessFile (fi,"rw");
            file.seek(file.length());
            file.writeUTF(translateToString(obj,col));
            file.close();

        }
        catch(IOException e){}
    }

    private void writeData(Tr obj, Color col)
    {
        File fi = new File("highlight.dat");
        String objType = "";
        objType = translateToString(obj,col);
        try
        {
            RandomAccessFile file = new RandomAccessFile (fi,"rw");
            file.seek(file.length());
            file.writeUTF(objType);
            file.close();
        }
        catch(IOException e){}
    }

    private void writeData(Connector obj, Color col)
    {
        File fi = new File("highlight.dat");
        String objType = "";

        if (obj instanceof Connector)
        {
            Connector con  = (Connector)obj;
            objType = translateToString(con,col);
        }
        try
        {
            RandomAccessFile file = new RandomAccessFile (fi,"rw");
            file.seek(file.length());
            file.writeUTF(objType);
            file.close();

        }
        catch(IOException e){}
    }

    private String getColorString(Color col)
    {
        if (col == Color.black) return "black";
        if (col == Color.blue) return "blue";
        if (col == Color.cyan) return "cyan";
        if (col == Color.darkGray) return "darkGray";
        if (col == Color.gray) return "gray";
        if (col == Color.green) return "green";
        if (col == Color.lightGray) return "lightGray";
        if (col == Color.magenta) return "magenta";
        if (col == Color.orange) return "orange";
        if (col == Color.pink) return "pink";
        if (col == Color.red) return "red";
        if (col == Color.white) return "white";
        if (col == Color.yellow) return "yellow";
        return "red";
    }

    private String translateToString(State obj, Color col)
    {
        String color = getColorString(col);
        String stateType = "LMBasic_State";
        if (obj instanceof Or_State)
            stateType = "LMOr_State";
        if (obj instanceof And_State)
            stateType = "LMAnd_State";
            State state = (State) obj;
        if (obj instanceof Or_State | obj instanceof And_State | obj instanceof Basic_State)
            return(new String(stateType+"&|&"+state.name.name+"&|&"+
                   state.rect.x+"&|&"+state.rect.y+"&|&"+state.rect.width+
                   "&|&"+state.rect.height+"&|&"+color+"&|&"+"\n"));
        return "";
    }

    private String translateToString(Tr obj, Color col)
    {
        String color = getColorString(col);
        String caption = obj.label.caption;
        if (caption == "") caption = ".";
        if (obj instanceof Tr){
            Tr tr = (Tr)obj;
            return (new String("LMTransition"+"&|&"+caption+"&|&"+
                    tr.points[0].x+"&|&"+tr.points[0].y+"&|&"+
                    tr.points[1].x+"&|&"+tr.points[1].y+"&|&"+color+"&|&"+"\n"));
        }
        return "";
    }

    private String translateToString(Connector obj, Color col)
    {
        String color = getColorString(col);
        String name = obj.name.name;
        if (name == "") name = ".";
        return (new String("LMConnector"+"&|&"+name+"&|&"+
                obj.position.x+"&|&"+obj.position.y+"&|&"+
                "0"+"&|&"+"0"+"&|&"+color+"&|&"+"\n"));
    }
}
