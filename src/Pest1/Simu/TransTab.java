package simu;

import absyn.*;
import java.io.*;

/**
 * Ein Eintrag in die transList
 */
public class TransTab implements Serializable{
	TransTabEntry transition = null;
	TrList tListe = null;
	ConnectorList conn = null;	
/**
 * Hilfsklasse fuer die Transitionstabelle
 *
 * @param arg1 simu.TransTabEntry
 * @param arg2 absyn.TrList
 * @param arg3 absyn.ConnectorList
 * @return
 * @version V1 vom 14.01.1999
 */
public TransTab(TransTabEntry arg1, TrList arg2, ConnectorList arg3) {
	transition = arg1;
	tListe = arg2;
	conn = arg3;	
}
}