package check;

import java.io.*;

/**
 * Klasse zum Speichern der Optionen des Syntax Checks.
 *
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: CheckConfig.java,v 1.2 1999-01-19 10:18:56 swtech11 Exp $
 */
public class CheckConfig implements Serializable {

  /**
  * Fehlermeldungen highlighten ?
  */
  public boolean sc_highlight = false;

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
  public boolean cr_highlight = false;

}

