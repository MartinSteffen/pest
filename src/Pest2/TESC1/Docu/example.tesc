// Beispiel aus dem Pflichtenheft
//
// $Id

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
                from S2 to S1 on G & in(SUD.P3.Q2.T2)
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
//	$Log: not supported by cvs2svn $
