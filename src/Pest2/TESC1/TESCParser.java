package tesc1;
   
import java.io.*;
import java.util.Vector;
import absyn.*;
import util.*;

/**
 * Parser für TESC.
 * <p>
 * @author Michael Sülzer, Christoph Schütte.
 * @version  $Id: TESCParser.java,v 1.4 1998-12-15 18:11:37 swtech00 Exp $
 */   
public class TESCParser {
    
    private static boolean DEBUG = false;  

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
    public Statechart parseStream(FileInputStream fis) throws IOException {
	errorText = new Vector();
	errorCount = 0;
	lexer = new TESCTokenizer(fis);
	debug("parseTESC");
	//eventlist = new SEventList();
	statechart = parseTesc();
        (new util.PrettyPrint()).start(statechart);
	return statechart;
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
     * @return Fehlertext des n-ten Fehlers.
     */
    public String getErrorText(int n) {return (String)errorText.elementAt(n);}
    

    private void Error(String msg) {
	errorText.addElement("Zeile " + token.getLine() + " : " + msg);
        errorCount++;
    }

    private void matchToken(int t) throws IOException{ 
	if (token.getId() == t) {
	    debug("Match: " + token.getValue());
	    token = lexer.getNextToken();
	}
	else {
	    Error("Syntaxfehler. " + token.getValue() + " erwartet.");
	    debug("Syntaxfehler. " + token.getValue() + " erwartet.");
	}
    }
    
    private void debug(String s) {
        if (DEBUG) System.out.println(s);
    }
  
    //----------------------------------------------------------------------------
    //  Implementierung der TESC-Grammatik
    //----------------------------------------------------------------------------

    /**
     *  TESC ::= STATE
     */
    private Statechart parseTesc() throws IOException {
	debug("Enter parseTESC");

	token = lexer.getNextToken();
        State rootstate = parseState();

	debug("Leave parseTESC");
	return new Statechart(eventlist, bvarlist, pathlist, rootstate);
    }

    /**
     * STATE ::= AND_STATE | OR_STATE | BASIC_STATE
     */
    private State parseState() throws IOException {
	debug("Enter parseState");

	State state = null;

        switch (token.getId()) {
	case Token.TOK_AND   : state = parseAndState();   break;
	case Token.TOK_OR    : state = parseOrState();    break;
	case Token.TOK_BASIC : state = parseBasicState(); break;
	default :
	    Error("Schlüsselwort 'and', 'or' oder 'basic' erwartet");
	}

	debug("Leave parseState");
	return state;
    }	    
	
    /**
     *  AND_STATE ::= "and" "identifier" AND_SUBSTATES "end" "identifier"
     */
    private And_State parseAndState() throws IOException {
	debug("Enter parseAndState");

	// "and" "identifier"
	matchToken(Token.TOK_AND);
	String and_identifier = token.getValue(); 
	matchToken(Token.TOK_IDENTIFIER);
	debug("AndState " + and_identifier);

	// AND_SUBSTATES
	And_State state = new And_State(new Statename(and_identifier), parseAndSubStates());

	// "end" "identifier"
	matchToken(Token.TOK_END);
	String end_identifier = token.getValue(); 
	matchToken(Token.TOK_IDENTIFIER);
	debug("End " + end_identifier);
	if (!end_identifier.equalsIgnoreCase(and_identifier)) {
	    Error("Bezeichner stimmt nicht mit voriger Definition ueberein."); 
	}

	debug("Leave parseAndState");
	return state;
    }

    /**
     * AND_SUBSTATES ::= OR_STATE { AND_SUBSTATES } | BASIC_STATE { AND_SUBSTATES }
     */
    private StateList parseAndSubStates() throws IOException {
	debug("Enter parseAndSubState");

	StateList statelist = null;

        switch (token.getId()) {
	case Token.TOK_AND   : {
	    Error("And-Zustaende duerfen keine weiteren And-Zustaende enthalten");

	    // And-Zustand lesen und vergessen.
	    parseAndState();
	    statelist = null;
	}
	case Token.TOK_OR    : statelist = new StateList( parseOrState(),    parseAndSubStates()); break;
	case Token.TOK_BASIC : statelist = new StateList( parseBasicState(), parseAndSubStates()); break;
	default              : statelist = null;
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
    private Or_State parseOrState() throws IOException {
	debug("Enter parseOrState");

	// "or " {"identifier"}
	matchToken(Token.TOK_OR);
	String or_identifier = token.getValue();
	matchToken(Token.TOK_IDENTIFIER);
	Statename statename = new Statename(or_identifier);

	// OR_SUBSTATES
	StateList statelist = parseOrSubStates();

	// CONNECTORS
	ConnectorList connlist  = parseConnectors();

	// TRANSITIONS
	TrList tranlist = parseTransitions();

	// DEFAULTCON
	StatenameList deflist = parseDefaultCons();
	
	// "end" {"identifier"}
	matchToken(Token.TOK_END);
	String end_identifier = token.getValue(); 
	matchToken(Token.TOK_IDENTIFIER);
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
    private StateList parseOrSubStates() throws IOException {
	debug("Enter parseOrSubState");

	StateList statelist = null;

        switch (token.getId()) {
	case Token.TOK_AND   : statelist = new StateList( parseAndState(),   parseOrSubStates()); break;
	case Token.TOK_OR    : statelist = new StateList( parseOrState(),    parseOrSubStates()); break;
	case Token.TOK_BASIC : statelist = new StateList( parseBasicState(), parseOrSubStates()); break;
	default              : statelist = null;
	}

	debug("Leave parseOrSubState");	
	return statelist;
    }
   
    /**
     *  BASIC_STATE ::= "basic" "identifier"
     */
    private Basic_State parseBasicState() throws IOException {
	debug("Enter parseBasicState");

	// BASIC_STATE
	matchToken(Token.TOK_BASIC);
	String identifier = token.getValue();
	matchToken(Token.TOK_IDENTIFIER);

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
	case Token.TOK_CON   : conlist = new ConnectorList( parseConnector(), parseConnectors()); break;
	default              : conlist = null;
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
	matchToken(Token.TOK_CON);
	String identifier = token.getValue();
	matchToken(Token.TOK_IDENTIFIER);

	debug("Leave parseConnector");
	return new Connector(new Conname(identifier));
    }

    /**
     * TRANSITIONS ::= TRANSITION TRANSITION
     */
    private TrList parseTransitions() throws IOException {
	debug("Enter parseTransitions");

	TrList tranlist = null;

        switch (token.getId()) {
	case Token.TOK_CON   : tranlist = new TrList( parseTransition(), parseTransitions()); break;
	default              : tranlist = null;
	}

	debug("Leave parseTransitions");
	return tranlist;
    }

    /**
     * TRANSITION ::= "from" "identifier" "to" "indentifier" "on" GUARD {"do" ACTION }
     */
    private Tr parseTransition() throws IOException {
	debug("Enter parseTransition");

	// "from" "identifier"
	matchToken(Token.TOK_FROM);
	String from_identifier = token.getValue();
	matchToken(Token.TOK_IDENTIFIER);
	Statename from_statename = new Statename(from_identifier);
	
	// "to" "indentifier"
	matchToken(Token.TOK_TO);
	String to_identifier = token.getValue();
	matchToken(Token.TOK_IDENTIFIER);
	Statename to_statename = new Statename(to_identifier);
	
	// "on" GUARD
	//Guard guard = parseGuard(); 

	// {"do" ACTION }
	//Action action = parseAction();

	debug("Leave parseTransition");
	//return new Tr( from, to, new TLabel(guard, action));
	return null;
    }

    /** 
     *  DEFAULTCONS ::=  DEFAULTCON { DEFAULTCON }
     */
    private StatenameList parseDefaultCons() throws IOException {
	debug("Enter parseDefaultCons");

	StatenameList snamelist = null;

        switch (token.getId()) {
	case Token.TOK_CON   : snamelist = new StatenameList( parseDefaultCon(), parseDefaultCons()); break;
	default              : snamelist = null;
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
	matchToken(Token.TOK_DEFAULT);
	String identifier = token.getValue();
	matchToken(Token.TOK_IDENTIFIER);

	debug("Leave parseDefaultCon");
	return new Statename(identifier);
    }
}
    
//----------------------------------------------------------------------
//      Schnittstelle zum TESCParser               
//      ----------------------------               
//
//      $Log: not supported by cvs2svn $
//      Revision 1.3  1998/12/13 17:49:06  swtech20
//      Checkin für Baseline
//
//      Revision 1.2  1998/12/03 13:08:18  swtech20
//      Geaenderte TESC1-Schnittstelle nach Absprache mit Gruppe TESC1 aus PEST1.
//      Die Schnittstellen sind jetzt für beide Gruppen gleich.
//
//              
//----------------------------------------------------------------------
   















