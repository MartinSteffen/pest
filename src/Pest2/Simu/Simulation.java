package Simu;


/**Die allumfassende Simulation-Klasse*/
import Absyn.*;
import Editor.*;

public class Simulation extends Object{

   /**Der alte Konstruktor: zunaechst nur die aktuelle Statechart als Parameter*/
   public Simulation(Statechart s){};

  /** Konstruktor: Wir benoetigen die aktuelle Instanz des Editors, die Daten (Statechart). Was noch fehlt, sind die GUI-Elemente, wie
z.B. Menus oder Optionen **/
  public Simulation(Statechart s, Editor e){
    if (s!=null){
      System.out.println("PEST2/Simulation hat eine Statechart erhalten");
    }
    else{
      System.out.println("PEST2/Simulation : Statechart ist null????");
    }
    if (e!=null){
      System.out.println("PEST2/Simulation hat ein Editorobject erhalten");
    }
    else{
      System.out.println("PEST2/Simulation : Editor ist null????");
    }
  };
}
