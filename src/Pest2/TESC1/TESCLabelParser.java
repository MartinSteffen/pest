package tesc1;
   
import java.io.*;
import java.lang.String;
import java.util.Vector;
import absyn.*;
import util.*;

/**
 * Label-Parser fuer den Editor.
 * <p>
 * @author Michael Suelzer, Christoph Schuette.
 * @version  $Id: TESCLabelParser.java,v 1.5 1999-02-07 11:58:30 swtech20 Exp $
 */   
class TESCLabelParser extends TESCParser {

    protected Statechart statechart;


    // Der Standard-Ktor muss verborgen werden, da
    // das Parsen eines Labels ohne Statechart
    // keinen Sinn macht.
    private TESCLabelParser() {};

    /**
     * Constructor.
     */
    public TESCLabelParser (Statechart sc) {
	statechart = sc;
	eventlist = sc.events;
	bvarlist  = sc.bvars;
	pathlist  = sc.cnames;
    }

    /**
     * Startet den Parsevorgang fuer ein Label.
     */
    public TLabel readLabel (BufferedReader br) throws IOException {

	warningText = new Vector ();
	warningCount = 0 ;

	errorText = new Vector ();
	errorCount = 0;

	lexer = new TESCTokenizer (br);
	token = lexer.getNextToken ();

	debug ("parseLabel");

	TLabel label = parseLabel ();

	// Die geaenderten Listen nach aussen geben
	statechart.bvars = bvarlist;
	statechart.events = eventlist;
	statechart.cnames = pathlist;

        return label;
    }


    /**
     *  LABEL ::= {GUARD} {"/" ACTION}
     */
    public TLabel parseLabel () throws IOException {
	//debug ("Enter parseLabel");

	Guard guard = parseGuardSTMStyle ();
	Action action = null;
	if (token.getId () == Token.TOK_SLASH) {
	    matchToken (Token.Slash);
	    action = parseActions ();
	}
	else
          action = new ActionEmpty (new Dummy ());

	// Caption fuer TLabel aufbauen.
	TLabel label = new TLabel (guard, action);

	TESCSaver saver = new TESCSaver (null);
        saver.setCaption (label);

	// Sollte wider Erwarten ein Fehler auftreten, dann merken
	errorCount += saver.getErrorCount ();
	for (int i=0; i < saver.getErrorCount (); i++) 
	    Error (saver.getErrorText (i));   

	//debug ("Leave parseLabel");
        return label;
    }
}

//----------------------------------------------------------------------
//	Label-Parser
//	------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1999/02/01 11:52:58  swtech20
//	- globaler Debug-Schalter
//
//	Revision 1.3  1999/01/20 17:32:10  swtech20
//	- Status und Doku aktualisiert
//	- Fehler, dass Anderungen an Bvarlisten ... nicht nach aussen-
//	  gegeben werden behoben.
//
//	Revision 1.2  1999/01/18 17:08:52  swtech20
//	- okDialog -> userMessage
//	- Pruefung auf gui==null
//	- package visibility fuer Nicht-Schnittstellenklassen
//
//	Revision 1.1  1999/01/17 17:16:41  swtech20
//	Umstellung der Guard-Syntax auf Statemate-Style, Implementierung des
//	LabelParsers fuer den Editor. Anpassung der Schnittstelle.
//
//
//
//
//----------------------------------------------------------------------

