package simu;

import absyn.*;
import editor.*;
import gui.*;

/**swtech22 - Simulation: Die Simulationsumgebung wird durch einen einfachen Konstruktor gestartet
 * <STRONG> Anforderungen. </STRONG> Wir verlassen uns darauf, dass die
 * Statecharts, die uns uebergeben werden, folgende Eigenschaften haben:
 *
 * <ul>
 * <li> Syntaktisch korrekte Statechart, wie im Pflichtenheft angegeben, d.h ohne Schleifen und andere Widrigkeiten (z.B. fehlende Start- oder Endpunkte von Transitionen)
 * </ul>
 *
 * die mit folgenden Checks ueberprueft werden koennen:
 *
 * <ul>
 * <li> checkModel(Statechart)
 * </ul>
 */
         
public class Simu extends Object{

   /**Der alte Konstruktor: zunaechst nur die aktuelle Statechart als Parameter
   *Dummy: Es wird nur geprüft, ob s!=null ist.*/ 
   public Simu(Statechart s){
    if (s!=null){
      System.out.println("PEST2/Simulation hat eine Statechart erhalten");
    }
    else{
      System.out.println("PEST2/Simulation : Statechart ist null????");
    }
  };


   /**Der neue (alte) Konstruktor: die aktuelle Statechart und ein Editorobject als als Parameter
    * Dummy: Es wird nur geprüft, ob s!=null und e!=null ist.*/    
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
  }

   /**Der gewuenschte Konstruktor: die aktuelle Statechart, ein Editorobject und das GUIInterface als Parameter
    * Dummy: Es wird nur geprüft, ob s!=null und e!=null ist.*/    
  public Simu(Statechart s, Editor e, GUIInterface gui){
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
 }



}


