/**
 * Options-Dialog to configure the Code-Generation.
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenConfig.java,v 1.2 1999-02-17 11:41:54 swtech25 Exp $
 */

package codegen;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class CodeGenConfig extends Dialog implements ActionListener{

     gui.pest parent;
     Button button;
     Checkbox[] cbox1, cbox2;
     CheckboxGroup cboxgroup1 = new CheckboxGroup();
     CheckboxGroup cboxgroup2 = new CheckboxGroup();
     CodeGenOpt cg_opt;

public CodeGenConfig(gui.pest vater, CodeGenOpt _cg_opt){
     super(vater, "Options - Code Generation", true);
     parent = vater;

     if (_cg_opt == null) {
          cg_opt = new CodeGenOpt();
     } else {
          cg_opt = _cg_opt; 
     }
     
     Panel panel = new Panel(new GridLayout(15, 1));
     panel.add(new Label("Handling of Non-determinism"));
     
     cbox1 = new Checkbox[3];
     if (cg_opt.nondetFlavor == cg_opt.takeFirst) {
	  cbox1[0] = new Checkbox("Take First", true, cboxgroup1);
     } else {
	  cbox1[0] = new Checkbox("Take First", false, cboxgroup1);
     }
     if (cg_opt.nondetFlavor == cg_opt.roundRobin) {
	  cbox1[1] = new Checkbox("Round Robin", true, cboxgroup1);
     } else {
	  cbox1[1] = new Checkbox("Round Robin", false, cboxgroup1);
     }
     if (cg_opt.nondetFlavor == cg_opt.random) {
	  cbox1[2] = new Checkbox("Randomized", true, cboxgroup1);
     } else {
	  cbox1[2] = new Checkbox("Randomized", false, cboxgroup1);
     }

     for (int i=0; i<=2; i++) panel.add(cbox1[i]);

     panel.add(new Label("---------------------------"));
     panel.add(new Label("Environment Style"));
     
     cbox2 = new Checkbox[2];
     cbox2[0] = new Checkbox("None", true, cboxgroup2);
     cbox2[1] = new Checkbox("Simple", false, cboxgroup2);
     for (int i=0; i<=1; i++) panel.add(cbox2[i]);

     panel.add(new Label("---------------------------"));
     cbox3 = new Checkbox("Generate traces", false);
     panel.add(cbox3);

     panel.add(new Label("---------------------------"));
     cbox4=new Checkbox("Compose Statecharts", false);
     panel.add(cbox4);
     panel.add(new Label(""));
     
     button = new Button ("OK");
     button.setActionCommand("Ja");
     button.addActionListener(this);
     panel.add(button);

     add(panel);
     pack();
     setVisible(true);
}

public void actionPerformed(ActionEvent e){
     String command = e.getActionCommand();
     if (command.equals(button.getActionCommand())){
	  for (int i=0; i<=2; i++) {
	       if (cbox1[i].getState()==true) {
		    cg_opt.nondetFlavor = i;
	       }
	  }

	  if (cbox2[i].getState()==true) {
	       cg_opt.envFlavor = i;
	  }

	  
	  


	  setVisible(false);
	  dispose();
     }
}

}
