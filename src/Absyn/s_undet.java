package Absyn;


/**
 * One kind of guard: untyped identifier.
 * If the type of the identifier can not be determined, this class is used.
 * @author Initially provided by Martin Steffen.
 * @version $Id: s_undet.java,v 1.3 1998-11-27 16:20:49 swtech00 Exp $
 */
public class s_undet extends Guard {
/**
 * Identifier name.
 */
    public String undet;
/**
 * Constructor.
 */
    public s_undet (String u) {
	undet = u;
    };
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: s_undet.java,v 1.3 1998-11-27 16:20:49 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:35  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
