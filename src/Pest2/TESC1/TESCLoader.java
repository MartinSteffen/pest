package tesc1;

import java.io.*;
import absyn.*;
import gui.*;

/**
 * Schnittstellenklasse fuer TESC1.        
 *
 * <p><STRONG>Garantie: </STRONG> <p>
 * Wir garantieren, dass die von unseren Module erzeugten Statecharts folgende 
 * Eigenschaften haben:
 * <ul>
 * <li> Ein Absyn-Baum eines Statecharts wird zurueckgeliefert, d.h.
 *    <ul>
 *    <li> der Baum ist nicht notwendigerweise als Statechart darstellbar
 *    <li> das Statechart ist nicht notwendigerweise semantisch korrekt 
 *    </ul>
 * <li> Leere Events und Guards werden mit Dummy gekennzeichnet statt NULL
 * <li> Keywords aus util.Keyword.java kommen nicht in Bezeichnern vor
 * <li> Alle Listen sind konsistent       
 * </ul>
 * 
 * <p><STRONG>Anforderungen: </STRONG> <p>
 * Wir verlassen uns darauf, dass die Statecharts, die uns uebergeben werden, folgende 
 * Eigenschaften haben:
 * <ul>
 * <li> Derzeit haben wir keine Anforderungen! (Kommt in Stufe 2)
 * </ul>
 * <br>
 *
 * <p><STRONG> Status: </STRONG> <p>
 * Momentan erkennt unser Parser die Sprache TESC, definiert in <A HREF="./tesc1/Docu/Grammatik">tesc1/Docu/Grammatik</A>
 * Mit dieser Sprachdefinition kann man Statecharts gemaess der Abstrakten Syntax 
 * textuell modellieren.
 *
 * <p><STRONG>Todo: </STRONG>
 * <ul>
 * <li> In die Absyn-Objekte muessen noch die Zeilennummern eingetragen werden.
 * <li> Bug Fixing.
 * </ul>
 *
 * <p><STRONG>Bekannte Fehler.</STRONG>
 * <ul>
 * <li> GUI erkennt fehlerhaftes Einlesen nicht. (Behoben bis 5.1.99)
 * </ul>
 *
 * <p><STRONG>Testmoeglichkeiten: </STRONG> <p>
 * Wer unseren Parser testen moechte, kann sich gemaess der <A HREF="./tesc1/Docu/Grammatik">TESC-Grammatik</A> ein
 * TESC-Programm schreiben und mittels DATEI|IMPORT|TESC in PEST laden.<p> 
 * Alternativ kann das <A HREF="./tesc1/Docu/example.tesc">Beispiel</A> aus
 * dem Pflichtenheft verwendet werden.
 * <p>
 * <hr>
 * @version  $Id: TESCLoader.java,v 1.9 1999-01-04 15:25:19 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 *  
 */   
public class TESCLoader {

    private static String PACKAGE_NAME = "TESC1: ";
    private GUIInterface gui = null;
    
    /**
     * Erzeugt eine Instanz von <code>TESCLoader<code>. 
     * Fehler waehrend des Parsens werden ueber die GUI-Schnitstelle mitgeteilt.
     */
    public TESCLoader (GUIInterface gui_) {           
	gui = gui_; 
    } 
   
    /**
     * Startet den Parse-Vorgang und liefert bei Erfolg ein Statechart.
     * @return 
     * <ul>
     * <li> Statechart-Instanz bei erfolgreichem Einlesen und Parsen.
     * <li> <code>null<code> bei Auftreten eines Fehlers.
     * </ul>
     * @param br BufferedReader
     * @see tesc1.TESCParser

     */
    public Statechart getStatechart (BufferedReader br) throws IOException {
	TESCParser parser = new TESCParser();
	Statechart statechart = parser.readStatechart(br);

	if (parser.getErrorCount() > 0) {
	    for (int i=0; i < parser.getErrorCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
	    }
	    gui.OkDialog("Fehler", "Statechart ist fehlerhaft.");
	    return null;
        }
	else 
	    return statechart;
    }

    public Guard getGuard(BufferedReader br) throws IOException {
	TESCParser parser = new TESCParser();
	Guard guard = parser.readGuard(br);

	if (parser.getErrorCount() > 0) {
	    for (int i=0; i < parser.getErrorCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
	    }
	    gui.OkDialog("Fehler", "Guard ist fehlerhaft.");
	    return null;
        }
	else 
	    return guard;
    }

    public Action getAction(BufferedReader br) throws IOException {
	TESCParser parser = new TESCParser();
	Action action = parser.readAction(br);

	if (parser.getErrorCount() > 0) {
	    for (int i=0; i < parser.getErrorCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
	    }
	    gui.OkDialog("Fehler", "Action ist fehlerhaft.");
	    return null;
        }
	else 
	    return action;
    }
}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.8  1999/01/03 21:48:18  swtech20
//	Implementierung des Parsers
//
//	Revision 1.7  1998/12/21 12:34:22  swtech20
//	Voraussetzungen angegeben.
//
//	Revision 1.6  1998/12/17 11:14:10  swtech20
//	BufferedReader statt FileInputStream.
//
//	Revision 1.5  1998/12/15 18:11:37  swtech00
//	Towards new naming conventions for PEST2
//
//	Revision 1.4  1998/12/13 17:49:05  swtech20
//	Checkin f’r Baseline
//
//	Revision 1.3  1998/12/07 19:55:16  swtech20
//	Wir geben Fehlermeldungen jetzt direkt an die GUI.
//
//	Revision 1.2  1998/12/05 18:47:27  swtech20
//	Rueckgabe des Beispiel-StateCharts.
//
//
//----------------------------------------------------------------------

