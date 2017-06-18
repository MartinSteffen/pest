# Global.Safer.P1
# Es fehlt noch das freezen der Schalter, damit eine Žnderung der Schalter w„hrend eines Zyklus keinen Einflu˜ auf die Berechnung hat.

events: P1_1Ready;

or P1:
	basic init;

	ref Grip in Grip.tesc type tesc;
	ref AAH in AAH.tesc type tesc;

	defcon: init;

	transitions:
		from init to Grip on; # Hier fehlt noch das freezen der Schalter
		from Grip to AAH on P1_1Ready;
end P1;
