package tesc1;

import java.util.*;
import absyn.*;
import java.io.*;
import gui.*;
import java.lang.*;
import util.*;

/** 
 *  
 * <STRONG> Garantie. </STRONG> <br> Wir garantieren, dass die von unseren
 * Modulen erzeugten Statecharts folgende Eingenschaften haben:
 * 
 * <ul>
 * <li> Es werden keine Statecharts erzeugt.
 * </ul>
 * 
 * <STRONG> Anforderungen. </STRONG> <br>
 * Wir verlassen uns darauf, dass die
 * Statecharts, die uns uebergeben werden, folgende Eigenschaften haben: 
 * 
 * <ul>
 * <li> 1. SyntaxCheck darf keine Fehler melden.
 * <li> 2. TrAnchors duerfen nicht UNDEFINED() sein.
 * <li> 3. Kein GuardUndet (s. Status)
 * <li> 4. Keine Schleifen bei Listen.
 * <li> 5. Keine unartigen Nullpointer.
 * <li> 6. TLabel müssen der tesc1-SyntaxTESCSaver.java genügen. (Momentan geht der Export zwar immer, aber evtl. kein Reimport mögl.)
 * </ul>
 * 
 * die mit folgenden Checks ueberprueft werden koennen:
 * 
 * <ul>
 * <li> 2. TestTransitions
 * <li> 3. ?
 * <li> 4.-5. sollte von den Modulen, die Statecharts erzeugen, sowieso garantiert werden.
 * </ul>

 * <DL COMPACT>
 * 
 * <DT><STRONG>
 * STATUS
 * </STRONG>
 * <br> 
 * Der Export funktioniert. Bei Transitionlabels wird defaultmäßig der SyntaxBaum geparst.
 * Alternativ kann auch einfach TLabel.caption gespeichert werden (hierbei bleibt die Formatierung
 * erhalten, allerdings können bei falscher Syntax exportierte Files nicht reimportiert werden).
 * Falls beim Parsen der Absyn ein GuardUndet auftritt, wird ein GuardEmpty exportiert, und der echte 
 * Guard als Kommentar in die nächste Zeile geschrieben, so daß der User versuchen kann, noch etwas zu retten.
 * Die GuardUndets tauchen z.B. beim stm-import auf, wenn ein stm-file importiert wurde, das Teil einer größeren
 * Statechart ist, die in mehrere Files aufgeteilt wurde, und in Pfad-Operationen auf States in einem anderen File zugrifffen wird.
 * <br>
 * <DT><STRONG>
 * TODO.
 * </STRONG>
 * <br>
 * <ul>
 * <li> Testen.
 * <li> bessere GuardUndet-Behandlung (?, sehen momentan keine Möglichkeit)
 * </ul>
 * 
 * <br>
 * <DT><STRONG>
 * TEMP.
 * </STRONG>
 * <ul>
 * <li> debug-ausgaben ins GUI Fenster.
 * </ul>
 *
 * </DL COMPACT>
 * <br>
 * <hr>
 * @author Arne Koch/Mike Rumpf.
 * @version  $Id: TESCSaver.java,v 1.9 1999-02-05 19:21:22 swtech13 Exp $ 
 */ 

/* Konventionen:
 * Kommentare enthalten:
 *
 * Temporaere Features: temporar
 * Debug      ---"--- : debug
 * Evtl. Fehler       : ???
 *
 */

/* Todo:
 *
 * - add* Funktionen in eigene Klasse auslagern, so daß TESCSaver reine Schnitstelle
 */

public class TESCSaver {


    private Statechart stchart;
    private int errorCount = 0;   // Anzahl der aufgetretenen Fehler
    private Vector errorList;     // Fehlerliste
    private GUIInterface gi;
   
    private BufferedWriter bw;  

    private static final int TAB = 5;
    private int tiefe;

    private Vector switches;

    /** 
     * Export eines Syntaxbaums in ein Tesc-File. 
     * @return 
     * <ul> 
     * <li> <code>true</code>  : Export erfolgreich 
     * <li> <code>false</code> : Export fehlgeschlagen 
     * </ul> 
     * @param bw BufferedWriter 
     * @param sc Statechart 
     */ 
    public boolean saveStatechart (BufferedWriter bw_, Statechart sc_) throws IOException { 
	bw = bw_;
	stchart = sc_;
	tiefe = 0;

	if (stchart == null)
	    gi.userMessage("Statechart leer");

	errorList = new Vector();

	return save();
    } 

    /** 
     * Schnittstelle zum Export eines Syntaxbaums in ein Tesc-File. 
     * 
     * @param gi_ Referenz auf GUIInterface, wenn null wird stdout benutzt
     */ 	
    public TESCSaver(GUIInterface gi_) {
	gi = gi_;
	
	tiefe = 0;
	
	switches = new Vector();
	initSwitches();

	
	
    }

