# Beispiel Tesc-File 1
# '#' leiten als Erweiterung der Grammatik Kommentare ein
# Falls ein Kommentar nicht am Anfang einer Zeile steht, muss er durch ein Space/Tab
# vom Rest getrennt werden

events:	E;
bvars:	B;

or S1:	
	basic B2;
	basic B1;

	defcon : S2;

	cons: C1;	
      
	transitions:
	from B2 to B1 on E;	

end S1;


