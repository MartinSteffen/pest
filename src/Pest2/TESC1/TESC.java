package TESC1;

import java.io.*;
import Absyn.*;

public class TESC {

  private Statechart statechart = null;

  public TESC () {
  }

  public Statechart createStatechart(FileInputStream fis) {


    // ****************************************************************
    // Hier Aufruf der Routinen von TESC1
    // ****************************************************************
 
    // StateChart aus TESC-File erstellen... 
 
    TESCParser Parser = new TESCParser();
    statechart = Parser.parseStream(fis);
 
    // Fehlerauswertung
   

    // ****************************************************************    
    // Hier Aufruf der Routinen vom SyntaxChecker
    // ****************************************************************


    // ****************************************************************
    // Hier Aufruf der Routinen von TESC2
    // ****************************************************************
 
         /*

	   Hi swtech19, 
	   hier solltet Ihr jetzt Eure Schnittstellenaufrufe 
	   eintragen.

	   swtech20

          */
        
    // Rueckgabe...

    return statechart;
  }
}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC
//	---------------------
//
//	$Id: TESC.java,v 1.1 1998-11-30 11:58:08 swtech20 Exp $
//
//	$Log: not supported by cvs2svn $
//----------------------------------------------------------------------

