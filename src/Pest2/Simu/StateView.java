package simu;


import java.awt.*;
import absyn.*;



class StateView extends Checkbox{

   State state=null;
   Path path=null;

   public StateView(Path p, State s){
     super(s.name.name,false);
     state=s;
     path=p;
     /* disable() ?*/
   }
}

