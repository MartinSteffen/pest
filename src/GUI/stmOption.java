package gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;

class stmOption
extends Dialog
implements ActionListener
{
    pest parent;
    Button okButton,abButton;
    TextField xText,yText;
    Checkbox cbox;

    stmOption(pest parent)
    {
	super(parent,"StatemateOption",true);
	this.parent = parent;
	Point p = parent.getLocation();
	setLocation(p.x + 30, p.y + 30);
	setLayout(new BorderLayout());
	setBackground(Color.lightGray);
	Panel panel = new Panel(new GridLayout(2,1));
	panel.add(new Label("Geben Sie die gewünschte"));
	panel.add(new Label("Auflösung ein !"));
	add("North",panel);
	panel = new Panel(new BorderLayout());
	Panel panel1 = new Panel(new GridLayout(2,2));
	xText = new TextField("640",5);
	yText = new TextField("480",5);
	panel1.add(new Label("x-Auflösung:"));
	panel1.add(xText);
	panel1.add(new Label("y-Auflösung:"));
	panel1.add(yText);
	panel.add("Center",panel1);
	cbox = new Checkbox("Koorditaten nicht übernehmen",true);
	panel.add("South",cbox);
	add("Center",panel);
	panel = new Panel(new FlowLayout());
	okButton = new Button("OK");
	okButton.addActionListener(this);
	okButton.setActionCommand("Ja,ich will diese Einstellung vornehmen!");
	abButton = new Button("Abbrechen");
	abButton.setActionCommand("Nö,doch lieber nicht");
	abButton.addActionListener(this);
	panel.add(okButton);
	panel.add(abButton);
	add("South",panel);
	pack();
	setVisible(true);
    }


    public void actionPerformed(ActionEvent e)
    {
	String cmd = e.getActionCommand();
	if (cmd.equals(okButton.getActionCommand()))
	    {
		System.out.println(xText.getText());
		try
		    {
			parent.stmXSize = Integer.parseInt(xText.getText());
			parent.stmYSize = Integer.parseInt(yText.getText());
			parent.stmKoord = cbox.getState();
		    }catch (NumberFormatException ex){}
		setVisible(false);
		dispose();
	    }
	if (cmd.equals(abButton.getActionCommand()))
	    {
		setVisible(false);
		dispose();
	    }
    }

}
	

    
