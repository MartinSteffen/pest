package TESC1;

class vTOKEN {
    
    static final int EOF      = -1;
    static final int DUMMY    = 0;

    // Schl�sselw�rter
    static final int BSTATE      = 1;
    static final int OSTATE      = 2;
    static final int ASTATE      = 3;
    static final int FROM        = 4;
    static final int ON          = 5;
    static final int TO          = 6;
    static final int DO          = 7;
    static final int END         = 8;
    static final int TRUE        = 9;
    static final int FALSE       = 10;
    static final int LPAR        = 11;
    static final int RPAR        = 12;
    static final int COLON       = 13;
    static final int SCOLON      = 14;
    static final int EVENTS      = 15;
    static final int COMMA       = 16;
    static final int AQUI        = 17;
    static final int IMPL        = 18;
    static final int AND         = 19;
    static final int OR          = 20;
    static final int IN          = 21;
    static final int ENTERED     = 22;
    static final int EXITED      = 23;

    static final int EMPTYEXP    = 24;
    static final int UNDEF       = 25;
    static final int TRANSITIONS = 26;
    static final int BVARS       = 27;
     
    static final int CONS        = 29;
    static final int BASSIGN     = 30;
    static final int NOT         = 31;

    // Nichtschl�sselw�rter
    static final int IDENT    = 40; 
}

/* vTOKEN
 * $Id: vTOKEN.java,v 1.1 1998-12-07 14:02:27 swtech13 Exp $
 * $Log: not supported by cvs2svn $
 */