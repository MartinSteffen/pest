package absyn;
import java.io.Serializable;
import java.awt.Rectangle;

public class Ref_State extends Basic_State {
  /**
   * Name of the file
   */
  public String filename;
  /**
   * sort of the imported syntax
   */
  public Syntax_Type filetype;
  
  public Ref_State (Statename n)                           {super(n);}
  public Ref_State (Statename n, CRectangle r)             {super(n,r);}
  public Ref_State (Statename n, CRectangle r, Location l) {super(n,r,l);}

  

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone  = (location == null) ? null : (Location)location.clone();
    return new Ref_State ((Statename)name.clone(),rect, locationclone);
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Ref_State.java,v 1.2 1999-02-01 13:41:15 swtech00 Exp $
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
