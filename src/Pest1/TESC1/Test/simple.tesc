# Beispiel Tesc-File
# '#' leiten als Erweiterung der Grammatik Kommentare ein
# Falls ein Kommentar nicht am Anfang einer Zeile steht, muss er durch ein Space/Tab
# vom Rest getrennt werden

events: 	e1, e2;	
bvars:		C;

or S3: 
	or S4:
		or StateY:
			basic B1;
			basic B2;
		end StateY;	
	end S4;
	and S2:	
		basic B1;
		basic B2;
	end S2;

	basic S5;

	defcon : S4;

	cons: c1, c2;

	transitions:
	from S4 to S2 on C && e1 || e2 do C := e1 && e2 || C , e2;
	from S2 to S4 on e2 do ~ ;
	
end S3;
