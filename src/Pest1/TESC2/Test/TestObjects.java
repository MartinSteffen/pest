package tesc2.Test;


import absyn.*;

class TestObjects {

  private static Statename P12name = new Statename
  ("P12 hat eine lange Bezeichnung.");

  private static Conname c1name = new Conname ("Connector 01");


  static Statechart getStatechart1() {

    Or_State P5 = new Or_State
      (new Statename ("P5"),
       new StateList
       (new Basic_State (new Statename ("P6")), new StateList
	(new Basic_State (new Statename ("P7")), null)),
       new TrList
       (new Tr (new Statename ("P6"), new Statename ("P7"),
		new TLabel (null, null, null, null, "Label von P6 nach P7")), null),
       null,
       null);

    Or_State P11 = new Or_State
      (new Statename ("P11"),
       new StateList
       (new Basic_State (new Statename ("P8")), new StateList
	(new Basic_State (new Statename ("P9")), new StateList
	 (new Basic_State (new Statename ("P10")), null))),
       new TrList
       (new Tr (new Statename ("P8"), new Statename ("P10"),
		new TLabel (null, null, null, null, "Label von P8 nach P10")), new TrList
	(new Tr (new Statename ("P9"), new Statename ("P8"),
		 new TLabel (null, null, null, null, "Label von P9 nach P8")), new TrList
	 (new Tr (new Statename ("P8"), new Statename ("P9"),
		  new TLabel (null, null, null, null, "Label von P8 nach P9")), null))),
       null,
       null);

    And_State P4 = new And_State
      (new Statename ("P4"),
       new StateList (P5, new StateList (P11, null)));

    Or_State P3 = new Or_State
      (new Statename ("P3"),
       new StateList (P4, null), null, null, null);

    TrList tL = new TrList 
      (new Tr (new Conname ("c2"), new Statename ("P3"),
	       new TLabel (null, null, null, null, "Label von Connector c2 nach P3")), new TrList
       (new Tr (new Statename ("P19"), c1name,
		new TLabel (null, null, null, null, "Label von P3 nach P19")), new TrList
	(new Tr (c1name, new Statename ("P20"),
		 new TLabel (null, null, null, null, "Label von Connector c1 nach P20")), new TrList
	 (new Tr (new Statename ("P21"), c1name,
		  new TLabel (null, null, null, null, "Label von P21 nach Connector c1")), new TrList
	  (new Tr (c1name,  new Statename ("P3"),
		   new TLabel (null, null, null, null, "Label von Connector c1 nach P3")), new TrList
	   (new Tr (c1name, new Conname ("c2"),
		    new TLabel (null, null, null, null, "Label von Connector c1 nach Connector c2")), null))))));

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
       (new Tr (new Statename ("P13"), new Statename ("P14"),
		new TLabel (null, null, null, null, "Label von P13 nach P14")), null),
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
       (new Tr (new Statename ("P15"), P12name,
		new TLabel (null, null, null, null, "Label von P15 nach P12")), new TrList
	(new Tr (P12name, new Statename ("P2"),
		 new TLabel (null, null, null, null, "Label von P12 nach P2")), new TrList
	 (new Tr (new Statename ("P2"), new Statename ("P17"),
		  new TLabel (null, null, null, null, "Label von P2 nach P17")), new TrList
	  (new Tr (new Statename ("P17"), new Statename ("P18"),
		   new TLabel (null, null, null, null, "Label von P17 nach P18")), new TrList
	   (new Tr (new Statename ("P18"), new Statename ("P16"),
		    new TLabel (null, null, null, null, "Label von P18 nach P16")), new TrList
	    (new Tr (new Statename ("P16"), new Statename ("P17"),
		     new TLabel (null, null, null, null, "Label von P16 nach P17")), null)))))),
       null,
       null);

