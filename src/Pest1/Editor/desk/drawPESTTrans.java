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
  
/**
 * @author Michael Mai / Martin Bestaendig
 * @version V1.2</dl>
 *<H3> STATUS:</H3>
 * <ul>
 * <li> Zeichnen von Transitionen funktioniert
 * </ul>
 */
public class drawPESTTrans  {
static boolean movetest=false;;
static Statechart root;	
static Absyn localobject;

public drawPESTTrans(Graphics g,Statechart nroot, Point[] pointarray, int countarray, Color c_color) {

CPoint[]  temppoint = new CPoint[countarray+1];
Statematrix matrix1,matrix2,matrix3;
TrList trtemp = null;
Or_State otemp1,otemp;
Tr transtemp;
State obname1,obname2;
Connector obcon1,obcon2;
TrAnchor name1= new UNDEFINED(),name2= new UNDEFINED();
Absyn getname1=null, getname2 = null; 
int cx1 = pointarray[0].x,cx2 = pointarray[countarray].x,cy1 = pointarray[0].y,cy2 = pointarray[countarray].y;
State tempstate,tempstate2,lauf,lauf2;
StateList templist= null;


root = nroot;

matrix1 = PESTdrawutil.getStateFrame(root,cx1,cy1,cx2,cy2);
matrix2 = PESTdrawutil.getState(root,cx1,cy1);
matrix3 = PESTdrawutil.getState(root,cx2,cy2);

if (matrix1.akt instanceof Basic_State)
{

if (matrix1.prev instanceof And_State)
  {
  }

if (matrix1.prev instanceof Or_State)
 {
     otemp = (Or_State) matrix1.prev; 
   
     for (int j=0;j < countarray; j++) { pointarray[j].x = pointarray[j].x;
     	                                pointarray[j].y = pointarray[j].y;
   									    temppoint[j] = new CPoint(pointarray[j]);}
 	temppoint[0] = transpoint(root,cx1,cy1);
 	temppoint[countarray] = transpoint(root,cx2,cy2);
     transtemp = new Tr(matrix1.akt.name,matrix1.akt.name,new TLabel(new GuardEmpty(new Dummy()),new ActionEmpty(new Dummy()))
 		     ,temppoint);
 	trtemp = otemp.trs;
	otemp.trs = new TrList(transtemp,trtemp);		 
 }

if (matrix1.akt == root.state)
  {
  tempstate = root.state;
  root.state = new Or_State(new Statename("___root"),new StateList(tempstate,null),null,null,null);
    otemp = (Or_State) root.state; 
  
    for (int j=0;j < countarray; j++) { pointarray[j].x = pointarray[j].x;
    	                                pointarray[j].y = pointarray[j].y;
  									    temppoint[j] = new CPoint(pointarray[j]);}
	temppoint[0] = transpoint(root,cx1,cy1);
	temppoint[countarray] = transpoint(root,cx2,cy2);
    transtemp = new Tr(tempstate.name,tempstate.name,new TLabel(new GuardEmpty(new Dummy()),new ActionEmpty(new Dummy()))
		     ,temppoint);
	
	otemp.trs = new TrList(transtemp,null);		 
  }
  

  
}



if (matrix1.akt instanceof Or_State)
{
  otemp1 = (Or_State) matrix1.akt;
  trtemp = otemp1.trs;
  
getname1 = PESTdrawutil.getSmallObject(root,cx1,cy1);
getname2 = PESTdrawutil.getSmallObject(root,cx2,cy2);

if (matrix2.akt.rect == null & getname1 instanceof Or_State) getname1 = null; 

if (matrix3.akt.rect == null & getname2 instanceof Or_State) getname2 = null; 
   
//System.out.println("name 1 :"+getname1);
//System.out.println("name 2 :"+getname2);
movetest = false;
      
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
	}
                movetest = false;
	if(getname2 != null)	
	{
	temppoint[countarray] = transpoint(root,cx2,cy2);
	if (movetest == true)
	{
	temppoint[countarray].x = temppoint[countarray].x - matrix1.x;
	temppoint[countarray].y = temppoint[countarray].y - matrix1.y;
	if (getname2 instanceof State) {  if (getname2 != root.state)
				           {
					 if (matrix3.prev instanceof And_State) 
					{obname2 = (State) matrix3.prev; name2 = obname2.name;}
					else {obname2 = (State) getname2; name2 = obname2.name;}
    				            } else {name2 = new UNDEFINED();}
				      };
	if (getname2 instanceof Connector) {obcon2 = (Connector) getname2; name2 = obcon2.name;};
	} else {temppoint[countarray] = new CPoint(cx2-matrix1.x,cy2-matrix1.y);} 
	} else {temppoint[countarray] = new CPoint(cx2-matrix1.x,cy2-matrix1.y);}
      

  transtemp = new Tr(name1,name2,new TLabel(new GuardEmpty(new Dummy()),new ActionEmpty(new Dummy()))
		     ,temppoint); // source, target, label
  otemp1.trs = new TrList(transtemp,trtemp);

  //System.out.println("tempp1 :"+temppoint[0]);
  //System.out.println("tempp2 :"+temppoint[countarray]);
  //System.out.println("name 1 :"+name1);
  //System.out.println("name 2 :"+name2);

}

}

        

