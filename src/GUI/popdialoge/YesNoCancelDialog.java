package gui.popdialoge;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;

/**
* Erstellt ein Fenster zur Ausgabe von Texte variabler L„nge.
* Bis auf die untere Fensterleiste mit den Buttons "Yes", "No"
* und "Cancel" sowie der Methode actionPerformed implementiert
* die Vaterklasse "dialog" die gesamte Funktionalit„t.
**/

class YesNoCancelDialog
extends dialog
{
   /**
   * Der Panel panel wurde in der Vaterklasse definiert.Hier wird er
   * mit den Buttons des Dialogfensters versehen. 
   **/

   public YesNoCancelDialog(Frame parent, FontMetrics fm, String title , String text)
   {
      super(parent, fm, title , text);
      Button button = new Button("Ja");
      button.addActionListener(this);
      super.panel.add(button);
      button = new Button("Nein");
      button.addActionListener(this);
      super.panel.add(button);
      button = new Button("Abbrechen");
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
      if (event.getActionCommand().equals("Ja")){
         answer = YES ;
         }
      else if (event.getActionCommand().equals("Nein")){
         answer = NO;
         }
      else{
         answer = CANCEL;
         }
      endDialog();
   }
}
