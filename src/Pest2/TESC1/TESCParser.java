package tesc1;
   
import java.io.*;
import java.lang.String;
import java.util.Vector;
import absyn.*;
import util.*;

/**
 * Parser für TESC.
 * <p>
 * @author Michael Sülzer, Christoph Schütte.
 * @version  $Id: TESCParser.java,v 1.6 1999-01-03 21:48:19 swtech20 Exp $
 */   
public class TESCParser {
    
    private static boolean DEBUG = true;  

    private Statechart statechart;
    private SEventList eventlist;
    private BvarList bvarlist;
    private PathList pathlist;

    private int errorCount = 0;
    private Vector errorText = null;
    private TESCTokenizer lexer = null;
    private Token token = null;

    /**
     * Constructor für den TESCParser.
     */
    public TESCParser () {}
    
    /**
     * Startet den Parsevorgang.
     */
    public Statechart readStatechart(BufferedReader br) throws IOException {

	errorText = new Vector();
	errorCount = 0;

	lexer = new TESCTokenizer(br);
	debug("parseTESC");

        pathlist = null;
	statechart = parseTesc();

	if (DEBUG) {
	    (new util.PrettyPrint()).start(statechart);
	}

	return statechart;
    }

    public Guard readGuard(BufferedReader br) throws IOException {

	errorText = new Vector();
	errorCount = 0;

	lexer = new TESCTokenizer(br);
	debug("parseGuard");

        pathlist = null;
        bvarlist = null;
        eventlist = null;
	Guard guard = parseGuard();

	if (DEBUG) {
	    (new util.PrettyPrint()).start(guard);
	}

        return guard;
    }

    public Action readAction(BufferedReader br) throws IOException {
	errorText = new Vector();
	errorCount = 0;

	lexer = new TESCTokenizer(br);
	debug("parseAction");

        pathlist = null;
        bvarlist = null;
        eventlist = null;
	Action action = parseAction();

	if (DEBUG) {
	    (new util.PrettyPrint()).start(action);
	}

        return action;
    }

    /**
     * Liefert die Anzahl der Fehler, die beim letzten Parsen
     * aufgetreten sind.
     * @return Anzahl der Fehler.
     */
    public int getErrorCount() {return errorCount;}
    
    
    /**
     * Liefert den Fehlertext zum n-ten Fehler, der beim letzen
     * Parsen aufgetreten ist.
     * @param n Fehlerindex
     * @return Fehlertext des n-ten Fehlers.
     */
    public String getErrorText(int n) {return (String)errorText.elementAt(n);}
    

    /**
     * Speichert einen Fehlertext.
     * @param msg Fehlertext
     */ 
    private void Error(String msg) {
	errorText.addElement("Zeile " + token.getLine() + " : " + msg);
        debug ("Zeile " + token.getLine() + " : " + msg);
        errorCount++;
    }

    /**
     * Gibt einen String aus, falls der Schalter DEBUG gesetzt ist.
     * @param s Text
     * @see DEBUG
     */    
    private void debug(String s) {
        if (DEBUG) System.out.println(s);
    }

    //------------------------------------------------------------------------
    //  Hilsfunktion(en) zum Parsen
    //------------------------------------------------------------------------

    /**
      * Prueft, ob ein gewuenschtes Token im Eingabestrom vorliegt.
      * Falls ja, wird das naechste Token gelesen.
      * Falls nein, wird ein Fehler ausgegeben und fortgefahren.
      * @param t Zu pruefendes Token 
      * @exception IOExecption Falls im Tokenizer ein Fehler auftritt.
      */
    private void matchToken(Token t) throws IOException{ 

	if (token.getId() == t.getId()) {
	    debug("Match: " + token.getValue());
	    token = lexer.getNextToken();
	}
	else {
	    Error("Syntaxfehler. " + t.getValue() + " statt " +
                  token.getValue()+ " erwartet.");
            debug("Syntaxfehler. " + t.getValue() + " statt " +
                  token.getValue()+ " erwartet.");
	}
    }

