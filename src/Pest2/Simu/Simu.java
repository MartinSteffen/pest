package Simu;


/**Die allumfassende Simulation-Klasse. Wir haben aus Kompatibilitaetsgruenden 2 Klassen: Simu und Simulation.*/ 
import Absyn.*;
import Editor.*;

public class Simu extends Object{

   /**Der alte Konstruktor: zunaechst nur die aktuelle Statechart als Parameter*/
   public Simu(Statechart s){};

  /** Konstruktor: Wir benoetigen die aktuelle Instanz des Editors, die Daten (Statechart). Was noch fehlt, sind die GUI-Elemente, wie
z.B. Menus oder Optionen **/
  public Simu(Statechart s, Editor e){
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
