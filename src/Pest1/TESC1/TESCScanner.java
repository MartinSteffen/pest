package TESC1;

//import java.util.*;
import Absyn.*;
import java.io.*;

class TESCScanner {

    private FileInputStream is;
    private TOKEN token;

    private int ln;  // Zeilennummern

    private int lastb =0;     // Steuervar für readStream
    private StringBuffer lah; // LookAhead - Buffer


    // protected-Methoden für das Package
	
    protected TESCScanner(FileInputStream is_) {
	token = new TOKEN();

	ln = 1;	
	is = is_;
	
	lah = new StringBuffer();	
    }

    protected TOKEN nextToken() {
	scan_forward();

	return token;
    }

    // überflüssig ?
    protected TOKEN getToken() {
	return token;
    }


    // private-Methoden nur für TESCScanner

    /**
     * is_white prüft, ob ein Character ein "Leer"zeichen ist.
     * Diese sind:
     * - Space, Tab, Newline, CR(?)
     */
    private boolean is_white(char c) {
	boolean ret = false;

	switch (c) {
	case ' ': 
	case '\t' :
	case '\n':
	case '\r':
	    ret = true;
	    break;

	default:
	    ret = false;
	    break;
	}

	return ret;
    }

    // zusätzliche Tokentrenner
    // die Funktion kann teil von is_partOfSep werden <- ist jetzt so
    private boolean is_sep(String s) {
	boolean ret = false;

	/*
	switch (c) {
	case ',':
	case ';':
	case ':':
	case '(':
	case ')':
	    ret = true;
	    break;
	default:
	    ret = false;
	    break;
	}

	*/

	if      (s.compareTo((String)",") == 0)   ret = true;
	else if (s.compareTo((String)";") == 0)   ret = true;
	else if (s.compareTo((String)":") == 0)   ret = true;
	else if (s.compareTo((String)"(") == 0)   ret = true;
	else if (s.compareTo((String)")") == 0)   ret = true;
	

	return ret;
    }


    // Prüft, ob s ein Trenner ist, d.h. ein Token das andere Token trennt, 
    // ohne dass whitechars dazwischen sein muessen. ( z.B. x=(a&&b) )
    private boolean is_partOfSep(String s) {
	boolean ret = false;

	// Einstellige Trenner
	if      (s.compareTo((String)",") == 0)   ret = true;
	else if (s.compareTo((String)";") == 0)   ret = true;
	else if (s.compareTo((String)":") == 0)   ret = true;
	else if (s.compareTo((String)"(") == 0)   ret = true;
	else if (s.compareTo((String)")") == 0)   ret = true;

	// Mehstellige Trenner
	else if (s.regionMatches(0, (String)"<=>", 0, s.length()))   ret = true;
	else if (s.regionMatches(0, (String) "=>", 0 ,s.length()))   ret = true;
	else if (s.regionMatches(0, (String)"&&", 0, s.length()))    ret = true;
	else if (s.regionMatches(0, (String) "||", 0 ,s.length()))   ret = true;
	else if (s.regionMatches(0, (String) ":=", 0 ,s.length()))   ret = true;

	return ret;
    }

    // spezielles read: fügt blanks vor trennern ein, die auch keys sind. <- Läuft jetzt anders
    // dadurch einfacher Scanner möglich 
    /*
    private int readStream()  { // throws IOExecption
	int b = -1;
	
	StringBuffer sb = new StringBuffer();

	if (lastb == '\n') ln++;

	try {
	    // Falls noch kein char gelesen, eins lesen
	    if (la == 0) {
		 
		b = is.read();
	      
		if (b != -1) {
		    ready = 0;
		    sb.append((char)b);
		    
		    // Bei Sep soll dieses von Blanks umschlossen werden
		    if (is_partOfSep(sb.toString())) {
			pos.write((int)' ');
			la++;
			while (is_partOfSep(sb.toString()) && b!=-1) {
			    pos.write(b);
			    la++;
			
			    b = is.read();
			    if (b != -1) sb.append((char)b);
			   
			}			
		      			       
			pos.write((int)' ');
			la++;
			// Sep korrekt "geklammert"; la gibt an wieviele chars schon im Stream,
			// so dass beim nächsten Lesen nicht aus dem FileInputStream gelesen werden muss
		    }
		
		    // falls sep wird ein char zu weit gelesen
		    pos.write(b);
		    la++;
		}
		else ready = -1;
	    }
	}
	catch (IOException e) {
	    // Bla
	    System.out.println("Ärger!");
	}

	try {

	    // Endgültiges Lesen des Chars
	    if (ready == -1 && pis.available()==0) b = -1;
	    else {
		la--;  // Zeichen gelesen => la dekrementieren
		b = pis.read();
	    }
	}
	catch (IOException e) {
	    // Bla
	    System.out.println("Ärger2!");
	}

	lastb = b;
	return b;
    }
    */
    
