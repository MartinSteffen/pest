package simu;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

class Monitor extends Frame{

  Vector event_views=null;
  Vector state_views=null;
  Vector tr_views=null;
  Vector boolean_views=null;
  ScrollPane sp1=null;
  ScrollPane sp2=null;
  ScrollPane sp3=null;
  ScrollPane sp4=null;
  MonitorComponent event_list=null;
  MonitorComponent state_list=null;
  MonitorComponent tr_list=null;
  MonitorComponent boolean_list=null;
  

  public Monitor(){
    super("Monitor - funktioniert noch nicht!!");

    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
	setVisible(false);
      }
    });

    GridBagLayout layout=new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.fill=GridBagConstraints.NONE;
    c.ipadx=10;
    c.ipady=10;
    c.anchor=GridBagConstraints.NORTH;
    c.insets=new Insets(3,3,3,3);
    setLayout(layout);
    setSize(700,300);

    event_views=new Vector();
    state_views=new Vector();
    tr_views=new Vector();
    boolean_views=new Vector();

    event_list=new MonitorComponent(event_views);
    state_list=new MonitorComponent(state_views);
    tr_list=new MonitorComponent(tr_views);
    boolean_list=new MonitorComponent(boolean_views);

    c.gridwidth=12;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=1;
    Label l=new Label("Monitor - funktioniert noch nicht");
    layout.setConstraints(l,c);
    c.gridwidth=2;
    c.gridheight=2;
    c.gridx=1;
    c.gridy=2;
    sp1=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp1.add(state_list);
    layout.setConstraints(sp1,c);
    c.gridwidth=2;
    c.gridheight=2;
    c.gridx=4;
    c.gridy=2;
    sp2=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp2.add(event_list);
    layout.setConstraints(sp2,c);
    c.gridwidth=2;
    c.gridheight=2;
    c.gridx=7;
    c.gridy=2;
    sp3=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp3.add(boolean_list);
    layout.setConstraints(sp3,c);
    c.gridwidth=2;
    c.gridheight=2;
    c.gridx=10;
    c.gridy=2;
    sp4=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp4.add(tr_list);
    layout.setConstraints(sp4,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=5;
    Label ls=new Label("States");
    layout.setConstraints(ls,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=4;
    c.gridy=5;
    Label le=new Label("Events");
    layout.setConstraints(le,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=7;
    c.gridy=5;
    Label lb=new Label("Conditions");
    layout.setConstraints(lb,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=10;
    c.gridy=5;
    Label lt=new Label("Transitionen");
    layout.setConstraints(lt,c);
    
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=6;
    Button bs=new Button("Hinzufuegen/Entfernen");
    layout.setConstraints(bs,c);

    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=4;
    c.gridy=6;
    Button be=new Button("Hinzufuegen/Entfernen");
    layout.setConstraints(be,c);

    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=7;
    c.gridy=6;
    Button bc=new Button("Hinzufuegen/Entfernen");
    layout.setConstraints(bc,c);

    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=10;
    c.gridy=6;
    Button bt=new Button("Hinzufuegen/Entfernen");
    layout.setConstraints(bt,c);


    add(l);
    add(ls);
    add(le);
    add(lb);
    add(lt);
    add(sp1);
    add(sp2);
    add(sp3);
    add(sp4);
    add(bs);
    add(be);
    add(bc);
    add(bt);
    
  }

  void setEvents(EventTabelle et){
    EventView temp=null;
    int size=event_views.size();
    for (int i=0; i<size; i++){
      temp=(EventView)event_views.elementAt(i);
      temp.setState(et.isSet(temp.event.name));
      event_views.setElementAt(temp,i);
    }
    event_list.setVector(event_views);
  }
        

  void setStates(StateTabelle st){
     StateView temp=null;
     int size=state_views.size();
     for (int i=0; i<size; i++){
       temp=(StateView)state_views.elementAt(i);
       temp.setState(st.isActive(temp.path));
       state_views.setElementAt(temp,i);
     }
     state_list.setVector(state_views);
  }


  void setBooleans(BooleanTabelle bt){};

  void setTrs(TransitionTabelle tt){
    TrView temp=null;
    int size=tr_views.size();
    for (int i=0; i<size; i++){
       temp=(TrView)tr_views.elementAt(i);
       temp.setState(tt.isActive(temp.transition));
       tr_views.setElementAt(temp,i);
    }
    tr_list.setVector(tr_views);
  }

  public void setStatus(Status s){
    setEvents(s.events);
    setStates(s.states);
    setBooleans(s.booleans);
    setTrs(s.transitions);
    repaint();
  }


}
   
     
    
