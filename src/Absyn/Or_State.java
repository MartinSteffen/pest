package absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public class Or_State extends State implements Serializable, Cloneable {
    public StateList     substates;
    public TrList        trs;
    public StatenameList defaults;
    public ConnectorList connectors;
    public Or_State(Statename n,
		    StateList sl,
		    TrList tl,
		    StatenameList snl,
		    ConnectorList cl) {
      name         = n;
      rect         = null;
      substates    = sl;
      trs          = tl;
      defaults     = snl;
      connectors   = cl;
    }
    public Or_State(Statename n,
		    StateList sl,
		    TrList tl,
		    StatenameList snl,
		    ConnectorList cl,
		    CRectangle r) {
      name         = n;
      rect         = r;
      substates    = sl;
      trs          = tl;
      defaults     = snl;
      connectors   = cl;
    }

    public Or_State(Statename n,
		    StateList sl,
		    TrList tl,
		    StatenameList snl,
		    ConnectorList cl,
		    CRectangle r,
		    Location l) {
      name         = n;
      rect         = r;
      substates    = sl;
      trs          = tl;
      defaults     = snl;
      connectors   = cl;
      location     = l;
    }
    
    
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {

	ConnectorList connectorsclone = (connectors ==null) ? null : (ConnectorList)connectors.clone();
	StateList      substatesclone = (substates == null) ? null : (StateList)substates.clone();
	TrList         trsclone       = (trs == null)       ? null : (TrList)trs.clone();
	StatenameList defaultsclone   = (defaults == null)  ? null : (StatenameList)defaults.clone();
	Location      locationclone   = (location == null)  ? null : (Location)location.clone();
	Statename nameclone           =  (name == null)     ? null : (Statename)name.clone();
	    
	return new Or_State(nameclone, 
			    (StateList) substatesclone, 
			    (TrList)trsclone, 
			    (StatenameList)defaultsclone, 
			    (ConnectorList)connectorsclone, 
			    (CRectangle)rect,
			    (Location) locationclone
			    );
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Or_State.java,v 1.15 1999-02-09 13:17:11 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.14  1999/02/09 11:32:57  swtech00
//	Abfangen von weiteren Null-Pointern beim Klonen
//
//	Revision 1.13  1999/01/28 10:40:04  swtech00
//	Erster Parameter (name) geklont.
//
//	Revision 1.12  1999/01/11 17:23:51  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.11  1999/01/09 15:47:54  swtech13
//	clone() methoden korrigiert (weitere nullpointerabfragen)
//
//	Revision 1.10  1998/12/15 16:33:30  swtech00
//	Towards new package names.
//
//	Revision 1.9  1998/12/15 13:38:06  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.8  1998/12/15 11:30:38  swtech00
//	Rectangle durch CRectangle ersetzt
//
//	Revision 1.7  1998/12/15 07:11:10  swtech01
//	Added Serialization to all classes
//
//	Revision 1.6  1998/12/11 17:43:00  swtech00
//	Cloneable
//
//	Revision 1.5  1998/12/02 12:41:37  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.4  1998/11/30 17:05:10  swtech00
//	- Namens-Feld entfernt (da es in der Oberklasse State ist)
//
//	- Konvention der Felder vereinfacht ("s_" entfernt, "o"-Praefix entfernt
//
//
//
//	(Martin)
//
//	Revision 1.3  1998/11/27 10:04:07  swtech20
//	Neuer Konstruktor.
//
//	Revision 1.2  1998/11/26 16:32:20  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------





