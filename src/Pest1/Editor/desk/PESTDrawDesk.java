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


 /* Hauptzeichenfeld (Voreinstellungen & Definitionen) */
  public class PESTDrawDesk extends Component implements ActionListener {

      protected short last_x, last_y;                		// letzter Klick
      protected Color current_color = Color.black;   	        // aktueller Farbe
      protected int width, height;                   		// Groessenvariablen
      protected PopupMenu popup;                     		// pop-up Menue
      public static Panel xpanel;                        		// Menuefenster

      static Storelist anf;
      static Storelist lauf;
      static Storelist basis;
      
     Statechart root= new Statechart(null,null,null,null);

  /** Initialisierung des Hauptframes*/

      public PESTDrawDesk(Panel panel, int width, int height,Statechart nroot) {
           new newStorelist();
	  root = nroot;

    this.xpanel = panel;
    this.width = width;
    this.height = height;

    // low-level Implementierungen
    this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

    // Erzeugung der pop-up Menues
    String[] labels = new String[] {
      "Undo", "Restore", "Load"};
    String[] commands = new String[] {
      "undo", "restore", "load"};
    popup = new PopupMenu();                   		// Menueerzeugung
    for(int i = 0; i < labels.length; i++) {
      MenuItem mi = new MenuItem(labels[i]);   	// erzeugt Menueeintrag
      mi.setActionCommand(commands[i]);        	// actioncommand.
      mi.addActionListener(this);              		// actionlistener.
      popup.add(mi);                           			// pop-up Menue aufruf
    }
    // Registrierung von pop-up
    this.add(popup);


  }

  public Dimension getPreferredSize() { return new Dimension(width, height); }

  // pop-up Menueabfrage
 // pop-up Menueabfrage 
  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals("undo")) undo(root);
    else if (command.equals("restore")) redo(root);
    else if (command.equals("load")) ;
    
  }
 

  // Grafik herstellen
      public void paint(Graphics g) { 	  new Repaint(g);Repaint r = new Repaint(); r.start(root,0,0,true);
				  new highlightObject(g,root);

   System.out.println("repaint gestartet");  
  }


   // Ueberwachung der Mausaktivitaeten in Abhaengigkeit der Zeichenfunktionen
   
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
	
	    if (e.getID() == MouseEvent.MOUSE_PRESSED) 
		{
		    last_x = (short) e.getX(); last_y = (short) e.getY(); 	// Save position.
		}

	    if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_StateLabel"))
		{ 
		    new labelPESTState(root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor));
		    Editor.SetListen();
		   repaint();
		}

	    if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_State"))
		{ 
		    new drawPESTState(g,root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor),
					     (int) (e.getX()/Editor.ZoomFaktor),
					     (int) (e.getY()/Editor.ZoomFaktor),
						Color.green);
		    Editor.SetListen();
		}
	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_Par"))
		{ 
		    new drawPESTParallel(g,root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor),
					     (int) (e.getX()/Editor.ZoomFaktor),
					     (int) (e.getY()/Editor.ZoomFaktor),
					     Color.green);
		    Editor.SetListen();
		}

	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_Trans"))
		{ 
		    new drawPESTTrans(g,root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor),
					     (int) (e.getX()/Editor.ZoomFaktor),
					     (int) (e.getY()/Editor.ZoomFaktor),
					     Color.magenta);
		    Editor.SetListen();
		}

	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Set_Default"))
		{ 
		    new setDefault(g,root,(int) (last_x/Editor.ZoomFaktor),
					     (int) (last_y/Editor.ZoomFaktor),
					     Color.magenta);
		    Editor.SetListen();
		    repaint();
		}



	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Select"))
		{ 
		    Absyn test = PESTdrawutil.getState(root,
			(int) (last_x/Editor.ZoomFaktor),
			(int) (last_y/Editor.ZoomFaktor)
				).akt;
		System.out.println("akt :  "+test);
		if (test instanceof Basic_State) {new highlightObject((Basic_State)test,Color.red);}
		if (test instanceof And_State) {new highlightObject((And_State)test,Color.red);}
		if (test instanceof Or_State) {new highlightObject((Or_State)test,Color.red);}
		if (test instanceof Connector) {new highlightObject((Connector)test,Color.red);}
	//	if (test instanceof Tr) {new highlightObject((Tr)test,Color.red);}
		System.out.println(">>>"+PESTdrawutil.getSmallObject(root,last_x,last_y));



		}
	if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_Conn"))
		{ 
		    new drawPESTConn(g,root,
			(int) (last_x/Editor.ZoomFaktor)-6,
			(int) (last_y/Editor.ZoomFaktor)-6,
			Color.blue);
		    Editor.SetListen(); 
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

private static void undo(Statechart xroot) { 
      	System.out.println("UNDO");
	  lauf = lauf.prev;
	xroot.state = lauf.chart.state;
	xroot.events = lauf.chart.events;
	xroot.bvars = lauf.chart.bvars;
	xroot.cnames = lauf.chart.cnames;
	Editor.newdraw();
   }

private static void redo(Statechart xroot) { 
   	System.out.println("REDO");
	  if (lauf != basis) {lauf = lauf.next;
	xroot.state = lauf.chart.state;
	xroot.events = lauf.chart.events;
	xroot.bvars = lauf.chart.bvars;
	xroot.cnames = lauf.chart.cnames;
	Editor.newdraw();}
   }

      public static void addundo(Statechart nroot)
      {        Statechart root = nroot;
	 lauf = lauf.next;
	try {lauf.chart = (Statechart) root.clone();}
	catch (Exception e) {System.out.println("Waere die Absyn korrekt, so funktionierte auch das Undo !!!!");}
      }


} // PESTDrawDesk



