
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import popdialoge.*;


public class pest
extends Frame
implements GUIInterface
{
    FileDialog fDialog;
    GUIMenu theGUIMenu;
    TextArea MsgWindow;

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

	Initialize();

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
	return new OKDialog(this,Titel,Msg).getAnswer();
    }

    public int YesNoDialog(String Titel, String Msg){
	return new YesNoDialog(this,Titel,Msg).getAnswer();
    }

    public int YesNoCancelDialog(String Titel, String Msg){
	return new YesNoCancelDialog(this,Titel,Msg).getAnswer();
    }

    public int OkDialog(Frame par, String Titel, String Msg){
	return new OKDialog(par,Titel,Msg).getAnswer();
    }

    public int YesNoDialog(Frame par, String Titel, String Msg){
	return new YesNoDialog(par,Titel,Msg).getAnswer();
    }

    public int YesNoCancelDialog(Frame par, String Titel, String Msg){
	return new YesNoCancelDialog(par,Titel,Msg).getAnswer();
    }



    
    public void Initialize()
    {
	userMessage("GUI:\tPEST initialisiert");
	repaint();
    }
    
    
    /**
     * Verwirft das aktuelle StateChart und laedt ein neues aus einer Datei,
     * welche mittels eines FileDialog Abgefragt wird.
     *
     * @see            java.awt.FileDialog
     */
    
    public void load()
    {
	fDialog.setMode(FileDialog.LOAD);
	fDialog.setTitle("StateChart laden");
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gewählt
	    {
		try {
		    BufferedReader  inFile = new BufferedReader(new FileReader(fDialog.getDirectory()+FileName));
		    if(inFile.readLine().equals("zustand"))
			{
			    Initialize();
			}
		    else
			{
			    System.out.println("Is keine PEST-Datei");
			}
		    inFile.close();
		    
		}catch (IOException e)
		    {
			System.out.println("Schade...");
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
    
    public void save()
    {
	fDialog.setMode(FileDialog.SAVE);
	fDialog.setTitle("Statechart speichern");
	fDialog.setVisible(true);
	String FileName = fDialog.getFile();
	if (FileName != null)//Ok gewählt
	    {
		try {
		    BufferedWriter  outFile = new BufferedWriter(new FileWriter(fDialog.getDirectory()+FileName));
		    
		}catch (IOException e)
		    {
			System.out.println("Schade...");
			// Alarm !
		    }
		
	    }
	
	fDialog.setVisible(false);
	fDialog.dispose();
    }


    
    public void paint(Graphics g)
    {
	g.setFont(new Font("Serif",Font.PLAIN,14));
	g.drawString("(P)rogramming (E)nviroment for (ST)atecharts",getInsets().left+30,
		     getInsets().top+50);
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
