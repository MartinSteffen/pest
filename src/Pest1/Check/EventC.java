package check;

import absyn.*;

/**
 * @author Java Praktikum: <a href="mailto:dw@ks.informatik.uni-kiel.de">Daniel Wendorff</a> und <a href="mailto:Stiller@T-Online.de">Magnus Stiller</a>
 *  @version  $Id: EventC.java,v 1.3 1999-02-14 20:56:26 swtech11 Exp $
 */
class EventC {
  SEvent e;
  Tr   t=null;
  String ort="";

  EventC(SEvent _e) {	e=_e;}

  EventC(SEvent _e, Tr _t) {
    e=_e;
    t=_t;
  }

  EventC(SEvent _e, String o) {
	  this(_e,null,o);
  }

  EventC(SEvent _e, Tr _t, String o) {
    e=_e;
    t=_t;
    ort=o;
  }
}
