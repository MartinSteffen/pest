package absyn;

import java.io.Serializable;


public class Statechart extends Absyn implements Serializable, Cloneable  {
    public SEventList  events;
    public BvarList    bvars;
    public PathList    cnames;
    public State       state;
    public Statechart(SEventList el, 
	       BvarList vl,
	       PathList pl,
	       State    st) {
	events = el;
	bvars  = vl;
	cnames = pl;
	state  = st;
    }
    public Statechart(String filename) {
    }

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone () throws CloneNotSupportedException {
	BvarList bvarsclone;
	if (bvars != null)
	    bvarsclone = (BvarList) bvars.clone();
	else
	    bvarsclone = null;
	return new Statechart((SEventList)events.clone(),
			      (BvarList)bvarsclone,
			      (PathList)cnames.clone(),
			      (State)state.clone());
    };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Statechart.java,v 1.12 1998-12-15 16:33:31 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.11  1998/12/15 13:38:08  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.10  1998/12/15 07:11:11  swtech01
//	Added Serialization to all classes
//
//	Revision 1.9  1998/12/11 17:43:01  swtech00
//	Cloneable
//
//	Revision 1.8  1998/12/01 17:46:24  swtech00
//	Unverandert
//
//	Revision 1.7  1998/11/26 17:11:18  swtech00
//	*** empty log message ***
//
//
//      Die Klasse Statechart wurde um einen neuen Konstruktor erweitert:
//      public Statechart(String filename);
//      Der Konstruktor dient zum Import von Dateien im HA-Format.
//
//	Revision 1.6  1998/11/26 16:32:24  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
