package Absyn;



public class Tr  {   // Transition 
    public TrAnchor source;
    public TrAnchor target;
    public Label    label;
    public Tr (TrAnchor s, TrAnchor t, Label l) {
	source = s;
	target = t;
	label  = l;
    };
};







//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Tr.java,v 1.3 1998-11-26 16:32:26 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
