package tesc1;

import java.io.*;
import java.util.Vector;
import absyn.*;

/**
 * Tokenizer für TESC.<br>
 * Die <code>TESCTokenizer</code>-Klasse zerlegt die Eingabe aus einem 
 * <code>FileInputStream</code> in eine Sequenz von Token-Objekten.<br>
 * Die moeglichen Token werden in der Klasse <code>Token</code> festgelegt.
 * Abgeschlossen wird die Sequenz immer mit dem Token <code>TOK_EOF</code>.
 * Zeichen, die von <code>TESCTokenizer</code> nicht verarbeitet werden koennen
 * werden durch das Token <code>TOK_BADCHAR</code> aufgenommen.<br>
 * Die Einteilung der Zeichen in Klassen wird ueber die Funktionen
 * <code>isIdentitifierChar, isSpecialChar, isWhitespaceChar.
 * vorgenommen.
 * Die Vereinigung dieser vier Klassen ergibt die Menge der gueltige Zeichen.
 * <p>
 * Alle Ausnahmen werden weitergereicht.
 * <p>
 * Eine typische Anwendung von <code>TESCTokenizer</code> sieht wie folgt aus:
 * <p><code>
 * TESCTokenizer scanner = new TESCTokenizer(fis);<br>
 * ...<br>
 * Token t = scanner.getNextToken();<br>
 * </code>
 * <p>
 * @author Michael Sülzer, Christoph Schütte.
 * @version  $Id: TESCTokenizer.java,v 1.7 1999-01-17 17:13:04 swtech20 Exp $
 *
 * @see Token
 * @see TESCParser
 */   
class TESCTokenizer {
    
    /**
     * Eingabestrom mit Zwischenpuffer, in den <code>input_look_ahead</code> viele Zeichen
     * zurueckgeschrieben werden koennen.
     * @see TESCTokenizer#input_look_ahead
     */
    private PushbackReader input;

    /**
     * Look-Ahead-Grenze fuer den Eingabestrom. Bei ueberschreiten der Puffer
     * grenze wird eine Ausnahme erzeugt.
     * @see TESCTokenizer#input
     */
    private int input_look_ahead = 2;

    /**
     * Aktuelles Eingabezeichen als integer-Wert, um auch Ereignisse wie EOF mitzubekommen.
     */
    private int input_char;

    /**
     * Aktuelle Zeilennummer von <code>input_char</code>. 
     */
    private int input_line_number;

    /**
     * Flag fuer EOF von <code>input</code>.
     * @see TESCTokenizer#input
     */
    private boolean input_eof = false; 

    /**
     * Kommentarzeichen
     * @see TESCTokenizer#input
     */
    private char comment[] = {'/','/'}; 

    /**
     * Erzeugt einen TESCTokenizer. 
     * Der Eingabestrom wird an den PushbackInputStream <code>input</code> uebergeben
     * und der Zeilenzaehler initialisiert.
     */
    public TESCTokenizer (BufferedReader r) throws IOException {
   
	input       = new PushbackReader(r,input_look_ahead);

	input_line_number = 1;
    }

    /**
     * Liefert das naechste Zeichen aus dem Eingabestrom. Bei jedem
     * Zeilenwechsel wird der Zaehler <code>input_line_number</code> erhoeht. Bei EOF
     * wird <code>input_eof</code> gesetzt.
     * @return <code>kleiner 0</code>   bei EOF 
     *         <code<groesser 0</code>  sonst
     */
    private int readChar() throws IOException {
	int next_char       = input.read();
	input_line_number  += ((next_char == '\n') ? 1 : 0);
        input_eof           = (next_char == -1);
	return next_char;
    }

