package Simu;

/**
 * This type was created in VisualAge.
 * @author: SWTECH 26
 * @version: $Id
 */

import Absyn.*;
import Editor.*;

public class Simu extends Object{
	
/**
 * Simu constructor comment.
 */
public Simu(Statechart Daten) {
	Statechart SDaten = Daten;
	if (SDaten != null) {
		System.out.println("Statechart angekommen !");
	}
	else {
		System.out.println("Keine Statechart angekommen ! Objekt war null");
	}
}

/**
 * This method was created in VisualAge.
 */
public void get_guard(Guard waechter) {
}

/**
 * This method was created in VisualAge.
 */
public void getNext() {
}

/**
 * This method was created in VisualAge.
 * @param aktion Absyn.Action
 */
public void make_action(Action aktion) {
}

/**
 * This method was created in VisualAge.
 */
public void makeTab(Statechart Daten) {
}

/**
 * This method was created in VisualAge.
 * @param Daten Absyn.Statechart
 * @param eEdit Editor
 */
public void Simu(Statechart Daten, Editor eEdit) {
/* Nur fuer Testlaeufe der GUI-Gruppe und Uebergabetests
 * Test, ob das uebergebene Objekt null ist
 */
	if (Daten != null) {
		System.out.println("Statechart angekommen !");
	}
	else {
		System.out.println("Keine Statechart angekommen ! Objekt war null");
	}
	if (eEdit != null) {
		System.out.println("Editorobjekt uebergeben !");
	}
	else {
		System.out.println("Keine Editorreferenz bekommen !");
	}
}
}





