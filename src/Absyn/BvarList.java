package Absyn;

public class BvarList extends Absyn {
    public Bvar head;
    public BvarList tail;
    public BvarList (Bvar h, BvarList tl) {
	head = h;
	tail = tl;
    };
};
