/**
 * setDefault.java
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



/**
 * @author Michael Mai / Martin Bestaendig
 * @version V1.2</dl>
 *<H3> STATUS:</H3>
 * <ul>
 * <li> Markieren von Anfangszustaenden funktioniert
 * </ul>
 */
public class setDefault 
{
Statechart root;

boolean drawtest = true;  
Statematrix matrix1=null;
Or_State otemp1,otemp2;
Basic_State btemp1;
StatenameList NameList1=null,NameList2=null,NameList3 = null;;
boolean ttest;
    
    public setDefault(Graphics g,Statechart nroot, int cx1, int cy1, Color c_color) 
              {
	root = nroot;
	matrix1 = PESTdrawutil.getState(root,cx1,cy1);
	if ((matrix1.akt instanceof Basic_State) & (matrix1.prev instanceof Or_State))
		{
		otemp1 = (Or_State) matrix1.prev;
		btemp1 = (Basic_State) matrix1.akt;
		NameList1 = otemp1.defaults;
		ttest = true;
		while (NameList1 != null)
		   {
		       if (NameList1.head == btemp1.name) {ttest = false;} else
		       {NameList3 = NameList2; NameList2 = new StatenameList(NameList1.head,NameList3);}
		   NameList1 = NameList1.tail;
		   }
		NameList3 = null;
		 if (ttest == true) {NameList3 = NameList2; NameList2 = new StatenameList(matrix1.akt.name,NameList3);}
		   otemp1.defaults = NameList2;
		} else { 
		  // System.out.println(">>FEHLER in setDefault1<<");
		}
	

	if ((matrix1.akt instanceof Or_State) & (matrix1.prev instanceof Or_State))
		{
		otemp1 = (Or_State) matrix1.prev;
		otemp2 = (Or_State) matrix1.akt;
		NameList1 = otemp1.defaults;
		ttest = true;
		while (NameList1 != null)
		   {
		       if (NameList1.head == otemp2.name) {ttest = false;} else
		       {NameList3 = NameList2; NameList2 = new StatenameList(NameList1.head,NameList3);}
		   NameList1 = NameList1.tail;
		   }
		NameList3 = null;
		 if (ttest == true) {NameList3 = NameList2; NameList2 = new StatenameList(matrix1.akt.name,NameList3);}
		   otemp1.defaults = NameList2;
		} else {
		  // System.out.println(">>FEHLER in setDefault2<<");
		}

	if (matrix1.akt == root.state & matrix1.akt.rect != null)
	  {
        State tempstate;
		Or_State otemp;
	    tempstate = root.state;
	    root.state = new Or_State(new Statename("...root"),new StateList(tempstate,null),null,null,null);
	    otemp = (Or_State) root.state; 
	    otemp.defaults = new StatenameList(matrix1.akt.name,null);		
	  }

    }
} // setDefault
