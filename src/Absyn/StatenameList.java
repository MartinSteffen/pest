package absyn;

import java.io.Serializable;

public class StatenameList extends Absyn implements Serializable, Cloneable {
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
      StatenameList tailclone =  (tail == null) ? null :  (StatenameList)tail.clone();
      Statename    headclone  = (head == null) ? null :  (Statename)head.clone();

      return new StatenameList(headclone,
				tailclone);
    };




}


