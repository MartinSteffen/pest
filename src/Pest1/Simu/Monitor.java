package simu;

import java.awt.*;
import java.util.*;
import editor.*;

/**
 * New type, created from HH on INTREPID.
 */
public class Monitor implements java.awt.event.AdjustmentListener {
	private Panel ivjContentsPane = null;
	private Frame ivjFrame1 = null;
	private TextField ivjTextField1 = null;
	private TextField ivjTextField10 = null;
	private TextField ivjTextField11 = null;
	private TextField ivjTextField12 = null;
	private TextField ivjTextField2 = null;
	private TextField ivjTextField3 = null;
	private TextField ivjTextField4 = null;
	private TextField ivjTextField5 = null;
	private TextField ivjTextField6 = null;
	private TextField ivjTextField7 = null;
	private TextField ivjTextField8 = null;
	private TextField ivjTextField9 = null;
	private TextField ivjTextField13 = null;
	private TextField ivjTextField14 = null;
	private TextField ivjTextField15 = null;
	private TextField ivjTextField16 = null;
	private TextField ivjTextField17 = null;
	private TextField ivjTextField18 = null;
	private TextField ivjTextField19 = null;
	private TextField ivjTextField20 = null;
	private Scrollbar ivjScrollbar1 = null;
	private Scrollbar ivjScrollbar2 = null;
	private Label ivjLabel1 = null;
	private Scrollbar ivjScrollbar3 = null;
	private Button ivjButton1 = null;
	private DrawFrame ivjDrawFrame1 = null;
	private TextField ivjTextField0 = null;
	
	private Simu SimuObject;		// Das uebergebene Simu - Objekt
	private Trace traceObject;		// Das uebergebene Trace - Objekt
	private int maxTraceElement;	// Die Laenge der Vektoren des TraceVectors
	int maxbvList = 0;						// |bvList|
	int maxseList = 0;						// |seList|
	int maxLines = 0;						// Summe : |bvList| + |seList|
	int maxbvListCorr = 0;				// |bvList|-1
	int maxseListCorr = 0;				// |seList|-1
/**
 * Konstruktor
 *
 * a) Die übergebenen Objekte werden sichtbar gemacht.
 * b) Die Variablen für die Benutzung des mittleren Schiebers werden initialisiert.
 *		ebenso wie die Werte des mittleren Schiebers selbst, denn sie koennen sich
 *		nicht mehr verändern.
 *		Weiterhin wird der Maximalwert für den unteren Schieber initialisiert.
 */

public Monitor(Simu arg1, Trace arg2) {
	super();
	
	// a)
	SimuObject = arg1;
	traceObject = arg2;
	maxTraceElement = traceObject.cStates.size();
	
	initialize();
	// b)
	maxbvList = SimuObject.bvList.size();
	maxseList = SimuObject.seList.size();
	maxbvListCorr = maxbvList-1;		// Werden bei fillTextFileds() gebraucht:
	maxseListCorr = maxseList-1;
	maxLines = maxseList+maxbvList;
	getScrollbar1().setMaximum(maxLines);	
	getScrollbar2().setMaximum(maxTraceElement);
	getScrollbar2().setValue(0);
	// Nur zu debug-Zwecken : Aufruf der fillTextFields - Methode :
	//fillTextFields();
}
/**
 * Method to handle events for the AdjustmentListener interface.
 * @param e java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getScrollbar3()) ) {
		connEtoC1(e);
	}
	if ((e.getSource() == getScrollbar1()) ) {
		connEtoC2(e);
	}
	if ((e.getSource() == getScrollbar2()) ) {
		connEtoC3(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * Comment
 */
public void button1_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	// Schließen Button gedrückt
	
	return;
}
/**
 * connEtoC1:  (Scrollbar3.adjustment.adjustmentValueChanged(java.awt.event.AdjustmentEvent) --> Monitor.scrollbar3_AdjustmentValueChanged(Ljava.awt.event.AdjustmentEvent;)V)
 * @param arg1 java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.AdjustmentEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.scrollbar3_AdjustmentValueChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (Scrollbar1.adjustment.adjustmentValueChanged(java.awt.event.AdjustmentEvent) --> Monitor.scrollbar1_AdjustmentValueChanged(Ljava.awt.event.AdjustmentEvent;)V)
 * @param arg1 java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.AdjustmentEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.scrollbar1_AdjustmentValueChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC3:  (Scrollbar2.adjustment.adjustmentValueChanged(java.awt.event.AdjustmentEvent) --> Monitor.scrollbar2_AdjustmentValueChanged(Ljava.awt.event.AdjustmentEvent;)V)
 * @param arg1 java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC3(java.awt.event.AdjustmentEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.scrollbar2_AdjustmentValueChanged1();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 *  maxTraceElement : Die Laenge der Vektoren des TraceVectors
 */
void fillDrawFrameData() {
	
	int scBrValue2;
	int scBrValue1;
	int maxLoop;
	int yPos = 0;										// Schreibindex für die y-Position
	int xPos = 0;										// Schreibindex für die x-Position
	Vector seVec = new Vector();			// Auslesevektor
	Vector bvVec = new Vector();			// Auslesevektor
	boolean fill = true;								// ausgelesener boolean - Wert
	boolean notIn = true;						// zeigt an, ob man mehr lesen will, als vorhanden ist
	int seMax = 0;									// um 1 reduzierte Vektorlänge der seList;
	int bvMax = 0;									// um 1 reduzierte Vektorlänge der bvList;
	
	
	maxTraceElement = traceObject.cStates.size()-1;	// update, denn die Länge kann sich ja verändert haben.
	scBrValue1 =getScrollbar1().getValue();
	scBrValue2 =getScrollbar2().getValue();

	// stelle fest, ob überhaupt Events oder Bvars existieren :
	if (maxTraceElement > -1){
		if (((Vector)(((Vector)(traceObject.cEvents)).elementAt(maxTraceElement))).size() > 0){
			seMax = ((Vector)(((Vector)(traceObject.cEvents)).elementAt(maxTraceElement))).size();
			//System.out.println("Monitor : fillDrawFrameData() : Anzahl Events :  "+seMax);
		}
		else{
			//System.out.println("Monitor : fillDrawFrameData() : Es sind keine Events vorhanden ");
		}
		if (((Vector)(((Vector)(traceObject.cBvars)).elementAt(maxTraceElement))).size() > 0){
			bvMax = ((Vector)(((Vector)(traceObject.cBvars)).elementAt(maxTraceElement))).size();
			//System.out.println("Monitor : fillDrawFrameData() : Anzahl Bvars :  "+bvMax);
		}
		else{
			//System.out.println("Monitor : fillDrawFrameData() : Es sind keine Bvars vorhanden ");
		}
	}
	else{
		//System.out.println("Monitor : fillDrawFrameData() : Es sind weder Events noch Bvars vorhanden ");
		//System.out.println("                                                       denn der Trace hat die Länge NULL ! ");
	}
	seMax--;		// Korrektur vornehmen, da Vektoren bei "0" beginnen
	bvMax--;
	//System.out.println("Monitor : fillDrawFrameData() : seMax :"+seMax);
	//System.out.println("Monitor : fillDrawFrameData() : bvMax :"+bvMax);
	//System.out.println("Monitor : fillDrawFrameData() : maxTraceElement :"+maxTraceElement);
	
	maxLoop = scBrValue2 + 19;
	if (maxLoop  > maxTraceElement){
		maxLoop = maxTraceElement;
	}
	if (scBrValue2 > maxTraceElement){
		System.out.println("Monitor : fillDrawFrame : FATALER FEHLER !!!");
	}
	xPos = 0;
	
	//printOld();
	
	for (int i = scBrValue2; i <= maxLoop; i++){
		// Die einzutragenden Vectoren auslesen:
		seVec = (Vector)(traceObject.cEvents).elementAt(i);
		bvVec = (Vector)(traceObject.cBvars).elementAt(i);
		//System.out.println("Monitor : fillDrawFrame : Laß Vektoren Nr.: "+i);
		//SimuObject.printseList(seVec);
		// Die Daten in das Koordinatenarray eintragen :
		yPos = 0;

		for (int j = 0; j <= 19; j++){
			// Schleife die alle Ergebnisse des doppel - Vektors ausließt
			if ((scBrValue1+yPos) <= seMax){
				// es ist ein SEvent einzutragen
				//System.out.println("Monitor : fillDrawFrameData() : Suche ein Event");
				fill = ((SEventTab)(seVec.elementAt(scBrValue1 + yPos))).bWert;
				//if (fill){
				//	System.out.println("Monitor : fillDrawFrame : Habe einen positiven Event gefunden !");
				//}
				//else{
				//	System.out.println("Monitor : fillDrawFrame : Habe einen negativen Event gefunden !");
				//}
				notIn = false;
			}
			else{
				// es ist kein SEvent einzutragen
				if ((scBrValue1 + yPos - seMax -1) <= bvMax){
					// es ist eine Bvar einzutragen
					//System.out.println("Monitor : fillDrawFrameData() : Suche eine Bvar");
					fill = ((BvarTab)(bvVec.elementAt(scBrValue1 + yPos - seMax - 1))).bWert;
					//if (fill){
					//System.out.println("Monitor : fillDrawFrame : Habe eine positive Bvar gefunden !");
					//}
					//else{
					//	System.out.println("Monitor : fillDrawFrame : Habe eine negative Bvar gefunden !");
					//}
					notIn = false;
				}
				else{
					// es ist weder ein SEvent noch eine Bvar einzutragen
					//System.out.println("Monitor : fillDrawFrame : Habe nichts vorgefunden !");
					notIn = true;
				}
			}
			// nun das ermittelte Ergebnis ins Array eintragen :
			if (notIn){
				getDrawFrame1().coords[yPos][xPos] = -1;
				//System.out.println("Monitor : fillDrawFrame : Trage '----1' ein an yPos : "+yPos+"   xPos : "+xPos);
			}
			else{
				if (fill){
					getDrawFrame1().coords[yPos][xPos] = 1;
					//System.out.println("Monitor : fillDrawFrame : Trage '+++++1' ein an yPos : "+yPos+"   xPos : "+xPos);
				}
				else{
					getDrawFrame1().coords[yPos][xPos] = 0;
					//System.out.println("Monitor : fillDrawFrame : Trage '000000' ein an yPos : "+yPos+"   xPos : "+xPos);
				}
			}
			
			// nun noch die y-Position updaten ( für den nächsten
			// inneren Schleifendurchlauf
			yPos++;
		}
					
		xPos++;		// in die nächste Spalte wechseln
		//System.out.println("Monitor : fillDrawFrameData() : Wechsle in Spalte"+xPos);
	}
	
	// nun müssen ggf. die übrigen Spalten mit "-1" aufgefüllt werden
	//System.out.println("Monitor : fillDrawFrameData() : loesche Spalte Nr.: "+xPos+ " bis Nr.: 19");
	for (int help1 = xPos; help1 <= 19; help1++){
	//	System.out.println("Monitor : fillDrawFrameData() : loesche Spalte Nr.: "+help1);
		for (int help2 = 0; help2 <= 19; help2++){
			getDrawFrame1().coords[help2][help1] = -1;
		}
	}
	//System.out.println("Monitor : fillDrawFrameData() : Erfolgreich beendet !");
}
/**
 * Füllt die Textfelder in Abhängigkeit des Schiebers Eins.
 * Zuerst werden die Events dann erst die Bvars eingetragen.
 *
 *	maxbvList			: Laenge der bvList 
 *	maxseList			: Laenge der seList 
 *	maxbvListCorr	: Laenge der bvList ( um 1 nach unten korrigiert )
 *	maxseListCorr	: Laenge der seList ( um 1 nach unten korrigiert )
 */
