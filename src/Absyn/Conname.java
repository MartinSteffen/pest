package absyn;

import java.io.Serializable;


public class Conname extends TrAnchor implements Serializable, Cloneable{
    public String name;
    public Conname(String n) {
	name  = n;
    };
    
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new Conname(name);
    };

}
//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: Conname.java,v 1.6 1998-12-15 16:33:26 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  1998/12/15 13:38:03  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.4  1998/12/15 07:11:08  swtech01
//	Added Serialization to all classes
//
//	Revision 1.3  1998/12/11 17:42:57  swtech00
//	Cloneable
//
//	Revision 1.2  1998/11/26 16:32:14  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
