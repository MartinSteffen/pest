package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class MonitorConfigDialog.java extends Dialog{

  Vector element_views=null;
  Vector element_list=null;

  public MonitorConfigDialog(Vector v, String what){
    element_list=v;
    super("Monitor konfigurieren: "+what);
    GridBagLayout layout=new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.fill=GridBagConstraints.NONE;
    c.ipadx=10;
    c.ipady=10;
    c.anchor=GridBagConstraints.NORTH;
    c.insets=new Insets(3,3,3,3);
    setLayout(layout); 
    setSize(200,300);
    
    elements_views=new Vector();
    element_list=new MonitorComponenet(element_views);
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
    sp1.add(element_list);
    layout.setConstraints(sp1,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=6;
    Button b=new Button("OK");
    layout.setConstraints(b,c);
  
    add(l);
    add(sp1);
    add(b);

  }
 
 
}


