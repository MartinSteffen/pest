package check;

import java.io.*;
import java.awt.*;

/**
 * Klasse zum Speichern der Optionen des Syntax Checks.
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: CheckConfig.java,v 1.3 1999-01-22 20:33:20 swtech11 Exp $
 */
public class CheckConfig implements Serializable {

  /**
  * Browser benutzen ?
  */
  public boolean sc_browser = true;

  /**
  * Warnung ausgeben ?
  */
  public boolean sc_warning = true;

  /**
  * Ergebnisse der Crossreference highlighten ?
  */
  public boolean cr_highlight = true;

  /**
  * Index der Highlicht Color
  */
  public int high_color = 8;

  /**
  * Array f�r die Farben
  */
  Color[] color;

  public CheckConfig() {
  	color = new Color[13];
	  color[0] = Color.red;
  	color[1] = Color.blue;
	  color[2] = Color.yellow;
  	color[3] = Color.green;
	  color[4] = Color.orange;
  	color[5] = Color.pink;
	  color[6] = Color.magenta;
  	color[7] = Color.cyan;
	  color[8] = Color.black;
	  color[9] = Color.white;
  	color[10] = Color.lightGray;
  	color[11] = Color.darkGray;
  	color[12] = Color.gray;
  }


}

