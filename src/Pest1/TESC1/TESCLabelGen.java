package tesc1;

import java.util.*;
import absyn.*;
import java.io.*;
import gui.*;
import java.lang.*;
import util.*;

/* Konventionen:
 * Kommentare enthalten:
 *
 * Temporaere Features: temporar
 * Debug      ---"--- : debug
 * Evtl. Fehler       : ???
 *
 */


public class TESCLabelGen {
    
    private int errorCount = 0;   // Anzahl der aufgetretenen Fehler
    private Vector errorList;     // Fehlerliste
    private GUIInterface gi;
   
    private Guard grd;
    private Action act;

    private boolean inEv;         // SchalteTESCLabelGen.javar, ob der Ev-Teil, oder der Bvar-Teil aufgebaut wird
    private boolean inAction;     // -- " --, setzt inEv ausser Gefecht
    private boolean switchToBv;   // Übergang zu Bvar-Teil
    private boolean start;
    private boolean undetUsed;
    private boolean errc;

    //StringBuffer str;

    //StringBuffer act_str;

    private Vector switches;

    /** 
     * Umwandeln eines Guard/Action in Strings
     * @param Guard 
     * @param Action
     * @param GUIInterface
     */ 
    public TESCLabelGen(Guard grd_, Action act_, GUIInterface gi_) {
	gi = gi_;
     
	grd = grd_;
	act = act_;

	inEv = true;
	inAction = false;
	switchToBv = false;
	start = true;
	undetUsed = false;
	errc = false;
	
	switches = new Vector();
	initSwitches();

	/*
	if (stchart == null)
	    gi.userMessage("Statechart leer");
	*/
	errorList = new Vector();
	
    }

    /** 
     * Umwandeln eines Guard/Action in Strings, Ausgabe in Terminal
     * @param Guard 
     * @param Action
     */ 
    public TESCLabelGen(Guard grd_, Action act_) {
	gi = null;
     
	grd = grd_;
	act = act_;

	inEv = true;
	inAction = false;
	switchToBv = false;
	start = true;
	undetUsed = false;
	errc = false;	
	
	switches = new Vector();
	initSwitches();

	
	errorList = new Vector();			
    }

    
    public String getGuard() {
	String s = null;
	boolean b = false;
	boolean c = false;
	boolean gbe = false;

	inEv = true;
	inAction = false;
	switchToBv = false;
	start = true;
	undetUsed = false;
	errc = false;
	
	// Falls nur Bvar/pathop und kein Eventteil
	if (!(grd instanceof GuardCompg)) {
	    b = firstBvar(grd);
	    inEv = false;
	}
	else {
	    if (((GuardCompg)grd).cguard.eop == Compguard.AND) {
		// Ist rhs bvar-teil?
		c = firstBvar(((GuardCompg)grd).cguard.erhs);
		// ist rhs leer => bvar-teil leer
		gbe = isEmptyGuard(((GuardCompg)grd).cguard.erhs);
		
	    }
	}
	
	if (c) {
	    s = new String(addGuard(((GuardCompg)grd).cguard.elhs) + "[");

	    // jetzt der Bvar-Teil
	    start = true; // wieder keine aüßeren Klammern	   
	    inEv = false;
	    s = new String(s + addGuard(((GuardCompg)grd).cguard.erhs) + "]");
	}
	else if (gbe)
	    // Leerer Bvar-Teil
	    s = addGuard(((GuardCompg)grd).cguard.elhs);
	else
	    // Mmh, glaube das heisst: Eventteil, kein Bvarteil (nicht mal Empty/Dummy)
	    s = addGuard(grd);

	// ???
	if (b) s = new String("[" + s + "]");

	if (undetUsed) {
	 
		s = makeLabelErrStr("Guard", s);   
	/*
	    s = new String("~ \n # Fehler: Variable(n) wurde(n) als GuardUndet angegegeben:\n # " + s + "\n");
	    err("Variable(n) wurde(n) als GuardUndet angegegeben (Guard)!");
	*/
	}

	return s;
    }
    
    private String makeLabelErrStr(String w, String orig) {
	String s = null;

	if (switches.contains("in_setCaption")) {
		s = new String("~  #Fehler: " + orig + "#");
	}
	else {
		s = new String("~ \n # Fehler: Variable(n) wurde(n) als GuardUndet angegegeben:\n # " + orig + "\n");
            	err("Variable(n) wurde(n) als GuardUndet angegegeben (" + w +")!");
	}

	return s;
    }

