package Absyn;


/**
 * Boolean variable.
 * @author Initially provided by Martin Steffen.
 * @version $Id: Bvar.java,v 1.4 1998-11-27 16:05:31 swtech00 Exp $
 */
public class Bvar  extends Absyn  {
/**
 * Contents of the variable.
 */
    public String var;
/**
 * Constructor.
 */
    public Bvar (String v) {
	var = v;
    };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Bvar.java,v 1.4 1998-11-27 16:05:31 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/11/26 16:32:12  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
