package simu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import absyn.*;

public class SchrittweitenDialog extends Dialog implements ActionListener{

  int result=0;
  Button b=null;
  TextField tf=null;
  int schrittweite=1;

  SchrittweitenDialog(Frame parent,int schrittweite){
    super(parent,"Schrittweite",true);
    setLayout(new FlowLayout());
    tf=new TextField(3);
    tf.setText(""+schrittweite+"");
    b=new Button("Schrittweite uebernehmen");
    b.addActionListener(this);
    add(tf);
    add(b);
    setSize(200,100);
  }
      

  public void actionPerformed(ActionEvent e){
    String text=tf.getText();
    if ((text!="")&&(text!=null)){
      result=Integer.parseInt(text);
    }
    if (result<=0) {
      tf.setText(""+schrittweite+"");
    }       
    else {
      tf.setText("");
      setVisible(false);
    }
  }


  public int getAnswer(){
    return result;
  }

}
    
  
  
