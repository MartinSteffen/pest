/**
 *  PESTdrawutil.java
 *
 *
 * Created: Thu Nov  5 10:43:39 1998
 *
 * @author Software Technologie 24
 * @version 1.0
 */

package Editor.desk;

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;         
import java.io.*;                 
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties;    
import Absyn.*;

public class PESTdrawutil {

public static Statematrix getState(Statechart root, int x1, int y1) {
	boolean ttest = true;
	State tempstate = null;
	State tempstate1 = null;
	State tempstate2 = null;
	State lauf,lauf2;
	StateList templist;
	Or_State otemp;
	And_State atemp;

	tempstate1 = null; tempstate2 = null;
	if (root != null) {tempstate = root.state; tempstate1 = null;tempstate2 = null;
	if (tempstate.rect == null) {ttest = true;} else {if (tempstate.rect.contains(x1,y1) ) {ttest = true;} else {ttest = false;}} 
	while (ttest==true) 
		{             
		lauf = tempstate;
		if (lauf instanceof Or_State)
			{otemp = (Or_State) tempstate;
			   templist = otemp.substates;
 			   ttest = false;
			if (lauf.rect !=null & lauf.rect.contains(x1,y1))
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1)) {
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
			if (lauf.rect !=null & lauf.rect.contains(x1,y1))
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					} 
				}
	
		}
		 if (lauf instanceof  Basic_State) {ttest = false;tempstate1 = lauf;}
	      }  
	}
	Statematrix statemat = new Statematrix(tempstate1,tempstate2);

	return statemat;
      } 


public static State getStateframe(Statechart root, int x1, int y1, int x2, int y2) {
	boolean ttest = true;
	State tempstate = null;
	State tempstate1 = null;
	State tempstate2 = null;
	State lauf,lauf2;
	StateList templist;
	Or_State otemp;
	And_State atemp;

	tempstate1 = null; tempstate2 = null;
	if (root != null) {tempstate = root.state; tempstate1 = null;tempstate2 = null;
	if (tempstate.rect == null) {ttest = true;} else {if (tempstate.rect.contains(x1,y1) & tempstate.rect.contains(x2,y2) ) {ttest = true;} else {ttest = false;}} 
	while (ttest==true) 
		{             
		lauf = tempstate;
		if (lauf instanceof Or_State)
			{otemp = (Or_State) tempstate;
			   templist = otemp.substates;
 			   ttest = false;
			if (lauf.rect !=null & lauf.rect.contains(x1,y1) & lauf.rect.contains(x2,y2))
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1) & lauf2.rect.contains(x2,y2)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1) & lauf2.rect.contains(x2,y2)) {
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
			if (lauf.rect !=null & lauf.rect.contains(x1,y1) & lauf.rect.contains(x2,y2))
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1) & lauf2.rect.contains(x2,y2)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					} 
				}
			if (lauf.rect ==null) 
				{
				  tempstate = lauf;
				  tempstate1 = tempstate;
				 while (templist != null) { 
					lauf2 = templist.head;
					if (lauf2.rect.contains(x1,y1) & lauf2.rect.contains(x2,y2)) {
						tempstate2 = tempstate1;
						tempstate = lauf2;
						tempstate1 = lauf2;
						ttest = true;
						}
					templist = templist.tail;	
					} 
				}
	
		}
		 if (lauf instanceof  Basic_State) {ttest = false;tempstate1 = lauf;}
	      }  
	}
	Statematrix statemat = new Statematrix(tempstate1,tempstate2);

	return tempstate1;
      } 




}
