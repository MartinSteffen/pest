/**
 * setcopymove.java
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

public class setcopymove 
{
Statechart root;
Absyn obj;
State allstate;
Or_State allorstate;
StateList stlist=null;
ConnectorList colist=null;

  
    
    public setcopymove(Statechart nroot, Absyn nobj, int cx1, int cy1) 
    {
	Statematrix matrix1=null,matrix2=null,matrix3=null;
	boolean test;
	CRectangle temprect= null,temprect2= null;
	root = nroot;
	obj = nobj;
	
	if (obj instanceof State)
	    {
		allstate = (State) obj;
		matrix1 = PESTdrawutil.getStateFrame(root,cx1,cy1,cx1+allstate.rect.width,cy1+allstate.rect.height);
		matrix2 = PESTdrawutil.getState(root,cx1,cy1);
		matrix3 = PESTdrawutil.getState(root,cx1+allstate.rect.width,cy1+allstate.rect.height);
		
		if (matrix2.akt == matrix3.akt & matrix1.akt instanceof Or_State)
		    {
			allorstate = (Or_State) matrix1.akt;
			stlist = allorstate.substates;
			test = true;
System.out.println("**********");
			while (stlist != null)
			    {
				
				temprect = new CRectangle(stlist.head.rect.x+matrix1.x,
							  stlist.head.rect.y+matrix1.y,
							  stlist.head.rect.width,
							  stlist.head.rect.height);
				temprect2 =  new CRectangle(cx1,cy1,allstate.rect.width,allstate.rect.height);
				if (temprect.intersects(temprect2) == true) {test = false;}
				
				System.out.println("test:"+temprect+" "+temprect2+""+test);
				stlist = stlist.tail;
			    }

			colist = allorstate.connectors;
			while (colist != null)
			    {
				temprect = new CRectangle(colist.head.position.x+matrix1.x-6,
							  colist.head.position.y+matrix1.y-6,
							  12,12);
				temprect2 =  new CRectangle(cx1,cy1,allstate.rect.width,allstate.rect.height);
				if (temprect.intersects(temprect2) == true) {test = false;}
				
				System.out.println("test:"+temprect+" "+temprect2+""+test);
				colist = colist.tail;
			    }
			if (test == true)
			    {
				System.out.println("einfuegen");
				stlist = allorstate.substates;
				allstate.rect = new CRectangle(cx1-matrix1.x,
							       cy1-matrix1.y,
							       allstate.rect.width,
							       allstate.rect.height);
				allstate.name = new Statename(allstate.name.name+"Kopie");
				allorstate.substates = new StateList(allstate,stlist);
				obj = null;
				
			    }
			
		    }

	    }




}
    
} // setcopymove
