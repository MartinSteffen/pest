/**
 * highlightObject.java
 *
 *
 * Created: Fri Nov 27 09:58:01 1998
 *
 */

package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import absyn.*;
import editor.desk.*;

/**
 * @author Michael Mai / Martin Bestaendig
 * @version v 1.9 </dl>
 * <H3>STATUS:</H3>
 * <ul>
 * <li> Alle Objekte koennen gehighlightet werden.
 * <li> Stubs dienen zur Kompatibilitaet mit anderem Editor
 * </ul>
 */
public class highlightObject {

    static Graphics h;
    static highLList drawlist = null;       

/**
 * Parameteruebergabe von Zeicheninformationen
 *<ul>  
 * <li>g :  Kennung der Zeichenflaeche     
 * </ul>
 */    
    public highlightObject (Graphics g) 
    {
	h = g;
    };
   
/**
 * Generates a change to default color of object 
 *<ul>
 * <li>g    : graphic frame   
 * <li>obj  :  
 * </ul>
 */
    highLList temp;
    highLO tempobj;

/**
 * Stub zu Editor2 
 *<ul>  
 * <li>obj  :  uebergebener Basic_State  
 * </ul>
 */
    public highlightObject(Basic_State obj) 
    { 
    }
/**
 * Zeichnet einen Basic-State in der angegebenen Farbe
 *<ul>  
 * <li>obj  :  uebergebener Basic_State    
 * <li>col  :  uebergebene Farbe 
 * </ul>
 */    
    public highlightObject(Basic_State obj, Color col) 
    {
	temp = drawlist;
	tempobj = new highLO(obj,col);
	drawlist = new highLList(tempobj,temp);
	//System.out.println("highlist : "+drawlist.head.type);
    }
/**
 * Stub zu Editor2
 *<ul>  
 * <li>obj  :  uebergebener And_State     
 * </ul>
 */  
    public highlightObject(And_State obj) 
    {
    }
/**
 * Zeichnet einen And_State in der angegebenen Farbe
 *<ul>  
 * <li>obj  :  uebergebener And_State    
 * <li>col  :  uebergebene Farbe 
 * </ul>
 */  
    public highlightObject(And_State obj, Color col) 
    {
 	temp = drawlist;
	tempobj = new highLO(obj,col);
	drawlist = new highLList(tempobj,temp);
	//System.out.println("highlist : "+drawlist.head.type);
    }
/**
 * Stub zu Editor2
 *<ul>  
 * <li>obj  :  uebergebener Or_State     
 * </ul>
 */    
    public highlightObject(Or_State obj) 
    { 
    }
/**
 * Zeichnet einen And_State in der angegebenen Farbe
 *<ul>  
 * <li>obj  :  uebergebener Or_State 
 * <li>col  :  uebergebene Farbe     
 * </ul>
 */ 
    public highlightObject(Or_State obj, Color col) 
    {
 	temp = drawlist;
	tempobj = new highLO(obj,col);
	drawlist = new highLList(tempobj,temp);
	//System.out.println("highlist : "+drawlist.head.type);
    }
/**
 * Stub zu Editor2
 *<ul>  
 * <li>obj  :  uebergebene Transition     
 * </ul>
 */    
    public highlightObject(Tr obj) 
    { 
    }
/**
 * Zeichnet eine Transition in der angegebenen Farbe
 *<ul>  
 * <li>obj  :  uebergebene Transition 
 * <li>col  :  uebergebene Farbe     
 * </ul>
 */     
    public highlightObject(Tr obj, Color col)
    {
 	temp = drawlist;
	tempobj = new highLO(obj,col);
	drawlist = new highLList(tempobj,temp);
	//System.out.println("highlist : "+drawlist.head.type);
    }
/**
 * Stub zu Editor2
 *<ul>  
 * <li>obj  :  uebergebene Transition     
 * </ul>
 */    
    public highlightObject(Connector obj) 
    { 
    }
/**
 * Zeichnet einen Connector in der angegebenen Farbe
 *<ul>  
 * <li>obj  :  uebergebener Connector 
 * <li>col  :  uebergebene Farbe     
 * </ul>
 */         
    public highlightObject(Connector obj, Color col) 
    {
 	temp = drawlist;
	tempobj = new highLO(obj,col);
	drawlist = new highLList(tempobj,temp);
	//System.out.println("highlist : "+drawlist.head.type);
    }
/**
 * Universalschnittstelle zu Zeichnen von AbSyn-Objekten
 *<ul>  
 * <li>obj  :  uebergebenes Objekt der SbSyn
 * <li>col  :  uebergebene Farbe     
 * </ul>
 */    
    public highlightObject(Absyn obj, Color col) 
    {
	if (obj instanceof Basic_State) 
	    {
		new highlightObject((Basic_State) obj,col);
	    }

	if (obj instanceof Or_State) 
	    {
		new highlightObject((Or_State) obj,col);
	    }

	if (obj instanceof And_State) 
	    {
		new highlightObject((And_State) obj,col);
	    }

	if (obj instanceof Tr) 
	    {
		new highlightObject((Tr) obj,col);
	    }

	if (obj instanceof Connector) 
	    {
		new highlightObject((Connector) obj,col);
	    }
    }
/**
 * Parameteruebergabe von Zeicheninformationen
 *<ul>  
 * <li>g    :  Kennung der Zeichenoberflaeche 
 * <li>root :  Statechart     
 * </ul>
 */    
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
/**
 * Zeichnet alle uebergebenenObjekte in der angegebenen Farbe
 */    
    public highlightObject() 
    { 
	Editor.newdraw()  ;
    } // repaint aufrufen.
/**
 * Loescht die Liste der zu highlightenden Objekte
 */    
    public highlightObject(boolean test) 
    {
	drawlist = null;
    }

/**
 * lokale Klasse zum Aufbau der highlight-Liste
 */ 
    class highLO 
    {
	private Absyn type;
	private Color color;
	private highLO(Absyn ab, Color col)
	{ 
	    type = ab;
	    color = col;
	}
    }
/**
 * lokale Klasse zum Aufbau der highlight-Liste
 */ 
    class highLList 
    {
	private highLO head; 
	private highLList tail;
	private highLList(highLO h, highLList tl)
	{ 
	    head = h;
	    tail = tl;
	}
    }
} // highlightObject
