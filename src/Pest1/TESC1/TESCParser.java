package tesc1;

import java.util.*;
import absyn.*;
import java.io.*;
import gui.*;
import java.lang.*;
import util.*;

/* Konventionen:
 * Kommentare enthalten:
 *
 * Temporaere Features: temporar
 * Debug      ---"--- : debug
 * Evtl. Fehler       : ???
 *
 */


/* Todo/Fragen
 * + Was enthält Path? Nur States oder auch Cons? - Nur States !
 * - Es wird immer ein ActionBlock zurückgeliefert
 * + Kann es mehr als einen defcon geben ? Selbst entscheiden. <- nur einer
 * + PathList der Größe nach sortieren
 * + Ist Basic_State als root erlaubt ? - Ja
 * + is_*name über Pfade prüfen !!
 * + Statenamelist in ostate fehlt noch ! <- defcons
 * - Undet bei Guards
 * + Fehlerbehandlung
 * - Wenn in der Tescdatei der Rootzustand geparst wird,
 *   werden evt. noch dahinterstehende Zeilen vom Parser ignoriert.
 *   (eigentlich nicht unbedingt ein Fehler, vielleicht unschoen?)
 * + Bei Ident mit Bezug auf vorhandenes Obejkt immer prüfen, ob auch vorhanden s.u.
 * - Bei Guards: ... on ; liefert GuardEmpty(..). Wg. Editor, falls User nichts angeben will.
 *   Ist das Ok, oder soll es Fehlermeldung geben?
 */


class TESCParser {


    private Statechart stchart;
    private int errorCount = 0;   // Anzahl der aufgetretenen Fehler
    private Vector errorList;     // Fehlerliste
    private GUIInterface gi;
    private TESCScanner ts;
    private TOKEN tok;

    private Vector snlist;        // Liste der bekannten Statenames
    private Vector cnlist;        // Liste der bekannten Connames
    //private Vector bnlist;        // Liste der bekannten Bvarnames / temporar
    //private Vector enlist;        // Liste der bekannten Eventnames / temporar

    private Vector path;          // enthält die Pfade

    private Vector switches;      // temporar

    private BufferedReader is;

    SEventList evlist;
    BvarList   bvlist;
    StringBuffer tl_caption;

    // protected-Methoden für das Package
	
    protected TESCParser(BufferedReader is_, GUIInterface gi_) {
	gi = gi_;
	is = is_;

	errorList = new Vector();
	snlist = new Vector();
	cnlist = new Vector();
	//bnlist = new Vector();
	//enlist = new Vector();

	evlist = null;
	bvlist = null;

	switches = new Vector();
	initSwitches();
	

	path = new Vector();
    }

    protected Statechart parse() {

	ts = new TESCScanner(is);

	stchart = (Statechart) null;

	try {
	    stchart = buildStatechart();
	}
	catch (IOException e) {
	    // Hier in Fehlervektor eintragen?
	    //System.out.println("Ärger!");
	    addError("Tesc1: Lesefehler im Reader");
	    gi.OkDialog((String) "Scanner: IOError", (String) "Fehler - bei Lesen des Streams!");
	}

	if (errorCount>0) {
           outputErrors();
           return null;
	}
        else
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


    // sobald Einstellungsdlge verfügbar sind, können die Einstellungen 
    // hiermit an den Parser übergeben werden.
    protected void initSwitches(Vector v) {
	if (v!=null) switches = v;
	else initSwitches();
    }

    protected Action readAction(BufferedReader br) throws IOException {
	Action act = null;

	ts = new TESCScanner(br);

	tok = ts.nextToken();
	tl_caption = new StringBuffer("/");

	act = new ActionBlock(actionlist(null));

	// Falls jetzt noch etwas != ; kommt, ist das falsch
	if (tok.token == vTOKEN.SCOLON) {
	    match(vTOKEN.SCOLON);
	    // Falls jetzt noch etwas kommt, ist das falsch
	    if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));
	}
	else {
	    addError(makeError(tok,"Unerwartetes Token"));
	}

	if (errorCount>0) {
           outputErrors();
	}

