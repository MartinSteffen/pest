 /**
 * DialogGetLabel.java
 *
 *
 * Created: Mon Nov  9 13:18:36 1998
 *
 * @author Software Technologie 24
 * @version 1.0
 */

import java.awt.*;               
import java.awt.datatransfer.*;  
import java.awt.event.*;         
import java.io.*;                
import java.util.zip.*;          
import java.util.Vector;        
import java.util.Properties;     

public class DialogGetLabel extends Dialog{

TextField textfeld = new TextField(50);
DialogGetLabel  fh;

  public static String GetLabel = "";
    ActionListener listener = new ActionListener() {
       public void actionPerformed(ActionEvent e){
              GetLabel = textfeld.getText();
	      fh.dispose();
    	}
    };

    public DialogGetLabel(Frame f,String tlabel, String tcaption) {
	super(f,tcaption,true);
	fh = this;
	GetLabel = "";
	this.setSize(250,110);
	this.setLocation(500,300);
	this.setLayout(new BorderLayout(3,3));
	Label textzeile = new Label(tlabel);
	textfeld.setText(tcaption);
	this.add("North",textzeile);
	this.add("Center",textfeld);
	Button OK = new Button("O.K.");
	this.add("South",OK);

	OK.setActionCommand("O.K.");
	OK.addActionListener(listener);

	this.isModal();
	this.show();
        }
} // DialogGetLabel
