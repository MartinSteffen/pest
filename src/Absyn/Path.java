package Absyn;


public class Path  {
    public String current;
    public Path   outer;
    public Path (String n, Path tl) {
	current    = n;
	outer      = tl;
    };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Path.java,v 1.3 1998-12-07 15:08:00 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:21  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
