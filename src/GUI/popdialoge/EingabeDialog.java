package gui.popdialoge;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.*;


/**
* Die Klasse "dialog" ist die Vaterklasse unserer Dialogfenster.
* Bis auf die Buttons der unteren Fensterleiste und der Methode
* actionPerformed implementiert sie deren gesamte Funktionalit„t.
**/

class EingabeDialog
extends Dialog
implements ActionListener
{
   int count;
   String Eingabe;
   String Line[];
   FontMetrics fm;
   TextField Textfeld;


   public EingabeDialog(Frame parent, FontMetrics fm, String title ,String text, String defaulttext)
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
       add("North",panel1);
     
       Panel panel2 = new Panel();
       panel2.setLayout(new FlowLayout(FlowLayout.CENTER));  
       Button button = new Button("OK");
       button.addActionListener(this);
       panel2.add(button);
       button = new Button("Abbrechen");
       button.addActionListener(this);
       panel2.add(button);
       add("South" , panel2);       

       Textfeld = new TextField(defaulttext);       
       Textfeld.addActionListener(this);
       add("Center", Textfeld);

       setResizable(false);
       setVisible(true); 
         
   }

   
   
   public void actionPerformed(ActionEvent event)
   {
   Object obj = event.getSource();
   if (obj instanceof TextField){
      Eingabe = Textfeld.getText();      
      }
   else if (obj instanceof Button){
       if (event.getActionCommand().equals("OK")){
           Eingabe = Textfeld.getText();           
           }
       else{
           Eingabe = null;           
           }
       }
   endDialog();      
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




   public String getEingabe()
    {
       return Eingabe;
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
           lineWidth = fm.stringWidth("AAAAAAAAAAAAAAAAAAAAAAAAAA");
       }    
       lineHeight = fm.getHeight();
       
       Point p = new Point();
       p.x = lineWidth ;
       p.y = ((count + 10 )* lineHeight) ;
       return p;
   }
}    


