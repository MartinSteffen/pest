package Absyn;

public class StatenameList extends Absyn implements Cloneable {
    public Statename head;
    public StatenameList  tail;



    public StatenameList(Statename h, StatenameList tl) {
	head = h;
	tail = tl;
    };




    public Object clone() throws CloneNotSupportedException {
	StatenameList tailclone;
	if (tail != null)
	    tailclone = (StatenameList)tail.clone();
	else
	    tailclone = null;
	return new StatenameList((Statename)head.clone(),
				 (StatenameList)tailclone);
    };




}


