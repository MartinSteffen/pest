package TESC1;

//import java.util.*;
import Absyn.*;
import java.io.*;

/** 
 * TESCLoader. 
 * Schnittstelle zu PEST.
 * @author Arne Koch/Mike Rumpf.
 * @version  $Id: TESCLoader.java,v 1.1 1998-12-03 21:26:39 swtech13 Exp $ 
 */ 
public class TESCLoader {

    private FileInputStream is;		// InputStream
    private Statechart stchart;		// der aufzubauende Statechart
    private TESCParser parser;          // der Parser

    // public-Methoden
   
    /** 
     * Umwandeln eines  TESC-File aus is_ in Statechart.
     * @return Liefert Statechart.
     */ 
    public Statechart getStatechart(FileInputStream is_) {
	// initiiert das Laden des Files und Aufbauen des StCharts
	is = is_;
	
	parser = new TESCParser(is);
	
	stchart = parser.parse();
	
	return stchart;
    }


    /**
     * Gibt die Anzahl der während des Parseprozesses aufgetretenen Fehler zurück.
     * @return die Anzahl der Fehler.
     */
    public int getErrorCount() {
	return parser.getErrorCount();
    }

    /**
     * Liefert den Fehlertext des i-ten Fehlers
     * @return den i-ten Fehler.
     */
    public String getErrorText(int i) {
	return parser.getErrorText(i);
    }

}
