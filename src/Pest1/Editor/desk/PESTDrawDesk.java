/**
 * PESTDrawDesk.java
 *
 *
 * Created: Thu Nov  5 11:52:16 1998
 *
 * @author Software Technologie 24
 * @version 1.0
 */

package editor.desk;

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;
import java.awt.Graphics.*;         
import java.io.*;                
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties;     
import absyn.*;
import editor.*;
import java.applet.*;
import tesc2.*;


 /* Hauptzeichenfeld (Voreinstellungen & Definitionen) */
  public class PESTDrawDesk extends Component implements ActionListener {

      protected int last_x, last_y,old_x,old_y;                		// letzter Klick
      protected Color current_color = Color.black;   	        // aktueller Farbe
      protected int width, height;                   		// Groessenvariablen
      protected PopupMenu popup;                     		// pop-up Menue
      public static Panel xpanel,dpanel;                        		// Menuefenster

      static Storelist anf;
      static Storelist lauf;
      static Storelist basis;
      static Absyn aktcomp = null;
      static boolean trroot = false;
      static Point[] tempwaypoint = new Point[100];
      static int laufwaypoint = 0;
      static Absyn deleteobj,copyobj;
      static Statematrix aktmatrix = null,aktmatrix2 = null;
      static State tempstate1=null;
      static Dimension dim;
      static tempobj ttobj = null;
      //  static TextArea ta = new TextArea(10,10);
      //static LabelArea textlabel = new LabelArea(20,20);
      //Image buffer;
      Graphics bufferGraphics;  
      
     Statechart root= new Statechart(null,null,null,null);

  /** Initialisierung des Hauptframes*/

      public PESTDrawDesk(Panel panel, int width, int height,Statechart nroot) {

      
           new newStorelist();
	  root = nroot;
    dpanel = panel;
    dpanel.setSize(width,height);
    dim = dpanel.getSize();
    this.xpanel = dpanel;
    this.width = dim.width;
    this.height = dim.height;
    this.xpanel.setForeground(Color.black);
    bufferGraphics = xpanel.getGraphics();

    // low-level Implementierungen
    this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

    // Erzeugung der pop-up Menues
    String[] labels = new String[] {
      "Undo", "Restore", "Loeschen","Kopieren"};
    String[] commands = new String[] {
      "undo", "restore", "loeschen","kopieren"};
    popup = new PopupMenu();                   		// Menueerzeugung
    for(int i = 0; i < labels.length; i++) {
      MenuItem mi = new MenuItem(labels[i]);   	// erzeugt Menueeintrag
      mi.setActionCommand(commands[i]);        	// actioncommand.
      mi.addActionListener(this);              		// actionlistener.
      popup.add(mi);                           			// pop-up Menue aufruf
    }
    // Registrierung von pop-up
    this.add(popup);
    // this.show();


  }

  public Dimension getPreferredSize() { return new Dimension(width, height); }

  // pop-up Menueabfrage
 // pop-up Menueabfrage 
  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
	Editor.Buttontype = "Select";
    if (command.equals("undo")) {root = undo();Editor.newdraw();}
    else if (command.equals("restore")) {root = redo();Editor.newdraw();}
    else if (command.equals("kopieren")) {	System.out.println("test2 : "+copyobj);
					try     {copyobj = (Absyn) deleteobj.clone();}
				        	catch (Exception e) {System.out.println("Clone-Fehler");}
					System.out.println("test2 : "+copyobj);
	}	
    else if (command.equals("loeschen")){ 
       
	    // ********************
    if (aktcomp instanceof State) {

        DeleteChart dc = new DeleteChart ( root );
        State todelete = (State)aktcomp;
        State father = dc.get_father(root.state, todelete);

	//    System.out.println("Zu l�schender State: "+ todelete);
	// System.out.println("Vater "+ father);

        dc.delete_State( todelete );

        repaint();


	    }

      if (aktcomp instanceof Tr) {

        DeleteChart dc = new DeleteChart ( root );
        Tr todelete = (Tr)aktcomp;
        Or_State father = dc.find_trans(todelete);

	//      System.out.println("Zu l�schende Transition: "+ todelete);
	// System.out.println("Zugeh�riger State: "+ father);

        dc.delete_Trans( todelete );

        repaint();

    }

    if (aktcomp instanceof Connector) {

        DeleteChart dc = new DeleteChart ( root );
        Connector todelete = (Connector)aktcomp;
        Or_State father = dc.find_con(todelete);

	//    System.out.println("Zu l�schende Transition: "+ todelete);
	// System.out.println("Zugeh�riger State: "+ father);

        dc.delete_Con( todelete );

        repaint();

    }
	    // ********************
    Editor.SetListen();
    }                                      
    
  }
 

  // Grafik herstellen
      public void paint(Graphics g) { 
	  //System.out.println("repaint gestartet");
	  	new Repaint(g,root);
		Repaint r = new Repaint(); 
		r.start(root,0,0,true);
		new highlightObject(g,root);
		//System.out.println(Editor.init);  
		Editor.init = false;
		//System.out.println(Editor.init);  
   		//	System.out.println("repaint beendet");  
  }
  
    public void restore(Graphics g) {
		new Repaint(g,root);
		Repaint r = new Repaint(); 
		r.start(root,0,0,true);
	}


   // Ueberwachung der Mausaktivitaeten in Abhaengigkeit der Zeichenfunktionen

