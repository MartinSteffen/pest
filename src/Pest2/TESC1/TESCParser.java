package TESC1;

import java.io.*;
import java.util.Vector;
import Absyn.*;

public class TESCParser {

  private Statechart statechart = null;
  private FileInputStream input = null;
  private int errorCount = 0;
  private Vector errorText = null;

  public TESCParser () {
  }

  public Statechart parseStream(FileInputStream fis) {
    errorText = new Vector();
    errorCount = 0;
    input = fis;
    Parse();
    return statechart;
  }

  public int getErrorCount() {
    return errorCount;  
  }

  public String getErrorText(int error) {
    return (String) errorText.elementAt(error);
  }

  private void Parse() {
  }
}
 
//----------------------------------------------------------------------
//      Schnittstelle zum TESCParser               
//      ----------------------------               
//
//      $Id: TESCParser.java,v 1.1 1998-11-30 11:58:59 swtech20 Exp $                                                    
//
//      $Log: not supported by cvs2svn $              
//----------------------------------------------------------------------
     
      