package Check;

import Absyn.*;

class testBVars {

  testBVars() {
  }
  boolean check(Statechart sc, modelCheckMsg mcm) {
    mcm.addWarning(5,"Root");
    return true;
  }

}
