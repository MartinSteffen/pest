package Absyn;

/**
 * One kind of guard: event.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_event.java,v 1.3 1998-11-27 16:17:39 swtech00 Exp $
 */
public class s_event extends Guard {
/**
 * Contents of event.
 */
    public SEvent event;
/**
 * Constructor.
 */
    public s_event (SEvent e) {
	event = e;
    }
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_event.java,v 1.3 1998-11-27 16:17:39 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:32  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
