
package gui;

import java.awt.*;
import java.awt.event.*;


class GUIcodegenML
extends Dialog
implements ActionListener
{

   pest parent;
   GUIwerkML user;
   CheckboxGroup cbg;
   Checkbox cb1, cb2;           
        
   public GUIcodegenML(pest parent, GUIwerkML user)
        {
        super(parent,"Codegenerator",true);
        this.parent = parent;
        this.user = user;
        Point p = parent.getLocation();
        setLocation(p.x + 30, p.y + 30);
        setLayout(new GridLayout(3,1));
        cbg = new CheckboxGroup();
        cb1 = new Checkbox("Aktuelles Statechart",cbg,true);
        cb2 = new Checkbox("Statecharts kombinieren",cbg,false);
        add(cb1);
        add(cb2);
        Button b = new Button("Ok");
        b.addActionListener(this);
        add(b);
        pack();
        setVisible(true);
        }

   String getLabel()
        {
        return cbg.getSelectedCheckbox().getLabel();    
        }

   public void actionPerformed(ActionEvent e)
        {
        setVisible(false);
        dispose();
        }


}
        

        
        
                