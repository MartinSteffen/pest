package tesc1;

//import java.util.*;
import absyn.*;
import java.io.*;
import gui.*;
import java.util.*;

/** 
 *  
 * <STRONG> Garantie. </STRONG> <br> Wir garantieren, dass die von unseren
 * Modulen erzeugten Statecharts folgende Eingenschaften haben:
 * 
 * <ul>
 * <li> Alle Bvars, Events, etc. auf die in z.B. Transition Bezug genommen wird, sind auch vorhanden. 
 *      <STRONG> AUSNAHME: </STRONG> States in Comppath
 * <li> Es gibt nur einen DefCon pro Ebene
 * <li> Keine Schleifen bei Listen.
 * <li> Keine unartigen Nullpointer.
 * <li> Keine doppelte Referenzierung.
 * <li> Keine UNDEFINED() TrAnchors
 * <li> Keine GuardUndet()
 * </ul>
 * 
 * Damit ist es nicht notwendig, folgende Checks an unsere Statecharts
 * anzuwenden:
 * 
 * <ul>
 * <li> TestBVars.pruefeBVar(...)    // Allerdings sollte gecheckt werden, ob überflüssige BVars vorhanden
 * <li> TestEvents.pruefeEvent(...)  // Allerdings sollte gecheckt werden, ob überflüssige Events vorhanden
 * <li> TestPI                       // Falls wir das richtig verstanden haben ..
 * </ul>
 * 
 * <STRONG> Anforderungen. </STRONG> <br>
 * Wir verlassen uns darauf, dass die
 * Statecharts, die uns uebergeben werden, folgende Eigenschaften haben: 
 * 
 * <ul>
 * <li> Diese Klasse erhält keine Statecharts
 * </ul>
 * 
 * die mit folgenden Checks ueberprueft werden koennen:
 * 
 * <ul>
 * <li> nichts.
 * </ul>

 * <DL COMPACT>
 * 
 * <DT><STRONG>
 * STATUS
 * </STRONG>
 * <br> 
 * Der Parser ist bis auf Kleinigkeiten (s. TODO) funktionsfaehig.
 * In tesc1/Test befinden sich mehrere Testdateien. <br>
 * Diese koennen ueber das GUI-Fenster mittels Import->TESC geladen werden. <br>
 * Die TESC-Sprache ist über die <A HREF="./tesc1/Docu/grammatik.txt">Grammatik</A> definiert. 
 * Siehe auch <A HREF="./tesc1/Docu/Doku.txt"> Doku.txt</A> <br>
 * Beispiel <A HREF="./tesc1/Test/example.tesc"> example.tesc</A>
 * Die Parse-Methoden für den Editor haben in eigenen Tests funktioniert.
 * <br>
 * <DT><STRONG>
 * TODO.
 * </STRONG>
 * <br>
 * <ul>
 * <li> Undet bei Guards (ist wohl nicht nötig)
 * <li> Abschließende Test (z.B. im Editor)
 * <li> Testen der Schnitstelle für den Editor (getAction/getGuard)
 * </ul>
 * 
 * <br>
 * <DT><STRONG>
 * TEMP.
 * </STRONG>
 * <ul>
 * <li> debug-ausgaben ins GUI Fenster.
 * </ul>
 *
 * <DT><STRONG>
 * <A NAME="options"> Einstellungsmöglichkeiten </A>
 * </STRONG> 
 *
 * <ul>
 * <li> debug
 * <li> jumpAfterError
 * </ul>
 *
 * </DL COMPACT>
 * <br>
 * <hr>
 * @author Arne Koch/Mike Rumpf.
 * @version  $Id: TESCLoader.java,v 1.18 1999-01-18 12:27:54 swtech13 Exp $ 
 */ 
public class TESCLoader {

    private BufferedReader is;		// InputStream
    private Statechart stchart;		// der aufzubauende Statechart
    private TESCParser parser;          // der Parser
    private GUIInterface gi;            // GUI
    private Vector options = null;             // enthält Optionen
    private SEventList evlist = null;   // Eventliste
    private BvarList bvlist = null;
 