	return act;
    }

    protected Action readAction(BufferedReader br, SEventList el, BvarList bl) throws IOException {
	Action act = null;

	evlist = el;
	bvlist = bl;

	ts = new TESCScanner(br);
	tok = ts.nextToken();
	tl_caption = new StringBuffer("/");

	act = new ActionBlock(actionlist(null));

	// Falls jetzt noch etwas != ; kommt, ist das falsch
	if (tok.token == vTOKEN.SCOLON) {
	    match(vTOKEN.SCOLON);
	    // Falls jetzt noch etwas kommt, ist das falsch
	    if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));
	}
	else {
	    addError(makeError(tok,"Unerwartetes Token"));
	}

	
	if (errorCount>0) {
           outputErrors();
	}

	return act;
    }

    
    protected Action readAction(BufferedReader br, Statechart st) throws IOException {
	Action act = null;

	evlist = st.events;
	bvlist = st.bvars;

	ts = new TESCScanner(br);
	tok = ts.nextToken();
	tl_caption = new StringBuffer("/");

	act = new ActionBlock(actionlist(null));

	// Falls jetzt noch etwas != ; kommt, ist das falsch
	if (tok.token == vTOKEN.SCOLON) {
	    match(vTOKEN.SCOLON);
	    // Falls jetzt noch etwas kommt, ist das falsch
	    if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));
	}
	else {
	    addError(makeError(tok,"Unerwartetes Token"));
	}

	
	if (errorCount>0) {
           outputErrors();
	}

	st.events = evlist;
	st.bvars  = bvlist;

	return act;
    }

    // ohne Anlegen neuer Listen
    protected Guard readGuard(BufferedReader br) throws IOException {
	Guard grd = null;

	debug("readGuard(BufferedReader br)");

	ts = new TESCScanner(br);

	tok = ts.nextToken();

	tl_caption = new StringBuffer();
	grd = guard(null);

	// Falls jetzt noch etwas kommt, ist das falsch
	if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));

	if (errorCount>0) {
           outputErrors();
	}

	return grd;
    }

    // legt neue Listen an
    protected Guard readGuard(BufferedReader br, SEventList el, BvarList bl) throws IOException {
	Guard grd = null;

	debug("readGuard(BufferedReader br, SEventList el, BvarList bl)");

	evlist = el;
	bvlist = bl;

	ts = new TESCScanner(br);

	tok = ts.nextToken();

	tl_caption = new StringBuffer();
	grd = guard(null);


	// Falls jetzt noch etwas kommt, ist das falsch
	if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));

	if (errorCount>0) {
           outputErrors();
	}
	
	return grd;
    }

    // legt neue Listen an
    protected Guard readGuard(BufferedReader br, Statechart st) throws IOException {
	Guard grd = null;

	debug("readGuard(BufferedReader br, SEventList el, BvarList bl)");

	evlist = st.events;
	bvlist = st.bvars;

	ts = new TESCScanner(br);

	tok = ts.nextToken();

	tl_caption = new StringBuffer();
	grd = guard(null);


	// Falls jetzt noch etwas kommt, ist das falsch
	if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));

	if (errorCount>0) {
           outputErrors();
	}
	
	st.events = evlist;
	st.bvars  = bvlist;

	return grd;
    }

    protected String getTL_CaptionList() {
	return tl_caption.toString();
    }

    protected SEventList getSEventList() {
	return evlist;
    }

    protected BvarList getBvarList() {
	return bvlist;
    }

    // private Methoden

    private void initSwitches() {
      //switches.addElement("debug");
	//switches.addElement("jumpAfterError");
    }

    /** Das erste Token kann sein:
     *  - events
     *  - bvars
     *  - or | and | basic
     */
    private Statechart buildStatechart() throws IOException {

	State st = null;
	PathList plist = null;

	StringBuffer f = new StringBuffer();

	tok = ts.nextToken();


	//sollte jetzt ueberfluesig sein.
	if (tok.token == vTOKEN.EVENTS) {
	    evlist = events();
	}
	if (tok.token == vTOKEN.BVARS) {
	    bvlist = bvars();
	}

	st = state();

	plist = makePathlist();

	return new Statechart(evlist, bvlist, plist, st);
    }

    
    // events : temporar
    private SEventList events() throws IOException {
	SEventList evlist = null;
	
	match(vTOKEN.EVENTS);	
	match(vTOKEN.COLON);

        evlist = eventlist();
	    
	return evlist;
    }
    
    // bvars : temporar
    private BvarList bvars() throws IOException {
	BvarList bvlist = null;

	match(vTOKEN.BVARS);
	match(vTOKEN.COLON);

	bvlist = bvarlist();
	 
	return bvlist;
    }

    // eventliste aufbauen temporar
    private SEventList eventlist() throws IOException {
	SEvent ev;
	SEventList evlist = null;
	boolean b;
	Location loc = new Location(tok.linenum);

        if (tok.token==vTOKEN.IDENT) {
	    ev = new SEvent(tok.value_str);
	    ev.location = loc;
	    
	    match(vTOKEN.IDENT);
	
	    if (tok.token==vTOKEN.COMMA) {
		b = match(vTOKEN.COMMA);
		if (b) evlist = new SEventList( ev, eventlist());
	    }
	    else if (tok.token == vTOKEN.SCOLON) {
	        b = match(vTOKEN.SCOLON);
                if (b) evlist =  new SEventList( ev, null);
	    }
	    else {
		addError(makeError(tok,"Fehler in Eventliste"));
	    }	
	}
	else {
	    addError(makeError(tok,"Fehler in Eventliste"));
	}

	return evlist;
    }
    

    // bvarliste aufbauen temporar
    private BvarList bvarlist() throws IOException {
	Bvar bv;
	BvarList bvlist = null;	
	Location loc = new Location(tok.linenum);

        if (tok.token==vTOKEN.IDENT) {
	    bv = new Bvar(tok.value_str);
	    bv.location = loc;
	    
	    match(vTOKEN.IDENT);
	
	    if (tok.token==vTOKEN.COMMA) {
		match(vTOKEN.COMMA);
		bvlist = new BvarList( bv, bvarlist());
	    }
	    else if (tok.token == vTOKEN.SCOLON) {
	        match(vTOKEN.SCOLON);
                bvlist =  new BvarList( bv, null);
	    }
	    else {
		addError(makeError(tok,"Fehler bei boolscher Variablenliste"));
	    }
	
	}

	return bvlist;
    }
    

    // liefert den root-state
    private State state() throws IOException {
	
	State st = null;
	Location loc = new Location(tok.linenum);

        if (tok.token==vTOKEN.BSTATE) {
	    match(vTOKEN.BSTATE);

	    st = bstate(null);
        }
	else if (tok.token==vTOKEN.OSTATE) {
	    match(vTOKEN.OSTATE);

	    st = ostate(null);
	}
	else if (tok.token==vTOKEN.ASTATE) {
	    match(vTOKEN.ASTATE);

	    st = astate(null);
	}
	else if (tok.token==vTOKEN.IDENT) {
	    addError(makeError(tok,"Zustandsdeklaration erwartet"));
	}

	if (st != null) st.location = loc;

	return st;
    }

    private Basic_State bstate(Path p) throws IOException {
	Statename     sn      = null;
	Basic_State   bs      = null;
	Path pth = null;
	Location loc = new Location(tok.linenum);

	if (tok.token == vTOKEN.IDENT) {
	    sn = new Statename (tok.value_str);
	    sn.location = loc;
	    addStatename(tok.value_str, p);

	    if (p == null) {
		// root
		pth = new Path(tok.value_str, null);
	    }
	    else {
		pth = p.append(tok.value_str);
	    }
	    addPath(pth);

	    match(vTOKEN.IDENT);
	    match(vTOKEN.SCOLON);

	    bs = new Basic_State(sn);
	    bs.location = loc;
	}
	else
	    addError(makeError(tok,"Identifier erwartet"));

	return bs;
    }

    private Or_State ostate(Path p) throws IOException {
	Or_State      os      = null;
	Statename     sn      = null;
	StateList     slist   = null;
	TrList        trlist  = null;
	StatenameList snlist  = null;
	ConnectorList clist = null;
	Path pth = null;

	String s = null;
	Location loc = new Location(tok.linenum);

	if (tok.token == vTOKEN.IDENT) {
	    sn = new Statename (tok.value_str);
	    sn.location = loc;
	    // Namen merken
	    s = new String(tok.value_str);
	    // In Liste einfügen
	    addStatename(tok.value_str, p);

	    if (p == null) {
		// root
		pth = new Path(tok.value_str, null);
	    }
	    else {
		pth = p.append(tok.value_str);
	    }
	    addPath(pth);


	    match(vTOKEN.IDENT);
	    match(vTOKEN.COLON);

	    // Liste der States innerhalb dieses States 
	    int le = errorCount;
	    slist = states(pth);
	    le = errorCount - le;

	    if (le>0 && switches.contains("jumpAfterError")) {
		debug("Aha");
		findNextTok(vTOKEN.END);
		match(vTOKEN.END);
		findNextTok(vTOKEN.END);
	    }

	    // Falls defcon, snlist aufbauen
	    if (tok.token == vTOKEN.DEFCON) {
		snlist = defcons(pth);
		
	    }

	    // Falls danach Connectors, clist aufbauen
	    if (tok.token == vTOKEN.CONS) {
		clist = cons(pth);
		
	    }

	    // Falls danach Transitionen, trlist aufbauen
	    if (tok.token == vTOKEN.TRANSITIONS) {
		trlist = trdef(pth);
		
	    }
	    
	    //snlist = makeStatenamelist();

	    os = new Or_State(sn, slist, trlist, snlist, clist);
	    os.location = loc;

	    match(vTOKEN.END);

	    if (tok.token == vTOKEN.IDENT) {
		if (s.compareTo(tok.value_str) != 0) {
		    //Error		    
		    StringBuffer sb = new StringBuffer();
		    sb.append("Name ungleich -> ");
		    sb.append(s);
		    sb.append(" <> ");
		    sb.append(tok.value_str);
		    addError(makeError(tok, sb.toString()));
		}
		else {
		    match(vTOKEN.IDENT);
		    match(vTOKEN.SCOLON);
		}
	    }
	    
	}
	else
	    addError(makeError(tok,"Identifier erwartet"));
	return os;
    }

    private And_State astate(Path p) throws IOException {
	And_State      as      = null;
	Statename     sn      = null;
	StateList     slist   = null;	
	StatenameList snlist  = null;
	Path pth = null;

	String s = null;
	Location loc = new Location(tok.linenum);

	if (tok.token == vTOKEN.IDENT) {
	    sn = new Statename (tok.value_str);
	    sn.location = loc;
	    // Namen merken
	    s = new String(tok.value_str);
	    // In Liste einfügen
	    addStatename(tok.value_str, p);

	    if (p == null) {
		// root
		pth = new Path(tok.value_str, null);
	    }
	    else {
		pth = p.append(tok.value_str);
	    }
	    addPath(pth);

	    match(vTOKEN.IDENT);
	    match(vTOKEN.COLON);
	    
	    slist = astates(pth);
	    
	    as = new And_State(sn, slist);
	    as.location = loc;

	    match(vTOKEN.END);

	    if (tok.token == vTOKEN.IDENT) {
		if (s.compareTo(tok.value_str) != 0) {
		    //Error
		    
		    StringBuffer sb = new StringBuffer();
		    sb.append("Name ungleich -> ");
		    sb.append(s);
		    sb.append(" <> ");
		    sb.append(tok.value_str);
		    addError(makeError(tok, sb.toString()));
		}
		else {
		    match(vTOKEN.IDENT);
		    match(vTOKEN.SCOLON);
		}
	    }
	    
	}
	else
	    addError(makeError(tok,"Identifier erwartet"));
	return as;
    }


    // liefert einen State innerhalb eines And_States
    private StateList astates(Path p) throws IOException {
	StateList sl = null;
	boolean b = false;
	//Path pth = null;
	Location loc = new Location(tok.linenum);

        if (tok.token == vTOKEN.BSTATE) {
	    b = match(vTOKEN.BSTATE);
	    if (b) {
		sl = new StateList(bstate(p), astates(p));
		
	    }
	    else {
		sl = null;	
	    }
	    
        }
	else if (tok.token == vTOKEN.OSTATE) {
	    b = match(vTOKEN.OSTATE);
	    
	    if (b) {
		sl = new StateList(ostate(p), astates(p));
		
	    }
	    else {
		sl = null;	
	    }

	}
	else if (tok.token == vTOKEN.END) {
	    sl = null;
	}
	else {
	    addError(makeError(tok,"Fehler in Zustandsliste"));
	}
	
	return sl;
    }

    // connectors : 
    private ConnectorList cons(Path p) throws IOException {
	boolean b = false;
	ConnectorList clist = null;
	
	b = match(vTOKEN.CONS);
	b = match(vTOKEN.COLON);

	if (b) {
	    clist = conlist(p);
	   
	}

	return clist;
    }
    
    // conliste aufbauen
    private ConnectorList conlist(Path p) throws IOException {
	Connector con;
	ConnectorList clist = null;
	Location loc = new Location(tok.linenum);

        if (tok.token==vTOKEN.IDENT) {
	    con = new Connector((Conname)setLoc(new Conname(tok.value_str), loc));
	    con.location = loc;

	    addConname(tok.value_str, p);
	    match(vTOKEN.IDENT);
	
	    if (tok.token==vTOKEN.COMMA) {
	       
		match(vTOKEN.COMMA);
		clist = new ConnectorList( con, conlist(p));
		
	    }
	    else if (tok.token == vTOKEN.SCOLON) {
	        match(vTOKEN.SCOLON);
                clist =  new ConnectorList( con, null);
		
	    }
	    else {
		addError(makeError(tok,"Fehler in Konnektorenliste"));
	    }
	
	}

	return clist;
    }
    
    private StatenameList defcons(Path p) throws IOException {
	boolean b = false;
        StatenameList snlist = null;
	Statename sn = null;
	Location loc = new Location(tok.linenum);

	b = match(vTOKEN.DEFCON);
	b = match(vTOKEN.COLON);

	if (b) {
	    if (tok.token == vTOKEN.IDENT) {
		sn = new Statename(tok.value_str);
		sn.location = loc;
		snlist = new StatenameList(sn, null);
		
		match(vTOKEN.IDENT);
	    }
	    else {
		addError(makeError(tok,"Fehler bei Defaultkonnektoren"));
	    }
	    match(vTOKEN.SCOLON);
	}

	return snlist;
    }


    private Action actionstmt (Path p) throws IOException {
	Action act = null;
	//boolean b = false;
	Aseq al = null;

	if (tok.token == vTOKEN.DO) {
	    match(vTOKEN.DO);
	    Location loc = new Location(tok.linenum);
	    // Auch ActionBlock bei nur einer Action ??
	    al = actionlist(p);
	    
	    act = new ActionBlock(al);
	    act.location = loc;
	}
	else {
	    // keine Action angegeben
	    Location loc = new Location(tok.linenum);
	    act = new ActionEmpty((Dummy)setLoc(new Dummy(), loc));
	    act.location = loc;
	}

	return act;
    }

    private Aseq actionlist(Path p) throws IOException {
	Aseq as = null;
	Action a;
	
	Location loc = new Location(tok.linenum);
	a = aktion(p);
	if (tok.token == vTOKEN.COMMA) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.COMMA);
	    //as = (Aseq)setLoc(new Aseq(a, actionlist(p)), loc);
	    as = new Aseq(a, actionlist(p));
	    
	}
	else if (tok.token == vTOKEN.SCOLON) {
	    // ??? !!!
	    if (a == null) a = new ActionEmpty((Dummy)setLoc(new Dummy(), loc), loc);
	    as =  new Aseq( a, null);
	    //as.location = loc;
	}

	return as;
    }

    private Action aktion(Path p) throws IOException {
	Action a = null;
	Location loc = new Location(tok.linenum);

	if (tok.token == vTOKEN.IDENT) {
	

	    // Das ist unschoen, aber wir haben kein lookahead
	    String s = new String(tok.value_str);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.IDENT);

	    if (tok.token == vTOKEN.BASSIGN) {
		a = bassign(p, s);
	    }
	    else {
		SEvent ev = (SEvent)setLoc(new SEvent(s), loc);
		a = new ActionEvt(ev);
		a.location = loc;
		
		if( !is_eventname(s)) {
		    if (!is_bvarname(s)) {
			debug("Füge neues SEvent in SEventList ein!");
			evlist = new SEventList( (SEvent)setLoc(new SEvent(s), loc), evlist);
		    }
		    else {
			addError(makeError(tok,"Ist schon als Bvarname deklariert!"));
		    }
		}
	    }

	   
	}
	
	else if (tok.token == vTOKEN.EMPTYEXP) {
	    a = new ActionEmpty((Dummy)setLoc(new Dummy(), loc));
	    a.location = loc;
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.EMPTYEXP);
	}
	
	return a;
    }

    // unschoen, aber wir haben kein lookahead
    private Action bassign(Path p, String s) throws IOException {
	Action a = null;
	Bvar bv = null;
	Location loc = new Location(tok.linenum);

	//if (tok.token == vTOKEN.IDENT) {	    

	    bv = new Bvar(s);
	    bv.location = loc;

	    
	    if (!is_bvarname(s)) {
		if (!is_eventname(s)) {
		    debug("Füge neue Bvar in BvarList ein!");
		    bvlist = new BvarList((Bvar)setLoc(new Bvar(s), loc), bvlist);
		}
		else {
			addError(makeError(tok,"Ist schon als Eventname deklariert!"));
		    }	
	    }

	    //match(vTOKEN.IDENT);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.BASSIGN);
	    
	    // boolop
	    if (tok.token == vTOKEN.TRUE) {
		loc = new Location(tok.linenum);
		a = new ActionStmt((MTrue)setLoc(new MTrue(bv), loc));
		a.location = loc;
		tl_caption.append(tok.value_str);
		match(vTOKEN.TRUE);
	    }
	    else if (tok.token == vTOKEN.FALSE) {
		loc = new Location(tok.linenum);
		a = new ActionStmt((MFalse)setLoc(new MFalse(bv), loc));
		a.location = loc;
		tl_caption.append(tok.value_str);
		match(vTOKEN.FALSE);
	    }
	    else {
		loc = new Location(tok.linenum);
		a = new ActionStmt((BAss)setLoc(new BAss((Bassign)setLoc(new Bassign(bv, guard_b(p)),loc) ), loc));
		a.location = loc;
	    }
	    //}

	return a;
    }
        
    

    // Guards
    private Guard guard(Path p) throws IOException {
	Guard grd_e = null;
	Guard grd_b = null;
	Guard grd   = null;
	Location loc = new Location(tok.linenum);

	debug("guard()");

	grd_e = guard_e(p);
	if (tok.token == vTOKEN.LPAR_E) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.LPAR_E);
	    grd_b = guard_b(p);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.RPAR_E);
	}
	
	if (grd_b == null) {
	    grd = grd_e;
	}
	else {
	    if (grd_e != null)
		grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.AND, grd_e, grd_b), loc));
	    else 
		grd = grd_b;

	    grd.location  = loc;
	}
	
	if (grd == null) grd = new GuardEmpty((Dummy)setLoc(new Dummy(), loc)); 

	return grd;
    }
    

    private Guard guard_e(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	debug("guard_e()");
	
	grd1 = ocompg_e(p);
	grd = grd1;	

	while (tok.token == vTOKEN.IMPL || tok.token == vTOKEN.AQUI) {
	    t = tok.token;
	    switch(t) {
	    case vTOKEN.IMPL:
		tl_caption.append(tok.value_str);
		match(vTOKEN.IMPL);
		break;
	    case vTOKEN.AQUI:	
		tl_caption.append(tok.value_str);
		match(vTOKEN.AQUI);
		break;
	    }
	    grd2 = ocompg_e(p);
	    switch(t) {
	    case vTOKEN.IMPL:
		grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.IMPLIES, grd1, grd2), new Location(tok.linenum)));
		grd.location = loc;
		break;
	    case vTOKEN.AQUI:	
		grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.EQUIV, grd1, grd2), new Location(tok.linenum)));
		grd.location = loc;
		break;
	    }
	}

	return grd;	
    }

    // ||
    private Guard ocompg_e(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	debug("oguard_e()");

	grd1 = acompg_e(p);
	grd = grd1;
	while (tok.token == vTOKEN.OR) {	    	
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.OR);
	    grd2 = acompg_e(p);
	    	
	    grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.OR, grd1, grd2), loc));
	    grd.location = loc;
	}

	return grd;
    }
    
    // &&
    private Guard acompg_e(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	debug("aguard_e()");

	grd1 = kcompg_e(p);
	grd = grd1;
	while (tok.token == vTOKEN.AND) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.AND);
	    grd2 = kcompg_e(p);
	    	
	    grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.AND, grd1, grd2), loc));
	    grd.location = loc;
	}
	

	return grd;
    }

    // Klammern und nicht-reduzierbares
    private Guard kcompg_e(Path p) throws IOException {	
	Guard grd = null;
	Location loc = new Location(tok.linenum);

	debug("kguard_e()");

	if (tok.token == vTOKEN.NOT) {
	    // ???
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.NOT);
	    grd = new GuardNeg(kcompg_e(p));
	}
	else if(tok.token == vTOKEN.EMPTYEXP) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.EMPTYEXP);
	    // Rekursion ??? oder kann ~ nur für sich stehen ?
	    grd = new GuardEmpty((Dummy)setLoc(new Dummy(), loc)); 
	}	
	else if (tok.token == vTOKEN.IDENT) {
	    // ???
	    SEvent ev = (SEvent)setLoc(new SEvent(tok.value_str), loc);

	    // falls neues event, evlist erweitern
	    if (!is_eventname(tok.value_str)) {
		if (!is_bvarname(tok.value_str)) {
		    debug("Füge neues SEvent in evlist ein!");
		    evlist = new SEventList( (SEvent)setLoc(new SEvent(tok.value_str), loc), evlist);
		}
		else {
			addError(makeError(tok,"Ist schon als Bvarname deklariert!"));
		    }
	    } 
	    
	    grd = new GuardEvent(ev);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.IDENT);
	}
	else if (tok.token == vTOKEN.LPAR) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.LPAR);
	    grd = guard_e(p);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.RPAR);
	}
	/*
	else {
	    // ??? !!!
	    if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));
	}
	*/
	if (grd != null) grd.location = loc;
	
	return grd;

    }


    // 
    private Guard pathop(Path actPath) throws IOException {
	Guard grd = null;
	Location loc = new Location(tok.linenum);

	//if (actPath==null) addError(makeError(tok, "Internal Error!"));

	switch(tok.token) {
	case vTOKEN.IN:
	    tl_caption.append(tok.value_str);
	    match (vTOKEN.IN);
	    tl_caption.append(tok.value_str);
	    match (vTOKEN.LPAR);
	    //grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.IN, actPath.append(tok.value_str)), loc));
	    
	    grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.IN, StringToPath(tok.value_str)), loc));
	    
	    grd.location = loc;
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.IDENT);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.RPAR);
	    break;
	case vTOKEN.ENTERED:
	    tl_caption.append(tok.value_str);
	    match (vTOKEN.ENTERED);
	    tl_caption.append(tok.value_str);
	    match (vTOKEN.LPAR);
	    //grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.ENTERED, actPath.append(tok.value_str)), loc));
	    
	    grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.ENTERED, StringToPath(tok.value_str)), loc));
	    
	    grd.location = loc;
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.IDENT);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.RPAR);
	    break;
	case vTOKEN.EXITED:
	    tl_caption.append(tok.value_str);
	    match (vTOKEN.EXITED);
	    tl_caption.append(tok.value_str);
	    match (vTOKEN.LPAR);
	    //grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.EXITED, actPath.append(tok.value_str)), loc));
	    
	    grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.EXITED, StringToPath(tok.value_str)), loc));
	    
	    grd.location = loc;
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.IDENT);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.RPAR);
	    break;
	}

	return grd;
    }
    

    // Guards fuer Bvars
    private Guard guard_b(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	grd1 = ocompg_b(p);
	grd = grd1;	

	while (tok.token == vTOKEN.IMPL || tok.token == vTOKEN.AQUI) {
	    t = tok.token;
	    switch(t) {
	    case vTOKEN.IMPL:
		tl_caption.append(tok.value_str);
		match(vTOKEN.IMPL);
		break;
	    case vTOKEN.AQUI:	
		tl_caption.append(tok.value_str);
		match(vTOKEN.AQUI);
		break;
	    }
	    grd2 = ocompg_b(p);
	    switch(t) {
	    case vTOKEN.IMPL:
		grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.IMPLIES, grd1, grd2), new Location(tok.linenum)));
		grd.location = loc;
		break;
	    case vTOKEN.AQUI:	
		grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.EQUIV, grd1, grd2), new Location(tok.linenum)));
		grd.location = loc;
		break;
	    }
	}

	return grd;
    }

    // ||
    private Guard ocompg_b(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	grd1 = acompg_b(p);
	grd = grd1;
	while (tok.token == vTOKEN.OR) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.OR);
	    grd2 = acompg_b(p);
	    	
	    grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.OR, grd1, grd2), loc));
	    grd.location = loc;
	}

	return grd;
    }
    
    // &&
    private Guard acompg_b(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	grd1 = kcompg_b(p);
	grd = grd1;
	while (tok.token == vTOKEN.AND) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.AND);
	    grd2 = kcompg_b(p);
	    	
	    grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.AND, grd1, grd2), loc));
	    grd.location = loc;
	}
	

	return grd;
    }

    // Klammern und nicht-reduzierbares
    private Guard kcompg_b(Path p) throws IOException {	
	Guard grd = null;
	Location loc = new Location(tok.linenum);
	
	if (tok.token == vTOKEN.NOT) {
	    // ???
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.NOT);
	    grd = new GuardNeg(kcompg_b(p));
	}
	else if(tok.token == vTOKEN.EMPTYEXP) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.EMPTYEXP);
	    // Rekursion ??? oder kann ~ nur für sich stehen ?
	    grd = new GuardEmpty((Dummy)setLoc(new Dummy(), loc)); 
	}
	else if(tok.token == vTOKEN.IN || tok.token == vTOKEN.ENTERED || tok.token == vTOKEN.EXITED) {
	    grd = pathop(p);
	}
	else if (tok.token == vTOKEN.IDENT) {
	    // ???
	    Bvar bv = (Bvar)setLoc(new Bvar(tok.value_str), loc);
	    // bvlist erweitern, falls neue Bvar
	    if (!is_bvarname(tok.value_str)) {
		if (!is_eventname(tok.value_str)) {
		    debug("Füge neue Bvar in bvlist ein!");
		    bvlist = new BvarList( (Bvar)setLoc(new Bvar(tok.value_str), loc), bvlist);
		}
		else {
			addError(makeError(tok,"Ist schon als Eventname deklariert!"));
		}
	    }
	    grd = new GuardBVar(bv);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.IDENT);
	}
	else if (tok.token == vTOKEN.LPAR) {
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.LPAR);
	    grd = guard_b(p);
	    tl_caption.append(tok.value_str);
	    match(vTOKEN.RPAR);
	}
	/*
	else {
	    // ??? !!!
	    if (tok.token != vTOKEN.EOF) addError(makeError(tok,"Unerwartetes Token"));
	}
	*/
	if (grd != null) grd.location = loc;
	
	return grd;

    }


    // Transitionen
    private TrList trdef(Path p) throws IOException {
	TrList trlist = null;

	match(vTOKEN.TRANSITIONS);
	match(vTOKEN.COLON);

	trlist = translist(p);

	return trlist;
    }
    
    
    // Eine Transition aufbauen
    private Tr trans(Path p) throws IOException {
	Tr tr = null;
	TrAnchor t1, t2;
	Guard grd;
	Action act;

	Location loc = new Location(tok.linenum);
	match(vTOKEN.FROM);
        
	t1 = sname(p);
	
	match(vTOKEN.TO);
		
	t2 = sname(p);
		
	match(vTOKEN.ON);

	tl_caption = new StringBuffer();

	grd = guard(p);
	
	tl_caption.append("/");
	act = actionstmt(p);
	
	tr = new Tr(t1, t2, new TLabel(grd, act, null, loc, tl_caption.toString()));

	tr.location = loc;
	match(vTOKEN.SCOLON);
	    

	return tr;
    }

    // TrAnchor
    private TrAnchor sname(Path p) throws IOException {
	TrAnchor ta = null;
	Location loc = new Location(tok.linenum);

	if (tok.token == vTOKEN.UNDEF) {
	    match(vTOKEN.UNDEF);
	    ta = new UNDEFINED();
	    ta.location = loc;
	}
	else if (tok.token == vTOKEN.IDENT) {
	    // Con oder State
	    if (is_conname(tok.value_str, p)) {
		ta = new Conname(tok.value_str);
		ta.location = loc;
		match(vTOKEN.IDENT);
	    }
	    else if(is_statename(tok.value_str, p)) {
		ta = new Statename(tok.value_str);
		ta.location = loc;
		match(vTOKEN.IDENT);
	    }
	    else {
		addError(makeError(tok,"Unbekannter Zustand/Konnektor"));
	    }
	    
	}
	else {
            addError(makeError(tok,"Syntax Error"));
	    // Error
	    //System.out.println("Oops!");
	}

	return ta;
    } 

    // Liste von Transitionen
    private TrList translist(Path p) throws IOException {
	//Tr tr;
	TrList trlist = null;
	boolean b = false;
	
	if (tok.token == vTOKEN.FROM) {
	    trlist = new TrList(trans(p), translist(p));
	}
	else if (tok.token==vTOKEN.IDENT) {
	    addError(makeError(tok,"Transitionsdefinition erwartet"));
	}
	else {
	    trlist = null;
	}

	return trlist;
    }

    // Statelist innerhalb eines OR-States
    private StateList states(Path p) throws IOException {
	StateList sl = null;
	State st = null;
	boolean b = false;
	
        if (tok.token == vTOKEN.BSTATE) {
	    b = match(vTOKEN.BSTATE);
	    if (b) {
		sl = new StateList(bstate(p), states(p));
	    }
	    else {
		sl = null;	
	    }
	    
        }
	else if (tok.token == vTOKEN.OSTATE) {
	    b = match(vTOKEN.OSTATE);
	    
	    if (b) {
		sl = new StateList(ostate(p), states(p));
	    }
	    else {
		sl = null;	
	    }

	}
	else if (tok.token == vTOKEN.ASTATE) {
	    b = match(vTOKEN.ASTATE);
	    if (b) {
		sl = new StateList(astate(p), states(p));
	    }
	    else {
		sl = null;	
	    }
	}
	else if (tok.token == vTOKEN.END) {
	    sl = null;
	}
	
	
	return sl;
    }


    // Hilfsfunktionen

    // erstellt PathList
    private PathList makePathlist() {
	PathList pl = null;
	int i = 0;
	int s;

	s = path.size();
	for (i=0; i<s; i++) {
	    pl = new PathList((Path)path.elementAt(i), pl);
	}

	return pl;
    }

    
    // fügt p der Größe nach sortiert in path-vector ein
    private void addPath(Path p) {
	int i;

	i = getPathPos(p);
	path.insertElementAt(p, i);
    }


    // gibt Referenz auf Path-Objekt, null wenn nict gefunden / temporar
    private Path findPath(String s) {
	int i, si;
	Path pth = null;
	String pfad, tp;

	debug(s.valueOf(path.size()));

	if (path.size()>0) {
	    pfad = s;
	    //System.out.print("!!!!! -> ");
	    //System.out.println(pfad);
	    
	    si = path.size();
	    
	    for(i=0; i<si; i++) {
		pth = (Path)path.elementAt(i);
		tp = makePathString(pth);
		debug(tp);

		if ( pfad.compareTo(tp) == 0 ) break;
		pth = null;
	    }
	}
	else {
	    pth = null;
	}

	return pth;	
    }

    // liefert Position, an der p eingefügt werden soll
    private int getPathPos(Path p) {
	int i, s;
	Path pth;
	String pfad, tp;

	if (path.size()>0) {
	    pfad = makePathString(p);
	    //System.out.print("!!!!! -> ");
	    //System.out.println(pfad);
	    
	    s = path.size();
	    
	    for(i=0;i<s;i++) {
		pth = (Path)path.elementAt(i);
		tp = makePathString(pth);
		if (PathLength(pfad) >= PathLength(tp)) break;
	    }
	}
	else {
	    i = 0;
	}

	return i;
    }

    // liefert Anzahl der Punkte in s
    private int PathLength(String s) {
	int si,i;
	int l = 0;
	
	si = s.length();

	for(i=0;i<si;i++) {
	    if(s.charAt(i)=='.') l++;
	}

	return l;
    }

    // baut aus p einen String (Statenames durch '.' getrennt 
    private String makePathString(Path p) {
	StringBuffer sb = new StringBuffer();
	
	if(p != null) {
	    sb.append(p.head);
	    if (p.tail != null) {
		sb.append(".");             // Punkt als Separator
		sb.append(makePathString(p.tail));
	    }
	}

	return sb.toString();
    }
    

    // macht aus einem String einen Path
    private Path StringToPath(String s) {
	int l = s.length();
	int i, b;
	String sub;
	Path p = null;

	s = s.concat(".");
	l++;

	b = 0;
	for (i=0; i<l; i++) {
	    if (s.charAt(i) == '.') {
		sub = s.substring( b, i);

		if (b == 0) p = new Path(sub, null);
		else p = p.append(sub);

		b = i+1;
	    }
	}

	//if (p == null && l != 0) p = new Path(s, null);

	return p;
    }

    // findet das nächste Token t
    // bei Fehlern kann so bis zum erwarteten Token gesprungen werden.
    private boolean findNextTok(int t) throws IOException {
	
	while (tok.token != vTOKEN.EOF && tok.token != t) {
	    tok = ts.nextToken();
	}

	// hiernach sind wir auf einem neuen Konstrukt
	//return match(vTOKEN.SCOLON);
	return true;
    }

    // Match
    private boolean match(int t) throws IOException {

	boolean b = true;

	// Pruefen, ob Identifikator Keyword (eines anderen Packages) ist.
	if (t == vTOKEN.IDENT &&  Keyword.isReserved(tok.value_str)) {
	    addError(makeError(tok,"Ist Keyword"));
	}

	if (tok.token == t) {
	    tok = ts.nextToken();
	}
	else {
	    addError(makeError(tok,"Syntax Error"));
	    
	    b = false;

	    if (switches.contains("jumpAfterError")) {
		debug("Suche ;");
		b = findNextTok(vTOKEN.SCOLON);
	    }
	}

	return b;
    }


    // Tools

    /**
      * Setzt in einem Absyn-Objekt die Zeilennummer ein.
      */
    private Absyn setLoc(Absyn a, Location loc) {
        a.location = loc;
        return a;
    }

 
    // Es können connectors und States mit gleichem Namen in unterschiedlichem Kontext 
    // vorhanden sein
    // => Die Pfade muessen geprüft werden.

    private boolean is_conname(String txt, Path p) {
	String x = makePathString(p).concat(".").concat(txt);

	return cnlist.contains(x);
    }

    private boolean is_statename(String txt, Path p) {	
	String x = makePathString(p).concat(".").concat(txt);

	return snlist.contains(x);	
    }


    
    // Bvars werden global definiert => eindeutige Namen
    // Pruefen direkt ueber Liste
    private boolean is_bvarname(String txt) {
	boolean b = false;
	BvarList bvl = bvlist;

	while (bvl != null) {
	    if (bvl.head != null) {
		if (bvl.head.var.compareTo(txt) == 0) {
		    b = true;
		    debug("Gefunden!");
		    break;
		}
	    }
	    bvl = bvl.tail;
	}
	
	return b;
    }
    /*
    private boolean is_bvarname(String txt) {
        return bnlist.contains(txt); 
    }
    */

    /*
    // s. Bvars
    private boolean is_eventname(String txt) {
	
	return enlist.contains(txt);	
    }
    */

    private boolean is_eventname(String txt) {
	boolean b = false;
	SEventList evl = evlist;

	while (evl != null) {
	    if (evl.head != null) {
		if (evl.head.name.compareTo(txt) == 0) {
		    b = true;
		    debug("Gefunden!");
		    break;
		}
	    }
	    evl = evl.tail;
	}
	
	return b;
    }
    

    // Hinzufügen der jeweiligen Names in die Listen, temporar
    private void addStatename(String s, Path p) {
	snlist.addElement(makePathString(p).concat(".").concat(s));
    }
    
    private void addConname(String s, Path p) {
	cnlist.addElement(makePathString(p).concat(".").concat(s));
    }


    // Nicht mehr benoetigt
    /*
    private void addBvarname(String s) {
	bnlist.addElement(s);
    }
    
    private void addEventname(String s) {
	enlist.addElement(s);

    }
    */

    // hängt Fehlermeldung an die Liste an.
    private void addError(String txt) {
	errorList.addElement(txt);
	errorCount++;
	debug("Fehler!");
	//System.out.println(txt);
    }

    // generiert Fehlerstring
    private String makeError(TOKEN tok_, String txt) {
	StringBuffer f = new StringBuffer();

	f.append("Tesc1: Zeile ");
	f.append(tok_.linenum);
	f.append(" - ");
	f.append(txt);
	f.append(" : Unerwartetes Token '");
	f.append(tok_.value_str);
	f.append("'");

	return f.toString();
    }

    private void outputErrors() {
        String txt;
        int s = errorList.size();
	    
	for(int i=0;i<s;i++) {
           txt = (String ) errorList.elementAt(i);
	   if (gi != null) gi.userMessage(txt);
	   else System.out.println(txt);
	}
    }

    private void debug(String s) {
	
	if (switches.contains("debug")) {
	    String txt = new String("Tesc1: DEBUG-> ");
	    
	    txt = txt.concat(s);
	    //if (gi != null) gi.userMessage(txt);
	    //else 
            System.out.println(txt);
	}
    }

}

