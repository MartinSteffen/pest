package simu;

/**
 * Hilfsklasse fuer den Eintrag in die eigene Eventtabelle
 */
public class SEventTab {
	absyn.SEvent bName;
	boolean bWert;

/**
 * SEventTab MainConstructor
 *
 * @param arg1 absyn.SEvent
 * @param boolWert boolean
 * @return
 * @version V1 vom 14.01.1999
 */
public SEventTab(absyn.SEvent arg1, boolean boolWert) {
	bName = arg1;
	bWert = boolWert;

}
}