//===============================================================
//
//               file     :    GraphAlg.java
//               group    :    4
//               account  :    swtech14
//               member   :    Eike Schulz & Martin Poerksen
//               date     :    30.11.98
//
//===============================================================

package TESC2;


import Absyn.*;

public class GraphAlg {

  // Konstanten fuer Algorithmus-Arten.
  public static final int ALGORITHM_0 = 0;
  public static final int ALGORITHM_1 = 1;

  // Ergebniswert fuer Graphplazierungsalgorithmus.
  private int errorcode;

  private Statechart sChart;


  // Konstruktor GraphAlg.
  // Uebergabeobjekt:
  // - Statechart-Objekt

  public GraphAlg (Statechart s) {
    sChart = s;
    errorcode = 0;
  } // constructor GraphAlg


  // Starte Graphplazierungsalgorithmus.
  // Uebergabewert:
  // - Integer-Wert fuer Algorithmus-Art (siehe Konstanten oben!)

  public void start (int algorithm) {
    if ((algorithm != ALGORITHM_0) && (algorithm != ALGORITHM_1))
      algorithm = ALGORITHM_0;

    // (Aufruf des entsprechenden Algorithmus erfolgt hier...)

  } // method start


  // Teste, ob Fehler aufgetreten ist.
  // Rueckgabewert:
  // - true, falls Fehler aufgetreten
  // - false, falls kein Fehler aufgetreten

  public boolean errorOccured () {
    return (errorcode != 0);
  } // method errorOccured


  // Gib Fehlercode-Interpretation zurueck.
  // Rueckgabewert:
  // - String mit verbalisierter Fehlermeldung

  public String getErrorMsg () {
    switch (errorcode) {
    case 0 : return "Graphplazierungsalgorithmus erfolgreich " +
                    "ausgefuehrt.";
    case 1 : return "FEHLER: Algorithmus-Art nicht verfuegbar.";

      // (...)

    default : return "FEHLER: Unbekannter Fehler aufgetreten";
    } // switch

  } // method getErrorMsg

} // class GraphAlg
