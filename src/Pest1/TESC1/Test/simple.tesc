events: e1,e2, inev;

or S3: 
	basic  S1;
	basic  S2;
	and S4:
		basic S5;
		basic S6;
		from S5 to S6 on e2 do B;
	end S4;

	from S1 to S2 on (e1 &&(a => ( S4<=>D )) ) do (e1, e2);


	or S0:
		events: 	XYZ;
		connectors: 	;
		pathnames: 	??;
		bvars: 		A;		
	
		basic S-1;
		basic S-2;

		from S1 to S2 on (not A:=false) do XYZ;
	end S0;

end S3;
