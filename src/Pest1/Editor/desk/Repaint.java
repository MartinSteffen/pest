
package editor.desk;

import absyn.*;
import java.awt.*;
import editor.*;
import java.lang.*;
import tesc2.*;

public class Repaint {

  static Graphics h;
   
    // static Color def_state = Editor.st_color();
    //static Color def_conn = Color.red;
    static Color def_text = Color.black;
    //static Color def_tr = Color.magenta;
    static Statechart root;
    static Absyn localobject;

   String tempstring;
    
  public Repaint (Graphics g,Statechart nroot) {h = g;root = nroot;};
   
    
  public Repaint () {}


    public void start (Absyn ab,int nx, int ny, boolean drawflag) {
	int neux = nx;
	int neuy = ny;

	if (ab instanceof Statechart) redraw((Statechart) ab,neux,neuy,drawflag);
	if (ab instanceof Basic_State) redraw((Basic_State) ab,neux,neuy,drawflag);
	if (ab instanceof Or_State) redraw((Or_State) ab,neux,neuy,drawflag);
	if (ab instanceof And_State) redraw((And_State) ab,neux,neuy,drawflag);
	if (ab instanceof Statename) redraw((Statename) ab,neux,neuy,drawflag);
	if (ab instanceof Tr) redraw((Tr) ab,neux,neuy,drawflag);
    }

 
    private void redraw(Statechart sc, int nx, int ny, boolean drawflag) {
	int neux = nx;
	int neuy = ny;
	if (sc.state != null) { Repaint re = new Repaint(); re.start(sc.state,neux,neuy,true);
	Editor.init = false;}
	// die anderen folgen spaeter
    }

