package Absyn;


/**
 * One kind of transition action: generation of events.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: ActionEvent.java,v 1.2 1998-12-03 17:10:48 swtech00 Exp $
 */
public class ActionEvent extends Action {
  public SEvent event;
  public ActionEvent (SEvent e) {
    event  =  e;
  };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ActionEvent.java,v 1.2 1998-12-03 17:10:48 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  1998/12/01 17:52:53  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.3  1998/11/27 16:16:22  swtech00
//	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
//	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
//
//	Revision 1.2  1998/11/26 16:32:31  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
