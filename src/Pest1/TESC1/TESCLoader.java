package tesc1;

//import java.util.*;
import absyn.*;
import java.io.*;
import gui.*;

/** 
 * TESCLoader. 
 * <STRONG> Garantie. </STRONG> Wir garantieren, dass die von unseren
 * Module erzeigten Statecharts folgende Eingenschaften haben:
 * 
 * <ul>
 * <li> Ist noch nicht bekannt.
 * <li> Alle States, Bvars, Events, etc. auf die in z.B. Transition Bezug genommen wird, sind auch vorhanden.
 * <li> Es gibt nur einen DefCon pro Ebene
 * </ul>
 * 
 * Damit ist es nicht notwendig, folgender Checks an unsere Statecharts
 * anzuwenden:
 * 
 * <ul>
 * <li> unbekannt.
 * </ul>
 * 
 * <STRONG> Anforderungen. </STRONG> Wir verlassen uns darauf, dass die
 * Statecharts, die uns uebergeben werden, folgende Eigenschaften haben: 
 * 
 * <ul>
 * <li> unbekannt, weil erst in Stufe 2 relevant.
 * </ul>
 * 
 * die mit folgenden Checks ueberprueft werden koennen:
 * 
 * <ul>
 * <li> unbekannt
 * </ul>

 * @author Arne Koch/Mike Rumpf.
 * @version  $Id: TESCLoader.java,v 1.7 1998-12-21 16:17:35 swtech13 Exp $ 
 */ 
public class TESCLoader {

    private BufferedReader is;		// InputStream
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
    public Statechart getStatechart(BufferedReader is_) throws IOException {
	// initiiert das Laden des Files und Aufbauen des StCharts
	is = is_;
	
	parser = new TESCParser(is, gi);
	
	stchart = parser.parse();
	
	return stchart;
    }

}

/* 
 * $Log: not supported by cvs2svn $
 * Revision 1.6  1998/12/17 11:54:14  swtech13
 * TESCLoader.java auf BufferedReader umgestellt
 *
 * Revision 1.5  1998/12/15 17:51:57  swtech00
 * Towards new naming conventions in PEST1
 *
 * Revision 1.4  1998/12/07 20:10:15  swtech13
 * Anpassung der Schnittstelle
 *
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
