package tesc1;

import java.io.*;
import absyn.*;
import gui.*;

/**
 * Export-Schnittstelle fuer TESC.  
 * <p>
 * Es wird die abstrakte Syntax auf ein TESC-Programm abgebildet.    
 *
 * <p><STRONG>Garantie: </STRONG> <p>
 * Wir garantieren folgende Eigenschaft:
 * <ul>
 * <li> Das erzeugte TESC-Programm ist ueber die TESC-Import-Schnittstelle wieder einlesbar,
 *      sofern es den Anforderungen genuegt.
 * </ul>
 * 
 * <p><STRONG>Anforderungen: </STRONG> <p>
 * Wir verlassen uns darauf, dass die Statecharts, die uns uebergeben werden, folgende 
 * Eigenschaften haben:
 * <ul>
 * <li> Guards sind aufgebaut wie in Statemate, d.h EventExpr[BvarExpr], wobei die Klammern wie ein UND-Operator wirken 
 * <li> UNDET-Guards kommen nicht vor.
 * <li> Leere Guards werden intern mit Dummy[Dummy] gekennzeichnet statt NULL
 * <li> Leere Events werden intern mit Dummy gekennzeichnet statt NULL
 * <li> Keywords aus util.Keyword.java kommen nicht in Bezeichnern vor
 * <li> Action-Statements duerfen in rhs nur Bvars referenzieren
 * <li> Alle Listen sind konsistent       
 * </ul>
 * <br>
 *
 * <p><STRONG> Status: </STRONG> <p>
 * Ref_States werden jetzte erzeugt. 
 *
 * <p><STRONG>Todo: </STRONG>
 * <ul>
 * <li>
 * </ul>
 *
 * <p><STRONG>Bekannte Fehler: </STRONG>
 * <ul>
 * <li> 
 * </ul>
 *
 * <p><STRONG>Testmoeglichkeiten: </STRONG> <p>
 * Jedes Statechart, z.B. das <A HREF="./tesc1/Docu/Example.tesc">Beispiel</A> aus
 * dem Pflichtenheft, kann testweise exportiert werden. 
 * <hr>
 * @version  $Id: TESCSaver.java,v 1.8 1999-02-18 09:57:08 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 *  
 */   
public final class TESCSaver {

    static String PACKAGE_NAME = "TESC1 : ";
    static GUIInterface gui = null;
    private TESCWriter writer = null;

    /**
     * Erzeugt eine Instanz von <code>TESCSaver</code>. 
     * Fehler werden ueber die GUI-Schnittstelle mitgeteilt.
     */
    public TESCSaver (GUIInterface gui_) {           
	gui = gui_; 
    } 
   
    /**
     * Startet den Export.
     * Falls im Ktor gui-Objekt uebergeben, erfolgt Fehlerausgabe dort.
     * @return 
     * <ul>
     * <li> <code>true</code>  : Export erfolgreich
     * <li> <code>false</code> : Export fehlgeschlagen
     * </ul>
     * @param bw BufferedWriter
     * @param sc Statechart
     */
    public boolean saveStatechart (BufferedWriter bw, Statechart sc) throws IOException {

	writer = new TESCWriter();
	writer.start(bw,sc);

	// Warnungen ausgeben
	if (gui != null) {
	    for (int i=0; i < writer.getWarningCount(); i++) {
		gui.userMessage(PACKAGE_NAME + writer.getWarningText(i));
	    }
	}

	if (writer.getErrorCount() > 0) {
	    if (gui != null) {
		for (int i=0; i < writer.getErrorCount(); i++) {
		    gui.userMessage(PACKAGE_NAME + writer.getErrorText(i));
		}
		gui.userMessage(PACKAGE_NAME + "Export fehlerhaft abgebrochen.");
	    }
	    return false;
        }
	else 
	    return true;
   }

