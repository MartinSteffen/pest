
package editor.desk;

import absyn.*;
import java.awt.*;
import editor.*;

public class HighObj {

  static Graphics h;
   
    static Color def_state = Color.green;
    static Color def_conn = Color.red;
    static Color def_text = Color.black;
    
  public HighObj (Graphics g) {h = g;};
   
    
  public HighObj () {}


    public void start (Absyn ab,int nx, int ny, boolean drawflag,Absyn na,Color col) {
	int neux = nx;
	int neuy = ny;
	if (ab instanceof Statechart) redraw((Statechart) ab,neux,neuy,drawflag,na,col);
	if (ab instanceof Basic_State) redraw((Basic_State) ab,neux,neuy,drawflag,na,col);
	if (ab instanceof Or_State) redraw((Or_State) ab,neux,neuy,drawflag,na,col);
	if (ab instanceof And_State) redraw((And_State) ab,neux,neuy,drawflag,na,col);
	if (ab instanceof Statename) redraw((Statename) ab,neux,neuy,drawflag,na,col);
	if (ab instanceof Tr) redraw((Tr) ab,neux,neuy,drawflag,na,col);
	if (ab instanceof Connector) redraw((Connector) ab,neux,neuy,drawflag,na,col);
    }

    private void redraw(Connector co, int nx, int ny, boolean drawflag,Absyn na,Color col) {
	int neux = nx;
	int neuy = ny;
	System.out.println("Connector zeichnen");

	if (co == na) { 
			h.setColor(col);
			h.fillOval((int) ((co.position.x+neux)*Editor.ZoomFaktor),
			(int) ((co.position.y+neuy)*Editor.ZoomFaktor),
			(int) (12*Editor.ZoomFaktor),
			(int) (12*Editor.ZoomFaktor)
			); System.out.println("Connector gefunden");
			}
	// die anderen folgen spaeter
    }

 
    private void redraw(Statechart sc, int nx, int ny, boolean drawflag,Absyn na,Color col) {
	int neux = nx;
	int neuy = ny;
	if (sc.state != null) { HighObj re = new HighObj(); re.start(sc.state,neux,neuy,true,na,col);}
	// die anderen folgen spaeter
    }

   private void redraw(Statename sn,int nx, int ny, boolean drawflag,Absyn na, Color col) {
	 h.setColor(def_text);h.drawString( sn.name,
			(int) ((nx*Editor.ZoomFaktor)+4),
			(int) ((ny*Editor.ZoomFaktor)+12)
		);
	}



    private void redraw(Basic_State bs, int nx, int ny, boolean drawflag, Absyn na, Color col) {
	int neux = nx;
	int neuy = ny;
	if (bs.rect != null & bs == na) 
	    {      
		if (drawflag == false)
		{
		    drawPESTParallel.drawParallel(h, (int) ((bs.rect.x+neux) * Editor.ZoomFaktor),
						(int) ((bs.rect.y+neuy) * Editor.ZoomFaktor),
						(int) (bs.rect.width* Editor.ZoomFaktor),
						(int) (bs.rect.height* Editor.ZoomFaktor),
						col);
		}
		else {h.setColor(col);h.drawRect((int) ((bs.rect.x+neux) * Editor.ZoomFaktor),
							(int) ((bs.rect.y+neuy) * Editor.ZoomFaktor),
							(int) (bs.rect.width* Editor.ZoomFaktor),
							(int) (bs.rect.height* Editor.ZoomFaktor)

					);}
	    }
	// die Statename folgt spaeter
    }

