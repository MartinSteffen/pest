package gui;

import java.awt.*;
import java.awt.event.*;

class GUIControlWindowML
extends MouseAdapter
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

		    }	    
	    }




	for (int i = 4 ; i < 7 ; ++i)
	    {
		if ((source.highLight[i]) && (source.rect[i].contains(pos)))
		    {
			menu.mlw.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[i]));
		    }	    
	    }



	if ((source.highLight[8]) && (source.rect[8].contains(pos)))
	    {
		menu.mlex.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[8]));
	    }	    


	

	if ((source.highLight[7]) && (source.rect[7].contains(pos)))
	    {
		menu.ml.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[7]));
	    }



	if ((source.highLight[9]) && (source.rect[9].contains(pos)))
	    {
		menu.mlw.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[9]));
	    }


	    
	if ((source.highLight[10]) && (source.rect[10].contains(pos)))
	    {
		menu.mlw.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[10]));
	    }

	if ((source.highLight[11]) && (source.rect[14].contains(pos)))
	    {
		menu.mlw.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,source.label[14]));
	    }

    }
 
    
}
