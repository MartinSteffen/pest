package Absyn;


/**
 * One kind of boolean statement: assigns true to the variable.
 * @author Initially provided by Martin Steffen.
 * @version $Id: MTrue.java,v 1.1 1998-12-01 17:52:55 swtech00 Exp $
 */
public class MTrue extends Boolstmt{
/**
 * Variable to change.
 */
  Bvar var;
  public MTrue(Bvar v){
    var = v;
  };
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: MTrue.java,v 1.1 1998-12-01 17:52:55 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/27 16:19:32  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:34  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