    /** 
     * Nach der Instanzierung von TESCLoader getStatechart(...) aufrufen
     * @param gi_ Referenz auf eine GUIInterface-Instanz
     */
    public TESCLoader(GUIInterface gi_) {
	gi = gi_;
    }

    // public-Methoden


    /** 
     * <A HREF="#options">Optionen</A> einstellen
     * @param opt Referenz auf einen Vector, der die <A HREF="#options"> Optionen</A> enthält.
     */
    public void initOptions(Vector opt) {
	options = opt;
    }
   
    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Statechart.
     * @param Referenz auf einen BufferedReader
     * @return Liefert Statechart oder null bei Fehler.
     */ 
    public Statechart getStatechart(BufferedReader is_) throws IOException {
	// initiiert das Laden des Files und Aufbauen des StCharts
	is = is_;
	
	parser = new TESCParser(is, gi);
	parser.initSwitches(options);
	
	stchart = parser.parse();
	
	return stchart;
    }


    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Guard.<br> Fehlerausgaben werden vom Parser ins gui-Fenster geschrieben.<br><STRONG>  Temporär!! </STRONG>
     * @param Referenz auf einen BufferedReader
     * @return Liefert Guard oder null bei Fehler.
     */ 
    public Guard getGuard(BufferedReader br) throws IOException {
	TESCParser parser = new TESCParser(br, gi);
	parser.initSwitches(options);

	Guard guard = parser.readGuard(br);

	if (parser.getErrorCount() > 0) {	   
	    gi.OkDialog("Fehler", "Guard ist fehlerhaft.");
	    return null;
        }
	else {
	    if (guard == null) guard = new GuardEmpty(new Dummy());
	    return guard;
	}
    }

    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Guard.<br> <STRONG>  Temporär!! </STRONG>.
     * @param Referenz auf einen BufferedReader
     * @param Referenz auf SEventList
     * @param Referenz auf BvarList
     * @return Liefert Guard oder null bei Fehler.
     */ 
    public Guard getGuard(BufferedReader br, SEventList el, BvarList bl) throws IOException {
	TESCParser parser = new TESCParser(br, gi);
	parser.initSwitches(options);

	Guard guard = parser.readGuard(br, el, bl);

	if (parser.getErrorCount() > 0) {	   
	    if (gi != null) gi.OkDialog("Fehler", "Guard ist fehlerhaft.");
	    else System.out.println("Fehler: Guard ist fehlerhaft.");

	    return null;
        }
	else {
	    if (guard == null) guard = new GuardEmpty(new Dummy());
	    evlist = parser.getSEventList();
	    bvlist = parser.getBvarList();

	    return guard;
	}
    }

    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Guard.
     * <br>Die neuen SEvents/Bvars werden in die Listen des übergebenen Statecharts eingetragen
     * @param Referenz auf einen BufferedReader
     * @param Referenz auf Statechart
     * @return Liefert Guard oder null bei Fehler.
     */ 
  public Guard getGuard(BufferedReader br, Statechart sc) throws IOException {
	TESCParser parser = new TESCParser(br, gi);
	parser.initSwitches(options);

	Guard guard = parser.readGuard(br, sc);

	if (parser.getErrorCount() > 0) {	   
	    if (gi != null) gi.OkDialog("Fehler", "Guard ist fehlerhaft.");
	    else System.out.println("Fehler: Guard ist fehlerhaft.");

	    return null;
        }
	else {
	    if (guard == null) guard = new GuardEmpty(new Dummy());
	    evlist = parser.getSEventList();
	    bvlist = parser.getBvarList();

	    return guard;
	}
    }


