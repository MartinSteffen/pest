package TESC1;

/**
 * Parser für TESC.
 * <p>
 * @author Michael Sülzer, Christoph Schütte (swtech20).
 * @version  $Id: TESCParser.java,v 1.2 1998-12-03 13:08:17 swtech20 Exp $
 */   

import java.io.*;
import java.util.Vector;
import Absyn.*;

public class TESCParser {

  private Statechart statechart = null;
  private FileInputStream input = null;
  private int errorCount = 0;
  private Vector errorText = null;

  /**
     * Constructor für den TESCParser.
     */
  public TESCParser () {
  }


  /**
     * Startet den Parsevorgang.
     */
  public Statechart parseStream(FileInputStream fis) {
    errorText = new Vector();
    errorCount = 0;
    input = fis;
   


    return statechart;
  }

  
  /**
     * Liefert die Anzahl der Fehler, die beim letzten Parsen
     * aufgetreten sind.
     * @return Anzahl der Fehler.
     */
  public int getErrorCount() {return errorCount;}

  
  /**
     * Liefert den Fehlertext zum n-ten Fehler, der beim letzen
     * Parsen aufgetreten ist.
     * @return Fehlertext des n-ten Fehlers.
     */
  public String getErrorText(int n) {return (String)errorText.elementAt(n);}
   
}
 
//----------------------------------------------------------------------
//      Schnittstelle zum TESCParser               
//      ----------------------------               
//
//      $Log: not supported by cvs2svn $
//      Revision 1.1  1998/11/30 11:58:59  swtech20
//      Initial revision
//              
//----------------------------------------------------------------------
     
      















