package simu;

import absyn.*;
import editor.*;

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


   /**Der neue (alte) Konstruktor: die aktuelle Statechart und ein Editorobject als als Parameter*/
   /*Dummy: Es wird nur geprüft, ob s!=null und e!=null ist.*/    
  public Simu(Statechart s, Editor e){
   if (s!=null){
      System.out.println("PEST2/Simulation hat eine Statechart erhalten");
    }
    else{
      System.out.println("PEST2/Simulation : Statechart ist null????");
    }
    if (e!=null){
      System.out.println("PEST2/Simulation hat einen Editor erhalten");
    }
    else{
      System.out.println("PEST2/Simulation : Editor ist null????");
    }
 };



}
