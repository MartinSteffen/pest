package tesc1;

import java.util.*;
import absyn.*;
import java.io.*;
import gui.*;
import java.lang.*;

/* Todo/Fragen
 * - Was enthält Path? Nur States oder auch Cons?
 * - Es wird immer ein ActionBlock zurückgeliefert
 * - Kann es mehr als einen defcon geben ?
 * + PathList der Größe nach sortieren
 * - Ist Basic_State als root erlaubt ?
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

    private FileInputStream is;
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


    //private static final int NNT = 0;  // NoNextToken

    // protected-Methoden für das Package
	
    protected TESCParser(FileInputStream is_, GUIInterface gi_) {
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
	    System.out.println("Ärger!");
	    gi.OkDialog((String) "Scanner: IOError", (String) "Fehler - bei Lesen des Streams!");
	}

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

	
	try {
	    tok = ts.nextToken();

	    if (tok.token == vTOKEN.EVENTS) {
		evlist = events();
	    }
	    if (tok.token == vTOKEN.BVARS) {
		bvlist = bvars();
	    }

	    st = state();

	}
	catch (IOException e) {
	    System.out.println("Mist!");
	}

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

	return evlist;
    }
    
    // eventliste aufbauen
    private SEventList eventlist() throws IOException {
	SEvent ev;
	SEventList evlist = null;
	
        if (tok.token==vTOKEN.IDENT) {
	    ev = new SEvent(tok.value_str);
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
		// error
	    }
	
	}

	return evlist;
    }
    
    // bvarliste aufbauen
    private BvarList bvarlist() throws IOException {
	Bvar bv;
	BvarList bvlist = null;
	
        if (tok.token==vTOKEN.IDENT) {
	    bv = new Bvar(tok.value_str);
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
		// error
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

	return st;
    }

    private Basic_State bstate(Path p) throws IOException {
	Statename     sn      = null;
	Basic_State   bs      = null;
	Path pth = null;

	if (tok.token == vTOKEN.IDENT) {
	    sn = new Statename (tok.value_str);
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

	if (tok.token == vTOKEN.IDENT) {
	    sn = new Statename (tok.value_str);

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
	
	    match(vTOKEN.END);

	    if (tok.token == vTOKEN.IDENT) {
		if (s.compareTo(tok.value_str) != 0) {
		    //Error
		    System.out.print("FEHLER - Name ungleich -> ");
		    System.out.print(s);
		    System.out.print(" <> ");
		    System.out.println(tok.value_str);
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

	if (tok.token == vTOKEN.IDENT) {
	    sn = new Statename (tok.value_str);

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
	
	    match(vTOKEN.END);

	    if (tok.token == vTOKEN.IDENT) {
		if (s.compareTo(tok.value_str) != 0) {
		    //Error
		    System.out.print("FEHLER - Name ungleich -> ");
		    System.out.print(s);
		    System.out.print(" <> ");
		    System.out.println(tok.value_str);
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
	    //Error
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
	
        if (tok.token==vTOKEN.IDENT) {
	    con = new Connector(new Conname(tok.value_str));
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
		// error
	    }
	
	}

	return clist;
    }
    
    private StatenameList defcons(Path p) throws IOException {
	boolean b = false;
        StatenameList snlist = null;
	
	b = match(vTOKEN.DEFCON);
	b = match(vTOKEN.COLON);

	if (b) {
	    if (tok.token == vTOKEN.IDENT) {
		snlist = new StatenameList(new Statename(tok.value_str), null);
		match(vTOKEN.IDENT);
	    }
	    else {
		//error
	    }
	    match(vTOKEN.SCOLON);
	}

	return snlist;
    }

    

    // !!!!!

    private Action actionstmt (Path p) throws IOException {
	Action act = null;
	//boolean b = false;

	if (tok.token == vTOKEN.DO) {
	    match(vTOKEN.DO);
	    // Auch ActionBlock bei nur einer Action ??
	    act = new ActionBlock(actionlist(p));
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

	if (tok.token == vTOKEN.IDENT) {
	    if (is_bvarname(tok.value_str)) {
		a = bassign(p);
	    }
	    else if( is_eventname(tok.value_str)) {
		a = new ActionEvt(new SEvent(tok.value_str));
		match(vTOKEN.IDENT);
	    }
	    else {
		// Error
	    }   
	   
	}
	else if (tok.token == vTOKEN.EMPTYEXP) {
	    a = new ActionEmpty(new Dummy());
	    match(vTOKEN.EMPTYEXP);
	}
	
	return a;
    }

    private Action bassign(Path p) throws IOException {
	Action a = null;
	Bvar bv = null;

	if (tok.token == vTOKEN.IDENT) {
	    bv = new Bvar(tok.value_str);
	    match(vTOKEN.IDENT);
	    match(vTOKEN.BASSIGN);
	    
	    // boolop
	    if (tok.token == vTOKEN.TRUE) {
		a = new ActionStmt(new MTrue(bv));
		match(vTOKEN.TRUE);
	    }
	    else if (tok.token == vTOKEN.FALSE) {
		a = new ActionStmt(new MFalse(bv));	
		match(vTOKEN.FALSE);
	    }
	    else {
		a = new ActionStmt(new BAss(new Bassign(bv, guard(p))));
	    }
	}

	return a;
    }
        

    // Guards
    private Guard guard(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
	
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
		grd = new GuardCompg(new Compguard(Compguard.IMPLIES, grd1, grd2));
		break;
	    case vTOKEN.AQUI:	
		grd = new GuardCompg(new Compguard(Compguard.EQUIV, grd1, grd2));
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
	
	grd1 = acompg(p);
	grd = grd1;
	while (tok.token == vTOKEN.OR) {	    	
	    match(vTOKEN.OR);
	    grd2 = acompg(p);
	    	
	    grd = new GuardCompg(new Compguard(Compguard.OR, grd1, grd2));
	
	}

	return grd;
    }
    
    // &&
    private Guard acompg(Path p) throws IOException {
	Guard grd1 = null;
	Guard grd2 = null;
	Guard grd = null;
	int t = 0;
		
	grd1 = kcompg(p);
	grd = grd1;
	while (tok.token == vTOKEN.AND) {	    	
	    match(vTOKEN.AND);
	    grd2 = kcompg(p);
	    	
	    grd = new GuardCompg(new Compguard(Compguard.AND, grd1, grd2));
	
	}
	

	return grd;
    }

    // Klammern und nicht-reduzierbares
    private Guard kcompg(Path p) throws IOException {	
	Guard grd = null;
	

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

	return grd;

    }

    // ???
    private Guard pathop(Path actPath) throws IOException {
	Guard grd = null;
	if (actPath!=null) System.out.println("HAllo");
	switch(tok.token) {
	case vTOKEN.IN:
	    match (vTOKEN.IN);
	    match (vTOKEN.LPAR);
	    grd = new GuardCompp(new Comppath(Comppath.IN, actPath.append(tok.value_str)));
	    match(vTOKEN.IDENT);
	    match(vTOKEN.RPAR);
	    break;
	case vTOKEN.ENTERED:
	    match (vTOKEN.ENTERED);
	    match (vTOKEN.LPAR);
	    grd = new GuardCompp(new Comppath(Comppath.ENTERED, actPath.append(tok.value_str)));
	    match(vTOKEN.IDENT);
	    match(vTOKEN.RPAR);
	    break;
	case vTOKEN.EXITED:
	    match (vTOKEN.EXITED);
	    match (vTOKEN.LPAR);
	    grd = new GuardCompp(new Comppath(Comppath.EXITED, actPath.append(tok.value_str)));
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

	match(vTOKEN.FROM);
        
	t1 = sname(p);
	
	match(vTOKEN.TO);
		
	t2 = sname(p);
		
	match(vTOKEN.ON);
	grd = guard(p);
	
	act = actionstmt(p);
	tr = new Tr(t1, t2, new TLabel(grd, act));
	match(vTOKEN.SCOLON);
	    

	return tr;
    }

    private TrAnchor sname(Path p) throws IOException {
	TrAnchor ta = null;

	if (tok.token == vTOKEN.UNDEF) {
	    match(vTOKEN.UNDEF);
	    ta = new UNDEFINED();
	}
	else if (tok.token == vTOKEN.IDENT) {
	    // Con oder State
	    if (is_conname(tok.value_str, p)) {
		ta = new Conname(tok.value_str);
		match(vTOKEN.IDENT);
	    }
	    else if(is_statename(tok.value_str, p)) {
		ta = new Statename(tok.value_str);
		match(vTOKEN.IDENT);
	    }
	    else {
		// Error
	    }
	    
	}
	else {
	    // Error
	    System.out.println("Oops!");
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
	else {
	    //Error
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
	    System.out.print("!!!!! -> ");
	    System.out.println(pfad);
	    
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

	if (tok.token == t) {
	    tok = ts.nextToken();
	}
	else {
	    // Error
	    System.out.print("FEHLER ( ");
	    System.out.print(tok.linenum);
	    System.out.print(") - ");
	    System.out.print(ts.getString(t));
	    System.out.println(" erwartet!");
	    
	    b = false;
	}

	return b;
    }

    // Tools

    // hängt Fehlermeldung an die Liste an.
    private void addError(String txt) {
	errorList.addElement(txt);
	errorCount++;
	System.out.println(txt);
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
		System.out.println("Connname");
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
		System.out.println("Statenname");
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
		System.out.println("Bvarname");
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
		System.out.println("Eventname");
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

    // generiert Fehlerstring
    private String makeError(TOKEN tok_, String txt) {
	StringBuffer f = new StringBuffer();

	f.append("Zeile ");
	f.append(tok_.linenum);
	f.append(" - ");
	f.append(txt);
	f.append(" : Unerwartetes Token '");
	f.append(tok_.value_str);
	f.append("'");

	return f.toString();
    }
}

/* TESCParser
 * $Id: TESCParser.java,v 1.5 1998-12-15 17:51:57 swtech00 Exp $
 * $Log: not supported by cvs2svn $
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
