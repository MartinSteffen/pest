/**
 * drawPESTParallel.java
 *
 *
 * Created: Tue Nov  3 12:00:25 1998
 *
 * @author  Software Technologie 24
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
import editor.desk.*;
import editor.*;
import absyn.*;

  
public class drawPESTParallel  {


static int laufname = 0;

public drawPESTParallel (Graphics g,Statechart nroot,int cx1, int cy1, int cx2, int cy2, Color c_color) 
{
Statechart root = nroot;


Statematrix matrix1,matrix2;
Basic_State btemp1,btemp2;
Or_State otemp1,otemp2;
And_State atemp1,atemp2;
State tempstate,tempstate2,tempstate3,lauf,lauf2;
StateList templist=null,templist2=null,templist3=null,templist4=null;
CRectangle temprect1=null,temprect2=null,temprect3=null;
boolean quer,ttest2,ttest3;
TrList trlist,trlist2,trlist3,trlsit4;
ConnectorList colist,colist2,colist3,colist4;
Statename statenametemp;

int dx1 = cx1;
int dx2 = cx2;
int dy1 = cy1;
int dy2 = cy2;
int delx = Math.abs( (cx2-cx1));
int dely = Math.abs((cy2-cy1));


matrix1 = PESTdrawutil.getState(root,dx1,dy1);
matrix2 = PESTdrawutil.getState(root,dx2,dy2);


if ( delx  <  dely) { System.out.println("kleiner"); dx2 = cx1; quer = false;} else  {System.out.println("groesser"); dy2 = cy1;quer = true;}

if (matrix1.akt == matrix2.akt & matrix1.akt != null ) 
	{
	if (matrix1.akt.rect != null) {
	System.out.println("par");


 if (quer == false) {temprect1 = new CRectangle(0,0,dx1-matrix1.x,matrix1.akt.rect.height) ; 
		temprect2 = new CRectangle(dx1-matrix1.x,0,matrix1.akt.rect.width-(dx1-matrix1.x),matrix1.akt.rect.height) ; } 
		else
		{temprect1 = new CRectangle(0,0                      ,matrix1.akt.rect.width  ,dy1-matrix1.y) ; 
		  temprect2 = new CRectangle(0,dy1-matrix1.y,matrix1.akt.rect.width  ,matrix1.akt.rect.height-(dy1-matrix1.y)) ; }
 	 
System.out.println("quer : "+quer+"    dx:"+delx  +"  dy"+dely );
	System.out.println("r1 : "+matrix1.akt.rect);
System.out.println("rn1 : "+temprect1);
System.out.println("rn2 : "+temprect2);

		} else {System.out.println("FEHLER1");}
	} else {System.out.println("FEHLER2");}


if (matrix1.akt instanceof Basic_State & matrix1.prev instanceof Or_State)
    {         
	tempstate = matrix1.prev;
	otemp1 = (Or_State) tempstate; 
	templist = otemp1.substates;
	temprect3 = matrix1.akt.rect;
	statenametemp = null;
	while (templist != null)
	{
	    if (templist.head != matrix1.akt) {templist4 = templist3; templist3 = new StateList(templist.head,templist4);} else {statenametemp = templist.head.name;}
		templist = templist.tail;
	}

	btemp1 = new Basic_State(new Statename("...Basic_State"+laufname),temprect1);
	laufname ++;
	btemp2 = new Basic_State(new Statename("...Basic_State"+laufname),temprect2);
	laufname ++;
	templist = new StateList(btemp1,null);
	templist2 = new StateList(btemp2,templist);
	
	atemp1 = new And_State(statenametemp,templist2,temprect3);
	templist4 = new StateList(atemp1,templist3);
	otemp1.substates = templist4; 

drawParallel(g,temprect1.x+matrix1.x,temprect1.y+matrix1.y,temprect1.x+temprect1.width+matrix1.x,temprect1.y+temprect1.height+matrix1.y,c_color);
drawParallel(g,temprect2.x+matrix1.x,temprect2.y+matrix1.y,temprect2.x+temprect2.width+matrix1.x,temprect2.y+temprect2.height+matrix1.y,c_color);

    }

if (matrix1.akt instanceof Basic_State & root.state == matrix1.akt)
    {
	temprect3 = root.state.rect;
	btemp1 = new Basic_State(new Statename("...Basic_State"+laufname),temprect1);
	laufname ++;
	btemp2 = new Basic_State(new Statename("...Basic_State"+laufname),temprect2);
	laufname ++;
	templist = new StateList(btemp1,null);
	templist2 = new StateList(btemp2,templist);
	root.state = new And_State(new Statename("...And_State"+laufname),templist2,temprect3);
	laufname ++;
drawParallel(g,temprect1.x+matrix1.x,temprect1.y+matrix1.y,temprect1.x+temprect1.width+matrix1.x,temprect1.y+temprect1.height+matrix1.y,c_color);
drawParallel(g,temprect2.x+matrix1.x,temprect2.y+matrix1.y,temprect2.x+temprect2.width+matrix1.x,temprect2.y+temprect2.height+matrix1.y,c_color);
    }




if (matrix1.akt instanceof Basic_State & matrix1.prev instanceof And_State)
    {      atemp1 = (And_State) matrix1.prev;
           temprect1.x = temprect1.x+matrix1.x;
	   temprect1.y = temprect1.y+matrix1.y;

	   temprect2.x = temprect2.x+matrix1.x;
	   temprect2.y = temprect2.y+matrix1.y;

	   btemp1 = new Basic_State(new Statename("...Basic_State"+laufname),temprect1);
	   laufname ++;
	   btemp2 = new Basic_State(new Statename("...Basic_State"+laufname),temprect2);
	   laufname ++;

	   templist3 = null;

	   templist = atemp1.substates;

	   while (templist.head != null) {
	       if (templist.head != matrix1.akt) {templist4 = templist3; templist3 = new StateList(templist.head,templist4);}
	       templist = templist.tail;

	   }

	   templist2 = new StateList(btemp1,templist3);
	   templist3 = new StateList(btemp2,templist2);
	   atemp1.substates = templist3;

    }

}






	
    public void drawParallel(Graphics g,int cx1, int cy1, int cx2, int cy2, Color c_color) {
	int l1,l2;
	int abg;

	g.setColor(c_color);
	/*g.drawLine(cx1,cy1,cx1,cy2);
	g.drawLine(cx2,cy1,cx2,cy2);*/ 

	l1 = (cy1 / 10) +1;
	l2 = (cy2 / 10);
              
	for (int i = l1; i < l2; i++)
	{
	g.drawLine(cx1,i*10,cx1,i*10+5);
	g.drawLine(cx2,i*10,cx2,i*10+5);
	}
	if  ((l1 * 10) > (cy1+5)) {
	g.drawLine(cx1,cy1,cx1,(10*l1) - 6);
	g.drawLine(cx2,cy1,cx2,(10*l1) - 6);
	} 
	if  ((l2 * 10) < (cy2)) {
	if ((l2*10)+5 < cy2) abg = (l2*10)+5; else abg = cy2;
	g.drawLine(cx1,l2 * 10,cx1,abg);
	g.drawLine(cx2,l2 * 10,cx2,abg);
	} 
	
	l1 = (cx1 / 10) +1;
	l2 = (cx2 / 10);
              
	for (int i = l1; i < l2; i++)
	{
	g.drawLine(i*10,cy1,i*10 + 5,cy1);
	g.drawLine(i*10,cy2,i * 10+5,cy2);
	}
	if  ((l1 * 10) > (cx1+5)) {
	g.drawLine(cx1,cy1,(10*l1) - 6,cy1);
	g.drawLine(cx1,cy2,(10*l1) - 6,cy2);
	} 
	if  ((l2 * 10) < (cx2)) {
	if ((l2*10)+5 < cx2) abg = (l2*10)+5; else abg = cx2;
	g.drawLine(l2 * 10,cy1,abg,cy1);
	g.drawLine(l2 * 10,cy2,abg,cy2);
	} 
    } 
    
} // drawPESTParallel

