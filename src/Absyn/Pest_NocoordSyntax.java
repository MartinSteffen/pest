package absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public class Pest_NocoordSyntax extends Pest_AbSyntax implements Serializable, Cloneable {

  // no fields

  public Pest_NocoordSyntax() {};
  public Pest_NocoordSyntax(Location l){location = l;}   // probably not useful


/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new Pest_NocoordSyntax(locationclone);
  };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Pest_NocoordSyntax.java,v 1.1 1999-01-31 17:23:36 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//----------------------------------------------------------------------
