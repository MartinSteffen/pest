package simu;

import absyn.*;

/**
 * New type, created from HH on INTREPID.
 */
public class TransTab {
	TransTabEntry transition = null;
	TrList tListe = null;
	ConnectorList conn = null;	
/**
 * Hilfsklasse fuer die Transitionstabelle
 */
public TransTab(TransTabEntry arg1, TrList arg2, ConnectorList arg3) {
	transition = arg1;
	tListe = arg2;
	conn = arg3;	
}
}