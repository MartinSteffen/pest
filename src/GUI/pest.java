package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import GUI.popdialoge.*;

import Absyn.*;

/** Diese Klasse ist die Hauptklasse von PEST
 * und daher ist sie auch startbar (sprich "main" ist adequat 
 * vorhanden. Sie bildet das Hauptfenster von PEST und implementiert
 * das "GUIInterface". Die einzigen PUBLIC-Elemente sind gegenw�rtig
 * die des Interface und die "main"-Methode.
 * Darum wird hier nichts beschrieben.
 * @see GUIInterface
 * @author Christian Spinneker / Ingo Mielsch
 * @version $id:$
*/


public class pest
extends Frame
implements GUIInterface
{
    FileDialog fDialog;
    GUIMenu theGUIMenu;
    TextArea MsgWindow;

    Statechart SyntaxBaum = null;
    String     SBDateiname = null;

    boolean CheckedSC = false;
    boolean ResultSC = false;

   public static void main(String[] args)
    {
	new pest();
    }
    
    public pest()
    {
	super("Und hier kommt die PEST");
	setBackground(Color.lightGray);
	setSize(500,400);  
	setLocation(200,100);
	setFont(new Font("Serif",Font.PLAIN,18));
	GUIWindowListener lis = new GUIWindowListener();
	theGUIMenu = new GUIMenu(this);
	setMenuBar(theGUIMenu);
	addWindowListener(lis);

        MsgWindow = new TextArea(200,80);
	MsgWindow.setFont(new Font("Serif",Font.PLAIN,14));
	MsgWindow.setEditable(false);
	add(MsgWindow);

	setVisible(true);
	fDialog = new FileDialog(this);

	theGUIMenu.updateMenu();
	userMessage("GUI   : PEST initialisiert");
	repaint();
   
    }


    boolean checkSB()
    {
	if(!CheckedSC)
	    {
		Check.modelCheck SCchecker = new Check.modelCheck();
		ResultSC = SCchecker.checkModel(SyntaxBaum);
		CheckedSC = true;
		if (SCchecker.getErrorNumber()==0)
		    {
			ResultSC = true;
		    }
	    }
	else
	    {
		userMessage("GUI   : SyntaxBaum nicht ver�ndert -> Benutze altes Ergebnis");
	    }

	if (!ResultSC)
	    {
		userMessage("GUI   : SyntaxCheck fehlgeschlagen");
	    }
	else
	    {
		userMessage("GUI   : SyntaxCheck erfolgreich :-)");
	    }

	return ResultSC;
    }

    void setStatechart(Statechart sc,String name)
    {
	SyntaxBaum = sc;
	SBDateiname = name;
	CheckedSC = false;
	ResultSC  = false;
	theGUIMenu.updateMenu();	
    }



    public void addGUIMenu(Menu m){
	theGUIMenu.add(m);
    }

    public void removeGUIMenu(Menu m){
	theGUIMenu.remove(m);
    }

    public void userMessage(String msg){
	MsgWindow.append(msg+"\n");
    }

    public int OkDialog(String Titel, String Msg){
	return new OKDialog(this,getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public int YesNoDialog(String Titel, String Msg){
	return new YesNoDialog(this,getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public int YesNoCancelDialog(String Titel, String Msg){
	return new YesNoCancelDialog(this,getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public int OkDialog(Frame par, String Titel, String Msg){
	return new OKDialog(par,par.getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public int YesNoDialog(Frame par, String Titel, String Msg){
	return new YesNoDialog(par,par.getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public int YesNoCancelDialog(Frame par, String Titel, String Msg){
	return new YesNoCancelDialog(par,par.getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    
    
    /**
     * Verwirft das aktuelle StateChart und laedt ein neues aus einer Datei,
     * welche mittels eines FileDialog Abgefragt wird.
     *
     * @see            java.awt.FileDialog
     */
    
    void load()
    {
	fDialog.setMode(FileDialog.LOAD);
	fDialog.setTitle("StateChart laden");
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gew�hlt
	    {
		try {
		    BufferedReader  inFile = new BufferedReader(new FileReader(fDialog.getDirectory()+FileName));
		    if(inFile.readLine().equals("zustand"))
			{
			    // hier soll noch irgendwie geladen werden
			    
			}
		    else
			{
			    OkDialog("Fehler","Ist keine PEST-Datei");
			}
		    inFile.close();
		    
		}catch (IOException e)
		    {
			OkDialog("Fehler","Auf die Datei kann nicht zugegriffen werden");
			// Alarm !
		    }
		
	    }
	
	fDialog.setVisible(false);
	fDialog.dispose();
    }



    /**
     * Speichert das aktuelle StateChart in eine Datei,
     * welche mittels eines FileDialog Abgefragt wird.
     *
     * @see            java.awt.FileDialog
     */
    
    void save()
    {
	fDialog.setMode(FileDialog.SAVE);
	fDialog.setTitle("Statechart speichern");
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gew�hlt
	    {
		try {
		    BufferedWriter  outFile = new BufferedWriter(new FileWriter(fDialog.getDirectory()+FileName));
		    
		}catch (IOException e)
		    {
			OkDialog("FEHLER","Das Speichern ist FEHLGESCHLAGEN !");
			// Alarm !
		    }
		
	    }
	
	fDialog.setVisible(false);
	fDialog.dispose();
    }


    
    public void paint(Graphics g)
    {
	g.setFont(new Font("Serif",Font.PLAIN,14));
	//	g.drawString("(P)rogramming (E)nviroment for (ST)atecharts",getInsets().left+30,
	//		     getInsets().top+50);
    }
    
    
    


    
    class GUIWindowListener
	extends WindowAdapter
    {
	public void windowClosing(WindowEvent e)
	{
	    e.getWindow().setVisible(false);    
	    // der Vorspann e.getWindow() muss hier nicht sein....
	    e.getWindow().dispose();            
	    //        -"-
	}
	
	public void windowClosed(WindowEvent e)
	{
	    System.exit(0);
	}
	
    }
    
}
