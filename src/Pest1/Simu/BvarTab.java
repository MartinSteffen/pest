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
 *
 * @param arg1 absyn.Bvar
 * @param boolWert boolean
 * @return
 * @version V1 vom 14.01.1999
 */
public BvarTab(absyn.Bvar arg1, boolean boolWert) {
	bName = arg1;
	bWert = boolWert;
}
}