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
