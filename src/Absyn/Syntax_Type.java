package absyn;

import java.io.Serializable;

import java.awt.Rectangle;

public abstract class Syntax_Type extends Absyn implements Serializable, Cloneable {
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */

  public abstract Object clone() throws  CloneNotSupportedException;
}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Syntax_Type.java,v 1.2 1999-02-01 14:33:46 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  1999/01/31 17:23:37  swtech00
//	Neuen ``Zustand'' (state) hinzugef"ugt:
//
//		Ref_State als Spezialfall eines Basic_States.
//
//
//	Zu Angabe der ``importierten'' Syntax weitere Klassen hinzugef"ugt,
//	die den Typ der Syntax beschreiben:
//
//	     Syntax_Type   ::=   Tesc_Syntax | Pest_AbSyntax
//	     Pest_AbSyntax ::=   Pest_CoordSynax | Pest_NocoordSyntax
//
//	[Steffen]
//
//----------------------------------------------------------------------


