package absyn;

import java.io.Serializable;

import java.awt.Point;



/**
 *Tr.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Tr.java,v 1.13 1999-02-09 11:33:00 swtech00 Exp $
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
  
  public Tr (TrAnchor s, TrAnchor t, TLabel l, CPoint[] p, Location loc) {
    points   = p;
    source   = s;
    target   = t;
    label    = l;
    location = loc;
  };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone () throws CloneNotSupportedException {
    CPoint [] pointsclone;
    if (points != null) {pointsclone = (CPoint [])points.clone();} else {pointsclone =null;};
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    TrAnchor sourceclone =  (source == null) ? null : (TrAnchor)source.clone();
    TrAnchor targetclone =  (target == null) ? null : (TrAnchor)target.clone();
    TLabel   labelclone  =  (label == null)  ? null : (TLabel)label.clone();
    return new Tr(sourceclone,
		  targetclone,
		  labelclone,
		  pointsclone,
		  locationclone
		  );
  };
};






//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Tr.java,v 1.13 1999-02-09 11:33:00 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.12  1999/01/11 17:23:52  swtech00
//	Alle Bestandteile der abstrakten Syntax mit Locations (= nicht-abstrakte
//	Unterklassen von Absyn) in der Form modifiziert, da"s das Locations-Feld
//	mit-geklont wird. =>
//
//	     o	Jeweils neuer Kontruktor hinzugef"ugt
//	     o  clone-Methode angepa"st
//
//	[Steffen]
//
//	Revision 1.11  1999/01/04 09:44:53  swtech24
//	*** empty log message ***
//
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
