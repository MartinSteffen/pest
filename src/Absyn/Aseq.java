package Absyn;

public class Aseq extends Absyn {
    public Action head;
    public Aseq tail;
    public Aseq (Action h, Aseq tl) {head = h;tail = tl; };
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Aseq.java,v 1.3 1998-11-26 16:32:10 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
