package Absyn;


public class Statechart extends Absyn {
    public SEventList  events;
    public BvarList bvars;
    public PathList cnames;
    public State    state;
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
//	$Id: Statechart.java,v 1.6 1998-11-26 16:32:24 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
