package gui;

import java.awt.*;
import java.awt.event.*;

class GUIControlWindowML
extends MouseAdapter
implements ActionListener
{

    pest parent;
    ControlWindow source;
    GUIMenu menu;

    GUIControlWindowML(pest parent)
    {
	source = parent.controlWindow;
	menu = parent.theGUIMenu;
	this.parent = parent;
    }
    

    public void mouseClicked(MouseEvent e)
    {
	Point pos = e.getPoint();
	for (int i = 0; i < 2; ++i)
	    {
		if (source.rect[i].contains(pos))
		    {
			if (parent.SyntaxBaum == null)
			    {
				source.highLight[5] = true;
			    }
			menu.ml.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[i]));

			source.repaint();
		    }
	    }

	for (int i = 2 ; i < 4 ; ++i)
	    {
		if (source.rect[i].contains(pos))
		    {
			if (parent.SyntaxBaum == null)
			    {
				source.highLight[5] = true;
			    }
			menu.mli.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[i]));

			source.repaint();
		    }	    
	    }


	for (int i = 4 ; i < 7 ; ++i)
	    {
		if ((source.highLight[i]) && (source.rect[i].contains(pos)))
		    {
			menu.mlw.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[i]));
			source.repaint();
		    }	    
	    }


	for (int i = 8 ; i < 10 ; ++i)
	    {
		if ((source.highLight[i]) && (source.rect[i].contains(pos)))
		    {
			menu.mlex.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[i]));
			source.repaint();
		    }	    
	    }

	

	if ((source.highLight[7]) && (source.rect[7].contains(pos)))
	    {
		menu.ml.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[7]));
		source.repaint();
	    }

	    
	if ((source.highLight[10]) && (source.rect[10].contains(pos)))
	    {
		menu.mlw.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[10]));
		source.repaint();
	    }


	//	if ((parent.SyntaxBaum != null) && (!parent.CheckedSC))
	if  ((parent.PEditor != null) ||((parent.SyntaxBaum != null) && (!parent.CheckedSC)))
	    {
		source.highLight[4] = true;
	    }
	else 
	    {
		source.highLight[4] = false;
	    }

	
	if ((source.highLight[5]) && (parent.SyntaxBaum != null))
	    {
		source.highLight[5] = true;
	    }
	else
	    {
		source.highLight[5] = false;
	    }


	if (parent.ResultSC)
	    {
		source.highLight[6] = true;
	    }
	else 
	    {
		source.highLight[6] = false;
	    }


	if (parent.SyntaxBaum != null)
	    {
		source.highLight[7] = true;
	    }
	else 
	    {
		source.highLight[7] = false;
	    }


	if (parent.ResultSC)
	    {
		source.highLight[8] = true;
	    }
	else 
	    {
		source.highLight[8] = false;
	    }


	if (parent.ResultSC)
	    {
		source.highLight[9] = true;
	    }
	else 
	    {
		source.highLight[9] = false;
	    }



	if ((parent.ResultSC) && (parent.PEditor != null))
	    {
		source.highLight[10] = true;
	    }
	else 
	    {
		source.highLight[10] = false;
	    }


    }


    public void actionPerformed(ActionEvent e)
    {
	if (parent.CheckedSC)
	    {
		parent.ResultSC = false;
		parent.CheckedSC = false;
		source.highLight[4] = true;
		source.highLight[6] = false;
		source.highLight[8] = false;
		source.highLight[9] = false;
		source.highLight[10] = false;
		source.repaint();
	    }
    }
    
}