    public String getAction() {
	String s = null;
	inEv = true;
	inAction = true;
	switchToBv = false;
	start = true;
	undetUsed = false;
	errc = false;

	s = addAction(act);

	if (undetUsed) {
		s = makeLabelErrStr("Action", s);
	/*
	    s = new String("~ \n # Fehler: Variable(n) wurde(n) als GuardUndet angegegeben: \n # " + s + "\n");
	    err("Variable(n) wurde(n) als GuardUndet angegegeben (Action)!");
	*/
	}
	return s;
    }

    protected boolean error() {
	return errc;
    }

    private String addGuard (Guard g) {
	String s = null;

	if (g instanceof GuardBVar)
	    s = addGuardBVar ((GuardBVar)g);
	else if (g instanceof GuardCompg)
	    s = addGuardCompg ((GuardCompg)g);
	else if (g instanceof GuardCompp)
	    s = addGuardCompp ((GuardCompp)g);
	else if (g instanceof GuardEmpty)
	    s = addGuardEmpty ((GuardEmpty)g);
	else if (g instanceof GuardEvent)
	    s = addGuardEvent ((GuardEvent)g);
	else if (g instanceof GuardNeg)
	    s = addGuardNeg ((GuardNeg)g);
	else if (g instanceof GuardUndet)
	    s = addGuardUndet((GuardUndet)g);
	else 
	    err("Guard unbekannt!");
	
	return s;
    }

    private String addGuardUndet(GuardUndet g) {
	String s = null;

	s = new String(g.undet);
	undetUsed = true;

	return s;
    }

    private String addGuardBVar(GuardBVar bv) {
	String s = null;
	if (bv.bvar != null) 	   
		s = addBvar(bv.bvar);

	if (inEv && !inAction)
	    err("Bvar " + bv.bvar.var + " im Eventteil gefunden!");

	return s;
    }

    private String addBvar(Bvar bv) {
	String s = null;

	s = new String(bv.var);

	return s;
    }

    private String addGuardEmpty(GuardEmpty ge) {
	//return new String("~");
	return new String("");
    }

    private String addGuardEvent (GuardEvent ev) {
	String s = null;
	if (ev.event != null) 
	    s = new String(ev.event.name);

	return s;
    }

    private String addGuardNeg (GuardNeg gn) {
	
	return new String("!(" + addGuard(gn.guard) + ")");
    }

    private String addGuardCompp (GuardCompp gcp) {
	String s = null;

	if (gcp.cpath != null) 
	    s = new String(addPathOp(gcp.cpath.pathop) + "(" + addComppath(gcp.cpath.path) + ")");

	return s;
    }

    private String addPathOp (int op) {
	String s = null;

	switch(op) {
	case Comppath.IN:
	    s = new String("in");
	    break;
	case Comppath.ENTERED:
	    s = new String("entered");
	    break;
	case Comppath.EXITED:
	    s = new String("exited");
	    break;
	}

	return s;
    }

    private String addComppath (Path p) {
	StringBuffer sb = new StringBuffer();
	
	if(p != null) {
	    sb.append(p.head);
	    if (p.tail != null) {
		sb.append(".");             // Punkt als Separator
		sb.append(addComppath(p.tail));
	    }
	}

	return sb.toString();
    }


    //private String addGuardCompg_ev (GuardCompg gcg) {
    
    private String addGuardCompg (GuardCompg gcg) {
	String s = null;
	boolean b = false;

	if (gcg.cguard != null) {
	    
	    // Keine äußeren Klammern
	    if (start) {
		start = false;
		s = new String(addGuard(gcg.cguard.elhs) + addGuardOp(gcg.cguard.eop) + addGuard(gcg.cguard.erhs));
	    }
	    else 
		s = new String("(" + addGuard(gcg.cguard.elhs) + addGuardOp(gcg.cguard.eop) + addGuard(gcg.cguard.erhs) + ")");

	   
	}

	return s;
    }

    private String addGuardOp(int op) {
	String s = null;
	/*
	if (switchToBv) {

	    switchToBv = false;
	    inEv = false;
	    return new String("[");
	}
	*/

	switch(op) {
	case Compguard.AND:
	    s = new String("&&");
	    break;
	case Compguard.OR:
	    s = new String("||");
	    break;
	case Compguard.EQUIV:
	    s = new String("<=>");
	    break;
	case Compguard.IMPLIES:
	    s = new String("=>");
	    break;
	}
	return s;
    }



