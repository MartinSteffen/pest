package tesc1;

import absyn.*;
import java.io.*;
import java.util.*;

/**
 * Speichert ein Statechart als TESC-Programm in einem BufferedWriter ab..  
 * <p>
 * <hr>
 * Die Grobstruktur stammt aus dem Pretty-Printer von Eike Schulz. 
 * <hr>
 * <p><STRONG>Testmoeglichkeiten: </STRONG> <p>
 * Jedes Statechart, z.B. das <A HREF="./tesc1/Docu/Example.tesc">Beispiel</A> aus
 * dem Pflichtenheft, kann testweise exportiert werden. 
 * <hr>
 * @version  $Id: TESCWriter.java,v 1.4 1999-02-07 11:56:58 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 *  
 */   
class TESCWriter {

    protected int column;                        // Offset-Spalte
    protected int tab;                           // Einrueck-Tabulator

    protected static final int NORM_COLUMN = 0;  // Einrueckung
    protected static final int NORM_TAB = 4;     // Tabulatorweite

    protected int warningCount = 0;
    protected Vector warningText = null;

    protected static int errorCount = 0;
    protected static Vector errorText = new Vector();


    /**
     * Ktor fuer TESCWriter.
     * @param c Offset-Spalte, in der die Ausgabe beginnen soll
     * @param t Einrueck-Tabulator. 
     *        Dabei muss c ein Vielfaches von t sein, anderenfalls
     *        wird c entsprechend abgerundet.
     */
    public TESCWriter (int c, int t) {

	// Fange Exception ab, falls Division durch 0 auftritt.
	try {
	    column = (c < 0) ? NORM_COLUMN : (c - (c % t));
	    tab = (t < 0) ? NORM_TAB : t;
	} catch (ArithmeticException e) {
	    column = (c - (c % NORM_TAB));
	    tab = NORM_TAB;
	}
    }


    public TESCWriter () { this (NORM_COLUMN, NORM_TAB); }


