package TESC1;

//import java.util.*;
import Absyn.*;
import java.io.*;

public class loadTESC {

	private FileInputStream is;		// InputStream
	private Statechart stchart;		// der aufubauende Statechart

// public-Methoden

	public loadTESC(FileInputStream is_) {
		// K-tor
		is = is_;
	}

	public Statechart getStatechart() {
		// initiert das Laden des Files und Aufbauen des StCharts
		
		return stchart;
	}

// private-Methoden
}


// am besten in eigene Files; habe ich jetzt gerade keine Lust zu

// benutzen wir jeweils FileInputStream oder packen wir das ganze in einen
// eigenen Stream??

// Parser muss einen anderen Stream (vom Scanner geliefert) benutzen, oder?
class Parser {

	private FileInputStream is;

// public-Methoden
	
	public Parser(FileInputStream is_) {
		is = is_;
	}
}


class Scanner {

	private FileInputStream is;

// public-Methoden
	
	public Scanner(FileInputStream is_) {
		is = is_;
	}
}
