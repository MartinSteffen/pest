/**
 * Test2.
 *
 * Created: Fri Jan 01 1999, 02:34:05
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: Test2.java,v 1.2 1999-01-22 21:59:20 swtech14 Exp $
 *
 *
 * Diese Klasse dient zum Testen der mit dem Graphplazierungsalgorithmus
 * verbundenen Methoden und Klassen.
 */

package tesc2.Test;


import absyn.*;
import java.awt.*;
import tesc2.GraphOptimizer;
import tesc2.AlgorithmException;
import java.applet.Applet;
import util.PrettyPrint;

public class Test2 {
  private static Statechart sChart;
  private static int x1, x2;


  /**
   * Main-Routine.
   * Erzeugt eine ´FontMetrics´, holt ein Statechart-Objekt aus ´Absyn.Example´
   * oder ´Test.Testobjects´, ruft den ´GraphOptimizer´ auf, und gibt das Re-
   * sultat in einem Frame aus.
   * Dieses geschieht unter Verwendung der Hilfsklasse 'TestOutput2', die ein
   * "Standalone-Applet" erzeugt.
   */

  public static void main (String[] args) {
    if ((args.length == 2)      &&
	(args[0].length() == 1) &&
	(args[1].length() ==1)  &&
	(Character.isDigit (args[0].charAt (0))) &&
	(Character.isDigit (args[1].charAt (0)))) {

      x1 = Character.digit (args[0].charAt (0), 10);
      x2 = Character.digit (args[1].charAt (0), 10);
      if (x1 == 1)
	sChart = Example.getExample();
      if (x1 == 2)
	sChart = TestObjects.getStatechart1();
      if (x1 == 3)
	sChart = TestObjects.getStatechart2();
      if (x1 == 4)
	sChart = TestObjects.getStatechart3();
      if (x1 == 5)
	sChart = TestObjects.getStatechart4();
      if (x1 == 6)
	sChart = TestObjects.getStatechart5();
      if (x1 != 1 && x1 != 2 && x1 != 3 && x1 != 4 && x1 != 5 && x1 != 6) {
	System.out.println ("Object not available.");
	System.exit (0);
      }
      if (x2 != 0 && x2 != 1) {
	System.out.println ("Second parameter must be 0 or 1.");
	System.exit (0);
      }

    } else {
      System.out.println ("USAGE: Test2 <n> <c>");
      System.out.println
	("n == number of testobject (1, 2, 3, 4, 5 or 6);");
      System.out.println
	("c == relative (0) or absolute (1) coordinates.");
      System.exit (0);
    }

    TestOutput2 output = new TestOutput2();
    output.abs = (x2 == 0) ? false : true;
    output.sc = sChart;
    output.start();

  } // method main

} // class Test2



class TestOutput2 extends Applet {

  boolean abs;
  Statechart sc;

  private static Frame f = new Frame();
  private Font font = new Font ("TimesRoman", Font.BOLD, 6);
  private FontMetrics fm;

  private final int OFFSET_X = 32;
  private final int OFFSET_Y = 32;

  private  final Color COLOR_STATE      = Color.white;//red;
  private  final Color COLOR_TRANSITION = Color.white;//green;
  private  final Color COLOR_COLLECTOR  = Color.white;
  private  final Color COLOR_LABEL      = Color.white;//orange;


  public synchronized void start () {

    Applet a = new TestOutput2();
    ((TestOutput2)a).abs = abs;
    ((TestOutput2)a).sc = sc;
    ((TestOutput2)a).fm = fm;
    f.add (a);
    f.resize (1024,700);
    f.show();
    f.setBackground (Color.black);
    f.move (10,20);
    a.init();
    a.paint (f.getGraphics());
  } // method start



  public void init () {
    Graphics g = f.getGraphics();
    g.setFont (font);

    // Hole FontMetrics.
    fm = g.getFontMetrics (font);

    // ------------------------ Rufe Algorithmus auf. -----------------------

    // ERZEUGE ALGORITHMUS-OBJEKT FUER STATECHART 'sc' UND FONTMETRICS 'fm'

    GraphOptimizer go = new GraphOptimizer (sc, fm);

    // STARTE ALGORITHMUS, FANGE 'Algorithm-Exception' AB.

    try {
      sc = go.start (0);
    } catch (AlgorithmException ae) {
      System.out.println (ae.getMessage());
      System.exit (0);
    }

    // ----------------------------------------------------------------------

    //---    PrettyPrint pp = new PrettyPrint ();
    //---    pp.start (sc);

  } // method init



  public void paint (Graphics g) {
    g.setFont (font);

    // Testausgabe absolut/relativ, je nach Eingabeparameter.

    if (abs == true) {
      abs_drawState (sc.state, g);
      System.out.println ("Ausgabe mit Absolutkoordinaten.");
    } else {
      rel_drawState (sc.state, OFFSET_X, OFFSET_Y, g);
      System.out.println ("Ausgabe mit Relativkoordinaten.");
    }
  } // method paint



  // -------------------- Ausgabe mit Absolut-Koordinaten ---------------------