    /*
    private void checkSw(Compguard grd) {
	if (!inAction) {
	    // nur dann kann was passieren
	    if (grd.eop == Compguard.AND && inEv) {
		
		if (start && grd.elhs instanceof GuardEmpty)
		    switchToBv = true;
		else {
		    // ganz absteigen
		    Compguard g = grd;

		    while (g.elhs instanceof GuardCompg) {
			System.out.println("Prüfe " + addGuardCompg(g.elhs));
			g = ((GuardCompg)g.elhs).cguard;			
		    }

		    if (firstBvar(g.erhs)) switchToBv = true;
		}
	    }
	}       
       
    }
    */

    // prüft, ob das erste Vorkommen einer Var in einem Guard ein Bvar/pathop ist
    private boolean firstBvar(Guard grd) {
	boolean b = false;

	if (grd instanceof GuardEvent) 
	    b = false;
	else if (grd instanceof GuardBVar)
	    b =  true;
	else if (grd instanceof GuardCompp)
	    b = true;
	else if (grd instanceof GuardEmpty)
	    b = false;
	    //b = true;

	else if (grd instanceof GuardCompg)
	    b = firstBvar(((GuardCompg)grd).cguard.elhs);	
	else if (grd instanceof GuardNeg)
	    b = firstBvar(((GuardNeg)grd).guard);

	return b;
    }

    private boolean isEmptyGuard(Guard grd) {
	boolean b = false;

	if (grd instanceof GuardEmpty)
	    b = true;

	return b;
    }


    // Wozu diese Fkt. ??
    private boolean secEmpty(Guard g) {
	boolean b = false;

	if (grd instanceof GuardEmpty)
	    b = true;

	return b;
    }



    // ACTIONS

    private String addAction(Action act) {
	String s = null;

	if (act instanceof ActionBlock) 
	    s = addActionBlock((ActionBlock)act);
	else if (act instanceof ActionEmpty)
	    s = addActionEmpty((ActionEmpty)act);
	else if (act instanceof ActionEvt)
	    s = addActionEvt((ActionEvt)act);
	else if (act instanceof ActionStmt)
	    s = addActionStmt((ActionStmt)act);

	return s;
    }

    private String addActionEmpty(ActionEmpty a) {
	//return new String("~");
	return new String("");
    }

    private String addActionEvt(ActionEvt a) {
	String s = null;

	if (a.event != null) 
	    s =new String(a.event.name);

	return s;
    }


    private String addActionStmt(ActionStmt a) {
	return addBoolStmt(a.stmt);
    }

    private String addBoolStmt(Boolstmt bs) {
	String s = null;

	if (bs instanceof BAss) 
	    s = addBassign(((BAss)bs).ass);
	else if (bs instanceof MTrue) 
	    s = new String(((MTrue)bs).var.var + ":=true");
	else if (bs instanceof MFalse) 
	    s = new String(((MFalse)bs).var.var + ":=false");

	return s;
    }

    private String addBassign(Bassign ba) {
	String s = null;
	
	s = new String(addBvar(ba.blhs) + ":=" + addGuard(ba.brhs));

	return s;
    }

    private String addActionBlock(ActionBlock a) {
	String s = null;
	
	s = addAseq(a.aseq);
	
	return s;
    }

    private String addAseq(Aseq a) {
	String s = null;

	if (a != null) {
	    // Kein , am Ende
	    if (a.tail != null)
		s = new String(addAction(a.head) + ", " + addAseq(a.tail));
	    else 
		s = new String(addAction(a.head));	
	}

	return s;
    }

    private void initSwitches() {
      // switches.addElement("debug");
	 switches.addElement("Trans.useAbsyn");
    }

    // switches hinzu
    protected void setOption(String s) {
         switches.addElement(s);
    }

    protected void remOption(String s) {
	switches.removeElement(s);
    }

    private void warn(String s) {
	gi.userMessage("TESC-Export: Warnung: "+ s);
    }

    private void err(String s) {
	errc = true;
	if (gi != null) gi.userMessage("TESC-Export: Fehler: "+ s);
	else
          System.out.println("TESC-Export: Fehler: "+ s);
    }


}

/* Letzte Änderungen
 *
 * - addGuardEmpty/addActionEmpty : liefern "" statt "~"
 * - in firstBvar: false bei Empty
 */
