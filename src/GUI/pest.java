package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import gui.popdialoge.*;

import absyn.*;

/** Diese Klasse ist die Hauptklasse von PEST
 * und daher ist sie auch startbar (sprich "main" ist adequat 
 * vorhanden. Sie bildet das Hauptfenster von PEST und implementiert
 * das "GUIInterface". Die einzigen PUBLIC-Elemente sind gegenwärtig
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

    editor.Editor PEditor = null;
    Dimension EditorDim = null;
    Point EditorLoc  = null;

    Statechart SyntaxBaum = null;
    String     SBDateiname = null;

    boolean CheckedSC = false;
    boolean ResultSC = false;
    boolean isDirty = false;

    Color stateColor = Color.red;
    Color connectorColor = Color.blue;
    Color transitionColor = Color.green;

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
	restoreConfig();
	fDialog = new FileDialog(this);

	theGUIMenu.updateMenu();
	userMessage("GUI   : PEST initialisiert");
	repaint();       
   
    }

    boolean isDirty()
    {
	if (PEditor != null)
	    {
		if(PEditor.listenEditor() == true)
		    {
			CheckedSC = false; 
			//da "listenEditor" immer nur angibt,
			//ob Änderungen seit dem letzen Aufruf
			//passiert sind, muss hier der Status 
			// für den Syntaxcheck gemerkt werden.
			isDirty = true;
		    }
	    }

	return isDirty;
    }

    void rememberConfig()
    {
	pestConfig theConfig = new pestConfig();
	theConfig.GUIDim = getSize();
	theConfig.GUILoc = getLocation();
	theConfig.Dateiname = SBDateiname;

	theConfig.stateColor      = stateColor;
	theConfig.transitionColor = transitionColor;
	theConfig.connectorColor  = connectorColor;

	if (PEditor == null)
	    {
		theConfig.isEditor = false;
		theConfig.EditorDim = EditorDim;
		theConfig.EditorLoc = EditorLoc;
	    }
	else
	    {
		theConfig.isEditor = true;
		theConfig.EditorDim = PEditor.getSize();
		theConfig.EditorLoc = PEditor.getLocation();
	    }

	try {
	    FileOutputStream outf = new FileOutputStream("pest.cfg");
	    ObjectOutputStream oos = new ObjectOutputStream(outf);
	    oos.writeObject(theConfig);
	    oos.flush();
	    oos.close();
	} catch(IOException e){
	    OkDialog("FEHLER","Die PEST-Parameter konnten nicht gespeichert werden.");
	}
    }


    void restoreConfig()
    {
	try {
	    FileInputStream inf = new FileInputStream("pest.cfg");
	    ObjectInputStream ois = new ObjectInputStream(inf);
	    pestConfig theConfig = (pestConfig)ois.readObject();
	    ois.close();

	    stateColor      = theConfig.stateColor;
	    transitionColor = theConfig.transitionColor;
	    connectorColor  = theConfig.connectorColor;
	    
	    setSize(theConfig.GUIDim);
	    setLocation(theConfig.GUILoc);
 	    if(theConfig.Dateiname !=null)
 		{
 		    load_named_sc(theConfig.Dateiname);
 		}

	    EditorLoc = theConfig.EditorLoc;
	    EditorDim = theConfig.EditorDim;

 	    if (theConfig.isEditor)
 		{
		    startEditor();
		    //		    PEditor.setLocation(EditorLoc);
		    //		    PEditor.setSize(EditorDim);
		}

	} catch(Exception e){
	    userMessage("GUI   : Die PEST-Parameter konnten nicht geladen werden.");
	}

    }

	
    void setDirty(boolean d)
    {
	isDirty = d;
    }

    void startEditor()
    {
	if (EditorLoc == null)
	    {
		startEditorSized(100,100,200,200);
	    }
	else
	    {
		startEditorSized(EditorLoc.x,EditorLoc.y,EditorDim.width,EditorDim.height);
	    }
    }

    void startEditorSized(int x, int y, int b ,int h )
    {
	if (PEditor == null)
	    {
		PEditor = new editor.Editor(SyntaxBaum,SBDateiname,x,y,b,h,this);
		if(EditorDim != null)
		    {
			PEditor.setSize(EditorDim);
			PEditor.setLocation(EditorLoc);
		    }
		PEditor.addWindowListener(new GUIexitLis(this));
	    }
	else
	    {
		OkDialog("Fehler","Es kann nur ein Editor gestartet werden !");
	    }
    }

    boolean checkSB(boolean forced)
    {
	userMessage("GUI   : SyntaxCheck start");
	if (forced)
	    {
		userMessage("GUI   : neuer Test wird gefordert.");
	    }
	else
	    {
		if (PEditor != null)
		    {
			if(PEditor.listenEditor() == true)
			    {
				CheckedSC = false;
				userMessage("GUI   : Statechart wurde verändert");
			    }
		    }
	    }

	if( (!CheckedSC) || (forced) )
	    {
		check.ModelCheck SCchecker = new check.ModelCheck(this);
		ResultSC = SCchecker.checkModel(SyntaxBaum);
		CheckedSC = true;
	    }
	else
	    {
		userMessage("GUI   : SyntaxBaum nicht verändert -> Benutze altes Ergebnis");
	    }

	if (!ResultSC)
	    {
		userMessage("GUI   : SyntaxCheck fehlgeschlagen");
	    }
	else
	    {
		userMessage("GUI   : SyntaxCheck erfolgreich :-)");
	    }

	userMessage("GUI   : SyntaxCheck ende");
	return ResultSC;
    }


    void setStatechart(Statechart sc,String name)
    {
	SyntaxBaum = sc;
	SBDateiname = name;
	CheckedSC = false;
	ResultSC  = false;
	userMessage("GUI   : Neues Statechart erzeugt");
	theGUIMenu.updateMenu();	
    }


    boolean isSaved()
    {
	boolean resp;

	if(isDirty())
	    {
		resp = false;
		if (YesNoDialog("ACHTUNG","Das aktuelle Statechart wurde noch nicht gespeichert ! Trotzdem fortfahren ?") == 1)
		    {
			resp = true;
		    }
	    }
	else
	    {
		resp = true;
	    }

	return resp;
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

    public String EingabeDialog(String Titel, String Msg, String Defaulttext){
        return new EingabeDialog(this, getGraphics().getFontMetrics(),Titel,Msg,Defaulttext).getEingabe();
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

    public String EingabeDialog(Frame par, String Titel, String Msg, String Defaulttext){
        return new EingabeDialog(par,par.getGraphics().getFontMetrics(),Titel,Msg,Defaulttext).getEingabe();
    }


    
    
    BufferedReader load(String titel)
    {
	BufferedReader resp = null;

	fDialog.setMode(FileDialog.LOAD);
	fDialog.setTitle(titel);
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gewählt
	    {
		try {
		    resp = new BufferedReader(new FileReader(fDialog.getDirectory()+FileName));
		    
		}catch (IOException e)
		    {
			resp = null;
			OkDialog("Fehler","Auf die Datei kann nicht zugegriffen werden");
			// Alarm !
		    }
	    }
	else
	    {
		resp = null;
	    }
	
	fDialog.setVisible(false);
	fDialog.dispose();

    return resp;
    }



    /**
     * Speichert das aktuelle StateChart in eine Datei,
     * welche mittels eines FileDialog Abgefragt wird.
     *
     * @see            java.awt.FileDialog
     */
    
    BufferedWriter save(String titel)
    {
	BufferedWriter resp = null;

	fDialog.setMode(FileDialog.SAVE);
	fDialog.setTitle(titel);
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gewählt
	    {
		try {
		    resp = new BufferedWriter(new FileWriter(fDialog.getDirectory()+FileName));
		    
		}catch (IOException e)
		    {
			resp = null;
			OkDialog("FEHLER","Das Speichern ist FEHLGESCHLAGEN !");
			// Alarm !
		    }
		
	    }
	
	fDialog.setVisible(false);
	fDialog.dispose();
	return resp;
    }


    void save_sc()
    {
	fDialog.setMode(FileDialog.SAVE);
	fDialog.setTitle("Statechart speichern");
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gewählt
	    {
		try {

		    FileOutputStream outf = new FileOutputStream(fDialog.getDirectory()+FileName);
                    ObjectOutputStream oos = new ObjectOutputStream(outf);
		    oos.writeObject(SyntaxBaum);
		    oos.flush();
		    oos.close();
      		    setDirty(false);
		    SBDateiname = fDialog.getDirectory()+FileName;
		}catch (Exception e)
		    {
			OkDialog("Fehler","Die Datei kann nicht gespeichert werden");
			// Alarm !
		    }
	    }

	fDialog.setVisible(false);
	fDialog.dispose();
    }


    
    void load_sc()
    {
	fDialog.setMode(FileDialog.LOAD);
	fDialog.setTitle("Statechart laden");
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gewählt
	    {
		load_named_sc(fDialog.getDirectory()+FileName);		
	    }
	
	fDialog.setVisible(false);
	fDialog.dispose();
    }

    void load_named_sc(String name)
    {
	try {
	    FileInputStream fis = new FileInputStream(name);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    absyn.Statechart Synb = (absyn.Statechart) ois.readObject();
	    ois.close();
	    setStatechart(Synb,name);
	    setDirty(false);
	}catch (Exception e)
	    {
		OkDialog("Fehler","Das Laden ist fehlgeschlagen !");
		// Alarm !
	    }
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
	    if(isSaved())
		{
		    rememberConfig();
		    e.getWindow().setVisible(false);    
		    // der Vorspann e.getWindow() muss hier nicht sein....
		    e.getWindow().dispose();            
		    //        -"-
		}
	}
	
	public void windowClosed(WindowEvent e)
	{
	    System.exit(0);
	}
	
    }
    
}
