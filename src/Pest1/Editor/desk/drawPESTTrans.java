/**
 * drawPESTTrans.java
 *
 *
 * Created: Tue Nov  3 12:00:25 1998
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
import editor.*;
  
public class drawPESTTrans  {
static boolean movetest=false;;
	

public drawPESTTrans(Graphics g,Statechart nroot, Point[] pointarray, int countarray, Color c_color) {

CPoint[]  temppoint = new CPoint[countarray+1];
Statematrix matrix1,matrix2,matrix3;
Statechart root = nroot;
TrList trtemp = null;
Or_State otemp1;
Tr transtemp;
State obname1,obname2;
Connector obcon1,obcon2;
TrAnchor name1= new UNDEFINED(),name2= new UNDEFINED();
Absyn getname1=null, getname2 = null; 
int cx1 = pointarray[0].x,cx2 = pointarray[countarray].x,cy1 = pointarray[0].y,cy2 = pointarray[countarray].y;



matrix1 = PESTdrawutil.getStateFrame(root,cx1,cy1,cx2,cy2);
matrix2 = PESTdrawutil.getState(root,cx1,cy1);
matrix3 = PESTdrawutil.getState(root,cx2,cy2);

System.out.println("gemeinsame Tr : "+matrix1.akt);
if (matrix1.akt instanceof Or_State)
{
  otemp1 = (Or_State) matrix1.akt;
  trtemp = otemp1.trs;
  
getname1 = PESTdrawutil.getSmallObject(root,cx1,cy1);
getname2 = PESTdrawutil.getSmallObject(root,cx2,cy2);

if (matrix2.akt.rect == null & getname1 instanceof Or_State) getname1 = null; 

if (matrix3.akt.rect == null & getname2 instanceof Or_State) getname2 = null; 
   

System.out.println("name 1 :"+getname1);
System.out.println("name 2 :"+getname2);
movetest = false;
      
        if(getname1 != null)	
	{
	    for (int j=0;j < countarray; j++) { pointarray[j].x = pointarray[j].x - matrix1.x;
	                                        pointarray[j].y = pointarray[j].y - matrix1.y;
						temppoint[j] = new CPoint(pointarray[j]);}
	temppoint[0] = transpoint(root,cx1,cy1);
	
	if (movetest == true)
	{
	temppoint[0].x = temppoint[0].x - matrix1.x;
	temppoint[0].y = temppoint[0].y - matrix1.y;
	if (getname1 instanceof State) {  if (matrix2.prev instanceof And_State) 
					{obname1 = (State) matrix2.prev; name1 = obname1.name;}
					else {obname1 = (State) getname1; name1 = obname1.name;}
				      };

	if (getname1 instanceof Connector) {obcon1 = (Connector) getname1; name1 = obcon1.name;};
	} else {temppoint[0] = new CPoint(cx1-matrix1.x,cy1-matrix1.y);} 
	} else {temppoint[0] = new CPoint(cx1-matrix1.x,cy1-matrix1.y);} 
                movetest = false;
	if(getname2 != null)	
	{
	temppoint[countarray] = transpoint(root,cx2,cy2);
	if (movetest == true)
	{
	temppoint[countarray].x = temppoint[countarray].x - matrix1.x;
	temppoint[countarray].y = temppoint[countarray].y - matrix1.y;
	if (getname2 instanceof State) {  if (matrix3.prev instanceof And_State) 
					{obname2 = (State) matrix3.prev; name2 = obname2.name;}
					else {obname2 = (State) getname2; name2 = obname2.name;}
				      };
	if (getname2 instanceof Connector) {obcon2 = (Connector) getname2; name2 = obcon2.name;};
	} else {temppoint[countarray] = new CPoint(cx2-matrix1.x,cy2-matrix1.y);} 
	} else {temppoint[countarray] = new CPoint(cx2-matrix1.x,cy2-matrix1.y);}
      

  transtemp = new Tr(name1,name2,new TLabel(new GuardEmpty(new Dummy()),new ActionEmpty(new Dummy()))
		     ,temppoint); // source, target, label
  otemp1.trs = new TrList(transtemp,trtemp);

System.out.println("tempp1 :"+temppoint[0]);
System.out.println("tempp2 :"+temppoint[countarray]);
System.out.println("name 1 :"+name1);
System.out.println("name 2 :"+name2);

if (name1 instanceof UNDEFINED) {g.setColor(c_color);
				g.fillOval(	(int) (((temppoint[0].x+matrix1.x)-3)*Editor.ZoomFaktor),
						(int) (((temppoint[0].y+matrix1.y)-3)*Editor.ZoomFaktor),
						6,6);}


 drawTrans(g,
		(int) ((temppoint[0].x+matrix1.x)*Editor.ZoomFaktor),
		(int) ((temppoint[0].y+matrix1.y)*Editor.ZoomFaktor),
		(int) ((temppoint[countarray].x+matrix1.x)*Editor.ZoomFaktor),
		(int) ((temppoint[countarray].y+matrix1.y)*Editor.ZoomFaktor),
		name1,
		name2,
		c_color);
}

}

        

public static void drawTrans(Graphics g,int cx1, int cy1, int cx2, int cy2,TrAnchor ta1,TrAnchor ta2, Color c_color) {
	
	double dx ,dy ,d ,ws ,winkel ,xu ,yu ,wneu;
	int size = 10;
	int x, y;

	winkel = 0;
	ws = 0;
	x = cx2-cx1;
	y = cy2-cy1;
	
	d = Math.sqrt((x*x)+(y*y));
	dx = x/d;
	dy = y/d;  // pr?fen, ob n"tig

	if ((dx != 0) & (dy != 0 )) ws = (Math.PI/2)-Math.asin(dx);
	if ((dx != 0) & (dy > 0 ))  winkel = (2*Math.PI)-ws;
  	if ((dx != 0) & (dy < 0 ))  winkel = ws;

	if ((dx == 0) & (dy <= 0 ))   winkel = Math.PI/2;
	if ((dx == 0) & (dy >= 0 ))   winkel = (3*Math.PI)/2;
	if ((dx <= 0) & (dy == 0 ))   winkel = Math.PI;
	if ((dx > 0) & (dy == 0 ))   winkel = 0;

	g.setColor(c_color);
	g.drawLine(cx1,cy1,cx2,cy2);

	xu = cx2-Math.round(50*Math.cos(winkel)); 	
	yu = cy2-Math.round(50*Math.sin(winkel));	
		
	wneu = winkel +Math. PI-((Math.PI)/4);	
	g.drawLine(cx2,cy2,cx2+ (int) Math.round(size*Math.cos(wneu)),cy2- (int) Math.round(size*Math.sin(wneu)));
		
	wneu = winkel - Math.PI +((Math.PI)/4);	//    wneu:=wwinkel+ ((180-ww)*(pi/180));
	g.drawLine(cx2,cy2,cx2+ (int) Math.round(size*Math.cos(wneu)),cy2- (int) Math.round(size*Math.sin(wneu)));

//	if (ta1 instanceof UNDEFINED) {g.fillOval(cx1-3,cy1-3,6,6);}
	if (ta2 instanceof UNDEFINED) {g.fillOval(cx2-3,cy2-3,6,6);}
    } 


public static CPoint transpoint(Statechart root,int cx1, int cy1)
	{
	int ncx1=cx1,ncy1=cy1;
	Statematrix matrix1,matrix2,matrix3;
	CPoint temppoint = new CPoint(0,0);
	Connector tempcon; 
	boolean ttest= false;
	movetest = false;
	Absyn tempabsyn = PESTdrawutil.getSmallObject(root,ncx1,ncy1);

	    if (tempabsyn instanceof State)
	     {
	     matrix1 = PESTdrawutil.getState(root,ncx1,ncy1);	
	     if ( (ncx1-matrix1.x) <= 15) {ncx1=matrix1.x;movetest = true;}
	     if ( (ncy1-matrix1.y) <= 15) {ncy1=matrix1.y;movetest = true;}
	     if ( (matrix1.x+matrix1.akt.rect.width)-ncx1 <= 15) {ncx1=matrix1.x+matrix1.akt.rect.width;movetest = true;}
	     if ( (matrix1.y+matrix1.akt.rect.height)-ncy1 <= 15) {ncy1=matrix1.y+matrix1.akt.rect.height;movetest = true;}
	     ttest = true; 
	     }
	     if (tempabsyn instanceof Connector)
	     {
	      matrix1 = PESTdrawutil.getState(root,ncx1,ncy1);	
	      tempcon = (Connector) tempabsyn;
	      ncx1 = matrix1.x+tempcon.position.x+6;
 	      ncy1 = matrix1.y+tempcon.position.y+6;
	movetest = true;
	     }

	temppoint.x = ncx1;
	temppoint.y = ncy1;
	return temppoint; 
	}



} // drawPESTTrans

