package gui;

import java.awt.*;
import java.awt.event.*;


class EditorOption
extends Dialog
implements ActionListener
{
   pest parent;
   GUIoptionML user; 
   CheckboxGroup zustand; 
   CheckboxGroup transition; 
   CheckboxGroup connector;
   Button button1;
   Button button2;
   static Color color[];
   String farbe[];
   Checkbox cbState[];
   Checkbox cbTrans[];
   Checkbox cbCon[]; 

	
   EditorOption(pest parent,GUIoptionML user)
   {
	super(parent,"Farbpalette");   
	this.parent = parent;
	this.user = user;
	user.isEdOpOpen = true;
	Point p = parent.getLocation();
	setLocation(p.x + 30 , p.y + 30);
 	setLayout(new GridLayout(11,1));

	color = new Color[9];
	color[0] = Color.red;
	color[1] = Color.blue;
	color[2] = Color.yellow;
	color[3] = Color.green;
	color[4] = Color.orange;
	color[5] = Color.pink;
	color[6] = Color.magenta;
	color[7] = Color.cyan;
	color[8] = Color.black;

	farbe = new String[9];
	farbe[0] = "Rot";
	farbe[1] = "Blau";
	farbe[2] = "Gelb";
	farbe[3] = "Gr�n";
	farbe[4] = "Orange";
	farbe[5] = "Pink";
	farbe[6] = "Magenta";
	farbe[7] = "Cyan";
	farbe[8] = "Schwarz";

	zustand = new CheckboxGroup();
	transition = new CheckboxGroup();
	connector = new CheckboxGroup();

	Panel panel = new Panel(new GridLayout(1,3));
	panel.setBackground(Color.gray);
	panel.setForeground(Color.white);
	panel.add(new Label("State"));
	panel.add(new Label("Transition"));
	panel.add(new Label("Connector"));
	add(panel);

	setBackground(Color.lightGray);
	cbState = new Checkbox[9];
	cbTrans = new Checkbox[9];
	cbCon = new Checkbox[9];
	for (int i = 0;i < 9;++i)
	    {
		panel = new Panel(new GridLayout(1,3));
		panel.setForeground(color[i]);
		cbState[i] = new Checkbox(farbe[i],zustand,false);
		cbTrans[i] = new Checkbox(farbe[i],transition,false);
		cbCon[i] = new Checkbox(farbe[i],connector,false);
		panel.add(cbState[i]);
		panel.add(cbTrans[i]);
		panel.add(cbCon[i]);
		add(panel);
	    }
	cbState[parent.stateColorIndex].setState(true);
	cbTrans[parent.transColorIndex].setState(true);
	cbCon[parent.conColorIndex].setState(true);

	panel = new Panel(new FlowLayout());
	panel.setBackground(Color.gray);
	panel.setForeground(Color.white);
	button1 = new Button("OK");
	button1.setActionCommand("Juppie,es gibt neue Farben!!");
	button1.addActionListener(this);
	panel.add(button1);
	button2 = new Button("Abbrechen");
	button2.setActionCommand("Schade..");
	button2.addActionListener(this);
	panel.add(button2);
	add(panel);			

	pack();
	setResizable(true);
	setVisible(true);

   }

    
    public void actionPerformed(ActionEvent e)
    {
	String cmd = e.getActionCommand();
	if (cmd.equals(button1.getActionCommand()))
	    {
		parent.stateColorIndex = getStateColorIndex();
		parent.transColorIndex = getTransColorIndex();
		parent.conColorIndex = getConColorIndex();
		user.isEdOpOpen = false;
		setVisible(false);
		dispose();
	    }
	else if (cmd.equals(button2.getActionCommand()))
	    {
		setVisible(false);
		dispose();
	    }
		
    }
            
 
     int getStateColorIndex()
    {
	int index = 0;
	String label = zustand.getSelectedCheckbox().getLabel();
	for (int i = 0;i < 9;++i)
	    {
		if (farbe[i].equals(label))
		    {
			index = i;
		    }
	    }
	return index;
	}   

    int getTransColorIndex()
	{
	int index = 0;
	String label = transition.getSelectedCheckbox().getLabel();
	for (int i = 0;i < 9;++i)
	    {
		if (farbe[i].equals(label))
		    {
			index = i;
		    }
	    }
	return index;
	}   
	
		   	
    int getConColorIndex()
	{
	int index = 0;
	String label = connector.getSelectedCheckbox().getLabel();
	for (int i = 0;i < 9;++i)
	    {
		if (farbe[i].equals(label))
		    {
			index = i;
		    }
	    }
	return index;
	}   
	  
 } 
