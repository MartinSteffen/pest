import Check.*;
import Absyn.*;

/**
 *  @author   Daniel Wendorff und Magnus Stiller
 *  @version  $Id: t_Example.java,v 1.1 1998-12-04 07:36:11 swtech11 Exp $
 */
public class t_Example{

  public static Statechart getExample_d() {
    
    SEvent A = new SEvent ("A");
    SEvent A1 = new SEvent ("A");
    SEvent B = new SEvent ("B");
    SEvent C = new SEvent ("C");
    SEvent D = new SEvent ("D");
    SEvent G = new SEvent ("G");

    SEventList statelist =
      new SEventList (A,
 new SEventList (A1,
        new SEventList (B,
         
            new SEventList (D,
              new SEventList (G,null)))));

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
		   new Label (new GuardEvent(C),null));

  Tr tr2 = new Tr (new Statename ("S2"), 
		   new Statename ("S1"),
		   new Label (new GuardCompg (new Compguard (Compguard.AND,
							  new GuardEvent(C),
							  new GuardCompp (new Comppath (Comppath.IN,
											new Path ("T245"))))),
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
						   new Label (new GuardEvent(D),null)),
					   new TrList (new Tr (new Statename ("T2"), 
							       new Statename ("T1"),
							       new Label (new GuardEvent(G),null)),null)),
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
						  new Label (new GuardEvent(A),new ActionEvent (C))),
					  null),
			      new StatenameList( new Statename("Q1"), null),	
			      null);
  
  Or_State SUD = new Or_State (
			       new Statename ("SUD"),
			       new StateList (P1,new StateList (P2,new StateList (P3,null))),
			       new TrList (new Tr (new Statename ("P1"), 
						   new Statename ("P2"), 
						   new Label (new GuardEvent(C),null)),
					   new TrList (new Tr (new Statename ("P1"), 
							       new Statename ("P3"), 
							       new Label (new GuardEvent(A),null)),
						       new TrList (new Tr (new Statename ("P3"), 
									   new Statename ("P1"), 
									   new Label (new GuardEvent(B),null)),null))),
			       new StatenameList (new Statename("P1"), null),
			       null);  
  
  return new Statechart (statelist,
			 blist,
			 pathlist,
			 SUD);
  }
}



