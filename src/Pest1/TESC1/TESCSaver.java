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


public class TESCSaver {


    private Statechart stchart;
    private int errorCount = 0;   // Anzahl der aufgetretenen Fehler
    private Vector errorList;     // Fehlerliste
    private GUIInterface gi;
   
    private BufferedWriter bw;  

    private static final int TAB = 5;
    private int tiefe;

    private Vector switches;

    // protected-Methoden für das Package
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
 	
    public TESCSaver(GUIInterface gi_) {
	gi = gi_;
	
	tiefe = 0;
	
	switches = new Vector();
	initSwitches();

	
	
    }

    private void initSwitches() {
	 switches.addElement("debug");
	 //switches.addElement("Trans.useAbsyn");
    }

    protected boolean save() throws IOException {
	boolean b = false;
	
	b = savestate(stchart.state);
	
	
	
	return b;
    }

    private boolean savestate(State sc) throws IOException {
	boolean b = false;

	if (sc instanceof Or_State)
	    b = saveorstate((Or_State) sc);
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
	     gi.userMessage("statelist");
	if (!b)
	     gi.userMessage("defcon");
	if (!c)
	     gi.userMessage("cons");
	if (!d)
	     gi.userMessage("Transition");


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
	bw.write(" on ");
	
	if (trans.label != null) {

	    if (switches.contains("Trans.useAbsyn")) {

		//TESCLabelGen tl = new TESCLabelGen(trans.label.guard, trans.label.action);
		
		//guard = tl.getGuard();
		//action = tl.getAction();

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

	    if (guard.length() == 0) guard = new String("~");
		
	    bw.write(guard);
	    
	    if (action != null) {
		bw.write(" do ");
		bw.write(action);
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
	    if (gi != null) gi.userMessage(txt);
	    else System.out.println(txt);
	}
    }
} 
	    
