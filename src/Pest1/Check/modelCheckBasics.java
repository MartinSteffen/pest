package Check;

import Absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: modelCheckBasics.java,v 1.1 1998-12-03 21:58:07 swtech11 Exp $
 */
class modelCheckBasics {
  modelCheckMsg msg = new modelCheckMsg();
  Statechart sc = new Statechart(null,null,null,null);
  boolean printOut; // falls true, dann schrittweise Ausgabe der Chart

  modelCheckBasics() { };

  modelCheckBasics(Statechart s, modelCheckMsg m) {
    sc = s;  
    msg = m;
    printOut = false;
  }
  
  void nextTransInTransList(TrList tl) {
    if (printOut == true) { System.out.println("Trans: "+((Statename)tl.head.source).name+" -> "+((Statename)tl.head.target).name); }
    if (tl.tail != null) { nextTransInTransList(tl.tail); }
  }

  void nextStateInStateList(StateList sl) {
    if (sl.head instanceof Or_State) {testTransOrState ((Or_State)sl.head); }
    if (sl.head instanceof And_State) {testTransAndState ((And_State)sl.head); }
    if (sl.tail != null) { nextStateInStateList(sl.tail); }
  }

  void testTransOrState(Or_State os) {
    if (printOut == true) { System.out.println("OR-State: "+os.name.name); }
    if (os.trs != null) { nextTransInTransList(os.trs); }
    if (os.substates != null) { nextStateInStateList(os.substates); }
  }

  void testTransAndState(And_State as) {
    if (printOut == true) { System.out.println("AND-State: "+as.name.name); }
    if (as.substates != null) { nextStateInStateList(as.substates); }
  }


}
