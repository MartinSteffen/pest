package GUI.popdialoge;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.*;


/**
* Die Klasse "dialog" ist die Vaterklasse unserer Dialogfenster.
* Bis auf die Buttons der unteren Fensterleiste und der Methode
* actionPerformed implementiert sie deren gesamte Funktionalit„t.
**/

abstract class dialog
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
   FontMetrics fm;


   public dialog(Frame parent, FontMetrics fm, String title ,String text)
   {

       // Festsetzung des Layout und der Lage des Dialogfensters.

       super(parent, title, true);
       this.fm = fm ;
       setFont(fm.getFont());
       Point p = parent.getLocation();
       setLocation(p.x + 30 , p.y + 30);
       Line = new String[100];
       setBackground(Color.lightGray);
       setLayout(new BorderLayout());
       p = WindowSize(text);
       setSize(p.x , p.y);
       Panel panel1 = new Panel();
       panel1.setLayout(new GridLayout((count + 3) ,1));
       panel1.add(new Label(""));
       for(int i = 0 ;(i<=count) ;++i)
       {
          panel1.add( new Label(Line[i], Label.CENTER));
       }
       //panel1.add(new Label());
       add("Center",panel1);

       /**
       * Die Funktionalit„t von "panel" wird in der jeweiligen
       * Kindklasse implementiert.
       **/
       panel = new Panel();
       //panel.setBackground(SystemColor.textHighlight);
       panel.setLayout(new FlowLayout(FlowLayout.CENTER));
       add("South" , panel);
       setResizable(false);
   }

   
   public void actionPerformed(ActionEvent event)
   {
   }

   // Schlie˜t das Dialogfenster.

   public void endDialog()
   {
       setVisible(false);
       dispose();
       ((Window)getParent()).toFront();
       getParent().requestFocus();
   }

   /**
   * Liefert als Integerwert den bet„tigten Button. Die Variable "answer"
   * erh„lt ihren Wert inder Methode actionPerformed der jeweiligen
   * Kindklasse.
   **/

   public int getAnswer()
   {
       return answer;
   }

   /**
   * Berechnet aus der L„nge des bergebenen Textes als Rckgabewert die Gr÷˜e des
   * Dialogfensters. Weiterhin wird der Text in Zeilen zerlegt und diese im
   * Stringarray Line abgelegt.
   **/

   public Point WindowSize(String text)
   {
      int charPerLine = (int)(Math.PI*Math.sqrt((double)text.length()));
      if (charPerLine < 30){
          charPerLine = 30;
          }

      StringTokenizer tok = new StringTokenizer(text);
      count = 0 ;
      int lineWidth = 0;
      int lineHeight = 0 ;
      String s;
    
      Line[0] = "";
      while (tok.hasMoreTokens()){
          s = " " + tok.nextToken();
          if (((Line[count]+ s).length()) > charPerLine){
              Line[count] = "       " + Line[count] + "       ";
              if (fm.stringWidth(Line[count]) > lineWidth){
                 lineWidth = fm.stringWidth(Line[count]);
	      } 
              Line[++count] = "";
              }
          Line[count] += s ;
          }
       Line[count] = "    " + Line[count] + "    ";
       if (lineWidth == 0){
           lineWidth = fm.stringWidth("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
       }    
       lineHeight = fm.getHeight();
       
       Point p = new Point();
       p.x = lineWidth ;
       p.y = ((count + 8 )* lineHeight) ;
       return p;
   }
}    