   private void redraw(Statename sn,int nx, int ny, boolean drawflag) {
   boolean test = true;
		if (sn.name.length() >= 3) {
		tempstring = sn.name.substring(0,3);
		//System.out.println("<<"+tempstring+">>");
		if (tempstring.compareTo("___")==0) test = false;}
 
		if (test == true)
		{
			if (drawflag == true)
			{
	 		h.setColor(def_text);h.drawString( sn.name,
			(int) ((nx*Editor.ZoomFaktor)+4),
			(int) ((ny*Editor.ZoomFaktor)+12));
			} else
			{
			h.setColor(def_text);h.drawString( sn.name,
			(int) ((nx*Editor.ZoomFaktor)+4),
			(int) ((ny*Editor.ZoomFaktor)-5));
			}
		}
	}

private void redraw(Tr tr,int nx, int ny, boolean drawflag) {
	int trsize = tr.points.length-1;
	if (tr.label.position != null)
	{
	h.setColor(def_text);
	if (tr.label.caption.length() < GraphOptimizer.TLABELLENGTH)
	{
	    h.drawString(tr.label.caption,
			 (int) ((tr.label.position.x+nx)*Editor.ZoomFaktor),
			 (int) ((tr.label.position.y+ny)*Editor.ZoomFaktor)
			 );
	} else
	    {
		h.drawString(Editor.LongLabel,
			 (int) ((tr.label.position.x+nx)*Editor.ZoomFaktor),
			 (int) ((tr.label.position.y+ny)*Editor.ZoomFaktor)
			 );
	    }
	}
	// System.out.println("Anzahl ZeigerPunkte : "+(int) (trsize+1));
	//for (int lauf = 0;lauf < (trsize-1);lauf++) {h.setColor(def_tr);h.drawLine(	(int) ((tr.points[lauf].x+nx)*Editor.ZoomFaktor),
	//								(int) ((tr.points[lauf].y+ny)*Editor.ZoomFaktor),
	//								(int) ((tr.points[lauf+1].x+nx)*Editor.ZoomFaktor),
	//								(int) ((tr.points[lauf+1].y+ny)*Editor.ZoomFaktor) )
	//								;}

 	//			drawPESTTrans.drawTrans(h,
	//			(int) ((tr.points[trsize-1].x+nx)*Editor.ZoomFaktor),
	//			(int) ((tr.points[trsize-1].y+ny)*Editor.ZoomFaktor),
	//			(int) ((tr.points[trsize].x+nx)*Editor.ZoomFaktor),
	//			(int) ((tr.points[trsize].y+ny)*Editor.ZoomFaktor),
	//			tr.source,
	//			tr.target,
	//			def_tr );
	drawPESTTrans.BTrans(h,tr,nx,ny, Editor.tr_color());

if (tr.source instanceof UNDEFINED) {h.setColor( Editor.tr_color());
				h.fillOval(	(int) (((tr.points[0].x+nx)-3)*Editor.ZoomFaktor),
						(int) (((tr.points[0].y+ny)-3)*Editor.ZoomFaktor),
						(int) (6*Editor.ZoomFaktor),(int) (6*Editor.ZoomFaktor));} else
	      {localobject = PESTdrawutil.getSmallObject(root,tr.points[0].x+nx,tr.points[0].y+ny);
//	      System.out.println("aktueller TRAnchor source :"+localobject);
		   if (localobject instanceof State) {
		       State hier = (State) localobject;
		       Statematrix matrix = PESTdrawutil.getState(root,tr.points[0].x+nx,tr.points[0].y+ny);
Statename trtest = (Statename)tr.source;

// System.out.println("AKT>"+matrix.akt);
//System.out.println("PREV>"+matrix.prev);
 //System.out.println("AKTRECT>"+matrix.akt.rect);

if (matrix.akt.rect != null)
{matrix = PESTdrawutil.getState(root,matrix.x-matrix.akt.rect.x,matrix.y-matrix.akt.rect.y);}


		      if (matrix.prev instanceof And_State) {
			  
			    if (hier.name.name.compareTo(trtest.name)!=0 &
				matrix.prev.name.name.compareTo(trtest.name) != 0)
				{
		//	System.out.println(hier.name.name+"--1->"+trtest.name);
			    Editor.dislocation();}
		//	  System.out.println("AND>"+matrix.prev);
		//	  System.out.println("AND>"+matrix.prev.name.name);
			  tr.source = new Statename(matrix.prev.name.name);} else 
			      {
				 if (hier.name.name.compareTo(trtest.name)!=0) 
				     {
				//System.out.println(hier.name.name+"--2->"+trtest.name);
				 Editor.dislocation();
				 }
				  tr.source = new Statename(hier.name.name);}
		   };
	      }

if (tr.target instanceof UNDEFINED) {h.setColor( Editor.tr_color());
 
				h.fillOval(	(int) (((tr.points[trsize].x+nx)-3)*Editor.ZoomFaktor),
						(int) (((tr.points[trsize].y+ny)-3)*Editor.ZoomFaktor),
						(int) (6*Editor.ZoomFaktor),(int) (6*Editor.ZoomFaktor));} 
	      {localobject = PESTdrawutil.getSmallObject(root,tr.points[trsize].x+nx,tr.points[trsize].y+ny);
	      // System.out.println("aktueller TRAnchor target :"+localobject);
		   if (localobject instanceof State) {
		       State hier = (State) localobject;
			if (hier.rect != null)
			{
		       Statematrix matrix = PESTdrawutil.getState(root,tr.points[trsize].x+nx,tr.points[trsize].y+ny);
		           Statename trtest2 = (Statename)tr.target;


// System.out.println("AKT2>"+matrix.akt);
// System.out.println("PREV2>"+matrix.prev);
 // System.out.println("AKTRECT2>"+matrix.akt.rect);
// System.out.println("AKTRECT2>"+matrix.prev.rect);

if (matrix.akt.rect != null)
{matrix = PESTdrawutil.getState(root,matrix.x-matrix.akt.rect.x,matrix.y-matrix.akt.rect.y);}


		       if (matrix.prev instanceof And_State) {
			//  System.out.println("AND>"+matrix.prev);
			 // System.out.println("AND>"+matrix.prev.name.name);
			       if (hier.name.name.compareTo(trtest2.name)!=0 &
				matrix.prev.name.name.compareTo(trtest2.name) != 0) 
				  {
			//	System.out.println(hier.name.name+"--3->"+trtest2.name);
			      Editor.dislocation();}
			  tr.target = new Statename(matrix.prev.name.name);} else 
			      {
				  if (hier.name.name.compareTo(trtest2.name)!=0) {
			// System.out.println(hier.name.name+"--4->"+trtest2.name);
				  Editor.dislocation();}
				  tr.target = new Statename(hier.name.name);}
			}
		   };
	      }

// erst nur Zweipunktzeichnen
	}

    private void redraw(Basic_State bs, int nx, int ny, boolean drawflag) {
	int neux = nx;
	int neuy = ny;
	if (bs.rect != null) 
	    {         	Repaint re = new Repaint();
		re.start(bs.name,neux+bs.rect.x,neuy+bs.rect.y,true);

		if (drawflag == false)
		{
		    drawPESTParallel.drawParallel(h, (int) ((bs.rect.x+neux) * Editor.ZoomFaktor),
						(int) ((bs.rect.y+neuy) * Editor.ZoomFaktor),
						(int) (bs.rect.width* Editor.ZoomFaktor),
						(int) (bs.rect.height* Editor.ZoomFaktor),
						 Editor.st_color());
		}
		else {h.setColor( Editor.st_color());h.drawRect((int) ((bs.rect.x+neux) * Editor.ZoomFaktor),
							(int) ((bs.rect.y+neuy) * Editor.ZoomFaktor),
							(int) (bs.rect.width* Editor.ZoomFaktor),
							(int) (bs.rect.height* Editor.ZoomFaktor)

					);}
		if (bs instanceof Ref_State)
		    {
			Ref_State rstate = (Ref_State) bs;
			h.setColor( Editor.st_color());h.drawString("@"+rstate.filename,
								    (int) ((bs.rect.x+neux) * Editor.ZoomFaktor+8),
								    (int) ((bs.rect.y+neuy+bs.rect.height/2) * Editor.ZoomFaktor));
		    }
	    }
	// die Statename folgt spaeter
    }

