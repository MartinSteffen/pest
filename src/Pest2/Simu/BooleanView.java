package simu;

import java.awt.*;
import absyn.*;


class BooleanView extends Checkbox{

   Bvar bvar=null;

   public BooleanView(Bvar b){
     super("",false);
     String label=b.var;
     bvar=b;
     setLabel(label);
   }
}

