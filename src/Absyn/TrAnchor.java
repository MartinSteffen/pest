package absyn;

import java.io.Serializable;

public abstract class TrAnchor extends Absyn implements Serializable, Cloneable  {
/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public abstract Object clone() throws  CloneNotSupportedException;
};







//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TrAnchor.java,v 1.7 1998-12-15 16:33:32 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.6  1998/12/15 13:38:09  swtech00
//	exception-tag hinzugefuegt um javadoc sauber durchlaufen zu lassen
//
//	Revision 1.5  1998/12/15 07:11:11  swtech01
//	Added Serialization to all classes
//
//	Revision 1.4  1998/12/11 17:43:02  swtech00
//	Cloneable
//
//	Revision 1.3  1998/11/26 16:32:26  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
