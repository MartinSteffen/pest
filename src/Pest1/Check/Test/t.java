import Check.*;
import Absyn.*;

// zum Testen ins Haupdirectory der PEST kopieren

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: t.java,v 1.2 1998-12-06 23:12:03 swtech11 Exp $
 */
public class t {

  public static void main(String argv[]) {
    Statechart sc = new Statechart(null,null,null,null);
    modelCheck mc = new modelCheck();
    boolean ok;

    // for (int i=0; i<argv.length; i++) { System.out.println(i + " " + argv[i]); }

    if (argv.length<1) { // Hilfssystem
      System.out.println();
      System.out.println("t - Das Testsystem fuer den Syntax Check");
      System.out.println("Aufruf: t op1 [op2]");
      System.out.println("  op1 enthaelt die zucheckende Statechart und muss angegeben werden:");
      System.out.println("      s  = Standard Beispiel aus der abstrakten Syntax");
      System.out.println("      d  = Beispiel 1 von Daniel fuer BVars und Events");
      System.out.println("      m  = Beispiel 1 von Magnus fuer Transitionen");
      System.out.println("      m2 = Beispiel 2 von Magnus fuer Transitionen und Connectoren");
      System.out.println("      l  = leere Statechart");
      System.out.println("  op2 ruft den entsprechenden Check auf und ist optional:");
      System.out.println("      a  = kompletter Model Check (oder keine Eingabe)");
      System.out.println("      b  = BVars checken");
      System.out.println("      e  = Events checken");
      System.out.println("      s  = States checken");
      System.out.println("      t  = Transitionen checken");
      System.out.println("      be = BVars und Events checken");
    }
    if (argv.length >= 1) {
      // Statecharts auswählen
      if (argv[0].equalsIgnoreCase("d")) {
        t_Example t = new t_Example();
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
        t_Example t = new t_Example();
        sc = t.getExample_m1();
      }
      if (argv[0].equalsIgnoreCase("m2")) {
        t_Example t = new t_Example();
        sc = t.getExample_m2();
      }
      // Check auswählen
      if (argv.length >= 2) {
        if (argv[1].equalsIgnoreCase("a")) { ok = mc.checkModel(sc); }
        if (argv[1].equalsIgnoreCase("b")) { ok = mc.checkBVars(sc); }
        if (argv[1].equalsIgnoreCase("e")) { ok = mc.checkEvents(sc); }
        if (argv[1].equalsIgnoreCase("s")) { ok = mc.checkStates(sc); }
        if (argv[1].equalsIgnoreCase("t")) { ok = mc.checkTransitions(sc); }
        if (argv[1].equalsIgnoreCase("be")) { ok = (mc.checkBVars(sc) & mc.checkEvents(sc)); }
      }
      else { ok = mc.checkModel(sc); } // kein Check ausgewählt, also alles

      System.out.println("Fehlermeldungen ( " + mc.getErrorNumber() +  " Stueck ):");
      if (mc.getErrorNumber()>0) {
        for (int i=1;(i<=mc.getErrorNumber());i++) {
          System.out.println("* "+mc.getErrorCode(i)+ ": " + mc.getErrorMsg(i)+ "\n  Ort: " +mc.getErrorPath(i)); } }
      System.out.println("Warnmeldungen ( " + mc.getWarningNumber() +  " Stueck ):");
      if (mc.getWarningNumber()>0) {
        for (int i=1;(i<=mc.getWarningNumber());i++) {
          System.out.println("* "+mc.getWarningCode(i)+ ": " + mc.getWarningMsg(i)+ "\n  Ort: " +mc.getWarningPath(i)); } }
    }
  }
}

