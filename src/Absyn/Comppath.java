package Absyn;

public class Comppath extends Absyn {
    public Pathop s_pop;
    public Path   s_spath;
    public Comppath(Pathop op, Path p) {
	s_pop = op;
	s_spath = p;
    }
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Comppath.java,v 1.3 1998-11-26 16:32:14 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
