package simu;

import absyn.*;
import java.awt.*;
import java.awt.event.*;

public class BVarRacingDialog extends Dialog implements ActionListener{
  BooleanTabelle status=null;
  Bvar var=null;

  BVarRacingDialog(Frame parent, BooleanTabelle s, Bvar v){ 
    super(parent,"Racing bei Condition");
    status=s;
    var=v;
  }
  
  public void actionPerformed(ActionEvent e){
    setVisible(false);
  }


  public BooleanTabelle getAnswer(){
    return status;
  }

}
