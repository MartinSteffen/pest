package absyn.test;

import java.awt.*;   // fuer die Frames
import tesc2.*;      // zum Testen der Graphplazierung 
import absyn.*;      // zum Testen der abstrakten Syntax
import util.PrettyPrint;  // PrettyPrinter


public class Test_GP {
  /**
   * main-Methode -> Tests der 
   *  - der Graphplazierung.
   * die Graphplazierung hat noch irgendwelche  unerklaerlichen 
   * Effekte wie: repaint aendert die Koordinataten (vielleicht)
   */
  public static void main (String[] args) {
    Statechart  sc = Example.getExample();  // das Standardbeispiel 
    PrettyPrint pp = new PrettyPrint ();
    Frame   f = new Frame();
    Font font = new Font ("TimesRoman", Font.BOLD, 12);
    FontMetrics fm;
    fm = f.getFontMetrics(font);

    GraphOptimizer go = new GraphOptimizer(sc, fm);

    try {
      sc  = go.start (0);
    } catch (AlgorithmException ae) {
      System.out.println (ae.getMessage());
	}
    pp.start(sc);;
  }
}
    

