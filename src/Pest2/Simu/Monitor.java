package simu;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

import absyn.*;
import gui.*;

class Monitor extends Frame implements ActionListener{

  Statechart statechart=null;

  Communicator comm=null;

  Vector event_views=null;
  Vector state_views=null;
  Vector tr_views=null;
  Vector boolean_views=null;

  Vector all_events=null;
  Vector all_conditions=null;

  ScrollPane sp1=null;
  ScrollPane sp2=null;
  ScrollPane sp3=null;
  ScrollPane sp4=null;

  MonitorComponent event_list=null;
  MonitorComponent state_list=null;
  MonitorComponent tr_list=null;
  MonitorComponent boolean_list=null;

  MonitorConfigDialog config_states=null;
  MonitorConfigDialog config_events=null;
  MonitorConfigDialog config_trs=null;
  MonitorConfigDialog config_conditions=null;

  Status status=null;

  public Monitor(Statechart s, Communicator co){
    super("Monitor");

    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
	setVisible(false);
      }
    });

    comm=co;

    status=new Status();
    
    parseStatechart(s);

    GridBagLayout layout=new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.fill=GridBagConstraints.NONE;
    c.ipadx=0;
    c.ipady=0;
    c.weightx=1;
    c.weighty=1;
    c.anchor=GridBagConstraints.NORTH;
    c.insets=new Insets(3,3,3,3);
    setLayout(layout);
    setSize(700,300);

    event_views=new Vector();
    state_views=new Vector();
    tr_views=new Vector();
    boolean_views=new Vector();

    event_list=new MonitorComponent(event_views,comm);
    state_list=new MonitorComponent(state_views,comm);
    tr_list=new MonitorComponent(tr_views,comm);
    boolean_list=new MonitorComponent(boolean_views,comm);
    event_list.update();
    state_list.update();
    tr_list.update();
    boolean_list.update();
    c.gridwidth=12;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=1;
    Label l=new Label("Monitor");
    layout.setConstraints(l,c);
    c.gridwidth=2;
    c.gridheight=5;
    c.gridx=1;
    c.gridy=2;
    sp1=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp1.add(state_list);
    layout.setConstraints(sp1,c);
    c.gridwidth=2;
    c.gridheight=5;
    c.gridx=4;
    c.gridy=2;
    sp2=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp2.add(event_list);
    layout.setConstraints(sp2,c);
    c.gridwidth=2;
    c.gridheight=5;
    c.gridx=7;
    c.gridy=2;
    sp3=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp3.add(boolean_list);
    layout.setConstraints(sp3,c);
    c.gridwidth=2;
    c.gridheight=5;
    c.gridx=10;
    c.gridy=2;
    sp4=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    sp4.add(tr_list);
    layout.setConstraints(sp4,c);
    c.gridwidth=2;
    c.gridheight=2;
    c.gridx=1;
    c.gridy=8;
    Label ls=new Label("States");
    layout.setConstraints(ls,c);
    c.gridwidth=2;
    c.gridheight=2;
    c.gridx=4;
    c.gridy=8;
    Label le=new Label("Events");
    layout.setConstraints(le,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=7;
    c.gridy=8;
    Label lb=new Label("Conditions");
    layout.setConstraints(lb,c);
    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=10;
    c.gridy=8;
    Label lt=new Label("Transitionen");
    layout.setConstraints(lt,c);
    

    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=4;
    c.gridy=9;
    Button be=new Button("Hinzufuegen/Entfernen");
    be.setActionCommand("Knopf Events");
    be.addActionListener(this);

    layout.setConstraints(be,c);

    c.gridwidth=2;
    c.gridheight=1;
    c.gridx=7;
    c.gridy=9;
    Button bc=new Button("Hinzufuegen/Entfernen");
    bc.setActionCommand("Knopf Conditions");
    //bc.setEnabled(false);
    bc.addActionListener(this);

    layout.setConstraints(bc,c);

 

    add(l);
    add(ls);
    add(le);
    add(lb);
    add(lt);
    add(sp1);
    add(sp2);
    add(sp3);
    add(sp4);
    add(be);
    add(bc);
    pack();
  }


  public void actionPerformed(ActionEvent e){
    String command=e.getActionCommand();
    if (command.equals("Knopf Events")){
      config_events=new MonitorConfigDialog(this,all_events,event_views,"Events");
      config_events.show();
      event_views=config_events.getAnswer();
      pack();
    }


    if (command.equals("Knopf Conditions")){
      config_conditions=new MonitorConfigDialog(this,all_conditions,boolean_views,"Conditions");
      config_conditions.show();
      boolean_views=config_conditions.getAnswer();
      pack();
    }


    setStatus(status);

  }

  void parseStatechart(Statechart s){
    all_conditions=new Vector();
    all_events=new Vector();
    BvarList var_tail=s.bvars;
    if (var_tail!=null){
      Bvar var_head=var_tail.head;
      all_conditions.addElement(new BooleanView(var_head));
      var_tail=var_tail.tail;
      while (var_tail!=null){
	var_tail=var_tail.tail;
	var_head=var_tail.head;
	all_conditions.addElement(new BooleanView(var_head));
      }
    }
    SEventList event_tail=s.events;
    if (event_tail!=null){
      SEvent event_head=event_tail.head;
      all_events.addElement(new EventView(event_head));
      event_tail=event_tail.tail;
      while (event_tail!=null){
	event_head=event_tail.head;
	all_events.addElement(new EventView(event_head));
	event_tail=event_tail.tail;
      }
    }
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


  void setBooleans(BooleanTabelle bt){
    BooleanView temp=null;
    int size=boolean_views.size();
    for (int i=0; i<size; i++){
       temp=(BooleanView)boolean_views.elementAt(i);
       temp.setState(bt.isTrue(temp.bvar));
       boolean_views.setElementAt(temp,i);
    }
    boolean_list.setVector(boolean_views);
  };

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



  Vector buildStateViews(StateTabelle tt){
    Vector result=new Vector();
    StateView temp=null;
    Path temppath=null;
    Enumeration enum=tt.data.keys();
    while (enum.hasMoreElements()){
      temppath=(Path)enum.nextElement();
      temp=new StateView(temppath,(State)tt.data.get(temppath));
      result.addElement(temp);
    }
    return result;
  }

  Vector buildTrViews(TransitionTabelle tt){
    Vector result=new Vector();
    TrView temp=null;
    Tr trans=null;
    Enumeration enum=tt.data.keys();
    while (enum.hasMoreElements()){
      trans=(Tr)enum.nextElement();
      temp=new TrView(trans);
      result.addElement(temp);
    }
    return result;
  }

  public void setStatus(Status s){
    state_views=buildStateViews(s.states);
    setStates(s.states);
    setEvents(s.events);
    setBooleans(s.booleans);
    tr_views=buildTrViews(s.transitions);
    setTrs(s.transitions);
    pack();
    repaint();
  }



}
   
     
    