public static void drawTrans(Graphics g,int cx1, int cy1, int cx2, int cy2,TrAnchor ta1,TrAnchor ta2, Color c_color) {
	
	double dx ,dy ,d ,ws ,winkel ,xu ,yu ,wneu;
	int size = 6;
	int size2 = 5;
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
		

	Polygon po = new Polygon();
	po.addPoint(cx2,cy2);

	wneu = winkel +Math. PI-((Math.PI)/7);	
	//g.drawLine(cx2,cy2,cx2+ (int) Math.round((4+size*Editor.ZoomFaktor)*Math.cos(wneu)),cy2- (int) Math.round((4+size*Editor.ZoomFaktor)*Math.sin(wneu)));
	po.addPoint(cx2+ (int) Math.round((4+size*Editor.ZoomFaktor)*Math.cos(wneu)),cy2- (int) Math.round((4+size*Editor.ZoomFaktor)*Math.sin(wneu)));	

	wneu = winkel - Math.PI;	//    wneu:=wwinkel+ ((180-ww)*(pi/180));
	//g.drawLine(cx2,cy2,cx2+ (int) Math.round((4+size*Editor.ZoomFaktor)*Math.cos(wneu)),cy2- (int) Math.round((4+size*Editor.ZoomFaktor)*Math.sin(wneu)));
	po.addPoint(cx2+ (int) Math.round((4+size2*Editor.ZoomFaktor)*Math.cos(wneu)),cy2- (int) Math.round((4+size2*Editor.ZoomFaktor)*Math.sin(wneu)));


	wneu = winkel - Math.PI +((Math.PI)/7);	//    wneu:=wwinkel+ ((180-ww)*(pi/180));
	//g.drawLine(cx2,cy2,cx2+ (int) Math.round((4+size*Editor.ZoomFaktor)*Math.cos(wneu)),cy2- (int) Math.round((4+size*Editor.ZoomFaktor)*Math.sin(wneu)));
	po.addPoint(cx2+ (int) Math.round((4+size*Editor.ZoomFaktor)*Math.cos(wneu)),cy2- (int) Math.round((4+size*Editor.ZoomFaktor)*Math.sin(wneu)));


	//if (ta1 instanceof UNDEFINED) {g.fillOval(cx1-3,cy1-3,6,6);}
       	//if (ta2 instanceof UNDEFINED) {g.fillOval(cx2-3,cy2-3,6,6);} 
	g.fillPolygon(po);
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
		if (matrix1.akt.rect != null)
		{	
	     if ( (ncx1-matrix1.x) <= 15) {ncx1=matrix1.x;movetest = true;}
	     if ( (ncy1-matrix1.y) <= 15) {ncy1=matrix1.y;movetest = true;}
	     if ( (matrix1.x+matrix1.akt.rect.width)-ncx1 <= 15) {ncx1=matrix1.x+matrix1.akt.rect.width;movetest = true;}
	     if ( (matrix1.y+matrix1.akt.rect.height)-ncy1 <= 15) {ncy1=matrix1.y+matrix1.akt.rect.height;movetest = true;}
	     ttest = true;
		} 
	     }
	     if (tempabsyn instanceof Connector)
	     {
	      matrix1 = PESTdrawutil.getState(root,ncx1,ncy1);	
	      tempcon = (Connector) tempabsyn;
	     // ncx1 = matrix1.x+tempcon.position.x+6;
 	     // ncy1 = matrix1.y+tempcon.position.y+6;
	movetest = true;
	     }

	temppoint.x = ncx1;
	temppoint.y = ncy1;
	return temppoint; 
	}

 public static void  BTrans (Graphics h,Tr tr, int cx1, int cy1,Color def_tr)
{
	CPoint[] tp = tr.points;
	int lauf2 = tp.length-1;  
	CPoint[] ta = Bezier(tp,4*(lauf2));
	int trsize = ta.length-1;
	//System.out.println("Anzahl ZeigerPunkte : "+(int) (trsize+1));
	for (int lauf = 0;lauf < (trsize-1);lauf++) {h.setColor(def_tr);h.drawLine(	(int) ((ta[lauf].x+cx1)*Editor.ZoomFaktor),
									(int) ((ta[lauf].y+cy1)*Editor.ZoomFaktor),
									(int) ((ta[lauf+1].x+cx1)*Editor.ZoomFaktor),
									(int) ((ta[lauf+1].y+cy1)*Editor.ZoomFaktor) )
									;}

 				drawPESTTrans.drawTrans(h,
				(int) ((ta[trsize-1].x+cx1)*Editor.ZoomFaktor),
				(int) ((ta[trsize-1].y+cy1)*Editor.ZoomFaktor),
				(int) ((ta[trsize].x+cx1)*Editor.ZoomFaktor),
				(int) ((ta[trsize].y+cy1)*Editor.ZoomFaktor),
				tr.source,
				tr.target,
				def_tr );

}

private static CPoint[] Bezier(CPoint[] r, int genau)
{
double[] x = new double[1000];
double[] y = new double[1000];
int m;
int n = r.length-1;
CPoint[] rt = new CPoint[genau+1];

for (int t = 0; t <= genau; t++)
	{
	for (int i = 0;i <= n;i++)
		{
//	System.out.println(i+". Teil : "+r[i]);
		x[i] = (double) r[i].x;
		y[i] = (double) r[i].y;
		//x[2*i+1] = (double) r[i].x;
		//y[2*i+1] = (double) r[i].y;

		}
	m = n;
	while (m > 0)
		{
		for (int j = 0;j <=(m-1);j++)
			{
			x[j] = x[j]+((double)t/(double)genau)*(x[j+1]-x[j]);
			y[j] = y[j]+((double)t/(double)genau)*(y[j+1]-y[j]);
			}
		m--;
		}
	rt[t] = new CPoint((int) Math.round(x[0]), (int) Math.round(y[0]));
	//System.out.println((int) Math.round(x[0]));
	}
	return rt;

}

} // drawPESTTrans






