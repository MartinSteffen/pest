package simu;

import absyn.*;
import java.io.*;

/**
 * Unterklasse fuer TransTab
 */
public class TransTabEntry implements Serializable{
	Path tteSource;
	Path tteTarget;
	TLabel tteLabel;
/**
 * Hilfsklasse
 *
 * @param arg1 absyn.Path
 * @param arg2 absyn.Path
 * @param arg3 absyn.TLabel
 * @return
 * @version V1 vom 14.01.1999
 */
public TransTabEntry(Path arg1, Path arg2, TLabel arg3) {
	tteSource = arg1;
	tteTarget = arg2;
	tteLabel = arg3;
}
}