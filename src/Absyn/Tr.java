package absyn;

import java.io.Serializable;

import java.awt.Point;



/**
 *Tr.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Tr.java,v 1.11 1999-01-04 09:44:53 swtech24 Exp $
 */
public class Tr extends Absyn implements Serializable, Cloneable {   // Transition
    public CPoint[]  points;
    public TrAnchor source;
    public TrAnchor target;
    public TLabel    label;
    public Tr (TrAnchor s, TrAnchor t, TLabel l) {
	points = null;
	source = s;
	target = t;
	label  = l;
    };
    public Tr (TrAnchor s, TrAnchor t, TLabel l, CPoint[] p) {
	points = p;
	source = s;
	target = t;
	label  = l;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone () throws CloneNotSupportedException {
	CPoint [] pointsclone;
	if (points != null) {pointsclone = (CPoint [])points.clone();} else {pointsclone =null;};
	return new Tr(
		      (TrAnchor)source.clone(),
		      (TrAnchor)target.clone(),
		      (TLabel)label.clone(),
		      pointsclone
		      );
    };
};







//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Tr.java,v 1.11 1999-01-04 09:44:53 swtech24 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.10  1998/12/15 16:33:32  swtech00
//	Towards new package names.
//
//	Revision 1.9  1998/12/15 13:38:08  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.8  1998/12/15 11:07:01  swtech00
//	Point -> CPoint, die Methode clone() angepasst. (Arrays of CPoint's funktionieren auch)
//
//	Revision 1.7  1998/12/15 07:11:11  swtech01
//	Added Serialization to all classes
//
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
