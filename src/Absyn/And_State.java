package Absyn;


public class And_State extends State{
    public Statename s_aname;
    public StateList s_asubstates;
    public And_State(Statename name,
	      StateList substates) 
    {
	s_aname = name;
	s_asubstates = substates;
    }
	
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: And_State.java,v 1.2 1998-11-26 16:32:10 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//
//----------------------------------------------------------------------
