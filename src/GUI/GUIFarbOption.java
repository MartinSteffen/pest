package gui;

import java.awt.*;
import java.awt.event.*;

class GUIFarbOption
extends Dialog
implements ActionListener
{
   pest parent;
   GUIoptionML user; 
   CheckboxGroup background; 
   CheckboxGroup activ; 
   CheckboxGroup inactiv;
   Button button1;
   Button button2;
   String farbe[];
   Checkbox cbBackground[];
   Checkbox cbActiv[];
   Checkbox cbInactiv[]; 

	
   GUIFarbOption(pest parent,GUIoptionML user)
   {
	super(parent,"Farbpalette",true);   
	this.parent = parent;
	this.user = user;
	Point p = parent.getLocation();
	setLocation(p.x + 30 , p.y + 30);
 	setLayout(new BorderLayout());

	farbe = new String[11];
	farbe[0] = "Rot";
	farbe[1] = "Blau";
	farbe[2] = "Gelb";
	farbe[3] = "Grün";
	farbe[4] = "Orange";
	farbe[5] = "Pink";
	farbe[6] = "Magenta";
	farbe[7] = "Cyan";
	farbe[8] = "Schwarz";
	farbe[9] = "Weiß";
	farbe[10] = "Grau";

	background = new CheckboxGroup();
	activ = new CheckboxGroup();
	inactiv = new CheckboxGroup();

	Panel panel = new Panel(new GridLayout(2,3));
	panel.setBackground(Color.gray);
	panel.setForeground(Color.white);
	panel.add(new Label("Hintergrund"));
	panel.add(new Label("Aktive"));
	panel.add(new Label("Inaktive"));
	panel.add(new Label(""));
	panel.add(new Label("Menüpunkte"));
	panel.add(new Label("Menüpunkte"));
	add("North",panel);

	panel = new Panel(new GridLayout(11,1));
	setBackground(Color.lightGray);
	cbBackground = new Checkbox[11];
	cbActiv = new Checkbox[11];
	cbInactiv = new Checkbox[11];
	for (int i = 0;i < 10;++i)
	    {
		Panel panel1 = new Panel(new GridLayout(1,3));
		panel1.setForeground(parent.color[i]);
		cbBackground[i] = new Checkbox(farbe[i],background,false);
		cbActiv[i] = new Checkbox(farbe[i],activ,false);
		cbInactiv[i] = new Checkbox(farbe[i],inactiv,false);
		panel1.add(cbBackground[i]);
		panel1.add(cbActiv[i]);
		panel1.add(cbInactiv[i]);
		panel.add(panel1);
	    }
	Panel panel1 = new Panel(new GridLayout(1,3));
	panel1.setForeground(Color.gray);
	cbBackground[10] = new Checkbox(farbe[10],background,false);
	cbActiv[10] = new Checkbox(farbe[10],activ,false);
	cbInactiv[10] = new Checkbox(farbe[10],inactiv,false);
	panel1.add(cbBackground[10]);
	panel1.add(cbActiv[10]);
	panel1.add(cbInactiv[10]);
	panel.add(panel1);	
	add("Center",panel);
	cbBackground[parent.BgColorIndex].setState(true);
	cbActiv[parent.ActColorIndex].setState(true);
	cbInactiv[parent.InactColorIndex].setState(true);

	panel = new Panel(new FlowLayout());
	panel.setBackground(Color.gray);
	panel.setForeground(Color.white);
	button1 = new Button("OK");
	button1.setActionCommand("Juppie,es gibt neue GUIFarben!!");
	button1.addActionListener(this);
	panel.add(button1);
	button2 = new Button("Abbrechen");
	button2.setActionCommand("Pech gehabt,GUI..");
	button2.addActionListener(this);
	panel.add(button2);
	add("South",panel);			

	pack();
	setResizable(true);
	setVisible(true);

   }

    
    public void actionPerformed(ActionEvent e)
    {
	String cmd = e.getActionCommand();
	if (cmd.equals(button1.getActionCommand()))
	    {
		parent.BgColorIndex = getBgColorIndex();
		parent.ActColorIndex = getActColorIndex();
		parent.InactColorIndex = getInactColorIndex();
		parent.controlWindow.repaint();
		setVisible(false);
		dispose();
	    }
	else if (cmd.equals(button2.getActionCommand()))
	    {
		setVisible(false);
		dispose();
	    }
		
    }
            
 
     int getBgColorIndex()
    {
	int index = 0;
	String label = background.getSelectedCheckbox().getLabel();
	for (int i = 0;i < 11;++i)
	    {
		if (farbe[i].equals(label))
		    {
			index = i;
		    }
	    }
	return index;
	}   

    int getActColorIndex()
	{
	int index = 0;
	String label = activ.getSelectedCheckbox().getLabel();
	for (int i = 0;i < 11;++i)
	    {
		if (farbe[i].equals(label))
		    {
			index = i;
		    }
	    }
	return index;
	}   
	
		   	
    int getInactColorIndex()
	{
	int index = 0;
	String label = inactiv.getSelectedCheckbox().getLabel();
	for (int i = 0;i < 11;++i)
	    {
		if (farbe[i].equals(label))
		    {
			index = i;
		    }
	    }
	return index;
	} 
	  
 } 
