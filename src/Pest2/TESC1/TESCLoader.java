package tesc1;

import java.io.*;
import absyn.*;
import gui.*;

/**
 * Import-Schnittstelle fuer TESC.        
 *
 * <p><STRONG>Garantie: </STRONG> <p>
 * Wir garantieren, dass die von unseren Module erzeugten Statecharts folgende 
 * Eigenschaften haben:
 * <ul>
 * <li> Ein Absyn-Baum eines Statecharts wird zurueckgeliefert, d.h.
 *    <ul>
 *    <li> der Baum ist nicht notwendigerweise als Statechart darstellbar
 *    <li> das Statechart ist nicht notwendigerweise semantisch korrekt
 *    <ul>
 *    <li> Ungueltige State-Verweise
 *    <li> Ungueltige Pfade
 *    <li> Nicht referenzierte Variablen, Events und Zustaende
 *    <li> Leere Zustaende
 *    </ul> 
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
 * <li>
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
 * <li> Schnittstelle mit EDITOR/STM absprechen.
 * <li> Grammatik aktualisieren
 * <li> Struktur GUARD/ACTION mit STM abstimmen.
 * <li> Doku TESC
 * <li> Weiteres Bug Fixing.
 * </ul>
 *
 * <p><STRONG>Bekannte Fehler.</STRONG>
 * <ul>
 * <li>
 * </ul>
 *
 * <p><STRONG>Testmoeglichkeiten: </STRONG> <p>
 * Wer unseren Parser testen moechte, kann sich gemaess der <A HREF="./tesc1/Docu/Grammatik">TESC-Grammatik</A> ein
 * TESC-Programm schreiben und mittels DATEI|IMPORT|TESC in PEST laden.<p> 
 * Alternativ kann das <A HREF="./tesc1/Docu/Example.tesc">Beispiel</A> aus
 * dem Pflichtenheft verwendet werden.
 * <p>
 * <hr>
 * @version  $Id: TESCLoader.java,v 1.12 1999-01-18 17:08:52 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 *  
 */   
public final class TESCLoader {

    private static String PACKAGE_NAME = "TESC1 : ";
    private GUIInterface gui = null;
    private TESCParser parser = null;

    /**
     * Erzeugt eine Instanz von <code>TESCLoader</code>. 
     * Fehler waehrend des Parsens werden ueber die GUI-Schnittstelle mitgeteilt.
     */
    public TESCLoader (GUIInterface gui_) {           
	gui = gui_; 
    } 
   
    /**
     * Startet den Parse-Vorgang und liefert bei Erfolg ein Statechart.
     * Falls im Ktor gui-Objekt uebergeben, erfolgt Fehlerausgabe dort.
     * @return 
     * <ul>
     * <li> Statechart-Instanz bei erfolgreichem Einlesen und Parsen.
     * <li> <code>null</code> bei Auftreten eines Fehlers.
     * </ul>
     * @param br BufferedReader
     * @see tesc1.TESCParser
     */
    public Statechart getStatechart (BufferedReader br) throws IOException {

	parser = new TESCParser();
	Statechart statechart = parser.readStatechart(br);
	
	// Warnungen ausgeben
	if (gui != null) {
	    for (int i=0; i < parser.getWarningCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getWarningText(i));
	    }
	}
        else {
            for (int i=0; i < parser.getWarningCount(); i++) {
                System.out.println(PACKAGE_NAME + parser.getWarningText(i));
            }
        }

