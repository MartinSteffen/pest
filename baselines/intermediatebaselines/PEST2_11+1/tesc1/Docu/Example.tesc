// Beispiel aus dem Pflichtenheft
//
// $Id: Example.tesc,v 1.2 1999/01/17 17:11:26 swtech20 Exp $

  event (A,B,C,D,G) 

  or SUD
      basic P1
      basic P2
      or P3
         basic Q1
         and Q2
             or R1
                basic S1
                basic S2
                from S1 to S2 on C
                from S2 to S1 on G[in(SUD.P3.Q2.T2)]
                default S1
             end R1
             or R2
                basic T1
                basic T2
                from T1 to T2 on D
                from T2 to T1 on G
                default T1
             end R2
         end Q2
         from Q1 to Q2 on A do C
         default Q1
      end P3
      from P1 to P2 on C
      from P1 to P3 on A
      from P3 to P1 on B
      default P1
  end SUD

//----------------------------------------------------------------------
//	Beispiel aus dem Pflichtenheft
//	------------------------------
//
//	$Log: Example.tesc,v $
//	Revision 1.2  1999/01/17 17:11:26  swtech20
//	Aenderung der Beispiele auf Statemate-Style
//
//	Revision 1.1  1999/01/11 12:14:21  swtech20
//	Updates.
//
//	Revision 1.2  1999/01/04 15:26:26  swtech20
//	Schluesselworte in Bezeichnern werden nicht mehr zugelassen.
//	Status upgedated.
//
//	Revision 1.1  1999/01/03 21:49:32  swtech20
//	Doku und Testdaten

