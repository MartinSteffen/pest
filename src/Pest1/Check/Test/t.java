import check.*;
import absyn.*;
import util.*;
import java.io.*;

/**
 * Eine Testklasse zum automatischen Generieren unserer Test-Statecharts
 *
 * @author Java Praktikum: <a href="mailto:swtech11@informatik.uni-kiel.de">Gruppe 11</a><br>Daniel Wendorff und Magnus Stiller
 * @version  $Id: t.java,v 1.16 1999-02-08 23:38:04 swtech11 Exp $
 */
public class t {
  static Statechart sc = new Statechart(null,null,null,null);
  static String n = new String();
  boolean ok = false;

  /**
   * Methode zum eigentlichen Speichern der Statecharts
   */
  static void s() {
 		try {
      n = n + ".sc";
	    FileOutputStream outf = new FileOutputStream(n);
      ObjectOutputStream oos = new ObjectOutputStream(outf);
      oos.writeObject(sc);
	    oos.flush();
      oos.close();
     }
     catch (Exception e) {
       System.out.println("Fehler: Die Datei kann nicht gespeichert werden"); // Alarm !
 	   }
  }

  /**
   * ausführbare MAIN-Methode, die alle unsere Beispiel Statecharts durchgeht und zum Speichern vorbereitet
   */
  public static void main(String argv[]) {
        t2_Example t2;

        t2 = new t2_Example();
        sc = t2.getExample_d();
        n = "Daniel1";
        s();

        t2 = new t2_Example();
        sc = t2.getExample_d2();
        n = "Daniel2";
        s();

        Example t = new Example();
        sc = t.getExample();
        n = "BeispielAbsSyn";
        s();

        t2 = new t2_Example();
        sc = t2.getExample_m();
        n = "Magnus1";
        s();

        t2 = new t2_Example();
        sc = t2.getExample_m2();
        n = "Magnus2";
        s();

        t2 = new t2_Example();
        sc = t2.getExample_m3();
        n = "Magnus3";
        s();
  }

}

