package TESC1;

import java.util.*;
import Absyn.*;
import java.io.*;
import GUI.*;


class TESCParser {

    private FileInputStream is;
    private Statechart stchart;
    private int errorCount = 0;   // Anzahl der aufgetretenen Fehler
    private Vector errorList;     // Fehlerliste
    private GUIInterface gi;

    // protected-Methoden für das Package
	
    protected TESCParser(FileInputStream is_, GUIInterface gi_) {
	gi = gi_;
	is = is_;	
    }

    protected Statechart parse() {

	TESCScanner ts = new TESCScanner(is);

	TOKEN tok;
	try {
	    tok = ts.nextToken();
	
	    int ln;
	    ln = tok.linenum;
	
	    boolean printTokVal = false;

	    System.out.print(tok.linenum);
	    while (tok.token != vTOKEN.EOF) {
	    
		if (tok.linenum!=ln) { System.out.println(""); System.out.print(tok.linenum);}
		System.out.print(" '");
		System.out.print(tok.value_str);
		if (printTokVal) {
		    System.out.print("/");
		    System.out.print(tok.token);
		}
		System.out.print("'");
		
		ln = tok.linenum;
		
	    
		tok = ts.nextToken();
	    
	    }
	
	}
	catch (IOException e) {
	    // Hier in Fehlervektor eintragen?
	    System.out.println("Ärger!");
	    gi.OkDialog((String) "Scanner: IOError", (String) "Fehler - bei Lesen des Streams!");
	}

	System.out.println("");

	stchart = new Statechart(null,null,null,null); //Dummy

	return stchart;
    }

    protected int getErrorCount() {
	return errorCount;
    }

    /**
     * Vector beginnt bei 0, der erste Fehler soll aber mit 1 bezeichnet werden
     * 
     */
    protected String getErrorText(int i) {
	return (String) ( (i<=errorCount && i>=1) ? errorList.elementAt(i-1) : "Unknown Error (Index out of Range)." );
    }
}

/* TESCParser
 * $Id: TESCParser.java,v 1.3 1998-12-07 20:10:16 swtech13 Exp $
 * $Log: not supported by cvs2svn $
 * Revision 1.2  1998/12/07 13:20:15  swtech13
 * Scanner geht, aber noch nicht vollstaendig,
 * Parser nix,
 * Grammatik muss noch ueberarbeitet werden.
 *
 */
