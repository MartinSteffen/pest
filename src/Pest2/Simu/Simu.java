package simu;

import absyn.*;
import editor.*;
import gui.*;
import java.io.*;

/**swtech22 - Simulation
 * <P>
 * <STRONG>ANFORDERUNGEN</STRONG><BR> 
 * Wir verlassen uns darauf, dass die
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
 * <P>
 * <STRONG>
 * STATUS
 * </STRONG>
 * <BR>
 * Die Simulationsumgebung ist implementiert und getestet.
 * Der Leistungsumfang entspricht den Anforderungen des Pflichtenhefts.
 * Zum Testen empfehlen wir die Beispiele aus unserem Test-Verzeichnis (weiteres hierzu in dem README).
 * Alle Statusmeldungen auf System.out und System.err sind wie abgesprochen
 * abgeändert worden und tauchen jetzt nur noch bei gesetztem Debugschalter auf.
 * <P>
 * <STRONG>
 * TODO.
 * </STRONG><BR>
 * <UL>
 * <LI> nichts
 * </UL>
 * <P>
 * <STRONG>
 * BEKANNTE FEHLER.
 * </STRONG><BR>
 * <UL>
 * <LI> NullPointer in substates werden nicht abgefangen, d.h ein OR-State muss mindestens einen Substate haben, ansonsten waere es ein Basic-State (ist eigentlich nicht direkt ein Fehler....)</LI>
 * </UL>        
 * 
 * @author Oliver Otte, Sven Thomsen
 * @version $Id: Simu.java,v 1.17 1999-02-23 13:37:16 swtech22 Exp $
 */
         
public class Simu extends Object{

  Communicator comm=null;
  Nachfolgermaschine maschine=null;

  /**Der gewuenschte Konstruktor: die aktuelle Statechart, ein Editorobject und das GUIInterface als Parameter*/    
  public Simu(Statechart s, Editor e, GUIInterface gui){
    if (s!=null){
      comm=new Communicator(gui,s);
      maschine=new Nachfolgermaschine(s,comm,gui);
      //maschine=new Nachfolgermaschine(absyn.Example.getExample(),comm);
      comm.setMachine(maschine);
      comm.setEditor(e);
    }
  }
}













