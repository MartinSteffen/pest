package util;

import java.lang.*;
import java.io.*;

/**
 * Klasse, die alle Schlüsselwörter aufnimmt, die nicht in 
 * Bezeichnern erlaubt sind. Es wird eine Methode angeboten,
 * die Vorkommen von Schlüsselwörtern in einem String überprüft
 * <p>
 * @author Michael Suelzer, Christoph Schuette.
 * @version  $Id: Keyword.java,v 1.4 1999-02-07 11:53:43 swtech20 Exp $
 */   

public class Keyword {

    /*
     * Hier werden weitere Schlüsselwörter eingetragen...
     * Muss noch zwischen PEST1/2 abgeglichen werden
     */
    private static final int KeywordAnz = 35; 
    private static final String KeywordList[] = {
	new String("default"),
	new String("basic"),
	new String("false"),
	new String("undef"),
	new String("from"),
	new String("true"),
	new String("and"),
	new String("con"),
	new String("end"),
	new String("entered"),
	new String("exited"),
	new String("in"),
	new String("do"),
	new String("on"),
	new String("or"),
	new String("to"),
        new String("transitions"),
	new String("bvars"),
	new String("events"),
	new String("("),
	new String(")"),
	new String(":"),
	new String(";"),
	new String("<=>"),
	new String("=>"),
	new String("&&"),
	new String("||"),
	new String(":="),
	new String("!"),
	new String("~"),
        new String("cons"),
        new String(","),
        new String("defcon"),  // U.S. defense condition
        new String("ref"),
        new String("as")
    };

    /**
     * Diese Routine prüft, ob der angegebene String ein Schlüsselwort ist.
     * @return Value of boolean.
     */
    public static boolean isReserved(String s) {
	for (int i=0; i<KeywordAnz; i++)  if (s.trim().equalsIgnoreCase(KeywordList[i])) return true;
        return false;
    }     

}  

//----------------------------------------------------------------------
//	Klasse mit Schlüsselwörtern
//	---------------------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1999/01/04 16:14:21  swtech13
//	Neue Keywords
//
//	Revision 1.2  1998/12/15 17:52:00  swtech00
//	Towards new naming conventions in PEST1
//
//	Revision 1.1  1998/12/13 17:33:14  swtech20
//	Initial revision
//
//
//
//----------------------------------------------------------------------

