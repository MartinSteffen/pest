package absyn.test;



import absyn.*;
import util.PrettyPrint;


public class Test {

  /**
   * main-Methode -> ein paar Tests der abstrakten Syntax
   */
  public static void main (String[] args) {

    // Erzeuge PP-Objekt.
    PrettyPrint pp = new PrettyPrint ();
    Statechart sc =  Example.getExample();
    pp.start(sc);

    try{
	//Statechart sc2 = (Statechart)sc.clone();

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

	A.clone();
	statelist.clone();

	Path sudp = new Path ("SUD", null);
	sudp.clone();
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
	
	t2p.clone();





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

    pathlist.clone();

  Basic_State S1 = new Basic_State (new Statename("S1"));
  Basic_State S2 = new Basic_State (new Statename("S2"));
  Basic_State T1 = new Basic_State (new Statename("T1"));
  Basic_State T2 = new Basic_State (new Statename("T2"));
  S1.clone();
  
  Tr tr1 = new Tr (new Statename ("S1"), 
		   new Statename ("S2"),
		   new TLabel (new GuardEvent(new SEvent("C")),new ActionEmpty(new Dummy())));

  tr1.clone();

  Tr tr2 = new Tr (new Statename ("S2"), 
		   new Statename ("S1"),
		   new TLabel (new GuardCompg (new Compguard (Compguard.AND,
							  new GuardEvent(new SEvent("G")),
							  new GuardCompp (new Comppath (Comppath.IN,t2p)))),
					   new ActionEmpty (new Dummy())));


  Or_State R1 = new Or_State (new Statename ("R1"),
			      new StateList (S1,new StateList (S2,null)),
			      new TrList (tr1, new TrList (tr2, null)),
			      new StatenameList (new Statename("S1"), null),	
			      null);

  R1.clone();
  
  Or_State R2 = new Or_State (
			      new Statename ("R2"),
			      new StateList (T1,new StateList (T2,null)),
			      new TrList  (new Tr (new Statename ("T1"), 
						   new Statename ("T2"),
						   new TLabel (new GuardEvent(new SEvent("D")),new ActionEmpty(new Dummy()))),
					   new TrList (new Tr (new Statename ("T2"), 
							       new Statename ("T1"),
							       new TLabel (new GuardEvent(new SEvent("G")),new ActionEmpty(new Dummy()))),null)),
			      new StatenameList (new Statename("T1"), null),	
			      null);
  
  Basic_State Q1 = new Basic_State (new Statename("Q1"));
  Q1.clone();
    
  R1.clone();
  R2.clone();
  (new StateList (R2,null)).clone();

   (new StateList (R1,new StateList (R2,null))).clone();

   And_State Q2 = new And_State (
				 new Statename ("Q2"),
				 new StateList (R1,new StateList (R2,null)));
  

   (new Statename ("Q2")).clone();
   (new StateList (R1,new StateList (R2,null))).clone();
   (new And_State (new Statename ("Q2"), new StateList (R1,new StateList (R2,null)))).clone();
   Basic_State P1 = new Basic_State (new Statename("P1"));

  Basic_State P2 = new Basic_State (new Statename("P2"));
  P2.clone();

  Or_State P3 = new Or_State (
			      new Statename ("P3"),
			      new StateList (Q1,new StateList (Q2,null)),
			      new TrList (new Tr (new Statename ("Q1"), 
						  new Statename ("Q2"),
						  new TLabel (new GuardEvent(new SEvent("A")),
							                     new ActionEvt (new SEvent("C")))),
					  null),
			      new StatenameList( new Statename("Q1"), null),	
			      null);
  (new Statename ("P3")).clone();
  P3.clone();
  Or_State P3p = new Or_State (
			      new Statename ("P3"),
			      new StateList (Q1,new StateList (Q2,null)),
			      new TrList (new Tr (new Statename ("Q1"), 
						  new Statename ("Q2"),
						  new TLabel (new GuardEvent(new SEvent("A")),
							                     new ActionEvt (new SEvent("C")))),
					  null),
			      new StatenameList( new Statename("Q1"), null),	
			      new ConnectorList(new Connector(new Conname("Und nun")), null));

  (new Conname("Und nun")).clone();
  (new Connector(new Conname("Und nun"))).clone();
  (new ConnectorList(new Connector(new Conname("Und nun")), null)).clone();
    
  P3p.clone();
  (new Statename ("P3")).clone();
  Q1.clone();
  Q2.clone();
  (new StateList (Q1,new StateList (Q2,null))).clone();


  
  Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,new StateList (P3,null))),
			       new TrList (new Tr (new Statename ("P1"), 
						   new Statename ("P2"), 
						   new TLabel (new GuardEvent(new SEvent("C")),new ActionEmpty(new Dummy()))),
					   new TrList (new Tr (new Statename ("P1"), 
							       new Statename ("P3"), 
							       new TLabel (new GuardEvent(new SEvent("A")),new ActionEmpty(new Dummy()))),
						       new TrList (new Tr (new Statename ("P3"), 
									   new Statename ("P1"), 
									   new TLabel (new GuardEvent(new SEvent("B")),
										       new ActionEmpty(new Dummy()))),null))),
			       new StatenameList (new Statename("P1"), null),
			       null);  
  

    sc.clone();
    }
    catch (Exception e) {
	System.out.println(e);
    };
    

  } // method main

} // class PrettyEx

