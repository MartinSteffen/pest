package Check;

import Absyn.*;

class testTransitions {

  testTransitions() {
  }
  boolean check(Statechart sc, modelCheckMsg mcm) {
    mcm.addWarning(4,"Root");
    return true;
  }

}
