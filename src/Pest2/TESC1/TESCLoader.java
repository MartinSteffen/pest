package TESC1;

/**
 * Schnittstellenklasse für TESC1.
 * <p>
 * @author Michael Sülzer, Christoph Schütte (swtech20).
 * @version  $Id: TESCLoader.java,v 1.1 1998-12-03 13:08:16 swtech20 Exp $
 */   

import java.io.*;
import Absyn.*;

public class TESCLoader {

  private Statechart statechart = null;
  private TESCParser parser = null;
  

  public Statechart getStatechart(FileInputStream fis) {
 
    parser = new TESCParser();
    statechart = parser.parseStream(fis);
 
    return statechart;
  }

  public int getErrorCount() {
    return parser.getErrorCount();
  }

  public String getErrorText(int error) {
    return parser.getErrorText(error);
  }

}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------

