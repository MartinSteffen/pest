package simu;

import absyn.*;
import editor.*;
import gui.*;
import java.io.*;

/**swtech22 - Simulation: Die Simulationsumgebung wird durch einen einfachen Konstruktor gestartet
 * <P>
 * <STRONG> Anforderungen. </STRONG> Wir verlassen uns darauf, dass die
 * Statecharts, die uns uebergeben werden, folgende Eigenschaften haben:
 *
 * <ul>
 * <li> Syntaktisch korrekte Statechart, wie im Pflichtenheft angegeben, d.h ohne Schleifen und andere Widrigkeiten (z.B. fehlende Start- oder Endpunkte von Transitionen, unerlaubte Null-Pointer(ausser als Listen))
 * </ul>
 *
 * die mit folgenden Checks ueberprueft werden koennen:
 *
 * <ul>
 * <li> checkModel(Statechart)
 * </ul>
 * <DL COMPACT>
 * <DT><STRONG>
 * STATUS
 * </STRONG>
 * <BR>
 * Der Nachfolgeralgorithmus ist implementiert und mit einigen Beispielen aus
 * "Examples" getestet. Das Testen gestaltet sich etwas schwierig, da wir bisher
 * die Transitionsbeschriftungen "erraten" mussten.
 * Es werden Racing-Situationen bei Conditions und Nichtdeterminismus bei Transitionen erkannt,
 * unsere GUI ist aber noch bearbeitungswuerdig.<BR>
 * Getestet haben wir z.B. absyn.Example, tv.st und a1.st
 * was auch funktioniert, d.h. es koennen Events eingeben werden, die States werden erreicht
 * und die Transitionen und Aktionen werden ausgefuehrt. <BR>
 * Die Aktiven States werden noch nicht im Editor angezeigt, dafuer aber auf der Console.
 * Die Aufrufe der Highlight-Funktionen sind schon drin, werden bisher vom Editor aber noch nicht verarbeitet (Stand: 28.1)
 * Alle Statusmeldungen auf System.out und System.err sind wie abgesprochen
 * abgeändert worden und tauchen jetzt nur noch bei gesetztem Debugschalter.
 * <DT><STRONG>
 * TODO.
 * </STRONG>
 * <UL>
 * <LI> weitere Tests
 * </UL>
 * <DT><STRONG>
 * BEKANNTE FEHLER.
 * </STRONG>
 * <UL>
 * <LI> NullPointer in substates werden nicht abgefangen, d.h ein OR-State muss mindestens einen Substate haben, ansonsten waere es ein Basic-State (ist eigentlich nicht direkt ein Fehler....)</LI>
 * <LI> Bei Racing-Situatuionen bei booleschen Variablen ist unsere Gui nicht 
   sehr hilfreich</LI>
 
 * </UL>                    
 */
         
public class Simu extends Object{

  Communicator comm=null;
  Nachfolgermaschine maschine=null;

  /**Der gewuenschte Konstruktor: die aktuelle Statechart, ein Editorobject und das GUIInterface als Parameter*/    
  public Simu(Statechart s, Editor e, GUIInterface gui){
    if (s!=null){
      comm=new Communicator(gui);
      maschine=new Nachfolgermaschine(s,comm,gui);
      //maschine=new Nachfolgermaschine(absyn.Example.getExample(),comm);
      comm.setMachine(maschine);
      comm.setEditor(e);
    }
  }
}













