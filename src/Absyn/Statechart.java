package Absyn;


public class Statechart {
    public SEventList  events;
    public BvarList bvars;
    public PathList cnames;
    public State    state;
    public Statechart(SEventList el, 
	       BvarList vl,
	       PathList pl,
	       State    st) {
	events = el;
	bvars  = vl;
	cnames = pl;
	state  = st;
    }
};
