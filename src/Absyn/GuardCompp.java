package Absyn;

import java.io.Serializable;

public class GuardCompp extends Guard implements Serializable, Cloneable {
    public Comppath cpath;
    public GuardCompp(Comppath p) {
	cpath = p;
    };

/**
 * @exception CloneNotSupportedException self-explanatory exception
 */
    public Object clone() throws CloneNotSupportedException {
	return new GuardCompp ((Comppath)cpath.clone());
    };

};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: GuardCompp.java,v 1.4 1998-12-15 13:38:05 swtech00 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  1998/12/15 07:11:08  swtech01
//	Added Serialization to all classes
//
//	Revision 1.2  1998/12/11 17:42:58  swtech00
//	Cloneable
//
//	Revision 1.1  1998/12/01 17:52:54  swtech00
//	Anderungen in der Abstrakten Syntax:
//
//		- die _s-Klassen sind alle rausgeworfen, wie besprochen
//		- gleichfalls _s-Felder
//		- Die Operatorkonstanten in die jeweiligen Klassen als Konstanten
//		  mit aufgenommen
//		- Alle Klassen beginnen nun mit einem Gro"sbuchstaben
//
//	Revision 1.2  1998/11/26 16:32:31  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
