/**
 *  PESTdrawutil.java
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

public class PESTdrawutil {

public static Statematrix getState(Statechart nroot, int x1, int y1) {
	Statechart root = new Statechart(null,null,null,null);
	root = nroot;
	boolean ttest = true;
	State tempstate = null;
	State tempstate1 = null;
	State tempstate2 = null;
	State lauf,lauf2;
	StateList templist;
	Or_State otemp;
	And_State atemp;
	int tempx1 = x1;
	int tempy1 = y1;
	int tx =0;
	int ty = 0;

	if (root == null) { } 
	else
                {if (root.state == null) { } else
	{tempstate = root.state; 
	if (tempstate.rect == null) {ttest = true;} else {if (tempstate.rect.contains(x1,y1) ) {ttest = true;} else {ttest = false;}} 
	while (ttest==true) 
		{             
		lauf = tempstate;
		if (lauf instanceof Or_State)
			{otemp = (Or_State) tempstate;
			   templist = otemp.substates;
 			   ttest = false;
			if (lauf.rect !=null)
			    { if (lauf.rect.contains(tempx1,tempy1)) {
				  tempstate = lauf;
				  tempstate1 = tempstate;
				  tempx1 = tempx1-lauf.rect.x;
				  tempy1 = tempy1-lauf.rect.y;
				tx = tx + lauf.rect.x; 
				ty = ty + lauf.rect.y;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					}} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	

					} 
				}
	
		}


			if (lauf instanceof And_State)
			{atemp = (And_State) tempstate;
			   templist = atemp.substates;
 			   ttest = false;
			if (lauf.rect !=null)
			    { if (lauf.rect.contains(tempx1,tempy1)) {
				  tempstate = lauf;
				  tempstate1 = tempstate;
				  tempx1 = tempx1-lauf.rect.x;
				  tempy1 = tempy1-lauf.rect.y;
				tx = tx + lauf.rect.x; 
				ty = ty + lauf.rect.y;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					}} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	

					} 
				}
	
		}

	
		 if (lauf instanceof  Basic_State) {ttest = false;tempstate1 = lauf;tx = tx + lauf.rect.x; ty = ty + lauf.rect.y;}
	      }  
	}
	}	
	Statematrix statemat = new Statematrix(tempstate1,tempstate2,tx,ty);

	return statemat;

      } 


public static Statematrix getStateFrame(Statechart nroot, int x1, int y1, int x2, int y2) {
	Statechart root = new Statechart(null,null,null,null);
	root = nroot;
	boolean ttest = true;
	State tempstate = null;
	State tempstate1 = null;
	State tempstate2 = null;
	State lauf,lauf2;
	StateList templist;
	Or_State otemp;
	And_State atemp;
	int tempx1 = x1;
	int tempy1 = y1;
	int tempx2 = x2;
	int tempy2 = y2;
	int tx =0;
	int ty = 0;

	if (root == null) { } 
	else
                {if (root.state == null) { } else
	{tempstate = root.state; 
	if (tempstate.rect == null) {ttest = true;} else {if (tempstate.rect.contains(x1,y1) & tempstate.rect.contains(x2,y2) ) {ttest = true;} else {ttest = false;}} 
	while (ttest==true) 
		{             
		lauf = tempstate;
		if (lauf instanceof Or_State)
			{otemp = (Or_State) tempstate;
			   templist = otemp.substates;
 			   ttest = false;
			if (lauf.rect !=null)
			    { if (lauf.rect.contains(tempx1,tempy1) & lauf.rect.contains(tempx2,tempy2)) {
				  tempstate = lauf;
				  tempstate1 = tempstate;
				  tempx1 = tempx1-lauf.rect.x;
				  tempy1 = tempy1-lauf.rect.y;
				  tempx2 = tempx2-lauf.rect.x;
				  tempy2 = tempy2-lauf.rect.y;
				tx = tx + lauf.rect.x; 
				ty = ty + lauf.rect.y;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1) & lauf2.rect.contains(tempx2,tempy2)){
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					}} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1)& lauf2.rect.contains(tempx2,tempy2)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	

					} 
				}
	
		}


			if (lauf instanceof And_State)
			{atemp = (And_State) tempstate;
			   templist = atemp.substates;
 			   ttest = false;
			if (lauf.rect !=null)
			    { if (lauf.rect.contains(tempx1,tempy1) & lauf.rect.contains(tempx2,tempy2)) {
				  tempstate = lauf;
				  tempstate1 = tempstate;
				  tempx1 = tempx1-lauf.rect.x;
				  tempy1 = tempy1-lauf.rect.y;
				  tempx2 = tempx2-lauf.rect.x;
				  tempy2 = tempy2-lauf.rect.y;
				tx = tx + lauf.rect.x; 
				ty = ty + lauf.rect.y;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1) & lauf2.rect.contains(tempx2,tempy2)){
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					}} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(tempx1,tempy1) & lauf2.rect.contains(tempx2,tempy2)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	

					} 
				}
	
		}

	
		 if (lauf instanceof  Basic_State) {ttest = false;tempstate1 = lauf;tx = tx + lauf.rect.x; ty = ty + lauf.rect.y;}
	      }  
	}
	}	
	Statematrix statemat = new Statematrix(tempstate1,tempstate2,tx,ty);

	return statemat;

      } 



}
