package absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public class Tesc_Syntax  extends Syntax_Type implements Serializable, Cloneable {

  // no fields

  public Tesc_Syntax() {};
  public Tesc_Syntax(Location l){location = l;}   // probably not useful


/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new Tesc_Syntax(locationclone);
  };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Tesc_Syntax.java,v 1.1 1999-01-31 17:23:37 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//----------------------------------------------------------------------
