package tesc1;

/**
 * Tokenklasse.
 * <p>
 * @author Michael Sülzer, Christoph Schütte.
 * @version  $Id: Token.java,v 1.3 1999-01-03 21:48:21 swtech20 Exp $
 */   

public class Token implements Cloneable {

    public static final int token_count = 28;

    public static final int TOK_BADCHAR    = -2;
    public static final int TOK_EOF        = -1;
    public static final int TOK_IDENTIFIER =  0;

    public static final int TOK_DEFAULT    =  1;
    public static final int TOK_ENTERED    =  2;
    public static final int TOK_EXITED     =  3;
    public static final int TOK_BASIC      =  4;
    public static final int TOK_FALSE      =  5;
    public static final int TOK_UNDEF      =  6;
    public static final int TOK_FROM       =  7;
    public static final int TOK_TRUE       =  8;
    public static final int TOK_AND        =  9;
    public static final int TOK_CON        = 10;
    public static final int TOK_END        = 11;
    public static final int TOK_IN         = 12;
    public static final int TOK_DO         = 13;
    public static final int TOK_ON         = 14;
    public static final int TOK_OR         = 15;
    public static final int TOK_TO         = 16;
    public static final int TOK_ANDOP      = 17;
    public static final int TOK_OROP       = 18;
    public static final int TOK_NOTOP      = 19;
    public static final int TOK_ASSIGN     = 20;
    public static final int TOK_IMPLIES    = 21;
    public static final int TOK_EQUIV      = 22;
    public static final int TOK_KOMMA      = 23;
    public static final int TOK_LPAR       = 24;
    public static final int TOK_RPAR       = 25;
    public static final int TOK_VAR        = 26;
    public static final int TOK_EVENT      = 27;
    public static final int TOK_DOT        = 28;

    public static final Token BadChar      = new Token(TOK_BADCHAR);
    public static final Token EOF          = new Token(TOK_EOF, "<EOF>");
    public static final Token Identifier   = new Token(TOK_IDENTIFIER, "<IDF>");

    public static final Token KeyDefault   = new Token(TOK_DEFAULT, "default");
    public static final Token KeyEntered   = new Token(TOK_ENTERED, "entered");
    public static final Token KeyExited    = new Token(TOK_EXITED , "exited");
    public static final Token KeyBasic     = new Token(TOK_BASIC  , "basic");
    public static final Token KeyEvent     = new Token(TOK_EVENT  , "event");
    public static final Token KeyFalse     = new Token(TOK_FALSE  , "false");
    public static final Token KeyUndef     = new Token(TOK_UNDEF  , "undef");
    public static final Token KeyFrom      = new Token(TOK_FROM   , "from");
    public static final Token KeyTrue      = new Token(TOK_TRUE   , "true");
    public static final Token KeyAnd       = new Token(TOK_AND    , "and");
    public static final Token KeyCon       = new Token(TOK_CON    , "con");
    public static final Token KeyEnd       = new Token(TOK_END    , "end");
    public static final Token KeyVar       = new Token(TOK_VAR    , "var");
    public static final Token KeyIn        = new Token(TOK_IN     , "in");
    public static final Token KeyDo        = new Token(TOK_DO     , "do");
    public static final Token KeyOn        = new Token(TOK_ON     , "on");
    public static final Token KeyOr        = new Token(TOK_OR     , "or");
    public static final Token KeyTo        = new Token(TOK_TO     , "to");
    public static final Token AndOp        = new Token(TOK_ANDOP  , "&");
    public static final Token OrOp         = new Token(TOK_OROP   , "|");
    public static final Token NotOp        = new Token(TOK_NOTOP  , "!");
    public static final Token Assign       = new Token(TOK_ASSIGN , "=");
    public static final Token Implies      = new Token(TOK_IMPLIES, "=>");
    public static final Token Equiv        = new Token(TOK_EQUIV  , "<=>");
    public static final Token Komma        = new Token(TOK_KOMMA  , ",");
    public static final Token Dot          = new Token(TOK_DOT    , ".");
    public static final Token LPar         = new Token(TOK_LPAR   , "(");
    public static final Token RPar         = new Token(TOK_RPAR   , ")");
      
    public static final Token token_list[] = {
        KeyDefault,
        KeyEntered,
        KeyExited,
        KeyBasic,
        KeyEvent,
        KeyFalse,
        KeyUndef,
        KeyFrom,
        KeyTrue,
        KeyAnd,
        KeyCon,
        KeyEnd,
        KeyVar,
        KeyIn,
        KeyDo,
        KeyOn,
        KeyOr,
        KeyTo,
        AndOp,
        OrOp,
        NotOp,
        Assign,
        Implies,
        Equiv,
        Komma,
        Dot,
        LPar,
        RPar
    };
    

    private int id;
    private String value;
    private int line;

    public Token (int i, String v, int l) {
        id = i;
        value = v;
        line = l;
    }

    public Token (int i, String v) {this(i,v,0);}

    public Token (int i) {this(i,null,0);}

    /**
     * Exaktes Clonen.
     */
    public Object clone() {
      return new Token(id,value,line);
    }

    /**
     * Clonen eines Tokens mit Setzen der Zeilennummer.
     */
    public Object clone(int l) {
      return new Token(id, value, l);
    }
 
    /**
     * Clonen eines Tokens mit Setzen von Wert und Zeilennummer.
     */
    public Token clone(String v, int l) {
      return new Token(id, v, l);
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
//      Revision 1.2  1998/12/15 18:11:37  swtech00
//      Towards new naming conventions for PEST2
//
//      Revision 1.1  1998/12/13 17:49:07  swtech20
//      Checkin für Baseline
//
//              
//----------------------------------------------------------------------
