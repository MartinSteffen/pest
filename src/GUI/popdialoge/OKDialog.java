package GUI.popdialoge;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;

/**
* Erstellt ein Fenster zur Ausgabe von Texte variabler L„nge.
* Bis auf die untere Fensterleiste mit dem Button "OK" sowie
* der Methode actionPerformed implementiert die Vaterklasse
* "dialog" die gesamte Funktionalit„t.
**/


public class OKDialog
extends dialog
{

   /**
   * Der Panel panel wurde in der Vaterklasse definiert.Hier wird er
   * mit den Buttons des Dialogfensters versehen.
   **/

   public OKDialog(Frame parent, FontMetrics fm, String title , String text)
   {
      super(parent, fm, title , text);
      Button button = new Button("OK");
      button.addActionListener(this);
      super.panel.add(button);
      super.setVisible(true);
  }

  /**
  * Die Variable "answer" wurde in der Vaterklasse definiert.
  **/

   public void actionPerformed(ActionEvent event)
   {
      if (event.getActionCommand().equals("OK"))
      {
         answer = OK ;
         endDialog();
      }
   }
}   