void fillTextFields() {
	int ScBrValue1;
	int bvCounter = 0;
	int seCounter = 0;
	boolean event = true;
	boolean nextExists = true;
	String fill;
	ScBrValue1 = getScrollbar1().getValue();

	// Initialer Test
	//System.out.println("Monitor : fillTextFields() : Checkpoint 1");
	
	seCounter = ScBrValue1;
	//System.out.println("Monitor : fillTextFields() : ScBrValue1: "+ScBrValue1);
	//System.out.println("Monitor : fillTextFields() : maxbvList"+maxbvList);
	//System.out.println("Monitor : fillTextFields() : maxbvListCorr"+maxbvListCorr);
	//System.out.println("Monitor : fillTextFields() : maxseList"+maxseList);
	//System.out.println("Monitor : fillTextFields() : maxseListCorr"+maxseListCorr);

	if (seCounter <= maxseListCorr) { // Wenn es kein Event gibt, ist maxseListCorr = -1
		// es ist zuerst ein Event einzutragen
		event = true;
	}
	else {
		// Es gibt kein einzutragendes Event, oder der Scrollbar1 steht zu weit unten, als daß
		// ein Event einzutragen wäre.
		event = false;
		bvCounter = ScBrValue1 - maxseList;
		if (bvCounter <= maxbvListCorr) { // Wenn es kein Event gibt, ist maxbvListCorr = -1
			// es ist eine Bvar einzutragen
			nextExists = true;
		}
		else {
			// es ist gar nichts einzutragen
			nextExists = false;
		}
	}



	
	// Eintrag in textField1 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 2");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField1().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField1().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField1().setText("");
	}


	// Eintrag in textField2 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 3");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField2().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField2().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField2().setText("");
	}



	// Eintrag in textField3 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 4");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField3().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField3().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField3().setText("");
	}



	// Eintrag in textField4 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 5");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField4().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField4().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField4().setText("");
	}



	// Eintrag in textField5 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 6");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField5().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField5().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField5().setText("");
	}



	// Eintrag in textField6 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 7");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField6().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField6().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField6().setText("");
	}



	// Eintrag in textField7 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 8");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField7().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField7().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField7().setText("");
	}



	// Eintrag in textField8 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 9");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField8().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField8().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField8().setText("");
	}



	// Eintrag in textField9 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 10");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField9().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField9().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField9().setText("");
	}



	// Eintrag in textField10 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 11");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField10().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField10().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField10().setText("");
	}



	// Eintrag in textField11 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 12");
	//System.out.println("Monitor : fillTextFields() :nextExists : "+nextExists);
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField11().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				//System.out.println("Monitor : fillTextFields() : '// ja, es ist noch ein weiteres Event einzutragen' erreicht ");
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField11().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		//System.out.println("Monitor : fillTextFields() :'// es war nichts mehr einzutragen' erreicht !");
		getTextField11().setText("");
	}



	// Eintrag in textField12 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 13");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField12().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField12().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField12().setText("");
	}



	// Eintrag in textField13 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 14");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField13().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField13().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField13().setText("");
	}



	// Eintrag in textField14 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 15");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField14().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField14().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField14().setText("");
	}



	// Eintrag in textField15 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 16");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField15().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField15().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField15().setText("");
	}



	// Eintrag in textField16 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 17");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField16().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField16().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField16().setText("");
	}



	// Eintrag in textField17 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 18");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField17().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField17().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField17().setText("");
	}


	// Eintrag in textField18 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 19");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField18().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField18().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField18().setText("");
	}



	// Eintrag in textField19 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 20");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField19().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField19().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField19().setText("");
	}



	// Eintrag in textField20 vornehmen
	//System.out.println("Monitor : fillTextFields() : Checkpoint 21");
	
	if (nextExists) {
		// es ist noch mind. ein Element einzutragen
		if (event) {
			// es ist ein Event einzutragen
			fill = ((SEventTab) (SimuObject.seList.elementAt(seCounter))).bName.name;
			getTextField20().setText(fill);
			// Test, ob noch ein weiteres Event oder eine Bvar einzutragen ist

			seCounter++;
			if (seCounter <= maxseListCorr) {
				// ja, es ist noch ein weiteres Event einzutragen
				event = true;
			}
			else {
				// nein, es ist kein weiteres Event einzutragen
				event = false;
				bvCounter = 0;
				// Test, ob noch eine weitere Bvar einzutragen ist:
				if (bvCounter <= maxbvListCorr) {
					// ja, es ist noch eine weitere Bvar einzutragen
					nextExists = true;
				}
				else {
					// nein, es ist keine Bvar mehr einzutragen
					nextExists = false;
				}
			}
		}
		else {
			// es ist eine Bvar einzutragen
			fill = ((BvarTab) (SimuObject.bvList.elementAt(bvCounter))).bName.var;
			getTextField20().setText(fill);
			// Teste nun, ob noch eine weitere Bvar einzutragen ist
			bvCounter++;
			if (bvCounter <= maxbvListCorr) {
				// ja, es ist noch eine weitere Bvar einzutragen
				nextExists = true;
			}
			else {
				// nein, es ist keine Bvar mehr einzutragen
				nextExists = false;
			}
		}
	}
	else {
		// es war nichts mehr einzutragen
		getTextField20().setText("");
	}


	
}
/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G5B3CD7A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E134DB8DF094C7F616ADD71DFC4779F4314E16FD58451515AAA5079DDC41314EA6E7F2A1B64EE11B1CF182A9AE51B91C8F1FE5C7C9119CD507FD33C82085A4A4A1E1A4A301847A576A1F9FA184569FC2BF8B48C6861993472E24C1DA3C3B336C4E02B060643D69F93DB32B194558D8D52FE67BFB7D3ADF3FFE7D7AF54FC8485FBDE703E5FD3EA0D8B6886A5FC10BA0CCCD8842444EADAF91E6CD17AC02662F
	94A8D7C86CCC07E7817062A7A5CB0E50AADB21EC0332954ABB60F71E902CD8BA86BFCC7AC9841E652EA385117BB94D7B49D04A296ECCF8EE85EA84EC98E8F6BA76EF75942878B414372A3F11AC6BC12F8DD82770C40186CFD66B89404B87CAD26A6C75045711810F811A8334C951ACEBDB2B4ADF5A14A80C9C4D0A4E42FEB4F2C442C656C7197A94BADE3F9B0D10A6346E5A864F76214FA60F86DC1EE0DB5B89496B12A53FA044EDC4BD86CED815FA02B08A65128C44960F605817C3B995481AC17A3C3027DD6DEF
	56C37385E144DC1F098C26C7CCC6C81F589F2F3030BE7BBD5467628C6AB3511329CC0EE4A9GEA3492EE69C73C8F4A8E20E9AB6972C6856912A44D97C67FE42ECB2C1569B05F9A52256366CACB21B24F7F5E0573108A3C7CCD12258A488E64861A8534D3712F07BBEB6059B9A6F7CB9E0F646D74793A5C937E93520468065FEDED40D1310F45F1498FFE4DFC6813911B01BEC4891B747E034446F6D620F96F09FDC2081BDF963D41E0EC79A773231354B616EC8A0DADFDB35A2D7DD334DB8A60A8077AB03CCF417B
	B607702AAD083F2C4CB9169D40DB34196CFC4FF552E5787AE3C2727D455199C63A3C3DA75A3D0569F04F1610AE4C46BF69C19B4F83FC8950AA20F5C0C5191225BD13EC3C2A3365D6B66E774A225F6BF41F8E0E791CFE39AB68F2CF08FE5556A7BDE6369E491CE9672D5C9EA73DB8EEF7A63347860EFF2A60D19A1C59698945CF319C873C35C02CFED07153512CF0FB5F2560F2D6383DB37A495E3300178A44EC353B97EDB53CD5327800E20066GAD84DA3915EC55505570A57DF1C8E3A7A63B3A6BFDF80A53325F
	596F8D48CE6F38F84669F7B9475CE2C0D06B7CD7976AB526137BDFE51D0CC39F47C52F68F74AAE498B2DBBBDA2CCCA005579C3D7AE56710B8AF758659121830FCF20F6B33A7350564E007C09EF42A90B219A0C1F5D0DBEF745F986DA04866037AD9BED659E41F8318E46DF10CDF66ABD0E188F4AB159E417EFF7E32C9B84A7915DEDEDBD2EF1D443693FC66B31CB717D59D05F1E83BE18C3BE7E4E8879F8D45163420257369B7A787C5FEF0C1E154BFCA9A1B764632A7FA9EDAF81BC85A8F39B6DA34BC6C6A244G
	A4544388C7BD69375D248E6B636DDB7611E10227E9FEEFB02943EC1458220DC90EFD8506B61AFBFFFE34ED9B5335E7DBF89CF82B8FEDB481F894501CEDB4EF7F50DD427C394B99904903CF794179440950AC323ABF6B49C67F3DA2FA659E51A7FAA760C7C0751F97FBB605FC7B22447D6E95854718518D4E976078EBBD398C07B634AB05783FD478CED9763B460232F8DA14E517F7B2A47FEBC51700AE9C85D41F9515BDAE894638147B42309731D4A8276791F6C1414A72347566FB4C66B3F55BCCBF4036A24D3D
	314F3CE45AC71A81E6669B117B382E44B80B5A66860EFF0B6D6920378FA81E5BA22BFFA42C7E6D747965F42AD07A506AF3A77BE0FED155EB6E238D3D46BE3A3F5758C78F77C676416EDEA39FE43C01DE0C07631257ABAA0B083385169B3EB30CBA3BA09FBC6B1A189465532E4FC5C1D81D077E9B2D445C0570FBD99E45492BFBA986D49D7B8B21314EEA1817E44614C75B7219CFF66407E2G53E970B84E3D6896BDE014EEA96815997E11924FD5BC1026E0E50DBF02E7C95569F13F64F60FB97DADB6674425E0C0
	46BA75211FE71C6E2058BD6574CE0A93D5171CD71C754E2BF23DE27F7ACEDE6B98162BCB6A6A0E1628B930AD409B3E50E95C1ABDF2F3072B4FD4B72A7E14A73C6A895337691B523769EB685B4475FD60505DFAFD1B3FA9FD1BCD74ED2A82051A85016D8F8D83689B8E709999E8D6BE6D8FAF9DC7BE6D6B6A46508DADB33786DEEA3E12EBFD1A2F714304FCF6EE5B4A790F5F023F42144F742B9BC47DDA37CB16A1A047F652EFE3DF1EC6BF3BA4FD96748DE3C6C579D4DF8E2ED171F2D004F48B990362B5B5E7DA5B
	37AD0C27580767E2C27F96F83AB5ED3235BB4F09384F6F724056081B17BA069B9E6D98E450114E29EC4EB3029B61590B09DB934501E73A7214B840526BA1CD1B6CBC7BFD2F762C9A8FEDA6812D84CA85CA5F01F51E69AFD2795677616C865481948BB47FFD56BF2B7748A0559B0272540ED03D189D5AFA9F59295EF2A827A2D6406AB98AD83D38822C7770881DA59BB8762402BD8883FDAE1D721793B95A333470C229E16CD27AD35BCFA9884FDF9CF6ECB733904EAE05B477577B35F35F9988C863AEA57D2538BE
	200DEB8CEB9B588C4F0BAEFFG4EB49362B44DF547G6A0A79318A2B7373635AA3424CFC7F58842EB1CA173A2FAA6BEFD86A06943D8F5C251B077DB3AE712AC0F6F87D1C768CAFF7E5C5EFB131C3E3A19B7FD02166EC8967E626EB4B20F19CBFB40877A81FF17FBEB2087EF6C575E9B69E31934FA04A1D0492CD5C908BE0DD0FDD82FD294ECF7272F5F5A0623435A9EE4B6ECF66FA0D6ED8E2D5DD59BD43C3925A9E777844C23ACF78019F75CE0672DA201C8F604C2F1C477E231F4EF5D500B90058FAFA21DA3B1E
	6281DFG148C44565340C0A7BC3FF65804B65838FC836163DA83FDD9F732FBA1E61B8CD78F460CBB30F9B11F48585C217697A5BCA1AC3CDC991DED609331FF3FB53AE4A733EF5F4E10CF88CF2D304CCBDA215A7179C1FEBE06BACBF6129DEB4A0D6C18DE444734DDB7262F3141B1BDBE37139E855047D0916951DBE924C7CC914D672F6CF7EABE59386E6BAB570EAB52C648576859E6ED2CDA7D17B92631EAF6911B07F9C561312A67A47A7E9240130BA8D6BDBD182B09D59F43EA033EE1C72178A35A53585AD54B
	AE7B86DA3B826D01F71DC27B2D01B6F30AE16DGF5GB90A290FD47BAD6FF10ECD9B5E63307579823BF7017662016695535960D1A97CFEE7F1F1685C3332D8FF86CAAD26FB19CD81ECAFFD973BFF4171B1BCCF4173B5B8731B9FAA71814BE5400B59C57D3F599F5EFF4AAEFD1F565D7AB2137B5D8C39DDE1E5DB189C731BCB1F964C0CFFBCD1E37B72C0C09BABFA209DBCC7CE40F39A084540772A2D0A9E30FEB9760032A76138576C263838C8493DECA5122587E834046ED97EE58877E1994A31C009C00B00D683
	2517427C17523CFFF842E80F524F3B598695BAF7B31BAD997CEA367E5799F261366EAB554BB1FBB84BE91FF01452FCC7593F1A8E774D102B8FAB479B6AF03392F5403DF8DEA959FE4747082D0672DAEE6759A732B4EBF8C8022C0D2F5F7E133857CAFE57244BCB58C9859B17203E1F30FBCF191FCFE3CF991FCF9FB8E5FCBE1DFDAA729DCA42A93373ABDB0707A574BB6B1E7075F9CC418B76042F4F173C34BE2B00B78B085D37BC256C7529D0CE5FCB58BA1FF2378265F60E957B91F3C0D95ECBF31D9D401CA50A
	178B15B26E8B2C7CA37F2D6E573EACE19F61986B6319BB5607517DCD31023F78FE36B2D7010098D096E30FD3F62A3C5F485B85033B021310F089F4F6A8D172D32CF3CC163A719C49E2550AAA03DCED48899E4AFC1B3D33433A71FBA95EC487F19E17C2F9B510D0C636CE2BA1BB9414995FEB59G6FB061A18D83EF8A6886D02C26DE826F630783115E4B98917349D6A537C9AA8B7755AE85DFDC966E2B7FB8C03E3A9CF86D653406FFB5E43E7ED68F992F3FF706F05D872420FFDC8CDFE37FB3D440F921285AAF0B
	9E1CBED667BDC5DEF67AA74599EBF443C7851CE7A63F75A333F54CFCA34D2E750DE66E57974BA359FA8E58E4BE50627250BC31761E563557425B7BEEA5353732FC268F248216DEA1D98AAA984FD6C1B6AF7A9867E61A0F39C4A9DF636DF6CA3866D3799D6C20D2AE63F7132FFAEF67CE76F6097511E25A07511A7EAAF1A1CFD8A261FE60F6DE130230F1C4D5A8E79FC91B93F728361B2A205C7C492B38AFDD044397CC79E91FB8AE0CD6AAB954E54ABF96C35D15C06B000A0057DEC97974BEBF36A13BE4A54FCC28
	44F76FE74F89DC1F7B4F629A812F13F191E050E036B8A2077B4D6A4A101D57D54E34B9DB0B3F3B026D65D43135F81063BB2FA29E2341599A3DD709C3D8962A61ACD0C37BC4F98D462CE99B64CCB642FC8A168865C59CFBD64116C3B905E369350889F512A53F0E30938AD68565F60EBDD0CB7B5390471E36A1A6C3B916E36B6B90CB288B3DC34C950E8E6A7D35EFD06F2FCB873EF9FFFD53340FBB612FEC6CB74B49C68B38BD9E2DC0AC994A6BB87626026554C3391E3084A5276A0072A8477E33125A73F1AC271A
	E463B8B6DCCDFDA4F2ACDE419654075E892789EF54E3BC895B7B3AA43C233433C546763BBF7A504F1601EC8AD0FA835D295F9B247B187643CF8AA933379A5E474C7DEDD9F4D1831B37760650D9EF7866CAB527BFF419F27AE9604FEBC0EC7232F6DD631C9C594072D146FFC84747785EADE879BF5771D15F06F4AD7FDD9D9F6DF1CD15E7E37B6D7BFCEC6F7E35E07B27CC53EFB4562AE3AAEAE44FD57F1CF037EF62172A0DF61C531E07BB527223ADA66D8C2B720E46100D547914A9BF0CEB2479EC8D12AF4CEF24
	F5736E95B74EBB14578265B44199872883E88448879483B487A889E8A950EAA02199769C20C620BEA08750B4D0ACD0A250C22065C029C05696480100768159DB285F522B582F834AD1C0F3009682A583897B20BED0871083A88AE88E50C220E4A0E1BF7001BA009CC0D1C0F300966EE7772BF8F70A773149D0968E40EF200E83546F770273ACB36D086341A70EFF3AB994AFF04CB886545FE84DAEEC0E9CDB5016CAACEFB22FD790218D2BC93F8E0D4CBE13BAE6949B21BF74811CFB3B5AE60C095ECBD5BC719663
	C73FD92E7A8916FDE16D0D98345F27E9B3D975EBB6F76F994E5DCEA194234BB5B86677F8C75EE222DB6DAD6C34C9FD96C628D39C0157E4026F557C5EFF9B7306D49901077188E369C1351CEBD2EF077A2C3EC5DFF84FDF8734DBADE70771338CE4CAB53F53B44FBC213D866B873DAE395FBB6ED7EE51519F8EE0DC7A24064ECB0E8314C7FD2B86E5475C5278E79A81D6FF8E2FBF1F577FC50D766E0E55FB33067608C53C5E4EDA74930FF35A6589FA54FA4DCADEF28AE8992FB7416A393C1AFA57EB293DD5D0E6B9
	61633514932E814CFA9062A7D09F5054C1EA6BE59B0E7D0AAB609AF30B1D9EFCF940F57C0D0D5E6744707A05F5B80F125F85E3E66FD6D8DFF16554D7BC541D8B3484A885A83315E41F2AB0375B4BCA4E0073EE637505CAF35D8AAB29FEBB2F7FEC2579BCAE2B243158DB49BE9B2BC867D140FCC031C0730016E2D911BBD4CD7D24F26CA64704C35477DFD5075B05653A2FA839C64EA11627A0B1FC390297E9F01683BBFD741DC69570E69D227B276CD15C5FD3211C59A6D94A005A01C65B68CC7568B9FC779D714E
	EFD8725961206B56DC8D29796B655BBD0333DC6003D24AA92626668A334EE71B3E3F1AEEE331B2DEFD96A5A65CCD71ABD718FD9E67E1B170D681D98F431E86348F48FE18467746B99B9B9F3EEA6B14E567781472960F8607AF56C2B09E0CD85B4F7D89B5E5ED587E08B1780C731261DB3B81C89959FB2FE16724C067311B32B62FF29CD65FE941F361E1641F8A16C430535C435A773D9BAA71DD6330B8AD773AC477C493614B376970E642570D687096421B33F478BE42B7FAF578FE42BF8F687083043FBB20438F
	92BEEB238EEFA53C522D438F91EE9B58229BD7230A471F5563CD94EFDB74F8330ADF8F6A719695BFF5D30F6FD371AFA4BD3EDF45EB3DFA7C000A0FFB7478C1956F6A53632DAA7E30DB0F9FD2F15F859D5ECC630D1550BE21AFCE48BECFCC69F0BE3F977FCF077379351C505939117073A7703E406E9C935D3C6D5F57E05D9E3F73AAFDC720565D05EB3BABA84B1237C97D9E41FDD56B4B56F6763E4D56CE6F634EDEB362F7F07EF7EF987107B9FF648BA37E94676F950C78B7B87F9D0B91BF16733F3D51081F4079
	1FD80D78C91CDF3259083F04735F5AE244CF667C3F49B2622F617C3B320D7856A34437679A710BB8FFC71E913F1173DF4FB76277F0FECB1191FF04735FABB6623BB97F455DC67CA84E0FABB56247F17E053DC67CC44EEF2AB362AF607C0B8DDA7E52A30C1F4A799FB5E97969C7993F6CA871530EEB792DAAFF74A86D516E9A5C1B70BE146DDF89135A336C72CD79266FF6FDC7556F69556754E389F72BF7233CCDB5E72945F268BB347E5A7570EC6131724FB81F5D953C4A733685C7692E40E28B3FAB40DC3CDE20
	33FE0B0E0F39F4310EAF6A78B8569F2C57729F5471F14CF8082F67BA3FE7C3FD1A380E4F1473F714DC47556561BA1E555DC7FCD0912E23CB27233DA2DCC77D983F2888577102CE47DFD720DD1BF9ECEC2C54175F2A540F41D6C5E330FDC8E3B8D795594E332A4247700AEE0CBF2D8A9F4327BABB27D6050F61B15D98062A75BA4771B24B4BDE434F75555F7F2679BD693BD36F270CC8FD677A0D764172319D23666FDEF60D9A3FFBA99BB5FE77D9B39A795B5C66D123EFF3D90E186740F91AG3F0882CAGCA82DA
	72A1651EBF9B5DA3A83927C15EF9ECE2925F7360FFEF4854571F1FB79F575C73377E1F0F274E631EBEA6415E6D417689FF76BC4E0DAC799410E18B4F23BD7C2E49A919D5D571674F233F3B450B8A4C6EC7A777E07E2B5C0F0ECB1EFA571827BEF845D36F9C136BC5C8055B5ACE865DEE7526D4D06FCA9F1471BC329AECA1F4C816028E9ECB15EF973BC57CD0116269F74A68BC30E67FDCE1D9755E68DC4164FB368E96C7C7BBC2F70334C65204BFECB7785E0227C3ECBDBFF6154E59319D74AD45FD5788DB4C31C5
	B78893BA897B5E970435F26C1CC0180FE3656B89CB64585FEDA4AC19E3332C0495F491F6F6B3E1C39C5B13C9D88C475E4EA2ECA147123289DB4B3168DC429A3B89BB19C75894478A3793B607E3170A88DB4E317D3B884B69A1CCA8A12C0FE3BFAFA5AC0AE38F6CA5AC09E3D3F5745DC6EA8F3D2BDA315360DD34123EB219C79A281D2AE3A4B3B244E5587FC87254164944B4716FC3384CAF5A364F6C07253E82457F36637C3D77B1BAD3FF470958D2A8279C23355EF9816F5E8C4F08G8E79A51F68172FB1D479E8
	CC0D174BEBEF778C4D641E0B78CE56DC2E296AF6655878F6BA4CE3539E472DE3D32DC3790696E24A70B51F7ABE3A5121FC6BF271E2C064B1F03FA307E37D01A1A96042CB91F67731C5F94F17599BFEA7122F60853D61F7A20F48F4A7D2863C18DE1A3F1F0E1540B3854A5663F00E816A819A8172815D83B47BB84D6B8B0E2A88734ADE92A9F15C6FF4F9E5E663442BC6B6CEBA2E7E1F67F1336FC76E3F96E9EE12C1EECD18AC13FB71C6A4B9EB9F1C957A0C64623E08A45788B2BD06F29704C8F2A3A063B6142BD9
	9FC9AE8AE4628C651E5F98C9AE91E496984AFD5F9AC9EEA9482CB2149B5D9CC9EEAD48E4769B49D5E6C612AB8119FD06F27F1D95C92E8FE4C68D657EB6BB121C8FE46EB1140B490DA4978FB2F343647E1F36338F0A23BE6338E998450AAFB54C989BBA2115EAD4BA25B1F548C8ABD17E5011BFE84596C79A131934A111B43162B4FFB04E9E109CAF879CF0C4C76F60000BD5EBF89913995A4684E88C27908FB888A44140113BG49818A079CF8C01F3D3B67893BFB7BF466E61C49F742FD77796EFE163D6C6E6FFE
	4FDE82B9FB9517CB06CCFA48DCCDB5174B02CCEE485C0C114B693F00B15C9721F27F2E61F22D10698E19BBE266F2231071044C2555F139C84844044CEDEA60F28910C989191BBA91AA17912C4D063F052ED0391FB5054A9902359FF4291D176EEED163BC8D199E10B364764EF6F27B67DD13098A7E9C6CDFF408BF53BDF960F299DC7600C33C4E0B4F3BCDBA5C13CB7E0F4C6DD0B8BD9E58E6453CC05BFFF74D4F8FF93E7F31CF52773A0D607331E36A576134317FFF9DFEF9AC74986155B16598E14FD868B1427E
	3135E30400F7B0385ED9EAB88C76350D09F3AB01DE258B0EE947383FE7ADB0F77F347FDEBC960EFDC2974E67DB3C54ABC25EE30B686DA22F45FFFFDE508DE30BEE74F679306EE372B4ABD857C35E63ABD867A4AFF5956BA2497BD6403A98729E50E0DDB2F96DF9D817CE5ED8BE569DA66FCFC731CEF7813D3734D857CC5E16C22CBBC75E73C5D8B7C15EDCB15679480BAE413AD8724E6828C7023C11D22C4BA02F288C6B32497B289C6B74DF2217DE0EF596725E54E31D153C4D95D8B7CCDEF2A5565DC65E0DAA2C
	0BA66FAE8356A512F72A9A6BD2494BFC8F6B8E11F73B916B8AEC68BDE2423A8F486B2D215EE272E6EB31EE14BC23996BBC64FDDA07F59B495BDB07F5716495B6E0DD9AF9DB9B30EE8FF9AFDA302E30873D887F5848A83EA6EF5F894C3612D76C9F8F8D436B2F495B6E77A260F5B4F9B54D180DA5AF4EBFAECA015719648D5A306E90F96BFBC44F508B2FFB513B5809F5ED64ADD8C54F892F3D3DB8667F43025857660193D320F77087CE713C9C18CB7D0F9767D2A320FEA3A8911481BA5C0B732DBB9631661DBE58
	8E289B6486C57561B62C3E4078E252A31E6E82732DA3BE7C6CAE3E8F5775F79F2EAB993CF4D08E48E007FBB3BB4E53FE340239FEBB661ED9415C84F8BED08CA88914C939B7D7B117CB393B854C697B615F8B689C48890A6847DC3286F3F17D189B4A43DCA2F82920EC10EEG76FFGF3F765E32EFBGF3B9C7B1B78A1E87948D5A86CA275CBF8A69F3D94ABDDD0839420BB0DEG1D868D03FC97B17737A24C45DC445C8F0BB117GDE8AE88F28E09076F590F33FA9415C39C14C8D68B0B7841E9B94850A8725D2AE
	3294F35914B317E1CE98026386BA896A87F906B0E7AF47DC749066DEAB475C967012C01920DC10719266EAAAB0F77A92661E2C40DC8FF8CED084A88E14C239FD1554C3C139F5D518BB8C1E6EB29CB7D0B748FD99F35B8C180B3A0C396EEA4C4502378D148E4A81992EE06E0891F3A72FE06EA7C64CD9C58F6483450012A817E642DCA665A6EAB0F7883C02E1B8EE20F3A067B066B655E2AEE298F31F1829EF833CF8D0AAA89B247B8AF3576B6833732FB077D69D665A406B87F9C05120ED14EBE940DCBA651EEF40
	DC96F839A063D5F80F02062FE2AE5702B95FD5BCAFECBAA1BD776CF05DBA77880D384E7B20BE8E14825A83AA9841FCDD13723C525A045B31D0DDDA13F4BB42782D6D1CEA466D34C2FDB7488D0A8245D37E29967F7D13635A4D71591979E9A7761F3C68BFBF27C2DD46887EAE26ED74EFE0945E97201320FE10E7945777639E7F7349B333AE697A367A4F6351D097BB0A6B1B6A44752502378BA4B8E6B4E6D01B8357F70FD57C8C4CADFE88A7E08F7848040890FC7E4A0B3F13D1874E135C6B6567C9F6F879391EB7
	3C72F91237B5EB179FDF144F13FC9A4C871876AEA253C6E22ADE6219BA1764CCBF15ACFF75BB1E294227469478BDFE6B933D6B8E771C0BB8CD4B4819C61CDD4BBC6772321C73E54972DFAE731C3B17F94E7695A5E796DD978B47107358AA4FE9DD15F3D6C8B816D6F94E78D51ED3A7A8B91F9010330DB8F78BBCE715C64E79390463B38D4F392061B97F28D1F22E64D13FB7F14667711CBB73641C6BA49C7AFC1E33A31F671CBB2A64FCD70B1C8944E955721CCBDAB967F669F155721CBB35BC6719C2A5671F693E
	A60BB82B0AF84E4F0A641C2FCB0FEB914F79E8914FD9D42C64CCA8C6CE73B5645CD94CF36ACB641C9FCA660CBBCAF8CECF894F19DE22647CCE071CC34479280E67FCC5A767F4C8B8CBCBF94E3325BC6766D2A56779B2643C0FB8BDE5BC6743E5F24E87A51C99E5BCE7F1B94FF923DC49D921C74E6708732C1E677CC6AF677C1D0473A1BD4F79121E67BCD521644C247BE041091C4515BC6769CAB9E7D17E5A655315BCE7ECA54FA9D4A9B99F29C24ED662FC290A67549A641C6D924EFF99F84E1B861E73850312F3
	369AB93FA64E58EA1E737755F24EC589E741B11EB369BD1E53E8D4F2BECB630AAD44793A1167ECB049B9FFA5619CB0711CF718F84E3DA6A567BDB548390BB813EAF84E3F5648B977C9B84D35BCE7DFAD4F3935D649F9490C1C6F3810738EB34F395DAC672C13748E3DE666B9CDF5BC67F21D1253D20F1CBD4459D74FF3C6544BB9AFC9B81F2C67B9336AF9CEEB031273A00D8BA30953E461B9ED96B9670FA41C6BACBC6793961E336A3812737DC664CCA64E7EC61E7376C6B92734B7AF31D14EA99D176CEF14F37E
	C242E9EBD2F2DEE9C64E4344F9E7B34F79D4331C33C1327CC0331CD3BA262BED11F3627A83E34A07EF8844FC41A64EA1503774589767BC2E297F4E38E7E69B97A6271DB38B417D083661FEB4DF477DF846A65F8F697BB347A65F8FA9677BBDFC0F5E359E79FEE00FDEE0BF363BEF6D47DF4E1B5476E34AB5BDAFF6A851B15950097BB2C17B322313BFA6EFF44A77C55AEFF85C4A3F47872DEA6F7160737F5E70FACD3259FE8675DC7DF2F83983ECBF03FAAE116DE7D04F2DE77B9954F3355A70F27B59FE86755C2F
	59FE86755CEDECBF03FA2E2BAC3C5CB1FDF8395DECBF03FAAE1E6DE7D04FAD307D8C6A396758FE86755C86361FC1BDF7156DE7D04FFDD89FDEAE076DE7D04F7D33B13C5CE8F3F83987ED6165B6327D8C2194783FAD4E37095F519AFFC0B8629134416FD505B33E7F4F51AF672726DC703258FEED42BD69FAF633EBFAEED6B0043ADA1CD99975B47EF6C3A4DC083C33C299157897EAFE7E76FA7D3D1F4C67891A4F05A37E07C63DE26F37F66E9BE1859F248D745BC69EDF7B7CC27F7DE52A4FAFE40E873FDBF5DC7E
	5DF41BBB446B544793B39A6B84764A9DEC971F2DD5FBCE4EBE65F03BBD8E774D76E927784CDC472247F52B4FBA2EC3DC7F9C5684978616BD5EA1F25FD813795FGD0CB8788B04DE9630F9EGG10E4GGD0CB818294G94G88G88G5B3CD7A6B04DE9630F9EGG10E4GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG499EGGGG
