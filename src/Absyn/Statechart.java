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

    public Statechart(SEventList el, 
		      BvarList vl,
		      PathList pl,
		      State    st,
		      Location l) {
	events   = el;
	bvars    = vl;
	cnames   = pl;
	state    = st;
	location = l;
    }

    public Statechart(String filename) {
    }

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone () throws CloneNotSupportedException {
      BvarList bvarsclone = (bvars == null) ? null : (BvarList) bvars.clone();
      State    stateclone = (state == null) ? null : (State) state.clone();
      Location  locationclone  = (location == null) ? null : (Location)location.clone();
      SEventList seventsclone;
      if (events != null)
	seventsclone = (SEventList) events.clone();
      else
	seventsclone = null;

      PathList cnamesclone;
      if (cnames != null)
	cnamesclone = (PathList) cnames.clone();
      else
	cnamesclone = null;
      


	return new Statechart((SEventList)seventsclone,
			      (BvarList)bvarsclone,
			      (PathList)cnamesclone,
			      (State)stateclone,
			      locationclone);
    };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Statechart.java,v 1.15 1999-02-09 09:53:02 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.14  1999/01/11 17:23:51  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.13  1999/01/09 15:47:54  swtech13
//	clone() methoden korrigiert (weitere nullpointerabfragen)
//
//	Revision 1.12  1998/12/15 16:33:31  swtech00
//	Towards new package names.
//
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
