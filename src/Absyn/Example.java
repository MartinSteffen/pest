package Absyn;


public class Example {
  
  public static Statechart getExample() {
    
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

    PathList pathlist =  
        new PathList (new Path ("SUD"),
          new PathList (new Path ("P1"),
            new PathList (new Path ("P2"),
              new PathList (new Path ("P3"),
		new PathList (new Path ("Q1"),
                  new PathList (new Path ("Q2"),
                    new PathList (new Path ("R1"),
                      new PathList (new Path ("R2"),
                        new PathList (new Path ("S1"),
			  new PathList (new Path ("S2"),
                            new PathList (new Path ("T1"),
                              new PathList (new Path ("T2"),null))))))))))));  

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
							  new GuardCompp (new Comppath (Comppath.IN,
											new Path ("T2"))))),
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
						  new TLabel (new GuardEvent(A),new ActionEvent (C))),
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



