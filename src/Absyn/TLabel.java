package absyn;

import java.io.Serializable;
import java.awt.Point;

/**
 * TLabel.
 * @author Initially provided by Martin Steffen.
 * @version $Id: TLabel.java,v 1.9 1999-01-11 17:23:51 swtech00 Exp $
 */
public class TLabel extends Absyn implements Serializable, Cloneable {
/**
 * Position of the label.
 */
    public CPoint position;
/**
 * Left-hand-side of the label: guard.
 */
    public Guard guard;
/**
 * Righ-hand-side of the label: action
 */
    public Action action;
/**
 * Constructor.
 */
    public TLabel (Guard g, Action a) {
	position = null;
	guard = g;
	action = a;
    };
/**
 * Constructor with Position.
 */
    public TLabel (Guard g, Action a, CPoint p) {
	position = p;
	guard = g;
	action = a;
    };

/**
 * Constructor with Position and Location.
 */
    public TLabel (Guard g, Action a, CPoint p, Location l) {
	position = p;
	guard = g;
	action = a;
	location = l;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
      CPoint positionclone     = (position==null)   ? null : (CPoint)position.clone();
      Guard  guardclone        = (guard  == null)   ? null : (Guard)guard.clone();
      Action actionclone       = (action == null)   ? null : (Action)action.clone();
      Location  locationclone  = (location == null) ? null : (Location)location.clone();

      return new TLabel (guardclone,
			 actionclone,
			 positionclone,
			 locationclone
			 );
    };
}



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TLabel.java,v 1.9 1999-01-11 17:23:51 swtech00 Exp $
//      $Log: not supported by cvs2svn $
//      Revision 1.8  1999/01/11 11:50:00  swtech14
//      nullpointer-Abfrage hinzugefuegt.
//
//      Revision 1.7  1998/12/15 16:33:32  swtech00
//      Towards new package names.
//
//      Revision 1.6  1998/12/15 13:38:08  swtech00
//      exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//      Revision 1.5  1998/12/15 11:06:06  swtech00
//      Point -> Cpoint, entprechend die Methode clone() angepasst.
//
//
//
//
//----------------------------------------------------------------------


