package absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public abstract class Syntax_Type  extends Absyn implements Serializable, Cloneable {
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */

  public abstract Object clone() throws  CloneNotSupportedException;
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Syntax_Type.java,v 1.1 1999-01-31 17:23:37 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//----------------------------------------------------------------------


