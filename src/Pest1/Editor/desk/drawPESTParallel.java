/**
 * drawPESTParallel.java
 *
 *
 * Created: Tue Nov  3 12:00:25 1998
 *
 * @author  Software Technologie 24
 * @version 1.0
 */

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;         
import java.io.*;                 
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties; 
  
public class drawPESTParallel  {	
    public drawPESTParallel(Graphics g,int cx1, int cy1, int cx2, int cy2, Color c_color) {
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

