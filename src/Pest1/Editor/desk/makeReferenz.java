/**
 * makeReferenz.java
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
 * <li> Erstellen von Referenz_States funktioniert
 * </ul>
 */
public class makeReferenz 
{
Statechart root;
Statematrix matrix;
Or_State otemp1;
And_State atemp1;
Basic_State btemp1;
StateList stlist;
    
    public makeReferenz(Graphics g,Statechart nroot, int cx1, int cy1, Color c_color) 
              {
	root = nroot;
	matrix = PESTdrawutil.getState(root,cx1,cy1);	 

	if (matrix.akt instanceof Basic_State)
	{  
		System.out.println("Basic_State gefunden");
		
		if (matrix.akt instanceof Ref_State)
			{
			System.out.println("Ref_State gefunden");
			if (matrix.prev instanceof Or_State)
				{
				otemp1 = (Or_State) matrix.prev;
				stlist = otemp1.substates;
				while (stlist != null)
					{
					if (stlist.head == matrix.akt) 
						{
						btemp1 = (Basic_State) stlist.head;
						stlist.head = new Basic_State(btemp1.name,btemp1.rect);
						}
					stlist = stlist.tail;
					}
				}
			if (matrix.prev instanceof And_State)
				{
				atemp1 = (And_State) matrix.prev;
				stlist = atemp1.substates;
				while (stlist != null)
					{
					if (stlist.head == matrix.akt) 
						{
						btemp1 = (Basic_State) stlist.head;
						stlist.head = new Basic_State(btemp1.name,btemp1.rect);
						}
					stlist = stlist.tail;
					}
				}
			if (matrix.prev == null)
				{
				btemp1 = (Basic_State) root.state;
				root.state = new Basic_State(btemp1.name,btemp1.rect);
				}
	
			} 
			else
			{
			SelectArea sela  = new SelectArea(cx1,cy1);
			System.out.println("FILENAME : "+sela.FileName);
			System.out.println("FILETYPE : "+sela.filetype);
			if (sela.filetype != null & sela.FileName.compareTo("")!=0)
			{
				if (matrix.prev instanceof Or_State)
					{
					otemp1 = (Or_State) matrix.prev;
					stlist = otemp1.substates;
					while (stlist != null)
						{
						if (stlist.head == matrix.akt) 
							{
							btemp1 = (Basic_State) stlist.head;
							stlist.head = new Ref_State(btemp1.name,btemp1.rect,sela.FileName,sela.filetype);
							}
						stlist = stlist.tail;
						}
					}
				if (matrix.prev instanceof And_State)
					{
					atemp1 = (And_State) matrix.prev;
					stlist = atemp1.substates;
					while (stlist != null)
						{
						if (stlist.head == matrix.akt) 
							{
							btemp1 = (Basic_State) stlist.head;
							stlist.head = new Ref_State(btemp1.name,btemp1.rect,sela.FileName,sela.filetype);
							}
						stlist = stlist.tail;
						}
					}
				if (matrix.prev == null)
				{
				btemp1 = (Basic_State) root.state;
				root.state = new Ref_State(btemp1.name,btemp1.rect,sela.FileName,sela.filetype);
				}


				}
			}
	} else
	{System.out.println("Fehler 101");}
    }
} // setDefault
