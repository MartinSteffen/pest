package Check;

import Absyn.*;
import java.util.*;

class testEvents extends test{
  private Vector Ist;
  private Vector Soll;
  testEvents() {
    Ist=new Vector();
    Soll=new Vector();
  }

  boolean check(Statechart sc, modelCheckMsg mcm) {
    msg=mcm;
    erstelle_Ist(sc);
    erstelle_Soll(sc);
    vergleiche();
    msg.addWarning(2,"Root");
    mcm=msg;
    return true;
  }
  void erstelle_Ist(Statechart sc) {
     SEventList e=sc.events;
     int i=0;
     for(;((e!=null) && (i<2*Ist.size()));){
        if (Ist.contains(e.head)) {i++;msg.addError(200,e.head.name);}
          else {Ist.addElement(e.head);};
         e=e.tail;
        }
     if (Ist.size()<i) {/* Prüfe auf Kreis in BVarList */}
   };

  void erstelle_Soll(Statechart sc) {

  // erstellen einer Liste von Events, die in Guard oder Action vorkommen

  };

  void vergleiche(){
  for(;!Ist.isEmpty();){
    if (Soll.contains(Ist.elementAt(0))) {Ist.removeElementAt(0);}
       else {msg.addError(201,((SEvent)Ist.elementAt(0)).name); Ist.removeElementAt(0); };
  };

  };

}
