package Absyn;

import java.awt.Point;

public class Tr  {   // Transition
    public Point[]  points;
    public TrAnchor source;
    public TrAnchor target;
    public TLabel    label;
    public Tr (TrAnchor s, TrAnchor t, TLabel l) {
	points = null;
	source = s;
	target = t;
	label  = l;
    };
    public Tr (TrAnchor s, TrAnchor t, TLabel l, Point[] p) {
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
//	$Id: Tr.java,v 1.5 1998-12-07 15:08:02 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/02 12:32:54  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.3  1998/11/26 16:32:26  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
