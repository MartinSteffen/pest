# Beispiel Tesc-File 1
# leiten als Erweiterung der Grammatik Kommentare ein
# Falls ein Kommentar nicht am Anfang einer Zeile steht, muss er durch ein Space/Tab
# vom Rest getrennt werden


or S1:	
	basic B2;
	basic B1;

	defcon : B2;

	cons: C1;	
      
	transitions:
	from B2 to B1 on E do E1, E2, E3;	

end S1;


