/**
 *  makevarlist
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

public class makevarlist{
	
   public makevarlist(Statechart nroot,TLabel aktlabel)
	{
	Statechart root = nroot;
	Action taction;
	Guard tguard;
	taction = aktlabel.action;
	tguard = aktlabel.guard;
	if (taction instanceof ActionBlock) {new makevarlist(root,(ActionBlock) taction);}
	if (taction instanceof ActionEvt) {new makevarlist(root,(ActionEvt) taction);}
	if (taction instanceof ActionStmt) {new makevarlist(root,(ActionStmt) taction);}

	if (tguard instanceof GuardEvent) {new makevarlist(root,(GuardEvent) tguard);}
	if (tguard instanceof GuardBVar) {new makevarlist(root,(GuardBVar) tguard);}
	if (tguard instanceof Guard) {new makevarlist(root,(Guard) tguard);}
	if (tguard instanceof GuardCompg) {new makevarlist(root,(GuardCompg) tguard);}

	}

// ****************** Guardlistener ************************

   public makevarlist(Statechart nroot,GuardEvent evt)
	{
	 Statechart root = nroot;
	 new makevarlist(root,(SEvent) evt.event);
	}

   public makevarlist(Statechart nroot,GuardBVar evt)
	{
	Statechart root = nroot;
	new makevarlist(root,(Bvar) evt.bvar);
	}

   public makevarlist(Statechart nroot,Guard evt)
	{
	Statechart root = nroot;
	GuardNeg gn1;
	if (evt instanceof GuardEvent) {new makevarlist(root,(GuardEvent) evt);}
	if (evt instanceof GuardBVar) {new makevarlist(root,(GuardBVar) evt);}
	if (evt instanceof GuardCompg) {new makevarlist(root,(GuardCompg) evt);}
	if (evt instanceof GuardNeg) {gn1 = (GuardNeg) evt;new makevarlist(root,(Guard) gn1.guard);}
	}

   public makevarlist(Statechart nroot,GuardCompg evt)
	{
	Statechart root = nroot; 
	Compguard cg1 = evt.cguard;
	new makevarlist(root,(Guard) cg1.elhs);
	new makevarlist(root,(Guard) cg1.erhs);

	}

// ********************* andere ******************************

   	public makevarlist(Statechart nroot,Bassign evt)
	{
	Statechart root = nroot;
	new makevarlist (root,(Bvar) evt.blhs);
	new makevarlist (root,(Guard) evt.brhs);
	}

   	public makevarlist(Statechart nroot,Aseq evt)
	{
	Statechart root = nroot;
	Aseq lauf = evt;
	if (lauf.head != null) 
	  {
 	  Action taction;
	  taction = lauf.head;
	  if (taction instanceof ActionBlock) {new makevarlist(root,(ActionBlock) taction);}
	  if (taction instanceof ActionEvt) {new makevarlist(root,(ActionEvt) taction);}
	  if (taction instanceof ActionStmt) {new makevarlist(root,(ActionStmt) taction);}
	  new makevarlist (root,(Aseq)lauf.tail);
	  }
	}


// ********************* Actionlistener *************************

   public makevarlist(Statechart nroot,ActionBlock evt)
	{
	Statechart root = nroot;
	Aseq as1 = evt.aseq;
	new makevarlist (root,as1);
	}

   public makevarlist(Statechart nroot,ActionEvt evt)
	{
	Statechart root = nroot;
	new makevarlist (root,(SEvent) evt.event);
	}

   public makevarlist(Statechart nroot,ActionStmt evt)
	{
	Statechart root = nroot;
	Boolstmt neustmt = evt.stmt;
	MTrue mt1;
	MFalse mf1;
	Bassign mb1;
	BAss bs1;
	if (neustmt instanceof MTrue) {mt1 = (MTrue) neustmt;new makevarlist (root,(Bvar) mt1.var);}
	if (neustmt instanceof MFalse) {mf1 = (MFalse) neustmt;new makevarlist (root,(Bvar) mf1.var);}
	if (neustmt instanceof BAss) {bs1 = (BAss) neustmt;mb1 = (Bassign) bs1.ass;new makevarlist (root,mb1);}
	}

// ******************** Event-/Varlistener ************************

   public makevarlist(Statechart nroot,SEvent evt)
	{
	Statechart root = nroot;
	SEventList tempeventlist = root.events;
	if (evt != null) {root.events = new SEventList(evt,tempeventlist);}
	}

   public makevarlist(Statechart nroot,Bvar bvar)
	{
	Statechart root = nroot;
	BvarList tempvarlist = root.bvars;
	if (bvar != null) {root.bvars = new BvarList(bvar,tempvarlist);}
	}
}
