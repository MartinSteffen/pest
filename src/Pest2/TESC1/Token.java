package tesc1;

/**
 * Tokenklasse.
 * <p>
 * @author Michael Sülzer, Christoph Schütte.
 * @version  $Id: Token.java,v 1.2 1998-12-15 18:11:37 swtech00 Exp $
 */   

public class Token {

    public static final int token_count = 24;

    public static final int TOK_BADCHAR    = -2;
    public static final int TOK_EOF        = -1;
    public static final int TOK_IDENTIFIER =  0;

    public static final int TOK_DEFAULT    =  1;
    public static final int TOK_BASIC      =  2;
    public static final int TOK_FALSE      =  3;
    public static final int TOK_FROM       =  4;
    public static final int TOK_TRUE       =  5;
    public static final int TOK_AND        =  6;
    public static final int TOK_CON        =  7;
    public static final int TOK_END        =  8;
    public static final int TOK_ENTERED    =  9;
    public static final int TOK_EXITED     = 10;
    public static final int TOK_IN         = 11;
    public static final int TOK_DO         = 12;
    public static final int TOK_ON         = 13;
    public static final int TOK_OR         = 14;
    public static final int TOK_TO         = 15;
    public static final int TOK_UNDEF      = 16;
    public static final int TOK_ANDOP      = 17;
    public static final int TOK_OROP       = 18;
    public static final int TOK_NOTOP      = 19;  
    public static final int TOK_EQUAL      = 20;
    public static final int TOK_KOMMA      = 21;
    public static final int TOK_LPAR       = 22;
    public static final int TOK_RPAR       = 23;
    public static final int TOK_IMPLIES    = 24;

    public static final Token token_list[] = {
	new Token(TOK_DEFAULT, "default", 0),
	new Token(TOK_BASIC,   "basic", 0),
	new Token(TOK_FALSE,   "false", 0),
	new Token(TOK_UNDEF,   "undef", 0),
	new Token(TOK_FROM,    "from", 0),
	new Token(TOK_TRUE,    "true", 0),
	new Token(TOK_AND,     "and", 0),
	new Token(TOK_CON,     "con", 0),
	new Token(TOK_END,     "end", 0),
	new Token(TOK_ENTERED, "entered", 0),
	new Token(TOK_EXITED,  "exited", 0),
	new Token(TOK_IN,      "in", 0),
	new Token(TOK_DO,      "do", 0),
	new Token(TOK_ON,      "on", 0),
	new Token(TOK_OR,      "or", 0),
	new Token(TOK_TO,      "to", 0),
        new Token(TOK_EQUAL,   "<=>", 0),
	new Token(TOK_IMPLIES, "=>", 0),
        new Token(TOK_ANDOP,   "&", 0),
        new Token(TOK_OROP,    "|", 0),
        new Token(TOK_NOTOP,   "!", 0),
        new Token(TOK_KOMMA,   ",", 0),
        new Token(TOK_LPAR,    "(", 0),
	new Token(TOK_RPAR,    ")", 0)
    };

    private int id;
    private String value;
    private int line;

    public Token (int i, String v, int l) {
	id = i;
	value = v;
	line = l;
    }
  
    /**
     * Liefert den Token-Identifikator.
     * @return Value of token.
     */
    public int getId() {return id;}

    /**
     * Liefert den String, der mit dem Token identifiziert wird.
     * @return Value of value.
     */
    public String getValue() {return value;}  

    /**
     * Liefert den Zeilennummer in der das Token vorkam.
     * @return Value of line.
     */
    public int getLine() {return line;}  

}
 
//----------------------------------------------------------------------
//      Tokenklasse               
//      -----------               
//
//      $Log: not supported by cvs2svn $
//      Revision 1.1  1998/12/13 17:49:07  swtech20
//      Checkin für Baseline
//
//              
//----------------------------------------------------------------------
