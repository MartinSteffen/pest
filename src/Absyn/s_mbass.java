package  Absyn;

/**
 * One kind of boolean statement: assignment.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_mbass.java,v 1.3 1998-11-27 16:18:16 swtech00 Exp $
 */
public class s_mbass extends Boolstmt{
/**
 * Contents of the assignment.
 */
  Bassign ass;
/**
 * Constructor.
 */
  public s_mbass (Bassign a) {
    ass = a;
  }
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_mbass.java,v 1.3 1998-11-27 16:18:16 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:33  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
