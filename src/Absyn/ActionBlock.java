package absyn;

import java.io.Serializable;



/**
 * One kind of transition action.
 * <br> Collects a sequence of actions into one block.
 * @author Initially provided by Martin Steffen.
 * @version $Id: ActionBlock.java,v 1.9 1999-02-09 11:32:54 swtech00 Exp $
 */
public class ActionBlock extends Action implements Serializable, Cloneable {
/**
 * Contents of the block.
 */
    public Aseq aseq;
    public ActionBlock (Aseq a) {
	aseq = a;
    };


    public ActionBlock (Aseq a, Location l) {
	aseq = a;
	location = l;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
      Location  locationclone  = (location == null) ? null : (Location)location.clone();
      Aseq aseqclone           = (aseq == null) ? null : (Aseq)aseq.clone();
      return new ActionBlock(aseqclone,locationclone);
    };
    
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ActionBlock.java,v 1.9 1999-02-09 11:32:54 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.8  1999/02/09 09:57:11  swtech00
//	Nul-pointer abgefangen
//
//	Revision 1.7  1999/01/11 17:23:46  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.6  1998/12/15 16:33:23  swtech00
//	Towards new package names.
//
//	Revision 1.5  1998/12/15 13:38:00  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.4  1998/12/15 07:11:06  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:38:58  swtech00
//	Cloneable
//
//	Revision 1.2  1998/12/07 14:58:31  swtech18
//	Konstruktor public gesetzt.
//
//	Revision 1.1  1998/12/01 17:52:52  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.3  1998/11/27 16:13:55  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:28  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