    Statechart sc = new Statechart (null, null, null, P1);
    return sc;
  } // method getStatechart1()



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



  static Statechart getStatechart3() {

    Or_State roll = new Or_State
      (new Statename ("ROLL"),
       new StateList
       (new Basic_State (new Statename ("NEG")), new StateList
	(new Basic_State (new Statename ("ZERO")), new StateList
	 (new Basic_State (new Statename ("POS")), null))),
       new TrList
       (new Tr (new Statename ("ZERO"), new Statename ("NEG"),
		new TLabel (null, null, null, null, "Label von ZERO nach NEG")), new TrList
	(new Tr (new Statename ("ZERO"), new Statename ("POS"),
		 new TLabel (null, null, null, null, "Label von ZERO nach POS")), null)),
       null,
       null);

    Or_State pitch = new Or_State
      (new Statename ("PITCH"),
       new StateList
       (new Basic_State (new Statename ("NEG")), new StateList
	(new Basic_State (new Statename ("ZERO")), new StateList
	 (new Basic_State (new Statename ("POS")), null))),
       new TrList
       (new Tr (new Statename ("ZERO"), new Statename ("NEG"),
		new TLabel (null, null, null, null, "Label von ZERO nach NEG")), new TrList
	(new Tr (new Statename ("ZERO"), new Statename ("POS"),
		 new TLabel (null, null, null, null, "Label von ZERO nach POS")), null)),
       null,
       null);

    Or_State yaw = new Or_State
      (new Statename ("YAW"),
       new StateList
       (new Basic_State (new Statename ("NEG")), new StateList
	(new Basic_State (new Statename ("ZERO")), new StateList
	 (new Basic_State (new Statename ("POS")), null))),
       new TrList
       (new Tr (new Statename ("ZERO"), new Statename ("NEG"),
		new TLabel (null, null, null, null, "Label von ZERO nach NEG")), new TrList
	(new Tr (new Statename ("ZERO"), new Statename ("POS"),
		 new TLabel (null, null, null, null, "Label von ZERO nach POS")), null)),
       null,
       null);

    And_State aah_cmd = new And_State
      (new Statename ("AAH_CMD"),
       new StateList (roll, new StateList (pitch, new StateList (yaw, null))));

    Or_State a = new Or_State
      (new Statename ("A"),
       new StateList (aah_cmd, null),
       new TrList
       (new Tr (new Statename ("AAH_CMD"), new Statename ("AAH_CMD"),
		new TLabel (null, null, null, null, "Label von AAH_CMD nach AAH_CMD")), null),
       null,
       null);

    Or_State horiz = new Or_State
      (new Statename ("HORIZ"),
       new StateList
       (new Basic_State (new Statename ("NEG")), new StateList
	(new Basic_State (new Statename ("ZERO")), new StateList
	 (new Basic_State (new Statename ("POS")), null))),
       new TrList
       (new Tr (new Statename ("ZERO"), new Statename ("NEG"),
		new TLabel (null, null, null, null, "Label von ZERO nach NEG")), new TrList
	(new Tr (new Statename ("ZERO"), new Statename ("POS"),
		 new TLabel (null, null, null, null, "Label von ZERO nach POS")), null)),
       null,
       null);

    Or_State vert = new Or_State
      (new Statename ("VERT"),
       new StateList
       (new Basic_State (new Statename ("NEG")), new StateList
	(new Basic_State (new Statename ("ZERO")), new StateList
	 (new Basic_State (new Statename ("POS")), null))),
       new TrList
       (new Tr (new Statename ("ZERO"), new Statename ("NEG"),
		new TLabel (null, null, null, null, "Label von ZERO nach NEG")), new TrList
	(new Tr (new Statename ("ZERO"), new Statename ("POS"),
		 new TLabel (null, null, null, null, "Label von ZERO nach POS")), null)),
       null,
       null);

    Or_State trans = new Or_State
      (new Statename ("TRANS"),
       new StateList
       (new Basic_State (new Statename ("NEG")), new StateList
	(new Basic_State (new Statename ("ZERO")), new StateList
	 (new Basic_State (new Statename ("POS")), null))),
       new TrList
       (new Tr (new Statename ("ZERO"), new Statename ("NEG"),
		new TLabel (null, null, null, null, "Label von ZERO nach NEG")), new TrList
	(new Tr (new Statename ("ZERO"), new Statename ("POS"),
		 new TLabel (null, null, null, null, "Label von ZERO nach POS")), null)),
       null,
       null);

    Or_State twist = new Or_State
      (new Statename ("TWIST"),
       new StateList
       (new Basic_State (new Statename ("NEG")), new StateList
	(new Basic_State (new Statename ("ZERO")), new StateList
	 (new Basic_State (new Statename ("POS")), null))),
       new TrList
       (new Tr (new Statename ("ZERO"), new Statename ("NEG"),
		new TLabel (null, null, null, null, "Label von ZERO nach NEG")), new TrList
	(new Tr (new Statename ("ZERO"), new Statename ("POS"),
		 new TLabel (null, null, null, null, "Label von ZERO nach POS")), null)),
       null,
       null);

    And_State raw_cmd = new And_State
      (new Statename ("RAW_CMD"),
       new StateList (horiz, new StateList (vert, new StateList (trans, new StateList (twist, null)))));

    Or_State c = new Or_State
      (new Statename ("C"),
       new StateList (raw_cmd, null),
       new TrList
       (new Tr (new Statename ("RAW_CMD"), new Statename ("RAW_CMD"),
		new TLabel (null, null, null, null, "Label von RAW_CMD nach RAW_CMD")), null),
       null,
       null);

    Or_State safer = new Or_State
      (new Statename ("SAFER"),
       new StateList (a, new StateList (c, null)),
       null,
       null,
       null);

    Statechart sc = new Statechart (null, null, null, safer);
    return sc;
  } // method getStatechart3()



  static Statechart getStatechart4() {

    Or_State P1 = new Or_State
      (new Statename ("P1"),
       new StateList
       (new Basic_State (new Statename ("P4")), new StateList
	(new Basic_State (new Statename ("P5")), null)),
       null,
       null,
       null);

    Or_State P2 = new Or_State
      (new Statename ("P2"),
       new StateList
       (new Basic_State (new Statename ("P6")), new StateList
	(new Basic_State (new Statename ("P7")), new StateList
	 (new Basic_State (new Statename ("P8")), null))),
       null,
       null,
       null);

    Or_State P3 = new Or_State
      (new Statename ("P3"),
       new StateList
       (new Basic_State (new Statename ("P9")), new StateList
	(new Basic_State (new Statename ("P10")), new StateList
	 (new Basic_State (new Statename ("P10")), new StateList
	  (new Basic_State (new Statename ("P11")), null)))),
       null,
       null,
       null);

    And_State P13 = new And_State
      (new Statename ("P13"),
       new StateList
       (new Basic_State (new Statename ("P14")), new StateList
	(new Basic_State (new Statename ("P15")), null)));

    And_State P12 = new And_State
      (new Statename ("P12"),
       new StateList (P1, new StateList (P2, new StateList (P3, new StateList (P13, null)))));

    Or_State P0 = new Or_State
      (new Statename ("P0"),
       new StateList
       (new Basic_State (new Statename ("P16")), new StateList
	(new Basic_State (new Statename ("P17")), new StateList
	 (new Basic_State (new Statename ("P18")), new StateList
	  (P12, null)))),
       null,
       null,
       null);

    Or_State start = new Or_State
      (new Statename ("start"),
       new StateList
       //       (new Basic_State (new Statename ("P19")), new StateList (P0, null))
       (P0, null),
       null,
       null,
       null);

    Statechart sc = new Statechart (null, null, null, start);
    return sc;
  } // method getStatechart4()



  static Statechart getStatechart5() {

    Or_State state1 = new Or_State
      (new Statename ("STATE1"),
       new StateList (new Basic_State (new Statename ("ROBOT")), null),
       null,
       null,
       null);

    Or_State state2 = new Or_State
      (new Statename ("STATE2"),
       new StateList (new Basic_State (new Statename ("FEED_BELT")), null),
       null,
       null,
       null);

    Or_State state3 = new Or_State
      (new Statename ("STATE3"),
       new StateList (new Basic_State (new Statename ("TABLE")), null),
       null,
       null,
       null);

     Or_State state4 = new Or_State
      (new Statename ("STATE4"),
       new StateList (new Basic_State (new Statename ("USER")), null),
       null,
       null,
       null);

     Or_State state5 = new Or_State
       (new Statename ("STATE5"),
	new StateList (new Basic_State (new Statename ("DEPOSIT_BELT")), null),
	null,
	null,
	null);

     Or_State state6 = new Or_State
      (new Statename ("STATE6"),
       new StateList (new Basic_State (new Statename ("PRESS")), null),
       null,
       null,
       null);

     Or_State state7 = new Or_State
      (new Statename ("STATE7"),
       new StateList (new Basic_State (new Statename ("CRANE")), null),
       null,
       null,
       null);

    And_State prod_cell = new And_State
      (new Statename ("PROD_CELL"),
       new StateList
       (state1, new StateList
	(state2, new StateList
	(state3, new StateList
	(state4, new StateList
	(state5, new StateList
	(state6, new StateList
	(state7, null))))))));

    Or_State sprod_cell = new Or_State
      (new Statename ("SPROD_CELL"),
       new StateList (prod_cell, null),
       null,
       null,
       null);

    Statechart sc = new Statechart (null, null, null, sprod_cell);
    return sc;
  } // method getStatechart5()

} // class TestObjekts
