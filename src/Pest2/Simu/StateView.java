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
     setLabel(pathToString(p));
     /* disable() ?*/
   }

  String pathToString(Path path){
    String result="";
    String head=path.head;
    Path   tail=path.tail;
    while (tail!=null){
      result=head+".";
      head=tail.head;
      tail=tail.tail;
    }
    result+=head;
    return result;
  }


}

