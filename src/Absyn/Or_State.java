package Absyn;

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
    
    
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {

	ConnectorList connectorsclone;
	if (connectors !=null) 
	    connectorsclone = (ConnectorList)connectors.clone();
	else
	    connectorsclone = null;
	
	    
	return new Or_State(name, 
			    (StateList) substates.clone(), 
			    (TrList)trs.clone(), 
			    (StatenameList)defaults.clone(), 
			    (ConnectorList)connectorsclone, 
			    (CRectangle)rect);
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Or_State.java,v 1.9 1998-12-15 13:38:06 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
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





