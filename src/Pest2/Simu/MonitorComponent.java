package simu;

import java.awt.event.*;
import java.awt.*;
import java.util.*;


class MonitorComponent extends Container{

  Vector components=null;

  ItemListener listener=null;

  MonitorComponent(Vector v, ItemListener l){
    components=v; 
    listener=l;
  }

  public void setVector(Vector v){
    components=v;
    update();
  }

  public Vector getVector(){
    return components;
  }

  public void paintComponents(Graphics g){
    Enumeration enum=components.elements();
    while (enum.hasMoreElements()){
      ((Component)enum.nextElement()).repaint();
    }
  }

  public void update(){
    removeAll();
    GridBagLayout layout=new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.fill=GridBagConstraints.NONE;
    c.ipadx=0;
    c.ipady=0;
    c.gridwidth=1;
    c.gridheight=1;
    c.gridy=1;
    c.gridx=1;
    c.weightx=1;
    c.weighty=1;
    c.anchor=GridBagConstraints.NORTHWEST;
    
    c.insets=new Insets(0,0,0,0);
    setLayout(layout);
    Checkbox temp=null;
    Enumeration enum=components.elements();
    while (enum.hasMoreElements()){
      temp=(Checkbox)enum.nextElement();
      temp.addItemListener(listener);
      c.gridy++;
      layout.setConstraints(temp,c);
      add((Checkbox)(temp));
    }
    repaint();
  }
}
    
  