    /**
     * setzt TLabel.caption in TESC1-Syntax
     * @param TLabel, dessen caption gesetzt werden soll.
     * @return 
     * <ul> 
     * <li> <code>true</code>  : erfolgreich 
     * <li> <code>false</code> : fehlgeschlagen 
     * </ul>
     */
    public boolean setCaption(TLabel tl) {
	boolean b = true, c, d;
	String g = null;
	String a = null;

	TESCLabelGen tlg = new TESCLabelGen(tl.guard, tl.action, gi);

	// Keinen Zeilenumbruch bei Fehlern
	tlg.setOption("in_setCaption");

	if (tl != null) {

	    g = tlg.getGuard();
	    c = !tlg.error();
	    a = tlg.getAction();
	    d = !tlg.error();
	    
	    b = (c && d);
	   
	    // Auch bei Fehler setzten ?
	    // Entält nur etwas, wenn guard/action != ""
	    if (a.length() > 0)
		tl.caption = new String(g + " / " + a);
	    else if (g.length() > 0)
		tl.caption = new String(g);
	    else
		tl.caption = new String("");

	}
	else 
	    b = false;
	
	// wieder normales Verhalten
	tlg.remOption("in_setCaption");

	return b;
    }

    private void initSwitches() {
	if (gi.isDebug()) switches.addElement("debug");
	switches.addElement("Trans.useAbsyn");
    }

    private boolean save() throws IOException {
	boolean b = false;

	bw.write("# TESC-File generiert durch TESC-Export");
	bw.newLine();
	bw.write("# Formatierung der Transitionlabel geht beim Export verloren.");
	bw.newLine();
	bw.write("# Transitionlabels sind i.a. vollstaendig geklammert (bis auf aeussere Klammern)");
	bw.newLine();
	bw.newLine();
	
	b = savestate(stchart.state);
	
	bw.flush();
	
	return b;
    }

    private boolean savestate(State sc) throws IOException {
	boolean b = false;

	if (sc instanceof Or_State)
	    b = saveorstate((Or_State) sc);
	else if (sc instanceof Ref_State)
	    b = saverefstate((Ref_State) sc);
	else if (sc instanceof Basic_State)
	    b = savebasicstate((Basic_State) sc);
	else if (sc instanceof And_State)
	    b = saveandstate((And_State) sc);	
	else 
	    b = false;

	return b;
    }  

    private boolean savebasicstate(Basic_State st) throws IOException {
	bw.write(whiteSpace(tiefe * TAB));
	bw.write("basic ");
	savestatename(st.name);
        bw.write(";");
	bw.newLine();

	return true;
    }

    private boolean saverefstate(Ref_State st) throws IOException {
	boolean b = false;

	bw.write(whiteSpace(tiefe * TAB));
	bw.write("ref ");
	savestatename(st.name);
	bw.write(" in ");
	bw.write(st.filename);
	
	bw.write(" type ");
	
	b = savetype(st.filetype);

        bw.write(";");
	bw.newLine();

	return b;
    }

    private boolean savetype(Syntax_Type st) throws IOException {
	boolean b = true;

	if (st instanceof Tesc_Syntax) {
	    bw.write("tesc");
	}
	else if (st instanceof Pest_CoordSyntax) {
	    bw.write("pest_coord");
	}
	else if (st instanceof Pest_NocoordSyntax) {
	    bw.write("pest_nocoord");
	}
	else 
	    b = false;

	return b;
    }

    private boolean savestatename(Statename sn) throws IOException {
	if (sn != null) {
	    bw.write(sn.name);
	    return true;
	}
	else
	    return false;
    }

    private boolean saveorstate(Or_State st) throws IOException {
	boolean a = false, b = false, c = false, d = false, e = false;

	bw.write(whiteSpace(tiefe * TAB));
	bw.write("or ");
	savestatename(st.name);
	bw.write(":");
	bw.newLine();
	bw.newLine();

	tiefe++;
	a = savestatelist(st.substates);
	b = savedefcon(st.defaults);
	c = saveconnectors(st.connectors);
	d = savetransitionlist(st.trs);

	if (!a)
	     debug("Fehler in statelist");
	if (!b)
	     debug("Fehler in defcon");
	if (!c)
	     debug("Fehler in cons");
	if (!d)
	     debug("Fehler in Transition");


	bw.newLine();
	tiefe--;
	bw.write(whiteSpace(tiefe * TAB));
	bw.write("end ");
	e = savestatename(st.name);
	bw.write(";");
	bw.newLine();
	bw.newLine();

	return a && b && c && d && e;
    }

    private boolean savetransitionlist(TrList tl) throws IOException {
	boolean b = false;

	if (tl != null) {
	    bw.newLine();
	    bw.write(whiteSpace(tiefe * TAB)); 
	    bw.write("transitions:");
	    bw.newLine();
	    tiefe++;
	    b = savetrlist(tl);
	    tiefe--;
	}
	else
	    b = true;

	return b;
    }

    private boolean savetrlist(TrList tl) throws IOException {
	boolean a = false, b = false;
	if (tl != null) {
	    a = savetransition(tl.head);
	    b = savetrlist(tl.tail);

	}
	else {
	    a = true;
	    b = true;
	}

	return a && b;
    }

