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
import gui.popdialoge.*;


public class drawPESTState 
{

    Statechart root= new Statechart(null,null,null,null);
    boolean drawtest = true;  

    public drawPESTState(Graphics g,Statechart nroot, int cx1, int cy1, int cx2, int cy2, Color c_color) 
              {


g.setColor(c_color);
	g.drawRect(50,50,500,200);
	g.drawRect(100,100,100,100);
	g.drawRect(300,100,100,100);

	g.drawRect(120,120,20,50);
	g.drawRect(150,120,20,50);





	root = nroot;
	if (root.state != null)
	{
		if (root.state instanceof Basic_State)
		{
		Rectangle brect = new CRectangle(cx1,cx1,cx2-cx1,cy2-cy1);
		Basic_State btemp,btemp2;
		btemp = (Basic_State)root.state;
		if (btemp.rect.contains(cx1,cy1) & btemp.rect.contains(cx2,cy2))
			{
			btemp2 = new Basic_State(null,new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1) );
			root.state = new Or_State(null,new StateList(btemp2,null),null,null,null,btemp.rect);
			}
		if (brect.contains(btemp.rect.x,btemp.rect.y) & brect.contains(btemp.rect.x+btemp.rect.width,btemp.rect.y+btemp.rect.height))
			{
			btemp2 = (Basic_State) root.state;
			root.state = new Or_State(null,new StateList(btemp2,null),null,null,null,new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1) );
			}

		if (brect.contains(btemp.rect.x,btemp.rect.y) == false & brect.contains(btemp.rect.x+btemp.rect.width,btemp.rect.y+btemp.rect.height) == false
			& btemp.rect.intersects(brect)==false)
			{
			btemp2 = new Basic_State(null,new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1) );
			StateList blist = new StateList(btemp2,null);
			blist = new StateList(btemp,blist);
			root.state = new Or_State(null,blist,null,null,null );
			}

		}	  

	}
	else
	{
	root.state = new Basic_State(new Statename("BASE"),new CRectangle(cx1,cy1,cx2-cx1,cy2-cy1));
	System.out.println(" neu : "+root.state);
	}

State akt,prev,all;
 akt = PESTdrawutil.getState(root,cx1,cy1).akt;
prev = PESTdrawutil.getState(root,cx1,cy1).prev;
all = PESTdrawutil.getStateframe(root,cx1,cy1,cx2,cy2);

	System.out.println("akt : "+PESTdrawutil.getState(root,cx1,cy1).akt);
	System.out.println("prev : "+PESTdrawutil.getState(root,cx1,cy1).prev);
	System.out.println("all : "+PESTdrawutil.getStateframe(root,cx1,cy1,cx2,cy2));
	g.setColor(Color.red);
	if (akt != null) {g.drawRect(akt.rect.x,akt.rect.y,akt.rect.width,akt.rect.height);}
	g.setColor(Color.green);
	if (prev != null) {g.drawRect(prev.rect.x,prev.rect.y,prev.rect.width,prev.rect.height);}

g.setColor(Color.magenta);
	if (all != null) {g.drawRect(all.rect.x,all.rect.y,all.rect.width,all.rect.height);}

       }

 private boolean schnitt(CRectangle r1,CRectangle r2) {
	  int test = 0;
	  boolean  r2inr1;

	 
	  r2inr1 = r1.intersects(r2);
	 
	  System.out.println("r2inr1 : "+r2inr1);

	  return r2inr1;
      }

    
} // drawPESTState
