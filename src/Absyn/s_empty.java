package Absyn;


/**
 * One kind of transition action: empty action.
 * <br>
 * <br> Initially provided by Martin Steffen.
 * <br> Version $Id: s_empty.java,v 1.3 1998-11-27 16:17:00 swtech00 Exp $
 */
public class s_empty extends Action {
  Dummy dummy;
  public s_empty (Dummy d) {
    dummy = d;
  }
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_empty.java,v 1.3 1998-11-27 16:17:00 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:32  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
