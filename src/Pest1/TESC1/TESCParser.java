package TESC1;

import java.util.*;
import Absyn.*;
import java.io.*;



class TESCParser {

    private FileInputStream is;
    private Statechart stchart;
    private int errorCount = 0;   // Anzahl der aufgetretenen Fehler
    private Vector errorList;     // Fehlerliste

    // protected-Methoden für das Package
	
    protected TESCParser(FileInputStream is_) {
	is = is_;	
    }

    protected Statechart parse() {
	
	stchart = new Statechart(null,null,null,null); //Dummy

	return stchart;
    }

    protected int getErrorCount() {
	return errorCount;
    }

    /**
     * Vector beginnt bei 0, der erste Fehler soll aber mit 1 bezeichnet werden
     * Wie macht das PEST2.TESC1 ??
     */
    protected String getErrorText(int i) {
	return (String) ( i<=errorCount ? errorList.elementAt(i-1) : "Unknown Error (Index out of Range)." );
    }
}
