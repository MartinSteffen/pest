package check;

import java.io.*;
import java.awt.*;

/**
 * Klasse zum Speichern der Optionen des Syntax Checks.
 *
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 *  @version  $Id: CheckConfig.java,v 1.7 1999-02-14 20:56:20 swtech11 Exp $
 */
public class CheckConfig implements Serializable {

  /**
  * Browser f�r Check benutzen ?
  */
  boolean sc_browser = true;

  /**
  * Warnung ausgeben ?
  *   0: keine Warnungen
  *   1: alle Warnungen
  *   2: bestimmte Warnungen
  */
  int sc_warning = 1;

  /**
  * String f�r Warnungs-Codes
  */
  String sc_warnStr = new String("");

  /**
  * Browser f�r Crossreference benutzen ?
  */
  boolean cr_highlight = true;

  /**
  * Index der Highlight Color
  */
  int high_color = 8;

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

