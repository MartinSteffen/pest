package simu;

/**
 * Hilfs-Klasse fuer Tabelle-BVar
 */

import java.util.*;
import java.lang.*;
 
public class BvarTab {
	absyn.Bvar bName;
	boolean bWert;

/**
 * Default-Constructor fuer BvarTab
 */
public BvarTab(absyn.Bvar arg1, boolean boolWert) {
	bName = arg1;
	bWert = boolWert;
}
}