    private void redraw(Or_State os, int nx, int ny, boolean drawflag) {
	int neux = nx;
	int neuy = ny;
	StateList templist;
	TrList trlist;
	StatenameList tempstatelist = os.defaults;
	templist = os.substates;
	ConnectorList colist;
	colist = os.connectors;
	trlist = os.trs;

	if (os.rect != null)
	    {         

		if (drawflag == false) 
		    {
			drawPESTParallel.drawParallel(h,(int) ((os.rect.x+neux) * Editor.ZoomFaktor),
						(int) ((os.rect.y+neuy) * Editor.ZoomFaktor),
						(int) (os.rect.width* Editor.ZoomFaktor),
						(int) (os.rect.height* Editor.ZoomFaktor),
						 Editor.st_color());
		    }
		else 
		    {
		    h.setColor( Editor.st_color());h.drawRect((int) ((os.rect.x+neux) * Editor.ZoomFaktor),
							(int) ((os.rect.y+neuy) * Editor.ZoomFaktor),
							(int) (os.rect.width* Editor.ZoomFaktor),
							(int) (os.rect.height* Editor.ZoomFaktor)

		);
		}
	//	h.drawString( os.name.name,neux,neuy);

		neux = neux + os.rect.x;
		neuy = neuy + os.rect.y;
		 Repaint re = new Repaint();
		re.start(os.name,neux,neuy,true);
	    }

	while (templist != null)
	    {
		Repaint re = new Repaint();
		re.start(templist.head,neux,neuy,true);
		templist = templist.tail;
	    }

	while (trlist != null)
	    {
		Repaint re = new Repaint();
		re.start(trlist.head,neux,neuy,true);
		trlist = trlist.tail;
	    }

	while (colist != null)
	    {
		if (colist.head.position != null) 
		{
		h.setColor( Editor.con_color());
		h.fillOval((int) ((colist.head.position.x+neux)*Editor.ZoomFaktor),
			(int) ((colist.head.position.y+neuy)*Editor.ZoomFaktor),
			(int) (12*Editor.ZoomFaktor),
			(int) (12*Editor.ZoomFaktor)
			);
		}
		colist = colist.tail;
	    }

	while (tempstatelist != null)
	   {
	templist = os.substates;	
	while (templist != null)
	    {
		//System.out.println("Default : "+templist.head.name.name+"|"+tempstatelist.head.name);

	                if (templist.head.name.name.compareTo(tempstatelist.head.name) == 0) {
		
		h.setColor( Editor.st_color());
		h.drawRect((int) ((templist.head.rect.x+neux)*Editor.ZoomFaktor)+2,
			(int) ((templist.head.rect.y+neuy)*Editor.ZoomFaktor)+2,
			(int) ((templist.head.rect.width)*Editor.ZoomFaktor)-4,
			(int) ((templist.head.rect.height)*Editor.ZoomFaktor)-4
			);
		h.drawRect((int) ((templist.head.rect.x+neux)*Editor.ZoomFaktor)+4,
			(int) ((templist.head.rect.y+neuy)*Editor.ZoomFaktor)+4,
			(int) ((templist.head.rect.width)*Editor.ZoomFaktor)-8,
			(int) ((templist.head.rect.height)*Editor.ZoomFaktor)-8
			);

		}
		templist = templist.tail;
	    }



	    tempstatelist = tempstatelist.tail;
	   }


	// die Listen fuer tr,co folgen spaeter
    }

 private void redraw(And_State as, int nx, int ny, boolean drawflag) {
	int neux = nx;
	int neuy = ny;
	StateList templist;
	
	templist = as.substates;
	
	if (as.rect != null & drawflag)
	    {
		h.setColor( Editor.st_color());
	//	h.drawRect(as.rect.x+neux,as.rect.y+neuy,as.rect.width,as.rect.height);
	
		h.drawRect((int) ((as.rect.x+neux) * Editor.ZoomFaktor),
				(int) ((as.rect.y+neuy) * Editor.ZoomFaktor),
				(int) (as.rect.width* Editor.ZoomFaktor),
				(int) (as.rect.height* Editor.ZoomFaktor));



		neux = neux + as.rect.x;
		neuy = neuy + as.rect.y;

		Repaint re = new Repaint();
		re.start(as.name,neux,neuy,false);

		//Repaint re = new Repaint();
		//re.start(as.name,neux,neuy-18,true);
	
	    }

	while (templist != null)
	    {
		Repaint re = new Repaint();
		re.start(templist.head,neux,neuy,false);
		templist = templist.tail;
	    }

	
	// die Listen 
    }
}
