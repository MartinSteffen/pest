/**
 * Options-Dialog to configure the Code-Generation.
 *
 * @author Marcel Kyas, Walter Loeser, Andre Paetzold.
 * @version $Id: CodeGenConfig.java,v 1.1 1999-02-11 12:06:26 swtech25 Exp $
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

public CodeGenConfig(gui.pest vater){
     super(vater, "Options - Code Generation", true);
     parent = vater;

     Panel panel = new Panel(new GridLayout(20, 1));
     panel.add(new Label("Handling of Non-determinism"));
     
     cbox1 = new Checkbox[3];
     cbox1[0] = new Checkbox("Take First", true, cboxgroup1);
     cbox1[1] = new Checkbox("Round Robin", false, cboxgroup1);
     cbox1[2] = new Checkbox("Randomized", false, cboxgroup1);
     for (int i=0; i<=2; i++) panel.add(cbox1[i]);

     panel.add(new Label("---------------------------"));
     panel.add(new Label("Environment Style"));
     
     cbox2 = new Checkbox[2];
     cbox2[0] = new Checkbox("None", true, cboxgroup2);
     cbox2[1] = new Checkbox("Simple", false, cboxgroup2);
     for (int i=0; i<=1; i++) panel.add(cbox2[i]);

     panel.add(new Label("---------------------------"));
     panel.add(new Checkbox("Generate traces", false));
     panel.add(new Label("---------------------------"));
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
	  setVisible(false);
	  dispose();
     }
}

}
