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
 * Bisher ist der Nachfolgeralgorithmus implementiert, aber noch nicht ausreichend getestet. 
 * Man kann auch schon etwas simulieren.<BR>
 * Es werden Racing-Situationen bei Conditions und Nichtdeterminismus bei Transitionen erkannt,
 * unsere GUI ist aber noch bearbeitungswuerdig.<BR>
 * Connectoren solten inzwischen funktionieren, sind aber noch nicht ausrichend getestet.<BR>
 * Getestet haben wir bisher nur am Beispiel aus absyn.Example,
 * welches auch funktioniert, d.h. es koennen Events eingeben werden, die States werden erreicht
 * und die Transitionen und Aktionen werden ausgefuehrt. <BR>
 * Die Aktiven States werden noch nicht im Editor angezeigt, dafuer aber auf der Console.
 * Die Aufrufe der Highlight-Funktionen sind schon vorgesehen, wir haben nur noch ein Problem
 * mit der Parameteruebergabe.
 * <DT><STRONG>
 * TODO.
 * </STRONG>
 * <UL>
 * <LI> Ausfuehrliche, dokumentierte Tests                 (-14.1)
 * </UL>
 * <DT><STRONG>
 * BEKANNTE FEHLER.
 * </STRONG>
 * <UL>
 * <LI> Noch nicht getestet, was absyn.Dummy anrichten kann
 * <LI> 
 * </UL>
 * <DT><STRONG>
 * TEMPORÄRE FEATURES.
 * </STRONG>
 * Unheimlich viel Muell auf System.err und System.out.<BR> Nebenbei werden pro Simulationsschritt 
 * die aktiven States angezeigt.<BR>
 * Achtung: Momentan sind einige Klassen public, bis auf Simu wird alles wieder verschwinden!!!!!!
 * </DL COMPACT>                    
 */
         
public class Simu extends Object{

  Communicator comm=null;
  Nachfolgermaschine maschine=null;

  /**Der gewuenschte Konstruktor: die aktuelle Statechart, ein Editorobject und das GUIInterface als Parameter*/    
  public Simu(Statechart s, Editor e, GUIInterface gui){
    if (s!=null){
      comm=new Communicator(gui);
      maschine=new Nachfolgermaschine(s,comm);
      //maschine=new Nachfolgermaschine(absyn.Example.getExample(),comm);
      comm.setMachine(maschine);
      comm.setEditor(e);
    }
  }
}













