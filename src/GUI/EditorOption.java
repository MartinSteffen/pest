package gui;

import java.awt.*;
import java.awt.event.*;


public class EditorOption
extends Dialog
{
   pest parent;
   CheckboxGroup zustand; 
   CheckboxGroup transition; 
   CheckboxGroup connector;
   Button button1;
   Button button2; 
	
   public EditorOption(pest parent,GUIoptionML lis)
   {
	super(parent,"Farbpalette");   
	this.parent = parent;
	Point p = parent.getLocation();
	setLocation(p.x + 30 , p.y + 30);
; 	setLayout(new GridLayout(11,1));

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
	panel = new Panel(new GridLayout(1,3));
	panel.setForeground(Color.red);	
	if (parent.stateColor == Color.red){
	    panel.add(new Checkbox("Rot",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Rot",zustand,false));
	    }	
	if (parent.transitionColor == Color.red){
	    panel.add(new Checkbox("Rot",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Rot",transition,false));
	    }
	if (parent.connectorColor == Color.red){
	    panel.add(new Checkbox("Rot",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Rot",connector,false));
	    }
	add(panel);

	panel = new Panel(new GridLayout(1,3));			
	panel.setForeground(Color.blue);
	if (parent.stateColor == Color.blue){
	    panel.add(new Checkbox("Blau",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Blau",zustand,false));
	    }	
	if (parent.transitionColor == Color.blue){
	    panel.add(new Checkbox("Blau",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Blau",transition,false));
	    }
	if (parent.connectorColor == Color.blue){
	    panel.add(new Checkbox("Blau",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Blau",connector,false));
	    }
	add(panel);
			
	panel = new Panel(new GridLayout(1,3));	
	panel.setForeground(Color.yellow);
	if (parent.stateColor == Color.yellow){
	    panel.add(new Checkbox("Gelb",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Gelb",zustand,false));
	    }	
	if (parent.transitionColor == Color.yellow){
	    panel.add(new Checkbox("Gelb",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Gelb",transition,false));
	    }
	if (parent.connectorColor == Color.yellow){
	    panel.add(new Checkbox("Gelb",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Gelb",connector,false));
	    }
	add(panel);			

	panel = new Panel(new GridLayout(1,3));
	panel.setForeground(Color.green);
	if (parent.stateColor == Color.green){
	    panel.add(new Checkbox("Grün",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Grün",zustand,false));
	    }	
	if (parent.transitionColor == Color.green){
	    panel.add(new Checkbox("Grün",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Grün",transition,false));
	    }
	if (parent.connectorColor == Color.green){
	    panel.add(new Checkbox("Grün",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Grün",connector,false));
	    }
	add(panel);			
	
	panel = new Panel(new GridLayout(1,3));
	panel.setForeground(Color.orange);
	if (parent.stateColor == Color.orange){
	    panel.add(new Checkbox("Orange",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Orange",zustand,false));
	    }	
	if (parent.transitionColor == Color.orange){
	    panel.add(new Checkbox("Orange",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Orange",transition,false));
	    }
	if (parent.connectorColor == Color.orange){
	    panel.add(new Checkbox("Orange",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Orange",connector,false));
	    }
	add(panel);			
	
	panel = new Panel(new GridLayout(1,3));
	panel.setForeground(Color.pink);
	if (parent.stateColor == Color.pink){
	    panel.add(new Checkbox("Pink",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Pink",zustand,false));
	    }	
	if (parent.transitionColor == Color.pink){
	    panel.add(new Checkbox("Pink",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Pink",transition,false));
	    }
	if (parent.connectorColor == Color.pink){
	    panel.add(new Checkbox("Pink",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Pink",connector,false));
	    }
	add(panel);			
	
	panel = new Panel(new GridLayout(1,3));
	panel.setForeground(Color.magenta);
	if (parent.stateColor == Color.magenta){
	    panel.add(new Checkbox("Magenta",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Magenta",zustand,false));
	    }	
	if (parent.transitionColor == Color.magenta){
	    panel.add(new Checkbox("Magenta",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Magenta",transition,false));
	    }
	if (parent.connectorColor == Color.magenta){
	    panel.add(new Checkbox("Magenta",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Magenta",connector,false));
	    }
	add(panel);			

	panel = new Panel(new GridLayout(1,3));
	panel.setForeground(Color.cyan);
	if (parent.stateColor == Color.cyan){
	    panel.add(new Checkbox("Cyan",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Cyan",zustand,false));
	    }	
	if (parent.transitionColor == Color.cyan){
	    panel.add(new Checkbox("Cyan",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Cyan",transition,false));
	    }
	if (parent.connectorColor == Color.cyan){
	    panel.add(new Checkbox("Cyan",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Cyan",connector,false));
	    }
	add(panel);			

	panel = new Panel(new GridLayout(1,3));
	panel.setForeground(Color.black);
	if (parent.stateColor == Color.black){
	    panel.add(new Checkbox("Schwarz",zustand,true));
	    }
	else{
	    panel.add(new Checkbox("Schwarz",zustand,false));
	    }	
	if (parent.transitionColor == Color.black){
	    panel.add(new Checkbox("Schwarz",transition,true));
	    }
	else{
	    panel.add(new Checkbox("Schwarz",transition,false));
	    }
	if (parent.connectorColor == Color.black){
	    panel.add(new Checkbox("Schwarz",connector,true));
	    }
	else{
	    panel.add(new Checkbox("Schwarz",connector,false));
	    }
	add(panel);

	panel = new Panel(new FlowLayout());
	panel.setBackground(Color.gray);
	panel.setForeground(Color.white);
	button1 = new Button("OK");
	button1.setActionCommand("EditorOK");
	button1.addActionListener(lis);
	panel.add(button1);
	button2 = new Button("Abbrechen");
	button2.setActionCommand("EditorAbbrechen");
	button2.addActionListener(lis);
	panel.add(button2);
	add(panel);			

	pack();
	setResizable(true);
	setVisible(true);

   }

            
 
    public Color getStateColor(){
	    
	Color color = null;
	String farbe = zustand.getSelectedCheckbox().getLabel();
	if (farbe.equals("Rot")){
	   color = Color.red;
	   }
	else if (farbe.equals("Blau")){
	   color = Color.blue;
	   }
	else if (farbe.equals("Gelb")){
	   color = Color.yellow;
	   }	 
	else if (farbe.equals("Grün")){
	   color = Color.green;
	   }
	else if (farbe.equals("Orange")){
	   color = Color.orange;
	   }
	else if (farbe.equals("Pink")){
	   color = Color.pink;
	   }
	else if (farbe.equals("Magenta")){
	   color = Color.magenta;
	   }
	else if (farbe.equals("Cyan")){
	   color = Color.cyan;
	   }	
 	else if (farbe.equals("Schwarz")){
	   color = Color.black;
	   }
	return color;
	}   

   public Color getTransColor()
	{
	Color color = null;    
	String farbe = transition.getSelectedCheckbox().getLabel();
	if (farbe.equals("Rot")){
	   color = Color.red;
	   }
	else if (farbe.equals("Blau")){
	   color = Color.blue;
	   }
	else if (farbe.equals("Gelb")){
	   color = Color.yellow;
	   }	 
	else if (farbe.equals("Grün")){
	   color = Color.green;
	   }
	else if (farbe.equals("Orange")){
	   color = Color.orange;
	   }
	else if (farbe.equals("Pink")){
	   color = Color.pink;
	   }
	else if (farbe.equals("Magenta")){
	   color = Color.magenta;
	   }
	else if (farbe.equals("Cyan")){
	   color = Color.cyan;
	   }	
 	else if (farbe.equals("Schwarz")){
	   color = Color.black;
	   }
	return color;
	}   



		   	
   public Color getConColor()
	{
        Color color = null;
	String farbe = connector.getSelectedCheckbox().getLabel();
        	if (farbe.equals("Rot")){
	   color = Color.red;
	   }
	else if (farbe.equals("Blau")){
	   color = Color.blue;
	   }
	else if (farbe.equals("Gelb")){
	   color = Color.yellow;
	   }	 
	else if (farbe.equals("Grün")){
	   color = Color.green;
	   }
	else if (farbe.equals("Orange")){
	   color = Color.orange;
	   }
	else if (farbe.equals("Pink")){
	   color = Color.pink;
	   }
	else if (farbe.equals("Magenta")){
	   color = Color.magenta;
	   }
	else if (farbe.equals("Cyan")){
	   color = Color.cyan;
	   }	
 	else if (farbe.equals("Schwarz")){
	   color = Color.black;
	   }
	return color;
	}   
	      	
	  
 } 
