package Absyn;


/**
 * One kind of boolean statement: assigns true to the variable.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_mtrue.java,v 1.3 1998-11-27 16:19:32 swtech00 Exp $
 */
public class s_mtrue extends Boolstmt{
/**
 * Variable to change.
 */
  Bvar var;
  public s_mtrue(Bvar v){
    var = v;
  };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_mtrue.java,v 1.3 1998-11-27 16:19:32 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:34  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
