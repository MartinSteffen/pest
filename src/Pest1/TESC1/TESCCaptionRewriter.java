package tesc1;

import absyn.*;
import java.io.*;
import java.util.*;

/**
 * Generiert fuer ein Statechart die Caption in TLabel neu.<br>
 * Da ich etwas unter Zeitmangel leide, habe ich von PEST2.tesc1 kopiert, und den Code angepaßt.<br>
 * Die Fehlerabfragen dürften nicht funktionieren.
 * <p>
 * <hr>
 * @version  $Id: TESCCaptionRewriter.java,v 1.1 1999-02-17 21:52:07 swtech13 Exp $
 * @author Arne Koch, Michael Suelzer, Christoph Schuette.
 *  
 */   
class TESCCaptionRewriter {

    protected int warningCount = 0;
    protected Vector warningText = null;

    protected static int errorCount = 0;
    protected static Vector errorText = new Vector();

    private TESCSaver writer = null;

    public TESCCaptionRewriter (Statechart sc) throws IOException {
	writer = new TESCSaver(null);
	rewrite((State)sc.state);
    }


    //------------------------------------------------------------------------
    //  Hilfsfunktion(en)
    //------------------------------------------------------------------------


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
	errorText.addElement("Fehler : " + msg);
        debug (msg);
        errorCount++;
    }


    /**
     * Speichert einen Warnungstext.
     * @param msg Fehlertext
     */ 
    protected void Warning(String msg) {
	warningText.addElement("Info : " + msg);
        debug (msg);
	warningCount++;
    }


    /**
     * Gibt einen String aus, falls der globale Debug-Schalter gesetzt ist.
     * @param s Text
     */    
    protected void debug(String s) {
	if (writer.isDebug()) System.out.println("TESCCaptionRewriter:" + s);	
    }

    //------------------------------------------------------------------------
    //  Rewrite
    //------------------------------------------------------------------------

    protected void rewrite(State s) throws IOException {
	if (s instanceof And_State)
	    rewrite ((And_State)s);
	else if (s instanceof Or_State)
	    rewrite ((Or_State)s);
    }

    protected void rewrite(And_State as) throws IOException {
	if (as != null) {
	    rewrite (as.substates);
	}
    }

    protected void rewrite(Or_State os) throws IOException {
	if (os != null) {
	    rewrite (os.substates);
	    rewrite (os.trs);
	}		
    }

    protected void rewrite(StateList sListIterator) throws IOException {
	while (sListIterator != null) {
	    rewrite ((State)sListIterator.head);
	    sListIterator = sListIterator.tail;
	}
    }

    protected void rewrite(TrList tListIterator) throws IOException {
	while (tListIterator != null) {
	    rewrite (tListIterator.head.label);
	    tListIterator = tListIterator.tail;
	}	
    }

    protected void rewrite(TLabel l)  throws IOException {

	//writer = new TESCCaptionWriter();
	StringWriter sw = new StringWriter ();
        BufferedWriter bw = new BufferedWriter(sw);
	writer.setCaption (l);
	//l.caption = sw.toString();

	// Sollte wider Erwarten ein Fehler auftreten, dann merken
	/*
	errorCount += writer.getErrorCount();
	for (int i=0; i < writer.getErrorCount(); i++) 
	    Error(writer.getErrorText(i));   

	// Sollt wider Erwarten eine Warnung auftreten, dann merken
	warningCount += writer.getWarningCount();
	for (int i=0; i < writer.getWarningCount(); i++) 
	    Error(writer.getWarningText(i));   
	*/

    }
}