    /** 
      * Setzt in einem Absyn-Objekt die Zeilennummer ein.
      */ 
    private Absyn setLoc(Absyn a) {
        a.location = new Location(token.getLine());
        return a;
    }
 
    //------------------------------------------------------------------------
    //  Hilfsfunktion(en) zum Aufbauen des Statecharts
    //------------------------------------------------------------------------

    /**
     *  Erweitert den Pfad für den weiteren rekursiven Abstieg.
     *  @param  path Bisheriger Pfad
     *  @param  s    Erweiterung
     *  @return Erweiterter Pfad 
     */
    private Path extendPath (Path path, String s) {

        Path pNew;

        if (path == null)
            pNew = new Path(s, null);
        else 
            pNew = path.append(s);

        appendPathList(pNew);

        return pNew;
    }

    /**
     *  Haengt an die globale Pfadliste des Statecharts einen
     *  Pfad an.
     *  @param path Anzuhaegender Pfad
     */
    private void appendPathList (Path path) {
       
        if (pathlist == null) {
            pathlist = new PathList(path, null);
        }
        else {
 	    PathList plIterator = pathlist;

            while (plIterator.tail != null) { 
                plIterator = plIterator.tail;
            } 

            plIterator.tail = new PathList(path, null);
        }
    }

    /**
     * Prueft, ob ein Connector in einer Liste enthalten ist.
     * @param  s String
     * @param  clIterator Liste
     * @return boolean Gefunden oder nicht
     */
    private boolean isConnectorName (String s, ConnectorList clIterator) {

        if (clIterator == null) { 
            return false;       
        }
        else {
            while (clIterator != null) {
                if (clIterator.head.name.name.equalsIgnoreCase(s)) {
                  return true;
                }
                clIterator = clIterator.tail;
            }
            return false;  
        }
    }

    /**
     * Prueft, ob ein Event in einer Liste enthalten ist.
     * @param  s String
     * @param  elIterator Liste
     * @return boolean Gefunden oder nicht
     */
    private boolean isEventName (String s, SEventList elIterator) {

        if (elIterator == null) { 
            return false;       
        }
        else {
            while (elIterator != null) {
                if (elIterator.head.name.equalsIgnoreCase(s)) {
                  return true;
                }
                elIterator = elIterator.tail;
            }
            return false;  
        }
    }

    /**
     * Prueft, ob eine Variable in einer Liste enthalten ist.
     * @param  s String
     * @param  blIterator Liste
     * @return boolean Gefunden oder nicht. 
     */
    private boolean isBvarName (String s, BvarList blIterator) {

        if (blIterator == null) { 
            return false;       
        }
        else {
            while (blIterator != null) {
                if (blIterator.head.var.equalsIgnoreCase(s)) {
                  return true;
                }
                blIterator = blIterator.tail;
            }
            return false;  
        }
    }

    /**
     * Liefert SEvent-Objekt aus Liste.
     * @param  s String
     * @param  elIterator Liste
     * @return SEvent Element aus Liste
     */
    private SEvent getEvent (String s, SEventList elIterator) {

        if (elIterator == null) { 
            return null;       
        }
        else {
            while (elIterator != null) {
                if (elIterator.head.name.equalsIgnoreCase(s)) {
                  return elIterator.head;
                }
                elIterator = elIterator.tail;
            }
            return null;  
        }
    }

    /**
     * Liefert Bvar-Objekt aus Liste.
     * @param  s String
     * @param  blIterator Liste
     * @return Bvar Element aus Liste 
     */
    private Bvar getBvar (String s, BvarList blIterator) {

        if (blIterator == null) { 
            return null;       
        }
        else {
            while (blIterator != null) {
                if (blIterator.head.var.equalsIgnoreCase(s)) {
                  return blIterator.head;
                }
                blIterator = blIterator.tail;
            }
            return null;  
        }
    }
    
    //------------------------------------------------------------------------
    //  Implementierung der TESC-Grammatik
    //------------------------------------------------------------------------

