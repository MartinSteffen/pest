package tesc1;
   
import java.io.*;
import java.lang.String;
import java.util.Vector;
import absyn.*;
import util.*;

/**
 * Parser fuer TESC.
 *
 * <p><STRONG>Testmoeglichkeiten: </STRONG> <p>
 * Wer unseren Parser testen moechte, kann sich gemaess der <A HREF="./tesc1/Docu/Grammatik">TESC-Grammatik</A> ein
 * TESC-Programm schreiben und mittels DATEI|IMPORT|TESC in PEST laden.<p> 
 * Alternativ kann das <A HREF="./tesc1/Docu/Example.tesc">Beispiel</A> aus
 * dem Pflichtenheft verwendet werden.
 * <p>
 * <hr>
 * @author Michael Suelzer, Christoph Schuette.
 * @version  $Id: TESCParser.java,v 1.13 1999-02-07 11:56:29 swtech20 Exp $
 */   
class TESCParser {
    
    protected static final int createEvent = 1;
    protected static final int createBvar  = 2;

    protected SEventList eventlist;
    protected BvarList bvarlist;
    protected PathList pathlist;

    protected int warningCount = 0;
    protected Vector warningText = null;

    protected int errorCount = 0;
    protected Vector errorText = null;

    protected TESCTokenizer lexer = null;
    protected Token token = null;


    /**
     * Constructor fuer den TESCParser.
     */
    public TESCParser () {}

    
    /**
     * Startet den Parsevorgang fuer ein Statechart.
     */
    public Statechart readStatechart(BufferedReader br) throws IOException {

	warningText = new Vector();
	warningCount = 0 ;

	errorText = new Vector();
	errorCount = 0 ;

	lexer = new TESCTokenizer(br);
	token = lexer.getNextToken();

	debug("parseTESC");

        pathlist = null;
	return parseTesc();
    }


    /**
     * Startet den Parsevorgang fuer einen Waechter.
     */
    public Guard readGuard(BufferedReader br) throws IOException {

	warningText = new Vector();
	warningCount = 0 ;

	errorText = new Vector();
	errorCount = 0;

	lexer = new TESCTokenizer(br);
	token = lexer.getNextToken();

	debug("parseGuard");

        pathlist = null;
        bvarlist = null;
        eventlist = null;

	return parseGuardSTMStyle();
    }