    //------------------------------------------------------------------------
    //  Hilsfunktion(en) zum Export
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
        if (TESCSaver.gui != null) {
	    if (TESCSaver.gui.isDebug()) System.out.println(TESCSaver.PACKAGE_NAME + s);
	}
    }


    /**
     *  Gib Fuellzeichen-String der Laenge n mit beginnendem Zeilenvorschub zurueck.
     * @param n Laenge n des String
     * @return String mit uebergebener Laenge n
     */
    protected String whiteSpace (int n) { 
	String str = "\n";
	for (int i=0; i<n; i++)
	    str = str + " ";
	    return str;
    }


    /**
     * Prueft, ob der Guard leer ist, d.h.
     * - entweder ein GuardEmpty ist
     * - oder ein Compguard, der nur GuardEmpty enthaelt
     */
    protected boolean isGuardEmpty (Guard g) {
	return (g instanceof GuardEmpty) ||
	       ((g instanceof GuardCompg) && 
		isGuardEmpty (((GuardCompg)g).cguard.elhs) && 
		isGuardEmpty (((GuardCompg)g).cguard.erhs));
    }


    /**
     * Prueft, ob die Action leer ist, d.h.
     * - entweder ein ActionEmpty ist
     * - oder ein ActionBlock, der nur ActionEmpty enthaelt
     */
    protected boolean isActionEmpty (Action a) {
	return (a instanceof ActionEmpty) ||
	       (a instanceof ActionBlock && isAseqEmpty (((ActionBlock)a).aseq.tail));
    }


   /**
     * Prueft, ob die Aseq leer ist, d.h.
     * - entweder null ist
     * - oder ein Aseq, der nur AseqEmpty enthaelt
     */
    protected boolean isAseqEmpty (Aseq a) {
	return (a == null) || isActionEmpty (a.head) && isAseqEmpty (a.tail);
    }


    //------------------------------------------------------------------------
    //  Export
    //------------------------------------------------------------------------


    /**
     *  Einsprung fuer weiteres.
     */ 
    public void start (BufferedWriter writer, Absyn ab) throws IOException {
	if (ab instanceof Action)
	    output (writer, (Action)ab);
	if (ab instanceof Aseq)
	    output (writer, (Aseq)ab);
	if (ab instanceof Bassign)
	    output (writer, (Bassign)ab);
	if (ab instanceof Boolstmt)
	    output (writer, (Boolstmt)ab);
	if (ab instanceof Bvar)
	    output (writer, (Bvar)ab);
	if (ab instanceof BvarList)
	    output (writer, (BvarList)ab);
	if (ab instanceof Compguard)
	    output (writer, (Compguard)ab);
	if (ab instanceof Comppath)
	    output (writer, (Comppath)ab);
	if (ab instanceof Connector)
	    output (writer, (Connector)ab);
	if (ab instanceof ConnectorList)
	    output (writer, (ConnectorList)ab);
	if (ab instanceof SEventList)
	    output (writer, (SEventList)ab);	
	if (ab instanceof Guard)
	    output (writer, (Guard)ab);
	if (ab instanceof Path)
	    output (writer, (Path)ab);
	if (ab instanceof SEvent)
	    output (writer, (SEvent)ab);
	if (ab instanceof State)
	    output (writer, (State)ab);
	if (ab instanceof Statechart) {

	    // Bei jedem Statechart den Fehlerstatus zuruecksetzten
	    errorText = new Vector();
	    errorCount = 0 ;

	    output (writer, (Statechart)ab);
	}
	if (ab instanceof StatenameList)
	    output (writer, (StatenameList)ab);
	if (ab instanceof TLabel)
	    output (writer, (TLabel)ab);
	if (ab instanceof Tr)
	    output (writer, (Tr)ab);
	if (ab instanceof TrAnchor)
	    output (writer, (TrAnchor)ab);
    }


    /**
     *  Darstellung für Statechart
     */
    protected void output (BufferedWriter writer, Statechart sChart) throws IOException {
	if (sChart != null) {
	    writer.write ("// Program automatically created by TESC-Export.\n//\n");

	    // Gib BvarList aus.
	    start (writer, sChart.bvars);

	    // Gib SEventList aus.
	    start (writer, sChart.events);
	    writer.write("\n");

	    // Starte bei Root-State.
	    start (writer, sChart.state);
	    writer.flush();
	}
    }

 
    /**
     *  Ausgabe SEventlist
     */
    protected void output (BufferedWriter writer, SEventList eListIterator) throws IOException {
	if (eListIterator != null) {
	    writer.write (whiteSpace (column) + Token.KeyEvent.getValue());
	    writer.write (" " + Token.LPar.getValue());
	    start (writer, eListIterator.head);
	    eListIterator = eListIterator.tail;
	    while (eListIterator != null) {
		writer.write (",");
		start (writer, eListIterator.head);
		eListIterator = eListIterator.tail;
	    }
	    writer.write (Token.RPar.getValue());
	}
    }


    /**
     *  Ausgabe BvarList
     */
    protected void output (BufferedWriter writer, BvarList bListIterator) throws IOException {
	if (bListIterator != null) {
	    writer.write (whiteSpace (column) + Token.KeyVar.getValue());
	    writer.write (" " + Token.LPar.getValue());
	    start (writer, bListIterator.head);
	    bListIterator = bListIterator.tail;
	    while (bListIterator != null) {
		writer.write (",");
		start (writer, bListIterator.head);
		bListIterator = bListIterator.tail;
	    }
	    writer.write (Token.RPar.getValue());
	}
    }


    /**
     *  Ausgabe  States.
     */
    protected void output (BufferedWriter writer, State state) throws IOException {
	if (state instanceof Ref_State)
	    output (writer, (Ref_State)state);
	else if (state instanceof Basic_State)
	    output (writer, (Basic_State)state);
	else if (state instanceof And_State)
	    output (writer, (And_State)state);
	else if (state instanceof Or_State)
	    output (writer, (Or_State)state);

    }


    /**
     *  Ausgabe  Basic-States.
     */
    protected void output (BufferedWriter writer, Basic_State bState) throws IOException {
	if (bState != null) {
	    writer.write (whiteSpace (column) + Token.KeyBasic.getValue() + " ");
            start( writer, bState.name);
	}
    }

    /**
     *  Ausgabe  Ref-States.
     */
    protected void output (BufferedWriter writer, Ref_State rState) throws IOException {
	if (rState != null) {
	    writer.write (whiteSpace (column) + Token.KeyRef.getValue() + " ");
            start( writer, rState.name);
	    writer.write (" " + Token.KeyIn.getValue());
	    writer.write (" \"" + rState.filename + "\"");
	    writer.write (" " + Token.KeyAs.getValue());

	    String type;
	    if (rState.filetype instanceof Tesc_Syntax)
		type = Token.KeyTesc.getValue();
	    else if (rState.filetype instanceof Pest_CoordSyntax)
		type = Token.KeyPest.getValue();
	    else if (rState.filetype instanceof Pest_NocoordSyntax)
		type = Token.KeyPestNoCoord.getValue();
	    else {
		type = "<unknown>";
		Error("Dateityp in " + rState.name + " unbekannt.");
	    } 
	    writer.write(" " + type);
	}
    }

    /**
     *  Ausgabe  And-States.
     */
    protected void output (BufferedWriter writer, And_State aState) throws IOException {
	if (aState != null) {
	    writer.write (whiteSpace (column) + Token.KeyAnd.getValue() + " ");
            start( writer, aState.name);

	    TESCWriter twAndSt = new TESCWriter (column + tab, tab);
	    twAndSt.start (writer, aState.substates);

	    writer.write (whiteSpace (column) + Token.KeyEnd.getValue() + " ");
	    start( writer, aState.name);
	}	
    }


    /**
     *  Ausgabe  Or-States.
     */
    protected void output (BufferedWriter writer, Or_State oState) throws IOException {
	if (oState != null) {
	    writer.write (whiteSpace (column) + Token.KeyOr.getValue() + " ");
            start( writer, oState.name);
	    
	    TESCWriter twOrSt = new TESCWriter (column + tab, tab);
	    
	    twOrSt.start (writer, oState.substates);
	    twOrSt.start (writer, oState.connectors);
	    twOrSt.start (writer, oState.trs);
	    twOrSt.start (writer, oState.defaults);

	    writer.write (whiteSpace (column) + Token.KeyEnd.getValue() + " ");
	    start( writer, oState.name);
	}
    }


    /**
     *  Ausgabe  Statenames.
     */
    protected void output (BufferedWriter writer, Statename sn) throws IOException {
	if (sn != null) {
	    writer.write (sn.name);
	}
    }


    /**
     * Ausgabe  StateList
     */
    protected void start (BufferedWriter writer, StateList sListIterator) throws IOException {
	while (sListIterator != null) {
	    start (writer, sListIterator.head);
	    sListIterator = sListIterator.tail;
	}
    }


    /**
     * Ausgabe  TrList.
     */
    protected void start (BufferedWriter writer, TrList tListIterator) throws IOException {
	while (tListIterator != null) {
	    start (writer, tListIterator.head);
	    tListIterator = tListIterator.tail;
	}
    }


    /**
     * Ausgabe  Tr.
     */
    protected void output (BufferedWriter writer, Tr t) throws IOException {
	if (t != null) {
	    writer.write (whiteSpace (column) + Token.KeyFrom.getValue() + " ");
	    start (writer, t.source);
	    writer.write (" " + Token.KeyTo.getValue() + " ");
	    start (writer, t.target);
	    start (writer, t.label);
	}
    }


    /**
     * Ausgabe  TrAnchors.
     */
    protected void output (BufferedWriter writer, TrAnchor ta) throws IOException {
	if (ta instanceof Statename)
	    output (writer, (Statename)ta);
	if (ta instanceof Conname)
	    output (writer, (Conname)ta);
	if (ta instanceof UNDEFINED)
	    output (writer, (UNDEFINED)ta);
    }


    /**
     *  Ausgabe  Connames
     */
    protected void output (BufferedWriter writer, Conname cn) throws IOException {
	if (cn != null) {
	    writer.write (cn.name);
	}
    }


    /**
     *  Ausgabe  UNDEFINED
     */
    protected void output (BufferedWriter writer, UNDEFINED un) throws IOException {
	if (un != null) {
	    writer.write (Token.KeyUndef.getValue());
      }
    }


    /**
     *  Ausgabe  Label.
     */
    protected void output (BufferedWriter writer, TLabel l) throws IOException {

	if (l != null) {

	    Guard  g = l.guard;
	    Action a = l.action;  

	    if (!isGuardEmpty(g)) {

		// Hat unser Guard den richtigen Aufbau ?
		if ((g instanceof GuardCompg) && ((GuardCompg)g).cguard.eop == Compguard.AND) {
		    
		    // Schluesselwort
		    writer.write (" " + Token.KeyOn.getValue() +  " ");

		    // Gibt es EventExpr ?
		    if (!isGuardEmpty(((GuardCompg)g).cguard.elhs)) {
			start (writer, ((GuardCompg)g).cguard.elhs);
		    }

		    // Gibt es BvarExpr?
		    if (!isGuardEmpty(((GuardCompg)g).cguard.erhs)) {
			writer.write (Token.LSPar.getValue());
			start (writer, ((GuardCompg)g).cguard.erhs); 
			writer.write (Token.RSPar.getValue());
		    }
		}
		else {
		    Error ("Guard besitzt nicht die Struktur EventExpr[BVarExpr]");
		}
	    }
	    if (!isActionEmpty(a)) {
		writer.write (" " + Token.KeyDo.getValue() +  " ");
		start (writer, a);
	    }
	}
    }


    /**
     *  Ausgabes  Guards.
     */
	protected void output (BufferedWriter writer, Guard g) throws IOException {
	    //debug("Enter output(Guard)");

	    if (g instanceof GuardBVar)
		output (writer, (GuardBVar)g);
	    if (g instanceof GuardCompg)
		output (writer, (GuardCompg)g);
	    if (g instanceof GuardCompp)
		output (writer, (GuardCompp)g);
	    if (g instanceof GuardEvent)
		output (writer, (GuardEvent)g);
	    if (g instanceof GuardNeg)
		output (writer, (GuardNeg)g);
	    if (g instanceof GuardUndet)
		output (writer, (GuardUndet)g);

	    //debug("Leave output(Guard)");
	}


    /**
     * Ausgabe  GuardBVar.
     */
    protected void output (BufferedWriter writer, GuardBVar gbv) throws IOException {
	if (gbv != null) {
	    start (writer, gbv.bvar);
	}
    }


    /**
     * Ausgabe  GuardCompg.
     */
    protected void output (BufferedWriter writer, GuardCompg gcg) throws IOException {
	if (gcg != null) {
	    start (writer, gcg.cguard);
	}
    }


    /**
     * Ausgabe  GuardCompp.
     */
    protected void output (BufferedWriter writer, GuardCompp gcp) throws IOException {
	//debug("Enter output(GuardCompp)");

	if (gcp != null) {
	    start (writer, gcp.cpath);
	}

	//debug("Leave output(GuardCompp)");
    }

 
    /**
     * Ausgabe  GuardEvent.
     */
    protected void output (BufferedWriter writer, GuardEvent gev) throws IOException {
	if (gev != null) {
	    start (writer, gev.event);
	}
    }


    /**
     * Ausgabe  GuardNeg.
     */
    protected void output (BufferedWriter writer, GuardNeg gn) throws IOException {
	if (gn != null) {
            writer.write (Token.NotOp.getValue());
	    start (writer, gn.guard);
	}
    } 

 
    /**
     * Ausgabe  GuardUndet.
     */
    protected void output (BufferedWriter writer, GuardUndet gu) throws IOException {
	if (gu != null) {
	    writer.write (whiteSpace (column) + "// Achtung : [GuardUndet] \n");
	    writer.write (whiteSpace (column) + gu.undet);
	    Error("Undet-Guard kann textuell nicht dargestellt werden.");
	}
    }


    /**
     * Ausgabe  Compguard.
     */
    protected void output (BufferedWriter writer, Compguard cg) throws IOException {
	if (cg != null) {

	    // Wandle Klassenkonstanten in Tokenstrings um:
	    String op;
	    switch (cg.eop) {
	    case Compguard.AND     : op = Token.AndOp.getValue(); break;
	    case Compguard.OR      : op = Token.OrOp.getValue(); break;
	    case Compguard.IMPLIES : op = Token.Implies.getValue(); break;
	    case Compguard.EQUIV   : op = Token.Equiv.getValue(); break;
	    default : 
		op = "<Illegal Op>";
		Error("Ungueltige Compguard-Operation");
	    } 
	    writer.write (Token.LPar.getValue());
	    start (writer, cg.elhs);
	    writer.write(" " + op + " ");
	    start (writer, cg.erhs);
	    writer.write (Token.RPar.getValue());
	}
    }

 
    /**
     * Ausgabe  Comppath.
     */
    protected void output (BufferedWriter writer, Comppath cp) throws IOException {
	//debug("Enter output(Comppath)");

	if (cp != null) {

	    // Wandle Klassenkonstanten in Tokenstrings um
	    String op;

	    switch (cp.pathop) {
	    case Comppath.IN      : op = Token.KeyIn.getValue(); break;
	    case Comppath.ENTERED : op = Token.KeyEntered.getValue(); break;
	    case Comppath.EXITED  : op = Token.KeyExited.getValue(); break;
	    default : 
		op = "<Illegal Op>";
		Error("Ungueltige Comppath-Operation");
	    }

	    writer.write (op);
	    writer.write (Token.LPar.getValue());
	    start (writer, cp.path);
	    writer.write (Token.RPar.getValue());	  
	}
	//debug("Leave output(Comppath)");
    }
	

    /**
     * Ausgabe  Bvar.
     */
    protected void output (BufferedWriter writer, Bvar bv) throws IOException {
	if (bv != null) {
	    writer.write (bv.var);
	}
    }


    /**
     * Ausgabe  Path
     */
    protected void output (BufferedWriter writer, Path pa) throws IOException {
	if (pa != null) { 
	    writer.write(pa.head);
	    if (pa.tail != null) {
		writer.write(Token.Dot.getValue());
		start(writer, pa.tail);
	    }
	}
    }


    /**
     * Ausgabe  SEvent.
     */
    protected void output (BufferedWriter writer, SEvent ev) throws IOException {
	if (ev != null) {
	    writer.write (ev.name);
	}
    }


    /**
     * Ausgabe  Action.
     */
    protected void output (BufferedWriter writer, Action a) throws IOException {
	if (a instanceof ActionBlock)
	    output (writer, (ActionBlock)a);
	if (a instanceof ActionEvt)
	    output (writer, (ActionEvt)a);
	if (a instanceof ActionStmt)
	    output (writer, (ActionStmt)a);
    }

    /**
     * Ausgabe  ActionBlocks.
     */
    protected void output (BufferedWriter writer, ActionBlock ab) throws IOException {
	if (ab != null) {
	    writer.write (Token.LPar.getValue());
	    start (writer, ab.aseq);
	    writer.write (Token.RPar.getValue());
	}
    }


    /**
     * Ausgabe  ActionEvt.
     */
    protected void output (BufferedWriter writer, ActionEvt aev) throws IOException {
	if (aev != null) {
	    start (writer, aev.event);
	}
    }


    /**
     * Ausgabe  ActionStmt.
     */
    protected void output (BufferedWriter writer, ActionStmt ast) throws IOException {
	if (ast != null) {
	    start (writer, ast.stmt);
	}
    }


    /**
     * Ausgabe  Aseq.
     */
    protected void output (BufferedWriter writer, Aseq aseqIterator) throws IOException {
	if (aseqIterator != null) {
	    start (writer, aseqIterator.head);
	    aseqIterator = aseqIterator.tail;
	    while (aseqIterator != null) {
		writer.write (",");
		start (writer, aseqIterator.head);
		aseqIterator = aseqIterator.tail;
	    }
	}
    }


    /**
     * Ausgabe  Boolstmt.
     */
    protected void output (BufferedWriter writer, Boolstmt b) throws IOException {
	if (b instanceof BAss)
	    output (writer, (BAss)b);
	if (b instanceof MFalse)
	    output (writer, (MFalse)b);
	if (b instanceof MTrue)
	    output (writer, (MTrue)b);
    }


  /**
   * Ausgabe  BAss.
   */
    protected void output (BufferedWriter writer, BAss bass) throws IOException {
	if (bass != null) {
	    start (writer, bass.ass);
	}
    }


    /**
     * Ausgabe  MFalse.
     */
    protected void output (BufferedWriter writer, MFalse mf) throws IOException {
	if (mf != null) {
	    start (writer, mf.var);
	    writer.write(Token.Assign.getValue()+Token.KeyFalse.getValue());
	}
    }


   /**
     * Ausgabe  MTrue.
     */
    protected void output (BufferedWriter writer, MTrue mt) throws IOException {
	if (mt != null) {
	    start (writer, mt.var);
	    writer.write(Token.Assign.getValue()+Token.KeyTrue.getValue());
	}
    }


    /**
     * Ausgabe  Bassign.
     */
    protected void output (BufferedWriter writer, Bassign ba) throws IOException {
	if (ba != null) {
	    start (writer, ba.blhs);
	    writer.write(Token.Assign.getValue());
	    start (writer, ba.brhs);
	}
    }


    /**
     * Ausgabe  ConnectorList.
     */
    protected void output (BufferedWriter writer, ConnectorList cListIterator) throws IOException {
	while (cListIterator != null) {
	    writer.write (whiteSpace (column) + Token.KeyCon.getValue() + " ");	
	    start (writer, cListIterator.head);
	    cListIterator = cListIterator.tail;
	}
    }


    /**
     * Ausgabe  Connector.
     */
    protected void output (BufferedWriter writer, Connector co) throws IOException {
	if (co != null) {
	    start (writer, co.name);
	}
    }


    /**
     * Ausgabe  Statenamelist - default-connector.
     */
    protected void output (BufferedWriter writer, StatenameList snListIterator) throws IOException {
	while (snListIterator != null) {
	    writer.write(whiteSpace (column) + Token.KeyDefault.getValue() + " ");
	    start (writer, snListIterator.head);
	    snListIterator = snListIterator.tail;
	} 
    }
}

      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1999/02/01 11:53:00  swtech20
//	- globaler Debug-Schalter
//
//	Revision 1.2  1999/01/20 17:32:12  swtech20
//	- Status und Doku aktualisiert
//	- Fehler, dass Anderungen an Bvarlisten ... nicht nach aussen-
//	  gegeben werden behoben.
//
//	Revision 1.1  1999/01/17 17:19:25  swtech20
//	Implementierung der Export-Funktionen. Neue Funktion zum Erzeugen
//	einer textuellen Darstellung eines TLabels mit Syntax Guard / Action.
//
//
//
//
//----------------------------------------------------------------------
