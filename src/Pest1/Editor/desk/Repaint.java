
package editor.desk;

import absyn.*;
import java.awt.*;
import editor.*;
import java.lang.*;

public class Repaint {

  static Graphics h;
   
    static Color def_state = Color.green;
    static Color def_conn = Color.red;
    static Color def_text = Color.black;
    static Color def_tr = Color.magenta;

   String tempstring;
    
  public Repaint (Graphics g) {h = g;};
   
    
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
	if (sc.state != null) { Repaint re = new Repaint(); re.start(sc.state,neux,neuy,true);}
	// die anderen folgen spaeter
    }

   private void redraw(Statename sn,int nx, int ny, boolean drawflag) {
   boolean test = true;
		if (sn.name.length() >= 3) {
		tempstring = sn.name.substring(0,3);
		System.out.println("<<"+tempstring+">>");
		if (tempstring.compareTo("...")==0) test = false;}
 
		if (test == true)
		{
	 	h.setColor(def_text);h.drawString( sn.name,
			(int) ((nx*Editor.ZoomFaktor)+4),
			(int) ((ny*Editor.ZoomFaktor)+12));
		}
	}

private void redraw(Tr tr,int nx, int ny, boolean drawflag) {
int trsize = tr.points.length-1;
	System.out.println("Anzahl ZeigerPunkte : "+(int) (trsize+1));
	for (int lauf = 0;lauf < (trsize-1);lauf++) {h.setColor(def_tr);h.drawLine(	(int) ((tr.points[lauf].x+nx)*Editor.ZoomFaktor),
									(int) ((tr.points[lauf].y+ny)*Editor.ZoomFaktor),
									(int) ((tr.points[lauf+1].x+nx)*Editor.ZoomFaktor),
									(int) ((tr.points[lauf+1].y+ny)*Editor.ZoomFaktor) )
									;}

if (tr.source instanceof UNDEFINED) {h.setColor(def_tr);
				h.fillOval(	(int) (((tr.points[0].x+nx)-3)*Editor.ZoomFaktor),
						(int) (((tr.points[0].y+ny)-3)*Editor.ZoomFaktor),
						6,6);}


	 drawPESTTrans.drawTrans(h,
				(int) ((tr.points[trsize-1].x+nx)*Editor.ZoomFaktor),
				(int) ((tr.points[trsize-1].y+ny)*Editor.ZoomFaktor),
				(int) ((tr.points[trsize].x+nx)*Editor.ZoomFaktor),
				(int) ((tr.points[trsize].y+ny)*Editor.ZoomFaktor),
				tr.source,
				tr.target,
				def_tr );


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
						def_state);
		}
		else {h.setColor(def_state);h.drawRect((int) ((bs.rect.x+neux) * Editor.ZoomFaktor),
							(int) ((bs.rect.y+neuy) * Editor.ZoomFaktor),
							(int) (bs.rect.width* Editor.ZoomFaktor),
							(int) (bs.rect.height* Editor.ZoomFaktor)

					);}
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
						def_state);
		    }
		else 
		    {
		    h.setColor(def_state);h.drawRect((int) ((os.rect.x+neux) * Editor.ZoomFaktor),
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
		h.setColor(def_conn);
		h.fillOval((int) ((colist.head.position.x+neux)*Editor.ZoomFaktor),
			(int) ((colist.head.position.y+neuy)*Editor.ZoomFaktor),
			(int) (12),
			(int) (12)
			);
		}
		colist = colist.tail;
	    }

	while (tempstatelist != null)
	   {
	templist = os.substates;	
	while (templist != null)
	    {
	                if (templist.head.name == tempstatelist.head) {
		System.out.println("Default : "+templist.head.name);
		h.setColor(def_state);
		// h.fillOval((int) ((templist.head.rect.x+neux+(templist.head.rect.width / 2)-10)*Editor.ZoomFaktor),
		//	(int) ((templist.head.rect.y+neuy+(templist.head.rect.height / 2)-10)*Editor.ZoomFaktor),
		//	(int) (20),
		//	(int) (20)
		//	);
		h.setColor(def_state);
		h.drawRect((int) ((templist.head.rect.x+neux)*Editor.ZoomFaktor)+2,
			   (int) ((templist.head.rect.y+neuy)*Editor.ZoomFaktor)+2,
			   (int) ((templist.head.rect.width)*Editor.ZoomFaktor)-4,
			   (int) ((templist.head.rect.height)*Editor.ZoomFaktor)-4 );
		h.drawRect((int) ((templist.head.rect.x+neux)*Editor.ZoomFaktor)+4,
			   (int) ((templist.head.rect.y+neuy)*Editor.ZoomFaktor)+4,
			   (int) ((templist.head.rect.width)*Editor.ZoomFaktor)-8,
			   (int) ((templist.head.rect.height)*Editor.ZoomFaktor)-8 );
		

	
		}
		templist = templist.tail;
	    }



	    tempstatelist = tempstatelist.tail;
	   }
    }

 private void redraw(And_State as, int nx, int ny, boolean drawflag) {
	int neux = nx;
	int neuy = ny;
	StateList templist;
	
	templist = as.substates;
	
	if (as.rect != null & drawflag)
	    {
		h.setColor(def_state);
	//	h.drawRect(as.rect.x+neux,as.rect.y+neuy,as.rect.width,as.rect.height);
	
		h.drawRect((int) ((as.rect.x+neux) * Editor.ZoomFaktor),
				(int) ((as.rect.y+neuy) * Editor.ZoomFaktor),
				(int) (as.rect.width* Editor.ZoomFaktor),
				(int) (as.rect.height* Editor.ZoomFaktor));



		neux = neux + as.rect.x;
		neuy = neuy + as.rect.y;

		Repaint re = new Repaint();
		re.start(as.name,neux,neuy-18,true);

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