/* TESCParser
 * $Id: TESCParser.java,v 1.19 1999-01-25 13:27:49 swtech13 Exp $
 * $Log: not supported by cvs2svn $
 * Revision 1.18  1999/01/17 21:42:36  swtech13
 * Verbesserungen/Bugfixes
 *
 * Revision 1.15  1999/01/11 23:20:10  swtech13
 * ~ in Guards, bassign in Action wird jetzt erkannt
 * Doku angepasst
 *
 * Revision 1.14  1999/01/11 20:10:32  swtech13
 * An geaenderte Grammatik angepasst.
 * Wir koennen jetzt den Typ der Variablen bei Guards/Actions aus dem Kontext
 * bestimmen.
 *
 * Revision 1.11  1999/01/06 14:57:24  swtech13
 * Fehlerbehandlung verbessert
 *
 * Revision 1.10  1999/01/06 13:48:56  swtech13
 * Neue Fehlermeldung
 *
 * Revision 1.9  1999/01/05 20:58:17  swtech13
 *   - pathop -> absolute Pfade
 *   - Keine Action angegeben -> new ActionEmpty(new Dummy())
 *   - Schaltermechanismus eingebaut.
 *   - debugmechanismus eingebaut.
 *
 * Revision 1.8  1999/01/04 16:12:02  swtech13
 * Locations hinzugefuegt. Pruefen auf Keywords.
 *
 * Revision 1.7  1998/12/21 16:17:36  swtech13
 * Fehlermeldungen im Parser -> GUI
 *
 * Revision 1.6  1998/12/17 11:54:15  swtech13
 * TESCLoader.java auf BufferedReader umgestellt
 *
 * Revision 1.5  1998/12/15 17:51:57  swtech00
 * Towards new naming conventions in PEST1
 *
 * Revision 1.4  1998/12/14 23:58:08  swtech13
 * Scanner: Teilstrings von keywords werden nicht mehr zurueckgewiesen
 *
 * Parser: Erste arbeitende Version
 * 	Todo:
 * 	-  Undet bei Guards
 * 	-  Fehlerbehandlung
 * 	   => bitte nur Test/simple.tesc als Eingabe verwenden
 * 	-  Bugfixes
 *
 * Revision 1.3  1998/12/07 20:10:16  swtech13
 * Anpassung der Schnittstelle
 *
 * Revision 1.2  1998/12/07 13:20:15  swtech13
 * Scanner geht, aber noch nicht vollstaendig,
 * Parser nix,
 * Grammatik muss noch ueberarbeitet werden.
 *
 */
