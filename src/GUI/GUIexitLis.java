package gui;
import java.awt.event.*;

class GUIexitLis
extends WindowAdapter
{
    pest myWindow;
    ControlWindow ctrlWin;

    public GUIexitLis(pest myWindow)
    {
	this.myWindow = myWindow;
	ctrlWin = myWindow.controlWindow; 
    }

    public void windowClosing(WindowEvent e)
    {
	//	e.getWindow().dispose();            
	//e.getWindow().setVisible(false)

	myWindow.isDirty();
	myWindow.EditorDim = myWindow.PEditor.getSize();
	myWindow.EditorLoc = myWindow.PEditor.getLocation();
	myWindow.PEditor.Dispose();
	// hiernach sollte der Editor beendet sein !
	myWindow.PEditor = null;

	ctrlWin.highLight[10] = false;
	ctrlWin.highLight[5] = true;
	ctrlWin.repaint();
    }

    public void windowActivated(WindowEvent e)
    {
	ctrlWin.highLight[5] = false;
	if (myWindow.ResultSC)
	    {
		ctrlWin.highLight[10] = true;
	    }
	ctrlWin.repaint();
    }

}
