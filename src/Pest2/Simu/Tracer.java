package simu;

import java.awt.*;
import java.awt.event.*;


class Tracer extends Frame implements AdjustmentListener,ActionListener{

  Communicator comm=null;
  Trace trace=null;
  Status akt_status=null;

  Scrollbar scroll=null;
  Button bzurueck=null;
  Button bvor=null;
  Button bend=null;
  Button bstart=null;

  int max=0;

  public Tracer(Communicator co){
    super("Trace-Control");

    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
	setVisible(false);
      }
    });

    comm=co;

    trace=new Trace();
    akt_status=new Status();

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
    setSize(200,300);

    scroll=new Scrollbar(Scrollbar.HORIZONTAL,0,0,0,0);
    scroll.setUnitIncrement(1);
    
    c.gridwidth=5;
    c.gridheight=2;
    c.gridx=1;
    c.gridy=1;
    layout.setConstraints(scroll,c);

    bzurueck=new Button("Zurueck");
    bzurueck.setActionCommand("Zurueck");
    bzurueck.addActionListener(this);
    c.gridwidth=1;
    c.gridheight=1;
    c.gridx=1;
    c.gridy=3;
    layout.setConstraints(bzurueck,c);

    bvor=new Button("Vor");
    bvor.setActionCommand("Vor");
    bvor.addActionListener(this);
    c.gridwidth=1;
    c.gridheight=1;
    c.gridx=2;
    c.gridy=3;
    layout.setConstraints(bvor,c);
    
    bstart=new Button("Aufnahme starten");
    bstart.setActionCommand("Start");
    bstart.addActionListener(this);
    c.gridwidth=1;
    c.gridheight=1;
    c.gridx=3;
    c.gridy=3;
    layout.setConstraints(bstart,c);

    bend=new Button("Aufnahme beenden");
    bend.setActionCommand("Stop");
    bend.addActionListener(this);
    bend.setEnabled(false);
    c.gridwidth=1;
    c.gridheight=1;
    c.gridx=4;
    c.gridy=3;
    layout.setConstraints(bend,c);

    add(scroll);
    add(bzurueck);
    add(bvor);
    add(bstart);
    add(bend);
    pack();
  }

  public void add(Status status){
    trace.add(status);
    System.err.println("Trace: "+trace.size());
    scroll.setValue(0);
    scroll.setMinimum(0);
    scroll.setMaximum(trace.size());
    scroll.setVisibleAmount(2);
    max=trace.size();
    pack();
    repaint();
  }

  public void adjustmentValueChanged(AdjustmentEvent e){
    int pos=e.getValue();
    akt_status=trace.go(pos);
    comm.trace_set(akt_status);
    pack();
    repaint();
  }

  public void actionPerformed(ActionEvent e){
    String command=e.getActionCommand();
    int pos=scroll.getValue();
    if (command.equals("Vor")){
      if (pos<max){
	scroll.setValue(pos+1);
	akt_status=trace.go(pos+1);
	comm.trace_set(akt_status);
	pack();
	repaint();
      }
    }
    if (command.equals("Zurueck")){
      if(pos>0){
	scroll.setValue(pos-1);
	akt_status=trace.go(pos-1);
	comm.trace_set(akt_status);
	pack();
	repaint();
      }
    }
    if (command.equals("Start")){
      trace=new Trace();
      comm.trace_record(true);
      bvor.setEnabled(false);
      bzurueck.setEnabled(false);
      bstart.setEnabled(false);
      bend.setEnabled(true);
    }
    if (command.equals("Stop")){
      comm.trace_record(false);
      bvor.setEnabled(true);
      bzurueck.setEnabled(true);
      bstart.setEnabled(true);
      bend.setEnabled(false);
    }
  }
    

}

    

    

