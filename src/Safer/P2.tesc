# Global.Safer.P2

events: P2Ready;

or P2:
	basic init;

	ref ThrusterSelection in ThrusterSelection.tesc type tesc;

	defcon: init;

	transitions:
		from init to ThrusterSelection on;
end P2;
