/**
 * labelPESTState.java
 *
 *
 * Created: Thu Nov  5 10:43:39 1998
 *
 * @author Software Technologie 24
 * @version 1.0
 */

package editor.desk;

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;         
import java.io.*;                 
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties;    
import absyn.*;
import editor.desk.*;
import editor.*;

public class labelPESTState 
{
Statechart root;

   boolean drawtest = true;  
   static int laufname = 0;
   Statematrix matrix1,matrix2;
    Or_State otemp;
    
    public labelPESTState(Statechart nroot, int cx1, int cy1) 
    {
	root = nroot;
	matrix1 = PESTdrawutil.getState(root,cx1,cy1);
	matrix2 = PESTdrawutil.getState(root,cx1,cy1+10 + (int) (10/Editor.ZoomFaktor));
	//if (matrix1.akt != null)
System.out.println("akt1 :"+matrix1.akt);
System.out.println("akt2 :"+matrix2.akt);
System.out.println("prev2 :"+matrix2.prev);
//if (matrix1.akt != null)
	{
	    //if (matrix1.akt.rect != null)
	{
	if (matrix2.prev instanceof And_State & matrix1.akt != matrix2.akt)
	    {

		matrix2.prev.name = new Statename(Editor.labelObject(matrix2.prev));

	    } else
		{
		    if (matrix1.akt != null)
		    {
		    matrix1.akt.name = new Statename(Editor.labelObject(matrix1.akt));
		   if (matrix1.prev != null & matrix1.prev instanceof Or_State)
		       {
			   otemp =  (Or_State) matrix1.prev;
			   otemp.defaults = null;
		       }
		    }
		} 
 
	}
	}
    }
    
} // drawPESTState
