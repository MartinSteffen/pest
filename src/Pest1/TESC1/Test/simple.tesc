events: e1, e2, inev;
bvars: 		A, B, D;

or S3: 
	basic  S1;
	basic  S2;
	and S4:
		basic S5;
		basic S6;
		from S5 to S6 on e2 do B;
	end S4;

	transitions:
	from S1 to S2 on (in(S4) || e1 && (A => ( B<=>D )) ) do e2;


	or S0:	
		connectors: c1;
			
		basic S-1;
		basic S-2;

		transitions:
		from S1 to S2 on (not A) do A:=true, e1;
	end S0;

end S3;
