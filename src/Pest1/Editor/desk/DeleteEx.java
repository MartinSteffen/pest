package editor.desk;

import absyn.*;
import util.*;


class DeleteEx{

    public static void main (String[] args) {


	Statechart sc = Example.getExample();


	State p3 = ((Or_State)sc.state).substates.tail.tail.head;

	System.out.println(p3.name);

	// Delete D = new Delete(

    }

}



