# Beispiel Tesc-File 1
# '#' leiten als Erweiterung der Grammatik Kommentare ein
# Falls ein Kommentar nicht am Anfang einer Zeile steht, muss er durch ein Space/Tab
# vom Rest getrennt werden

events: 	e1, e2;	
bvars:		C;

or S1: 
	and S2:	
		basic B1;
		basic B2;
	end S2;

	defcon : S2;

end S1;
