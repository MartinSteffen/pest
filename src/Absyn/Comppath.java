package Absyn;

/**
 * Operation on path of statenames.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Comppath.java,v 1.4 1998-11-27 16:07:39 swtech00 Exp $
 */
public class Comppath extends Absyn {
/**
 * Path operation.
 */
    public Pathop s_pop;
/**
 * Path.
 */
    public Path   s_spath;
/**
 * Constructor.
 */
    public Comppath(Pathop op, Path p) {
	s_pop = op;
	s_spath = p;
    }
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Comppath.java,v 1.4 1998-11-27 16:07:39 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/26 16:32:14  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
