package Absyn;

public class Bassign extends Absyn {
    public Bvar s_blhs;
    public Guard s_brhs;
    public Bassign (Bvar l, Guard r) {
	s_blhs = l;
	s_brhs = r;
    };
};
    



//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Bassign.java,v 1.3 1998-11-26 16:32:11 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
