package Check;

import Absyn.*;


/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: testTransitions.java,v 1.2 1998-12-03 21:58:11 swtech11 Exp $
 */
class testTransitions extends modelCheckBasics {

  testTransitions(Statechart s, modelCheckMsg m) {
    super(s,m);
    // printOut = true;
  }

  boolean check() {
    if (sc.state instanceof Or_State) {testTransOrState ((Or_State)sc.state); }
    if (sc.state instanceof And_State) {testTransAndState ((And_State)sc.state); }  
    msg.addWarning(4,"Root");
    return true;
  }

}
