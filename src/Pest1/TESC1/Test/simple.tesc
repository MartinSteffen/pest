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

	or StateX:	
	end StateX;

	basic S5;

	connectors: c1, c2;

	transitions:
	from S4 to c1 on C && e1 || e2 do C := e1 && e2 || C , e2;
	from S2 to S5 on e2 do ~ ;
	
end S3;
