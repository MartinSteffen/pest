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
	D0CB838494G88G88G1A2BD3A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E154DB8FD09C57F9F7A362282D6AA8B52E5598271A18E95DD66DA87635A1AEEDD5D713E89A4DD453AA99B9A536522A36A616B324D5B3CEABB74B0190311104A5E42398C9E0A199C9A738636F71C78A883040C2969684F1AE96375CADF0F07F385B839451265F5B375F3B5D3DF79CCB69CC2B19CFFB7BFD5F7BFE3F7D763D6F6D3EFD8825E7B69E4C33168AC25EC1C17D571CA78865CF88426F84FF3C003A22
	8A17D5507C2B84F9D3F876F4819C4B40567F2C4BDAA2F4C40B605C866765FF6E323E8D3F0F8BBD51BDDF009FC9F03241E64BDE5336B4CE359FF18EA967671B0B61F884448E3AA1100C8A02FF3539CC55AF40799175B711BC08EA2BA4BE11C019C63FDB759340D68A122578381A0DBEB2685702EC847931426C70B127EA5F61788DE1645FCF959EA6B81AF6A8423B9423CABD8A42BF9FA4C94897BC87A1A1C27B404C645528AF90EBED6D77C56577A3E2C0CAFF17705890A8D27C84E1944EAB4E925DBBC1F26D3B60
	BC972468AC72795A3B6DAA5E3A5F7CA2A46BF4D2BE5E3314475A33F1BE3BCEBC1FC7B1FF510C185B4EA22654C6B005AA17358C44DE057A976E92FDAF1C3BC1962A104B0B6711CBD6680B42686964DC56D7D18E162AB897DB4173F9A7157BECD16ED5AE58CA4F392C55A07DA0FE10F5A06FA87DAB643F88474EF139AB948804021D6170B53FB7F2B364157C703B3595AC2A6E366489C53C02C07B5063819B078FCA66B9FD7FA1C22F6D0E00773DFBFD0610AEB7969E64DCDB6911F305D9EA0C6D67625766BECF7236
	A6C472368F7424DDA388557F36224FBC9F5707AF92FD25F24F49F959FBAE6B56EA4CF35AFD64B2F47FF3426E411A42C3BCAEFFD0DFE83928F2399867C2F37CD0086478D55017DEF2D9EDA05DA0DE1034CB18632F786BD36538AFA8CB1120689F0C0D074508FCAD6673FB25081A6B3466E4395EF8A9B14FC7D8BE1ED37A1D65924D47C1265F236837E974B4CFB30A1E1C67002D43867DD271FFE9CC492B0D7A1FE3F12CEE22FFD52327F17ED16957643C7FB2746B9A171566EAFF1864AA3356E55D82328BA4972444
	8EF933E32E8A82354B6C0F831ABC5136FF9AF88B0E52029C917B02D1D98CFA24E1B16293477DD2D4D0FD3E9AA0B99D57357B3AB2CEBC00F1DD8ACA91D1760502909D6A885C14A8755999F81378C4A445BA648BC8G90888B98B7A7F09C0EFEB1AA7FAC6C95E5A96EC16DFB82244F4D0943909182406FC5BB4915C529D17D70FB4A0EF9BA9FA13AADF03E0B65E5ACF88C0EB72013C87E5656EE1F075090A377F0BC7EF2E80A0C8970EFF0C01D85A92EF3D933C12A6B21AE0374B6C0BC106AC638A7A05BC0C61ADC56
	3DA04EE62889A03BC1E4274BDA5062322603D4353A2C96105EB617B587243F5DE55D590E6367FBA3B8FE9EA8FFCC48FE64BCF77CD87E646D4251AB341F4AD7626307725E24704EGFDB6C88E488110722BAEEBBB48A848A2C886C8B6C88E488110721F029DE494E491A483A49BA48764GC8F98758C1C6C196C1B2C032BBF07EFBAF38B1AFF96DA2B9435FA4EFA4D764B7499F498F5AC81E1345C0A177039CC94EC9CE1379EDDCC6ACB3C26E114239CD1D9FDA697DCB550E74857CCD7A8F69A324BF7437AF5D1674
	9323AEBD497512BE382EB951C67A69085A9749B93DD73F3E643DFAC3737BA8485B6AEFB2D64FEAEC87171133FC75F882643DA57C8E2D62FDA252289EEB5563E59047B25A1D527CA66311548ED2F36BD3343B4451DDC862CBEADAA5C7CF1EC7C8DDBA23419704A76B35F33BFBFFB5379EEC9ABCD71853C16F717E8E63FC7A4DA819BBCABB61B989E420936B5F67039502B2AFDC9323924E8437A2D044A5EF3C9AD25FC7C324AFC8F3D2D06E1642D2508BBF22EA9D7EC208F0D5670809100075BBD3511339378B0AF8
	1469FFAF74A655C38C6D0C037627943BA84B915FF8CC1686A5D97685A7636D1FD138C4F553FAD42D7D0FA94F0AD3F00D8FF4E2EDFA210D68B261BC0B693E2B68F6687CE65BC6125CB35256EC9F4CB23421983F1294A35FE4FC5E9824982F0C752AFDA4DF75B9247627B754FEF6D420631974C5B23ECF2A39BD2576BF52BF4998BA4BE29639C94C7DF0CD655760F98C448F128E128D328724388B66C910D110B410CDA0BBC18E00D4F5C33D8311C1B23AB15FAF5F2D48DBBE4FA3CBF0555625D4B5617F1B50DCB50D
	11DCADA7CFA7D2646A5CB2B017134F7F0BC26B43EB6164352AE016DF2BDE1F65572A125925EBD159AC2F96D15B19D972FC6989850312D2CC19D9206F5A5F9BA21CFDC11FFC47671D1464C15F4FA5F83E69A6F56C1B4AB36CD6783D2B9B1F8D674F62DCD05D7328E0FF250C7B1ED75C5BD4E86F2135273BA7BE97D0CE17A2E4AE107CD2G1252950A85E52AF7A84F472ABE1A2F682A2F344031227ADACC16C341FA1B28DCC5CD45654BD7AB583200ED1CDA47BA95EB8F49FA347A4270853BC07B6BF161D331B1DD1C
	C0A89615EA147F3B7CBE4F0C642D1E9667449AF1DE2ED16EDA4DCDE2D3C2DDD8B69A45FA582145F22C8216E36C2636B5E0B9D42C2D2DCFA8D8031EC8486F9F97A38E1B681D0EC5E512431A784FE1519F133A264460E4A2FAA77362D2083250E31D7CF87DCB87EC833EF5AA5F3F6C576626EE95F2D317101BFA95EBDDEC0D0255B5A5F9E646C38B8E1BCF1682B5643FC1995E651224238F6CDCBC8F46996BB306B0E60045A0575E2072916775FC6AD609CF1DC9BE0DAA9F79081ECF7DAA7129B74927C96553FC37CB
	4727416689854221A0E910842AE12C8BDD626330D90D77CF7F290F5718BADEA3AF1ED30DF77010BEDED36AF8CD3CF8ADEA3CDF6AC95345EBDE257CB71B4C3F43C179785DFABE4ED56263B44B270E72F9FC31DB4727A5F53EDBF879F65453F8A7AF3C2F2F877654819DF6EE44861CD73E64B644F4ACA326039B3391E3BEF77CC1FD4C55AA948E33154251041C42F9EB8CE3F3F5A8B518E404E33B4F3A46B036D707CF23C9BED89B66EE2599EA436A70E9CA6063D81AD00BDA7B2E2599464E6A70E9B15B035431770D
	DB8F9A475EAA75E93BD9C6EC6CFE597620F16C2E92A733CF8871317F2D2B22667928E1951E0F9A0C4FC7ED976A1A58F3F171717B9A3C46D540EB5463992FBB5EDC39F09C4F675E3A7F3FFB5DCB73000C50F52A23B172EE6205F716851075BD38CE1595A1F6DC27D797283A6039183ED6012D68FD724E7403D02966BD68499E7A9DF6BF9C29FDBE4C338F3C0F76D73976A3ACFE556C5276019476C52E5D4B70DF635A97187D3FA2BC7BBAE67F91579E473F94DD5AFE07EB5F48626F625AAD4C6E12170E7F699476AF
	F16D5BD87C3A984F1E436CCFF36D7178AFF06D392C3DFB0EE7AF380E766FF06DE54CBEBE3FB47EC3A96C4F24303F1842FEEC01E737B1FE8FF36D9D57719B5961B9B27E06605C8F32F89D475F77A347B5632FBF941A0905074897C07CCE97AD01E3D41A0DC9C10FC48CB724FB6AB73EFF089EB3581437D7766D7065A8D94372EBE25235114DA25107A33E009839C79609553D92CF6976C12C870EE4DF45E6B8EE852175EC71E6A19C15FA644035160593E48D0555A98177DE143773626540B197E4E3AF717940DD2E
	5A0B603C822423177AAD76527CD33F43A27A6D037302BE17352A0F7A397B34FEBB18DF9A68B3C032D53F628F34FE9B4651EF9474B2C87A8754EF074EEF4203FE7BC1DFFC836E798D6AB7F5C36BF7410BFE1BC03FD56337C76777ED66D75D8F575A9F77F377EB7DBEA721DFA66833B5FEB9BA3F0189742B386932BAEF467DC6EEEA7D8AA651AF8374960D5FCE1DDF9673AB7B50E535FF98779B78D06B671FC23FF450EF5278ED5779D578502F64164BDAFDAB6E57FBCB6B3713792D83FD2646EF2B4EEF6DB46BAF83
	50DF8662FE9D83DA3F6BB32C9F00BEC363172D737BB766A7FC8475864479917513BF527A7D199F7DAC205F2E715B277A59EE933FD7589E1EDA26AB88925D5F424079F281FEFB3AD9225DCFA4FC260E3B5EC8A4FC1B46CF3BED7C7654B12FFCFB813D65B6561389D9DBCFBA2351104727EC81D0EB420918F6AD126AFE92FB9D0E933EC8D46E8BFA25852C9F95B142156C91D0556AF87F7DCBD70444BD8FBDDE1F9C0A60272E2EF96599E4A8549572CA3DD0023A58D26D30CF1AA77DC659FFF4B94C769F5D4635583F
	7FD83B37C9361EAEFCA3C9EE04C11A130A41F8EEF273F1EF539F3258EE30D35D5333285B40F48D91546DE03ABFE23A22A154BD94C5DD87539D15D13740F4FB19EEB35379E2285B4BF4EF4D212E62E728FB1469C6182EEC9EF56B192EEF01650D697E0A6904E16C8F476778EB52A767489E3B99B6171C1AA3F5FDCE1DBF68FD7E4AF136DF8D9FB2DBDBB57B553E2BFC8FA43E4EE15CA376D4AB418B4379DA100D4348ED289DFD33186F351F925FDCB8AF3A83BA903A9FA76A46FDBDCE223B03F3EC3DD3B74702FEBB
	C86E9D1C1B3E65A65F22CDBDE133BEBDD1C93E69A4D981D7476C2157BBD46C87048CE10B43D9F81453AF572FB9D3D8B022560411F83FDC5B7DFCDE86883D46A6912FF181FCB60F60B51675722EF16F88DE6363624A2F7137CA63571830722E6E057AA163F5GB09BDC486BE41316175FC5F92DF7A1AF77F8A79C7F47CB740C6B2193E40DECB5166869F57D45B8DED7A6F04EE157D557404BF76947F8DD751E1567DB5064BBE1B5DF4D77E7BD482B9AB05D9FA32F9F1E56725A70B165154DF87D40C36697132F75AA
	2FCFC51B613842D5FF1577EDAF725EC1B87F87722E2D64716EGBB45FEDA07EDF25DCE45AE1490FB88622EE3587FFA19073D15E1BBF558A6D773D56CE79876AE08DBB20A5816DA9EF66FA8E2776830CD2E8E2A5877A690FB946226B3EC0F03073D1DE13F2543B6399228E2DF1EC46C5D90376C9344BED64F439E7884316F1DF0EA304D2E7AD16C6FB0ECAF444DE0585B9BF858BB99769A5DF51B7CFA20DE77A3D3083D9762D65CC56C7BCDBC6C113B087D248E5B641782953B4F0758FE081B4930AFBAF958B98C7B
	5BBAEC13AB1BAA76EB8CBB9762D60FA176F3ADBCEC7798E2FFDA565EEF330B1894BBFB9A31E5083B09E12FED63E16F09E3376930CD96B60A9D1EC16CD7A12E5D0D584DD7F858D3EE06DD2F43B6D958A8F6159F3197A12E05E13FF815B7C76DF363DC702B7E154FD14376BAB6C719D9D9443D53AFB14E02886FC4A2F27E3BD19E67300879FAC103EBE6E5156D4B2F533EBF6D1D2ECC7A7E14A652E7308D22717D29769EF9A63500FE1B084F4B0F4E11BDA9787EF49B5ED8606E79491A8635774C131C43F333FA5EB7
	CFF01567E68177935505177ADB8323640838C6647FB949D9AE1C970F3B2CD5A06DA0236348ED47FC4AFD79BD8BCB6C4BFF5A942F45F15CCB16B5EB265DFA562EA5E226DDA6EB770C29F6D92C5D43D1B36D3633F6253219F63BD93BD7CC355B4F5A85E3E65A95F9305DBBF3E65A1533F699265A59D93BD373E65AF533F69FAE18E9B7425A7D3529F6FE562E52E9EC7700A73EBFB4DD739B057EBD48F9656FC71E74503F8749E37ADA7AF7A89ABD2DF5D94A3BADB95F8236AAAF623B5A0D78EB3DF14C8CEFB27CBFD6
	5EFFB7FBA9CEBE53EFD174DBB4FA0A5F2B6815F58A30B5C808FF4FED445FA069B1AD06F35A6EB3896DEA8C673B8C6DE86D7E1BD6328F5230AEC0BFF0893876DBFBCD3B761B8BF1CAA600B3C86F842E89CCBBF1CD40BD01B570597BE41DED831CDBC036036C85A91E04E790107EC92C05E796F8EBD07ADA18EC81AA3EA71A6621DDDCD97EBEC8E8E74C1FFFD25F0EDE772D5EA2B5C65AA45E439F2D104361F1E33B5306736CC95EBD1CF615A55EC336590E729CE8C21EBB994F2FA560AD0F67B7925A99F3D5BC454B
	152F817BC85594F2F84433B28ECFA434B366EAF40A172B6F573E1D18AB7C382E72341F41DC490C6708F7E5BCA7925A99F9EE6266EA2292F33515F1A816D64661A7896D0C9C8EF0B9B8EA10C3298FB9FCFD059C1ECB49215F476370F2ADF270B28E3F18D89907DFCBE8E76410416570F987F230B08E7513AB635019504E48E19F17C3DFBDF2A818C68EAF2D10433F246450BE4D63105F089C0698074726D64661F7935A99B92C63F2F82A89B9E4B28E37FCAB6370C9C2BBA3079CAE87114DF57B99077C6915F1F8B3
	211D1103ED06472124753FE93B7A28A82EAC5FC6C4B47895D151E04489493A9933B13BEE42E6BD09B361CCCC065D180D47B0C7E68E19F59C12E3B26C0CB9E1B6BA5BA29ACC22410992B2E2E8B89A198972598D8DB482028693F448486EC28692DAE8015850ADB44A07123D2F2A6E6D772A2A0B6846BFFE276B7D5EFD777D6AD5552BD76F2BD1C343E5547043DB5430FED28D57F5B5BCD1059A42C843554B3726A1C813CE2DA1D6D743C0B5EA58CC9A7E643EB58DF6CDBA3506AC371E06A3B528416A266F09DB5470
	46249A5C3A9AFE5F019A025CB4CF61866356C0B8560DED2D69A3EC9D8CFE93B7FB7A86FCE67C9E360E32F1A34FE0DFCF5FGF295925765817BC1DCB7FCD69A0B0B871F496457B796F8AD4978187EDA12A9E37AEBC9260F992FFB1BB396E82D092C6F995277CEFFDB0A4CBDFB85B98BF14D0348D909FB0E38C6629E98C22E1D380FFD48F909CB9DC6AE0838E9440593D7F2953915447D83F1EB0933DEC3EE83F12E916492095BC25CF6626CD29D15BEG5FB6834865D4B02E81426D83F81F66CB7BF74847B66E7871
	4AC06B6F3E8B48F93CF0DB73104F59AB0DD51A60FC43289C5A2A319CD6D3B92C29E1E328F1947E1194E65FB94AF7F04DF725156521DF9EBD5FD99EFAEBA40FC87C53996FCA4F565010EBC87A28EAF07A1CCA5CAFFD6CDBD0338E6B3CBC8FC86E1FDC5539D731B96D7354333542F23B7AA7F9BA634B4DCE28E564EF4857EB545F10ECCDE99214C52A873FA1FBCEE19D166541F1655DE72439D690F68302BCF8BDBC1AFE829E919EFF992FD20E653AA3F6BCF0BD757C38FEBD7513F1D6E78EF906873BDC6A3AA8E1BC
	13627CDF252CBF18951BEC7312145E679C6CF1E96A32A4A93D9C97A87DEF478355F772B599F2725724046E2716C63E0CE2BC62FD97470537AA47323F320B3C3FD272775FE5FAE5EE764AE98F42EF1ED74CC37637370B77E7A5FF83D623EB6860FCB5FBF9FD3102BF9B7933E59B796B667C0D40F128977B5C67F4737E4A485F659CA3FF111C3F55023EFA976F2F1C7C15959879DB47790B977C25DC627D5949DFD221113FA44EDF6A955E5FA3023F8A72372C5848DF66953F3FFC415F57BDEFF17E0A0B505F79D2A3
	FF351C3F96415F073D023F92DADBEDB372574B793BAE787BB751DFA97AFBD477FEC64C657CC5897E027B84FFD67A56B72C1322B9FF31023F4A2F85FFB674F7285248DF8267EF1B606F37223FB27497D3E564AFED506FAFEB10777760E541DFB97A9B2AB672D74479EB907CB95D023FE27417DBE3642F157367967C9D6D977C59515F7B9DC67ECC1C3F30C17E9D717B9B2C2FF6044299D2F8540C6FBAD79D33156B6C53835DCAA3DA1E9F58A935BD6B41706CAE64DE1438BA605EC8C1EE0B549E1D866179E457A335
	BD5F0770EB44A5CA5CB1E80C7C8DF1BF105A2359A0EA9379BBA535C71F06F0F9AAF207253667A8588DD15A8809FB926CF6125DD9F91E9A685BC1F64FC85C3FC338026CBE135A23FF85FFEF92F7DDEAFB323910AFD05A9D9257857E3E3F8B2F511F6D6CBB426D9C758CC3D9A58ECAFDDC629CB0659A2C9F44F145072F31390217861D7D703A3B60F5F519E3062436ED8536A50AA05CG68868481220630CE1F6E5066F70FC3AF3FF7FCEDA932DFEFB57ABD8A1C9D508A9881047B506F7C7FE56D91F8D37BD84301B5
	F06436CBE1AD4D0973E15BE52CCE1A93876134E16006316D62BE253EAF6D437EFB3109BBC648E389CBBFE17EBA07653104EA62E3A4FEB3474BE38CEFCBEDA7966E3D5A1652B2026D5FE0BB36FFEB47106B175A495D909EA16E3254379D8461B0629617E05A9544BDA675CD45C2B801B83754CF33954266F1647E318C539EA4EE23B46F508E61966226D9512E17387BED18C7A8F1CD59E897CEDC3FC57A660570FA621E1738F888A791F7C78E26CD3D0E5C93A734E94F9520DD9E590D96EA6D5EACC23BA6324BAB56
	5ABDD20CF6DE32FB21C4EB67AAC53BB0321BE7555AE55B50AE1A6CEA4B34F67FCAE59FCFF63FAA575A5D20FB43FC8339FBAA34F67915E8F7146C4ED5E96DB6D521DDA359EDF7E86DE652B7191B6C3EFFD2EBD7D103F6F3492E2BDD9A7B0770BA628ABA50EE83F1DFFC09F649B7300E59D2406AF8757817072D1DEA12AB8F79D9B659593314BA5116128530CE607C669943B83F59D1354C3C35BDDBF7FE734A87ECFB1AA664675ABD611F3BD01B0A731B3F144A1825DB817189930C8B956667307AE716D99EEB1163
	33AA79F8760DB03E0B0FBFAF44B33D05C2FA13904F4A633892AF1F5B4B99F4EEBB161B7367855EA7B36F9B791C1A145F4DE79E5A3DF6564FF64AE5976DE0E59784F19100555F6077DE53C9BC6758EF701C47CE2A4FB9DBB82715A75567AC1E534FCE2A4F59164A472757284F79B7CAFA39EE36562A6B66D4133FBE4EB4852A1BDF156A7AFC13FA7CB7D9629DA675782F298E47FF1BA0EE15896F512952B77041A9D05FCC213A341E1E21A9788E39779356BEF1C3588488832C842C85448112GE9F740BB87E087B4
	83FA81A6C0B8E095E09DA081108CC81F8A7981EA812DGAFA084908908862C87A4824CC1D0F7872106E7A48DEC7D8EDBF343566730B5BFF9905F88688504GD6G56819281E953A09E5088688504CC13576130B5B6354A5A983B322625C8D90B12272CA131A8EBBFB215B59BCA7F791984E50FB626D153463BC34B5D8CD83931F2B53249D372CD6072726AE861414A1F15A53BDEEE651A6959316B982278CAD42E0F50EE380973CB1FC493833BB7587545F01A62FF1D724B6EB3F63F18943D16EF11FFA417EF2BCA
	C3AF17BE5AC01FFC2DC369DA176B589C7EFFDCE786B6AE3CF7929B5C2B0E5F7729F012B4ECBFA956314A5E8B6CF95F97400E75CB615EFB6C1BDC3DD75E21C97261E13407A0027579D5F361834AAF6E39474A1C6D0105FB273D77ADFC727B7F1DD045FD409D9B6DB1A63745175A31AD3E826A08B500F840F6C0D6B03645039C52FCDA67C5179BFE9D41EC7F69ED5274C5E5BF25C65607FB895A6AEB02194FF029BDEB026350E0EC732FAA41FC4C53615E87D881ADG5FF44C6739D2EDBE25EF7A7319DA46827D4E11
	3E7E61FE331C5792293D456E17306918D7EC99669585DCB4A08E108C488C413CDEA9E77747553E213E0BEED7DDBFDB6483E159679E29ED464A519E02BEA32C68339138F640F5C084E08DC056BA1DA5D34A35AB9B6D63A1BE8910BE836ADAC0738CAAD70B76FCFF3457FF3EAFCB71C3ECC2ACE95B19036FF26F8C5436B887738A82AE9C908D0883A413CDF7815AE44EECCB4987B4815C005019E873E1915A2C84EEADA081E03E935E89F7224D3F9423CDADF0ADG9FA08C301AEC2E17224D86609281E92170FE81B4
	0622CD198DEDFA013B8E08G2C8144134DAF68BAEE87AEFD96149F2099601D05B6E1F47F4E85AE0A71004D005459E8D3DFC95F1B4059812D0091C0B8597C3A8AED22010B85EC853CB3874AEA8E5AAC276F56A660BA81A6C0A4E0AD59B4D37FFB82F05BG8767423D8EE8190B57FD2CDDFB5DF73F6D3FF7CEF7208F9F2489812CG2C83A44E45FBCD1AEA2E34FD36C9ED6634F96AB9F9BBA47E60BC751C3CE0BB4E4933C0DC50BC9C477CEC9FEB33A5C2B875AEF87E81D6C04BDDB876F87F8D36071A617C31960F37
	49F5556566069365F7CAD7454D4CD3E4105B46FF484136F1EF6F92F36891CB40F50D5E3B64775ADC65B7AB73215DD89F9EB0476DE7E53A966262816679D097G72818D73717CDE3D11AF1F9FDB16D96773B93BFA25951FF8F2EC91261FE69DA5F2B967D73372EC1D2FAEFF1B44F74FD7177F638EACFFAF44ED180FED2C4FCF5EEC99496D564FEAF16C4D9E06ED5942BAEC4B3606217FEDF52C1E8E22706F24B01BE3AE07036BF0DC0C595C0C0EEF83256F1B7238157DC5A7037CFCADB9256D13FA78143096C1B93F
	58B0FCEE275663B3379938E4C06682A8CFC06B82FCEE9F7B8BBB87E5A7BFA52F4FBF75F3382F5D87E7D9BF29FF97BE5465AB745B623EF455454C3645B562BB5367F2F713ED6AD901AFC1BE62AD01AFC57ECFA582CF7B5AD4FDA07034374C72EF841E76F68A6B9078F264AD960137A37F65F1012FC0FE7661370573427D90970D08BC166363AAFBAC4B8FC7D9BDC03B7250B5AEBE4673A544BF7FB14F17923F2712672D44DFBB4073B662BFBD47737EBDF25E7B10674B09DFAA6834936F781C67AB08F7DDF80BBF2F
	A264379C92F83A9FAEE789BC5D8F6EBF7A6FA95CDBB0788BBFE7D37662780550A7BB32C0DEDB9231G571E5CBF21973F0A625F3F22979FC3714E2F746263A83E7CA33D782D947F70893DF873C20CCFBF2697FF1062BBF55367D1FCF536DE3C0362EF9C570BEF26F817EE7AEE0A2F52CDBF32907BE01E34E33BADF4A17641BCDA256E03E177DF01997BD0DE2AD27741CCC961638FBB54FDB0BB0474679C6ABE181D3B78FE27773F60578355BEF9F48F3E275475EA4CC2E54CE0213FCFCDEE07ACE21FCB4AFDB72BA4
	97FE87DC57AC349A698CFC946465B674B95D1E1E5CFCCBD13E2538640F18FFD48F1FFF5C53C7832EC7CADDA4671B297C763E7750EE39DCB716E039B6AF42F2CDADB5AE5732D2F539E68B713D2546653A24D4DD2E2FA957C55987300C7DC1CFC9914E997BF599EA8CA10D16F235468705EB7B17F23546C3020620F235C631FF6E11F235C6BCC7D96391BB53486A91D9638F2D28F1ADE9CC329A17E32ECD2D51A94437580C4BB15896289CE5CDB332D1D3B2E9FAB4DB2DA9C77079D236DA1398FF582256F4CC08BFE7
	D1EB3A3213AF374C9C46DDF23A07DC2473897AA6AB8AC71D2F64986B4C4ED56B7C2B907FF92E314E293901F44A1AE6962026DE5294DDE02C696582352685C2FCD62131267305461A9A0AD0D3A4E99AACB25694D52456742C903F3148D8D3DA3131265F942326B85214D3E22C69C209DA536E1DFC7C3492E3CD0F1698EBFA3094B525AFC6CD1BB4F51A28E91FD52D29CE50D4E3B556F445EA2C295D061A9AC953CC1B3126B51A7AE10A2069D5CD7DA0EA321499EBFA3B9CB519C8D30526DE95B5DD56542B7FACE8DA
	2A29D7C5CD4FEA6AD5D153E3741E0FA6CD09F6E3CDFF28D0EB7A1520292942D853C40531263ECA5414C41A96D59AEBFADAB366F5CCF8BF3D21997392B51D28B2561469C0CDF9CBD053990731269107DAD317206981CDFBC854942FE98F091A1E2AC6CD1D246935EAE3CD47B563E40B85CDED1AF1B2D1531DA70DB5DD2DC1CD6124A92AC62DC9FCAFEF2CD1EBFACA08BF502E56A43E176B5B551A447772658E54341EB4AD6EB05674638E3526B705FBFCC70731264AAF83E9127B957E13B5A51563DD957E63D07B6D
	1FDFFD5FED4DC36E1B3A30AF70FC5AAF76694F276D5C27BF6F7F52BE637F10985827779F92F2BFDACC863B8EE9F737259C85B4825A815E3B318F6DF77B58B7G7B87B7ED7F59696E9EB6F7177DEB1B8F73FAF1FF6073CA5CBF79FF3D7D47FE76DDFC51835F3A7D4CBF722F6F175695FA3C92A9F35B773372986C6B697549268A1F72AE3B1F5D2ECB922D6CC7D06D3F072A386398476D3F1FFEFADB73107B43FC35FA7DE121F09D236F166A62BA0B010F283B79F9019B1BD9FF1BB4891D76376C49E5751CB45F234B
	53DF50F7313FE078EAFF0173222F40555D672B2CBCBF6CF6ABB3BF4C4A5C0F9F1F12BA35A35AD2B281C591786D7C3775547608407A6605FAF5FD33DB183F3037C1DD5F307B4C9F3F7C343A3EB9AC4CDFF87E343A3EB92024174F6F3F8E692C85266EABD9F71674BEE17DDAA6BA171FCEA097CD5C79AB48A591977E95F2F9CB119B788839CE6292CFA097CE5CA74710DBCF5CA232CB3B87B9DFB6F235442DBE0E5C88F191E4370A38C132CB3C877B0FE79B4EFB5503DC273C48060415E7712EF759FD73BF4E2FF5F7
	C87C940A7A4F973FF6F579DA1CBD14FEE3A653E2D9561662D8067550AF8FFD9C488F104D039E2FEB50B7AA335266E84A33372964E64FC1CE773A619C6740691EB21C1B9FB85DEC43B978015345984E358F1CEE0E611C7A40694E965DDA3AE443B97201537D3D61DC7840691C06F35E8327CB8F783F2D46691EB01C439EB85D0061DC7540690EE8765B78F669E28D671E87CEB7EEB847BCF0BA2F61DCF2BDA86B0DB28C569BE5CC5E0E084FE0F5088B5E7FAD23DEE5BD500B9952BA4DCB1706DC54DE591211CE5C59
	21E64FD09FDBC7A43F3FE6573277D7BB54C1BEC068B29CB76A2AE3FE6ADD6C7FFF316F734E23B82E394D36443C612552BD6C7D2257779935CC69FBDD666FFBE53F7298E3CD3DB4DFB2D2BD7658A87169116A314767CE6158E3964419A2316E7E3B7ADB3BDEB9427D31B952BFEE188C476FACEFCB31G2C4B7D3C1C6E6793FC3AC6AE3E9D0E3D00A0C038A65D85E1DF2515DC7CDAB85EGC884EC5724DB7A951FAE7DFB7E7823F0EC87B481BA3F27CEB774911F4E47450740F184E095A0C6136E1593FC3AB8AEFEAB
	9C1B6FEDCBB988483BD71D2E69981F4E4145B743F1B7E084902AC997A1649749452F0663F500F8C012A65D28709EC90D6266E042F1BE2096509225CE77CF4779F43DDC7CF5B81E8B0882C4EB52AD97720B65629360F89BA06D3E36142C7B54692E897995F1718DF05C8AF083CC9CAFBF2F1BB259B1FD6B482FFAA56E67192C0D477E6F2E4F696E9B1336FC3EG0683BD4AFFDFCBF3C77FEFGD0CB8788FFC5375817A3GGG00GGD0CB818294G94G88G88G1A2BD3A6FFC5375817A3GGG00GG8CGG
	GGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG51A3GGGG
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
			ivjFrame1.setBounds(121, 23, 538, 399);
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
void writeBack() {

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
			//simuObject.seList.removeElementAt(position);
			inVector.insertElementAt(new SEventTab((temp1), wert),position);
			//simuObject.seList.insertElementAt(new SEventTab((temp1), wert),position);
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
			//simuObject.bvList.removeElementAt(position);
			inVector.insertElementAt(new BvarTab((temp2), wert),position);
			//simuObject.bvList.insertElementAt(new BvarTab((temp2), wert),position);
			if (debug == true){
				System.out.println("writeBack : BVar "+temp2.var+" in bvList gesetzt mit Wert : "+wert);
			}
		}
	}
	if (debug == true){
		System.out.println("ListFrame  : writeBack() erfolgreich beendet mit (inVector) :");
		simuObject.printseList(inVector);
		System.out.println("ListFrame  : writeBack() erfolgreich beendet mit (seList):");
		simuObject.printseList(simuObject.seList);
	}

	if (simuObject.td instanceof Trace) {
		if (simuObject.getCBMI1().getState()){
			simuObject.handleMonitor.getDrawFrame1().back = false;
			simuObject.handleMonitor.getDrawFrame1().repaint();
		}
	}
	else {
		System.out.print("LISTFRAME: WRITEBACK() : FATALER FEHLER !! PROGRAMM INSTABIL !!");
	}

	
}
/**
 *
 * Schreibt die, in der UI veraenderten Listen in die internen Listen zurueck 
 *
 * Benutzt globale Objekte	  		: inVector ( das ist entweder die seList oder die bvList )
 * Aufgerufen von					: Monitor ...
 * Ruft auf							:
 * @param
 * @returns
 * @version V4.01 vom 04.02.1999
 */
void writeBackByMon() {

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
			//simuObject.seList.removeElementAt(position);
			inVector.insertElementAt(new SEventTab((temp1), wert),position);
			//simuObject.seList.insertElementAt(new SEventTab((temp1), wert),position);
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
			//simuObject.bvList.removeElementAt(position);
			inVector.insertElementAt(new BvarTab((temp2), wert),position);
			//simuObject.bvList.insertElementAt(new BvarTab((temp2), wert),position);
			if (debug == true){
				System.out.println("writeBack : BVar "+temp2.var+" in bvList gesetzt mit Wert : "+wert);
			}
		}
	}
	if (debug == true){
		System.out.println("ListFrame  : writeBack() erfolgreich beendet mit (inVector) :");
		simuObject.printseList(inVector);
		System.out.println("ListFrame  : writeBack() erfolgreich beendet mit (seList):");
		simuObject.printseList(simuObject.seList);
	}
	
}
}