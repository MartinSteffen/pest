package Absyn;

import java.io.Serializable;


public class Location implements Serializable, Cloneable {

    public Object clone() throws CloneNotSupportedException {
	return new Location ();
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Location.java,v 1.4 1998-12-15 07:11:09 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/12/11 17:42:59  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:18  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
