package Check;

import Absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testStates.java,v 1.2 1998-12-06 23:03:55 swtech11 Exp $
 */
class testStates extends modelCheckBasics{

  testStates(Statechart _s, modelCheckMsg _m) {
    super(_s,_m);
  }
  boolean check() {
    msg.addWarning(3,"bei allen States");
    return true;
  }

}
