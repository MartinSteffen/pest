package simu;

import absyn.*;

/**
 * New type, created from HH on INTREPID.
 */
public class TransTabEntry {
	Path tteSource;
	Path tteTarget;
	TLabel tteLabel;
/**
 * Hilfsklasse
 */
public TransTabEntry(Path arg1, Path arg2, TLabel arg3) {
	tteSource = arg1;
	tteTarget = arg2;
	tteLabel = arg3;
}
}