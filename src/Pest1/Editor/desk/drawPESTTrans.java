/**
 * drawPESTTrans.java
 *
 *
 * Created: Tue Nov  3 12:00:25 1998
 *
 * @author Software Technologie 24
 * @version 1.0
 */

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;         
import java.io.*;                 
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties; 
  
public class drawPESTTrans  {	
    public drawPESTTrans(Graphics g,int cx1, int cy1, int cx2, int cy2, Color c_color, boolean defpoint) {
	
	double dx ,dy ,d ,ws ,winkel ,xu ,yu ,wneu;
	int size = 7;
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
	if (defpoint == true) g.fillOval(cx1-3,cy1-3,6,6);
    } 
    
} // drawPESTTrans

