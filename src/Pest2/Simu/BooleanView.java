package simu;

import java.awt.*;
import absyn.*;


class BooleanView extends Checkbox{

   Bvar bvar=null;

   public BooleanView(Bvar b){
     super("",false);
     bvar=b;
   }
}

