package check;

import absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: BVarC.java,v 1.2 1999-01-28 20:56:16 swtech11 Exp $
 */
class BVarC {
  Bvar b;
  Tr   t=null;
  String ort="";

  BVarC(Bvar _b) { b=_b;}

  BVarC(Bvar _b, Tr _t) {
    b=_b;
    t=_t;
  }

  BVarC(Bvar _b, String o) {
	  this(_b,null,o);
  }

  BVarC(Bvar _b, Tr _t, String o) {
    b=_b;
    t=_t;
    ort=o;
  }
}