public void processMouseMotionEvent(MouseEvent e)
{Graphics g = getGraphics();
  Absyn aktcomp2;
  
if (e.getID() == MouseEvent.MOUSE_MOVED & (Editor.Editor() == "Select" | Editor.Editor() == "Info" ))
      {
	if (copyobj == null)
	{
	aktcomp2 = PESTdrawutil.getSmallObject(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor)) ; 
	if (aktcomp2 != aktcomp) 
	{
		aktcomp = PESTdrawutil.getSmallObject(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor));
	//	System.out.println("Akt. Komponente : "+aktcomp);
		new highlightObject(true);
		new highlightObject(aktcomp,Color.black);
		new highlightObject();
		// textlabel.hide();
	        }
	} else
	{
	   if (copyobj instanceof State)
	     {
		State tempdrstate = (State) copyobj;
	//	g.setColor(this.getBackground());
	//	g.drawRect(last_x,last_y,tempdrstate.rect.width,tempdrstate.rect.height);
	//	restore(g);
	//	g.setColor(Color.black);
	//	g.drawRect(e.getX(),e.getY(),tempdrstate.rect.width,tempdrstate.rect.height);
	//	last_x = e.getX();
	//	last_y = e.getY();

 	bufferGraphics = this.getGraphics();
		  
		    bufferGraphics.setColor(this.getBackground());
		    bufferGraphics.drawRect(last_x,last_y,tempdrstate.rect.width,tempdrstate.rect.height);


		    bufferGraphics.setColor(Color.black);
		    bufferGraphics.drawRect(e.getX(),e.getY(),tempdrstate.rect.width,tempdrstate.rect.height);
		last_x = e.getX();
		last_y = e.getY();

		    restore(g);

	     }
	}
       }

if (e.getID() == MouseEvent.MOUSE_MOVED & Editor.Editor() == "Draw_StateLabel")
      {
	if (PESTdrawutil.getSmallObject(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor)) != aktcomp) 
	{
		aktmatrix = PESTdrawutil.getState(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor));
		aktmatrix2 = PESTdrawutil.getState(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor)+10);
		if (aktmatrix.akt != aktmatrix2.akt & aktmatrix2.prev instanceof And_State & aktmatrix.prev != aktmatrix2.akt) 
			{aktmatrix.akt = aktmatrix2.prev;}
		// System.out.println("Akt. Komponente : "+aktmatrix.akt);
		if (tempstate1 != aktmatrix.akt)
		{
		new highlightObject(true);
		new highlightObject(aktmatrix.akt,Color.black);
		new highlightObject();
		tempstate1 = aktmatrix.akt;
		}
        	}
      }

if (e.getID() == MouseEvent.MOUSE_MOVED & Editor.Editor() == "Draw_TransLabel")
      {
	if (PESTdrawutil.getSmallObject(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor)) != aktcomp) 
	{
		aktcomp = PESTdrawutil.getSmallObject(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor));
	//	System.out.println("Akt. Komponente : "+aktcomp);
		if (aktcomp instanceof Tr)
		{
		new highlightObject(true);
		new highlightObject(aktcomp,Color.black);
		new highlightObject();
		} else {	new highlightObject(true);}
		repaint();
        	} 
      }

