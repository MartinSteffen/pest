package check;

import absyn.*;


 class EventC {
    SEvent e;
    Tr   t=null;
    String ort="";
    
    EventC(SEvent _e) {
	e=_e;}

    EventC(SEvent _e, Tr _t) {
    e=_e;
    t=_t;};

    EventC(SEvent _e, String o) {
	  this(_e,null,o);}

    EventC(SEvent _e, Tr _t, String o) {
    e=_e;
    t=_t;
    ort=o;};}
