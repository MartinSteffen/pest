package gui;
import java.awt.event.*;

class GUIexitLis
    extends WindowAdapter
{
    pest myWindow;

    public GUIexitLis(pest myWindow)
    {
	this.myWindow = myWindow;
    }

    public void windowClosing(WindowEvent e)
    {
	//	e.getWindow().dispose();            
	//e.getWindow().setVisible(false);
	myWindow.isDirty();
	myWindow.EditorDim = myWindow.PEditor.getSize();
	myWindow.EditorLoc = myWindow.PEditor.getLocation();
	myWindow.PEditor.Dispose();
	// hiernach sollte der Editor beendet sein !
	myWindow.PEditor = null;
    }
    
}
