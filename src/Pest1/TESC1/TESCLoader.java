package tesc1;

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
 * <li> TestBVars.pruefeBVar(...)    // Allerdings sollte gecheckt werden, ob �berfl�ssige BVars vorhanden
 * <li> TestEvents.pruefeEvent(...)  // Allerdings sollte gecheckt werden, ob �berfl�ssige Events vorhanden
 * <li> TestPI                       // Falls wir das richtig verstanden haben ..
 * </ul>
 * 
 * <STRONG> Anforderungen. </STRONG> <br>
 * Wir verlassen uns darauf, dass die
 * Statecharts, die uns uebergeben werden, folgende Eigenschaften haben: 
 * 
 * <ul>
 * <li> Diese Klasse erh�lt keine Statecharts
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
 * Der Parser ist fertig. <br>
 * In tesc1/Test befinden sich mehrere Testdateien. <br>
 * Diese koennen ueber das GUI-Fenster mittels Import->TESC geladen werden. <br>
 * Die TESC-Sprache ist �ber die <A HREF="./tesc1/Docu/grammatik.txt">Grammatik</A> definiert. 
 * Siehe auch <A HREF="./tesc1/Docu/Doku.txt"> Doku.txt</A> <br>
 * Beispiel <A HREF="./tesc1/Test/example.tesc"> example.tesc</A>
 * <br>
 *
 * </DL COMPACT>
 * <br>
 * <hr>
 * @author Arne Koch/Mike Rumpf.
 * @version  $Id: TESCLoader.java,v 1.25 1999-02-17 21:51:33 swtech13 Exp $ 
 */ 
public class TESCLoader {

    private BufferedReader is;		// InputStream
    private Statechart stchart;		// der aufzubauende Statechart
    private TESCParser parser;          // der Parser
    private GUIInterface gi;            // GUI
    private Vector options = null;             // enth�lt Optionen
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
     * Umwandeln eines  TESC-File aus BufferedReader in Guard.
     * <br>Die neuen SEvents/Bvars werden in die Listen des �bergebenen Statecharts eingetragen
     * @param Referenz auf einen BufferedReader
     * @param Referenz auf Statechart
     * @return Liefert Guard oder null bei Fehler.
     */ 
  protected Guard getGuard(BufferedReader br, Statechart sc) throws IOException {
	TESCParser parser = new TESCParser(br, gi);

	if (parser != null) {
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
	else 
	    return null;
    }


    /**
     * Liefert um SyntaxBaum (aus tl.caption berechnet) erweitertes TLabel
     * @return TLabel oder null bei Fehler
     */
    public TLabel getLabel(TLabel tl_, Statechart sc) {

	String G = getG(tl_.caption);
	String A = getA(tl_.caption);

	// Vorherige Informationen erhalten
	TLabel tl = new TLabel(tl_.guard, tl_.action, tl_.position, tl_.location, tl_.caption);

	try {
	    if (G != null)
		tl.guard = getGuard(new BufferedReader(new StringReader(G)), sc);
	    if (A != null)
		tl.action = getAction(new BufferedReader(new StringReader(A)), sc);
	}
	catch (IOException ioe) {
	    tl = null;
	}

	return tl;
    }
    

     // liefert den Guard-Teil des Labels
    private String getG(String str) {
	String s = "~";

	int pos = str.indexOf('/');
	if (pos!=-1) {
	    s = str.substring(0,pos);
	    
	}
	else {
	    s = str;
	}

	if (s == null)
	    s = new String("~");

	// Haben Probleme, falls L�nge == 1
	return new String(s + " ");
    }

    // liefert den Action-Teil des Labels 
    private String getA(String str) {
	String s = "~";

	int pos = str.indexOf('/');
	if (pos!=-1) {
	    s = str.substring(pos+1);
	    
	}

	// Action muss mit ";" enden; liegt am Parser
	return new String(s + ";");
    }

    /** 
     * Umwandeln eines  TESC-File aus BufferedReader in Action.<br> <STRONG> Achtung: </STRONG> Es werden nur Actionstatements akzeptiert, die mit einem ; abgeschlossen sind!
     * <br>Die neuen SEvents/Bvars werden in die Listen des �bergebenen Statecharts eingetragen
     * @param Referenz auf einen BufferedReader
     * @param Referenz auf Statechart
     * @return Liefert Action oder null bei Fehler.
     */ 
    protected Action getAction(BufferedReader br, Statechart st) throws IOException {
	TESCParser parser = new TESCParser(br, gi);
	if (parser != null) {
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
	else {
	    return null;
	}
    }
}

/* 
 * $Log: not supported by cvs2svn $
 * Revision 1.24  1999/02/09 13:38:55  swtech13
 * Fehler 56 fixed
 *
 * Revision 1.23  1999/02/08 14:45:06  swtech13
 * Aufraeumen der Schnittstellen
 *
 * Revision 1.22  1999/01/25 13:27:49  swtech13
 * debug auskommentiert
 *
 * Revision 1.21  1999/01/20 22:07:53  swtech13
 * Haesslichen Fehler in getLabel(TLabel,...) beseitigt
 *
 * Revision 1.18  1999/01/18 12:27:54  swtech13
 * Schnittstellen-Anpassung
 *
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
