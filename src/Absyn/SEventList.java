package Absyn;

public class SEventList {
    SEvent  head;
    SEventList  tail;
    public SEventList(SEvent h, SEventList tl) {
	head = h;
	tail = tl;
    };
};
