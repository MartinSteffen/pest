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

  
/**
 * @author Michael Mai / Martin Bestaendig
 * @version V1.2</dl>
 *<H3> STATUS:</H3>
 * <ul>
 * <li> Zeichnen von Parallelstates funktioniert
 * </ul>
 */
public class drawPESTParallel  {


static int laufname = 0;

public drawPESTParallel (Graphics g,Statechart nroot,int cx1, int cy1, int cx2, int cy2, Color c_color) 
{
Statechart root = nroot;

CPoint temppoint1 = new CPoint(0,0),temppoint2= new CPoint(0,0),tp3=new CPoint(0,0);
Statematrix matrix1,matrix2;
Basic_State btemp1,btemp2;
Or_State otemp1,otemp2;
And_State atemp1,atemp2;
State tempstate,tempstate2,tempstate3,lauf,lauf2,tempstate8,tempstate9,tempstate4;
StateList templist=null,templist2=null,templist3=null,templist4=null,templist5 = null,templist6 = null;
CRectangle temprect1=null,temprect2=null,temprect3=null,temprect = null,temprect4 = null,temprect5 = null;
boolean quer,ttest2,ttest3;
TrList trlist=null,trlist2=null,trlist3=null,trlist4=null;
ConnectorList colist=null,colist2= null,colist3=null,colist4=null,colist5 = null;
Statename statenametemp=null;

int dx1 = cx1;
int dx2 = cx2;
int dy1 = cy1;
int dy2 = cy2;
int delx = Math.abs( (cx2-cx1));
int dely = Math.abs((cy2-cy1));


matrix1 = PESTdrawutil.getState(root,dx1,dy1);
matrix2 = PESTdrawutil.getState(root,dx2,dy2);



if (matrix1.akt != null & matrix2.akt != null)
 {
   
if ( delx  <  dely) { 
		//System.out.println("kleiner"); 
		dx2 = cx1; quer = false;
		dy1 = matrix1.y;
		if (matrix1.akt.rect != null) {dy2 = matrix1.y+matrix1.akt.rect.height;}
		} else  
		{
		//System.out.println("groesser");
		 dy2 = cy1;quer = true;
		dx1 = matrix1.x;
		if (matrix1.akt.rect != null) {dx2 = matrix1.x+matrix1.akt.rect.width;}
		}
     

// g.drawRect(dx1,dy1,dx2-dx1,dy2-dy1);

// g.drawRect(dx1-matrix1.x,0,matrix1.akt.rect.width-(dx1-matrix1.x),matrix1.akt.rect.height) ; 

// d.drawRect(cx1-matrix1.x,cy1-matrix1.y,cx2-cx1,cy2-cy1);



//System.out.println("matrix1 "+matrix1.akt); 
//System.out.println("matrix2 "+(matrix2.akt == matrix1.akt)); 


if (matrix1.akt == matrix2.akt & matrix1.akt != null ) 
    {
	if (matrix1.akt.rect != null) {
	    //System.out.println("par");


	    if (quer == false) {temprect1 = new CRectangle(0,0,dx1-matrix1.x,matrix1.akt.rect.height) ; 
	    temprect2 = new CRectangle(dx1-matrix1.x,0,matrix1.akt.rect.width-(dx1-matrix1.x),matrix1.akt.rect.height) ; } 
	    else
		{temprect1 = new CRectangle(0,0                      ,matrix1.akt.rect.width  ,dy1-matrix1.y) ; 
		temprect2 = new CRectangle(0,dy1-matrix1.y,matrix1.akt.rect.width  ,matrix1.akt.rect.height-(dy1-matrix1.y)) ; }
 	 
	    //System.out.println("quer : "+quer+"    dx:"+delx  +"  dy"+dely );
	    //System.out.println("r1 : "+matrix1.akt.rect);
	    //System.out.println("rn1 : "+temprect1);
	    //System.out.println("rn2 : "+temprect2);

	    // } else {System.out.println("FEHLER1");}
	    //   } else {System.out.println("FEHLER2");}


// ************************************
//System.out.println("suche State"); 


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

	btemp1 = new Basic_State(new Statename("___Basic_State"+laufname),temprect1);
	laufname ++;
	btemp2 = new Basic_State(new Statename("___Basic_State"+laufname),temprect2);
	laufname ++;
	templist = new StateList(btemp1,null);
	templist2 = new StateList(btemp2,templist);
	
	atemp1 = new And_State(statenametemp,templist2,temprect3);
	templist4 = new StateList(atemp1,templist3);
	otemp1.substates = templist4; 

drawPar(g,temprect1.x+matrix1.x,temprect1.y+matrix1.y,temprect1.width,temprect1.height,c_color);
drawPar(g,temprect2.x+matrix1.x,temprect2.y+matrix1.y,temprect2.width,temprect2.height,c_color);

    }

if (matrix1.akt instanceof Basic_State & root.state == matrix1.akt)
    {
	temprect3 = root.state.rect;
	btemp1 = new Basic_State(new Statename("___Basic_State"+laufname),temprect1);
	laufname ++;
	btemp2 = new Basic_State(new Statename("___Basic_State"+laufname),temprect2);
	laufname ++;
	templist = new StateList(btemp1,null);
	templist2 = new StateList(btemp2,templist);
	root.state = new And_State(new Statename("___And_State"+laufname),templist2,temprect3);
	laufname ++;
drawPar(g,temprect1.x+matrix1.x,temprect1.y+matrix1.y,temprect1.width,temprect1.height,c_color);
drawPar(g,temprect2.x+matrix1.x,temprect2.y+matrix1.y,temprect2.width,temprect2.height,c_color);
    }




if (matrix1.akt instanceof Basic_State & matrix1.prev instanceof And_State)
    	{      atemp1 = (And_State) matrix1.prev;
	temprect3 = matrix1.akt.rect;
                   temprect1.x = temprect1.x+temprect3.x;
	   temprect1.y = temprect1.y+temprect3.y;

	   temprect2.x = temprect2.x+temprect3.x;
	   temprect2.y = temprect2.y+temprect3.y;

      	   btemp1 = new Basic_State(new Statename("___Basic_State"+laufname),temprect1);
	   laufname ++;
	   btemp2 = new Basic_State(new Statename("___Basic_State"+laufname),temprect2);
	   laufname ++;

	   templist3 = null;

	   templist = atemp1.substates;
//System.out.println("temprect1 : "+temprect1);
//System.out.println("temprect2 : "+temprect2);

	      while (templist  != null) {
		if (templist.head != matrix1.akt) {templist4 = templist3; templist3 = new StateList(templist.head,templist4);}
	      templist = templist.tail;
	    }

	   templist2 = new StateList(btemp1,templist3);
	   templist3 = new StateList(btemp2,templist2);

	   atemp1.substates = templist3;

	 templist = atemp1.substates;
	      while (templist  != null) {
		drawPar(g,templist.head.rect.x+(matrix1.x-matrix1.akt.rect.x),templist.head.rect.y+(matrix1.y-matrix1.akt.rect.y),templist.head.rect.width,templist.head.rect.height,c_color);
	      templist = templist.tail;
	    }

    	}

	if (matrix1.akt instanceof Or_State & matrix1.akt.rect != null)
	{ 
	boolean test = true;
	//if (matrix1.akt instanceof And_State) {
	//	atemp1 = (And_State) matrix1.akt;
	//	templist = atemp1.substates;
	//	trlist = null;
	//	colist = null;
	//	}
	if (matrix1.akt instanceof Or_State) {
		otemp1 = (Or_State) matrix1.akt;
		templist = otemp1.substates;
		trlist = otemp1.trs;
		colist = otemp1.connectors;
		}
	
	temprect3 = new CRectangle(dx1-matrix1.x,dy1-matrix1.y,dx2-dx1,dy2-dy1);
                //System.out.println("temprect : "+temprect1);

	while (templist != null)
	{
		if (temprect3.intersects(templist.head.rect)) {test = false;}
 		templist = templist.tail;
	}	

	while (colist != null)
	{
	temprect4 = new CRectangle(colist.head.position.x,colist.head.position.y,12,12);
	if (temprect3.intersects(temprect4)) {test = false;} 
	colist = colist.tail;
	}	

	while (trlist != null) {

	temppoint1 = (CPoint) trlist.head.points[0];
	int pointlength = trlist.head.points.length - 1;
	temppoint2 = (CPoint) trlist.head.points[pointlength];
	if (temppoint1.x <= temppoint2.x) {tp3.x = temppoint1.x;} else {tp3.x = temppoint2.x;}
	if (temppoint1.y <= temppoint2.y) {tp3.y = temppoint1.y;} else {tp3.y = temppoint2.y;}
	temprect4 = new CRectangle(tp3.x,tp3.y,Math.abs(temppoint1.x-temppoint2.x),Math.abs(temppoint1.y-temppoint2.y));
//System.out.println("temprect4 : "+temprect4);
//	if (temprect.contains(temppoint1.x,temppoint1.y) & temprect.contains(temppoint2.x,temppoint2.y))
	if (temprect3.intersects(temprect4)) {test = false;}
 	trlist = trlist.tail;
	}

	if (test == true)
	    {      //System.out.println("ja, dass klappt");     // hier Zuweisung zu den einzelnen And_Unterstates
	// ****************************************************************************
	if (matrix1.akt instanceof And_State) {
		atemp1 = (And_State) matrix1.akt;
		templist = atemp1.substates;
		trlist = null;
		colist = null;
		}
	if (matrix1.akt instanceof Or_State) {
		otemp1 = (Or_State) matrix1.akt;
		templist = otemp1.substates;
		trlist = otemp1.trs;
		colist = otemp1.connectors;
		}
	templist3 = null;
	while (templist != null)
	{ 
	       if (templist.head != matrix1.akt) {
		if (temprect1.intersects(templist.head.rect))
			{
			templist4 = templist3; templist3 = new StateList(templist.head,templist4);
			templist3.head.rect.x = templist3.head.rect.x - temprect1.x;
			templist3.head.rect.y = templist3.head.rect.y - temprect1.y;
			} else
			{
			templist4 = templist2;templist2 = new StateList(templist.head,templist4);
			templist2.head.rect.x = templist2.head.rect.x - temprect2.x;
			templist2.head.rect.y = templist2.head.rect.y - temprect2.y;

			}	
		} else {statenametemp = templist.head.name;} 
	templist = templist.tail;
	}	

	colist3 = null;
	colist2 = null;

	while (colist != null)
	{
	temprect4 = new CRectangle(colist.head.position.x,colist.head.position.y,12,12);
	if (temprect1.intersects(temprect4)) 	
			{ colist4 = colist3; colist3 = new ConnectorList(colist.head,colist4);
			colist3.head.position.x = colist3.head.position.x - temprect1.x;
			colist3.head.position.y = colist3.head.position.y - temprect1.y;
			} 
	else //if (temprect2.intersects(temprect4))
			{
			colist5 = colist2;colist2 = new ConnectorList(colist.head,colist5);
			colist2.head.position.x = colist2.head.position.x - temprect2.x;
			colist2.head.position.y = colist2.head.position.y - temprect2.y;

			}	
	colist = colist.tail;
	} 

//System.out.println("connector hier");
	
	trlist4 = null;

	while (trlist != null) {

	temppoint1 = (CPoint) trlist.head.points[0];
	int pointlength = trlist.head.points.length - 1;
	temppoint2 = (CPoint) trlist.head.points[pointlength];
	if (temppoint1.x <= temppoint2.x) {tp3.x = temppoint1.x;} else {tp3.x = temppoint2.x;}
	if (temppoint1.y <= temppoint2.y) {tp3.y = temppoint1.y;} else {tp3.y = temppoint2.y;}
	temprect4 = new CRectangle(tp3.x,tp3.y,Math.abs(temppoint1.x-temppoint2.x),Math.abs(temppoint1.y-temppoint2.y));
//System.out.println("temprect4 : "+temprect4);

	
	if (temprect1.intersects(temprect4))
			{trlist4 = trlist3; trlist3 = new TrList(trlist.head,trlist4);
			 
			} else
			{
			trlist4 = trlist2;
			trlist2 = new TrList(trlist.head,trlist4);
			trlist2 = move_trans(trlist2, temprect3.x , temprect3.y );
			}  // hier noch arbeiten	
 	trlist = trlist.tail;
	}

//System.out.println("hhiieerr");


	if (colist2 == null & templist2 == null & trlist2 == null)
	{
	tempstate8 = new Basic_State(new Statename("___Basic_State"+laufname),temprect2);
	laufname = laufname + 1;
	} else
	{
	tempstate8 = new Or_State(new Statename("___Or_State"+laufname),templist2,trlist2,null,colist2,temprect2);
	laufname = laufname + 1;
	}

	if (colist3 == null & templist3 == null & trlist3 == null)
	{
	tempstate9 = new Basic_State(new Statename("___Basic_State"+laufname),temprect1);
	laufname = laufname + 1;
	} else
	{
	tempstate9 = new Or_State(new Statename("___Or_State"+laufname),templist3,trlist3,null,colist3,temprect1);
	laufname = laufname + 1;
	}

	if (matrix1.akt != root.state)
{
	if (matrix1.prev instanceof And_State)  // O.K.
	{ 
	//System.out.println("proc 1");
	templist3 = null;
	atemp2 = (And_State) matrix1.prev;
	temprect4 = matrix1.prev.rect;
	templist2 = atemp2.substates;
	while (templist2 != null) { 
		if (templist2.head != matrix1.akt) {templist4 = templist3; templist3 = new StateList(templist2.head,templist4);}
		templist2 = templist2.tail;}
	atemp2.substates = templist3;

	atemp1 = (And_State) matrix1.prev;
	templist6 = atemp1.substates;


drawPar(g,matrix1.x+tempstate8.rect.x,matrix1.y+tempstate8.rect.y,tempstate8.rect.width,tempstate8.rect.height,c_color);
drawPar(g,matrix1.x+tempstate9.rect.x,matrix1.y+tempstate9.rect.y,tempstate9.rect.width,tempstate9.rect.height,c_color);


	tempstate8.rect.x = tempstate8.rect.x+matrix1.akt.rect.x;
	tempstate8.rect.y = tempstate8.rect.y+matrix1.akt.rect.y;
	tempstate9.rect.x = tempstate9.rect.x+matrix1.akt.rect.x;
	tempstate9.rect.y = tempstate9.rect.y+matrix1.akt.rect.y;

	templist5 = new StateList(tempstate9,templist6);
	templist6 = templist5;
	templist5 = new StateList(tempstate8,templist6);
	atemp1.substates = templist5;

	}

	if (matrix1.prev instanceof Or_State) // O.K.
	{   //System.out.println("proc 202");
	templist3 = null;
	otemp2 = (Or_State) matrix1.prev;
	templist2 = otemp2.substates;
	while (templist2 != null) { 
		if (templist2.head != matrix1.akt) {templist4 = templist3; templist3 = new StateList(templist2.head,templist4);} else {statenametemp = templist2.head.name;}
	templist2 = templist2.tail;}
	otemp2.substates = templist3;

	otemp1 = (Or_State) matrix1.prev;
	
	temprect5 = matrix1.akt.rect;
	templist5 = new StateList(tempstate9,null);
	templist6 = templist5;
	templist5 = new StateList(tempstate8,templist6);
	tempstate4 = new And_State(statenametemp,templist5,temprect5);
	otemp1.substates = new StateList(tempstate4,otemp1.substates);

drawPar(g,temprect1.x+matrix1.x,temprect1.y+matrix1.y,temprect1.width,temprect1.height,c_color);
drawPar(g,temprect2.x+matrix1.x,temprect2.y+matrix1.y,temprect2.width,temprect2.height,c_color);

	}

}

// ******************************************************

if (matrix1.akt == root.state)
{
	if (matrix1.akt instanceof And_State)  // 
	{ 
	//System.out.println("proc 1");
	templist3 = null;
	atemp2 = (And_State) matrix1.prev;
	temprect4 = matrix1.prev.rect;
	templist2 = atemp2.substates;
	while (templist2 != null) { 
		if (templist2.head != matrix1.akt) {templist4 = templist3; templist3 = new StateList(templist2.head,templist4);}else {statenametemp = templist2.head.name;}
		templist2 = templist2.tail;}
	atemp2.substates = templist3;

	atemp1 = (And_State) matrix1.prev;
	templist6 = atemp1.substates;


drawPar(g,matrix1.x+tempstate8.rect.x,matrix1.y+tempstate8.rect.y,tempstate8.rect.width,tempstate8.rect.height,c_color);
drawPar(g,matrix1.x+tempstate9.rect.x,matrix1.y+tempstate9.rect.y,tempstate9.rect.width,tempstate9.rect.height,c_color);


	tempstate8.rect.x = tempstate8.rect.x+matrix1.akt.rect.x;
	tempstate8.rect.y = tempstate8.rect.y+matrix1.akt.rect.y;
	tempstate9.rect.x = tempstate9.rect.x+matrix1.akt.rect.x;
	tempstate9.rect.y = tempstate9.rect.y+matrix1.akt.rect.y;

	templist5 = new StateList(tempstate9,templist6);
	templist6 = templist5;
	templist5 = new StateList(tempstate8,templist6);
	atemp1.substates = templist5;

	}

	if (matrix1.akt instanceof Or_State) // O.K.
	{   //System.out.println("proc 2xxxxx");
	templist3 = null;
	otemp2 = (Or_State) matrix1.akt;
	templist2 = otemp2.substates;
	while (templist2 != null) { 
		if (templist2.head != matrix1.akt) {templist4 = templist3; templist3 = new StateList(templist2.head,templist4);}
		templist2 = templist2.tail;}
	otemp2.substates = templist3;

	otemp1 = (Or_State) matrix1.akt;
	
	temprect5 = matrix1.akt.rect;
	templist5 = new StateList(tempstate9,null);
	templist6 = templist5;
	templist5 = new StateList(tempstate8,templist6);
	tempstate4 = new And_State(new Statename("___And_State"+laufname),templist5,temprect5);
	laufname++;
	root.state = tempstate4;
	//otemp1.substates = new StateList(tempstate4,otemp1.substates);

drawPar(g,temprect1.x+matrix1.x,temprect1.y+matrix1.y,temprect1.width,temprect1.height,c_color);
drawPar(g,temprect2.x+matrix1.x,temprect2.y+matrix1.y,temprect2.width,temprect2.height,c_color);

	}

}

	// ****************************************************************************
	    } 	else {Editor.fehlermeldung1();//System.out.println("FEHLER3");
			}


	}

	// **************

    } else {Editor.fehlermeldung1();//System.out.println("FEHLER1");
	}
    } else {Editor.fehlermeldung1();//System.out.println("FEHLER2");
	}

}

}

