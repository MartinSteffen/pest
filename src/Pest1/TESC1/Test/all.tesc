# Beispiel Tesc-File 2
# leiten als Erweiterung der Grammatik Kommentare ein
# Falls ein Kommentar nicht am Anfang einer Zeile steht, muss er durch ein Space/Tab
# vom Rest getrennt werden

# Alle syntaktischen Möglichkeiten sollen hier einmal angewandt werden.

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

	and A1:	
		or S1:
			basic B3;
			basic B4;
			defcon: B3;
		end S1;
		basic B2;
	end A1;

	basic S6;

	or S5:
		and A2:
			basic X;
			basic Y;
		end A2;
		basic Z;

		defcon: A2;
		cons: c3;
		transitions:
			from A2 to c3 on [in(S3.S5.A2)];
			from c3 to Z;
	end S5;

	ref R1 in bsp1.tesc type tesc;

	defcon : S4;

	cons: c1, c2;

	transitions:
	from S4 to A1 on e2 [C]  do C := B1 && B2 || A , e1;
	from A1 to S4 on e1 do ~ ;
	from S5 to R1 on [A] do e2;
	
end S3;
