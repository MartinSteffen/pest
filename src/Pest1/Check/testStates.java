package Check;

import Absyn.*;

class testStates {

  testStates() {
  }
  boolean check(Statechart sc, modelCheckMsg mcm) {
    mcm.addWarning(3,"Root");
    return true;
  }

}
