
/**
 * tesc2Option.java
 *
 *
 * Created: Wed Jan 27 17:59:59 1999
 *
 * @author Achim Abeling
 * @version
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import tesc2.*;

class tesc2Option extends Dialog
    implements ActionListener {
    
    pest parent;
    Checkbox[] checkbox;;
    CheckboxGroup cbg = new CheckboxGroup();

    public tesc2Option(pest _parent) {
	super(_parent,"Algorithmuswahl",true);

	parent = _parent;

	Point p = parent.getLocation();
	setLocation(p.x + 30, p.y + 80);

	String[] list_of_algorithms = GraphOptimizer.LIST_OF_ALGORITHMS;
	int n = list_of_algorithms.length;
	checkbox = new Checkbox[n];
	for (int i=0;i<n;i++) {
	    if (parent.layoutAlgorithm==i) {
		checkbox[i] = new Checkbox(list_of_algorithms[i],cbg,true);
	    } else {
		checkbox[i] = new Checkbox(list_of_algorithms[i],cbg,false);
	    }
	}
	
	Panel panel = new Panel(new GridLayout(2+n,1));
	panel.add(new Label("Algorithmuswahl"));
	for (int i=0;i<n;i++) 
	    panel.add(checkbox[i]);
	Button button = new Button("Fertig");
	button.addActionListener(this);

	panel.add(button);

	this.add(panel);

	pack();
	setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {

	Checkbox selected = cbg.getSelectedCheckbox();

	/* Man ist erst fertig, wenn ein Algorithmus gewählt wurde */
	if (selected!=null) {
	    for (int i=0;i<checkbox.length;i++) {
		if (checkbox[i] == selected) 
		    parent.layoutAlgorithm = i;
	    }
	    setVisible(false);
	    dispose();
	}
    };
    
} // tesc2Option