    /**
     * Caption fuer TLabel aufbauen und setzen. 
     * Falls im Ktor gui-Objekt uebergeben, erfolgt Fehlerausgabe dort.
     * @return 
     * <ul>
     * <li> <code>true</code>  : Setzen erfolgreich
     * <li> <code>false</code> : Setzen fehlgeschlagen
     * </ul>
     * @param l Label
     */
    public boolean setCaption(TLabel l) throws IOException {

	writer = new TESCCaptionWriter();

	StringWriter sw = new StringWriter ();
        BufferedWriter bw = new BufferedWriter(sw);
	writer.start (bw,l);
	l.caption = sw.toString();

	// Warnungen ausgeben
	if (gui != null) {
	    for (int i=0; i < writer.getWarningCount(); i++) {
		gui.userMessage(PACKAGE_NAME + writer.getWarningText(i));
	    }
	}

	// Fehler ausgeben
	if (writer.getErrorCount() > 0) {
	    if (gui != null) {
		for (int i=0; i < writer.getErrorCount(); i++) {
		    gui.userMessage(PACKAGE_NAME + writer.getErrorText(i));
		}
	    }
	    return false;
        }
	else 
	    return true;
    }


    /**
     * Caption fuer ein gesamtes Statechart aufbauen. 
     * Falls im Ktor gui-Objekt uebergeben, erfolgt Fehlerausgabe dort.
     * @return 
     * <ul>
     * <li> <code>true</code>  : Setzen erfolgreich
     * <li> <code>false</code> : Setzen fehlgeschlagen
     * </ul>
     * @param l Label
     */
    public boolean setCaption(Statechart sc) throws IOException {

	TESCCaptionRewriter rewriter = new TESCCaptionRewriter(sc);
	// Warnungen ausgeben
	if (gui != null) {
	    for (int i=0; i < rewriter.getWarningCount(); i++) {
		gui.userMessage(PACKAGE_NAME + rewriter.getWarningText(i));
	    }
	}

	// Fehler ausgeben
	if (rewriter.getErrorCount() > 0) {
	    if (gui != null) {
		for (int i=0; i < rewriter.getErrorCount(); i++) {
		    gui.userMessage(PACKAGE_NAME + rewriter.getErrorText(i));
		}
	    }
	    return false;
        }
	else 
	    return true;
    }

    /**
     * Liefert die Anzahl der Fehler, die beim letzten Parsen
     * aufgetreten sind.
     * @return Anzahl der Fehler.
     */
    int getErrorCount() {return writer.errorCount;}
    
    
    /**
     * Liefert den Fehlertext zum n-ten Fehler, der beim letzen
     * Parsen aufgetreten ist.
     * @param n Fehlerindex
     * @return Fehlertext des n-ten Fehlers.
     */
    String getErrorText(int n) {return (String)writer.errorText.elementAt(n);}
    

    /**
     * Liefert die Anzahl der Warnungen, die beim letzten Parsen
     * aufgetreten sind.
     * @return Anzahl der Warnungen.
     */
    int getWarningCount() {return writer.warningCount;}
    
    
    /**
     * Liefert den Warnungstext zur n-ten Warnung, der beim letzen
     * Parsen aufgetreten ist.
     * @param n imdex
     * @return Text.
     */
    String getWarningText(int n) {return (String)writer.warningText.elementAt(n);}
   
}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.7  1999/02/11 16:52:25  swtech20
//	Kosmetik.
//
//	Revision 1.6  1999/02/07 11:58:31  swtech20
//	- ref_state implementiert
//	- bugs #36,#37 gefixed
//
//	Revision 1.5  1999/02/01 11:52:59  swtech20
//	- globaler Debug-Schalter
//
//	Revision 1.4  1999/01/20 17:32:12  swtech20
//	- Status und Doku aktualisiert
//	- Fehler, dass Anderungen an Bvarlisten ... nicht nach aussen-
//	  gegeben werden behoben.
//
//	Revision 1.3  1999/01/18 17:08:53  swtech20
//	- okDialog -> userMessage
//	- Pruefung auf gui==null
//	- package visibility fuer Nicht-Schnittstellenklassen
//
//	Revision 1.2  1999/01/17 17:19:24  swtech20
//	Implementierung der Export-Funktionen. Neue Funktion zum Erzeugen
//	einer textuellen Darstellung eines TLabels mit Syntax Guard / Action.
//
//	Revision 1.1  1999/01/11 12:13:56  swtech20
//	Bugfixes.
//
//
//
//----------------------------------------------------------------------

