import check.*;
import absyn.*;
import util.*;
import java.io.*;

// zum Testen ins Haupdirectory der PEST kopieren

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: t.java,v 1.11 1998-12-22 17:30:31 swtech11 Exp $
 */
public class t {

  public static void main(String argv[]) {
    ModelCheck mc = new ModelCheck();
    Statechart sc = null;
    String n = new String();
    boolean ok = false;

    // for (int i=0; i<argv.length; i++) { System.out.println(i + " " + argv[i]); }

    if (argv.length<1) { // Hilfssystem
      System.out.println();
      System.out.println("t - Das Testsystem fuer den Syntax Check");
      System.out.println("Version: $Id: t.java,v 1.11 1998-12-22 17:30:31 swtech11 Exp $ ");
      System.out.println("Aufruf: t op1 [op2]");
      System.out.println("  op1 enthaelt die zucheckende Statechart und muss angegeben werden:");
      System.out.println("      s  = Standard Beispiel aus der abstrakten Syntax");
      System.out.println("      d  = Beispiel 1 von Daniel fuer BVars und Events");
      System.out.println("      m  = Beispiel 1 von Magnus fuer Transitionen");
      System.out.println("      m2 = Beispiel 2 von Magnus fuer Transitionen und Connectoren");
      System.out.println("      l  = leere Statechart");
      System.out.println("      f* = VORSICHT: diverse Beispiele fuer Statecharts");
      System.out.println("                     mit mehrfacher Referenzierung");
      System.out.println("               * = 1,2,3,4,5,6,7,8,9,10,11  ");
      System.out.println();
      System.out.println("  op2 enthaelt zusaetzliche Moeglichkeiten und ist optional:");
      System.out.println("      # gibt die Meldungen in einer Textdatei aus");
      System.out.println("      s speichert die Statechart");
      System.out.println();
    }
    else if (argv.length >= 1) {
      // Statecharts auswählen
	    if (argv[0].equalsIgnoreCase("d")) {
        t2_Example t = new t2_Example();
        sc = t.getExample_d();
        n = "Daniel1";
	    }
      if (argv[0].equalsIgnoreCase("s")) {
        Example t = new Example();
        sc = t.getExample();
        n = "BeispielAbsSyn";
      }
      if (argv[0].equalsIgnoreCase("l")) {
        sc = new Statechart(null,null,null,null);
        n = "null";
      }
      if (argv[0].equalsIgnoreCase("m")) {
        t2_Example t = new t2_Example();
        sc = t.getExample_m();
        n = "Magnus1";
      }
      if (argv[0].equalsIgnoreCase("m2")) {
        t2_Example t = new t2_Example();
        sc = t.getExample_m2();
        n = "Magnus2";
	    }
      if (argv[0].equalsIgnoreCase("f1")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f1();
        n = "Fatal1";
	    }
      if (argv[0].equalsIgnoreCase("f2")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f2();
        n = "Fatal2";
	    }
      if (argv[0].equalsIgnoreCase("f3")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f3();
        n = "Fatal3";
	    }
      if (argv[0].equalsIgnoreCase("f4")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f4();
        n = "Fatal4";
	    }
      if (argv[0].equalsIgnoreCase("f5")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f5();
        n = "Fatal5";
	    }
      if (argv[0].equalsIgnoreCase("f6")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f6();
        n = "Fatal6";
	    }
      if (argv[0].equalsIgnoreCase("f7")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f7();
        n = "Fatal7";
	    }
      if (argv[0].equalsIgnoreCase("f8")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f8();
        n = "Fatal8";
	    }
      if (argv[0].equalsIgnoreCase("f9")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f9();
        n = "Fatal9";
	    }
      if (argv[0].equalsIgnoreCase("f10")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f10();
        n = "Fatal10";
	    }
      if (argv[0].equalsIgnoreCase("f11")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f11();
        n = "Fatal11";          
	    }

      // Check auswählen
      ok = mc.checkModel(sc);

      // PrettyPrint pp = new PrettyPrint();
      // pp.start(sc);
      
      System.out.println("Rueckgabestatus der Checks: "+ok);
      System.out.println("Fehlermeldungen ( " + mc.getErrorNumber() +  " Stueck ):");
      if (mc.getErrorNumber()>0) {
        for (int i=1;(i<=mc.getErrorNumber());i++) {
          System.out.println("* "+mc.getErrorCode(i)+ ": " + mc.getErrorMsg(i)+ "\n  Ort: " +mc.getErrorPath(i)); } }
      System.out.println("Warnmeldungen ( " + mc.getWarningNumber() +  " Stueck ):");
      if (mc.getWarningNumber()>0) {
        for (int i=1;(i<=mc.getWarningNumber());i++) {
          System.out.println("* "+mc.getWarningCode(i)+ ": " + mc.getWarningMsg(i)+ "\n  Ort: " +mc.getWarningPath(i)); } }

      if (argv.length >= 2) {
        if (argv[1].equalsIgnoreCase("#")) {
          mc.outputToFile(new String("syntax.txt"));
        }
        if (argv[1].equalsIgnoreCase("s")) {
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
      }

    }
  }
}

