package tesc1;

import absyn.*;
import java.io.*;
import java.util.*;

/**
 * Generiert fuer ein TLabel eine Stringdarstellung der Form EventExpr[BvarExpr] / Action  
 * <p>
 * <hr>
 * Abgeleitet von TESCWriter. Ueberlaedt output(BufferedWriter,TLabel) fuer
 * die Statemate-Darstellung eines Transitionslabels.
 * <hr>
 * @version  $Id: TESCCaptionWriter.java,v 1.2 1999-01-18 17:08:51 swtech20 Exp $
 * @author Michael Suelzer, Christoph Schuette.
 *  
 */
class TESCCaptionWriter extends TESCWriter {

    /**
     *  Ausgabe Label in der Statemate Notation EventExpr[BvarExpr] / Action.
     */
    protected void output (BufferedWriter writer, TLabel l) throws IOException {

	if (l != null) {

	    Guard  g = l.guard;
	    Action a = l.action;  

	    if (!isGuardEmpty (g)) {

		// Hat unser Guard den richtigen Aufbau ?
		if ((g instanceof GuardCompg) && ((GuardCompg)g).cguard.eop == Compguard.AND) {

		    // Gibt es EventExpr ?
		    if (!isGuardEmpty(((GuardCompg)g).cguard.elhs)) {
			start (writer, ((GuardCompg)g).cguard.elhs);
		    }

		    // Gibt es BvarExpr?
		    if (!isGuardEmpty(((GuardCompg)g).cguard.erhs)) {
			writer.write (Token.LSPar.getValue());
			start (writer, ((GuardCompg)g).cguard.erhs); 
			writer.write (Token.RSPar.getValue());
		    }
		}
		else {
		    Error ("Guard besitzt nicht die Struktur EventExpr[BVarExpr]");
		}
	    }
	    if (!isActionEmpty(a)) {
		writer.write (" / ");
		start (writer, a);
	    }
	}
	writer.flush ();
    } 
}
