package simu;

import absyn.*;
import java.awt.*;
import java.awt.event.*;

class BVarRacingDialog extends Dialog implements ActionListener{
  BooleanTabelle status=null;
  Bvar var=null;

  BVarRacingDialog(Frame parent, BooleanTabelle s, Bvar v){ 
    super(parent,"Racing bei Condition");
    setLayout(new GridLayout(2,1));
    status=s;
    var=v;
    Label l=new Label("Racing-Situation bei Variable '"+v.var+"' aufgetreten!\n\n Variable wird auf false gesetzt!");
    Button b=new Button("OK");
    b.addActionListener(this);
    add(l);
    add(b);

    
  }
  
  public void actionPerformed(ActionEvent e){
    status.setFalse(var);
    setVisible(false);
  }


  public BooleanTabelle getAnswer(){
    return status;
  }

}
