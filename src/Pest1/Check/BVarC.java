package check;

import absyn.*;

/**
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 *  @version  $Id: BVarC.java,v 1.3 1999-02-14 20:56:19 swtech11 Exp $
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
