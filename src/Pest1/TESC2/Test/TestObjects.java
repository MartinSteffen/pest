/**
 * TestObjects.
 *
 * Created: Fri Jan 01 1999, 02:34:05
 *
 * @author Developed by Eike Schulz for swtech14.
 * @version $Id: TestObjects.java,v 1.1 1999-01-08 23:16:35 swtech14 Exp $
 *
 *
 * Diese Klasse erzeugt zwei Testobjekte, die zum Testen der mit dem Graph-
 * plazierungsalgorithmus verbundenen Methoden und Klassen dienen.
 */

package tesc2.Test;


import absyn.*;

class TestObjects {

  private static Statename P12name = new Statename
  ("P12 hat eine lange Bezeichnung.");

  private static Conname c1name = new Conname ("Connector 01");


  /**
   * Erzeuge Objekt 1.
   */

  static Statechart getStatechart1() {

    Or_State P5 = new Or_State
      (new Statename ("P5"),
       new StateList
       (new Basic_State (new Statename ("P6")), new StateList
	(new Basic_State (new Statename ("P7")), null)),
       new TrList
       (new Tr (new Statename ("P6"), new Statename ("P7"),
		new TLabel (null, null)), null),
       null,
       null);

    Or_State P11 = new Or_State
      (new Statename ("P11"),
       new StateList
       (new Basic_State (new Statename ("P8")), new StateList
	(new Basic_State (new Statename ("P9")), new StateList
	 (new Basic_State (new Statename ("P10")), null))),
       new TrList
       (new Tr (new Statename ("P8"), new Statename ("P10"), new TLabel (null, null)), new TrList
	(new Tr (new Statename ("P9"), new Statename ("P8"), new TLabel (null, null)), new TrList
	 (new Tr (new Statename ("P8"), new Statename ("P9"), new TLabel (null, null)), null))),
       null,
       null);

    And_State P4 = new And_State
      (new Statename ("P4"),
       new StateList (P5, new StateList (P11, null)));

    Or_State P3 = new Or_State
      (new Statename ("P3"),
       new StateList (P4, null),
       null,
       null,
       null);

    TrList tL = new TrList 
      (new Tr (new Conname ("c2"), new Statename ("P3"), new TLabel (null, null)), new TrList
       (new Tr (new Statename ("P19"), c1name, new TLabel (null, null)), new TrList
	(new Tr (c1name, new Statename ("P20"), new TLabel (null, null)), new TrList
	 (new Tr (new Statename ("P21"), c1name, new TLabel (null, null)), new TrList
	  (new Tr (c1name,  new Statename ("P3"), new TLabel (null, null)), new TrList
	   (new Tr (c1name, new Conname ("c2"), new TLabel (null, null)), null))))));

    Or_State P2 = new Or_State
      (new Statename ("P2"),
       new StateList
       (new Basic_State (new Statename ("P19")), new StateList
	(new Basic_State (new Statename ("P20")), new StateList
	 (new Basic_State (new Statename ("P21")), new StateList
	  (P3, null)))),
       tL,
       null,
       new ConnectorList
       (new Connector (c1name), new ConnectorList
	(new Connector (new Conname ("c2")), null)));

    Or_State P12 = new Or_State
      (P12name,
       new StateList
       (new Basic_State (new Statename ("P13")), new StateList
	(new Basic_State (new Statename ("P14")), null)),
       new TrList
       (new Tr (new Statename ("P13"), new Statename ("P14"), new TLabel (null, null)), null),
       null,
       null);

    Or_State P1 = new Or_State
      (new Statename ("P1"),
       new StateList
       (new Basic_State (new Statename ("P15")), new StateList
	(new Basic_State (new Statename ("P16")), new StateList
	 (new Basic_State (new Statename ("P17")), new StateList
	  (new Basic_State (new Statename ("P18")), new StateList
	   (P2, new StateList (P12, null)))))),
       new TrList
       (new Tr (new Statename ("P15"), P12name, new TLabel (null, null)), new TrList
	(new Tr (P12name, new Statename ("P2"), new TLabel (null, null)), new TrList
	 (new Tr (new Statename ("P2"), new Statename ("P17"), new TLabel (null, null)),
	  new TrList
	  (new Tr (new Statename ("P17"), new Statename ("P18"), new TLabel (null, null)),
	   new TrList
	   (new Tr (new Statename ("P18"), new Statename ("P16"), new TLabel (null, null)),
	    new TrList
	    (new Tr (new Statename ("P16"), new Statename ("P17"), new TLabel (null, null)),
	     null)))))),
       null,
       null);

    Statechart sc = new Statechart (null, null, null, P1);
    return sc;
  } // method getStatechart1()



  /**
   * Erzeuge Objekt 2.
   */

  static Statechart getStatechart2() {

    Or_State P5 = new Or_State
      (new Statename ("P5"),
       new StateList (new Basic_State (new Statename ("P6")), null),
       null,
       null,
       null);

    Or_State P4 = new Or_State
      (new Statename ("P4"), new StateList (P5, null), null, null, null);

    Or_State P8 = new Or_State
      (new Statename ("P8"),
       new StateList (new Basic_State (new Statename ("P9")), null),
       null,
       null,
       null);

    Or_State P7 = new Or_State
      (new Statename ("P7"), new StateList (P8, null), null, null, null);

    StateList sL = new StateList (P4, null);
    sL = new StateList (P7, sL);

    Or_State P3 = new Or_State (new Statename ("P3"), sL ,null, null, null);

    Or_State P2 = new Or_State
      (new Statename ("P2"), new StateList (P3, null), null, null, null);

    Or_State P12 = new Or_State
      (new Statename ("P12"),
       new StateList (new Basic_State (new Statename ("P13")), null),
       null,
       null,
       null);

    Or_State P11 = new Or_State
      (new Statename ("P11"), new StateList (P12, null), null, null, null);

    Or_State P10 = new Or_State
      (new Statename ("P10"), new StateList (P11, null), null, null, null);

    Or_State P1 = new Or_State
      (new Statename ("P1"),
       new StateList (P2, new StateList (P10, null)), null, null, null);

    Statechart sc = new Statechart (null, null, null, P1);
    return sc;
  } // method getStatechart2()

} // class TestObjekts
