package simu;

import java.awt.*;
import java.util.*;


class MonitorComponent extends Container{

  Vector components=null;

  MonitorComponent(Vector v){
    components=v; 
  }

  public void setVector(Vector v){
    components=v;
    update();
  }

  public void update(){
    removeAll();
    GridBagLayout layout=new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.fill=GridBagConstraints.NONE;
    c.ipadx=2;
    c.ipady=2;
    c.gridwidth=1;
    c.gridheight=1;
    c.gridy=1;
    c.gridx=1;
    c.anchor=GridBagConstraints.NORTH;
    c.insets=new Insets(1,1,1,1);
    setLayout(layout);
    Component temp=null;
    Enumeration enum=components.elements();
    while (enum.hasMoreElements()){
      temp=(Component)enum.nextElement();
      c.gridx++;
      layout.setConstraints(temp,c);
      add((Component)(enum.nextElement()));
    }
    repaint();
  }
}
    
  
