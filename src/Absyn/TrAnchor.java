package Absyn;

import java.io.Serializable;

public abstract class TrAnchor extends Absyn implements Serializable, Cloneable  {
    public abstract Object clone() throws  CloneNotSupportedException;
};







//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TrAnchor.java,v 1.5 1998-12-15 07:11:11 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/11 17:43:02  swtech00
//	Cloneable
//
//	Revision 1.3  1998/11/26 16:32:26  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
