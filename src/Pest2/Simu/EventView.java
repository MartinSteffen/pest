package simu;

import java.awt.*;
import absyn.*;


class EventView extends Checkbox{

   SEvent event=null;

   EventView(SEvent e){
     super(e.name,false);
     event=e;
   }
}
