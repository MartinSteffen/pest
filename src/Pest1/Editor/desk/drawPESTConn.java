/**
 * drawPESTConn.java
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
 * <li> Zeichnen von Connectoren funktioniert
 * </ul>
 */
public class drawPESTConn 
{
Statechart root;

boolean drawtest = true;  
    static int laufname = 0;
   
    
    public drawPESTConn(Graphics g,Statechart nroot, int cx1, int cy1, Color c_color) 
    {
   Statematrix matrix1,matrix2;
   Statechart root = nroot;
   CPoint temppoint1 = new CPoint(0,0);
   ConnectorList colist1,colist2;
   Or_State otemp1=null,otemp2=null;
   And_State atemp1,atemp2=null;
   Basic_State btemp1,btemp2;
   Connector contemp;
   Statename tempstatename;
   CRectangle temprect,temprect2;
   StateList templist = null,templist2,templist3;
   State tempstate1;

   laufname = laufname+1;
	matrix1 = PESTdrawutil.getState(root,cx1-2,cy1-2);
	matrix2 = PESTdrawutil.getState(root,cx1+14,cy1+14);
	
//System.out.println("ma1 : "+matrix1.akt);
//System.out.println("ma2 : "+matrix2.akt);


	if (matrix1.akt == matrix2.akt)
	{
	      if (matrix1.akt instanceof Or_State)
	      {
	       boolean test = true;
	       otemp2 = (Or_State) matrix1.akt;
	       colist2 = otemp2.connectors;
	       templist3 = otemp2.substates;
	       temprect = new CRectangle(cx1-matrix1.x,cy1-matrix1.y,12,12);
	       while (colist2 != null)
		{
		temprect2 = new CRectangle(colist2.head.position.x,colist2.head.position.y,12,12);
		if (temprect.intersects(temprect2)) {test = false;}
		colist2 = colist2.tail;
		} 
	       while (templist3 != null)
		{
		temprect2 = templist3.head.rect;
		if (temprect.intersects(temprect2)) {test = false;}
		templist3 = templist3.tail;
		} 



	       if (test == true)
	       {otemp1 = (Or_State) matrix1.akt;
	       temppoint1.x = cx1-matrix1.x;
	       temppoint1.y = cy1-matrix1.y;
	       contemp = new Connector(new Conname("___Connector"+laufname),temppoint1);
	       colist1 = otemp1.connectors;
	       colist2 = new ConnectorList(contemp,colist1);
	       otemp1.connectors = colist2;
	       drawConn(g,cx1,cy1,c_color);}
	      }

	      if (matrix1.akt instanceof Basic_State)
	      {
	      tempstatename = matrix1.akt.name;
	      temprect = matrix1.akt.rect;
	      otemp1 = new Or_State(tempstatename,null,null,null,null,temprect);
	      temppoint1.x = cx1-matrix1.x;
	      temppoint1.y = cy1-matrix1.y;
	      contemp = new Connector(new Conname("___Connector"+laufname),temppoint1);
	      otemp1.connectors = new ConnectorList(contemp,null);
	      laufname++;
	      if (matrix1.akt == root.state) 
		{root.state = otemp1;drawConn(g,cx1,cy1,c_color);}
	      	else
                               {
		if (matrix1.prev instanceof And_State ) {atemp2 = (And_State) matrix1.prev; templist = atemp2.substates;}
		if (matrix1.prev instanceof Or_State) { otemp2 = (Or_State) matrix1.prev; templist = otemp2.substates;}

		templist3 = null;
	      	while (templist != null)
		{
		if (templist.head != matrix1.akt) {templist2 = templist3; templist3 = new StateList(templist.head,templist2);}
		templist = templist.tail;
		}
		templist2 = new StateList(otemp1,templist3);
		if (matrix1.prev instanceof And_State ) {atemp2.substates = templist2;} 
		if (matrix1.prev instanceof Or_State) { otemp2.substates = templist2;}
		 drawConn(g,cx1,cy1,c_color);
	               }
	      }

	   if (matrix1.akt == null & root.state !=null)
	      {  tempstate1 = root.state;

		temppoint1.x = cx1-matrix1.x;
	      	temppoint1.y = cy1-matrix1.y;
	      	contemp = new Connector(new Conname("___Connector"+laufname),temppoint1);

	                root.state = new Or_State(new Statename("___Or_State"+laufname),
				new StateList(tempstate1,null),
				null,
				null,
				new ConnectorList(contemp,null));
		drawConn(g,cx1,cy1,c_color);
	      }	      
	
	} else {//System.out.println("Conn Fehler");
		Editor.fehlermeldung1();
		}	

}

private void drawConn(Graphics g, int cx1,int cy1,Color c_color)
{ g.setColor(c_color);
   g.fillOval(  	(int) (cx1*Editor.ZoomFaktor),
		(int) (cy1*Editor.ZoomFaktor),
		12,12);
}
    
} // drawPESTState
