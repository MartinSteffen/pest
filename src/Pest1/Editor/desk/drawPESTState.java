/**
 * drawPESTState.java
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
 * <li> Zeichnen von States funktioniert
 * </ul>
 */
public class drawPESTState
{
Statechart root;

boolean drawtest = true;  
    static int laufname = 0;
   
    public drawPESTState(Graphics g,Statechart nroot, int cx1, int cy1, int cx2, int cy2, Color c_color) 
              {

root = nroot;

//System.out.println("arbeit");
//System.out.println("dieser :  "+PESTdrawutil.getStateFrame(root,cx1,cy1,cx2,cy2).akt);
	
	laufname = laufname + 1;
		if (root.state != null)
	{
		if (root.state instanceof Or_State)
		{
		if (s_or(root,cx1,cy1,cx2,cy2) == true) {g.setColor(c_color); g.drawRect((int) (cx1*Editor.ZoomFaktor),
										(int) (cy1*Editor.ZoomFaktor),
										(int) ((cx2-cx1)*Editor.ZoomFaktor),
										(int) ((cy2-cy1)*Editor.ZoomFaktor)
										);
			}
		} 
		if (root.state instanceof And_State)
		{
		  // System.out.println("AND");
		if (s_or(root,cx1,cy1,cx2,cy2) == true) {g.setColor(c_color); g.drawRect((int) (cx1*Editor.ZoomFaktor),
										(int) (cy1*Editor.ZoomFaktor),
										(int) ((cx2-cx1)*Editor.ZoomFaktor),
										(int) ((cy2-cy1)*Editor.ZoomFaktor)
										);}
		} 
		if (root.state instanceof Basic_State)
		{
		if (s_basic(root,cx1,cy1,cx2,cy2) == true) {g.setColor(c_color); g.drawRect((int) (cx1*Editor.ZoomFaktor),
										(int) (cy1*Editor.ZoomFaktor),
										(int) ((cx2-cx1)*Editor.ZoomFaktor),
										(int) ((cy2-cy1)*Editor.ZoomFaktor)
										);} 
		} 
	}
	else
	{
	root.state = new Basic_State( new Statename("___state"+laufname) ,new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1));
	g.setColor(c_color);
             	g.drawRect((int) (cx1*Editor.ZoomFaktor),
			(int) (cy1*Editor.ZoomFaktor),
			(int) ((cx2-cx1)*Editor.ZoomFaktor),
			(int) ((cy2-cy1)*Editor.ZoomFaktor)
		);	
	}
       }

private boolean s_basic(Statechart root,int cx1, int cy1, int cx2, int cy2) {

State tempstate,tempstate2,tempstate3;
Basic_State btemp,btemp2;
Or_State otemp;
And_State atemp;
StateList templist;
State lauf,lauf2;
boolean ttest = false;

	CRectangle brect = new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1);
		
	btemp = (Basic_State)root.state;

	if ( btemp.rect.contains(cx1,cy1) & btemp.rect.contains(cx2,cy2) )
		{
		btemp2 = new Basic_State( new Statename("___state"+laufname) ,new CRectangle(cx1-btemp.rect.x,cy1-btemp.rect.y,cx2-cx1,cy2-cy1) );
		Statename temp  = root.state.name;
		root.state = new Or_State(temp,new StateList(btemp2,null),null,null,null,btemp.rect);
		ttest = true;

  //System.out.println("no1");
		}
	
