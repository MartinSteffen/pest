package TESC1;

/**
 * Schnittstellenklasse fuer TESC1.
 * <p>
 * @author Michael Suelzer, Christoph Schuette (swtech20).
 * @version  $Id: TESCLoader.java,v 1.2 1998-12-05 18:47:27 swtech20 Exp $
 */   

import java.io.*;
import Absyn.*;

public class TESCLoader {

    private Statechart statechart = null;
    private TESCParser parser = null;
  

    public Statechart getStatechart(FileInputStream fis) {
 
	parser = new TESCParser();
	statechart = parser.parseStream(fis);
	
	return Example.getExample();
    }

    public int getErrorCount() {
	return parser.getErrorCount();
    }

    public String getErrorText(int error) {
	return parser.getErrorText(error);
    }

}
      
//----------------------------------------------------------------------
//	Schnittstelle zu TESC1
//	----------------------
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------

