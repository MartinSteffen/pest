package Absyn;

/**
 * Connector.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Connector.java,v 1.4 1998-11-27 16:08:16 swtech00 Exp $
 */
public class Connector  extends Absyn {
/**
 * Connector name is used for initial bookkeeping only.
 */
    public Conname name;
/**
 * Constructor.
 */
    public Connector(Conname n) {
	name = n;
    }
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Connector.java,v 1.4 1998-11-27 16:08:16 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/26 16:32:15  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
