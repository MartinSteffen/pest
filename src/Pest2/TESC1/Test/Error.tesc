// Dieses TESC-Beispiel beinhaltet folgende Fehler:
//
//  - Ungueltige State-Verweise (SS)
//  - Ungueltige Pfade (PP)
//  - Nicht referenzierte Variablen (VV), Events (EE) und Zustaende (ZZ)
//    (Falls soetwas ueberhaupt relevant ist)
//  - Leere Zustaende (OR_STATE_2)
//
// Diese werden vom TESC-Parser nicht erkannt und muessen somit
// spaetestens vom Syntax-Checker ermittelt werden.
// Fuer weitere Kommentare siehe Dokumentation TESCLoader.
//
// INHALTLICH IST DIESES BEISPIEL  A B S O L U T  SINNLOS.
//
// $Id: Error.tesc,v 1.1 1999-01-11 12:16:31 swtech20 Exp $

  var (V1,V2,VV)
  event (A,B,C,EE)

  or OR_STATE

      basic B1
      basic B2
      basic ZZ

      or OR_STATE_2                                       // Leerer Zustand
      end OR_STATE_2

      from B1 TO B2 on in(OR_STATE.PP) do (V1=true,A,B,C) // Ungueltiger Pfad
      from B1 TO SS                                       // Ungueltiger State-Verweis
	
 end OR_STATE


//----------------------------------------------------------------------
//	Fehler, die vom TESC-Parser nicht erkannt werden
//	------------------------------------------------
//
//	$Log: not supported by cvs2svn $

