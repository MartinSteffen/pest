package Absyn;


public class Statechart extends Absyn {
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
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Statechart.java,v 1.8 1998-12-01 17:46:24 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
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
