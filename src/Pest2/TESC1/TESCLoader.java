package tesc1;

import java.io.*;
import absyn.*;
import gui.*;

/**
 * Schnittstellenklasse fuer TESC1.        
 * @version  $Id: TESCLoader.java,v 1.6 1998-12-17 11:14:10 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 */   
public class TESCLoader {

    private static String PACKAGE_NAME = "tesc1: ";
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
	//Statechart statechart = parser.readStatechart(br);

	// Nur zu Testzwecken
	Statechart statechart = Example.getExample();
	return statechart;
        /*
	if (parser.getErrorCount() > 0) {
	    for (int i=0; i < parser.getErrorCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
	    }
	    gui.OkDialog("Fehler", "Statechart ist fehlerhaft.");
	    return null;
        }
	else 
	    return statechart;
       */
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
//	Revision 1.5  1998/12/15 18:11:37  swtech00
//	Towards new naming conventions for PEST2
//
//	Revision 1.4  1998/12/13 17:49:05  swtech20
//	Checkin für Baseline
//
//	Revision 1.3  1998/12/07 19:55:16  swtech20
//	Wir geben Fehlermeldungen jetzt direkt an die GUI.
//
//	Revision 1.2  1998/12/05 18:47:27  swtech20
//	Rueckgabe des Beispiel-StateCharts.
//
//
//----------------------------------------------------------------------

