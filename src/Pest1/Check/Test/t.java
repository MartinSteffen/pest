import check.*;
import absyn.*;
import util.*;
import java.io.*;

// zum Testen ins Haupdirectory der PEST kopieren

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: t.java,v 1.15 1999-01-22 20:33:59 swtech11 Exp $
 */
public class t {
  ModelCheck mc = new ModelCheck();
  static Statechart sc = new Statechart(null,null,null,null);
  static String n = new String();
  boolean ok = false;

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

  public static void main(String argv[]) {
        t2_Example t2;
        tf_Example tf;

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

        tf = new tf_Example();
        sc = tf.getExample_f1();
        n = "Fatal1";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f2();
        n = "Fatal2";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f3();
        n = "Fatal3";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f4();
        n = "Fatal4";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f5();
        n = "Fatal5";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f6();
        n = "Fatal6";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f7();
        n = "Fatal7";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f8();
        n = "Fatal8";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f9();
        n = "Fatal9";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f10();
        n = "Fatal10";
        s();

        tf = new tf_Example();
        sc = tf.getExample_f11();
        n = "Fatal11";
        s();
  }
  
}

