package simu;

import java.awt.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * Klasse, die die Listenfenster enthaelt
 */

 public class ListFrame extends Frame implements java.awt.event.AdjustmentListener, java.awt.event.ItemListener, java.awt.event.MouseListener {
	Vector inVector;	 	// bekommt einen Verweis auf bvList bzw. seList
	Simu simuObject;
	int listSize;			// bekommt die Anzahl der BVars bzw. SEvents 
	boolean arBool[];		// bekommt die zuletzt gueltigen Wahrheitswerte
	boolean arBoolWork[];	// bekommt eine Arbeitskopie der Wahrheitswerte
	String arString[];		// bekommt die String - Namen der BVaer bzw. SEvents
	private Button ivjButton1 = null;
	private Button ivjButton2 = null;
	private Panel ivjContentsPane = null;
	private Frame ivjFrame1 = null;
	private Scrollbar ivjScrollbar1 = null;
	private Panel ivjPanel1 = null;
	private Button ivjButton3 = null;
	private boolean debug=true;
	private Checkbox ivjCheckbox1 = null;
	private Checkbox ivjCheckbox10 = null;
	private Checkbox ivjCheckbox11 = null;
	private Checkbox ivjCheckbox12 = null;
	private Checkbox ivjCheckbox2 = null;
	private Checkbox ivjCheckbox3 = null;
	private Checkbox ivjCheckbox4 = null;
	private Checkbox ivjCheckbox5 = null;
	private Checkbox ivjCheckbox6 = null;
	private Checkbox ivjCheckbox7 = null;
	private Checkbox ivjCheckbox8 = null;
	private Checkbox ivjCheckbox9 = null;
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
	private Button ivjButton4 = null;
	private Button ivjButton5 = null;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ListFrame() {
	super();
	initialize();
}
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ListFrame(Vector arg1, boolean arg2, Simu arg3, int arg4) {
	super();
	simuObject = arg3;
	listSize = arg4;
	inVector = arg1;
	debug = arg2;
	
	//listSize--;
	/*if (listSize < 12) {
		listSize = 0;
	}
	else {
		listSize = listSize - 12;
	}*/
	getScrollbar1().setMaximum(listSize);
	getScrollbar1().setVisibleAmount(12);
	initialize(arg1);
}
/**
 * Method to handle events for the AdjustmentListener interface.
 * @param e java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getScrollbar1()) ) {
		connEtoC3(e);
	}
	// user code begin {2}
	// user code end
}
/**
 *  
 * Uebernimmt arBoolWork in arBool und ruft schliesslich ********** (s.u.) auf
 * Ausserdem wird der "LastKnownState"-Button gesperrt.
 *
 * Benutzt globale Objekte			: arBool, arBoolWork
 * Aufgerufen von					: 
 * Ruft auf							:
 *
 * @params arg1 Vector
 * @returns
 * @version 3.00 vom 17.01.1999
 */
public void button1_MouseClicked() {


	if (debug==true){
		System.out.println("ListFrame-Uebernehmen gedrueckt");
	}
	int i = inVector.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts

	for (int j=0; j < i; j++){					// Array arBoolWork in arBool uebernehmen
		arBool[j] = arBoolWork[j];
	}

	// Hier muss noch eine Methode eingefuegt werden, die arBool in bvList bzw. seList zurueckschreibt
	// Arbeitstitel: writeBack();
	writeBack();
	// simuObject.resetSimu();
	getButton5().setEnabled(false);
	return;
}
/**
 *  
 * Uebernimmt arBool in arBoolWork und ruft schliesslich fillWork auf.
 *
 * Benutzt globale Objekte			: arBool, arBoolWork
 * Aufgerufen von					: button2_MouseClicked() - "Verwerfen"
 * Ruft auf							:
 *
 * @params arg1 Vector
 * @returns
 * @version 3.00 vom 18.01.1999
 */
public void button2_MouseClicked() {
	
	if (debug==true){
		System.out.println("ListFrame-Verwerfen gedrueckt");
	}
	int i = inVector.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts

	for (int j=0; j < i; j++){					// Array arBoolWork in arBool uebernehmen
		arBoolWork[j] = arBool[j];
	}

	fillWork(getScrollbar1().getValue());
	return;
}
/**
 * Comment
 */
public void button3_ActionEvents() {
	this.dispose();
	System.out.println("Button gedrueckt !");
	return;
}
/**
 * Reagiert auf Mouseclick auf Reset-Button im ListFrame (Button 4)
 */
public void button4_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	if (debug==true){
		System.out.println("ListFrame-RESET gedrueckt");
	}
	int i = inVector.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts

	for (int j=0; j < i; j++){					// Array arBoolWork wird zurueckgesetzt
		arBoolWork[j] = (false);
	}

	fillWork(getScrollbar1().getValue());
	
	return;
}
/**
 * Reagiert auf Mouseclick auf den "LastKnownState"-Button (Button 5)
 * mit Uebernahme des aktuellen Zustands von bvList oder seList in
 * arBool und (!) arBoolWork
 */
public void button5_MouseClicked(java.awt.event.MouseEvent mouseEvent) {
	Object helpObject; 
	BvarTab d1;
	absyn.Bvar d2;
	SEventTab d3;	
	absyn.SEvent d4;
	
	String Item;
	int i = inVector.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts
			
	if (debug==true){
		System.out.println("ListFrame:LastKnownState-ButtonEvent: counter= "+i);
	}
	
	i--;
	for (int j = 0; j <= i; j++) {			// Schleife durchlaeuft Vektor und fuellt die Liste
		helpObject = inVector.elementAt(j);
		
											// Da diese Methode sowohl fuer bvList als auch fuer seList
											// verwendet wird, ist eine Fallunterscheidung noetigt.
		
		if (helpObject instanceof BvarTab){	// Fall 1: bvList
			d1 = (BvarTab)helpObject;
			d2 = d1.bName;
			Item = d2.var;
			boolean bWert = d1.bWert;
			boolean bWertWork = d1.bWert;
			arString[j] = Item;
			arBool[j] = bWert;
			arBoolWork[j] = bWertWork;
			
			if (debug==true){
				System.out.println("LastKnownState-Button-Event: Durchlauf Nr.: "+j+" ist "+Item);
			}
		}
		
		if (helpObject instanceof SEventTab){ // Fall 2: seList
			d3 = (SEventTab)helpObject;
			d4 = d3.bName;
			Item = d4.name;
			boolean bWert = d3.bWert;
			boolean bWertWork = d3.bWert;
			arString[j] = Item;
			arBool[j] = bWert;
			arBoolWork[j] = bWertWork;
			
			
			if (debug==true){
				System.out.println("LastKnownState-Button-Event: Durchlauf Nr.: "+j+" ist "+Item);
			}
		}
	}
	fillWork(getScrollbar1().getValue());


	return;
}
/**
 * Comment
 */
public void checkbox1_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox1().getState();
	relPosition = getScrollbar1().getValue();
	arBoolWork[relPosition] = status;	
	return;
}
/**
 * Comment
 */
public void checkbox10_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox10().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 9;
	arBoolWork[relPosition] = status;	

	return;
}
/**
 * Comment
 */
public void checkbox11_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox11().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 10;
	arBoolWork[relPosition] = status;	

	return;
}
/**
 * Comment
 */
public void checkbox12_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox12().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 11;
	arBoolWork[relPosition] = status;	

	return;
}
/**
 * Comment
 */
public void checkbox2_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox2().getState();
	relPosition = getScrollbar1().getValue();
	relPosition++;
	arBoolWork[relPosition] = status;	
	return;
}
/**
 * Comment
 */
public void checkbox3_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox3().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 2;
	arBoolWork[relPosition] = status;	
	return;
}
/**
 * Comment
 */
public void checkbox4_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox4().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 3;
	arBoolWork[relPosition] = status;	

	return;
}
/**
 * Comment
 */
public void checkbox5_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox5().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 4;
	arBoolWork[relPosition] = status;	

	return;
}
/**
 * Comment
 */
public void checkbox6_ItemStateChanged1(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox6().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 5;
	arBoolWork[relPosition] = status;	

	return;
}
/**
 * Comment
 */
public void checkbox7_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox7().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 6;
	arBoolWork[relPosition] = status;	
	return;
}
/**
 * Comment
 */
public void checkbox8_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox8().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 7;
	arBoolWork[relPosition] = status;	

	return;
}
/**
 * Comment
 */
