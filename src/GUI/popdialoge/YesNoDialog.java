package GUI.popdialoge;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;

/**
* Erstellt ein Fenster zur Ausgabe von Texte variabler Länge.
* Bis auf die untere Fensterleiste mit den Buttons "Yes" und "No"
* sowie der Methode actionPerformed implementiert die Vaterklasse
* "dialog" die gesamte Funktionalität.
**/

public class YesNoDialog
extends dialog
{

   /**
   * Der Panel panel wurde in der Vaterklasse definiert.Hier wird er
   * mit den Buttons des Dialogfensters versehen. 
   **/

   public YesNoDialog(Frame parent , String title , String text)
   {
      super(parent , title , text);
      Button button = new Button("Yes");
      button.addActionListener(this);
      super.panel.add(button);
      button = new Button("No");
      button.addActionListener(this);
      super.panel.add(button);
      //super.pack();
      super.setVisible(true);
  }

  /**
  * Die Variable "answer" wurde in der Vaterklasse definiert.
  **/


   public void actionPerformed(ActionEvent event)
   {
      if (event.getActionCommand().equals("Yes"))
      {
         answer = YES ;
      }
      else{
         answer = NO;
      }
      endDialog();
   }
}

