package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import gui.popdialoge.*;


import absyn.*;

/** <h1>Diese Klasse ist die Hauptklasse von PEST</h1>
 * und daher ist sie auch startbar (sprich "main" ist adequat 
 * vorhanden. Sie bildet das Hauptfenster von PEST und implementiert
 * das "GUIInterface". Die einzigen PUBLIC-Elemente sind gegenwärtig
 * die des Interface und die "main"-Methode.
 * Darum wird hier nichts beschrieben.<br><br>
 * <h2>Empfohlender Aufruf:</h2>
 * <ol>
 * <li> java pest
 * </ol>
 * <h2>Unsere Forderungen:</h2>
 * <ul>
 * <li> Alle sollt Ihr compilierbar sein
 * <li> Alle sollt Ihr funktionell sein
 * </ul>
 * <h2>Unsere Garantien:</h2>
 * <ul>
 * <li> Die PEST wird über Euch kommen.
 * <li> 
 * </ul>
 * <br>
 * <a href="mailto:swtech15@informatik.uni-kiel.de">SWTECH15 mailen...</a><br>
 * <br>
 * <br>
 * <DL COMPACT>
 * <DT><STRONG>STATUS: </STRONG><br>
 * Alle compilierbaren Pakete können aufgerufen werden.
 * Die Abhängigkeiten (gemäß unseren Vorgaben) sind implementiert.
 * Insgesamt sind alle Teilaufgaben (siehe "plan") erfüllt,
 * mit Ausnahme der Punkte unter ToDo.<br>
 * <DT><STRONG>To Do: </STRONG><br>
 * <ul>
 * <li>Optionendialoge sind zum Teil noch zu machen, sobald diese mit
 * den Gruppen geklärt sind.<br>
 * <li>Abhängigkeitstransparente Benutzerführung<br>
 * <li>Exportaufruf(e) <br>
 * <li>Das Wiederherstellen der Oberfläche ist noch nicht fehlerfrei.
 * </ul> 
 * <DT><STRONG>Temporäre Features: </STRONG><br>
 * Aufruf des PrettyPrinters<br>
 * </DL COMPACT>
 *

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
    ControlWindow controlWindow;
    GUIControlWindowML cwlis;
    GUIexitLis exlis;
    check.CheckConfig checkConfig;

    editor.Editor PEditor = null;
    Dimension EditorDim = null;
    Point EditorLoc  = null;

    simu.Simu simu = null;

    Statechart SyntaxBaum = null;
    String     SBDateiname = null;
    String     SBPfad = null;

    boolean CheckedSC = false;
    boolean ResultSC = false;
    boolean isDirty = false;
    boolean ctrlWin = true;

    boolean debugMode = true;
    boolean warningOpen = false;

    Color color[]; 

    int stateColorIndex = 0;
    int conColorIndex = 1;
    int transColorIndex = 2;

    int BgColorIndex = 10;
    int ActColorIndex = 0;
    int InactColorIndex = 9;

    int stmXSize = 640;
    int stmYSize = 480;
    boolean stmKoord = true;

    int layoutAlgorithm=0;

   public static void main(String[] args)
    {
	new pest();
    }
    
    public pest()
    {
	super("Und hier kommt die PEST");
	controlWindow = new ControlWindow(this);
	exlis = new GUIexitLis(this);
	setBackground(Color.lightGray);
	setSize(630,400); 
	setLocation(200,200);
	setFont(new Font("Serif",Font.PLAIN,18));
	theGUIMenu = new GUIMenu(this);
	setMenuBar(theGUIMenu);	
	GUIWindowListener lis = new GUIWindowListener();
	addWindowListener(lis);
	setLayout(new BorderLayout());

	color = new Color[11];
	color[0] = Color.red;
	color[1] = Color.blue;
	color[2] = Color.yellow;
	color[3] = Color.green;
	color[4] = Color.orange;
	color[5] = Color.pink;
	color[6] = Color.magenta;
	color[7] = Color.cyan;
	color[8] = Color.black;
	color[9] = Color.white;
	color[10] = Color.lightGray;
	
	cwlis = new GUIControlWindowML(this);
	controlWindow.addMouseListener(cwlis);
	add("Center",controlWindow);

	MsgWindow = new TextArea(4,80);
	MsgWindow.setFont(new Font("Serif",Font.PLAIN,14));
	MsgWindow.setEditable(false);
	add("South",MsgWindow);

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

	theConfig.Dateiname = SBDateiname;
	theConfig.Pfad = SBPfad;
        theConfig.CheckedSC = CheckedSC;
        theConfig.ResultSC = ResultSC;
        theConfig.isDirty = isDirty;
	theConfig.ctrlWin = ctrlWin;
	theConfig.debug = debugMode;


	theConfig.checkConfig = checkConfig;

	theConfig.stateColorIndex      = stateColorIndex;
	theConfig.transColorIndex      = transColorIndex;
	theConfig.conColorIndex        = conColorIndex;

	theConfig.BgColorIndex         = BgColorIndex;
	theConfig.ActColorIndex        = ActColorIndex;
	theConfig.InactColorIndex      = InactColorIndex; 

	theConfig.stmXSize = stmXSize;
	theConfig.stmYSize = stmYSize;
	theConfig.stmKoord = stmKoord;

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

	    stateColorIndex      = theConfig.stateColorIndex;
	    transColorIndex      = theConfig.transColorIndex;
	    conColorIndex        = theConfig.conColorIndex;

	    BgColorIndex         = theConfig.BgColorIndex;
	    ActColorIndex        = theConfig.ActColorIndex;
	    InactColorIndex      = theConfig.InactColorIndex;

	    stmXSize = theConfig.stmXSize;
	    stmYSize = theConfig.stmYSize;
	    stmKoord = theConfig.stmKoord;

	    SBDateiname = theConfig.Dateiname;
	    SBPfad = theConfig.Pfad;

	    debugMode = theConfig.debug;
	    theGUIMenu.cbItem2.setState(debugMode);

	    ctrlWin = theConfig.ctrlWin;
	    theGUIMenu.cbItem1.setState(ctrlWin);
	    if (ctrlWin)
		{
		    MsgWindow.setRows(4);
		    add("South",MsgWindow);
		    add("Center",controlWindow);		    
		}
	    else
		{
		    MsgWindow.setRows(50);
 		    remove(controlWindow);
		    add("Center",MsgWindow);
		}


	    if (theConfig.checkConfig == null)
		{
		    checkConfig = new check.CheckConfig();
		}
	    else
		{
		    checkConfig = theConfig.checkConfig;
		}



	    File file = new File(SBPfad,SBDateiname);
 	    if(file.exists())		
 		{
 		    load_named_sc(theConfig.Pfad,theConfig.Dateiname);

		    CheckedSC = theConfig.CheckedSC;
		    ResultSC = theConfig.ResultSC;
		    isDirty = theConfig.isDirty;
		    
		    controlWindow.highLight[4] = !CheckedSC;
		    controlWindow.highLight[6] = ResultSC;
		    controlWindow.highLight[7] = true; 
		    controlWindow.highLight[8] = ResultSC;
		    controlWindow.highLight[9] = true;
		    EditorLoc = theConfig.EditorLoc;
		    EditorDim = theConfig.EditorDim;
		    
		    if (theConfig.isEditor)
			{
			    startEditor();
			    PEditor.setLocation(EditorLoc.x,EditorLoc.y + PEditor.getInsets().top);
			    PEditor.setSize(EditorDim);
			    controlWindow.highLight[5] = false;
			    if (ResultSC)
				{
				    controlWindow.highLight[10] = true;
				}
			}
 		}	    
	    controlWindow.repaint();
		    

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
		startEditorSized(100,100,500,400);
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
		PEditor = new editor.Editor(SyntaxBaum,SBPfad + SBDateiname,x,y,b,h,this);

		if(EditorDim != null)
		    {
			PEditor.setSize(EditorDim);
			PEditor.setLocation(EditorLoc);
		    }

 		PEditor.addWindowListener(exlis);							  
// 		PEditor.w2.addActionListener(exlis);
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
		check.ModelCheck SCchecker;

		if (PEditor == null)
		    {
			SCchecker = new check.ModelCheck(this);
		    }
		else
		    {
			SCchecker = new check.ModelCheck(this,PEditor);
		    }
		ResultSC = SCchecker.checkModel(SyntaxBaum);
		CheckedSC = true;
		theGUIMenu.updateMenu();
	    }
	else
	    {
		userMessage("GUI   : SyntaxBaum nicht verändert -> Benutze altes Ergebnis");
	    }

	if (!ResultSC)
	    {
		userMessage("GUI   : SyntaxCheck fehlgeschlagen");
		controlWindow.highLight[4] = false;
		controlWindow.repaint();
	    }
	else
	    {
		userMessage("GUI   : SyntaxCheck erfolgreich :-)");
		controlWindow.highLight[4] = false;
		controlWindow.highLight[8] = true;
		controlWindow.highLight[6] = true;
		if (PEditor != null)
		    {
			controlWindow.highLight[10] = true;
		    }
		controlWindow.repaint();
	    }

	userMessage("GUI   : SyntaxCheck ende");
	return ResultSC;
    }


    void setStatechart(Statechart sc,String pfad,String datei)
    {
	SyntaxBaum = sc;
	SBDateiname = datei;
	SBPfad = pfad;
	CheckedSC = false;
	ResultSC  = false;
	controlWindow.highLight[4] = true;
	if (PEditor == null)
	    {
		controlWindow.highLight[5] = true;
	    }
	else
	    {
		exlis.windowClosing(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
		startEditor();
	    }
		
	controlWindow.highLight[6] = false;
	controlWindow.highLight[7] = true;
	controlWindow.highLight[8] = false;
	controlWindow.highLight[9] = true;
	controlWindow.highLight[10] = false;
	controlWindow.repaint();
	userMessage("GUI   : Neues Statechart erzeugt");
	theGUIMenu.updateMenu();	
    }


    public void StateChartHasChanged()
    {
	isDirty = true;
	if (CheckedSC)
	    {
		CheckedSC = false;
		ResultSC = false;
		controlWindow.highLight[4] = true;
		controlWindow.highLight[6] = false;
		controlWindow.highLight[8] = false;
		controlWindow.highLight[10] = false;
		controlWindow.repaint();
	    }
    }


    public void editorClosing()
    {
	exlis.windowClosing(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
    }

	

    boolean isSaved()
    {
	boolean resp;
	if(isDirty)
	    {
		resp = false;
		if (!warningOpen)
		    {
			warningOpen = true;  
			if (YesNoDialog("ACHTUNG","Das aktuelle Statechart wurde noch nicht gespeichert ! Trotzdem fortfahren ?") == 1)
			    {
				resp = true;
			    }
			warningOpen = false;
		    }
		else 
		    {
			resp = false;
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
	return new OKDialog(par,getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public int YesNoDialog(Frame par, String Titel, String Msg){
	return new YesNoDialog(par,getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public int YesNoCancelDialog(Frame par, String Titel, String Msg){
	return new YesNoCancelDialog(par,getGraphics().getFontMetrics(),Titel,Msg).getAnswer();
    }

    public String EingabeDialog(Frame par, String Titel, String Msg, String Defaulttext){
        return new EingabeDialog(par,getGraphics().getFontMetrics(),Titel,Msg,Defaulttext).getEingabe();
    }

    public boolean isDebug()
    {
	return debugMode;
    }

    public Color getStatecolor()
    {
	return color[stateColorIndex];
    }

    public Color getTransitioncolor()
    {
	return color[transColorIndex];
    }

    public Color getConnectorcolor()
    {
	return color[conColorIndex];
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
		    SBPfad = fDialog.getDirectory();
		    SBDateiname = FileName;
		    exlis.windowClosing(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
		    startEditor();
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
		load_named_sc(fDialog.getDirectory(),FileName);		
	    }
	
	fDialog.setVisible(false);
	fDialog.dispose();
    }

    void load_named_sc(String pfad,String datei)
    {
	try {
	    FileInputStream fis = new FileInputStream(pfad + datei);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    absyn.Statechart Synb = (absyn.Statechart) ois.readObject();
	    ois.close();
	    setStatechart(Synb,pfad,datei);
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
