package Absyn;


public class And_State extends State{
    public StateList substates;
    public And_State(Statename n,
		     StateList sl) 
    {
	name = n;
	substates = sl;
    }
	
}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: And_State.java,v 1.3 1998-11-30 17:07:40 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  1998/11/26 16:32:10  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