    /**
     *  TESC ::= VAR EVENT STATE
     */
    private Statechart parseTesc() throws IOException {
	debug("Enter parseTESC");

        // Das erste Token holen
	token = lexer.getNextToken();

        // VAR
        bvarlist = parseVar();

        // EVENT
        eventlist = parseEvent();

        // STATE
        State rootstate = parseState(null);

	matchToken(Token.EOF);

	debug("Leave parseTESC");
	return new Statechart(eventlist, bvarlist, pathlist, rootstate);
    }

    /**
     * VAR ::= {"var" VARS} 
     */
    private BvarList parseVar() throws IOException {

        // {"var" VARS}
        if (token.getId() == Token.TOK_VAR) {
            matchToken(Token.KeyVar);
            return parseVars();
        }
        else
            return null;
    }

    /**
     * VARS ::= "identifier" | "(" VARLIST ")"
     */   
    private BvarList parseVars() throws IOException {
        debug ("Enter parseVars");

        BvarList bvList = null;
        
        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :
 
            bvList = new BvarList (new Bvar (token.getValue()),null);
            matchToken (Token.Identifier); 
            break;

        case Token.TOK_LPAR       :

            matchToken (Token.LPar);
            bvList = parseVarList();
            matchToken (Token.RPar);
            break;

        default                   :

            Error ("Identifikator oder ( erwartet.");
            bvList = null;

        }

