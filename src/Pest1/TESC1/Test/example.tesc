# Beispiel aus dem Pflichtenheft
# ohne Fehler ( /A bei defcon / Interleveltransition von Q2->P2 )

#events: A, B, C, D, G;

or SUD:
	basic P1;
	basic P2;
	
	or P3:
		basic Q1;
		and Q2:
			or R1:
				basic S1;
				basic S2;

				defcon: S1;

				transitions:
				from S1 to S2 on C;
				from S2 to S1 on G && in(SUD.P3.Q2.R2.T2);
			end R1;

			or R2:
				basic T1;
				basic T2;

				defcon: T1;

				transitions:
				from T1 to T2 on D;
				from T2 to T1 on G;	
			end R2;		
		end Q2;
		defcon: Q1;
		transitions:
		from Q1 to Q2 on A do C;

	end P3;

	defcon: P1;

	transitions:
	from P1 to P2 on C;
	from P1 to P3 on A;
	from P3 to P1 on B;
	
end SUD;
