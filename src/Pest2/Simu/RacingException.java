package simu;

import absyn.*;

class RacingException extends Exception{
  Bvar var=null;
  BooleanTabelle status=null;
 
  RacingException(BooleanTabelle s, Bvar v){
    super();
    status=s;
    var=v;
  }

  RacingException(){
    super();
  }


}
