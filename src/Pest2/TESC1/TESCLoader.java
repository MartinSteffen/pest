package TESC1;

import java.io.*;
import Absyn.*;
import GUI.*;

/**
 * Schnittstellenklasse fuer TESC1. 
 * @version  $Id: TESCLoader.java,v 1.4 1998-12-13 17:49:05 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 */   
public class TESCLoader {

    private Statechart statechart = null;
    private TESCParser parser = null;
    private GUIInterface gui = null;
    
    /**
     * Erzeugt eine Instanz von <code>TESCLoader<code>. 
     * Fehler waehrend des Parsens werden ueber die GUI-Schnitstelle mitgeteilt.
     */
    public TESCLoader(GUIInterface gui_) {           
	gui = gui_; 
    } 
   
    /**
     * Startet den Parse-Vorgang und liefert bei Erfolg ein Statechart.
     * @return 
     * <ul>
     * <li> Statechart-Instanz bei erfolgreichem Einlesen und Parsen.
     * <li> <code>null<code> bei Auftreten eines Fehlers.
     * </ul>
     * @param fis FileInputStream
     * @see TESC1.TESCParser
     */
    public Statechart getStatechart(FileInputStream fis) throws IOException {
 
	parser = new TESCParser();
	statechart = parser.parseStream(fis);
	return  Example.getExample();
    }

    private int getErrorCount() {
	return parser.getErrorCount();
    }

    private String getErrorText(int error) {
	return parser.getErrorText(error);
    }
}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/12/07 19:55:16  swtech20
//	Wir geben Fehlermeldungen jetzt direkt an die GUI.
//
//	Revision 1.2  1998/12/05 18:47:27  swtech20
//	Rueckgabe des Beispiel-StateCharts.
//
//
//----------------------------------------------------------------------

