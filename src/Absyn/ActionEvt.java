package Absyn;


/**
 * One kind of transition action: generation of events.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: ActionEvt.java,v 1.2 1998-12-11 17:39:13 swtech00 Exp $
 */
public class ActionEvt extends Action implements Cloneable {
  public SEvent event;
  public ActionEvt (SEvent e) {
    event  =  e;
  };

    public Object clone() throws CloneNotSupportedException {
	return new ActionEvt((SEvent)event.clone());
    };
    
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: ActionEvt.java,v 1.2 1998-12-11 17:39:13 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  1998/12/07 12:01:58  swtech24
//	*** empty log message ***
//
//	Revision 1.2  1998/12/03 17:10:48  swtech00
//	Felder public gemacht
//
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
