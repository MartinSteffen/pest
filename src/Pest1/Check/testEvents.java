package Check;

import Absyn.*;

class testEvents {

  testEvents() {
  }
  boolean check(Statechart sc, modelCheckMsg mcm) {
    mcm.addWarning(2,"Root");
    return true;
  }

}
