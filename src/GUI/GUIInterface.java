package gui;
import java.awt.*;


/** Dieses Interface beinhaltet alle von uns (GUI) gebotene Funktionalitaet.
 * Wer die von uns gebotene Funktionalität nutzen will, muss
 * eine Referenz auf eine Klasse die dieses Interface implementiert
 * im Konstruktor uebergeben bekommen. (..wenn, dann sagt Bescheid)
 * @author Christian Spinneker / Ingo Mielsch
 * @version $id:$
*/


public interface GUIInterface{

    /** Diese Funktion
     * gibt im Meldungsfenster den uebergebenen String aus.
     * Wir sind hier sehr universell geblieben, aber:
     * Ausgaben sollte der Modulname vorangestellt werden, event TAB,
     * ein Doppelpunkt und dann der Text
     * @param msg      Naja, der Text halt  :-)
     */
    public void userMessage(String msg);

    /** 
     * Bringt einen modalen Dialog mit dem uebergebenen Titel,
     * Nachricht und OK-Button auf den Bildschirm.
     * Diese Varianten ohne "parent"-Parameter binden sich
     * an die GUI.
     * Auch hier sind wir universell geblieben. Fehlermeldungen
     * sollen mit "Fehler" betitelt werden und Warnungen mit "Warnung".
     * BITTE BEACHTEN !
     * @param Titel        Text fuer den Fenstertitel
     * @param Msg          Nachricht im Fenster
     */
    public int OkDialog(String Titel, String Msg);

    /** EingabeDialog
     *wie OkDialog,jedoch mit OK und Abbrechen.Bietet zusätzlich noch ein
     *TextField zu Eingabe von Text,ein Defaulttext muss uebergeben werden.
     *Der Rueckgabewert liefert den aktuellen String des TextFields
     *BITTE BEACHTEN !
     *@param Titel       Text fuer Fenstertitel
     *@param Msg         Nachricht im Fenster
     *@param Defaulttext Defaulttext fuer TextField
     */  

    public String EingabeDialog(String Titel, String Msg, String Defaulttext);

    
    /** OkDialog
     * Bringt einen modalen Dialog mit dem uebergebenen Titel,
     * Nachricht und OK-Button auf den Bildschirm.
     * Diese Varianten mit "parent"-Parameter binden sich
     * an das uebergebene Fenster und blockieren es bis eine
     * Auswahl stattgefunden hat. 
     * Auch hier sind wir universell geblieben. Fehlermeldungen
     * sollen mit "Fehler" betitelt werden und Warnungen mit "Warnung".
     * BITTE BEACHTEN !
     * @param par          Referenz auf das Elternfenster
     * @param Titel        Text fuer den Fenstertitel
     * @param Msg          Nachricht im Fenster
     */

 
    public int OkDialog(Frame par, String Titel, String Msg);


     /** EingabeDialog
     *wie OkDialog,jedoch mit OK und Abbrechen.Bietet zusätzlich noch ein
     *TextField zu Eingabe von Text,ein Defaulttext muss uebergeben werden.
     *Der Rueckgabewert liefert den aktuellen String des TextFields
     *BITTE BEACHTEN !
     *@param par         Referenz auf das Elternfenster  
     *@param Titel       Text fuer Fenstertitel
     *@param Msg         Nachricht im Fenster
     *@param Defaulttext Defaulttext fuer TextField
     */  

    public String EingabeDialog( Frame par, String Titel, String Msg, String Defaulttext);

     /** getStatecolor
      * Gibt ein Color-Objekt zurück, welches zum Zeichnen von States benutzt
      * werden soll.
     */  
    public Color getStatecolor();

     /** getTransitioncolor
      * Gibt ein Color-Objekt zurück, welches zum Zeichnen von Transitionen benutzt
      * werden soll.
     */  
    public Color getTransitioncolor();

     /** getConnectorcolor
      * Gibt ein Color-Objekt zurück, welches zum Zeichnen von Connectoren benutzt
      * werden soll.
     */  

    public Color getConnectorcolor();

     /** StateChartHasChanged
      * Diese Funktion dient,dazu der GUI mitzuteilen, dass an den aktuellen Statechart Änderungen
      * vorgenommen wurden.
     */  

    public void StateChartHasChanged();

    /** editorClosing
     * Diese Funktion dient dazu,der GUI mitzuteilen,dass der Editor geschlosen wurde.
     */

    public void editorClosing();

    /** isDebug
     * Gibt den Debugging Status zurueck.
     */

    public boolean isDebug();

    /** simuExit
     * Diese Funktion dient dazu,der GUI mitzuteilen,dass der Simulator geschlosen wurde.
     */  

    public void simuExit();
}

