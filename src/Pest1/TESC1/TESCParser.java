package TESC1;

import java.util.*;
import Absyn.*;
import java.io.*;



class TESCParser {

    private FileInputStream is;
    private Statechart stchart;
    private int errorCount = 0;   // Anzahl der aufgetretenen Fehler
    private Vector errorList;     // Fehlerliste

    // protected-Methoden für das Package
	
    protected TESCParser(FileInputStream is_) {
	is = is_;	
    }

    protected Statechart parse() {

	TESCScanner ts = new TESCScanner(is);

	TOKEN tok;
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
	System.out.println("");

	stchart = new Statechart(null,null,null,null); //Dummy

	return stchart;
    }

    protected int getErrorCount() {
	return errorCount;
    }

    /**
     * Vector beginnt bei 0, der erste Fehler soll aber mit 1 bezeichnet werden
     * Wie macht das PEST2.TESC1 ??
     */
    protected String getErrorText(int i) {
	return (String) ( (i<=errorCount || i<1) ? errorList.elementAt(i-1) : "Unknown Error (Index out of Range)." );
    }
}

/* TESCParser
 * $Id: TESCParser.java,v 1.2 1998-12-07 13:20:15 swtech13 Exp $
 * $Log: not supported by cvs2svn $
 */