    // "klammert" Trenner mit Spaces. Liefert den nächsten char aus dem Stream oder Space
    private int trim(int b) {

	try {

	if (b != -1) {
		lah.append((char)b);
	   
		// Falls ein Trenner vorliegt, diesen mit Space "klammern"
		if (is_partOfSep(lah.toString()) && b != -1) {
		    	    
		    // Bis zum Ende des Trenners lesen
		    b = is.read();
		    lah.append((char)b);
		    
		    while (is_partOfSep(lah.toString()) && b != -1) {
			b = is.read();
			lah.append((char)b);
		    }
		    
		    // Es wird ein Char weiter gelesen, deshalb dazwischen ein Space
		    lah.insert(lah.length()-1, ' ');
		    		   
		    //nächstes char soll Space sein
		    b = ' ';
		}
		else lah.setLength(0);
	}

	}
	catch (IOException e) {
	    // Bla
	    System.out.println("Ärger!");
	}

	return b;
    }


    // spezielles read. Formatiert den Text so, dass Token immer von whitechars umgeben sind
    private int readStream() {
	int b = -1;
	StringBuffer sb = new StringBuffer();
	String buf;

	if (lastb == '\n') ln++;

	try {

	    // Falls schon chars gelesen, diese aus Buffer lesen
	    if (lah.length()!=0) {
		// erstes char konsumieren
		b = lah.charAt(0);
		buf = lah.toString().substring(1);
		lah.setLength(0);
		lah.append(buf);

		// Falls das dem Trenner folgende char wieder ein Trenenr ist, 
		// dieses wieder space-klammern
		if (lah.length()==0 && is_partOfSep(buf.valueOf((char)b))) b = trim(b);
	    }
	    else {
		// sonst aus Stream lesen
		b = is.read();	
		// und sicherstellen, dass volständige Space-Klammerung
		b = trim(b);
	    }
	    
	}
	catch (IOException e) {
	    // Bla
	    System.out.println("Ärger!");
	}

	lastb = b;
	return b;
    }
    

    private int scan_forward() {
	int b=0;
	StringBuffer sb = new StringBuffer();

	b = readStream();
	
	// Leerzeichen eliminieren
	while(is_white((char)b) && b != -1) b = readStream();

	// Token extrahieren
	while (!is_white((char)b) && b != -1) {   // -1 => EOF
	    sb.append((char)b);
	    b = readStream();
	}
		
	if (b != -1) {
	    token.value_str = sb.toString();
	    token.token     = typeOfToken(sb.toString());
	    token.linenum   = ln;
	}
	else {
	    token.value_str = "";
	    token.token     = vTOKEN.EOF;
	    token.linenum   = ln;	
	}
	
	return b;
    }

