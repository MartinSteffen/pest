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
State allstate,tempstate;
Or_State allorstate;
StateList stlist=null;
ConnectorList colist=null;
Or_State otemp1,otemp2;
And_State atemp1;
StateList sublist,sublist2;
Basic_State tempbasic;

  
    
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
		
if (matrix1.akt == null)
    {
	//*******************************************
  tempstate = root.state;
  root.state = new Or_State(new Statename("...root"),new StateList(tempstate,null),null,null,null);
	//*******************************************
  matrix1 = PESTdrawutil.getStateFrame(root,cx1,cy1,cx1+allstate.rect.width,cy1+allstate.rect.height);
  matrix2 = PESTdrawutil.getState(root,cx1,cy1);
  matrix3 = PESTdrawutil.getState(root,cx1+allstate.rect.width,cy1+allstate.rect.height);
    }
 
if (matrix2.akt == matrix3.akt & matrix1.akt instanceof Basic_State)
		    {
			if (matrix1.prev == null)
			    {
				tempstate = root.state;
				root.state = new Or_State(new Statename("...root"),new StateList(tempstate,null),null,null,null);
			    }
			matrix1 = PESTdrawutil.getStateFrame(root,cx1,cy1,cx1+allstate.rect.width,cy1+allstate.rect.height);
			if (matrix1.prev instanceof And_State) {
			    atemp1 = (And_State) matrix1.prev;
			    sublist = atemp1.substates;
			    while(sublist.head != matrix1.akt)
				{
				    sublist = sublist.tail;
				}
			}
			if (matrix1.prev instanceof Or_State) {
			    otemp1 = (Or_State) matrix1.prev;
			    sublist = otemp1.substates;
			    while(sublist.head != matrix1.akt)
				{
				    sublist = sublist.tail;
				}
			}
			
			tempbasic = (Basic_State) matrix1.akt;
			otemp2 = new Or_State(tempbasic.name,null,null,null,null,tempbasic.rect);
			sublist.head = otemp2;
		    }

                 matrix1 = PESTdrawutil.getStateFrame(root,cx1,cy1,cx1+allstate.rect.width,cy1+allstate.rect.height);
		 if (matrix2.akt == matrix3.akt & matrix1.akt==null)
		     {
System.out.println("nichts");
		     }
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
