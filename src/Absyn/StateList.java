package Absyn;

public class StateList {
    public State head;
    public StateList tail;
    public StateList(State h, StateList tl) {
	head = h;
	tail = tl;
    };
};


