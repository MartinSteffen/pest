import Absyn.*;

/**
 *  Beispiele für Statecharts mit fatalen Fehlern
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: tf_Example.java,v 1.1 1998-12-10 22:23:03 swtech11 Exp $
 */
public class tf_Example {

  public static Statechart getExample_f1() {

    Basic_State P1 = new Basic_State (new Statename("P1"));
    Basic_State Z1 = new Basic_State (new Statename("Z1"));
    Basic_State Z2 = new Basic_State (new Statename("Z2"));

    Or_State d1 = new Or_State (
			       new Statename ("d1"),
             new StateList (Z2, null),
             null,
             null,
             null);

    Or_State P2 = new Or_State (
			       new Statename ("P2"),
			       new StateList (Z1,new StateList (d1,null)),
			       new TrList (new Tr (new Statename ("Z1"), new Conname("BLABLA-CON"), new TLabel (null,null)),
					     new TrList (new Tr (new Statename ("Z2"), new Statename ("Z1"), new TLabel (null,null)),
						     new TrList (new Tr (new Conname("SUD-1-CON"), new Statename ("P1"), new TLabel (null,null)),
                   new TrList (new Tr (new Statename ("Z2"), new UNDEFINED (), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("Z1"), new Conname("P2-CON"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("Z2"), null),
			       new ConnectorList (new Connector(new Conname("P2-CON")), null));

    Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,null)),
			       new TrList (new Tr (new Conname("sud-3-CON"), new Statename ("P2"), new TLabel (null,null)),
					     new TrList (new Tr (new Statename ("P1"), new Conname("P2-CON"), new TLabel (null,null)),
						     new TrList (new Tr (new Statename ("P3"), new Statename ("P1"),	new TLabel (null,null)),
                   new TrList (new Tr (new Conname("SUD-1-CON"), new Conname("sud-2-CON"), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("P2"), new Statename ("P1"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("P1"), null),
			       new ConnectorList (new Connector(new Conname("SUD-1-CON")),
               new ConnectorList(new Connector(new Conname("sud-2-CON")), null))    );

    d1.substates.head=P2;

    return new Statechart (null, null, null, SUD);
  }

  public static Statechart getExample_f2() {

    Basic_State P1 = new Basic_State (new Statename("P1"));
    Basic_State Z1 = new Basic_State (new Statename("Z1"));
    Basic_State Z2 = new Basic_State (new Statename("Z2"));


    Or_State P2 = new Or_State (
			       new Statename ("P2"),
			       new StateList (Z1,new StateList (P1,null)),
			       new TrList (new Tr (new Statename ("Z1"), new Conname("BLABLA-CON"), new TLabel (null,null)),
					     new TrList (new Tr (new Statename ("Z2"), new Statename ("Z1"), new TLabel (null,null)),
						     new TrList (new Tr (new Conname("SUD-1-CON"), new Statename ("P1"), new TLabel (null,null)),
                   new TrList (new Tr (new Statename ("Z2"), new UNDEFINED (), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("Z1"), new Conname("P2-CON"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("Z2"), null),
			       new ConnectorList (new Connector(new Conname("P2-CON")), null));

    Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,null)),
			       new TrList (new Tr (new Conname("sud-3-CON"), new Statename ("P2"), new TLabel (null,null)),
					     new TrList (new Tr (new Statename ("P1"), new Conname("P2-CON"), new TLabel (null,null)),
						     new TrList (new Tr (new Statename ("P3"), new Statename ("P1"),	new TLabel (null,null)),
                   new TrList (new Tr (new Conname("SUD-1-CON"), new Conname("sud-2-CON"), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("P2"), new Statename ("P1"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("P1"), null),
			       new ConnectorList (new Connector(new Conname("SUD-1-CON")),
               new ConnectorList(new Connector(new Conname("sud-2-CON")), null))    );


    return new Statechart (null, null, null, SUD);
  }


  public static Statechart getExample_f3() {


  Basic_State S1 = new Basic_State (new Statename("S1"));
  Basic_State S2 = new Basic_State (new Statename("S2"));
  Basic_State T1 = new Basic_State (new Statename("Tp"));
  Basic_State T2 = new Basic_State (new Statename("T2"));

  Basic_State Q1 = new Basic_State (new Statename("Q1"));
    
  And_State Q2 = new And_State (
				new Statename ("Q2"),null);
  
  Basic_State P1 = new Basic_State (new Statename("P1"));

  Basic_State P2 = new Basic_State (new Statename("P2"));

  Or_State P3 = new Or_State (
			      new Statename ("P3"),
			      new StateList (Q1,new StateList (Q2,null)),
			      null,
			      new StatenameList( new Statename("Q1"), null),	
			      null);
  
  Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (Q2,new StateList (P2,new StateList (P3,null))),
			       null,
			       new StatenameList (new Statename("P1"), null),
			       null);  
  
  return new Statechart (null, null, null, SUD);
  }


  public static Statechart getExample_f4() {

    Basic_State P1 = new Basic_State (new Statename("P1"));
    Basic_State Z1 = new Basic_State (new Statename("Z1"));
    Basic_State Z2 = new Basic_State (new Statename("Z2"));

    Tr trans1 = new Tr (new Statename ("Z2"), new Statename ("Z1"), new TLabel (null,null));

    Or_State P2 = new Or_State (
			       new Statename ("P2"),
			       new StateList (Z1,new StateList (Z2,null)),
			       new TrList (new Tr (new Statename ("Z1"), new Conname("BLABLA-CON"), new TLabel (null,null)),
					     new TrList (trans1,
						     new TrList (new Tr (new Conname("SUD-1-CON"), new Statename ("P1"), new TLabel (null,null)),
                   new TrList (new Tr (new Statename ("Z2"), new UNDEFINED (), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("Z1"), new Conname("P2-CON"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("Z2"), null),
			       new ConnectorList (new Connector(new Conname("P2-CON")), null));

    Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,null)),
			       new TrList (new Tr (new Conname("sud-3-CON"), new Statename ("P2"), new TLabel (null,null)),
					     new TrList (trans1,
						     new TrList (new Tr (new Statename ("P3"), new Statename ("P1"),	new TLabel (null,null)),
                   new TrList (new Tr (new Conname("SUD-1-CON"), new Conname("sud-2-CON"), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("P2"), new Statename ("P1"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("P1"), null),
			       new ConnectorList (new Connector(new Conname("SUD-1-CON")),
               new ConnectorList(new Connector(new Conname("sud-2-CON")), null))    );

    return new Statechart (null, null, null, SUD);
  }

  public static Statechart getExample_f5() {

    Basic_State P1 = new Basic_State (new Statename("P1"));
    Basic_State Z1 = new Basic_State (new Statename("Z1"));
    Basic_State Z2 = new Basic_State (new Statename("Z2"));

    TrList  tr1 =  new TrList (new Tr (new Statename ("Z1"), new Conname("BLABLA-CON"), new TLabel (null,null)),
					           new TrList (new Tr (new Statename ("Z2"), new Statename ("Z1"), new TLabel (null,null)),
						           new TrList (new Tr (new Conname("SUD-1-CON"), new Statename ("P1"), new TLabel (null,null)),
                         new TrList (new Tr (new Statename ("Z2"), new UNDEFINED (), new TLabel (null,null)),
                           new TrList (new Tr (new Statename ("Z1"), new Conname("P2-CON"), new TLabel (null,null)),
                   null)))));

    Or_State P2 = new Or_State (
			       new Statename ("P2"),
			       new StateList (Z1,new StateList (Z2,null)),
			       tr1,
			       new StatenameList (new Statename("Z2"), null),
			       new ConnectorList (new Connector(new Conname("P2-CON")), null));

    Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,null)),
			       tr1,
			       new StatenameList (new Statename("P1"), null),
			       new ConnectorList (new Connector(new Conname("SUD-1-CON")),
               new ConnectorList(new Connector(new Conname("sud-2-CON")), null))    );

    return new Statechart (null, null, null, SUD);
  }


}