if ((e.getID() == MouseEvent.MOUSE_DRAGGED) & (Editor.Editor() =="Draw_State"))
		{
		//new Repaint(g,root);
		//Repaint r = new Repaint(); 
		//r.start(root,0,0,true); 
		   	bufferGraphics = this.getGraphics();
		  
		    bufferGraphics.setColor(this.getBackground());
		    bufferGraphics.drawRect((int) (last_x),
		  	       (int) (last_y),
		  	       (int) (old_x),
		  	       (int) (old_y));

		    bufferGraphics.setColor(Color.black);
		    bufferGraphics.drawRect((int) (last_x),
		  	       (int) (last_y),
		  	       (int) ((e.getX()-last_x)),
		  	       (int) ((e.getY()-last_y)));
		      
		    old_x = e.getX()-last_x;
		    old_y = e.getY()-last_y;

		    restore(g);
		}

if ((e.getID() == MouseEvent.MOUSE_DRAGGED) & (Editor.Editor() =="Draw_Par"))
		{// Editor.SetListen();
		    int px2,py2;
		    
		    if (Math.abs(last_x-e.getX()) < Math.abs(last_y-e.getY()))
			{
			    px2 = last_x;
			    py2 = e.getY();
			} else
			    {
				px2 = e.getX();
				py2 = last_y;
			    }

		    //repaint(last_x,last_y,old_x,old_y);
		    // g.setColor(Color.black);
		    // g.drawLine((int) (last_x/Editor.ZoomFaktor),
		    //      (int) (last_y/Editor.ZoomFaktor),

		    //    (int) ((px2)/Editor.ZoomFaktor),
		    //    (int) ((py2)/Editor.ZoomFaktor));



		    ttobj = new tempobj("pa",
					  (int) (last_x),
					  (int) (last_y),
					  (int) (px2),
					  (int) (py2));
		       
		    //     System.out.println("boxdraw beendet");
		       repaint(last_x,last_y,old_x,old_y);
		       //   repaint();

		    old_x = e.getX()-last_x+1;
		    old_y = e.getY()-last_y+1;
		    //  Editor.SetListen();trroot = false;

		}



