package Absyn;

public class Example {

  public static Statechart getExample() {

    SEvent A = new SEvent ("A");
    SEvent B = new SEvent ("B");
    SEvent C = new SEvent ("C");
    SEvent D = new SEvent ("D");
    SEvent G = new SEvent ("G[in(T2)]"); // so richtig ?
    SEventList slist =
        new SEventList (A,
        new SEventList (B,
        new SEventList (C,
        new SEventList (D,
        new SEventList (G,null)))));

    PathList plist =  
        new PathList (new Path ("SUD.P1"),
        new PathList (new Path ("SUD.P2"),
        new PathList (new Path ("SUD.P3"),
        new PathList (new Path ("SUD.P3.Q1"),
        new PathList (new Path ("SUD.P3.Q2"),
        new PathList (new Path ("SUD.P3.Q2.R1"),
        new PathList (new Path ("SUD.P3.Q2.R1.S1"),
        new PathList (new Path ("SUD.P3.Q2.R1.S2"),
        new PathList (new Path ("SUD.P3.Q2.R2"),
        new PathList (new Path ("SUD.P3.Q2.R2.T1"),
        new PathList (new Path ("SUD.P3.Q2.R2.T2"),null)))))))))));  

    Basic_State S1 = new Basic_State (new Statename("S1"));
    Basic_State S2 = new Basic_State (new Statename("S2"));
    Basic_State T1 = new Basic_State (new Statename("T1"));
    Basic_State T2 = new Basic_State (new Statename("T2"));

    Or_State R1 = new Or_State (
        new Statename ("R1"),
        new StateList (S1,new StateList (S2,null)),
        new TrList (new Tr (new Statename ("S1"), 
			    new Statename ("S2"),
			    new Label (new s_event(C),null)),
          new TrList (new Tr (new Statename ("S2"), 
			      new Statename ("S1"),
			      new Label (new s_event(G),null)),null)),
        new StatenameList (new Statename("S1"), null),	
        null);

    Or_State R2 = new Or_State (
        new Statename ("R2"),
        new StateList (T1,new StateList (T2,null)),
        new TrList  (new Tr (new Statename ("T1"), 
			     new Statename ("T2"),
			     new Label (new s_event(D),null)),
          new TrList (new Tr (new Statename ("T2"), 
			      new Statename ("T1"),
			      new Label (new s_event(G),null)),null)),
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
			    new Label (new s_event(A),new s_egen (C))),
                    null),
        new StatenameList( new Statename("Q1"), null),	
        null);

    Or_State SUD = new Or_State (
        new Statename ("SUD"),
        new StateList (P1,new StateList (P2,new StateList (P3,null))),
        new TrList (new Tr (new Statename ("P1"), 
			    new Statename ("P2"), 
			    new Label (new s_event(C),null)),
          new TrList (new Tr (new Statename ("P1"), 
                              new Statename ("P3"), 
			      new Label (new s_event(A),null)),
          new TrList (new Tr (new Statename ("P3"), 
                              new Statename ("P1"), 
			      new Label (new s_event(B),null)),null))),
        new StatenameList (new Statename("P1"), null),
        null);  
 
    return new Statechart (slist,null,plist,SUD);
  }
}


