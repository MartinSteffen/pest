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
  
  public Ref_State (Statename n, String fn, Syntax_Type t) {
    super(n);
    filename = fn;
    filetype = t;
  }
  public Ref_State (Statename n, 
		    CRectangle r, 
		    String fn, 
		    Syntax_Type t) {
    super(n,r);
    filename = fn;
    filetype = t;
  }
  public Ref_State (Statename n, 
		    CRectangle r, 
		    Location l,
		    String fn, 
		    Syntax_Type t) {
    super(n,r,l);
    filename = fn;
    filetype = t;
  }

  

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
  public Object clone() throws CloneNotSupportedException {
    Location  locationclone    = (location == null) ? null : (Location)location.clone();
    Syntax_Type filetypeclone  = (filetype == null) ? null : (Syntax_Type)filetype.clone();
    Statename nameclone        = (name  == null)    ? null : (Statename)name.clone();
    return new Ref_State (nameclone,rect, locationclone, 
			  filename,
			  filetypeclone);
    // beachte: filename ist ein string und muss nicht geklont werden
    };

}

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Ref_State.java,v 1.5 1999-02-09 13:17:11 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  1999/02/09 11:32:58  swtech00
//	Abfangen von weiteren Null-Pointern beim Klonen
//
//	Revision 1.3  1999/02/02 12:49:16  swtech00
//	Konstruktoren erg"anzt [Steffen]
//
//	Revision 1.2  1999/02/01 13:41:15  swtech00
//	public eingefuegt [Steffen]
//
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