if (e.getID() == MouseEvent.MOUSE_MOVED & Editor.Editor() == "Draw_Trans" & trroot == true)
      {
	  //aktcomp = PESTdrawutil.getSmallObject(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor));
		//new Repaint(g,root);
		//Repaint r = new Repaint(); 
		//r.start(root,0,0,true); 
		
	  bufferGraphics = this.getGraphics();		  
	  bufferGraphics.setColor(this.getBackground());
	  int tj = 0;
	  while (tempwaypoint[tj+1] != null)
	      {bufferGraphics.drawLine(tempwaypoint[tj].x,tempwaypoint[tj].y,tempwaypoint[tj+1].x,tempwaypoint[tj+1].y);
	      tj++;};
	  drawPESTTrans.drawTrans(bufferGraphics,last_x,last_y,old_x,old_y,null,null,this.getBackground());
  //bufferGraphics.drawLine(last_x,last_y,e.getX(),e.getY());
  restore(g);
	  tj = 0;
	  bufferGraphics.setColor(Color.black);
	  	  while (tempwaypoint[tj+1] != null)
	  	      {bufferGraphics.drawLine(tempwaypoint[tj].x,tempwaypoint[tj].y,tempwaypoint[tj+1].x,tempwaypoint[tj+1].y);
	  	      tj++;};
			  
	 drawPESTTrans.drawTrans(bufferGraphics,last_x,last_y,e.getX(),e.getY(),null,null,Color.black);
		
		old_x = e.getX();
		old_y = e.getY();
      }

}
   
  public void processMouseEvent(MouseEvent e) {
	String locstring="";
	int state1;
	int state2;
	int trx1;
	int try1;
	int trx2 = 0;
	int try2 = 0;
	int dx;
	int dy;
	short ttemp;
	double d1,d2,dt;



        if (e.isPopupTrigger())                               // If popup trigger,
      popup.show(this, e.getX(), e.getY());                   // pop up the menu.

	else
	    {
	    Graphics g = getGraphics();
	
	    if (e.getID() == MouseEvent.MOUSE_PRESSED & Editor.Editor() != "Draw_Trans" & Editor.Editor() != "Draw_Conn"  &
		Editor.Editor() != "Draw_StateLabel")
		{
		    last_x = (short) e.getX(); last_y = (short) e.getY(); 	// Save position.
		}

	   if (e.getID() == MouseEvent.MOUSE_PRESSED & Editor.Editor() == "Draw_Trans")
		{
		if (trroot == false) {trroot = true;last_x = (short) e.getX(); last_y = (short) e.getY();
		       laufwaypoint = 0; 
		       tempwaypoint = new Point[100];
		       tempwaypoint[0] = new Point ((int) (last_x /Editor.ZoomFaktor), (int) (last_y/Editor.ZoomFaktor) );
		}
		else {
			if (e.getClickCount() < 2)
			{
			    laufwaypoint++;
			//g.setColor(Editor.tr_color());
			//g.drawLine(last_x,last_y,e.getX(),e.getY());
			drawPESTTrans.drawTrans(bufferGraphics,last_x,last_y,e.getX(),e.getY(),null,null,this.getBackground());
			g.setColor(Color.black);g.drawLine(last_x,last_y,e.getX(),e.getY());
			last_x = (short) e.getX(); last_y = (short) e.getY();
			
			tempwaypoint[laufwaypoint] = new Point((int) (last_x/Editor.ZoomFaktor),(int) (last_y/Editor.ZoomFaktor));
			} else
			{
			if (laufwaypoint > 0)
			{
		//	System.out.println("Pfeilspitze");
			TrAnchor an1 = null;
			TrAnchor an2 = null;
			//drawPESTTrans.drawTrans(g,tempwaypoint[laufwaypoint-1].x
			//			,tempwaypoint[laufwaypoint-1].y
			//			,tempwaypoint[laufwaypoint].x
			//			,tempwaypoint[laufwaypoint].y
			//			,an1,an2,Editor.tr_color());
			new drawPESTTrans(g,root,tempwaypoint,laufwaypoint,Color.magenta);
			Editor.SetListen();trroot = false;
			repaint();
			} else {trroot = false;}
			}
		}
	//	System.out.println("Waylist :"+tempwaypoint+"    lauf : "+laufwaypoint);
	    }

	    if ((e.getID() == MouseEvent.MOUSE_PRESSED) & (Editor.Editor() =="Draw_StateLabel"))
		{ 
		    // Editor.SetListen();
		    new labelPESTState(root,(int) (e.getX()/Editor.ZoomFaktor),
					     (int) (e.getY()/Editor.ZoomFaktor));
		   Editor.SetListen(); trroot = false;
		   repaint();
		}

	if ((e.getID() == MouseEvent.MOUSE_PRESSED) & (Editor.Editor() =="Draw_TransLabel"))
		{ 
		    Absyn akttrans;
		    akttrans = PESTdrawutil.getSmallObject(root,(int) (e.getX()/Editor.ZoomFaktor),(int) (e.getY()/Editor.ZoomFaktor)); 
		   if (akttrans instanceof Tr)
		       {//Editor.SetListen();
		    new labelPESTTrans(root,(int) (e.getX()/Editor.ZoomFaktor),
		 		                 (int) (e.getY()/Editor.ZoomFaktor),(Tr) akttrans);
		    Editor.SetListen();trroot = false;
		new highlightObject();
		   repaint();
		  } else {	new highlightObject(true);repaint();}

		}

	    if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_State"))
		{// Editor.SetListen();
		       repaint();
		    new drawPESTState(g,root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor),
					     (int) (e.getX()/Editor.ZoomFaktor),
					     (int) (e.getY()/Editor.ZoomFaktor),
						Editor.st_color());
		    Editor.SetListen();trroot = false;

		}
	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_Par"))
	    {// Editor.SetListen();
		    new drawPESTParallel(g,root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor),
					     (int) (e.getX()/Editor.ZoomFaktor),
					     (int) (e.getY()/Editor.ZoomFaktor),
					     Editor.st_color());
		    Editor.SetListen();trroot = false;
		    repaint();
		}

	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Set_Default"))
	    { // Editor.SetListen();
		    new setDefault(g,root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor),
					     Editor.tr_color());
		    Editor.SetListen();trroot = false;

		    repaint();
		}

	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Select") & (copyobj == null))
		{ 
		    Absyn test = PESTdrawutil.getSmallObject(root,
			(int) (last_x/Editor.ZoomFaktor),
			(int) (last_y/Editor.ZoomFaktor)
				);
		deleteobj = test;
		// System.out.println("to delete >>>"+deleteobj);
		}

	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Select") & (copyobj != null))
		{ 
		   copyobj = null;
		}



