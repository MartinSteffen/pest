package Absyn;


/**
 * One kind of transition action: generation of events.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: s_egen.java,v 1.3 1998-11-27 16:16:22 swtech00 Exp $
 */
public class s_egen extends Action {
  SEvent event;
  public s_egen (SEvent e) {
    event  =  e;
  };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_egen.java,v 1.3 1998-11-27 16:16:22 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:31  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