    private boolean savetransition (Tr trans) throws IOException{
	boolean a = false, b = false;
	String action = null;
	String guard = null;

	bw.write(whiteSpace(tiefe * TAB));
	bw.write("from ");
	a = saveanchor(trans.source);
	bw.write(" to ");
	b = saveanchor(trans.target);
	//bw.write(" on ");
	
	if (trans.label != null) {

	    if (switches.contains("Trans.useAbsyn")) {

		TESCLabelGen tl = new TESCLabelGen(trans.label.guard, trans.label.action, gi);
		
		guard = tl.getGuard();
		// zurückkommen soll, ob die Fkt. funktioniert hat, deshalb negieren
		b = !tl.error();
		action = tl.getAction();
		a = !tl.error();

	    }
	    else {
		String str = new String(trans.label.caption);
		int pos = str.indexOf('/');
		if (pos!=-1) {
		    guard = str.substring(0,pos);
		    action = str.substring(pos+1);
		    if (action.length()==0)
			action = null;
		}
		else {
		    guard = str;
		    action = null;
		}
	    }

            // Freizeichen, bzw "on", wenn guard vorhanden
	    if (guard.length() == 0) 		
		guard = new String(" "); 	    
	    else 
		bw.write(" on ");
		
	    bw.write(guard);
	    
	    if (action != null) {
		// Auch "do" nur schreiben, wenn Action etwas enthält
		if (action.length() > 0) {
		    bw.write(" do ");
		    bw.write(action);
		}
	    }
	    
	    bw.write(";");
	    bw.newLine();
	    
	}
	else
	    b = false;
	
	return a && b;
    }


    private boolean saveanchor(TrAnchor anc) throws IOException {
	boolean b = false;

	if (anc instanceof Statename) 
	    b = savestatename((Statename) anc);
	else if (anc instanceof Conname)
	    b = saveconnectorname((Conname) anc);
	else
	    b = false;

	return b;
    }

    private boolean savedefcon (StatenameList sl)throws IOException {
	boolean  b = false;
	
	if (sl != null) {
	    bw.newLine();
	    bw.write(whiteSpace(tiefe * TAB));
	    bw.write("defcon: ");
	    b = savestatenamelist(sl);
	}
	else
	    b = true;

	return b;
    }

    private boolean saveconnectors (ConnectorList cl) throws IOException{
	boolean b = false;

	if (cl != null) {
	    bw.newLine();
	    bw.write(whiteSpace(tiefe * TAB));
	    bw.write("cons: ");

	    b = saveconnectorlist(cl);
	}
	else
	    b = true;

	return b;
    }

     private boolean saveconnectorlist(ConnectorList sl) throws IOException {
	boolean a = false, b = false;
	
	if (sl != null) {
	    a = saveconnector(sl.head);
	    
	    if (sl.tail == null) {
		bw.write(";");
	        bw.newLine();
	    }
	    else
		bw.write(",");

	    b = saveconnectorlist(sl.tail);
	}
	else {
	    a = true;
	    b = true;
	}

	return a && b;
     }

    private boolean saveconnector(Connector c) throws IOException {
	return saveconnectorname(c.name);
    }

    private boolean saveconnectorname(Conname c) throws IOException{
	if (c != null) {
	    bw.write(c.name);
	    return true;
	}
	else
	    return false;
    }
	
    private boolean saveandstate(And_State st) throws IOException {
	boolean b = false;

	bw.write(whiteSpace(tiefe * TAB));
	tiefe++;
	bw.write("and ");
	savestatename(st.name);
	bw.write(":");
	bw.newLine();
	bw.newLine();

	b = savestatelist(st.substates);

	bw.newLine();
	tiefe--;
	bw.write(whiteSpace(tiefe * TAB));

	bw.write("end ");
	b = savestatename(st.name);
	bw.write(";");
	bw.newLine();
	bw.newLine();

	return b;
    }

    private boolean savestatelist(StateList sl) throws IOException {
	boolean a = false, b = false;
	if (sl != null) {
	    a = savestate(sl.head); 
	    b = savestatelist(sl.tail);
	}
	else {
	    a = true;
	    b = true;
	}

	return a && b;
    }

     private boolean savestatenamelist(StatenameList sl) throws IOException {
	boolean a = false, b = false;
	if (sl != null) {
	    a = savestatename(sl.head);
	    
	    if (sl.tail == null) {
		bw.write(";");
	        bw.newLine();
	    }
	    else
		bw.write(",");

	    b = savestatenamelist(sl.tail);
	}
	else {
	    a = true;
	    b = true;
	}

	return a && b;
     }

    private String whiteSpace (int n) {
	String str = "";
	for (int i=0; i<n; i++)
	    str = str + " ";
	return str;
    } 


    private void debug(String s) {
	
	if (switches.contains("debug")) {
	    String txt = new String("TESCSaver: DEBUG-> ");
	    
	    txt = txt.concat(s);
	    //if (gi != null) gi.userMessage(txt);
	    //else 
              System.out.println(txt);
	}
    }
} 


/* Letzte Änderungen
 *
 * - in savetransition: "on"/"do" nur schreiben, wenn guard/action != ""
 * - ins setCaption: "/" nur , wenn action != ""
 */	    
