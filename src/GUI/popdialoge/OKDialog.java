package GUI.popdialoge;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;

/**
* Erstellt ein Fenster zur Ausgabe von Texte variabler Länge.
* Bis auf die untere Fensterleiste mit dem Button "OK" sowie
* der Methode actionPerformed implementiert die Vaterklasse
* "dialog" die gesamte Funktionalität.
**/


public class OKDialog
extends dialog
{

   /**
   * Der Panel panel wurde in der Vaterklasse definiert.Hier wird er
   * mit den Buttons des Dialogfensters versehen.
   **/

   public OKDialog(Frame parent , String title , String text)
   {
      super(parent , title , text);
      Button button = new Button("OK");
      button.addActionListener(this);
      panel.add(button);
      setVisible(true);
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
   