    private void redraw(Or_State os, int nx, int ny, boolean drawflag, Absyn na,Color col) {
	int neux = nx;
	int neuy = ny;
	StateList templist;
	templist = os.substates;
	ConnectorList colist;
	TrList temptrlist;
	colist = os.connectors;
	temptrlist = os.trs;

	if (os.rect != null & os == na)
	    {         

		if (drawflag == false) 
		    {
			drawPESTParallel.drawParallel(h,(int) ((os.rect.x+neux) * Editor.ZoomFaktor),
						(int) ((os.rect.y+neuy) * Editor.ZoomFaktor),
						(int) (os.rect.width* Editor.ZoomFaktor),
						(int) (os.rect.height* Editor.ZoomFaktor),
						col);
		    }
		else 
		    {
		    h.setColor(col);h.drawRect((int) ((os.rect.x+neux) * Editor.ZoomFaktor),
							(int) ((os.rect.y+neuy) * Editor.ZoomFaktor),
							(int) (os.rect.width* Editor.ZoomFaktor),
							(int) (os.rect.height* Editor.ZoomFaktor)

		);
		}
	//	h.drawString( os.name.name,neux,neuy);

		    }

		if (os.rect != null)
		{neux = neux + os.rect.x;
		neuy = neuy + os.rect.y;}
		



	while (templist != null)
	    {
		HighObj re = new HighObj();
		re.start(templist.head,neux,neuy,true,na,col);
		templist = templist.tail;
	    }

	while (colist != null)
	    {
		if (colist.head.position != null) 
		{
		HighObj re = new HighObj();
		re.start(colist.head,neux,neuy,true,na,col);
		}
		colist = colist.tail;
	    }
	while (temptrlist != null)
	    {
		if (temptrlist.head != null) 
		{
		HighObj re = new HighObj();
		re.start(temptrlist.head,neux,neuy,true,na,col);
		}
		temptrlist = temptrlist.tail;
	    }


	// die Listen fuer tr,co folgen spaeter
    }

 private void redraw(And_State as, int nx, int ny, boolean drawflag, Absyn na, Color col) {
	int neux = nx;
	int neuy = ny;
	StateList templist;
	
	templist = as.substates;
	
	if (as.rect != null & drawflag & as == na)
	    {
		h.setColor(col);
	//	h.drawRect(as.rect.x+neux,as.rect.y+neuy,as.rect.width,as.rect.height);
	
		h.drawRect((int) ((as.rect.x+neux) * Editor.ZoomFaktor),
				(int) ((as.rect.y+neuy) * Editor.ZoomFaktor),
				(int) (as.rect.width* Editor.ZoomFaktor),
				(int) (as.rect.height* Editor.ZoomFaktor));
	    }

		if (as.rect != null)
		{neux = neux + as.rect.x;
		neuy = neuy + as.rect.y;}


	while (templist != null)
	    {
		HighObj re = new HighObj();
		re.start(templist.head,neux,neuy,false,na,col);
		templist = templist.tail;
	    }

	
	// die Listen 
    }

private void redraw(Tr tr,int nx, int ny, boolean drawflag,Absyn na,Color col) {
	 if (tr == na){ 
	int trsize = tr.points.length-1;
	System.out.println("Anzahl ZeigerPunkte : "+(int) (trsize+1));
	//for (int lauf = 0;lauf < (trsize-1);lauf++) {h.setColor(col);h.drawLine(	(int) ((tr.points[lauf].x+nx)*Editor.ZoomFaktor),
	//								(int) ((tr.points[lauf].y+ny)*Editor.ZoomFaktor),
	//								(int) ((tr.points[lauf+1].x+nx)*Editor.ZoomFaktor),
	//								(int) ((tr.points[lauf+1].y+ny)*Editor.ZoomFaktor) )
	//								;}

				if (tr.source instanceof UNDEFINED) {h.setColor(col);
				h.fillOval(	(int) (((tr.points[0].x+nx)-3)*Editor.ZoomFaktor),
						(int) (((tr.points[0].y+ny)-3)*Editor.ZoomFaktor),
						6,6);}



	//			drawPESTTrans.drawTrans(h,
	//			(int) ((tr.points[trsize-1].x+nx)*Editor.ZoomFaktor),
	//			(int) ((tr.points[trsize-1].y+ny)*Editor.ZoomFaktor),
	//			(int) ((tr.points[trsize-0].x+nx)*Editor.ZoomFaktor),
	//			(int) ((tr.points[trsize-0].y+ny)*Editor.ZoomFaktor),
	//			tr.source,
	//			tr.target,
	//			col );
	
	drawPESTTrans.BTrans(h,tr,nx,ny,col);}

// erst nur Zweipunktzeichnen
	}
}