public static void drawPar(Graphics g,int cx1, int cy1, int cx2, int cy2, Color c_color) 
	{
	drawParallel(g,
			(int) (cx1* Editor.ZoomFaktor),
			(int) (cy1* Editor.ZoomFaktor),
			(int) (cx2 * Editor.ZoomFaktor),
			(int) (cy2 * Editor.ZoomFaktor),
			c_color);
	}

	
    public static void drawParallel(Graphics g,int cx1, int cy1, int cx2, int cy2, Color c_color) {
	int l1,l2;
	int abg;

	g.setColor(c_color);
	/*g.drawLine(cx1,cy1,cx1,cy2);
	g.drawLine(cx2,cy1,cx2,cy2);*/ 

	l1 = (cy1 / 10) +1;
	l2 = ((cy1+cy2) / 10);
              
	for (int i = l1; i < l2; i++)
	{
	g.drawLine(cx1,i*10,cx1,i*10+5);
	g.drawLine(cx1+cx2,i*10,cx1+cx2,i*10+5);
	}
	if  ((l1 * 10) > (cy1+5)) {
	g.drawLine(cx1,cy1,cx1,(10*l1) - 6);
	g.drawLine(cx1+cx2,cy1,cx1+cx2,(10*l1) - 6);
	} 
	if  ((l2 * 10) < (cy1+cy2)) {
	if ((l2*10)+5 < (cy1+cy2)) abg = (l2*10)+5; else abg = cy1+cy2;
	g.drawLine(cx1,l2 * 10,cx1,abg);
	g.drawLine(cx1+cx2,l2 * 10,cx1+cx2,abg);
	} 
	
	l1 = (cx1 / 10) +1;
	l2 = ((cx1+cx2) / 10);
              
	for (int i = l1; i < l2; i++)
	{
	g.drawLine(i*10,cy1,i*10 + 5,cy1);
	g.drawLine(i*10,cy1+cy2,i * 10+5,cy1+cy2);
	}
	if  ((l1 * 10) > (cx1+5)) {
	g.drawLine(cx1,cy1,(10*l1) - 6,cy1);
	g.drawLine(cx1,cy1+cy2,(10*l1) - 6,cy1+cy2);
	} 
	if  ((l2 * 10) < (cx1+cx2)) {
	if ((l2*10)+5 < (cx1+cx2)) abg = (l2*10)+5; else abg = cx1+cx2;
	g.drawLine(l2 * 10,cy1,abg,cy1);
	g.drawLine(l2 * 10,cy1+cy2,abg,cy1+cy2);
	}  
    } 



 private TrList move_trans ( TrList trl, int dx, int dy ) {

  // Verschiebt die x und y Werte im Array der Transition um dx und dy

  TrList dummylist = trl;

  // dx = dx == null ? 0 : dx;
  //  dy = dy == null ? 0 : dy;

  System.out.println("verschiebe die Koordinaten um " + dx + " und " + dy);

    while (trl != null) {

    if (trl.head != null) {

      for (int i = 0; i < trl.head.points.length; i++) {

        trl.head.points[i].x = trl.head.points[i].x - dx;
        trl.head.points[i].y = trl.head.points[i].y - dy;

      } // for

    } // if

    trl = trl.tail;

    } // while

    return dummylist;


  } // move_trans

    
} // drawPESTParallel