    /**
     * Gibt ein Zeichen in den Zeichenstrom unter Beruecksichtigung
     * der Zeilennummer und EOF(-1) zurueck. 
     * @param c    Zeichen, das zurueckgegeben werden soll.
     */
    private void unreadChar(int c) throws IOException {
	// EOF selbst wird nicht zurueckgelegt, da bel. reproduzierbar.
	if (c != -1) {
	    input_line_number -= ((c == '\n') ? 1 : 0);
	    input.unread(c);
	}
    }

    /**
     * Prüft, ob n im abgeschlossenen Intervall [min,max] liegt.
     * Es wird nicht geprüft, ob min >= max gilt.
     * @param n     zu pruefendes Element <br>
     *        min   Intervalluntergrenze <br>
     *        max   Intervallobergrenze
     **/
    private boolean in(int n,int min, int max) {
	return (n >= min && n <= max);
    }

    /** 
     * Prüft, ob es sich um ein im Sinne von TESCTokenizer gültiges
     * Zeichen handelt.
     */
    private boolean isValidChar (int n) {
	return isIdentifierChar (n) | isSpecialChar (n) | isWhitespaceChar (n);
    }

    /** 
     * Prüft, ob es sich um ein Identifikator-Zeichen handelt,
     * das sind die Zeichen [A-Za-z0-9_].
     */
    private boolean isIdentifierChar(int n) {
	return (in(n,65,90) | in(n,97,122) | in(n,48,57) || n == 95);  
    }

    /** 
     * Prüft, ob es sich um ein Sonderzeichen handelt,
     * das sind die Zeichen !"#$%&'()*+,-./:;<=>[]|
     */
    private boolean isSpecialChar(int n) {
	return (in(n,33,47) | in(n,58,62) | n == 91 | n == 93 | n == 124);
    }

    /** 
     * Prüft, ob es sich um ein Whitespace-Zeichen handelt,
     * das sind die Zeichen [\n\t\r<BLANK>].
     */
    private boolean isWhitespaceChar(int n) {
	return (n == '\n') | (n == '\t') | (n == '\r') | ( n== ' ') ;
    }
  
    /**
     * Prüft, ob es sich um einen Token-Praefix handelt.
     * @see Token#token_list
     */
    private boolean isTokenPrefix(String s) {
 	for (int i=1; i<Token.token_count;i++) {
	    if (Token.token_list[i].getValue().startsWith(s))
		return true;
	}
	return false;
    }

    /**
     * Prüft, ob es sich um ein Token handelt.
     * @see Token#token_list
     */ 
    private boolean isToken(String s) {
	for (int i=1; i<Token.token_count;i++) {
	    if (Token.token_list[i].getValue().equalsIgnoreCase(s))
		return true;
	}
	return false;
    }

    /**
     * Liest das naechste, laengstmoegliche Token ein.
     * Solange der uebergebene String ein gueltiges Token-Präfix
     * ist, wird das naechste Zeichen aus dem Eingabestrom angehaengt
     * und rekursiv verzweigt. Ist das Ergebnis negativ, wird
     * geprüft, ob das aktuelle Token-Praefix ein ganzes Token
     * ist. Ist das der Fall wird der Token-String zurueckgegeben.
     * Andernfalls der Original-String.
     * @param s    Praefix
     * @returns String
     * @see Token#token_list
     * @see TESCTokenizer#getNextToken
     */
    private String readLongestToken(String s) throws IOException {

	StringBuffer s2 = new StringBuffer(s);

	// Naechstes Zeichen lesen und an alten Prefix anhaengen
	int input_char_rek = readChar();
	s2.append((char)input_char_rek);

	if (isTokenPrefix(s2.toString())) {
	    // Neuer String ist noch ein Praefix
	    String s3 = readLongestToken(s2.toString());
	    if (isToken(s3)) {
		// Es wurde ein noch laengeres Token gefunden.
		return s3;
	    }
	    else 
		// Laengere Token exitistieren nicht
		if (isToken(s2.toString())) {
		    // Aktuelle String ist laengstmoegliches Token
		    return s2.toString();  
		}
		else {
		    // Der aktuelle String ist kein Token.
		    unreadChar(input_char_rek);
		    return s;
	    }
	}
	else {
	    // Der aktuelle String ist kein Token.
	    unreadChar(input_char_rek); 
	    return s;
	}
    }

