package Absyn;


public class Conname extends TrAnchor implements Cloneable{
    public String name;
    public Conname(String n) {
	name  = n;
    };
    
    public Object clone() throws CloneNotSupportedException {
	return new Conname(name);
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Conname.java,v 1.3 1998-12-11 17:42:57 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:14  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
