package absyn;

import java.io.Serializable;

import java.awt.Rectangle;

class Ref_State extends Basic_State {
  /**
   * Name of the file
   */
  public String filename;
  /**
   * sort of the imported syntax
   */
  public Syntax_Type filetype;
  
  public Ref_State (Statename n)                           {super(n);}
  public Ref_State (Statename n, CRectangle r)             {super(n,r);}
  public Ref_State (Statename n, CRectangle r, Location l) {super(n,r,l);}

  

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new Ref_State ((Statename)name.clone(),rect, locationclone);
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Ref_State.java,v 1.1 1999-01-31 17:23:37 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//----------------------------------------------------------------------
