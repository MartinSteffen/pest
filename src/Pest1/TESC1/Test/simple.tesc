# Beispiel Tesc-File
# leiten als Erweiterung der Grammatik Kommentare ein
# Falls ein Kommentar nicht am Anfang einer Zeile steht, muss er durch ein Space/Tab
# vom Rest getrennt werden

or S3: 
	or S4:
		or StateY:
			basic B1;
			basic B2;
			defcon : B1;
			transitions:
				from B1 to B2;
		end StateY;
		basic B;
		defcon : B;
	end S4;

	and S2:	
		basic B1;
		basic B2;
	end S2;

	basic S5;

	defcon : S4;

	cons: c1, c2;

	transitions:
	from S4 to S2 on e2 [C]  do C := B1 && B2 || A , e1;
	from S2 to S4 on e1 do ~ ;
	
end S3;
