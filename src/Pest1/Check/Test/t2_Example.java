
import absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: t2_Example.java,v 1.18 1999-02-08 23:38:09 swtech11 Exp $
 */
public class t2_Example {

/**
 * Statechart Daniel 1
 */
  public static Statechart getExample_d() {
    

Bvar a3 = new Bvar ("A");
 Bvar a4 = new Bvar ("A");
 Bvar a5 = new Bvar ("B");
 Bvar a6 = new Bvar ("B");
 Bvar a7 = new Bvar ("F");
   
 /*BvarList blist=new BvarList(a3,null);
   blist=new BvarList(a4,blist);*/

     BvarList blist =
      new BvarList (a3,
 new BvarList (a4,
 new BvarList (a6,
 new BvarList (a5,null))));

    SEvent A = new SEvent ("A");
    SEvent B = new SEvent ("B");
    SEvent C = new SEvent ("o");
    SEvent D = new SEvent ("D");
    SEvent G = new SEvent ("G");

    SEventList statelist =
      new SEventList (A,
        new SEventList (B,
        
            new SEventList (D,
              new SEventList (G,null))));

    Path sudp = new Path ("SUD", null);
    Path p1p  = sudp.append("P1");
    Path p2p  = sudp.append("P2");
    Path p3p  = sudp.append("P3");
    Path q1p  = p3p.append("Q1");
    Path q2p  = p3p.append("Q2");
    Path r1p = q2p.append("R1");
    Path r2p = q2p.append("R2");
    Path s1p = r1p.append("S1");
    Path s2p = r1p.append("S2");
    Path t1p = r2p.append("T1");
    Path t2p = r2p.append("T2");


    PathList pathlist =
    new PathList (sudp,
      new PathList (p1p,
       new PathList (p2p,
         new PathList (p3p,
           new PathList (q1p,
             new PathList (q2p,
               new PathList (r1p,
                 new PathList (r2p,
                   new PathList (s1p,
	             new PathList (s2p,
                       new PathList (t1p,
                         new PathList (t2p,null))))))))))));

  Basic_State S1 = new Basic_State (new Statename("S1"));
  Basic_State S2 = new Basic_State (new Statename("S2"));
  Basic_State T1 = new Basic_State (new Statename("Tp"));
  Basic_State T2 = new Basic_State (new Statename("T2"));
  
  Tr tr1 = new Tr (new Statename ("S1"), 
		   new Statename ("S2"),
		   new TLabel (new GuardEvent(C),null));

  Tr tr2 = new Tr (new Statename ("S9"), 
		   new Statename ("S1"),
		   new TLabel (new GuardCompg (new Compguard (Compguard.AND,
							  new GuardEvent(B),
							  new GuardCompp (new Comppath (Comppath.IN,(new Path("SUD", null)).append("P4"))))),
					   new ActionEmpty (new Dummy())));


  Or_State R1 = new Or_State (new Statename ("R1"),
			      new StateList (S1,new StateList (S2,null)),
			      new TrList (tr1, new TrList (tr2, null)),
			      new StatenameList (new Statename("T1"), null),	
			      null);
  
  Or_State R2 = new Or_State (
			      new Statename ("R2"),
			      new StateList (T1,new StateList (T2,null)),
			      new TrList  (new Tr (new Statename ("T1"), 
						   new Statename ("T2"),
						   new TLabel (new GuardEvent(D),null)),
					   new TrList (new Tr (new Statename ("T2"), 
							       new Statename ("T1"),
							       new TLabel (new GuardEvent(B),null)),null)),
			      new StatenameList (new Statename("T1"), null),	
			      null);
  
  Basic_State Q1 = new Basic_State (new Statename("Q1"));
    
  And_State Q2 = new And_State (
				new Statename ("Q2"),null);
  
  Basic_State P1 = new Basic_State (new Statename("P1"));

  Basic_State P2 = new Basic_State (new Statename("P2"));

  Or_State P3 = new Or_State (
			      new Statename ("P3"),
			      new StateList (Q1,new StateList (Q2,null)),
			      new TrList (new Tr (new Statename ("Q1"), 
						  new Statename ("Q2"),
						  new TLabel (new GuardEvent(A),new ActionEvt (C))),
					  null),
			      new StatenameList( new Statename("Q1"), null),	
			      null);
  
  Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,new StateList (P3,null))),
			       new TrList (new Tr (new Statename ("P1"), 
						   new Statename ("P2"), 
						   new TLabel (new GuardEvent(C),null)),
					   new TrList (new Tr (new Statename ("P1"), 
							       new Statename ("P3"), 
							       new TLabel (new GuardEvent(A),null)),
						       new TrList (new Tr (new Statename ("P3"), 
									   new Statename ("P1"), 
									   new TLabel (null,null)),null))),
			       new StatenameList (new Statename("P1"), null),
			       null);  
  
  return new Statechart (statelist,
			 blist,
			 pathlist,
			 SUD);
  }


/**
 * Statechart Magnus 1
 */
