import check.*;
import absyn.*;
import Util.*;

// zum Testen ins Haupdirectory der PEST kopieren

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: t.java,v 1.9 1998-12-15 17:51:41 swtech00 Exp $
 */
public class t {

  public static void main(String argv[]) {
    Statechart sc = new Statechart(null,null,null,null);
    ModelCheck mc = new ModelCheck();
    boolean ok = false;

    // for (int i=0; i<argv.length; i++) { System.out.println(i + " " + argv[i]); }

    if (argv.length<2) { // Hilfssystem
      System.out.println();
      System.out.println("t - Das Testsystem fuer den Syntax Check");
      System.out.println("Aufruf: t op1 op2 [#]");
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
      System.out.println("  op2 ruft den entsprechenden Check auf:");
      System.out.println("      a  = kompletter Model Check");
      System.out.println("      b  = BVars checken");
      System.out.println("      e  = Events checken");
      System.out.println("      s  = States checken");
      System.out.println("      t  = Transitionen checken");
      System.out.println("      p  = Statechart auf mehrfache Referenzierung checken");
      System.out.println("      be = BVars und Events checken");
      System.out.println();
      System.out.println("  # gibt die Meldungen zusaetzlich in eine Textdatei aus.");       
    }
    else if (argv.length >= 1) {
      // Statecharts auswählen
	    if (argv[0].equalsIgnoreCase("d")) {
        t2_Example t = new t2_Example();
        sc = t.getExample_d();
	    }
      if (argv[0].equalsIgnoreCase("s")) {
        Example t = new Example();
        sc = t.getExample();
      }
      if (argv[0].equalsIgnoreCase("l")) {
        sc = new Statechart(null,null,null,null);
      }
      if (argv[0].equalsIgnoreCase("m")) {
        t2_Example t = new t2_Example();
        sc = t.getExample_m();
      }
      if (argv[0].equalsIgnoreCase("m2")) {
        t2_Example t = new t2_Example();
        sc = t.getExample_m2();
	    }
      if (argv[0].equalsIgnoreCase("f1")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f1();
	    }
      if (argv[0].equalsIgnoreCase("f2")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f2();
	    }
      if (argv[0].equalsIgnoreCase("f3")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f3();
	    }
      if (argv[0].equalsIgnoreCase("f4")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f4();
	    }
      if (argv[0].equalsIgnoreCase("f5")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f5();
	    }
      if (argv[0].equalsIgnoreCase("f6")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f6();
	    }
      if (argv[0].equalsIgnoreCase("f7")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f7();
	    }
      if (argv[0].equalsIgnoreCase("f8")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f8();
	    }
      if (argv[0].equalsIgnoreCase("f9")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f9();
	    }
      if (argv[0].equalsIgnoreCase("f10")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f10();
	    }
      if (argv[0].equalsIgnoreCase("f11")) {
        tf_Example t = new tf_Example();
        sc = t.getExample_f11();
	    }

      // Check auswählen
      if (argv.length >= 2) {
        if (argv[1].equalsIgnoreCase("a")) { ok = mc.checkModel(sc); }
        else if (argv[1].equalsIgnoreCase("b")) { ok = mc.checkBVars(sc); }
        else if (argv[1].equalsIgnoreCase("e")) { ok = mc.checkEvents(sc); }
        else if (argv[1].equalsIgnoreCase("s")) { ok = mc.checkStates(sc); }
        else if (argv[1].equalsIgnoreCase("t")) { ok = mc.checkTransitions(sc); }
        else if (argv[1].equalsIgnoreCase("p")) { ok = mc.checkPI(sc); }
        else if (argv[1].equalsIgnoreCase("be")) { ok = (mc.checkBVars(sc) & mc.checkEvents(sc)); }
        else { ok = mc.checkModel(sc); } // kein Check ausgewählt, also alles
      }

      
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

      if (argv.length >= 3) {
        if (argv[2].equalsIgnoreCase("#")) {
          mc.outputToFile(new String("syntax.txt"));
        }
      }

    }
  }
}

