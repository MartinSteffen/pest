/**
 * PESTDrawDesk.java
 *
 *
 * Created: Thu Nov  5 11:52:16 1998
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
import Editor.*;


 /* Hauptzeichenfeld (Voreinstellungen & Definitionen) */
  public class PESTDrawDesk extends Component implements ActionListener {

      protected short last_x, last_y;                		// letzter Klick
      protected Color current_color = Color.black;   	        // aktueller Farbe
      protected int width, height;                   		// Groessenvariablen
      protected PopupMenu popup;                     		// pop-up Menue
      protected Frame frame;                        		// Menuefenster

      static Storelist anf;
      static Storelist lauf;
      static Storelist basis;

      
      Statechart root= new Statechart(null,null,null,null);

  /** Initialisierung des Hauptframes*/

      public PESTDrawDesk(Frame frame, int width, int height,Statechart nroot) {
           new newStorelist();
	  root = nroot;

    this.frame = frame;
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
    if (command.equals("undo")) System.out.println(undo());
    else if (command.equals("restore")) System.out.println(redo());
    else if (command.equals("load")) ;
    
  } 

  // Grafik herstellen
  public void paint(Graphics g) {
   
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

	    if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (editor.editor() =="Draw_State"))
		{ 
		    new drawPESTState(g,root,last_x,last_y,e.getX(),e.getY(),Color.blue);
		    editor.SetListen();
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
	  int chart;
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
		    lauf.chart = i;
		    anf.next = lauf;
		    anf = lauf;
		    
		  }
	      anf.next = basis;
	      basis.prev = anf;
	      lauf = basis;
	  }
      }

      private static int undo()
      {
	  lauf = lauf.prev;
	  return lauf.chart;
      }

      private static int redo()
      {
	  if (lauf != basis) {lauf = lauf.next;}
	  return lauf.chart;
      }


} // PESTDrawDesk