public static Statechart getExample_m() {
    
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
    Path t1p  = new Path ("T1", r2p);
    Path t2p  = new Path ("T2", r2p);
    PathList pathlist =
    new PathList (sudp,
      new PathList (p1p,
       new PathList (p2p,
         new PathList (p3p,
           new PathList (q1p,
             new PathList (q2p,
               new PathList (r1p,
                 new PathList (r2p,
                   new PathList (s1p,
	             new PathList (s2p,
                       new PathList (t1p,
                         new PathList (t2p,null))))))))))));


    Basic_State P1 = new Basic_State (new Statename("P1"));
    Basic_State Z1 = new Basic_State (new Statename("Z1"));
    Basic_State Z2 = new Basic_State (new Statename("Z2"));

    Or_State P2 = new Or_State (
			       new Statename ("P2"),
			       new StateList (Z1,new StateList (Z2,null)),
			       new TrList (new Tr (new Statename ("Z1"), new Statename ("Z5"), new TLabel (null,null)),
					     new TrList (new Tr (new Statename ("Z2"), new Statename ("Z1"), new TLabel (null,null)),
						     new TrList (new Tr (new Statename ("Z1"), new Statename ("P1"), new TLabel (null,null)),
                   new TrList (new Tr (new Statename ("Z2"), new UNDEFINED (), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("Z1"), new Statename ("Z2"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("Z2"), null),
			       null);

    Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,null)),
			       new TrList (new Tr (new Statename ("Z1"), new Statename ("P2"), new TLabel (null,null)),
					     new TrList (new Tr (new Statename ("P1"), new UNDEFINED (), new TLabel (null,null)),
						     new TrList (new Tr (new Statename ("P3"), new Statename ("P1"),	new TLabel (null,null)),
                   new TrList (new Tr (new UNDEFINED (), new UNDEFINED (), new TLabel (null,null)),
                     new TrList (new Tr (new Statename ("P2"), new Statename ("P1"), new TLabel (null,null)),
              null))))),
			       new StatenameList (new Statename("P1"), null),
			       null);

    return new Statechart (null, null, pathlist, SUD);
  }

/**
 * Statechart Magnus 2
 */
  public static Statechart getExample_m2() {

    Basic_State P1 = new Basic_State (new Statename("P1"));
    Basic_State Z1 = new Basic_State (new Statename("Z1"));
    Basic_State Z2 = new Basic_State (new Statename("Z2"));

    Or_State P2 = new Or_State (
			       new Statename ("P2"),
			       new StateList (Z1,new StateList (Z2,null)),
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
                       new TrList (new Tr (new Conname("1-CON"), new Conname("1-CON"), new TLabel (null,null)),
              null)))))),
			       new StatenameList (new Statename("P1"), null),
			       new ConnectorList (new Connector(new Conname("SUD-1-CON")),
               new ConnectorList(new Connector(new Conname("sud-2-CON")), null))    );

    return new Statechart (null, null, null, SUD);
  }

