/**
 * highlightObject.java
 *
 *
 * Created: Fri Nov 27 09:58:01 1998
 *
 * @author Software Technologie 24
 * @version
 */

package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import absyn.*;
import editor.desk.*;

public class highlightObject {

  static Graphics h;
  static highLList drawlist = null;       

  public highlightObject (Graphics g) {h = g;};
   
    
/**
 * Generates a change to default color of object 
 *<ul>
 * <li>g    : graphic frame   
 * <li>obj  :  
 * </ul>
 */
   highLList temp;
   highLO tempobj;

    public highlightObject(Basic_State obj) { }
    public highlightObject(Basic_State obj, Color col) { temp = drawlist;
						tempobj = new highLO(obj,col);
						drawlist = new highLList(tempobj,temp);
						//System.out.println("highlist : "+drawlist.head.type);
}

    public highlightObject(And_State obj) { }
    public highlightObject(And_State obj, Color col) { 	temp = drawlist;
						tempobj = new highLO(obj,col);
						drawlist = new highLList(tempobj,temp);
						//System.out.println("highlist : "+drawlist.head.type);
}
   
    public highlightObject(Or_State obj) { }
    public highlightObject(Or_State obj, Color col) { 	temp = drawlist;
						tempobj = new highLO(obj,col);
						drawlist = new highLList(tempobj,temp);
						//System.out.println("highlist : "+drawlist.head.type);
}

    public highlightObject(Tr obj) { }
    public highlightObject(Tr obj, Color col) { 	temp = drawlist;
					tempobj = new highLO(obj,col);
					drawlist = new highLList(tempobj,temp);
					//System.out.println("highlist : "+drawlist.head.type);
}


    public highlightObject(Connector obj) { }
    public highlightObject(Connector obj, Color col) { 	temp = drawlist;
						tempobj = new highLO(obj,col);
						drawlist = new highLList(tempobj,temp);
						//System.out.println("highlist : "+drawlist.head.type);
}
    public highlightObject(Absyn obj, Color col) {
	if (obj instanceof Basic_State) {new highlightObject((Basic_State) obj,col);}
	if (obj instanceof Or_State) {new highlightObject((Or_State) obj,col);}
	if (obj instanceof And_State) {new highlightObject((And_State) obj,col);}
	if (obj instanceof Tr) {new highlightObject((Tr) obj,col);}
	if (obj instanceof Connector) {new highlightObject((Connector) obj,col);}

	}

    public highlightObject(Graphics g,Statechart root) 
     {
     h = g;
    highLList neulist = drawlist; 
     new HighObj(h);
      HighObj rp = new HighObj();
      while (neulist != null)
	{ 
	    //System.out.println("neulist :"+neulist.head.type);
	rp.start(root,0,0,true,neulist.head.type,neulist.head.color);
	neulist = neulist.tail;
	}
     }


    public highlightObject() { Editor.newdraw()  ;} // repaint aufrufen.
    public highlightObject(boolean test) {drawlist = null;}


class highLO 
{
     private Absyn type;
     private Color color;
     private highLO(Absyn ab, Color col)
	{ type = ab;
	   color = col;}
}

class highLList 
{
     private highLO head; 
     private highLList tail;
     private highLList(highLO h, highLList tl)
	{ head = h;
	   tail = tl;}
}



} // highlightObject
