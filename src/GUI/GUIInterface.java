package gui;
import java.awt.*;


/** Dieses Interface beinhaltet alle von uns (GUI) gebotene Funktionalitaet.
 * Wer die von uns gebotene Funktionalit�t nutzen will, muss
 * eine Referenz auf eine Klasse die dieses Interface implementiert
 * im Konstruktor uebergeben bekommen. (..wenn, dann sagt Bescheid)
 * @author Christian Spinneker / Ingo Mielsch
 * @version $id:$
*/


public interface GUIInterface{

    /** Diese Funktion
     * fuegt ein "Menu" in die Menuzeile der GUI ein.
     * Kann mit removeGUIMenu wieder entfernt werden.
     * Es wird erwartet, dass das Menu bereits mit einem
     * eigenen ActionListener verknuepft wurde !
     * @see removeGUIMenu
     * @param m       Referenz auf ein komplettes Menu, das eingef�gt werden soll
     */
    public void addGUIMenu(Menu m);


    /** Diese Funktion
     * entfernt ein mit addGUIMenu eingef�gtes Menu wieder
     * aus der Menuzeile des GUI.
     * @see addGUIMenu
     * @param m        Referenz auf das mit addGUIMenu eingefuegte Menu 
     */
    public void removeGUIMenu(Menu m);


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

    /** YesNoDialog
     * wie OkDialog, mit YES und NO statt OK.
     * @see OkDialog
     */
    public int YesNoDialog(String Titel, String Msg);

    /** YesNoCancel
     * wie OkDialog, mit YES, NO und CANCEL, statt OK.
     * @see OkDialog
     */
    public int YesNoCancelDialog(String Titel, String Msg);

    
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

    /** YesNoDialog
     * wie OkDialog, mit YES und NO statt OK.
     * @see OkDialog
     */
    public int YesNoDialog(Frame par, String Titel, String Msg);

    /** YesNoCancel
     * wie OkDialog, mit YES, NO und CANCEL, statt OK.
     * @see OkDialog
     */
    public int YesNoCancelDialog(Frame par, String Titel, String Msg);
}

