package check;
import absyn.*;

 class BVarC {
    Bvar b;
    Tr   t=null;
    String ort="";
    
      BVarC(Bvar _b) {
	b=_b;}

    BVarC(Bvar _b, Tr _t) {
    b=_b;
    t=_t;};

      BVarC(Bvar _b, String o) {
	  this(_b,null,o);}

    BVarC(Bvar _b, Tr _t, String o) {
    b=_b;
    t=_t;
    ort=o;};}
