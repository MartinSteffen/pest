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
	private boolean debug1 = true;
	private boolean debug2 = true;
	private boolean debug3 = true;
	private boolean debug4 = true;
	private boolean debug5 = true;

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
 * Fuellt die Textfelder und die Checkboxen der Listframes mit den Daten aus arString und arBool
 * in Abhaengigkeit von der uebergebenen Anfangsposition.
 *
 * Benutzt globale Objekte			: arString,arBool
 * Aufgerufen von				: initTabs()
 * Ruft auf					: -----
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
		System.out.println("Methode fill: Startzaehler     : " +counter);
	}
	
	if (counter < max)
	{
		getTextField1().setText(arString[counter]);
		getTextField1().setEnabled(true);
		getCheckbox1().setState(Array.getBoolean(arBool, counter));
		System.out.println("fill: TextField 1 gefuellt !");
		
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
 * Fllt die Textfelder und die Checkboxen der Listframes mit den Daten aus arString und arBoolWork
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
	D0CB838494G88G88GE3D6B2A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E14CDBFDD09C4715A796F1140AF261E26213AB644C59A421AACA4AF7D1E279C4AA44C7A52ABA4A27CAC8CA4EA9F5D4C22EF4F7BA1762226A78C3E58FAB84120DE5A410AD4558C282490B3C02DDD8BE05A484C88289C98490E6E5E4F6E1C7E2E13F581D85E461B82F2767754E4E76321ABD7E082A1EE67A3D576F776B579FB3535B88D5475277969B2A8421F82F207CEBAB960434D4C1786609E7C7D1E7EF30
	9984553F63A08705170DA5F075032D6EF91B21C278D971BEA8A7C3F96BCFEC0637613ED248AD6EFE8CEEE2609C815BE33F7A4593AB637C4805B8E5F23967C2B9DC8F002C855D8610428602FF7B4291C53F954A8714FBA24590B5391178B41DBE225237B7D23FF1383A095DCCFCC29734BE39A079A0C5A07BE5E6EF9CEAC7AC4B634278AB6F143EC1F0D475D0464D94E3D9398A429F7612A46C912677C2C2042E2139FB6703AECF2833F3509514AE879C9EF1274CE37C74BE59CF90B6C2F949C2F4DBCF13361B613E
	9F44EEC1BEBFBB5125602DFB62A9C8ECF5CCBED9964AA35F9266D3F133FB8F457C7A85442CE9C2CC5FE502B98EE5BF48DA267F678A22CF0772A6102DCD4865BFCFA117CD3E27040977E2F35951C4B914B70739B84FF56FB9AA7773409C6907FE3039C112C0B2C0F2C08AC05E1147D7496CE938DE1C12FAFD9E0F4FFB516F6FF1BB8357FDCE518D771D1DE0D1F4436234AF609484BA066E1CB6F178206CEE0E9CBFC4E85BBE94305F7BD25204D42939F4AF27EDD587EACBAB2CB4C61DB55C360DD612B71309642D8F
	6EC93DE6523F327E062CAFD269535B097ED7F21F1372CC0B4DD05206F9CEFE00DCC69F7C2D10BFE2AAAD63F1792E2534201DF2585D9E66C2F3DC6BA1B9B6F6588CFDA093A092C8AA48468E4C710D5966F8B99E70CAE2406BF00F04267C0E005493F2391DE2C049F57A05D8395E5A911D6783AC9F3F1347DDC1874D47DE26FFC956972974B4CFA1D9CF4A651DB0D6C1287F738709BE2F137A1FE4F1FEA36B0DE743FA9AA7DB9E57243C8C364CAE1B01666A0BDE122B3CF3B643AE10FD67ED069A10EE100973182BA7
	E69B9FF2BC8E2972C46B8E4F3D85D7F1C98AB8863CC149611D96479C8117E34AAD8685456723B912532908FAF6F91ECC83469551AB869C124B670568300EC02784290FEB6EA071890832F554659181406397B06E7CDCA5DC5D0E20F44B6FF4C8E258035A6F4F11B1376098030890G6EB71CA7391264B5EA996E571F47BCBD63A33ADDD05EF78173D2B8F7882E57E010086E4E4EBE57B42161885C477958E40229A9D4033F93E4ABC8D7374D3093E47C224D3083E429876275C299A4932488E4ED1F4DF064124DB0
	831285D2F1995688906B95D8A7C0C67BE12DGD99A30994A2F5A8C4BD7F16E3CB40EF3A7297A6BC256576A38F3E763664325A55768982D3E961EBB1473284C398F74CE10E4108C109C10821012C1F8AE027401B8C112C1B2C0F2C08AC0CA2E039D248F4489128C12811283D2GD2F2836CA0FDB77039377DF4CA31725C6B236DA5F90A35062DA4A49FF8CF72C5722675A139A6796355A7B9D717C9DF4442A27D20AE137EA5FD2A076F8E451F74BD9987727374EAF40CF20E0E0CA3B22E48D832AADCB2953E852A76
	4DDCCAAC1734EFFABF403EA1637D3822CFA4DE236A1E2CC1359C9FB2D7BF08D17FDDCD79688AD8751AB2D9BB5F504977CD65FAD84E833DEF6678D9B8BAB297493B466BA06FAB3A37156B99155F3184F3C967FAE6337A19EDAFBC451D6B9BC6EACB1DB71431F5C37B1C3CB5CF1E89E9205F84326D862EEB0D6E9AC1DE6FFB9CC191D7781B81D81CC5E7F815233EA68F491538A0FA25BE51AFFA1DF093D45657E68F612A2C7DF7FD822E4B9D321EBCD3FBE1F18EB27D854FC12A07986AA7895A2F48F607A485DCD3A1
	C99C91A549653D972EFFD366920CF8DC8715B57DD9799DF09734F15FCDDCFB7E6BB25119205C4DF40532EE7C265A6F41657198FDC16A6A6D3FEECD9D0A119A93E3CF82E3E42F26CCB1369C6CD746489E4527CC99CFE42C12B97226C047FB25B2E60FAA39FDD7992FE48DE891687C25B1FFA747DC06B6E50064029402D48F419A8F6286C9854982A9GA97F932CD7A093A049A09BC072C0F603548DE33EFFDAD1D37C70BC8F2C4015374EE825A5017C7EB5884D554103A4D78F132743F1F2F572A1B09FA61FFF0DC2
	57072FFAE32FD5CFF879EB5513DE7EDA75CD6F4AEB51F73D3C35085AB6FA49FB63344F6B9565451419857A8D7D52A8616C723A248FDD4EFB22B4627AC49484E9182CE3DF125FCD87615EBE0C6FFD0B3578ACB0DE7ABB417C3FEFF13F5F4A7B2D251B06695A13BB9CFE96D0CEBF7011E7016896BD1014DEDF48ABD17DE6793DD751877748BA63398E385698FBC2126473B61B9CF2AB9AEA4E1CB9DF43BE77CDD354BAF9D136DEA2D98F9A6B476A4D829D2F1542A3214908B89EDFA8A8B6487F773ADD53F32253B86B
	D8F0B4B896258639579A2E931B9C2A7E2151A856E396B516E59530AC13575535814B22E065F4BEADE30DCC87FCEE7714A3E0B1B91C332120C4F258903E9DF338C3E26F0C43FBAF9A7DA27362D28832501397797186D78EF89678B6A9FC7FE5D01D1B26D548CDD3D4EE1A952CF521B5B2D66F0CB8BDB765DB3218DC1268E9A07F0DC8700D96A39D83E06762CDE31C4981ED88ED86D88C52F62B424731984927E91578B46964532270118EC472E9DEA5BE4DBA7934AAFC5ABE6E0D60E3B5CD7BBCFE1F17D40881E51D
	6CC51770BCECD3623D7CE9E43C167871DAF8715A15F8FB4BA26335460F574A0B5721447B5C256408F8ED2B147FB61D7937D8A89F37BD12CF7BAA71E9574B2709727946F2DF841F0E78796E60655B52CC639D2D3F9C399E186387341839912D78DC79C73BA62665A1E2DA38B1DBB0660B150FC646DC2D054222F7253034A2A7FF719A4D5CDC9DCAD61D0CF0EE8F9856E866766A70E95149875706051B491A35E1F57834C671312CCC28C3D97BFA12B5F3E7F578F4689DC14A5C7B654DC735F3EF154634D9AFA3B6F7
	1FB5BD2A1D3B2B44C96F9BC2F86E7F7A3CC375FEE4DD0577A32B767D68ECFDD3ABFBAFAE2FFC2042EBD9853C16C8BCED3B435565063789F4FF68EF82641BE0A7FCAB88A3B64311915C9FEA7393BB6EFBAB9BC33D70BECABFE74016B2C23ED53EE12AD2FDFF18C7686F1A4BF02576774E706C5B187DC92E7DG0B7F2FF16C7F9747DE5B40336FE47899DC3BB00A76130DBCFB9533FF0BEB8F637FBC0E7D952E5D4862FFE666593B18DD302C9C5F9447BE4E358F3178AFF26DCEE63F5D34F27CC73976A5D63F2C19E7
	DF476CEB3976F4E65FEFDD993FA50E5D91477E65B87667E25A697CDA97A473EBA3704D8559BE0A734B6F2FD44D2FC11FEFAE649FA53F18616FE5735037C2D01C8F095EE911982E0977155F4426668FE9EC72D7A17B2DEDF61E6C8D39D5B1691E43B38E227787DC9EC760BE59FCD54E963C90F1EEE097F0A46790AAEE598CF5A0F43DDA3EDE8AD7F93D31609E4652E132B74156A1814FAA1C3A420B57853106C004B1F9DE9D2AD66CB320CB82C93FC57D365FD276ADE43F9D4CEF904ACE1064B16A17BD26767BCEA5
	7A95C0F9B7C87507544F7D215AAFD805FED9204F83A9D47C4C63EA3F2E9A749B853D9BE45DB8755B926177AA73AB37C13EECE13FBE1B5A6F7B4751EFAD6853D5FE5991FE77CE20DF49EDE8436D30DF57ED355FBB356817847A75AA3FCD91FE7F467CF6033E6A23301F75A3355F1ABA74DB82FD0A4A6F19883F361368D7887A7209301FE9C26D77BF4C4F8F7A35AA3F4C883FA7CF215FCE50175C897B555DD17B0D3C4F468B6813D4FE6991FEE5C6B68EC03FDB65D77D315AEFB3733303FE89E47D4754AFAF426F4F
	7568D7B589ED1C8C7B8DCED23FF513446FA9F6662511691E13F53F000973EC897E26F33DC2FD7EC67802193B0FA75B157885135A5FF4FEAC1138A5F668CFBB2EA73F1FD72FA79703C15F34CB7E49DCD993D2836ABDBEE5BFB4709ADC6F3A82C1E9406B941758FEE800F0A53F29ABEAE53EFF6703F3C2749901CBCE17648B60CFC83D0B72BB46282F5767947BE1896AE5DB20E3AEF1117433FCDEA74F0479B25AF10F73373755E701A4C3F5696BB1F2E337531CA4594339B11E4333C0374EE06CEC30D35D6D865495
	B15D3FB31D59013A09C6544DB0DD0B99F569CC77C3265B46F4DF31202EE28AF595CD286BE73A9FB1DD92531DEFC65DA6267BB1536D1A42FE7EEA103F073BBEC84E1A4D31E7C4DA102C578B4AF301765FF3156C5C963E9CF6F62A4EED15F66FD37A5AB80DE725DE68A1F8F6D286C919C6EE9FDFC15FCC667B78G714D07F291C805936626FCAE6546A1BC5FE2841D5D094FEE5B387A59399E74D9A079CEFC667C609079ADD5571BB19B2BF70F135FC0E26C98ABF3316C35EE85BBC9C89332AD2D25EFF246DB4A1AF7
	CB8B1D4AD98AB1BC5E32BF6D5E1387C25BF830925BB883BE69A23651BD4CEB63B6915B38D419F89B1F2C8A37B1EA27DAB9931447F86D844C3A3B486B42181A5744DD4AAB69AE72BADBF5912E7F6FADED46356CB059D3DA0DADED5A2E270EE13BD201F3AEEB5778A8AF5FA57730DD7BEB924F37204AF7546E3712EF3186F99D814C21FB486B0FCBEADEEB6FD1DE9B982F5C9A725C5079992C70FAA458865784F749955E466348BB9BB895B15EC31F70F81BE7EC860A7D7D88EC1D7BDC8A76F6065D87F117E7907B55
	87BC6C0D8C3BBD82DB676E37021DF2823137C0DC4105585F7B14075D6EC26CCB9158BAF753946C3EDA449E0438498C7B4EE7BC6CAC067DD684364E1DB3853B08E1EF05387BE6913B3C18075DBD0B58778F372B30756E12D16C8DF508BD8AF157B16C4D869EF68E43DE9351EE1D3B6DCA3B6DA791FB9B442D18C36C59929EF67F9CE2E7C6E06B5CD9D7302BCEA1768444CDE558C7CBF958398C7BB79158BAF782956CAD8CBB9F629EF1A37616B29E76109B31BFAF297BDB6F269F45DEFE9F311D90B70DE1AF6E67E1
	6705314FC6E06BDC58A836590858BBA0EE0D8731CF3D4E439E77B06C6688EC1D8B9B45AEE058EE081B4130FFD94EFBC6ED77603360DAFD624F28B1F393FBC6D9F56C88629962EF57A367DD404768C54EBFB94063EC77E23E7EC3052BE7A7121DCFEFD2FF976D18BD96733BC87252F73035DE6DF751538B641DB483741B3C783E7C16C44EF060F751B0FC09C06F3949DE853567C8A467705E2C1437C884D7FEEF96707C4D0B26154E5CEBA5570B67097397C94E72213C9B244A875F0FA003BE64F6B39477FC7A2525
	954E275FBE2307175B07E72FA69B7454CBE27572F555CBE5759C0DFA6AE532FA1DE6BD7532D83D4D3A6AEDE575D2ADFA6A9530FA071B7454ABE2751E57D52F5C0F75FA1B75542BE175F2F4553332FA673A35757A7D61730D932AFB947AF78ABD3DE4AD717B695FA96CE17AC1D91F2452AB5FAA7237A6A927002D1061CFDF51620F4F07B15D733170DF3BAA634CEB712BE4FD4A3C96BFEDG715341D692C07C51C3DA7C19C0A4E63226CC6BCDC555EB5014B3B57568DA7A73CEF20ECF731DCEFFA892F00FF54D20FA
	0FB59B626403148461599E44EF74310F709B5D9C44B52975BE594F1A01F2B2C89A48A6106DA0C5A055922ECDFF9F626D75C42ECD31B6FA42E7FAE99E5E2ECCACFFA7236AE97357A5C556236DFEFA985BBDAAE19F7EBAC18EBB226A3D27A92F13F8FDB8EBBB925D076C30986539B036CF4149E0BCB30FA5467307D17534395A4E4D55D7C6B1D7050C43DCCDE29C6EC75553664A9462656A8F0DEFC7678AFF9CD6F87EE989F35597C21E8D4793635995D5CF4BF3B9444B5597BE41DC25B28E3FCD10432E389CF2399C
	DA1711C3816370780944B8E4C45553F228DE60F1787D2748413A009C2E56A646E1BC2A1E16031B4BA175B364104CB83C12A007723AF89C32389CFA0B11C39E6370CFD1319E0EC3F6DC8E650BBC8EAF9B10C35DA2F238FBB2B18ED2D4BDAD0789AE07FFA8C18E92637047D309F1B0C65553F2584065B0D40A9CF2980797926430BDAE075DDC8E2F16A1072AA56470487B09F1C8092A2765B0384463703D7D484149B8349A93635097D5CF4BA10D4B614E6B48E1A363705F89F2A862F2788BEF579F9755F5655118AC
	29E0B01214980C28A4C58386BF6B36345AAECD6C5636592E4D72E9C8C3F65946EDCD56EC592CFE248D598EB07C9A069F09E8C8A589DACCB09AC50408919B887E9810G4278ABE2834888138C81E14499C058F35FFD674CFBEF5EDC231B6C9F67B3F33F771C733E773EB7775EF76FBDF7D4575465E04EC38EC6B7F2085AF5EB9CD6F859E9B9543A75B8A46753B8440D632FD1298F8900F4289B47DAAFDDE5F19C780E5AB6E87F42E940775308D1360EB3B8EC6F33FFC1F18792F6E9907407B11EB5C9DACF0B841F2B
	496F3AEB3EE3A1FEF7CDBF96E263B57DD808172E09633652DD3EE2A5B83FAB6FA13F84900E0D6EC5AC0930CD04994790F36DC32C1830D395086DA16C89426A88FBF8BFE2D64276D4A256CF58D304CD90764981440288FB1A30F0422C523AD98C249FA76CC8BD4356C35AB80E4F1F25012D5115D33ADDCA57D17AD79F790A8F3BD9E15750E27C9A7F7D15DDC3AFE60D17FDCCDA9B6C858E2EF12C0FCB962C0F90AAEFE193AB7FCACA7FC9CAA752FB5742462F0A6BCA1F753162AB3B06DEFD3CA161BF59DAA07DF6C6
	C6ECA352CB5351EEE7371C77DBA7FB57730A93EA617BE978FC604CD7F3E41DE34E4186960F4A474DCF156B2C6736F5C3AB425F91CFB6E95F91D94CE39C54453AF1FCC7DC510CED14E18257F14F373177609AC8370174CF607D38F0C1B4FF60B7612963E079BBEF9B6A5C3E5B2193EE7DF668E3B7EB93C7862F8EDF32E95B9A2B3B1872BCEF1DEC7E15D59B5739A859BB3B077BECDEEDD52FE44F73FC598F38FD35E77C1E34ECD65E13CAFA1EA25AC5F5942128231572F76E6F15A2253F8364EF7C0C1E3FB5326DDA
	78CC1ED0BF5B5C5F3F3E2574D7C57E022DA2FE666B9EDF2557157C9616AA7DD5133FB3AE113FAA05BF8BFC6F3C0EF35C366DCAFF6F133F00B1113FA1053F99AAFE2F6FD07ABBC87E8ECF087C05A87CC52A7C7D234ADF8D79FB7E3A48DF2C42DF1C4A5F34BFAB7D9DA2FF77CD097C2DD378CBD1792BAEB3A97C6D2FC0FF16D4113F42C90F3F32C9253F67547EAA515F26B4113F9A053FE6153FF9BBD57E8E203F070CA2FFDD8AFFCE153FD3EF2B7CD5213F5319A2FF7E8AFF21AAFF996F287CD523BFE33648DF3442
	5FAA153F6F2A7D3D0F7E7EB6C764AFDE61EF3D4A5FC0394A5FC11AD34C9579B3CCF97C95CDA97DED5F2572371FE20B73C47E4A957EEAD57E562A7D55203F1773C57E5A947EFA2714FD4478981B0BF5D1FACACA3B8D5857596A25E7C2EA330FFD51AB8F1279BE3BACE9EC59G0A0D690879CBD8BDE0AF25A196A80DADEF07749C524B1746162FC1FAA3E1F7C958CE980CFC0B30FEE9EC799610FA1A7CBDA7E107G5BC9FA0FCA63CDB7E0A304B5C8E34B4740AE5508D8925F83867CDEA63DAB525872D9C89FA23D97
	A56C82784BB2E25DFFDE47467F0EEE7760D5280318A926B73DCE39BE67126BB6EC8A576716DDE3FB69AE8FF78FC0B756B1E43BE4887613462E75B8C6B0C03A88248A448A62744336FA4ED9B69E001E5249BAEEB6401067209B34638BF61EC28258A5116D6C935EDCA3CE68F14D1EF6BE0DF39A33205FD2402AC02CA0CE16BF4DB7A7BE1E392D095EBD27519E3613D20C83240D534F2715CC47714C5BAD5AE735EA3A223FD2FCC7616B877FF50A796B1C4E578F0E90DE5CAE6DFFD360FCDD61D3E9BC45523D374118
	6AF69C938FDF40B1F16D5FA0B62713E1DD10F69276776D2867C7D8E2073456896998426E166613D7C1BA1E30274F206DFA42D24EB22C509F5E1D7C69BD490AFAF504BDFE9A2F51CB58008B7506881B65E6D8B024A3885BA0E14BA13D1A30C5E3E81BC0582AF1EF5B768954B35C095848F5EF3D54C954ABA33D9DD35EFA51D328E7A13DDF983C75BECDC5BD87697925F96B652723DE90696DB3FA6BFD5F08FA4BC96FDFB23C759C192897CFFA01D95EFA5B32D1AF0574EAF33C75FE1A03FA255FC06CBFCC5EFA6EDC
	54BBCAFAF34D5EFA65F92857CFFA0D525CC6G244388ABAAC03DC8422A8BD1EF4DB730AD7A4DDE5646EB573786D90CD1B3EFA8786FB6340E7DEE9240EE8308F9A66E933E73AA6E932EBDBC5F303E738D5DFD4211CB8EE4164D643F6123B3BDFB85DC34CFF8CCFAC7E7F6FE109FB3D3EA1B6A15ED93EBEB828DFCAD056757FC244CE76F884F26A973BFD365B33E7BD476212AFCD69FEEB91F176D792DD436179798764C76FDBE23A1001729CC7EDCFBBCAAA3799F30ECAB9A309D6C8C40F6B073982B4FE0C8C7022C
	A27C5193D2BDC3FA8308B9905F8D43ACD8BFE501D8BFBF37E86B67202A7C06C6ED7D6CD6153F32D1DBBF55C6E5FEEF2336FE86E4FB5EE67BB7E95B6C4EC0CFBBBD94682B4D7E281915F1C62057DA34048787FA2DC51F443560F0484B1EC573BAAD2CCE9C1076A36C6DD6FC6E43E6E15F3269949B4B2C02F4BC48FA9063DD67530AC1AAC0ACA0DDA0AE10A0108810D810F810A4106CA0786D0254003401740378010400C4032C86C984C986A91CFDBE2D9C248E649C08E3B6F2F8331DF1E0B1B3AC4E05454430B89B
	BF488F830985C984B15C8D578229836982718389830985C93C1B473E1449B1ABA5F22CC9119CA3E216E3BB0CF2CC860B37E03194ACCE02F33035A54A670BB10EDE635F3B3C311B91D6EF469B78E07546BEEB02BCFA2B0344B62C7ED9DD327B456EE1050FEB307B18A87BE2759F22719BF493656B3F81A7A66C59F0A86A31CB76DFAA5B32670CBDAF6C191346D6DF627A490A6BC6EBB844AA6C8BF56E9F0A3CBF086E75C19D1DED7F077B4C042D91675CC0874F05D31EE1346F86B66CEC244386FE1E927B3D7BBAC7
	0E4DE161D9F06C7DDDFB0E5D169BDCC7A96FFC899DB6BFF8C41146B36A36AB6A1C1D67046700ED7E92BE15675B3D2749D31E37A7BAAF0B0F516F3CC06F8DF320BDG29823102B8676098FDC9273477313B4766E0F6F358994935ACCB76714BF674918FF96BC14C4150F600B487230FCD9D5EBE4659B4317CAE34788C2B0701EE17FD606A00E1A218790D15463C6CD9F08463BB450BE770DA31004501ECGA93A87F85F03575AFC0E5D7BD17B083D47E12B9FE041B51066BE771C4B166B48FA8F7AFC540ABEFB81F3
	010400440044B39D096BBFE5F84ABB43057A4910EF3E974A8A528C623897B9BC6876AEEFFE16273C39E3ACFF04EDC4A5EEBB46301F8E389739FDEF8C2F958658F210B8108DA0C55FC49D7B846AD481E6816987718709A01D62C954D98DD882C88AC8C9883C3304204E0F27D0279330A110A010E810B524B31A0ABAC900996F03BE8564A8C86FFD2833B39DF5E6G96829283928F12CCBAF1C654A91E8B7587528C62G89180BBA37E522CEA4E031A009A0067B211F3B9FF5AA33D1278EB0AB08136501ACA71DFF4B
	C11DB8405601E407C2DF8BE289C51D20DC546987EC82A494E4A5C88269FC1807BAA900954E037E84248DE4E89E5E772EFC6F7B7EF136675959DD00BE0240A69CE49548DA906383782CAD130610FCDCD3D84F46AFE58F70F1CDAA612FCBF805821763E92431B6CB57C2DE588338DEB9474446E34679D0F7A0B5A0EDA0C373699C6BF1F65617F01F18F5F02859B6EAF3A8168DF9FFB1D5FFB37B911970B1721F366398393FFF2EA1600DB7FD4689864C67FDD6247CD9D29C1501ED5DAB06D2B32B538448CB86A98903
	36G448252950665EB9E5F434B47429C6B1D4E6ECB7DD284A5960E85B5FAE0B631A26F67E850164D996669BF7D43FC0DD3AB243320C34334776D1004C704E96F1B79985E376510D73CG4F0EFE5F42700985DABF9FCA387F822D1F55A750CFB064A5AF40715B03161B3DC7FCCCBC3F8957GBB9762B8793613B8CE1ED8C8EB224D2CB10F20F43B14E6FB4B65B5511338BE47F4EE06471791F9CD142EF1AB67F05EC8786F5B5A62BD8F76F90B72DDE725DC3E240558EE9CE9455FFC76A2F8BEC1EAC1BAC1A696E13B
	713DF6D6867944BB1CABEF72E0F87E5B6B0DEC3E56F3DA9D72F26E73686279EDC776B3DD2B4D65BCEE37B9FAC957582842AB918FB5297083083FDB2942697C17432F2BF0BA03E541148A27B31002AF2A70030817162A709A447F7A0E8ABF04782CED392AF26139015FF429F12C479FE87431AE5F1456AC69749A3A477BF7AA71CA427FF93792BFC0F86687CA3C0A70EB2FA871EA421BBED6621E33E4B63F29448F92BECF453306705AD3CA7C9061368E1332DC95087FE60B8A276761739295CE4F03E317671942B3
	786E784403D54BE7E764DED4BE7F210BF8CCC96CA20CB9F97A2FFA79F1147FD017DE7EBA4A3F5223171FC279353DFA790561F4B649E53D7CB24AFF22CFAF3F0672675975721BA97FBF0774723BA87F3B43FA79CE4A1F61544B77277C56D13D7C50F01C5FF964820E9B2343F1FE67198B5A799D767C6DB5607CCCC127F6FE67E7294A7C7ACE6D7C4E4BAA7B11CE6D7C4E46B4657C57EB1FA863006A9E5B11097D1436DDDD974E5B4B8D611E39BDDE2E14F6ACD77903D82EF7BB44656A6C50166BADD57E2C8EF139E2
	BBFC150BBF7FCF1EE1FF90D3D901658C6FD316B37EA7BBFC46BBF5BD484B6714BF7BB7C74965ACBE0B650C24F29EBF2BAD678B2A7B63BA2BAD6796D5B9161C5516D3BDFF19F0D6DBCE2CA7DE4E06F32C1C2CAD609C57D91163DA6238753478DED8CEEBB9F62B7247CE0B6FC564E9DF7702F3DA62C2CEA58FA127F8171653F615CF13DB4BC91DDF6B56F27A0EAA7F8B371653C82A325E8EC96FF5173B9DA3B66219C86FF5D66219BFAE66D9B72E65F9C215BFBCAE6699BE6E0B279CAFB0C16F1A44697B93E2CE3F1B
	50F27ADC153F7D3A18D347F5B1273FCCA227D54449B1A9667440241653DD2A7CB513E2CEE9D3E2CEAFCCA1279462344DA0667431C14B696FD479D386B127E586B12750D464D475AD647453D4B12714D4AD27E7D479FB53441C3A52441CCE26A3A787F1F2270BB9BD1C2E65142EEA631EC997F3AAB00AB97D41081C4209D3F9061853A799DACE6F29B885E408B97DA0C34CE9F1A6F2CAA0CECFE60AB965E4E9B91DD7F1BA1CA566B410A566F4AE9BB995C5A027BB32451CE232351C2629B8BD1F2D6574A315FDC90E
	18D31689B9B59327EA1318D31FC94BA9CA4569BE1318534FCCE2CE5F4EC5CEB308535ADCB1272DE62F5F1D0A1345AC66B4E696F33265A1271544E9CE1E38DF7ED11E16530FD5791B72341C547D722EFCADA7F53FFC2AGB9EDA0CED385E2CE4B8A341C32D47574EC01185316C2DF1C785C40D6F68C0E7C7DAD4577083A2FEA5F301E70EB7C70EB3D861F5F3A5F64FBEF6EC2137E5E5C8813FE0C40D213787F923EED527B3F84BE977722746C6CD9FCBE4D8262G718389DE0C73F047F3F79824F9B81DB938E33DFD
	EC9FB07B67B1A7DEEB1759F739AA4CB77E3F32C3E676EE5BB3886F2B834CBF7DFF0D195D9B67601084F26C28195547303D2F5F49D5E53CC95ACB6130DD16E0BE8776E483468AC4AF46F93195C7F1DEEC4DE20A59B96EF956BED4FCFF7558577F5C6D7EFF38C62D45D326E30A6FBFBF7175DF7B37DF6BB5785C5E6AA63D393D94383F65CBD87E5C1325829FC5CB14FBADFF5946668E250D7DF476E65F8E3676A76D2139B4B830575EB330776A68405E6E9E67DEDB2F5D794187ADD79D8EF9B70DC15ECFF3FB0B940F
	8F3E7DC1421660BC4025D6ECF7E316E03BBB3BD55B6E3E265A6771D82B365DE53FB7CF7E46D6ED3B3BCD354F631DB6ED3B7B0AEC4F4B7747ADBA7153B49547F9FFF4917738A4D1D9DE7D144E30FB9831E73A906BA2AC2287319042C6FA91DBC358DFAEA3169D09D8CEBFE2F504455991F39136738AE25104A58DA3360E3095CE44CA23903BE39431F3D158A6AC9746DF6889EFDB8B582D115B24DF1959F3F3267BB35D539B1B202ADBFBBE33DDF2DA3B7B6877125AF1EB577D3D541F87817F48A8EC47CB73F67B3A
	BE00ED4303C336E1271B235241F0B207B74E5EAC87EE7702B05E41375DA34238865FF67FAE0CDF70ED97AC0CD370ED57B0F9EBF6AF8A638EFC5BAD9046977836EBCB3FB53BBF8A6385FC5BAD95468578363BA85C7F6F5B4EEC3AB53B95427D7C3E6DBE936E5B77EDF72660E66D786F768FF93E47D32979B79ECF19247D22B69887D95DC3F20CD5C13E945BFA7972080D46EDC5790504B50E348D0E58D9EC966FD38DC7757A54472330CFFD62E829209CEB2364BDD1D2DF78EB698C18E4408A17027DD2DCCF7B4EC9
	462941467E979867EAE71661FA73066A3906386776E7323ECAEF2E36EEA93F06E329E7CE1AFD7235575FCB7DEF58D26D1AECDAAB43A317EA57E4E736601AEC8C6415BC027DC0FF6B2D5D7B93970571FB60BF78913D786757BE954634014DCADD3BDFF7096C56004DDADD3B289E11DDB2581823756CDC3DA23BD230295235EB38AC323300CD272EDDFE3FB0D68DECE6685ABDE597462401CD242E5D3BD70431E7E09327EB37F1D898E386B6A93AF631CEE1AC593273E9E54B746CE60E8AE3464026D9E347FF7B7FD3
	403E537BA36F7E653C8C69BD1D7DDF1E3D5BE17F5F81GD0CB878854BE3BBA95A3GGG00GGD0CB818294G94G88G88GE3D6B2A654BE3BBA95A3GGG00GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGGCFA3GGGG
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
			ivjButton3.setBounds(378, 229, 126, 26);
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
			ivjCheckbox1.setBounds(185, 16, 91, 16);
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
			ivjCheckbox10.setBounds(185, 192, 98, 16);
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
			ivjCheckbox11.setBounds(185, 214, 98, 16);
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
			ivjCheckbox12.setBounds(185, 238, 98, 16);
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
			ivjCheckbox2.setBounds(185, 35, 91, 16);
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
			ivjCheckbox3.setBounds(185, 53, 91, 16);
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
			ivjCheckbox4.setBounds(185, 71, 91, 16);
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
			ivjCheckbox5.setBounds(185, 90, 91, 16);
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
			ivjCheckbox6.setBounds(185, 110, 91, 16);
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
			ivjCheckbox7.setBounds(185, 129, 91, 16);
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
			ivjCheckbox8.setBounds(185, 150, 91, 16);
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
			ivjCheckbox9.setBounds(185, 172, 91, 16);
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
			ivjFrame1.setBounds(54, 21, 538, 283);
			getFrame1().add(getContentsPane(), "Center");
			// user code begin {1}
			ivjFrame1.setSize(538,240);
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
			ivjPanel1.setBounds(8, 7, 525, 267);
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
			ivjScrollbar1.setBounds(15, 10, 23, 246);
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
			ivjTextField1.setBounds(43, 14, 139, 16);
			ivjTextField1.setEditable(false);
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
			ivjTextField10.setBounds(43, 191, 139, 17);
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
			ivjTextField11.setBounds(43, 215, 139, 17);
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
			ivjTextField12.setBounds(43, 238, 139, 17);
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
 * Return the TextField2 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField2() {
	if (ivjTextField2 == null) {
		try {
			ivjTextField2 = new java.awt.TextField();
			ivjTextField2.setName("TextField2");
			ivjTextField2.setBounds(43, 33, 139, 16);
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
 * Return the TextField3 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField3() {
	if (ivjTextField3 == null) {
		try {
			ivjTextField3 = new java.awt.TextField();
			ivjTextField3.setName("TextField3");
			ivjTextField3.setBounds(43, 51, 139, 17);
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
			ivjTextField4.setBounds(43, 71, 139, 17);
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
			ivjTextField5.setBounds(43, 90, 139, 17);
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
			ivjTextField6.setBounds(43, 110, 139, 17);
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
			ivjTextField7.setBounds(43, 130, 139, 17);
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
			ivjTextField8.setBounds(43, 151, 139, 16);
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
			ivjTextField9.setBounds(43, 172, 139, 16);
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
 * @params arg1 Vector
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
 * Arbeitsversion !!!!
 *
 * Benutzt globale Objekte	  		:
 * Aufgerufen von					:
 * Ruft auf							:
 */
private void writeBack() {
}
}
