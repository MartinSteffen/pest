package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class MonitorConfigDialog extends Dialog implements ActionListener, ItemListener{

  Vector elements_selected=null;
  Vector elements_all=null;
  MonitorComponent list=null;
  ScrollPane sp1=null;

  public MonitorConfigDialog(Frame parent, Vector all_elements,Vector selected_elements, String what){
    super(parent,"Monitor konfigurieren: "+what,true);
    elements_selected=selected_elements;
    elements_all=all_elements;
    GridBagLayout layout=new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.fill=GridBagConstraints.NONE;
    c.ipadx=10;
    c.ipady=10;
    c.anchor=GridBagConstraints.NORTH;
    c.insets=new Insets(3,3,3,3);
    setLayout(layout); 
    setSize(200,300);
    Checkbox tempcomp=null;
    for (int i=0; i<elements_all.size();i++){
      tempcomp=(Checkbox)elements_all.elementAt(i);
      tempcomp.setState(elements_selected.contains(tempcomp));
      elements_all.setElementAt(tempcomp,i);
    }
	
    list=new MonitorComponent(elements_all,this);
    list.update();
    c.gridwidth=1;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=1;
    Label l=new Label("Bitte die gewuenschten Elemente aktivieren");
    layout.setConstraints(l,c);
    c.gridwidth=2;
    c.gridheight=2;
    c.gridx=1;
    c.gridy=2;
    sp1=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp1.add(list);
    layout.setConstraints(sp1,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=6;
    Button b=new Button("OK");
    b.addActionListener(this);
    layout.setConstraints(b,c);
    add(l);
    add(sp1);
    add(b);
    pack();
  }

  public Vector getAnswer(){
    return elements_selected;
  }
 

  public void itemStateChanged(ItemEvent e){
  }

  public void actionPerformed(ActionEvent e){
    Vector selection=list.getVector();
    elements_selected=new Vector();
    Enumeration enum=selection.elements();
    Checkbox temp=null;
    boolean selected=false;
    while (enum.hasMoreElements()){
      temp=(Checkbox)enum.nextElement();
      selected=temp.getState();
      if (selected){
	elements_selected.addElement(temp);
      }
    } 
    setVisible(false);
  }
 
}