        debug ("Leave parseVars");
        return bvList;
    }

    /**
     * VARLIST ::= "identifier" { "," VARLIST }
     */
    private BvarList parseVarList() throws IOException {
        debug ("Enter parseVarList");

        // "identifier" 
        String sName = token.getValue();
        matchToken (Token.Identifier);

        // { "," VARLIST }
        BvarList bvRest = null;

        if (token.getId() == Token.TOK_KOMMA) {
          matchToken (Token.Komma);
          bvRest = parseVarList();
        }

        BvarList bvList = new BvarList (new Bvar (sName),bvRest);

        debug ("Leave parseVarList");
        return bvList;

    }

    /**
     * EVENT ::= {"var" EVENTS} 
     */
    private SEventList parseEvent() throws IOException {

        // {"var" EVENTS}
        if (token.getId() == Token.TOK_EVENT) {
            matchToken(Token.KeyEvent);
            return parseEvents();
        }
        else
            return null;
    }

    /**
     * EVENTS ::= "identifier" | "(" EVENTLIST ")"
     */   
    private SEventList parseEvents() throws IOException {
        debug ("Enter parseEvents");

        SEventList eList = null;
        
        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :
 
            eList = new SEventList (new SEvent (token.getValue()),null);
            matchToken (Token.Identifier); 
            break;

        case Token.TOK_LPAR       :

            matchToken (Token.LPar);
            eList = parseEventList();
            matchToken (Token.RPar);
            break;

        default                   :

            Error ("Identifikator oder ( erwartet.");
            eList = null;

        }

        debug ("Leave parseEvents");
        return eList;
    }

    /**
     * EVENTLIST ::= "identifier" { "," EVENTLIST }
     */
    private SEventList parseEventList() throws IOException {
        debug ("Enter parseEventList");

        // "identifier" 
        String sName = token.getValue();
        matchToken (Token.Identifier);

        // { "," EVENTLIST }
        SEventList eRest = null;

        if (token.getId() == Token.TOK_KOMMA) {
          matchToken (Token.Komma);
          eRest = parseEventList();
        }

        SEventList eList = new SEventList (new SEvent (sName),eRest);

        debug ("Leave parseEventList");
        return eList;

    }

    /**
     * STATE ::= AND_STATE | OR_STATE | BASIC_STATE
     */
    private State parseState(Path path) throws IOException {
	debug("Enter parseState");

	State state = null;

        switch (token.getId()) {

	case Token.TOK_AND   : 
            state = parseAndState(path);   
            break;

	case Token.TOK_OR    : 
            state = parseOrState(path);    
            break;

	case Token.TOK_BASIC :
            state = parseBasicState(path); 
            break;

	default :
	    Error("Schlüsselwort 'and', 'or' oder 'basic' erwartet");
	}

	debug("Leave parseState");
	return state;
    }	    
	
    /**
     *  AND_STATE ::= "and" "identifier" AND_SUBSTATES "end" "identifier"
     */
    private And_State parseAndState(Path path) throws IOException {
        Location loc = new Location(token.getLine());
	debug("Enter parseAndState");

	// "and" "identifier"
	matchToken(Token.KeyAnd);
	String and_identifier = token.getValue(); 
	matchToken(Token.Identifier);
        Statename statename = new Statename(and_identifier);
 
	// Unterzustaende parsen
        StateList state_list = parseAndSubStates(extendPath(path,and_identifier));

	// AND_SUBSTATES
	And_State state = new And_State(statename, state_list);
        state.location = loc;

	// "end" "identifier"
	matchToken(Token.KeyEnd);
	String end_identifier = token.getValue(); 
	matchToken(Token.Identifier);
	if (!end_identifier.equalsIgnoreCase(and_identifier)) {
	    Error("Bezeichner stimmt nicht mit voriger Definition ueberein."); 
	}

	debug("Leave parseAndState");
	return state;
    }

    /**
     * AND_SUBSTATES ::= OR_STATE { AND_SUBSTATES } | BASIC_STATE { AND_SUBSTATES }
     */
    private StateList parseAndSubStates(Path path) throws IOException {
	debug("Enter parseAndSubState");

	StateList statelist = null;

        switch (token.getId()) {

	case Token.TOK_AND   :
	    Error("And-Zustaende duerfen keine weiteren And-Zustaende enthalten");
            statelist = new StateList( parseAndState(path),
                                       parseAndSubStates(path)); 

	    // And-Zustand vergessen.
	    statelist = null;
	    break;

	case Token.TOK_OR    : 
            statelist = new StateList( parseOrState(path),
                                       parseAndSubStates(path)); 
            break;
	case Token.TOK_BASIC : 
            statelist = new StateList( parseBasicState(path), 
                                       parseAndSubStates(path)); 
            break;
	default              : 
            statelist = null;
	}

	debug("Leave parseAndSubState");
	return statelist;
    }

    /**
     *  OR_STATE ::= "or " {"identifier"} 
     *                     OR_SUBSTATES
     *                     CONNECTORS
     *                     TRANSITIONS
     *                     DEFAULTCON
     *               "end" {"identifier"}
     */
    private Or_State parseOrState(Path path) throws IOException {
	debug("Enter parseOrState");

	// "or " {"identifier"}
	matchToken(Token.KeyOr);
	String or_identifier = token.getValue();
	matchToken(Token.Identifier);
	Statename statename = new Statename(or_identifier);

	// OR_SUBSTATES
	StateList statelist = parseOrSubStates(extendPath(path,or_identifier));

	// CONNECTORS
	ConnectorList connlist = parseConnectors();

	// TRANSITIONS
	TrList tranlist = parseTransitions(connlist);

	// DEFAULTCON
	StatenameList deflist = parseDefaultCons();
	
	// "end" {"identifier"}
	matchToken(Token.KeyEnd);
	String end_identifier = token.getValue(); 
	matchToken(Token.Identifier);
	debug("End " + end_identifier);
	if (!end_identifier.equalsIgnoreCase(or_identifier)) {
	    Error("Bezeichner stimmt nicht mit voriger Definition ueberein."); 
	}

	debug("Leave parseOrState");
	return new Or_State(statename, 
			    statelist,
			    tranlist,
			    deflist,
			    connlist);	
    }

    /**
     * OR_SUBSTATES  ::= STATE { OR_SUBSTATES }
     */
    private StateList parseOrSubStates(Path path) throws IOException {
	debug("Enter parseOrSubState");

	StateList statelist = null;

        switch (token.getId()) {

	case Token.TOK_AND   : 
            statelist = new StateList( parseAndState(path), 
                                       parseOrSubStates(path)); 
            break;

	case Token.TOK_OR    : 
            statelist = new StateList( parseOrState(path),  
                                       parseOrSubStates(path)); 
            break;

	case Token.TOK_BASIC : 
            statelist = new StateList( parseBasicState(path), 
                                       parseOrSubStates(path)); 
            break;

	default              : 
            statelist = null;
	}

	debug("Leave parseOrSubState");	
	return statelist;
    }
   
    /**
     *  BASIC_STATE ::= "basic" "identifier"
     */
    private Basic_State parseBasicState(Path path) throws IOException {
	debug("Enter parseBasicState");

	// BASIC_STATE
	matchToken(Token.KeyBasic);
	String identifier = token.getValue();
	matchToken(Token.Identifier);

	// Pfadliste aktualisieren
        Path new_path = extendPath(path, identifier);

	debug("Leave parseBasicState");
	return new Basic_State(new Statename(identifier));
    }

    /**
     * CONNECTORS ::= CONNECTOR { CONNECTORS }
     */
    private ConnectorList parseConnectors() throws IOException {
	debug("Enter parseConnectors");

	ConnectorList conlist = null;

        switch (token.getId()) {

	case Token.TOK_CON : 
            conlist = new ConnectorList( parseConnector(), 
                                         parseConnectors()); 
            break;

	default : 
            conlist = null;
	}

	debug("Leave parseConnectors");
	return conlist;
    }

    /**
     * CONNECTOR ::= "con" "identifier"
     */
    private Connector parseConnector() throws IOException {
	debug("Enter parseConnector");

	// "con" "identifier"
	matchToken(Token.KeyCon);
	String identifier = token.getValue();
	matchToken(Token.Identifier);

	debug("Leave parseConnector");
	return new Connector(new Conname(identifier));
    }

    /**
     * TRANSITIONS ::= TRANSITION TRANSITION
     */
    private TrList parseTransitions (ConnectorList connlist) throws IOException {
	debug ("Enter parseTransitions");

	TrList tranlist = null;

        switch (token.getId()) {

	case Token.TOK_FROM : 
            tranlist = new TrList (parseTransition(connlist), 
                                   parseTransitions(connlist)); 
            break;

	default : 
            tranlist = null;
	}

	debug ("Leave parseTransitions");
	return tranlist;
    }

    /**
     * TRANSITION ::= "from" TRANCHOR "to" TRANCHOR {"on" GUARD} {"do" ACTION}
     */
    private Tr parseTransition (ConnectorList connlist) throws IOException {
	debug ("Enter parseTransition");

	// "from" TRANCHOR
	matchToken(Token.KeyFrom);
        TrAnchor taFrom = parseTransitionAnchor(connlist);

	// "to" TRANCHOR
	matchToken(Token.KeyTo);
        TrAnchor taTo = parseTransitionAnchor(connlist);
	
	// { "on" GUARD }
        Guard guard;
        if (token.getId() == Token.TOK_ON) {
           matchToken(Token.KeyOn);
           guard = parseGuard();
        }
        else
           guard = new GuardEmpty (new Dummy()); 

	// {"do" ACTION }
        Action action = null;
        if (token.getId() == Token.TOK_DO) {
          matchToken(Token.KeyDo);
          action = parseAction();
        }
        else
          action = new ActionEmpty (new Dummy());

	debug ("Leave parseTransition");
        return new Tr( taFrom, taTo, new TLabel(guard, action));
    }

    /**
     * TRANCHOR ::= "UNDEF" | "identifier"
     */
    private TrAnchor parseTransitionAnchor(ConnectorList connlist) 
                                                         throws IOException {
        debug ("Enter parseTransitionAnchor");

	String name = token.getValue();
        TrAnchor anchor;

        if (token.getId() == Token.TOK_UNDEF) { 

            matchToken(Token.KeyUndef);

            anchor = new UNDEFINED();
        }
        else {

            matchToken(Token.Identifier);

            if (isConnectorName(name, connlist))
                anchor = new Conname(name);
            else
                anchor = new Statename(name);
        }

        debug ("Leave parseTransitionAnchor");
        return anchor;
    }


    /** 
     *  DEFAULTCONS ::=  DEFAULTCON { DEFAULTCON }
     */
    private StatenameList parseDefaultCons() throws IOException {
	debug("Enter parseDefaultCons");

	StatenameList snamelist = null;

        switch (token.getId()) {

	case Token.TOK_DEFAULT : 
            snamelist = new StatenameList( parseDefaultCon(), 
                                           parseDefaultCons()); 
            break;

	default : 
            snamelist = null;
	}

	debug("Leave parseDefaultCons");
	return snamelist;
    }

    /**
     * DEFAULTCON ::= "default" "identifier"
     */
    private Statename parseDefaultCon() throws IOException {
	debug("Enter parseDefaultCon");

	// "default" "identifier"
	matchToken(Token.KeyDefault);
	String identifier = token.getValue();
	matchToken(Token.Identifier);

	debug("Leave parseDefaultCon");
	return new Statename(identifier);
    }

    //------------------------------------------------------------------------
    //  Implementierung Guard
    //------------------------------------------------------------------------
    

    /**
     *  GUARD ::= GUARDIMPLIES GUARD_REST
     */
    private Guard parseGuard() throws IOException {
        debug ("Enter parseGuard");
        
        Guard lhs = parseGuardImplies();

        debug ("Leave parseGuard");
        return parseGuardRest(lhs);

    }

    /**
     *  GUARD_REST ::= {"<=>" GUARDIMPLIES GUARD_REST}
     */
    private Guard parseGuardRest(Guard lhs) throws IOException {
        debug ("Enter parseGuardRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_EQUIV) {
            matchToken(Token.Equiv);
            rhs = parseGuardImplies();
            comp = parseGuardRest(
                     new GuardCompg (new Compguard (Compguard.EQUIV,
                                                    lhs,
                                                    rhs)));
        }
        else {
            comp = lhs;
        }

        debug ("Leave parseGuardRest");
        return comp;
    }

    /**
     *  GUARDIMPLIES ::= GUARDOR GUARDIMPLES_REST
     */
    private Guard parseGuardImplies()  throws IOException {
        debug ("Enter parseGuardImplies");
        
        Guard lhs = parseGuardOr();

        debug ("Leave parseGuardImplies");
        return parseGuardImpliesRest(lhs);
    }

    /**
     *  GUARDIMPLIES_REST ::= {"=>" GUARDOR GUARDIMPLIES_REST}
     */
    private Guard parseGuardImpliesRest(Guard lhs) throws IOException {
        debug ("Enter parseGuardImpliesRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_IMPLIES) {
            matchToken(Token.Implies);
            rhs = parseGuardOr();
            comp = parseGuardImpliesRest(
                     new GuardCompg (new Compguard (Compguard.IMPLIES,
                                                    lhs,
                                                    rhs)));
        }
        else {
            comp = lhs;
        }

        debug ("Leave parseGuardImpliesRest");
        return comp;
    }

     /**
     *  GUARDOR ::= GUARDAND GUARDOR_REST
     */
    private Guard parseGuardOr()  throws IOException {
        debug ("Enter parseGuardOr");
        
        Guard lhs = parseGuardAnd();

        debug ("Leave parseGuardOr");
        return parseGuardOrRest(lhs);
    }

    /**
     *  GUARDOR_REST ::= {"|" GUARDAND GUARDOR_REST}
     */
    private Guard parseGuardOrRest(Guard lhs) throws IOException {
        debug ("Enter parseGuardOrRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_OROP) {
            matchToken(Token.OrOp);
            rhs = parseGuardAnd();
            comp = parseGuardOrRest(
                     new GuardCompg (new Compguard (Compguard.OR,
                                                    lhs,
                                                    rhs)));
        }
        else {
            comp = lhs;
        }

        debug ("Leave parseGuardOrRest");
        return comp;
    }

    /**
     *  GUARDAND ::= GUARDID GUARDAND_REST
     */
    private Guard parseGuardAnd()  throws IOException {
        debug ("Enter parseGuardAnd");
        
        Guard lhs = parseGuardIdentifier();

        debug ("Leave parseGuardAnd");
        return parseGuardAndRest(lhs);
    }

    /**
     *  GUARDAND_REST ::= {"&" GUARDID GUARDAND_REST}
     */
    private Guard parseGuardAndRest(Guard lhs) throws IOException {
        debug ("Enter parseGuardAndRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_ANDOP) {
            matchToken(Token.AndOp);
            rhs = parseGuardIdentifier();
            comp = parseGuardAndRest(
                     new GuardCompg (new Compguard (Compguard.AND,
                                                    lhs,
                                                    rhs)));
        }
        else {
            comp = lhs;
        }

        debug ("Leave parseGuardAndRest");
        return comp;
    }

    /**
     *  GUARDID ::= "identifier" | "!" GUARD | COMPPATH | "(" GUARD ")"
     */
   private Guard parseGuardIdentifier() throws IOException {
        debug ("Enter parseGuardIdentifier");

        Guard g = null;

        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :
            
            String name = token.getValue();
            matchToken(Token.Identifier);

            if (isEventName(name, eventlist)) {
                g = new GuardEvent(getEvent(name,eventlist));                 
            }
            else if (isBvarName(name, bvarlist)) {
                g = new GuardBVar(getBvar(name,bvarlist)); 
            }
            else {
              Error ("Guard enthält weder Event noch Bvar: " +
                    token.getValue());
              g = null;
            }
            break;

        case Token.TOK_NOTOP      :

            matchToken (Token.NotOp);
            g = new GuardNeg (parseGuard());
            break;

        case Token.TOK_ENTERED    :
        case Token.TOK_EXITED     :
        case Token.TOK_IN         :
                     
            g = new GuardCompp (parseCompPath());
            break;
        
        case Token.TOK_LPAR       :

          matchToken (Token.LPar);
          g = parseGuard();
          matchToken (Token.RPar);
          break;
 
        default                   :

            Error ("Guard erwartet.");
        }

        debug ("Leave parseGuardIdentifier");
        return g;
    }

    /**
     *  COMPPATH ::= ( IN | ENTERED | EXITED ) "(" PATH ")"
     */ 
    private Comppath parseCompPath() throws IOException {
 
        int pathop;

        // ( IN | ENTERED | EXITED ) 
        switch (token.getId()) {

        case Token.TOK_ENTERED    : 

            matchToken(Token.KeyEntered);
            pathop = Comppath.ENTERED; 
            break;

        case Token.TOK_EXITED     : 
            
            matchToken(Token.KeyExited);
            pathop = Comppath.EXITED;  
            break;

        case Token.TOK_IN         : 

            matchToken(Token.KeyIn);
            pathop = Comppath.IN;      
            break;

        default                   :

            Error ("Schluesselwort in, entered oder exited erwartet.");
            pathop = 0;
        }

        // "(" "identifier" ")"
        matchToken(Token.LPar);
        Comppath cp = new Comppath(pathop, parsePath());
        matchToken(Token.RPar);

        return cp;
    }    
 
    /**
     *  PATH := "identifier" {"." PATH} 
     */
    private Path parsePath() throws IOException {
       
        // "identifier"
        String sHead = token.getValue();
        matchToken(Token.Identifier);

        // {"." PATH}
        Path pTail = null;

        if (token.getId() == Token.TOK_DOT) {
            pTail = parsePath();
        }

        return new Path (sHead, pTail);
    }

 
    
    //------------------------------------------------------------------------
    //  Implementierung Action
    //------------------------------------------------------------------------

    /**
     * ACTION ::= "identifier" | BOOLSTMT | "(" ACTIONLIST ")" 
     */
    private Action parseAction() throws IOException {
        debug ("Enter parseAction");

        Action ac = null;
        
        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :

            // "identifier"
            String sName = token.getValue(); 

            if (isEventName(sName, eventlist)) {
                matchToken (Token.Identifier);
                ac = new ActionEvt(getEvent(sName,eventlist));
            }
            else {
                // BOOLSTMT
                ac = new ActionStmt(parseBoolStmt());
            }
            break;

        case Token.TOK_LPAR       :

            matchToken (Token.LPar);
            ac = new ActionBlock(parseActionList());
            matchToken (Token.RPar);
            break;

        default                   :

            Error ("Identifikator oder ( erwartet.");

        }

        debug ("Leave parseAction");
        return ac;
    }

    /** 
     * BOOLSTMT ::= "identifier" "=" ( true | false | GUARD )
     */
    private Boolstmt parseBoolStmt() throws IOException {

        // "identifier" ":="
        String sName = token.getValue();
        matchToken(Token.Identifier);
        matchToken(Token.Assign);

        Boolstmt bs = null;

        // ( true | false | GUARD )
        switch (token.getId()) {

        case Token.TOK_TRUE       :

            matchToken(Token.KeyTrue);
            if (isBvarName (sName,bvarlist)) {
                bs = new MTrue (getBvar (sName,bvarlist));
            }
            else
               Error (sName + " ist keine Variable.");
 
            break;

        case Token.TOK_FALSE      :
 
            matchToken(Token.KeyFalse);
            if (isBvarName (sName,bvarlist)) {
                bs = new MFalse (getBvar (sName,bvarlist));
            }
            else
               Error (sName + " ist keine Variable.");

            break;
 
        case Token.TOK_IDENTIFIER :
        case Token.TOK_ENTERED    :
        case Token.TOK_EXITED     :
        case Token.TOK_IN         :
        case Token.TOK_LPAR       :

            bs = new BAss (
                           new Bassign (getBvar (sName,bvarlist),
                                        parseGuard()));
            break;

        default                   :
        
            Error("true, false oder Guard erwartet.");
        }
 
        return bs;
    }
 

    /**
     * ACTIONLIST ::= ( "identifier" | BOOLSTMT ) { "," ACTIONLIST }
     */
    private Aseq parseActionList() throws IOException {
        debug ("Enter parseActionList");

        Action ac = null;
        Aseq asRest = null;

        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :

            // "identifier"
            String sName = token.getValue();

            if (isEventName(sName, eventlist)) {
                matchToken (Token.Identifier);
                ac = new ActionEvt(getEvent(sName,eventlist));
            }
            else {
                // BOOLSTMT
                ac = new ActionStmt(parseBoolStmt());
            }

            // { "," ACTIONLIST }
            if (token.getId() == Token.TOK_KOMMA) {
              matchToken (Token.Komma);
              asRest = parseActionList();
            }
 
            break;

        default :

            Error("Identifier erwartet.");
        }

        debug ("Leave parseActionList");
        return new Aseq (ac,asRest);
    }

}
    
//----------------------------------------------------------------------
//      Schnittstelle zum TESCParser               
//      ----------------------------               
//
//      $Log: not supported by cvs2svn $
//      Revision 1.5  1998/12/17 11:14:11  swtech20
//      BufferedReader statt FileInputStream.
//
//      Revision 1.4  1998/12/15 18:11:37  swtech00
//      Towards new naming conventions for PEST2
//
//      Revision 1.3  1998/12/13 17:49:06  swtech20
//      Checkin für Baseline
//
//      Revision 1.2  1998/12/03 13:08:18  swtech20
//      Geaenderte TESC1-Schnittstelle nach Absprache mit Gruppe TESC1 aus PEST1.
//      Die Schnittstellen sind jetzt für beide Gruppen gleich.
//
//              
//----------------------------------------------------------------------