	// Fehler ausgeben
	if (parser.getErrorCount() > 0) {
	    if (gui != null) {
	        for (int i=0; i < parser.getErrorCount(); i++) {
		    gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
	        }
	        gui.userMessage(PACKAGE_NAME + "Statechart ist fehlerhaft.");
	    }
            else {
                for (int i=0; i < parser.getErrorCount(); i++) {
                    System.out.println(PACKAGE_NAME + parser.getErrorText(i));
                }
            }
            return null;
        }
	else 
	    return statechart;
    }

    /**
     * Startet den Parse-Vorgang und liefert bei Erfolg ein Label.
     * Falls im Ktor gui-Objekt uebergeben, erfolgt Fehlerausgabe dort.
     * @return 
     * <ul>
     * <li> Label-Instanz mit gesetztem caption-Feld.
     * <li> <code>null</code> bei Auftreten eines Fehlers.
     * </ul>
     * @param br BufferedReader
     * @param sc Statechart, in dem die Bvar- und Eventlisten gepflegt werden sollen
     * @see tesc1.TESCParser
     */
    public TLabel getLabel (BufferedReader br,Statechart sc) throws IOException {
	
	parser = new TESCLabelParser(sc);
	TLabel label = ((TESCLabelParser)parser).readLabel(br);

	// Warnungen ausgeben
	if (gui != null) {
	    for (int i=0; i < parser.getWarningCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getWarningText(i));
	    }
	}
	else {
	    for (int i=0; i < parser.getWarningCount(); i++) {
		System.out.println(PACKAGE_NAME + parser.getWarningText(i));
	    }
	}

	// Fehler ausgeben
	if (parser.getErrorCount() > 0) {
	    if (gui != null) {
		for (int i=0; i < parser.getErrorCount(); i++) {
		    gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
		}
		gui.userMessage(PACKAGE_NAME + "Statechart ist fehlerhaft.");
	    }
	    else {
		for (int i=0; i < parser.getErrorCount(); i++) {
		    System.out.println(PACKAGE_NAME + parser.getErrorText(i));
		}
	    }
	    return null;
        }
	else 
	    return label;
    }

    /**
     * Startet den Parse-Vorgang und liefert bei Erfolg einen Guard.
     * @return 
     * <ul>
     * <li> Guard-Instanz bei erfolgreichem Einlesen und Parsen.
     * <li> <code>null</code> bei Auftreten eines Fehlers.
     * </ul>
     * @param br BufferedReader
     * @param sc Statechart, in dem die Bvar- und Eventlisten gepflegt werden sollen
     * @see tesc1.TESCParser
     */
    private Guard getGuard(BufferedReader br,Statechart sc) throws IOException {
	TESCParser parser = new TESCParser();
	Guard guard = parser.readGuard(br);

	if (parser.getErrorCount() > 0) {
	    for (int i=0; i < parser.getErrorCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
	    }
	    gui.userMessage(PACKAGE_NAME + "Guard ist fehlerhaft.");
	    return null;
        }
	else 
	    return guard;
    }

    /**
     * Startet den Parse-Vorgang und liefert bei Erfolg eine Action.
     * @return 
     * <ul>
     * <li> Action-Instanz bei erfolgreichem Einlesen und Parsen.
     * <li> <code>null</code> bei Auftreten eines Fehlers.
     * </ul>
     * @param br BufferedReader
     * @param sc Statechart, in dem die Bvar- und Eventlisten gepflegt werden sollen
     * @see tesc1.TESCParser
     */
    private Action getAction(BufferedReader br,Statechart sc) throws IOException {
	TESCParser parser = new TESCParser();
	Action action = parser.readAction(br);

	if (parser.getErrorCount() > 0) {
	    for (int i=0; i < parser.getErrorCount(); i++) {
		gui.userMessage(PACKAGE_NAME + parser.getErrorText(i));
	    }
	    gui.userMessage(PACKAGE_NAME + "Action ist fehlerhaft.");
	    return null;
        }
	else 
	    return action;
    }

    /**
     * Liefert die Anzahl der Fehler, die beim letzten Parsen
     * aufgetreten sind.
     * @return Anzahl der Fehler.
     */
    private int getErrorCount() {return parser.errorCount;}
    
    
    /**
     * Liefert den Fehlertext zum n-ten Fehler, der beim letzen
     * Parsen aufgetreten ist.
     * @param n Fehlerindex
     * @return Fehlertext des n-ten Fehlers.
     */
    private String getErrorText(int n) {return (String)parser.errorText.elementAt(n);}
    

    /**
     * Liefert die Anzahl der Warnungen, die beim letzten Parsen
     * aufgetreten sind.
     * @return Anzahl der Warnungen.
     */
    private int getWarningCount() {return parser.warningCount;}
    
    
    /**
     * Liefert den Warnungstext zur n-ten Warnung, der beim letzen
     * Parsen aufgetreten ist.
     * @param n imdex
     * @return Text.
     */
    private String getWarningText(int n) {return (String)parser.warningText.elementAt(n);}
   
}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.11  1999/01/17 17:16:40  swtech20
//	Umstellung der Guard-Syntax auf Statemate-Style, Implementierung des
//	LabelParsers fuer den Editor. Anpassung der Schnittstelle.
//
//	Revision 1.10  1999/01/11 12:13:54  swtech20
//	Bugfixes.
//
//	Revision 1.9  1999/01/04 15:25:19  swtech20
//	Schluesselworte in Bezeichnern werden nicht mehr zugelassen.
//	Status upgedated.
//
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

