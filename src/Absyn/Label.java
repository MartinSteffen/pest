package Absyn;

public class Label extends Absyn {
    public Guard guard;
    public Action action;
    public Label (Guard g, Action a) {
	guard = g;
	action = a;
    }
}