    /**
     * Startet den Parsevorgang fuer eine Action.
     */
    public Action readAction(BufferedReader br) throws IOException {

	warningText = new Vector();
	warningCount = 0 ;

	errorText = new Vector();
	errorCount = 0;

	lexer = new TESCTokenizer(br);
	token = lexer.getNextToken();
	debug("parseAction");

        pathlist = null;
        bvarlist = null;
        eventlist = null;

	return parseActions();
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
     * Liefert die Anzahl der Warnungen, die beim letzten Parsen
     * aufgetreten sind.
     * @return Anzahl der Warnungen.
     */
    public int getWarningCount() {return warningCount;}
    
    
    /**
     * Liefert den Warnungstext zur n-ten Warnung, der beim letzen
     * Parsen aufgetreten ist.
     * @param n imdex
     * @return Text.
     */
    public String getWarningText(int n) {return (String)warningText.elementAt(n);}
    

    /**
     * Speichert einen Fehlertext.
     * @param msg Fehlertext
     */ 
    protected void Error(String msg) {
	errorText.addElement("Zeile " + token.getLine() + " : Fehler : " + msg);
        debug ("Zeile " + token.getLine() + " : " + msg);
        errorCount++;
    }


    /**
     * Speichert einen Warnungstext.
     * @param msg Fehlertext
     */ 
    protected void Warning(String msg) {
	warningText.addElement("Zeile " + token.getLine() + " : Info : " + msg);
        debug ("Zeile " + token.getLine() + " : Info : " + msg);
	warningCount++;
    }


    /**
     * Gibt einen String aus, falls der globale Debug-Schalter gesetzt ist.
     * @param s Text
     */    
    protected void debug(String s) {
        if (TESCLoader.gui != null) {
	    if (TESCLoader.gui.isDebug()) System.out.println( TESCLoader.PACKAGE_NAME + s);
	}
    }

    //------------------------------------------------------------------------
    //  Hilsfunktion(en) zum Parsen
    //------------------------------------------------------------------------


    /**
      * Prueft, ob ein gewuenschtes Token im Eingabestrom vorliegt.
      * Falls ja, wird das naechste Token gelesen.
      * Falls nein, wird ein Fehler ausgegeben und fortgefahren.
      * @param t Zu pruefendes Token 
      * @exception IOException Falls im Tokenizer ein Fehler auftritt.
      */
    protected void matchToken(Token t) throws IOException{ 

	if ((t.getId() == Token.TOK_IDENTIFIER) && 
	    (Keyword.isReserved(token.getValue()))) {
	    Error("Fehler. " + token.getValue() + " ist reserviert.");
	    debug("Fehler. " + token.getValue() + " ist reserviert.");
	    token = lexer.getNextToken();
	}
	else {
	    if (token.getId() == t.getId()) {
		debug("Match: " + token.getValue());
		token = lexer.getNextToken();
	    }
	    else {
		Error("Syntaxfehler. " + t.getValue() + " statt " +
		      token.getValue()+ " erwartet.");
		debug("Syntaxfehler. " + t.getValue() + " statt " +
		      token.getValue()+ " erwartet.");
		token = lexer.getNextToken();
	    }
	}
    }


    /** 
      * Setzt in einem Absyn-Objekt die Zeilennummer ein.
      */ 
    protected Absyn setLoc(Absyn a,Location l) {a.location = l; return a;}


    //------------------------------------------------------------------------
    //  Hilfsfunktion(en) zum Aufbauen des Statecharts
    //------------------------------------------------------------------------


    /**
     *  Erweitert den Pfad für den weiteren rekursiven Abstieg.
     *  @param  path Bisheriger Pfad
     *  @param  s    Erweiterung
     *  @return Erweiterter Pfad 
     */
    protected Path extendPath (Path path, String s) {

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
    protected void appendPathList (Path path) {
       
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
    protected boolean isConnectorName (String s, ConnectorList clIterator) {

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
    protected boolean isEventName (String s, SEventList elIterator) {

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
     * Prueft, ob ein Event in <code>eventlist</code> enthalten ist.
     * @param  s String
     * @return boolean Gefunden oder nicht
     */
    protected boolean isEventName (String s) {
	return isEventName (s,eventlist);
    }


    /**
     * Prueft, ob eine Variable in einer Liste enthalten ist.
     * @param  s String
     * @param  blIterator Liste
     * @return boolean Gefunden oder nicht. 
     */
    protected boolean isBvarName (String s, BvarList blIterator) {

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
     * Prueft, ob eine Variable in <code>bvarlist</code> enthalten ist.
     * @param  s String
     * @return boolean Gefunden oder nicht. 
     */
    protected boolean isBvarName (String s) {
	return isBvarName (s,bvarlist);
    }


    /**
     * Clont ein SEvent-Objekt aus einer Liste und paßt die Location an.
     * Statt cloneEventLocalized sollte eher getEvent verwendet werden.
     * @param  s String
     * @param  elIterator Liste
     * @return SEvent Element aus Liste
     */
    protected SEvent cloneEventLocalized (String s, SEventList elIterator, Location loc) {

        if (elIterator == null) { 
            return null;       
        }
        else {
            while (elIterator != null) {
                if (elIterator.head.name.equalsIgnoreCase(s)) {
		    String sName = new String(elIterator.head.name);
		    return (SEvent) setLoc( new SEvent(sName),loc);
                }
                elIterator = elIterator.tail;
            }
            return null;  
        }
    }


    /**
     * Clont Bvar-Objekt aus Liste und paßt die Location an.
     * Statt cloneBvarLocalized sollte eher getBvar verwendet werden.
     * @param  s String
     * @param  blIterator Liste
     * @return Bvar Element aus Liste 
     */
    protected Bvar cloneBvarLocalized (String s, BvarList blIterator, Location loc) {

        if (blIterator == null) { 
            return null;       
        }
        else {
            while (blIterator != null) {
                if (blIterator.head.var.equalsIgnoreCase(s)) {
		    String sName = new String(blIterator.head.var);
		    return (Bvar) setLoc( new Bvar(sName),loc);
                }
                blIterator = blIterator.tail;
            }
            return null;  
        }
    }


    /**
     * Holt ein SEvent aus eventlist oder fuegt eine neues hinzu.
     * @param  s String
     * @param  l Location
     * @return SEvent Element aus Liste
     */
    protected SEvent getEvent (String s, Location l) {

	// In der aktuellen Liste nachsehen
	SEvent event  = cloneEventLocalized (s,eventlist,l);

	// Wenn nicht gefunden, dann Liste um neuen Eintrag per prepend erweitern
	if (event == null) {
	    Warning (s + " implizit als Event deklariert");

	    event = (SEvent) setLoc(new SEvent (s),l);
	    eventlist = new SEventList (event,eventlist);
	}

	return event;
    }


    /**
     * Holt eine Bvar aus bvarlist oder fuegt eine neues hinzu.
     * @param  s String
     * @param  l Location
     * @return Bvar Element aus Liste
     */
    protected Bvar getBvar (String s, Location loc) {

	// In der aktuellen Liste nachsehen
	Bvar bvar  = cloneBvarLocalized (s,bvarlist,loc);

	// Wenn nicht gefunden, dann Liste um neuen Eintrag per prepend erweitern
	if (bvar == null) {
	    Warning (s + " implizit als Bvar deklariert");

	    bvar = (Bvar) setLoc(new Bvar (s),loc);
	    bvarlist = new BvarList (bvar,bvarlist);
	}

	return bvar;
    }


    //------------------------------------------------------------------------
    //  Implementierung der TESC-Grammatik
    //------------------------------------------------------------------------


    /**
     *  TESC ::= VAR EVENT STATE
     */
    protected Statechart parseTesc() throws IOException {
	//debug("Enter parseTESC");

	// VAR
        bvarlist = parseVar();

        // EVENT
        eventlist = parseEvent();

        // STATE
        State rootstate = parseState(null);

	matchToken(Token.EOF);

	//debug("Leave parseTESC");
	return new Statechart(eventlist, bvarlist, pathlist, rootstate);
    }


    /**
     * VAR ::= {"var" VARS} 
     */
    protected BvarList parseVar() throws IOException {

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
    protected BvarList parseVars() throws IOException {
        Location loc = new Location(token.getLine());
        //debug ("Enter parseVars");

        BvarList bvList = null;
        
        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :
 
            bvList = new BvarList ((Bvar) setLoc(new Bvar (token.getValue()),loc),null);
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

        //debug ("Leave parseVars");
        return bvList;
    }


    /**
     * VARLIST ::= "identifier" { "," VARLIST }
     */
    protected BvarList parseVarList() throws IOException {
        Location loc = new Location(token.getLine());
        //debug ("Enter parseVarList");

        // "identifier" 
        String sName = token.getValue();
        matchToken (Token.Identifier);

        // { "," VARLIST }
        BvarList bvRest = null;

        if (token.getId() == Token.TOK_KOMMA) {
          matchToken (Token.Komma);
          bvRest = parseVarList();
        }

        BvarList bvList = new BvarList ((Bvar) setLoc(new Bvar (sName),loc),bvRest);

        //debug ("Leave parseVarList");
        return bvList;

    }


    /**
     * EVENT ::= {"event" EVENTS} 
     */
    protected SEventList parseEvent() throws IOException {

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
    protected SEventList parseEvents() throws IOException {
        Location loc = new Location(token.getLine());
        //debug ("Enter parseEvents");

        SEventList eList = null;
        
        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :
 
            eList = new SEventList ((SEvent) setLoc(new SEvent (token.getValue()),loc),null);
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

        //debug ("Leave parseEvents");
        return eList;
    }


    /**
     * EVENTLIST ::= "identifier" { "," EVENTLIST }
     */
    protected SEventList parseEventList() throws IOException {
        Location loc = new Location(token.getLine());
        //debug ("Enter parseEventList");

        // "identifier" 
        String sName = token.getValue();
        matchToken (Token.Identifier);

        // { "," EVENTLIST }
        SEventList eRest = null;

        if (token.getId() == Token.TOK_KOMMA) {
          matchToken (Token.Komma);
          eRest = parseEventList();
        }

        SEventList eList = new SEventList ((SEvent) setLoc(new SEvent (sName),loc),eRest);

        //debug ("Leave parseEventList");
        return eList;

    }


    /**
     * STATE ::= AND_STATE | OR_STATE | BASIC_STATE | REF_STATE
     */
    protected State parseState(Path path) throws IOException {
	//debug("Enter parseState");

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

	case Token.TOK_REF :
            state = parseRefState(path); 
            break;


	default :
	    Error("Schlüsselwort 'and', 'or' oder 'basic' erwartet.");
	}

	//debug("Leave parseState");
	return state;
    }	    
	

    /**
     *  AND_STATE ::= "and" "identifier" AND_SUBSTATES "end" "identifier"
     */
    protected And_State parseAndState(Path path) throws IOException {
        Location loc = new Location(token.getLine());
	//debug("Enter parseAndState");

	// "and" "identifier"
	matchToken(Token.KeyAnd);
	String and_identifier = token.getValue();
	Location locName = new Location(token.getLine());
	matchToken(Token.Identifier);
        Statename statename = (Statename) setLoc(new Statename(and_identifier),locName);
 
	// Unterzustaende parsen
        StateList state_list = parseAndSubStates(extendPath(path,and_identifier));

	// AND_SUBSTATES
	And_State state = (And_State) setLoc(new And_State(statename, state_list),loc);

	// "end" "identifier"
	matchToken(Token.KeyEnd);
	String end_identifier = token.getValue(); 
	matchToken(Token.Identifier);
	if (!end_identifier.equalsIgnoreCase(and_identifier)) {
	    Error("Bezeichner stimmt nicht mit voriger Definition ueberein."); 
	}

	//debug("Leave parseAndState");
	return state;
    }


    /**
     * AND_SUBSTATES ::= OR_STATE {AND_SUBSTATES} | BASIC_STATE {AND_SUBSTATES} | REF_STATE {AND_SUBSTATES}
     */
    protected StateList parseAndSubStates(Path path) throws IOException {
	//debug("Enter parseAndSubState");

	StateList statelist = null;

        switch (token.getId()) {

	case Token.TOK_AND   :
	    Error("And-Zustaende duerfen keine weiteren And-Zustaende enthalten.");
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

	case Token.TOK_REF : 
            statelist = new StateList( parseRefState(path), 
                                       parseAndSubStates(path)); 
            break;


	default              : 
            statelist = null;
	}

	//debug("Leave parseAndSubState");
	return statelist;
    }


    /**
     *  OR_STATE ::= "or " {"identifier"} 
     *                     OR_SUBSTATES CONNECTORS TRANSITIONS DEFAULTCON
     *               "end" {"identifier"}
     */
    protected Or_State parseOrState(Path path) throws IOException {
	Location loc = new Location(token.getLine());
	//debug("Enter parseOrState");

	// "or " {"identifier"}
	matchToken(Token.KeyOr);
	String or_identifier = token.getValue();
	Location locName = new Location(token.getLine());
	matchToken(Token.Identifier);
	Statename statename = (Statename) setLoc(new Statename(or_identifier),locName);

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

	if (!end_identifier.equalsIgnoreCase(or_identifier)) {
	    Error("Bezeichner stimmt nicht mit voriger Definition ueberein."); 
	}

	//debug("Leave parseOrState");
	return (Or_State) setLoc(new Or_State(statename, 
					      statelist,tranlist,
					      deflist,connlist),
				 loc);	
    }


    /**
     * OR_SUBSTATES  ::= STATE { OR_SUBSTATES }
     */
    protected StateList parseOrSubStates(Path path) throws IOException {
	//debug("Enter parseOrSubState");

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

	//debug("Leave parseOrSubState");	
	return statelist;
    }
   

    /**
     *  BASIC_STATE ::= "basic" "identifier"
     */
    protected Basic_State parseBasicState(Path path) throws IOException {
	Location loc = new Location(token.getLine());
	//debug("Enter parseBasicState");

	// BASIC_STATE
	matchToken(Token.KeyBasic);
	String identifier = token.getValue();
	Location locName = new Location(token.getLine());
	matchToken(Token.Identifier);
	Statename statename = (Statename) setLoc(new Statename(identifier),locName);

	// Pfadliste aktualisieren
        Path new_path = extendPath(path, identifier);

	//debug("Leave parseBasicState");
	return (Basic_State) setLoc(new Basic_State(statename),loc);
    }

    /**
     *  REF_STATE  ::= "ref" IDENTIFIER "in" STRING "as" ("tesc" | "pest" | "pest_nocoord")
     */
    protected Ref_State parseRefState(Path path) throws IOException {
	Location loc = new Location(token.getLine());
	//debug("Enter parseRefState");

	// REF_STATE "identifier"
	matchToken(Token.KeyRef);
	String identifier = token.getValue();
	Location locName = new Location(token.getLine());
	matchToken(Token.Identifier);
	Statename statename = (Statename) setLoc(new Statename(identifier),locName);

	// Pfadliste aktualisieren
        Path new_path = extendPath(path, identifier);

        // "in" STRING
        String filename = null;

        matchToken(Token.KeyIn);
        if (token.getId() == Token.TOK_STRING) {
	    filename = token.getValue();
            matchToken(Token.String);
	}

        // "as" ("tesc" | "pest" | "pest_nocoord")
        Syntax_Type type = null;
       
        matchToken(Token.KeyAs);
	Location l = new Location(token.getLine());

	switch (token.getId()) {

	case Token.TOK_TESC   :
	    matchToken(Token.KeyTesc);
	    type = new Tesc_Syntax(l);
	    break;

	case Token.TOK_PEST   :
	    matchToken(Token.KeyPest);
	    type = new Pest_CoordSyntax(l);
	    break;

	case Token.TOK_PEST_NOCOORD   :
	    matchToken(Token.KeyPestNoCoord);
	    type = new Pest_NocoordSyntax(l);
	    break;

	default              :
	    Error("Ungültiger Statecharttyp");
	}

	//debug("Leave parseRefState");
	return new Ref_State(statename,null,loc,filename,type);
    }
    /**

     * CONNECTORS ::= CONNECTOR { CONNECTORS }
     */
    protected ConnectorList parseConnectors() throws IOException {
	//debug("Enter parseConnectors");

	ConnectorList conlist = null;

        switch (token.getId()) {

	case Token.TOK_CON : 
            conlist = new ConnectorList( parseConnector(), 
                                         parseConnectors()); 
            break;

	default : 
            conlist = null;
	}

	//debug("Leave parseConnectors");
	return conlist;
    }


    /**
     * CONNECTOR ::= "con" "identifier"
     */
    protected Connector parseConnector() throws IOException {
	Location loc = new Location(token.getLine());
	//debug("Enter parseConnector");

	// "con" "identifier"
	matchToken(Token.KeyCon);
	String identifier = token.getValue();
	Location locConName = new Location(token.getLine());
	matchToken(Token.Identifier);
	Conname conname = (Conname) setLoc(new Conname(identifier),locConName);
 
	//debug("Leave parseConnector");
	return (Connector) setLoc(new Connector(conname),loc);
    }


    /**
     * TRANSITIONS ::= TRANSITION TRANSITION
     */
    protected TrList parseTransitions (ConnectorList connlist) throws IOException {
	//debug ("Enter parseTransitions");

	TrList tranlist = null;

        switch (token.getId()) {

	case Token.TOK_FROM : 
            tranlist = new TrList (parseTransition(connlist), 
                                   parseTransitions(connlist)); 
            break;

	default : 
            tranlist = null;
	}

	//debug ("Leave parseTransitions");
	return tranlist;
    }


    /**
     * TRANSITION ::= "from" TRANCHOR "to" TRANCHOR {"on" GUARD_STM} {"do" ACTION}
     */
    protected Tr parseTransition (ConnectorList connlist) throws IOException {
	Location loc = new Location(token.getLine());
	//debug ("Enter parseTransition");

	// "from" TRANCHOR
	matchToken(Token.KeyFrom);
        TrAnchor taFrom = parseTransitionAnchor(connlist);

	// "to" TRANCHOR
	matchToken(Token.KeyTo);
        TrAnchor taTo = parseTransitionAnchor(connlist);
	
	// { "on" GUARD }
        Guard guard = null;
        if (token.getId() == Token.TOK_ON) {
           matchToken(Token.KeyOn);
           guard = parseGuardSTMStyle();
        }
        else
           guard = (GuardEmpty) setLoc(new GuardEmpty ((Dummy) setLoc(new Dummy(),loc)),loc); 

	// {"do" ACTION }
        Action action = null;
        if (token.getId() == Token.TOK_DO) {
          matchToken(Token.KeyDo);
          action = parseActions();
        }
        else
          action = (ActionEmpty) setLoc(new ActionEmpty ((Dummy) setLoc(new Dummy(),loc)),loc);

	// Caption fuer TLabel aufbauen.
	TESCSaver saver = new TESCSaver(null);
	TLabel label = new TLabel (guard, action, null, loc, null);
        saver.setCaption(label);

	// Sollt wider Erwarten ein Fehler auftreten, dann merken
	errorCount += saver.getErrorCount();
	for (int i=0; i < saver.getErrorCount(); i++) 
	    Error(saver.getErrorText(i));   

	//debug ("Leave parseTransition");
        return (Tr) setLoc(new Tr( taFrom, taTo, label),loc);
    }


    /**
     * TRANCHOR ::= "UNDEF" | "identifier"
     */
    protected TrAnchor parseTransitionAnchor(ConnectorList connlist) 
                                                         throws IOException {
	Location loc = new Location(token.getLine());
        //debug ("Enter parseTransitionAnchor");

	String name = token.getValue();
        TrAnchor anchor;

        if (token.getId() == Token.TOK_UNDEF) { 

            matchToken(Token.KeyUndef);

            anchor = (UNDEFINED) setLoc(new UNDEFINED(),loc);
        }
        else {

            matchToken(Token.Identifier);

            if (isConnectorName(name, connlist))
                anchor = (Conname) setLoc(new Conname(name),loc);
            else
                anchor = (Statename) setLoc(new Statename(name),loc);
        }

        //debug ("Leave parseTransitionAnchor");
        return anchor;
    }


    /** 
     *  DEFAULTCONS ::=  DEFAULTCON
     *  Nur noch ein Default-Konnektor moeglich.
     *  
     *  DEFAULTCONS ::=  DEFAULTCON { DEFAULTCON }
     */
    protected StatenameList parseDefaultCons() throws IOException {
	//debug("Enter parseDefaultCons");

	StatenameList snamelist = null;

        switch (token.getId()) {

	case Token.TOK_DEFAULT : 
            snamelist = new StatenameList( parseDefaultCon(), 
                                           null);
	    // parseDefaultCons()); 
            break;

	default :
	    Error ("default-Konnektor fehlt.");
            snamelist = null;
	}

	//debug("Leave parseDefaultCons");
	return snamelist;
    }


    /**
     * DEFAULTCON ::= "default" "identifier"
     */
    protected Statename parseDefaultCon() throws IOException {
	Location loc = new Location(token.getLine());
	//debug("Enter parseDefaultCon");

	// "default" "identifier"
	matchToken(Token.KeyDefault);
	String identifier = token.getValue();
	matchToken(Token.Identifier);

	//debug("Leave parseDefaultCon");
	return (Statename) setLoc(new Statename(identifier),loc);
    }


    //------------------------------------------------------------------------
    //  Implementierung Guard
    //------------------------------------------------------------------------
    

   /**
     *  GUARD_STM ::= {GUARD} {"[" GUARD "]"}
     *  Neuer Guard-Aufbau nach Statemate
     */
    protected Guard parseGuardSTMStyle() throws IOException {
 	Location loc = new Location(token.getLine());
        //debug ("Enter parseGuard");
        	
	Guard event = null;
	Guard bvar = null;

	switch (token.getId()) {
	case Token.TOK_IDENTIFIER :
        case Token.TOK_ENTERED    :
        case Token.TOK_EXITED     :
        case Token.TOK_IN         :
        case Token.TOK_NOTOP      :
        case Token.TOK_LPAR       :
	    event = parseGuard(createEvent);
	    break;
	default:
	    event = (GuardEmpty) setLoc(new GuardEmpty ((Dummy) setLoc(new Dummy(),loc)),loc);
        }

	switch (token.getId()) {
	case Token.TOK_LSPAR :
	    matchToken(Token.LSPar);
	    bvar = parseGuard(createBvar);
	    matchToken(Token.RSPar);
	    break;
	default:
	    bvar = (GuardEmpty) setLoc(new GuardEmpty ((Dummy) setLoc(new Dummy(),loc)),loc);
	}

        //debug ("Leave parseGuard");
	return (GuardCompg) setLoc(new GuardCompg ((Compguard) setLoc(new Compguard (Compguard.AND,
										     event,
										     bvar),loc)),loc);
    }


    /**
     *  GUARD ::= GUARDIMPLIES GUARD_REST
     */
    protected Guard parseGuard(int creationType) throws IOException {
        //debug ("Enter parseGuardEvent");
        
        Guard lhs = parseGuardImplies(creationType);

        //debug ("Leave parseGuardEvent");
        return parseGuardRest(lhs,creationType);
    }


    /**
     *  GUARD_REST ::= {"<=>" GUARDIMPLIES GUARD_REST}
     */
    protected Guard parseGuardRest(Guard lhs, int creationType) throws IOException {
 	Location loc = new Location(token.getLine());
	//debug ("Enter parseGuardRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_EQUIV) {
            matchToken(Token.Equiv);
            rhs = parseGuardImplies(creationType);
            comp = parseGuardRest(
                     (GuardCompg) setLoc(new GuardCompg ((Compguard) setLoc(new Compguard (Compguard.EQUIV,
											   lhs,
											   rhs),loc)),loc)
		     ,creationType);
        }
        else {
            comp = lhs;
        }

        //debug ("Leave parseGuardRest");
        return comp;
    }


    /**
     *  GUARDIMPLIES ::= GUARDOR GUARDIMPLES_REST
     */
    protected Guard parseGuardImplies(int creationType)  throws IOException {
        //debug ("Enter parseGuardImplies");
        
        Guard lhs = parseGuardOr(creationType);

        //debug ("Leave parseGuardImplies");
        return parseGuardImpliesRest(lhs,creationType);
    }


    /**
     *  GUARDIMPLIES_REST ::= {"=>" GUARDOR GUARDIMPLIES_REST}
     */
    protected Guard parseGuardImpliesRest(Guard lhs, int creationType) throws IOException {
	Location loc = new Location(token.getLine());
        //debug ("Enter parseGuardImpliesRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_IMPLIES) {
            matchToken(Token.Implies);
            rhs = parseGuardOr(creationType);
            comp = parseGuardImpliesRest(
                     (GuardCompg) setLoc(new GuardCompg ((Compguard) setLoc(new Compguard (Compguard.IMPLIES,
											   lhs,
											   rhs),loc)),loc),
		     creationType
		     );
        }
        else {
            comp = lhs;
        }

        //debug ("Leave parseGuardImpliesRest");
        return comp;
    }


    /**
     *  GUARDOR ::= GUARDAND GUARDOR_REST
     */
    protected Guard parseGuardOr(int creationType)  throws IOException {
        //debug ("Enter parseGuardOr");
        
        Guard lhs = parseGuardAnd(creationType);

        //debug ("Leave parseGuardOr");
        return parseGuardOrRest(lhs,creationType);
    }


    /**
     *  GUARDOR_REST ::= {"|" GUARDAND GUARDOR_REST}
     */
    protected Guard parseGuardOrRest(Guard lhs,int creationType ) throws IOException {
	Location loc = new Location(token.getLine());
        //debug ("Enter parseGuardOrRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_OROP) {
            matchToken(Token.OrOp);
            rhs = parseGuardAnd(creationType);
            comp = parseGuardOrRest(
                     (GuardCompg) setLoc(new GuardCompg ((Compguard) setLoc(new Compguard (Compguard.OR,
											   lhs,
											   rhs),loc)),loc),
		     creationType
		     );
        }
        else {
            comp = lhs;
        }

        //debug ("Leave parseGuardOrRest");
        return comp;
    }


    /**
     *  GUARDAND ::= GUARDNOT GUARDAND_REST
     */
    protected Guard parseGuardAnd(int creationType)  throws IOException {
        //debug ("Enter parseGuardAnd");
        
        Guard lhs = parseGuardNot(creationType);

        //debug ("Leave parseGuardAnd");
        return parseGuardAndRest(lhs,creationType);
    }


    /**
     *  GUARDAND_REST ::= {"&" GUARDNOT GUARDAND_REST}
     */
    protected Guard parseGuardAndRest(Guard lhs,int creationType) throws IOException {
	Location loc = new Location(token.getLine());
        //debug ("Enter parseGuardAndRest");

        Guard rhs = null;
        Guard comp = null;

        if (token.getId() == Token.TOK_ANDOP) {
            matchToken(Token.AndOp);
            rhs = parseGuardNot(creationType);
            comp = parseGuardAndRest(
                     (GuardCompg) setLoc(new GuardCompg ((Compguard) setLoc(new Compguard (Compguard.AND,
											   lhs,
											   rhs),loc)),loc),
		     creationType);
        }
        else {
            comp = lhs;
        }

        //debug ("Leave parseGuardAndRest");
        return comp;
    }


    /**
     *  GUARDNOT ::= {"!"} GUARDID
     */
    protected Guard parseGuardNot(int creationType) throws IOException {
	Location loc = new Location(token.getLine());
	//debug ("Enter parseGuardNot");

	Guard g;

	if (token.getId() == Token.TOK_NOTOP) { 
	    matchToken (Token.NotOp);
            g = (GuardNeg) setLoc(new GuardNeg (parseGuardIdentifier(creationType)),loc);
	}
	else 
	    g = parseGuardIdentifier(creationType);

	//debug ("Leave parseGuardNot");
	return g;
   }


    /**
     *  GUARDID ::= "identifier" | COMPPATH | "(" GUARD ")"
     *
     *  Ueber creationType wird gesteuert, ob EventExpr oder BvarExpr aus
     *  der Guard-Syntax EventExpr[BvarExpr] erzeugt werden soll:
     *   - creatEvent erzeugt Events und weist schon definierte Bvars zurueck
     *   - createBvar erzeugts Bvar und wiest schon definierte Events zurueck
     */
   protected Guard parseGuardIdentifier (int creationType) throws IOException {
	Location loc = new Location(token.getLine());
        //debug ("Enter parseGuardIdentifier");

        Guard g = null;

        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :
            
            String name = token.getValue();
            matchToken(Token.Identifier);

	    // Sind wir in EventExpr oder BvarExpr?
	    switch (creationType) {

	    case createEvent :
		// Wir sind in der EventExpr des Guards, also nur sind Events erlaubt.

		if (isBvarName (name)) Error ("Event-Teil des Guards enthält Bvar.");
		else g = (GuardEvent) setLoc(new GuardEvent (getEvent (name,loc)), loc);     
		break;
 
	    case createBvar :
		// Wir sind in der BvarExpr des Guards, also nur sind BVar erlaubt.

		if (isEventName (name)) Error ("Bvar-Teil des Guards enthält Event.");
		else g = (GuardBVar) setLoc(new GuardBVar (getBvar (name,loc)), loc); 
		break;
	    }
	    break;
	    
        case Token.TOK_ENTERED    :
        case Token.TOK_EXITED     :
        case Token.TOK_IN         :
                     
            g = (GuardCompp) setLoc(new GuardCompp (parseCompPath()),loc);
            break;
        
        case Token.TOK_LPAR       :

          matchToken (Token.LPar);
	  g = parseGuard (creationType);
          matchToken (Token.RPar);
          break;
 
        default                   :

            Error ("Guard erwartet.");
        }

        //debug ("Leave parseGuardIdentifier");
        return g;
    }


    /**
     *  COMPPATH ::= ( IN | ENTERED | EXITED ) "(" PATH ")"
     */ 
    protected Comppath parseCompPath() throws IOException {
	Location loc = new Location(token.getLine());
	//debug ("Enter parseCompPath");

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
        Comppath cp = (Comppath) setLoc(new Comppath(pathop, parsePath()),loc);
        matchToken(Token.RPar);

	//debug("Leave parseCompPath");
        return cp;
    }    

 
    /**
     *  PATH := "identifier" {"." PATH} 
     */
    protected Path parsePath() throws IOException {
       
        // "identifier"
        String sHead = token.getValue();
        matchToken(Token.Identifier);

        // {"." PATH}
        Path pTail = null;

        if (token.getId() == Token.TOK_DOT) {
            matchToken(Token.Dot);
            pTail = parsePath();
        }

        return new Path (sHead, pTail);
    }

     
    //------------------------------------------------------------------------
    //  Implementierung Action
    //------------------------------------------------------------------------

    /**
     *  ACTIONS ::= ( "(" ACTIONLIST ")" ) | ACTION
     */
    protected Action parseActions() throws IOException {
	Location loc = new Location(token.getLine());
        //debug ("Enter parseActions");

	Action action = null;

        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :
	    action = parseAction();
            break;

        case Token.TOK_LPAR       :

            matchToken (Token.LPar);
            action = (ActionBlock) setLoc(new ActionBlock(parseActionList()),loc);
            matchToken (Token.RPar);
            break;

        default                   :

            Error ("Identifikator oder ( erwartet.");

        }
        //debug ("Leave parseActions");
        return action;
    }
    

    /**
     * ACTION ::= "identifier" | ( "identifier" "=" ( true | false | GUARD ))
     */
    protected Action parseAction() throws IOException {
        //debug ("Enter parseAction");

        Action ac = null;
        
	// "identifier"
        switch (token.getId()) {

        case Token.TOK_IDENTIFIER :

            String sName = token.getValue(); 
	    matchToken (Token.Identifier);

	    if (token.getId() == Token.TOK_ASSIGN) {

		Boolstmt bs = null;
		
		matchToken (Token.Assign);
		Location loc = new Location(token.getLine());

		// ( true | false | GUARD )
		switch (token.getId()) {

		case Token.TOK_TRUE       :

		    matchToken(Token.KeyTrue);

		    if (!isEventName (sName)) {
			bs = (MTrue) setLoc(new MTrue (getBvar (sName,loc)),loc);
		    }
		    else
			Error (sName + " ist ein Event.");
 
		    break;


		case Token.TOK_FALSE      :
 
		    matchToken(Token.KeyFalse);
		    if (!isEventName (sName)) {
			bs = (MFalse) setLoc(new MFalse (getBvar (sName,loc)),loc);
		    }
		    else
			Error (sName + " ist ein Event.");
		    
		    break;
 
		case Token.TOK_IDENTIFIER :
		case Token.TOK_ENTERED    :
		case Token.TOK_EXITED     :
		case Token.TOK_IN         :
		case Token.TOK_NOTOP      :
		case Token.TOK_LPAR       :
		    if (!isEventName (sName)) {
			bs = (BAss) setLoc(new BAss (new Bassign (getBvar (sName,loc),parseGuard(createBvar))),loc);
		    }
		    else 
			Error (sName + " ist ein Event.");
	    
		    break;

		default                   :
        
		    Error("true, false oder Bvar-Guard erwartet.");
		}
		ac = new ActionStmt(bs,loc);
	    }
	    else {
		// Eventgenerierung. Bvar sind hier nicht erlaubt.
		Location loc = new Location(token.getLine());

		if (isBvarName (sName)) {
		    Error("Bvar = (true, false oder Bvar-Guard) erwartet.");  
		}
		else {
		    ac = (ActionEvt) setLoc(new ActionEvt(getEvent(sName,loc)),loc);
		}
	    }
	    break;

	default :
	    Error ("Identifier erwartet statt " + token.getValue() + " erwartet.");
	}

        //debug ("Leave parseAction");
        return ac;
    }


    /**
     * ACTIONLIST ::= ACTION { "," ACTIONLIST }
     */
    protected Aseq parseActionList() throws IOException {
        //debug ("Enter parseActionList");

	// { ACTION }
        Action action = parseAction();
        Aseq asRest = null;
	
	// { "," ACTIONLIST }
	if (token.getId() == Token.TOK_KOMMA) {
	    matchToken (Token.Komma);
	    asRest = parseActionList();
	}
 
        //debug ("Leave parseActionList");
        return new Aseq (action,asRest);
    }
}
    
//----------------------------------------------------------------------
//      Schnittstelle zum TESCParser               
//      ----------------------------               
//
//      $Log: not supported by cvs2svn $
//      Revision 1.12  1999/02/01 11:52:59  swtech20
//      - globaler Debug-Schalter
//
//      Revision 1.11  1999/01/20 17:32:11  swtech20
//      - Status und Doku aktualisiert
//      - Fehler, dass Anderungen an Bvarlisten ... nicht nach aussen-
//        gegeben werden behoben.
//
//      Revision 1.10  1999/01/18 17:08:52  swtech20
//      - okDialog -> userMessage
//      - Pruefung auf gui==null
//      - package visibility fuer Nicht-Schnittstellenklassen
//
//      Revision 1.9  1999/01/17 17:16:40  swtech20
//      Umstellung der Guard-Syntax auf Statemate-Style, Implementierung des
//      LabelParsers fuer den Editor. Anpassung der Schnittstelle.
//
//      Revision 1.8  1999/01/11 12:13:55  swtech20
//      Bugfixes.
//
//      Revision 1.7  1999/01/04 15:25:20  swtech20
//      Schluesselworte in Bezeichnern werden nicht mehr zugelassen.
//      Status upgedated.
//
//      Revision 1.6  1999/01/03 21:48:19  swtech20
//      Implementierung des Parsers
//
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
