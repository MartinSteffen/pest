package simu;

import java.lang.*;

/**
 * Hilfsklasse der Traceklasse
 * Stellt die Eintraege fuer die Traces zur Verfuegung
 * Form: 	Int Index		=	Index im Ausgangsvector
 *			string Name		= 	Name des Eintrages (um bei spaeteren Versionen auch ueberpruefen zu koennen,
 								ob die Eintraege und Indexe korrekt sind.
 */
public class TraceTab {
	int index;
	String name;
/**
 * TraceTab constructor 
 */
protected TraceTab(int arg1, String arg2) {
	//super();
	index = arg1;
	name = arg2;
}
}