  /**
   * <STRONG>NICHT</STRONG> verwenden, liefert null.
   * @return null
   */
  public TLabel getLabel(BufferedReader br, Statechart sc) {

    return null;
  }

    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Action.<br> <STRONG> Achtung: </STRONG> Der String MUSS mit einem ; abgeschlossen sein<br><STRONG>  Temporär!! </STRONG>
     * @param Referenz auf einen BufferedReader     
     * @return Liefert Action oder null bei Fehler.
     */ 
    public Action getAction(BufferedReader br) throws IOException {
	TESCParser parser = new TESCParser(br, gi);
	parser.initSwitches(options);
	Action action = parser.readAction(br);

	if (parser.getErrorCount() > 0) {	    
	    gi.OkDialog("Fehler", "Action ist fehlerhaft.");
	    return null;
        }
	else {
	    if (action == null) action = new ActionEmpty(new Dummy());
	    return action;
	}
    }

    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Action.<br> <STRONG> Achtung: </STRONG> Es werden nur Actionstatements akzeptiert, die mit einem ; abgeschlossen sind!<br> <STRONG>  Temporär!! </STRONG>
     * @param Referenz auf einen BufferedReader
     * @param Referenz auf SEventList
     * @param Referenz auf BvarList
     * @return Liefert Action oder null bei Fehler.
     */ 
    public Action getAction(BufferedReader br, SEventList el, BvarList bl) throws IOException {
	TESCParser parser = new TESCParser(br, gi);
	parser.initSwitches(options);
	Action action = parser.readAction(br, el, bl);

	if (parser.getErrorCount() > 0) {	    
	    if (gi != null) gi.OkDialog("Fehler", "Action ist fehlerhaft.");
	    else System.out.println("Fehler: Action ist fehlerhaft.");
	    return null;
        }
	else {
	    if (action == null) action = new ActionEmpty(new Dummy());

	    evlist = parser.getSEventList();
	    bvlist = parser.getBvarList();

	    return action;
	}
    }

    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Action.<br> <STRONG> Achtung: </STRONG> Es werden nur Actionstatements akzeptiert, die mit einem ; abgeschlossen sind!
     * <br>Die neuen SEvents/Bvars werden in die Listen des übergebenen Statecharts eingetragen
     * @param Referenz auf einen BufferedReader
     * @param Referenz auf Statechart
     * @return Liefert Action oder null bei Fehler.
     */ 
    public Action getAction(BufferedReader br, Statechart st) throws IOException {
	TESCParser parser = new TESCParser(br, gi);
	parser.initSwitches(options);
	Action action = parser.readAction(br, st);

	if (parser.getErrorCount() > 0) {	    
	    if (gi != null) gi.OkDialog("Fehler", "Action ist fehlerhaft.");
	    else System.out.println("Fehler: Action ist fehlerhaft.");
	    return null;
        }
	else {
	    if (action == null) action = new ActionEmpty(new Dummy());

	    evlist = parser.getSEventList();
	    bvlist = parser.getBvarList();

	    return action;
	}
    }
    /** 
     * Zugriff auf die SEventList des Parsers<br><STRONG>  Temporär!! </STRONG>
     * @return Liefert SEventList des Parsers.
     */ 
    public SEventList getSEventList() {
	return evlist;
    }

    /** 
     * Zugriff auf die BvarList des Parsers<br><STRONG>  Temporär!! </STRONG>
     * @return Liefert BvarList des Parsers.
     */ 
    public BvarList getBvarList() {
	return bvlist;
    }
}

/* 
 * $Log: not supported by cvs2svn $
 * Revision 1.17  1999/01/17 21:42:35  swtech13
 * Verbesserungen/Bugfixes
 *
 * Revision 1.14  1999/01/11 23:20:09  swtech13
 * ~ in Guards, bassign in Action wird jetzt erkannt
 * Doku angepasst
 *
 * Revision 1.13  1999/01/11 20:24:29  swtech13
 * Liefern jetzt Dummies, bei readAction/Guard(..), falls diese leer.
 *
 * Revision 1.12  1999/01/11 11:52:18  swtech13
 * Links korrigiert
 *
 * Revision 1.11  1999/01/09 15:51:14  swtech13
 * Schnittstelle erweitert, um methoden zum Parsen von Guards/Actions
 * Angabe von Optionen
 *
 * Revision 1.9  1999/01/05 20:58:15  swtech13
 *   - pathop -> absolute Pfade
 *   - Keine Action angegeben -> new ActionEmpty(new Dummy())
 *   - Schaltermechanismus eingebaut.
 *   - debugmechanismus eingebaut.
 *
 * Revision 1.8  1999/01/04 16:12:01  swtech13
 * Locations hinzugefuegt. Pruefen auf Keywords.
 *
 * Revision 1.7  1998/12/21 16:17:35  swtech13
 * Fehlermeldungen im Parser -> GUI
 *
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
