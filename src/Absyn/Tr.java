package Absyn;

import java.io.Serializable;

import java.awt.Point;

public class Tr  implements Serializable, Cloneable {   // Transition
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

    public Object clone () throws CloneNotSupportedException {
	return new Tr(
		      (TrAnchor)source.clone(),
		      (TrAnchor)target.clone(),
		      (TLabel)label.clone(),
		      points
		      );
    };
};







//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Tr.java,v 1.7 1998-12-15 07:11:11 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.6  1998/12/11 17:43:02  swtech00
//	Cloneable
//
//	Revision 1.5  1998/12/07 15:08:02  swtech00
//	- TLabel anstelle Label
//
//	- Path verbessert: path ist nun eine Liste von Strings
//
//	- Example.java angepasst
//
//	Revision 1.4  1998/12/02 12:32:54  swtech19
//	Einfuegen der Positionen.
//
//	Revision 1.3  1998/11/26 16:32:26  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
