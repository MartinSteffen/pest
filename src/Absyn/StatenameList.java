package Absyn;

public class StatenameList extends Absyn {
    public Statename head;
    public StatenameList  tail;
    public StatenameList(Statename h, StatenameList tl) {
	head = h;
	tail = tl;
    }
}

