package TESC1;

/**
 * Tokenizer für TESC.
 * <p>
 * @author Michael Sülzer, Christoph Schütte (swtech20).
 * @version  $Id: TESCTokenizer.java,v 1.1 1998-12-03 13:08:18 swtech20 Exp $
 */   

import java.io.*;
import java.util.Vector;
import Absyn.*;

public class TESCTokenizer {

  private FileInputStream input = null;
 
  /**
     * Constructor für den TESCTokenizer.
     */
  public TESCTokenizer (FileInputStream fis) {
    
    input = fis;
  }


  /**
     * Startet einen Lexerlauf.
     */
  public int getNextToken() {
    return 4;
  }   
}
 
//----------------------------------------------------------------------
//      Schnittstelle zum TESCParser               
//      ----------------------------               
//
//      $Log: not supported by cvs2svn $
//              
//----------------------------------------------------------------------