public void checkbox9_ItemStateChanged(java.awt.event.ItemEvent itemEvent) {
	boolean status;
	int relPosition;
	status = getCheckbox9().getState();
	relPosition = getScrollbar1().getValue();
	relPosition = relPosition + 8;
	arBoolWork[relPosition] = status;	


	return;
}
/**
 * connEtoC1:  (Button3.mouse.mouseClicked(java.awt.event.MouseEvent) --> ListFrame.button3_ActionEvents()V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button3_ActionEvents();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC10:  (Checkbox6.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox6_ItemStateChanged1(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC10(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox6_ItemStateChanged1(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC11:  (Button4.mouse.mouseClicked(java.awt.event.MouseEvent) --> ListFrame.button4_MouseClicked(Ljava.awt.event.MouseEvent;)V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC11(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button4_MouseClicked(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC12:  (Checkbox7.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox7_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC12(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox7_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC13:  (Checkbox8.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox8_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC13(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox8_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC14:  (Checkbox9.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox9_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC14(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox9_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC15:  (Checkbox10.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox10_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC15(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox10_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC16:  (Checkbox11.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox11_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC16(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox11_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC17:  (Checkbox12.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox12_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC17(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox12_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC18:  (Button5.mouse.mouseClicked(java.awt.event.MouseEvent) --> ListFrame.button5_MouseClicked(Ljava.awt.event.MouseEvent;)V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC18(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button5_MouseClicked(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (Button1.mouse.mouseClicked(java.awt.event.MouseEvent) --> ListFrame.button1_MouseClicked()V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button1_MouseClicked();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC3:  (Scrollbar1.adjustment.adjustmentValueChanged(java.awt.event.AdjustmentEvent) --> ListFrame.scrollbar1_AdjustmentValueChanged(Ljava.awt.event.AdjustmentEvent;)V)
 * @param arg1 java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC3(java.awt.event.AdjustmentEvent arg1) {
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
 * connEtoC4:  (Button2.mouse.mouseClicked(java.awt.event.MouseEvent) --> ListFrame.button2_MouseClicked()V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC4(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button2_MouseClicked();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC5:  (Checkbox1.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox1_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC5(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox1_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC6:  (Checkbox2.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox2_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC6(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox2_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC7:  (Checkbox3.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox3_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC7(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox3_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC8:  (Checkbox4.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox4_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC8(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox4_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC9:  (Checkbox5.item.itemStateChanged(java.awt.event.ItemEvent) --> ListFrame.checkbox5_ItemStateChanged(Ljava.awt.event.ItemEvent;)V)
 * @param arg1 java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC9(java.awt.event.ItemEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.checkbox5_ItemStateChanged(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 *
 * Setzt den Status der visuellen Komponenten Checkbox und TextField entsprechend der 
 * Laenge der Bvar- bzw EventListe auf enabled bzw. disabled fuer Eingaben.
 *
 * Benutzt globale Objekte			: inVector
 * Aufgerufen von					: initialize()
 * Ruft auf							: ----
 *
 * @params
 * @returns
 * @version V3.00 vom 18.01.1999
 */
private void disableChecks() {
	int counter = 0;
	int max = 0;

	max = inVector.size();

	if (1 <= max ) {
		getCheckbox1().setEnabled(true);
		getTextField1().setEnabled(true);
	}
	else {
		getCheckbox1().setEnabled(false);
		getTextField1().setEnabled(false);
	}

	if (2 <= max ) {
		getCheckbox2().setEnabled(true);
		getTextField2().setEnabled(true);
	}
	else {
		getCheckbox2().setEnabled(false);
		getTextField2().setEnabled(false);
	}

	if (3 <= max ) {
		getCheckbox3().setEnabled(true);
		getTextField3().setEnabled(true);
	}
	else {
		getCheckbox3().setEnabled(false);
		getTextField3().setEnabled(false);
	}

	if (4 <= max ) {
		getCheckbox4().setEnabled(true);
		getTextField4().setEnabled(true);
	}
	else {
		getCheckbox4().setEnabled(false);
		getTextField4().setEnabled(false);
	}

	if (5 <= max ) {
		getCheckbox5().setEnabled(true);
		getTextField5().setEnabled(true);
	}
	else {
		getCheckbox5().setEnabled(false);
		getTextField5().setEnabled(false);
	}

	if (6 <= max ) {
		getCheckbox6().setEnabled(true);
		getTextField6().setEnabled(true);
	}
	else {
		getCheckbox6().setEnabled(false);
		getTextField6().setEnabled(false);
	}
	if (7 <= max ) {
		getCheckbox7().setEnabled(true);
		getTextField7().setEnabled(true);
	}
	else {
		getCheckbox7().setEnabled(false);
		getTextField7().setEnabled(false);
	}

	if (8 <= max ) {
		getCheckbox8().setEnabled(true);
		getTextField8().setEnabled(true);
	}
	else {
		getCheckbox8().setEnabled(false);
		getTextField8().setEnabled(false);
	}

	if (9 <= max ) {
		getCheckbox9().setEnabled(true);
		getTextField9().setEnabled(true);
	}
	else {
		getCheckbox9().setEnabled(false);
		getTextField9().setEnabled(false);
	}

	if (10 <= max ) {
		getCheckbox10().setEnabled(true);
		getTextField10().setEnabled(true);
	}
	else {
		getCheckbox10().setEnabled(false);
		getTextField10().setEnabled(false);
	}

	if (11 <= max ) {
		getCheckbox11().setEnabled(true);
		getTextField11().setEnabled(true);
	}
	else {
		getCheckbox11().setEnabled(false);
		getTextField11().setEnabled(false);
	}

	if (12 <= max ) {
		getCheckbox12().setEnabled(true);
		getTextField12().setEnabled(true);
	}
	else {
		getCheckbox12().setEnabled(false);
		getTextField12().setEnabled(false);
	}
}
/**
 *
 * Füllt die Textfelder und die Checkboxen der Listframes mit den Daten aus arString und arBool
 * in Abhaengigkeit von der uebergebenen Anfangsposition.
 *
 * Benutzt globale Objekte			: arString,arBool
 * Aufgerufen von					: initTabs()
 * Ruft auf							: -----
 * @param start int
 * @returns
 * @version 3.0 vom 17.01.1999
 */
private void fill(int start)
{
	int counter = 0;
	int max = 0;
	counter = start;
	max = Array.getLength(arString);
	
	if (debug == true)
	{
		System.out.println("Methode fill: Berechnete Laenge: " + max);
	}
	
	if (counter < max)
	{
		getTextField1().setText(arString[counter]);
		getCheckbox1().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField1().setText("");
		getCheckbox1().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField2().setText(arString[counter]);
		getCheckbox2().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField2().setText("");
		getCheckbox2().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField3().setText(arString[counter]);
		getCheckbox3().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField3().setText("");
		getCheckbox3().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField4().setText(arString[counter]);
		getCheckbox4().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField4().setText("");
		getCheckbox4().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField5().setText(arString[counter]);
		getCheckbox5().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField5().setText("");
		getCheckbox5().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField6().setText(arString[counter]);
		getCheckbox6().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField6().setText("");
		getCheckbox6().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField7().setText(arString[counter]);
		getCheckbox7().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField7().setText("");
		getCheckbox7().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField8().setText(arString[counter]);
		getCheckbox8().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField8().setText("");
		getCheckbox8().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField9().setText(arString[counter]);
		getCheckbox9().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField9().setText("");
		getCheckbox9().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField10().setText(arString[counter]);
		getCheckbox10().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField10().setText("");
		getCheckbox10().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField11().setText(arString[counter]);
		getCheckbox11().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField11().setText("");
		getCheckbox11().setState(false);
	}
	counter++;
	if (counter < max)
	{
		getTextField12().setText(arString[counter]);
		getCheckbox12().setState(Array.getBoolean(arBool, counter));
	}
	else
	{
		getTextField12().setText("");
		getCheckbox12().setState(false);
	}
}
/**
 *
 * Füllt die Textfelder und die Checkboxen der Listframes mit den Daten aus arString und arBoolWork
 * in Abhaengigkeit von der uebergebenen Anfangsposition.
 *
 * Benutzt globale Objekte			: arString,arBoolWork
 * Aufgerufen von					: initTabs()
 * Ruft auf							: -----
 * @param start int
 * @returns
 * @version 3.0 vom 17.01.1999
 */
