package tesc1;

import java.io.*;
import absyn.*;
import gui.*;

/**
 * Export-Schnittstelle fuer TESC1.        
 *
 * <p><STRONG>Garantie: </STRONG> <p>
 * Wir garantieren, dass die von unseren Module erzeugten Statecharts folgende 
 * Eigenschaften haben:
 * <ul>
 * <li> Noch keine Garantien.
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
 * Fangen erst an.
 *
 * <p><STRONG>Todo: </STRONG>
 * <ul>
 * <li> Loslegen.
 * </ul>
 *
 * <p><STRONG>Bekannte Fehler.</STRONG>
 * <ul>
 * <li>
 * </ul>
 *
 * <p><STRONG>Testmoeglichkeiten: </STRONG> <p>
 * <p>
 * <hr>
 * @version  $Id: TESCSaver.java,v 1.1 1999-01-11 12:13:56 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 *  
 */   
public class TESCSaver {

    private static String PACKAGE_NAME = "TESC1: ";
    private GUIInterface gui = null;
    
    /**
     * Erzeugt eine Instanz von <code>TESCSaver<code>. 
     * Fehler werden ueber die GUI-Schnittstelle mitgeteilt.
     */
    public TESCSaver (GUIInterface gui_) {           
	gui = gui_; 
    } 
   
    /**
     * Startet den Export.
     * @return 
     * <ul>
     * <li> <code>true</code>  : Export erfolgreich
     * <li> <code>false</code> : Export fehlgeschlagen
     * </ul>
     * @param bw BufferedWriter
     * @param sc Statechart
     */
    public boolean saveStatechart (BufferedWriter bw, Statechart sc) throws IOException {

	/*
	if (parser.getErrorCount() > 0) {
	    for (int i=0; i < 1parser.getErrorCount(); i++) {
		gui.userMessage(PACKAGE_NAME + "parser.getErrorText(i)");
	    }
	    gui.OkDialog("Fehler", "Statechart ist fehlerhaft.");
	    return false;
        }
	else 
	    return true;
        */
       return true; 
   }
}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//
//
//----------------------------------------------------------------------

