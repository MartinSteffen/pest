package Absyn;

public class Label {
    public Guard guard;
    public Action action;
    public Label (Guard g, Action a) {
	guard = g;
	action = a;
    }
}


