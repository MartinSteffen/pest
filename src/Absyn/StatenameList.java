package absyn;

import java.io.Serializable;

public class StatenameList implements Serializable, Cloneable {
    public Statename head;
    public StatenameList  tail;



    public StatenameList(Statename h, StatenameList tl) {
	head = h;
	tail = tl;
    };




/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
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


