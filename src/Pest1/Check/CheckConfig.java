package check;

import java.io.*;

/**
 * Klasse zum Speichern der Optionen des Syntax Checks.
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: CheckConfig.java,v 1.1 1999-01-18 20:56:30 swtech11 Exp $
 */
public class CheckConfig implements Serializable {

  /**
  * Fehlermeldungen highlighten ?
  */
  public boolean sc_highlight = false;

  /**
  * Warnung ausgeben ?
  */
  public boolean sc_warning = true;

  /**
  * Ergebnisse der Crossreference highlighten ?
  */
  public boolean cr_highlight = false;

}