if ( brect.intersects(btemp.rect) == false)
		{
		Statename temp  = root.state.name;
		btemp2 = new Basic_State( new Statename("___state"+laufname) ,new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1));
		laufname = laufname +1;
		root.state = new Or_State( new Statename("___state"+laufname) ,new StateList(btemp2,new StateList(btemp,null)),null,null,null);
		ttest = true;
 //System.out.println("no3");

		}

	if ( brect.contains(btemp.rect.x,btemp.rect.y) & brect.contains(btemp.rect.x+btemp.rect.width,btemp.rect.y+btemp.rect.height) )
		{
		Statename temp  = root.state.name;
		btemp2 = new Basic_State(temp,btemp.rect);
		btemp2.rect.x = btemp2.rect.x-cx1;
		btemp2.rect.y = btemp2.rect.y-cy1;
		root.state = new Or_State(  new Statename("___state"+laufname)  ,new StateList(btemp2,null),null,null,null,new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1));
		ttest = true;
 //System.out.println("no2");

		}
		if (ttest == false)  {  Editor.fehlermeldung1(); // System.out.println("Fehler01");
				}   
	return ttest;
}

private boolean s_or(Statechart root,int cx1, int cy1, int cx2, int cy2) {

State tempstate,tempstate2,tempstate3,tempstate4,tempstate5;
Basic_State btemp,btemp2;
Or_State otemp,otemp2;
And_State atemp;
CRectangle temprect,temprect2;
StateList templist = null;
StateList templist2 = null;
StateList templist3 = null;
StateList templist4 = null;
Point temppoint1,temppoint2;

boolean ortyp;

TrList trlist = null;
TrList trlist2 = null;
TrList trlist3 = null;
TrList trlist4 = null;

ConnectorList colist = null;
ConnectorList colist2 = null;
ConnectorList colist3 = null;
ConnectorList colist4 = null;

String anchortemp1 = null;
String anchortemp2 = null;

State lauf,lauf2;
boolean ttest = false, ttest2 = true;
Statematrix matrix,matrix2,matrix3;

matrix = PESTdrawutil.getState(root,cx1,cy1);
matrix2 = PESTdrawutil.getState(root,cx2,cy2);

ttest = true;

   if (matrix.akt == matrix2.akt) {


                if (matrix.akt == null)
	{ temprect = new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1);
                 temprect2 = root.state.rect;
	   if (temprect.intersects(root.state.rect) == false){
                   tempstate = root.state;
	   btemp = new Basic_State(new Statename("___State"+laufname),new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1));
	   templist = new StateList(btemp,null);
	    laufname++;
	   root.state = new Or_State(new Statename("___State"+laufname),new StateList(tempstate,templist),null,null,null);
//	   System.out.println("aussen");
	   } 
	  else 
	       {  if (temprect.contains(temprect2.x,temprect2.y) & temprect.contains( temprect2.x+temprect2.width,temprect2.y+temprect2.height ))
		{
		tempstate = root.state;
		tempstate.rect.x = tempstate.rect.x -cx1;
 		tempstate.rect.y = tempstate.rect.y -cy1;
		templist = new StateList(tempstate,null);
		root.state = new Or_State(new Statename("___State"+laufname),templist,null,null,null,new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1));
		// System.out.println("innen");
		} else 
		{	Editor.fehlermeldung1();
			//System.out.println("Fehler INNEN");
			ttest = false;}
	       }
	}

	if (matrix.akt instanceof Basic_State)
	{
	tempstate = matrix.akt;
	tempstate2 = matrix.prev;
	if (tempstate2 instanceof And_State) {atemp = (And_State) tempstate2; templist = atemp.substates;}
	if (tempstate2 instanceof Or_State) {otemp = (Or_State) tempstate2; templist = otemp.substates;}
	
	while (templist != null) { 
		lauf2 = templist.head;
		//System.out.println("noch");
		if (tempstate == lauf2) {
			btemp = new Basic_State(new Statename("___state"+laufname),new CRectangle(cx1-matrix.x ,cy1-matrix.y ,cx2-cx1,cy2-cy1));  
			templist.head = new Or_State(tempstate.name,new StateList(btemp,null),null,null,null,tempstate.rect);
			ttest = true;
			}
		templist = templist.tail;	
		} 
	}

	if (matrix.akt instanceof Or_State)
	{
	ttest2 = true;
	tempstate = matrix.akt;
	tempstate2 = matrix.prev;
	otemp = (Or_State) tempstate; 
	templist = otemp.substates;
	trlist = otemp.trs;
	colist = otemp.connectors;
	temprect = new CRectangle(cx1-matrix.x,cy1-matrix.y,cx2-cx1,cy2-cy1);	
//System.out.println("rect  : "+temprect);

	ortyp = false; 
	while (templist != null) { 
	    if  (templist.head.rect.intersects(temprect)== false) {//System.out.println("out");
							} 
	    else {temprect2 = templist.head.rect;
//System.out.println("rect2 : "+temprect2);
		if (temprect.contains(temprect2.x,temprect2.y) &
		     temprect.contains(temprect2.x+temprect2.width,temprect2.y+temprect2.height)) 
		{//System.out.println("in");
		} else{   Editor.fehlermeldung1();
		ttest2 = false; // System.out.println("Fehler 001");
		}
	} 
	    templist = templist.tail;
	}	

	while (colist != null) {
	    temppoint1 = (Point) colist.head.position;
	    temppoint2 = (Point) new CPoint(colist.head.position.x+12,colist.head.position.y+12);

//System.out.println("temprect : "+temprect);
//System.out.println("temppoint1 : "+temppoint1);
//System.out.println("temppoint2 : "+temppoint2);

//System.out.println("temppoint1 in : "+ temprect.contains(temppoint1) );
//System.out.println("temppoint2 in : "+ temprect.contains(temppoint2 )  );


	    if (temprect.contains(temppoint1) & temprect.contains(temppoint2)) { 
	      colist4 = colist2; 
	      colist2 = new ConnectorList(colist.head,colist4);
	      //System.out.println("con in");
	      colist2.head.position.x = colist.head.position.x-(cx1-matrix.x);	    
	      colist2.head.position.y = colist.head.position.y-(cy1-matrix.y);
		
	    } else { 
	      if (temprect.contains(temppoint1)== false & temprect.contains(temppoint2) == false &
			  new Rectangle(temppoint1.x,temppoint1.y,12,12).intersects(temprect) == false) {
		colist4 = colist3;
		colist3 = new ConnectorList(colist.head,colist4);			  
		//System.out.println("con out");
	      } else { 
		Editor.fehlermeldung1();
		//System.out.println("Fehler in stateconn");
		ttest = false;	    
	      }    
	    }
	    colist = colist.tail;
	}

	while (trlist != null) {

	    temppoint1 = (CPoint) trlist.head.points[0];
	    int pointlength = trlist.head.points.length - 1;
	    temppoint2 = (CPoint) trlist.head.points[pointlength];

	    if (temprect.contains(temppoint1.x,temppoint1.y) & temprect.contains(temppoint2.x,temppoint2.y))
		{//trlist4 = trlist2; trlist2 = new TrList(trlist.head,trlist4);
	            for (int j = 1;j < pointlength;j ++)
			{
			   trlist.head.points[j].x = trlist.head.points[j].x-(cx1-matrix.x);
		 	   trlist.head.points[j].y = trlist.head.points[j].y-(cy1-matrix.y);
 
			}
			trlist.head.points[0].x = trlist.head.points[0].x-(cx1-matrix.x);
		 	trlist.head.points[0].y = trlist.head.points[0].y-(cy1-matrix.y);
			trlist.head.points[pointlength].x = trlist.head.points[pointlength].x-(cx1-matrix.x);
			trlist.head.points[pointlength].y = trlist.head.points[pointlength].y-(cy1-matrix.y);
			if (trlist.head.label.position != null)
			{
			    trlist.head.label.position.x = trlist.head.label.position.x-(cx1-matrix.x);
			    trlist.head.label.position.y = trlist.head.label.position.y-(cy1-matrix.y);
			}
			trlist4 = trlist2; trlist2 = new TrList(trlist.head,trlist4);
		} else 
		{trlist4 = trlist3; trlist3 = new TrList(trlist.head,trlist4);}
 

	    trlist = trlist.tail;
	}

          if (ttest == true & ttest2 == true) { templist = otemp.substates;
           //System.out.println("movexx");

              while (templist != null) { 
	    if  (templist.head.rect.intersects(temprect)== false) {
	      templist4 = templist3; 
	      templist3 = new StateList(templist.head,templist4); 
	      // System.out.println("moveyy"); 
	    } 
	    else {ortyp = true;templist4 = templist2; 
	    templist2 = new StateList(templist.head,templist4);
	    templist2.head.rect.x = templist.head.rect.x-(cx1-matrix.x);
	    templist2.head.rect.y = templist.head.rect.y-(cy1-matrix.y);
// System.out.println("move");
		} 
	    templist = templist.tail;
	}
             }

	if (ttest == true & ttest2 == true)
		{

if ((matrix.akt instanceof Or_State)  & (colist2 != null | templist2 != null | trlist2 != null) &
   (colist3 != null | templist3 != null | trlist3 != null) )
    //  {System.out.println("neue Funktion");}
    {//System.out.println("Or einx");
			tempstate5 = new Or_State(new Statename ("___State"+laufname),templist2,trlist2,null,colist2,new CRectangle(cx1-matrix.x,cy1-matrix.y,cx2-cx1,cy2-cy1));
			otemp = (Or_State) matrix.akt;
			templist4 = new StateList(tempstate5,templist3);
			otemp.substates = templist4;
			otemp.connectors = colist3;
			otemp.trs = trlist3;
						}		
		if ((matrix.akt instanceof Or_State)  & (colist2 != null | templist2 != null | trlist2 != null) & matrix.akt != root.state) {
		  // System.out.println("Or ein");
			tempstate5 = new Or_State(new Statename ("___State"+laufname),templist2,trlist2,null,colist2,new CRectangle(cx1-matrix.x,cy1-matrix.y,cx2-cx1,cy2-cy1));
			otemp = (Or_State) matrix.akt;
			templist4 = new StateList(tempstate5,templist3);
			otemp.substates = templist4;
			otemp.connectors = colist3;
			otemp.trs = trlist3;
			}

		if (matrix.akt instanceof Or_State & colist3 == null & trlist3 == null & templist3 == null & matrix.akt.rect != null)
		    {//System.out.println("wagadugu");
			tempstate5 = new Or_State(new Statename ("___State"+laufname),templist2,trlist2,null,colist2,new CRectangle(cx1-matrix.x,cy1-matrix.y,cx2-cx1,cy2-cy1));
			otemp = (Or_State) matrix.akt;
			templist4 = new StateList(tempstate5,templist3);
			otemp.substates = templist4;
			otemp.connectors = colist3;
			otemp.trs = trlist3;
		}
     
		 if ((matrix.akt instanceof Or_State)  & colist2 == null & templist2 == null & trlist2 == null) // O.K.
		     {//System.out.println("Basic ein");
			btemp = new Basic_State(new Statename ("___State"+laufname),new CRectangle(cx1-matrix.x,cy1-matrix.y,cx2-cx1,cy2-cy1));
			otemp = (Or_State) matrix.akt;
			templist = otemp.substates;
			templist2 = new StateList(btemp,templist);
			otemp.substates = templist2;
			}

		if (matrix.akt == root.state & templist3 == null & trlist3 == null & colist3 == null & root.state.rect == null)  // O.K.
			{matrix.akt.rect = new CRectangle(cx1-matrix.x,cy1-matrix.y,cx2-cx1,cy2-cy1);
			// System.out.println("333334433333333333333333333");	
			}
	}
}
	
   } else
   { Editor.fehlermeldung1();//System.out.println("Fehler in xx");
	ttest = false;}   

// System.out.println("test : "+ttest+" "+ttest2);

  if (ttest2 == false) {ttest = false;}
  return ttest;
}
    
} // drawPESTState