if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Info"))
		{ 
		    Absyn test = PESTdrawutil.getSmallObject(root,
			(int) (last_x/Editor.ZoomFaktor),
			(int) (last_y/Editor.ZoomFaktor)
				);

			if (test instanceof Tr)
			{
			Tr trtmp = (Tr) test;
			  {
			      //repaint();
			      //textlabel.text(trtmp.label.caption);
			      //textlabel.show(e.getX(),e.getY());
			      if (trtmp.label.caption.length() >= GraphOptimizer.TLABELLENGTH)
			      {
			      new LabelArea(e.getX(),e.getY(),trtmp.label.caption);
			      }
			  }// else { textlabel.hide();}
			}
		}



	if ((e.getID() == MouseEvent.MOUSE_PRESSED) & (Editor.Editor() =="Draw_Conn"))
	    { //  Editor.SetListen();
		    new drawPESTConn(g,root,
			(int) (e.getX()/Editor.ZoomFaktor)-6,
			(int) (e.getY()/Editor.ZoomFaktor)-6,
			Editor.con_color());
		     Editor.SetListen();trroot = false;

		}
	}
  }
 
  // Loeschen von Bildschirm und Speicher
  void clear() {
    repaint();                   		// Bildschirm loeschen
  }


      private int schnitt(Rectangle r1,Rectangle r2) {
	  int test = 0;
	  boolean r1inr2 = false , r2inr1 = false;

	 
	  r2inr1 = r1.intersects(r2);
	  r1inr2 = r2.intersects(r1);

	  return test;
      }

      private static class Storelist{
	  Storelist next;
	  Storelist prev;
	  Statechart chart;
	  private Storelist(Storelist a, Storelist b)
	  { prev = a;
	    next = b;
	  }
      }

      private static class newStorelist
      {
	  private newStorelist()
	  {  

	      anf = new Storelist(null,null);
	      basis = anf;
	      for (int i = 1;i < 10;++i)
		  {
		    lauf = new Storelist(null,null);
		    lauf.prev = anf;
		    lauf.chart = new Statechart(null,null,null,null);
		    anf.next = lauf;
		    anf = lauf;
		  }
	      anf.next = basis;
	      basis.prev = anf;
	      lauf = basis;
	  }
      }

private static Statechart undo() { 
      	System.out.println("UNDO");
	  lauf = lauf.prev;
	//xroot.state = lauf.chart.state;
	//xroot.events = lauf.chart.events;
	//xroot.bvars = lauf.chart.bvars;
	//xroot.cnames = lauf.chart.cnames;
	//Editor.newdraw();
	return lauf.chart;
   }

private static Statechart redo() { 
   	System.out.println("REDO");
	  if (lauf != basis) {lauf = lauf.next;
	//xroot.state = lauf.chart.state;
	//xroot.events = lauf.chart.events;
	//xroot.bvars = lauf.chart.bvars;
	//xroot.cnames = lauf.chart.cnames;
	//Editor.newdraw();
	}
	return lauf.chart;
   }

      public static void addundo(Statechart nroot)
      {        Statechart root = nroot;
	 lauf = lauf.next;
	try {lauf.chart = (Statechart) root.clone();}
	catch (Exception e) {System.out.println("Waere die Absyn korrekt, so funktionierte auch das Undo !!!!");}
      }

public static Dimension sizer() {return dpanel.getSize();}

public static void resizer(int fak) {
	Dimension old = dpanel.getSize();
	dpanel.setSize(old.width*fak,old.height*(fak+1));
	dim = dpanel.getSize();
	}


      private static class tempobj {
	  String Id;
	  int x1,y1,x2,y2;
	  public tempobj(String i,int a, int b, int c,int d){
	      Id = i;
	      x1 = a;
	      y1 = b;
	      x2 = c;
	      y2 = d;
	  }
      }

} // PESTDrawDesk



