/**
 * Options-Dialog to configure the Code-Generation.
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenConfig.java,v 1.3 1999-02-17 13:22:40 swtech25 Exp $
 */

package codegen;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class CodeGenConfig extends Dialog implements ActionListener{
     
     gui.pest parent;

     Button button;
     Checkbox[] cbox1, cbox2;
     Checkbox cbox3, cbox4, cbox5;
     CheckboxGroup cboxgroup1 = new CheckboxGroup();
     CheckboxGroup cboxgroup2 = new CheckboxGroup();
     TextField mypath, class1, class2;
     
     CodeGenOpt cg_opt;
     
public CodeGenConfig(gui.pest vater, CodeGenOpt _cg_opt){
     super(vater, "Options - Code Generation", true);
     parent = vater;
     
     if (_cg_opt == null) {
          cg_opt = new CodeGenOpt();
     } else {
          cg_opt = _cg_opt; 
     }
     
     Panel panel = new Panel(new GridLayout(22, 1));
     panel.add(new Label("Handling of Non-determinism"));
     
     String[] label1 = {"Take First",
			"Round Robin",
			"Random"};
     
     cbox1 = new Checkbox[3];
     for (int i=0; i<=2; i++) {
 	  if (cg_opt.nondetFlavor == i) {
 	       cbox1[i] = new Checkbox(label1[i], true, cboxgroup1);
 	  } else {
 	       cbox1[i] = new Checkbox(label1[i], false, cboxgroup1);
 	  }
 	  panel.add(cbox1[i]);
     }

     panel.add(new Label("---------------------------"));
     panel.add(new Label("Environment Style"));
     
     String[] label2 = {"none",
			"simple"};

     cbox2 = new Checkbox[2];
     for (int i=0; i<=1; i++) {
	  if (cg_opt.envFlavor == i) {
	       cbox2[i] = new Checkbox(label2[i], true, cboxgroup2);
	  } else {
	       cbox2[i] = new Checkbox(label2[i], false, cboxgroup2);
	  }
	  panel.add(cbox2[i]);
     }

     panel.add(new Label("---------------------------"));
     if (cg_opt.traceCodeGen == true) {
	  cbox3 = new Checkbox("Generate traces", true);
     } else {
	  cbox3 = new Checkbox("Generate traces", false);
     }
     panel.add(cbox3);

     panel.add(new Label("---------------------------"));
     if (cg_opt.twoStatecharts == true) {
	  cbox4=new Checkbox("Compose Statecharts", true);
     } else {
	  cbox4=new Checkbox("Compose Statecharts", false);
     }
     panel.add(cbox4);
     panel.add(new Label("Two Classnames"));
     class1 = new TextField(cg_opt.name1, 20);
     class2 = new TextField(cg_opt.name2, 20);
     panel.add(class1);
     panel.add(class2);

     panel.add(new Label("---------------------------"));
     if (cg_opt.verbose == true) {
	  cbox5=new Checkbox("be verbose", true);
     } else {
	  cbox5=new Checkbox("be verbose", false);
     }
     panel.add(cbox5);

     panel.add(new Label("---------------------------"));
     panel.add(new Label("Path for CodeGen"));
     mypath = new TextField(cg_opt.path, 20);
     panel.add(mypath);

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

	  for (int i=0; i<=1; i++) {
	       if (cbox2[i].getState()==true) {
		    cg_opt.envFlavor = i;
	       }
	  }

	  if (cbox3.getState()==true) {
	       cg_opt.traceCodeGen = true;
	  }

	  if (cbox4.getState()==true) {
	       cg_opt.twoStatecharts = true;
	  }
	  
	  if (cbox5.getState()==true) {
	       cg_opt.verbose = true;
	  }

	  cg_opt.path = mypath.getText();
	  cg_opt.name1 = class1.getText();
	  cg_opt.name2 = class2.getText();

	  setVisible(false);
	  dispose();
     }
}

}
