package TESC1;

//import java.util.*;
import Absyn.*;
import java.io.*;
import GUI.*;

/** 
 * TESCLoader. 
 * Schnittstelle zu PEST.
 * @author Arne Koch/Mike Rumpf.
 * @version  $Id: TESCLoader.java,v 1.4 1998-12-07 20:10:15 swtech13 Exp $ 
 */ 
public class TESCLoader {

    private FileInputStream is;		// InputStream
    private Statechart stchart;		// der aufzubauende Statechart
    private TESCParser parser;          // der Parser
    private GUIInterface gi;            // GUI

    /** 
     * Nach der Instanzierung von TESCLoader getStatechart(...) aufrufen
     * @param Referenz auf eine GUIInterface-Instanz
     */
    public TESCLoader(GUIInterface gi_) {
	gi = gi_;
    }

    // public-Methoden
   
    /** 
     * Umwandeln eines  TESC-File aus is_ in Statechart.
     * @param Referenz auf einen FileInputStream
     * @return Liefert Statechart oder null bei Fehler.
     */ 
    public Statechart getStatechart(FileInputStream is_) {
	// initiiert das Laden des Files und Aufbauen des StCharts
	is = is_;
	
	parser = new TESCParser(is, gi);
	
	stchart = parser.parse();
	
	return stchart;
    }

}

/* 
 * $Log: not supported by cvs2svn $
 * Revision 1.3  1998/12/07 15:13:21  swtech13
 * Scanner um Kommentare erweitert, Schnittstelle um Konstruktor mit
 * GUIInterface Parameter erweitert.
 *
 * Revision 1.2  1998/12/07 13:20:14  swtech13
 * Scanner geht, aber noch nicht vollstaendig,
 * Parser nix,
 * Grammatik muss noch ueberarbeitet werden.
 *
 */