  private  void abs_drawState (State s, Graphics g) {
    StateList sL     = null;
    TrList tL        = null;
    ConnectorList cL = null;


    if (s != null) {

      if (s.rect != null) {
	g.setColor (COLOR_STATE);
	g.drawRect (s.rect.x, s.rect.y + OFFSET_Y, s.rect.width,s.rect.height);
      }

      if ((s.name != null) && (s.name.position != null)) {
	g.setColor (COLOR_LABEL);
	g.drawString (s.name.name,
		      s.name.position.x, s.name.position.y + OFFSET_Y);
      }

      if (s instanceof Or_State) {
	sL = ((Or_State)s).substates;
	tL = ((Or_State)s).trs;
	cL = ((Or_State)s).connectors;
      }

      if (s instanceof And_State) {
	sL = ((And_State)s).substates;
      }

    } // if s != null

    while (sL != null) {
      abs_drawState (sL.head, g);
      sL = sL.tail;
    }
    while (tL != null) {
      abs_drawTransition (tL.head, g);
      tL = tL.tail;
    }
    while (cL != null) {
      abs_drawConnector (cL.head, g);
      cL = cL.tail;
    }

  } // method abs_drawState



  private  void abs_drawTransition (Tr t, Graphics g) {

    g.setColor (COLOR_TRANSITION);
    if (t != null) {
      if (t.points != null)
	for (int i=0; i < t.points.length-1; i++)
	  if ((t.points[i] != null) && (t.points[i+1] != null))
	    g.drawLine (t.points[i].x, t.points[i].y + OFFSET_Y,
			t.points[i+1].x, t.points[i+1].y + OFFSET_Y);

      g.setColor (COLOR_LABEL);
      if ((t.label != null) && (t.label.position != null))
	g.drawString ("Testlabel",
		      t.label.position.x, t.label.position.y + OFFSET_Y);

    } // if t != null

  } // method abs_drawTransition



  private  void abs_drawConnector (Connector c, Graphics g) {

    g.setColor (COLOR_COLLECTOR);
    if (c != null) {
      if (c.position != null)
	g.drawOval (c.position.x, c.position.y + OFFSET_Y,
		    GraphOptimizer.CONNECTOR_SIZE,
		    GraphOptimizer.CONNECTOR_SIZE);

      g.setColor (COLOR_LABEL);
      if ((c.name != null) && (c.name.position != null))
	g.drawString (c.name.name,
		      c.name.position.x, c.name.position.y + OFFSET_Y);

    } // if c!= null

  } // method abs_drawConnector



  // -------------------- Ausgabe mit Relativ-Koordinaten ---------------------



  private  void rel_drawState (State s, int coo_x, int coo_y,
				     Graphics g) {
    StateList sL     = null;
    TrList tL        = null;
    ConnectorList cL = null;

    int x = coo_x;
    int y = coo_y;

    if (s != null) {

      if (s.rect != null) {
	x = s.rect.x + coo_x;
	y = s.rect.y + coo_y;
	g.setColor (COLOR_STATE);
	g.drawRect (x, y, s.rect.width, s.rect.height);
      }

      if ((s.name != null) && (s.name.position != null)) {
	g.setColor (COLOR_LABEL);
	g.drawString (s.name.name,
		      s.name.position.x + coo_x,
		      s.name.position.y + coo_y);
      }

      if (s instanceof Or_State) {
	sL = ((Or_State)s).substates;
	tL = ((Or_State)s).trs;
	cL = ((Or_State)s).connectors;
      }

      if (s instanceof And_State) {
	sL = ((And_State)s).substates;
      }

    } // if s != null

    while (sL != null) {
      rel_drawState (sL.head, x, y, g);
      sL = sL.tail;
    }
    while (tL != null) {
      rel_drawTransition (tL.head, x, y, g);
      tL = tL.tail;
    }
    while (cL != null) {
      rel_drawConnector (cL.head, x, y, g);
      cL = cL.tail;
    }

  } // method rel_drawState



  private  void rel_drawTransition (Tr t, int coo_x, int coo_y,
					  Graphics g) {
    if (t != null) {

      if (t.points != null) {
	g.setColor (COLOR_TRANSITION);
	for (int i=0; i < t.points.length-1; i++)
	  if ((t.points[i] != null) && (t.points[i+1] != null))
	    g.drawLine (t.points[i].x + coo_x,
			t.points[i].y + coo_y,
			t.points[i+1].x + coo_x,
			t.points[i+1].y + coo_y);
      }
      if ((t.label != null) && (t.label.position != null)
	  && (t.label.caption != null)) {
	g.setColor (COLOR_LABEL);
	g.drawString (t.label.caption,
		      t.label.position.x + coo_x,
		      t.label.position.y + coo_y);
      }
    } // if t != null

  } // method rel_drawTransition



  private  void rel_drawConnector (Connector c, int coo_x, int coo_y,
					 Graphics g) {
    if (c != null) {

      if (c.position != null) {
	g.setColor (COLOR_COLLECTOR);
	g.drawOval (c.position.x + coo_x, c.position.y + coo_y,
		    GraphOptimizer.CONNECTOR_SIZE,
		    GraphOptimizer.CONNECTOR_SIZE);
      }
      if ((c.name != null) && (c.name.position != null)) {
	g.setColor (COLOR_LABEL);
	g.drawString (c.name.name,
		      c.name.position.x + coo_x,
		      c.name.position.y + coo_y);
      }
    } // if c!= null

  } // method rel_drawConnector

} // class TestOutput2
