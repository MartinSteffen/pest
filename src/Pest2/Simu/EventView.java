package simu;

import java.awt.*;
import absyn.*;


public class EventView extends Checkbox{

   SEvent event=null;

   EventView(SEvent e){
     super(e.name,false);
     event=e;
   }
}
