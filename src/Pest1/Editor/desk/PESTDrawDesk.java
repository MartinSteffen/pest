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
      
      Statechart root= new Statechart(null,null,null,null);

  /** Initialisierung des Hauptframes*/

      public PESTDrawDesk(Frame frame, int width, int height,Statechart nroot) {
      
	  root = nroot;

    this.frame = frame;
    this.width = width;
    this.height = height;

    // low-level Implementierungen
    this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

    // Erzeugung der pop-up Menues
    String[] labels = new String[] {
      "Clear", "Save", "Load"};
    String[] commands = new String[] {
      "clear", "save", "load"};
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

	    if ((e.getID() == MouseEvent.MOUSE_RELEASED) & (Editor.Editor() =="Draw_State"))
		{ 
		    // new drawPESTState(g,root,last_x,last_y,e.getX(),e.getY(),Color.blue);
		    Editor.SetListen();
		}
	}
  }
 
  // Loeschen von Bildschirm und Speicher
  void clear() {
    repaint();                   		// Bildschirm loeschen
  }
public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals("clear")) clear();
    else if (command.equals("save")) ;
    else if (command.equals("load")) ;
    
  }

      private int schnitt(Rectangle r1,Rectangle r2) {
	  int test = 0;
	  boolean r1inr2 = false , r2inr1 = false;

	 
	  r2inr1 = r1.intersects(r2);
	  r1inr2 = r2.intersects(r1);

	  return test;
      }


} // PESTDrawDesk



