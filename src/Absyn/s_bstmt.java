package Absyn;


/**
 * One kind of transition action: boolean statement.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_bstmt.java,v 1.3 1998-11-27 16:14:31 swtech00 Exp $
 */
public class s_bstmt extends Action {
/**
 * Contents of the statement.
 */
  Boolstmt stmt;
  public s_bstmt  (Boolstmt st) {
    stmt = st;
  }
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_bstmt.java,v 1.3 1998-11-27 16:14:31 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:29  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
