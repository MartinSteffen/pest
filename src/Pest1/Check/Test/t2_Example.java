import Absyn.*;


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
    PathList pathlist =
    new PathList (sudp,
      new PathList (p1p,
       new PathList (p2p,
           new PathList (q1p,
             new PathList (q2p,
               new PathList (r1p,
                 new PathList (r2p,
                   new PathList (s1p,
	             new PathList (s2p,
                       new PathList (t1p,
                         new PathList (t2p,null)))))))))));

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
    
    SEvent A = new SEvent ("A");
    SEvent B = new SEvent ("B");
    SEvent C = new SEvent ("C");
    SEvent D = new SEvent ("D");
    SEvent G = new SEvent ("G");

    SEventList statelist =
      new SEventList (A,
        new SEventList (B,
          new SEventList (C,
            new SEventList (D,
              new SEventList (G,null)))));

    Path sudp = new Path ("Sud", null);
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

  Basic_State S1 = new Basic_State (new Statename("S1"));
  Basic_State S2 = new Basic_State (new Statename("S2"));
  Basic_State T1 = new Basic_State (new Statename("T1"));
  Basic_State T2 = new Basic_State (new Statename("T2"));
  
  Tr tr1 = new Tr (new Statename ("S1"), 
		   new Statename ("S2"),
		   new TLabel (new GuardEvent(C),null));

  Tr tr2 = new Tr (new Statename ("S2"), 
		   new Statename ("S1"),
		   new TLabel (new GuardCompg (new Compguard (Compguard.AND,
							  new GuardEvent(G),
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
							       new TLabel (new GuardEvent(G),null)),null)),
			      new StatenameList (new Statename("T1"), null),	
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
			 null,
			 pathlist,
			 SUD);
  }
}



