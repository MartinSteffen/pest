package GUI;
import java.awt.*;


/* Wer die von uns gebotene Funktionalität nutzen will, bekommt
 * eine Referenz auf eine Klasse die dieses Interface implementiert
 * im Konstruktor uebergeben.*/


interface GUIInterface{

    /* addGUIMenu
     * fuegt ein "Menu" in die Menuzeile der GUI ein.
     * Kann mit removeGUIMenu wieder entfernt werden.*/
    public void addGUIMenu(Menu m);

    /* removeGUIMenu
     * entfernt ein mit addGUIMenu eingefügtes Menu wieder
     * aus der Menuzeile des GUI.*/
    public void removeGUIMenu(Menu m);

    /* userMessage
     * gibt im Meldungsfenster den uebergebenen String aus.
     */
    public void userMessage(String msg);

    /* OkDialog
     * Bringt einen modalen Dialog mit dem uebergebenen Titel,
     * Nachricht und OK-Button auf den Bildschirm.
     * Diese Varianten ohne "parent"-Parameter binden sich
     * an die GUI. */
    public int OkDialog(String Titel, String Msg);

    /* YesNoDialog
     * wie OkDialog, mit YES und NO statt OK.*/
    public int YesNoDialog(String Titel, String Msg);

    /* YesNoCancel
     * wie OkDialog, mit YES, NO und CANCEL, statt OK.*/
    public int YesNoCancelDialog(String Titel, String Msg);

    
    /* OkDialog
     * Bringt einen modalen Dialog mit dem uebergebenen Titel,
     * Nachricht und OK-Button auf den Bildschirm.
     * Diese Varianten mit "parent"-Parameter binden sich
     * an das uebergebene Fenster und blockieren es bis eine
     * Auswahl stattgefunden hat. */
    public int OkDialog(Frame par, String Titel, String Msg);

    /* YesNoDialog
     * wie OkDialog, mit YES und NO statt OK.*/
    public int YesNoDialog(Frame par, String Titel, String Msg);

    /* YesNoCancel
     * wie OkDialog, mit YES, NO und CANCEL, statt OK.*/
    public int YesNoCancelDialog(Frame par, String Titel, String Msg);
}
