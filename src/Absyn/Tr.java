package Absyn;



public class Tr  {   // Transition 
    public TrAnchor source;
    public TrAnchor target;
    public Label    label;
    public Tr (TrAnchor s, TrAnchor t, Label l) {
	source = s;
	target = t;
	label  = l;
    };
};







