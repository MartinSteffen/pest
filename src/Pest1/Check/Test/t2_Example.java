import Absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: t2_Example.java,v 1.3 1998-12-09 14:28:59 swtech11 Exp $
 */
public class t2_Example {
  
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
							  new GuardCompp (new Comppath (Comppath.IN,t2p)))),
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
									   new TLabel (new GuardEvent(B),null)),null))),
			       new StatenameList (new Statename("P1"), null),
			       null);  
  
  return new Statechart (statelist,
			 blist,
			 pathlist,
			 SUD);
  }


public static Statechart getExample_m() {
    
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
              null))))),
			       new StatenameList (new Statename("P1"), null),
			       new ConnectorList (new Connector(new Conname("SUD-1-CON")),
               new ConnectorList(new Connector(new Conname("sud-2-CON")), null))    );

    return new Statechart (null, null, null, SUD);
  }

  public static Statechart getExample_m3() {

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

}


