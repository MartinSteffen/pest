package simu;

/**
 * @author: SWTECH 26
 * @version: $Id
 */

import absyn.*;
import editor.*;

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
 */
private void get_guard(Guard waechter) {
}

/**
 */
private void getNext() {
}

/**
 * @param aktion Absyn.Action
 */
private void make_action(Action aktion) {
}

/**
 */
private void makeTab(Statechart Daten) {
}

/**
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

