package popdialoge;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.*;
import java.io.*;

/**
* Die Klasse "dialog" ist die Vaterklasse unserer Dialogfenster.
* Bis auf die Buttons der unteren Fensterleiste und der Methode
* actionPerformed implementiert sie deren gesamte Funktionalität.
**/

abstract public class dialog
extends Dialog
implements ActionListener
{
   public static int OK = 0;
   public static int YES = 1;
   public static int NO = 2;
   public static int CANCEL = 3;
   int answer;
   int count;
   Panel panel;
   String Line[];


   public dialog(Frame parent ,String title ,String text)
   {

       // Festsetzung des Layout und der Lage des Dialogfensters.

       super(parent , title , true);
       Point p = parent.getLocation();
       setLocation(p.x + 30 , p.y + 30);
       Line = new String[100];
       p = WindowSize(text);
       setSize(p.x , p.y);
       setBackground(Color.lightGray);
       setLayout(new BorderLayout());
       Panel panel1 = new Panel();
       panel1.setLayout(new GridLayout((count + 3) ,1));
       panel1.add(new Label(""));
       for(int i = 0 ;(i<=count) ;++i)
       {
          panel1.add( new Label(Line[i], Label.CENTER));
       }
       panel1.add(new Label());
       add("Center",panel1);

       /**
       * Die Funktionalität von "panel" wird in der jeweiligen
       * Kindklasse implementiert.
       **/
       panel = new Panel();
//       panel.setBackground(SystemColor.textHighlight);
       panel.setLayout(new FlowLayout(FlowLayout.CENTER));
       add("South" ,panel);
       //add("West", new Label(" "));
       //add("East", new Label(" "));

       //pack();
       setResizable(false);

   }

   public void actionPerformed(ActionEvent event)
   {
   }

   // Schließt das Dialogfenster.

   public void endDialog()
   {
       setVisible(false);
       dispose();
       ((Window)getParent()).toFront();
       getParent().requestFocus();
   }

   /**
   * Liefert als Integerwert den betätigten Button. Die Variable "answer"
   * erhält ihren Wert inder Methode actionPerformed der jeweiligen
   * Kindklasse.
   **/

   public int getAnswer()
   {
       return answer;
   }

   /**
   * Berechnet aus der Länge des übergebenen Textes als Rückgabewert die Größe des
   * Dialogfensters. Weiterhin wird der Text in Zeilen zerlegt und diese im
   * Stringarray Line abgelegt.
   **/

   public Point WindowSize(String text)
   {

      int charPerLine = (int)(3*Math.sqrt((double)text.length()));
      if (charPerLine < 40){
          charPerLine = 40;
          }
      StringTokenizer tok = new StringTokenizer(text);
      count = 0 ;
      String s;
      while (tok.hasMoreTokens()){
          s = " " + tok.nextToken();
          if (((Line[count]+ s).length()- 5) > charPerLine){
              Line[count] = "  " + Line[count].substring(5) + "  ";
              ++count;
              }
          Line[count] += s ;
          }
       Line[count] = "  " + Line[count].substring(5);
       Point p = new Point();
       p.x = (int)((charPerLine + 2) * 6.25);
       p.y = ((count + 3)* 18) + 45;
       return p;
   }
}    


