import Check.*;
import Absyn.*;

// zum Testen ins Haupdirectory der PEST kopieren

public class test {

  public static void main(String args[]) {
    Statechart sc = new Statechart(null,null,null,null);
    modelCheck mc = new modelCheck();
    boolean ok = mc.checkModel(sc);
    System.out.println("Fehler oder Warnung aufgetreten: " + ok);
    System.out.println("Error-Anzahl: " + mc.getErrorNumber());
    if (mc.getErrorNumber()>0) {
     for (int i=1;(i<=mc.getErrorNumber());i++) {
        System.out.println("Errormeldung als String:\n  "+mc.getError(i));
        System.out.println("Errormeldung als Einzelteile:\n  "
                            + "( "+ mc.getErrorCode(i)+ " / " 
                            + mc.getErrorMsg(i)+ " / " +mc.getErrorPath(i)+ " )");
      }
    }
    System.out.println("Warning-Anzahl: " + mc.getWarningNumber());
    if (mc.getWarningNumber()>0) {
      for (int i=1;(i<=mc.getWarningNumber());i++) {
        System.out.println("Warningmeldung als String:\n  "+mc.getWarning(i));
        System.out.println("Warningmeldung als Einzelteile:\n  "
                            + "( "+ mc.getWarningCode(i)+ " / " + mc.getWarningMsg(i)
                            + " / " +mc.getWarningPath(i)+ " )");
      }
    }
  }
}

