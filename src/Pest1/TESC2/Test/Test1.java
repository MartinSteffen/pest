/**
 * Test1.
 *
 * Created: Fri Jan 01 1999, 02:43:25
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: Test1.java,v 1.2 1999-01-22 21:59:17 swtech14 Exp $
 *
 *
 * Diese Klasse dient zum Testen der mit dem Graphplazierungsalgorithmus
 * verbundenen Methoden und Klassen.
 */

package tesc2.Test;


import absyn.*;
import java.awt.*;
import util.PrettyPrint;
import tesc2.GraphOptimizer;
import tesc2.AlgorithmException;

public class Test1 {
  private static Frame f = new Frame();
  private static Font font = new Font ("TimesRoman", Font.BOLD, 12);
  private static FontMetrics fm;
  private static Statechart sChart;

  private static int x1;


  /**
   * Main-Routine.
   * Erzeugt eine ´FontMetrics´, holt ein Statechart-Objekt aus ´Absyn.Example´
   * oder ´Test.Testobjects´, ruft den ´GraphOptimizer´ auf, und gibt das Re-
   * sultat unter Verwendung des PrettyPrinters auf dem Bildschirm aus.
   */

  public static void main (String[] args) {
    if ((args.length == 1)      &&
	(args[0].length() == 1) &&
	(Character.isDigit (args[0].charAt (0)))) {

      x1 = Character.digit (args[0].charAt (0), 10);
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
    } else {
      System.out.println ("USAGE: Test1 <n>");
      System.out.println ("n == number of testobject (1, 2, 3 or 4);");
      System.exit (0);
    }

    // Hole FontMetrics.

    fm = f.getFontMetrics (font);

    // Rufe Algorithmus auf.

    GraphOptimizer go = new GraphOptimizer (sChart, fm);
    try {
      sChart = go.start (0);
      PrettyPrint pp = new PrettyPrint();
      pp.start (sChart);

    } catch (AlgorithmException ae) {
      System.out.println (ae.getMessage());

    } finally {
      System.exit (0);
    }

  } // method main

} // class Test1
