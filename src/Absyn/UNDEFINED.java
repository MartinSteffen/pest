package Absyn;

import java.io.Serializable;

public class UNDEFINED extends TrAnchor implements Cloneable, Serializable{

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new UNDEFINED();
    };

};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: UNDEFINED.java,v 1.5 1998-12-15 13:38:09 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/15 07:11:12  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:43:03  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:27  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