private void fillWork(int start) {
	int counter = 0;
	int max = 0;
	counter = start;
	max = Array.getLength(arString);
	
	if (debug == true)
	{
		System.out.println("ListFrame.fillWork: Berechnete Laenge: " + max);
	}
	
	if (counter < max)
	{
		getTextField1().setText(arString[counter]);
		getCheckbox1().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField1().setText("");
		getCheckbox1().setState(false);
	}
	
	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}
	
	counter++;
	
	if (counter < max)
	{
		getTextField2().setText(arString[counter]);
		getCheckbox2().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField2().setText("");
		getCheckbox2().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField3().setText(arString[counter]);
		getCheckbox3().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField3().setText("");
		getCheckbox3().setState(false);
	}
	
	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;

	if (counter < max)
	{
		getTextField4().setText(arString[counter]);
		getCheckbox4().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField4().setText("");
		getCheckbox4().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField5().setText(arString[counter]);
		getCheckbox5().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField5().setText("");
		getCheckbox5().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField6().setText(arString[counter]);
		getCheckbox6().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField6().setText("");
		getCheckbox6().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField7().setText(arString[counter]);
		getCheckbox7().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField7().setText("");
		getCheckbox7().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField8().setText(arString[counter]);
		getCheckbox8().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField8().setText("");
		getCheckbox8().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField9().setText(arString[counter]);
		getCheckbox9().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField9().setText("");
		getCheckbox9().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;

	if (counter < max)
	{
		getTextField10().setText(arString[counter]);
		getCheckbox10().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField10().setText("");
		getCheckbox10().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField11().setText(arString[counter]);
		getCheckbox11().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField11().setText("");
		getCheckbox11().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

	counter++;
	
	if (counter < max)
	{
		getTextField12().setText(arString[counter]);
		getCheckbox12().setState(Array.getBoolean(arBoolWork, counter));
	}
	else
	{
		getTextField12().setText("");
		getCheckbox12().setState(false);
	}

	if (debug == true) {
		System.out.println("fillWork: counter ist: "+counter+", Status: "+Array.getBoolean(arBoolWork, counter));
	}

}
/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G1B2BBEA6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E154DB8FD09C57F9D7ADEA2BA953120AA8F2CBE31AD00F26D5E69C1BE908CDE7D41736EAC649A82952AAB51AEABCCA2DD1ED97FB5409BAD5BD4B89A1E2E3D9321005E3ACA103E4E41D610E7FA0247057828BD9D8A08E49585CF23740217B475D9E1CACB2536F6D5B6F5D6E5EBB0EA5F426554C275D7D3E6FFD3F5FFE7B5E77F65FBD0432B319078BADE502D0F8D8D07F35948AC2762302F06F8F7E7DAB28AB
	29F4D8844D3F33A047052BD5C5F0AC875B1E3FF1D80E89BB66CB605A8A5759CFBBAC27607C0470537945EF42C9821C9130B5FF5D714E52B8C7FD08F3C4397E2C2E940EC7C1EC209B814928A478613AF2D59F056B236AB911C208EABDCBFCF2FD659A7DEE55CFGDB99C80E6273FBF5C69F99746BC0B2C1FE21B0FB7D4DD6553E5D3EC198FB651D6257890E269D0A709E4528D60F027052E11204C3026BB0A4C468983ABBFDA56C71C55A5B87BDE17923106813525FA3BC1E7515A8FE02B08E5715D5C4F747CF6EFD
	A7DC970014D4A11F3FFB2FC345CBFD9812DADE11100F3B0A72D8D79563B3F67019C20A19DD0718DB2B90F3FDB341942A9D16F290DBB56A9BA7083E9F2E1DA051EA64722F6711CBCE601B42786944DC522AA9076C6A98174D7B1EA9FCDBF94EFF68A74F21GECE5679C169A10C190AFC8AA48BBCA7F3A657BG0EDD13F2CF40678B783B0241EE2FBBF4BD60163CF05E5E8E96D5F7D3F285C2EEC120FD68B91F154387A56B1C3E7F902177669070397726E58869F2C371E14E3D159D2DAA4ED1E3ECBB973BB767F912
	372783A4EF4F031E34EB82217A1FA97A2C73B1FD708322F7AA4F1CDC173F6F306455E01ED36EA317117BDF95F68FDB0B0F7038FC5BDE1C7D014A650398971AE3B9C8F2FCG74E5979D96ABC8AF089BA465A2667891DFC3329C8F78E5A964973D4311492098123BA39E2FDB8A29397EDEDD22DCE7DE0C4F73D1160FA3AD643E33AF52FC9CE67AB7957DD60D1E6669E1251F126BFC30F5DA21DFAA7E9DCEA52FD66AFF0E45F1A87A839ABD0D532E74EBF2BD78A1746BDA0705666AE7F3A4D7D9F58E4B96101DA085A0
	47EC10B79B66EA1F2FEE197DF1C813A75A76DB7E3760A8C56510B8608F4B225FA50D0AA10FB8691542026A3359CFF2BA29EB779DE51C38G632A6417C2226C8978A1BA5491F8A8E16A73247FB87189C90AF5446313G409794B06E537E93F0740AE179D650AD4AD24C035A3F6FA7FDEEDE9C050890G4E97EDA4D72FA8B5EA904EE7EC1827D3E1225B8257BBD9DE7A82EF42719AF492495B5E5E6BF1919AE2689E0E472D0199B2A6403F518EF59624345EE1498529E9003A8C525F8871C0EA1A601900EC8599EBF6
	D876023436C0CDG598DA237BAACC5ED8ECBBAC8F5BB4CC5A07D179C16FC10418E07E5C7870E1F9747F07C2C2978A3A177EB555C71137D544962714B341F4A17E36307727EB705F78668F3C172C18E02D4DCF1D8BAC046C196C1B2C0F2C172C18E02D47C8A6CA063A00BA099A039A079A087C1AABA418EB28E32881281125B09735F14BF33B0F16DA2B943F312B712ABF2CE72C77203B61267C4B1D04873A0C712F31253C4FE194B08E5C648B3D2B8DFD26707F67A7C1235A3FD814FC97FA1FD047407410E255B12
	FEE25425A738DF5287D3DB62ED241F0E29FD11DC53E77523A51F55EB1A73B7C0CE2967E42CD6E9EC07171133C36A71A4487BCB789DD94567C424C9BD5629478FC16C4BE8772E661C0CC7D2BBC84DEDC8526EA2C7F7A101AF29E9E7B9FA72BEC26A52998D3EA0ECEA504E6D4E7D673975E073F0D5F1FEA7FD467BBB0D73E92ECC660E32AEF8CF82996A427A37A6D0A9A873C2379816F0A6389102A2AE39E355107A7EBAC87A02B4AF7965DEA9A8795DF092D66BF0CA10F0D56708290000757BAB0A1E4C3DBDD04443
	CC1F96BCCE7590C3BB6320FD23E297E5B964190C485230A44B9E7FF42CFD2642A52C1B5643EA6D5F2E3CAB4E40BD2E69425A74FA8B51E541F58E5315AB3A6DBA3FC71A4792BCB35256EC9F4CB1342198B989B18E190C4F9B0394235B592F7611C32A4F9135BF3D267633B784BA1EC9DFA4637BEDB5376F2A7D0F74CFB2062AD84C4F15187B611EAA3A61FD8C448B128E128B328724348766C910F11094104DA0BBC08E02D477C23D8311C1B2FAB15F1D9315054B67F9F4892E5A3A142CA67CFF931A2B5FD5F2351C
	BC1DCC122BF34B40DCCEBE7FAF8A2D8FAF4EA52ED57FB1472FD57FB5472FD5052125EB51AFC23CDAC4ED47C2647D5295707BA52518B233C03F35DF9CA11CBDFE0F6C703827A5F9587325846FB73D240E1D74137A148767BBFB715DF0218A67021A3E2F8B36975E62FE671576B7965B7AE86D696D0B4D8514D3C5184C8512D772C1D2FA82913FCC756FA96F472ABEFCC85155DCEE03E3E5CDF7C416837E862B285CC5ED65079FDE29E44B8256C9EA1D68D22CFDA46B611A8B2397EC826D2FA70487A2933AB83EC0A4
	AC55AA7F77F8BD2E3B123BE6D61C97EB458539D6F9EA355709CD89F5E159E894EB3DDD0BE5DF85AC7B44F5EDEB40322BD8F96D5FD23006DD210057BBA9066CD651BD9B894BA407353153D1519B11FAE6C47FF4BCFA977362D288335093DD7CF803CB873C84FC6BD53EFFBD284DCD7DAA6426BEAEB78DAAD6EAE42D0255B3A3396ECE8622F62BC716FC35643FE1993E659224E3G6CDCBC97461998B006B0E60045A0775E2872919774FC6AD709CF3DC9BECDAA9F79281ECF43AA71E9B04927D9655372C50F0ECF23
	5595708583FE52A081D463C48F3A4446E10B9A6F65DF6B63B5A50F57440B572A46BBFCC49F2FB9F93CE6DE3CB6B55EEF75256862352CD27EDBCC665FEE27FC3CCEBD1F56D56253EA16CFBD6573470B3DBABEED49735D464B373D0146FB7B42C77AFAE0CB9E50EE63C6EC44F965BBCEC3CC7BB2E25A39B11BB066CFCFBC280F39DA0542EE36D2581B11D330F02DE1EC2E8E25C6130CF0EC8FD8569A46766A70E9B24987EB437C0D94C3EDD89DBE4DF1FC6CCB93EAD3EBDFF70AE16C2C8E1FB633BDC89DFBBF397120
	F16C2DD21F3619E544466EF72D8F9A476EAAF1B27B06909B7B7FFCC5543C9FB52E427BD123717D6852057AE676DEDCFA623E862FE995701A74F8467B0EB5D7EE9C477339376E7F6F5E7752BCA0A3F41DCA18A75FA6EE78E60902247561BA55B742440E6B746A82D58F3C97534FAA3015FCC43E191A02E51A6F20CDFD74F7587DF024763F605A07BEC2FB4B9C4FFE1445F7A5317FFE927BD3DC3B1B61F704F876A8337FA5571E4A6CD742CB634FA631EF605AB3D97CD3B24F1E4D6C9B3976D87C3CA476822EFDAB0B
	BF97615973197DE52EBD967F74BC4FDE405ABF423597DDC5FB65824FDE4E6CFF42354770F7A63197C517365B12584739F6AB63370BEB6F3C0A3F113D220C3F91387602ACDE457177CF61931A71B798885C0D84C748AF0078BB1DFC8C0EE1E9AEA279DD92B1DC136E293F717D3D7C264126FC3D325F8EFFA413B5AC2FA6A6DD9BF9CCA47AE0486393C37748A2313AD762B95DBE08B460C876D5BC8647BC90DA4F962F9743D129C7F6DCEB091EA4EBA82CCE893877626396DE3CFCB89600E476AB7D42D9215ACB603A
	9224331F7AAD76537CD3BF1F08FE4F43F5510043D2BDC07D1C83DABF5BA47A2500BE83A4D775AB7DD86BF700790D03DE86C97F187AED5779BD65C23F7D20AF3D864F7C9A751B3926757B9DB77AED86FD1E46EF0F4E6F3204FEB503F02F03B1BF6720566FA56617857ADC0DDF3E4E6FCF27502F723A4352FABD66B7F6DD6BF7FB9A7DB2C01F2D715B2173FBFD867D4ABFF1D8EC1F447C06BE517A7D9573CB877DE60D5FB61DDF4403FE47EEB8ACB5B7E2FE7DB734FE97E651AF9574D99A3FBC1D5FAE66D7B2847DE5
	A86657B924757B6ADD568FC01F21714B557975F951CF78946A8DC86B2754CF7ED46BD762C33FEC50EF5378BD2F7AD9EF92BF2B9F775354B1DD0F227B8798B85FAD425F1E2E9F536EA7929E32F1579B0984EF5278A9B70D3FBDF5C4155FDEC01FFD936B49E0C4DBCF3A4261004B23EC81D0EBC2713CF6AD126ADE1BFF950ED31ED0D89E703B25A8560F637304AB59A3202A55717E6797AF8B71FB9E7A5C9EB990421F3AFA9614F71011C0CF40AD75C3896AE1CB35239EE901749BE57F11B748769F5D443558E76FE8
	77B649160A62579264C69826B9299C0E65A6388F77B63D40E2BB41CEF5AF4F21EEA35305C3285B4EF47B192EE484F5BF88232E1369BE11D197E53A77A328FB0C69B6B15DDE263BB50F3A4ADB287BC7269BE3BA47826A5218EEFD14650D694A6E21CE98457ED03440DF13FEF5016C313B4B6612238B242E4F2B7387FD4E5FBB41762B61CBE6FB3BE63F5A99DB095AA7DAC7F10F58F37584AF88576BC0B2C7115B83AD681B43FC1FECA33E85F0DD62G9D885D0F3355097BFADA094E01F36C7AAA5D9C8B7AEDA0858E
	1C1B6AC472DB3429B7EC5627274E125FF4922C002BE376482B1DAA769AA1C358E2EFAEFE0353AF53563EDBDCB426560431D83FAC3918DFD881C26FF103087798851F4746709E9F6D665D635EB13C477F7E8D6E71113258BD462D3C2BFB21EECFA22F030059F89BF9BDF1DA4B4BFB1B72CA3B0D3C36CCF64171B7DE22E7DC0F1CA4EBE42B31C4CF6F6B2C8B6FAB8BB867337B5AD6414BF7599D3C2FF52E1567DB5064BBEEB5DF4D779E462B86B01DF710574499AD2F0DF7A82FDC46EB54CD6697131F75AA2F8742AD
	F0DC612A3F4A7BF1B7725ECEB8FF063C3FF616473B936C947B899D3649F5BB95FBCAC26C91081B4A30EF5572307398F62B8E5B64EA3E0A7D4BA9445E89F10F0DA3F6110D075DBF0E58FDBAEC132B03AA768F9976B844CDE758CF58F9585B9876DBBAEC13AB01AA768353083D9B6216FF0E58138DBC6C214F917B5E49D68D3659D5BF0A5DBC0358EE081B41300FB77130F7B06C353A7BB6796B01FA5F7B9876DE08DB7985E2EFE966E10FFD01581BF458A6FFA9D0313F61C1ECAF444DE25801D69EF6BE435E2543B6
	393229E25F1CC56C8208DBB30158275BF9584E89447EEDD97B3C4DAEE2D26C623B08ADC35C4D8CFB67A59E761E9876A59D364942C6311FE45887A02E4D09588FDD66E14FB899F6038E5BE4E12358FEAFE2AFC25CEC065D7CAB5E9C753C9367021FF8D7BEC70D5A6A599CE5E6E59177CE5BFD48D990611BC8C44E364FF91C03A2666BDFB438E6D6D6593E7CFA6D77535E59B3893F1FD2C47A8E36D1B4FEBFB5FFC95EC933C13FD5447765758BE4CF8AFEBF5D04AF96F8FADE3226C16DDDD112F3F8EFD62F7B2284D7
	F9EF96F0BF51DCF0293FB5B0CA3E08EBC4617BA4E785F0DDBA6930D403F4000CCF323522E852FD79FD51A576653FEC0A5762A46EA57B791C19F6E92CDDB4E426DD96EB771F265A6530F6BF8E1BE9370D359B164D345B4D5AD9A3E65A6DE76D3EED2ADD098B5B5D19B7532E0235FB46D4BB9BEBB73EE026DDAFEB37B1EA265D98EB7776BDB36D3C2C5D284D58EE0DAB36BFB4DDF30EC27F9E643386D23BB639685F039CE2FAC95167E87434563D20FC5BAA5F5CE0333A917F2196A3FE1ABB061965CE04DF5B2E6038
	0D782D0AFE2B5B083F258D71F7022DC3C27C9FBA0D7819129EB357F0CD5B6547352BB5DC6FB63423357B476DE49F24E1DD007E40A5605A6F639D5A355F839027FC8AB8038CCD611A40772BF0CDE0E68AEB600EC5324E16895739A0BBC08AC04A26E1AE86991946DAF87E9EEF8DCADF8B93ADC04576C453BCE408AB4B5F1F4535B366CF1E563723773D2B3BC40D11BA0D4FF0E2F2E59CFCF16DCE9B2E7326F94FF076F6F97CB3E41B6DA84FFD2711E7BE63793EEBE5BC1B625A99F3D5B6434B5521F7310FD8E71043
	3E95F2B890574E18AB778CAFD7AF551D0A4F957E382E725CF086F33548F86638D74673EF635A99F9E6F3F3354B0A395A46B8F82515F178B22E1D11C3110747A13596B9D4F810C365544AB8544635B3F29861F2384456A53D0C430E95F2F8B6A907ACAE07FDF664104BB82C1BDE99078DF16D0C9C76F3B9BC5C009CCAE711C3474C4AB8FC9C574E4821F71647213F89B90CB38E85AB6470CAD28E69DC8EBFEFC68E1B9907AC4F4AB8BC9E574E48E18F1743232D4861A063B0B63BB28ED3F16D0C9C9A6F72B8B85B7F
	07376B0F0A6A3A7320A492C37C910992A3C68D27259BF34A6E469EB66124B632DDBC65B41C0DCDC9625975D4EB28E59B5652D3136A6928E8B031CA0EA6E0G9995A2D6FE8CB040G83E244ACC9C9C3D292B9F6F099E1789187E704C121A06E773E773E5FF9773DB7D725497E71B9B377F33F77FBBF6F3EB7775EF7FF8DEAE83B029A76DF191E0677F469349AC28CB59C342306A524E175B4B57C78369A5699EAF82691B52412861FFBFA9AE66852E9B5943A0DB48C1DC18DF6B7EA28556DD13FB38D67F569349AFC
	069A7A3BD0C3189B7BDF85D3525E88882FF3E3DFEB4EB85B87036F446D1E412BBE933E8FB77C0D4D9BF906877B872FA257ACF1BD9E309F46FD432152DC5CA670E99AC23F93930177124C18B05ECB92BAE13C17E44E04F85F5B4209C0FBCDE4FD7B2E213E4320CF668E8CA3D7CC5CDC626C44E50DA057CE5CAF086BA56EAB9FF2E3441D9BC5AE0C38F8621691F76DBAF2CF923715388462C6EEA0371E386F0EA117C61CE59C390C2B488DC96DE68E04CB087BD2EA475AA05CFB951FD3E7951BBB8C257095A93C0C42
	3F2E8C348F6FEE417250F2CA3DD575F565E134B7D03E7625521CE5BCE8C8267218E5437258C657EB2DE7571F6741F01394E66FBBCAFF2E6E6B524A72B0AE0F7F7C5A72B0AA0FFC09DF7B6EC169B7B6B2629A11DE2EDA1CBE2792770A0F3D936A76E37DD9DE8F246CB3F06ED3EC4E7BBC67581EE139FF7DE23141BCF33B93EA9B79DD52D32FFD17E4FBCB2DD0962D9EFC175CC26DCA37074EB1E9E66F4B91900E81A4507DF96E04E81CE1032A0C53156FF29D729F13016B2B1FCF9A57D73F1CE4F56708E7F4384725
	2D1336CE66D01C7F6D140D8B33E2D3764EC969FD4E61FE172ECE5BF5B307628225FF6BE620FACF3EA74FF37724121E271FFD20AE23CCAF7F5C65294296653B6CEF61E5353FAA72776AD123B2EFD152F64227DB13076C6FD20F5A1F157CE5660B6EE1104AD7B8272F24D76D2F1A7CBDD2A072972D7297875F133CB8767EAA672F46DFFEA5A2FF1BD47E36F17A9EF3297D55123F79E5A2FF7B2F797D192F297DDD68D37B33113FE65D18199AD6153FD64E5FDB7DEAFFF57E7BDBA17267D07973F27E3E5F7F264ADF05
	857DAD2D92798BD1790B607C0D8CF07EAA515FA755A2FFABD57ED6F37ECE8EF27E2A505F769A113FF5AAFFA91C3F17F9FFD6747738CD64EF3B4ADF56305A5F5C2FB8FF55684FD5AF72D7B86C77E7637C1D3B42792BC1FF076DA2FFEDAAFF5D1C3F5F727EEA515F1AC611BF1F4ADFA8676FF1B7672F827D8D1F9179DB22729743793BBA44793321BFF71748DF3C4ADF72303A0DA81CE2E336E394AE164263A6EC6BDCE7256B136A6C73D7FB154E343CCE70D1290FFC8E8CBF5A0D1CD96A171E856EF73B10AB127A20
	77C0F88159FDCB6232A17C9B62CECAFD50A2680CFC1BB8135487ADGD1AF133FE009DB8B615A8C64AECAFD50F1309B2134AFCB5C9A305BC9F62172FAB55037036C76C9FD500D902EA33B599277D7707786F1BF147A25ECCD64CFA96D7FC87D529E707778EE3CC7A19676BE61F60EFBC6D9D90DB03BC7387596294ABDB8BC02730B7FF8032D996C9BF68EC1F377972F2B471469137A389558177001EDA8A09A10GC89941BAFD418556EF009655479AF85691117BA1CB2A347D90F63EC5BB786D75E15AD535FA2D09
	35C6DAA7A10D2CE9298D7D06C3B89A10GC8E1712301B5497D1EEF57E33F27F8947BBDBB9A18EDB70447G73C6315F73C51376190E9ED535EB2A6F74DB1666A3CEFF407CA50D4A73910D44FFAE719D577D3CF2AE17546FE261E4080B9F433E73669358F70E799BF2EF94B1EE9D04D308FBCD9A9FCF07F04684F2D53918B607380EBC4659A15CCE5CDEE99C5681E1AFF11F96E05A9062EAA4AD51900EA72EA09F6D1209EBBE0AF9ECA76E1F48DF66A4F24F1B253390A15CCCDC5DB1498E425D443DD602E9FD443DD7
	26CF9BDD06F691442DAD575BDD3020DD9CF1E395FA3B3D15E837013813D5FA3BD5D5E817F19339972CFA3B5EEA34ABA63BFBEA74F607EA512E0D6C2CB63D5D3374DE65A53B0DF5FA3B49FA348BA73B48863D5DE9BB5A4512DDDB235E6E25C634DBCFF63FEB525B5DF7866DCCD34845BA98D788E19BF1B33B502E05386FDEC23B01A92C1FB6173176C1BB0766E17B2F5A644AC37EAD2F32305F128F5205812226F00D74FD2338C65A5E7028A95511EF38C6FA65BFD46D0D1B12FF576B277C6B9FD6539A6985523342
	52655C025F51AD467D00DBCF466A1F87CC72FC0D9C7FD925BA1E3DDFEC5C250E1F4945B33D65DC7AD5DCBCAB0FF1A5DE3E36AD6F52356DD8EEAA199F782C4D08DB72B545A91F1BBEFCE2CF33EF3DF2ED6BE87CBB7D96560D85552CBCCB02607EG9CC148EF164E9874C1B88C9095046F1571B6AC1F38A0AC1F2DB6ED79946CD6DFDFE11D36FCCEF3577FE51D36FC2AB3547121F55A7299D2524B7578CADD5FAEBD48DFF7E786872A472F5959B56685EB671BA7A43EA4D8BB5F3C5B0E734DF608DB960C4F33DB7AFD
	E44C003AF886F2DF506F43B6835B1B0A8FD8BF4881E1AFA08430849083C8GECGEC83E44D04BA84508C6884F8812100E500D8C092A085E08A017CG96C0AB209BB086188708862C86A48352G997750983B24016D97E2FBFC58FEA03647A88762ED00CE4098A082908BC886EC83645C8B7100CE40583D723E9F362727D9590BE3D37650D8143DAF454A1E95333257A4C759A3221CFD553CC1B9930EE9545E9BF63D017A19F782D6EE2CDCC5B6B9CA3EC92A3C3A8D3428414A1F15A53BDF8E651E9959317BB82678
	CAD66E0FBA3E78AE2E4FF49BCD8C6C59E077177AE10A2ED8A59F761C3167452B70D9F7107FBCD53E2D9A8D1D2A745182FD4A3C48D93C573586B639FF47FDE6E073507BEEE303E763294F19AA3FCD9AF6FED503C9BE730A7D5E7F90400E0DFF61D9FF6C5DDFFBB65FBB3749C78D51190588B63658288A3F2DFC6299FF2C4C5919DBF8D65B21BB7029BEEF30D493F7C475DDF426195CEF5FF1827B6D91D0C72C84A482D2D9FDB18B7B6DC70B24F9E867E5179BBECBE631732E5358AB0272EE13F50E0D9577C13FFE65
	AC66737DF32C9E75427749D978FE70F1AE661386DCC66805DD050096C0EFA866F3A5CF1F0F54DDD47249AFE0E5B5649C9B9C9A9DB249F9D925FEACFBDEC2C2B12F3E82FA9781AE9A10GC8E17177E1DE1F1CE04F47754111414BEE5759A136298842324FBE29DF4D4A3178BE74D9130FBEED4035815C00B040CA002CF52654744B76EBC9C3A244EFG18E6C35D8AE81E0D9A361875577B6C1B7E6BEDBF4662C75882DC5256FF8C5B726E5928AD2B145E87018B85C48392GA9E4737DB234493C7F422EBC009D60G
	CC5E0FB6D7ADE833843898C092A08D109506B67915E8E3812E85508B8881C411CDA23DE34683178C588648F9G5A1C8750E6229AEDBA01F38342GAB8109E473C75AFB1B8A1CE98E149F209950BD87EDDE24774EA0604281CF8256815249E6810D3D9F1E8B6D9C2095B0G881D0BB69F50DA2FE86062G6B815B81F97350663F9B51468EDCBB408B188708A11BE5E750A68938CD000C79702C83DA6663FDFF4E213F6F7B777B1F1D45DD682397520C81A2G31006479782CF959632B74FDCED5C8E7C24F177BBEBB
	092F14780C877D3C5CA7FADA6A0F33F09644799E44F9534F8F30BEDBB20453817B9700EEC04B821C6B0C1DE2E73689572BF5F83CED2E6BAE37EA7AD2D9CFA99CDBB5025C0FFE6F9876238786961B42720F865CC7593DC0EE5702144F3C1CA776E0FD7836E94BC1D626B1901788C883E406433D85D843717ACC37CA646BE35BC04F7AFC4E1E81E907A9DE9C5B74692759000CFCEDAF6A46F9DB437DED2CA3BCD0DF36C3BA7B5B9B2E3DEFDD92BF992E3DEFC555F85FC2A1AEB59C4F8A3FE8E3FC7BC3DABFBD926FF8
	C86BA735967D8CC0DC62C35847DBAB9C3FB5025CEFDED30FF30C1605B43E5000FD697605687F061D359313948E1642ECCD3D9C5EE74779BFE6F3B7BA6E84F29E47A90F691C27FABB483F6F19CD7A713345CD6A7721F94A7545AF44FA43CD7546FA6052GD90B20BC816D0B305EF86AF3F68D4A4905CADE1FFD6C67709C3FA39F31C644FF6AA06A72157BED719C3E468AE65B619A73FDB868F27712ED46C79CDF097C12B7B93E8A79BFD6F2BC1D6353F0046369AC1D6537B81E4E328A6F6278DA644DE60E37515C5A
	C90E2FC3FECE6EDB5CF561790F0B46F89E4B71990DBD166551F1D68F51A9C4F40FAB0A54FCA5714F1FD673D5446F2DD773D662EF3C2D662B097F7893B56FBF936850D1B5DFCBFCA42753C63C7DB3B5DFC73C6BAFEF2A2F4B027C66F7B81E1E07ABF99CCF4F037B147F194233946F3D68672A1533C7EE95291FFF6FA2F9AFCD78A25CEB53F64AA8BE1A62D7669B4547D17C8B6F9B45A7D17C070679EF2278AFCA0C6237D17C8143747BA368EC994374E60AF7969845DBA93E6F24D1FCAB45F79A26F7D07C0F8D2F5F
	9B01E3C0A78A315F989201E3C01F95EA47005873D7E622B130C26D98D0B0B7C6362AD0BB863403CB7FCA21F68CE867EE7598D976C5757E2766B54777E2BB252DD763A2647AB2A142BF7EA777031EE4FFBF01731C394761732A6B0619F6DFFD88AFA54547DB861DEECF7F7192332544DCD1F98A730F6AD7671F3C76F8407DD75B15FC339E16BF878EBD31C7AED7EFAE16EB6B43D82ECBF345651A142BAD5782AEFED71E38DC2DF95AF27D0DF2DF64F278D3817BC322CA8B2DE7AEC00DC1247129822D46A738FB1BDA
	2055788E276128D9BCBE5AEE56EA44EB14B55EBC41B432FAC4566848C70D3124F1F63E389C6372359A1DDC7C2F724565E8BE9A289CE5CD2FD239251126FC33D653B14E6727E62DA6BEBED8D7EEC5DC7CBF6B4A6D5ACEF539CDC96F04FDCE77080BF45614224E6245283337D42CF3E129D667DF397804D2314E57CB836914B57D3C0C5ED3C95321F231267365DACD8FF171B74A451A3ED3AE5694EEC1CD912469D90BD8D33AC52B69B9AE7E7D8A31264E8A3126F31528A909B4DD2B94EBFACCB7F731E727BA7EA73A
	398BDE539BD5E2CDDB2BD0D366A32849EC95EB7A5C2A55F4165394E295EB7A1ED52CE9F9B5EAEAA6CD4FD50BB56D2C51EA8A66B4D55508B539EA441A5AEBD15398E91A2A95EB0A2955EAFA1A537433DA312643B631265F53DCCF8CE9AA2D93EB7ADF5DDC4F2B1C26793A399EDE539A5DDC8F2F69DF6AD1D38AE9FA25DE2C69C803D6D39157BE7D29C12C69E603D853009DB51917202605F63126843BD6D38F2769F53BD8D3C923D8D3F693EA6AA4CD671B441AEEB4E9B5BD4CE97ACE13D8534BCDE2CDBFBC031A66
	112674B3DACDFC3BECF5E8B57D9B975F6750EA625B658807D6935FAE2F6EC2CD892469D7DDE2CD66CBDACDEFF04FF857A53126391782E91247954A592B21725D267A7E834B5735265988F29EAFFD23F948E3E3978F84DEB76CBCE03CEE387F0071BE072183627F4C98BDE0741F9972B85E0F3259FDB0C5DE581587E886F4G86A2F18CEF4FC1768E407E31CEBFFEF73E371F2DD1E67FD267433CFEFDB070F53DFE70767FED37BB0B3D97DF76403B6E9073CF7F7B11456E0D4F6315C865FC32ACD69E43037D83BE59
	D4618FE73167596D6A13E8F97CAC2D8A77B104C462185ABAAB0E29C5C552FE7A9A7F3356237AFE3A7A1BFF6EDA7E9F726836792FE9D075FDEB6DB71F77DB36EFB28FF9DCB015EB87F1DCB0896E6F76C8963F3641AC70119229DE877AC2BB9BF714B69D5039267D47D9FDAF2D5169718C158DDE9EAA9B3DBED4663C6CABF3758E7A6A6B7FBC6AF6AB2BF5CC4AFA1DA71B5878F04B528B3B9CG5FD29CC3F8105A023025D86F2E563585FB38F5A4DB4FE86BDD76FB734717B7EB6B5DDCEE9D0933D9DB6F3E2D24172F
	6F776F986C8127E1BCD977772475F9ECFCAF1E2EE566A964369337B19FB96B23487D61A4F2EE62EE123FA86242CA10DBCF5CF932BB3C8C39D32548359197D100DC88F11114EF9CF1F349AE1D38BDE4D7329C6B0462423B9D47176B5694D614CA1D9415451E1BAF1DDF991E0C79B1947527173FF27578BA1C7D147E5F0FB0AD3D20A3E8B956472F3DFBBA109FA05B07BDDE5730EFDCE625C371145F5EE86E5DDE033247C03887A2F0BA27F0AFC660F469534C2F21F4FA69368877D084CE7730F02FC460F42D1553CB
	77DB615E0740693EA55C6390B85D973A73CB6EACDDC65D7452BDA35C1B90B85D10F08FC260F4F93AF54FF7166EC742BD8501537D2BF06F0091645F7B7359017BD3AFE55F3EBF35B11B55A1AE6887F50CFB157DDFA959527E5C3E3E9197755B7EABBB0B380FC65ABDA303EC5F185C269E319A35294B16E31B7ACB2BD9F09D314BE5FB39AD5CAB2DC3CF84EE93CB3F9C6762B6B7B0CD67DC6CBF24F1BC797EC21C2BCE2FDEECDA770B0A3D2C2DB29ACFBE3CC24E23F905FFBC1BFD4A733645E724F51CAB3473399592
	9F34C2BB1F7BD4934E6706C1DC5A8AEC8796CD735EBFDD24CE577118FFAEF6G3ECF82668122D43CF2B644A9F53AD8D5FCA2FC5FGC887E4C6E95325F0F5EF1EAA5E825FDBG1DG37427F5FGD0CB878883550B47CBA3GGG00GGD0CB818294G94G88G88G1B2BBEA683550B47CBA3GGG00GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG05A3GGGG
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
			ivjButton1.setFont(new java.awt.Font("Dialog", 0, 12));
			ivjButton1.setBounds(378, 15, 126, 26);
			ivjButton1.setLabel("Uebernehmen");
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
 * Return the Button2 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton2() {
	if (ivjButton2 == null) {
		try {
			ivjButton2 = new java.awt.Button();
			ivjButton2.setName("Button2");
			ivjButton2.setBounds(378, 48, 126, 26);
			ivjButton2.setLabel("Verwerfen");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton2;
}
/**
 * Return the Button3 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton3() {
	if (ivjButton3 == null) {
		try {
			ivjButton3 = new java.awt.Button();
			ivjButton3.setName("Button3");
			ivjButton3.setBounds(384, 320, 126, 26);
			ivjButton3.setLabel("Schliessen");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton3;
}
/**
 * Return the Button4 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton4() {
	if (ivjButton4 == null) {
		try {
			ivjButton4 = new java.awt.Button();
			ivjButton4.setName("Button4");
			ivjButton4.setBounds(378, 99, 126, 26);
			ivjButton4.setLabel("Reset");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton4;
}
/**
 * Return the Button5 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton5() {
	if (ivjButton5 == null) {
		try {
			ivjButton5 = new java.awt.Button();
			ivjButton5.setName("Button5");
			ivjButton5.setBounds(378, 131, 126, 26);
			ivjButton5.setLabel("LastKnownState");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton5;
}
/**
 * Return the Checkbox1 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox1() {
	if (ivjCheckbox1 == null) {
		try {
			ivjCheckbox1 = new java.awt.Checkbox();
			ivjCheckbox1.setName("Checkbox1");
			ivjCheckbox1.setBounds(184, 16, 18, 16);
			ivjCheckbox1.setLabel("");
			ivjCheckbox1.setState(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox1;
}
/**
 * Return the Checkbox10 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox10() {
	if (ivjCheckbox10 == null) {
		try {
			ivjCheckbox10 = new java.awt.Checkbox();
			ivjCheckbox10.setName("Checkbox10");
			ivjCheckbox10.setBounds(184, 274, 18, 16);
			ivjCheckbox10.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox10;
}
/**
 * Return the Checkbox11 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox11() {
	if (ivjCheckbox11 == null) {
		try {
			ivjCheckbox11 = new java.awt.Checkbox();
			ivjCheckbox11.setName("Checkbox11");
			ivjCheckbox11.setBounds(184, 305, 18, 16);
			ivjCheckbox11.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox11;
}
/**
 * Return the Checkbox12 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox12() {
	if (ivjCheckbox12 == null) {
		try {
			ivjCheckbox12 = new java.awt.Checkbox();
			ivjCheckbox12.setName("Checkbox12");
			ivjCheckbox12.setBounds(184, 332, 18, 16);
			ivjCheckbox12.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox12;
}
/**
 * Return the Checkbox2 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox2() {
	if (ivjCheckbox2 == null) {
		try {
			ivjCheckbox2 = new java.awt.Checkbox();
			ivjCheckbox2.setName("Checkbox2");
			ivjCheckbox2.setBounds(184, 45, 18, 16);
			ivjCheckbox2.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox2;
}
/**
 * Return the Checkbox3 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox3() {
	if (ivjCheckbox3 == null) {
		try {
			ivjCheckbox3 = new java.awt.Checkbox();
			ivjCheckbox3.setName("Checkbox3");
			ivjCheckbox3.setBounds(184, 74, 18, 16);
			ivjCheckbox3.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox3;
}
/**
 * Return the Checkbox4 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox4() {
	if (ivjCheckbox4 == null) {
		try {
			ivjCheckbox4 = new java.awt.Checkbox();
			ivjCheckbox4.setName("Checkbox4");
			ivjCheckbox4.setBounds(184, 103, 18, 16);
			ivjCheckbox4.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox4;
}
/**
 * Return the Checkbox5 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox5() {
	if (ivjCheckbox5 == null) {
		try {
			ivjCheckbox5 = new java.awt.Checkbox();
			ivjCheckbox5.setName("Checkbox5");
			ivjCheckbox5.setBounds(184, 130, 18, 16);
			ivjCheckbox5.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox5;
}
/**
 * Return the Checkbox6 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox6() {
	if (ivjCheckbox6 == null) {
		try {
			ivjCheckbox6 = new java.awt.Checkbox();
			ivjCheckbox6.setName("Checkbox6");
			ivjCheckbox6.setBounds(184, 160, 18, 16);
			ivjCheckbox6.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox6;
}
/**
 * Return the Checkbox7 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox7() {
	if (ivjCheckbox7 == null) {
		try {
			ivjCheckbox7 = new java.awt.Checkbox();
			ivjCheckbox7.setName("Checkbox7");
			ivjCheckbox7.setBounds(184, 192, 18, 16);
			ivjCheckbox7.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox7;
}
/**
 * Return the Checkbox8 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox8() {
	if (ivjCheckbox8 == null) {
		try {
			ivjCheckbox8 = new java.awt.Checkbox();
			ivjCheckbox8.setName("Checkbox8");
			ivjCheckbox8.setBounds(184, 221, 18, 16);
			ivjCheckbox8.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox8;
}
/**
 * Return the Checkbox9 property value.
 * @return java.awt.Checkbox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Checkbox getCheckbox9() {
	if (ivjCheckbox9 == null) {
		try {
			ivjCheckbox9 = new java.awt.Checkbox();
			ivjCheckbox9.setName("Checkbox9");
			ivjCheckbox9.setBounds(184, 247, 18, 16);
			ivjCheckbox9.setLabel("");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCheckbox9;
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
			getContentsPane().add(getPanel1(), getPanel1().getName());
			// user code begin {1}
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
 * Return the Frame1 property value.
 * @return java.awt.Frame
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Frame getFrame1() {
	if (ivjFrame1 == null) {
		try {
			ivjFrame1 = new java.awt.Frame();
			ivjFrame1.setName("Frame1");
			ivjFrame1.setLayout(new java.awt.BorderLayout());
			ivjFrame1.setBounds(54, 21, 538, 399);
			getFrame1().add(getContentsPane(), "Center");
			// user code begin {1}
			ivjFrame1.setSize(538,399);
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
 * Return the Panel1 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Panel getPanel1() {
	if (ivjPanel1 == null) {
		try {
			ivjPanel1 = new java.awt.Panel();
			ivjPanel1.setName("Panel1");
			ivjPanel1.setLayout(null);
			ivjPanel1.setBounds(8, 7, 525, 386);
			getPanel1().add(getButton1(), getButton1().getName());
			getPanel1().add(getButton2(), getButton2().getName());
			getPanel1().add(getScrollbar1(), getScrollbar1().getName());
			getPanel1().add(getButton3(), getButton3().getName());
			getPanel1().add(getTextField1(), getTextField1().getName());
			getPanel1().add(getTextField2(), getTextField2().getName());
			getPanel1().add(getTextField3(), getTextField3().getName());
			getPanel1().add(getTextField4(), getTextField4().getName());
			getPanel1().add(getTextField5(), getTextField5().getName());
			getPanel1().add(getTextField6(), getTextField6().getName());
			getPanel1().add(getTextField7(), getTextField7().getName());
			getPanel1().add(getTextField8(), getTextField8().getName());
			getPanel1().add(getTextField9(), getTextField9().getName());
			getPanel1().add(getTextField10(), getTextField10().getName());
			getPanel1().add(getTextField11(), getTextField11().getName());
			getPanel1().add(getTextField12(), getTextField12().getName());
			getPanel1().add(getCheckbox1(), getCheckbox1().getName());
			getPanel1().add(getCheckbox2(), getCheckbox2().getName());
			getPanel1().add(getCheckbox3(), getCheckbox3().getName());
			getPanel1().add(getCheckbox4(), getCheckbox4().getName());
			getPanel1().add(getCheckbox5(), getCheckbox5().getName());
			getPanel1().add(getCheckbox6(), getCheckbox6().getName());
			getPanel1().add(getCheckbox7(), getCheckbox7().getName());
			getPanel1().add(getCheckbox8(), getCheckbox8().getName());
			getPanel1().add(getCheckbox9(), getCheckbox9().getName());
			getPanel1().add(getCheckbox10(), getCheckbox10().getName());
			getPanel1().add(getCheckbox11(), getCheckbox11().getName());
			getPanel1().add(getCheckbox12(), getCheckbox12().getName());
			getPanel1().add(getButton4(), getButton4().getName());
			getPanel1().add(getButton5(), getButton5().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjPanel1;
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
			ivjScrollbar1.setMaximum(20);
			ivjScrollbar1.setBounds(15, 10, 23, 342);
			ivjScrollbar1.setVisibleAmount(12);
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
 * Return the TextField1 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField1() {
	if (ivjTextField1 == null) {
		try {
			ivjTextField1 = new java.awt.TextField();
			ivjTextField1.setName("TextField1");
			ivjTextField1.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField1.setBackground(java.awt.Color.white);
			ivjTextField1.setBounds(42, 10, 139, 26);
			ivjTextField1.setEditable(true);
			ivjTextField1.setForeground(java.awt.Color.black);
			// user code begin {1}
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
			ivjTextField10.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField10.setBackground(java.awt.Color.white);
			ivjTextField10.setBounds(42, 271, 139, 26);
			ivjTextField10.setEditable(true);
			ivjTextField10.setForeground(java.awt.Color.black);
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
			ivjTextField11.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField11.setBackground(java.awt.Color.white);
			ivjTextField11.setBounds(42, 299, 139, 26);
			ivjTextField11.setEditable(true);
			ivjTextField11.setForeground(java.awt.Color.black);
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
			ivjTextField12.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField12.setBackground(java.awt.Color.white);
			ivjTextField12.setBounds(42, 327, 139, 26);
			ivjTextField12.setEditable(true);
			ivjTextField12.setForeground(java.awt.Color.black);
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
 * Return the TextField2 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField2() {
	if (ivjTextField2 == null) {
		try {
			ivjTextField2 = new java.awt.TextField();
			ivjTextField2.setName("TextField2");
			ivjTextField2.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField2.setBackground(java.awt.Color.white);
			ivjTextField2.setBounds(42, 39, 139, 26);
			ivjTextField2.setEditable(true);
			ivjTextField2.setForeground(java.awt.Color.black);
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
 * Return the TextField3 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField3() {
	if (ivjTextField3 == null) {
		try {
			ivjTextField3 = new java.awt.TextField();
			ivjTextField3.setName("TextField3");
			ivjTextField3.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField3.setBackground(java.awt.Color.white);
			ivjTextField3.setBounds(42, 68, 139, 26);
			ivjTextField3.setEditable(true);
			ivjTextField3.setForeground(java.awt.Color.black);
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
			ivjTextField4.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField4.setBackground(java.awt.Color.white);
			ivjTextField4.setBounds(42, 97, 139, 26);
			ivjTextField4.setEditable(true);
			ivjTextField4.setForeground(java.awt.Color.black);
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
			ivjTextField5.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField5.setBackground(java.awt.Color.white);
			ivjTextField5.setBounds(42, 125, 139, 26);
			ivjTextField5.setEditable(true);
			ivjTextField5.setForeground(java.awt.Color.black);
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
			ivjTextField6.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField6.setBackground(java.awt.Color.white);
			ivjTextField6.setBounds(42, 156, 139, 26);
			ivjTextField6.setEditable(true);
			ivjTextField6.setForeground(java.awt.Color.black);
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
			ivjTextField7.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField7.setBackground(java.awt.Color.white);
			ivjTextField7.setBounds(42, 185, 139, 26);
			ivjTextField7.setEditable(true);
			ivjTextField7.setForeground(java.awt.Color.black);
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
			ivjTextField8.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField8.setBackground(java.awt.Color.white);
			ivjTextField8.setBounds(42, 214, 139, 26);
			ivjTextField8.setEditable(true);
			ivjTextField8.setForeground(java.awt.Color.black);
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
			ivjTextField9.setFont(new java.awt.Font("dialog", 0, 10));
			ivjTextField9.setBackground(java.awt.Color.white);
			ivjTextField9.setBounds(42, 243, 139, 26);
			ivjTextField9.setEditable(true);
			ivjTextField9.setForeground(java.awt.Color.black);
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
	getButton3().addMouseListener(this);
	getButton1().addMouseListener(this);
	getScrollbar1().addAdjustmentListener(this);
	getButton2().addMouseListener(this);
	getCheckbox1().addItemListener(this);
	getCheckbox2().addItemListener(this);
	getCheckbox3().addItemListener(this);
	getCheckbox4().addItemListener(this);
	getCheckbox5().addItemListener(this);
	getCheckbox6().addItemListener(this);
	getCheckbox7().addItemListener(this);
	getCheckbox8().addItemListener(this);
	getCheckbox9().addItemListener(this);
	getCheckbox10().addItemListener(this);
	getCheckbox11().addItemListener(this);
	getCheckbox12().addItemListener(this);
	getButton4().addMouseListener(this);
	getButton5().addMouseListener(this);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	// user code begin {1}
	// user code end
	setName("ListFrame");
	setLayout(new java.awt.BorderLayout());
	setSize(23, 70);
	add(getContentsPane(), "Center");
	initConnections();
	// user code begin {2}
	// user code end
}
/**
 * Initialisiert die Klasse
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize(Vector arg1) {
	// user code begin {1}
	// user code end
	setName("ListFrame");
	setLayout(new java.awt.BorderLayout());
	setSize(23, 70);
	add(getContentsPane(), "Center");
	initConnections();
	initTabs(arg1);
	disableChecks();
	// user code begin {2}
	// user code end
}
/**
 * 
 * Inititialisiert die Arrays arString, arBool, arBoolWork 
 *
 * Benutzt globale Objekte			: seList bzw. evList
 * Aufgerufen von					: initialize()
 * Ruft auf							: ----
 */
public void initTabs(Vector arg1) {
	Object helpObject; 
	BvarTab d1;
	absyn.Bvar d2;
	SEventTab d3;	
	absyn.SEvent d4;
	
	String Item;
	int i = arg1.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts
	
	arString = new String[i];
	arBool = new boolean[i];
	arBoolWork = new boolean[i];
		
	if (debug==true){
		System.out.println("LISTFRAME.initialize: counter= "+i);
	}
	
	i--;
	for (int j = 0; j <= i; j++) {			// Schleife durchlaeuft Vektor und fuellt die Liste
		helpObject = arg1.elementAt(j);
		
											// Da diese Methode sowohl fuer bvList als auch fuer seList
											// verwendet wird, ist eine Fallunterscheidung noetigt.
		
		if (helpObject instanceof BvarTab){	// Fall 1: bvList
			d1 = (BvarTab)helpObject;
			d2 = d1.bName;
			Item = d2.var;
			boolean bWert = false;
			boolean bWertWork = false;
			arString[j] = Item;
			arBool[j] = bWert;
			arBoolWork[j] = bWertWork;
			
			if (debug==true){
				System.out.println("initTabs ( Bvars ) Durchlauf Nr.: "+j+" ist "+Item);
			}
		}
		
		if (helpObject instanceof SEventTab){ // Fall 2: seList
			d3 = (SEventTab)helpObject;
			d4 = d3.bName;
			Item = d4.name;
			boolean bWert = false;
			boolean bWertWork = false;
			arString[j] = Item;
			arBool[j] = bWert;
			arBoolWork[j] = bWertWork;
			
			if (debug==true){
				System.out.println("initTabs ( SEvents ) Durchlauf Nr.: "+j+" ist "+Item);
			}
		}
	}
	fill(0);
}
/**
 * Method to handle events for the ItemListener interface.
 * @param e java.awt.event.ItemEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void itemStateChanged(java.awt.event.ItemEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getCheckbox1()) ) {
		connEtoC5(e);
	}
	if ((e.getSource() == getCheckbox2()) ) {
		connEtoC6(e);
	}
	if ((e.getSource() == getCheckbox3()) ) {
		connEtoC7(e);
	}
	if ((e.getSource() == getCheckbox4()) ) {
		connEtoC8(e);
	}
	if ((e.getSource() == getCheckbox5()) ) {
		connEtoC9(e);
	}
	if ((e.getSource() == getCheckbox6()) ) {
		connEtoC10(e);
	}
	if ((e.getSource() == getCheckbox7()) ) {
		connEtoC12(e);
	}
	if ((e.getSource() == getCheckbox8()) ) {
		connEtoC13(e);
	}
	if ((e.getSource() == getCheckbox9()) ) {
		connEtoC14(e);
	}
	if ((e.getSource() == getCheckbox10()) ) {
		connEtoC15(e);
	}
	if ((e.getSource() == getCheckbox11()) ) {
		connEtoC16(e);
	}
	if ((e.getSource() == getCheckbox12()) ) {
		connEtoC17(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		ListFrame aListFrame;
		aListFrame = new ListFrame();
		try {
			Class aCloserClass = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
			Class parmTypes[] = { java.awt.Window.class };
			Object parms[] = { aListFrame };
			java.lang.reflect.Constructor aCtor = aCloserClass.getConstructor(parmTypes);
			aCtor.newInstance(parms);
		} catch (java.lang.Throwable exc) {};
		aListFrame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of java.awt.Frame");
		exception.printStackTrace(System.out);
	}
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseClicked(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getButton3()) ) {
		connEtoC1(e);
	}
	if ((e.getSource() == getButton1()) ) {
		connEtoC2(e);
	}
	if ((e.getSource() == getButton2()) ) {
		connEtoC4(e);
	}
	if ((e.getSource() == getButton4()) ) {
		connEtoC11(e);
	}
	if ((e.getSource() == getButton5()) ) {
		connEtoC18(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseEntered(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseExited(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mousePressed(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseReleased(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Wird ausgefuehrt, wenn der Benutzer einen ScrollbarEvent ausloest
 * Liest die aktuelle Position des Scrollbars und veranlasst fill(), die TextFields und die Checkboxen
 * auf den aktuellen Wert zu bringen.
 *
 * @params java.awt.event.AdjustmentEvent
 * @returns
 * @version V3.00 vom 17.01.1999
 */
public void scrollbar1_AdjustmentValueChanged(java.awt.event.AdjustmentEvent adjustmentEvent) {
	int i = getScrollbar1().getValue();
	if (debug==true) {
		System.out.println("Von Scrollbar: Aktueller Wert ist: "+i);
	}
	fillWork(i);
	return;
}
/**
 *
 * Uebernimmt die bvList bzw. seList, die uebergeben werden, in arBool und arBoolWork und ruft schliesslich
 * zum Anzeigen die Methode fill(int) auf.
 * Weiterhin wird der "LastKnownState"-Button wieder freigegeben.
 *
 * Benutzt globale Objekte			: (bvList, seList) arBool
 * Aufgerufen von					: simu(,,)
 * Ruft auf							: fill(int)
 *
 * @param arg1 Vector
 * @returns
 * @version 3.00 vom 17.01.1999
 */
public void update(Vector arg1){
	
	Object helpObject;
	BvarTab d1;
	SEventTab d3;	
	boolean wWert;
	int i = arg1.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts

		
	if (debug==true){
		System.out.println("LISTFRAME.update : counter= "+i);
	}
	
	i--;
	for (int j = 0; j <= i; j++) {			// Schleife durchlaeuft Vektor und fuellt die Liste
		helpObject = arg1.elementAt(j);
		
											// Da diese Methode sowohl fuer bvList als auch fuer seList
											// verwendet wird, ist eine Fallunterscheidung noetigt.
		
		if (helpObject instanceof BvarTab){	// Fall 1: bvList
			d1 = (BvarTab)helpObject;
			wWert = d1.bWert;
			if (wWert==true){
				arBool[j]=(true);
				arBoolWork[j]=(true);
			}
			else {
				arBool[j]=(false);
				arBoolWork[j]=(false);
			}
			
		}
		
		if (helpObject instanceof SEventTab){ // Fall 2: seList
			d3 = (SEventTab)helpObject;
			wWert = d3.bWert;
			if (wWert==true){
				arBool[j]=(true);
				arBoolWork[j]=(true);
			}
			else {
				arBool[j]=(false);
				arBoolWork[j]=(false);
			}
		
		}
	}
	
	fill(getScrollbar1().getValue());
	getButton5().setEnabled(true);			// Setzt den LastKnownState-Button auf benutzbar	
}
/**
 *
 * Schreibt die, in der UI veraenderten Listen in die internen Listen zurueck 
 *
 * Benutzt globale Objekte	  		: inVector ( das ist entweder die seList oder die bvList )
 * Aufgerufen von					:
 * Ruft auf							:
 * @param
 * @returns
 * @version V4.01 vom 04.02.1999
 */
private void writeBack() {

	String FrameName = "";
	boolean wert = true;
	absyn.SEvent temp1 = null;
	absyn.Bvar temp2 = null;
	FrameName = this.getTitle();

	if (debug) {
		System.out.println("Anfang :XX"+FrameName+"xx : Ende");
	}
	
	if (FrameName.equals("Event-Liste")){

		if (debug) {
			System.out.println("Fenster als Event-Liste identifiziert");
		}
		int i = inVector.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts
		for (int position=0; position < i; position++){					

			wert = arBool[position];
			temp1 = ((SEventTab)(inVector.elementAt(position))).bName;
			inVector.removeElementAt(position);
			inVector.insertElementAt(new SEventTab((temp1), wert),position);
			if (debug == true){
				System.out.println("writeBack : SEvent "+temp1.name+" in seList gesetzt mit Wert : "+wert);
			}
		}
	}
	else{
		System.out.println("Fenster als BVar-Liste identifiziert");		
		int i = inVector.size();					// Bestimmt die Groesse des uebergebenen Vektorobjekts
		for (int position=0; position < i; position++){					

			wert = arBool[position];
			temp2 = ((BvarTab)(inVector.elementAt(position))).bName;
			inVector.removeElementAt(position);
			inVector.insertElementAt(new BvarTab((temp2), wert),position);
			if (debug == true){
				System.out.println("writeBack : BVar "+temp2.var+" in bvList gesetzt mit Wert : "+wert);
			}
		}
	}
}
}