/**
 * Statechart Daniel 2
 */
  public static Statechart getExample_d2() {

 Bvar a3 = new Bvar ("");

 /*BvarList blist=new BvarList(a3,null);
   blist=new BvarList(a4,blist);*/

     BvarList blist =
      new BvarList (a3,null);

    SEvent A = new SEvent ("A");
    SEvent B = new SEvent ("B");
    SEvent C = new SEvent ("C");
    SEvent D = new SEvent ("");
    SEvent G = new SEvent ("G");

    SEventList statelist =
      new SEventList (A,
        new SEventList (B,
          new SEventList (C,
            new SEventList (D,
              new SEventList (G,null)))));


    
    Path sudp = new Path ("SUD", null);
    Path p1p  = sudp.append("P1");
    Path p2p  = sudp.append("P2");
    Path p3p  = sudp.append("P3");
    Path q1p  = p3p.append("");
    Path q2p  = p3p.append("Q2");
    Path r1p = q2p.append("R1");
    Path r2p = q2p.append("R2");
    Path s1p = r1p.append("S1");
    Path s2p = r1p.append("S2");
    Path t1p = r2p.append("T1");
    Path t2p = r2p.append("T2");

    Path sudk = new Path ("SUD", null);
    Path ttt =sudk.append("P1").append("T2");

    PathList pathlist =
    new PathList (sudp,
      new PathList (p1p,
       new PathList (p2p,
         new PathList (p3p,
           new PathList (q1p,
             new PathList (q2p,
               new PathList (r1p,
                 new PathList (r2p,
                   new PathList (s1p,
	             new PathList (s2p,
                       new PathList (t1p,
                         new PathList (t2p,null))))))))))));

  Basic_State S1 = new Basic_State (new Statename("S1"));
  Basic_State S2 = new Basic_State (new Statename("S2"));
  Basic_State T1 = new Basic_State (new Statename("T1"));
  Basic_State T2 = new Basic_State (new Statename("T2"));

  Tr tr1 = new Tr (new Statename ("S1"), 
		   new Statename ("S2"),
		   new TLabel (new GuardEmpty(new Dummy()),new ActionEmpty(new Dummy())));

  Tr tr2 = new Tr (new Statename ("S2"), 
		   new Statename ("S1"),
		   new TLabel (new GuardCompg (new Compguard (Compguard.AND,
							  new GuardEvent(new SEvent("A")),
							  new GuardEvent(new SEvent("C")))),
					   new ActionEmpty (new Dummy())));


  Or_State R1 = new Or_State (new Statename ("R1"),
			      new StateList (S1, new StateList(S2, null)),
			      new TrList (tr1, new TrList (tr2, null)),
			      new StatenameList (new Statename("S9"), null),	
			      null);
  
  Or_State R2 = new Or_State (
			      new Statename ("R2"),
			      new StateList (T1,new StateList (T2,null)),
			      new TrList  (new Tr (new Statename ("T1"), 
						   new Statename ("T2"),
						   new TLabel (new GuardCompg (new Compguard (Compguard.AND,
							  new GuardEvent(new SEvent("C")),
							  new GuardEvent (new SEvent("A")))),new ActionEmpty(new Dummy()))),
					   new TrList (new Tr (new Statename ("T2"), 
							       new Statename ("T1"),
							       new TLabel (new GuardEvent(new SEvent("G")),new ActionEmpty(new Dummy()))),null)),
			      null,	
			      null);
  
  Basic_State Q1 = new Basic_State (new Statename("Q1"));
    
  And_State Q2 = new And_State (
				new Statename ("Q2"),
				new StateList (R1,new StateList (R2,null)));
  
  Basic_State P1 = new Basic_State (new Statename("P1"));

  Basic_State P2 = new Basic_State (new Statename("P2"));

  Or_State P3 = new Or_State (
			      new Statename ("P3"),
			      new StateList (Q1,new StateList (Q2,null)),
			      new TrList (new Tr (new Statename ("Q1"), 
						  new Statename ("Q2"),
						  new TLabel (new GuardEmpty(new Dummy()),
							                     new ActionEvt (new SEvent("C")))),
					  null),
			      new StatenameList( new Statename("Q1"), null),	
			      null);
  
  Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,new StateList (P3,null))),
			       new TrList (new Tr (new Statename ("P1"), 
						   new Statename ("P2"),
						   new TLabel (new GuardCompp(new Comppath(Comppath.IN,ttt)),new ActionEmpty(new Dummy()))),
					   new TrList (new Tr (new Statename ("P1"), 
							       new Statename ("P3"), 
							       new TLabel (new GuardEvent(new SEvent("A")),new ActionEmpty(new Dummy()))),
						       new TrList (new Tr (new Statename ("P1"), 
									   new Statename ("P1"), 
									   new TLabel (new GuardEvent(new SEvent("A")),
										       new ActionEmpty(new Dummy()))),null))),
			       new StatenameList (new Statename("P1"), new StatenameList(new Statename("P1"), null)),
			       null);  
  
  return new Statechart (statelist,
			 blist,
			 pathlist,
			 SUD);
  }

/**
 * Statechart Magnus 3
 */
  public static Statechart getExample_m3() {

    Basic_State P1 = new Basic_State (new Statename("P1"));
    Basic_State P2 = new Basic_State (new Statename("P2"));


    Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,null)),
			       new TrList (new Tr (new Statename("P1"), new Statename ("P2"), new TLabel (null,null)),
					     new TrList (new Tr (new Statename ("P1"), new Conname("C1"), new TLabel (null,null)),
						     new TrList (new Tr (new Conname("C1"), new Statename ("P2"),	new TLabel (null,null)),
                   new TrList (new Tr (new Conname("C8"), new Conname("C7"), new TLabel (null,null)),
                     new TrList (new Tr (new Conname("C5"), new Statename ("P1"), new TLabel (null,null)),
                       new TrList (new Tr (new Statename("P1"), new Conname("C6"), new TLabel (null,null)),
                         new TrList (new Tr (new Statename("P1"), new Conname("C2"), new TLabel (null,null)),
                           new TrList (new Tr (new Conname("C2"), new Conname("C3"), new TLabel (null,null)),
                             new TrList (new Tr (new Conname("C2"), new Conname("C4"), new TLabel (null,null)),
                               new TrList (new Tr (new Conname("C4"), new Conname("C2"), new TLabel (null,null)),
                                 new TrList (new Tr (new Conname("C3"), new Conname("C4"), new TLabel (null,null)),
              null))))))))))),
			       new StatenameList (new Statename("P1"), null),
			       new ConnectorList (new Connector(new Conname("C1")),
               new ConnectorList (new Connector(new Conname("C2")),
                 new ConnectorList (new Connector(new Conname("C3")),
                   new ConnectorList (new Connector(new Conname("C4")),
                     new ConnectorList (new Connector(new Conname("C5")),
                       new ConnectorList (new Connector(new Conname("C6")),
                         new ConnectorList (new Connector(new Conname("C7")),
                           new ConnectorList (new Connector(new Conname("C8")),
             null)) ))))))
    );

    return new Statechart (null, null, null, SUD);
  }



}



