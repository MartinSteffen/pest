package Util;

import java.lang.*;
import java.io.*;

/**
 * Klasse, die alle Schl�sselw�rter aufnimmt, die nicht in 
 * Bezeichnern erlaubt sind. Es wird eine Methode angeboten,
 * die Vorkommen von Schl�sselw�rtern in einem String �berpr�ft
 * <p>
 * @author Michael Suelzer, Christoph Schuette.
 * @version  $Id: Keyword.java,v 1.1 1998-12-13 17:33:14 swtech20 Exp $
 */   

public class Keyword {

    /*
     * Hier werden weitere Schl�sselw�rter eingetragen...
     */
    private static final int KeywordAnz = 16; 
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
	new String("to")
    };

    /**
     * Diese Routine pr�ft, ob der angegebene String ein Schl�sselwort ist.
     * @return Value of boolean.
     */
    public static boolean isReserved(String s) {
	for (int i=0; i<KeywordAnz; i++)  if (s.trim().equalsIgnoreCase(KeywordList[i])) return true;
        return false;
    }     

}  

//----------------------------------------------------------------------
//	Klasse mit Schl�sselw�rtern
//	---------------------------
//
//	$Log: not supported by cvs2svn $
//
//
//----------------------------------------------------------------------

