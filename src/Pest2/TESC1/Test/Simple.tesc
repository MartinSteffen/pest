// Einfaches Beispiel.
//
// $Id: Simple.tesc,v 1.2 1999-01-17 17:11:44 swtech20 Exp $

 event (e1,e2)
  
  and A
     or A1
        basic D
        from D to D on e1[v1] do e2
     end A1
     or A2
        basic D
	con C
	from D to C do e1
	default D
     end A2
  end A

//----------------------------------------------------------------------
//	Einfaches Beispiel
//	------------------
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  1999/01/11 12:16:33  swtech20
//	Neue Beispiele zum Testen des TESC-Imports.
//

