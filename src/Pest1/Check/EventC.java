package check;

import absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: EventC.java,v 1.2 1999-01-28 20:56:19 swtech11 Exp $
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
