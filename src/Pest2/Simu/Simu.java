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
 * "Examples" getestet.
 * Es werden Racing-Situationen bei Conditions und Nichtdeterminismus bei Transitionen erkannt,
 * unsere GUI ist funktionell aber noch nicht schoen.<BR>
 * Getestet haben wir absyn.Example, tv.st und a1.st, sowie die Beispiele aus unserem Test-Verzeichnis.
 * Die Aktiven States und Transitionen werden gehighlighted, das Highlighting hat noch ein paar kleinere
 * Macken. (Workaround: Fenster verkleinern und wieder vergroessern, repaint() hilft :-) 
 * Alle Statusmeldungen auf System.out und System.err sind wie abgesprochen
 * abgeändert worden und tauchen jetzt nur noch bei gesetztem Debugschalter auf.
 * Die bekannten Fehler mit Connectoren sind behoben.
 * Inzwischen kann man sowohl die Schrittweite eingeben, als auch die Aufloesung von Nichtdeterminismen
 * veraendern (Zufall oder Benutzer).
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













