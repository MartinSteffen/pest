import absyn.*;

/**
 *  Beispiele für Statecharts mit fatalen Fehlern
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: tf_Example.java,v 1.3 1998-12-15 17:51:42 swtech00 Exp $
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

  public static Statechart getExample_f6() {

    Path sudp = new Path ("SUD", null);
    Path p1p  = new Path ("P1", sudp);
    Path p2p  = new Path ("P2", sudp);
    Path p3p  = new Path ("P3", sudp);
    Path q1p  = new Path ("Q1", p3p);
    Path q2p  = new Path ("Q2", p3p);
    Path r1p  = new Path ("R1", q2p);
    Path r2p  = new Path ("R2", q2p);
    Path s1p  = new Path ("S1", r1p);
    Path s2p  = new Path ("S2", r1p);
    Path t1p  = new Path ("T0", r2p);
    Path t2p  = new Path ("T2", r2p);


    PathList pt =
    new PathList (sudp,
      new PathList (p1p,
       new PathList (p2p,
           new PathList (q1p,
             new PathList (q2p,
               new PathList (r1p,
                   new PathList (s1p,
	                  new PathList (s2p,
                       new PathList (t1p, null)))))))));

    pt.tail.tail.tail = pt;

    return new Statechart (null, null, pt, null);
  }

  public static Statechart getExample_f7() {

    Path sudp = new Path ("SUD", null);
    Path p1p  = new Path ("P1", sudp);
    Path p2p  = new Path ("P2", sudp);
    Path p3p  = new Path ("P3", sudp);
    Path q1p  = new Path ("Q1", p3p);
    Path q2p  = new Path ("Q2", p3p);
    Path r1p  = new Path ("R1", q2p);
    Path r2p  = new Path ("R2", q2p);
    Path s1p  = new Path ("S1", r1p);
    Path s2p  = new Path ("S2", r1p);
    Path t1p  = new Path ("T0", r2p);
    Path t2p  = new Path ("T2", r2p);


    Path _a = new Path("A",null);
    Path _b = new Path("B",_a);
    Path _c = new Path("C",_b);
    _c.tail.tail=_c;

    PathList pt =
    new PathList (sudp,
      new PathList (p1p,
       new PathList (_c,
           new PathList (q1p,
             new PathList (q2p,
               new PathList (r1p,
                   new PathList (s1p,
	                  new PathList (s2p,
                       new PathList (t1p, null)))))))));

    return new Statechart (null, null, pt, null);
  }

  public static Statechart getExample_f8() {

    SEvent A = new SEvent ("A");
    SEvent B = new SEvent ("B");
    SEvent C = new SEvent ("o");
    SEvent D = new SEvent ("D");
    SEvent G = new SEvent ("G");

    SEventList elist =
      new SEventList (A,
        new SEventList (B,
            new SEventList (D,
              new SEventList (G,null))));
    elist.tail.tail=elist;


    return new Statechart (elist, null, null, null);
  }

  public static Statechart getExample_f9() {

    SEvent A = new SEvent ("A");
    SEvent B = new SEvent ("B");
    SEvent C = new SEvent ("o");
    SEvent D = new SEvent ("D");
    SEvent G = new SEvent ("G");

    SEventList elist =
      new SEventList (A,
        new SEventList (B,
            new SEventList (A,
              new SEventList (G,null))));

    return new Statechart (elist, null, null, null);
  }

  public static Statechart getExample_f10() {

    Bvar a3 = new Bvar ("A");
    Bvar a4 = new Bvar ("A");
    Bvar a5 = new Bvar ("B");
    Bvar a6 = new Bvar ("B");
    Bvar a7 = new Bvar ("F");

    BvarList blist =
      new BvarList (a3,
        new BvarList (a4,
          new BvarList (a6,
            new BvarList (a5,null))));

    blist.tail.tail=blist;

    return new Statechart (null, blist, null, null);
  }

  public static Statechart getExample_f11() {

    Bvar a3 = new Bvar ("A");
    Bvar a4 = new Bvar ("A");
    Bvar a5 = new Bvar ("B");
    Bvar a6 = new Bvar ("B");
    Bvar a7 = new Bvar ("F");

    BvarList blist =
      new BvarList (a3,
        new BvarList (a4,
          new BvarList (a6,
            new BvarList (a3,null))));

    return new Statechart (null, blist, null, null);
  }

}



