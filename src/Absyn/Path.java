package absyn;

import java.io.Serializable;


public class Path  extends Absyn implements Serializable, Cloneable {
    public String head;
    public Path   tail;
    public Path (String h, Path tl) {
	head       = h;
	tail       = tl; 
    };
    public Path append (String s) {
	if(tail != null)
	    {
		return new Path (head,tail.append(s));
	    } 
	else 
	    return new Path(head, new Path (s, null));
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	Path tailclone = (tail == null) ? null :  (Path)tail.clone();
	return new Path (head, (Path)tailclone);
	// Beachte: head ist ein string und muss nicht geklont werden;
    };
  
			 
			 
};
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Path.java,v 1.10 1999-02-09 11:32:57 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.9  1999/01/12 08:57:28  swtech00
//	Die "Anderungen von gestern in den ``Listen'' r"uckg"angig gemacht.
//
//	Grund: Uniformit"at bestimmter Funktionen, insbesondere des PrettyPrinters.
//	       Dieser nimmt als Argument einen Term der abstrakten Syntax (eine
//	       instanz der Klasse Absyn) und verzweigt dann mittels instance_of.
//
//	       Der Code f"ur derartige Funktionen w"are ansonsten etwas komplexer.
//	       Allerdings haben nun (wieder) die Listen ein location-Feld durch
//	       Vererbung aus Absyn, was im Grunde "uberfl"ussig ist.
//
//
//	[Steffen]
//
//	Revision 1.8  1998/12/15 16:33:30  swtech00
//	Towards new package names.
//
//	Revision 1.7  1998/12/15 13:38:06  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.6  1998/12/15 07:11:10  swtech01
//	Added Serialization to all classes
//
//	Revision 1.5  1998/12/11 17:43:00  swtech00
//	Cloneable
//
//	Revision 1.4  1998/12/08 09:37:03  swtech00
//	Path corrected
//
//	Revision 1.3  1998/12/07 15:08:00  swtech00
//	- TLabel anstelle Label
//
//	- Path verbessert: path ist nun eine Liste von Strings
//
//	- Example.java angepasst
//
//	Revision 1.2  1998/11/26 16:32:21  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