    /*
    private boolean is_key(String s) {
	boolean ret = false;

	if      (s.compareTo((String)"basic") == 0)   ret = true;
	else if (s.compareTo((String)"or") == 0)      ret = true;
	else if (s.compareTo((String)"amd") == 0)     ret = true;
	else if (s.compareTo((String)"from") == 0)    ret = true;
	else if (s.compareTo((String)"on") == 0)      ret = true;	
	else if (s.compareTo((String)"to") == 0)      ret = true;	
	else if (s.compareTo((String)"do") == 0)      ret = true;
	else if (s.compareTo((String)"end") == 0)     ret = true;
	else if (s.compareTo((String)"true") == 0)    ret = true;
	else if (s.compareTo((String)"(") == 0)       ret = true;
	else if (s.compareTo((String)")") == 0)       ret = true;
	else if (s.compareTo((String)":") == 0)       ret = true;
	else if (s.compareTo((String)";") == 0)       ret = true;
	else if (s.compareTo((String)"events") == 0)  ret = true;
	else if (s.compareTo((String)",") == 0)       ret = true;
	else if (s.compareTo((String)"<=>") == 0)     ret = true;
	else if (s.compareTo((String)"=>") == 0)      ret = true;

	return ret;
    }
    */

    private int typeOfToken(String s) {
	int i = vTOKEN.DUMMY;

	
	if (s.compareTo((String)"") == 0)             i = vTOKEN.DUMMY;

	else if (s.compareTo((String)"basic") == 0)   i = vTOKEN.BSTATE;
	else if (s.compareTo((String)"or") == 0)      i = vTOKEN.OSTATE;
	else if (s.compareTo((String)"and") == 0)     i = vTOKEN.ASTATE;
	else if (s.compareTo((String)"from") == 0)    i = vTOKEN.FROM;
	else if (s.compareTo((String)"on") == 0)      i = vTOKEN.ON;	
	else if (s.compareTo((String)"to") == 0)      i = vTOKEN.TO;	
	else if (s.compareTo((String)"do") == 0)      i = vTOKEN.DO;
	else if (s.compareTo((String)"end") == 0)     i = vTOKEN.END;
	else if (s.compareTo((String)"true") == 0)    i = vTOKEN.TRUE;
	else if (s.compareTo((String)"false") == 0)   i = vTOKEN.FALSE;
	else if (s.compareTo((String)"(") == 0)       i = vTOKEN.LPAR;
	else if (s.compareTo((String)")") == 0)       i = vTOKEN.RPAR;
	else if (s.compareTo((String)":") == 0)       i = vTOKEN.COLON;
	else if (s.compareTo((String)";") == 0)       i = vTOKEN.SCOLON;
	else if (s.compareTo((String)"events") == 0)  i = vTOKEN.EVENTS;
	else if (s.compareTo((String)",") == 0)       i = vTOKEN.COMMA;
	else if (s.compareTo((String)"<=>") == 0)     i = vTOKEN.AQUI;
	else if (s.compareTo((String)"=>") == 0)      i = vTOKEN.IMPL;
	else if (s.compareTo((String)"&&") == 0)      i = vTOKEN.AND;
	else if (s.compareTo((String)"||") == 0)      i = vTOKEN.OR;
	else if (s.compareTo((String)"in") == 0)      i = vTOKEN.IN;
	else if (s.compareTo((String)"entered") == 0) i = vTOKEN.ENTERED;
	else if (s.compareTo((String)"exited") == 0)  i = vTOKEN.EXITED;

	else if (s.compareTo((String)"~") == 0)           i = vTOKEN.EMPTYEXP;
	else if (s.compareTo((String)"undef") == 0)       i = vTOKEN.UNDEF;
	else if (s.compareTo((String)"transitions") == 0) i = vTOKEN.TRANSITIONS;
	else if (s.compareTo((String)"bvars") == 0)       i = vTOKEN.BVARS;
	else if (s.compareTo((String)"pathnames") == 0)   i = vTOKEN.PATHNAMES;
	else if (s.compareTo((String)"connectors") == 0)  i = vTOKEN.CONS;
	else if (s.compareTo((String)":=") == 0)          i = vTOKEN.BASSIGN;
	else if (s.compareTo((String)"not") == 0)         i = vTOKEN.NOT;

	else i = vTOKEN.IDENT;
	
	return i;
    }
    
}


/* TESCScanner
 * $Id: TESCScanner.java,v 1.2 1998-12-07 13:20:16 swtech13 Exp $
 * $Log: not supported by cvs2svn $
 */
