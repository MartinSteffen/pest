package Check;

import Absyn.*;
import java.util.*;

class test {
  modelCheckMsg msg = new modelCheckMsg();
  Statechart sc = new Statechart(null,null,null,null);

  test() { };
  
  test(Statechart s, modelCheckMsg m) {sc=s; msg=m;}
  
  void nextTransInTransList(TrList tl) {
      //   System.out.println("Trans: "+((Statename)tl.head.source).name+" -> "+((Statename)tl.head.target).name);
      //   System.out.println(tl.tail);
    if (tl.tail != null) { nextTransInTransList(tl.tail); }
  }

  void nextStateInStateList(StateList sl) {
    if (sl.head instanceof Or_State) {testTransOrState ((Or_State)sl.head); }
    if (sl.head instanceof And_State) {testTransAndState ((And_State)sl.head); }
    if (sl.tail != null) { nextStateInStateList(sl.tail); }
  }

  void testTransOrState(Or_State os) {
      //  System.out.println("OR-State: "+os.name);
    if (os.trs != null) { nextTransInTransList(os.trs); }
    if (os.substates != null) { nextStateInStateList(os.substates); }
  }

  void testTransAndState(And_State as) {
      //   System.out.println("AND-State: "+as.name);
    if (as.substates != null) { nextStateInStateList(as.substates); }
  }


}
