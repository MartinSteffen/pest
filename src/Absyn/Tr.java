package Absyn;

import java.awt.Point;

public class Tr  {   // Transition
    public Point[]  points;
    public TrAnchor source;
    public TrAnchor target;
    public Label    label;
    public Tr (TrAnchor s, TrAnchor t, Label l) {
	points = null;
	source = s;
	target = t;
	label  = l;
    };
    public Tr (TrAnchor s, TrAnchor t, Label l, Point[] p) {
	points = p;
	source = s;
	target = t;
	label  = l;
    };
};







//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Tr.java,v 1.4 1998-12-02 12:32:54 swtech19 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/26 16:32:26  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
