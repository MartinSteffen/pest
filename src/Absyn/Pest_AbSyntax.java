package absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public abstract class Pest_AbSyntax  extends Syntax_Type implements Serializable, Cloneable {

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public abstract Object clone() throws  CloneNotSupportedException;
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Pest_AbSyntax.java,v 1.1 1999-01-31 17:23:36 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//----------------------------------------------------------------------