    /**
     * Liest einen Identifikator-String ein.
     * Es wird solange gelesen, bis ein nicht Identifikatorzeichen
     * auftaucht. Das letzte Zeichen wird wieder zurueckgelegt.
     * Das erste Zeichen wird aus dem Puffer genommen, danach
     * wird zum erstenmal gelesen.
     * @returns   String
     * @see TESCTokenizer#getNextToken
     */
    private String readIdentifier () throws IOException {

	StringBuffer identifier = new StringBuffer();

	while (isIdentifierChar (input_char)) {
	    identifier.append ((char)input_char);
	    input_char = readChar (); 
	}
	unreadChar(input_char);

	return identifier.toString();
    }

    /**
     * Liefert das naechste Token aus dem Eingabestrom.
     * @returns Token
     */
    public Token getNextToken() throws IOException {

	StringBuffer token_value = new StringBuffer();

	input_char = readChar();

	// --- Whitespace ueberlesen ----------------------------------------------------
	while (isWhitespaceChar(input_char))
	    input_char = readChar();

	// --- Kommentare ueberlesen ----------------------------------------------------
	// Ein Kommentar beginnt mit //
	if (input_char == comment[0]) {
	    int input_save = input_char;
	    input_char = readChar();
	    if (input_char == comment[1]) {
		while ((input_char != '\n') & !input_eof) {
		    input_char = readChar();
		}
		unreadChar(input_char);
		return getNextToken();

	    }
	    else {
		unreadChar(input_char);
		unreadChar(input_save);
		input_char = readChar();
	    }
	}

	// --- Stream-Ende --------------------------------------------------------------
	if (input_eof)
	    return (Token) Token.EOF.clone(input_line_number);

   
	// --- Token ermitteln und zurueckgeben -----------------------------------------
	if (isValidChar(input_char)) {
	    if (isSpecialChar(input_char)) {
		token_value.append((char)input_char);
		token_value = new StringBuffer(readLongestToken(token_value.toString()));
	    }
	    else {
		if (isIdentifierChar (input_char)) {
		    token_value =  new StringBuffer(readIdentifier ());
		}
	    }
	    for (int i=0; i<Token.token_count;i++) {
		
		if (Token.token_list[i].getValue().equalsIgnoreCase(token_value.toString()))
		    return (Token) Token.token_list[i].clone(input_line_number);
	    }
	    return (Token) Token.Identifier.clone(token_value.toString(), input_line_number);	
	}

	// --- Ungueltiges Zeichen -----------------------------------------------------
	token_value.append((char)input_char);
	return (Token) Token.BadChar.clone(token_value.toString(),input_line_number);
    }
}

//----------------------------------------------------------------------
//      Schnittstelle zum TESCParser               
//      ----------------------------               
//
//      $Log: not supported by cvs2svn $
//      Revision 1.6  1999/01/11 12:13:57  swtech20
//      Bugfixes.
//
//      Revision 1.5  1999/01/03 21:48:20  swtech20
//      Implementierung des Parsers
//
//      Revision 1.4  1998/12/17 11:14:13  swtech20
//      BufferedReader statt FileInputStream.
//
//      Revision 1.3  1998/12/15 18:11:37  swtech00
//      Towards new naming conventions for PEST2
//
//      Revision 1.2  1998/12/13 17:49:06  swtech20
//      Checkin für Baseline
//
//      Revision 1.1  1998/12/03 13:08:18  swtech20
//      Geaenderte TESC1-Schnittstelle nach Absprache mit Gruppe TESC1 aus PEST1.
//      Die Schnittstellen sind jetzt für beide Gruppen gleich.
//
//              
//----------------------------------------------------------------------


