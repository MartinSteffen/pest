package Simu;

import Absyn.*;

/**swtech22 - Simulation: Die Simulationsumgebung wird durch einen einfachen Konstruktor gestartet*/ 
public class Simu extends Object{

   /**Der Konstruktor: zunaechst nur die aktuelle Statechart als Parameter*/
   /*Dummy: Es wird nur geprüft, ob s!=null ist.*/ 
   public Simu(Statechart s){
    if (s!=null){
      System.out.println("PEST2/Simulation hat eine Statechart erhalten");
    }
    else{
      System.out.println("PEST2/Simulation : Statechart ist null????");
    }
  };
}