**end of data**/
}
/**
 * Return the Button1 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton1() {
	if (ivjButton1 == null) {
		try {
			ivjButton1 = new java.awt.Button();
			ivjButton1.setName("Button1");
			ivjButton1.setBounds(30, 615, 139, 26);
			ivjButton1.setLabel("Schließen");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton1;
}
/**
 * Return the ContentsPane property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Panel getContentsPane() {
	if (ivjContentsPane == null) {
		try {
			ivjContentsPane = new java.awt.Panel();
			ivjContentsPane.setName("ContentsPane");
			ivjContentsPane.setLayout(null);
			getContentsPane().add(getTextField1(), getTextField1().getName());
			getContentsPane().add(getTextField2(), getTextField2().getName());
			getContentsPane().add(getTextField3(), getTextField3().getName());
			getContentsPane().add(getTextField4(), getTextField4().getName());
			getContentsPane().add(getTextField5(), getTextField5().getName());
			getContentsPane().add(getTextField6(), getTextField6().getName());
			getContentsPane().add(getTextField7(), getTextField7().getName());
			getContentsPane().add(getTextField8(), getTextField8().getName());
			getContentsPane().add(getTextField9(), getTextField9().getName());
			getContentsPane().add(getTextField10(), getTextField10().getName());
			getContentsPane().add(getTextField11(), getTextField11().getName());
			getContentsPane().add(getTextField12(), getTextField12().getName());
			getContentsPane().add(getTextField13(), getTextField13().getName());
			getContentsPane().add(getTextField14(), getTextField14().getName());
			getContentsPane().add(getTextField15(), getTextField15().getName());
			getContentsPane().add(getTextField16(), getTextField16().getName());
			getContentsPane().add(getTextField17(), getTextField17().getName());
			getContentsPane().add(getTextField18(), getTextField18().getName());
			getContentsPane().add(getTextField19(), getTextField19().getName());
			getContentsPane().add(getTextField20(), getTextField20().getName());
			getContentsPane().add(getScrollbar1(), getScrollbar1().getName());
			getContentsPane().add(getScrollbar2(), getScrollbar2().getName());
			getContentsPane().add(getScrollbar3(), getScrollbar3().getName());
			getContentsPane().add(getTextField0(), getTextField0().getName());
			getContentsPane().add(getLabel1(), getLabel1().getName());
			getContentsPane().add(getDrawFrame1(), getDrawFrame1().getName());
			getContentsPane().add(getButton1(), getButton1().getName());
			// user code begin {1}
			// System.out.println("Monitor getContentsPane() : Habe eines erzeugt !");	
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjContentsPane;
}
/**
 * Return the DrawFrame1 property value.
 * @return simu.DrawFrame
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */

