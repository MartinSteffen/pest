package Absyn;

/**
 * One kind of boolean statement: assigns false to the variable
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_mfalse.java,v 1.3 1998-11-27 16:18:53 swtech00 Exp $
 */
public class s_mfalse extends Boolstmt{
/**
 * Variable to change.
 */
  Bvar var;
/**
 * Constructor.
 */
  public s_mfalse(Bvar v){
    var = v;
  };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_mfalse.java,v 1.3 1998-11-27 16:18:53 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:33  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
