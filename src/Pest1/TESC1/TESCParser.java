package tesc1;

import java.util.*;
import absyn.*;
import java.io.*;
import gui.*;
import java.lang.*;
import util.*;

/* Todo/Fragen
 * + Was enthält Path? Nur States oder auch Cons? - Nur States !
 * - Es wird immer ein ActionBlock zurückgeliefert
 * + Kann es mehr als einen defcon geben ? Selbst entscheiden. <- nur einer
 * + PathList der Größe nach sortieren
 * + Ist Basic_State als root erlaubt ? - Ja
 * + is_*name über Pfade prüfen !!
 * + Statenamelist in ostate fehlt noch ! <- defcons
 * - Undet bei Guards
 * - Fehlerbehandlung
 * - Bei Ident mit Bezug auf vorhandenes Obejkt immer prüfen, ob auch vorhanden s.u.
 */


/* Vorraussetzungen
 * - Stufe 1: -
 * - Stufe 2:
 *   - Boolesche Ausdrücke sollten vom Editor GEPARST worden sein
 *
 */
 
/* Erfüllte Vorraussetzungen:
 * - Auftretende Bezeichner sind gültig. (z.B. Statenames in transitions existieren auch als State)
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
    private Vector bnlist;        // Liste der bekannten Bvarnames
    private Vector enlist;        // Liste der bekannten Eventnames

    private Vector path;          // enthält die Pfade

    private BufferedReader is;
    //private static final int NNT = 0;  // NoNextToken

    // protected-Methoden für das Package
	
    protected TESCParser(BufferedReader is_, GUIInterface gi_) {
	gi = gi_;
	is = is_;

	errorList = new Vector();
	snlist = new Vector();
	cnlist = new Vector();
	bnlist = new Vector();
	enlist = new Vector();

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



    // private Methoden

    /** Das erste Token kann sein:
     *  - events
     *  - bvars
     *  - or | and | basic
     */
    private Statechart buildStatechart() throws IOException {

	SEventList evlist = null;
	BvarList bvlist = null;
	State st = null;
	PathList plist = null;

	StringBuffer f = new StringBuffer();

	tok = ts.nextToken();

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

    
    // events : 
    private SEventList events() throws IOException {
	boolean b = false;
	SEventList evlist = null;
	
	b = match(vTOKEN.EVENTS);	
	b = match(vTOKEN.COLON);

	if (b) {
	    evlist = eventlist();
	    
	}
	else
	  addError(makeError(tok,"Syntax Error"));
	return evlist;
    }
    
    // eventliste aufbauen
    private SEventList eventlist() throws IOException {
	SEvent ev;
	SEventList evlist = null;
	Location loc = new Location(tok.linenum);

        if (tok.token==vTOKEN.IDENT) {
	    ev = new SEvent(tok.value_str);
	    ev.location = loc;
	    addEventname(tok.value_str);
	    match(vTOKEN.IDENT);
	
	    if (tok.token==vTOKEN.COMMA) {
		match(vTOKEN.COMMA);
		evlist = new SEventList( ev, eventlist());
	    }
	    else if (tok.token == vTOKEN.SCOLON) {
	        match(vTOKEN.SCOLON);
                evlist =  new SEventList( ev, null);
	    }
	    else {
		addError(makeError(tok,"Fehler in Eventliste"));
	    }
	
	}

	return evlist;
    }
    
    // bvarliste aufbauen
    private BvarList bvarlist() throws IOException {
	Bvar bv;
	BvarList bvlist = null;	
	Location loc = new Location(tok.linenum);

        if (tok.token==vTOKEN.IDENT) {
	    bv = new Bvar(tok.value_str);
	    bv.location = loc;
	    addBvarname(tok.value_str);
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
    
    // bvars :
    private BvarList bvars() throws IOException {
	boolean b = false;
	BvarList bvlist = null;

	b = match(vTOKEN.BVARS);
	b = match(vTOKEN.COLON);

	if (b) {
	    bvlist = bvarlist();
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
	    slist = states(pth);
	    

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
		    //addError(makeError(tok,"Unbekannter Zustandsname"));
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

	return os;
    }

    private And_State astate(Path p) throws IOException {
	And_State      as      = null;
	Statename     sn      = null;
	StateList     slist   = null;
	//TrList        trlist  = null;
	StatenameList snlist  = null;
	//ConnectorList clist = null;
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
	    // Liste der States innerhalb dieses States <- genau 2 ?
	    //slist = new StateList(astates(pth), new StateList(astates(pth), null));
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
	    con = new Connector(new Conname(tok.value_str));
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

    

    // !!!!!

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

	return act;
    }

    private Aseq actionlist(Path p) throws IOException {
	Aseq as = null;
	Action a;

	a = aktion(p);
	if (tok.token == vTOKEN.COMMA) {
	    match(vTOKEN.COMMA);
	    as = new Aseq(a, actionlist(p));
	    
	}
	else if (tok.token == vTOKEN.SCOLON) {
	    as =  new Aseq( a, null);
	    
	}

	return as;
    }

    private Action aktion(Path p) throws IOException {
	Action a = null;
	Location loc = new Location(tok.linenum);

	if (tok.token == vTOKEN.IDENT) {
	    if (is_bvarname(tok.value_str)) {
		a = bassign(p);
		a.location = loc;
	    }
	    else if( is_eventname(tok.value_str)) {
		a = new ActionEvt((SEvent)setLoc(new SEvent(tok.value_str), loc));
		a.location = loc;
		match(vTOKEN.IDENT);
	    }
	    else {
		addError(makeError(tok,"Unbekannter Zustandsname"));
	    }   
	   
	}
	else if (tok.token == vTOKEN.EMPTYEXP) {
	    a = new ActionEmpty((Dummy)setLoc(new Dummy(), loc));
	    a.location = loc;
	    match(vTOKEN.EMPTYEXP);
	}
	
	return a;
    }

    private Action bassign(Path p) throws IOException {
	Action a = null;
	Bvar bv = null;
	Location loc = new Location(tok.linenum);

	if (tok.token == vTOKEN.IDENT) {
	    bv = new Bvar(tok.value_str);
	    bv.location = loc;
	    match(vTOKEN.IDENT);
	    match(vTOKEN.BASSIGN);
	    
	    // boolop
	    if (tok.token == vTOKEN.TRUE) {
		loc = new Location(tok.linenum);
		a = new ActionStmt((MTrue)setLoc(new MTrue(bv), loc));
		a.location = loc;
		match(vTOKEN.TRUE);
	    }
	    else if (tok.token == vTOKEN.FALSE) {
		loc = new Location(tok.linenum);
		a = new ActionStmt((MFalse)setLoc(new MFalse(bv), loc));
		a.location = loc;
		match(vTOKEN.FALSE);
	    }
	    else {
		loc = new Location(tok.linenum);
		a = new ActionStmt((BAss)setLoc(new BAss((Bassign)setLoc(new Bassign(bv, guard(p)),loc) ), loc));
		a.location = loc;
	    }
	}

	return a;
    }
        
    // hier
    // Guards
    private Guard guard(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	grd1 = ocompg(p);
	grd = grd1;	

	while (tok.token == vTOKEN.IMPL || tok.token == vTOKEN.AQUI) {
	    t = tok.token;
	    switch(t) {
	    case vTOKEN.IMPL:
		match(vTOKEN.IMPL);
		break;
	    case vTOKEN.AQUI:	
		match(vTOKEN.AQUI);
		break;
	    }
	    grd2 = ocompg(p);
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
    private Guard ocompg(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	grd1 = acompg(p);
	grd = grd1;
	while (tok.token == vTOKEN.OR) {	    	
	    match(vTOKEN.OR);
	    grd2 = acompg(p);
	    	
	    grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.OR, grd1, grd2), loc));
	    grd.location = loc;
	}

	return grd;
    }
    
    // &&
    private Guard acompg(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	Location loc = new Location(tok.linenum);

	grd1 = kcompg(p);
	grd = grd1;
	while (tok.token == vTOKEN.AND) {	    	
	    match(vTOKEN.AND);
	    grd2 = kcompg(p);
	    	
	    grd = new GuardCompg((Compguard)setLoc(new Compguard(Compguard.AND, grd1, grd2), loc));
	    grd.location = loc;
	}
	

	return grd;
    }

    // Klammern und nicht-reduzierbares
    private Guard kcompg(Path p) throws IOException {	
	Guard grd = null;
	Location loc = new Location(tok.linenum);
	
	if (tok.token == vTOKEN.NOT) {
	    // ??
	    match(vTOKEN.NOT);
	    grd = new GuardNeg(kcompg(p));
	}
	else if(tok.token == vTOKEN.EMPTYEXP) {
	    // Rekursion ?? oder kann ~ nur für sich stehen ?
	    grd = new GuardEmpty(new Dummy()); 
	}
	else if(tok.token == vTOKEN.IN || tok.token == vTOKEN.ENTERED || tok.token == vTOKEN.EXITED) {
	    grd = pathop(p);
	}
	else if (tok.token == vTOKEN.IDENT) {
	    if (is_bvarname(tok.value_str)) {
		grd = new GuardBVar(new Bvar(tok.value_str));
	    }
	    else if (is_eventname(tok.value_str)) {
		grd = new GuardEvent(new SEvent(tok.value_str));
	    }
	    match(vTOKEN.IDENT);
	}
	else if (tok.token == vTOKEN.LPAR) {
	    match(vTOKEN.LPAR);
	    grd = guard(p);
	    match(vTOKEN.RPAR);
	}
	
	if (grd != null) grd.location = loc;
	
	return grd;

    }

    // ???
    private Guard pathop(Path actPath) throws IOException {
	Guard grd = null;
	Location loc = new Location(tok.linenum);

	if (actPath!=null) addError(makeError(tok, "Internal Error!"));
	switch(tok.token) {
	case vTOKEN.IN:
	    match (vTOKEN.IN);
	    match (vTOKEN.LPAR);
	    grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.IN, actPath.append(tok.value_str)), loc));
	    grd.location = loc;
	    match(vTOKEN.IDENT);
	    match(vTOKEN.RPAR);
	    break;
	case vTOKEN.ENTERED:
	    match (vTOKEN.ENTERED);
	    match (vTOKEN.LPAR);
	    grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.ENTERED, actPath.append(tok.value_str)), loc));
	    grd.location = loc;
	    match(vTOKEN.IDENT);
	    match(vTOKEN.RPAR);
	    break;
	case vTOKEN.EXITED:
	    match (vTOKEN.EXITED);
	    match (vTOKEN.LPAR);
	    grd = new GuardCompp((Comppath)setLoc(new Comppath(Comppath.EXITED, actPath.append(tok.value_str)), loc));
	    grd.location = loc;
	    match(vTOKEN.IDENT);
	    match(vTOKEN.RPAR);
	    break;
	}

	return grd;
    }

    private TrList trdef(Path p) throws IOException {
	TrList trlist = null;

	match(vTOKEN.TRANSITIONS);
	match(vTOKEN.COLON);

	trlist = translist(p);

	return trlist;
    }
    
    

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
	grd = guard(p);
	
	act = actionstmt(p);
	tr = new Tr(t1, t2, (TLabel)setLoc(new TLabel(grd, act), loc));
	tr.location = loc;
	match(vTOKEN.SCOLON);
	    

	return tr;
    }

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

    private TrList translist(Path p) throws IOException {
	//Tr tr;
	TrList trlist = null;
	boolean b = false;
	
	//b = match(vTOKEN.FROM);
	
	if (tok.token == vTOKEN.FROM) {
	    trlist = new TrList(trans(p), translist(p));
	}
	else {
	   
	    trlist = null;
	}

	return trlist;
    }

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
    

    // Match
    private boolean match(int t) throws IOException {

	boolean b = true;

	if (t == vTOKEN.IDENT &&  Keyword.isReserved(tok.value_str)) {
	    addError(makeError(tok,"Ist Keyword"));
	}

	if (tok.token == t) {
	    tok = ts.nextToken();
	}
	else {
	    // Error
	    /*
	    System.out.print("FEHLER ( ");
	    System.out.print(tok.linenum);
	    System.out.print(") - ");
	    System.out.print(ts.getString(t));
	    System.out.println(" erwartet!");
	    */
	    b = false;
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
	
	boolean b = false;
	int i = 0;
	int l = cnlist.size();
	String x = makePathString(p).concat(".").concat(txt);

	while (i<l) {
	    if (x.compareTo((String)cnlist.elementAt(i)) == 0) {
		//System.out.println("Connname");
		b = true;
		break;
	    }
	    i++;
	}
	return b;
    }

    private boolean is_statename(String txt, Path p) {
	boolean b = false;
	int i = 0;
	int l = snlist.size();
	String x = makePathString(p).concat(".").concat(txt);

	
	while (i<l) {
	    if (x.compareTo((String)snlist.elementAt(i)) == 0) {
		//System.out.println("Statenname");
		b = true;
		break;
	    }
	    i++;
	}
	return b;	
    }


    // Bvars werden global definiert => eindeutige Namen
    private boolean is_bvarname(String txt) {
	boolean b = false;
	int i = 0;
	int l = bnlist.size();

	while (i<l) {
	    if (txt.compareTo((String)bnlist.elementAt(i)) == 0) {
		//System.out.println("Bvarname");
		b = true;
		break;
	    }
	    i++;
	}
	return b;	
    }

    // s. Bvars
    private boolean is_eventname(String txt) {
	boolean b = false;
	int i = 0;
	int l = enlist.size();

	while (i<l) {
	    if (txt.compareTo((String)enlist.elementAt(i)) == 0) {
		//System.out.println("Eventname");
		b = true;
		break;
	    }
	    i++;
	}
	return b;	
    }

    // Hinzufügen der jeweiligen Names in die Listen
    private void addStatename(String s, Path p) {
	
	snlist.addElement(makePathString(p).concat(".").concat(s));

    }
    
    private void addConname(String s, Path p) {
	cnlist.addElement(makePathString(p).concat(".").concat(s));

    }

    private void addBvarname(String s) {
	bnlist.addElement(s);

    }
    
    private void addEventname(String s) {
	enlist.addElement(s);

    }

       // hängt Fehlermeldung an die Liste an.
    private void addError(String txt) {
	errorList.addElement(txt);
	errorCount++;
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
	   gi.userMessage(txt);
	}
    }

}

/* TESCParser
 * $Id: TESCParser.java,v 1.8 1999-01-04 16:12:02 swtech13 Exp $
 * $Log: not supported by cvs2svn $
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
