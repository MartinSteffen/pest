package Absyn;

import java.io.Serializable;

public class SEvent implements Serializable, Cloneable {  
    public String name;
    public SEvent (String n) {
	name = n;
	
    }
    public Object clone() throws CloneNotSupportedException {
	return new SEvent(name);
    }
};


    
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: SEvent.java,v 1.5 1998-12-15 07:11:10 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1998/12/11 17:43:00  swtech00
//	Cloneable
//
//	Revision 1.3  1998/11/27 09:59:04  swtech20
//	name public
//
//	Revision 1.2  1998/11/26 16:32:22  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