DrawFrame getDrawFrame1() {
	if (ivjDrawFrame1 == null) {
		try {
			ivjDrawFrame1 = new simu.DrawFrame(this);
			ivjDrawFrame1.setName("DrawFrame1");
			ivjDrawFrame1.setBackground(java.awt.SystemColor.window);
			ivjDrawFrame1.setBounds(209, 52, 300, 556);
			ivjDrawFrame1.setForeground(java.awt.SystemColor.menuText);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjDrawFrame1;
}
/**
 * Return the Frame1 property value.
 * @return java.awt.Frame
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */

Frame getFrame1() {
	if (ivjFrame1 == null) {
		try {
			ivjFrame1 = new java.awt.Frame();
			ivjFrame1.setName("Frame1");
			ivjFrame1.setLayout(new java.awt.BorderLayout());
			ivjFrame1.setBounds(60, 33, 535, 670);
			ivjFrame1.setTitle("Monitor");
			getFrame1().add(getContentsPane(), "Center");
			// user code begin {1}
			// System.out.println("Monitor: getFrame1() : Habe eines erzeugt !");	
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjFrame1;
}
/**
 * Return the Label1 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Label getLabel1() {
	if (ivjLabel1 == null) {
		try {
			ivjLabel1 = new java.awt.Label();
			ivjLabel1.setName("Label1");
			ivjLabel1.setText("Nummer");
			ivjLabel1.setBounds(26, 17, 52, 26);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjLabel1;
}
/**
 * Return the Scrollbar1 property value.
 * @return java.awt.Scrollbar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Scrollbar getScrollbar1() {
	if (ivjScrollbar1 == null) {
		try {
			ivjScrollbar1 = new java.awt.Scrollbar();
			ivjScrollbar1.setName("Scrollbar1");
			ivjScrollbar1.setBounds(175, 52, 26, 555);
			ivjScrollbar1.setBlockIncrement(20);
			ivjScrollbar1.setVisibleAmount(20);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjScrollbar1;
}
/**
 * Return the Scrollbar2 property value.
 * @return java.awt.Scrollbar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
Scrollbar getScrollbar2() {
	if (ivjScrollbar2 == null) {
		try {
			ivjScrollbar2 = new java.awt.Scrollbar();
			ivjScrollbar2.setName("Scrollbar2");
			ivjScrollbar2.setUnitIncrement(1);
			ivjScrollbar2.setBounds(209, 615, 300, 26);
			ivjScrollbar2.setVisibleAmount(20);
			ivjScrollbar2.setBlockIncrement(20);
			ivjScrollbar2.setOrientation(java.awt.Scrollbar.HORIZONTAL);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjScrollbar2;
}
/**
 * Return the Scrollbar21 property value.
 * @return java.awt.Scrollbar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
Scrollbar getScrollbar3() {
	if (ivjScrollbar3 == null) {
		try {
			ivjScrollbar3 = new java.awt.Scrollbar();
			ivjScrollbar3.setName("Scrollbar3");
			ivjScrollbar3.setUnitIncrement(2);
			ivjScrollbar3.setBlockIncrement(2);
			ivjScrollbar3.setValue(0);
			ivjScrollbar3.setMaximum(39);
			ivjScrollbar3.setMinimum(0);
			ivjScrollbar3.setBounds(193, 14, 330, 26);
			ivjScrollbar3.setVisibleAmount(1);
			ivjScrollbar3.setOrientation(java.awt.Scrollbar.HORIZONTAL);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjScrollbar3;
}
/**
 * Return the TextField41 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
TextField getTextField0() {
	if (ivjTextField0 == null) {
		try {
			ivjTextField0 = new java.awt.TextField();
			ivjTextField0.setName("TextField0");
			ivjTextField0.setText("");
			ivjTextField0.setBounds(86, 14, 80, 26);
			ivjTextField0.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField0;
}
/**
 * Return the TextField1 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField1() {
	if (ivjTextField1 == null) {
		try {
			ivjTextField1 = new java.awt.TextField();
			ivjTextField1.setName("TextField1");
			ivjTextField1.setText("");
			ivjTextField1.setBounds(30, 52, 139, 26);
			// user code begin {1}
			ivjTextField1.setEditable(false);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField1;
}
/**
 * Return the TextField10 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField10() {
	if (ivjTextField10 == null) {
		try {
			ivjTextField10 = new java.awt.TextField();
			ivjTextField10.setName("TextField10");
			ivjTextField10.setBounds(30, 304, 139, 26);
			ivjTextField10.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField10;
}
/**
 * Return the TextField11 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField11() {
	if (ivjTextField11 == null) {
		try {
			ivjTextField11 = new java.awt.TextField();
			ivjTextField11.setName("TextField11");
			ivjTextField11.setBounds(30, 332, 139, 26);
			ivjTextField11.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField11;
}
/**
 * Return the TextField12 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField12() {
	if (ivjTextField12 == null) {
		try {
			ivjTextField12 = new java.awt.TextField();
			ivjTextField12.setName("TextField12");
			ivjTextField12.setBounds(30, 360, 139, 26);
			ivjTextField12.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField12;
}
/**
 * Return the TextField13 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField13() {
	if (ivjTextField13 == null) {
		try {
			ivjTextField13 = new java.awt.TextField();
			ivjTextField13.setName("TextField13");
			ivjTextField13.setBounds(30, 388, 139, 26);
			ivjTextField13.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField13;
}
/**
 * Return the TextField14 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField14() {
	if (ivjTextField14 == null) {
		try {
			ivjTextField14 = new java.awt.TextField();
			ivjTextField14.setName("TextField14");
			ivjTextField14.setBounds(30, 416, 139, 26);
			ivjTextField14.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField14;
}
/**
 * Return the TextField15 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField15() {
	if (ivjTextField15 == null) {
		try {
			ivjTextField15 = new java.awt.TextField();
			ivjTextField15.setName("TextField15");
			ivjTextField15.setBounds(30, 444, 139, 26);
			ivjTextField15.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField15;
}
/**
 * Return the TextField16 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField16() {
	if (ivjTextField16 == null) {
		try {
			ivjTextField16 = new java.awt.TextField();
			ivjTextField16.setName("TextField16");
			ivjTextField16.setBounds(30, 472, 139, 26);
			ivjTextField16.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField16;
}
/**
 * Return the TextField17 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField17() {
	if (ivjTextField17 == null) {
		try {
			ivjTextField17 = new java.awt.TextField();
			ivjTextField17.setName("TextField17");
			ivjTextField17.setBounds(30, 500, 139, 26);
			ivjTextField17.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField17;
}
/**
 * Return the TextField18 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField18() {
	if (ivjTextField18 == null) {
		try {
			ivjTextField18 = new java.awt.TextField();
			ivjTextField18.setName("TextField18");
			ivjTextField18.setBounds(30, 528, 139, 26);
			ivjTextField18.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField18;
}
/**
 * Return the TextField19 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField19() {
	if (ivjTextField19 == null) {
		try {
			ivjTextField19 = new java.awt.TextField();
			ivjTextField19.setName("TextField19");
			ivjTextField19.setBounds(30, 556, 139, 26);
			ivjTextField19.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField19;
}
/**
 * Return the TextField2 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField2() {
	if (ivjTextField2 == null) {
		try {
			ivjTextField2 = new java.awt.TextField();
			ivjTextField2.setName("TextField2");
			ivjTextField2.setBounds(30, 80, 139, 26);
			ivjTextField2.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField2;
}
/**
 * Return the TextField20 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField20() {
	if (ivjTextField20 == null) {
		try {
			ivjTextField20 = new java.awt.TextField();
			ivjTextField20.setName("TextField20");
			ivjTextField20.setBounds(30, 584, 139, 26);
			ivjTextField20.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField20;
}
/**
 * Return the TextField3 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField3() {
	if (ivjTextField3 == null) {
		try {
			ivjTextField3 = new java.awt.TextField();
			ivjTextField3.setName("TextField3");
			ivjTextField3.setBounds(30, 108, 139, 26);
			ivjTextField3.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField3;
}
/**
 * Return the TextField4 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField4() {
	if (ivjTextField4 == null) {
		try {
			ivjTextField4 = new java.awt.TextField();
			ivjTextField4.setName("TextField4");
			ivjTextField4.setBounds(30, 136, 139, 26);
			ivjTextField4.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField4;
}
/**
 * Return the TextField5 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField5() {
	if (ivjTextField5 == null) {
		try {
			ivjTextField5 = new java.awt.TextField();
			ivjTextField5.setName("TextField5");
			ivjTextField5.setBounds(30, 164, 139, 26);
			ivjTextField5.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField5;
}
/**
 * Return the TextField6 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField6() {
	if (ivjTextField6 == null) {
		try {
			ivjTextField6 = new java.awt.TextField();
			ivjTextField6.setName("TextField6");
			ivjTextField6.setBounds(30, 192, 139, 26);
			ivjTextField6.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField6;
}
/**
 * Return the TextField7 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField7() {
	if (ivjTextField7 == null) {
		try {
			ivjTextField7 = new java.awt.TextField();
			ivjTextField7.setName("TextField7");
			ivjTextField7.setBounds(30, 220, 139, 26);
			ivjTextField7.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField7;
}
/**
 * Return the TextField8 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField8() {
	if (ivjTextField8 == null) {
		try {
			ivjTextField8 = new java.awt.TextField();
			ivjTextField8.setName("TextField8");
			ivjTextField8.setBounds(30, 248, 139, 26);
			ivjTextField8.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField8;
}
/**
 * Return the TextField9 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField9() {
	if (ivjTextField9 == null) {
		try {
			ivjTextField9 = new java.awt.TextField();
			ivjTextField9.setName("TextField9");
			ivjTextField9.setBounds(30, 276, 139, 26);
			ivjTextField9.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField9;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
/**
 * Initializes connections
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() {
	// user code begin {1}
	// user code end
	getScrollbar3().addAdjustmentListener(this);
	getScrollbar1().addAdjustmentListener(this);
	getScrollbar2().addAdjustmentListener(this);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	// user code begin {1}
	getDrawFrame1().repaint();
	// user code end
	initConnections();
	// user code begin {2}
	getButton1().setVisible(false);
	// user code end
}
/**
 * This method was created in VisualAge.
 */
void printOld() {
	int max;
	Vector seVct = new Vector();
	max = traceObject.cStates.size()-1;
	
	for (int i = 0; i <= max; i++){
		seVct = (Vector)(traceObject.cEvents.elementAt(i));
		System.out.println("Trace - cEvent - Liste Nr.: "+i);
		SimuObject.printseList(seVct);
	}
}
/**
 * Der mittlere Scrollbar wurde bewegt. Das hat zur Folge, daß die Textfelder neu
 * gefüllt werden müssen und die Grafik neu berechnet und dann ausgegeben werden
 * muß.
 */
public void scrollbar1_AdjustmentValueChanged(java.awt.event.AdjustmentEvent adjustmentEvent) {
	//System.out.println("Scrollbar 1 wurde bewegt und hat jetzt den Wert : "+getScrollbar1().getValue());
	fillTextFields();
	fillDrawFrameData();
	getDrawFrame1().repaint();
	//System.out.println("scrollbar1_AdjustmentValueChanged : Wurde korrekt beendet !");
	return;
}
/**
 * a) Die aktuelllen Werte werden eingelesen.
 * b) Ggf. muß der maximale Wert für den Schieber angepaßt werden
 * c) Es wird aus den Daten die nächste legale Position errechnet.
 * d) Die neue Position wird im Nummer-Textfenster ausgegeben.
 *		Außerdem wird der TraceCounter korregiert und die neue Position auch im Trace-
 *		Fenster ausgegeben.
 * e) Die neue Grafik wird berechnet und ausgegeben
 * f) Wenn sich die 'neue' Position vom Wert des traceCounter's unterscheidet, soll man das
 *		TraceElement auch in Simu und den anderen Fenstern setzen und sichtbar machen (writeback()).
 */
 void scrollbar2_AdjustmentValueChanged1() {
	int ScBrValue2;
	int ScBrValue3;
	int lastMaxForScBr2;
	boolean lastFound;
	int oldTraceCounter = 0;		// Wert des TraceCounters bevor der Scrolbar bewegt wurde
	
	//	a)
	//System.out.println("Scrollbar 2 wurde bewegt und hat jetzt den Wert : "+getScrollbar2().getValue());
	
	ScBrValue3 =getScrollbar3().getValue();
	ScBrValue3 = (ScBrValue3/2);					// Der reale Wert des Scrollbars3
	
	ScBrValue2 =getScrollbar2().getValue();		// Der 'reale' Wert des Scrollbars2
	
	maxTraceElement = traceObject.cStates.size()-1;	// update, denn die Länge kann sich ja verändert haben.
																							// der negative offset ist notwenig, weil die SrollBars bei
																							// '0' mit dem Zählen beginnen.
	//	b)
																							
	lastMaxForScBr2 = getScrollbar2().getMaximum();
	if (lastMaxForScBr2 != maxTraceElement+1){					// Der maximale Wert für den Schieber muß angepaßt werden !
		getScrollbar2().setMaximum(maxTraceElement+1);	// Offset wg. Schiebereigenart notwendig
	}
	
																							
	//System.out.println("Scrollbar2_AdjustmentValueChanged : ScBrValue2:"+ScBrValue2);
	//System.out.println("Scrollbar2_AdjustmentValueChanged : ScBrValue3 :"+ScBrValue3);
	//System.out.println("Scrollbar2_AdjustmentValueChanged : maxTraceElement :"+maxTraceElement);

	// c)
	
	if ((ScBrValue2 + ScBrValue3) > maxTraceElement){	// Test, ob der angegebene Wert 'illegal' ist.
		if(ScBrValue3 > maxTraceElement){								// Test, oberer Scrollbar illegale Position hat.
			System.out.println("Scrollbar2_AdjustmentValueChanged : Fehler ");
			ScBrValue2 = 0;
			getScrollbar3().setValue(0);
		}
		else{
			ScBrValue2=0;
			lastFound = false;
			while (lastFound == false){											// Finde letzte mögliche Position ...
				if ((ScBrValue2 + ScBrValue3) == maxTraceElement){
					lastFound = true;
				}
				else{
					ScBrValue2++;
				}
			}
		}
		
		getScrollbar2().setValue(ScBrValue2);							// ... und stelle sie ein.
	}

	// d)	Die neue Position ermitteln, in den traceCounter uebernehmen und ausgegeben:
	ScBrValue3 =getScrollbar3().getValue();
	ScBrValue3 = (ScBrValue3/2);						// Der reale Wert des Scrollbars3
	ScBrValue2 =getScrollbar2().getValue();		// Der 'reale' Wert des Scrollbars2
	oldTraceCounter = traceObject.traceCounter;
	traceObject.traceCounter = (ScBrValue2+ScBrValue3);
	Integer intObject = new Integer((ScBrValue2+ScBrValue3));
	getTextField0().setText(intObject.toString());
	traceObject.getTextField2().setText(intObject.toString());
	// e)
	fillDrawFrameData();
	getDrawFrame1().repaint();
	
	// f)
	if (oldTraceCounter != traceObject.traceCounter){
		// neue Position angenommen
		// System.out.println("scrollbar2_AdjustmentValueChanged1() : Indifferenz festgestellt !");
		writeBackTraceElement();
	}
	
	//System.out.println("scrollbar2_AdjustmentValueChanged1() : Wurde korrekt beendet !");
	return;
}
/**
 * a) Sorgt für die korrekte (d.h. gerade ) Stellung des oberen Schiebers
 * b) Sorgt dafür, daß der Schieber nicht auf einer illegalen Position steht.
 * c)	Die neue Position wird im Nummer-Textfenster ausgegeben:
 *		Außerdem wird der TraceCounter korregiert und die neue Position auch im Trace-
 *		Fenster ausgegeben.
 * d) Die Grafik wird mit der veränderten Position ausgegeben.
 * e) Wenn sich die 'neue' Position vom Wert des traceCounter's unterscheidet, soll man das
 *		TraceElement auch in Simu und den anderen Fenstern setzen und sichtbar machen (writeback()).
 */
public void scrollbar3_AdjustmentValueChanged(java.awt.event.AdjustmentEvent adjustmentEvent) {
	int ScBrValue3;
	int ScBrValue2;
	int help;	
	boolean odd;	// Zeigt an , daß der Wert des Scrollbars ungerade ist. (true).
	boolean lastFound;
	int oldTraceCounter = 0;		// Wert des TraceCounters bevor der Scrolbar bewegt wurde
	
	//	a)
	
	ScBrValue3 =getScrollbar3().getValue();
	help =  (ScBrValue3/2);
	help =  help*2;
	if (help != ScBrValue3){
		odd = true;
	}
	else{
		odd = false;
	}

	if (odd){				// Ggf. Stellungskorrektur vornehmen
		ScBrValue3--;
		getScrollbar3().setValue(ScBrValue3);
		//System.out.println(" scrollbar3_AdjustmentValueChanged : ODD-Korrektur vorgenommen !");
	}


	
	//	b)

	ScBrValue3 =getScrollbar3().getValue();
	ScBrValue3 = (ScBrValue3/2);					// Der reale Wert des Scrollbars3
	
	ScBrValue2 =getScrollbar2().getValue();		// Der 'reale' Wert des Scrollbars2
	
	maxTraceElement = traceObject.cStates.size()-1;	// update, denn die Länge kann sich ja verändert haben.
																							// der negative offset ist notwenig, weil die SrollBars bei
																							// '0' mit dem Zählen beginnen.
																							
	//System.out.println(" scrollbar3_AdjustmentValueChanged : ScBrValue2:"+ScBrValue2);
	//System.out.println(" scrollbar3_AdjustmentValueChanged : ScBrValue3 :"+ScBrValue3);
	//System.out.println(" scrollbar3_AdjustmentValueChanged : maxTraceElement :"+maxTraceElement);
	
	if ((ScBrValue2 + ScBrValue3) > maxTraceElement){	// Test, ob der angegebene Wert 'illegal' ist.
		if(ScBrValue2 > maxTraceElement){								// Test, unterer Scrollbar illegale Position hat.
			System.out.println(" scrollbar3_AdjustmentValueChanged : Fehler ");
			ScBrValue3 = 0;
			getScrollbar2().setValue(0);
		}
		else{
			ScBrValue3=0;
			lastFound = false;
			while (lastFound == false){											// Finde letzte mögliche Position ...
				if ((ScBrValue2 + ScBrValue3) == maxTraceElement){
					lastFound = true;
				}
				else{
					ScBrValue3++;
				}
			}
		}
		ScBrValue3 = ScBrValue3*2;
		getScrollbar3().setValue(ScBrValue3);							// ... und stelle sie ein.


	}
	
	// c)	Die neue Position wird im Nummer-Textfenster ausgegeben:
	ScBrValue3 =getScrollbar3().getValue();
	ScBrValue3 = (ScBrValue3/2);						// Der reale Wert des Scrollbars3
	ScBrValue2 =getScrollbar2().getValue();		// Der 'reale' Wert des Scrollbars2
	oldTraceCounter = traceObject.traceCounter;
	traceObject.traceCounter = (ScBrValue2+ScBrValue3);
	Integer intObject = new Integer((ScBrValue2+ScBrValue3));
	getTextField0().setText(intObject.toString());
	traceObject.getTextField2().setText(intObject.toString());

	
	
	// d)  Anzeige des neuen Anzeigebalkens:
	fillDrawFrameData();
	getDrawFrame1().repaint();

	// e)
	if (oldTraceCounter != traceObject.traceCounter){
		// neue Position angenommen
		// System.out.println("scrollbar3_AdjustmentValueChanged : Indifferenz festgestellt !");
		writeBackTraceElement();
	}

	//System.out.println("scrollbar3_AdjustmentValueChanged : Wurde korrekt beendet !");
	return;
}
/**
 * Comment
 */
public void textField0_TextValueChanged(java.awt.event.TextEvent textEvent) {
	return;
}
/**
 * Comment
 */
public void textField41_TextValueChanged(java.awt.event.TextEvent textEvent) {
	return;
}
/**
 * Diese Methode schreibt das durch die Scrollbars zwei und drei bestimmte Traceelement
 * in die Vektoren bvList, seList, und InList und zeigt diese Informationen in den entsprechenden
 * Fenstern auch an.
 * Als Ausbaustufe werden auch die ExitedList und die EnteredList zurückgeschrieben.
 */
void writeBackTraceElement() {
	int pos;
	Vector bv = new Vector();
	Vector se = new Vector();
	Vector in = new Vector();
	Vector ex = new Vector();
	Vector en = new Vector();
	highlightObject ho = null;
	
	// Position feststellen
	pos = getScrollbar2().getValue() + ( (getScrollbar3().getValue())/2);
	
	// Daten-Vektoren auslesen
	se =	(Vector)(traceObject.cEvents).elementAt(pos);
	bv =	(Vector)(traceObject.cBvars).elementAt(pos);
	in = 	(Vector)(traceObject.cStates).elementAt(pos);
	//ex = (Vector)(traceObject.cExStates).elementAt(pos);
	//en = (Vector)(traceObject.cEnStates).elementAt(pos);

	// Daten zurückschreiben:
	SimuObject.bvList = bv;
	SimuObject.seList = se;
	SimuObject.InList = in;
	//SimuObject.EnteredList = en;
	//SimuObject.ExitedList = ex;
	
	// Daten anzeigen :
	if (SimuObject.LF1 instanceof ListFrame){
		SimuObject.LF1.update(SimuObject.bvList);
		SimuObject.LF1.writeBackByMon();
	}
	if (SimuObject.LF2 instanceof ListFrame){
		SimuObject.LF2.update(SimuObject.seList);
		SimuObject.LF2.writeBackByMon();
	}

	ho = new highlightObject(true);
	SimuObject.selectHighlightObject();
	ho = new highlightObject();
	
	//System.out.println("Monitor : writeBackElement : Wurde korrekt beendet !");
}
}